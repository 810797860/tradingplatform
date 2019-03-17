var currentPage = 1;
var searchSize = 13;
var $_typeId = "";
var $_subcategoryId = null;
var $_tabActive = 0;
var clickPage = 0;  //判断是否点击页数,0为是
var aTypeData = [{id: 202094, title: "不限"}];
var aChildTypeData = [{id: 0, title: "不限"}];

var searchType = [
    {
        name: '类别',
        type: 'type',
        active: 202094,
        data: aTypeData
    },
    {
        name: '子类别',
        type: 'childType',
        data: aChildTypeData,
        linkMethod: function () {
            var _this = this,
                typeId = _this.selectData.type,
                data = [];
            _this.childType.forEach(function (item) {
                if (item.pid === typeId) {
                    data.push(item);
                }
            });
            console.log()
            return (data.length > 0) ? [{id: 0, title: '不限'}].concat(data) : null;
        }
    }
];
// 存储页面上的筛选数据
var searchData = {};
// searchType[0].types = searchType[0].types.concat(industryFieldTypes);

$(function () {
    extractTypeData();
    initSearchData();
    initTypeSearch();
    init_data();
    handleEvent();
    // 设置超出提示
    setTextOverTip();
});

// 提取类别、子类别数据
function extractTypeData() {
    industryFieldTypes.forEach(function (item) {
        if (item.pid === 202094) {
            aTypeData.push(item);
        } else {
            aChildTypeData.push(item);
        }
    })
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

function initTypeSearch() {
    // 引入搜索框
    var typeSearch = new TypeSearch();
    // 初始化头部类型多选框
    typeSearch.setData(searchType);
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
            if (typeName === 'type' && searchData.childType) {
                searchData.childType = undefined;
                delete searchData.childType;
            }
            // 记录点击的分类数据
            searchData[typeName] = optionId
        }
        // 初始化分页的记录
        resetCurrentPage();
        // 重新请求数据
        $_typeId = searchData.type;
        getAdList(searchData.type, searchData.input, searchData.childType);
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
        getAdList(searchData.type, searchData.input, searchData.childType);
    });
    // 设置搜索input的keyDown回调
    typeSearch.setSearchInputKeyDownCallback(function (event, inputNode) {
        if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
            searchData.input = inputNode.val();
            // 初始化分页的记录
            resetCurrentPage();
            // 重新请求数据
            getAdList(searchData.type, searchData.input, searchData.childType);
        }
    })
}


/*** 初始化dom结构 ***/

/*function init_dom () {
    for (var i = 0; i < searchType.length; i++) {
        var li = document.createElement("li");
        $(li).addClass("type-item");
        var div = document.createElement("div");
        $(div).addClass("option-area option-area-hidden");
        $(li).append("<span class='type'>" + searchType[i].title + ":&nbsp;&nbsp;</span>");
        var g = 0;
        for (var j = 0; j < searchType[i].types.length; j++) {
            if (searchType[i].types[j].pid === 202094 || searchType[i].types[j].pid === undefined) {
                if (g === 0) {
                    $(div).append('<span class="option active" data-id="' + searchType[i].types[j].id + '" data-pid="'+ searchType[i].types[j].pid +'" data-row="' + i + '" data-col="' + j + '">' + searchType[i].types[j].title + '</span>')
                    g++;
                } else {
                    $(div).append('<span class="option" data-id="' + searchType[i].types[j].id + '" data-pid="'+ searchType[i].types[j].pid +'" data-row="' + i + '" data-col="' + j + '">' + searchType[i].types[j].title + '</span>')
                    g++;
                }
                if (g > 7) {
                    $(div).append('<span class="open-all">更多<i class="icon-open-arrow"></i></span></span>')
                }
            }
        }
        $(li).append(div);
        $("#type-ul").append(li);
    }
}*/

/*** 初始化数据 ***/
function init_data() {
    // 获取资讯列表
    getAdList();
    // 获取右栏推荐行业资讯
    getRecommendList();
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
    $(".platform-ad .platform-ad-container .search-result .info-item").click(function () {
        var id = $(this).data('id');
        // window.location.href = '/f/' + id + '/platform_ad_detail.html?pc=true';
        window.open('/f/' + id + '/platform_ad_detail.html?pc=true');
    });
    $(".consultation-card").click(function () {
        var id = $(this).data('id');
        // window.location.href = '/f/' + id + '/platform_ad_detail.html?pc=true';
        window.open('/f/' + id + '/platform_ad_detail.html?pc=true');
    });
    /*** 跳转到详情页 结束 ***/

    // 切换排序tab
    $(".search-result-area .tab .tab-item").on("click", function () {
        // 复原currentPage
        resetCurrentPage();
        $(".search-result-area .tab .tab-item").removeClass("active");
        $(this).addClass("active");

        if ($(this)[0].innerText === "点击量") {
            $_tabActive = 1;
        } else if ($(this)[0].innerText === "发布时间") {
            $_tabActive = 2;
        } else {
            $_tabActive = 0;
        }
        // if () {
        //
        // }
        getAdList($_typeId, $("#search-input").val(), $_subcategoryId);
    });

    // 监听分页跳转
    $("#pageToolbar").off().on("click", function () {
        if ($(this).data('currentpage') != currentPage) {
            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            getAdList($_typeId, $("#search-input").val());
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            // getServiceList($_typeId, $("#search-input").val());
            getAdList($_typeId, $("#search-input").val(), $_subcategoryId);
        }
    });
}

/*** 获取行业资讯列表 ***/
function getAdList(typeId, searchVal, subcategory_id) {
    if (typeId === undefined) {
        typeId = "202094"
    }
    var json = {
        "types": [
            "c_business_industry_information"
        ],
        "fields": [],
        "filterFields": [],
        "page": currentPage,
        "size": searchSize,
        "sortObjects": [],
        "sortFields": []
    };
    switch ($_tabActive) {
        case 0:
            delete json.sortFields
            delete json.sort
            json.sortObjects = [
                {field: "_score", direction: "DESC"},
                // {field: "created_at", direction: "DESC"},
                {
                    "field": "recommended",
                    "direction": "DESC"
                },
                {
                    "field": "recommended_index",
                    "direction": "DESC"
                }
            ];
            break;
        case 1:
            delete json.sortObjects;
            json.sortFields = ["click_rate"];
            json.sort = "DESC";
            break;
        case 2:
            json.sortFields = ["created_at"];
            json.sort = "DESC";
            break;
    }
    if (!!searchVal) {
        // console.log(searchVal);
        json.fields.push({
            "fields": [
                "title"
            ],
            "values": [
                searchVal
            ],
            "searchType": "stringQuery"
        })
        json.highlightBuilder = {
            "fields": [
                {
                    "name": "title",
                    "preTags": [
                        "<span class='high-light'>"
                    ],
                    "postTags": [
                        "</span>"
                    ]
                }
            ]
        }
    }
    console.log(typeId);
    if (String(typeId) !== "202094" && !!typeId) {
        console.log('a');
        json.fields.push({
            "fields": [
                "type_id"
            ],
            "values": [
                typeId
            ],
            "searchType": "term"
        })
    }
    if (!!subcategory_id) {
        json.fields.push({
            "fields": [
                "subcategory_id"
            ],
            "values": [
                subcategory_id
            ],
            "searchType": "term"
        })
    }
    console.log(json);
    new NewAjax({
        type: "POST",
        url: "/searchEngine/customSearch",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.data_object.totalRecord;
            var data = res.data.data_object.data;
            /* 传的数据总量 开始 */
            if (clickPage === 0) {
                $('#pageToolbar').Paging({pagesize: searchSize, count: totalRecord, toolbar: true});
                $('#pageToolbar').find("div:eq(1)").remove();

            } else {
                $('#pageToolbar').Paging({pagesize: searchSize, count: totalRecord, toolbar: true});
                $('#pageToolbar').find("div:eq(0)").remove();
                clickPage = 0;
            }
            /* 传的数据总量 结束 */
            setAdList(data);
        }
    })
}

/*** 设置行业资讯列表 ***/
function setAdList(list) {
    var infoItems = $(".search-result").children(".info-item");
    // 隐藏不需要的元素
    for (var k = 0; k < infoItems.length; k++) {
        if (list.length < searchSize && k >= list.length) {
            $(infoItems[k]).css("display", 'none')
        } else {
            $(infoItems[k]).css("display", 'block')
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(infoItems[i]).attr("data-id", list[i].data_id); // id
        $(infoItems[i]).find(".info-title").html(list[i].title_highLight ? list[i].title_highLight : list[i].title); // title
        $(infoItems[i]).find(".info-title").attr('title', list[i].title); // title
        $(infoItems[i]).find(".publish-time").html($(this).formatTime(new Date(list[i].created_at)).split(" ")[0]);  // 时间
        $(infoItems[i]).find(".see-count").html(list[i].click_rate);              // 点击量
    }
}

/*** 获取右栏推荐行业资讯列表 ***/
function getRecommendList() {
    var json = {
        "pager": {//分页信息
            "current": 1,   //当前页数0
            "size": 3        //每页条数
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/industryInformation/get_recommend_industry_information",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setRecommendList(list);
        }
    });
}

/*** 设置右栏推荐行业资讯列表 ***/
function setRecommendList(list) {
    var consultationCards = $(".consultation-card");
    for (var i = 0; i < list.length; i++) {
        $(consultationCards[i]).attr("data-id", list[i].id); // id
        if (list[i].type) {
            var type = JSON.parse(list[i].type);
            $(consultationCards[i]).find(".title").html(type.title);
        }
        $(consultationCards[i]).find(".desc").html(list[i].title);  // 描述
        $(consultationCards[i]).find(".desc").attr('title', list[i].title);  // 描述
        $(consultationCards[i]).find(".time").html($(this).formatTime(new Date(list[i].created_at)).split(" ")[0]);  // 时间
        $(consultationCards[i]).find(".look-num").html(list[i].click_rate);  // 点击量
    }
}

// 设置文本超出提示
function setTextOverTip() {
    setTextOverTipOfNewsList();
}

// 绑定产品父框事件
function setTextOverTipOfNewsList() {
    // 获取列表父框
    var nListParent = $('.content-left .search-result').eq(0);
    // 绑定事件
    nListParent.mouseover(eventOfNewsTextOver);
}

//产品文本超出事件
function eventOfNewsTextOver(event) {
    // 获取当前作用节点
    var nNowActive = null;
    // 节点标签名
    var nodeName = event.target.tagName.toLowerCase();
    if (nodeName === 'span' && $(event.target).hasClass('text-overflow')) {
        nNowActive = $(event.target);
    }
    if (nNowActive) {
        layer.closeAll();
        layer.tips(nNowActive.text(), nNowActive, {
            tips: [1, '#000000']
        });
    }
}