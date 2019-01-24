$(function () {
    /*=== 常量 ===*/
    // 常量配置数据
    var constConfigData = {
        AREA_MAX_WIDTH: 1920,
        AREA_MIN_WIDTH: 1350,
        AREA_MAX_HEIGHT: 1080,
        BUTTON_HTML:'<div class="panoramic-hall-content-module-item-btn-div"><span class="panoramic-hall-content-module-item-btn-tip"></span><button class="panoramic-hall-content-module-item-btn" name=""></button></div>',
        CIRCLE_HTML:'<div class=""></div>'
    };
    // 常量对象
    var constObj = null;
    // 常量初始化
    configConst(constConfigData);

    /*=== 变量 ===*/
    // 获取全景展厅区域
    var n_panoramicHallArea = $('#panoramicHall');
    // 获取展厅列表区域
    var n_panoramicHallList = n_panoramicHallArea.find('.panoramic-hall-content-module-list').eq(0);
    // 获取全景展厅项节点
    var n_hallListItem = n_panoramicHallArea.find('.panoramic-hall-content-module-list li');
    // 获取当前的url
    var url = window.location.href;
    // 全景图背景配置数据
    var bgImageConfigData = {
        vr: '/static/front/assets/image/vr.jpg',
        robot: '/static/front/assets/image/robot.jpg'
    };
    // span 标签正则
    var r_btnSpan = /<span((?!<\/span>).)+<\/span>/;
    // div 按钮外框的正则
    var r_btnDiv = /<div(.+(?=<span))/;
    // btn 标签正则
    var r_btn = /<button((?!<\/button>).)+<\/button>/;
    // 空字符换行的正则
    var r_space = />\s+<(\/)?/g;
    // 全景按钮配置数据
    var btnConfigData = {
        vr: [
            {
                tip:'新鹏3C机器人展区',
                type: 'guide',
                top: Math.ceil(642 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(1342 / constObj.AREA_MAX_WIDTH * 100) + '%'
            },
            {
                tip:'产品视频',
                type: 'video',
                top: Math.ceil(280 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(356 / constObj.AREA_MAX_WIDTH * 100) + '%'
            },
            {
                tip:'产品全景图',
                type: 'view',
                top: Math.ceil(734 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(416 / constObj.AREA_MAX_WIDTH * 100) + '%'
            },
            {
                tip:'产品视频',
                type: 'video',
                top: Math.ceil(353 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(772 / constObj.AREA_MAX_WIDTH * 100) + '%'
            },
            {
                tip:'产品全景图',
                type: 'view',
                top: Math.ceil(616 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(791 / constObj.AREA_MAX_WIDTH * 100) + '%'
            }
        ],
        robot:[
            {
                tip:'车间正门',
                type: 'guide',
                top: Math.ceil(582 / constObj.AREA_MAX_HEIGHT * 100) + '%',
                left: Math.ceil(902 / constObj.AREA_MAX_WIDTH * 100) + '%'
            }
        ]
    };
    // icon配置数据
    var iconConfig = {
        video: 'icon-yingyinshiting_s',
        view: 'icon-quanjing'
    };
    // id关联函数配置数据
    var idFucConfig = {
        zoomBig:globalTip,
        eye:globalTip,
        chat:globalTip,
        VR:globalTip,
        sceneSelect:globalTip,
        sceneShare:globalTip,
        mapNavigation:globalTip,
        music:globalTip,
        map:globalTip,
        rotate:globalTip
    };
    // 记录类型
    var hallType = getHallTypeFromUrl(url);

    /*=== 函数调用 ===*/
    // 区域大小初始化
    eventOfChangeAreaHeight();
    // 页面大小监听
    listenWindowWidthChange();
    // 写入背景图
    addImageAndButton();
    // 调用按钮事件
    bindGlobalButtonEvent();
    // 切换展厅
    changeHall(hallType);


    /*=== 函数定义 ===*/
    // 动态定义所有的常量
    function configConst(configData) {
        (function (configData) {
            'use strict';
            if (constObj === null) {
                constObj = new Object({});
            }
            Object.keys(configData).forEach(function (key) {
                if (constObj[key] !== undefined) {
                    return 0;
                }
                Object.defineProperty(constObj, key, {
                    value: configData[key],
                    writable: false,
                    enumerable: true,
                    configurable: false
                });
            });
        }(configData));
    }
    // 页面宽度监听
    function listenWindowWidthChange() {
        window.onresize = eventOfChangeAreaHeight;
    }
    // 计算高度并修改高度
    function eventOfChangeAreaHeight() {
        // 获取当前可见宽度
        var nowClientWidth = document.body.clientWidth,
            // 获取区域高度
            areaHeight = 0;
        if (nowClientWidth > constObj.AREA_MAX_WIDTH) {
            return 0;
        }
        if (nowClientWidth < constObj.AREA_MIN_WIDTH) {
            areaHeight = Math.ceil(constObj.AREA_MIN_WIDTH * (constObj.AREA_MAX_HEIGHT / constObj.AREA_MAX_WIDTH));
        } else {
            areaHeight = Math.ceil(nowClientWidth * (constObj.AREA_MAX_HEIGHT / constObj.AREA_MAX_WIDTH));
        }
        n_panoramicHallArea.css({
            height: areaHeight + 'px'
        });
    }
    // 写入图片+按钮
    function addImageAndButton() {
        var nowNodeName = '';
        n_hallListItem.each(function (index, itemNode) {
            nowNodeName = itemNode.getAttribute('name');
            writeBgImage(itemNode,nowNodeName);
            writeFucButton(itemNode,nowNodeName)
        });
    }
    // 写入背景图片
    function writeBgImage(node, nodeName) {
        var n_img = document.createElement('img');
        n_img.onload = function () {
            n_img.style.width = '100%';
            n_img.style.height = '100%';
            $(node).prepend(n_img);
        };
        n_img.src = bgImageConfigData[nodeName];
    }
    // 写入功能按钮
    function writeFucButton(node, nodeName) {
        var s_result = '';
        btnConfigData[nodeName].forEach(function (btnItem) {
            s_result += constObj.BUTTON_HTML.replace(r_space, function (spaceStr) {
                return (spaceStr.indexOf('/') > -1) ? '></' : '><';
            }).replace(r_btnDiv, function (btnStr) {
                return btnStr.slice(0, -1) + 'style="top:' + btnItem.top + ';left:' + btnItem.left + '"' + btnStr.slice(-1);
            }).replace(r_btnSpan, function (spanStr) {
                return spanStr.slice(0, -7) + btnItem.tip + spanStr.slice(-7);
            }).replace(r_btn, function (btnStr) {
                return btnStr.replace('name=""', 'name="' + btnItem.type + '"');
            }).replace(r_btn, function (btnStr) {
                var stSave = constObj.CIRCLE_HTML.replace('class=""',function (str) {
                    if (btnItem.type !== 'guide') {
                        return str.slice(0, -1) + iconConfig[btnItem.type] + str.slice(-1);
                    } else {
                        return str;
                    }
                });
                if (btnItem.type !== 'guide') {
                    return btnStr.slice(0, -9) + stSave + btnStr.slice(-9);
                } else {
                    return btnStr;
                }
            })
        });
        node.innerHTML = node.innerHTML + s_result;
        // console.log(s_result);
    }
    // 点击事件委托
    function bindGlobalButtonEvent() {
        n_panoramicHallArea.off('click', 'eventOfButtonClick').click(eventOfButtonClick);
    }
    // 按钮事件
    function eventOfButtonClick(event) {
        // 获取当前点击节点
        var n_nowClick = $(event.target);
        // 节点名称
        var nodeName = event.target.tagName.toLowerCase();
        // 按钮节点
        var n_button = null;
        // 按钮id
        var buttonId = null;
        // 当前展厅项
        var n_nowLi = null;

        if (nodeName !== 'button' && n_nowClick.parents('button').length === 0) {
            return 0;
        }

        n_button = (nodeName === 'button') ? n_nowClick : n_nowClick.parents('button').eq(0);
        // 有 id
        if (n_button.attr('id')) {
            buttonId = n_button.attr('id');
            idFucConfig[buttonId]();
        } else {
            if (n_button.attr('name') === 'guide') {
                n_nowLi = n_button.parents('li').eq(0);
                if (n_nowLi.attr('name') === 'vr') {
                    window.location.href = '/f/panoramic_hall.html#type="robot"';
                    changeHall('robot');
                } else {
                    window.location.href = '/f/panoramic_hall.html#type="vr"';
                    changeHall('vr');
                }
            } else {
                globalTip();
            }
        }
    }
    // 获取url参数
    function getHallTypeFromUrl(url) {
        if (url.indexOf('#') < 0) {
            return 'vr';
        }
        var stSave = url.split('#')[1];
        return stSave.split('%22')[1];
    }
    // 提示函数
    function globalTip(tip) {
        tip = tip || '当前功能暂未开放';
        layer.closeAll();
        layer.msg(tip);
    }
    // 切换展厅
    function changeHall(type) {
        var n_nowLi = null;
        n_hallListItem.each(function (index,item) {
            n_nowLi = $(item);
            n_nowLi.hide();
            if (n_nowLi.attr('name').toLowerCase() === type){
                n_nowLi.show();
            }
        })
    }
});