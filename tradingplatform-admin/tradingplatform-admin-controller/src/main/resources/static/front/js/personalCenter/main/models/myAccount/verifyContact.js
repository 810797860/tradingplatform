var oVerifyContact = new VerifyContact();

oVerifyContact.initDataInVerifyContact();
oVerifyContact.handleEventInVerifyContact();

function VerifyContact () {
    var self = this;
    var verifyStepInVerifyContact = 1;
    var sendCodeTime = 60;
    var sendCodeTimer = null;
    var oTitleNode = $('.verify-contact .verify-title').eq(0);
    var oBackBtn = $(".verify-contact .back-button").eq(0);
    var oSelectArea = $(".verify-contact .select-verify-way-area").eq(0);
    var oPhoneArea = $(".verify-contact .verify-form-phone-area").eq(0);
    var oEmailArea = $(".verify-contact .verify-form-email-area").eq(0);
    // 手机正则
    var phoneRegExp = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    var oPhoneInputNode = $('.verify-form-phone-area .verify-phone').eq(0);
    // 邮箱正则
    var emailRegExp = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    var oEmailInputNode = $('.verify-email-area .verify-email').eq(0);
    var isInputChangeEmail = false;
    var isInputChangePhoneCode = false;

    self.clear = function () {
        verifyStepInVerifyContact = 1;
        sendCodeTime = 60;
        clearInterval(sendCodeTimer);
        sendCodeTimer = null;
        $(".verify-contact .get-code-button").html("发送验证码");
        $(".verify-contact .error-info-content").css("display", 'none');
        $(".verify-contact input").removeClass('error-border');
    };

    // 初始化数据
    self.initDataInVerifyContact = function () {
        self.clear();
        oTitleNode.html("修改联系方式");
        oSelectArea.css("display", "block");
        oPhoneArea.css("display", "none");
        oEmailArea.css("display", "none");
        oBackBtn.css("display", "none");

        $(".verify-contact input").val("");
    };

    self.initDomInVerifyContact = function (which) {
        self.clear();
        var localStorage = window.localStorage;
        var user = JSON.parse(localStorage.getItem("user"));
        var phone = user.phone;
        var email = user.email;
        if (which === 'phone') {
            if (phone) {
                oTitleNode.html("验证手机号");
                oPhoneInputNode.val(phone);
                verifyStepInVerifyContact = 1;
            } else {
                verifyStepInVerifyContact = 2;
                oTitleNode.html("绑定新手机号");
                oPhoneInputNode.attr("disabled", false).prev().html("手机号");
                $(".verify-contact .verify-form-phone-area .verify-submit").html("绑定");
            }
        } else if (which === 'email') {
            if (email) {
                oTitleNode.html("验证邮箱");
                oEmailInputNode.val(email);
                verifyStepInVerifyContact = 1;
            } else {
                verifyStepInVerifyContact = 2;
                oTitleNode.html("绑定新邮箱");
                oEmailInputNode.attr("disabled", false).prev().html("邮箱");
                $(".verify-contact .verify-form-email-area .verify-submit").html("绑定");
            }
        }
    };

    // 处理事件
    self.handleEventInVerifyContact = function () {
        /*******  验证手机号 *******/
        // 点击验证手机号的立即验证
        $(".verify-contact .verify-by-phone .verify-button").off().on("click", function () {
            self.initDomInVerifyContact('phone');
            oBackBtn.css("display", "inline-block");
            oSelectArea.css("display", "none");
            oPhoneArea.css("display", "block");
        });
        // 点击获取手机验证码
        $(".verify-contact .verify-form-phone-area .get-code-button").off().on("click", function () {
            if (sendCodeTime === 60) {
                self.getPhoneCode(oPhoneInputNode.val())
            }
        });
        // 点击验证手机号的下一步
        $(".verify-contact .verify-form-phone-area .verify-submit").off().on("click", function () {
            if (sendCodeTime > 0) {
                if (parseInt(verifyStepInVerifyContact) === 1) {
                    self.verifyPhone(oPhoneInputNode.val(), $(".verify-contact .verify-form-phone-area .input-code").val());
                } else if (parseInt(verifyStepInVerifyContact) === 2) {
                    self.resetPhone(oPhoneInputNode.val(), $(".verify-contact .verify-form-phone-area .input-code").val());
                }
            } else {
                layer.msg("验证码已过期，请重新获取");
            }
        });

        oPhoneInputNode.on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).next().children(".error-info-content").text("手机号不能为空！")
                $(this).next().children(".error-info-content").css("display", 'block');
            } else if (!phoneRegExp.test($(this).val())) {
                $(this).addClass("error-border");
                $(this).next().children(".error-info-content").text("手机号格式不正确！")
                $(this).next().children(".error-info-content").css("display", 'block');
            }
        });
        oPhoneInputNode.on("focus", function () {
            $(this).removeClass("error-border");
            $(this).next().children(".error-info-content").css("display", 'none');
        });

        $(".verify-contact .verify-form-phone-area .input-code").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).next().next().children(".error-info-content").text("验证码不能为空！")
                $(this).next().next().children(".error-info-content").css("display", 'block');
            }
        });
        $(".verify-contact .verify-form-phone-area .input-code").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).next().next().children(".error-info-content").css("display", 'none');
        });

        /*******  验证邮箱 *******/
        // 点击验证邮箱的立即验证
        $(".verify-contact .verify-by-email .verify-button").off().on("click", function () {
            self.initDomInVerifyContact('email');
            oBackBtn.css("display", "inline-block");
            oSelectArea.css("display", "none");
            oEmailArea.css("display", "block");
        });
        // 点击获取邮箱验证码
        $(".verify-contact .verify-form-email-area .get-code-button").off().on("click", function () {
            if (sendCodeTime === 60) {
                self.getEmailCode(oEmailInputNode.val());
            }
        });
        // 点击验证邮箱的下一步
        $(".verify-contact .verify-form-email-area .verify-submit").off().on("click", function () {
            if (sendCodeTime > 0) {
                if (parseInt(verifyStepInVerifyContact) === 1) {
                    self.verifyEmail(oEmailInputNode.val(), $(".verify-contact .verify-form-email-area .input-code").val());
                } else if (parseInt(verifyStepInVerifyContact) === 2) {
                    self.resetEmail(oEmailInputNode.val(), $(".verify-contact .verify-form-email-area .input-code").val());
                }
            } else {
                layer.msg("验证码已过期，请重新获取");
            }
        });

        oEmailInputNode.on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).next().children(".error-info-content").text("邮箱不能为空！");
                $(this).next().children(".error-info-content").css("display", 'block');
            } else if (!emailRegExp.test($(this).val())) {
                $(this).addClass("error-border");
                $(this).next().children(".error-info-content").text("邮箱格式不正确！");
                $(this).next().children(".error-info-content").css("display", 'block');
            }
        });
        oEmailInputNode.on("focus", function () {
            $(this).removeClass("error-border");
            $(this).next().children(".error-info-content").css("display", 'none');
        });

        $(".verify-contact .verify-form-email-area .input-code").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).next().next().children(".error-info-content").text("验证码不能为空！");
                $(this).next().next().children(".error-info-content").css("display", 'block');
            }
        });
        $(".verify-contact .verify-form-email-area .input-code").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).next().next().children(".error-info-content").css("display", 'none');
        });

        // 返回按钮的点击事件
        oBackBtn.on("click", function () {
            oTitleNode.html("修改联系方式");
            oBackBtn.css("display", "none");
            oSelectArea.css("display", "block");
            oPhoneArea.css("display", "none");
            oEmailArea.css("display", "none");
        })

        oEmailInputNode.change(function () {
            isInputChangeEmail = true;
        });

        oPhoneInputNode.change(function () {
            isInputChangePhoneCode = true;
        });
    };

    // 获取手机号验证码
    self.getPhoneCode = function (phone) {
        if (!phone) {
            layer.msg("手机号不能为空！");
            return;
        } else if (!phoneRegExp.test(phone)) {
            layer.msg("手机号格式不正确！");
            return;
        }
        // console.log(isInputChangePhoneCode);
        if (isInputChangePhoneCode === true) {
            var obj = {
                type: 202081,
                account: phone
            };
            new NewAjax({
                url: '/f/user/verify_account?pc=true',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(obj),
                success: function (res) {
                    if (res === false) {
                        new NewAjax({
                            type: "POST",
                            url: "/message/pc/" + phone + "/send_sms?pc=true",
                            contentType: "application/json;charset=UTF-8",
                            success: function (res) {
                                if (res.status === 200) {
                                    layer.msg("发送验证码成功");
                                    sendCodeTimer = setInterval(function () {
                                        sendCodeTime--;
                                        if (sendCodeTime < 0) {
                                            clearInterval(sendCodeTimer);
                                            sendCodeTimer = null;
                                            sendCodeTime = 60;
                                            $(".verify-contact .verify-form-phone-area .get-code-button").html("发送验证码");
                                        } else {
                                            $(".verify-contact .verify-form-phone-area .get-code-button").html(sendCodeTime);
                                        }
                                    }, 1000)
                                } else {
                                    layer.msg("发送验证码失败");
                                }
                            }
                        });
                    } else {
                        pTipMessage('提示', '该手机号已被注册，请重新输入', 'warning', 2000, true);
                    }
                },
                error: function (err) {
                    console.error('检测账号：' + err)
                }
            });
        } else {
            new NewAjax({
                type: "POST",
                url: "/message/pc/" + phone + "/send_sms?pc=true",
                contentType: "application/json;charset=UTF-8",
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("发送验证码成功");
                        sendCodeTimer = setInterval(function () {
                            sendCodeTime--;
                            if (sendCodeTime < 0) {
                                clearInterval(sendCodeTimer);
                                sendCodeTimer = null;
                                sendCodeTime = 60;
                                $(".verify-contact .verify-form-phone-area .get-code-button").html("发送验证码");
                            } else {
                                $(".verify-contact .verify-form-phone-area .get-code-button").html(sendCodeTime);
                            }
                        }, 1000)
                    } else {
                        layer.msg("发送验证码失败");
                    }
                }
            });
        }
    };

    // 验证手机号
    self.verifyPhone = function (phone, code) {
        if (!phone) {
            layer.msg("手机号不能为空！");
            return;
        } else if (!phoneRegExp.test(phone)) {
            layer.msg("手机号格式不正确！");
            return;
        } else if (!code) {
            layer.msg("验证码不能为空！");
            return;
        }
        var json = {
            phone_num: phone,
            randCode: code
        };
        new NewAjax({
            type: "POST",
            url: "/f/user/pc/update_phone/verify_account?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    layer.msg("手机号验证成功");
                    verifyStepInVerifyContact = 2;
                    oTitleNode.html("绑定新手机号");
                    oPhoneInputNode.attr("disabled", false).prev().html("手机号");
                    $(".verify-contact .verify-form-phone-area .get-code-button").html("发送验证码");
                    oPhoneInputNode.val("");
                    $(".verify-contact .verify-form-phone-area .input-code").val("");
                    $(".verify-contact .verify-form-phone-area .verify-submit").html("绑定");
                    sendCodeTime = 0;
                    clearInterval(sendCodeTime);
                } else {
                    layer.msg(res.message);
                }
            }
        });
    };

    // 重置手机号
    self.resetPhone = function (phone, code) {
        if (!phone) {
            layer.msg("手机号不能为空！");
            return;
        } else if (!phoneRegExp.test(phone)) {
            layer.msg("手机号格式不正确！");
            return;
        } else if (!code) {
            layer.msg("验证码不能为空！");
            return;
        }
        var json = {
            phone_num: phone,
            randCode: code
        };
        new NewAjax({
            type: "POST",
            url: "/f/user/pc/update_phone/reset_phone?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    layer.msg("绑定手机号成功");
                    var localStorage = window.localStorage;
                    var user = JSON.parse(localStorage.getItem("user"));
                    user.phone = phone;
                    localStorage.setItem("user", JSON.stringify(user));
                    verifyStepInVerifyContact = 1;
                    setTimeout(function () {
                        self.initDataInVerifyContact();
                    }, 1000)
                } else {
                    layer.msg(res.message);
                }
            }
        });
    };

    // 获取邮箱验证码
    self.getEmailCode = function (email) {
        if (!email) {
            layer.msg("邮箱不能为空！");
            return;
        } else if (!emailRegExp.test(email)) {
            layer.msg("邮箱格式不正确！");
            return;
        }
        if (isInputChangeEmail === true) {
            var obj = {
                type: 202082,
                account: email
            };
            new NewAjax({
                url: '/f/user/verify_account?pc=true',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(obj),
                success: function (res) {
                    if (res === false) {
                        new NewAjax({
                            type: "POST",
                            url: "/message/pc/" + email + "/send_mail?pc=true",
                            contentType: "application/json;charset=UTF-8",
                            success: function (res) {
                                if (res.status === 200) {
                                    layer.msg("发送验证码成功");
                                    sendCodeTimer = setInterval(function () {
                                        sendCodeTime--;
                                        if (sendCodeTime < 0) {
                                            clearInterval(sendCodeTimer);
                                            sendCodeTimer = null;
                                            sendCodeTime = 60;
                                            $(".verify-contact .verify-form-email-area .get-code-button").html("发送验证码");
                                        } else {
                                            $(".verify-contact .verify-form-email-area .get-code-button").html(sendCodeTime);
                                        }
                                    }, 1000)
                                } else {
                                    layer.msg("发送验证码失败");
                                }
                            }
                        });
                    } else {
                        pTipMessage('提示', '该邮箱已被注册，请重新输入', 'warning', 2000, true);
                    }
                    isInputChangeEmail = false;
                },
                error: function (err) {
                    console.error('检测账号：' + err)
                }
            });
        } else {
            new NewAjax({
                type: "POST",
                url: "/message/pc/" + email + "/send_mail?pc=true",
                contentType: "application/json;charset=UTF-8",
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("发送验证码成功");
                        sendCodeTimer = setInterval(function () {
                            sendCodeTime--;
                            if (sendCodeTime < 0) {
                                clearInterval(sendCodeTimer);
                                sendCodeTimer = null;
                                sendCodeTime = 60;
                                $(".verify-contact .verify-form-email-area .get-code-button").html("发送验证码");
                            } else {
                                $(".verify-contact .verify-form-email-area .get-code-button").html(sendCodeTime);
                            }
                        }, 1000)
                    } else {
                        layer.msg("发送验证码失败");
                    }
                }
            });
        }
    }

    // 验证邮箱
    self.verifyEmail = function (email, code) {
        if (!email) {
            layer.msg("邮箱不能为空！");
            return;
        } else if (!emailRegExp.test(email)) {
            layer.msg("邮箱格式不正确！");
            return;
        } else if (!code) {
            layer.msg("验证码不能为空！");
            return;
        }
        var json = {
            email: email,
            randCode: code
        };
        new NewAjax({
            type: "POST",
            url: "/f/user/pc/update_email/verify_account?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    layer.msg("邮箱验证成功");
                    verifyStepInVerifyContact = 2;
                    oTitleNode.html("绑定新邮箱");
                    oEmailInputNode.attr("disabled", false).prev().html("邮箱");
                    $(".verify-contact .verify-form-email-area .get-code-button").html("发送验证码");
                    oEmailInputNode.val("");
                    $(".verify-contact .verify-form-email-area .input-code").val("");
                    $(".verify-contact .verify-form-email-area .verify-submit").html("绑定");
                    // console.log(sendCodeTime)
                    sendCodeTime = 0;
                    clearInterval(sendCodeTime);
                } else {
                    layer.msg(res.message);
                }
            }
        });
    };

    // 重置邮箱
    self.resetEmail = function (email, code) {
        if (!email) {
            layer.msg("邮箱不能为空！");
            return;
        } else if (!emailRegExp.test(email)) {
            layer.msg("邮箱格式不正确！");
            return;
        } else if (!code) {
            layer.msg("验证码不能为空！");
            return;
        }
        var json = {
            email: email,
            randCode: code
        };
        new NewAjax({
            type: "POST",
            url: "/f/user/pc/update_email/reset_email?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    layer.msg("绑定邮箱成功");
                    var localStorage = window.localStorage;
                    var user = JSON.parse(localStorage.getItem("user"));
                    user.email = email;
                    localStorage.setItem("user", JSON.stringify(user));
                    verifyStepInVerifyContact = 1;
                    setTimeout(function () {
                        self.initDataInVerifyContact();
                    }, 1000)
                } else {
                    layer.msg(res.message);
                }
            }
        });
    };
}

