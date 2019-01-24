var searchSize = 5;
var currentPage = 1;
// var intPage = 0;
var $_typeId = '202035'; // 分类里面的不限
// var $_address = "";
var clickPage = 0;  //判断是否点击页数,0为是
// var $_tabActive = 0;

// 0代表全部，202035代表区分类型
var selectAllIdArr = [0,202035];
// 存储类型搜索框传参
var typeSearchJson = {
    "types": [
        "c_business_service_providers"
    ],
    "fields": [],
    "filterFields": [],
    "page": 1,
    "size": 5,
    "sort": "DESC",
    "sortFields": []
};
// 存储行业数据
var aIndustry = [{id: 202052, title: '不限'}];
// 存储子行业数据
var aSubIndustry = [{id: 0, title: '不限'}];
// 获取地区数据
var addressData = window.ChineseDistricts;
// 储存地区分类
var cityArr = [{
    id: 0,
    title: '不限'
}];
// 存储类型多选框数据
var searchType = [
    {
        name: '行业',
        type: 'industry',
        active: 202052,
        data: aIndustry
    },
    {
        name: '子行业',
        type: 'subIndustry',
        active: 202035,
        data: aSubIndustry,
        linkMethod: function () {
            var _this = this,
                industryId = _this.selectData.industry,
                data = [];
            _this.subIndustry.forEach(function (item) {
                if (item.pid === industryId) {
                    data.push(item);
                }
            });
            return (data.length > 0) ? [{id: 0, title: '不限'}].concat(data) : null;
        }
    },
    {
        name: '按城市',
        type: 'city',
        active: 0,
        selectMore: false,
        data: cityArr
    },
    {
        name: '按地区',
        type: 'area',
        active: 0,
        selectMore: false,
        data: [{id: 0, title: '不限'}].concat([addressData]),
        linkMethod: function () {
            var _this = this,
                cityId = _this.selectData.city,
                data = _this.area[1][cityId],
                areaData = [{id: 0, title: '不限'}];
            if (data) {
                Object.keys(data).forEach(function (key) {
                    var obj = {};
                    obj.id = Number(key);
                    obj.title = data[key];
                    areaData.push(obj);
                });
                return areaData;
            } else {
                return null;
            }
        }
    }
];
// 存储页面上的筛选数据
var searchData = {};

// 记录上一次搜索的关键词
var lastSearchVal = "";


$(function () {
    // init_dom();
    extractIndustryData(industry);
    // 初始化搜索条件的存储
    initSearchData();
    _getCityArr();
    // 生成初始化多选框
    initTypeSearch();
    $('.search-area').hide();
    init_data();
    handleClickEvent();
    // 设置超出提示
    setTextOverTip();
});
// 提取行业数据
function extractIndustryData(data) {
    data.forEach(function (item) {
        if(item.pid === 202052) {
            aIndustry.push(item);
        } else {
            aSubIndustry.push(item);
        }
    })
}

/*** 初始化数据 ***/
function init_data () {
    // 获取查询字段
    getSearchValue();
    // 重新请求数据
    getServiceList();
    // 获取推荐列表
    getRecommandServiceList();
    toPeopelCenterPublishCase();
}

// 初始化搜索条件
function initSearchData() {
    searchData.type = 202035;
    searchData.province = 0;
    searchData.city = 0;
    searchData.area = 0;
    searchData.input = '';
    searchData.sort = [
        {
            name: 'is_recommend',
            value: true
        },
        {
            name: 'recommended_index',
            value: true
        }
    ]
}

/*** 复原currentPage ***/
function resetCurrentPage () {
    $('#pageToolbar').data('currentpage', 1);
    currentPage = 1;
    clickPage = 1;
    $('#pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
}

// 处理点击事件
function handleClickEvent () {
    //跳转到详情页
    $(".service-lib-card .information .company-name").click(function () {
        // 记录搜索关键词
        if ($(".search-both #search-input").val() && lastSearchVal !== $(".search-both #search-input").val()) {
            setSearchHotKey($(".search-both #search-input").val());
            lastSearchVal = $(".search-both #search-input").val();
        }
        var id = $(this).parents(".service-lib-card").data('id');
        // window.location.href = '/f/' + id + '/provider_detail.html';
        window.open('/f/' + id + '/provider_detail.html');
    });
    $(".company-style-change .company .company-main .company-name").click(function () {
        var id = $(this).parents(".company-style-change").data('id');
        // window.location.href = '/f/' + id + '/provider_detail.html';
        window.open('/f/' + id + '/provider_detail.html');
    });
    // 切换排序tab
    $(".search-result-area .tab .tab-item").on("click", function () {
        $(".search-result-area .tab .tab-item").removeClass("active");
        $(this).addClass("active");
        resetCurrentPage();
        if ($(this)[0].innerText === "关注度") {
            // getServiceList($_typeId, $("#search-input").val(), "collection_amount");
            searchData.sort = 'click_rate';
        } else if ($(this)[0].innerText === "接包数") {
            // getServiceList($_typeId, $("#search-input").val(), "updated_at")
            searchData.sort = 'task_amount';
        } else {
            // getServiceList($_typeId, $("#search-input").val())
            searchData.sort = [
                {
                    name: 'is_recommend',
                    value: true
                },
                {
                    name: 'recommended_index',
                    value: true
                }
            ];
        }
        // getServiceList($_typeId, $("#search-input").val());
        getServiceList();
    });
    // 监听分页跳转
    $("#pageToolbar").on("click", function(){
        if($(this).data('currentpage') != currentPage) {

            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            // getServiceList($_typeId, $("#search-input").val());
            getServiceList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            console.log(currentPage);
            // getServiceList($_typeId, $("#search-input").val());
            getServiceList();
        }
    });
}

function _getCityArr () {
    var data = addressData['440000'];
    // 初始化省数组
    if (cityArr.length > 1) {
        cityArr.splice(1, cityArr.length - 1);
    }
    Object.keys(data).forEach(function(key) {
        var obj = {};
        obj.id = Number(key);
        obj.title = data[key];
        cityArr.push(obj);
    })
}
/***
 * 处理类型多选框
 */
function initTypeSearch() {
    // 引入搜索框
    var typeSearch = new TypeSearch();
    // 初始化头部类型多选框
    typeSearch.setData(searchType, false);
    // 设置点击回调
    typeSearch.setClickCallback(function(node, parentNode){
        var optionId = node.data('id');
        var typeName = parentNode.attr('type');
        if (searchData[typeName] instanceof Array) {
            var index = $.inArray(optionId, searchData[typeName]);
            // 若是不限的id
            if ($.inArray(optionId, selectAllIdArr) > -1) {
                searchData[typeName] = [optionId]
            } else {
                // 若存在不限的id，则情况数组
                if ($.inArray(searchData[typeName][0], selectAllIdArr) > -1) {
                    searchData[typeName] = []
                }
                if (index > -1) {
                    searchData[typeName].splice(index, 1)
                } else {
                    searchData[typeName].push(optionId)
                }
            }
        } else {
            // 若是省份/城市点击
            if (typeName === 'province') {
                searchData.city = 0;
                searchData.area = 0
            } else if (typeName === 'city'){
                searchData.area = 0
            }
            if (typeName === 'province' || typeName === 'city' || typeName === 'area') {
                optionId = node.text()
            }
            if (typeName === 'industry' && searchData.subIndustry) {
                searchData.subIndustry = undefined;
                delete searchData.subIndustry;
            }
            // 记录点击的分类数据
            searchData[typeName] = optionId
        }
        // 初始化分页的记录
        resetCurrentPage();
        // 重新请求数据
        getServiceList();
        // console.log('clickCallback', optionId)
    });
    // 设置搜索按钮的click回调
    typeSearch.setSearchBtnClickCallback(function(inputNode) {
        if (inputNode.val() === searchData.input){
            return 0
        }
        // 赋值
        searchData.input = inputNode.val();
        // 初始化分页的记录
        resetCurrentPage();
        // 重新请求数据
        getServiceList();
    });
    // 设置搜索input的keyDown回调
    typeSearch.setSearchInputKeyDownCallback(function(event,inputNode) {
        if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
            searchData.input = inputNode.val();
            // 初始化分页的记录
            resetCurrentPage();
            // 重新请求数据
            getServiceList();
        }
    })
}

/**  获取商库列表数据  **/
function getServiceList () {
    // 初始化
    typeSearchJson.fields = [
        {
            "fields": ["back_check_status_id"],
            "values": ["202050"],
            "searchType": "term"
        }
    ];
    Object.keys(searchData).forEach(function(key) {
        var obj = {
            values: (searchData[key] instanceof Array) ? searchData[key] : [searchData[key]],
            searchType: 'term'
        }
        // 类型
        if (key === 'industry' || key === 'subIndustry') {
            // console.log(obj.values)
            if (obj.values[0] !== 202052 && obj.values[0] !== 0) {
                // obj.fields = [(key === 'type') ? 'skilled_label_id' : 'application_industry_id']
                if (key === 'industry') {
                    obj.fields = ['industry_id_id'];
                } else {
                    obj.fields = ['sub_industry_id_id'];
                    /*obj.values[0] = "/.*" + obj.values[0] + ".*!/";      //搜索引擎正则查询
                    obj.searchType = 'stringQuery';     //正则查询是使用模糊匹配*/
                }
                typeSearchJson.fields.push(obj);
            }
            // 地址
        } else if (key === 'category') {
            if (obj.values[0] !== 202138) {
                obj.fields = ['category_id'];
                typeSearchJson.fields.push(obj)
            }
        } else if (key === 'province' || key === 'city' || key === 'area') {
            if (obj.values[0] !== 0 && obj.values[0] !== '不限') {
                obj.fields = [(key === 'province') ? 'province_name' : (key === 'city') ? 'city_name' : 'district_name'];
                typeSearchJson.fields.push(obj)
            }
            //  标题
        } else if (key === 'input') {
            // 没有内容，放空；否则，传参
            if (obj.values[0] !== '') {
                obj.fields = ['name'];
                obj.searchType = 'stringQuery';
                typeSearchJson.fields.push(obj);
                typeSearchJson.highlightBuilder = {
                    fields: [
                        {
                            name: "name",
                            preTags: [
                                "<span class='high-light'>"
                            ],
                            postTags: [
                                "</span>"
                            ]
                        }
                    ]
                }
            }
            // tabs
        } else if (key === 'sort') {
            if (typeof searchData[key] === "string") {
                typeSearchJson.sort = "DESC";
                typeSearchJson.sortFields = [searchData[key]];
                typeSearchJson.sortObjects = undefined
            } else {
                var arr = [{field: "_score", direction: "DESC"}];
                if (searchData[key] instanceof Array) {
                    searchData[key].forEach(function(item) {
                        var obj = {};
                        obj.field = item.name;
                        obj.direction = item.value ? "DESC" : "ASC";
                        arr.push(obj)
                    })
                } else if (typeof searchData[key] === "object") {
                    var obj = {};
                    obj.field = searchData[key].name;
                    obj.direction = searchData[key].value ? "DESC" : "ASC"
                }
                typeSearchJson.sort = undefined;
                typeSearchJson.sortFields = undefined;
                typeSearchJson.sortObjects = arr
            }
        }
    });
    // 页数
    typeSearchJson.page = currentPage;
    // 每页数量
    typeSearchJson.size = searchSize;
    new NewAjax({
        type: "POST",
        url: "/searchEngine/customSearch",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(typeSearchJson),
        success: function (res) {
            var totalRecord = res.data.data_object.totalRecord;
            var data = res.data.data_object.data;
            // $("#result-number").html(totalRecord);
            /* 传的数据总量 开始 */

            /*
            if(clickPage === 0){
                $('#pageToolbar').Paging({pagesize:searchSize,count:totalRecord,toolbar:true});
                $('#pageToolbar').find("div:eq(1)").remove();

            }else {
                $('#pageToolbar').Paging({pagesize:searchSize,count:totalRecord,toolbar:true});
                $('#pageToolbar').find("div:eq(0)").remove();
                clickPage = 0;
            }
            */
            if ($('#pageToolbar').find("div").length === 0) {
                $('#pageToolbar').Paging({pagesize:searchSize,count:totalRecord,toolbar:true});

            }else if (clickPage !== 0) {
                $('#pageToolbar').find("div").remove();
                $('#pageToolbar').Paging({pagesize: searchSize, count: totalRecord, toolbar: true});
            }
            /* 传的数据总量 结束 */
            // console.log(totalRecord);
            // console.log(data);
            setServiceListToPage(data);
        }
    })
}

/**  设置商库列表  **/
function setServiceListToPage (list) {
    var serviceLibCards = $("#search-result-list").children(".service-lib-card");
    // 隐藏不需要的元素
    for (var k = 0; k < serviceLibCards.length; k++) {
        if (list.length < searchSize && k >= list.length) {
            $(serviceLibCards[k]).css("display", 'none')
        } else {
            $(serviceLibCards[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(serviceLibCards[i]).attr("data-id", list[i]['data_id']); // id
        $(serviceLibCards[i]).find(".technology-area").html('行业：' + (list[i]['industry_id'] ? list[i]['industry_id'] : '暂无数据'));   // 技术领域
        $(serviceLibCards[i]).find(".company-name").html(list[i]['name_highLight'] ? list[i]['name_highLight'] : list[i]['name']); //公司名
        $(serviceLibCards[i]).find(".company-name").attr('title', list[i]['name']); //公司名
        $(serviceLibCards[i]).find(".bag-number").html(list[i]['task_amount'] ? list[i]['task_amount'] : '0'); //接包数
        $(serviceLibCards[i]).find(".area").html('地区：' + list[i]['address']); //地址
        $(serviceLibCards[i]).find(".power-number").html(list[i]['qualification'] ? list[i]['qualification'] : '暂无数据'); //能力值
        $(serviceLibCards[i]).find(".money-number").html(list[i]['total_transaction'] ? "￥" + list[i]['total_transaction'] + " 万元" : "面议"); //交易总额
        if (list[i]['logo']){
            $(serviceLibCards[i]).find(".company-logo").attr("src", $(this).getAvatar(JSON.parse(list[i]['logo'])[0]['id']));
        }
    }
}

/**  获取右栏推荐服务列表数据  **/
function getRecommandServiceList () {
    var json = {
        "pager":{//分页信息
            "current": 1,   //当前页数0
            "size": 5        //每页条数
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/serviceProviders/get_recommend_providers",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            // console.log(list);
            setRecommendServiceList(list);
        }
    })
}

// 去个人中心的我的商铺
function toPeopelCenterPublishCase() {
    $('.service-recommand-main .first-botton').click(function () {
        var href = $(this).attr('data-href');
        new NewAjax({
            url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
            contentType: 'application/json',
            type: 'get',
            success: function (res) {
                if (res.data.data_object !== null && !!res.data.data_object.back_check_status){
                    if (JSON.parse(res.data.data_object.back_check_status).id == 202050){
                        window.open('/f/personal_center.html?pc=true&menu=myShopList', '_self');
                    } else {
                        pTipMessage('提示', '您未通过身份认证', 'warning', 2000, true);
                        setTimeout(function () {
                            window.open('/f/personal_center.html?pc=true&menu=authentication', '_self');
                        }, 1500)
                    }
                } else {
                    pTipMessage('提示', '您未通过身份认证', 'warning', 2000, true);
                    setTimeout(function () {
                        window.open('/f/personal_center.html?pc=true&menu=authentication', '_self');
                    }, 1500)
                }
            },
            error: function (err) {
                console.error('身份验证请求数据失败，err：' + err)
            }
        })
    })
}

/**  设置右栏推荐服务列表  **/
function setRecommendServiceList (list) {
    var companyStyleChangeCards = $(".company-style-change");
    // 隐藏不需要的元素
    for (var k = 0; k < companyStyleChangeCards.length; k++) {
        if (list.length < 5 && k >= list.length) {
            $(companyStyleChangeCards[k]).css("display", 'none')
        } else {
            $(companyStyleChangeCards[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(companyStyleChangeCards[i]).attr("data-id", list[i]['id']); // id
        $(companyStyleChangeCards[i]).find(".company-name").html(list[i]['name']);  // 标题
        $(companyStyleChangeCards[i]).find(".company-name").attr('title',list[i]['name']);  // 标题
        if (!!list[i]['industry_id']) {
            $(companyStyleChangeCards[i]).find(".company-field").html('行业：' + JSON.parse(list[i]['industry_id']).title);  // 技术领域
        } else {
            $(companyStyleChangeCards[i]).find(".company-field").html('行业：暂无数据');  // 能力值
        }
        $(companyStyleChangeCards[i]).find(".company-logo").attr('src', list[i]['logo'] ? $(this).getAvatar(JSON.parse(list[i]['logo'])[0]['id']) : null);  // 图片
    }
}

/******* 设置搜索关键词 *******/
function setSearchHotKey (keyWords) {
    var json = {
        "keyWords": keyWords
    };
    new NewAjax({
        type: "POST",
        url: "/f/serviceProvidersSearchRecord/create_update?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {}
    })
}

// 获取url参数进行条件查询
function getSearchValue() {
    var url = window.location.href;
    var stSave = url.match(/search=[^&]*/g);
    var searchStr = (stSave) ? stSave[0] : null;
    var searchValue = null;
    var nSearchInput = $('#search-input');
    if (searchStr) {
        stSave = searchStr.split('=');
        if (stSave.length > 1) {
            searchValue = decodeURIComponent(stSave[1]);
            nSearchInput.val(searchValue);
            searchData.input = searchValue;
        }
    }
}

// 设置文本超出提示
function setTextOverTip() {
    setTextOverTipOfCompanyList();
    setTextOverTipOfRecommandCompanyList();
}

// 绑定企业父框事件
function setTextOverTipOfCompanyList() {
    // 获取列表父框
    var nListParent = $('#search-result-list').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfCompanyTextOver);
}

// 推荐企业文本超出事件
function eventOfCompanyTextOver(event) {
    // 获取当前作用节点
    var nNowActive = null;
    // 节点标签名
    var nodeName = event.target.tagName.toLowerCase();
    if (nodeName === 'div' && $(event.target).hasClass('text-overflow')) {
        nNowActive = $(event.target);
        layer.closeAll();
        layer.tips(nNowActive.text(), nNowActive, {
            tips: [1, '#000000']
        });
    }
}

// 绑定推荐企业父框事件
function setTextOverTipOfRecommandCompanyList() {
    // 获取列表父框
    var nListParent = $('.content-right .service-recommand').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfRecommandCompanyTextOver);
}

// 推荐企业文本超出事件
function eventOfRecommandCompanyTextOver(event) {
    // 获取当前作用节点
    var nNowActive = null;
    // 节点标签名
    var nodeName = event.target.tagName.toLowerCase();
    if (nodeName === 'div' && $(event.target).hasClass('company-name')) {
        nNowActive = $(event.target);
        layer.closeAll();
        layer.tips(nNowActive.text(), nNowActive, {
            tips: [1, '#000000']
        });
    }
}