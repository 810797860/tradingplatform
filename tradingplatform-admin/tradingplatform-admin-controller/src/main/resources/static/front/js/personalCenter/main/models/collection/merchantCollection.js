var currentPageOfMerchantCollectionList = 1;
var searchSizeOfMerchantCollectionList = 10;

var pageInOrTabMerchantCollection = 0; // 判断跳页刷新还是取消收藏刷新，0为跳页

// 搜索条件对象
var selectConditionOfMerchantCollectionList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".merchant-collection-splitpage >div").on("click", function(){
        console.log($('.merchant-collection-splitpage .focus')[0].innerText);
        if($('.merchant-collection-splitpage .focus')[0].innerText != currentPageOfMerchantCollectionList) {
            currentPageOfMerchantCollectionList = $('.merchant-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMerchantCollection = 0;
            getMerchantCollectionList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfMerchantCollectionList = $('.merchant-collection-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabMerchantCollection = 0;
            getMerchantCollectionList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageMerchantCollection () {
    $('.merchant-collection-splitpage >div').data('currentpage', 1);
    currentPageOfMerchantCollectionList = 1;
    $('.merchant-collection-splitpage >div').find('li[data-page="' + currentPageOfMerchantCollectionList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 用来存储设置表格的变量
var merchantCollectionListData = []

$(function () {
    listenToPage();
    init_dom()
    initDateTimePicker('merchant-collection-list')
    merchantCollectionSearch();
});


function merchantCollectionSearch() {
    $('.merchant-collection-list').find('.searchByTime').click(function () {
        selectConditionOfMerchantCollectionList.searchVal = $('.merchant-collection-list').find('.searchByNameContent').val()
        selectConditionOfMerchantCollectionList.startTime = $('.merchant-collection-list').find('.search-star-time').val()
        selectConditionOfMerchantCollectionList.endTime = $('.merchant-collection-list').find('.search-end-time').val()

        pageInOrTabMerchantCollection = 1
        currentPageOfMerchantCollectionList = 1

        getMerchantCollectionList();
    })
    $('.merchant-collection-list').find('.searchByName').click(function () {
        selectConditionOfMerchantCollectionList.searchVal = $('.merchant-collection-list').find('.searchByNameContent').val()

        pageInOrTabMerchantCollection = 1
        currentPageOfMerchantCollectionList = 1

        getMerchantCollectionList();
    })
}


function init_dom () {
    $('.merchant-collection-list .personal-center-search-list').remove();
    $('.merchant-collection-list .personal-center-search-time').children().eq(0).html('收藏时间：');

    pageInOrTabMerchantCollection = 0
    currentPageOfMerchantCollectionList = 1

}
// 获取发布的需求列表
function getMerchantCollectionList () {
    var json = {
        "pager": {
            "current": currentPageOfMerchantCollectionList,
            "size": searchSizeOfMerchantCollectionList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (selectConditionOfMerchantCollectionList.searchVal) {
        json.name = selectConditionOfMerchantCollectionList.searchVal;
    }
    if (selectConditionOfMerchantCollectionList.startTime) {
        json.createdAtStart = selectConditionOfMerchantCollectionList.startTime;
    }
    if (selectConditionOfMerchantCollectionList.endTime) {
        json.createdAt = selectConditionOfMerchantCollectionList.endTime;
    }
    json.is_collection = true
    new NewAjax({
        type: "POST",
        url: "/f/serviceProvidersCollection/pc/query_providers_collection_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            console.log(list);

            if (pageInOrTabMerchantCollection === 0){
                $('.merchant-collection-splitpage >div').Paging({pagesize:searchSizeOfMerchantCollectionList,count:totalRecord,toolbar:true});
                $('.merchant-collection-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabMerchantCollection === 1){
                $('.merchant-collection-splitpage >div').Paging({pagesize:searchSizeOfMerchantCollectionList,count:totalRecord,toolbar:true});
                $('.merchant-collection-splitpage >div').find("div:eq(0)").remove();
            }
            $('.merchant-collection-total').html("共" + totalRecord + "条");

            merchantCollectionListData = list;
            setMerchantCollectionList(list);
            setMerchantCollectionListData();
            mercharCollectionHandleClick();
            if (list.length === 0) {
                $('.merchant-collection-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.merchant-collection-list').append(div)
                $('.merchant-collection-splitpage').css("display", "none")
                $('.merchant-collection-total').css("display", "none")
            } else {
                $('.merchant-collection-list').find('.noData').remove()
                $('.merchant-collection-splitpage').css("display", "block")
                $('.merchant-collection-total').css("display", "block")
            }
        }
    });
}

// 设置发布的需求列表
function setMerchantCollectionList (list) {
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

function setMerchantCollectionListData() {
    var data = []
    var table = new Table('merchant-collection-list-table')
    var baseStyleArr = []
    if (merchantCollectionListData != undefined && merchantCollectionListData.length != 0) {
        merchantCollectionListData.forEach(function(item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function(key){
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'provider_name':
                            styleItem.name = '服务商'
                            break
                        case 'address':
                            styleItem.name = '地区'
                            break
                        case 'phone':
                            styleItem.name = '手机'
                            styleItem.width = 160
                            break
                        case 'created_at':
                            styleItem.name = '收藏时间'
                            styleItem.width = 120
                            break
                        case 'providers_id':
                            styleItem.name = '操作'
                            styleItem.width = 120
                            break
                        default:
                            styleItem.name = key
                            break
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.provider_name = [item.provider_name,item.providers_id]
            obj.address = item.address
            obj.phone = item.phone
            obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
            obj.providers_id = item.providers_id
            data.push(obj)
        })
    }
    var orderArr = ['provider_name','address','phone','created_at','providers_id']
    table.setTableData(data)
    table.setBaseStyle(baseStyleArr)
    table.setColOrder(orderArr)
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function(type, content, label) {
        if (type === 'providers_id') {
            var span = '<span class="cancelCollection" data-id="'+content+'" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer">取消收藏</span>'
            return (label === 'td') ? span : content
        } else if (type=== 'provider_name') {
            var span = '<span title="'+content[0]+'" class="merchantCollectionToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    })
    table.createTable()
}

function mercharCollectionHandleClick() {
    $(document).on('click','.merchantCollectionToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/provider_detail.html?pc=true')
    })
    $('.merchant-collection-list').find('.cancelCollection').click(function () {
        var serviceProviders_id =  $(this).attr('data-id')
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var json = {
                isCollection: false,
                serviceProvidersId: serviceProviders_id
            }
            new NewAjax({
                type: "POST",
                url: "/f/serviceProviders/pc/collection_service_providers?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {

                        pageInOrTabMerchantCollection = 1
                        currentPageOfMerchantCollectionList = 1

                        getMerchantCollectionList()
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