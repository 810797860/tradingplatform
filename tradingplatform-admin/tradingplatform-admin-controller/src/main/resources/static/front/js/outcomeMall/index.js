/**
 * Created by 空 on 2018/10/15.
 */
$(function () {
    // var totalRecord = 6;
    var pageSize = 6;
    var currentPage = 1;
    var clickPage = 0;  //判断是否点击页数,0为是
    outcomeMallTypes.unshift({
        title: "不限",
        id:"202094"
    });
    var searchType = [
        {
            title: '专业领域',
            types: outcomeMallTypes
        }
    ];
    console.log(searchType.length);
    console.log(searchType);

    initPage();
    handleDetail();
    //获取最新专利
    getRightNewData();
    // 获取专利TAP页数据
    getNewDataOfOutcome();
    //右侧栏跳转详情页
    rightClickToDetail();
    //主页跳转详情页
    indexClickToDetail();
    // 跳去个人中心发布案例
    toPeopelCenterPublishCase();

    /* 传的数据总量 开始 */
    function initPage() {


        // 搜索类型的初始化
        console.log(searchType.length);
        for (var i = 0; i < searchType.length; i++) {
            var li = document.createElement("li");
            $(li).addClass("type-item");
            var div = document.createElement("div");
            $(div).addClass("option-area option-area-hidden");
            $(li).append('<span class="type">' + searchType[i].title + ':&nbsp;&nbsp;</span>');
            for (var j = 0; j < searchType[i].types.length; j++) {
                if (j === 0) {
                    if (i === 0) {
                        $(div).append('<span class="option active" data-id="' + searchType[i].types[j].id+'">'+searchType[i].types[j].title+'</span>');
                    } else if (i === 1) {
                        $(div).append('<span class="option active" data-typeid="'+searchType[i].types[j].id+'">'+searchType[i].types[j].title+'</span>');
                    }
                } else {
                    if (i === 0) {
                        $(div).append('<span class="option" data-id="'+searchType[i].types[j].id+'">'+searchType[i].types[j].title+'</span>');
                    } else if (i === 1) {
                        $(div).append('<span class="option" data-typeid="'+searchType[i].types[j].id+'">'+searchType[i].types[j].title+'</span>');
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

    function handleDetail() {
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
            if($(this).children("i").hasClass("icon-close-arrow")) {
                $(this).html('展开筛选<i class="icon-open-arrow"></i>');
                $("#select-area").css("display", 'none');
            } else {
                $(this).html('收起筛选<i class="icon-close-arrow"></i>');
                $("#select-area").css("display", 'block');
            }
        });

        // 处理点击option操作
        $(".option").off().click(function () {
            // 复原currentPage
            resetCurrentPage();
            $(this).parent().children(".option").removeClass("active");
            $(this).addClass("active");
            if ($(this)[0].dataset.id) {
                $_typeId = $(this)[0].dataset.id;
            } else if($(this)[0].dataset.typeid) {
                $_field = $(this)[0].dataset.typeid;
            }
            // getExpertList($_field, $_typeId, $("#search-input").val());
            getNewDataOfOutcome($("#search-input").val());
        });

        // 处理搜索操作
        $("#search-btn").click(function () {
            // 复原currentPage
            resetCurrentPage();
            // 处理请求操作
            // getExpertList($_field, $_typeId, $("#search-input").val());
            getNewDataOfOutcome($("#search-input").val());
        });

        $('#search-input').on('keypress',function(event){
            if(event.keyCode == 13) {
                // 复原currentPage
                resetCurrentPage();
                // 处理请求操作
                getNewDataOfOutcome();
            }
        });

        // 监听分页跳转
        $("#pageToolbar").on("click", function(){
            console.log('a');
            if($(this).data('currentpage') != currentPage) {
                clickPage = 0;
                // currentPage = $('#pageToolbar').attr('data-currentpage');
                currentPage = $('#pageToolbar').data('currentpage');
                // getExpertList($_field, $_typeId, $("#search-input").val());.
                getNewDataOfOutcome($("#search-input").val());
            }
        }).keydown(function(e) {
            console.log('b');
            if (e.keyCode == 13) {
                clickPage = 0;
                // currentPage = $('#pageToolbar').attr('data-currentpage');

                currentPage = $('#pageToolbar').data('currentpage');
                console.log(currentPage);
                // getExpertList($_field, $_typeId, $("#search-input").val());
                getNewDataOfOutcome($("#search-input").val());
            }
        });
    }

    function resetCurrentPage () {
        clickPage = 1;
        $('#pageToolbar').data('currentpage', 1);
        currentPage = 1;
        $('#pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
    }


    function getRightNewData() {
        var json = {
            pager:{//分页信息
                "current":1,   //当前页数0
                "size": 5       //每页条数
            }
        }
        new NewAjax({
            url: '/f/patents/get_latest_patents?pc=true',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                var list = res.data.data_list;
                console.log($('.recommand-experts .global-experts-card'));
                for (var i = 0; i < list.length; i++) {
                    $('.recommand-experts .global-experts-card').eq(i).attr('data-toDetailId', list[i].id);
                    $('.recommand-experts .global-experts-card').eq(i).find('.title').text(list[i].title);
                    $('.recommand-experts .global-experts-card').eq(i).find('.title').attr('title',list[i].title);
                    $('.recommand-experts .global-experts-card').eq(i).find('.desc').text(list[i].summary);
                    if (!!list[i].type) {
                        $('.recommand-experts .global-experts-card').eq(i).find('.experts-type').text(JSON.parse(list[i].type).title);
                    } else {
                        $('.recommand-experts .global-experts-card').eq(i).find('.experts-type').text('暂无数据');
                    }
                }
            }
        })
    }

    function getNewDataOfOutcome(searchInput) {
        var json = {
            types: [
                "c_business_patents"
            ],
            fields: [],
            filterFields: [],
            highlightBuilder: {
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
            },
            page: currentPage,
            size: 6,
            sortObjects: [
                {field: "_score", direction: "DESC"},
                {
                    "field": "recommended",
                    "direction": "DESC"
                },
                {
                    "field": "recommended_index",
                    "direction": "DESC"
                },
                {
                    "field": "created_at",
                    "direction": "DESC"
                }
            ]
        }
        var tapId = $('.option-area .active').attr('data-id');
        if (tapId == 202094) {
            json.fields = [];
            json.fields.push({
                fields: [
                    "type_id"
                ],
                values: ["202091","202092","202093"],
                searchType: "term"
            });
        } else {
            json.fields = [];
            json.fields.push({
                fields: [
                    "type_id"
                ],
                values: [tapId],
                searchType: "term"
            });
        }
        if (!!searchInput) {
            json.fields.push(
                {
                    fields: [
                        "title"
                    ],
                    values: [searchInput],
                    searchType: "stringQuery"
                }
            );
        }
        new NewAjax({
            url: '/searchEngine/customSearch',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_object.data;
                var totalRecord = res.data.data_object.totalRecord;
                // console.log(totalRecord);
                // console.log($('#pageToolbar'));
                if(clickPage === 0){
                    $('#pageToolbar').Paging({pagesize: 6,count:totalRecord,toolbar:true});
                    $('#pageToolbar').find("div:eq(1)").remove();
                }else {
                    $('#pageToolbar').Paging({pagesize: 6,count:totalRecord,toolbar:true});
                    $('#pageToolbar').find("div:eq(0)").remove();
                    clickPage = 0;
                }
                console.log(list.length);
                console.log($('.outcome-mall-item').length);
                for (var i = 0; i < list.length; i++) {
                    if (i < list.length) {
                        $('.outcome-mall-item').eq(i).show();
                        $('.outcome-mall-item').eq(i).attr('data-resultId', list[i].data_id);
                        if (!!list[i].picture) {
                            $('.outcome-mall-item-img').eq(i).find('img').attr('src', '/adjuncts/file_download/' + JSON.parse(list[i].picture)[0].id)
                        } else {
                            $('.outcome-mall-item-img').eq(i).find('img').attr('src', '/static/front/assets/image/empty3.jpg');
                        }
                        $('.outcome-mall-item-title-p').eq(i).text(list[i].title);
                        $('.outcome-mall-item-title-p').eq(i).attr('title',list[i].title);
                        $('.outcome-mall-item-all-peopel-second').eq(i).text(list[i].patent_number);
                        $('.outcome-mall-item-time-check-time').eq(i).text('发布于' + ' ' + $(this).formatTime(new Date(list[i].created_at)));
                        $('.outcome-mall-item-time-check-check').eq(i).text('浏览量' + ' ' +  list[i].click_rate);
                        $('.outcome-mall-item-introduction').eq(i).text(list[i].summary);
                    }
                }
                if (list.length < $('.outcome-mall-item').length) {
                    var num = $('.outcome-mall-item').length - list.length
                    console.log(num);
                    for (var i = 5; i > (5-num); i--) {
                        $('.outcome-mall-item').eq(i).hide();
                    }
                }
            }
        })
    }

    function rightToDetail(id) {
        var url = '/f/'+ id +'/patents_detail.html?pc=true';
        window.open(url,'_self');
        // new NewAjax({
        //     url: url,
        //     contentType: 'application/json',
        //     type: 'GET',
        //     success: function (res) {
        //         console.log(res);
        //     }
        // })
    }

    function rightClickToDetail() {
        $('.global-experts-card').click(function () {
            var id = $(this).attr('data-todetailid');
            rightToDetail(id);
        })
    }

    function indexClickToDetail() {
        $('.outcome-mall-item').click(function () {
            var id = $(this).attr('data-resultId');
            rightToDetail(id);
        })
    }

    function toPeopelCenterPublishCase() {
        $('.content-right .first-botton').click(function () {
            var href = $(this).attr('data-href');
            new NewAjax({
                url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    if (res.data.data_object !== null && !!res.data.data_object.back_check_status){
                        if (JSON.parse(res.data.data_object.back_check_status).id == 202050){
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
            })
        })
    }
})