/**
 * Created by 空 on 2018/11/15.
 */
$(function () {
    /*=== 行业模块列表 ===*/
    // 展厅的liHtml （无内容使用）
    var sHallLiVoidHtml = '<li class="number-hall-industries-page-area"></li>';
    // 展厅的liHtml (有内容使用)
    var sHallLiAHtml = '<li class="number-hall-industries-item-area">\n' +
        '                                <a class="number-hall-industries-link" href="" target="_blank">\n' +
        '                                    <img class="number-hall-industries-image" src="" alt="">\n' +
        '                                    <p class="number-hall-industries-title"><span class="content"></span></p>\n' +
        '                                    <p class="number-hall-industries-contact"><span class="content">联系电话：</span></p>\n' +
        '                                    <p class="number-hall-industries-address"><span class="content">地址：</span></p>\n' +
        '                                </a>\n' +
        '                            </li>';
    var noDataHtml = '<p class="content-none">暂无数据</p>';
    // 存储请求列表数据
    var oHallListData = {};
    // 请求到第几页
    var nPageIndex = 1;
    // 请求每页的数量
    var nPageSize = 4;
    var oIndustryListNode = $(".number-hall-industries .type-body .type-area").eq(0);
    var aNumberIndustryItem = oIndustryListNode.find(".type-item");
    var sNowHallTypeId = aNumberIndustryItem.eq(0).data("id");
    var oNumberIndustryRightBtn = $(".number-hall-industries .type-body .right-btn");
    var oNumberIndustryLeftBtn = $(".number-hall-industries .type-body .left-btn");
    var iNumberInduTotalWidth = 0;

    var oHallListNode = $('.number-hall-industries-list').eq(0);
    var oHallListMarkNode = oHallListNode.find('.number-hall-industries-mark').eq(0);
    var oHallListLeftBtnNode = $('.number-hall-industries-content .icon-left-arrow').eq(0);
    var oHallListRightBtnNode = $('.number-hall-industries-content .icon-arrow-right').eq(0);
    var oIsRequestAll = {};
    // 获取移入作用节点
    var oMouseOverNode = null;
    // layer记录
    var nowLayerSave = null;


    /**
     * 函数调用
     * author：GloryAce
     */
    // setNumberList(numberList, moveOfIndustry);
    // getCompanyList(moveOfEnterprise);
    setTextOverTip();
    // 行业模块初始化
    initIndustryModel();


    /**
     * 数字展厅模块
     * author：GloryAce
     */
    /*function setNumberList(list, callback) {
        for (var i = 0; i < aIndustryItem.length; i++) {
            var oIndustryContentNode = $(sIndustryContentHtml);
            if (list[i].id) {
                $(aIndustryItem[i]).attr('href', '/f/' + list[i].id + '/number_hall_detail.html?pc=true');
            }
            $(aIndustryItem[i]).find(".industry-card-img").attr('src', list[i].cover);
            $(aIndustryItem[i]).find(".industry-card-text").text(list[i].title);
            for (var j = 0; j < list[i].info.length; j++) {
                var oPNumberInfoNode = $(sPNumberIndustryItemHtml).text(list[i].info[j]);
                oIndustryContentNode.append(oPNumberInfoNode);
            }
            iDivNumberIndustryHeight[i] = iPNumberIndustryHeight * list[i].info.length + iDivNumberIndustryPaddingV;
            $(aIndustryItem[i]).append(oIndustryContentNode);
        }
        // 数字展厅的动画效果
        if (callback) {
            callback();
        }
    }*/


    /**
     * 龙头企业模块
     * author：GloryAce
     */
    // 获取龙头企业数据
    /*function getCompanyList(callback) {
        var json = {
            pager: {
                current: 1,
                size: 8
            }
        };
        $.ajax({
            url: '/f/serviceProviders/get_recommend_providers?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    setCompanyList(list, callback);
                }
            },
            error: function (err) {
                console.error('龙头企业', err);
            }
        });
    }*/

    // 设置龙头企业数据
    /*function setCompanyList(list, callback) {
        for (var i = 0; i < aEnterpriseItem.length; i++) {
            $(aEnterpriseItem[i]).attr('href', '/f/' + list[i].id + '/provider_detail.html?pc=true');
            $(aEnterpriseItem[i]).find(".enterprise-logo").attr('src', list[i].logo ? $(this).getAvatar(JSON.parse(list[i].logo)[0].id) : null);
            $(aEnterpriseItem[i]).find(".enterprise-title").text(list[i].name);
            $(aEnterpriseItem[i]).find('.field').text(list[i].industry_id ? JSON.parse(list[i].industry_id).title : '暂无数据');
        }
        // 龙头企业动画
        callback();
    }*/

    /**
     * 动画函数定义
     * author：GloryAce
     */
    // 根据父级和子类名获取节点
    /*function getByClass(oParent, sClass) {
        var aEle = oParent.getElementsByTagName('*');
        var aResult = [];
        for (var i = 0; i < aEle.length; i++) {
            var aClass = aEle[i].className.split(" ");
            for (var j = 0; j < aClass.length; j++) {
                if (aClass[j] === sClass) {
                    aResult.push(aEle[i]);
                    break;
                }
            }
        }
        return aResult;
    }*/

    // 数字展厅的动画效果
    /*function moveOfIndustry() {
        for (var i = 0; i < aIndustryItem.length; i++) {
            (function (i) {
                var oInfo = getByClass(aIndustryItem[i], 'industry-card-inline-block')[0];
                aIndustryItem[i].onmouseover = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {borderWidth: 2, opacity: 100, borderColor: 'rgba(0, 102, 204, 0.94'});
                    startMove(oInfo, {
                        width: 150,
                        height: iDivNumberIndustryHeight[i],
                        marginTop: -iDivNumberIndustryHeight[i] / 2 - 40,
                        opacity: 100,
                        fontSize: 18,
                        marginLeft: -75
                    });
                };
                aIndustryItem[i].onmouseout = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {borderWidth: 10, opacity: 60, borderColor: 'rgba(220, 220, 220, 0.6)'});
                    startMove(oInfo, {width: 0, height: 0, opacity: 0, fontSize: 0, marginLeft: 0});
                };
            })(i)
        }
    }*/

    // 龙头企业的动画效果
    /*function moveOfEnterprise() {
        for (var i = 0; i < aEnterpriseItem.length; i++) {
            (function (i) {
                aEnterpriseItem[i].onmouseover = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {opacity: 100});
                };
                aEnterpriseItem[i].onmouseout = function () {
                    var aImg = this.getElementsByTagName("img")[0];
                    startMove(aImg, {opacity: 60});
                };
            })(i)
        }
    }*/

    // 设置文本超出提示
    function setTextOverTip() {
        setTextOverTipOfCompany();
    }

    // 设置龙头企业文本超出事件
    function setTextOverTipOfCompany() {
        var nCompany = $('.number-hall-industries-list').eq(0);
        nCompany.mouseover(eventOfCompanyTextOver);
    }

    // 龙头企业文本超出事件
    function eventOfCompanyTextOver(event) {
        if (event.target.tagName.toLowerCase() === 'span' && event.target.parentNode.className.indexOf('number-hall-industries-') > -1) {
            oMouseOverNode = $(event.target);
            if (nowLayerSave) {
                layer.close(nowLayerSave);
                nowLayerSave = null;
            }
            nowLayerSave = layer.tips(oMouseOverNode.text(), oMouseOverNode, {
                tips: [1, '#000000']
            });
        } else if (nowLayerSave) {
            layer.close(nowLayerSave);
            nowLayerSave = null;
        }
    }

    /*=== 行业模块列表 ===*/
    // 数据初始化
    function initIndustryModel() {
        // 绑定行业模块的所有事件
        bindAllEventOfIndustry();
        // 判定展示箭头按钮
        decideNavArrowBtnShow();
        // 获取初始数据
        getInitialData();
    }
    
    // 判定导航左右按钮的展示
    function decideNavArrowBtnShow() {
        if (!(oIndustryListNode.width() > oIndustryListNode.parent().width())) {
            oNumberIndustryRightBtn.hide();
        } else {
            oNumberIndustryRightBtn.show();
        }
    }
    
    // 数据初始化
    function getInitialData() {
        // 行业有数据
        if (industry.length > 0) {
            sNowHallTypeId = industry[0].id;
            if ($.getUrlParam("industry")) {
                sNowHallTypeId = $.getUrlParam("industry");
            }
            // 标注初始选中项
            aNumberIndustryItem.each(function (index, itemNode) {
                var oNowNode = $(itemNode),
                    lengthNumber = 0,
                    itemNodeLeft = 0,
                    listParentWidth = 0;
                if (String(oNowNode.data('id')) === String(sNowHallTypeId)){
                    oNowNode.addClass('active');
                    itemNodeLeft = oNowNode.get(0).offsetLeft;
                    listParentWidth = oIndustryListNode.parent().width();
                    if (itemNodeLeft > listParentWidth) {
                        lengthNumber = Math.ceil(oNowNode.get(0).offsetLeft);
                        if (listParentWidth * lengthNumber < oIndustryListNode.width()) {
                            oIndustryListNode.css({
                                left: -1 * listParentWidth * (lengthNumber - 1) + 'px'
                            })
                        } else {
                            oIndustryListNode.css({
                                left: listParentWidth - oIndustryListNode.width() + 'px'
                            })
                        }
                    }
                    return false;
                }
            });
            getHallList(function () {
                // 有数据
                if (oHallListData[sNowHallTypeId]) {
                    var stSave = oHallListData[sNowHallTypeId].slice(0);
                    // 清空内容
                    oHallListNode.html('');
                    writeHallList(stSave);
                    if (!(oIsRequestAll[sNowHallTypeId] && (oHallListNode.width() === oHallListNode.parent().width()))) {
                        oHallListRightBtnNode.show();
                    }
                } else { // 无数据
                    oHallListLeftBtnNode.hide();
                    oHallListRightBtnNode.hide();
                    oHallListNode.css({
                        width: 0,
                        left: 0
                    }).html(sHallLiVoidHtml.slice(0,-5) + noDataHtml + sHallLiVoidHtml.slice(-5));
                }
            })
        } else { // 无数据
            oHallListLeftBtnNode.hide();
            oHallListRightBtnNode.hide();
            oHallListNode.css({
                width: 0,
                left: 0
            }).html(sHallLiVoidHtml.slice(0,-5) + noDataHtml + sHallLiVoidHtml.slice(-5));
        }
    }
    
    // 行业事件
    function bindAllEventOfIndustry() {
        eventOfNumberIndustryBtn();
        eventOfNumberIndustryItem();
        hallListBtnClick();
    }
    
    // 产品行业滚动动画
    function eventOfNumberIndustryBtn () {
        // 获取列表jq节点
        var oListNode = oIndustryListNode,
            // 计算单个选项宽度
            iNumberInduItemWidth = parseInt(getStyle(aNumberIndustryItem[0], 'width')),
            // 浮框宽度
            nListParentWidth = oListNode.parent().width(),
            // 列表的左距离
            listLeft = 0;
        // 列表宽度
        iNumberInduTotalWidth = iNumberInduItemWidth * aNumberIndustryItem.length + 50;
        oIndustryListNode.css("width", iNumberInduTotalWidth + 'px');

        oNumberIndustryRightBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (nListParentWidth + listLeft === oListNode.width()) {
                return;
            }
            if (listLeft + 2 * nListParentWidth < oListNode.width()){
                startMove(oIndustryListNode.get(0), {left: -1 * (listLeft + nListParentWidth)});
            } else {
                startMove(oIndustryListNode.get(0), {left: nListParentWidth - oListNode.width()});
            }
        });
        oNumberIndustryLeftBtn.on("click", function () {
            listLeft = Math.abs(parseInt(oListNode.css('left')));
            if (listLeft === 0) {
                return;
            }
            if (listLeft > nListParentWidth) {
                startMove(oIndustryListNode.get(0), {left: listLeft - nListParentWidth});
            } else {
                startMove(oIndustryListNode.get(0), {left: 0});
            }
        });
    }
    
    // 产品行业点击事件
    function eventOfNumberIndustryItem () {
        var target = null,
            index = null,
            isAnimating = false,
            stSave = null,
            listLength = null,
            animateFuc = function () {
                if (oHallListData[sNowHallTypeId]) { // 数据存在
                    listLength = oHallListData[sNowHallTypeId].length;
                    stSave = oHallListData[sNowHallTypeId].slice(0, (listLength > nPageSize) ? nPageSize : listLength);
                    writeHallList(stSave);
                    if (!(oIsRequestAll[sNowHallTypeId] && (oHallListNode.width() === oHallListNode.parent().width()))) {
                        oHallListRightBtnNode.show();
                    }
                } else { // 数据不存在
                    oHallListLeftBtnNode.hide();
                    oHallListRightBtnNode.hide();
                    oHallListNode.css({
                        width: 0,
                        left: 0
                    }).html(sHallLiVoidHtml.slice(0, -5) + noDataHtml + sHallLiVoidHtml.slice(-5));
                }
                isAnimating = false;
            };
        oIndustryListNode.on("click", 'li', function (event) {
            target = (event.target.tagName.toLowerCase() === 'li') ? $(event.target) : $(event.target).parents('li');
            if (target.hasClass('active') || isAnimating) {
                return 0;
            }
            isAnimating = true;
            index = 0;
            target.addClass("active").siblings().removeClass("active");
            sNowHallTypeId = target.data("id");
            oHallListMarkNode.show();
            // 清空内容
            oHallListNode.html('');
            if (!oIsRequestAll[sNowHallTypeId]) { // 还没全请求完
                if (oHallListData[sNowHallTypeId] === undefined) { // 没有数据
                    // 请求页码重置为1
                    nPageIndex = 1;
                    getHallList(animateFuc);
                } else {
                    // 计算当前页码
                    nPageIndex = Math.ceil(oHallListData[sNowHallTypeId].length / nPageSize);
                    animateFuc();
                }
            } else { // 已经请求完
                animateFuc();
            }
        });
    }

    // 根据行业模块id获取展厅列表
    function getHallList(callback) {
        if (oIsRequestAll[sNowHallTypeId]) {
            return 0;
        }
        // 参数数据
        var configData = {
            applicationIndustry: sNowHallTypeId,
            pager: {//分页信息
                current: nPageIndex,   //当前页数
                size: nPageSize  //每页条数，只能获取一条数据
            }
        };
        $.ajax({
            url: '/f/digitalShowroom/query?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(configData),
            success: function (res) {
                var oShowData = null,
                    stSave = null;
                if (res.status === 200) {
                    oShowData = res.data.data_list;
                    if (oShowData && oShowData.length > 0) {
                        if (oHallListData[sNowHallTypeId] === undefined) {
                            oHallListData[sNowHallTypeId] = []
                        }
                        if (oShowData.length < nPageSize) {
                            oIsRequestAll[sNowHallTypeId] = true;
                        }
                        oShowData.forEach(function (item) {
                            var obj = {};
                            obj.id = item.id;
                            obj.title = item.title;
                            obj.contact = item.phone_num;
                            obj.address = item.address;
                            obj.picture = [];
                            if (item.picture_show) {
                                stSave = JSON.parse(item.picture_show);
                                stSave.forEach(function (item) {
                                    var oSave = {};
                                    oSave.src = '/adjuncts/file_download/' + item.id;
                                    oSave.title = item.title;
                                    oSave.id = item.id;
                                    obj.picture.push(oSave);
                                })
                            }
                            oHallListData[sNowHallTypeId].push(obj);
                        });
                    } else {
                        if (oHallListData[sNowHallTypeId] === undefined) {
                            oHallListData[sNowHallTypeId] = null;
                        }
                        oIsRequestAll[sNowHallTypeId] = true;
                    }
                    if (callback) {
                        callback();
                    }
                }
                oHallListMarkNode.hide();
            },
            error: function (err) {
                console.error('行业展厅', err);
                oHallListMarkNode.hide();
            }
        });
    }

    // 写入展厅列表
    function writeHallList(listData) {
        // result = '',
        var stSave = '',
            listWidth = oHallListNode.width(),
            parentWidth = oHallListNode.parent().width();
        listData.forEach(function (item) {
            stSave += sHallLiAHtml.replace(/href=""/, function (hrefStr) {
                return hrefStr.slice(0, -1) + '/f/' + item.id + '/number_hall_detail.html?pc=true' + hrefStr.slice(-1);
            }).replace(/src=""/, function (srcStr) {
                if (item.picture.length) {
                    return srcStr.slice(0, -1) + item.picture[0].src + srcStr.slice(-1);
                } else {
                    return srcStr;
                }
            }).replace(/alt=""/, function (altStr) {
                return altStr.slice(0, -1) + item.title + altStr.slice(-1);
            }).replace(/<p class="number-hall-industries-title"((?!<\/p>).*)<\/p>/, function (titleStr) {
                return titleStr.replace(/<span((?!<\/span>).*)<\/span>/, function (spanStr) {
                    return spanStr.slice(0, -7) + item.title + spanStr.slice(-7);
                });
            }).replace(/<p class="number-hall-industries-contact"((?!<\/p>).*)<\/p>/, function (contactStr) {
                return contactStr.replace(/<span((?!<\/span>).*)<\/span>/, function (spanStr) {
                    return spanStr.slice(0, -7) + (item.contact || '暂无数据') + spanStr.slice(-7);
                });
            }).replace(/<p class="number-hall-industries-address"((?!<\/p>).*)<\/p>/, function (addressStr) {
                return addressStr.replace(/<span((?!<\/span>).*)<\/span>/, function (spanStr) {
                    return spanStr.slice(0, -7) + (item.address || '暂无数据') + spanStr.slice(-7);
                });
            }).replace(/>\s+<(\/)?/g, function (str) {
                // 去除多余的空格换行
                return (str.indexOf('/') > -1) ? '></' : '><';
            });
            /*if (((index + 1) % 2 === 0 && index > 0) || index === listData.length - 1) {
                result += sHallLiVoidHtml.slice(0, -5) + stSave + sHallLiVoidHtml.slice(-5);
                stSave = '';
            }*/
        });
        oHallListNode.css({
            width: listWidth + (listData.length * (parentWidth / 2)) + 'px'
        });
        oHallListNode.html(oHallListNode.html() + stSave);
    }

    // 绑定导航承载框点击函数
    function hallListBtnClick() {
        bindHallListLeftBtnClick();
        bindHallListRightBtnClick();
    }

    // 左按钮点击
    function bindHallListLeftBtnClick() {
        oHallListLeftBtnNode.off().click(function () {
            rightMoveFuc();
        });
    }

    // 右按钮点击
    function bindHallListRightBtnClick() {
        oHallListRightBtnNode.off().click(function () {
            var stSave = null;
            if (!oIsRequestAll[sNowHallTypeId]) {
                nPageIndex += 1;
                getHallList(function () {
                    stSave = nPageIndex * nPageSize;
                    if (oHallListData[sNowHallTypeId][stSave] !== undefined) {
                        writeHallList(oHallListData[sNowHallTypeId].slice(stSave));
                    }
                    leftMoveFuc();
                });
            } else {
                leftMoveFuc();
            }
        });
    }

    // 右移动方法
    function rightMoveFuc() {
        // 获取左距离
        var nLeftLength = Math.abs(parseInt(oHallListNode.css('left'))),
            oParentNode = oHallListNode.parent(),
            nParentWidth = oParentNode.width();
        if (nLeftLength > 0) {
            if (nLeftLength > nParentWidth) {
                oHallListNode.animate({
                    left: nParentWidth - nLeftLength + 'px'
                }, function () {
                    oHallListRightBtnNode.show();
                });
            } else {
                oHallListNode.animate({
                    left: 0
                }, function () {
                    oHallListRightBtnNode.show();
                    oHallListLeftBtnNode.hide();
                });
            }
        }
    }

    // 左移动方法
    function leftMoveFuc() {
        // 获取左距离
        var nLeftLength = Math.abs(parseInt(oHallListNode.css('left'))),
            oParentNode = oHallListNode.parent(),
            nParentWidth = oParentNode.width(),
            nListWidth = oHallListNode.width();
        if (nLeftLength + nParentWidth < nListWidth) {
            if (nLeftLength + 2 * nParentWidth < nListWidth) {
                oHallListNode.animate({
                    left: (0 - nLeftLength - nParentWidth) + 'px'
                }, function () {
                    oHallListLeftBtnNode.show();
                });
            } else {
                oHallListNode.animate({
                    left: nParentWidth - nListWidth + 'px'
                }, function () {
                    oHallListLeftBtnNode.show();
                    oHallListRightBtnNode.hide();
                });
            }
        }
    }
});