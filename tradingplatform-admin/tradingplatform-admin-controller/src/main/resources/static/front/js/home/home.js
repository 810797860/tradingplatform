$(function () {
    /**
     * 定义轮播图变量
     * author：GloryAce
     */
        // 获取轮播图数据
    var sliderDataArr = [];
    // 当前展示图的下标
    var sliderShowIndex = 0;
    // 获取轮播图区域
    var sliderAreaNode = $('.homeSliderDiv .homeSliderImgLinkDiv').eq(0);
    // 轮播图的点击记录
    var isSliderClick = false;
    // 轮播的执行定时器
    var sliderRunTimer = null;
    // 轮播图动画延迟时间
    var delayTime = 3000;
    // 动画位移距离
    var sliderDistance = 750;

    /**
     * 定义资讯tab变量
     * author：GloryAce
     */
    var oUlHomeNoticeList = $(".homeNoticeInfoDiv .homeNoticeListCoverDiv .homeNoticeListUl");
    var oHomeDemandNoticeListMiddleLayer = $(".demandNotice .homeNoticeListMiddleLayer");
    var oHomeInformationNoticeListMiddleLayer = $(".informationNotice .homeNoticeListMiddleLayer");
    var homeNoticeListTitleDivTab = 0;
    var demandNoticeList = [];
    var informationNoticeList = [];
    var searchSizeOfNotice = 16;

    /**
     * 定义热门产品变量
     * author：GloryAce
     */
    var oUlProductIndustry = $(".product-model .type-body .type-area").get(0);
    var aProductIndustryItem = $(oUlProductIndustry).find(".type-item");
    var iProductIndustryId = aProductIndustryItem.eq(0).data("id");
    var oProductIndustryRightBtn = $(".product-model .type-body .right-btn");
    var oProductIndustryLeftBtn = $(".product-model .type-body .left-btn");
    var iProductInduTotalWidth = 0;
    var iUlProductIndustryPaddingLeft = 50;
    var iProductIndustryCurrentPage = 1;

    var oUlProductContentNode = $(".product-content");
    var sLiProductContentHtml = '<li class="product-content-item float-l"></li>';
    var oUlProductNode = $('<ul class="product-content-item-content clearfix"></ul>');
    var sLiProductHtml = '<li class="product-item float-l">' +
                            '<a class="product-item-content" href="javascript:void(0)" target="_blank">' +
                                '<p class="product-title text-overflow"></p>' +
                                // '<p class="product-industry text-overflow"></p>' +
                                '<div class="product-img-area">' +
                                    '<img class="product-img" src="" alt="">' +
                                    '<div class="product-info clearfix">' +
                                        '<p class="float-l product-col product-money"></p>' +
                                        '<p class="float-l product-col product-dock"></p>' +
                                    '</div>' +
                                '</div>' +
                            '</a>' +
                        '</li>';


    /**
     * 定义龙头企业变量
     * author：GloryAce
     */
    var oUlCompanyIndustry = $(".company-model .type-body .type-area").get(0);
    var aCompanyIndustryItem = $(oUlCompanyIndustry).find(".type-item");
    var iCompanyIndustryId = aCompanyIndustryItem.eq(0).data("id");
    var oCompanyIndustryRightBtn = $(".company-model .type-body .right-btn");
    var oCompanyIndustryLeftBtn = $(".company-model .type-body .left-btn");
    var iCompanyInduTotalWidth = 0;
    var iCompanyIndustryCurrentPage = 1;

    var oUlCompanyContentNode = $(".company-content");
    var sLiCompanyContentHtml = '<li class="company-content-item float-l"></li>';
    var oUlCompanyNode = $('<ul class="company-content-item-content clearfix"></ul>');
    var sLiCompanyHtml = '<li class="company-item float-l">' +
                                '<a class="company-item-content" target="_blank">'+
                                    '<img class="company-img" src="" alt="">' +
                                    '<p class="company-title"></p>' +
                                    //text-overflow
                                    // '<p class="company-industry text-overflow"></p>' +
                                '</a>' +
                        '</li>';

    /**
     * 定义数字展厅变量
     * author：GloryAce
     */
    var oUlNumberIndustry = $(".number-model .type-body .type-area").get(0);
    var aNumberIndustryItem = $(oUlNumberIndustry).find(".type-item");
    var iNumberIndustryId = aNumberIndustryItem.eq(0).data("id");
    var oNumberIndustryRightBtn = $(".number-model .type-body .right-btn");
    var oNumberIndustryLeftBtn = $(".number-model .type-body .left-btn");
    var iNumberInduTotalWidth = 0;
    // var iNumberIndustryCurrentPage = 1;

    var oUlNumberContentNode = $(".number-content");
    /*var sLiNumberContentHtml = '<li class="number-content-item float-l"></li>';
    var oUlNumberNode = $('<ul class="number-content-item-content clearfix"></ul>');
    var sLiNumberHtml = '<li class="number-item float-l"><a class="number-item-content" href="javascript:void(0)"><img class="number-img" src="" alt=""><p class="number-title"></p><div class="industry-area"></div></a></li>';
    var sPNumberIndustryItemHtml = '<p class="industry-item"></p>';
    var iPNumberIndustryHeight = 22;
    var iDivNumberIndustryHeight = [];
    var iDivNumberIndustryPaddingV = 30;*/
    var numberList = {
        '001': [
            {
                id: 1,
                title: '高端装备制造产业',
                cover: '/static/assets/number-equipment.jpg',
                info: ['自动化', '数字化', '只能化']
            },
            {
                title: '新一代信息技术产业',
                cover: '/static/assets/number-information-tech.jpg',
                info: ['待开发']
            },
            {
                title: '新材料产业',
                cover: '/static/assets/number-material.jpg',
                info: ['待开发']
            },
            {
                title: '生物产业',
                cover: '/static/assets/number-biological.jpg',
                info: ['待开发']
            },
            {
                title: '节能环保',
                cover: '/static/assets/number-energy-saving.jpg',
                info: ['待开发']
            },
            {
                title: '新能源',
                cover: '/static/assets/number-energy.jpg',
                info: ['待开发']
            },
            {
                title: '海洋经济',
                cover: '/static/assets/number-marine-economy.jpg',
                info: ['待开发']
            },
            {
                title: '数字创意产业',
                cover: '/static/assets/number-digital-creativity.jpg',
                info: ['待开发']
            }
        ]
    };
    // 列表子项的html
    var sLiHtml = '<li class="number-content-item"></li>';
    // 图片子项的html
    var sImageDivHtml = '<div class=""><img class="number-picture" src="" alt=""></div>';
    // 展厅入口的html
    var hallEntranceHtml = '<div class="number-hall-entrance" style="">\n' +
        '                        <img class="number-picture" src="" alt="" style="">\n' +
        '                        <div class="number-hall-entrance-content">\n' +
        '                            <p class="number-hall-entrance-title"></p>\n' +
        '                            <p class="number-hall-entrance-en"></p>\n' +
        '                            <a class="number-hall-entrance-btn" href="" target="_blank">进入展厅</a>\n' +
        '                        </div>\n' +
        '                    </div>';
    // 存储id数组
    var aHallIds = [];
    // 存储展厅项数组
    var aHalls = {};
    // 当前选中id
    var sNowSelectId = null;
    // 展厅loading节点
    var oNumberHallLoadingNode = $('.number-model .number-mask').eq(0);
    // 静态数据
    var DATA = {
        "id": 1,
        "typeId": 202151,
        "titleCN": "高端装备制造",
        "titleEN":"High-end equipment manufacturing",
        "picArr": [
            {"src": "/static/front/assets/image/hall1.jpg", "title": "展区1", "id": "1"},
            {"src": "/static/front/assets/image/hall2.jpg", "title": "展区2", "id": "2"},
            {"src": "/static/front/assets/image/hall3.jpg", "title": "展区3", "id": "3"},
            {"src": "/static/front/assets/image/hall4.jpg", "title": "展区4", "id": "4"},
            {"src": "/static/front/assets/image/hall5.jpg", "title": "展区5", "id": "5"},
            {"src": "/static/front/assets/image/hall6.png", "title": "展区6", "id": "6"}
        ]
    };

    /**
     * 定义战略伙伴变量
     * author：GloryAce
     */
    var partnerItemHtml = '<a class="home-partner-item" href="" rel="nofollow" target="_blank"><img class="home-partner-logo" src="" alt="" title=""></a>';
    // 战略伙伴的请求参数
    var partner = {
        pager: {
            current: 1,
            size: 14
        },
        sortPointer: {
            filed: "num_sort",
            order: "DESC"
        }
    };
    // 获取战略伙伴的列表节点
    var partnerListNode = $('.home-partner-list').eq(0);
    // 是否已经请求完所有的战略伙伴
    var isHaveAllPartner = false;
    // 是否正在请求
    var isPartnerRequest = false;
    // 是否左移
    var isLeftMove = false;
    // 是否右移
    var isRightMove = false;
    // 是否可以左移
    var isCanLeftMove = true;
    // 是否可以右移
    var isCanRightMove = false;
    // 是否移动中
    var isPartnerMoving = false;
    // 记录请求的页码
    var partnerPageNum = 1;
    // 记录请求数量
    var partnerPageSize = 14;


    /**
     * 函数调用
     * author：GloryAce
     */
    // 修改行业图片和样式
    resetIconAndStyleOfIndustry($('.homeContentDiv .homeExpresswayDiv').eq(0));
    // 轮播图
    getSliderArr(function (picList) {
        // 设置轮播图
        setShufflingFigures(picList);
        // 启动轮播
        startSlider();
    });
    // 需求采购
    getDemandArr(setDemandList);
    getInformationArr();
    eventOfNoticeTabClick();
    // 热门产品
    $(aProductIndustryItem[0]).addClass("active");
    getProductList(setProductList, eventOfProduct);
    eventOfProductIndustryBtn();
    eventOfProductIndustryItem();
    // 数字展厅
    aNumberIndustryItem.eq(0).addClass('active');
    getNumberList();
    eventOfNumberIndustryBtn();
    eventOfNumberIndustryItem();
    // 龙头企业
    aCompanyIndustryItem.eq(0).addClass('active');
    getCompanyList(eventOfCompany);
    eventOfCompanyIndustryBtn();
    eventOfCompanyIndustryItem();
    // 战略伙伴
    getPartnerArr(function (partnerList) {
        // 创建战略伙伴
        createPartner(partnerList);
        if (isVersionOverNine()) {
            eventOfPartnerAnimation();
        }
        eventOfPartnerClick();
    });


    /**
     * 轮播图模块
     * author：GloryAce
     */
    // 轮播图数据请求
    function getSliderArr(callback) {
        // 获取轮播图数据
        $.ajax({
            url: '/f/shufflingFigure/202047/get_by_type',
            contentType: 'application/json',
            type: 'get',
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                console.error('轮播图', err);
            }
        })
    }
    // 开启动画
    function startSlider () {
        sliderRunTimer = setInterval(sliderAnimateStart, delayTime);
        eventOfSlider();
    }
    // 获取轮播图
    function setShufflingFigures (list) {
        // 添加第一张到最后的位置
        list.push(list[0]);
        // 存储轮播图承载区域节点
        var sliderListAreaNode = $('<div class="homeSliderList"></div>');
        // 存储轮播图节点html
        var sliderNodeHtml = '<a class="homeSliderImgLink" href="" target="_blank">\n' +
            '                     <img class="homeSliderImg" src="" alt="">\n' +
            '                  </a>';
        // 存储轮播图节点
        var newSliderNode = null;
        // 轮播图切换点区域
        var sliderPointAreaNode = $('<div class="slider-point-area"></div>');
        // 存储slider-point节点HTML
        var sliderPointNodeHtml = '<i class="slider-point"></i>';
        // 存储slider-point节点
        var newSliderPointNode = null;
        // 清除换行空格
        var stSave = sliderNodeHtml.replace(/>\s+<(\/)?/g, function (str) {
            // 去除多余的空格换行
            return (str.indexOf('/') > -1) ? '></' : '><';
        });
        // 获取左背景图
        var leftBgNode = $('.homeSliderDiv .homeSliderLeftPartDiv').eq(0);
        // 获取右背景图
        var rightBgNode = $('.homeSliderDiv .homeSliderRightPartDiv').eq(0);
        // 清空轮播图列表
        /*sliderList.empty().css({
            width: sliderDistance * list.length + 'px',
            left: 0
        });*/
        // 清空轮播图列表
        sliderListAreaNode.empty().css({
            width: sliderDistance * list.length + 'px',
            left: 0
        });
        sliderPointAreaNode.empty().css({
            width: 30 * list.length + 'px',
            marginLeft: -(30 * list.length) / 2 + 'px'
        });
        sliderDataArr = [].concat(list);
        // 遍历数组
        list.forEach(function (item, index) {
            if (index === 0) {
                leftBgNode.css({
                    'backgroundImage': 'url(' + getAvatar(item.picture) + ')'
                });
                rightBgNode.css({
                    'backgroundImage': 'url(' + getAvatar(item.picture) + ')'
                });
            }
            // 获取新节点
            newSliderNode = $(stSave);
            // 写入链接
            newSliderNode.attr({
                href: item.url
            }).find('img').eq(0).attr({
                src: getAvatar(item.picture),
                alt: item.title + ((index + 1) === list.length ?  1 : (index + 1))
            });
            if (index < list.length - 1) {
                // 获取新的slider-point节点
                newSliderPointNode = index === 0 ? $(sliderPointNodeHtml).addClass("slider-point-select") : $(sliderPointNodeHtml);
                sliderPointAreaNode.append(newSliderPointNode); // 轮播图point节点
            }
            // 插入节点
            // sliderList.append(newSliderNode);   // 有缺点：绘制了list.length次
            sliderListAreaNode.append(newSliderNode);   // 轮播图节点
        });
        sliderAreaNode.append(sliderListAreaNode).append(sliderPointAreaNode);
    }
    // 设置轮播图point选中
    function setSliderPointSelect(index) {
        $(".homeSliderDiv .homeSliderImgLinkDiv .slider-point-area .slider-point")
            .removeClass("slider-point-select").eq(index)
            .addClass("slider-point-select");
    }
    // 轮播图动画
    function sliderAnimateStart (direction) {
        // 获取轮播图承载区域
        var sliderList = $('.homeSliderDiv .homeSliderImgLinkDiv .homeSliderList').eq(0);
        var liLength = sliderDataArr.length;

        // 获取图片对应节点
        var picNodeArr = sliderList.find('a');
        // 当前图片路径
        var nowPicUrl = null;
        // 获取左背景图
        var leftBgNode = $('.homeSliderDiv .homeSliderLeftPartDiv').eq(0);
        // 获取右背景图
        var rightBgNode = $('.homeSliderDiv .homeSliderRightPartDiv').eq(0);

        if (!direction) {
            direction = "left";
        }
        if (direction === "left") {
            if (sliderShowIndex < (liLength - 2)) {
                sliderShowIndex++;
                setSliderPointSelect(sliderShowIndex);
            } else if (sliderShowIndex < (liLength - 1)) {
                sliderShowIndex++;
                setSliderPointSelect(0);
            } else {
                sliderShowIndex = 1;
                sliderList.css("left", 0);
                setSliderPointSelect(1);
            }
        } else if (direction === "right") {
            if (sliderShowIndex > 1) {
                sliderShowIndex--;
                setSliderPointSelect(sliderShowIndex);
            } else if (sliderShowIndex > 0) {
                sliderShowIndex--;
                setSliderPointSelect(0);
            } else {
                sliderShowIndex = liLength - 2;
                sliderList.css("left", -sliderDistance * (sliderShowIndex + 1));
                setSliderPointSelect(sliderShowIndex);
            }
        }
        nowPicUrl = picNodeArr.eq(sliderShowIndex).find('img').eq(0).attr('src');
        sliderList.stop().animate({
            left: -sliderDistance * sliderShowIndex
        }, function () {
            leftBgNode.css({
                'backgroundImage': 'url(' + nowPicUrl + ')'
            });
            rightBgNode.css({
                'backgroundImage': 'url(' + nowPicUrl + ')'
            });
        });
    }
    // 轮播图事件：鼠标移进移除、点击point
    function eventOfSlider () {
        // 获取轮播图承载区域
        var sliderList = $('.homeSliderDiv .homeSliderImgLinkDiv .homeSliderList').eq(0);
        // 左背景图
        var nLeftBgDiv = $('.homeSliderDiv .homeSliderLeftPartDiv').eq(0);
        // 右背景图
        var nRightBgDiv = $('.homeSliderDiv .homeSliderRightPartDiv').eq(0);
        // 轮播图节点数组
        var aSliderItem = sliderList.children();
        var stSave = null;
        // 轮播图区域：鼠标进入移除事件
        sliderAreaNode.mouseover(function () {
            if (sliderRunTimer) {
                clearInterval(sliderRunTimer);
                sliderRunTimer = null;
            }
        }).mouseleave(function () { // 使用leave的原因是子元素与父元素之间会触发out
            if (!sliderRunTimer) {
                sliderRunTimer = setInterval(sliderAnimateStart, delayTime);
            }
        });
        // 轮播图点
        sliderAreaNode.on("click", ".slider-point-area .slider-point", function () {
            sliderShowIndex = $(this).index();
            stSave = aSliderItem.eq(sliderShowIndex).find('img').eq(0).attr('src');
            sliderList.stop().animate({
                left: -sliderDistance * sliderShowIndex
            }, function () {
                nLeftBgDiv.css({
                    backgroundImage: 'url(' + stSave + ')'
                });
                nRightBgDiv.css({
                    backgroundImage: 'url(' + stSave + ')'
                });
            });
            setSliderPointSelect(sliderShowIndex);
        });
    }

    /**
     * 最新资讯、需求采购模块
     * author：GloryAce
     */
    // 需求采购数据请求
    function getDemandArr(callback) {
        var json = {
            "sortPointer":{//分页信息
                "order":"desc",   //排序
                "field":"updatedAt"  //排序字段
            },
            "pager":{//分页信息
                "current": 1,   //当前页数
                "size": searchSizeOfNotice        //每页条数
            }
        };
        new NewAjax({
            url: '/f/projectDemand/query_latest_demand',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200 && res.data.data_list.length > 0) {
                    var totalRecord = res.data.total;
                    demandNoticeList = res.data.data_list;
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                console.error('需求采购', err);
            }
        });
    }
    // 设置需求采购列表
    function setDemandList(list) {
        var oUlDemandInformation = $('<ul class="homeNoticeList"></ul>');
        for (var i = 0; i < list.length; i++) {
            var oDemandInformationItem = $('<li class="homeNoticeItem"><a class="homeNoticeItemLink" target="_blank"></a></li>');
            oDemandInformationItem.children().attr('title', list[i]['name']).html(list[i]['name']).attr('href', '/f/' + list[i].id + '/demand_detail.html?pc=true');
            oUlDemandInformation.append(oDemandInformationItem);
        }
        oHomeDemandNoticeListMiddleLayer.append(oUlDemandInformation);
    }
    // 最新资讯数据请求
    function getInformationArr(callback) {
        var json = {
            "sortPointer":{//分页信息
                "order":"desc",   //排序
                "field":"updatedAt"  //排序字段
            },
            "pager":{//分页信息
                "current": 1,   //当前页数
                "size": searchSizeOfNotice        //每页条数
            }
        };
        new NewAjax({
            url: '/f/industryInformation/query_latest_information',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200 && res.data.data_list.length > 0) {
                    var totalRecord = res.data.total;
                    informationNoticeList = res.data.data_list;
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                console.error('最新资讯', err);
            }
        });
    }
    // 设置资讯列表
    function setInformationList(list) {
        var oUlInformationNotice = $('<ul class="homeNoticeList"></ul>');
        for (var i = 0; i < list.length; i++) {
            var oInformationNoticeItem = $('<li class="homeNoticeItem"><a class="homeNoticeItemLink" target="_blank"></a></li>');
            oInformationNoticeItem.children().attr('title', list[i]['title']).html(list[i]['title']).attr('href', '/f/' + list[i].id + '/platform_ad_detail.html?pc=true');
            oUlInformationNotice.append(oInformationNoticeItem);
        }
        oHomeInformationNoticeListMiddleLayer.append(oUlInformationNotice);
    }
    // 监听tab点击
    function eventOfNoticeTabClick () {
        $(".homeNoticeListTitleDiv .homeNoticeListTitle").off().on("click", function () {
            var self = this;
            $(this).addClass("homeNoticeListTitle__select").siblings().removeClass("homeNoticeListTitle__select");
            if ($(this).index() === 1) {
                oHomeDemandNoticeListMiddleLayer[0].scrollTop = 0;
                oHomeDemandNoticeListMiddleLayer.css("overflowY", 'hidden');
                homeNoticeListTitleDivTab = 1;
                if (!informationNoticeList.length) {
                    getInformationArr(setInformationList);
                } else if (informationNoticeList.length && oHomeInformationNoticeListMiddleLayer.find("li").length === 0) {
                    setInformationList(informationNoticeList);
                }
            } else if ($(this).index() === 0) {
                oHomeInformationNoticeListMiddleLayer[0].scrollTop = 0;
                oHomeInformationNoticeListMiddleLayer.css("overflowY", 'hidden');
                homeNoticeListTitleDivTab = 0;
                if (!demandNoticeList.length) {
                    getDemandArr(setDemandList);
                } else if(demandNoticeList.length && oHomeDemandNoticeListMiddleLayer.find("li").length === 0) {
                    setDemandList(demandNoticeList);
                }
            }
            startMove(oUlHomeNoticeList[0], {left: -240 * $(this).index()}, function () {
                if ($(self).index() === 1) {
                    oHomeInformationNoticeListMiddleLayer.css("overflowY", 'auto');
                } else if ($(self).index() === 0) {
                    oHomeDemandNoticeListMiddleLayer.css("overflowY", 'auto');
                }
            });
        });
    }



    /**
     * 热门产品模块
     * author：GloryAce
     */
    // 获取热门产品数据
    function getProductList (callback, eventFunc) {
        var size = 6;
        var json  = {
            applicationIndustry: iProductIndustryId,
            sortPointer:{
                filed:"recommended_index",
                order:"DESC"
            },
            pager: {
                current: 1,
                size: size
            }
        };
        $.ajax({
            url: '/f/matureCase/query?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    if (list.length > 0) {
                        $('.product-content').show();
                        $('.product-content-no-data').hide();
                        if (callback) {
                            callback(size, list, eventFunc);
                        }
                    } else {
                        $('.product-content').hide();
                        $('.product-content-no-data').show();
                    }
                }
            },
            error: function (err) {
                console.error('产品', err);
            }
        });
    }
    // 设置热门产品数据
    function setProductList (size, list, callback) {
        var oLiProductContentNode = $(sLiProductContentHtml);
        for (var i = 0; i < list.length; i++) {
            var oLiProductNode = $(sLiProductHtml);
            oLiProductNode.find(".product-item-content").attr('href', '/f/' + list[i].id + '/case_detail.html?pc=true');
            oLiProductNode.find(".product-img").attr('src', list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(',')[0]) : null);
            oLiProductNode.find(".product-title").text(list[i].title);
            // oLiProductNode.find('.product-industry').text('行业：' + (list[i].application_industry ? JSON.parse(list[i].application_industry).title: '暂无数据'));
            oLiProductNode.find('.product-money').text((list[i].case_money ? '¥' + list[i].case_money + '万元' : '面议'));
            oLiProductNode.find('.product-dock').text(list[i].click_rate);
            oUlProductNode.append(oLiProductNode);
        }
        if (i <= size) {
            for (var j = 0; j < size - i + 1; j++) {
                var oLiProductNode = $(sLiProductHtml);
                oLiProductNode.css("display", 'none');
                oUlProductNode.append(oLiProductNode);
            }
        }
        oLiProductContentNode.append(oUlProductNode);
        oUlProductContentNode.append(oLiProductContentNode);
        if (callback) {
            callback();
        }
    }
    // 产品行业滚动动画
    function eventOfProductIndustryBtn () {
        // 获取列表jq节点
        var oListNode = $(oUlProductIndustry),
            // 计算单个选项宽度
            iProductInduItemWidth = parseInt(getStyle(aProductIndustryItem[0], 'width')),
            // 浮框宽度
            nListParentWidth = oListNode.parent().width(),
            // 列表的左距离
            listLeft = 0;
        // 列表宽度
        iProductInduTotalWidth = iProductInduItemWidth * aProductIndustryItem.length + iUlProductIndustryPaddingLeft;
        $(oUlProductIndustry).css("width", iProductInduTotalWidth + 'px');
        oProductIndustryRightBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (nListParentWidth + listLeft === oListNode.width()) {
                return;
            }
            if (listLeft + 2 * nListParentWidth < oListNode.width()){
                startMove(oUlProductIndustry, {left: -1 * (listLeft + nListParentWidth)});
            } else {
                startMove(oUlProductIndustry, {left: nListParentWidth - oListNode.width()});
            }
        });
        oProductIndustryLeftBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (listLeft === 0) {
                return;
            }
            if (listLeft > nListParentWidth) {
                startMove(oUlProductIndustry, {left: nListParentWidth - listLeft});
            } else {
                startMove(oUlProductIndustry, {left: 0});
            }
        });
    }
    // 产品行业点击事件
    function eventOfProductIndustryItem () {
        $(oUlProductIndustry).on("click", 'li', function (event) {
            var target = (event.target.tagName.toLowerCase() === 'li') ? $(event.target) : $(event.target).parents('li');
            iProductIndustryId = target.data("id");
            target.addClass("active").siblings().removeClass("active");
            getProductList(setProductListByClickIndustryItem, eventOfProduct);
        })
    }
    // 点击获取新的数据时（dom已存在）：设置热门产品数据
    function setProductListByClickIndustryItem (size, list, callback) {
        var aLiProductContent = $(".product-content .product-item").css("display", 'block');
        for (var i = 0; i < list.length; i++) {
            $(aLiProductContent[i]).find(".product-item-content").attr('href', '/f/' + list[i].id + '/case_detail.html?pc=true');
            $(aLiProductContent[i]).find(".product-img").attr('src', list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(',')[0]) : null);
            $(aLiProductContent[i]).find(".product-title").text(list[i].title);
            $(aLiProductContent[i]).find('.product-industry').text('行业：' + (list[i].application_industry ? JSON.parse(list[i].application_industry).title: '暂无数据'));
            $(aLiProductContent[i]).find('.product-money').text('￥ ' + (list[i].case_money ? list[i].case_money + '万元' : '面议'));
            $(aLiProductContent[i]).find('.product-dock-num').text(list[i].click_rate);
        }
        if (i <= size) {
            for (var j = 0; j < size - i + 1; j++) {
                $(aLiProductContent[i + j]).css("display", 'none');
            }
        }
        if (callback) {
            callback();
        }
    }
    // 鼠标移进移出
    function eventOfProduct () {
        // 左右箭头的提示
        /*oProductIndustryLeftBtn[0].onmouseenter = oProductIndustryRightBtn[0].onmouseenter = function () {
            layer.tips($(this).attr("title"), $(this) , {
                area: 'auto',
                maxWidth: '80',
                tips: [1, '#535353']
            });
        };
        oProductIndustryLeftBtn[0].mouseleave = oProductIndustryRightBtn[0].mouseleave = function () {
            layer.closeAll();
        };*/
        $(".product-model .product-item .product-title").mouseenter(function () {
            layer.tips($(this).text(), $(this) , {
                area: 'auto',
                maxWidth: '290',
                tips: [1, '#535353']
            });
        }).mouseleave(function () {
            layer.closeAll();
        });
    }

    /**
     * 龙头企业模块
     * author：GloryAce
     */
    // 龙头企业滚动动画
    function eventOfCompanyIndustryBtn () {
        // 获取列表jq节点
        var oListNode = $(oUlCompanyIndustry),
            // 计算单个选项宽度
            iCompanyInduItemWidth = parseInt(getStyle(aCompanyIndustryItem[0], 'width')),
            // 浮框宽度
            nListParentWidth = oListNode.parent().width(),
            // 列表的左距离
            listLeft = 0;
        // 列表宽度
        iCompanyInduTotalWidth = iCompanyInduItemWidth * aCompanyIndustryItem.length + iUlProductIndustryPaddingLeft;
        $(oUlCompanyIndustry).css("width", iCompanyInduTotalWidth + 'px');

        oCompanyIndustryRightBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (nListParentWidth + listLeft === oListNode.width()) {
                return;
            }
            if (listLeft + 2 * nListParentWidth < oListNode.width()){
                startMove(oUlCompanyIndustry, {left: -1 * (listLeft + nListParentWidth)});
            } else {
                startMove(oUlCompanyIndustry, {left: nListParentWidth - oListNode.width()});
            }
        });
        oCompanyIndustryLeftBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (listLeft === 0) {
                return;
            }
            if (listLeft > nListParentWidth) {
                startMove(oUlCompanyIndustry, {left: nListParentWidth - listLeft});
            } else {
                startMove(oUlCompanyIndustry, {left: 0});
            }
        });
    }
    // 龙头企业点击事件
    function eventOfCompanyIndustryItem () {
        $(oUlCompanyIndustry).on("click", 'li', function (event) {
            var target = (event.target.tagName.toLowerCase() === 'li') ? $(event.target) : $(event.target).parents('li');
            iCompanyIndustryId = target.data("id");
            target.addClass("active").siblings().removeClass("active");
            getCompanyList();
        })
    }
    // 获取龙头企业数据
    function getCompanyList (callback) {
        var json  = {
            industryId: iCompanyIndustryId,
            sortPointer:{
                filed:"recommended_index",
                order:"DESC"
            },
            pager: {
                current: 1,
                size: 6
            }
        };
        $.ajax({
            url: '/f/serviceProviders/query?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    if (list.length > 0) {
                        $('.company-content').show();
                        $('.company-content-no-data').hide();
                        setCompanyList(list, callback);
                    } else {
                        $('.company-content').hide();
                        $('.company-content-no-data').show();
                    }
                }
            },
            error: function (err) {
                console.error('龙头企业', err);
            }
        });
    }
    // 设置龙头企业数据
    function setCompanyList (list, callback) {
        var oLiCompanyContentNode = null,
            oLiCompanyNode = null,
            oCompanyLiNode = null,
            oNowNode = null;
        // 没有子节点
        if (oUlCompanyContentNode.children().length < 1) {
            oLiCompanyContentNode = $(sLiCompanyContentHtml);
            for (var i = 0; i < list.length; i++) {
                oLiCompanyNode = $(sLiCompanyHtml);
                oLiCompanyNode.find(".company-item-content").attr('href', '/f/' + list[i].id + '/provider_detail.html?pc=true');
                oLiCompanyNode.find(".company-img").attr('src', list[i].logo ? $(this).getAvatar(list[i].logo) : null);
                oLiCompanyNode.find(".company-title").text(list[i].name);
                oLiCompanyNode.find('.company-industry').text('高企领域：' + (list[i].industry_id ? JSON.parse(list[i].industry_id).title: '暂无数据'));
                oUlCompanyNode.append(oLiCompanyNode);
            }
            oLiCompanyContentNode.append(oUlCompanyNode);
            oUlCompanyContentNode.append(oLiCompanyContentNode);
        } else { // 已有子节点
            oCompanyLiNode = $('.company-bottom-right .company-item');
            oCompanyLiNode.each(function (index, item) {
                oNowNode = $(item);
                if (index > list.length - 1) {
                    oNowNode.hide();
                } else {
                    oNowNode.find(".company-item-content").attr('href', '/f/' + list[index].id + '/provider_detail.html?pc=true');
                    oNowNode.find(".company-img").attr('src', list[index].logo ? $(this).getAvatar(list[index].logo) : null);
                    oNowNode.find(".company-title").text(list[index].name);
                    oNowNode.find('.company-industry').text('高企领域：' + (list[index].industry_id ? JSON.parse(list[index].industry_id).title : '暂无数据'));                    oNowNode.show();
                    oNowNode.show();
                }
            })
        }
        if (callback) {
            callback();
        }
    }
    // 鼠标移进移出
    function eventOfCompany () {
        $(".company-model .company-item .company-title").mouseenter(function () {
            layer.tips($(this).text(), $(this) , {
                area: 'auto',
                maxWidth: '240',
                tips: [1, '#535353']
            });
        }).mouseleave(function () {
            layer.closeAll();
        });
    }

    /**
     * 数字展厅模块
     * author：GloryAce
     */
    // 数字展厅滚动动画
    function eventOfNumberIndustryBtn () {
        // 获取列表jq节点
        var oListNode = $(oUlNumberIndustry),
            // 计算单个选项宽度
            iNumberInduItemWidth = parseInt(getStyle(aNumberIndustryItem[0], 'width')),
            // 浮框宽度
            nListParentWidth = oListNode.parent().width(),
            // 列表的左距离
            listLeft = 0;
        // var iTargetLeft = iNumberInduItemWidth * 6;
        // 列表宽度
        iNumberInduTotalWidth = iNumberInduItemWidth * aNumberIndustryItem.length + iUlProductIndustryPaddingLeft;
        $(oUlNumberIndustry).css("width", iNumberInduTotalWidth + 'px');

        oNumberIndustryRightBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (nListParentWidth + listLeft === oListNode.width()) {
                return;
            }
            if (listLeft + 2 * nListParentWidth < oListNode.width()){
                startMove(oUlNumberIndustry, {left: -1 * (listLeft + nListParentWidth)});
            } else {
                startMove(oUlNumberIndustry, {left: nListParentWidth - oListNode.width()});
            }
        });
        oNumberIndustryLeftBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (listLeft === 0) {
                return;
            }
            if (listLeft > nListParentWidth) {
                startMove(oUlNumberIndustry, {left: nListParentWidth - listLeft});
            } else {
                startMove(oUlNumberIndustry, {left: 0});
            }
        });
    }
    // 数字展厅点击事件
    function eventOfNumberIndustryItem () {
        var target = null,
            oOldHallItemNode = null,
            index = null,
            isAnimating = false,
            stSave = null,
            animateFuc = function () {
                oOldHallItemNode = aHalls[sNowSelectId];
                oOldHallItemNode.animate({
                    left: '-100%'
                }, function () {
                    oOldHallItemNode.css({
                        left: '100%'
                    });
                    if (index < 1) {
                        index += 1;
                    } else {
                        isAnimating = false;
                    }
                });
                stSave = String(iNumberIndustryId);
                aHalls[stSave].css({
                    left: '100%'
                }).animate({
                    left: '0'
                }, function () {
                    sNowSelectId = stSave;
                    if (index < 1) {
                        index += 1;
                    } else {
                        isAnimating = false;
                    }
                });
            };
        $(oUlNumberIndustry).on("click", 'li', function (event) {
            target = (event.target.tagName.toLowerCase() === 'li') ? $(event.target) : $(event.target).parents('li');
            if (target.hasClass('active') || isAnimating) {
                return 0;
            }
            isAnimating = true;
            index = 0;
            target.addClass("active").siblings().removeClass("active");
            iNumberIndustryId = target.data("id");
            if ($.inArray(iNumberIndustryId, aHallIds) < 0) {
                aHallIds.push(iNumberIndustryId);
                getNumberList(animateFuc);
            } else {
                animateFuc();
            }
        })
    }
    // 获取数字展厅数据
    function getNumberList (callback) {
        var size = 6;
        var json  = {
            applicationIndustry: iNumberIndustryId,
            sortPointer:{
                filed:"recommended_index",
                order:"DESC"
            },
            pager: {
                current: 1,
                size: size
            }
        };
        if (!sNowSelectId) {
            sNowSelectId = String(iNumberIndustryId);
        }
        oNumberHallLoadingNode.show();
        $.ajax({
            url: '/f/digitalShowroom/query?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(json),
            success: function (res) {
                var oShowData = null,
                    sPictures = null,
                    sAttrName = null;
                if (res.status === 200) {
                    sAttrName = String(iNumberIndustryId);
                    // 初始化
                    if(numberList[sAttrName] === undefined) {
                        numberList[sAttrName] = {};
                    }
                    if (res.data.data_list && res.data.data_list.length > 0) {
                        oShowData = res.data.data_list[0];
                        sPictures = oShowData.picture_show;
                        numberList[sAttrName].id = oShowData.id;
                        numberList[sAttrName].typeId = iNumberIndustryId;
                        numberList[sAttrName].titleCN = oShowData.title;
                        numberList[sAttrName].titleEN = oShowData.english_title;
                        numberList[sAttrName].picArr = extractNumberHallPictureData(sPictures);
                        // 暂时使用静态数据，等需求明确后修改
                        if (iNumberIndustryId === 202151) {
                            setNumberList(DATA);
                        } else {
                            setNumberList(numberList[sAttrName]);
                        }
                    } else {
                        numberList[sAttrName].typeId = iNumberIndustryId;
                        setNumberList(numberList[sAttrName]);
                    }
                    oNumberHallLoadingNode.hide();
                    if (callback) {
                        callback();
                    }
                }
            },
            error: function (err) {
                console.error('产品', err);
            }
        });
    }
    // 提取数据
    function extractNumberHallPictureData(picData) {
        if (picData) {
            var list = JSON.parse(picData);
            var aResult = [];
            list.forEach(function (item) {
                var obj = {};
                obj.src = '/adjuncts/file_download/' + item.id;
                obj.title = item.title;
                obj.id = item.id;
                aResult.push(obj);
            });
            return aResult;
        } else {
            return [];
        }
    }

    // 设置数字展厅数据
    function setNumberList (oHallData) {
        var result = sLiHtml,
            stSave = '',
            aList = null,
            lengthLevel = null;
        if (oHallData.id && oHallData.picArr.length > 0) {
            aList = oHallData.picArr;
            lengthLevel = 1;
            $.each(aList, function (index, item) {
                if (index < 5) {
                    stSave += sImageDivHtml.replace(/class=""/, function (classStr) {
                        return (index < 2) ? classStr.slice(0, -1) + 'number-picture-big' + classStr.slice(-1) : classStr.slice(0, -1) + 'number-picture-small' + classStr.slice(-1)
                    }).replace(/src=""/, function (srcStr) {
                        return srcStr.slice(0, -1) + item.src + srcStr.slice(-1);
                    }).replace(/alt=""/, function (altStr) {
                        return altStr.slice(0, -1) + item.title + altStr.slice(-1);
                    })
                } else {
                    // 跳出
                    return false;
                }
            });
            lengthLevel = 6 - aList.length;
            if (lengthLevel > 0) {
                if (lengthLevel > 4) {
                    lengthLevel = 4;
                }
            } else {
                lengthLevel = 1;
            }
            stSave += hallEntranceHtml.replace(/<img[^>]*>/, function (imageStr) {
                if (aList.length > 5) {
                    return imageStr.replace(/style=""/, function () {
                        return '';
                    }).replace(/src=""/, function (srcStr) {
                        return srcStr.slice(0, -1) + aList[5].src + srcStr.slice(-1);
                    }).replace(/alt=""/, function (altStr) {
                        return altStr.slice(0, -1) + aList[5].title + altStr.slice(-1);
                    })
                } else {
                    return imageStr.replace(/style=""/, function () {
                        return 'style="display:none"'
                    })
                }
            }).replace(/class="[^"]*"/, function (classStr) {
                if (aList.length > 5) {
                    return classStr.slice(0, -1) + ' number-picture-small' + classStr.slice(-1);
                } else {
                    return classStr;
                }
            }).replace(/<p class="number-hall-entrance-title"><\/p>/, function (titleCN) {
                return titleCN.slice(0, -4) + oHallData.titleCN + titleCN.slice(-4);
            }).replace(/<p class="number-hall-entrance-en"><\/p>/, function (titleEN) {
                return titleEN.slice(0, -4) + oHallData.titleEN + titleEN.slice(-4);
            }).replace(/style=""/, function (styleStr) {
                if (aList.length > 5) {
                    return '';
                } else {
                    return styleStr.slice(0, -1) + 'width:' + (lengthLevel * 25) + '%' + styleStr.slice(-1);
                }
            }).replace(/href=""/, function (hrefStr) {
                return hrefStr.slice(0, -1) + '/f/number_hall.html?pc=true&industry=' + iNumberIndustryId + hrefStr.slice(-1);
                // return hrefStr.slice(0, -1) + '/f/' + oHallData.id + '/number_hall_detail.html?pc=true' + hrefStr.slice(-1);
            });
        } else {
            stSave = '暂无数据';
            result = result.replace(/class="[^"]+"/, function (classStr) {
                return classStr.slice(0, -1) + ' number-content-item-none' + classStr.slice(-1);
            })
        }
        // 获取处理后的字符串
        result = result.slice(0, -5) + stSave + result.slice(-5);
        // 字符串转节点
        stSave = $(result);
        // 存储节点
        aHalls[String(oHallData.typeId)] = stSave;
        // 插入节点
        oUlNumberContentNode.append(stSave);
    }

    // 设置数字展厅数据
    /*function setNumberList () {
        var oLiNumberContentNode = null,
            oLiNumberNode = null,
            oNumberLiNode = null,
            oNowNode = null;
        if (oUlNumberContentNode.children().length < 1) {
            oLiNumberContentNode = $(sLiNumberContentHtml);
            for (var i = 0; i < list.length; i++) {
                oLiNumberNode = $(sLiNumberHtml);
                if (list[i].id) {
                    oLiNumberNode.find(".number-item-content").attr('href', '/f/' + list[i].id + '/number_hall_detail.html?pc=true');
                }
                oLiNumberNode.find(".number-img").attr('src', list[i].cover);
                oLiNumberNode.find(".number-title").text(list[i].title);
                for (var j = 0; j < list[i].info.length; j++) {
                    var oPNumberInfoNode = $(sPNumberIndustryItemHtml).text(list[i].info[j]);
                    oLiNumberNode.find(".industry-area").append(oPNumberInfoNode);
                }
                iDivNumberIndustryHeight[i] = iPNumberIndustryHeight * list[i].info.length + iDivNumberIndustryPaddingV;
                oUlNumberNode.append(oLiNumberNode);
            }
            oLiNumberContentNode.append(oUlNumberNode);
            oUlNumberContentNode.append(oLiNumberContentNode);
        } else {
            oNumberLiNode = $('.number-bottom-right .number-item');
            oNumberLiNode.each(function (index, item) {
                oNowNode = $(item);
                if (index > list.length - 1) {
                    oNowNode.hide();
                } else {
                    oNowNode.find(".company-item-content").attr('href', '/f/' + list[index].id + '/provider_detail.html?pc=true');
                    oNowNode.find(".company-img").attr('src', list[index].logo ? $(this).getAvatar(JSON.parse(list[index].logo)[0].id) : null);
                    oNowNode.find(".company-title").text(list[index].name);
                    oNowNode.find('.company-industry').text('行业：' + (list[index].industry_id ? JSON.parse(list[index].industry_id).title: '暂无数据'));
                }
            })
        }

        if (callback) {
            callback();
        }
    }*/
    // 数字展厅的动画效果
    /*function moveOfNumber () {
        var aNumberItem = $(oUlNumberContentNode).find(".number-content-item").eq(0).find(".number-item");
        for (var i = 0; i < aNumberItem.length; i++) {
            (function (i) {
                var oInfo = $(aNumberItem[i]).find(".industry-area")[0];
                aNumberItem[i].onmouseover = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {borderWidth: 2, opacity: 100, borderColor: 'rgba(0, 102, 204, 0.94'});
                    startMove(oInfo, {width: 100, height: iDivNumberIndustryHeight[i], marginTop: -iDivNumberIndustryHeight[i] / 2 - 20, opacity: 100, fontSize: 14, marginLeft: -50});
                };
                aNumberItem[i].onmouseout = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {borderWidth: 10, opacity: 60, borderColor: 'rgba(220, 220, 220, 0.6)'});
                    startMove(oInfo, {width: 0, height: 0, opacity: 0, fontSize: 0, marginTop: -0, marginLeft: 0});
                };
            })(i)
        }
    }*/

    /**
     * 战略伙伴模块
     * author：GloryAce
     */
    // 是否需要兼容动画
    function isVersionOverNine() {
        var browser = window.BROWSERTYPE;
        var ieVersion = null;
        if(typeof browser === "boolean") {
            return browser;
        } else if (typeof browser === "string") {
            if (browser.indexOf('IE') > -1) {
                ieVersion = parseInt(browser.slice(2));
                return !(ieVersion < 10);
            } else {// 兼容动画
                return true;
            }
        }
    }
    // 战略伙伴数据请求
    function getPartnerArr(callback) {
        if (isHaveAllPartner || isPartnerRequest) {
            return 0
        }
        isPartnerRequest = true;
        partner.pager.current = partnerPageNum;
        partner.pager.size = partnerPageSize;
        $.ajax({
            url: '/f/partners/query',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(partner),
            success: function (res) {
                if (res.status === 200) {
                    isPartnerRequest = false;
                    partnerPageNum += 1;
                    if (res.data.data_list.length < partnerPageSize) {
                        isHaveAllPartner = true;
                    }
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                isPartnerRequest = false;
                console.error('战略伙伴', err);
            }
        })
    }
    // 初始化战略伙伴列表
    function createPartner(partner) {
        var partnerListHtml = '';
        var flagRule = /(href|src|alt|title)=('|")('|")/g;
        partner.forEach(function (item) {
            partnerListHtml += partnerItemHtml.replace(flagRule, function (flagStr) {
                if (flagStr.indexOf('src') > -1) {
                    return 'src="' + (partnerListNode.getAvatar(item.logo)) + '"';
                } else if (flagStr.indexOf('alt') > -1) {
                    return 'alt="' + (item.name) + '"';
                } else if (flagStr.indexOf('title') > -1) {
                    return 'title="' + (item.name) + '"';
                } else if (flagStr.indexOf('href') > -1) {
                    return 'href="' + (item.url) + '"';
                }
            })
        });
        partnerListHtml = partnerListNode.html() + partnerListHtml;
        if (partnerListHtml.length !== partnerListNode.html().length) {
            partnerListNode.html(partnerListHtml);
            if (partnerListNode.outerWidth() < partnerListNode.parent().width()) {
                isCanLeftMove = false;
                isCanRightMove = false
            }
        }
    }
    // 战略伙伴动画监听
    function eventOfPartnerAnimation() {
        partnerListNode.on('webkitAnimationEnd', function () {
            var oldLeft = parseInt(partnerListNode.css('left'));
            if (isLeftMove) {
                if (partnerListNode.hasClass('animate-partner-left-move')) {
                    partnerListNode.css('left', (oldLeft - partnerListNode.parent().width()) + 'px');
                } else {
                    partnerListNode.css({
                        left: "100%",
                        transform: 'translateX(-100%)'
                    })
                }
                if (!isCanRightMove) {
                    isCanRightMove = true;
                }
            } else if (isRightMove) {
                if (partnerListNode.hasClass('animate-partner-right-move')) {
                    partnerListNode.css('left', (oldLeft + partnerListNode.parent().width()) + 'px');
                } else {
                    partnerListNode.css({
                        left: "0",
                        transform: 'translateX(0)'
                    })
                }
                if (!isCanLeftMove) {
                    isCanLeftMove = true;
                }
            }
            if (!isHaveAllPartner) {
                getPartnerArr(function (partnerList) {
                    createPartner(partnerList);
                })
            }
            cleanAnimationClass(partnerListNode);
            // 记录移动结束
            isPartnerMoving = false;
        })
    }
    // 战略伙伴的点击监听
    function eventOfPartnerClick() {
        // 获取战略伙伴内容框
        var partnerDiv = $('.home-partner-content-div');
        // 获取获取浏览器版本情况
        var isOverNine = isVersionOverNine();
        // 事件委托
        partnerDiv.off().on('click', function (event) {
            var clickNode = $(event.target);
            if (clickNode.hasClass('home-partner-left-btn') || clickNode.parents('.home-partner-left-btn').length > 0) {
                // 移动中不能点击
                if (isPartnerMoving) {
                    return 0;
                }
                if (isOverNine) {
                    eventOfPartnerLeftBtnClick();
                } else {
                    eventOfPartnerLeftBtnClickNoAnimation()
                }
            } else if (clickNode.hasClass('home-partner-right-btn') || clickNode.parents('.home-partner-right-btn').length > 0) {
                if (isPartnerMoving) {
                    return 0;
                }
                if (isOverNine) {
                    eventOfPartnerRightBtnClick();
                } else {
                    eventOfPartnerRightBtnClickNoAnimation();
                }
            }
        })
    }
    // 战略伙伴左键事件（列表右移）
    function eventOfPartnerLeftBtnClick() {
        // 获取left
        var partnerListLeft = partnerListNode.css('left');
        // 获取列表承载框的宽度
        var listDivWidth = partnerListNode.parent().width();
        // 可以右移
        if (isCanRightMove) {
            // 记录现在是左移还是右移
            isRightMove = true;
            isLeftMove = false;
            // ps: 左边的条件判定 '列表已经左移到最左端', 右边的条件是判定列表左距离是否还大于承载框的宽度
            if (parseInt(partnerListLeft) === listDivWidth || !(parseInt(partnerListLeft) > -1 * listDivWidth)) {
                // 这里对‘最左端’的情况作了处理，不处理无法正常右移
                if (parseInt(partnerListLeft) === listDivWidth) {
                    partnerListNode.css({
                        left: 1050 - partnerListNode.outerWidth() + "px",
                        transform: 'translateX(0)'
                    })
                }
                partnerListNode.removeClass('animate-partner-left-move');
                partnerListNode.addClass('animate-partner-right-move');
            } else if (parseInt(partnerListLeft) < 0 && parseInt(partnerListLeft) > -1 * listDivWidth) {
                partnerListNode.addClass('animate-partner-right-move-finish');
                isCanRightMove = false;
            }
            isPartnerMoving = true;
        }
    }
    // 兼容动画：战略伙伴左键事件（列表右移）
    function eventOfPartnerLeftBtnClickNoAnimation() {
        // 获取列表的左距离
        var partnerListLeft = parseInt(partnerListNode.css('left'));
        // 获取列表承载框
        var listDiv = partnerListNode.parent();
        // 获取承载框的宽度
        var listDivWidth = listDiv.width();
        // 存储配置数据
        var configData = {
            left: 0
        };
        // 当left为0,不可以再右移
        if (partnerListLeft === 0 || !isCanRightMove) {
            return 0;
        }
        // 此数据只是为了记录列表的移动方向
        isRightMove = true;
        isLeftMove = false;
        // 当移动距离小于父框宽度
        if (Math.abs(partnerListLeft) < listDivWidth) {
            configData.left = 0;
        } else {// 移动距离大于父框距离
            configData.left = partnerListLeft + listDivWidth + 'px';
        }
        isPartnerMoving = true;
        // 绑定动画方法
        partnerListNode.animate(configData, 2000, function () {
            // 因为右移过一次，所以可以左移
            isCanLeftMove = true;
            // 若数据还未请求完
            if (!isHaveAllPartner) {
                getPartnerArr(function (partnerList) {
                    createPartner(partnerList);
                })
            }
            // 无法再右移
            if (parseInt(partnerListNode.css('left')) === 0) {
                isCanRightMove = false;
            } else {
                isCanRightMove = true;
            }
            // 结束移动状态
            isPartnerMoving = false;
        });
    }
    // 战略伙伴右键事件（列表左移）
    function eventOfPartnerRightBtnClick() {
        // 获取left
        var partnerListLeft = partnerListNode.css('left');
        // 获取列表承载框的宽度
        var listDivWidth = partnerListNode.parent().width();
        // 获取剩余能左移的距离
        var canLeftMoveLength = 0;
        // 是否还是左移
        if (isCanLeftMove) {
            // 记录现在是左移还是右移
            isLeftMove = true;
            isRightMove = false;
            canLeftMoveLength = partnerListNode.width() - (listDivWidth - parseInt(partnerListLeft));
            // 判定剩下能左移的距离是否大于承载框的宽度
            if (!(canLeftMoveLength < listDivWidth)) {
                partnerListNode.removeClass('animate-partner-right-move');
                partnerListNode.addClass('animate-partner-left-move');
                /*if (!isCanRightMove) {
                    isCanRightMove = true;
                }*/
            } else {
                partnerListNode.addClass('animate-partner-left-move-finish');
                isCanLeftMove = false;
            }
            isPartnerMoving = true;
        }
    }
    // 兼容动画：战略伙伴右键事件（列表左移）
    function eventOfPartnerRightBtnClickNoAnimation() {
        // 获取列表的left
        var partnerListLeft = parseInt(partnerListNode.css('left'));
        // 列表宽度
        var listWidth = partnerListNode.width();
        // 获取列表承载框
        var listDiv = partnerListNode.parent();
        // 获取承载框的宽度
        var listDivWidth = listDiv.width();
        // 存储配置数据
        var configData = {
            left: 0
        };
        // 若左距离 + 列表承载框的宽度 = 列表宽度 的时候不用执行以下代码
        if (listDivWidth - partnerListLeft === listWidth || !isCanLeftMove) {
            return 0;
        }
        // 此数据只是为了记录列表的移动方向
        isRightMove = false;
        isLeftMove = true;
        // 剩下可移动距离小于承载框的宽度
        if (listWidth - (listDivWidth - partnerListLeft) < listDivWidth) {
            configData = {
                left: listDivWidth - listWidth + 'px'
            };
        } else {
            configData = {
                left: partnerListLeft - listDivWidth + 'px'
            };
        }
        isPartnerMoving = true;
        // 绑定动画方法
        partnerListNode.animate(configData, 2000, function () {
            // 因为左移过一次，所以可以右移
            isCanRightMove = true;
            // 若数据还未请求完
            if (!isHaveAllPartner) {
                // 这里是异步请求
                getPartnerArr(function (partnerList) {
                    createPartner(partnerList);
                    // 无法再左移
                    if (partnerListNode.parent().width() - parseInt(partnerListNode.css('left'), 10) === partnerListNode.width()) {
                        isCanLeftMove = false;
                    } else {
                        isCanLeftMove = true;
                    }
                    // 结束移动状态
                    isPartnerMoving = false;
                })
            } else {
                // 无法再左移
                if (partnerListNode.parent().width() - parseInt(partnerListNode.css('left'), 10) === partnerListNode.width()) {
                    isCanLeftMove = false;
                } else {
                    isCanLeftMove = true;
                }
                // 结束移动状态
                isPartnerMoving = false;
            }
        });
    }
    // 清除战略伙伴的动画类名
    function cleanAnimationClass(node) {
        if (node.hasClass('animate-partner-left-move')) {
            node.removeClass('animate-partner-left-move');
        } else if (node.hasClass('animate-partner-right-move')) {
            node.removeClass('animate-partner-right-move');
        } else if (node.hasClass('animate-partner-left-move-finish')) {
            node.removeClass('animate-partner-left-move-finish');
        } else if (node.hasClass('animate-partner-right-move-finish')) {
            node.removeClass('animate-partner-right-move-finish');
        }
    }
});

