var currentPageOfDockResultList = 1;
var searchSizeOfDockResultList = 10;
var dockResultListTypeId;
var pageInOrTabDockResult = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var dockResultListCaseId;
var dockResultArr = [];

/** 回复的分页 **/
// hrz
var currentPageDockResultReply = 1;
var searchSizeDockResultReply = 5;

// 搜索条件对象
var selectConditionOfDockResultList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".dock-result-splitpage >div").on("click", function(){
        console.log(11);
        console.log($('.dock-result-splitpage .focus')[0].innerText);
        if($('.dock-result-splitpage .focus')[0].innerText != currentPageOfDockResultList) {
            currentPageOfDockResultList = $('.dock-result-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabDockResult = 0;
            getDockResultList();
        }
    }).keydown(function(e) {
            if (e.keyCode == 13) {
                    currentPageOfDockResultList = $('.dock-result-splitpage .focus')[0].innerText;

                    // getServiceList($_typeId, $("#search-input").val());
                    pageInOrTabDockResult = 0;
                    getDockResultList();
                }
        });

    /** 回复的分页 **/
    // hrz
    $(".dock-result-reply-splitpage >div").on("click", function(){
        console.log($('.dock-result-reply-splitpage .focus')[0].innerText);
        if($('.dock-result-reply-splitpage .focus')[0].innerText != currentPageDockResultReply) {
            currentPageDockResultReply = $('.dock-result-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.dock-result-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getDockResultSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageDockResultReply = $('.dock-result-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.dock-result-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getDockResultSecond();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageDockResult () {
    $('.dock-result-splitpage >div').data('currentpage', 1);
    currentPageOfDockResultList = 1;
    $('.dock-result-splitpage >div').find('li[data-page="' + currentPageOfDockResultList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var dockResultListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('dock-result-list')
    publishDemandListSearch();
});
function init_dom() {
    $('.dock-result-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.dock-result-list .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.dock-result-list .personal-center-search-list ul li').click(function () {
        $('.dock-result-list').find('ul li').removeClass('active')
        $(this).addClass('active')
        dockResultListTypeId = $(this).attr('data-typeId')

        pageInOrTabDockResult = 1
        currentPageOfDockResultList = 1

        getDockResultList()
    })
}

function publishDemandListSearch() {
    $('.dock-result-list').find('.searchByTime').click(function () {
        selectConditionOfDockResultList.searchVal = $('.dock-result-list').find('.searchByNameContent').val()
        selectConditionOfDockResultList.startTime = $('.dock-result-list').find('.search-star-time').val()
        selectConditionOfDockResultList.endTime = $('.dock-result-list').find('.search-end-time').val()

        pageInOrTabDockResult = 1
        currentPageOfDockResultList = 1

        getDockResultList();
    })
    $('.dock-result-list').find('.searchByName').click(function () {
        selectConditionOfDockResultList.searchVal = $('.dock-result-list').find('.searchByNameContent').val()

        pageInOrTabDockResult = 1
        currentPageOfDockResultList = 1

        getDockResultList();
    })
}

// 获取报名的需求列表
function getDockResultList () {
    var json = {
        "pager":{
            "current": currentPageOfDockResultList,
            "size": searchSizeOfDockResultList
        },
        "sortPointer":{
            "filed": "created_at",
            "order": "DESC"
        }
    }
    if (dockResultListTypeId) {
        json.reply = dockResultListTypeId
    }
    if (selectConditionOfDockResultList.searchVal) {
        json.title = selectConditionOfDockResultList.searchVal;
    }
    if (selectConditionOfDockResultList.startTime) {
        json.createdAtStart = selectConditionOfDockResultList.startTime;
    }
    if (selectConditionOfDockResultList.endTime) {
        json.createdAt = selectConditionOfDockResultList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseConsulting/pc/query_case_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabDockResult === 0){
                $('.dock-result-splitpage >div').Paging({pagesize:searchSizeOfDockResultList,count:totalRecord,toolbar:true});
                $('.dock-result-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabDockResult === 1){
                $('.dock-result-splitpage >div').Paging({pagesize:searchSizeOfDockResultList,count:totalRecord,toolbar:true});
                $('.dock-result-splitpage >div').find("div:eq(0)").remove();
            }
            $('.dock-result-total').html("共" + totalRecord + "条");

            dockResultListData = list;
            getDockResultListData();
            dockResulthandleClick();
            if (list.length === 0) {
                $('.dock-result-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.dock-result-list').append(div)
                $('.dock-result-splitpage').css("display", "none")
                $('.dock-result-total').css("display", "none")
            } else {
                $('.dock-result-list').find('.noData').remove()
                $('.dock-result-splitpage').css("display", "block")
                $('.dock-result-total').css("display", "block")
            }
        }
    });
}

function getDockResultListData() {
    var data = [];
    var table = new Table('dock-result-list-table');
    var baseStyleArr = [];
    var arr = [];
    if (dockResultListData != undefined && dockResultListData.length != 0) {
        dockResultListData.forEach(function(item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '案例名称'
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
            obj.title = [item.title,item.case_id]
            obj.user_id = JSON.parse(item.user_id).user_name
            obj.contents = item.contents
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.reply =item.reply
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
            arr = ['title','user_id','contents','created_at','reply','id']
            // }
            data.push(obj)
        })
    }
    var orderArr = arr
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label){
        if (type === 'reply') {
            var span
            if (content === true) {
                span = '<span>已回复</span>'
            } else {
                span = '<span>未回复</span>'
            }
            return (label === 'td') ? span : content
        } else if (type === 'id') {
            span = '<span class="reply-message" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>回复信息</span>'
            return (label === 'td') ? span : content
        } else if (type=== 'title') {
            var span = '<span title="'+content[0]+'" class="dockResultListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function dockResulthandleClick() {
    $(document).on('click','.dockResultListToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/case_detail.html?pc=true')
    })

    $('.dock-result-list').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');
        dockResultArr = idArr;

        $('.dock-result-list div').removeClass('my-consultatio-show')
        $('.dock-result-list').children().eq(1).addClass('my-consultatio-show')
        var json ={
            "id": idArr[0],
            "pager":{
            "current":1,
                "size":5,
            },    //每页条数
            "sortPointer":{
            "filed":"created_at",
                "order":"DESC"
            }
        }

        new NewAjax({
            type: "POST",
            url: "/f/matureCaseConsulting/pc/query_case_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // hrz
                currentPageDockResultReply = 1;
                $('.dock-result-reply-splitpage >div').find("div:eq(0)").remove();

                $('.dock-result-list .replyContentHistory>div').not(":first").remove()
                console.log(res);
                var userInfo = null
                var messages = res.data.data_list
                if (messages.length > 0) {
                    dockResultListCaseId = messages[0].case_id;
                    var div2 = $('<div class="oneMessage"></div>')
                    var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                    var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[0].user_id).user_name + '</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[0].created_at)) + '</span>')
                    div.append(span)
                    div.append(span1)
                    div.append(span2)
                    div.append(span3)
                    var p = $('<p style="font-size: 14px;word-break: break-all">'+ messages[0].contents + '</p>')
                    div1.append(p)
                    div2.append(div)
                    div2.append(div1)
                    $('.dock-result-list .replyContentHistory').append(div2)
                    // $(".dock-result-list .replyContentHistory").append(div1)
                    getDockResultSecond()
                }
            }
        })




        // var json = {
        //     "pager":{
        //         "current":1,
        //         "size":4
        //     },
        //     "sortPointer":{
        //         "filed":"created_at",
        //         "order":"ASC"
        //     }
        // }
        // if (!!$(this).attr('data-id')) {
        //     json.caseId = idArr[0]
        //     json.pid = parseInt(idArr[1])
        // }
        // console.log('a')
        // new NewAjax({
        //     type: "POST",
        //     url: "/f/matureCaseConsulting/pc/query_case_by_user_id_reply?pc=true",
        //     contentType: "application/json;charset=UTF-8",
        //     dataType: "json",
        //     data: JSON.stringify(json),
        //     success: function (res) {
        //         console.log(res);
        //         var userInfo = null
        //         var messages = res.data.data_list
        //         for (var i=0; i<messages.length; i++){
        //             var div = $('<div style="height: 40px;line-height: 40px"></div>')
        //             var div1 = $('<div style="height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
        //             var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].consultant).user_name + '</span>')
        //             var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
        //             var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
        //             var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
        //             div.append(span)
        //             div.append(span1)
        //             div.append(span2)
        //             div.append(span3)
        //             var p = $('<p style="font-size: 14px">'+ messages[i].contents + '</p>')
        //             div1.append(p)
        //             $('.dock-result-list .replyContentHistory').append(div)
        //             $(".dock-result-list .replyContentHistory").append(div1)
        //         }
        //         $('.dock-result-list').find('.replyContent-submit').click(function () {
        //             var content = $('.dock-result-list').find('.replyContent-textarea').val()
        //             var obj = {}
        //             if (!!idArr[1]) {
        //                 obj.pid = parseInt(idArr[1])
        //                 obj.id = parseInt(idArr[2])
        //             } else {
        //                 obj.id = parseInt(idArr[2])
        //                 obj.pid = parseInt(idArr[2])
        //             }
        //             obj.caseId = parseInt(idArr[0])
        //             obj.contents = content
        //             obj.respondent = parseInt(idArr[3])
        //             new NewAjax({
        //                 type: "POST",
        //                 url: "/f/matureCaseConsulting/pc/create_update?pc=true",
        //                 contentType: "application/json;charset=UTF-8",
        //                 dataType: "json",
        //                 data: JSON.stringify(obj),
        //                 success: function (res) {
        //                     if(res.status === 200){
        //                         $('.dock-result-list div').removeClass('my-consultatio-show')
        //                         $('.dock-result-list').children().eq(0).addClass('my-consultatio-show')
        //                         getDockResultList();
        //                     }
        //                 }
        //             })
        //         })
        //     }
        // })
    })
}

function getDockResultSecond () {
    var obj ={
        'caseId': dockResultListCaseId,
        "pid": dockResultArr[0],
        "pager":{
            "current":currentPageDockResultReply,
            "size":searchSizeDockResultReply,
        },    //每页条数
        "sortPointer":{
            "filed":"created_at",
            "order":"DESC"
        }
    }
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseConsulting/pc/query_case_by_user_id_reply?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(obj),
        success: function (res) {
            console.log(3333);
            console.log(res);


            // hrz
            // if (pageInOrTabDockResult === 0){
            // }
            // if (pageInOrTabDockResult === 1){
            //     $('.dock-result-splitpage >div').Paging({pagesize:searchSizeOfDockResultList,count:totalRecord,toolbar:true});
            // $('.dock-result-splitpage >div').find("div:eq(0)").remove();
            // }
            if (res.data.total == 0) {
                $('.dock-result-reply-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.dock-result-reply-splitpage >div').Paging({pagesize:searchSizeDockResultReply,count:totalRecord,toolbar:true});
                $('.dock-result-reply-splitpage >div').find("div:eq(1)").remove();
            }

            var messages = res.data.data_list
            var userInfo = window.localStorage.getItem("user")
            console.log(userInfo);
            console.log(JSON.parse(userInfo).id);
            for (var i=0; i<messages.length; i++){
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                if (JSON.parse(messages[i].user_id).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                } else {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                }
                div.append(span)
                div.append(span1)
                div.append(span2)
                div.append(span3)
                var p = $('<p style="font-size: 14px;word-break: break-all">'+ messages[i].contents + '</p>')
                div1.append(p)
                $('.dock-result-list .replyContentHistory').append(div)
                $(".dock-result-list .replyContentHistory").append(div1)
            }
            $('.dock-result-list').find('.replyContent-submit').off().click(function () {
                var content = $('.dock-result-list').find('.replyContent-textarea').val()
                var obj = {}
                if (!!dockResultArr[1]) {
                    obj.pid = parseInt(dockResultArr[1])
                } else {
                    obj.pid = parseInt(dockResultArr[2])
                }
                obj.caseId = dockResultListCaseId
                obj.contents = filterSensitiveWord(content)
                if (obj.contents == '') {
                    layer.msg("回复内容不能为空")
                }else {
                    $('.dock-result-list .replyContent-submit').attr('disabled',true);
                    new NewAjax({
                        type: "POST",
                        url: "/f/matureCaseConsulting/pc/create_update?pc=true",
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        data: JSON.stringify(obj),
                        success: function (res) {
                            if(res.status === 200){
                                layer.msg("回复成功，即将返回页面")
                                $('.dock-result-list').find('.replyContent-textarea').val('');
                                setTimeout(function () {
                                    $('.dock-result-list div').removeClass('my-consultatio-show')
                                    $('.dock-result-list').children().eq(0).addClass('my-consultatio-show')
                                    getDockResultList();
                                },500)
                            }
                        }
                    })
                }
            })
        }
    })
}