/**
 * 数字展厅详情
 * author: GloryAce
 */
$(function () {
    var oUlBig = $(".number-hall-detail .big-pic")[0];
    var oSmall = $(".small-pic-area")[0];
    var oUlSmall = $(".number-hall-detail .small-pic-list")[0];
    var oBtnPrev = $('.number-hall-detail .prev')[0];
    var oBtnNext = $('.number-hall-detail .next')[0];
    //vr_url
    var iSmallPicWidth = 206;

    var nowZIndex = 2;      // 当前大图的z-index
    var now = 0;            // 当前轮播序号
    var timer = null;
    var interval = 4500;    // 轮播时间间隔

    // 详情数据
    var detailData = {};
    // 产品联系方式导航栏
    var oNavNodes = $('.product-contact-nav-list').eq(0);
    // 详情模块节点数组
    var aModelNodes = $('.number-hall-product-contact-area .detail-container').eq(0).children();
    // 暂无数据
    var noDataHtml = '<p class="content-none">暂无数据</p>';

    // 行业类型
    var iProductIndustryId = (hallDetail.application_industry) ? JSON.parse(hallDetail.application_industry).id : '202151';

    /*=== 地图 ===*/
    var domId = 'mapPlugin';
    // 113.042662
    var longitude = null;
    // 23.157785
    var latitude = null;
    var zoom = 15;
    // 浮框的宽
    var panelWidth = 350;
    // 浮框的高
    var panelHeight = 105;
    var title = "广工大数控装备协同创新研究院";
    var address = "广东省佛山市南海区狮山镇南海软件科技园广云路";
    var phone = "0757-86687073";
    var introduction = "由省科技厅、佛山市、南海区和广工大共同建设的集数控装备技术研发、成果转化及孵化、人才培养与引进等功能于一体的开放式创新型科研实体和公共服务平台。";


    /**
     * 函数调用
     * author: GloryAce
     */
    extractHallDetail();
    writeHallDetail();
    // 精选产品数据
    getProductList();
    // 设置文本超出提示
    setTitleOverTip();
    // 导航点击事件
    eventOfHallDetailNavClick();
    // 联系数据
    wirteHallContactData();

    /**
     * 数字展厅区域
     * author: GloryAce
     */
    // 提取展厅数据
    function extractHallDetail() {
        var stSave = null;
        console.log(hallDetail);
        detailData.title_cn = hallDetail.title || '暂无数据';
        detailData.title_en = hallDetail.english_title || '暂无数据';
        detailData.introduction = hallDetail.introduction || '暂无数据';
        detailData.image = [];
        if (hallDetail.picture_show) {
            stSave = JSON.parse(hallDetail.picture_show);
            stSave.forEach(function (item) {
                var obj = {};
                obj.src = '/adjuncts/file_download/' + item.id;
                obj.title = item.title;
                detailData.image.push(obj);
            })
        }
    }

    // 写入展厅数据
    function writeHallDetail() {
        // 获取中文标题
        var nTitleCN = $('.detail-container .title-ch').eq(0);
        // 获取英文标题
        var nTitleEN = $('.detail-container .title-en').eq(0);
        // 获取介绍节点
        var nIntroduction = $('.detail-container .detail-info').eq(0);
        nTitleCN.text(detailData.title_cn);
        nTitleEN.text(detailData.title_en);
        nIntroduction.text(detailData.introduction);
        // 轮播图
        setSliderList(detailData.image, handleEvent);
    }

    function setSliderList(list, callback) {
        var fragmentOfBig = document.createDocumentFragment();
        var fragmentOfSmall = document.createDocumentFragment();
        for (var i = 0; i < list.length; i++) {
            var liItemOfBig = null;
            var liItemOfSmall = null;
            if (i === 0) {
                liItemOfBig = $('<li style="z-index: 1;"><img src="' + list[i].src + '" alt="' + list[i].title + '"/><p class="img-title">' + list[i].title + '</p></li>');
                liItemOfSmall = $('<li style="filter: alpha(opacity: 100); opacity: 1;"><img src="' + list[i].src + '" alt="' + list[i].title + '"/><p class="img-title">' + list[i].title + '</p></li>');
            } else {
                liItemOfBig = $('<li><img src="' + list[i].src + '" alt="' + list[i].title + '"/><p class="img-title">' + list[i].title + '</p></li>');
                liItemOfSmall = $('<li><img src="' + list[i].src + '" alt="' + list[i].title + '"/><p class="img-title">' + list[i].title + '</p></li>');
            }
            fragmentOfBig.appendChild(liItemOfBig[0]);
            fragmentOfSmall.appendChild(liItemOfSmall[0]);
        }
        oUlBig.appendChild(fragmentOfBig);
        oUlSmall.appendChild(fragmentOfSmall);
        $(oUlSmall).css('width', list.length * iSmallPicWidth + 'px');
        // 事件监听
        if (callback) {
            callback();
        }
    }

    // 事件监听函数
    function handleEvent() {
        var aLiSmall = $(oUlSmall).find("li");
        /**** 左右按钮点击 ****/
        oBtnPrev.onclick = function () {
            now--;
            if (now === -1) {
                now = aLiSmall.length - 1;
            }
            sliderMove();
        };
        oBtnNext.onclick = function () {
            now++;
            if (now === aLiSmall.length) {
                now = 0;
            }
            sliderMove();
        };

        /**** 点击小图切换大图 ****/
        for (var i = 0; i < aLiSmall.length; i++) {
            aLiSmall[i].index = i;
            aLiSmall[i].onclick = function () {
                if (this.index === now) return;
                now = this.index;
                sliderMove();
            };
            aLiSmall[i].onmouseover = function () {
                startMove(this, {opacity: 100});
            };
            aLiSmall[i].onmouseout = function () {
                if (this.index != now) {
                    startMove(this, {opacity: 60});
                }
            };
        }

        /**** 启动动画 ****/
        timer = setInterval(oBtnNext.onclick, interval);
        oSmall.onmouseover = oUlBig.onmouseover = function () {
            clearInterval(timer);
            timer = null;
        };
        oSmall.onmouseout = oUlBig.onmouseout = function () {
            clearInterval(timer);
            timer = setInterval(oBtnNext.onclick, interval);
        };
    }

    // 动画执行函数
    function sliderMove() {
        var aLiSmall = $(oUlSmall).find("li");
        var aLiBig = $(oUlBig).find("li");
        aLiBig[now].style.zIndex = nowZIndex++;
        for (var j = 0; j < aLiSmall.length; j++) {
            startMove(aLiSmall[j], {opacity: 60});
        }
        startMove(aLiSmall[now], {opacity: 100});
        aLiBig[now].style.height = 0;
        startMove(aLiBig[now], {height: 508});
        // 当list宽度超出父框时
        if ($(oSmall).width() < $(oUlSmall).width()) {
            if (now === 0) {
                startMove(oUlSmall, {left: 0});
            } else if (now === aLiSmall.length - 2) {
                startMove(oUlSmall, {left: -(now - 2) * aLiSmall[0].offsetWidth});
            } else if (now === aLiSmall.length - 1) {
                startMove(oUlSmall, {left: -(now - 3) * aLiSmall[0].offsetWidth});
            } else {
                startMove(oUlSmall, {left: -(now - 1) * aLiSmall[0].offsetWidth});
            }
        }
    }

    /*=== 导航栏 ===*/
    function eventOfHallDetailNavClick() {
        oNavNodes.off().click(function (event) {
            var oNowNavNode = $(event.target);
            if (oNowNavNode.get(0).tagName.toLowerCase() === 'li' && !oNowNavNode.hasClass('active')) {
                oNowNavNode.siblings().removeClass('active');
                oNowNavNode.addClass('active');
                hallProductContactChange(oNowNavNode.attr('name'));
            }
        })
    }

    function hallProductContactChange(typeName) {
        aModelNodes.each(function (index, modelNode) {
            var oNowModelNode = $(modelNode);
            if (oNowModelNode.attr('name') === typeName) {
                oNowModelNode.show();
            } else {
                oNowModelNode.hide();
            }
        })
    }

    /**
     * 精选产品区域
     * author: GloryAce
     */
    // 获取精选产品数据
    /*function getProductList () {
        var json = {
            pager: {
                current: 1,
                size: 8
            }
        };
        $.ajax({
            url: '/f/matureCase/get_recommend_mature_case',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(json),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    setProductList(list)
                } else {
                    throw new Error(res.status)
                }
            },
            error: function (err) {
                console.error('综合', err)
            }
        })
    }*/

    function getProductList() {
        var size = 8,
            oProductArea = $('.product-content').eq(0),
            json = {
                applicationIndustry: iProductIndustryId,
                sortPointer: {
                    filed: "recommended_index",
                    order: "DESC"
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
                        setProductList(list);
                    } else {
                        oProductArea.html(noDataHtml);
                    }
                }
            },
            error: function (err) {
                console.error('产品', err);
                oProductArea.html(noDataHtml);
            }
        });
    }


    // 设置精选产品数据
    function setProductList(list) {
        var aProductItem = $(".number-hall-detail .product-content .product-item"),
            oNowItemNode = null;
        aProductItem.each(function (index, itemNode) {
            oNowItemNode = $(itemNode);
            if (index > list.length - 1) {
                oNowItemNode.remove();
            } else {
                oNowItemNode.find(".product-card").attr("href", '/f/' + list[index].id + '/case_detail.html?pc=true');
                oNowItemNode.find(".title").text(list[index].title);
                oNowItemNode.find(".type").text('行业：' + (list[index].application_industry ? JSON.parse(list[index].application_industry).title : '暂无数据'))
                oNowItemNode.find(".address").text('所属地区：' + (list[index].address ? list[index].address : '暂无数据'));
                oNowItemNode.find(".product-img").attr('src', list[index].picture_cover ? $(this).getAvatar(list[index].picture_cover.split(",")[0]) : null);
                oNowItemNode.find(".see-num").text(list[index].click_rate);
                oNowItemNode.find(".money").text('￥ ' + (list[index].case_money ? list[index].case_money + '万元' : '面议'));
            }
        });
    }

    // 设置title超出提示
    function setTitleOverTip() {
        // 产品列表父框区域
        var nProductListParent = $('.number-hall-product-area .product-content').eq(0);
        // 绑定事件
        nProductListParent.mouseover(eventOfTextOver)
    }

    // 超出提示事件
    function eventOfTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        if (event.target.tagName.toLowerCase() === 'p') {
            nNowActive = $(event.target);
            if (nNowActive.hasClass('text-overflow')) {
                layer.closeAll();
                layer.tips(nNowActive.text(), nNowActive, {
                    tips: [1, '#000000']
                });
            }
        }
    }

    /*=== 写入联系方式 ===*/
    function wirteHallContactData() {
        // 获取p标签
        var oWaiterNode = $('.detail-contact-model .hall-waiter').eq(0),
            oPhoneNode = $('.detail-contact-model .hall-phone').eq(0),
            oTimeNode = $('.detail-contact-model .hall-time').eq(0),
            oAddressNode = $('.detail-contact-model .hall-address').eq(0),
            oMapNode = $('#mapPlugin'),
            stSave = null;

        if (hallDetail.waiter) {
            oWaiterNode.text(oWaiterNode.text() + hallDetail.waiter);
        } else {
            oWaiterNode.text(oWaiterNode.text() + '暂无数据');

        }
        if (hallDetail.phone_num) {
            oPhoneNode.text(oPhoneNode.text() + hallDetail.phone_num);
        } else {
            oPhoneNode.text(oPhoneNode.text() + '暂无数据');
        }
        if (hallDetail.open_time) {
            oTimeNode.text(oTimeNode.text() + hallDetail.open_time);
        } else {
            oTimeNode.text(oTimeNode.text() + '暂无数据');

        }
        if (hallDetail.address || hallDetail.detail_address) {
            oAddressNode.text(oAddressNode.text() + (hallDetail.address + ' ' || '') + (hallDetail.detail_address || ''));
        } else {
            oAddressNode.text(oAddressNode.text() + '暂无数据');
        }
        oMapNode.hide();
        /*if (hallDetail.address_coordinate) {
            oMapNode.show();
            stSave = hallDetail.address_coordinate.split(',');
            longitude = Number(stSave[0]);
            latitude = Number(stSave[1]);
            title = hallDetail.title || '暂无数据';
            if (hallDetail.address || hallDetail.detail_address) {
                address = (hallDetail.address || '') + (hallDetail.detail_address || '');
            } else {
                address = "暂无数据";
            }
            phone = hallDetail.phone_num || '暂无数据';
            introduction = hallDetail.introduction || '暂无数据';
            showMap(domId, longitude, latitude, zoom, panelWidth, panelHeight, title, address, phone, introduction);
        } else {
            oMapNode.hide();
        }*/
    }

    /**
     * 地图展示
     * 百度地图拾取坐标地址（用于搜索一个地址的经纬度）：http://api.map.baidu.com/lbsapi/getpoint/index.html
     * @param {String} domId 加载地图的dom节点的id
     * @param {Number} longitude 经度
     * @param {Number} latitude 纬度
     * @param {Number} zoom 地图放大级别，数字越大越精确
     * @param {Number} width panel的宽度
     * @param {Number} height panel的高度
     * @param {String} title 标题
     * @param {String} address 地址
     * @param {String} phone 电话
     * @param {String} introduction 简介
     */
    function showMap(domId, longitude, latitude, zoom, width, height, title, address, phone, introduction) {
        var map = new BMap.Map(domId);
        var point = new BMap.Point(longitude, latitude);
        map.centerAndZoom(point, zoom);
        map.enableScrollWheelZoom();
        var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
            '地址：' + address + '<br/>电话：' + phone + '<br/>简介：' + introduction +
            '</div>';
        //创建检索信息窗口对象
        var searchInfoWindow = null;
        searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
            title: title,           // 标题
            width: width,           // 宽度
            height: height,          // 高度
            panel: "panel",         // 检索结果面板
            enableAutoPan: true,     // 自动平移
            searchTypes: [
                BMAPLIB_TAB_SEARCH,     // 周边检索
                BMAPLIB_TAB_TO_HERE,    // 到这里去
                BMAPLIB_TAB_FROM_HERE   // 从这里出发
            ]
        });
        searchInfoWindow.open(new BMap.Point(longitude, latitude));
        var marker = new BMap.Marker(point);                          // 创建marker对象
        marker.enableDragging();                                      // marker可拖拽
        marker.addEventListener("click", function (e) {
            searchInfoWindow.open(marker);
        });
        map.addOverlay(marker);                                       //在地图中添加marker
    }
});