/**
 * Created by yjq on 2018/9/3.
 */
var currentPageOfServiceConsultationList = 1;
var searchSizeOfServiceConsultationList = 10;
var consultationServiceListTypeId;
var pageInOrTabConsultationService = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var consultationServiceListArr = [];
var consultationServiceListCaseId;

/** 回复的分页 **/
var currentPageConsultationServiceReply = 1;
var searchSizeConsultationServiceReply = 5;

// 搜索条件对象
var selectConditionOfServiceConsultationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".service-consultation-splitpage >div").on("click", function(){
        console.log($('.service-consultation-splitpage .focus')[0].innerText);
        if($('.service-consultation-splitpage .focus')[0].innerText != currentPageOfServiceConsultationList) {
            currentPageOfServiceConsultationList = $('.service-consultation-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationService = 0;
            getConsultationServiceList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfServiceConsultationList = $('.service-consultation-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationService = 0;
            getConsultationServiceList();
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".service-consultation-reply-splitpage >div").on("click", function(){
        console.log($('.service-consultation-reply-splitpage .focus')[0].innerText);
        if($('.service-consultation-reply-splitpage .focus')[0].innerText != currentPageConsultationServiceReply) {
            currentPageConsultationServiceReply = $('.service-consultation-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.service-consultation-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getConsultationServiceListSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageConsultationServiceReply = $('.service-consultation-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.service-consultation-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getConsultationServiceListSecond();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageConsultationService () {
    $('.service-consultation-splitpage >div').data('currentpage, 1');
    currentPageOfServiceConsultationList = 1;
    $('.service-consultation-splitpage >div').find('li[data-page="' + currentPageOfServiceConsultationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var consultationServiceListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('service-consultation-list');
    consultationServiceListSearch();
});
function init_dom() {
    $('.service-consultation-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.service-consultation-list .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.service-consultation-list .personal-center-search-list ul li').click(function () {
        $('.service-consultation-list').find('ul li').removeClass('active')
        $(this).addClass('active')
        consultationServiceListTypeId = $(this).attr('data-typeId')

        pageInOrTabConsultationService = 1
        currentPageOfServiceConsultationList = 1

        getConsultationServiceList()
    })
}

function consultationServiceListSearch() {
    $('.service-consultation-list').find('.searchByTime').click(function () {
        selectConditionOfServiceConsultationList.searchVal = $('.service-consultation-list').find('.searchByNameContent').val()
        selectConditionOfServiceConsultationList.startTime = $('.service-consultation-list').find('.search-star-time').val()
        selectConditionOfServiceConsultationList.endTime = $('.service-consultation-list').find('.search-end-time').val()

        pageInOrTabConsultationService = 1
        currentPageOfServiceConsultationList = 1

        getConsultationServiceList();
    })
    $('.service-consultation-list').find('.searchByName').click(function () {
        selectConditionOfServiceConsultationList.searchVal = $('.service-consultation-list').find('.searchByNameContent').val()

        pageInOrTabConsultationService = 1
        currentPageOfServiceConsultationList = 1

        getConsultationServiceList();
    })
}

// 获取报名的需求列表
function getConsultationServiceList () {
    var json = {
        "pager":{
            "current": currentPageOfServiceConsultationList,
            "size": searchSizeOfServiceConsultationList
        },
        "sortPointer":{
            "filed": "created_at",
            "order": "DESC"
        }
    }
    if (consultationServiceListTypeId) {
        json.reply = consultationServiceListTypeId
    }
    if (selectConditionOfServiceConsultationList.searchVal) {
        json.title = selectConditionOfServiceConsultationList.searchVal;
    }
    if (selectConditionOfServiceConsultationList.startTime) {
        json.createdAtStart = selectConditionOfServiceConsultationList.startTime;
    }
    if (selectConditionOfServiceConsultationList.endTime) {
        json.createdAt = selectConditionOfServiceConsultationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/serviceMessageConsulting/pc/query_service_by_user_id_mine?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;

            if (pageInOrTabConsultationService === 0){
                $('.service-consultation-splitpage >div').Paging({pagesize:searchSizeOfServiceConsultationList,count:totalRecord,toolbar:true});
                $('.service-consultation-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabConsultationService === 1){
                $('.service-consultation-splitpage >div').Paging({pagesize:searchSizeOfServiceConsultationList,count:totalRecord,toolbar:true});
                $('.service-consultation-splitpage >div').find("div:eq(0)").remove();
            }
            $('.service-consultation-list-total').html("共" + totalRecord + "条");
            consultationServiceListData = list;
            setConsultationConsultationList(list);
            getConsultationServiceListData();
            consulationServiceListHandleClick();
            if (list.length === 0) {
                $('.service-consultation-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.service-consultation-list').append(div)
                $('.service-consultation-splitpage').css("display", "none")
                $('.service-consultation-list-total').css("display", "none")
            } else {
                $('.service-consultation-list').find('.noData').remove()
                $('.service-consultation-splitpage').css("display", "block")
                $('.service-consultation-list-total').css("display", "block")
            }
        }
    });
}

function setConsultationConsultationList (list) {
    for (var i = 0; i < list.length; i++) {
        list[i].id;
        list[i].name;   // 需求名称
        // 类型
        if (list[i].industry_field) {
            var industryField = JSON.parse(list[i].industry_field);
        }
        // 发布人
        if (list[i].publisher) {
            var publisher = JSON.parse(list[i].publisher);
        }
        // 雇佣状态
        if (list[i].is_winning) {
            var isWinning = JSON.parse(list[i].is_winning);
        }
        list[i].created_at; // 发布时间
    }
}


function getConsultationServiceListData() {
    var data = []
    var table = new Table('service-consultation-list-table')
    var baseStyleArr = []
    var arr = []
    if (consultationServiceListData != undefined && consultationServiceListData.length != 0) {
        consultationServiceListData.forEach(function (item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'service_title':
                            styleItem.name = '服务名称'
                            styleItem.width = 220
                            break
                        case 'provider':
                            styleItem.name = '案例单位'
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
                            styleItem.width = 120
                            break
                        case 'id':
                            styleItem.name = '操作'
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.service_title = [item.service_title, item.service_id];
            obj.provider = item.provider;
            obj.contents = item.contents;
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0];
            obj.reply =item.reply;
            // if (item.reply === true) {
            //     for (var i=0; i<baseStyleArr.length; i++) {
            //         if(baseStyleArr[i].type === 'id') {
            //             baseStyleArr.splice(i, 1)
            //         }
            //     }
            //     arr = ['title','results_unit_provider','contents','created_at','reply']
            // } else {
            if (!!item['respondent']) {
                obj.id = [item.id, item.pid,item.id,JSON.parse(item['respondent']).id]
            } else {
                obj.id = [item.id, item.pid,item.id]
            }
            // }
            arr = ['service_title','provider','contents','created_at','reply','id']
            data.push(obj)
        })
    }
    var orderArr = arr
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(
        function (type, content, label) {
            if (type === 'reply') {
                var span
                if (content === true) {
                    span = '<span>已回复</span>'
                } else {
                    span = '<span>未回复</span>'
                }
                return (label === 'td') ? span : content
            } else if (type === 'id') {
                if (!!content){
                    span = '<span class="reply-message" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看信息</span>'
                } else {
                    // span = '<button type="button" class="btn btn-default btn-sm" style="font-size: 14px;padding: 0 10px;height: 30px;line-height: 30px;border: none;background-color: #f4f4f4" disabled="disabled">回复信息</button>'
                    // span = '<span class="reply-message-none" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #f4f4f4;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: #333333;cursor: pointer" disabled="disabled"></i>回复信息</span>'
                }
                // console.log(content.[4]);
                // console.log(typeof (content));
                // span = '<span class="reply-message" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>回复信息</span>'
                return (label === 'td') ? span : content
            } else if (type=== 'service_title') {
                var span = '<span title="'+content[0]+'" class="consultationServiceListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
                return (label === 'td') ? span : content
            }
        })
    table.createTable()
}

function consulationServiceListHandleClick() {
    $(document).on('click','.consultationServiceListToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/general_service_detail.html?pc=true')
    })

    $('.service-consultation-list').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');

        consultationServiceListArr = idArr;

        $('.service-consultation-list div').removeClass('my-consultatio-show')
        $('.service-consultation-list').children().eq(1).addClass('my-consultatio-show')
        $('.service-consultation-list .replyContent').remove()
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
            url: "/f/serviceMessageConsulting/pc/query_service_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                currentPageConsultationServiceReply = 1;
                $('.service-consultation-reply-splitpage >div').find("div:eq(0)").remove();

                $('.service-consultation-list .replyContentHistory>div').not(":first").remove();
                console.log(res);
                var userInfo = null;
                var messages = res.data.data_list;
                consultationServiceListCaseId = messages[0].case_id
                console.log(consultationServiceListCaseId)
                if (messages.length > 0) {
                    var div2 = $('<div class="oneMessage"></div>')
                    var div = $('<div style="min-height: 40px;line-height: 40px"></div>');
                    var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>');
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    // var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    // var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[0].created_at)) + '</span>');
                    div.append(span);
                    // div.append(span1)
                    // div.append(span2)
                    div.append(span3);
                    var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[0].contents + '</p>');
                    div1.append(p);
                    div2.append(div);
                    div2.append(div1);
                    $('.service-consultation-list .replyContentHistory').append(div2);
                    // $(".service-consultation-list .replyContentHistory").append(div1);
                    getConsultationServiceListSecond();
                }
            }
        })


        // $('.service-consultation-list').find('.reply-message').click(function () {
        //     console.log($(this).attr('data-id'));
        //     var idArr = $(this).attr('data-id').split(',');
        //     $('.service-consultation-list div').removeClass('my-consultatio-show')
        //     $('.service-consultation-list').children().eq(1).addClass('my-consultatio-show')
        //     var json = {
        //         "pager":{
        //             "current":1,
        //             "size":4
        //         },
        //         "sortPointer":{
        //             "filed":"created_at",
        //             "order":"ASC"
        //         }
        //     }
        //     if (!!$(this).attr('data-id')) {
        //         json.caseId = idArr[0]
        //         json.pid = parseInt(idArr[1])
        //     }
        //     new NewAjax({
        //         type: "POST",
        //         url: "/f/matureCaseConsulting/pc/query_case_by_user_id_mine_reply?pc=true",
        //         contentType: "application/json;charset=UTF-8",
        //         dataType: "json",
        //         data: JSON.stringify(json),
        //         success: function (res) {
        //             console.log(res);
        //             var userInfo = null
        //             var messages = res.data.data_list
        //             for (var i=0; i<messages.length; i++){
        //                 var div = $('<div style="height: 40px;line-height: 40px"></div>')
        //                 var div1 = $('<div style="height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
        //                 var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].consultant).user_name + '</span>')
        //                 var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
        //                 var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
        //                 var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
        //                 div.append(span)
        //                 div.append(span1)
        //                 div.append(span2)
        //                 div.append(span3)
        //                 var p = $('<p style="font-size: 14px">'+ messages[i].contents + '</p>')
        //                 div1.append(p)
        //                 $('.service-consultation-list .replyContentHistory').append(div)
        //                 $(".service-consultation-list .replyContentHistory").append(div1)
        //             }
        //             $('.service-consultation-list').find('.replyContent-submit').click(function () {
        //                 var content = $('.service-consultation-list').find('.replyContent-textarea').val()
        //                 var obj = {}
        //                 if (!!idArr[1]) {
        //                     obj.pid = parseInt(idArr[1])
        //                     obj.id = parseInt(idArr[2])
        //                 } else {
        //                     obj.id = parseInt(idArr[2])
        //                     obj.pid = parseInt(idArr[2])
        //                 }
        //                 obj.caseId = parseInt(idArr[0])
        //                 obj.contents = content
        //                 obj.respondent = parseInt(idArr[3])
        //                 new NewAjax({
        //                     type: "POST",
        //                     url: "/f/matureCaseConsulting/pc/create_update?pc=true",
        //                     contentType: "application/json;charset=UTF-8",
        //                     dataType: "json",
        //                     data: JSON.stringify(obj),
        //                     success: function (res) {
        //                         if(res.status === 200){
        //                             $('.service-consultation-list div').removeClass('my-consultatio-show')
        //                             $('.service-consultation-list').children().eq(0).addClass('my-consultatio-show')
        //                             getConsultationServiceList();
        //                         }
        //                     }
        //                 })
        //             })
        //         }
        //     })
        // })
    })
}

function getConsultationServiceListSecond() {
    var obj = {
        'caseId': consultationServiceListCaseId,
        "pid": consultationServiceListArr[0],
        "pager": {
            "current": currentPageConsultationServiceReply,
            "size": searchSizeConsultationServiceReply,
        },    //每页条数
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    }
    new NewAjax({
        type: "POST",
        url: "/f/serviceMessageConsulting/pc/query_service_by_user_id_reply?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(obj),
        success: function (res) {

            if (res.data.total == 0) {
                $('.service-consultation-reply-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.service-consultation-reply-splitpage >div').Paging({pagesize:searchSizeConsultationServiceReply,count:totalRecord,toolbar:true});
                $('.service-consultation-reply-splitpage >div').find("div:eq(1)").remove();
            }

            var messages = res.data.data_list
            var userInfo = window.localStorage.getItem("user")
            for (var i = 0; i < messages.length; i++) {
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                if (JSON.parse(messages[i].respondent).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">'+JSON.parse(messages[i].user_id).user_name+'</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                } else {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">'+JSON.parse(messages[i].respondent).user_name+'</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                }
                div.append(span)
                div.append(span1)
                div.append(span2)
                div.append(span3)
                var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[i].contents + '</p>')
                div1.append(p)
                $('.service-consultation-list .replyContentHistory').append(div)
                $(".service-consultation-list .replyContentHistory").append(div1)

            }
            $('.service-consultation-list').find('.replyContent-submit').off().click(function () {
                var content = $('.service-consultation-list').find('.replyContent-textarea').val()
                var obj = {}
                if (!!consultationServiceListArr[1]) {
                    obj.pid = parseInt(consultationServiceListArr[1])
                } else {
                    obj.pid = parseInt(consultationServiceListArr[2])
                }
                obj.caseId = consultationServiceListCaseId
                obj.contents = content
                new NewAjax({
                    type: "POST",
                    url: "/f/matureCaseConsulting/pc/create_update?pc=true",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(obj),
                    success: function (res) {
                        if (res.status === 200) {
                            $('.service-consultation-list div').removeClass('my-consultatio-show')
                            $('.service-consultation-list').children().eq(0).addClass('my-consultatio-show')
                            getConsultationServiceList();
                        }
                    }
                })
            })
        }
    })
}