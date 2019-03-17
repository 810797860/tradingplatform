$(function () {
    /*=== 变量定义 ===*/
    // 存储推荐列表子项html
    var recommendCaseItemHtml = '<a class="recommend-case-card-div" href="" target="_blank"><div class="recommend-case-card-title" title=""></div><p class="recommend-case-card-type">行业：<span class="recommend-case-card-type-content"></span></p><p class="recommend-case-card-application">所属地区：<span class="recommend-case-card-application-content"></span></p><div class="recommend-case-card-browse-money"><p class="recommend-case-card-browse"><i class="icon-eye"></i><span class="recommend-case-card-browse-number"></span></p><p class="recommend-case-card-money"></p></div></a>'
    // 获取类型多选框展示区域
    // var typeSearchListShowDiv = $('.select-type-search .select-area').eq(0);
    // 获取分项区域
    var tabsDiv = $('.case-hall-select-tabs-div').eq(0);
    // 综合参数
    var total = {
        pager: {
            current: 1,
            size: 3
        }
    };
    var addressData = window.ChineseDistricts;
    // 获取推荐列表
    var recommendCaseList = $('.recommend-case-list-content-div').eq(0);
    // 获取热门列表
    var hotCaseList = $('.recommend-case-list-content-div').eq(1);
    // 获取分页
    var pageNode = $('#pageToolbar');
    // 存储类型搜索框传参
    var typeSearchJson = {
        "types": [
            "c_business_mature_case"
        ],
        "fields": [],
        "filterFields": [],
        "page": 1,
        "size": 9,
        "sort": "DESC",
        "sortFields": []
    };
    var cityArr = [{
        id: 0,
        title: '不限'
    }];
    // 存储页面上的筛选数据
    var searchData = {};
    // 存储当前页数
    var nowPageNum = 1;
    // 存储每页的条数
    var pageSize = 9;
    //判断是否点击页数,0为是
    var clickPage = 0;
    // 获取多选框对象
    var typeSearch;
    // 存储全选的id数组
    var selectAllIdArr = [0, 202035, 202052];
    // 记录上一次搜索的关键词
    var lastSearchVal = "";
    // 存储行业数据
    var aIndustry = [{id: 202052, title: '不限'}];
    // 存储子行业数据
    var aSubIndustry = [{id: 0, title: '不限'}];
    // 获取类型多选框初始化数据
    var typeSearchData = [
        {
            name: '行业',
            type: 'industry',
            active: 202052,
            data: aIndustry
        },
        {
            name: '子行业',
            type: 'subIndustry',
            active: 0,
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

    _getCityArr();

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

    // 设置historyHref
    historyHrefSetGetMethod(function (newUrl) {
        if (newUrl === '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true') {
            new NewAjax({
                url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    if (res.data.data_object !== null && !!res.data.data_object.back_check_status && parseInt(JSON.parse(res.data.data_object.back_check_status).id) === 202050) {
                        window.location.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
                    } else {
                        window.location.href = '/f/personal_center.html?pc=true#menu=authentication';
                        saveToLocalStorage('history', '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true');
                        toMenu('authentication');
                    }
                },
                error: function (err) {
                    console.error('身份验证请求数据失败，err：' + err);
                }
            })
        }
    });

    // 引入搜索组件
    typeSearch = new TypeSearch();

    // 初始化是把搜索栏去掉
    $('.search-area').hide();

    /*=== 函数调用区 ===*/
    // 提取数据
    extractIndustryData(industry);
    // 初始化选项记录
    initSearchData();
    // 绑定tab点击事件
    eventOfTabsClick();
    // 分页的点击事件
    eventOfPageClick();
    // 搜索列表项的点击事件
    eventOfToDetailClick();
    //  点击发布案例跳转个人中心
    toPeopelCenterPublishCase();
    // 初始化头部类型多选框
    typeSearch.setData(typeSearchData, false);
    // 设置点击回调
    typeSearch.setClickCallback(function (node, parentNode) {
        var optionId = node.data().id;
        var typeName = parentNode.attr('type');
        // 多选的情况
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
        } else { // 非多选的情况
            // 若是省份/城市点击
            if (typeName === 'province') {
                searchData.city = 0;
                searchData.area = 0
            } else if (typeName === 'city') {
                searchData.area = 0;
            }
            if (typeName === 'province' || typeName === 'city' || typeName === 'area') {
                optionId = node.text()
            }
            if (typeName === 'industry' && searchData.subIndustry) {
                searchData.subIndustry = undefined;
                delete searchData.subIndustry;
            }
            // 记录点击的分类数据
            searchData[typeName] = optionId;
        }
        // 初始化分页的记录
        resetCurrentPage();
        // 重新请求数据
        selectTypeSearch(function (dataSource) {
            initPage(dataSource.totalRecord);
            initTotalRanking(dataSource.data);
        })
    });
    // 设置搜索按钮的click回调
    typeSearch.setSearchBtnClickCallback(function (inputNode) {
        if (inputNode.val() === searchData.input) {
            return 0
        }
        // 赋值
        searchData.input = inputNode.val()
        // 初始化分页的记录
        resetCurrentPage()
        // 重新请求数据
        selectTypeSearch(function (dataSource) {
            initPage(dataSource.totalRecord)
            initTotalRanking(dataSource.data)
        })
    });
    // 设置搜索input的keyDown回调
    typeSearch.setSearchInputKeyDownCallback(function (event, inputNode) {
        if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
            searchData.input = inputNode.val()
            // 初始化分页的记录
            resetCurrentPage()
            // 重新请求数据
            selectTypeSearch(function (dataSource) {
                initPage(dataSource.totalRecord)
                initTotalRanking(dataSource.data)
            })
        }
    });
    // 根据多选框请求数据
    /*var storage = window.localStorage;
    var searchVal = storage.getItem("searchVal");
    if (searchVal) {
        $(".search-both #search-input").val(searchVal);
        searchData.input = searchVal;
        storage.removeItem("searchVal")
    }
    selectTypeSearch(function (dataSource) {
        initPage(dataSource.totalRecord);
        initTotalRanking(dataSource.data);
    });*/
    getSearchValue();
    // 初始化分页的记录
    resetCurrentPage();
    // 重新请求数据
    selectTypeSearch(function (dataSource) {
        initPage(dataSource.totalRecord);
        initTotalRanking(dataSource.data)
    });
    // 请求推荐数据
    getTotalRanking(function (RankList) {
        initRecommendCase(RankList)
    });

    getHotRanking(function (RankList) {
        initHotCase(RankList)
    });
    // 设置文本超出提示
    setTextOverTip();

    /*=== 数据请求 ===*/

    // 搜索函数
    function selectTypeSearch(callback) {
        // 初始化
        typeSearchJson.fields = [
            {
                "fields": ["back_check_status_id"],
                "values": ["202050"],
                "searchType": "term"
            }
        ];
        Object.keys(searchData).forEach(function (key) {
            var obj = {
                values: (searchData[key] instanceof Array) ? searchData[key] : [searchData[key]],
                searchType: 'term'
            };
            // 应用/类型
            if (key === 'industry' || key === 'subIndustry') {
                // console.log(obj.values)
                if (obj.values[0] !== 202052 && obj.values[0] !== 0) {
                    // obj.fields = [(key === 'type') ? 'skilled_label_id' : 'application_industry_id']
                    if (key === 'industry') {
                        obj.fields = ['application_industry_id'];
                    } else {
                        obj.fields = ['sub_application_industry_id'];
                        /*obj.values[0] = "/.*" + obj.values[0] + ".*!/";      //搜索引擎正则查询
                        obj.searchType = 'stringQuery';     //正则查询是使用模糊匹配*/
                    }
                    typeSearchJson.fields.push(obj);
                }
                // 地址
            } else if (key === 'province' || key === 'city' || key === 'area') {
                if (obj.values[0] !== 0 && obj.values[0] !== '不限') {
                    obj.fields = [(key === 'province') ? 'province_name' : (key === 'city') ? 'city_name' : 'district_name']
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
                    typeSearchJson.sortFields = [searchData[key]];
                    typeSearchJson.sort = 'DESC';
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
                        obj.direction = searchData[key].value ? "DESC" : "ASC";
                        arr.push(obj)
                    }
                    typeSearchJson.sort = undefined
                    typeSearchJson.sortFields = undefined
                    typeSearchJson.sortObjects = arr
                }
            }
        });
        // 页数
        typeSearchJson.page = nowPageNum;
        // 每页数量
        typeSearchJson.size = pageSize;
        $.ajax({
            url: '/searchEngine/customSearch',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(typeSearchJson),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data.data_object)
                    }
                } else {
                    throw new Error(res.status)
                }
            },
            error: function (err) {
                console.error('综合', err)
            }
        })
    }

    // 推荐列表
    function getTotalRanking(callback) {
        $.ajax({
            url: '/f/matureCase/get_recommend_mature_case',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(total),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data.data_list)
                    }
                } else {
                    throw new Error(res.status)
                }
            },
            error: function (err) {
                console.error('综合', err)
            }
        })
    }


    // 热门列表

    function getHotRanking(callback) {
        console.log(total);
        $.ajax({
            url: '/f/matureCase/query?pc=true',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(total),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data.data_list)
                    }
                } else {
                    throw new Error(res.status)
                }
            },
            error: function (err) {
                console.error('综合', err)
            }
        })
    }


    /*=== 数据写入 ===*/

    // 初始化综合列表
    function initTotalRanking(dataList) {
        var rankItems = $('.total-Ranking-item')
        rankItems.each(function (index, item) {
            var rankItem = $(item)
            // 展示类型
            rankItem.find('.belong-class').eq(0).css({display: 'block'})
            // 隐藏原有的底部栏
            rankItem.find('.detail').eq(0).css({display: 'none'})
            // 展示新的底部栏
            rankItem.find('.browse-money').eq(0).css({display: 'block'})
            // 超出数据上限的隐藏
            if (index > dataList.length - 1) {
                rankItem.css({display: 'none'})
            } else {
                // 展示每项
                rankItem.css({display: 'block'})
                // 每项都记录id
                rankItem.attr({href: '/f/' + dataList[index].data_id + '/case_detail.html'})
                // 写入标题
                rankItem.find('.title').eq(0).html(dataList[index].title_highLight ? dataList[index].title_highLight : dataList[index].title);
                rankItem.find('.title').eq(0).attr('title', dataList[index].title);
                // 写入类型
                rankItem.find('.belong-class-name').eq(0).text(dataList[index].application_industry);
                // 写入应用行业
                rankItem.find('.type-content').eq(0).text(dataList[index].address);
                // 插入图片
                rankItem.find('.solution-card-image').eq(0).attr({src: rankItem.getAvatar(dataList[index].picture_cover ? JSON.parse(dataList[index].picture_cover)[0].id : null)})
                // 修改浏览数
                rankItem.find('.browse-Number').eq(0).text(dataList[index].click_rate)
                // 修改金钱数
                rankItem.find('.money-part .money').eq(0).text(dataList[index].case_money ? "¥ " + dataList[index].case_money + "万元" : '¥ 面议')
            }
        })
    }

    // 初始化推荐列表
    function initRecommendCase(caseList) {
        var data = null
        var result = ''
        var shortTimeSave = ''
        var linkRule = /href=""/
        var titleRule = /<div class="recommend-case-card-title((?!<\/div>).)+<\/div>/
        var spanRule = /<span class="(recommend-case-card-application-content|recommend-case-card-type-content|recommend-case-card-browse-number)((?!<\/span>).)+<\/span>/g
        var pRule = /<p class="recommend-case-card-money((?!<\/span>).)+<\/p>/
        caseList.forEach(function (caseItem) {
            result += recommendCaseItemHtml.replace(titleRule, function (titleStr) {
                titleStr = titleStr.replace('title=""', 'title="' + caseItem.title + '"');
                return titleStr.slice(0, -6) + caseItem.title + titleStr.slice(-6)
            }).replace(spanRule, function (spanStr) {
                // 是类型
                if (spanStr.indexOf('recommend-case-card-type-content') > -1) {
                    data = JSON.parse(caseItem.application_industry)
                } else if (spanStr.indexOf('recommend-case-card-application-content') > -1) {
                    // 应用行业
                    data = caseItem.address;
                } else if (spanStr.indexOf('recommend-case-card-browse-number') > -1) {
                    // 浏览数
                    data = caseItem.click_rate
                }
                if (typeof data === "object") {
                    // 是否为数组，getAttrString自定义原型链方法
                    if (data.getAttrString) {
                        shortTimeSave = data.getAttrString('title')
                    } else {
                        shortTimeSave = data.title
                    }
                } else {
                    shortTimeSave = data
                }
                return spanStr.slice(0, -7) + shortTimeSave + spanStr.slice(-7)
            }).replace(pRule, function (pStr) {
                return pStr.slice(0, -4) + (caseItem.case_money ? "¥ " + caseItem.case_money + "万元" : "¥ 面议") + pStr.slice(-4)
            }).replace(linkRule, 'href="/f/' + caseItem.id + '/case_detail.html"')
        })
        // 写入列表
        recommendCaseList.html(result)
    }


    // 初始化热门产品
    function initHotCase(caseList) {
        var data = null
        var result = ''
        var shortTimeSave = ''
        var linkRule = /href=""/
        var titleRule = /<div class="recommend-case-card-title((?!<\/div>).)+<\/div>/
        var spanRule = /<span class="(recommend-case-card-application-content|recommend-case-card-type-content|recommend-case-card-browse-number)((?!<\/span>).)+<\/span>/g
        var pRule = /<p class="recommend-case-card-money((?!<\/span>).)+<\/p>/
        caseList.forEach(function (caseItem) {
            result += recommendCaseItemHtml.replace(titleRule, function (titleStr) {
                titleStr = titleStr.replace('title=""', 'title="' + caseItem.title + '"');
                return titleStr.slice(0, -6) + caseItem.title + titleStr.slice(-6)
            }).replace(spanRule, function (spanStr) {
                // 是类型
                if (spanStr.indexOf('recommend-case-card-type-content') > -1) {
                    data = JSON.parse(caseItem.application_industry)
                } else if (spanStr.indexOf('recommend-case-card-application-content') > -1) {
                    // 应用行业
                    data = caseItem.address;
                } else if (spanStr.indexOf('recommend-case-card-browse-number') > -1) {
                    // 浏览数
                    data = caseItem.click_rate
                }
                if (typeof data === "object") {
                    // 是否为数组，getAttrString自定义原型链方法
                    if (data.getAttrString) {
                        shortTimeSave = data.getAttrString('title')
                    } else {
                        shortTimeSave = data.title
                    }
                } else {
                    shortTimeSave = data
                }
                return spanStr.slice(0, -7) + shortTimeSave + spanStr.slice(-7)
            }).replace(pRule, function (pStr) {
                return pStr.slice(0, -4) + (caseItem.case_money ? "¥ " + caseItem.case_money + "万元" : "¥ 面议") + pStr.slice(-4)
            }).replace(linkRule, 'href="/f/' + caseItem.id + '/case_detail.html"')
        })
        // 写入列表
        hotCaseList.html(result)
    }

    // 初始化分页
    function initPage(dataNumber) {
        /* 传的数据总量 开始 */
        if (pageNode.find("div").length === 0) {
            pageNode.Paging({pagesize: pageSize, count: dataNumber, toolbar: true});
        } else if (clickPage !== 0) {
            pageNode.find("div").remove();
            pageNode.Paging({pagesize: pageSize, count: dataNumber, toolbar: true});
        }
    }

    // 初始化搜索条件
    function initSearchData() {
        searchData.type = 202052
        // 多选，需为数组；单选，字符串/number
        searchData.application = 202035
        searchData.province = 0
        searchData.city = 0
        searchData.area = 0
        searchData.input = ''
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
                name: 'click_rate',
                value: true
            },
            {
                name: 'created_at',
                value: true
            }
        ]
    }

    /*=== 事件监听 ===*/

    // 标签页tab域的点击事件
    function eventOfTabsClick() {
        tabsDiv.off('click').on('click', function (event) {
            var tab = $(event.target);
            if (tab.attr('name') === 'void' || (tab.hasClass('case-hall-select-tab-item__select') && tab.data().sort === undefined)) {
                return 0
            }
            var name = tab.attr('name');
            // 初始化分页的记录
            resetCurrentPage();
            // 存储tab的选择
            switch (name) {
                case 'total-ranking':
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
                    break
                case 'attention':
                    searchData.sort = 'click_rate'
                    break
                case 'update-time':
                    searchData.sort = 'created_at'
                    break
                case 'money':
                    if (tab.hasClass('case-hall-select-tab-item__select')) {
                        tab.data().sort = !tab.data().sort
                    } else {
                        tab.data().sort = true
                        searchData.sort = {}
                    }
                    searchData.sort.name = 'case_money'
                    searchData.sort.value = tab.data().sort
                    break
                default:
                    break
            }
            // 切换tab样式
            funcModelClassChange(name)
            // 重新请求数据
            selectTypeSearch(function (dataSource) {
                initPage(dataSource.totalRecord)
                initTotalRanking(dataSource.data)
            })
        })
    }

    // 分页的点击事件
    function eventOfPageClick() {
        pageNode.off('click').on('click', function () {
            if (pageNode.data().currentpage != nowPageNum) {
                clickPage = 0;
                nowPageNum = pageNode.data().currentpage;
                // 重新请求数据
                selectTypeSearch(function (dataSource) {
                    initPage(dataSource.totalRecord)
                    initTotalRanking(dataSource.data)
                })
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                nowPageNum = pageNode.data().currentpage;
                // 重新请求数据
                selectTypeSearch(function (dataSource) {
                    initPage(dataSource.totalRecord)
                    initTotalRanking(dataSource.data)
                })
            }
        });
    }

    // 点击进入详情事件
    function eventOfToDetailClick() {
        $(".total-Ranking-item").off().on("click", function () {
            // 记录搜索关键词
            if ($(".search-both #search-input").val() && lastSearchVal !== $(".search-both #search-input").val()) {
                setSearchHotKey($(".search-both #search-input").val());
                lastSearchVal = $(".search-both #search-input").val();
            }
        })
    }

    /*=== 功能函数 ===*/

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

    // 切换模块tab样式
    function funcModelClassChange(modelName) {
        var tabs = $('.case-hall-select-tab-item')
        tabs.each(function (index, item) {
            var tab = $(item)
            var sort = tab.data().sort
            if (tab.attr('name') === modelName) {
                if (!tab.hasClass('case-hall-select-tab-item__select')) {
                    tab.addClass('case-hall-select-tab-item__select')
                }
                // 若有排序
                if (sort !== undefined) {
                    tab.find('i').each(function (index, item) {
                        var icon = $(item)
                        if (Boolean(index) === sort) {
                            icon.addClass('lift-sort-icon__select')
                        } else {
                            icon.removeClass('lift-sort-icon__select')
                        }
                    })
                }
            } else {
                tab.removeClass('case-hall-select-tab-item__select')
                if (sort !== undefined) {
                    tab.find('i').each(function (index, item) {
                        var icon = $(item)
                        icon.removeClass('lift-sort-icon__select')
                    })
                }
            }
        })
    }

    // 初始分页栏
    function resetCurrentPage() {
        clickPage = 1;
        pageNode.data().currentpage = 1;
        nowPageNum = 1;
        pageNode.find('li[data-page="' + nowPageNum + '"]').addClass('focus').siblings().removeClass('focus');
    }

    function toPeopelCenterPublishCase() {
        $('.recommend-case-div .recommend-case-head-div .release-demand-btn').click(function () {
            if (typeof window.historyHref === "object") { // 没有definePrototype
                window.historyHref.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
            } else {
                window.historyHref = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
            }
            /*var href = $(this).attr('data-href');
            new NewAjax({
                url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    if (res.data.data_object !== null && !!res.data.data_object.back_check_status) {
                        if (JSON.parse(res.data.data_object.back_check_status).id == 202050) {
                            window.open('/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true', '_self');
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
            })*/
        })
    }

    // 设置搜索关键词
    function setSearchHotKey(keyWords) {
        var json = {
            "keyWords": keyWords
        };
        $.ajax({
            type: "POST",
            url: "/f/matureCaseSearchRecord/create_update?pc=true",
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
        setTextOverTipOfProductList();
        setTextOverTipOfRecommendList();
    }

    // 绑定产品父框事件
    function setTextOverTipOfProductList() {
        // 获取列表父框
        var nListParent = $('.case-hall-left-part .total-Ranking-div').eq(0);
        // 绑定事件
        nListParent.mouseover(eventOfProductTextOver);
    }

    //产品文本超出事件
    function eventOfProductTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        // 节点标签名
        var nodeName = event.target.tagName.toLowerCase();
        if (nodeName === 'div' && $(event.target).hasClass('text-overflow')) {
            nNowActive = $(event.target);
        } else if (nodeName === 'span' && $(event.target).hasClass('type-content')) {
            nNowActive = $(event.target);
        }
        if (nNowActive) {
            layer.closeAll();
            layer.tips(nNowActive.text(), nNowActive, {
                tips: [1, '#000000']
            });
        }
    }

    // 绑定推荐产品父框事件
    function setTextOverTipOfRecommendList() {
        // 获取列表父框
        var nListParent = $('.case-hall-right-part .recommend-case-list-content-div').eq(0);
        // 绑定事件
        nListParent.mouseover(eventOfRecommendTextOver);
    }

    // 推荐产品文本超出事件
    function eventOfRecommendTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        // 节点标签名
        var nodeName = event.target.tagName.toLowerCase();
        if (nodeName === 'div' && $(event.target).hasClass('recommend-case-card-title')) {
            nNowActive = $(event.target);
        } else if (nodeName === 'span' && $(event.target).hasClass('recommend-case-card-application-content')) {
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

