/**
 * Created by mu-HUN on 2018/8/20.
 */
var currentPage = 1;
var pageSize = 12;
var $_typeId = "202035";
var $_field = "200000";
var $_tabActive = 0;    // 记录在哪个tab页
var clickPage = 0;  //判断是否点击页数,0为是

//专家类型
industryFieldTypes.unshift({
    title: "不限",
    id: "202035"
});
applicationIndustryTypes.unshift({
    title: "不限",
    id: "200000"
});
//搜索选项
var searchType = [
    {
        title: '专业领域',
        types: industryFieldTypes
    },
    // {
    //     title: '技术领域',
    //     types: applicationIndustryTypes
    // }
];

$(function () {
    init_dom();
    init_data();
    /** 初始化数据 **/
    handleEvent();
    toPeopelCenterExprets();
})

function init_dom() {
    for (var i = 0; i < searchType.length; i++) {
        var li = document.createElement("li");
        $(li).addClass("type-item");
        var div = document.createElement("div");
        $(div).addClass("option-area option-area-hidden");
        $(li).append('<span class="type">' + searchType[i].title + ':&nbsp;&nbsp;</span>');
        for (var j = 0; j < searchType[i].types.length; j++) {
            if (j === 0) {
                if (i === 0) {
                    $(div).append('<span class="option active" data-id="' + searchType[i].types[j].id + '">' + searchType[i].types[j].title + '</span>');
                } else if (i === 1) {
                    $(div).append('<span class="option active" data-typeid="' + searchType[i].types[j].id + '">' + searchType[i].types[j].title + '</span>');
                }
            } else {
                if (i === 0) {
                    $(div).append('<span class="option" data-id="' + searchType[i].types[j].id + '">' + searchType[i].types[j].title + '</span>');
                } else if (i === 1) {
                    $(div).append('<span class="option" data-typeid="' + searchType[i].types[j].id + '">' + searchType[i].types[j].title + '</span>');
                }
            }
            if (j === 7) {
                $(div).append('<span class="open-all">更多<i class="icon-open-arrow"></i></span></span>');
            }
        }
        $(li).append(div);
        $("#type-ul").append(li);
    }
}

/*** 初始化数据 ***/
function init_data() {
    var storage = window.localStorage;
    var searchVal = storage.getItem("searchVal");
    if (searchVal) {
        $(".search-both #search-input").val(searchVal);
        storage.removeItem("searchVal");
        $('.search-both #search-btn').trigger("click");
    }
    //获取专家列表
    getExpertList($_field, $_typeId, $("#search-input").val());
    //获取推荐列表
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
    // 关闭筛选
    $('#close-select').off().click(function () {
        $('.select-area').toggle();
    })

    //跳转到详情页
    $(".expert-intro-card").click(function () {
        var id = $(this).data('id');
        window.location.href = '/f/' + id + '/expert_detail.html?pc=true';
    });
    $(".global-experts-card,.global-experts-card-even").click(function () {
        var id = $(this).data('id');
        window.location.href = '/f/' + id + '/expert_detail.html?pc=true';
    });

    // 处理更多、收起操作
    $(".open-all").click(function () {
        var optionArea = $(this).parent();
        if ($(optionArea).hasClass("option-area-hidden")) {
            $(optionArea).removeClass("option-area-hidden");
            $(this).html('收起<i class="icon-close-arrow"></i>');
        } else {
            $(optionArea).addClass("option-area-hidden");
            $(this).html('更多<i class="icon-open-arrow"></i>');
        }
    });
    // 处理收起、展开筛选操作
    $("#close-select").click(function () {
        if ($(this).children("i").hasClass("icon-close-arrow")) {
            $(this).html('展开筛选<i class="icon-open-arrow"></i>');
            $("#select-area").css("display", 'none');
        } else {
            $(this).html('收起筛选<i class="icon-close-arrow"></i>');
            $("#select-area").css("display", 'block');
        }
    });

    // 处理点击option操作
    $(".option").click(function () {
        // 复原currentPage
        resetCurrentPage();
        $(this).parent().children(".option").removeClass("active");
        $(this).addClass("active");
        if ($(this)[0].dataset.id) {
            $_typeId = $(this)[0].dataset.id;
        } else if ($(this)[0].dataset.typeid) {
            $_field = $(this)[0].dataset.typeid;
        }
        getExpertList($_field, $_typeId, $("#search-input").val());
    });
    // 处理搜索操作
    $("#search-btn").click(function () {
        // 复原currentPage
        resetCurrentPage();
        // 处理请求操作
        getExpertList($_field, $_typeId, $("#search-input").val());
    });
    $('#search-input').on('keypress', function (event) {
        if (event.keyCode == 13) {
            // 复原currentPage
            resetCurrentPage();
            // 处理请求操作
            getExpertList($_field, $_typeId, $("#search-input").val());
        }
    });
    // 切换排序tab
    $(".search-result-area .tab .tab-item").off().on("click", function () {
        // 复原currentPage
        resetCurrentPage();
        $(".search-result-area .tab .tab-item").removeClass("active");
        $(this).addClass("active");
        if ($(this)[0].innerText === "综合排名") {
            $_tabActive = 0;
        } else if ($(this)[0].innerText === "关注度") {
            $_tabActive = 1;
        }
        getExpertList($_field, $_typeId, $("#search-input").val());
    });
    // 监听分页跳转
    $("#pageToolbar").on("click", function () {
        if ($(this).data('currentpage') != currentPage) {
            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            getExpertList($_field, $_typeId, $("#search-input").val());
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            clickPage = 0;
            currentPage = $('#pageToolbar').data('currentpage');
            getExpertList($_field, $_typeId, $("#search-input").val());
        }
    });
}

/** 获取专家列表**/
function getExpertList(fieldId, typeId, searchVal) {
    var json = {
        "types": [
            "c_business_experts"
        ],
        "fields": [
            {
                "fields": [//后台审核状态，必填项
                    "back_check_status_id"
                ],
                "values": [
                    "202050"
                ],
                "searchType": "term"
            }
        ],
        "filterFields": [],
        "page": currentPage,
        "size": pageSize,
        "sortObjects": [
            {field: "_score", direction: "DESC"},
            {
                field: "recommended",
                direction: "DESC"
            },
            {
                field: "recommended_index",
                direction: "DESC"
            }
        ]
        // "sort": "DESC",
        // "sortFields": []
    };
    switch ($_tabActive) {
        case 0:
            delete json.sortFields;
            delete json.sort;
            json.sortObjects = [
                {field: "_score", direction: "DESC"},
                {
                    field: "recommended",
                    direction: "DESC"
                },
                {
                    field: "recommended_index",
                    direction: "DESC"
                }
            ];
            // json.sortFields.unshift("recommended_index")
            break;
        case 1:
            delete json.sortObjects;
            // json.sortFields.unshift("collection_amount");
            json.sortFields = ["click_rate"];
            json.sort = "DESC";
            break;
    }
    if (searchVal) {
        json.fields.push({
            "fields": [
                "name"
            ],
            "values": [
                searchVal
            ],
            "searchType": "stringQuery"
        });
        json.highlightBuilder = {
            "fields": [
                {
                    "name": "name",
                    "preTags": [
                        "<span class='high-light'>"
                    ],
                    "postTags": [
                        "</span>"
                    ]
                }
            ]
        };
    }
    if (parseInt(typeId) !== 202035) {
        json.fields.push({
            "fields": [
                "profession_field_id"
            ],
            "values": [
                typeId
            ],
            "searchType": "term"
        });
    }
    if (parseInt(fieldId) !== 200000) {
        json.fields.push({
            "fields": [
                "industry_id_id"
            ],
            "values": [
                "/.*" + fieldId + ".*/"   //搜索引擎正则查询
            ],
            "searchType": "stringQuery"     //正则查询是使用模糊匹配
        });
    }
    $.ajax({
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
                $('#pageToolbar').Paging({pagesize: pageSize, count: totalRecord, toolbar: true});
                $('#pageToolbar').find("div:eq(1)").remove();
            } else {
                $('#pageToolbar').Paging({pagesize: pageSize, count: totalRecord, toolbar: true});
                $('#pageToolbar').find("div:eq(0)").remove();
                clickPage = 0;
            }
            setExpertListToPage(data);
        }
    })
}

//回显数据
function setExpertListToPage(list) {
    var expertCards = $("#search-result-list").children(".expert-intro-card");
//    隐藏多余的模板
    for (var k = 0; k < expertCards.length; k++) {
        if (list.length < pageSize && k >= list.length) {
            $(expertCards[k]).css("display", "none");
        } else {
            $(expertCards[k]).css("display", "inline-block");
        }
    }
    for (var i = 0; i < list.length; i++) {
        $(expertCards[i]).attr("data-id", list[i].data_id);
        if (!!list[i].personal_image) {
            var image = JSON.parse(list[i].personal_image);
            $(expertCards[i]).find(".avatar").attr("src", $(this).getAvatar(image[0].id));
        } else {
            $(expertCards[i]).find(".avatar").attr("src", '/static/assets/defalutexpretTitle.png');
        }
        //领域
        if (!!list[i].profession_field) {
            $(expertCards[i]).find(".specialty").html(list[i].profession_field);
        } else {
            $(expertCards[i]).find(".specialty").html('暂无数据');
        }
        // $(expertCards[i]).find(".specialty").html(list[i].profession_field);
        //职位
        $(expertCards[i]).find(".junior").html(list[i].technical_title);
        //姓名
        $(expertCards[i]).find(".name").html(list[i].name_highLight ? list[i].name_highLight : list[i].name);
        $(expertCards[i]).find(".name").attr('title', list[i].name);
    }
}

//获取推荐列
function getRecommendList() {
    var json = {
        "pager": {//分页信息
            "current": 1,   //当前页数0
            "size": 5        //每页条数
        }
    };
    $.ajax({
        type: 'POST',
        url: '/f/experts/get_recommend_experts',
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setRecommendList(list);
        }
    })
}

/** 设置推荐栏**/
function setRecommendList(list) {
    var expertsCards = $(".global-experts-card");
    // 隐藏不需要的元素
    for (var k = 0; k < expertsCards.length; k++) {
        if (list.length < 5 && k >= list.length) {
            $(expertsCards[k]).css("display", 'none');
        } else {
            $(expertsCards[k]).css("display", 'block');
        }
    }
    for (var i = 0; i < list.length; i++) {
        console.log(list[i]);
        $(expertsCards[i]).attr("data-id", list[i].id);
        $(expertsCards[i]).find('.title').html(list[i].name);
        $(expertsCards[i]).find('.title').attr('title', list[i].name);
        $(expertsCards[i]).find('.desc').html(list[i].personal_profile);
        $(expertsCards[i]).find('.experts-type-area').html(list[i].technical_title);
        var field = eval('(' + list[i].profession_field + ')');
        var str = ''
        if (!!field) {
            for (var j = 0; j < field.length; j++) {
                str += field[j].title;
                if (j != field.length - 1) {
                    str += ",";
                }
            }
        }
        $(expertsCards[i]).find('.experts-type').html(list[i].technical_title);
    }
}

function toPeopelCenterExprets() {
    $('.content-right .first-botton').click(function () {
        var href = $(this).attr('data-href');
        window.location.href = '/f/personal_center.html?pc=true&menu=expertPersonalInfo'
    })
}
