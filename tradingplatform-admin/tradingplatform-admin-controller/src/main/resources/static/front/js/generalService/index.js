$(function () {
    var service = new generalService();
    var currentPage = 1;
    // console.log(serviceClassification);
    service.initPage();
    service.handleEvent();
    var clickPage = 0;  //判断是否点击页数,0为是

    function generalService() {
        // 获取tab页的父节点
        var $_tabGeneralService = $('.tabGeneralService');
        // 获取卡片
        var $_serviceCard = $('.service-card');
        // 获取左侧卡片
        var $_serviceLeftCard = $('.service-text-card');
        var _this = this;
        // 储存服务分类
        var aIndustry = [{id: 202190, title: '不限'}];
        // 获取地区数据
        var addressData = window.ChineseDistricts;
        // 储存地区分类
        var cityArr = [{
            id: 0,
            title: '不限'
        }];
        // 搜索数据
        var searchType = [
            {
                name: '服务分类',
                type: 'industry',
                active: 202190,
                data: aIndustry
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
        // 传参初始化
        var typeSearchJson = {
            "types": [
                "c_business_service_message"
            ],
            "filterFields": [],
            "fields": [],
            "page": currentPage,
            "size": 9,
            "sort": "DESC"
        };
        // 存储页面上的筛选数据
        var searchData = {};


        // 页面的初始化事件
        _this.initPage = function () {
            _this.extractIndustryData(serviceClassification);
            _this.initSearchData();
            _this._getCityArr();
            _this.initTypeSearch();
            $('.search-area').hide();
            // 获取url参数
            getSearchValue();
            // 初始化分页的记录
            resetCurrentPage();
            // 重新请求数据
            _this.getDataForServiceCard();
            _this.getLeftDataForServiceCard();
        };

        // 初始化搜索条件
        _this.initSearchData = function () {
            searchData.type = 202035;
            searchData.province = 0;
            searchData.city = 0;
            searchData.area = 0;
            searchData.input = '';
            searchData.sort = [
                {
                    name: 'recommended',
                    value: true
                },
                {
                    name: 'recommended_index',
                    value: true
                }
            ]
        };

        // 页面的点击事件
        _this.handleEvent = function () {
            _this.changeTab();
            _this.pageChange();
            // 设置超出文本
            setTextOverTip();
            // this.searchBtnClick();
        };

        _this.extractIndustryData = function (data) {
            data.forEach(function (item) {
                if (item.pid === 202190) {
                    aIndustry.push(item);
                }
            })
        };

        // 监听分页跳转
        _this.pageChange = function () {
            $("#pageToolbar").on("click", function () {
                if ($(this).data('currentpage') != currentPage) {
                    clickPage = 0;
                    currentPage = $('#pageToolbar').data('currentpage');
                    // getServiceList($_typeId, $("#search-input").val());
                    _this.getDataForServiceCard();
                }
            }).keydown(function (e) {
                if (e.keyCode == 13) {
                    clickPage = 0;
                    currentPage = $('#pageToolbar').data('currentpage');
                    // console.log(currentPage);
                    // getServiceList($_typeId, $("#search-input").val());
                    _this.getDataForServiceCard();
                }
            });
        };

        // 点击tab更改函数
        _this.changeTab = function () {
            $_tabGeneralService.find('.tab-item').click(function () {
                $(this).siblings().removeClass('active');
                $(this).addClass('active');
                resetCurrentPage();
                if ($(this).index() == 0) {
                    // getServiceList($_typeId, $("#search-input").val())
                    searchData.sort = [
                        {
                            name: 'recommended',
                            value: true
                        },
                        {
                            name: 'recommended_index',
                            value: true
                        }
                    ];
                } else if ($(this).index() == 1) {
                    searchData.sort = 'docking_num';
                    // typeSearchJson.sortFields = ["docking_num"];
                    // 初始化分页的记录
                    // 重新请求数据
                } else {
                    searchData.sort = "recommended_index";
                    // typeSearchJson.sortFields = ["updated_at"];
                    // 初始化分页的记录
                }
                _this.getDataForServiceCard();
            })
        };


        // 提取广东省城市数据
        _this._getCityArr = function () {
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

        /***
         * 处理类型多选框
         */
        _this.initTypeSearch = function () {
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
                        searchData.city = 0;
                        searchData.area = 0
                    } else if (typeName === 'city') {
                        searchData.area = 0
                    }
                    if (typeName === 'province' || typeName === 'city' || typeName === 'area') {
                        optionId = node.text()
                    }
                    if (typeName == 'industry' && optionId == 202190) {
                        searchData.industry = undefined;
                        delete searchData.industry;
                    }
                    // 记录点击的分类数据
                    searchData[typeName] = optionId
                }
                // 初始化分页的记录
                resetCurrentPage();
                // 重新请求数据
                _this.getDataForServiceCard();
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
                _this.getDataForServiceCard();
            });
            // 设置搜索input的keyDown回调
            typeSearch.setSearchInputKeyDownCallback(function (event, inputNode) {
                console.log(inputNode);
                if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
                    searchData.input = inputNode.val();
                    // 初始化分页的记录
                    resetCurrentPage();
                    // 重新请求数据
                    _this.getDataForServiceCard();
                }
            })
        };

        // 设置卡片数据
        _this.setDataForServiceCard = function (list) {
            var serviceLength = $_serviceCard.length;
            for (var j = 0; j < list.length; j++) {
                $_serviceCard.eq(j).show();
                $_serviceCard.eq(j).attr('href', '/f/' + list[j].data_id + '/general_service_detail.html');
                $_serviceCard.eq(j).find('.title').text(list[j].title);
                $_serviceCard.eq(j).find('.classification').text(list[j].category);
                if (!!list[j].icon) {
                    $_serviceCard.eq(j).find('.service-card-content img').attr('src', '/adjuncts/file_download/' + JSON.parse(list[j].icon)[0].id);
                }
                $_serviceCard.eq(j).find('.detail .money').text((!!list[j].price && list[j].price !== 0) ? '￥' + list[j].price + '万元' : '￥面议');
                $_serviceCard.eq(j).find('.detail .dock-num').text(list[j].docking_num);
            }
            if (serviceLength > list.length) {
                for (var i = list.length; i < serviceLength; i++) {
                    $_serviceCard.eq(i).hide();
                }
            }
        };

        // 获取卡片数据
        _this.getDataForServiceCard = function () {
            typeSearchJson.fields = [
                {
                    "fields": ["status_id"],
                    "values": ["202050"],
                    "searchType": "term"
                }
            ];
            Object.keys(searchData).forEach(function (key) {
                var obj = {
                    values: (searchData[key] instanceof Array) ? searchData[key] : [searchData[key]],
                    searchType: 'term'
                };
                // 类型
                if (key === 'industry' || key === 'subIndustry') {
                    // console.log(obj.values)
                    if (obj.values[0] !== 202190 && obj.values[0] !== 0) {
                        // obj.fields = [(key === 'type') ? 'skilled_label_id' : 'application_industry_id']
                        if (key === 'industry') {
                            obj.fields = ['category_id'];
                        }
                        // else {
                        //     obj.fields = ['sub_industry_id_id'];
                        //     /*obj.values[0] = "/.*" + obj.values[0] + ".*!/";      //搜索引擎正则查询
                        //      obj.searchType = 'stringQuery';     //正则查询是使用模糊匹配*/
                        // }
                        typeSearchJson.fields.push(obj);
                    }
                    // 地址
                }
                // else if (key === 'category') {
                //     if (obj.values[0] !== 202138) {
                //         obj.fields = ['category_id'];
                //         typeSearchJson.fields.push(obj)
                //     }
                // }
                else if (key === 'province' || key === 'city' || key === 'area') {
                    if (obj.values[0] !== 0 && obj.values[0] !== '不限') {
                        obj.fields = [(key === 'province') ? 'province_name' : (key === 'city') ? 'city_name' : 'district_name'];
                        typeSearchJson.fields.push(obj)
                    }
                    //  标题
                } else if (key === 'input') {
                    // 没有内容，放空；否则，传参
                    if (obj.values[0] !== '') {
                        obj.fields = ['title'];
                        obj.searchType = 'stringQuery';
                        typeSearchJson.fields.push(obj);
                        typeSearchJson.highlightBuilder = {
                            fields: [
                                {
                                    name: "title",
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
                            searchData[key].forEach(function (item) {
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
            typeSearchJson.size = 9;
            var url = '/searchEngine/customSearch?pc=true';
            new NewAjax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(typeSearchJson),
                success: function (res) {
                    console.log(res);
                    var list = res.data.data_object.data;
                    var totalRecord = res.data.data_object.totalRecord;
                    if (clickPage === 0) {
                        $('#pageToolbar').Paging({pagesize: 9, count: totalRecord, toolbar: true});
                        $('#pageToolbar').find("div:eq(1)").remove();

                    } else {
                        $('#pageToolbar').Paging({pagesize: 9, count: totalRecord, toolbar: true});
                        $('#pageToolbar').find("div:eq(0)").remove();
                        clickPage = 0;
                    }
                    console.log(list);
                    _this.setDataForServiceCard(list);
                },
                error: function () {

                }
            })
        };

        // 获取左侧栏卡片数据
        _this.getLeftDataForServiceCard = function () {
            var json = {
                "types": [
                    "c_business_service_message"
                ],
                "page": 1,
                "size": 6,
                "sortObjects": [
                    {
                        "field": "_score",//必传
                        "direction": "DESC"
                    },
                    {
                        "field": "recommended",
                        "direction": "DESC"
                    },
                    {
                        "field": "recommended_index",
                        "direction": "DESC"
                    }
                ]
            };
            var url = '/searchEngine/customSearch?pc=true';
            new NewAjax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(json),
                success: function (res) {
                    console.log(res);
                    var list = res.data.data_object.data;
                    _this.setLeftDataForServiceCard(list);
                },
                error: function () {
                }
            })
        };

        _this.setLeftDataForServiceCard = function (list) {
            var serviceLength = $_serviceLeftCard.length;
            for (var j = 0; j < list.length; j++) {
                $_serviceLeftCard.eq(j).show();
                $_serviceLeftCard.eq(j).attr('href', '/f/' + list[j].data_id + '/general_service_detail.html');
                $_serviceLeftCard.eq(j).find('.title').text(list[j].title);
                if (!!list[j].provider_id) {
                    $_serviceLeftCard.eq(j).find('.provider-data').text(JSON.parse(list[j].provider_id).name);
                }
                $_serviceLeftCard.eq(j).find('.time').text($(this).formatTime(new Date(list[j].created_at)).split(' ')[0]);
            }
            if (serviceLength > list.length) {
                for (var i = list.length; i < serviceLength; i++) {
                    $_serviceLeftCard.eq(i).hide();
                }
            }
        };

        // 复原currentPage
        function resetCurrentPage() {
            $('#pageToolbar').data('currentpage', 1);
            currentPage = 1;
            clickPage = 1;
            $('#pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
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
            setTextOverTipOfServerList();
            setTextOverTipOfRecommandServerList();
        }

        // 绑定产品父框事件
        function setTextOverTipOfServerList() {
            // 获取列表父框
            var nListParent = $('.search-result').eq(0);
            // 绑定事件
            nListParent.mouseover(eventOfServerTextOver);
        }

        //产品文本超出事件
        function eventOfServerTextOver(event) {
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
        function setTextOverTipOfRecommandServerList() {
            // 获取列表父框
            var nListParent = $('.content-right .recommand-service').eq(0);
            // 绑定事件
            nListParent.mouseover(eventOfRecommandServerTextOver);
        }

        // 推荐企业文本超出事件
        function eventOfRecommandServerTextOver(event) {
            // 获取当前作用节点
            var nNowActive = null;
            // 节点标签名
            var nodeName = event.target.tagName.toLowerCase();
            if (nodeName === 'p' && $(event.target).hasClass('title')) {
                nNowActive = $(event.target);
            } else if (nodeName === 'span' && $(event.target).hasClass('provider-data')) {
                nNowActive = $(event.target);
            }
            if (nNowActive) {
                layer.closeAll();
                layer.tips(nNowActive.text(), nNowActive, {
                    tips: [1, '#000000']
                });
            }
        }
    }
});