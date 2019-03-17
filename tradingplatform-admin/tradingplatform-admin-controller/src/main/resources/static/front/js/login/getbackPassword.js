/**
 * Created by mu-HUN on 2018/9/6.
 */

$(function () {
    var $captcha = $('#captcha-img');
    var $captchaInput = $('#captcha-input');
    var $captchaInputCode = $('#verification-code-input');
    var $account = $('#account');
    var $nextStep = $('#next-step');
    var $backHome = $(".get-back-btn");
    var $submitPassword = $('#submitPassword');
    var $password = $('#password');
    var $again = $('#again');
    var $sendVerification = $('.send-verification');
    var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
    var time = 60;

    var emailReg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
    var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/


    $('.login-head-left').click(function () {
        window.open('/f/wu.html?pc=true', '_self');
    });

    $('#account').blur(function () {
        var account = $account.val();

        var str = account.match(emailReg);
        var str1 = account.match(phoneReg);
        // var captcha = $captchaInput.val();
        // var emailReg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
        // var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/
        // var str = account.match(emailReg);
        // var str1 = account.match(phoneReg);
        console.log(str)
        console.log(str1)
        console.log((str || str1))
        if (account === '') {
            showMessage('账号/邮箱/手机号码不能为空！', '#account', 1);
        } else if ((str || str1) == null) {
            $('.no-send-code').show();
            $('.verificationCode').hide();
        } else {
            $('.verificationCode').show();
            $('.no-send-code').hide();
        }
    })

    $sendVerification.click(function () {
        var url;
        var str = $account.val().match(emailReg);
        var str1 = $account.val().match(phoneReg);
        if (str) {
            url = '/message/pc/' + $account.val() + '/send_mail?pc=true';
        } else if (str1) {
            url = '/message/pc/' + $account.val() + '/send_sms?pc=true';
        }
        new NewAjax({
            url: url,
            contentType: 'application/json',
            type: 'POST',
            success: function (res) {
                console.log(res);
            },
            error: function (err) {
                console.error(err);
            }
        })
        var sendCodeTimer = setInterval(function () {
            if (time > 0) {
                time--;
                $('.send-verification').text(time);
                $('.send-verification').attr('disabled', true);
                $('.send-verification').css('backgroundColor', '#dcdcdc').css('cursor', 'not-allowed');
            } else {
                clearInterval(sendCodeTimer);
                time = 60;
                $('.send-verification').text('发送验证码');
                $('.send-verification').attr('disabled', false);
                $('.send-verification').css('backgroundColor', '#0066cc').css('cursor', 'pointer');
            }
        }, 1000);
    });


    $account.focus();
    $captcha.bind('click', captchaClick);
    $nextStep.bind('click', validateAccount);
    $backHome.bind('click', getBackHome);
    $submitPassword.bind('click', submitPassword);

    function submitPassword() {
        var password = $password.val();
        var again = $again.val();
        if (password === '') {
            showMessage('重置密码不能为空！', '#password', 1);
            return;
        } else if (again === '') {
            showMessage('请再输入一次密码', '#password', 1);
            return;
        }
        if (password.length < 6) {
            showMessage('密码长度不能少于6位！', '#password', 1);
            return;
        }
        if (password != again) {
            layer.msg('两次密码输入不相同');
            $again.val('');
            return;
        }
        var postData = {
            password: encrypt(password)
        }
        $.ajax({
            type: 'post',
            url: '/pc/forgot_password/reset_password',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(postData),
            success: function (res) {
                new PNotify({
                    title: '密码修改',
                    text: '密码修改成功',
                    type: 'success',
                    delay: 3000,
                    addclass: "stack-bottomright",
                    stack: stack_bottomright
                });
                $('.getBack-step-list li').removeClass('active');
                $('.getBack-step-list li').eq(2).addClass('active');
                $('.getBack-main-div').hide();
                $('.getBack-success').show();
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

    //验证账号
    function validateAccount() {
        var account = $account.val();
        var captcha = $captchaInput.val();
        var captchaInputCode = $captchaInputCode.val();
        console.log(captchaInputCode);
        if (account === '') {
            showMessage('邮箱/手机号码不能为空！', '#account', 1);
            return;
        } else if (captcha === '' && captchaInputCode === '') {
            console.log(captcha);
            console.log(captchaInputCode);
            showMessage('验证码不能为空！', '#account', 1);
            return;
        }
        var emailReg = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
        var phoneReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/
        var type = 0
        if (emailReg.test(account)) {
            type = 202082
        } else if (phoneReg.test(account)) {
            type = 202081
        } else {
            type = 202083
        }
        var postData = {
            type: type,
            account: account,
            captcha: captcha ? captcha : captchaInputCode
        }
        $.ajax({
            type: 'post',
            url: '/pc/forgot_password/verify_account',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(postData),
            dataType: 'json',
            success: function (res) {
                switch (res.status) {
                    case 200:
                        $('.verificationCode').hide();
                        new PNotify({
                            title: '账号验证',
                            text: '验证成功',
                            type: 'success',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        passwordRebuild();
                        break;
                    case 414:
                        new PNotify({
                            title: '验证失败',
                            text: '用户不存在',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        showMessage(res.message, '#account', 1);
                        $captcha.trigger('click');
                        break;
                    case 310:
                        new PNotify({
                            title: '验证失败',
                            text: '验证码错误',
                            type: 'error',
                            delay: 3000,
                            addclass: "stack-bottomright",
                            stack: stack_bottomright
                        });
                        showMessage(res.message, '#captcha-input', 1);
                        $captcha.trigger('click');
                        break;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                new PNotify({
                    title: '验证失败',
                    text: '服务器内部出错，或网络出错！',
                    type: 'error',
                    delay: 3000,
                    addclass: "stack-bottomright",
                    stack: stack_bottomright
                });
            }
        })
    }

    //切换验证码
    function captchaClick() {
        var img = $('#captcha-img');
        img.attr('src', img.attr('src'));
    }

    //消息弹框
    function showMessage(msg, selector, type) {
        layer.tips(msg, selector, {
            tips: type
        });
    }

    //跳转到密码重置
    function passwordRebuild() {
        $('.account').hide();
        $('.verification-input').hide();
        $('#next-step').hide();
        $('.user-password').show();
        $('#password').focus();
        $('#submitPassword').show();
        $('.getBack-step-list li').removeClass('active');
        $('.getBack-step-list li').eq(1).addClass('active');
    }

    // 密码加密
    function passwordEncrypt(password) {
        return hex_md5(encrypt(password));
    }

    // 返回首页
    function getBackHome() {
        window.location.href = '/f/wu.html?pc=true'
    }
})
