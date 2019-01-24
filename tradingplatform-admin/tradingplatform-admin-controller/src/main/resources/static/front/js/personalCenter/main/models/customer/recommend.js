function IntelligentRecommendation() {
    var shopList = [];
    // 记录layui的Index
    var layIndex = null;
    // 企业收藏列表
    var oCollectShopInfo = {};

    /* 分页组件 */
    // 总数
    var total = 0;
    // 每页数量
    var nPageSize = 5;
    var tablePage = new PluginPagination('recommend-page');
    var canLift = true;
    var nNowPageNumber = 1;
    var maxPageNumber = null;

    eventOfGoodTypeClick();
    eventOfArrowClick();
    eventOfFuncListClick();
    initPlusPage();

    /* 外部调用 */
    this.initModuleOfRecommend = function () {
        initRecommend();
    };

    // 数据初始化
    function initRecommend() {
        shopList = [];
        layIndex = null;
        oCollectShopInfo = {};
        nNowPageNumber = 1;
        canLift = true;
        getCollectShop(function () {
            getRecommendShopList();
        });
    }

    // loading
    function loading(bIsLoading) {
        if (bIsLoading === undefined) {
            bIsLoading = true;
        }
        // 显示加载
        if (bIsLoading) {
            if (!layIndex) {
                layIndex = layer.load(2, {
                    shade: [0.5, '#dcdcdc']
                });
            }
        } else {
            if (layIndex) {
                layer.close(layIndex);
                layIndex = null;
            }
        }
    }

    // 获取用户收藏的企业列表数据
    function getCollectShop(callback) {
        loading(true);
        new NewAjax({
            url: '/f/serviceProvidersCollection/pc/query_is_collection',
            contentType: 'application/json;charset=UTF-8',
            type: 'GET',
            success: function (res) {
                if (res.status === 200) {
                    extractCollectShopData(res.data.data_list);
                    if (callback) {
                        callback();
                    }
                }
            },
            error: function (err) {
                console.error('getCollectShop 错误：err：' + err);
            }
        })
    }

    // 提取收藏列表数据
    function extractCollectShopData(list) {
        list.forEach(function (item) {
            oCollectShopInfo[item.providers_id] = true;
        });
    }

    // 初始化分页
    function initPlusPage() {
        // 测试分页
        tablePage.openFirstEndModel(false)
            .openLift(true) // 打开跳转
            .setGetInputNodeCallback(function (inputNode) {  // 获取输入框
                var rule = /[^\d]/g;
                inputNode.bind('input propertychange', function () {
                    var pageNumber = inputNode.val().replace(rule, function () {
                        return ''
                    });
                    if (pageNumber.length > 0) {
                        pageNumber = parseInt(pageNumber);
                        if (pageNumber < 1) {
                            pageNumber = 1;
                        } else if (pageNumber > maxPageNumber) {
                            pageNumber = maxPageNumber;
                        }
                        nNowPageNumber = pageNumber;
                        canLift = true;
                    } else {
                        canLift = false;
                    }
                    inputNode.val(pageNumber);
                });
            })
            // 创建分页
            .createPage()
            // 点击回调
            .setClickCallback(function (node, oldPageNumber) {
                var _this = this;
                var labelType = node.get(0).tagName.toLowerCase();
                var newNumber = 0;
                if (labelType === 'li') {
                    // 页码按钮
                    if (node.data('status') !== undefined) {
                        newNumber = parseInt(node.data('mark'));
                        if (node.data('status') !== 'active' && newNumber !== oldPageNumber) {
                            nNowPageNumber = newNumber;
                            _this.setNowPageNumber(nNowPageNumber)
                                .createPage();
                            getRecommendShopList();
                        }
                    } else {// 我是上下页
                        if (node.data('mark') === 'prev') {
                            newNumber = oldPageNumber - 1;
                            if (newNumber > 0) {
                                nNowPageNumber = newNumber;
                                _this.setNowPageNumber(nNowPageNumber)
                                    .createPage();
                                getRecommendShopList();
                            }
                        } else if (node.data('mark') === 'next') {
                            newNumber = oldPageNumber + 1;
                            if (newNumber < maxPageNumber + 1) {
                                nNowPageNumber = newNumber;
                                _this.setNowPageNumber(nNowPageNumber)
                                    .createPage();
                                getRecommendShopList();
                            }
                        }
                    }
                } else if (labelType === 'button') {
                    // 按钮
                    if (canLift) {
                        _this.setNowPageNumber(nNowPageNumber)
                            .createPage();
                        getRecommendShopList();
                    }
                }
            });
    }

    // 获取推荐商户列表
    function getRecommendShopList() {
        var configData = {
            types: ["c_business_service_providers"],
            fields: [
                {
                    fields: [
                        "back_check_status_id"
                    ],
                    values: [
                        202050
                    ],
                    searchType: "stringQuery"
                },
                {
                    fields: [
                        "is_recommend"
                    ],
                    values: [
                        true
                    ],
                    searchType: "stringQuery"
                },
                {
                    fields: [
                        "industry_id_id"
                    ],
                    values: [],
                    searchType: "stringQuery"
                }
            ],
            page: nNowPageNumber,
            size: nPageSize,
            sort: "DESC",
            sortFields: [
                "recommended_index"
            ]
        };
        if (_config && _config.industry_id) {
            configData.fields[2].values.push(JSON.parse(_config.industry_id).id);
        }
        loading(true);
        new NewAjax({
            url: '/searchEngine/customSearch',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(configData),
            success: function (res) {
                var info = null;
                if (res.status === 200) {
                    info = res.data.data_object;
                    // 获取总数
                    total = info.totalRecord;
                    // 计算最大页数
                    maxPageNumber = Math.ceil(total / nPageSize);
                    // 判定是否显示分页列表
                    if (total > nPageSize) {
                        tablePage.showPage(true)
                            .setMaxPageNumber(maxPageNumber)
                            .setNowPageNumber(nNowPageNumber)
                            .createPage();
                    } else {
                        tablePage.showPage(false);
                    }
                    // 初始化企业数据列表
                    shopList = [];
                    // 提取列表数据
                    extractShopInfoList(info.data);
                    // 写入企业数据
                    createShopList();
                    loading(false);
                }
            },
            error: function (err) {
                loading(false);
                console.error('getRecommendShopList 错误：err：' + err);
            }
        })
    }

    // 提取推荐商户数据
    function extractShopInfoList(dataList) {
        dataList.forEach(function (item) {
            shopList.push({
                shop_id: item.data_id,
                shop_title: item.name,
                shop_url: '/f/' + item.data_id + '/provider_detail.html',
                shop_logo: (item.logo) ? '/adjuncts/file_download/' + JSON.parse(item.logo)[0].id : null,
                shop_email: (item.email) ? item.email : null,
                shop_phone: (item.phone) ? item.phone : null,
                shop_identity: (item.category_id) ? item.category_id : null
            })
        });
    }

    // 写入商户数据
    function createShopList() {
        // 获取企业列表
        var oShopListNode = $('.recommend .recommend-list').eq(0);
        // 企业节点
        var oShopNode = oShopListNode.children();
        // 获取子项节点字符串
        var sItemHtml = null;
        // 下标
        var index = 0;
        // html字符串
        var sHtml = '';
        // 若企业项少了则增加新的企业项
        if (oShopNode.length < shopList.length) {
            sItemHtml = oShopNode.eq(0).prop('outerHTML');
            for (; index < shopList.length; index++) {
                sHtml += sItemHtml
            }
            oShopListNode.html(sHtml);
            oShopNode = oShopListNode.children();
        }
        // 遍历企业项
        oShopNode.each(function (index, node) {
            var oNode = $(node);
            // 超过时隐藏并跳过
            if (index > shopList.length - 1) {
                oNode.hide();
                return true;
            } else {
                oNode.show();
                var shopItem = shopList[index];
                var stSave = null;
                var stSaveNode = null;
                shopItem.activeNode = oNode;
                oNode.find('a.company-title-link').text(shopItem.shop_title).attr({
                    href: shopItem.shop_url
                });
                // 身份验证
                stSaveNode = oNode.find('.company-certification-icon-item[type="identity"] .company-certification-icon').eq(0);
                // 有验证
                if (shopItem.shop_identity) {
                    stSave = shopItem.shop_identity;
                    if (!stSaveNode.parent().hasClass('active-identity')) {
                        stSaveNode.parent().addClass('active-identity');
                    }
                    if (stSave === 202135) { // 个人
                        stSaveNode.removeClass(function (index, className) {
                            return className.match(/icon-\S+/)[0];
                        }).addClass('icon-shimingrenzheng');
                    } else if (stSave === 202137) { // 企业
                        stSaveNode.removeClass(function (index, className) {
                            return className.match(/icon-\S+/)[0];
                        }).addClass('icon-qiyerenzheng');
                    }
                } else { // 没验证
                    if (stSaveNode.parent().hasClass('active-identity')) {
                        stSaveNode.parent().removeClass('active-identity');
                    }
                    stSaveNode.removeClass(function (index, className) {
                        return className.match(/icon-\S+/)[0];
                    }).addClass('icon-shimingrenzheng1');
                }
                // 手机验证
                stSaveNode = oNode.find('.company-certification-icon-item[type="phone"] .company-certification-icon').eq(0);
                if (shopItem.phone) {
                    stSaveNode.addClass('active-identity');
                } else {
                    stSaveNode.removeClass('active-identity');
                }
                // 邮箱验证
                stSaveNode = oNode.find('.company-certification-icon-item[type="email"] .company-certification-icon').eq(0);
                if (shopItem.email) {
                    stSaveNode.addClass('active-identity');
                } else {
                    stSaveNode.removeClass('active-identity');
                }
                // 获取企业logo
                stSaveNode = oNode.find('.recommend-company-logo').eq(0);
                stSaveNode.attr({
                    src: shopItem.shop_logo,
                    alt: shopItem.shop_title
                });
                // 获取功能列表区
                stSaveNode = oNode.find('.recommend-func-icon-list').eq(0);
                stSaveNode.attr({
                    dataIndex: index
                });
                // 获取收藏功能项
                stSaveNode = oNode.find('.recommend-func-icon-item[type="collection"]').eq(0);
                if (oCollectShopInfo[shopItem.shop_id] && !stSaveNode.hasClass('active-func')) {
                    stSaveNode.addClass('active-func');
                } else {
                    stSaveNode.removeClass('active-func');
                }
                // 获取产品类型列表
                stSaveNode = oNode.find('.recommend-type-list').eq(0);
                stSaveNode.attr({
                    dataIndex: index
                });
                // 获取产品列表
                getNewGoodsList(shopItem, function (data) {
                    var goodList = extractGoodInfoList(data.data_list);
                    createGoodList(goodList, oNode);
                })
            }
        })
    }

    // 请求最新产品列表数据
    function getNewGoodsList(shopInfo, callback) {
        var config = {
            providerId: shopInfo.shop_id,
            pager: {
                "current": 1,
                "size": 5
            }
        };
        new NewAjax({
            url: '/f/matureCase/get_lasted_mature_case',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(config),
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data);
                    }
                }
            },
            error: function (err) {
                console.error('getNewGoodsList 错误：err：' + err);
            }
        })
    }

    // 请求热门产品列表数据
    function getPopularGoodsList(shopInfo, callback) {
        var config = {
            providerId: shopInfo.shop_id,
            pager: {
                "current": 1,
                "size": 5
            }
        };
        new NewAjax({
            url: '/f/matureCase/get_recommend_mature_case',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(config),
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data);
                    }
                }
            },
            error: function (err) {
                console.error('getNewGoodsList 错误：err：' + err);
            }
        })
    }

    // 提取产品列表
    function extractGoodInfoList(data) {
        var result = [];
        data.forEach(function (item) {
            result.push({
                good_title: item.title,
                good_picture: '/adjuncts/file_download/' + item.picture_cover,
                good_url: '/f/' + item.id + '/case_detail.html'
            })
        });
        return result;
    }

    // 写入商品列表
    function createGoodList(list, node) {
        // html字符串
        var sHtml = '';
        // 获取产品列表
        var oGoodListNode = node.find('.recommend-result-list').eq(0);
        // 获取产品项
        var oGoodNode = oGoodListNode.children();
        // 产品项总宽度
        var totalWidth = 0;
        // 暂时存储
        var stSaveNode = oGoodListNode.parents('.recommend-result-list-div').eq(0).find('.arrow-div');
        // 初始还原列表位置
        oGoodListNode.css({
            left: 0
        });
        if (list.length > 0) {
            oGoodListNode.show().next().hide();
            // 若产品项不足
            if (oGoodNode.length < list.length) {
                // 获取产品HTML
                var sGoodHtml = oGoodNode.eq(0).prop('outerHTML');
                for (var index = 0; index < list.length; index++) {
                    sHtml += sGoodHtml;
                }
                oGoodListNode.html(sHtml);
                oGoodNode = oGoodListNode.children();
            }
            totalWidth = (oGoodNode.eq(0).width() + 34) * list.length;
            oGoodListNode.css({
                width: totalWidth + 'px'
            })
            // 当列表长度大于外框时
            if (oGoodListNode.width() > oGoodListNode.parent().width()) {
                stSaveNode.each(function (index, node) {
                    if ($(node).attr('type') === 'right') {
                        $(node).removeClass('arrow-disable').attr({
                            disable: 'false'
                        });
                    }
                });
            } else {
                // 禁用左右标签
                stSaveNode.each(function (index, node) {
                    $(node).addClass('arrow-disable').attr({
                        disable: 'true'
                    });
                });
            }
            oGoodNode.each(function (index, node) {
                var oNowNode = $(node);
                var goodInfo = list[index];
                if (index > list.length - 1) {
                    oNowNode.hide();
                    return true;
                } else {
                    oNowNode.show();
                    // 写入链接
                    stSaveNode = oNowNode.find('a.recommend-result-item-link').eq(0);
                    stSaveNode.attr({
                        href: goodInfo.good_url
                    });
                    // 写入图片
                    stSaveNode = oNowNode.find('img.recommend-result-item-image').eq(0);
                    stSaveNode.attr({
                        src: goodInfo.good_picture,
                        alt: goodInfo.good_title
                    });
                    stSaveNode = oNowNode.find('p.recommend-result-item-title').eq(0);
                    stSaveNode.text(goodInfo.good_title);
                }
            })
        } else { // 没有数据
            oGoodListNode.hide().next().show();
            // 禁用左右标签
            stSaveNode.each(function (index, node) {
                $(node).addClass('arrow-disable').attr({
                    disable: 'true'
                });
            })
        }
    }

    // event:功能列表点击事件
    function eventOfFuncListClick() {
        var oShopListNode = $('.recommend ul.recommend-list').eq(0);
        oShopListNode.on('click', 'li.recommend-func-icon-item', function () {
            // 获取当前功能节点
            var oNowFuncNode = $(this);
            // 是否启用功能
            var bIsEnable = (oNowFuncNode.attr('enable') === 'true');
            // 不启用
            if (!bIsEnable) {
                layer.closeAll();
                layer.msg('功能未启用');
                return 0;
            }
            // 获取功能类型
            var sNowFuncType = oNowFuncNode.attr('type');
            // 获取数据下标
            var sDataIndex = oNowFuncNode.parent().attr('dataIndex');
            // 获取数据项
            var oShopItem = shopList[sDataIndex];
            if (sNowFuncType === 'collection') {
                // 取消收藏
                if (oNowFuncNode.hasClass('active-func')) {
                    handleOfCollectCompany(oShopItem, false, function () {
                        layer.closeAll();
                        layer.msg('取消收藏成功');
                        oNowFuncNode.removeClass('active-func');
                        delete oCollectShopInfo[oShopItem.shop_id];
                    })
                } else { // 收藏
                    handleOfCollectCompany(oShopItem, true, function () {
                        layer.closeAll();
                        layer.msg('收藏成功');
                        oNowFuncNode.addClass('active-func');
                        oCollectShopInfo[oShopItem.shop_id] = true;
                    })
                }
            } else {
                return 0;
            }
        })
    }

    // 企业收藏
    function handleOfCollectCompany(shopData, bIsCollect, callback) {
        if (bIsCollect === undefined) {
            bIsCollect = true;
        }
        // 获取企业id
        var companyId = shopData.shop_id;
        var config = {
            serviceProvidersId: companyId,
            isCollection: bIsCollect
        };
        new NewAjax({
            url: '/f/serviceProviders/pc/collection_service_providers',
            contentType: 'application/json;charset=UTF-8',
            type: 'POST',
            data: JSON.stringify(config),
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data);
                    }
                }
            },
            error: function (err) {
                console.error('handleOfCollectCompany 错误：err：' + err);
            }
        })
    }

    // event:商品类型点击事件
    function eventOfGoodTypeClick() {
        var oShopListNode = $('.recommend ul.recommend-list').eq(0);
        oShopListNode.on('click', 'li.recommend-type', function () {
            // 获取当前点击节点
            var oNowTypeNode = $(this);
            // 已选中是不能点击
            if (oNowTypeNode.hasClass('active-type')) {
                return 0;
            }
            // 当前类型
            var sNowType = oNowTypeNode.attr('type');
            // 获取数据项
            var oShopInfoItem = shopList[oNowTypeNode.parent().attr('dataIndex')];
            // 获取商品列表
            var oGoodAreaNode = oNowTypeNode.parent().next();
            // 获取
            oNowTypeNode.siblings().each(function (index, node) {
                var oNowNode = $(node);
                if (oNowNode.hasClass('active-type')) {
                    oNowNode.removeClass('active-type');
                    return false;
                }
            });
            oNowTypeNode.addClass('active-type');
            if (sNowType === 'new') {
                // 获取产品列表
                getNewGoodsList(oShopInfoItem, function (data) {
                    var goodList = extractGoodInfoList(data.data_list);
                    createGoodList(goodList, oGoodAreaNode);
                })
            } else if (sNowType === 'hot') {
                // 获取产品列表
                getPopularGoodsList(oShopInfoItem, function (data) {
                    var goodList = extractGoodInfoList(data.data_list);
                    createGoodList(goodList, oGoodAreaNode);
                })
            }
        });
    }

    // event:左右点击事件
    function eventOfArrowClick() {
        var oShopListNode = $('.recommend ul.recommend-list').eq(0);
        oShopListNode.on('click', 'div.arrow-div', function () {
            // 获取当前点击节点
            var oNowArrowNode = $(this);
            // 当前是否允许点击
            var bCanClick = (oNowArrowNode.attr('disable') === 'false');
            // 不允许点击
            if (!bCanClick) {
                return 0;
            }
            // 点击节点的类型
            var sNowArrowType = oNowArrowNode.attr('type');
            // 获取商品列表
            var oGoodListNode = oNowArrowNode.parent().find('ul.recommend-result-list').eq(0);
            // 获取当前左距离
            var nNowGoodListLeft = parseInt(oGoodListNode.css('left')) || 0;
            // 移动距离
            var moveLength = 0;
            // listParent的宽度
            var nListParentWidth = oGoodListNode.parent().width();
            // 获取第二箭头
            var oOtherArrowNode = null;
            // 进行右移
            if (sNowArrowType === 'left') {
                oOtherArrowNode = oNowArrowNode.nextAll('.arrow-div').eq(0);
                if (nNowGoodListLeft < 0) {
                    if (Math.abs(nNowGoodListLeft) > nListParentWidth) {
                        moveLength = nNowGoodListLeft + nListParentWidth;
                        handlerOfNodeMove(oGoodListNode, moveLength, function () {
                            if (Math.abs(nNowGoodListLeft) + nListParentWidth === oGoodListNode.width()) {
                                oOtherArrowNode.removeClass('arrow-disable').attr({
                                    disable: 'false'
                                })
                            }
                        });
                    } else {
                        moveLength = 0;
                        handlerOfNodeMove(oGoodListNode, moveLength, function () {
                            if (Math.abs(nNowGoodListLeft) + nListParentWidth === oGoodListNode.width()) {
                                oOtherArrowNode.removeClass('arrow-disable').attr({
                                    disable: 'false'
                                })
                            }
                            oNowArrowNode.addClass('arrow-disable').attr({
                                disable: 'true'
                            });
                        });
                    }
                }
            } else { // 进行左移
                oOtherArrowNode = oNowArrowNode.prevAll('.arrow-div').eq(0);
                if (Math.abs(nNowGoodListLeft) + 2 * nListParentWidth < oGoodListNode.width()) {
                    moveLength = nNowGoodListLeft - nListParentWidth;
                    handlerOfNodeMove(oGoodListNode, moveLength, function () {
                        if (nNowGoodListLeft === 0) {
                            oOtherArrowNode.removeClass('arrow-disable').attr({
                                disable: 'false'
                            })
                        }
                    });
                } else {
                    moveLength = nListParentWidth - oGoodListNode.width();
                    handlerOfNodeMove(oGoodListNode, moveLength, function () {
                        if (nNowGoodListLeft === 0) {
                            oOtherArrowNode.removeClass('arrow-disable').attr({
                                disable: 'false'
                            })
                        }
                        oNowArrowNode.addClass('arrow-disable').attr({
                            disable: 'true'
                        });
                    });
                }
            }
        });
    }

    // 节点移动
    function handlerOfNodeMove(node, length, callback) {
        node.animate({
            left: length
        }, 1000, function () {
            if (callback) {
                callback();
            }
        });
    }

}