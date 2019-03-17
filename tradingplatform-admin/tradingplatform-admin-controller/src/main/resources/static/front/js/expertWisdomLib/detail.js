/**
 * Created by mu-HUN on 2018/8/22.
 */
var expertsData = expertsResult

$(function () {
    init_data();
    handelClick();
    expertAskButtonClick();
    expertOfAskSubmitBtnClick();
    $(".global-experts-card,.global-experts-card-even").click(function () {
        var id = $(this).data('id');
        window.location.href = '/f/' + id + '/expert_detail.html?pc=true';
    });
    toPeopelCenterExprets();
})

function init_data() {
    var field = $('.expert-profession').data('field');
    var str = "";
    if (!!field) {
        for (var i = 0; i < field.length; i++) {
            var span = $('<span style="display: inline-block;width: 80px;background-color: #0066cc;color: white;font-size: 14px;text-align: center;margin-left: 10px;border-radius: 5px;line-height: 40px;overflow: hidden;text-overflow: ellipsis;white-space: normal;vertical-align: top">' + field[i] + '</span>');
            $('.expert-base-info-item:last-child').append(span);
        }
    }
    // if (!!field) {
    //     for (var i = 0;i < field.length; i++) {
    //         str += field[i].title;
    //         if(i != field.length-1){
    //             str += ",";
    //         }
    //     }
    // }
    $('.expert-profession').html(str);

    getRecommendList();
}

// 处理事件
function handelClick() {
    $(".expert-share-link").off().on("click", function () {
        if ($(this).children("i").hasClass("icon-star-void")) {
            toCollectTheExpert($(this), expertsResult.id, true);
        } else {
            toCollectTheExpert($(this), expertsResult.id, false);
        }
    });
}

/** 推荐专家**/
function getRecommendList() {
    var json = {
        "pager": {//分页信息
            "current": 1,   //当前页数0
            "size": 5        //每页条数
        }
    };
    $.ajax({
        type: 'POST',
        url: '/f/experts/get_recommend_experts',
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setRecommendList(list);
        }
    })
}

/** 设置推荐栏**/
function setRecommendList(list) {
    var expertsCards = $(".global-experts-card");
    // 隐藏不需要的元素
    for (var k = 0; k < expertsCards.length; k++) {
        if (list.length < 5 && k >= list.length) {
            $(expertsCards[k]).css("display", 'none')
        } else {
            $(expertsCards[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(expertsCards[i]).attr("data-id", list[i].id);
        $(expertsCards[i]).find('.title').html(list[i].name);
        $(expertsCards[i]).find('.title').attr('title', list[i].name);
        $(expertsCards[i]).find('.desc').html(list[i].personal_profile);
        $(expertsCards[i]).find('.experts-type-area').html(list[i].technical_title);
        var field = eval('(' + list[i].profession_field + ')');
        var str = '';
        if (!!field) {
            for (var j = 0; j < field.length; j++) {
                str += field[j].title;
                if (j != field.length - 1) {
                    str += ",";
                }
            }
        }
        $(expertsCards[i]).find('.experts-type').html(list[i].technical_title);
    }
}

/*** 用户收藏/ 取消收藏 ***/
function toCollectTheExpert(dom, id, isCollect) {
    var json = {
        "expertsId": id,
        "isCollection": isCollect
    };
    $.ajax({
        type: "POST",
        url: "/f/experts/pc/collection_experts?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            // 403： 未登录
            if (res.status === 403) {
                window.location.href = "/f/login.html?pc=true";
            } else if (res.status === 200) {
                if (!isCollect) {
                    layer.msg("取消收藏成功！")
                    $(dom).children("i").attr("class", "icon-star-void")
                } else {
                    layer.msg("收藏成功！")
                    $(dom).children("i").attr("class", "icon-collect")
                }
                $(dom).children(".collect-num").html(res.data.total)
            }
        }
    })
}

function expertAskButtonClick() {
    $('.expert-base-info-div .expert-ask').click(function () {
        var user = window.localStorage.getItem('user');
        if (!!user) {
            layer.open({
                type: 1,
                title: "在线咨询",
                skin: 'layui-layer-lan', //加上边框
                area: ['500px', '330px'], //宽高
                content: '<div class="expertAskBtnContents"><textarea style="width: 90%;height: 190px;padding: 10px;box-sizing: border-box;margin-left: 5%;margin-top: 20px"></textarea><button class="expertAskBtnSubmit" style="margin-left: 200px;width: 100px;height: 30px;line-height: 30px;font-size: 14px;color: white;background-color: #0066cc;border: none;border-radius: 10px;outline: none;margin-top: 20px;cursor: pointer">提交</button></div>'
            });
        } else {
            layer.msg('登录后才能咨询');
            setTimeout(function () {
                window.open('/f/login.html?pc=true', '_self');
            }, 1000);
        }
    })
}

// 在线咨询提交按钮点击事件
function expertOfAskSubmitBtnClick() {
    $(document).on('click', '.expertAskBtnSubmit', function () {
        var json = {
            expertsId: expertsData.id,
            contents: filterSensitiveWord($('.expertAskBtnContents textarea').val())
        }
        if (json.contents == '') {
            layer.msg("咨询内容不能为空！")
        } else {
            $('.expertAskBtnSubmit').attr('disabled', true);
            new NewAjax({
                url: "/f/expertsConsulting/pc/create_update?pc=true",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("咨询成功，请前往个人中心查看")
                        setTimeout(function () {
                            layer.closeAll();
                        }, 1000)
                    } else {
                        layer.msg("咨询不成功，请重新再试!")
                    }
                }
            })
        }
    })
}

function toPeopelCenterExprets() {
    $('.expert-wisdom-lib-detail-main-right .first-botton').click(function () {
        var href = $(this).attr('data-href');
        window.location.href = '/f/personal_center.html?pc=true&menu=expertPersonalInfo'
    })
}
