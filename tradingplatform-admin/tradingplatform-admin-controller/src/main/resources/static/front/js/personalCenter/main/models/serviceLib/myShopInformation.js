/**
 * Created by 空 on 2018/11/19.
 */
var currentPageOfMyShopInformationList = 1;
var searchSizeOfMyShopInformationList = 10;
var MyShopInformationListTypeId;
var pageInOrTabMyShopInformation = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var myShopInformationListCaseId;
var myShopInformationArr = [];

/** 回复的分页 **/
// hrz
var currentPageMyShopInformationReply = 1;
var searchSizeMyShopInformationReply = 5;

// 搜索条件对象
var selectConditionOfMyShopInformationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".my-shop-information-splitpage>div").on("click", function () {
        console.log(11);
        console.log($('.my-shop-information-splitpage .focus')[0].innerText);
        if ($('.my-shop-information-splitpage .focus')[0].innerText != currentPageOfMyShopInformationList) {
            currentPageOfMyShopInformationList = $('.my-shop-information-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMyShopInformation = 0;
            getMyShopInformationList();
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            currentPageOfMyShopInformationList = $('.my-shop-information-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMyShopInformation = 0;
            getMyShopInformationList();
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".my-shop-information-splitpage >div").on("click", function () {
        console.log($('.my-shop-information-splitpage .focus')[1].innerText);
        if ($('.my-shop-information-splitpage .focus')[1].innerText != currentPageMyShopInformationReply) {
            currentPageMyShopInformationReply = $('.my-shop-information-splitpage .focus')[1].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabMyShopInformation = 0;
            // myShopInformationhandleClick();
            $('.my-shop-information .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyShopInformationSecond();
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            currentPageMyShopInformationReply = $('.my-shop-information-splitpage .focus')[1].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabMyShopInformation = 0;
            // myShopInformationhandleClick();
            $('.my-shop-information .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyShopInformationSecond();
        }
    });
}

/*** 复原currentPage ***/
function resetCurrentPageMyShopInformation() {
    $('.my-shop-information-splitpage >div').data('currentpage', 1);
    currentPageOfMyShopInformationList = 1;
    $('.my-shop-information-splitpage >div').find('li[data-page="' + currentPageOfMyShopInformationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var myShopInformationListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('my-shop-information')
    publishDemandListSearch();
});

function init_dom() {
    $('.my-shop-information .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.my-shop-information .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.my-shop-information .personal-center-search-list ul li').click(function () {
        $('.my-shop-information').find('ul li').removeClass('active')
        $(this).addClass('active')
        MyShopInformationListTypeId = $(this).attr('data-typeId')

        pageInOrTabMyShopInformation = 1
        currentPageOfMyShopInformationList = 1

        getMyShopInformationList()
    })
}

function publishDemandListSearch() {
    $('.my-shop-information').find('.searchByTime').click(function () {
        selectConditionOfMyShopInformationList.searchVal = $('.my-shop-information').find('.searchByNameContent').val()
        selectConditionOfMyShopInformationList.startTime = $('.my-shop-information').find('.search-star-time').val()
        selectConditionOfMyShopInformationList.endTime = $('.my-shop-information').find('.search-end-time').val()

        pageInOrTabMyShopInformation = 1
        currentPageOfMyShopInformationList = 1

        getMyShopInformationList();
    })
    $('.my-shop-information').find('.searchByName').click(function () {
        selectConditionOfMyShopInformationList.searchVal = $('.my-shop-information').find('.searchByNameContent').val()

        pageInOrTabMyShopInformation = 1
        currentPageOfMyShopInformationList = 1

        getMyShopInformationList();
    })
}

// 获取报名的需求列表
function getMyShopInformationList() {
    var json = {
        "pager": {
            "current": currentPageOfMyShopInformationList,
            "size": searchSizeOfMyShopInformationList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    }
    if (MyShopInformationListTypeId) {
        json.reply = MyShopInformationListTypeId
    }
    if (selectConditionOfMyShopInformationList.searchVal) {
        json.title = selectConditionOfMyShopInformationList.searchVal;
    }
    if (selectConditionOfMyShopInformationList.startTime) {
        json.createdAtStart = selectConditionOfMyShopInformationList.startTime;
    }
    if (selectConditionOfMyShopInformationList.endTime) {
        json.createdAt = selectConditionOfMyShopInformationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/serviceProvidersConsulting/pc/query_provider_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabMyShopInformation === 0) {
                $('.my-shop-information-splitpage >div').Paging({
                    pagesize: searchSizeOfMyShopInformationList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.my-shop-information-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabMyShopInformation === 1) {
                $('.my-shop-information-splitpage >div').Paging({
                    pagesize: searchSizeOfMyShopInformationList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.my-shop-information-splitpage >div').find("div:eq(0)").remove();
            }
            $('.my-shop-information-total').html("共" + totalRecord + "条");

            myShopInformationListData = list;
            getMyShopInformationListData();
            myShopInformationhandleClick();
            if (list.length === 0) {
                $('.my-shop-information').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.my-shop-information').append(div)
                $('.my-shop-information-splitpage').css("display", "none")
                $('.my-shop-information-total').css("display", "none")
            } else {
                $('.my-shop-information').find('.noData').remove()
                $('.my-shop-information-splitpage').css("display", "block")
                $('.my-shop-information-total').css("display", "block")
            }
        }
    });
}

function getMyShopInformationListData() {
    var data = [];
    var table = new Table('my-shop-information-table');
    var baseStyleArr = [];
    var arr = [];
    if (myShopInformationListData != undefined && myShopInformationListData.length != 0) {
        myShopInformationListData.forEach(function (item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function (key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'provider':
                            styleItem.name = '店铺名称'
                            styleItem.width = 220
                            break
                        case 'user_id':
                            styleItem.name = '咨询人'
                            styleItem.width = 120
                            break
                        case 'contents':
                            styleItem.name = '咨询内容'
                            break
                        case 'created_at':
                            styleItem.name = '咨询时间'
                            styleItem.width = 120
                            break
                        case 'reply':
                            styleItem.name = '状态'
                            break
                        case 'id':
                            styleItem.name = '操作'
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.provider = [item.provider, item.provider_id]
            obj.user_id = JSON.parse(item.user_id).user_name
            obj.contents = item.contents
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.reply = item.reply
            // if (item.reply === true) {
            //     for (var i=0; i<baseStyleArr.length; i++) {
            //         if(baseStyleArr[i].type === 'id') {
            //             baseStyleArr.splice(i, 1)
            //         }
            //     }
            //     arr = ['title','consultant','contents','created_at','reply']
            // } else {
            if (!!item['respondent']) {
                obj.id = [item.id, item.pid, item.id, JSON.parse(item['respondent']).id]
            } else {
                obj.id = [item.id, item.pid, item.id]
            }
            arr = ['provider', 'user_id', 'contents', 'created_at', 'reply', 'id']
            // }
            data.push(obj)
        })
    }
    var orderArr = arr
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        if (type === 'reply') {
            var span
            if (content === true) {
                span = '<span>已回复</span>'
            } else {
                span = '<span>未回复</span>'
            }
            return (label === 'td') ? span : content
        } else if (type === 'id') {
            span = '<span class="reply-message" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>回复信息</span>'
            return (label === 'td') ? span : content
        } else if (type === 'provider') {
            var span = '<span title="' + content[0] + '" class="myShopInformationListToDetail" data-proId="' + content[1] + '" style="cursor: pointer;color: #0066cc">' + content[0] + '</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function myShopInformationhandleClick() {
    $(document).on('click', '.myShopInformationListToDetail', function () {
        var id = $(this).attr('data-proId')
        window.open('/f/' + id + '/provider_detail.html?pc=true', '_self')
    })

    $('.my-shop-information').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');
        myShopInformationArr = idArr;
        console.log(idArr);

        $('.my-shop-information div').removeClass('my-consultatio-show')
        $('.my-shop-information').children().eq(1).addClass('my-consultatio-show')
        var json = {
            "id": idArr[0],
            "pager": {
                "current": 1,
                "size": 5,
            },    //每页条数
            "sortPointer": {
                "filed": "created_at",
                "order": "DESC"
            }
        }

        new NewAjax({
            type: "POST",
            url: "/f/serviceProvidersConsulting/pc/query_provider_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // hrz
                currentPageMyShopInformationReply = 1;
                $('.my-shop-information-splitpage >div').find("div:eq(0)").remove();

                $('.my-shop-information .replyContentHistory>div').not(":first").remove()
                console.log(res);
                var userInfo = null
                var messages = res.data.data_list
                if (messages.length > 0) {
                    myShopInformationListCaseId = messages[0].provider_id;
                    var div2 = $('<div class="oneMessage"></div>')
                    var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                    var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[0].user_id).user_name + '</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[0].created_at)) + '</span>')
                    div.append(span)
                    div.append(span1)
                    div.append(span2)
                    div.append(span3)
                    var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[0].contents + '</p>')
                    div1.append(p)
                    div2.append(div)
                    div2.append(div1)
                    $('.my-shop-information .replyContentHistory').append(div2)
                    // $(".my-shop-information .replyContentHistory").append(div1)
                    getMyShopInformationSecond()
                }
            }
        })
    })
}

function getMyShopInformationSecond() {
    var obj = {
        'patentsId': myShopInformationListCaseId,
        "pid": myShopInformationArr[0],
        "pager": {
            "current": currentPageMyShopInformationReply,
            "size": searchSizeMyShopInformationReply,
        },    //每页条数
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    }
    new NewAjax({
        type: "POST",
        url: "/f/serviceProvidersConsulting/pc/query_provider_by_user_id_reply?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(obj),
        success: function (res) {
            console.log(3333);
            console.log(res);


            // hrz
            // if (pageInOrTabMyShopInformation === 0){
            // }
            // if (pageInOrTabMyShopInformation === 1){
            //     $('.my-shop-information-splitpage >div').Paging({pagesize:searchSizeOfMyShopInformationList,count:totalRecord,toolbar:true});
            // $('.my-shop-information-splitpage >div').find("div:eq(0)").remove();
            // }
            if (res.data.total == 0) {
                $('.my-shop-information-splitpage >div').empty()
            } else {
                var totalRecord = res.data.total;
                $('.my-shop-information-splitpage >div').Paging({
                    pagesize: searchSizeMyShopInformationReply,
                    count: totalRecord,
                    toolbar: true
                });
                $('.my-shop-information-splitpage >div').find("div:eq(1)").remove();
            }

            var messages = res.data.data_list
            var userInfo = window.localStorage.getItem("user")
            // console.log(userInfo);
            // console.log(JSON.parse(userInfo).id);
            for (var i = 0; i < messages.length; i++) {
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                if (JSON.parse(messages[i].user_id).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
                    var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                } else {
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                }
                div.append(span)
                div.append(span1)
                div.append(span2)
                div.append(span3)
                var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[i].contents + '</p>')
                div1.append(p)
                $('.my-shop-information .replyContentHistory').append(div)
                $(".my-shop-information .replyContentHistory").append(div1)
            }
            $('.my-shop-information').find('.replyContent-submit').off().click(function () {
                console.log('a');
                var content = $('.my-shop-information').find('.replyContent-textarea').val()
                var obj = {}
                if (!!myShopInformationArr[1]) {
                    obj.pid = parseInt(myShopInformationArr[1])
                } else {
                    obj.pid = parseInt(myShopInformationArr[2])
                }
                obj.providerId = myShopInformationListCaseId
                obj.contents = filterSensitiveWord(content)
                if (obj.contents == '') {
                    layer.msg("回复内容不能为空")
                } else {
                    $('.my-shop-information .replyContent-submit').attr('disabled', true);
                    new NewAjax({
                        type: "POST",
                        url: "/f/serviceProvidersConsulting/pc/create_update?pc=true",
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        data: JSON.stringify(obj),
                        success: function (res) {
                            if (res.status === 200) {
                                layer.msg("回复成功，即将返回页面")
                                $('.my-shop-information').find('.replyContent-textarea').val('');
                                setTimeout(function () {
                                    $('.my-shop-information .replyContent-submit').attr('disabled', false);
                                    $('.my-shop-information div').removeClass('my-consultatio-show')
                                    $('.my-shop-information').children().eq(0).addClass('my-consultatio-show')
                                    getMyShopInformationList();
                                }, 500)
                            }
                        }
                    })
                }
            })
        }
    })
}