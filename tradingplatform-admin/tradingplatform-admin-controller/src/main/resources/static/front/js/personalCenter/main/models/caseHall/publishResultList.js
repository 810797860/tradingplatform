var currentPageOfPublishResultList = 1;
var searchSizeOfPublishResultList = 10;
var publishResultListTypeId;
var pageInOrTabPublishResult = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var deleted;

// 搜索条件对象
var selectConditionOfPublishResultList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".publish-result-splitpage >div").on("click", function(){
        // console.log($('.publish-result-splitpage .focus')[0].innerText);
        if($('.publish-result-splitpage .focus')[0].innerText != currentPageOfPublishResultList) {
            currentPageOfPublishResultList = $('.publish-result-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabPublishResult = 0;
            getPublishResultList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfPublishResultList = $('.publish-result-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabPublishResult = 0;
            getPublishResultList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPagePublishResult () {
    $('.publish-result-splitpage >div').data('currentpage', 1);
    currentPageOfPublishResultList = 1;
    $('.publish-result-splitpage >div').find('li[data-page="' + currentPageOfPublishResultList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}


// 用来存储设置表格的变量
var publishResultListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('publish-result-list')
    publishResultListSearch();
    // getSignUpCompanyInfo(2382);
});
function init_dom() {
    $('.publish-result-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="202049"><span>待审核</span><span>1</span></li> <li data-typeId="202050"><span>审核通过</span><span>2</span></li> <li data-typeId="202051"><span>审核不通过</span><span>3</span></li> <li data-deleted="1"><span>下架</span><span>3</span></li>');
    $('.publish-result-list .personal-center-search-time').children().eq(0).html('发布时间：');
    $('.publish-result-list .personal-center-search-list ul li').click(function () {
        $('.publish-result-list').find('ul li').removeClass('active')
        $(this).addClass('active')
        publishResultListTypeId = $(this).attr('data-typeId')
        if ($(this).attr('data-deleted')) {
            deleted = true
        } else {
            deleted = false
        }
        pageInOrTabPublishResult = 1
        currentPageOfPublishResultList = 1
        getPublishResultList()
    })
}

function publishResultListSearch() {
    $('.publish-result-list').find('.searchByTime').click(function () {
        selectConditionOfPublishResultList.searchVal = $('.publish-result-list').find('.searchByNameContent').val()
        selectConditionOfPublishResultList.startTime = $('.publish-result-list').find('.search-star-time').val()
        selectConditionOfPublishResultList.endTime = $('.publish-result-list').find('.search-end-time').val()

        pageInOrTabPublishResult = 1
        currentPageOfPublishResultList = 1

        getPublishResultList();
    })
    $('.publish-result-list').find('.searchByName').click(function () {
        selectConditionOfPublishResultList.searchVal = $('.publish-result-list').find('.searchByNameContent').val()

        pageInOrTabPublishResult = 1
        currentPageOfPublishResultList = 1

        getPublishResultList();
    })
}

// 获取发布的需求列表
function getPublishResultList () {
    var json = {
        "pager": {
            "current": currentPageOfPublishResultList,
            "size": searchSizeOfPublishResultList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (publishResultListTypeId) {
        json.backCheckStatus = publishResultListTypeId
    }
    if (deleted) {
        json.deleted = deleted
    }
    if (selectConditionOfPublishResultList.searchVal) {
        json.title = selectConditionOfPublishResultList.searchVal;
    }
    if (selectConditionOfPublishResultList.startTime) {
        json.createdAtStart = selectConditionOfPublishResultList.startTime;
    }
    if (selectConditionOfPublishResultList.endTime) {
        json.createdAt = selectConditionOfPublishResultList.endTime;
    }
    // console.log(json);
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseCheckRecords/pc/query_case_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            // console.log(list);

            if (pageInOrTabPublishResult === 0){
                $('.publish-result-splitpage >div').Paging({pagesize:searchSizeOfPublishResultList,count:totalRecord,toolbar:true});
                $('.publish-result-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabPublishResult === 1){
                $('.publish-result-splitpage >div').Paging({pagesize:searchSizeOfPublishResultList,count:totalRecord,toolbar:true});
                $('.publish-result-splitpage >div').find("div:eq(0)").remove();
            }
            $('.publish-result-total').html("共" + totalRecord + "条");

            publishResultListData = list;
            setPublishResultList(list);
            setPublishResultListData();
            publishResultListHandleClick();
            if (list.length === 0) {
                $('.publish-result-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.publish-result-list').append(div)
                $('.publish-result-splitpage').css("display", "none")
                $('.publish-result-total').css("display", "none")
            } else {
                $('.publish-result-list').find('.noData').remove()
                $('.publish-result-splitpage').css("display", "block")
                $('.publish-result-total').css("display", "block")
            }
            for (var i=0; i<publishResultListData.length; i++) {
                if (publishResultListData[i].deleted === true) {
                    $('.publish-result-list').find('.obtained').eq(i).html('上架')
                    $('.publish-result-list').find('.obtained').addClass('deleted')
                } else {
                    $('.publish-result-list').find('.obtained').html('下架')
                    $('.publish-result-list').find('.obtained').removeClass('deleted')
                }
            }
        }
    });
}

// 设置发布的需求列表
function setPublishResultList (list) {
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


// 获取报名者列表
function getSignUpList (id) {
    var json = {
        id: id
    }
    new NewAjax({
        type: "POST",
        url: "/f/projectResult/pc/get_by_project_id",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            // console.log(list);
            setSignUpList(list);
        }
    });
}

// 设置报名者列表
function setSignUpList (list) {

}

// 获取报名者详情
function getSignUpCompanyInfo (projectId) {
    var json = {
        projectId:projectId
    }
    new NewAjax({
        type: "POST",
        url: "/f/projectResultSignUp/pc/get_project_signup",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var data = res.data.data_object;
            if (data) {
                setSignUpCompanyInfo(data);
            }
        }
    });
}

// 设置报名者详情数据
function setSignUpCompanyInfo (object) {
    $(".sign-up-company-info .info-title").html(object.name);
    $(".sign-up-company-info .company-logo").attr('src', $(this).getAvatar(object.logo));
    $(".sign-up-company-info .info-phone").html(object.phone);
    $(".sign-up-company-info .desc").html(object.introduction);
    $(".sign-up-company-info .info-money").html(object.offer_price);
    $(".sign-up-company-info .info-time").html($(this).formatTime(new Date(object.created_at)).split(" ")[0]);

    var data = []
    var table = new Table('table-area')
    var baseStyleArr = []
    if (object.technical_solution_attachment != undefined) {
        // 提取数据
        JSON.parse(object.technical_solution_attachment).forEach(function(item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '文件名'
                            styleItem.width = 423
                            break
                        case 'size':
                            styleItem.name = '大小'
                            styleItem.width = 180
                            break
                        case 'id':
                            styleItem.name = '操作'
                            break
                        default:
                            styleItem.name = key
                            break
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.title = item.title + '.' + item.prefix
            obj.size = item.size
            obj.id = item.id
            data.push(obj)
        })
        // 决定数据顺序
        var orderArr = ['title','size','id']
        table.setTableData(data)
        table.setBaseStyle(baseStyleArr)
        table.setColOrder(orderArr)
        table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label){
            if (type === 'title') {
                return (label === 'td') ? '<span class="file-title">'+ content +'</span>' : content
            } else if (type === 'size') {
                return (label === 'td') ? content + 'KB' : content
            } else if (type === 'id') {
                var span = '<span class="see-file"><i class="icon-search"></i>查看</span> <span class="download-file"><i class="icon-download"></i>下载</span>'
                return (label === 'td') ? span : content
            }
        });
        table.createTable()
    }
}


function setPublishResultListData() {
    var data = []
    var table = new Table('publish-result-list-table')
    var baseStyleArr = []
    if (publishResultListData != undefined && publishResultListData.length != 0) {
        publishResultListData.forEach(function(item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '成果名称'
                            styleItem.width = 200
                            break
                        case 'skilled_label':
                            styleItem.name = '类型'
                            styleItem.width = 120
                            break
                        case 'case_money':
                            styleItem.name = '价格/万元'
                            styleItem.width = 100
                            break
                        case 'back_check_status':
                            styleItem.name = '状态'
                            styleItem.width = 100
                            break
                        case 'created_at':
                            styleItem.name = '发布时间'
                            break
                        case 'id':
                            styleItem.name = '操作'
                            break
                        default:
                            styleItem.name = key
                            break
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.title = [item.title, item.case_id]
            obj.skilled_label = JSON.parse(item.skilled_label).title
            obj.case_money = item.case_money
            obj.back_check_status = JSON.parse(item.back_check_status).title
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.id = [item.id,JSON.parse(item.back_check_status).id]
            data.push(obj)
        })
    }
    var orderArr = ['title','skilled_label','case_money','back_check_status','created_at','id']
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label){
        if (type === 'case_money') {
            if (content == 0) {
                content = '面议'
            }
            return (label === 'td') ? '￥'+content : content
        } else if (type === 'id') {
            var span
            if (content[1] === '202049' || content[1] === '202051'){
                span = '<span class="caseHallEdit" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>编辑</span>' +
                   '<span class="obtained" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>下架</span>'
            }else {
                span = '<span class="caseHallEdit" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>编辑</span>' +
                 '<span class="obtained" data-id="'+ content +'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>下架</span>'
            }

            return (label === 'td') ? span : content
        } else if (type=== 'title') {
            var span = '<span title="'+content[0]+'" class="publishResultListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function publishResultListHandleClick() {
    $(document).on('click','.publishResultListToDetail',function () {
        var id = $(this).attr('data-proId')
        console.log(id);
        window.open('/f/'+ id +'/case_detail.html?pc=true')
    })

    $('.publish-result-list').find('.caseHallEdit').click(function () {
        window.open('/f/matureCaseCheckRecords/pc/'+ $(this).attr('data-id').split(',')[0] +'/to_create_update.html?pc=true', '_self')
    })
    $('.publish-result-list').find('.obtained').click(function () {
        var json = [$(this).attr('data-id').split(',')[0]]
        if ($(this).hasClass('deleted')){
            layer.confirm('你确定要上架该成果？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                new NewAjax({
                    type: "POST",
                    url: "/f/matureCaseCheckRecords/batch_update?pc=true",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(json),
                    success: function (res) {
                        if(res.status === 200) {
                            getPublishResultList()
                            layer.closeAll()
                        } else {
                            layer.open({
                                title: '温馨提示',
                                content: '内部信息出错'
                            })
                        }
                    }
                });
            }, function(){
                layer.msg('取消了上架', {
                    time: 1000, //20s后自动关闭
                });
            });
        }else{
            layer.confirm('你确定要下架该成果？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                new NewAjax({
                    type: "POST",
                    url: "/f/matureCaseCheckRecords/batch_delete?pc=true",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(json),
                    success: function (res) {
                        if(res.status === 200) {
                            getPublishResultList()
                            layer.closeAll()
                        } else {
                            layer.open({
                                title: '温馨提示',
                                content: '内部信息出错'
                            })
                        }
                    }
                });
            }, function(){
                layer.msg('取消了下架', {
                    time: 1000, //20s后自动关闭
                });
            });
        }
    })
}