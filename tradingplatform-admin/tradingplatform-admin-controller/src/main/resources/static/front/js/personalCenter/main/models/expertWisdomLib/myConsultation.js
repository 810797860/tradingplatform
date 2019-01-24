var currentPageOfMyConsultationList = 1;
var searchSizeOfMyConsultationList = 10;
var myCousultationTypeId;
var pageInOrTabMyConsultation = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var myConsyltationCaseId;
var myConsultationArr = [];

/** 回复的分页 **/
// hrz
var currentPageMyConsultationReply = 1;
var searchSizeMyConsultationReply = 5;

// 搜索条件对象
var selectConditionOfMyConsultationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".my-consultation-splitpage >div").on("click", function(){
        console.log($('.my-consultation-splitpage .focus')[0].innerText);
        if($('.my-consultation-splitpage .focus')[0].innerText != currentPageOfMyConsultationList) {
            currentPageOfMyConsultationList = $('.my-consultation-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMyConsultation = 0;
            getMyConsultationList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            if($('.my-consultation-splitpage .focus')[0].innerText != currentPageOfMyConsultationList) {
                currentPageOfMyConsultationList = $('.my-consultation-splitpage .focus')[0].innerText;

                // getServiceList($_typeId, $("#search-input").val());
                pageInOrTabMyConsultation = 0;
                getMyConsultationList();
            }
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".my-consultation-reply-splitpage >div").on("click", function(){
        console.log($('.my-consultation-reply-splitpage .focus')[0].innerText);
        if($('.my-consultation-reply-splitpage .focus')[0].innerText != currentPageMyConsultationReply) {
            currentPageMyConsultationReply = $('.my-consultation-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.my-consultation-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyConsultationSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageMyConsultationReply = $('.my-consultation-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.my-consultation-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyConsultationSecond();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageMyConsultation () {
    $('.my-consultation-splitpage >div').data('currentpage', 1);
    currentPageOfMyConsultationList = 1;
    $('.my-consultation-splitpage >div').find('li[data-page="' + currentPageOfMyConsultationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var myConsultationListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('my-consultation-list')
    consultationInformationListSearch();
});
function init_dom () {
    $('.my-consultation-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.my-consultation-list .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.my-consultation-list .personal-center-search-list ul li').click(function () {
        $('.my-consultation-list').find('ul li').removeClass('active')
        $(this).addClass('active')
        myCousultationTypeId = $(this).attr('data-typeId')

        pageInOrTabMyConsultation = 1
        currentPageOfMyConsultationList = 1

        getMyConsultationList()
    })
}

function consultationInformationListSearch() {
    $('.my-consultation-list').find('.searchByTime').click(function () {
        selectConditionOfMyConsultationList.searchVal = $('.my-consultation-list').find('.searchByNameContent').val()
        selectConditionOfMyConsultationList.startTime = $('.my-consultation-list').find('.search-star-time').val()
        selectConditionOfMyConsultationList.endTime = $('.my-consultation-list').find('.search-end-time').val()

        pageInOrTabMyConsultation = 1
        currentPageOfMyConsultationList = 1

        getMyConsultationList();
    })
    $('.my-consultation-list').find('.searchByName').click(function () {
        selectConditionOfMyConsultationList.searchVal = $('.my-consultation-list').find('.searchByNameContent').val()

        pageInOrTabMyConsultation = 1
        currentPageOfMyConsultationList = 1

        getMyConsultationList();
    })
}

// 获取发布的需求列表
function getMyConsultationList () {
    var json = {
        "pager": {
            "current": currentPageOfMyConsultationList,
            "size": searchSizeOfMyConsultationList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (myCousultationTypeId) {
        json.reply = myCousultationTypeId
    }
    if (selectConditionOfMyConsultationList.searchVal) {
        json.contents = selectConditionOfMyConsultationList.searchVal;
    }
    if (selectConditionOfMyConsultationList.startTime) {
        json.createdAtStart = selectConditionOfMyConsultationList.startTime;
    }
    if (selectConditionOfMyConsultationList.endTime) {
        json.createdAt = selectConditionOfMyConsultationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/expertsConsulting/pc/query_consult_by_user_id_mine?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            if (pageInOrTabMyConsultation === 0){
                $('.my-consultation-splitpage >div').Paging({pagesize:searchSizeOfMyConsultationList,count:totalRecord,toolbar:true});
                $('.my-consultation-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabMyConsultation === 1){
                $('.my-consultation-splitpage >div').Paging({pagesize:searchSizeOfMyConsultationList,count:totalRecord,toolbar:true});
                $('.my-consultation-splitpage >div').find("div:eq(0)").remove();
            }
            $('.my-consultation-total').html("共" + totalRecord + "条");

            myConsultationListData = list;
            setMyConsultationListData();
            myConsulationHandleClick();
            if (list.length === 0) {
                $('.my-consultation-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.my-consultation-list').append(div)
                $('.my-consultation-splitpage').css("display", "none")
                $('.my-consultation-total').css("display", "none")
            } else {
                $('.my-consultation-list').find('.noData').remove()
                $('.my-consultation-splitpage').css("display", "block")
                $('.my-consultation-total').css("display", "block")
            }
        }
    });
}

function setMyConsultationListData() {
    var data = []
    var table = new Table('my-consultation-list-table')
    var baseStyleArr = []
    var arr = []
    if (myConsultationListData != undefined && myConsultationListData.length != 0) {
        myConsultationListData.forEach(function(item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'name':
                            styleItem.name = '专家'
                            styleItem.width = 220
                            break
                        case 'contents':
                            styleItem.name = '回复内容'
                            styleItem.width = 300
                            break
                        case 'created_at':
                            styleItem.name = '回复时间'
                            break
                        case 'reply':
                            styleItem.name = '状态'
                            styleItem.width = 120
                            break
                        case 'experts_id':
                            styleItem.name = '操作'
                        // default:
                        //     styleItem.name = key
                        //     break
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.name = [item.name,item.experts_id]
            obj.contents = item.contents
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.reply =item.reply
            // if (item.reply === true) {
            //     for (var i=0; i<baseStyleArr.length; i++) {
            //         if(baseStyleArr[i].type === 'experts_id') {
            //             baseStyleArr.splice(i, 1)
            //         }
            //     }
            //     arr = ['expert_name','contents','created_at','reply']
            // } else {
            if (!!item['respondent']){
                obj.experts_id = [item.experts_id,item['pid'],item.id,JSON.parse(item['respondent']).id]
            } else {
                obj.experts_id = [item.experts_id,item['pid'],item.id]
            }
            arr = ['name','contents','created_at','reply','experts_id']
            // }
            data.push(obj)
        })
    }
    var orderArr = arr
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label) {
        if (type === 'reply') {
            var span
            if (content === true) {
                span = '<span>已回复</span>'
            } else {
                span = '<span>未回复</span>'
            }
            return (label === 'td') ? span : content
        } else if (type === 'experts_id') {
            span = '<span class="reply-message" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看信息</span>'
            return (label === 'td') ? span : content
        }else if (type=== 'name') {
            var span = '<span title="'+content[0]+'" class="myConsultationToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function myConsulationHandleClick() {
    $(document).on('click','.myConsultationToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/expert_detail.html?pc=true')
    })
    $('.my-consultation-list').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');
        myConsultationArr = idArr
        $('.my-consultation-list div').removeClass('my-consultatio-show')
        $('.my-consultation-list').children().eq(1).addClass('my-consultatio-show')
        $('.my-consultation-list .replyContent').remove()
        var json = {
            "id": idArr[2],
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
            url: "/f/expertsConsulting/pc/query_consult_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // hrz
                currentPageMyConsultationReply = 1;
                $('.my-consultation-reply-splitpage >div').find("div:eq(0)").remove();

                $('.my-consultation-list .replyContentHistory>div').not(":first").remove();
                var messages = res.data.data_list;
                if (messages.length > 0) {
                    myConsyltationCaseId = messages[0].case_id;
                    var div2 = $('<div class="oneMessage"></div>');
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
                    $('.my-consultation-list .replyContentHistory').append(div2);
                    // $(".my-consultation-list .replyContentHistory").append(div1);
                    getMyConsultationSecond();
                }
            }
        })
    })
    
    
    
    
    
    // $('.my-consultation-list').find('.reply-message').click(function () {
    //     var idArr = $(this).attr('data-id').split(',');
    //     $('.my-consultation-list div').removeClass('my-consultatio-show')
    //     $('.my-consultation-list').children().eq(1).addClass('my-consultatio-show')
    //     var json = {
    //         "pager":{
    //             "current":1,
    //             "size":4
    //         },
    //         "sortPointer":{
    //             "filed":"created_at",
    //             "order":"DESC"
    //         }
    //     }
    //     console.log($(this).attr('data-id'));
    //     if (!!$(this).attr('data-id')) {
    //         json.expertsId = idArr[0]
    //         json.pid = parseInt(idArr[1])
    //     }
    //     new NewAjax({
    //         type: "POST",
    //         url: "/f/expertsConsulting/pc/query_consult_by_user_id_mine_reply?pc=true",
    //         contentType: "application/json;charset=UTF-8",
    //         dataType: "json",
    //         data: JSON.stringify(json),
    //         success: function (res) {
    //             var userInfo = null
    //             var messages = res.data.data_list
    //             for (var i=0; i<messages.length; i++){
    //                 var div = $('<div style="height: 40px;line-height: 40px"></div>')
    //                 var div1 = $('<div style="height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
    //                 var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].consultor_name).user_name + '</span>')
    //                 var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
    //                 var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
    //                 var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
    //                 div.append(span)
    //                 div.append(span1)
    //                 div.append(span2)
    //                 div.append(span3)
    //                 var p = $('<p style="font-size: 14px">'+ messages[i].contents + '</p>')
    //                 div1.append(p)
    //                 $('.my-consultation-list .replyContentHistory').append(div)
    //                 $(".my-consultation-list .replyContentHistory").append(div1)
    //             }
    //             $('.my-consultation-list').find('.replyContent-submit').click(function () {
    //                 var content = $('.my-consultation-list').find('.replyContent-textarea').val()
    //                 var obj = {}
    //                 if (!!idArr[1]) {
    //                     obj.pid = parseInt(idArr[1])
    //                     obj.id = parseInt(idArr[2])
    //                 } else {
    //                     obj.id = parseInt(idArr[2])
    //                     obj.pid = parseInt(idArr[2])
    //                 }
    //                 obj.expertsId = parseInt(idArr[0])
    //                 obj.contents = content
    //                 obj.respondent = parseInt(idArr[3])
    //                 new NewAjax({
    //                     type: "POST",
    //                     url: "/f/expertsConsulting/pc/create_update?pc=true",
    //                     contentType: "application/json;charset=UTF-8",
    //                     dataType: "json",
    //                     data: JSON.stringify(obj),
    //                     success: function (res) {
    //                         if (res.status === 200) {
    //                             $('.my-consultation-list div').removeClass('my-consultatio-show')
    //                             $('.my-consultation-list').children().eq(0).addClass('my-consultatio-show')
    //                             getMyConsultationList()
    //                         }
    //                     }
    //                 })
    //             })
    //         }
    //     })
    // })
}

function getMyConsultationSecond() {
    var obj = {
        'caseId': myConsyltationCaseId,
        "pid": myConsultationArr[2],
        "pager": {
            "current": currentPageMyConsultationReply,
            "size": searchSizeMyConsultationReply,
        },    //每页条数
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/expertsConsulting/pc/query_consult_by_user_id_reply?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(obj),
        success: function (res) {
            // hrz

            if (res.data.total == 0) {
                $('.my-consultation-reply-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.my-consultation-reply-splitpage >div').Paging({pagesize:searchSizeMyConsultationReply,count:totalRecord,toolbar:true});
                $('.my-consultation-reply-splitpage >div').find("div:eq(1)").remove();
            }


            console.log(res);
            var messages = res.data.data_list;
            var userInfo = window.localStorage.getItem("user");
            for (var i = 0; i < messages.length; i++) {
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>');
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>');
                if (JSON.parse(messages[i].respondent).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>');
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>');
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>');
                } else {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>');
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>');
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>');
                }
                div.append(span);
                div.append(span1);
                div.append(span2);
                div.append(span3);
                var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[i].contents + '</p>');
                div1.append(p);
                $('.my-consultation-list .replyContentHistory').append(div);
                $(".my-consultation-list .replyContentHistory").append(div1);
            }
            $('.my-consultation-list').find('.replyContent-submit').off().click(function () {
                var content = $('.my-consultation-list').find('.replyContent-textarea').val();
                var obj = {};
                if (!!myConsultationArr[1]) {
                    obj.pid = parseInt(myConsultationArr[1]);
                } else {
                    obj.pid = parseInt(myConsultationArr[2]);
                }
                obj.caseId = myConsyltationCaseId;
                obj.contents = content;
                new NewAjax({
                    type: "POST",
                    url: "/f/expertsConsulting/pc/create_update?pc=true",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(obj),
                    success: function (res) {
                        if (res.status === 200) {
                            $('.my-consultation-list div').removeClass('my-consultatio-show');
                            $('.my-consultation-list').children().eq(0).addClass('my-consultatio-show');
                            getDockResultList();
                        }
                    }
                })
            })
        }
    })
}