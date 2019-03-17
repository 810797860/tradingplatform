$(function () {
    var searchSize = 8;
    var currentPage = 1;
    var $_typeId = '202035';
    var $_priceSortIsDesc = false; // 价格是否降序，desc: 降序
    // var $_tabActive = 0;    // 记录在哪个tab页
    var clickPage = 0;  //判断是否点击页数,0为是
    // 存储全选的id数组
    var selectAllIdArr = [0, 202035, 202066];
    // 存储类型搜索框传参
    var typeSearchJson = {
        "types": [
            "c_business_project_demand"
        ],
        "fields": [],
        "filterFields": [],
        "page": 1,
        "size": 9,
        // "sort": "DESC",
        "sortObjects": [{field: "_score", direction: "DESC"}, {field: "status_id", direction: "ASC"}]
    };
    var cityArr = [{
        id: 0,
        title: '不限'
    }];
    var addressData = window.ChineseDistricts;
    // 存储行业数据
    var aIndustry = [{id: 202052, title: '不限'}];
    // 存储子行业数据
    var aSubIndustry = [{id: 0, title: '不限'}];
    // 存储状态数据
    var aStatusType = [{id: 202066, title: '不限'}];
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
            name: '状态',
            type: 'status',
            active: 202066,
            data: aStatusType
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

    /*=== 函数调用 ===*/
    extractIndustryData(industry);
    extractStatusData(statusType);
    // 初始化搜索条件的存储
    initSearchData();

    _getCityArr();
    // 生成初始化多选框
    initTypeSearch();
    // 请求初始化数据
    init_data();
    // 事件调用
    handleEvent();
    // 设置超出提示
    setTextOverTip();

    /*** 初始化数据 ***/
    function init_data() {
        $('.search-area').hide();
        /*var storage = window.localStorage;
        var searchVal = storage.getItem("searchVal");
        if (searchVal) {
            $(".search-both #search-input").val(searchVal);
            searchData.input = searchVal;
            storage.removeItem("searchVal");
            $('.search-both #search-btn').trigger("click");
        }*/
        getSearchValue();
        // 初始化分页的记录
        resetCurrentPage();
        // 重新请求数据
        getDemandList();
        // 获取优质需求列表
        getRecommendDemandList();
        getNewRecommendDemandList();
    }

    // 提取广东省城市数据
    function _getCityArr() {
        var data = addressData['440000'];
        // 初始化省数组
        if (cityArr.length > 1) {
            cityArr.splice(1, cityArr.length - 1);
        }
        Object.keys(data).forEach(function (key) {
            var obj = {};
            obj.id = Number(key);
            obj.title = data[key];
            cityArr.push(obj);
        })
    }

    // 提取行业数据
    function extractIndustryData(data) {
        data.forEach(function (item) {
            if (item.pid === 202052) {
                aIndustry.push(item);
            } else {
                aSubIndustry.push(item);
            }
        })
    }

    // 提取状态数据
    function extractStatusData(data) {
        data.forEach(function (item) {
            if (item.id === 202069 || item.id === 202074) {
                aStatusType.push(item);
            }
        });
    }

    // 初始化搜索条件
    function initSearchData() {
        searchData.type = 202035;
        searchData.status = 202066;
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
            },
            {
                name: 'created_at',
                value: true
            },
            {
                name: 'click_rate',
                value: true
            }
        ]
    }

    /*** 复原currentPage ***/
    function resetCurrentPage() {
        clickPage = 1;
        $('#pageToolbar').data('currentpage', 1);
        currentPage = 1;
        $('#pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
    }

    /*** 处理事件 ***/
    function handleEvent() {
        /*** 跳转到详情页 开始 ***/
        $(".demand-card .title").off().on("click", function () {
            if ($(".search-both #search-input").val() && lastSearchVal !== $(".search-both #search-input").val()) {
                setSearchHotKey($(".search-both #search-input").val());
                lastSearchVal = $(".search-both #search-input").val();
            }
            var id = $(this).parents(".demand-card").data('id');
            // window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
            window.open('/f/' + id + '/demand_detail.html?pc=true');
        });
        $(".recommend-demand-title").off().on("click", function () {
            var id = $(this).parents(".recommend-demand-card").data('id');
            // window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
            window.open('/f/' + id + '/demand_detail.html?pc=true');
        });
        /*** 跳转到详情页 结束 ***/
        // 切换排序tab
        $(".search-result-area .tab .tab-item").off().on("click", function () {
            // 清除选中
            $(".search-result-area .tab .tab-item").removeClass("active");
            // 重新选中
            $(this).addClass("active");
            // 复原currentPage
            resetCurrentPage();
            // 判别点击的tab
            if ($(this)[0].innerText === "发布时间") {
                $_priceSortIsDesc = false;
                $('.demand-hall .demand-hall-lift-sort-icon i').removeClass('demand-hall-lift-sort-icon__select');
                searchData.sort = {
                    name: 'created_at',
                    value: true
                }
                // $_tabActive = 1;
            } else if ($(this)[0].innerText === "关注度") {
                $_priceSortIsDesc = false;
                $('.demand-hall .demand-hall-lift-sort-icon i').removeClass('demand-hall-lift-sort-icon__select');
                searchData.sort = {
                    // name: 'collection_amount',
                    name: 'click_rate',
                    value: true
                }
                // $_tabActive = 2;
            } else if ($(this)[0].innerText === "价格") {
                // console.log($_priceSortIsDesc);
                $('.demand-hall .demand-hall-lift-sort-icon i').removeClass('demand-hall-lift-sort-icon__select');
                if ($_priceSortIsDesc === false) {
                    $('.demand-hall .demand-hall-lift-sort-icon .icon-down-triangle').addClass('demand-hall-lift-sort-icon__select');
                } else {
                    $('.demand-hall .demand-hall-lift-sort-icon .icon-up-triangle').addClass('demand-hall-lift-sort-icon__select');
                }
                $_priceSortIsDesc = !$_priceSortIsDesc;
                searchData.sort = [
                    {
                        name: 'budget_amount_start',
                        value: $_priceSortIsDesc
                    },
                    {
                        name: 'budget_amount',
                        value: $_priceSortIsDesc
                    }
                ]
                // $_tabActive = 3;
                // 记录价格的排序方式是Desc还是Asc
            } else {
                $('.demand-hall .demand-hall-lift-sort-icon i').removeClass('demand-hall-lift-sort-icon__select');
                $_priceSortIsDesc = false;
                searchData.sort = [
                    {
                        name: 'is_recommend',
                        value: true
                    },
                    {
                        name: 'recommended_index',
                        value: true
                    },
                    {
                        name: 'created_at',
                        value: true
                    },
                    {
                        name: 'click_rate',
                        value: true
                    }
                ]
                // $_tabActive = 0;
            }
            // 原参数 $_typeId, $("#search-input").val()
            getDemandList();
        });
        // 监听分页跳转
        $("#pageToolbar").off().on("click", function () {
            if ($(this).data('currentpage') != currentPage) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getDemandList();
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                // getServiceList($_typeId, $("#search-input").val());
                getDemandList();
            }
        });

        // 收藏/取消收藏
        $(".collect-area").off().on("click", function () {
            if ($(this).children(".label").children("i").hasClass("icon-star-void")) {
                toCollectTheDemand($(this), $(this).parents(".demand-card").data('id'), true);
            } else {
                toCollectTheDemand($(this), $(this).parents(".demand-card").data('id'), false);
            }
        });
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
        typeSearch.setClickCallback(function (node, parentNode) {
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
                    searchData.city = 0
                    searchData.area = 0
                } else if (typeName === 'city') {
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
            getDemandList();
            // console.log('clickCallback', optionId)
        });
        // 设置搜索按钮的click回调
        typeSearch.setSearchBtnClickCallback(function (inputNode) {
            if (inputNode.val() === searchData.input) {
                return 0
            }
            // 赋值
            searchData.input = inputNode.val();
            // 初始化分页的记录
            resetCurrentPage();
            // 重新请求数据
            getDemandList();
            // console.log('btnCallback', searchData.input)
        });
        // 设置搜索input的keyDown回调
        typeSearch.setSearchInputKeyDownCallback(function (event, inputNode) {
            if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
                searchData.input = inputNode.val();
                // 初始化分页的记录
                resetCurrentPage();
                // 重新请求数据
                getDemandList();
                // console.log('inputCallback', searchData.input)
            }
        })
    }

    /**  获取需求列表数据  typeId, searchVal**/
    function getDemandList() {
        // 初始化
        typeSearchJson.fields = [
            {
                "fields": ["status_id"],
                "values": ["202069", "202070", "202071", "202072", "202073", "202074", "202075"],
                "searchType": "term"
            }
        ];
        Object.keys(searchData).forEach(function (key) {
            var obj = {
                values: (searchData[key] instanceof Array) ? searchData[key] : [searchData[key]],
                searchType: 'term'
            }
            // 应用/类型
            if (key === 'status') {
                if (obj.values[0] !== 202066) {
                    if (obj.values[0] == 202074) {
                        typeSearchJson.fields[0].values = ["202071", "202072", "202073", "202074"];
                    } else {
                        typeSearchJson.fields[0].values = obj.values;
                    }
                } else {
                    typeSearchJson.fields[0].values = ["202069", "202070", "202071", "202072", "202073", "202074", "202075"];
                }
            } else if (key === 'industry' || key === 'subIndustry') {
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
            } else if (key === 'province' || key === 'city' || key === 'area') { // 地址
                if (obj.values[0] !== 0 && obj.values[0] !== '不限') {
                    obj.fields = [(key === 'province') ? 'province_name' : (key === 'city') ? 'city_name' : 'district_name']
                    typeSearchJson.fields.push(obj)
                }
                //  标题
            } else if (key === 'input') {
                // 没有内容，放空；否则，传参
                // console.log('obj.values[0]',obj.values[0]);
                if (obj.values[0] !== '') {
                    obj.fields = ['name'];
                    obj.searchType = 'stringQuery';
                    typeSearchJson.fields.push(obj);
                    // typeSearchJson.sortObjects = [{field: "_score", direction: "DESC"}, {field: "status_id", direction: "ASC"}];
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
                    typeSearchJson.sortObjects.push(JSON.parse(searchData[key]));
                } else {
                    var arr = [{field: "_score", direction: "DESC"}, {field: "status_id", direction: "ASC"}];
                    if (searchData[key] instanceof Array) {
                        searchData[key].forEach(function (item) {
                            var obj = {};
                            obj.field = item.name;
                            obj.direction = item.value ? "DESC" : "ASC";
                            arr.push(obj);
                        })
                    } else if (typeof searchData[key] === "object") {
                        var obj = {};
                        obj.field = searchData[key].name;
                        obj.direction = searchData[key].value ? "DESC" : "ASC";
                        arr.push(obj);
                    }
                    typeSearchJson.sortObjects = arr;
                }
            }
        });
        // 页数
        typeSearchJson.page = currentPage;
        // 每页数量
        typeSearchJson.size = searchSize;
        $.ajax({
            type: "POST",
            url: "/searchEngine/customSearch",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(typeSearchJson),
            success: function (res) {
                var totalRecord = res.data.data_object.totalRecord;
                var data = res.data.data_object.data;
                /* 传的数据总量 开始 */
                if ($('#pageToolbar').find("div").length === 0) {
                    $('#pageToolbar').Paging({pagesize: searchSize, count: totalRecord, toolbar: true});
                } else if (clickPage !== 0) {
                    $('#pageToolbar').find("div").remove();
                    $('#pageToolbar').Paging({pagesize: searchSize, count: totalRecord, toolbar: true});
                }
                /* 传的数据总量 结束 */
                setDemandListToPage(data);
            }
        })
    }

    /**  设置需求列表  **/
    function setDemandListToPage(list) {
        // console.log(list);
        var demandCards = $("#search-result-list").children(".demand-card");
        // 隐藏不需要的元素
        for (var k = 0; k < demandCards.length; k++) {
            if (list.length < searchSize && k >= list.length) {
                $(demandCards[k]).css("display", 'none')
            } else {
                $(demandCards[k]).css("display", 'block')
            }
        }
        for (var i = 0; i < list.length; i++) {
            $(demandCards[i]).attr("data-id", list[i].data_id); // id
            if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
                $(demandCards[i]).find(".money").html("面议");     // 金额
            } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
                $(demandCards[i]).find(".money").html(list[i].budget_amount + "万");     // 金额
            } else {
                $(demandCards[i]).find(".money").html(list[i].budget_amount_start + "万" + '-' + list[i].budget_amount + '万');     // 金额
            }
            if (list[i].status_id == 202069) {
                $('.demand-card .demand-card-row .status-area').eq(i).css('color', '#ff4e00');
                $('.demand-card .demand-card-row .status-area>span').eq(i).html("报名中");
            } else if (list[i].status_id == 202075) {
                $('.demand-card .demand-card-row .status-area').eq(i).css('color', '#646464');
                $('.demand-card .demand-card-row .status-area>span').eq(i).html("已过期");
            } else {
                $('.demand-card .demand-card-row .status-area').eq(i).css('color', '#1bbbb4');
                $('.demand-card .demand-card-row .status-area>span').eq(i).text("已完成");
            }
            // $(demandCards[i]).find(".status").html(list[i].status);   // 状态
            $(demandCards[i]).find(".title").attr('title', list[i].name);       // 标题
            $(demandCards[i]).find(".title").html(list[i].name_highLight ? list[i].name_highLight : list[i].name);       // 标题
            $(demandCards[i]).find(".people-num").html(list[i].registration_number ? list[i].registration_number : '暂无');     // 报名人数
            $(demandCards[i]).find(".see-num").html(list[i].click_rate);              // 点击量
            $(demandCards[i]).find(".collect-num").html(list[i].collection_amount);   // 收藏量
            $(demandCards[i]).find(".deadline").html(list[i].validdate);   // 剩余有效期
            $(demandCards[i]).find(".collect-area i").attr("class", "icon-star-void");
        }
        // 获得收藏过的需求id
        var collectedDemandList = setDemandListToCollect();
        for (var i = 0; i < collectedDemandList.length; i++) {
            for (var j = 0; j < list.length; j++) {
                if (list[j].data_id === collectedDemandList[i].project_id) {
                    $(demandCards[j]).find(".collect-area i").attr("class", "icon-collect")
                    break;
                }
            }
        }
    }


    /**  获取右栏推荐需求列表数据  **/
    function getRecommendDemandList() {
        var json = {
            "pager": {//分页信息
                "current": 1,   //当前页数0
                "size": 4        //每页条数
            }
        };
        $.ajax({
            type: "POST",
            url: "/f/projectDemand/query_recommend_demand",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // console.log(res);
                var list = res.data.data_list;
                // console.log(list);
                setRecommendDemandList(list);
            }
        })
    }

    /**  设置右栏推荐需求列表  **/
    function setRecommendDemandList(list) {
        var recommendDemandCards = $(".recommend-demand-card")
        // 隐藏不需要的元素
        for (var k = 0; k < recommendDemandCards.length; k++) {
            if (list.length < 4 && k >= list.length) {
                $(recommendDemandCards[k]).css("display", 'none')
            } else {
                $(recommendDemandCards[k]).css("display", 'block')
            }
        }
        for (var i = 0; i < list.length; i++) {
            $(recommendDemandCards[i]).attr("data-id", list[i].id); // id
            $(recommendDemandCards[i]).find(".recommend-demand-title").html(list[i].name);  // 标题
            $(recommendDemandCards[i]).find(".recommend-demand-title").attr('title', list[i].name);  // 标题
            if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
                $(recommendDemandCards[i]).find(".recommend-demand-money").html("面议");     // 金额
            } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
                $(recommendDemandCards[i]).find(".recommend-demand-money").html(list[i].budget_amount + "万");     // 金额
            } else {
                $(recommendDemandCards[i]).find(".recommend-demand-money").html(list[i].budget_amount_start + "万" + '-' + list[i].budget_amount + '万');     // 金额
            }
            $(recommendDemandCards[i]).find(".recommend-demand-deadline").html(list[i].validdate);  // 截止时间
        }
    }


    // 获取右栏最新需求列表数据
    function getNewRecommendDemandList() {
        var json = {
            "pager": {//分页信息
                "current": 1,   //当前页数0
                "size": 4        //每页条数
            }
        };
        $.ajax({
            type: "POST",
            url: "/f/projectDemand/query_latest_demand?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // console.log(res);
                var list = res.data.data_list;
                // console.log(list);
                setNewRecommendDemandList(list);
            }
        })
    }

    function setNewRecommendDemandList(list) {
        var recommendDemandCards = $(".recommend-demand-card")
        // 隐藏不需要的元素
        for (var k = 4; k < recommendDemandCards.length; k++) {
            if (list.length < 8 && k >= list.length + 4) {
                $(recommendDemandCards[k]).css("display", 'none')
            } else {
                $(recommendDemandCards[k]).css("display", 'block')
            }
        }
        // console.log(list);
        for (var i = 0; i < list.length; i++) {
            $(recommendDemandCards[i + 4]).attr("data-id", list[i].id); // id
            $(recommendDemandCards[i + 4]).find(".recommend-demand-title").html(valueFilter(list[i].name, '暂无标题'));  // 标题
            $(recommendDemandCards[i + 4]).find(".recommend-demand-title").attr('title', valueFilter(list[i].name, '暂无标题'));  // 标题
            if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
                $(recommendDemandCards[i + 4]).find(".recommend-demand-money").html("面议");     // 金额
            } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
                $(recommendDemandCards[i + 4]).find(".recommend-demand-money").html(valueFilter(list[i].budget_amount, 0) + "万");     // 金额
            } else {
                $(recommendDemandCards[i + 4]).find(".recommend-demand-money").html(valueFilter(list[i].budget_amount_start, 0) + "万" + '-' + valueFilter(list[i].budget_amount, 0) + '万');     // 金额
            }
            $(recommendDemandCards[i + 4]).find(".recommend-demand-deadline").html(valueFilter(list[i].validdate, 0));  // 截止时间
        }
    }

    /*** 用户收藏/ 取消收藏 ***/
    function toCollectTheDemand(dom, id, isCollect) {
        var json = {
            "projectId": id,
            "isCollection": isCollect
        };
        $.ajax({
            type: "POST",
            url: "/f/projectDemand/pc/collection_project_demand?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // 403： 未登录
                if (res.status === 403) {
                    window.location.href = "/f/login.html?pc=true";
                } else if (res.status === 200) {
                    if (!isCollect) {
                        layer.msg("取消收藏成功！")
                        $(dom).children(".label").children("i").attr("class", "icon-star-void")
                    } else {
                        layer.msg("收藏成功！")
                        $(dom).children(".label").children("i").attr("class", "icon-collect")
                    }
                    $(dom).children(".collect-num").html(res.data.total)
                }
            }
        })
    }

    /*** 异步获取用户收藏过的需求 ***/
    function setDemandListToCollect() {
        var list = []
        $.ajax({
            type: "GET",
            url: "/f/projectDemandCollection/pc/query_is_collection?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            async: false,
            success: function (res) {
                if (res.status === 200) {
                    list = res.data.data_list;
                    return list
                }
                // 403： 未登录
            }
        })
        return list
    }

    /******* 设置搜索关键词 *******/
    function setSearchHotKey(keyWords) {
        var json = {
            "keyWords": keyWords
        };
        $.ajax({
            type: "POST",
            url: "/f/projectDemandSearchRecord/create_update?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
            }
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
        setTextOverTipOfDemandList();
        setTextOverTipOfRecommandDemandList();
    }

    // 绑定需求父框事件
    function setTextOverTipOfDemandList() {
        // 获取列表父框
        var nListParent = $('#search-result-list').eq(0);
        // 绑定事件
        nListParent.mouseover(eventOfDemandTextOver);
    }

    // 需求文本超出事件
    function eventOfDemandTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        // 节点标签名
        var nodeName = event.target.tagName.toLowerCase();
        if (nodeName === 'span' && $(event.target).hasClass('text-overflow')) {
            nNowActive = $(event.target);
            layer.closeAll();
            layer.tips(nNowActive.text(), nNowActive, {
                tips: [1, '#000000']
            });
        }
    }

    // 绑定推荐需求父框事件
    function setTextOverTipOfRecommandDemandList() {
        // 获取列表父框
        var nListParent = $('.content-right .recommend-card').eq(0);
        // 绑定事件
        nListParent.mouseover(eventOfRecommandDemandTextOver);
    }

    // 推荐需求文本超出事件
    function eventOfRecommandDemandTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        // 节点标签名
        var nodeName = event.target.tagName.toLowerCase();
        if (nodeName === 'p' && $(event.target).hasClass('text-overflow')) {
            nNowActive = $(event.target);
        }
        if (nNowActive) {
            layer.closeAll();
            layer.tips(nNowActive.text(), nNowActive, {
                tips: [1, '#000000']
            });
        }
    }
});
