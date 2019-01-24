var currentPageOfConsultationInformationList = 1;
var searchSizeOfConsultationInformationList = 10;
var consulationInformationTypeId;
var pageInOrTabConsultationInformation = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var consultationInfirmationId;
var consultationInfirmationArr = [];

/** 回复的分页 **/
// hrz
var currentPageConsultationInformationReply = 1;
var searchSizeConsultationInformationReply = 5;

//留言數據
var messageData = []
// 搜索条件对象
var selectConditionOfConsultationInformationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".consultation-information-splitpage >div").on("click", function(){
        console.log($('.consultation-information-splitpage .focus')[0].innerText);
        if($('.consultation-information-splitpage .focus')[0].innerText != currentPageOfConsultationInformationList) {
            currentPageOfConsultationInformationList = $('.consultation-information-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationInformation = 0;
            getConsultationInformationList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfConsultationInformationList = $('.consultation-information-splitpage .focus')[0].innerText;

                // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabConsultationInformation = 0;
            getConsultationInformationList();
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".consultation-information-reply-splitpage >div").on("click", function(){
        console.log($('.consultation-information-reply-splitpage .focus')[0].innerText);
        if($('.consultation-information-reply-splitpage .focus')[0].innerText != currentPageConsultationInformationReply) {
            currentPageConsultationInformationReply = $('.consultation-information-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.consultation-information-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove();
            getConsultationInformation();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageConsultationInformationReply = $('.consultation-information-reply-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabDockResult = 0;
            // dockResulthandleClick();
            $('.consultation-information-list .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove();
            getConsultationInformation();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageConsultationInformation () {
    $('.consultation-information-splitpage >div').data('currentpage', 1);
    currentPageOfConsultationInformationList = 1;
    $('.consultation-information-splitpage >div').find('li[data-page="' + currentPageOfConsultationInformationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var consultationInformationListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('consultation-information-list');
    consultationInformationListSearch();
});

function init_dom() {
    $('.consultation-information-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.consultation-information-list .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.consultation-information-list .personal-center-search-list ul li').click(function () {
        $('.consultation-information-list').find('ul li').removeClass('active');
        $(this).addClass('active');
        consulationInformationTypeId = $(this).attr('data-typeId');

        pageInOrTabConsultationInformation = 1;
        currentPageOfConsultationInformationList = 1;

        getConsultationInformationList();
    })
}

function consultationInformationListSearch() {
    $('.consultation-information-list').find('.searchByTime').click(function () {
        selectConditionOfConsultationInformationList.searchVal = $('.consultation-information-list').find('.searchByNameContent').val();
        selectConditionOfConsultationInformationList.startTime = $('.consultation-information-list').find('.search-star-time').val();
        selectConditionOfConsultationInformationList.endTime = $('.consultation-information-list').find('.search-end-time').val();

        pageInOrTabConsultationInformation = 1;
        currentPageOfConsultationInformationList = 1;

        getConsultationInformationList();
    })
    $('.consultation-information-list').find('.searchByName').click(function () {
        selectConditionOfConsultationInformationList.searchVal = $('.consultation-information-list').find('.searchByNameContent').val();

        currentPageOfConsultationInformationList = 1;
        currentPageOfConsultationInformationList = 1;

        getConsultationInformationList();
    })
}

// 获取发布的需求列表
function getConsultationInformationList () {
    var json = {
        "pager": {
            "current": currentPageOfConsultationInformationList,
            "size": searchSizeOfConsultationInformationList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (consulationInformationTypeId) {
        json.reply = consulationInformationTypeId
    }
    if (selectConditionOfConsultationInformationList.searchVal) {
        json.contents = selectConditionOfConsultationInformationList.searchVal;
    }
    if (selectConditionOfConsultationInformationList.startTime) {
        json.createdAtStart = selectConditionOfConsultationInformationList.startTime;
    }
    if (selectConditionOfConsultationInformationList.endTime) {
        json.createdAt = selectConditionOfConsultationInformationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/expertsConsulting/pc/query_consult_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabConsultationInformation === 0){
                $('.consultation-information-splitpage >div').Paging({pagesize:searchSizeOfConsultationInformationList,count:totalRecord,toolbar:true});
                $('.consultation-information-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabConsultationInformation === 1){
                $('.consultation-information-splitpage >div').Paging({pagesize:searchSizeOfConsultationInformationList,count:totalRecord,toolbar:true});
                $('.consultation-information-splitpage >div').find("div:eq(0)").remove();
            }
            $('.consultation-information-total').html("共" + totalRecord + "条");

            consultationInformationListData = list;
            setConsultationInformationList(list);
            setConsultationInformationListData();
            consulationInfomationHandleClick();
            if (list.length === 0) {
                $('.consultation-information-list').find('.noData').remove();
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>';
                $('.consultation-information-list').append(div);
                $('.consultation-information-splitpage').css("display", "none");
                $('.consultation-information-total').css("display", "none");
            } else {
                $('.consultation-information-list').find('.noData').remove();
                $('.consultation-information-splitpage').css("display", "block");
                $('.consultation-information-total').css("display", "block");
            }
        }
    });
}

// 设置发布的需求列表
function setConsultationInformationList (list) {
    for (var i = 0; i < list.length; i++) {
        list[i].id;
        list[i].name;   // 需求名称
        // 类型
        if (list[i].industry_field) {
            var industryField = JSON.parse(list[i].industry_field);
        }
        // 状态
        if (list[i].status) {
            var status = JSON.parse(list[i].status);
        }
        list[i].total_docking;  // 对接人数
        list[i].created_at; // 发布时间
    }
}


function setConsultationInformationListData() {
    var data = [];
    var table = new Table('consultation-information-list-table');
    var baseStyleArr = [];
    var arr = [];
    if (consultationInformationListData != undefined && consultationInformationListData.length != 0) {
        consultationInformationListData.forEach(function(item) {
            var obj = {};
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {};
                    styleItem.type = key;
                    switch (key) {
                        case 'user_id':
                            styleItem.name = '咨询人';
                            styleItem.width = 220;
                            break;
                        case 'contents':
                            styleItem.name = '咨询内容';
                            break;
                        case 'created_at':
                            styleItem.name = '咨询时间';
                            styleItem.width = 120;
                            break;
                        case 'reply':
                            styleItem.name = '状态';
                            styleItem.width = 120;
                            break;
                        case 'experts_id':
                            styleItem.name = '操作';
                            styleItem.width = 120;
                            break;
                        // default:
                        //     styleItem.name = key
                        //     break
                    }
                    styleItem.align = 'left';
                    baseStyleArr.push(styleItem);
                })
            }
            obj.user_id = JSON.parse(item.user_id).user_name;
            obj.contents = item.contents;
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0];
            obj.reply =item.reply;
            // if (item.reply === true) {
            //     for (var i=0; i<baseStyleArr.length; i++) {
            //         if(baseStyleArr[i].type === 'id') {
            //             baseStyleArr.splice(i, 1)
            //         }
            //     }
            //     arr = ['consultor_name','contents','created_at','reply']
            // } else {
            if (!!item['respondent']) {
                obj.experts_id = [item.experts_id, item.pid, item.id,JSON.parse(item['respondent']).id];
            } else {
                obj.experts_id = [item.experts_id, item.pid, item.id];
            }
            arr =  ['user_id','contents','created_at','reply', 'experts_id'];
            // }
            data.push(obj);
        })
    }
    var orderArr = arr;
    table.setTableData(data);
    table.setBaseStyle(baseStyleArr);
    table.setColOrder(orderArr);
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label) {
        if (type === 'reply') {
            var span;
            if (content === true) {
                span = '<span>已回复</span>';
            } else {
                span = '<span>未回复</span>';
            }
            return (label === 'td') ? span : content;
        } else if (type === 'experts_id') {
            span = '<span class="reply-message" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>回复信息</span>';
            return (label === 'td') ? span : content;
        }
    })
    table.createTable();
}


function consulationInfomationHandleClick() {
    $('.consultation-information-list').find('.reply-message').click(function () {
        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');
        consultationInfirmationArr = idArr;
        $('.consultation-information-list div').removeClass('my-consultatio-show');
        $('.consultation-information-list').children().eq(1).addClass('my-consultatio-show');
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
        };

        new NewAjax({
            type: "POST",
            url: "/f/expertsConsulting/pc/query_consult_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // hrz
                currentPageConsultationInformationReply = 1;
                $('.consultation-information-reply-splitpage >div').find("div:eq(0)").remove();

                $('.consultation-information-list .replyContentHistory>div').not(":first").remove();
                var messages = res.data.data_list;
                if (messages.length > 0) {
                    consultationInfirmationId = messages[0].experts_id;
                    var div2 = $('<div class="oneMessage"></div>');
                    var div = $('<div style="min-height: 40px;line-height: 40px"></div>');
                    var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>');
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[0].user_id).user_name + '</span>');
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>');
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[0].created_at)) + '</span>');
                    div.append(span);
                    div.append(span1);
                    div.append(span2);
                    div.append(span3);
                    var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[0].contents + '</p>');
                    div1.append(p);
                    div2.append(div);
                    div2.append(div1);
                    $('.consultation-information-list .replyContentHistory').append(div2);
                    // $(".consultation-information-list .replyContentHistory").append(div1);
                    getConsultationInformation();
                }
            }
        })
    })
    
    
    
    
    
    // $('.consultation-information-list').find('.reply-message').click(function () {
    //     $('.consultation-information-list div').removeClass('my-consultatio-show')
    //     $('.consultation-information-list').children().eq(1).addClass('my-consultatio-show')
    //     var idArr = $(this).attr('data-id').split(',');
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
    //     if (!!$(this).attr('data-id')) {
    //         json.expertsId = idArr[0]
    //         json.pid = parseInt(idArr[1])
    //     }
    //     console.log('a')
    //     new NewAjax({
    //         type: "POST",
    //         url: "/f/expertsConsulting/pc/query_consult_by_user_id_reply?pc=true",
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
    //                 $('.consultation-information-list .replyContentHistory').append(div)
    //                 $(".consultation-information-list .replyContentHistory").append(div1)
    //             }
    //             $('.consultation-information-list').find('.replyContent-submit').click(function () {
    //                 var content = $('.consultation-information-list').find('.replyContent-textarea').val()
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
    //                             $('.consultation-information-list div').removeClass('my-consultatio-show')
    //                             $('.consultation-information-list').children().eq(0).addClass('my-consultatio-show')
    //                             getConsultationInformationList();
    //                         }
    //                     }
    //                 })
    //             })
    //         }
    //     })
    // })
}

function getConsultationInformation() {
    var obj = {
        'expertsId': consultationInfirmationId,
        "pid": consultationInfirmationArr[2],
        "pager": {
            "current": currentPageConsultationInformationReply,
            "size": searchSizeConsultationInformationReply,
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
            console.log('a')

            if (res.data.total == 0) {
                $('.consultation-information-reply-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.consultation-information-reply-splitpage >div').Paging({pagesize:searchSizeConsultationInformationReply,count:totalRecord,toolbar:true});
                $('.consultation-information-reply-splitpage >div').find("div:eq(1)").remove();
            }

            console.log(res);
            var messages = res.data.data_list;
            var userInfo = window.localStorage.getItem("user");
            for (var i = 0; i < messages.length; i++) {
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>');
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>');
                if (JSON.parse(messages[i].user_id).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>');
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>');
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>');
                } else {
                    var span = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>');
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>');
                    var span2 = $('<span style="color: #0088c0; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>');
                }
                div.append(span);
                div.append(span1);
                div.append(span2);
                div.append(span3);
                var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[i].contents + '</p>');
                div1.append(p);
                $('.consultation-information-list .replyContentHistory').append(div);
                $(".consultation-information-list .replyContentHistory").append(div1);
            }
            $('.consultation-information-list').find('.replyContent-submit').off().click(function () {
                var content = $('.consultation-information-list').find('.replyContent-textarea').val();
                var obj = {};
                if (!!consultationInfirmationArr[1]) {
                    obj.pid = parseInt(consultationInfirmationArr[1]);
                } else {
                    obj.pid = parseInt(consultationInfirmationArr[2]);
                }
                obj.expertsId = consultationInfirmationId;
                obj.contents = filterSensitiveWord(content);
                console.log(obj.contents);
                if (obj.contents == '') {
                    layer.msg("回复内容不能为空")
                }else {
                    $('.consultation-information-list .replyContent-submit').attr('disabled',true);
                    new NewAjax({
                        type: "POST",
                        url: "/f/expertsConsulting/pc/create_update?pc=true",
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        data: JSON.stringify(obj),
                        success: function (res) {
                            if (res.status === 200) {
                                $('.consultation-information-list').find('.replyContent-textarea').val('');
                                layer.msg("回复成功，即将返回页面");
                                setTimeout(function () {
                                    $('.consultation-information-list div').removeClass('my-consultatio-show');
                                    $('.consultation-information-list').children().eq(0).addClass('my-consultatio-show');
                                    getConsultationInformationList();
                                },500)
                            }
                        }
                    })
                }
            })
        }
    })
}