var currentPageOfDemandCollectionList = 1;
var searchSizeOfDemandCollectionList = 10;

var pageInOrTabDemandCollection = 0; // 判断跳页刷新还是取消收藏刷新，0为跳页

// 搜索条件对象
var selectConditionOfDemandCollectionList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".demand-collection-splitpage >div").on("click", function(){
        console.log($('.demand-collection-splitpage .focus')[0].innerText);
        if($('.demand-collection-splitpage .focus')[0].innerText != currentPageOfDemandCollectionList) {
            currentPageOfDemandCollectionList = $('.demand-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabDemandCollection = 0;
            getDemandCollectionList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfDemandCollectionList = $('.demand-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabDemandCollection = 0;
            getDemandCollectionList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageDemandCollection () {
    $('.demand-collection-splitpage >div').data('currentpage', 1);
    currentPageOfDemandCollectionList = 1;
    $('.demand-collection-splitpage >div').find('li[data-page="' + currentPageOfDemandCollectionList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var demandCollectionListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('demand-collection-list')
    demandCollectionSearch();
});
function init_dom () {
    $('.demand-collection-list .personal-center-search-list').remove();
    $('.demand-collection-list .personal-center-search-time').children().eq(0).html('收藏时间：');

    pageInOrTabDemandCollection = 0
    currentPageOfDemandCollectionList = 1

}

function demandCollectionSearch() {
    $('.demand-collection-list').find('.searchByTime').click(function () {
        selectConditionOfDemandCollectionList.searchVal = $('.demand-collection-list').find('.searchByNameContent').val()
        selectConditionOfDemandCollectionList.startTime = $('.demand-collection-list').find('.search-star-time').val()
        selectConditionOfDemandCollectionList.endTime = $('.demand-collection-list').find('.search-end-time').val()

        pageInOrTabDemandCollection = 1
        currentPageOfDemandCollectionList = 1

        getDemandCollectionList();
    })
    $('.demand-collection-list').find('.searchByName').click(function () {
        selectConditionOfDemandCollectionList.searchVal = $('.demand-collection-list').find('.searchByNameContent').val()

        pageInOrTabDemandCollection = 1
        currentPageOfPublishDemandList = 1

        getDemandCollectionList();
    })
}

// 获取发布的需求列表
function getDemandCollectionList () {
    var json = {
        "pager": {
            "current": currentPageOfDemandCollectionList,
            "size": searchSizeOfDemandCollectionList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (selectConditionOfDemandCollectionList.searchVal) {
        json.name = selectConditionOfDemandCollectionList.searchVal;
    }
    if (selectConditionOfDemandCollectionList.startTime) {
        json.createdAtStart = selectConditionOfDemandCollectionList.startTime;
    }
    if (selectConditionOfDemandCollectionList.endTime) {
        json.createdAt = selectConditionOfDemandCollectionList.endTime;
    }
    json.is_collection = true,
    new NewAjax({
        type: "POST",
        url: "/f/projectDemandCollection/pc/query_demand_collection_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabDemandCollection === 0){
                $('.demand-collection-splitpage >div').Paging({pagesize:searchSizeOfDemandCollectionList,count:totalRecord,toolbar:true});
                $('.demand-collection-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabDemandCollection === 1){
                $('.demand-collection-splitpage >div').Paging({pagesize:searchSizeOfDemandCollectionList,count:totalRecord,toolbar:true});
                $('.demand-collection-splitpage >div').find("div:eq(0)").remove();
            }
            $('.demand-collection-total').html("共" + totalRecord + "条");

            demandCollectionListData = list;
            setDemandCollectionListData();
            demandCollectionHandleClick();
            if (list.length === 0) {
                $('.demand-collection-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.demand-collection-list').append(div)
                $('.demand-collection-splitpage').css("display", "none")
                $('.demand-collection-total').css("display", "none")
            } else {
                $('.demand-collection-list').find('.noData').remove()
                $('.demand-collection-splitpage').css("display", "block")
                $('.demand-collection-total').css("display", "block")
            }
        }
    });
}

// 设置发布的需求列表

function setDemandCollectionListData() {
    var data = []
    var table = new Table('demand-collection-list-table')
    var baseStyleArr = []
    if (demandCollectionListData != undefined && demandCollectionListData.length != 0) {
        demandCollectionListData.forEach(function(item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'name':
                            styleItem.name = '需求名称'
                            styleItem.width = 220
                            break
                        case 'deadline':
                            styleItem.name = '报名截止时间'
                            styleItem.width = 120
                            break
                        case 'budget_amount_start':
                            styleItem.name = '金额/万元'
                            break
                        case 'industry_field':
                            styleItem.name = '需求类型'
                            styleItem.width = 120
                            break
                        case 'created_at':
                            styleItem.name = '收藏时间'
                            styleItem.width = 120
                            break
                        case 'project_id':
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
            obj.name = [item.name,item.project_id]
            obj.deadline = $(this).formatTime(new Date(item.deadline)).split(' ')[0]
            if (item.budget_amount_start === item.budget_amount && item.budget_amount_start === 0) {
                obj.budget_amount_start = '面议'
            } else {
                obj.budget_amount_start = item.budget_amount_start + '-' + item.budget_amount
            }
            obj.industry_field = item.industry_field
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.project_id = item.project_id
            data.push(obj)
        })
    }
    var orderArr = ['name','deadline','budget_amount_start','industry_field','created_at','project_id']
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label) {
        if (type === 'project_id') {
            var span = '<span class="cancelCollection" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer">取消收藏</span>'
            return (label === 'td') ? span : content
        } else if (type === 'name') {
            var span = '<span title="'+content[0]+'" class="demandCollectionToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function demandCollectionHandleClick() {
    $(document).on('click','.demandCollectionToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/demand_detail.html?pc=true')
    })

    $('.demand-collection-list').find('.cancelCollection').click(function () {
        console.log($(this).attr('data-id'));
        var project_id = $(this).attr('data-id')
        console.log($(this).attr('data-id'));
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var json = {
                isCollection: false,
                projectId: project_id
            }
            new NewAjax({
                type: "POST",
                url: "/f/projectDemand/pc/collection_project_demand?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {

                        pageInOrTabDemandCollection = 1
                        currentPageOfDemandCollectionList = 1

                        getDemandCollectionList()
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
            layer.msg('取消了这次删除', {
                time: 1000, //20s后自动关闭
            });
        });
    })
}