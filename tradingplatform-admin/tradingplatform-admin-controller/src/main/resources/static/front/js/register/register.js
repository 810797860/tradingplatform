/**
 * Created by mu-HUN on 2018/9/5.
 */
$(function () {
    var $phone = $('#phone');
    var $email = $('#email');
    var $captcha = $('#captcha-input');
    var $userName = $('#userName');
    var $password = $('#password');
    var $passwordCopy = $('#passwordCopy');
    //发送验证码相关
    var $sendBtn = $('.send-message').eq(0);
    var $backHome = $(".get-back-btn");
    var $backLogin = $(".back-to-login").eq(0);
    var $chooseWay = $(".register-way-tab a").eq(1);
    var $registerBtn = $(".register-button").eq(0);
    var registerWay = 1;
    var sendCodeTime = 60;
    var sendCodeTimer = null;
    var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
    // 验证状态
    var registerStatus = {}

    /*=== 函数调用 ===*/
    initStatus();
    $phone.focus();
    $sendBtn.bind('click', sendVerificationCode);
    $backHome.bind('click', getBackHome);
    $backLogin.bind('click', getBackLogin);
    $chooseWay.bind('click', changeRegisterWay);
    $registerBtn.bind('click', formSubmit);
    eventOfPhoneBlur();
    eventOfEmailBlur();
    eventOfPasswordBlur();
    eventOfPasswordCopyBlur();
    eventOfUserNameBlur();
    eventOfCodeBlur();
    eventOfPhoneFocus();
    eventOfEmailFocus();
    eventOfPasswordFocus();
    eventOfPasswordCopyFocus();
    eventOfUserNameFocus();
    eventOfCodeFocus();
    backLogin();
    getIndex();

    // 表单提交
    function formSubmit(e) {
        var isPass = true
        if (registerWay == 1) {// 手机
            registerStatus.email = true
            $.each(Object.keys(registerStatus), function (index, key) {
                if (!registerStatus[key]) {
                    isPass = false
                    return false
                }
            })
            if (isPass) {
                formSubmitPhone(e);
            }
        } else { // 邮箱
            registerStatus.phone = true
            $.each(Object.keys(registerStatus), function (index, key) {
                if (!registerStatus[key]) {
                    isPass = false
                    return false
                }
            })
            if (isPass) {
                formSubmitEmail(e);
            }
        }
    }

    // 返回登录
    function backLogin() {
        $(".register-way-tab .backLogin").off().click(function () {
            window.location.href = '/f/login.html?pc=true';
            // window.open("/f/login.html?pc=true", "_self")
        });
    }

    // 手机注册
    function formSubmitPhone(e) {
        e.stopPropagation()
        e.preventDefault()
        var phoneNum = $phone.val()
        var captcha = $captcha.val()
        var username = $userName.val()
        var password = $password.val()
        var passNum = 1
        if (phoneNum.length === 0) {
            // showMessage('手机号不能为空！', '#account-input', 1);
            $phone.addClass('input-error')
            $phone.next().text('手机号不能为空！').show()
        } else {
            passNum += 1;
        }
        if (captcha.length === 0) {
            // showMessage('验证短信不能为空！', '#captcha-input', 1);
            $captcha.addClass('input-error')
            $captcha.next().text('二维码不能为空！').show()
        } else {
            passNum += 1;
        }
        if (username.length === 0) {
            // showMessage('用户名不能为空！', '#captcha-input', 1);
            $userName.addClass('input-error')
            $userName.next().text('用户不能为空！').show()
        } else {
            passNum += 1;
        }
        if (password.length === 0) {
            // showMessage('用户名不能为空！', '#captcha-input', 1);
            $password.addClass('input-error')
            $password.next().text('密码不能为空！').show()
        } else {
            passNum += 1;
        }
        if (passNum !== Object.keys(registerStatus).length - 1) {
            console.error('我不通过', passNum, Object.keys(registerStatus).length)
            return 0
        }
        var postData = {
            user_name: username,
            phone_num: phoneNum,
            randCode: captcha,
            password: encrypt(password)
        }
        $.ajax({
            type: 'post',
            url: '/f/user/pc/phone_register',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(postData),
            success: function (res) {
                switch (res.status) {
                    case 200:
                        new PNotify({
                            title: '注册',
                            text: '注册成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        $('.register-main-div').hide();
                        $('.register-success').show();
                        break;
                    default:
                        new PNotify({
                            title: '注册',
                            text: res.message,
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        break;
                    /* case 800:
                         new PNotify({
                             title: '注册失败',
                             text: '手机验证码错误',
                             type: 'error',
                             delay: 3000,
                             addclass: "stack-bottomright",
                             stack: stack_bottomright
                         });
                         break;
                     case 402:
                         new PNotify({
                             title: '注册失败',
                             text: '登陆出错',
                             type: 'error',
                             delay: 3000,
                             addclass: "stack-bottomright",
                             stack: stack_bottomright
                         });
                         break;
                     case 405:
                         new PNotify({
                             title: '注册失败',
                             text: '登陆失败，密码错误或用户名错误',
                             type: 'error',
                             delay: 3000,
                             addclass: "stack-bottomright",
                             stack: stack_bottomright
                         });
                         break;
                     case 416:
                         new PNotify({
                             title: '注册失败',
                             text: '登陆失败，密码错误或用户名错误',
                             type: 'error',
                             delay: 3000,
                             addclass: "stack-bottomright",
                             stack: stack_bottomright
                         });
                         break;*/
                }
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
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

    // 邮箱注册
    function formSubmitEmail(e) {
        e.stopPropagation()
        e.preventDefault()
        var email = $email.val()
        var captcha = $captcha.val()
        var username = $userName.val()
        var password = $password.val()
        var passNum = 1
        if (email.length === 0) {
            // showMessage('手机号不能为空！', '#account-input', 1);
            $phone.addClass('input-error')
            $phone.next().text('手机号不能为空！').show()
        } else {
            passNum += 1;
        }
        if (captcha.length === 0) {
            // showMessage('验证短信不能为空！', '#captcha-input', 1);
            $captcha.addClass('input-error')
            $captcha.next().text('二维码不能为空！').show()
        } else {
            passNum += 1;
        }
        if (username.length === 0) {
            // showMessage('用户名不能为空！', '#captcha-input', 1);
            $userName.addClass('input-error')
            $userName.next().text('用户不能为空！').show()
        } else {
            passNum += 1;
        }
        if (password.length === 0) {
            // showMessage('用户名不能为空！', '#captcha-input', 1);
            $password.addClass('input-error')
            $password.next().text('密码不能为空！').show()
        } else {
            passNum += 1;
        }
        if (passNum !== Object.keys(registerStatus).length - 1) {
            console.error('我不通过', passNum, Object.keys(registerStatus).length)
            return 0
        }
        var postData = {
            user_name: username,
            email: email,
            randCode: captcha,
            password: encrypt(password)
        }
        $.ajax({
            type: 'post',
            url: '/f/user/pc/email_register',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(postData),
            success: function (res) {
                switch (res.status) {
                    case 200:
                        new PNotify({
                            title: '注册',
                            text: '注册成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        $('.register-main-div').hide();
                        $('.register-success').show();
                        break;
                    default:
                        new PNotify({
                            title: '注册',
                            text: res.message,
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        break;
                }
            }, error: function (XMLHttpRequest, textStatus, errorThrown) {
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

    // 发送验证码
    function sendVerificationCode(e) {
        // let display = $phone.css("display");
        if (!registerWay) {
            var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            var emailVal = $email.val();
            var flag = emailVal.match(reg)
            var sendMessageBtn = $captcha.next()
            if (emailVal === '') {
                showMessage('邮箱不能为空！', '#email', 1);
                return;
            } else if (!flag) {
                showMessage('邮箱格式不正确！', '#email', 1);
                return;
            }
            if (sendCodeTime === 60) {
                $.ajax({
                    type: 'post',
                    url: '/message/pc/' + emailVal + '/send_mail',
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (data) {
                        new PNotify({
                            title: '发送成功',
                            text: '发送成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });

                        sendCodeTimer = setInterval(function () {
                            sendCodeTime--;
                            if (sendCodeTime < 0) {
                                clearInterval(sendCodeTimer);
                                sendCodeTimer = null;
                                sendCodeTime = 60;
                                sendMessageBtn.html("发送验证码");
                                sendMessageBtn.css("background-color", "#0066cc")
                                sendMessageBtn.css("color", "#fff")
                            } else {
                                sendMessageBtn.html(sendCodeTime);
                                sendMessageBtn.css("background-color", "#fff")
                                sendMessageBtn.css("color", "#dcdcdc")
                            }
                        }, 1000)
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
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
        } else {
            var phoneNum = $phone.val();
            if (phoneNum === '') {
                //手机号码判空
                showMessage('手机号不能为空！', '#phone', 1);
                return;
            }
            if (sendCodeTime === 60) {
                $.ajax({
                    type: 'post',
                    url: '/message/pc/' + phoneNum + '/send_sms',
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (data) {
                        new PNotify({
                            title: '发送成功',
                            text: '发送成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        sendCodeTimer = setInterval(function () {
                            sendCodeTime--;
                            if (sendCodeTime < 0) {
                                clearInterval(sendCodeTimer);
                                sendCodeTimer = null;
                                sendCodeTime = 60;
                                $(".send-message").html("发送验证码");
                                $(".send-message").css("background-color", "#0066cc")
                                $(".send-message").css("color", "#fff")
                            } else {
                                $(".send-message").html(sendCodeTime);
                                $(".send-message").css("background-color", "#fff")
                                $(".send-message").css("color", "#dcdcdc")
                            }
                        }, 1000)
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
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
    }

    // 消息弹框
    function showMessage(msg, selector, type) {
        layer.tips(msg, selector, {
            tips: type
        });
    }

    // 修改注册模式
    function changeRegisterWay() {
        if (!registerWay) { // 手机
            $('.user-email').hide();
            $('.user-phone').show();
            $userName.val('')
            $phone.val('').focus()
            $password.val('')
            $passwordCopy.val('')
            $chooseWay.html('邮箱注册 >');
            if (sendCodeTime > 0 && sendCodeTime < 60) {
                sendCodeTime = -1
            }
            registerWay = 1;
        } else { // 邮箱
            $('.user-email').show();
            $('.user-phone').hide();
            $userName.val('')
            $email.val('').focus()
            $password.val('')
            $passwordCopy.val('')
            $chooseWay.html('手机注册 >');
            if (sendCodeTime > 0 && sendCodeTime < 60) {
                sendCodeTime = -1
            }
            registerWay = 0;
        }
        captchaBtnAble(false)
    }

    // 点击图片返回首页
    function getIndex() {
        $('.register-head-left').click(function () {
            window.location.href = '/f/wu.html?pc=true';
            // window.open('/f/wu.html?pc=true', '_self');
        })
    }

    // 返回首页
    function getBackHome() {
        window.location.href = '/f/wu.html?pc=true'
    }

    // 返回登录页面
    function getBackLogin() {
        window.location.href = '/f/login.html?pc=true'
    }

    /*=== 点击事件监听 ===*/

    // 手机号
    function eventOfPhoneBlur() {
        $phone.blur(function () {
            phoneBlur()
        })
    }

    // 邮箱
    function eventOfEmailBlur() {
        $email.blur(function () {
            emailBlur()
        })
    }

    // 密码
    function eventOfPasswordBlur() {
        $password.blur(function () {
            passwordBlur()
        })
    }

    // 重复密码
    function eventOfPasswordCopyBlur() {
        $passwordCopy.blur(function () {
            passwordCopyBlur()
        })
    }

    // 用户名
    function eventOfUserNameBlur() {
        $userName.blur(function () {
            userNameBlur()
        })
    }

    // 验证码
    function eventOfCodeBlur() {
        $captcha.blur(function () {
            captchaBlur()
        })
    }

    // 手机号
    function eventOfPhoneFocus() {
        $phone.focus(function () {
            $phone.removeClass('input-error')
            $phone.next().text('').hide()
        })
    }

    // 邮箱
    function eventOfEmailFocus() {
        $email.focus(function () {
            $email.removeClass('input-error')
            $email.next().text('').hide()
        })
    }

    // 密码
    function eventOfPasswordFocus() {
        $password.focus(function () {
            $password.removeClass('input-error')
            $password.next().text('').hide()
        })
    }

    // 重复密码
    function eventOfPasswordCopyFocus() {
        $passwordCopy.focus(function () {
            $passwordCopy.removeClass('input-error')
            $passwordCopy.next().text('').hide()
        })
    }

    // 用户名
    function eventOfUserNameFocus() {
        $userName.focus(function () {
            $userName.removeClass('input-error')
            $userName.next().text('').hide()
        })
    }

    // 验证码
    function eventOfCodeFocus() {
        $captcha.focus(function () {
            $captcha.removeClass('input-error')
            $captcha.next().next().text('').hide()
        })
    }

    /*=== 功能函数 ===*/

    // 初始化验证状态
    function initStatus() {
        registerStatus.phone = false;
        registerStatus.email = false;
        registerStatus.password = false;
        registerStatus.passwordCopy = false;
        registerStatus.name = false;
        registerStatus.code = false;
    }

    // 验证手机/邮箱是否已被注册
    function testAccount(account, callback) {
        new NewAjax({
            url: '/f/user/verify_account?pc=true',
            contentType: 'application/json',
            type: 'post',
            data: JSON.stringify(account),
            success: function (res) {
                if (callback) {
                    callback(res)
                }
            },
            error: function (err) {
                console.error('检测账号：' + err)
            }
        })
    }

    // 验证码按钮禁用
    function captchaBtnAble(enable) {
        console.log('禁用验证码', $sendBtn)
        // 激活
        if (enable) {
            $sendBtn.removeAttr('disabled')
        } else {// 禁用
            $sendBtn.attr('disabled', 'disabled')

        }
    }

    // 手机号的失焦函数
    function phoneBlur() {
        var value = $phone.val()
        var rule = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/
        var phoneTip = $phone.next()
        if (value.length > 0) {
            if (rule.test(value)) {
                testAccount({
                    type: 202081,
                    account: value
                }, function (status) {
                    // 已被注册
                    if (status) {
                        phoneTip.text('该手机已注册').show()
                        $phone.addClass('input-error')
                        registerStatus.phone = false;
                        captchaBtnAble(false)
                    } else {
                        //未注册
                        phoneTip.text('').hide()
                        $phone.removeClass('input-error')
                        registerStatus.phone = true;
                        captchaBtnAble(true)
                    }
                })
            } else {
                $phone.addClass('input-error')
                phoneTip.text('您输入的手机号有误，请重新输入').show()
                registerStatus.phone = false;
                captchaBtnAble(false)
            }
        } else {
            registerStatus.phone = false;
            captchaBtnAble(false)
        }
    }

    // 邮箱的失焦函数
    function emailBlur() {
        var value = $email.val()
        var rule = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
        var emailTip = $email.next()
        if (value.length > 0) {
            if (rule.test(value)) {
                testAccount({
                    type: 202082,
                    account: value
                }, function (status) {
                    // 已被注册
                    if (status) {
                        emailTip.text('该邮箱已注册').show()
                        $email.addClass('input-error')
                        registerStatus.email = false;
                        captchaBtnAble(false)
                    } else {
                        //未注册
                        emailTip.text('').hide()
                        $email.removeClass('input-error')
                        registerStatus.email = true;
                        captchaBtnAble(true)
                    }
                })
            } else {
                $email.addClass('input-error')
                emailTip.text('您输入的邮箱有误，请重新输入').show()
                registerStatus.email = false;
                captchaBtnAble(false)
            }
        } else {
            registerStatus.email = false;
            captchaBtnAble(false)
        }
    }

    // 密码的失焦函数
    function passwordBlur() {
        // 密码
        var value = $password.val();
        // 正则
        var rule = /<[^>]*\/?>/;
        // 密码提示
        var passwordTip = $password.next()
        if (value.length > 0) {
            if (rule.test(value)) {
                $password.addClass('input-error')
                passwordTip.text('您出现<...>等非法输入，请重新输入').show()
                registerStatus.password = false;
            } else {
                $password.removeClass('input-error')
                passwordTip.text('').hide()
                registerStatus.password = true;
                // 调用重复面的验证
                passwordCopyBlur()
            }
        } else {
            registerStatus.password = false;
        }
    }

    // 重复密码的失焦函数
    function passwordCopyBlur() {
        // 重复密码
        var value = $passwordCopy.val()
        // 正则
        var rule = /<[^>]*\/?>/;
        // 重复密码提示
        var passwordCopyTip = $passwordCopy.next()
        // 密码
        var password = null
        if (value.length > 0) {
            if (rule.test(value)) {
                $passwordCopy.addClass('input-error')
                passwordCopyTip.text('您出现<...>等非法输入，请重新输入').show()
                registerStatus.passwordCopy = false;
            } else if ($password.val().length > 0) {
                password = $password.val()
                if (value === password) {
                    $passwordCopy.removeClass('input-error')
                    passwordCopyTip.text('').hide()
                    registerStatus.passwordCopy = true;
                } else {
                    $passwordCopy.addClass('input-error')
                    passwordCopyTip.text('您两次输入的密码不一致，请重新输入').show()
                    registerStatus.passwordCopy = false;
                }
            } else {
                $passwordCopy.removeClass('input-error')
                passwordCopyTip.text('').hide()
                registerStatus.passwordCopy = false;
            }
        } else {
            registerStatus.passwordCopy = false;
        }
    }

    // 昵称失焦
    function userNameBlur() {
        // 昵称
        var value = $userName.val()
        // 正则
        var rule = /<[^>]*\/?>/;
        // 昵称提示
        var userNameTip = $userName.next()
        if (value.length > 0) {
            if (rule.test(value)) {
                $userName.addClass('input-error')
                userNameTip.text('您出现<...>等非法输入，请重新输入').show()
                registerStatus.name = false;
            } else {
                $userName.removeClass('input-error')
                userNameTip.text('').hide()
                registerStatus.name = true;
            }
        } else {
            registerStatus.name = false;
        }
    }

    // 验证码失焦验证
    function captchaBlur() {
        // 验证码
        var value = $captcha.val()
        // 正则
        var rule = /<[^>]*\/?>/;
        // 昵称提示
        var captchaTip = $captcha.next().next()
        if (value.length > 0) {
            if (rule.test(value)) {
                $captcha.addClass('input-error')
                captchaTip.text('您出现<...>等非法输入，请重新输入').show()
                registerStatus.code = false;
            } else {
                $captcha.removeClass('input-error')
                captchaTip.text('').hide()
                registerStatus.code = true;
            }
        } else {
            registerStatus.code = false;
        }
    }
})

