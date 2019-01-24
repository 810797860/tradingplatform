var currentPageOfCaseCollectionList = 1;
var searchSizeOfCaseCollectionList = 10;

var pageInOrTabCaseCollection = 0; // 判断跳页刷新还是取消收藏刷新，0为跳页

// 搜索条件对象
var selectConditionOfCaseCollectionList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".case-collection-splitpage >div").on("click", function(){
        console.log($('.case-collection-splitpage .focus')[0].innerText);
        if($('.case-collection-splitpage .focus')[0].innerText != currentPageOfCaseCollectionList) {
            currentPageOfCaseCollectionList = $('.case-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabCaseCollection = 0;
            getCaseCollectionList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfCaseCollectionList = $('.case-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabCaseCollection = 0;
            getCaseCollectionList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageCaseCollection () {
    $('.case-collection-splitpage >div').data('currentpage', 1);
    currentPageOfCaseCollectionList = 1;
    $('.case-collection-splitpage >div').find('li[data-page="' + currentPageOfCaseCollectionList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var caseCollectionListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('case-collection-list')
    caseCollectionSearch();
});

function caseCollectionSearch() {
    $('.case-collection-list').find('.searchByTime').click(function () {
        selectConditionOfCaseCollectionList.searchVal = $('.case-collection-list').find('.searchByNameContent').val()
        selectConditionOfCaseCollectionList.startTime = $('.case-collection-list').find('.search-star-time').val()
        selectConditionOfCaseCollectionList.endTime = $('.case-collection-list').find('.search-end-time').val()

        pageInOrTabCaseCollection = 1
        currentPageOfCaseCollectionList = 1

        getCaseCollectionList();
    })
    $('.case-collection-list').find('.searchByName').click(function () {
        selectConditionOfCaseCollectionList.searchVal = $('.case-collection-list').find('.searchByNameContent').val()

        pageInOrTabCaseCollection = 1
        currentPageOfCaseCollectionList = 1

        getCaseCollectionList();
    })
}

function init_dom () {
    $('.case-collection-list .personal-center-search-list').remove();
    $('.case-collection-list .personal-center-search-time').children().eq(0).html('收藏时间：');

    pageInOrTabCaseCollection = 0
    currentPageOfCaseCollectionList = 1

}
// 获取发布的需求列表
function getCaseCollectionList () {
    var json = {
        "pager": {
            "current": currentPageOfCaseCollectionList,
            "size": searchSizeOfCaseCollectionList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (selectConditionOfCaseCollectionList.searchVal) {
        json.title = selectConditionOfCaseCollectionList.searchVal;
    }
    if (selectConditionOfCaseCollectionList.startTime) {
        json.createdAtStart = selectConditionOfCaseCollectionList.startTime;
    }
    if (selectConditionOfCaseCollectionList.endTime) {
        json.createdAt = selectConditionOfCaseCollectionList.endTime;
    }
    json.is_collection = true
    new NewAjax({
        type: "POST",
        url: "/f/matureCaseCollection/pc/query_case_collection_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list)

            if (pageInOrTabCaseCollection === 0){
                $('.case-collection-splitpage >div').Paging({pagesize:searchSizeOfCaseCollectionList,count:totalRecord,toolbar:true});
                $('.case-collection-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabCaseCollection === 1){
                $('.case-collection-splitpage >div').Paging({pagesize:searchSizeOfCaseCollectionList,count:totalRecord,toolbar:true});
                $('.case-collection-splitpage >div').find("div:eq(0)").remove();
            }
            $('.case-collection-total').html("共" + totalRecord + "条");

            caseCollectionListData = list;
            setCaseCollectionListData();
            caseCollectionHandleClick();
            if (list.length === 0) {
                $('.case-collection-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.case-collection-list').append(div)
                $('.case-collection-splitpage').css("display", "none")
                $('.case-collection-total').css("display", "none")
            } else {
                $('.case-collection-list').find('.noData').remove()
                $('.case-collection-splitpage').css("display", "block")
                $('.case-collection-total').css("display", "block")
            }
        }
    });
}

function setCaseCollectionListData() {
    var data = []
    var table = new Table('case-collection-list-table')
    var baseStyleArr = []
    console.log(caseCollectionListData);
    if (caseCollectionListData != undefined && caseCollectionListData.length != 0) {
        caseCollectionListData.forEach(function(item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '成果名称'
                            styleItem.width = 220
                            break
                        case 'case_money':
                            styleItem.name = '金额/万元'
                            styleItem.width = 120
                            break
                        case 'application_industry':
                            styleItem.name = '成果类型'
                            break
                        case 'created_at':
                            styleItem.name = '收藏时间'
                            styleItem.width = 120
                            break
                        case 'case_id':
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
            obj.title = [item.title,item.case_id]
            obj.case_money = item.case_money
            obj.application_industry = []
            for (var i=0 ; i<JSON.parse(item.application_industry).length; i++) {
                obj.application_industry.push(JSON.parse(item.application_industry)[i].title)
            }
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.case_id = item.case_id
            data.push(obj)
        })
    }
    var orderArr = ['title','case_money','application_industry','created_at','case_id']
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label){
        if (type === 'case_id') {
            var span = '<span class="cancelCollection" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer">取消收藏</span>'
            return (label === 'td') ? span : content
        } else if (type=== 'title') {
            var span = '<span title="'+content[0]+'" class="caseCollectionToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function caseCollectionHandleClick() {
    $(document).on('click','.caseCollectionToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/case_detail.html?pc=true')
    })

    $('.case-collection-list').find('.cancelCollection').click(function () {
        console.log($(this).attr('data-id'));
        var matureCase_id = $(this).attr('data-id')
        console.log($(this).attr('data-id'));
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var json = {
                isCollection: false,
                matureCaseId: matureCase_id
            }
            new NewAjax({
                type: "POST",
                url: "/f/matureCase/pc/collection_mature_case?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {

                        pageInOrTabCaseCollection = 1
                        currentPageOfCaseCollectionList = 1

                        getCaseCollectionList()
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