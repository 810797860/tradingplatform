/**
 * Created by 空 on 2018/8/22.
 */
// 用户ID
var caseid;
var extend = false;
// 注入的数据
var dataSource = window.case_detailData;
var pNum;
var casePid;

// 大评论
var currentPageBig = 1;
var evaluationSearchSize = 5;
// 小回复
var currentPageSmall = 1;
var replySearchSize = 5;
var thisId;
var thisPid;
var demandHallDetailTableData;
var isSignUp;
var isAddconments = 'false';
var isFirst = true;
var isTiao = false;

var removePageItem = 1;//是否页内，0为否

// 推荐产品
var sRecommendResultItemHtml = '<li class="footer-recommend-item">\n' +
    '                        <a href="" class="footer-recommend-link" target="_blank">\n' +
    '                            <p class="footer-recommend-item-title text-overflow"></p>\n' +
    '                            <div class="footer-recommend-item-image-div">\n' +
    '                                <img src="" alt="" class="footer-recommend-item-image">\n' +
    '                                <div class="footer-recommend-item-money-div">\n' +
    '                                    <p class="footer-recommend-item-number">\n' +
    '                                        <i class="icon-eye"></i>\n' +
    '                                        <span class="footer-recommend-item-number-data"></span>\n' +
    '                                    </p>\n' +
    '                                    <p class="footer-recommend-item-money"></p>\n' +
    '                                </div>\n' +
    '                            </div>\n' +
    '                        </a>\n' +
    '                    </li>';

var sRecommendCompanyItemHtml = '<li class="footer-recommend-item">\n' +
    '                        <a href="" class="footer-recommend-link" target="_blank">\n' +
    '                            <div class="footer-recommend-item-image-div">\n' +
    '                                <img src="" alt="" class="footer-recommend-item-image">\n' +
    '                            </div>\n' +
    '                            <p class="footer-recommend-item-title text-overflow"></p>\n' +
    '                            <p class="footer-recommend-item-industry"></p>\n' +
    '                        </a>\n' +
    '                    </li>';

/*=== 留言 ===*/
// 留言按钮
var leaveMessageBtn = $('.leave-message-content-btn').eq(0);
// 获取留言插件
var leaveMessage = new LeaveMessage('callHallLeaveMessage');
// 获取留言数量节点
var leaveMessageNumberNode = $('.tab-desc-area .evaluate').eq(0);
// 记录评论（留言）每次请求的条数
var leaveMessageSearchSize = 5; // 评论
// 记录回复每次请求的条数
var replySearchSize = 5; // 回复
// 记录需要获取回复数据的总次数
var requireAllNumber = 0;
// 记录当前留言的回复被请求了多少次
var requireNumber = 0;
// 存储留言数据
var messageData = [];
// 存储回复数据
var replyData = [];
// 存储配置数据
var pageConfigData = [];
// 获取分页页码
var pageNumberData = [];
// 初始留言框
var firstLeaveMessageInput = leaveMessageBtn.next();


$(function () {
    initPage();
    applicantClick();
    // 设置超出提示
    setTextOverTip();
    eventOfBottomRecommend();
    getFooterRecommendResult();
    getFooterRecommendCompany();
});

function getMessage() {
    var userInfo = window.localStorage.getItem('user');
    $('.container-center').removeClass('div-avtive');
    $('.p-container-center').removeClass('div-avtive');
    $('.commons-container-center').addClass('div-avtive');
    var data = {
        "projectId": caseid,
        "pager": {
            "current": currentPageBig,
            "size": evaluationSearchSize
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "ASC"
        },
        "backCheckStatus": 202050
    };
    var postdata = JSON.stringify(data);
    new NewAjax({
        url: "/f/projectDemandComments/pc/query_one?pc=true",
        method: "POST",
        data: postdata,
        contentType: "application/json;charset=utf-8",
        success: function(data){
            $('.commons-count').text(data.data.total);
            if (data.data['data_list'] == null) {
                $('#illustration').text('该模块没有数据')
            }
            else{
                if(data.data['total'] == 0){
                    $(".main-evaluate").css("display", "none")
                } else {
                    $(".main-evaluate").css("display", "block")
                }
                $('.commons-container-center').empty();
                for(var i=0; i<data.data['data_list'].length; i++){
                    var respondent
                    if (!!data.data['data_list'][i].respondent) {
                        respondent = JSON.parse(data.data['data_list'][i].respondent).id
                    }
                    var user_name = JSON.parse(data.data['data_list'][i].user_id).user_name;
                    var created_at = data.data['data_list'][i].created_at;
                    var date = new Date(created_at);
                    var time = timestampToTime(date);
                    var div = $('<div class="commons-span"></div>');
                    var span  = $('<span>'+ user_name +'</span>');
                    var span1  = $('<span>'+ time +'</span>');
                    var span4 = $('<span style="margin-left: 20px;font-size: 12px;color: #0066cc;cursor: pointer" class="demand-hall-detail-delete-message" data-messageId="'+data.data['data_list'][i].id+'">删除</span>');
                    var div1 = $('<div class="commons-p"></div>');
                    var p = $('<p style="word-break: break-all">'+ data.data['data_list'][i].contents +'</p>');
                    var pid = JSON.parse(data.data['data_list'][i].project_id).id;
                    var user_name = JSON.parse(data.data['data_list'][i].user_id).user_name;
                    var projectId = JSON.parse(data.data['data_list'][i].project_id).id;
                    var span2 = $('<span class="commons-span-r" data-id="'+ data.data['data_list'][i].id +'" data-pid="'+pid+'" data-user="'+user_name+'" data-num="' + i + '" data-extend="no">查看评论</span>')
                    var span3 = $('<i data-id="'+ data.data['data_list'][i].id +'" data-pid="'+ data.data['data_list'][i].id +'" data-user="'+user_name+'" data-contents="'+ data.data['data_list'][i].contents +'" data-num="' + i + '" data-projectId="'+ projectId +'" data-respondent="'+ respondent +'"  data-respondent-name="'+ JSON.parse(data.data['data_list'][i].user_id).user_name +'" style="display: inline-block;width: 16px;height: 16px;color:#0088c0;cursor: pointer" class="icon-message"></i>')
                    div.append(span);
                    div.append(span1);
                    if (!!userInfo) {
                        var userData = JSON.parse(userInfo);
                        if (!!projectResultData &&　userData) {
                            if (!!projectResultData.publisher){
                                if (userData.id == JSON.parse(projectResultData.publisher).id) {
                                    div.append(span4);
                                }
                            }
                        }
                    }
                    if (!!userInfo) {
                        if (!!data.data['data_list'][i].user_id) {
                            var userData = JSON.parse(userInfo);
                            if(userData.id == JSON.parse(data.data['data_list'][i].user_id).id) {
                                div.append(span4);
                            }
                        }
                    }
                    div1.append(p)
                    div1.append(span3)
                    if (!!data.data['data_list'][i].if_father) {
                        div.append(span2)
                    }
                    $('.commons-container-center').append(div)
                    $('.commons-container-center').append(div1)
                }
                var div2 = $('<div class="commons-container-center-bottom" style="width: 100%; height: 40px;line-height: 40px;padding: 30px 0;text-align: center"></div>')
                var reply = $('<button class="commons-container-center-bottom-reply" style="height: 40px;width: 100px;line-height: 40px;font-size: 16px;background-color: #0066cc;color: white;border: none;border-radius: 10px;outline: none;cursor: pointer">回复</button>')
                var div3 = $('<div class="commons-container-center-reply-contentsDiv" style="margin-top: 20px"></div>')
                var replyContents = $('<textarea class="commons-container-center-reply-contents" style="width: 100%;min-height: 250px;font-size: 14px;border: 1px solid #414141;box-sizing: border-box;padding: 20px;margin-bottom: 20px" placeholder="请输入你的留言"></textarea>')
                var submit = $('<button class="commons-container-center-reply-submit" style="width: 100px;height: 30px;background-color: #0066cc;color: white;border: none;outline: none;border-radius: 10px;margin-left: 300px;cursor: pointer">确定</button>')
                var cancel = $('<button class="commons-container-center-reply-cancel" style="width: 100px;height: 30px;background-color: #FF0000;color: white;border: none;outline: none;border-radius: 10px;margin-left: 20px;cursor: pointer">取消</button>')
                div2.append(reply)
                div3.append(replyContents)
                div3.append(submit)
                div3.append(cancel)
                div3.hide()
                $('.commons-container-center').append(div2)
                $('.commons-container-center').append(div3)

                //hrz
                var totalRecord = data.data.total;
                if (!!isFirst) {
                    $('.main-evaluate >div').Paging({pagesize: evaluationSearchSize, count: totalRecord, toolbar: true});
                } else if(isAddconments === 'true') {
                    $('.main-evaluate >div').Paging({pagesize: evaluationSearchSize, count: totalRecord, toolbar: true});
                    $('.main-evaluate >div').find("div:eq(0)").remove();
                } else if (!!isTiao) {
                    $('.main-evaluate >div').Paging({pagesize: evaluationSearchSize, count: totalRecord, toolbar: true});
                    $('.main-evaluate >div').find("div:eq(1)").remove();
                } else {
                    $('.main-evaluate >div').Paging({pagesize: evaluationSearchSize, count: totalRecord, toolbar: true});
                    $('.main-evaluate >div').find("div:eq(0)").remove();
                }
                isFirst = false;
                isAddconments = 'false';
                isTiao = false;
            }
        }
    })
}

function applicantClick() {
    $("#applicant").click(function () {
        //hrz
        $(".main-evaluate").hide();

        $('.container-center').addClass('div-avtive')
        $('.p-container-center').removeClass('div-avtive')
        $('.commons-container-center').removeClass('div-avtive')
        extend = false
        var data = {
            "projectId": dataSource.id,
            "pager": {
                "current": 1,
                "size": 4
            },
            "sortPointer": {
                "filed": "created_at",
                "order": "DESC"
            }
        }
        var postdata = JSON.stringify(data)
        new NewAjax({
            url: "/f/projectDemandSignUp/pc/get_by_project_id",
            method: "POST",
            data: postdata,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(data){
                // var inList = false;
                // var userInfo = window.localStorage.getItem('user');
                $('.publish-count').text(data.data.total);
                // if (!!userInfo) {
                // var userId = JSON.parse(userInfo).id;
                if (data.data['data_list'].length == 0) {
                    $('.container-center').empty();
                    var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                    $('.container-center').append(div);
                }
                else{
                    $('.container-center').empty();
                    var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                    $('.container-center').append(div);
                    // if (userId == JSON.parse(projectResultData.publisher).id) {
                    //     inList = true;
                    // }
                    // for (var j = 0; j < data.data['data_list'].length; j++) {
                    //     if (userId == JSON.parse(data.data['data_list'][j].user_id).id) {
                    //         inList = true;
                    //     }
                    // }
                    // if (inList == false) {
                    //     return;
                    // }
                    for(var i=0; i<data.data['data_list'].length; i++) {
                        var imgid = JSON.parse(data.data['data_list'][i].user_id).avatar
                        var imgBank = $('.container-center');
                        if (!!data.data['data_list'][i].user_id){
                            var div = $('<div class="img" data-id="'+data.data['data_list'][i].id+'" data-user_id="'+ JSON.parse(data.data['data_list'][i].user_id).id +'"></div>')
                        }
                        var img
                        if (!!imgid) {
                            img = $('<img src="/adjuncts/file_download/' + imgid + '" alt="默认"/>')
                        } else {
                            img = $('<img style="background-color: white" src="/static/assets/defalutexpretTitle.png" />')
                        }
                        var span = $('<span>'+ JSON.parse(data.data['data_list'][i].user_id).user_name +'</span>')
                        div.append(img)
                        div.append(span)
                        imgBank.append(div)
                        var height = (data.data['data_list'].length/4) > 0 ? (data.data['data_list'].length/4) + 1 : (data.data['data_list'].length/4)
                        $('.container-center').height(height*153)
                    }
                    // var id = $(this).attr('data-id');
                    // var user_id = $(this).attr('data-user_id');
                    // if (!!projectResultData &&　userInfo) {
                    //     if (!!projectResultData.publisher) {
                    //     }
                    // }
                    // $('#illustration').text(data.data['data_list']['user_name'])

                }
                // } else {
                //     $('.container-center').empty();
                //     var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                //     $('.container-center').append(div);
                //     layer.msg('请先登录');
                //     setTimeout(function () {
                //         window.open('/f/login.html?pc=true','_self');
                //     },1000);
                // }
            }
        })
    });
}

function handleClick () {
    $(document).on('click','.commons-container-center-bottom-reply',function () {
        var user = window.localStorage.getItem('user');
        if (!!user) {
            $('.commons-container-center-bottom').hide()
            $('.commons-container-center-reply-contentsDiv').show()
        } else {
            layer.msg('请先登录');
            setTimeout(function () {
                window.open('/f/login.html?pc=true','_self');
            },500);
        }
    })
    $(document).on('click','.commons-container-center-reply-cancel',function () {
        $('.commons-container-center-bottom').show()
        $('.commons-container-center-reply-contentsDiv').hide()
    })
    $(document).on('click','.commons-container-center-reply-submit',function () {
        var content = $('.commons-container-center-reply-contents').val()
        if (!!content){
            var json = {
                projectId: caseid,
                contents: filterSensitiveWord(content)
            }
            new NewAjax({
                url: "/f/projectDemandComments/pc/create_update?pc=true",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        currentPageBig = 1;
                        layer.msg("留言成功");
                        isAddconments = 'true';
                        setTimeout(function () {
                            getMessage();
                        },500)
                    }
                }
            })
        } else {
            layer.msg('留言内容不能为空');
        }
    })
    $(".container-head>span").click(function () {
        // var i = $(this).index();
        $(this).addClass("span-active").siblings().removeClass('span-active');
    });

    $("#message").click(function () {
        //hrz
        currentPageBig = 1;
        $(".main-evaluate").show()
        $('.main-evaluate >div').find("div:eq(1)").remove();

        getMessage();
    });
    $("#demandDetails").click(function () {
        // hrz
        $(".main-evaluate").css("display", "none");

        $('.container-center').removeClass('div-avtive')
        $('.p-container-center').addClass('div-avtive')
        $('.commons-container-center').removeClass('div-avtive')
        extend = false
        new NewAjax({
            url: "/f/projectDemand/"+caseid+"/get_detail_by_id",
            method: "GET",
            contentType: "application/json;charset=utf-8",
            success: function(data){
                if (!!data.data['data_object']) {
                    $('#illustration').text(data.data['data_object']['illustration'])
                }
            }
        })
    });
    $('.file-activity-search').click(function (e) {
        var id = $(this).attr('data-fileid')
        if (!!id) {
            var img = $('<img src="/adjuncts/file_download/'+ id +'"/>')
        } else {
            var img = $('<img src="/static/assets/defalutexpretTitle.png" />')
        }
    });
    $('.file-activity-download').click(function () {
        $(this).attr('data-fileid')
    });
    $(document).on('click','.commons-span-r',function (e) {
        var id = $(this).data('id');
        var pid = $(this).data('pid');
        var username = $(this).data('user')
        var num = $(this).data('num')
        pNum = num
        thisId = id;
        thisPid = pid;
        var extend = false;
        if ($(this).attr('data-extend') == 'no') {
            extend = false;
        } else {
            extend = true;
        }
        if (extend == true) {
            $(this).parent().next().find('.chaldren-comment-head').remove();
            $(this).parent().next().find('.chaldren-comment-content').remove();
            $(this).text('查看评论');
            $(this).attr('data-extend', 'no');

            // hrz
            $(".main-reply").css("display","none");
        }
        else if (extend == false) {
            $('.commons-span-r').text('查看评论');
            $(this).text('收起评论')
            $('.chaldren-comment-head').hide();
            $('.chaldren-comment-content').hide();

            // hrz
            currentPageSmall = 1;
            $(this).parent().next().find(".main-reply").css("display","block");

            $('.commons-span-r').attr('data-extend', 'no');
            getDeandHallDetailSecond();
            $(this).attr('data-extend', 'yes');
            // extend = !extend;
        }
    })
    $(document).on('click','.commons-span .demand-hall-detail-delete-message',function () {
        var id = [$(this).attr('data-messageId')];
        new NewAjax({
            url: '/f/projectDemandComments/batch_delete?pc=true',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(id),
            success: function (res) {
                if (res.status === 200) {
                    currentPageBig = 1;
                    evaluationSearchSize = 5;
                    isAddconments = false;
                    // removePageItem = 0;
                    layer.msg("留言删除成功");
                    getMessage();
                } else {
                    layer.msg("留言删除失败");
                }
            }
        })
    })
    $(document).on('click','.chaldren-comment-head .demand-hall-detail-delete-message',function () {
        var id = [$(this).attr('data-messageId')];
        new NewAjax({
            url: '/f/projectDemandComments/batch_delete?pc=true',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(id),
            success: function (res) {
                if (res.status === 200) {
                    layer.msg("留言删除成功");
                    $('.commons-p>div').hide();
                    removePageItem = 0;
                    getDeandHallDetailSecond();
                } else {
                    layer.msg("留言删除失败");
                }
            }
        })
    })
    $(document).on('click','.img',function (e){
        var id = $(this).attr('data-id');
        var user_id = $(this).attr('data-user_id');
        var userInfo = window.localStorage.getItem('user');
        if (!!projectResultData &&　userInfo) {
            if (!!projectResultData.publisher) {
                if (JSON.parse(userInfo).id == JSON.parse(projectResultData.publisher).id) {
                    window.open('/f/projectDemandSignUp/'+id+'/toDetail.html?pc=true', "_self");
                } else if (JSON.parse(userInfo).id == user_id){
                    window.open('/f/projectDemandSignUp/'+id+'/toDetail.html?pc=true', "_self");
                } else {
                    layer.msg("只有发包人与报名者自己才能进入");
                }
            }
        } else {
            layer.msg("只有发包人与报名者自己才能进入,请先登录");
        }
    })

    $(document).on('click','.commons-container-center .icon-message',function (e) {
        var user = window.localStorage.getItem('user');
        if (!!user) {
            $('.commons-p .extendMessageDiv').remove();
            var respondent = $(this).attr('data-respondent');
            var respondentName = $(this).attr('data-respondent-name');
            var projectId = $(this).attr('data-projectId');
            var pid = $(this).attr('data-pid');
            casePid = pid;
            thisId = pid;
            var getContents = $(this).attr('data-contents');
            var id = $(this).attr('data-id');
            var userName = $(this).attr('data-user');
            var userId = $(this).attr('data-user-id');
            if (!!$(this).attr('data-num')) {
                var num = $(this).attr('data-num');
            } else {
                var num = ($(this).parent().parent('.commons-p').index()+1)/2 - 1;
            }
            var span = $('<span style="display: inline-block;font-size: 14px">'+ respondentName +'</span>')
            var p = $('<p style="display: inline-block;font-size: 14px;word-break: break-all">'+ getContents +'</p>')
            var head = $('<div style="margin-top: 10px;border-top: 1px solid gainsboro;padding: 0 60px"></div>')
            head.append(span)
            head.append(p)
            var span1 = $('<span style="font-size: 14px;display: inline-block;vertical-align: top">回复:</span>')
            var textare = $('<textarea style="font-size: 14px;display: inline-block; margin-left: 20px;width: 610px;min-height: 200px;border: 1px solid #f4f4f4" class="getContents"></textarea>')
            var contentsDiv = $('<div style="padding: 0 60px 10px 60px;position: relative"></div>')
            var submit = $('<button class="extendMessageSubmit" style="width: 80px;height: 30px;line-height: 30px;position: absolute;right:200px;background-color: #0066cc;color: white;outline: none;border: none;border-radius: 10px">确定</button>')
            var cancel = $('<button class="extendMessageCancel" style="width: 80px;height: 30px;line-height: 30px;position: absolute;right:90px;background-color: #FF0000;color: white;outline: none;border: none;border-radius: 10px">取消</button>')
            var buttonDiv = $('<div style="height: 60px;line-height: 30px;position: relative;padding-bottom: 30px"></div>')
            contentsDiv.append(span1)
            contentsDiv.append(textare)
            buttonDiv.append(submit)
            buttonDiv.append(cancel)
            var all = $('<div class="extendMessageDiv"></div>')
            all.append(head)
            all.append(contentsDiv)
            all.append(buttonDiv)
            $('.commons-p').eq(num).append(all)
        } else {
            layer.msg('请先登录');
            setTimeout(function () {
                window.open('/f/login.html?pc=true','_self');
            },500);
        }
    })
    $(document).on('click','.commons-p .extendMessageDiv .extendMessageCancel',function (e) {
        $('.commons-p .extendMessageDiv').remove()
    })
    $(document).on('click','.commons-p .extendMessageDiv .extendMessageSubmit',function (e) {
        if (!!$('.commons-p .getContents').val()) {
            var json ={
                projectId: caseid,
                contents: $('.commons-p .getContents').val(),
                pid: casePid
            }
            new NewAjax({
                url: "/f/projectDemandComments/pc/create_update?pc=true",
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(json),
                success:function (res) {
                    if (res.status === 200) {
                        layer.msg("评论成功")
                        $('.commons-p .extendMessageDiv').remove();
                        // $(".main-evaluate").append($(".main-reply"))
                        $('.commons-p .chaldren-comment-head').remove();
                        $('.commons-p .chaldren-comment-content').remove()
                        removePageItem = 0;
                        getDeandHallDetailSecond();
                    } else {
                        layer.msg("评论失败")
                    }
                }
            })
        } else {
            layer.msg("评论内容不能为空")
        }
    })
    // $(".container .container-center").find(".img").click(function () {
    //     let id = $(this).attr('data-id')
    //     let url = `/f/projectDemandSignUp/${id}/toDetail.html?pc=true`
    //     new NewAjax({
    //         url: url,
    //         method: "GET",
    //         dataType: "json",
    //         contentType: "application/json;charset=utf-8",
    //         success(data){
    //         }
    //     })
    // })
    // 点击收藏按钮：收藏和取消收藏
    $(".collect-area").off().on("click", function () {
        if ($(this).children("i").hasClass("icon-star-void")) {
            toCollectTheDemand($(this), dataSource.id, true);
        } else {
            toCollectTheDemand($(this), dataSource.id, false);
        }
    });

    /*** 跳转到详情页 ***/
    $(".recommend-demand-card .recommend-demand-title").off().on("click", function () {
        var id = $(this).parents(".recommend-demand-card").data('id');
        // window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
        window.open('/f/' + id + '/demand_detail.html?pc=true');
    });

    /*** 监听分页跳转 ***/
    // 评论大分页
    $(".main-evaluate >div").off().on("click", function(){
        if($('.main-evaluate .focus')[0].innerText != currentPageBig) {
            currentPageBig = $('.main-evaluate .focus')[0].innerText;
            isTiao = true;

            // hrz
            $(".main-reply").css("display","none");
            $(".main-evaluate").append($(".main-reply"))
            getMessage();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageBig = $('.main-evaluate .focus')[0].innerText;
            isTiao = true;
            // beforeclickEvaluation = 0;// hrz
            // 清楚评价的渲染
            // $('.main-evaluate').append($(".main-reply"));
            // $(".evaluate-info").remove();
            // $(".main-reply").css("display", "none");
            getMessage();
        }
    });
    // 评论小分页
    $(".main-reply >div").off().on("click", function(){
        if($('.main-reply .focus')[0].innerText != currentPageSmall) {
            currentPageSmall = $('.main-reply .focus')[0].innerText;
            $(".main-evaluate").append($(".main-reply"));
            $('.commons-p>div').remove();
            removePageItem = 1;
            getDeandHallDetailSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageSmall = $('.main-reply .focus')[0].innerText;
            $(".main-evaluate").append($(".main-reply"));
            $('.commons-p>div').remove();

            removePageItem = 1;
            getDeandHallDetailSecond();
        }
    });

};

function getDeandHallDetailSecond() {
        var userInfo = window.localStorage.getItem('user');
        var data = {
            "pid": thisId,
            "projectId": caseid,
            "pager":{
                "current":currentPageSmall,
                "size":replySearchSize
            },
            "sortPointer": {
                "filed": "created_at",
                "order": "DESC"
            },
            "backCheckStatus": 202050,
        }
        var postdata = JSON.stringify(data)
        new NewAjax({
            url: "/f/projectDemandComments/pc/query?pc=true",
            method: "POST",
            data: postdata,
            contentType: "application/json;charset=utf-8",
            success: function(data){
                var totalRecord = data.data.total;
                if($('.main-reply >div >div').length == 0 || removePageItem == 1){
                    $('.main-reply>div').Paging({pagesize: replySearchSize, count: totalRecord, toolbar: true});
                    // $('.main-reply >div').find("div:eq(1)").remove();
                    $('.main-reply>div>div:eq(1)').remove();
                    removePageItem = 0;
                } else {
                    $('.main-reply >div').Paging({pagesize: replySearchSize, count: totalRecord, toolbar: true});
                    $('.main-reply>div>div:eq(0)').remove();
                    $('.main-reply').show();
                }

                // if(removePageItem == 1){


                // 切换大分页后清除
                // $('.main-reply >div:eq(1)').remove();

                // clickEvaluation = 1; // 评论展开已经点击
                for(var i=0; i<data.data['data_list'].length; i++){
                    var projectId = caseid
                    var time = timestampToTime(data.data['data_list'][i].created_at)
                    var div = $('<div class="chaldren-comment-head"></div>')
                    var div1 = $('<div class="chaldren-comment-content"></div>')
                    var name = JSON.parse(data.data['data_list'][i].user_id).user_name
                    var span = $('<span class="com-name">'+ JSON.parse(data.data['data_list'][i].respondent).user_name +'</span>')
                    var span1 = $('<span class="call">回复</span>')
                    var span2= $('<span class="user-name">'+ name +'</span>')
                    var span3 = $('<span class="time">'+ time + '</span>')
                    var span5 = $('<span style="margin-left: 20px;font-size: 12px;color: #0066cc;cursor: pointer" data-messageId="'+ data.data['data_list'][i].id +'" class="demand-hall-detail-delete-message">删除</span>');
                    var p = $('<p style="word-break: break-all">'+ data.data['data_list'][i].contents +'</p>')
                    var span4 = $('<i data-id="'+ data.data['data_list'][i].id +'" data-pid="'+data.data['data_list'][i].id+'" data-user="'+JSON.parse(data.data['data_list'][i].respondent).user_name+'" data-user-id="'+JSON.parse(data.data['data_list'][i].user_id).id+'" data-contents="'+ data.data['data_list'][i].contents +'" data-projectId="'+ projectId +'" data-respondent="'+ JSON.parse(data.data['data_list'][i].user_id).id +'" data-respondent-name="'+ JSON.parse(data.data['data_list'][i].user_id).user_name +'" style="display: inline-block;width: 16px;height: 16px;color:#0088c0;cursor: pointer" class="icon-message"></i>')
                    // var span4 = $('<i class="icon-message"></i>')
                    div.append(span2);
                    div.append(span1)
                    div.append(span)
                    div.append(span3)
                    if (!!userInfo) {
                        var userData = JSON.parse(userInfo);
                        if (!!projectResultData &&　userData) {
                            if (!!projectResultData.publisher){
                                if (userData.id == JSON.parse(projectResultData.publisher).id) {
                                    div.append(span5);
                                }
                            }
                        }
                    }
                    if (!!userInfo) {
                        if (!!data.data['data_list'][i].user_id) {
                            var userData = JSON.parse(userInfo);
                            if(userData.id == JSON.parse(data.data['data_list'][i].user_id).id) {
                                div.append(span5);
                            }
                        }
                    }
                    div1.append(p);
                    div1.append(span4);
                    $('.commons-p').eq(pNum).append(div);
                    $('.commons-p').eq(pNum).append(div1);
                    $('.commons-p').eq(pNum).append($(".main-reply"));
                }
            }
        })
}

function initPage() {
    var href = window.location.href.split('&')[1];
    var oIconNode = $('.verification').eq(0);
    // console.log(href);
    if (!!projectResultData.publisher) {
        var publisher = JSON.parse(projectResultData.publisher);
        // console.log(publisher);
        if (!publisher.email) {
            $('.icon-lianxi-youxiang').removeClass('lianxi-active');
        } else {
            $('.icon-lianxi-youxiang').addClass('lianxi-active');
        }
        if (!publisher.phone) {
            $('.icon-shouji').removeClass('lianxi-active');
        } else {
            $('.icon-shouji').addClass('lianxi-active');
        }
        if (verification.id == '202050') {
            console.log('有验证');
            oIconNode.removeClass('icon-shimingrenzheng1');
            if (publisher.verification_type == '202137') {
                oIconNode.addClass('icon-qiyerenzheng');
            } else if (publisher.verification_type == '202136') {
                oIconNode.addClass('icon-tuanduirenzheng');
            } else {
                oIconNode.addClass('icon-shimingrenzheng');
            }
        } else {
            oIconNode.removeClass('icon-qiyerenzheng');
            oIconNode.removeClass('icon-tuanduirenzheng');
            oIconNode.removeClass('icon-shimingrenzheng');
            oIconNode.addClass('icon-shimingrenzheng1');
        }
    }

    if (!!dataSource) {
        if (!!dataSource.attachment) {
            demandHallDetailTableData = JSON.parse(dataSource.attachment)
        }
    }
    if (!!href) {
        $('.container-head>span').removeClass('span-active');
        $('#applicant').addClass('span-active');
        $(".main-evaluate").hide();

        $('.container-center').addClass('div-avtive')
        $('.p-container-center').removeClass('div-avtive')
        $('.commons-container-center').removeClass('div-avtive')
        extend = false
        var data = {
            "projectId": dataSource.id,
            "pager": {
                "current": 1,
                "size": 4
            },
            "sortPointer": {
                "filed": "created_at",
                "order": "DESC"
            }
        }
        var postdata = JSON.stringify(data)
        new NewAjax({
            url: "/f/projectDemandSignUp/pc/get_by_project_id",
            method: "POST",
            data: postdata,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function(data){
                // var inList = false;
                // var userInfo = window.localStorage.getItem('user');
                $('.publish-count').text(data.data.total);
                // if (!!userInfo) {
                // var userId = JSON.parse(userInfo).id;
                if (data.data['data_list'].length == 0) {
                    $('.container-center').empty();
                    var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                    $('.container-center').append(div);
                }
                else{
                    $('.container-center').empty();
                    var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                    $('.container-center').append(div);
                    // if (userId == JSON.parse(projectResultData.publisher).id) {
                    //     inList = true;
                    // }
                    // for (var j = 0; j < data.data['data_list'].length; j++) {
                    //     if (userId == JSON.parse(data.data['data_list'][j].user_id).id) {
                    //         inList = true;
                    //     }
                    // }
                    // if (inList == false) {
                    //     return;
                    // }
                    for(var i=0; i<data.data['data_list'].length; i++) {
                        var imgid = JSON.parse(data.data['data_list'][i].user_id).avatar
                        var imgBank = $('.container-center');
                        if (!!data.data['data_list'][i].user_id){
                            var div = $('<div class="img" data-id="'+data.data['data_list'][i].id+'" data-user_id="'+ JSON.parse(data.data['data_list'][i].user_id).id +'"></div>')
                        }
                        var img
                        if (!!imgid) {
                            img = $('<img src="/adjuncts/file_download/' + imgid + '" alt="默认"/>')
                        } else {
                            img = $('<img style="background-color: white" src="/static/assets/defalutexpretTitle.png" />')
                        }
                        var span = $('<span>'+ JSON.parse(data.data['data_list'][i].user_id).user_name +'</span>')
                        div.append(img)
                        div.append(span)
                        imgBank.append(div)
                        var height = (data.data['data_list'].length/4) > 0 ? (data.data['data_list'].length/4) + 1 : (data.data['data_list'].length/4)
                        $('.container-center').height(height*153)
                    }
                    // var id = $(this).attr('data-id');
                    // var user_id = $(this).attr('data-user_id');
                    // if (!!projectResultData &&　userInfo) {
                    //     if (!!projectResultData.publisher) {
                    //     }
                    // }
                    // $('#illustration').text(data.data['data_list']['user_name'])

                }
                // } else {
                //     $('.container-center').empty();
                //     var div = $('<div style="display: block;font-size: 12px;color: #999;height: 30px;line-height: 0px;text-align: left;font-weight: 600">tips:仅报名者与发布人可查看报名者信息</div>')
                //     $('.container-center').append(div);
                //     layer.msg('请先登录');
                //     setTimeout(function () {
                //         window.open('/f/login.html?pc=true','_self');
                //     },1000);
                // }
            }
        })
    }
    initData();
    handleClick();
}

function initData() {
    var userInfo = (window.localStorage.getItem('user') !== undefined) ? JSON.parse(window.localStorage.getItem('user')) : undefined;
    var publisher = $('#publisher');
    var statusNode = $('#status');
    var status = null;
    $('.demand-hall .content-left .sign-up button').click(function () {
        window.open('/f/projectDemandSignUp/'+ dataSource.id +'/demand_sign_up.html?pc=true', '_self')
    })
    if (!!userInfo) {
        var url = "/f/projectDemand/pc/" + dataSource.id + "/judgeHasSignUp?pc=true"
            new NewAjax({
            url: url,
            method: "GET",
            contentType: "application/json;charset=utf-8",
            success: function (res) {
                if (!!res.data){
                    if (!!res.data.data_object){
                        if (res.data.data_object === true) {
                            isSignUp = true
                            $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9');
                            $('.demand-hall .content-left .sign-up button').attr('disabled',true);
                            $('.demand-hall .content-left .sign-up button').text("已报名");
                            demandHallDetailTable();
                        }
                    } else {
                        isSignUp = false
                        demandHallDetailTable();
                    }
                }
            }
        })
    } else {
        isSignUp = false
        demandHallDetailTable();
    }
    getRecommendDemandList();
    getNewRecommendDemandList();
    publisher.text(publisher.data("publisher") ? publisher.data("publisher").user_name : '无');
    var deadline = $('#deadline').attr('data-deadline').split(' ')[0];
    $("#deadline").text(deadline);
    if (statusNode.data("status") !== undefined && statusNode.data("status") !== null) {
        status = statusNode.data("status")
    }
    var budget_amount_start = $('#budget_amount_start').attr('data-budget_amount_start')
    var budget_amount = $('#budget_amount').attr('data-budget_amount')
    if (budget_amount > 0 && budget_amount_start == budget_amount){
        $('#budget_amount').text(budget_amount);
        $('.money-line').css("display","none");
        $('#budget_amount_start').css("display","none");
    }else if(budget_amount > 0 && budget_amount_start != budget_amount){
        $('#budget_amount_start').text(budget_amount_start);
        $('#budget_amount').text(budget_amount);
    } else{
        $('#budget_amount').text("面议");
        $('.money-unit').css("display","none");
        $('.money-line').css("display","none");
        $('#budget_amount_start').css("display","none");
    }

    if (!!userInfo) {
        if(!!projectResultData.publisher && (userInfo.id == JSON.parse(projectResultData.publisher).id)) {
            $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9');
            $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed');
            $('.demand-hall .content-left .sign-up button').attr('disabled',true);
            $('.demand-hall .content-left .sign-up button').text("马上报名");
        }
    }

    var illustration = $('#illustration').attr("data-illustration");
    if (!!illustration){
        $('#illustration').html(illustration);
    }
    if (status.id == '202074') {
        $(".ui-step li").removeClass("step-done");
        for(var i=0; i<5; i++) {
            $(".ui-step li").eq(i).addClass("step-done");
        }
        $('.ui-step li').eq(4).addClass("step-active").siblings().removeClass('step-active');
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("报名已截止")
    } else if (status.id == '202073') {
        $(".ui-step li").removeClass("step-done");
        for(var i=0; i<4; i++) {
            $(".ui-step li").eq(i).addClass("step-done");
        }
        $('.ui-step li').eq(3).addClass("step-active").siblings().removeClass('step-active');
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("报名已截止")
    } else if (status.id == '202072') {
        $(".ui-step li").removeClass("step-done");
        for(var i=0; i<3; i++) {
            $(".ui-step li").eq(i).addClass("step-done");
        }
        $('.ui-step li').eq(2).addClass("step-active").siblings().removeClass('step-active');
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("报名已截止")
    } else if (status.id == '202071') {
        $(".ui-step li").removeClass("step-done");
        for(var i=0; i<2; i++) {
            $(".ui-step li").eq(i).addClass("step-done");
        }
        $('.ui-step li').eq(1).addClass("step-active").siblings().removeClass('step-active');
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("报名已截止")
    } else if (status.id == '202069') {
        $(".ui-step li").removeClass("step-done");
        for(var i=0; i<1; i++) {
            $(".ui-step li").eq(i).addClass("step-done");
        }
        $('.ui-step li').eq(0).addClass("step-active").siblings().removeClass('step-active');
    }
    else if (status.id == '202075') {
        $(".ui-step li").removeClass("step-done");
        $(".ui-step li").removeClass("step-active");
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("报名已截止")
    } else {
        $(".ui-step li").removeClass("step-done");
        $(".ui-step li").removeClass("step-active");
        $('.demand-hall .content-left .sign-up button').css('backgroundColor','#b9b9b9')
        $('.demand-hall .content-left .sign-up button').css('cursor','not-allowed')
        $('.demand-hall .content-left .sign-up button').attr('disabled',true)
        $('.demand-hall .content-left .sign-up button').text("马上报名")
    }

    var id = $('#applicant').attr('data-id');
    caseid = id;

    var attachment = $("#attachment").data("attachment")
    var table = $('.file-table');
    if (!!attachment){
        for(var i=0; i<attachment.length; i++) {
            var td = $('<td class="file-index">'+attachment[i].title+'.'+attachment[i].prefix+'</td>');
            var td1 = $('<td class="file-name">'+attachment[i].size+'k</td>');
            var td2 = $(' <td class="file-activity">' +
                '<a class="file-activity-search" data-fileid="'+attachment[i].id+'">' +
                '<i class="icon-search"></i>&nbsp;&nbsp;查看' +
                '</a>' +
                '<a class="file-activity-download" data-fileid="'+attachment[i].id+'">' +
                '<i class="icon-download"></i>&nbsp;&nbsp;下载' +
                '</a>' +
                '</td>');
            var tr = $('<tr><td class="file-index">'+(i+1)+'</td></tr>')
            tr.append(td)
            tr.append(td1)
            tr.append(td2)
            table.append(tr)
        }
    }
    // setfileInfo();
    // $(".ui-step-cont-number").click(function () {
    //     var li = $(this).parent().parent()
    //     var index = $(li).index()
    //     $(".ui-step li").eq(index).addClass("step-active").siblings().removeClass('step-active')
    //     $(".ui-step li").removeClass("step-done")
    //     for(var i=0; i<index; i++) {
    //         $(".ui-step li").eq(i).addClass("step-done")
    //     }
    // })
}

function timestampToTime (times) {
    var date = new Date(times)
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = date.getDate() + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes()
    return Y+M+D+h+m;
}

function getRecommendDemandList () {
    var json = {
        "pager":{//分页信息
            "current": 1,   //当前页数0
            "size": 9        //每页条数
        }
    }
    new NewAjax({
        type: "POST",
        url: "/f/projectDemand/query_recommend_demand",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setRecommendDemandList(list);
        }
    })
}

/**  设置右栏推荐需求列表  **/
function setRecommendDemandList (list) {
    var recommendDemandCards = $(".recommend-demand-card")
    // 隐藏不需要的元素
    for (var k = 0; k < recommendDemandCards.length; k++) {
        if (list.length < 9 && k >= list.length) {
            $(recommendDemandCards[k]).css("display", 'none')
        } else {
            $(recommendDemandCards[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(recommendDemandCards[i]).attr("data-id", list[i].id); // id
        $(recommendDemandCards[i]).find(".recommend-demand-title").html(list[i].name);  // 标题
        $(recommendDemandCards[i]).find(".recommend-demand-title").attr('title',list[i].name);  // 标题
        if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
            $(recommendDemandCards[i]).find(".recommend-demand-money").html("面议");     // 金额
        } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
            $(recommendDemandCards[i]).find(".recommend-demand-money").html(list[i].budget_amount + "万");     // 金额
        } else {
            $(recommendDemandCards[i]).find(".recommend-demand-money").html(list[i].budget_amount_start + "万" + '-' + list[i].budget_amount + '万');     // 金额
        }
        $(recommendDemandCards[i]).find(".recommend-demand-deadline").html(list[i].validdate);  // 截止时间
    }
}

function getNewRecommendDemandList () {
    var json = {
        "pager":{//分页信息
            "current": 1,   //当前页数0
            "size": 4        //每页条数
        }
    }
    $.ajax({
        type: "POST",
        url: "/f/projectDemand/query_latest_demand?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setNewRecommendDemandList(list);
        }
    })
}

function setNewRecommendDemandList (list) {
    var recommendDemandCards = $(".recommend-demand-card")
    // 隐藏不需要的元素
    for (var k = 4; k < recommendDemandCards.length; k++) {
        if (list.length < 8 && k >= list.length + 4) {
            $(recommendDemandCards[k]).css("display", 'none')
        } else {
            $(recommendDemandCards[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(recommendDemandCards[i+4]).attr("data-id", list[i].id); // id
        $(recommendDemandCards[i+4]).find(".recommend-demand-title").html(list[i].name);  // 标题
        $(recommendDemandCards[i+4]).find(".recommend-demand-title").attr('title',list[i].name);  // 标题
        if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
            $(recommendDemandCards[i+4]).find(".recommend-demand-money").html("面议");     // 金额
        } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
            $(recommendDemandCards[i+4]).find(".recommend-demand-money").html(list[i].budget_amount + "万");     // 金额
        } else {
            $(recommendDemandCards[i+4]).find(".recommend-demand-money").html(list[i].budget_amount_start + "万" + '-' + list[i].budget_amount + '万');     // 金额
        }
        $(recommendDemandCards[i+4]).find(".recommend-demand-deadline").html(list[i].validdate);  // 截止时间
    }
}


function demandHallDetailTable() {
    var windowUser = window.localStorage.getItem('user');
    var userId = null;
    if (!!windowUser && !!JSON.parse(windowUser).id) {
        userId = JSON.parse(windowUser).id
    }
    var isPublishUser = false;
    if (!!dataSource.publisher) {
        if (userId == JSON.parse(dataSource.publisher).id){
            isPublishUser = true;
        }
    }
    var data = [];
    var table = new Table('demand-hall-detail-table');
    var baseStyleArr = [];
    var arr = [];
    if (demandHallDetailTableData != undefined && demandHallDetailTableData.length != 0) {
        demandHallDetailTableData.forEach(function(item) {
            var obj = {};
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {};
                    styleItem.type = key;
                    switch (key) {
                        case 'title':
                            styleItem.name = '文件名称';
                            break;
                        case 'size':
                            styleItem.name = '文件大小/KB';
                            break;
                        case 'prefix':
                            styleItem.name = '文件格式';
                            break;
                        case 'id':
                            styleItem.name = '操作';
                            break;
                        // default:
                        //     styleItem.name = key
                        //     break
                    }
                    styleItem.align = 'left';
                    baseStyleArr.push(styleItem);
                })
            }
            obj.title = item.title;
            obj.prefix = item.prefix;
            obj.size = item.size;
            obj.id =item.id;
            // if (item.reply === true) {
            //     for (var i=0; i<baseStyleArr.length; i++) {
            //         if(baseStyleArr[i].type === 'id') {
            //             baseStyleArr.splice(i, 1)
            //         }
            //     }
            //     arr = ['consultor_name','contents','created_at','reply']
            // } else {
            arr =  ['title','size','prefix','id'];
            // }
            data.push(obj);
        })
    }
    var orderArr = arr;
    var spanArr = []
    table.setTableData(data);
    table.setBaseStyle(baseStyleArr);
    table.setColOrder(orderArr);
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        var span
        // if (type === 'size'){
        //     return (label === 'td') ? content + 'KB' : content;
        // }
        if (type === 'id') {
            if (isSignUp || isPublishUser) {
                spanArr = ['title', 'size', 'prefix', 'id'];
                span = '<a href="/adjuncts/file_download/' + content + '" class="detail-download" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>下载文件</a>';
            } else {
                span = '<span></span>'
                spanArr = ['title', 'size', 'prefix'];
            }
            return (label === 'td') ? span : content;
            // if (label === 'td') {
            //     return span
            // } else {
            //     return content
            // }
        }
    });
    if (isSignUp || isPublishUser){
        spanArr = ['title','size','prefix','id'];
    } else {
        spanArr = ['title','size','prefix'];
    }
    table.setShowColArr(spanArr);
    table.createTable();
}

/*** 用户收藏/ 取消收藏 ***/
function toCollectTheDemand (dom, id, isCollect) {
    var json = {
        "projectId": id,
        "isCollection": isCollect
    };
    new NewAjax({
        type: "POST",
        url: "/f/projectDemand/pc/collection_project_demand?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            // 403： 未登录
            if (res.status === 403) {
                window.location.href = "/f/login.html?pc=true";
            } else if (res.status === 200) {
                if (!isCollect) {
                    layer.msg("取消收藏成功！");
                    $(dom).children("i").attr("class", "icon-star-void");
                } else {
                    layer.msg("收藏成功！");
                    $(dom).children("i").attr("class", "icon-collect");
                }
                $(dom).children(".collect-num").html(res.data.total);
            }
        }
    })
}

// 设置文本超出提示
function setTextOverTip() {
    setTextOverTipOfRecommandDemandList();
    setTextOverTipOfRecommandResultList();
    setTextOverTipOfRecommandCompanyList();
}
// 绑定推荐需求父框事件
function setTextOverTipOfRecommandDemandList() {
    // 获取列表父框
    var nListParent = $('.content-right .recommend-card').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfRecommandDemandTextOver);
}
// 绑定推荐产品父框事件
function setTextOverTipOfRecommandResultList() {
    // 获取列表父框
    var nListParent = $('.footer-recommend-area .recommend-result-area').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfRecommandDemandTextOver);
}
// 绑定推荐企业父框事件
function setTextOverTipOfRecommandCompanyList() {
    // 获取列表父框
    var nListParent = $('.footer-recommend-area .recommend-company-area').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfRecommandDemandTextOver);
}

// 推荐需求文本超出事件
function eventOfRecommandDemandTextOver(event) {
    // 获取当前作用节点
    var nNowActive = null;
    // 节点标签名
    var nodeName = event.target.tagName.toLowerCase();
    if (nodeName === 'p' && $(event.target).hasClass('text-overflow')) {
        nNowActive = $(event.target);
    }
    if (nNowActive) {
        layer.closeAll();
        layer.tips(nNowActive.text(), nNowActive, {
            tips: [1, '#000000']
        });
    }
}




// 底部相关推荐事件
function eventOfBottomRecommend () {
    var aBottomRecommendNavItem = $(".footer-recommend-content-div .footer-recommend-nav-div .footer-recommend-nav-item");
    var oRecommendResultArea = $(".footer-recommend-content-div .recommend-result-area");
    var oRecommendCompanyArea = $(".footer-recommend-content-div .recommend-company-area");
    aBottomRecommendNavItem.on("click", function () {
        $(this).addClass("footer-recommend-nav-item_select").siblings().removeClass("footer-recommend-nav-item_select");
        if ($(this).attr('name') === 'result') {
            oRecommendCompanyArea.css("display", 'none');
            oRecommendResultArea.css("display", 'block');
        } else if ($(this).attr('name') === 'company') {
            oRecommendResultArea.css("display", 'none');
            oRecommendCompanyArea.css("display", 'block');
        }
    })
}

// 获取推荐产品数据
function getFooterRecommendResult () {
    var json = {
        "pager":{//分页信息
            "current": 1,   //当前页数0
            "size": 4       //每页条数
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/matureCase/get_recommend_mature_case?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setFooterRecommendResult(list);
        }
    })
}
function setFooterRecommendResult (list) {
    var newFrag = document.createDocumentFragment();
    var oRecommendResultArea = $(".footer-recommend-content-div .recommend-result-area");

    for (var i = 0; i < list.length; i++) {
        var oRecommendResultItem = $(sRecommendResultItemHtml);
        oRecommendResultItem.find(".footer-recommend-link").attr("href", '/f/' + list[i].id + '/case_detail.html');
        oRecommendResultItem.find(".footer-recommend-item-title").text(list[i].title);
        oRecommendResultItem.find(".footer-recommend-item-image").attr("src", list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(",")[0]) : null);
        oRecommendResultItem.find(".footer-recommend-item-money").text('￥' + (list[i].case_money ? list[i].case_money + '万元' : '面议'));
        oRecommendResultItem.find(".footer-recommend-item-number-data").text(list[i].click_rate);
        newFrag.append(oRecommendResultItem[0]);
    }
    oRecommendResultArea.append(newFrag);
}

// 获取推荐企业数据
function getFooterRecommendCompany () {
    var json = {
        "pager":{//分页信息
            "current": 1,   //当前页数0
            "size": 4        //每页条数
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/serviceProviders/get_recommend_providers?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setFooterRecommendCompany(list);
        }
    })
}
function setFooterRecommendCompany (list) {
    var newFrag = document.createDocumentFragment();
    var oRecommendCompanyArea = $(".footer-recommend-content-div .recommend-company-area");

    for (var i = 0; i < list.length; i++) {
        var oRecommendCompanyItem = $(sRecommendCompanyItemHtml);
        oRecommendCompanyItem.find(".footer-recommend-link").attr("href", '/f/' + list[i].id + '/provider_detail.html');
        oRecommendCompanyItem.find(".footer-recommend-item-title").text(list[i].name);
        oRecommendCompanyItem.find(".footer-recommend-item-image").attr("src", list[i].logo ? $(this).getAvatar(JSON.parse(list[i].logo)[0].id) : null);
        oRecommendCompanyItem.find(".footer-recommend-item-industry").text('行业：' + (list[i].industry_id ? JSON.parse(list[i].industry_id).title : '暂无数据'));
        newFrag.append(oRecommendCompanyItem[0]);
    }
    oRecommendCompanyArea.append(newFrag);
}

/** 留言 **/
// 初始化留言组件
function initPlusLeaveMessage() {
    // 若当前用户登录了,写入当前用户的id
    if (!!nowUserId) {
        leaveMessage.setNowUserId(nowUserId);
    }
    // 设置确认按钮点击回调
    leaveMessage.setTrueBtnCallBack(function (askInfo, node) {
        // 获取输入框
        var inputNode = node.parent().prev().find('textarea').eq(0);
        if (inputNode.val().length === 0){
            layer.msg('填写内容不能为空!');
            inputNode.css({
                borderColor: '#ff0000'
            });
            return 0;
        }
        //caseId: nowDemandId,
        var json = {
            payAttentionId: (askInfo.pid) ? askInfo.pid : askInfo.id,
            byReplyUserId: askInfo.user.id,
            content: node.parent().prev().find('textarea').eq(0).val()
        };
        new NewAjax({
            url: "/f/reply/create_update?pc=true",
            contentType: "application/json",
            type: "POST",
            data: JSON.stringify(json),
            success: function () {
                getLeaveMessage(function () {
                    // 设置数据
                    leaveMessage.setData(messageData)
                        .setReplyData(replyData)
                        .setPageConfig(pageConfigData)
                        .createLeaveMessage();
                })
            }
        })
    }).setDeleteCallBack(function (deleteInfo, node) {
        // 获取留言/回复id
        var messageId = (typeof deleteInfo.id === "string") ? deleteInfo.id : deleteInfo.id.toString();
        // 获取当前内容节点
        var nowContentNode = node.parent();
        // 删除请求
        var url = '';
        // 回传参数
        var data = {};
        // 是否为留言
        if (nowContentNode.hasClass('leave-message-content')) {
            url = '/f/payAttention/delete';
            data.payAttentionId = messageId;
        } else if (nowContentNode.hasClass('leave-message-reply-content')) {
            url = '/f/reply/delete';
            data.replyId = messageId;
        }
        // 删除请求
        new NewAjax({
            url: url,
            contentType: "application/json",
            type: "POST",
            data: JSON.stringify(data),
            success: function () {
                // 获取留言数量
                var messageNumber = leaveMessageNumberNode.data('number') - 1;
                nowPageNumber = (messageNumber > defaultPageSize) ? (messageNumber % defaultPageSize !== 0) ? Math.floor(messageNumber / defaultPageSize) + 1 : Math.floor(messageNumber / defaultPageSize) : 1;
                // 删除后重新获取留言
                getLeaveMessage(function () {
                    // 设置数据
                    leaveMessage.setData(messageData)
                        .setReplyData(replyData)
                        .setPageConfig(pageConfigData)
                        .createLeaveMessage();
                })
            }
        })
    });
}
// 留言请求
function getLeaveMessage(callback) {
    // 请求的传参
    var dataJson = {
        type: 202133,
        payId: nowDemandId,
        pager: {
            current: nowPageNumber,
            size: leaveMessageSearchSize
        },
        sortPointer:{
            order:"ASC",
            field:"updated_at"
        }
    };
    // 请求回复的次数
    requireNumber = 0;
    // 请求回复的总次数
    requireAllNumber = 0;
    // 请求留言
    new NewAjax({
        url: '/f/payAttention/query?pc=true',
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify(dataJson),
        success: function (res) {
            if (res.status === 200) {
                // 获取数据
                var messageArr = res.data.data_list;
                // 留言条数
                var messageNumber = res.data.total;
                // 修改tab中的留言数量
                leaveMessageNumberNode.text(messageNumber);
                leaveMessageNumberNode.data('number', messageNumber);
                // 清空留言数据
                messageData = [];
                // 清空回复
                replyData = [];
                // 清空分页配置
                pageConfigData = [];
                // 清空分页页码数据
                pageNumberData = [];
                // 若留言数量不为0
                if (messageArr.length > 0) {
                    // 计算留言页码数量
                    maxPageNumber = Math.ceil(messageNumber / defaultPageSize);
                    // 重新写入留言数量
                    messagePage.setMaxPageNumber(maxPageNumber)
                        .setNowPageNumber(nowPageNumber)
                        .createPage();
                    // 若未达到出现分页的情况，先隐藏分页
                    if (maxPageNumber < 2) {
                        messagePage.showPage(false);
                    } else {
                        messagePage.showPage(true);
                    }
                    // 记录请求回复的总次数
                    requireAllNumber = messageArr.length;
                    // 处理留言数据
                    dealWithMessageData(messageArr, callback);
                } else {
                    // 提示无数据
                    $('.leave-message-content-div .leave-message-div').eq(0).empty().append('<div style="width: 100%;height: 50px;line-height: 50px;text-align: center">暂无留言</div>');
                    // 隐藏分页
                    messagePage.showPage(false);
                }
            }
        },
        error: function (err) {
            console.error('留言：' + (err));
        }
    })
}
// 获取回复
function getReply(message, callback) {
    var dataJson = {
        payAttentionId: message.id,
        pager: {
            "current": (!!message.nowPageNumber) ? message.nowPageNumber: 1,
            "size": replySearchSize
        },
        sortPointer:{
            order:"ASC",
            field:"updated_at"
        }
    };
    new NewAjax({
        url: '/f/reply/sortQuery?pc=true',
        contentType: 'application/json',
        type: 'post',
        data: JSON.stringify(dataJson),
        success: function (res) {
            if (res.status === 200) {
                if (callback) {
                    callback(res.data.data_list, res.data.total);
                }
            }
        },
        error: function (err) {
            console.error('回复：' + (err));
        }
    })
}
// 处理留言数据
function dealWithMessageData(messageArr, callback) {
    // 暂时存储
    var shortTimeSave = null;
    // 初始化回复数据
    replyData = [];
    // 遍历留言
    messageArr.forEach(function (message, index) {
        // 提取留言数据
        var messageObj = {};
        // 获取mark
        var mark = 'message' + ((nowPageNumber - 1) * defaultPageSize + index);
        shortTimeSave = message.user_id ? JSON.parse(message.user_id) : {};
        // 记录留言
        messageObj.message = {};
        // 记录当前留言的id
        messageObj.message.id = message.id;
        messageObj.message.time = message.created_at;
        messageObj.message.content = message.content;
        // 记录留言者信息
        messageObj.message.user = {};
        messageObj.message.user.id = message.front_user_id_id;
        messageObj.message.user.name = message.front_user_id;
        // 记录回复
        messageObj.reply = [];
        // 存储数据
        messageData.push({
            mark: mark,
            data: messageObj
        });
        // 负责记录每个留言请求了哪页回复
        if (pageNumberData.searchArrayObj(mark, 'mark') === -1) {
            pageNumberData.push({
                mark: mark,
                number: defaultPageNumber
            })
        }
        // 存在回复
        if (message.isfather) {
            // 存储提问信息，存储回答信息
            // var askInfo, answerInfo;
            // 请求回复
            getReply(message, function (replyArr, replyTotal) {
                // 初始暂时存储
                var stSave = [];
                // 记录请求次数
                requireNumber += 1;
                // 遍历回复数据
                replyArr.forEach(function (reply) {
                    var replyObj = {};
                    // answerInfo = reply.user_id ? JSON.parse(reply.user_id) : {};
                    // askInfo = reply.respondent ? JSON.parse(reply.respondent) : {};
                    replyObj.pid = message.id;
                    replyObj.ask = {};
                    replyObj.ask.id = reply.by_reply_user_id;
                    replyObj.ask.name = reply.by_reply_nickname;
                    replyObj.answer = {};
                    replyObj.answer.id = reply.reply_user_id;
                    replyObj.answer.name = reply.reply_nickname;
                    // 敏感词过滤
                    replyObj.content = filterSensitiveWord(reply.content);
                    replyObj.time = reply.created_at;
                    replyObj.id = reply.id;
                    // messageObj.reply.push(replyObj);
                    stSave.push(replyObj);
                });
                replyData.push({
                    mark: mark,
                    data: stSave
                });
                getReplyPageConfigData(mark, replyTotal);
                // 当所有数据处理完成时，调用写入方法
                if (requireNumber === requireAllNumber && callback) {
                    callback();
                }
            })
        } else {
            replyData.push({
                mark: mark,
                data: []
            });
            requireNumber += 1;
            // 当所有数据处理完成时，调用写入方法
            if (requireNumber === requireAllNumber && callback) {
                callback();
            }
        }
    })
}
// 提取replyPageConfig
function getReplyPageConfigData(mark, total) {
    // 配置项是否存在
    var isHaveConfigItem = (pageConfigData.searchArrayObj(mark, 'mark', true) !== -1);
    // 获取配置数据
    var pageConfigItem = (isHaveConfigItem) ? pageConfigData.searchArrayObj(mark, 'mark', true).data : {};
    // 暂时存储
    var replyDataItem = null;
    // 获取message的id
    var message = {
        id: (messageData.searchArrayObj(mark, 'mark', true) !== -1) ? messageData.searchArrayObj(mark, 'mark', true).data.message.id : 0
    };
    // 提取分页配置数据
    pageConfigItem.isOpenPage = (total > defaultPageSize);
    pageConfigItem.maxPageNumber = (total > defaultPageSize) ? (total % defaultPageSize === 0) ? Math.floor(total/defaultPageSize) : Math.floor(total/defaultPageSize) + 1 : undefined;
    pageConfigItem.nowPageNumber = (total > defaultPageSize) ? pageNumberData.searchArrayObj(mark, 'mark', true).number: 1;
    if (pageConfigItem.clickCallback === undefined) {
        pageConfigItem.clickCallback = function (mark, newNumber) {
            var askInfo, answerInfo;
            // 获取当前页数
            message.nowPageNumber = newNumber;
            // 记录当前页数
            pageNumberData.searchArrayObj(mark, 'mark', true).number = newNumber;
            // 请求回复
            getReply(message, function (replyArr, replyTotal) {
                // 初始暂时存储
                var stSave = [];
                // 记录请求次数
                requireNumber += 1;
                // 遍历回复数据
                replyArr.forEach(function (reply) {
                    var replyObj = {};
                    answerInfo = reply.user_id ? JSON.parse(reply.user_id) : {};
                    askInfo = reply.respondent ? JSON.parse(reply.respondent) : {};
                    replyObj.ask = {};
                    replyObj.ask.id = askInfo.id;
                    replyObj.ask.name = askInfo.user_name;
                    replyObj.answer = {};
                    replyObj.answer.id = answerInfo.id;
                    replyObj.answer.name = answerInfo.user_name;
                    replyObj.content = filterSensitiveWord(reply.content);
                    replyObj.time = reply.created_at;
                    replyObj.id = reply.id;
                    // messageObj.reply.push(replyObj);
                    stSave.push(replyObj);
                });
                // 存储分页配置信息
                replyDataItem = replyData.searchArrayObj(mark, 'mark', true);
                if (replyDataItem === -1) {
                    replyData.push({
                        mark: mark,
                        data: stSave
                    });
                } else {
                    replyDataItem.data = stSave;
                }
                // 重新获取分页信息
                getReplyPageConfigData(mark, replyTotal);
                // 执行回复数据重写
                leaveMessage.setReplyData(replyData)
                    .setPageConfig(pageConfigData)
                    .createLeaveMessage();
            })
        }
    }
    // 若数据不存在，则插入数据
    if (!isHaveConfigItem) {
        pageConfigData.push({
            mark: mark,
            data: pageConfigItem
        });
    }
}
// 留言按钮点击事件
function eventOfLeaveMessageBtnClick() {
    leaveMessageBtn.off().click(function () {
        leaveMessageBtn.hide();
        firstLeaveMessageInput.show().find('textarea').eq(0).val('');
    })
}
// 留言确定按钮点击事件
function eventOfLeaveMessageTrueBtnClick() {
    $('.case-hall-detail-other-message-reply-submit').off().click(function () {
        var json = {
            payId: nowDemandId,
            type: 202133,
            content: filterSensitiveWord($('.case-hall-detail-other-message-reply-contents').val())
        };
        new NewAjax({
            url: "/f/payAttention/create_update?pc=true",
            contentType: "application/json",
            type: "POST",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    // 修改当前留言数量
                    getLeaveMessage(function () {
                        // 设置数据
                        leaveMessage.setData(messageData)
                            .setReplyData(replyData)
                            .setPageConfig(pageConfigData)
                            .createLeaveMessage();
                        // 隐藏编辑框
                        firstLeaveMessageInput.val('').hide();
                        // 展开留言按钮
                        leaveMessageBtn.show();
                    })
                }
            }
        })
    })
}
// 初始留言框的取消按钮事件
function eventOfLeaveMessageCancelBtnClick() {
    var cancelBtn = $('.case-hall-detail-other-message-reply-cancel').eq(0);
    cancelBtn.off().click(function () {
        firstLeaveMessageInput.hide();
        leaveMessageBtn.show();
    })
}