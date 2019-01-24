/**
 * Created by 空 on 2018/11/19.
 */
var currentPageOfMyShopConsultationList = 1;
var searchSizeOfMyShopConsultationList = 10;
var MyShopConsultationListTypeId;
var pageInOrTabMyShopConsultation = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var myShopConsultationListCaseId;
var myShopConsultationArr = [];

/** 回复的分页 **/
// hrz
var currentPageMyShopConsultationReply = 1;
var searchSizeMyShopConsultationReply = 5;

// 搜索条件对象
var selectConditionOfMyShopConsultationList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".my-shop-consultation-splitpage>div").on("click", function(){
        console.log(11);
        console.log($('.my-shop-consultation-splitpage .focus')[0].innerText);
        if($('.my-shop-consultation-splitpage .focus')[0].innerText != currentPageOfMyShopConsultationList) {
            currentPageOfMyShopConsultationList = $('.my-shop-consultation-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMyShopConsultation = 0;
            getMyShopConsultationList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfMyShopConsultationList = $('.my-shop-consultation-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMyShopConsultation = 0;
            getMyShopConsultationList();
        }
    });

    /** 回复的分页 **/
    // hrz
    $(".my-shop-consultation-splitpage >div").on("click", function(){
        console.log($('.my-shop-consultation-splitpage .focus')[1].innerText);
        if($('.my-shop-consultation-splitpage .focus')[1].innerText != currentPageMyShopConsultationReply) {
            currentPageMyShopConsultationReply = $('.my-shop-consultation-splitpage .focus')[1].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabMyShopConsultation = 0;
            // myShopConsultationhandleClick();
            $('.my-shop-consultation .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyShopConsultationSecond();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageMyShopConsultationReply = $('.my-shop-consultation-splitpage .focus')[1].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            // pageInOrTabMyShopConsultation = 0;
            // myShopConsultationhandleClick();
            $('.my-shop-consultation .replyContentHistory>div').not('.replyContentHistory-head, .oneMessage').remove()
            getMyShopConsultationSecond();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageMyShopConsultation () {
    $('.my-shop-consultation-splitpage >div').data('currentpage', 1);
    currentPageOfMyShopConsultationList = 1;
    $('.my-shop-consultation-splitpage >div').find('li[data-page="' + currentPageOfMyShopConsultationList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var myShopConsultationListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('my-shop-consultation')
    publishDemandListSearch();
});
function init_dom() {
    $('.my-shop-consultation .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="0"><span>未回复</span><span>1</span></li> <li data-typeId="1"><span>已回复</span><span>2</span></li>');
    $('.my-shop-consultation .personal-center-search-time').children().eq(0).html('咨询时间：');
    $('.my-shop-consultation .personal-center-search-list ul li').click(function () {
        $('.my-shop-consultation').find('ul li').removeClass('active')
        $(this).addClass('active')
        MyShopConsultationListTypeId = $(this).attr('data-typeId')

        pageInOrTabMyShopConsultation = 1
        currentPageOfMyShopConsultationList = 1

        getMyShopConsultationList()
    })
}

function publishDemandListSearch() {
    $('.my-shop-consultation').find('.searchByTime').click(function () {
        selectConditionOfMyShopConsultationList.searchVal = $('.my-shop-consultation').find('.searchByNameContent').val()
        selectConditionOfMyShopConsultationList.startTime = $('.my-shop-consultation').find('.search-star-time').val()
        selectConditionOfMyShopConsultationList.endTime = $('.my-shop-consultation').find('.search-end-time').val()

        pageInOrTabMyShopConsultation = 1
        currentPageOfMyShopConsultationList = 1

        getMyShopConsultationList();
    })
    $('.my-shop-consultation').find('.searchByName').click(function () {
        selectConditionOfMyShopConsultationList.searchVal = $('.my-shop-consultation').find('.searchByNameContent').val()

        pageInOrTabMyShopConsultation = 1
        currentPageOfMyShopConsultationList = 1

        getMyShopConsultationList();
    })
}

// 获取报名的需求列表
function getMyShopConsultationList () {
    var json = {
        "pager":{
            "current": currentPageOfMyShopConsultationList,
            "size": searchSizeOfMyShopConsultationList
        },
        "sortPointer":{
            "filed": "created_at",
            "order": "DESC"
        }
    }
    if (MyShopConsultationListTypeId) {
        json.reply = MyShopConsultationListTypeId
    }
    if (selectConditionOfMyShopConsultationList.searchVal) {
        json.title = selectConditionOfMyShopConsultationList.searchVal;
    }
    if (selectConditionOfMyShopConsultationList.startTime) {
        json.createdAtStart = selectConditionOfMyShopConsultationList.startTime;
    }
    if (selectConditionOfMyShopConsultationList.endTime) {
        json.createdAt = selectConditionOfMyShopConsultationList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/serviceProvidersConsulting/pc/query_provider_by_user_id_mine?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabMyShopConsultation === 0){
                $('.my-shop-consultation-splitpage >div').Paging({pagesize:searchSizeOfMyShopConsultationList,count:totalRecord,toolbar:true});
                $('.my-shop-consultation-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabMyShopConsultation === 1){
                $('.my-shop-consultation-splitpage >div').Paging({pagesize:searchSizeOfMyShopConsultationList,count:totalRecord,toolbar:true});
                $('.my-shop-consultation-splitpage >div').find("div:eq(0)").remove();
            }
            $('.my-shop-consultation-total').html("共" + totalRecord + "条");

            myShopConsultationListData = list;
            getMyShopConsultationListData();
            myShopConsultationhandleClick();
            if (list.length === 0) {
                $('.my-shop-consultation').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.my-shop-consultation').append(div)
                $('.my-shop-consultation-splitpage').css("display", "none")
                $('.my-shop-consultation-total').css("display", "none")
            } else {
                $('.my-shop-consultation').find('.noData').remove()
                $('.my-shop-consultation-splitpage').css("display", "block")
                $('.my-shop-consultation-total').css("display", "block")
            }
        }
    });
}

function getMyShopConsultationListData() {
    var data = [];
    var table = new Table('my-shop-consultation-table');
    var baseStyleArr = [];
    var arr = [];
    if (myShopConsultationListData != undefined && myShopConsultationListData.length != 0) {
        myShopConsultationListData.forEach(function(item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
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
            obj.provider = [item.provider,item.provider_id]
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
            arr = ['provider','user_id','contents','created_at','reply','id']
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
            span = '<span class="reply-message" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看信息</span>'
            return (label === 'td') ? span : content
        } else if (type=== 'provider') {
            var span = '<span title="'+content[0]+'" class="myShopConsultationListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function myShopConsultationhandleClick() {
    $(document).on('click','.myShopConsultationListToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/provider_detail.html?pc=true', '_self')
    })

    $('.my-shop-consultation').find('.reply-message').click(function () {

        console.log($(this).attr('data-id'));
        var idArr = $(this).attr('data-id').split(',');
        myShopConsultationArr = idArr;
        console.log(idArr);

        $('.my-shop-consultation div').removeClass('my-consultatio-show')
        $('.my-shop-consultation').children().eq(1).addClass('my-consultatio-show')
        $('.my-shop-consultation .replyContent').hide();
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
            url: "/f/serviceProvidersConsulting/pc/query_provider_by_user_id_reply_one?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // hrz
                currentPageMyShopConsultationReply = 1;
                $('.my-shop-consultation-splitpage >div').find("div:eq(0)").remove();

                $('.my-shop-consultation .replyContentHistory>div').not(":first").remove()
                console.log(res);
                var userInfo = null
                var messages = res.data.data_list
                if (messages.length > 0) {
                    myShopConsultationListCaseId = messages[0].provider_id;
                    var div2 = $('<div class="oneMessage"></div>')
                    var div = $('<div style="min-height: 40px;line-height: 40px"></div>');
                    var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>');
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>');
                    // var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    // var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[0].created_at)) + '</span>');
                    div.append(span);
                    // div.append(span1)
                    // div.append(span2)
                    div.append(span3);
                    var p = $('<p style="font-size: 14px;word-break: break-all">' + messages[0].contents + '</p>');
                    div1.append(p);
                    div2.append(div);
                    div2.append(div1);
                    $('.my-shop-consultation .replyContentHistory').append(div2);
                    // $(".consultation-result-list .replyContentHistory").append(div1);
                    getMyShopConsultationSecond();
                }
            }
        })
    })
}

function getMyShopConsultationSecond () {
    var obj ={
        'patentsId': myShopConsultationListCaseId,
        "pid": myShopConsultationArr[0],
        "pager":{
            "current":currentPageMyShopConsultationReply,
            "size":searchSizeMyShopConsultationReply,
        },    //每页条数
        "sortPointer":{
            "filed":"created_at",
            "order":"DESC"
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
            // if (pageInOrTabMyShopConsultation === 0){
            // }
            // if (pageInOrTabMyShopConsultation === 1){
            //     $('.my-shop-consultation-splitpage >div').Paging({pagesize:searchSizeOfMyShopConsultationList,count:totalRecord,toolbar:true});
            // $('.my-shop-consultation-splitpage >div').find("div:eq(0)").remove();
            // }
            if (res.data.total == 0) {
                $('.my-shop-consultation-splitpage >div').empty()
            }else {
                var totalRecord = res.data.total;
                $('.my-shop-consultation-splitpage >div').Paging({pagesize:searchSizeMyShopConsultationReply,count:totalRecord,toolbar:true});
                $('.my-shop-consultation-splitpage >div').find("div:eq(1)").remove();
            }

            var messages = res.data.data_list
            var userInfo = window.localStorage.getItem("user")
            // console.log(userInfo);
            // console.log(JSON.parse(userInfo).id);
            for (var i=0; i<messages.length; i++){
                var div = $('<div style="min-height: 40px;line-height: 40px"></div>')
                var div1 = $('<div style="min-height: 40px;line-height: 40px; background-color: #f4f4f4;padding-left: 60px" ></div>')
                if (JSON.parse(messages[i].user_id).id == JSON.parse(userInfo).id) {
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">咨询</span>')
                    var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].respondent).user_name + '</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                } else {
                    var span = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + JSON.parse(messages[i].user_id).user_name + '</span>')
                    var span1 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">回复</span>')
                    var span2 = $('<span style="color: #0066cc; font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">我</span>')
                    var span3 = $('<span style="font-size: 16px;display: inline-block;background-color: white;margin-left: 20px;">' + $(this).formatTime(new Date(messages[i].created_at)) + '</span>')
                }
                div.append(span)
                div.append(span1)
                div.append(span2)
                div.append(span3)
                var p = $('<p style="font-size: 14px;word-break: break-all">'+ messages[i].contents + '</p>')
                div1.append(p)
                $('.my-shop-consultation .replyContentHistory').append(div)
                $(".my-shop-consultation .replyContentHistory").append(div1)
            }
            $('.my-shop-consultation').find('.replyContent-submit').click(function () {
                console.log('a');
                var content = $('.my-shop-consultation').find('.replyContent-textarea').val()
                var obj = {}
                if (!!myShopConsultationArr[1]) {
                    obj.pid = parseInt(myShopConsultationArr[1])
                } else {
                    obj.pid = parseInt(myShopConsultationArr[2])
                }
                obj.providerId = myShopConsultationListCaseId
                obj.contents = filterSensitiveWord(content)
                if (obj.contents == '') {
                    layer.msg("回复内容不能为空")
                }else {
                    $('.my-shop-consultation .replyContent-submit').attr('disabled',true);
                    new NewAjax({
                        type: "POST",
                        url: "/f/patentsConsulting/pc/create_update?pc=true",
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        data: JSON.stringify(obj),
                        success: function (res) {
                            if(res.status === 200){
                                layer.msg("回复成功，即将返回页面")
                                $('.my-shop-consultation').find('.replyContent-textarea').val('');
                                setTimeout(function () {
                                    $('.my-shop-consultation .replyContent-submit').attr('disabled',false);
                                    $('.my-shop-consultation div').removeClass('my-consultatio-show')
                                    $('.my-shop-consultation').children().eq(0).addClass('my-consultatio-show')
                                    getMyShopConsultationList();
                                },500)
                            }
                        }
                    })
                }
            })
        }
    })
}