/**
 * Created by yjq on 2018/9/3.
 */
var currentPageOfConsultationConsultationList = 1;
var searchSizeOfConsultationConsultationList = 10;
var consultationResultListTypeId;
var pageInOrTabConsultationResult = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var consultationResultListArr = [];
var consultationResultListCaseId;

/** 回复的分页 **/
var currentPageConsultationResultReply = 1;
var searchSizeConsultationResultReply = 5;

// 搜索条件对象
var selectConditionOfConsultationConsultationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".consultation-result-splitpage >div").on("click", function(){
        console.log($('.consultation-result-splitpage .focus')[0].innerText);
        if($('.consultation-result-splitpage .focus')[0].innerText != currentPageOfConsultationConsultationList) {
            currentPageOfConsultationConsultationList = $('.consultation-result-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationResult = 0;
            getConsultationResultList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfConsultationConsultationList = $('.consultation-result-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationResult = 0;
            getConsultationResultList();
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".consultation-result-reply-splitpage >div").on("click", function(){
        console.log($('.consultation-result-reply-splitpage .focus')[0].innerText);
        if($('.consultation-result-reply-splitpage .focus')[0].innerText != currentPageConsultationResultReply) {
            currentPageConsultationResultReply = $('.consultation-result-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.consultation-result-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getConsultationResultListSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageConsultationResultReply = $('.consultation-result-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.consultation-result-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getConsultationResultListSecond();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageConsultationResult () {
    $('.consultation-result-splitpage >div').data('currentpage, 1');
    currentPageOfConsultationConsultationList = 1;
    $('.consultation-result-splitpage >div').find('li[data-page="' + currentPageOfConsultationConsultationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var consultationResultListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('consultation-result-list');
    consultationResultListSearch();
});
function init_dom() {
    $('.consultation-result-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.consultation-result-list .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.consultation-result-list .personal-center-search-list ul li').click(function () {
        $('.consultation-result-list').find('ul li').removeClass('active')
        $(this).addClass('active')
        consultationResultListTypeId = $(this).attr('data-typeId')

        pageInOrTabConsultationResult = 1
        currentPageOfConsultationConsultationList = 1

        getConsultationResultList()
    })
}

function consultationResultListSearch() {
    $('.consultation-result-list').find('.searchByTime').click(function () {
        selectConditionOfConsultationConsultationList.searchVal = $('.consultation-result-list').find('.searchByNameContent').val()
        selectConditionOfConsultationConsultationList.startTime = $('.consultation-result-list').find('.search-star-time').val()
        selectConditionOfConsultationConsultationList.endTime = $('.consultation-result-list').find('.search-end-time').val()

        pageInOrTabConsultationResult = 1
        currentPageOfConsultationConsultationList = 1

        getConsultationResultList();
    })
    $('.consultation-result-list').find('.searchByName').click(function () {
        selectConditionOfConsultationConsultationList.searchVal = $('.consultation-result-list').find('.searchByNameContent').val()

        pageInOrTabConsultationResult = 1
        currentPageOfConsultationConsultationList = 1

        getConsultationResultList();
    })
}

// 获取报名的需求列表
function getConsultationResultList () {
    var json = {
        "pager":{
            "current": currentPageOfConsultationConsultationList,
            "size": searchSizeOfConsultationConsultationList
        },
        "sortPointer":{
            "filed": "created_at",
            "order": "DESC"
        }
    }
    if (consultationResultListTypeId) {
        json.reply = consultationResultListTypeId
    }
    if (selectConditionOfConsultationConsultationList.searchVal) {
        json.title = selectConditionOfConsultationConsultationList.searchVal;
    }
    if (selectConditionOfConsultationConsultationList.startTime) {
        json.createdAtStart = selectConditionOfConsultationConsultationList.startTime;
    }
    if (selectConditionOfConsultationConsultationList.endTime) {
        json.createdAt = selectConditionOfConsultationConsultationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseConsulting/pc/query_case_by_user_id_mine?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;

            if (pageInOrTabConsultationResult === 0){
                $('.consultation-result-splitpage >div').Paging({pagesize:searchSizeOfConsultationConsultationList,count:totalRecord,toolbar:true});
                $('.consultation-result-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabConsultationResult === 1){
                $('.consultation-result-splitpage >div').Paging({pagesize:searchSizeOfConsultationConsultationList,count:totalRecord,toolbar:true});
                $('.consultation-result-splitpage >div').find("div:eq(0)").remove();
            }
            $('.consultation-result-total').html("共" + totalRecord + "条");
            console.log(list);
            consultationResultListData = list;
            setConsultationConsultationList(list);
            getConsultationResultListData();
            consulationResultListHandleClick();
            if (list.length === 0) {
                $('.consultation-result-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.consultation-result-list').append(div)
                $('.consultation-result-splitpage').css("display", "none")
                $('.consultation-result-total').css("display", "none")
            } else {
                $('.consultation-result-list').find('.noData').remove()
                $('.consultation-result-splitpage').css("display", "block")
                $('.consultation-result-total').css("display", "block")
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


function getConsultationResultListData() {
    var data = []
    var table = new Table('consultation-result-list-table')
    var baseStyleArr = []
    var arr = []
    if (consultationResultListData != undefined && consultationResultListData.length != 0) {
        consultationResultListData.forEach(function (item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '案例名称'
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
            obj.title = [item.title, item.case_id];
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
            arr = ['title','provider','contents','created_at','reply','id']
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
            } else if (type=== 'title') {
                var span = '<span title="'+content[0]+'" class="consultationResultListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
                return (label === 'td') ? span : content
            }
    })
    table.createTable()
}

function consulationResultListHandleClick() {
    $(document).on('click','.consultationResultListToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/case_detail.html?pc=true')
    })

    $('.consultation-result-list').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');

        consultationResultListArr = idArr;

        $('.consultation-result-list div').removeClass('my-consultatio-show')
        $('.consultation-result-list').children().eq(1).addClass('my-consultatio-show')
        $('.consultation-result-list .replyContent').remove()
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
            url: "/f/matureCaseConsulting/pc/query_case_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                currentPageConsultationResultReply = 1;
                $('.consultation-result-reply-splitpage >div').find("div:eq(0)").remove();

                $('.consultation-result-list .replyContentHistory>div').not(":first").remove();
                console.log(res);
                var userInfo = null;
                var messages = res.data.data_list;
                consultationResultListCaseId = messages[0].case_id
                console.log(consultationResultListCaseId)
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
                    $('.consultation-result-list .replyContentHistory').append(div2);
                    // $(".consultation-result-list .replyContentHistory").append(div1);
                    getConsultationResultListSecond();
                }
            }
        })


        // $('.consultation-result-list').find('.reply-message').click(function () {
        //     console.log($(this).attr('data-id'));
        //     var idArr = $(this).attr('data-id').split(',');
        //     $('.consultation-result-list div').removeClass('my-consultatio-show')
        //     $('.consultation-result-list').children().eq(1).addClass('my-consultatio-show')
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
        //                 $('.consultation-result-list .replyContentHistory').append(div)
        //                 $(".consultation-result-list .replyContentHistory").append(div1)
        //             }
        //             $('.consultation-result-list').find('.replyContent-submit').click(function () {
        //                 var content = $('.consultation-result-list').find('.replyContent-textarea').val()
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
        //                             $('.consultation-result-list div').removeClass('my-consultatio-show')
        //                             $('.consultation-result-list').children().eq(0).addClass('my-consultatio-show')
        //                             getConsultationResultList();
        //                         }
        //                     }
        //                 })
        //             })
        //         }
        //     })
        // })
    })
}

function getConsultationResultListSecond() {
    var obj = {
        'caseId': consultationResultListCaseId,
        "pid": consultationResultListArr[0],
        "pager": {
            "current": currentPageConsultationResultReply,
            "size": searchSizeConsultationResultReply,
        },    //每页条数
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    }
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseConsulting/pc/query_case_by_user_id_reply?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(obj),
        success: function (res) {

            if (res.data.total == 0) {
                $('.consultation-result-reply-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.consultation-result-reply-splitpage >div').Paging({pagesize:searchSizeConsultationResultReply,count:totalRecord,toolbar:true});
                $('.consultation-result-reply-splitpage >div').find("div:eq(1)").remove();
            }

            var messages = res.data.data_list
            var userInfo = window.localStorage.getItem("user")
            for (var i = 0; i < messages.length; i++) {
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                if (JSON.parse(messages[i].respondent).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">'+JSON.parse(messages[i].user_id).user_name+'</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
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
                $('.consultation-result-list .replyContentHistory').append(div)
                $(".consultation-result-list .replyContentHistory").append(div1)

            }
            $('.consultation-result-list').find('.replyContent-submit').off().click(function () {
                var content = $('.consultation-result-list').find('.replyContent-textarea').val()
                var obj = {}
                if (!!consultationResultListArr[1]) {
                    obj.pid = parseInt(consultationResultListArr[1])
                } else {
                    obj.pid = parseInt(consultationResultListArr[2])
                }
                obj.caseId = consultationResultListCaseId
                obj.contents = content
                new NewAjax({
                    type: "POST",
                    url: "/f/matureCaseConsulting/pc/create_update?pc=true",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(obj),
                    success: function (res) {
                        if (res.status === 200) {
                            $('.consultation-result-list div').removeClass('my-consultatio-show')
                            $('.consultation-result-list').children().eq(0).addClass('my-consultatio-show')
                            getConsultationResultList();
                        }
                    }
                })
            })
        }
    })
}