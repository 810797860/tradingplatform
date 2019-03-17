// 获取webSocket
var global_ws = null;

// 检测网页的打开状态（新开/刷新）
windowOpenStatus(function (isOpen) {
    if (window.localStorage === undefined) {
        console.error('当前浏览器版本不兼容localStorage');
        return 0;
    }
    // 获取localStorage
    var local = window.localStorage;
    // 存储a标签html
    var linkHtml = '<a class="pNotify-message-link" href=""></a>';
    // 查阅的定时器
    var checkTime = null;
    // 新开页面
    if (isOpen) {
        // 页面计数不为空且计数为0，表示页面全关闭后再新开了一个页面
        if (local.getItem('windowIndex') !== null && parseInt(local.getItem('windowIndex')) === 0) {
            // 清空所有数据
            saveToLocalStorage('user', null);
            saveToLocalStorage('serviceName', null);
            saveToLocalStorage('check', '0');
        }
    }
    // 网页计数
    saveNewWindowNumber(true);

    // 根据全局数据判定处理socket的链接
    if (local.getItem('user') !== null) {
        if (global_ws !== null && global_ws.socketClose !== undefined) {
            global_ws.socketClose();
        }
        // 获取用户数据
        var data = JSON.parse(local.getItem('user'));
        if (global_ws === null) {
            global_ws = new SOCKET(data.id);
        } else {
            // 切换用户链接
            global_ws.setSocketNewUrl(data.id);
            // 重启socket
            global_ws.restartSocket();
        }
        // 监听 open
        global_ws.setSocketOpenCallback(function () {
            /* 链接上时的回调函数 */
            console.log('链接成功');
        });
        // 监听 message
        global_ws.setSocketMessageCallback(function (data) {
            console.log(data);
            var info = JSON.parse(JSON.parse(data).content);
            var messageTip = $('#head-personal').find('span[name="messageNumber"]').eq(0);
            var oldNumber = valueFilter(messageTip.data('number'), 0);
            var messageNumber = parseInt(oldNumber) + 1;
            var title = info.title;
            var message = linkHtml.replace(/href=""/, 'href="/f/personal_center.html?pc=true#menu=messageInformList"')
                .replace(/><\/a>/, '>' + info.content + '<\/a>');
            messageTip.data('number', messageNumber);
            messageTip.text((messageNumber < 100) ? messageNumber.toString() : '99+').show();
            local.setItem('check', messageNumber.toString());
            pTipMessage(title, message, 'warning', 5000, true);
        }, 'data');
    } else {
        // 页面打开时，socket初始化
        global_ws = new SOCKET();
    }
    // 判别消息通知角标
    if (local.getItem('check') !== null) {
        checkTime = setTimeout(function () {
            // 消息提示
            var messageTip = $('#head-personal').find('span[name="messageNumber"]').eq(0);
            var checkNumber = parseInt(local.getItem('check'), 10);
            if (checkNumber > 0) {  // 未查阅信息
                messageTip.data('number', checkNumber).text(checkNumber).show();
            } else {// 已经查阅信息
                messageTip.data('number', 0).text('0').hide();
            }
            clearTimeout(checkTime);
        }, 200)
    } else {
        checkTime = setTimeout(function () {
            // 消息提示
            var messageTip = $('#head-personal').find('span[name="messageNumber"]').eq(0);
            // 新建全局数据check
            local.setItem('check', '0');
            // 归零隐藏角标
            messageTip.data('number', 0).text('0').hide();
            clearTimeout(checkTime);
        }, 200)
    }
    // 调用localStorage监听
    storageEvent(function (key, STORAGE) {
        if (key === 'user') {
            var data = STORAGE.getItem(key);
            // 表示退出登录
            if (data === null) {
                if (global_ws !== null && global_ws.socketClose !== undefined && typeof global_ws.socketClose === "function") {
                    global_ws.socketClose();
                }
            } else {
                if (global_ws === null) {
                    // 获取用户数据
                    data = JSON.parse(data);
                    // 启用 webSocket
                    global_ws = new SOCKET(data.id);
                    // 监听 open
                    global_ws.setSocketOpenCallback(function () {
                        console.log('链接成功');
                    });
                    // 监听 message
                    global_ws.setSocketMessageCallback(function (data) {
                        console.log(data);
                        var info = JSON.parse(JSON.parse(data).content);
                        var messageTip = $('#head-personal').find('span[name="messageNumber"]').eq(0);
                        var oldNumber = valueFilter(messageTip.data('number'), 0);
                        var messageNumber = parseInt(oldNumber) + 1;
                        var title = info.title;
                        var message = linkHtml.replace(/href=""/, 'href="/f/personal_center.html?pc=true#menu=messageInformList"')
                            .replace(/><\/a>/, '>' + info.content + '<\/a>');
                        messageTip.data('number', messageNumber);
                        messageTip.text((messageNumber < 100) ? messageNumber.toString() : '99+').show();
                        console.error(messageNumber);
                        local.setItem('check', messageNumber.toString());
                        pTipMessage(title, message, 'warning', 5000, true);
                    }, 'data');
                    // 监听 message
                    global_ws.setSocketCloseCallback(function (status) {
                    });
                }
            }
        } else if (key === 'check') {
            var check = STORAGE.getItem(key);
            var messageTip = $('#head-personal').find('span[name="messageNumber"]').eq(0);
            if (check !== null) {
                var checkNumber = parseInt(check);
                if (checkNumber > 0) {  // 未查阅信息
                    messageTip.data('number', checkNumber).text(checkNumber).show();
                } else {// 已经查阅信息
                    messageTip.data('number', 0).text('0').hide();
                }
            } else {// 表示未查阅
                STORAGE.setItem(key, '0');
                messageTip.data('number', 0).text(0).hide();
            }
        }
    })
});

// 页面关闭前监听
windowBeforeUnload(function (event) {
    var sTip = "系统可能不会保存您所做的更改。";
    event = event || window.event;
    // 若存在重要操作，则提示
    if (window.importantOperation) {
        event.returnValue = sTip;
        return sTip;
    }
});

// 页面关闭退出
windowUnload(function () {
    // 重置重要操作标记
    set_IMPORTANTOPERATION(false);
    // 减去页面计数
    saveNewWindowNumber(false);
    // 若存在socket
    if (global_ws !== null && global_ws.socketClose !== undefined && typeof global_ws.socketClose === "function") {
        // 关闭socket
        global_ws.socketClose();
    }
});

// 处理登录信息（立即执行函数）
(function () {
    // 登录按钮html
    var loginBtnHtml = '<a id="loginToCenter" style="text-decoration: none" class="login-link" href="/f/login.html?pc=true"><span class="login">登录</span></a>';
    // 注册按钮html
    var registerBtnHtml = '<a id="goToRegister" style="text-decoration: none" class="register-link" href="/f/register.html?pc=true">注册</a>';
    // 消息按钮html
    // var personalBtnHtml = '<a id="head-personal" style="text-decoration: none" class="avatar-center personal-link" href="javascript:void(0);">消息<span class="badge" name="messageNumber" style="display: none">0</span></a>';
    // 退出按钮html
    var signOutBtnHtml = '<a id="head-signOut" style="text-decoration: none" class="avatar-out sign-out-link" target="_self" href="/f/login.html?pc=true">退出</a>';
    // 个人头像Html
    var avatarHtml = '<a id="head-avatar" style="text-decoration: none" class="avatar-div avatar-link" href="/f/personal_center.html?pc=true#menu=userInfo"><img class="avatar-img" src=""></a>';
    // 个人中心按钮html
    var personalBtnHtml = '<a id="personal-center" class="personal-center-link" href="/f/personal_center.html?pc=true#menu=userInfo">个人中心</a>';
    // 用户名html
    var userNameBtnHtml = '<a id="user-name" class="user-name-link" href="/f/personal_center.html?pc=true#menu=userInfo"></a>';
    // 帮助中心Html
    var helpHtml = '<a id="head-help" style="text-decoration: none" class="help-link" href="/f/help_center_detail.html?pc=true" target="_blank">帮助中心</a>';

    // 登录信息处理
    dealWithLogin();

    // 处理登录节点
    function dealWithLogin() {
        // 定义a标签正则表达式
        var aNodeRule = /<a id="((?!<\/a>).)+<\/a>/g;
        // 存储新html内容
        var newHtml = '';
        // 获取头部右侧节点
        var rightPartNode = $('.header .right').eq(0);
        // 获取内容
        var html = rightPartNode.html();
        // 获取用户信息
        var userInfo = getUserInfo();
        // 若用户信息不为空
        if (userInfo !== null) {
            var stSave = userInfo.avatar;
            var picUrl = (stSave !== undefined) ? rightPartNode.getAvatar(stSave) : '/static/assets/logo.png';
            if (html.length > 0) {
                // 获取新html
                newHtml = html.replace(aNodeRule, function (aStr) {
                    if (aStr.indexOf('loginToCenter') > -1) {
                        return avatarHtml.replace(/src=""/, 'src="' + (picUrl) + '"') + userNameBtnHtml.replace("</a>", userInfo.userName + '</a>') + personalBtnHtml;
                    } else if (aStr.indexOf('goToRegister') > -1) {
                        return signOutBtnHtml;
                    } else {
                        return aStr;
                    }
                })
            } else {
                newHtml = avatarHtml.replace(/src=""/, 'src="' + (picUrl) + '"') + userNameBtnHtml.replace("</a>", userInfo.userName + '</a>') + personalBtnHtml + signOutBtnHtml + helpHtml;
            }
        } else {
            if (html.length > 0) {
                newHtml = html.replace(aNodeRule, function (aStr) {
                    if (aStr.indexOf('head-personal') > -1) {
                        return loginBtnHtml;
                    } else if (aStr.indexOf('head-signOut') > -1) {
                        return registerBtnHtml;
                    } else if (aStr.indexOf('head-avatar') > -1) {
                        return '';
                    } else {
                        return aStr;
                    }
                })
            } else {
                newHtml = loginBtnHtml + registerBtnHtml + helpHtml;
            }
        }
        rightPartNode.html(newHtml);
        rightPartNode = null;
    }

    // 获取用户信息
    function getUserInfo() {
        var storage = window.localStorage;
        return (storage.getItem('user') !== null) ? JSON.parse(storage.getItem('user')) : null;
    }
})();

// 需要页面完全加载完成
$(function () {
    var $userName = userNameInfo;
    var loginOk = 0;
    var selectOption = "demand";
    // 获取浏览器版本
    var browser = BrowserType();
    // 存储热门搜索关键词的请求接口
    var keyRequestUrl = {
        demand: '/f/projectDemandSearchRecord/query_hot_search_record',
        product: '/f/matureCaseSearchRecord/query_hot_search_record',
        company: '/f/serviceProvidersSearchRecord/query_hot_search_record',
        service: '/f/serviceMessageSearchRecord/query_hot_search_record'
    };
    // 存储热门搜索关键词的存储接口
    var keySaveUrl = {
        demand: '/f/projectDemandSearchRecord/create_update',
        product: '/f/matureCaseSearchRecord/create_update',
        company: '/f/serviceProvidersSearchRecord/create_update',
        service: '/f/serviceMessageSearchRecord/create_update'
    };

    /* 直播 */
    // 直播展示节点
    var oVideoLiveShowNode = $('.global-video-live-div[type="show"]').eq(0);
    // 直播关闭节点
    var oVideoLiveHiddenNode = $('.global-video-live-div[type="hidden"]').eq(0);

    // 将浏览器版本赋值给全局变量
    if (window.BROWSERTYPE === undefined) {
        Object.defineProperty(window, 'BROWSERTYPE', {
            // 不可删除
            configurable: false,
            // 不可枚举
            enumerable: false,
            // 可以修改
            writable: true,
            // 默认值null
            value: 'ok'
        })
    }
    window.BROWSERTYPE = (browser !== null) ? (browser !== undefined) ? browser : true : false;

    if ($userName) {
        loginOk = 1
    }

    // 设置用户名
    if (loginOk) {
        $(".avatar-login").css("display", "inline-block");
        $(".avatar-img").bind('click', goTocenter);
        $(".login-register").css("display", "none");
        $(".avatar-center").bind('click', goTocenter);
    } else {
        $(".avatar-login").css("display", "none");
        $(".login-register").css("display", "inline-block");
    }
    // 获取热门搜索
    getHotKey();
    // 调用全部事件
    handleEvent();
    // 禁用flvjs提示
    closeFlvJsLog();


    /*=== 监听事件 ===*/
    function handleEvent() {
        // 监听免费发布点击事件
        $('.right .freePublishCase').click(function () {
            var user = window.localStorage.getItem('user');
            if (!!user) {
                window.location.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
                // window.open('/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true', '_self');
            } else {
                layer.msg("请先登录");
                setTimeout(function () {
                    window.location.href = '/f/login.html?pc=true';
                    // window.open('/f/login.html?pc=true', '_self');
                }, 500);
            }
        })

        // 监听搜索的点击事件
        $(".header .bottom-container .right .search-btn").off().on("click", eventOfSelectChange);
        // 监听搜索框的回车事件
        $('.header .bottom-container .right .search-input').on('keypress', function (event) {
            if (event.keyCode == 13) {
                eventOfSelectChange();
            }
        });
        // 监听hot-key的点击事件
        $(".header .bottom-container .right .right-bottom").on("click", ".hot-item", function () {
            $(".header .bottom-container .right .search-input").val($(this).text());
            $('.header .bottom-container .right .search-btn').trigger("click");
        });

        // 监听select的change事件
        $(".header .right .select").off().on("change", function () {
            selectOption = $(".header .bottom-container .right .select").find("option:selected").data("value");
            getHotKey();
        });

        // 消息点击监听
        eventOfMessageLinkClick();

        /* 直播 */
        eventOfVideoCloseIconClick();
        eventOfVideoHiddenClick();
    }

    // select的change
    function eventOfSelectChange() {
        // var storage = window.localStorage;
        var searchValue = $(".header .bottom-container .right .search-input").val();
        var selectOption = $(".header .bottom-container .right .select").find("option:selected").data("value");
        var configData = {
            keyWords: searchValue
        };
        // storage.setItem("searchVal", searchValue);
        if (keySaveUrl[selectOption]) {
            new NewAjax({
                type: "POST",
                url: keySaveUrl[selectOption],
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(configData),
                success: function (res) {
                    if (res.status === 200) {
                        if (selectOption === "demand") {
                            window.location.href = "/f/demand_hall.html?pc=true&search=" + searchValue;
                            // window.open("/f/demand_hall.html?pc=true&search="+ searchValue);
                        } else if (selectOption === "product") {
                            window.location.href = "/f/case_hall.html?pc=true&search=" + searchValue;
                            // window.open("/f/case_hall.html?pc=true&search="+ searchValue);
                        } else if (selectOption === "company") {
                            window.location.href = "/f/service_provider_lib.html?pc=true&search=" + searchValue;
                            // window.open("/f/service_provider_lib.html?pc=true&search=" + searchValue);
                        } else if (selectOption === "service") {
                            window.location.href = "/f/general_service.html?pc=true&search=" + searchValue;
                            // window.open("/f/general_service.html?pc=true&search="+ searchValue);
                        }
                    }
                },
                error: function (err) {

                }
            });
        } else {
            if (selectOption === "demand") {
                window.location.href = "/f/demand_hall.html?pc=true&search=" + searchValue;
                // window.open('/f/demand_hall.html?pc=true&search='+ searchValue);
            } else if (selectOption === "product") {
                window.location.href = "/f/case_hall.html?pc=true&search=" + searchValue;
                // window.open('/f/case_hall.html?pc=true&search=' + searchValue);
            } else if (selectOption === "company") {
                window.location.href = "/f/service_provider_lib.html?pc=true&search=" + searchValue;
                // window.open('/f/service_provider_lib.html?pc=true&search=' + searchValue);
            } else if (selectOption === "service") {
                window.location.href = "/f/general_service.html?pc=true&search=" + searchValue;
                // window.open('/f/general_service.html?pc=true&search=' + searchValue);
            }
        }
    }

    // 消息点击
    function eventOfMessageLinkClick() {
        var messageLink = $('#head-personal');
        // var messageTip = messageLink.find('span[name="messageNumber"]').eq(0);
        var storage = window.localStorage;
        messageLink.off().click(function () {
            window.location.href = '/f/personal_center.html?pc=true#menu=messageInformList';
            // window.open('/f/personal_center.html?pc=true#menu=messageInformList');
            storage.setItem('check', '0');
            return false;
        })
    }

    /* 直播 */

    // 直播通知窗口的关闭按钮的点击事件
    function eventOfVideoCloseIconClick() {
        var oCloseIconNode = oVideoLiveShowNode.find('.global-video-live-close-div').eq(0);
        oCloseIconNode.off().click(function () {
            oVideoLiveShowNode.hide();
            oVideoLiveHiddenNode.show();
        })
    }

    // 直播通知窗口关闭模式
    function eventOfVideoHiddenClick() {
        oVideoLiveHiddenNode.off().click(function () {
            oVideoLiveShowNode.show();
            oVideoLiveHiddenNode.hide();
        })
    }

    /*=== 功能函数 ===*/
    function goTocenter() {
        window.location.href = "/f/personal_center.html?pc=true";
        // window.open('/f/personal_center.html?pc=true');
    }

    // 禁用flvjs提示
    function closeFlvJsLog() {
        // 禁止flv.js调用log
        if (flvjs) {
            flvjs.LoggingControl.enableAll = false;
        }
    }

    // 获取hot-key
    function getHotKey() {
        var configData = {
            pager: {
                current: 1,
                size: 5
            }
        };
        if (keyRequestUrl[selectOption]) {
            new NewAjax({
                type: "POST",
                url: keyRequestUrl[selectOption],
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(configData),
                success: function (res) {
                    setHotKey(res.data.data_list);
                }
            })
        } else {
            setHotKey([]);
        }
    }

    // 设置hot-key
    function setHotKey(list) {
        // 清空Hot-key
        $(".header .bottom .right .right-bottom").html("<label>热门搜索：</label>");
        for (var i = 0; i < list.length; i++) {
            var spanTag = $('<span class="hot-item"></span>');
            spanTag.html(list[i].key_words);
            if (i % 2 !== 0) {
                spanTag.addClass("hot-active");
            }
            if (i >= 5) break;
            $(".header .bottom .right .right-bottom").append(spanTag);
        }
    }
});





