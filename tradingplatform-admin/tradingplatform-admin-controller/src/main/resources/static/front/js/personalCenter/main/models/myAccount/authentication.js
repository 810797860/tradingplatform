var windowCompanyAuthentication = new CompanyAuthentication();

function CompanyAuthentication() {
    var _this = this;
    // 身份证input节点
    // 正面
    var oIDCardFaceInputNode_1 = $('#positive-card-img');
    var oIDCardFaceInputNode_2 = $('#positive-card-img1');
    // 背面
    var oIDCardBackInputNode_1 = $('#negative-card-img');
    var oIDCardBackInputNode_2 = $('#negative-card-img1');
    // 手持
    var oIDCardHandheldInputNode_1 = $('#handheld-card-img');
    var oIDCardHandheldInputNode_2 = $('#handheld-card-img1');
    // 企业营业执照input节点
    var oLicenseInputNode_1 = $('#business-license-input');
    var oLicenseInputNode_2 = $('#business-license-input1');
    // 获取水印的图片的ID
    var companyImgId;
    var personImgFrontId;
    var personImgBackId;
    var personImgHeldId;
    // 获取两个div
    var $_selectVerifyWayArea = $('.authentication-div .verify-way-area');
    // 判断是否能提交
    var isTrueName = true;
    var isIdCard = true;
    var isOpenCard = true;
    var isEnterpriseName = true;
    // 提交时企业与个人的ID
    var submitPersonId;
    var submitCompanyId;
    // 提交时控制不能重复提交
    var controlNumPerson = true;
    var controlNumCompany = true;

    // 截图
    // 是否正在截图
    var bIsCuting = false;
    // 当前截图实例
    var oNowCutInstance = null;
    // 获取截图模块
    var oCutPictureModelArea = $('.authentication-certification .certification-cut-picture-Area-Bg').eq(0);
    // 获取截图插件区域
    var oCutInstanceArea = $('#certification-cut-picture').eq(0);
    // 截图区域id
    var cutPictureAreaId = oCutInstanceArea.get(0).id;
    // 展示截图的image节点
    var oNowCutPictureImageNode = oCutPictureModelArea.find('.certification-image').eq(0);
    // 截图宽高
    var nCutWindowWidth = 400;
    var nCutWindowHeight = 200;
    // 截图蒙板
    var oCutPictureMark = oCutPictureModelArea.find('.cutPicturePluginLoadingMark').eq(0);
    // 按钮
    var oCutPictureBtnTrue = oCutPictureModelArea.find('button[name="cutTrue"]').eq(0);
    var oCutPictureBtnCancel = oCutPictureModelArea.find('button[name="cutCancel"]').eq(0);
    // 截图是否正在上传
    var bIsUploading = false;
    // 记录当前触发的input 节点
    var oNowActiveInputNode = null;
    // 当前的图片节点
    var oNowActiveImageNode = null;
    // 当前上传图片类型
    var nUploadImageType = null;

    initCutPicturePlugin();
    eventHandle();
    initPage();


    _this.initDom = function () {
        $('.authentication-certification .authentication-div').show();
        $('.authentication-certification .prersonal-certification-area').hide();
        $('.authentication-certification .enterprise-certification-area').hide();
        $(".right-page").removeClass("page-active").siblings(".authentication-form-area").addClass("page-active");
    };

    // 获取企业初始化页面的数据
    function initPageCompanyData () {
        new NewAjax({
            url: '/f/companyVerification/get_by_user_id',
            type: 'get',
            async: false,
            contentType: 'application/json',
            success: function (res) {
                if (res.status === 200) {
                    var data = res.data.data_object;
                    submitCompanyId = data.id;
                    $('.prersonal-certification').hide();
                    $('.enterprise-certification').show();
                    $('.enterprise-certification .verify-button').html('查看状态');
                    if (!!data.back_check_status) {
                        $('#enterpriseName').val(data.company_name);
                        $('#openCard').val(data.business_license_num);
                        $('#business-license-img').attr('src', '/adjuncts/file_download/' + JSON.parse(data.business_license).id);
                        $('#business-license-img').show();
                        if (JSON.parse(data.back_check_status).id == 202051) {
                            oLicenseInputNode_1.show();
                            oLicenseInputNode_2.show();
                            $('#enterpriseName').attr('disabled', false);
                            $('#openCard').attr('disabled', false);
                            $('.enterprise-certification-area-submit').show();
                            $('.enterprise-certification-area-status').css('borderColor', 'rgba(255,0,0)').css('color', 'rgba(255,0,0)');
                            $('.enterprise-certification-area-status').html('不通过');
                        } else if (JSON.parse(data.back_check_status).id == 202050) {
                            oLicenseInputNode_1.hide();
                            oLicenseInputNode_2.hide();
                            $('#enterpriseName').attr('disabled', true);
                            $('#openCard').attr('disabled', true);
                            $('.enterprise-certification-area-submit').hide();
                            $('.enterprise-certification-area-status').css('borderColor', 'rgb(0, 184, 63)').css('color', 'rgb(0, 184, 63)');
                            $('.enterprise-certification-area-status').html('已通过');
                        } else {
                            oLicenseInputNode_1.hide();
                            oLicenseInputNode_2.hide();
                            $('#enterpriseName').attr('disabled', true);
                            $('#openCard').attr('disabled', true);
                            $('.enterprise-certification-area-submit').hide();
                            $('.enterprise-certification-area-status').css('borderColor', 'rgb(170, 170, 170)').css('color', 'rgb(170, 170, 170)');
                            $('.enterprise-certification-area-status').html('审核中');
                        }
                    }
                }
            }
        })
    };

    // 获取个人初始化页面的数据
    function initPagePersonalData () {
        new NewAjax({
            url: '/f/personalVerification/get_by_user_id?pc=true',
            type: 'get',
            async: false,
            contentType: 'application/json',
            success: function (res) {
                if (res.status === 200) {
                    var personData = res.data.data_object;
                    submitPersonId = personData.id;
                    $('.enterprise-certification').hide();
                    $('.prersonal-certification').show();
                    $('.prersonal-certification .verify-button').html('查看状态');
                    if (!!personData.back_check_status) {
                        $('#trueName').val(personData.true_name);
                        $('#idCard').val(personData.identity_num);
                        $('#positive-certification-logo-download-img').attr('src', '/adjuncts/file_download/' + JSON.parse(personData.id_card_front).id);
                        $('#negative-certification-logo-download-img').attr('src', '/adjuncts/file_download/' + JSON.parse(personData.id_card_back).id);
                        $('#handheld-certification-logo-download-img').attr('src', '/adjuncts/file_download/' + JSON.parse(personData.id_card_hand_held).id);
                        $('#positive-certification-logo-download-img').show();
                        $('#negative-certification-logo-download-img').show();
                        $('#handheld-certification-logo-download-img').show();
                        if (JSON.parse(personData.back_check_status).id == 202051) {
                            oIDCardFaceInputNode_1.show();
                            oIDCardBackInputNode_1.show();
                            oIDCardHandheldInputNode_1.show();
                            oIDCardFaceInputNode_2.show();
                            oIDCardBackInputNode_2.show();
                            oIDCardHandheldInputNode_2.show();
                            $('#trueName').attr('disabled', false);
                            $('#idCard').attr('disabled', false);
                            $('.prersonal-certification-area-submit').show();
                            $('.prersonal-certification-area-status').css('borderColor', 'rgba(255,0,0)').css('color', 'rgba(255,0,0)');
                            $('.prersonal-certification-area-status').html('不通过');
                        } else if (JSON.parse(personData.back_check_status).id == 202050) {
                            oIDCardFaceInputNode_1.hide();
                            oIDCardBackInputNode_1.hide();
                            oIDCardHandheldInputNode_1.hide();
                            oIDCardFaceInputNode_2.hide();
                            oIDCardBackInputNode_2.hide();
                            oIDCardHandheldInputNode_2.hide();
                            $('#trueName').attr('disabled', true);
                            $('#idCard').attr('disabled', true);
                            $('.prersonal-certification-area-submit').hide();
                            $('.prersonal-certification-area-status').css('borderColor', 'rgb(0, 184, 63)').css('color', 'rgb(0, 184, 63)');
                            $('.prersonal-certification-area-status').html('已通过');
                        } else {
                            oIDCardFaceInputNode_1.hide();
                            oIDCardBackInputNode_1.hide();
                            oIDCardHandheldInputNode_1.hide();
                            oIDCardFaceInputNode_2.hide();
                            oIDCardBackInputNode_2.hide();
                            oIDCardHandheldInputNode_2.hide();
                            $('#trueName').attr('disabled', true);
                            $('#idCard').attr('disabled', true);
                            $('.prersonal-certification-area-submit').hide();
                            $('.prersonal-certification-area-status').css('borderColor', 'rgb(170, 170, 170)').css('color', 'rgb(170, 170, 170)');
                            $('.prersonal-certification-area-status').html('审核中');
                        }
                    }
                }
            }
        })
    }

    function eventOfGoBack () {
        $(".authentication-certification .back-button").off().on("click", function () {
            _this.initDom();
        });
    }
    // 初始化页面
    function initPage () {
        var user = window.localStorage.getItem('user');
        if (!!user) {
            var userInfo = JSON.parse(user);
        } else {
            return;
        }
        // 注入的数据
        if (!!userInfo.verificationType) {
            $('.enterprise-certification-area .prersonal-certification-area-img-content').hide();
            $('.enterprise-certification-area .prersonal-certification-area-img-content1').show();
            $('.prersonal-certification-area .prersonal-certification-area-img-content').hide();
            $('.prersonal-certification-area .prersonal-certification-area-img-content1').show();
            $('.select-verify-way-title').hide();
            if (JSON.parse(userInfo.verificationType).id == 202135) {
                initPagePersonalData();
            } else {
                initPageCompanyData();
            }
        } else {
            $('.prersonal-certification-area-status').hide();
            $('.enterprise-certification-area-status').hide();
            $('.enterprise-certification').show();
            $('.prersonal-certification').show();
            $('.prersonal-certification .verify-button').html('立即认证');
            $('.enterprise-certification .verify-button').html('立即认证');
            $('.enterprise-certification-area .prersonal-certification-area-img-content').show();
            $('.enterprise-certification-area .prersonal-certification-area-img-content1').hide();
            $('.prersonal-certification-area .prersonal-certification-area-img-content').show();
            $('.prersonal-certification-area .prersonal-certification-area-img-content1').hide();
        }
    }
    // 点击跳转验证页面
    function certificationClick () {
        // 个人认证
        $_selectVerifyWayArea.eq(0).find(".verify-button").click(function () {
            $('.authentication-div').hide();
            $('.prersonal-certification-area').show();
            if (userInfo.verificationType && JSON.parse(userInfo.verificationType).id == 202135) {
                initPagePersonalData();
            }
        });

        // 企业认证
        $_selectVerifyWayArea.eq(1).find(".verify-button").click(function () {
            $('.authentication-div').hide();
            $('.enterprise-certification-area').show();
            if (userInfo.verificationType && JSON.parse(userInfo.verificationType).id != 202135) {
                initPageCompanyData();
            }
        })
    }

    function onblurCheck () {
        // 真实姓名失去焦点
        $('#trueName').on('blur', function () {
            if (!$('#trueName').val()) {
                $("#trueName").next().children(".expert-info-error-tip").css("display", 'block');
                // isSubmitIntroduction  = false;
                isTrueName = false;
            } else {
                if (filterSensitiveWordIsTrue($('#trueName').val())) {
                    layer.msg("输入内容含有敏感词，请重新输入");
                    isTrueName = false;
                } else {
                    isTrueName = true;
                }
                // isSubmitIntroduction  = true;
            }
        });
        // 真实姓名获得焦点
        $('#trueName').on('focus', function () {
            $("#trueName").next().children(".expert-info-error-tip").css("display", 'none');
        });

        // 证件号码失去焦点
        $('#idCard').on('blur', function () {
            if (!$('#idCard').val()) {
                $("#idCard").next().children(".expert-info-error-tip").css("display", 'block');
                // isSubmitIntroduction  = false;
                isIdCard = false;
            } else {
                if (!(/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/).test($('#idCard').val())) {
                    $("#idCard").next().children(".expert-info-error-tip").css("display", 'block');
                    $("#idCard").next().children(".expert-info-error-tip").html('身份证号码不正确，请重新输入');
                    isIdCard = false;
                } else {
                    isIdCard = true;
                }
                // isSubmitIntroduction  = true;
            }
        });
        // 证件号码获得焦点
        $('#idCard').on('focus', function () {
            $("#idCard").next().children(".expert-info-error-tip").css("display", 'none');
        });

        // 企业名称失去焦点
        $('#enterpriseName').on('blur', function () {
            if (!$('#enterpriseName').val()) {
                $("#enterpriseName").next().children(".expert-info-error-tip").css("display", 'block');
                // isSubmitIntroduction  = false;
                isEnterpriseName = false;
            } else {
                if (filterSensitiveWordIsTrue($('#enterpriseName').val())) {
                    layer.msg("输入内容含有敏感词，请重新输入");
                    isEnterpriseName = false;
                } else {
                    isEnterpriseName = true;
                }
                // isSubmitIntroduction  = true;
            }
        });
        // 企业名称获得焦点
        $('#enterpriseName').on('focus', function () {
            $("#enterpriseName").next().children(".expert-info-error-tip").css("display", 'none');
        });


        // 营业执照注册号失去焦点
        $('#openCard').on('blur', function () {
            if (!$('#openCard').val()) {
                $("#openCard").next().children(".expert-info-error-tip").css("display", 'block');
                // isSubmitIntroduction  = false;
                isOpenCard = false;
            } else {
                if (!(/(^(?:(?![IOZSV])[\dA-Z]){2}\d{6}(?:(?![IOZSV])[\dA-Z]){10}$)|(^\d{15}$)/).test($('#openCard').val())) {
                    $("#openCard").next().children(".expert-info-error-tip").css("display", 'block');
                    $("#openCard").next().children(".expert-info-error-tip").html('营业执照号码不正确，请重新输入');
                    isOpenCard = false;
                } else {
                    isOpenCard = true;
                }
                // isSubmitIntroduction  = true;
            }
        });
        // 营业执照注册号获得焦点
        $('#openCard').on('focus', function () {
            $("#openCard").next().children(".expert-info-error-tip").css("display", 'none');
        });
    }

    function uploadImg () {
        oIDCardFaceInputNode_1.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var length = 0;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 1);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardFaceInputNode_1;
            oNowActiveImageNode = oNowActiveInputNode.parent().next().find('img').eq(0);
            nUploadImageType = 1;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oIDCardFaceInputNode_2.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var length = 0;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 1);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardFaceInputNode_2;
            oNowActiveImageNode = oNowActiveInputNode.parent().find('img').eq(0);
            nUploadImageType = 1;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oIDCardBackInputNode_1.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var length = 0;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 2);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardBackInputNode_1;
            oNowActiveImageNode = oNowActiveInputNode.parent().next().find('img').eq(0);
            nUploadImageType = 2;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oIDCardBackInputNode_2.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var length = 0;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 2);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardBackInputNode_2;
            oNowActiveImageNode = oNowActiveInputNode.parent().find('img').eq(0);
            nUploadImageType = 2;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oIDCardHandheldInputNode_1.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 3);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardHandheldInputNode_1;
            oNowActiveImageNode = oNowActiveInputNode.parent().next().find('img').eq(0);
            nUploadImageType = 3;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oIDCardHandheldInputNode_2.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 3);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oIDCardHandheldInputNode_2;
            oNowActiveImageNode = oNowActiveInputNode.parent().find('img').eq(0);
            nUploadImageType = 3;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oLicenseInputNode_1.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 4);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oLicenseInputNode_1;
            oNowActiveImageNode = oNowActiveInputNode.parent().next().find('img').eq(0);
            nUploadImageType = 4;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });

        oLicenseInputNode_2.off().on("change", function () {
            /*var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var imgFile = [];
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f]);
                } else {
                    isOtherFile = true;
                }
            }
            if (isOtherFile) {
                layer.msg("只能上传图片");
                return;
            }
            if (imgFile.length > 1) {
                layer.msg("只能上传一张图片");
                return;
            }
            for (var p = 0; p < imgFile.length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (imgFile.length) {
                certificationUploadPicture(formData, 4);
            }*/
            var files = $(this).get(0).files,
                img = new Image();
            layer.closeAll();
            if (!files[0].type) {
                // input点击取消的情况
                return 0;
            }
            if (files.length > 1) {
                layer.msg('仅能上传 1 张图片');
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.msg("只能上传图片");
                return 0;
            }
            oNowActiveInputNode = oLicenseInputNode_2;
            oNowActiveImageNode = oNowActiveInputNode.parent().find('img').eq(0);
            nUploadImageType = 4;
            bIsCuting = true;
            // 加载新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutPictureModelArea.is(':hidden')) {
                oCutPictureModelArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oNowActiveInputNode.get(0).value = '';
        });
    }

    function submitPersonalData () {
        $('.prersonal-certification-area-submit-btn').off().click(function () {
            layer.closeAll();
            if (!controlNumPerson) {
                return;
            }
            if (bIsCuting) {
                layer.msg('请先完成截图操作');
                return
            }
            if (!$('#trueName').val() || !$('#idCard').val()) {
                layer.msg("请完善信息");
                return;
            }
            if (!isIdCard || !isTrueName) {
                layer.msg("请正确填写信息");
                return;
            }
            if (!$('#positive-certification-logo-download-img').attr('src') || !$('#negative-certification-logo-download-img').attr('src') || !$('#handheld-certification-logo-download-img').attr('src')) {
                layer.msg("请完善信息");
                return;
            }
            var json = {
                trueName: filterSensitiveWord($('#trueName').val()),
                identityNum: $('#idCard').val(),
                idCardFront: parseInt($('#positive-certification-logo-download-img').attr('src').split('/')[3]),
                idCardFrontWatermark: parseInt(personImgFrontId),
                idCardBack: parseInt($('#negative-certification-logo-download-img').attr('src').split('/')[3]),
                idCardBackWatermark: parseInt(personImgBackId),
                idCardHandHeld: parseInt($('#handheld-certification-logo-download-img').attr('src').split('/')[3]),
                idCardHandHeldWatermark: parseInt(personImgHeldId)
            };
            if (!!submitPersonId) {
                json.id = submitPersonId
            }
            controlNumPerson = false;
            var url = '/f/personalVerification/pc/create_update?pc=true'
            new NewAjax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("提交成功");
                        var user = window.localStorage.getItem('user');
                        if (!!user) {
                            var userInfo = JSON.parse(user);
                            userInfo.verificationType = JSON.stringify({id: '202135', pid: '202134', title: '个人'});
                            window.localStorage.setItem('user', JSON.stringify(userInfo));
                        }
                        initPage();
                        _this.initDom();
                    } else {
                        controlNumPerson = true;
                    }
                },
                error: function (err) {
                    controlNumPerson = true;
                    console.error(err);
                }
            })
        })
    }

    function submitEnterpriseData () {
        $('.enterprise-certification-area-submit-btn').off().click(function () {
            layer.closeAll();
            if (!controlNumCompany) {
                return;
            }
            if (bIsCuting) {
                layer.msg('请先完成截图操作');
                return
            }
            if (!$('#enterpriseName').val() || !$('#openCard').val()) {
                layer.msg("请完善信息");
                return;
            }
            if (!isOpenCard || !isEnterpriseName) {
                layer.msg("请正确填写信息");
                return;
            }
            if (!$('#business-license-img').attr('src')) {
                layer.msg("请完善信息");
                return;
            }
            var json = {
                companyName: filterSensitiveWord($('#enterpriseName').val()),
                businessLicenseNum: $('#openCard').val(),
                businessLicense: $('#business-license-img').attr('src').split('/')[3],
                businessLicenseWatermark: parseInt(companyImgId)
            };
            if (!!submitCompanyId) {
                json.id = submitCompanyId;
            }
            controlNumCompany = false;
            var url = '/f/companyVerification/pc/create_update?pc=true';
            new NewAjax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("提交成功");
                        var user = window.localStorage.getItem('user');
                        if (!!user) {
                            var userInfo = JSON.parse(user);
                            userInfo.verificationType = JSON.stringify({id: '202137', pid: '202134', title: '企业'});
                            window.localStorage.setItem('user', JSON.stringify(userInfo));
                        }
                        initPage();
                        _this.initDom();
                    } else {
                        controlNumCompany = true;
                    }
                },
                error: function (err) {
                    controlNumCompany = true;
                    console.error(err);
                }
            })
        })
    }

    function certificationUploadPicture (files, type, callback) {
        files.append('waterMarkContent', '仅用于待定平台实名认证');
        new NewAjax({
            type: "POST",
            url: "/adjuncts/true/file_upload",
            data: files,
            async: true,
            processData: false,
            // dataType: 'json',
            contentType: false,
            success: function (res) {
                if (res.status === 200) {
                    isLoadImage = false;
                    var list = res.data.data_list;
                    if (type == 1) {
                        $('.prersonal-certification-area .prersonal-certification-area-img-content').eq(0).hide();
                        $('.prersonal-certification-area .prersonal-certification-area-img-content1').eq(0).show();
                        $("#positive-certification-logo-download-img").attr('src', '/adjuncts/file_download/' + list[1].id);
                        personImgFrontId = list[0].id;
                        // $_logo = list[0].id
                        $("#positive-certification-logo-download-img").css("display", 'block');
                    } else if (type == 2) {
                        $('.prersonal-certification-area .prersonal-certification-area-img-content').eq(1).hide();
                        $('.prersonal-certification-area .prersonal-certification-area-img-content1').eq(1).show();
                        $("#negative-certification-logo-download-img").attr('src', '/adjuncts/file_download/' + list[1].id);
                        personImgBackId = list[0].id;
                        // $_logo = list[0].id
                        $("#negative-certification-logo-download-img").css("display", 'block');
                    } else if (type == 3) {
                        $('.prersonal-certification-area .prersonal-certification-area-img-content').eq(2).hide();
                        $('.prersonal-certification-area .prersonal-certification-area-img-content1').eq(2).show();
                        $("#handheld-certification-logo-download-img").attr('src', '/adjuncts/file_download/' + list[1].id);
                        personImgHeldId = list[0].id;
                        // $_logo = list[0].id
                        $("#handheld-certification-logo-download-img").css("display", 'block');
                    } else {
                        $('.enterprise-certification-area .prersonal-certification-area-img-content').hide();
                        $('.enterprise-certification-area .prersonal-certification-area-img-content1').show();
                        $("#business-license-img").attr('src', '/adjuncts/file_download/' + list[1].id);
                        companyImgId = list[0].id;
                        $("#business-license-img").css("display", 'block');
                    }
                    if (callback) {
                        callback();
                    }
                }
            },
            error: function (err) {
                console.error(err);
            }
        });
    }

    function eventHandle() {
        eventOfGoBack();
        certificationClick();
        uploadImg();
        onblurCheck();
        submitPersonalData();
        submitEnterpriseData();
        cutAvatarBtnEvent();
    }

    /* 截图 */

    // 初始化截图插件
    function initCutPicturePlugin() {
        var img = new Image();
        // 引入截图插件
        if (window.ScreenshotPlugin === undefined) {
            $.getScript("/static/front/js/fragments/screenShot.js", function () {
                oNowCutInstance = new ScreenshotPlugin(cutPictureAreaId);
                oNowCutInstance.setResultCallback(function (fileStr) {
                    // 若非截图时间
                    if (!bIsCuting) {
                        return 0;
                    }
                    oNowCutPictureImageNode.attr({
                        src: fileStr
                    });
                }).setShowCanvasSize(900, 350)
                    .setGetImageFromOutSide(true)
                    .setCutCanvasWidthHeightRatio(nCutWindowWidth, nCutWindowHeight);
                oCutPictureMark.hide();
            });
        } else if (!oNowCutInstance) {
            oNowCutInstance = new ScreenshotPlugin(cutPictureAreaId);
            oNowCutInstance.setResultCallback(function (fileStr) {
                // 若非截图时间
                if (!bIsCuting) {
                    return 0;
                }
                oNowCutPictureImageNode.attr({
                    src: fileStr
                });
            }).setShowCanvasSize(900, 350)
                .setGetImageFromOutSide(true)
                .setCutCanvasWidthHeightRatio(nCutWindowWidth, nCutWindowHeight);
            oCutPictureMark.hide();
        }
    }

    // 截图按钮事件
    function cutAvatarBtnEvent() {
        eventOfCutAvatarBtnTrue();
        eventOfCutAvatarBtnCancel();
    }

    // 按钮确定事件
    function eventOfCutAvatarBtnTrue() {
        var data = null,
            stSave = null,
            blob = null,
            fd = null,
            sNewPictureUrl = null;
        oCutPictureBtnTrue.off().click(function () {
            layer.closeAll();
            if (!bIsCuting || !nUploadImageType) {
                layer.msg('非法操作，请遵循流程');
                return 0;
            }
            if (bIsUploading) {
                layer.msg('正在上传中，请稍后操作');
                return 0;
            }
            sNewPictureUrl = oNowCutPictureImageNode.get(0).src;
            if (!sNewPictureUrl) {
                layer.msg('没有要上传的图片');
                return 0;
            }
            bIsUploading = true;
            oCutPictureMark.show();
            data = window.atob(sNewPictureUrl.split(',')[1]);
            stSave = new Uint8Array(data.length);
            for (var index = 0; index < data.length; index++) {
                stSave[index] = data.charCodeAt(index);
            }
            // canvas.toDataURL 返回的默认格式就是 image/png
            blob = new Blob([stSave], {type: "image/png"});
            fd = new FormData();
            fd.append("filename", "avatar.png");
            fd.append('files', blob);
            certificationUploadPicture(fd, nUploadImageType, function () {
                nUploadImageType = null;
                bIsUploading = false;
                bIsCuting = false;
                // 隐藏蒙板
                oCutPictureMark.hide();
                // 隐藏区域
                oCutPictureModelArea.hide();
            });
            /*uploadFile(fd, function (res) {
                var data = null;
                bIsUploading = false;
                // 隐藏蒙板
                oCutPictureMark.hide();
                bIsCuting = false;
                if (res.status === 200) {
                    data = res.data.data_list;
                    // 保存图片id
                    $_logo = data[0].id;
                    // 上传成功时copy新图片链接
                    sOldPictureUrl = oNowCutPictureImageNode.get(0).src;
                    // 隐藏区域
                    oCutPictureModelArea.hide();
                } else {
                    layer.closeAll();
                    layer.msg('上传头像失败，请稍后重试');
                }
            });*/
        });
    }

    // 按钮取消事件
    function eventOfCutAvatarBtnCancel() {
        oCutPictureBtnCancel.off().click(function () {
            if (bIsUploading) {
                layer.closeAll();
                layer.msg('正在上传中，请稍后操作');
                return 0;
            }
            /*if (!sNewPictureUrl) {
                return 0;
            }
            sNewPictureUrl = null;*/
            bIsCuting = false;
            /*if (!sOldPictureUrl) {
                // 若没有旧链接，直接隐藏img 显示 + 号
                oNowCutPictureImageNode.hide().parent().css({
                    fontSize: '30px'
                });
            } else {
                oNowCutPictureImageNode.get(0).src = sOldPictureUrl;
            }
            delete sOldPictureUrl;*/
            // 隐藏截图区域
            oCutPictureModelArea.hide();
        });
    }

}
