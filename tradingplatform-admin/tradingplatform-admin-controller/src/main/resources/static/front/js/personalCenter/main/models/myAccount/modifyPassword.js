var codeUrl = "/captcha?pc=true"
var isPassVerify = false

$(function () {
    var localStorage = window.localStorage;
    var user = JSON.parse(localStorage.getItem("user"));
    $(".modify-password .account").val(user.userName);
    handleEventInModifyPwd();
});

function handleEventInModifyPwd() {
    $(".modify-password .code-img").off().on("click", function () {
        $(this).attr('src', codeUrl);
    });
    $(".modify-password .submit").off().on("click", function () {
        if (!isPassVerify) {
            if (!$(".modify-password .password").val() || !$(".modify-password .code").val()) {
                layer.msg('密码或验证码错误！');
                $(".modify-password .code-img").attr('src', codeUrl);
            } else {
                verifyAccount($(".modify-password .password").val(), $(".modify-password .code").val());
            }
        } else {
            resetPassword($(".modify-password .new-password").val(), $(".modify-password .re-password").val());
        }
    });
    $(".modify-password .re-password").off().on("input propertychange", function () {
        $(".modify-password .re-password-area .error-info").addClass("hidden")
    });
    $(".modify-password .password").keyup(function (event) {
        if (event.keyCode === 13) {
            $(".modify-password .submit").trigger("click");
        }
    });
    $(".modify-password .code").keyup(function (event) {
        if (event.keyCode === 13) {
            $(".modify-password .submit").trigger("click");
        }
    });
    $(".modify-password .re-password").keyup(function (event) {
        if (event.keyCode === 13) {
            $(".modify-password .submit").trigger("click");
        }
    });
    $(".modify-password .new-password").keyup(function (event) {
        if (event.keyCode === 13) {
            $(".modify-password .submit").trigger("click");
        }
    });
}

// 验证账号
function verifyAccount(password, code) {
    // if (!password) {
    //     layer.msg("密码不能为空！");
    //     return
    // } else if (!code) {
    //     layer.msg("验证码不能为空！");
    //     return
    // }
    var json = {
        password: passwordEncrypt(password),
        password_des: passwordEncryptDes(password),
        captcha: code
    }
    new NewAjax({
        type: "POST",
        url: "/pc/update_password/verify_account?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            if (res.status === 200) {
                layer.msg("验证成功！");
                isPassVerify = true;
                $(".modify-password .new-password-area").removeClass("hidden");
                $(".modify-password .re-password-area").removeClass("hidden");
                $(".modify-password .password-area").addClass("hidden");
                $(".modify-password .code-area").addClass("hidden");
            } else {
                layer.msg(res.message);
                $(".modify-password .code-img").attr('src', codeUrl)
            }
        }
    });
}

// 重置密码
function resetPassword(newPassword, rePassword) {
    if (!newPassword || !rePassword) {
        layer.msg("密码不能为空！");
        return
    } else if (newPassword !== rePassword) {
        layer.msg("密码不一致！");
        $(".modify-password .re-password-area .error-info").removeClass("hidden");
        return
    }
    var json = {
        password: encrypt(newPassword)
    }
    new NewAjax({
        type: "POST",
        url: "/pc/update_password/reset_password?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            if (res.status === 200) {
                layer.msg('重置密码成功');
                window.location.href = "/f/login.html?pc=true"
            } else {
                layer.msg(res.message);
            }
        }
    });
}

// 密码加密
function passwordEncrypt(password) {
    return hex_md5(encrypt(password));
}

// 密码加密
function passwordEncryptDes(password) {
    return encrypt(password);
}