/**
 * Created by 空 on 2018/11/23.
 */
// var $loginFrame = $('#login-frame');

// 清空用户信息
(function () {
    // 检测网页的打开状态（新开/刷新）
    windowOpenStatus(function () {
        if (window.localStorage === undefined) {
            console.error('当前浏览器版本不兼容localStorage');
            return 0
        }
        saveToLocalStorage('user', null);
        saveToLocalStorage('serviceName', null);
        saveToLocalStorage('check', '0');
        // 网页计数
        saveNewWindowNumber(true);
    });
    // 关闭页面监听
    windowBeforeUnload(function () {
        saveNewWindowNumber(false);
    })

})();

// 绑定事件
$(function () {
    // 登录body区域
    var loginArea = $('.login-area').eq(0);
    var $accountInput = $('#account-input');
    var $passwordInput = $('#password-input');
    var $captchaInput = $('#captcha-input');
    var $captcha = $('#captcha-img,#captcha-tip');
    var $loginBtn = $('#login-btn');
    var $backToIndex = $(".back-index").eq(0);
    var $rememberPasswordButton = $(".remember-password-button").eq(0);
    var $tabActive = 0;
    // 发送验证码
    var $sendBtn = $('.send-message').eq(0);
    // 验证码图片
    var $codeImg = $('#captcha-img');
    var sendCodeTime = 60;
    var sendCodeTimer = null;
    // 手机正则
    var loginPhoneRegExp = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    // 邮箱正则
    var loginEmailRegExp = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    // 消息框位置控制
    var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};

    /* 重新加密 */
    // 防抖变量
    var isSubmitLogin = true;
    // 加载框
    var oLoadingNode = $('.login-loading-div').eq(0);

    /*=== 函数调用 ===*/
    init_dom();
    loginArea.css({
        height: window.innerHeight + 'px'
    });
    $accountInput.bind('keydown', enterSubmit);
    $passwordInput.bind('keydown', enterSubmit);
    $captchaInput.bind('keydown', enterSubmit);
    $captcha.bind('click', captchaClick);
    $loginBtn.bind('click', formSubmit);
    $sendBtn.bind('click', sendVerificationCode);
    $backToIndex.bind('click', backToIndex);
    $rememberPasswordButton.bind('click', rememberPassword);
    handelClick();

    $('.username-input #account-input').blur(function () {
        var account = $accountInput.val();
        // 判空
        var typeId = $('#account-input').attr('typeId')
        if (!!typeId) {
            if (typeId == 1) {
                if (account === '') {
                    showMessage('手机号不能为空！', '#account-input', 1);
                    return;
                }
                // var loginPhoneRegExp = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
                var str = account.match(loginPhoneRegExp);
                if (str === null) {
                    showMessage('请输入正确手机号！', '#account-input', 1);
                    return;
                }
            }
        }
    });

    // 初始化dom 结构
    function init_dom() {
        $accountInput.val("").focus();
        $passwordInput.val("");
        $accountInput.val("");
        $captchaInput.val("");

        // 验证码刷新
        $('#captcha-img').attr('src', $('#captcha-img').attr('src'));

        // 读取cookie中的账号密码
        var username = $.cookie('@kgbasd&%');
        var password = $.cookie('$7asd*%');
        //将获取的值填充入输入框中
        $accountInput.val(username);
        $passwordInput.val(password);
        if(username != null && username != '' && password != null && password != '') {
            //选中保存秘密的复选框
            $rememberPasswordButton.attr('checked', true);
        }

    }
    // 记住密码
    function rememberPassword() {
        if ($rememberPasswordButton[0].checked) {
            //存储一个带7天期限的cookie
            $.cookie("@kgbasd&%", $accountInput.val(), { expires: 7 });
            $.cookie("$7asd*%", $passwordInput.val(), { expires: 7 });
        }
        else {
            $.cookie("@kgbasd&%", "", { expires: -1 });
            $.cookie("$7asd*%", "", { expires: -1 });
        }
    }
    // 返回首页
    function backToIndex() {
        window.location.href = '/f/wu.html?pc=true';
    }
    // 点击事件
    function handelClick() {
        $(".tab-style").on("click", function () {
            $(this).addClass("tab-style-active").siblings().removeClass("tab-style-active");
            // 初始化输入框
            init_dom();
            if ($(this)[0].innerText === "手机快速登录") {
                $(".username").attr("placeholder","请输入手机号码");
                $(".username").attr("typeId",1);
                $(".password-input").css("display","none");
                $("#captcha-img").css("display","none").attr("placeholder","短信验证码");
                $(".send-message").css("display","inline-block");
                $tabActive = 1;
                // console.log(11);
            } else {
                $(".username").attr("placeholder","请输入账号/手机号/邮箱");
                $(".username").attr("typeId",0);
                $(".password-input").css("display","inline-block");
                $("#captcha-img").css("display","inline").attr("placeholder","请输验证码");
                $(".send-message").css("display","none");
                $tabActive = 0;
            }
            // eventOfCodeImageClick();
            /* else if ($(this)[0].innerText === "邮箱登录") {
             $(".username").attr("placeholder","请输入邮箱");
             $(".password-input").css("display","inline-block");
             $("#captcha-img").css("display","inline").attr("placeholder","请输验证码");
             $(".send-message").css("display","none");
             $tabActive = 2;
             // console.log(22);
             } else {
             $(".username").attr("placeholder","请输入账号");
             $(".password-input").css("display","inline-block");
             $("#captcha-img").css("display","inline").attr("placeholder","请输验证码");
             $(".send-message").css("display","none");

             // console.log(33);
             }*/
            // getServiceList($_typeId, $("#search-input").val());
            // getServiceList();
        })

        $('.login-head-left').click(function () {
            window.open('/f/wu.html?pc=true', '_self');
        })
    }
    // 发送验证码
    function sendVerificationCode(e) {
        e.stopPropagation();
        e.preventDefault();
        var selfArguments = arguments;
        var account = $accountInput.val();
        // 判空
        if(account === '') {
            showMessage('手机号不能为空！', '#account-input', 1);
            return;
        }
        // var loginPhoneRegExp = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
        var str = account.match(loginPhoneRegExp);
        if(str === null) {
            showMessage('请输入正确手机号！', '#account-input', 1);
            return;
        }
        if(sendCodeTime === 60) {
            $.ajax({
                type:'post',
                url:'/message/pc/'+ account + '/send_sms',
                contentType:'application/json;charset=utf-8',
                dataType:'json',
                // data: JSON.stringify(postData),
                success:function(data){
                    new PNotify({
                        title: '发送成功',
                        text: '发送成功',
                        type: 'success',
                        delay: 3000,
                        addclass: "stack-bottomright",
                        stack: stack_bottomright
                    });
                    sendCodeTimer = setInterval(function(){
                        sendCodeTime--;
                        if (sendCodeTime < 0) {
                            clearInterval(sendCodeTimer);
                            sendCodeTimer = null;
                            sendCodeTime = 60;
                            $(".send-message").html("发送验证码");
                            $(".send-message").css("background-color", "#0066cc");
                            $(".send-message").css("color", "#fff");
                            $(".send-message").css("cursor", "pointer");
                            $(".send-message").attr('disabled',false);
                        } else {
                            $(".send-message").html(sendCodeTime);
                            $(".send-message").css("background-color", "#fff");
                            $(".send-message").css("color", "#dcdcdc");
                            $(".send-message").css("cursor", "no-allowed");
                            $(".send-message").attr('disabled','disabled');
                        }
                    }, 1000)
                },error:function(XMLHttpRequest, textStatus, errorThrown){
                    new PNotify({
                        title: '登录失败',
                        text: '服务器内部出错，或网络出错！',
                        type: 'error',
                        delay: 3000,
                        addclass: "stack-bottomright",
                        stack: stack_bottomright
                    });
                }
            })
        }
    }
    // 按键监听执行函数
    function enterSubmit(ev) {
        if(ev.keyCode == 13){
            formSubmit(ev);
        }
    }
    // 验证码回调函数
    function captchaClick() {
        var img = $('#captcha-img');
        var url = img.attr('src');
        if (url.indexOf('?index=') > -1) {
            url = url.replace(/index=.+$/, 'index=' + new Date().getTime());
        } else {
            url += '?index=' + new Date().getTime();
        }
        img.attr('src', url);
    }
    // 提交函数
    function formSubmit(e) {
        if($tabActive == 1){
            formSubmitPhone(e);
        } else {
            formSubmitAccount(e);
        }
    }
    // 账户登陆
    function formSubmitAccount(e) {
        if (!isSubmitLogin) {
            // 防止重复登录
            return 0;
        }
        isSubmitLogin = false;
        e && e.stopPropagation();
        e && e.preventDefault();
        var account = $accountInput.val();
        var password = $passwordInput.val();
        var captcha = $captchaInput.val();
        // 判空
        if(account === '') {
            showMessage('手机号/邮箱不能为空！', '#account-input', 1);
            isSubmitLogin = true;
            return;
        } else if(password === '') {
            showMessage('密码不能为空！', '#password-input', 1);
            isSubmitLogin = true;
            return;
        } else if(captcha === '') {
            showMessage('验证码不能为空！', '#captcha-input', 1);
            isSubmitLogin = true;
            return;
        }
        var postData = {
            account: account,
            password: passwordEncrypt(password),
            password_des: passwordEncryptDes(password),
            captcha: captcha
        };
        if (loginPhoneRegExp.test(account)) {
            postData.type = 202081
        } else if(loginEmailRegExp.test(account)){
            postData.type = 202082
        } else {
            postData.type = 202083
        }
        if (oLoadingNode.is(':hidden')) {
            oLoadingNode.show();
        }
        $.ajax({
            type:'post',
            url:'/pc/account_login',
            contentType:'application/json;charset=utf-8',
            dataType:'json',
            data: JSON.stringify(postData),
            success:function(res){
                isSubmitLogin = true;
                switch(res.status){
                    case 200:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录',
                            text: '登录成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        saveUserInfo(res.data.data_object)
                        window.location.href = '/f/wu.html?pc=true';
                        break;
                    case 310:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '验证码错误或已过期',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        $captchaInput.val("");
                        captchaClick();
                        break;
                    case 405:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '密码错误或账号错误',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        $captchaInput.val("");
                        captchaClick();
                        break;
                    case 402:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '登录出错',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        $captchaInput.val("");
                        captchaClick();
                        break;
                    case 415:
                        updateEncrypt(function () {
                            formSubmitAccount(e);
                        });
                        /*$.getScript("/encrypt/javascript",function(){  //加载encrypt.js,成功后，并执行回调函数
                            selfArguments.callee();
                        });*/
                        break;
                }
            },error:function(XMLHttpRequest, textStatus, errorThrown){
                isSubmitLogin = true;
                oLoadingNode.hide();
                new PNotify({
                    title: '登录失败',
                    text: '服务器内部出错，或网络出错！',
                    type: 'error',
                    delay: 3000,
                    addclass: "stack-bottomright",
                    stack: stack_bottomright
                });
            }
        });
    }
    // 手机登陆
    function formSubmitPhone(e) {
        if (!isSubmitLogin) {
            // 防止重复登录
            return 0;
        }
        isSubmitLogin = false;
        e && e.stopPropagation();
        e && e.preventDefault();
        var account = $accountInput.val();
        var captcha = $captchaInput.val();
        // 判空
        if(account === '') {
            showMessage('手机号不能为空！', '#account-input', 1);
            isSubmitLogin = true;
            return;
        } else if(captcha === '') {
            showMessage('验证短信不能为空！', '#captcha-input', 1);
            isSubmitLogin = true;
            return;
        }
        var postData = {
            phone_num: account,
            randCode: captcha
        };
        if (oLoadingNode.is(':hidden')) {
            oLoadingNode.show();
        }
        $.ajax({
            type:'post',
            url:'/pc/phone_login',
            contentType:'application/json;charset=utf-8',
            dataType:'json',
            data: JSON.stringify(postData),
            success:function(res){
                isSubmitLogin = true;
                switch(res.status){
                    case 200:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录',
                            text: '登录成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        saveUserInfo(res.data.data_object);
                        window.location.href = '/f/wu.html?pc=true';
                        break;
                    case 800:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '手机验证码错误',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        break;
                    case 402:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '登陆出错',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        break;
                    case 405:
                        oLoadingNode.hide();
                        new PNotify({
                            title: '登录失败',
                            text: '登陆失败，密码错误或用户名错误',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        break;
                    case 415:
                        updateEncrypt(function () {
                            formSubmitPhone(e);
                        });
                        /*$.getScript("/encrypt/javascript",function(){  //加载encrypt.js,成功后，并执行回调函数
                            selfArguments.callee();
                        });*/
                        break;
                }
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
                isSubmitLogin = true;
                oLoadingNode.hide();
                new PNotify({
                    title: '登录失败',
                    text: '服务器内部出错，或网络出错！',
                    type: 'error',
                    delay: 3000,
                    addclass: "stack-bottomright",
                    stack: stack_bottomright
                });
            }
        });
    }
    // 密码加密
    function passwordEncrypt(password) {
        return hex_md5(encrypt(password));
    }
    function passwordEncryptDes(password) {
        return encrypt(password);
    }
    // 不能为空的提示
    function showMessage(msg, selector, type) {
        layer.tips(msg, selector, {
            tips: type
        });
    }
    // 存储localStorage
    function saveUserInfo(data) {
        var storage = window.localStorage;
        var userInfo = {};
        userInfo.userName = data.user_name;
        userInfo.id = data.id;
        userInfo.phone = data.phone_num;
        userInfo.email = data.email;
        userInfo.avatar = (data.avatar !== undefined && data.avatar !== null) ? JSON.parse(data.avatar).id : undefined;
        userInfo.account = data.account;
        userInfo.study = (data.professional_field !== undefined && data.professional_field !== null) ? JSON.parse(data.professional_field) : {};
        userInfo.address = data.address;
        userInfo.birthday = data.birthday;
        userInfo.detailedAddress = data.detailed_address;
        userInfo.cityName = data.city_name;
        userInfo.districtName = data.district_name;
        userInfo.provinceName = data.province_name;
        userInfo.selfStatement = data.self_statement;
        userInfo.dataCompleteness = data.data_completeness;
        userInfo.verificationType = data.verification_type
        // 记录数据
        storage.setItem('user', JSON.stringify(userInfo));
        storage.setItem('check', (data.notReadCount !== null) ? String(data.notReadCount) : '0');
    }
});
// 更新登录凭证
function updateEncrypt(callback) {
    var encrypt = document.getElementById('encrypt');
    var head = document.getElementsByTagName('head')[0];
    var body = document.getElementsByTagName('body')[0];
    var newEncrypt = document.createElement('script');
    newEncrypt.id = encrypt.id;
    new NewAjax({
        url: '/encrypt/javascript',
        type: 'GET',
        cache: false,
        contentType: 'application/json;charset=utf-8',
        dataType: 'script',
        success: function (res) {
            newEncrypt.innerHTML = res;
            head.removeChild(encrypt);
            body.appendChild(newEncrypt);
            if (callback) {
                callback();
            }
        }
    })
}