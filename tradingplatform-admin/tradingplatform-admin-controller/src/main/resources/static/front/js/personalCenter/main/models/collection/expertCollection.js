var currentPageOfExpertCollectionList = 1;
var searchSizeOfExpertCollectionList = 10;

var pageInOrTabExpertCollection = 0; // 判断跳页刷新还是取消收藏刷新，0为跳页

// 搜索条件对象
var selectConditionOfExpertCollectionList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".expert-collection-splitpage >div").on("click", function(){
        console.log($('.expert-collection-splitpage .focus')[0].innerText);
        if($('.expert-collection-splitpage .focus')[0].innerText != currentPageOfExpertCollectionList) {
            currentPageOfExpertCollectionList = $('.expert-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabExpertCollection = 0;
            getExpertCollectionList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfExpertCollectionList = $('.expert-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabExpertCollection = 0;
            getExpertCollectionList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageExpertCollection () {
    $('.expert-collection-splitpage >div').data('currentpage', 1);
    currentPageOfExpertCollectionList = 1;
    $('.expert-collection-splitpage >div').find('li[data-page="' + currentPageOfExpertCollectionList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var expertCollectionListData = []

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('expert-collection-list');
    expertCollectionSearch();
});

function expertCollectionSearch() {
    $('.expert-collection-list').find('.searchByTime').click(function () {
        selectConditionOfExpertCollectionList.searchVal = $('.expert-collection-list').find('.searchByNameContent').val()
        selectConditionOfExpertCollectionList.startTime = $('.expert-collection-list').find('.search-star-time').val()
        selectConditionOfExpertCollectionList.endTime = $('.expert-collection-list').find('.search-end-time').val()

        pageInOrTabExpertCollection = 1
        currentPageOfExpertCollectionList = 1

        getExpertCollectionList();
    })
    $('.expert-collection-list').find('.searchByName').click(function () {
        selectConditionOfExpertCollectionList.searchVal = $('.expert-collection-list').find('.searchByNameContent').val()

        pageInOrTabExpertCollection = 1
        currentPageOfExpertCollectionList = 1

        getExpertCollectionList();
    })
}

function init_dom () {
    $('.expert-collection-list .personal-center-search-list').remove();
    $('.expert-collection-list .personal-center-search-time').children().eq(0).html('收藏时间：');

    pageInOrTabExpertCollection = 0
    currentPageOfExpertCollectionList = 1

}
// 获取发布的需求列表
function getExpertCollectionList () {
    var json = {
        "pager": {
            "current": currentPageOfExpertCollectionList,
            "size": searchSizeOfExpertCollectionList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (selectConditionOfExpertCollectionList.searchVal) {
        json.expertName = selectConditionOfExpertCollectionList.searchVal;
    }
    if (selectConditionOfExpertCollectionList.startTime) {
        json.createdAtStart = selectConditionOfExpertCollectionList.startTime;
    }
    if (selectConditionOfExpertCollectionList.endTime) {
        json.createdAt = selectConditionOfExpertCollectionList.endTime;
    }
    json.is_collection = true
    new NewAjax({
        type: "POST",
        url: "/f/expertsCollection/pc/query_experts_collection_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabExpertCollection === 0){
                $('.expert-collection-splitpage >div').Paging({pagesize:searchSizeOfExpertCollectionList,count:totalRecord,toolbar:true});
                $('.expert-collection-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabExpertCollection === 1){
                $('.expert-collection-splitpage >div').Paging({pagesize:searchSizeOfExpertCollectionList,count:totalRecord,toolbar:true});
                $('.expert-collection-splitpage >div').find("div:eq(0)").remove();
            }
            $('.expert-collection-total').html("共" + totalRecord + "条");

            expertCollectionListData = list;
            setExpertCollectionList(list);
            setExpertCollectionListData();
            expertCollectionHandleClick();
            if (list.length === 0) {
                $('.expert-collection-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.expert-collection-list').append(div)
                $('.expert-collection-splitpage').css("display", "none")
                $('.expert-collection-total').css("display", "none")
            } else {
                $('.expert-collection-list').find('.noData').remove()
                $('.expert-collection-splitpage').css("display", "block")
                $('.expert-collection-total').css("display", "block")
            }
        }
    });
}

// 设置发布的需求列表
function setExpertCollectionList (list) {
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



function setExpertCollectionListData() {
    var data = []
    var table = new Table('expert-collection-list-table')
    var baseStyleArr = []
    if (expertCollectionListData != undefined && expertCollectionListData.length != 0) {
        expertCollectionListData.forEach(function(item){
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'experts_name':
                            styleItem.name = '专家姓名'
                            styleItem.width = 220
                            break
                        case 'technical_title':
                            styleItem.name = '专家职称'
                            styleItem.width = 120
                            break
                        case 'industry_name':
                            styleItem.name = '技术领域'
                            break
                        case 'created_at':
                            styleItem.name = '收藏时间'
                            styleItem.width = 120
                            break
                        case 'experts_id':
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
            obj.experts_name = [item.experts_name,item.experts_id]
            obj.technical_title = item.technical_title
            obj.industry_name = item.industry_name
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.experts_id = item.experts_id
            data.push(obj)
        })
    }
    var orderArr = ['experts_name','technical_title','industry_name','created_at','experts_id']
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label) {
        if (type === 'experts_id') {
            var span = '<span class="cancelCollection" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer">取消收藏</span>'
            return (label === 'td') ? span : content
        }else if (type=== 'experts_name') {
            var span = '<span title="'+content[0]+'" class="expertCollectionToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function expertCollectionHandleClick() {
    $(document).on('click','.expertCollectionToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/expert_detail.html?pc=true')
    })
    $('.expert-collection-list').find('.cancelCollection').click(function () {
        var experts_id = $(this).attr('data-id')
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var json = {
                isCollection: false,
                expertsId: experts_id
            }
            new NewAjax({
                type: "POST",
                url: "/f/experts/pc/collection_experts?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {

                        pageInOrTabExpertCollection = 1
                        currentPageOfExpertCollectionList = 1

                        getExpertCollectionList()
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