var personalCenterMyShop = new MyShop();
var introduction = null;

// 存储类型多选框数据
function MyShop() {
    var _this = this;
    // 整个商铺区域
    var totalArea = $('.personal-center-right .my-shop-area').eq(0);
    var $_serviceLibInfo = serviceLibInfo;
    var $_evaluationHead = evaluationHead;
    var $_tabActive = 0;
    var searchSize = 5;
    var searchSizeTransaction = 9;
    var searchSizeCase = 12;
    var currentPage = 1;
    var clickPage = 0;  // 判断是否点击页数,0为是
    var evaluationStandard = 0; // 判断交易评价的评价标签
    var evaluationType = "好评";
    // 编辑按钮
    var editBtn = $('#shopHomeEditBtn');
    // 提交按钮
    var submitBtn = $('.edit-myshop-form-div-submit').eq(0);

    // 存储富文本
    var saveNewHomeContent = null;
    // 存储请求的审核数据
    var editData = {
        id: null,
        detailCover: null,
        personalizedHomepage: null
    };
    // 获取富文本节点
    // 获取展示div
    var shopHomeShowNode = $('#shopHomeContent');
    // 提交状态
    // var isSubmitSuccess = false

    var searchType = [
        {
            name: '行业',
            type: 'type',
            show: true,
            data: industryType
        },
        {
            name: '子行业',
            type: 'type',
            show: true,
            data: industryType
            // data: applicationIndustrys
        }
    ];
    var $_picturesId = "";
    var canSubmit = true;
    var richText = null;
    var glory = null;
    var isSumbit = false;
    var $_logo = null;
    var $_applicationIndustryId = [];
    var isSubmitShopName = true;
    var isSubmitConcatPerson = true;
    var isSubmitShopPhone = true;
    var isSubmitsShopFixedPhone = true;
    var isSubmitShopEmail = true;
    var isSubmitInternet = true;
    var isSubmitqq = true;
    var isSubmitIntroduction = true;
    var isSubmitDesc = true;
    var isShopTime = 60;
    var isShopTimePhone = 60;
    var isSubmitCodePhone = true;
    var isSubmitCodeEmail = true;

    /* 行业多选框 */
    // 存储页面上的筛选数据
    var searchData = {};
    // 获取多选框对象
    var typeSearch = null;
    // 存储全选的id数组
    var selectAllIdArr = [0, 202035, 202052];
    // 存储行业数据
    var aIndustry = [{id: 202052, title: '不限'}];
    // 存储子行业数据
    var aSubIndustry = [{id: 0, title: '不限'}];
    var typeSearchData = [
        {
            name: '行业',
            type: 'industry',
            active: 202052,
            isImportant: true,
            data: aIndustry
        },
        {
            name: '子行业',
            type: 'subIndustry',
            active: 0,
            isImportant: true,
            data: aSubIndustry,
            linkMethod: function () {
                var _this = this,
                    industryId = _this.selectData.industry,
                    data = [];
                _this.subIndustry.forEach(function (item) {
                    if (item.pid === industryId) {
                        data.push(item);
                    }
                });
                return (data.length > 0) ? [{id: 0, title: '不限'}].concat(data) : null;
            }
        }
    ];


    /* 截图 */
    // 是否正在截图
    var bIsCuting = false;
    // 当前截图实例
    var oNowCutInstance = null;
    // 公司logo的Input节点
    var oCompanyLogoInputNode = $('.edit-myshop-form-div .shop-logo-upload').eq(0);
    // 获取截图插件外框区域
    var oCutInstanceArea = $('.edit-myshop-form-div .cutPictureArea').eq(0);
    // 截图区域id
    var cutPictureAreaId = oCutInstanceArea.children().get(0).id;
    // 展示截图的image节点
    var oNowCutPictureImageNode = $('.edit-myshop-form-div .shop-logo-download-img').eq(0);
    var sNewPictureUrl = undefined;
    var sOldPictureUrl = undefined;
    // 截图宽高
    var nCutWindowWidth = 270;
    var nCutWindowHeight = 120;
    // 截图蒙板
    var oCutPictureMark = oCutInstanceArea.find('.cutPicturePluginLoadingMark').eq(0);
    // 按钮
    var oCutPictureBtnTrue = oCutInstanceArea.find('.cutPictureBtn[name="cutTrue"]').eq(0);
    var oCutPictureBtnCancel = oCutInstanceArea.find('.cutPictureBtn[name="cutCancel"]').eq(0);
    // 截图是否正在上传
    var bIsUploading = false;


    set_IMPORTANTOPERATION(true);
    // 设置默认选中项
    setDefualtSelect();
    // 提取行业数据
    extractIndustryData(industryType);
    initTypeSelectModel();
    initRichText();
    initCutPicturePlugin();
    handleClickEvent();

    // 初始数据
    _this.initData = function () {
        getNewCaseDatapage();
        var user = window.localStorage.getItem('user');
        var userInfo;
        if (!!user) {
            userInfo = JSON.parse(user);
            if (!!userInfo.phone) {
                $('#shopPhone').val(userInfo.phone);
                $('#shopPhone').attr('disabled', true);
                $('#phone-code-area').hide();
                $('#shop-send-phone-code').hide();
            } else {
                $('#shopPhone').attr('disabled', false);
                $('#phone-code-area').hide();
                $('#shop-send-phone-code').show();
                isSubmitCodePhone = false;
            }

            if (!!userInfo.email) {
                $('#shopEmail').val(userInfo.email);
                $('#shopEmail').attr('disabled', true);
                $('#email-code-area').hide();
                $('#shop-send-code').hide();
            } else {
                $('#shopEmail').attr('disabled', false);
                $('#email-code-area').hide();
                $('#shop-send-code').show();
            }
        }
        if (!!providerResult) {
            // 头部的公司LOGO
            if (!!providerResult['logo']) {
                $('.detail-header-left-logo').attr('src', '/adjuncts/file_download/' + JSON.parse(providerResult.logo).id);
            } else {
                $('.detail-header-left-logo').attr('src', '/static/front/assets/image/empty3.jpg');
            }

            $('.detail-header-right-area .expertise-item').remove();
            // 设置头部的擅长领域
            if (!!providerResult.skilled_field) {
                for (var i = 0; i < JSON.parse(providerResult.skilled_field).length; i++) {
                    var text = JSON.parse(providerResult.skilled_field)[i];
                    var span = $('<span class="expertise-item">' + text + '</span>')
                    $('.detail-header-right-area').append(span);
                }
                // 擅长领域回显
                $('.edit-show .select-row').eq(1).find('.addType').remove();
                $('.edit-show .select-row').eq(1).find('.deleteType').remove();
                for (var j = 0; j < JSON.parse(providerResult.skilled_field).length; j++) {
                    for (var k = 0; k < $('.edit-show .select-row').eq(1).find('.option').length; k++) {
                        if (JSON.parse(providerResult.skilled_field)[j] == $('.edit-show .select-row').eq(1).find('.option').eq(k).text()) {
                            $('.edit-show .select-row').eq(1).find('.option').eq(k).addClass('option-active');
                            break;
                        }
                    }
                    if (k == $('.edit-show .select-row').eq(1).find('.option').length) {
                        var type = $('<span class="inline-block option option-active" style="position: relative">' + JSON.parse(providerResult.skilled_field)[j] + '<i style="font-size: 16px;position: absolute;top: 0px;right: 5px" class="deleteType">×</i></span>');
                        $('.edit-show .select-row').eq(1).find('.option-area').append(type);
                    }
                }
            }
            if (!!providerResult.logo) {
                var span = $(' <span class="addType" style="width: 90px;display: inline-block;margin: 11px 15px 5px 0;height: 30px; line-height: 30px;border: 1px solid #0066cc;color: #0066cc;text-align: center;font-size: 30px;border-radius: 5px;cursor: pointer;margin-left:10px;">+</span>');
                $('.edit-show .select-row').eq(1).find('.option-area').append(span);
                $('.shop-logo-download-img').attr('src', '/adjuncts/file_download/' + JSON.parse(providerResult.logo).id);
                $('.shop-logo').css('fontSize', 0);
                $('.shop-logo-download-img').show();
                // $('.shop-logo-tips').hide();
                $('.shop-logo-tips').show();
            } else {
                $('.shop-logo-tips').show();
            }
            // 回显地区
            $('.edit-show #provinceName').val($('.edit-show #provinceName').attr('data-province'));
            $('#cityName').val($('#cityName').attr('data-city'));
            $('#districtName').val($('#districtName').attr('data-district'));
            if (!!providerResult.skilled_label) {
                // 回显类型
                $('.edit-show .select-row').eq(0).find('.option').removeClass('option-active');
                for (var z = 0; z < $('.edit-show .select-row').eq(0).find('.option').length; z++) {
                    if ($('.edit-show .select-row').eq(0).find('.option').eq(z).attr('data-id') == JSON.parse(providerResult.skilled_label).id) {
                        $('.edit-show .select-row').eq(0).find('.option').eq(z).addClass('option-active');
                    }
                }
            }
            if (!!providerResult.back_check_status) {
                // 回显状态
                if (JSON.parse(providerResult.back_check_status).id == 202049) {
                    $('.edit-show .edit-myshop-status').text('审核中');
                    submitBtn.hide();
                    $('#shop-send-code').hide();
                } else if (JSON.parse(providerResult.back_check_status).id == 202051) {
                    $('.edit-show .edit-myshop-status').text('不通过');
                    $('.edit-show .edit-myshop-status').css('borderColor', 'rgba(255,0,0)').css('color', 'rgba(255,0,0)');
                    submitBtn.show();
                    // $('#shop-send-code').show();
                    if (!!userInfo.email) {
                        $('#shop-send-code').hide();
                    } else {
                        $('#shop-send-code').show();
                    }
                } else {
                    $('.edit-show .edit-myshop-status').text('已通过');
                    $('.edit-show .edit-myshop-status').css('borderColor', 'rgb(0, 184, 63)').css('color', 'rgb(0, 184, 63)');
                    submitBtn.show();
                    // $('#shop-send-code').show();
                    if (!!userInfo.email) {
                        $('#shop-send-code').hide();
                    } else {
                        $('#shop-send-code').show();
                    }
                }
            }
        }
        //  设置评价的头部数据
        setEvaluationHeadData();
    };
    // 初始化dom结构
    _this.initDom = function () {
        $("#detail-data").css("display", 'none');
        $("#case-hall").css("display", 'none');
        $("#complete-task").css("display", 'none');
        $("#transaction-evaluate").css("display", 'none');
        // 更新案例展示solutionCard的样式
        updateSolutionCard();
    };
    // 初始化头部模块的图片信息
    _this.initHeadBgImage = function () {
        // 获取复选框节点
        var checkBoxNode = $('#shopBgImageSelect');
        // 获取用户图片节点
        var userUpLoadImgNode = $('.shopBgImageDiv .shopBgImage').eq(0);
        // 获取默认图片框节点
        var defaultBgImgNode = $('.shopHomeDefaultBgImage').eq(0);
        if ($_serviceLibInfo.detail_cover !== undefined) {
            // 取消勾选默认图片
            checkBoxNode.prop('checked', false);
            // 写入链接并展示
            userUpLoadImgNode.attr({
                src: userUpLoadImgNode.getAvatar($_serviceLibInfo.detail_cover)
            }).css({
                display: ''
            }).prev().css({
                display: 'none'
            });
            // 隐藏默认背景，展开用户背景
            defaultBgImgNode.css({
                display: 'none'
            }).next().css({
                display: ''
            })
        } else {
            // 勾选默认图片
            checkBoxNode.prop('checked', true);
            // 去除图片链接并隐藏
            userUpLoadImgNode.removeAttr('src').css({
                display: 'none'
            }).prev().css({
                display: ''
            });
            // 展示默认背景，隐藏用户背景
            defaultBgImgNode.css({
                display: ''
            }).next().css({
                display: 'none'
            })
        }
    };


    // 点击事件
    function handleClickEvent() {
        var nowTab = null;
        var nowTabName = '';
        var homeModel = $('#tab-index');
        var detailModel = $("#detail-data");
        var caseModel = $("#case-hall");
        var taskModel = $("#complete-task");
        var appraisal = $("#transaction-evaluate");
        // var editShop = $('.online-contact');
        var $_tabReleseTime = $('.case-hall-release-time');
        var $_attention = $('.case-hall-attention');
        var releseTimeAscending = true;
        var attentionAscending = false;
        var $_searchBtnSubmit = $('#case-hall-search-btn');
        var $_searchInput = $('#case-hall-search-input');
        var initSearchJson = {
            "order": "DESC",
            "filed": "created_at"
        }
        var setTimeRunphone;
        var setTimeRun;
        // 判断是否可以发送验证码
        var isCanClickSend = true;
        var isCanClickSendPhone = true;

        //案例展示tab页切换
        // 发布时间
        $_tabReleseTime.click(function () {
            $('.case-hall').find('.noData').hide();
            attentionAscending = false;
            $_attention.removeClass('div-active');
            $_attention.children().children().removeClass('lift-sort-icon__select');
            $(this).addClass('div-active');
            if (!releseTimeAscending) {
                $(this).children().find('.icon-up-triangle').removeClass('lift-sort-icon__select');
                $(this).children().find('.icon-down-triangle').addClass('lift-sort-icon__select');
                initSearchJson = {
                    "order": "DESC",
                    "filed": "created_at"
                }
            } else {
                $(this).children().find('.icon-down-triangle').removeClass('lift-sort-icon__select');
                $(this).children().find('.icon-up-triangle').addClass('lift-sort-icon__select');
                initSearchJson = {
                    "order": "ASC",
                    "filed": "created_at"
                }
            }
            releseTimeAscending = !releseTimeAscending;
            currentPage = 1;
            clickPage = 1;
            getSearchCaseDataPage(initSearchJson)
        })


        // 关注度
        $_attention.click(function () {
            $('.case-hall').find('.noData').hide();
            releseTimeAscending = false;
            $_tabReleseTime.removeClass('div-active');
            $_tabReleseTime.children().children().removeClass('lift-sort-icon__select');
            $(this).addClass('div-active');
            if (!attentionAscending) {
                $(this).children().find('.icon-up-triangle').removeClass('lift-sort-icon__select');
                $(this).children().find('.icon-down-triangle').addClass('lift-sort-icon__select');
                initSearchJson = {
                    "order": "DESC",
                    "filed": "click_rate"
                }
            } else {
                $(this).children().find('.icon-down-triangle').removeClass('lift-sort-icon__select');
                $(this).children().find('.icon-up-triangle').addClass('lift-sort-icon__select');
                initSearchJson = {
                    "order": "ASC",
                    "filed": "click_rate"
                }
            }
            attentionAscending = !attentionAscending;
            currentPage = 1;
            clickPage = 1;
            getSearchCaseDataPage(initSearchJson)
        })

        // 搜索框点击事件
        $_searchBtnSubmit.click(function () {
            var title = $_searchInput.val();
            if (!title) {
                layer.msg('搜索内容不能为空');
                return 0;
            }
            clickPage = 1;
            getSearchCaseDataPage(initSearchJson, title)
        })

        // 店铺名称失去焦点
        $('#shopName').on('blur', function () {
            if (!$('#shopName').val()) {
                $(this).next().find('.expert-info-error-tip').eq(0).text('店铺名称不能为空！').show();
                isSubmitShopName = false;
            } else {
                isSubmitShopName = true;
            }
        })

        $('#shopName').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 联系人失去焦点
        $('#concatPerson').on('blur', function () {
            if (!$('#concatPerson').val()) {
                $(this).next().find('.expert-info-error-tip').eq(0).text('联系人不能为空！').show();
                isSubmitConcatPerson = false;
            } else {
                isSubmitConcatPerson = true;
            }
        })

        $('#concatPerson').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 手机失去焦点
        $('#shopPhone').on('blur', function () {
            if (!$('#shopPhone').val()) {
                $(this).next().find('.expert-info-error-tip').eq(0).text('手机号码不能为空！').show();
                isSubmitShopPhone = false;
            } else {
                if (!(/^1[34578]\d{9}$/.test($('#shopPhone').val()))) {
                    $(this).next().find('.expert-info-error-tip').eq(0).text('手机号码错误，请重新输入！').show();
                    isSubmitShopPhone = false;
                } else {
                    isSubmitShopPhone = true;
                }
            }
        })

        $('#shopPhone').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 固话失去焦点
        $('#shopFixedPhone').on('blur', function () {
            if (!$('#shopFixedPhone').val()) {
            } else {
                if (!(/^([0-9]{3,4}-)?[0-9]{7,8}$/.test($('#shopFixedPhone').val()))) {
                    $(this).next().find('.expert-info-error-tip').eq(0).text('固话输入错误，请重新输入！').show();
                    isSubmitsShopFixedPhone = false;
                } else {
                    isSubmitsShopFixedPhone = true;
                }
            }
        })

        $('#shopFixedPhone').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 邮箱失去焦点
        $('#shopEmail').on('blur', function () {
            if (!$('#shopEmail').val()) {
            } else {
                if (!(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})$/.test($('#shopEmail').val()))) {
                    $(this).next().find('.expert-info-error-tip').eq(0).text('邮箱输入错误，请重新输入！').show();
                    isSubmitCodeEmail = false;
                    isSubmitShopEmail = false;
                } else {
                    isSubmitShopEmail = true;
                }
            }
        })

        $('#shopEmail').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // qq失去焦点
        $('#qq').on('blur', function () {
            if (!$('#qq').val()) {
            } else {
                if (!(/^[1-9][0-9]{4,10}$/.test($('#qq').val()))) {
                    $(this).next().find('.expert-info-error-tip').eq(0).text('qq号码输入错误，请重新输入！').show();
                    isSubmitqq = false;
                } else {
                    isSubmitqq = true;
                }
            }
        })

        $('#qq').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 官网失去焦点
        $('#internet').on('blur', function () {
            if (!$('#internet').val()) {
            } else {
                if (!(/^(?=^.{3,255}$)(http(s)?:\/\/)?(www\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\d+)*(\/\w+\.\w+)*$/.test($('#internet').val()))) {
                    $(this).next().find('.expert-info-error-tip').eq(0).text('网址格式错误，请重新输入！').show();
                    isSubmitInternet = false;
                } else {
                    isSubmitInternet = true;
                }
            }
        });

        $('#internet').on('focus', function () {
            $(this).next().find('.expert-info-error-tip').eq(0).hide();
        });

        // 点击图标跳到关于我们
        $('.service-lib-detail .about-us').click(function () {
            $('.tab .tab-item ').removeClass('active');
            $('.service-lib-detail .tab .tab-item').eq(3).addClass('active');
            // resetCurrentPageCase();
            $_tabActive = 1;
            detailModel.siblings().css("display", 'none');
            detailModel.css("display", 'block');
            // getDetailData();
            setDetailData();
        });

        $('.case-desc').on('blur', function () {
            if (!$('.case-desc').val()) {
                $(".shop-introduction-area .error-info").children(".error-info-content").css("display", 'block');
                isSubmitIntroduction = false;
            } else {
                isSubmitIntroduction = true;
            }
        });
        // 成果详情获得焦点
        $('.case-desc').on('focus', function () {
            $(".shop-introduction-area .error-info").children(".error-info-content").css("display", 'none');
        });

        richText.on('blur', function () {
            if (!richText.getData()) {
                $(".case-desc-area .error-info").children(".error-info-content").css("display", 'block');
                isSubmitDesc = false;
            } else {
                isSubmitDesc = true;
            }
        });
        // 成果详情获得焦点
        richText.on('focus', function () {
            $(".case-desc-area .error-info").children(".error-info-content").css("display", 'none');
        });

        $(document).on('click', '.addType', function () {
            var length = $('.edit-show .select-row').eq(1).find('.option-active').length;
            if (length === 3) {
                layer.msg("最多只能选择3个");
                return 0;
            }
            layer.prompt({title: '请输入擅长领域', formType: 2}, function (pass, index) {
                layer.close(index);
                $('.edit-show .select-row').eq(1).find('.addType').remove();
                var type = $('<span class="inline-block option option-active" style="position: relative">' + pass + '<i style="font-size: 16px;position: absolute;top: 0px;right: 5px" class="deleteType">×</i></span>');
                $('.edit-show .select-row').eq(1).find('.option-area').append(type);
                var span = $(' <span class="addType" style="width: 90px;display: inline-block;margin: 11px 15px 5px 0;height: 30px; line-height: 30px;border: 1px solid #0066cc;color: #0066cc;text-align: center;font-size: 30px;border-radius: 5px;cursor: pointer;margin-left:10px;">+</span>');
                $('.edit-show .select-row').eq(1).find('.option-area').append(span);
            })
        });

        // 公司logo 上传事件
        oCompanyLogoInputNode.off().on("change", function () {
            var files = $(this).get(0).files,
                img = new Image();
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
            bIsCuting = true;
            // 存储旧链接
            if (sOldPictureUrl === undefined) {
                if (oNowCutPictureImageNode.is(':hidden')) {
                    // 隐藏 + 号
                    // oNowCutPictureImageNode.show().prev().hide();
                    // 展示图片，直接隐藏 + 号
                    oNowCutPictureImageNode.show().parent().css({
                        fontSize: '0'
                    });
                    sOldPictureUrl = null;
                } else {
                    sOldPictureUrl = oNowCutPictureImageNode.get(0).src;
                }
            }
            // 存储新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oNowCutInstance.setNewImage(img);
            // 展示截图区域
            if (oCutInstanceArea.is(':hidden')) {
                oCutInstanceArea.show();
            }
            // 清空文本，保证下次能正常触发change
            oCompanyLogoInputNode.get(0).value = '';

        });
        // 点击发送手机验证码
        $('#shop-send-phone-code').off().click(function (e) {
            e.stopPropagation();
            if (isCanClickSendPhone) {
                isCanClickSendPhone = !isCanClickSendPhone
                if (!(/^1[34578]\d{9}$/.test($('#shopPhone').val()))) {
                    layer.msg("输入手机号码不正确，请重新输入");
                    return 0;
                } else {
                    $('#shop-send-phone-code').css('cursor', 'no-drop');
                    $('#phone-code-area').show();
                    new NewAjax({
                        url: '/message/pc/' + $('#shopPhone').val() + '/send_sms?pc=true',
                        contentType: 'application/json',
                        type: 'post',
                        success: function (res) {
                            if (res.status === 200) {
                                layer.msg("发送验证码成功");
                                setTimeRunphone = setInterval(function () {
                                    $('#shop-send-phone-code').html(isShopTimePhone);
                                    $('#shop-send-phone-code').css('backgroundColor', '#eee');
                                    isShopTimePhone--;
                                    if (isShopTimePhone < 0) {
                                        isCanClickSendPhone = true;
                                        $('#shop-send-phone-code').css('cursor', 'pointer');
                                        $('#shop-send-phone-code').css('backgroundColor', '#0066cc');
                                        clearInterval(setTimeRunphone);
                                        isShopTimePhone = 60;
                                        $('#shop-send-phone-code').html('发送验证码');
                                    }
                                }, 1000)
                            } else {
                                layer.msg("发送验证码失败");
                            }
                        }
                    })
                }
            }
        });
        // 点击发送邮箱验证码
        $('#shop-send-code').off().click(function (e) {
            e.stopPropagation();
            if (isCanClickSend) {
                isCanClickSend = !isCanClickSend
                if (!(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})$/.test($('#shopEmail').val()))) {
                    layer.msg("邮箱格式不对，请重新输入");
                    isCanClickSend = true;
                    return 0;
                } else {
                    $('#shop-send-code').css('cursor', 'no-drop');
                    $('#email-code-area').show();
                    new NewAjax({
                        url: '/message/pc/' + $('#shopEmail').val() + '/send_mail',
                        contentType: 'application/json',
                        type: 'post',
                        success: function (res) {
                            if (res.status === 200) {
                                layer.msg("发送验证码成功");
                                setTimeRun = setInterval(function () {
                                    $('#shop-send-code').html(isShopTime);
                                    $('#shop-send-code').css('backgroundColor', '#eee');
                                    isShopTime--;
                                    if (isShopTime < 0) {
                                        isCanClickSend = true;
                                        $('#shop-send-code').css('cursor', 'pointer');
                                        $('#shop-send-code').css('backgroundColor', '#0066cc');
                                        clearInterval(setTimeRun);
                                        isShopTime = 60;
                                        $('#shop-send-code').html('发送验证码');
                                    }
                                }, 1000);
                            } else {
                                layer.msg("发送验证码失败");
                            }
                        }
                    })
                }
            }
        });
        // 验证手机
        $('.send-phone-code').click(function () {
            if (!$('#phoneCode').val()) {
                layer.msg("验证码不能为空");
                isSubmitShopPhone = false;
                return 0;
            }
            var json = {
                phone_num: $('#shopPhone').val(),
                randCode: $('#phoneCode').val()
            }
            new NewAjax({
                url: '/f/user/pc/update_phone/reset_phone?pc=true',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(json),
                dataType: 'json',
                success: function (res) {
                    if (res.status == 200 && res.message == 'OK') {
                        layer.msg("绑定成功");
                        isCanClickSendPhone = true;
                        isSubmitCodePhone = true;
                        $('#shop-send-phone-code').css('cursor', 'pointer');
                        clearInterval(setTimeRunphone);
                        isShopTimePhone = 60;
                        $('#shop-send-phone-code').html('发送验证码');
                        $('#shopPhone').attr('disabled', true);
                        $('#phone-code-area').hide();
                        $('#shop-send-phone-code').hide();
                        var user = window.localStorage.getItem('user');
                        if (!!user) {
                            user = JSON.parse(user);
                            user.phone = $('#shopPhone').val();
                            user = JSON.stringify(user);
                            window.localStorage.setItem('user', user);
                        }
                        isSubmitShopPhone = true;
                    } else {
                        isCanClickSendPhone = true;
                        $('#shop-send-phone-code').css('cursor', 'pointer');
                        $('#shop-send-phone-code').css('backgroundColor', '#0066cc');
                        clearInterval(setTimeRunphone);
                        isShopTimePhone = 60;
                        $('#shop-send-phone-code').html('发送验证码');
                        layer.msg(res.message);
                        isSubmitShopPhone = false;
                    }
                },
                error: function (err) {
                    console.error(err);
                    isSubmitShopPhone = false;
                }
            })
        });
        // 邮箱发生改变
        /*$('#shopEmail').on('change', function () {
            $('#email-code-area').hide();
            $('#shop-send-code').show();
            isCanClickSend = true;
            isSubmitShopEmail = false;
        })*/
        // 验证邮箱
        $('.send-email-code').click(function () {
            if (!$('#emailCode').val()) {
                layer.msg("验证码不能为空");
                isSubmitShopEmail = false;
                return 0;
            }
            var json = {
                email: $('#shopEmail').val(),
                randCode: $('#emailCode').val()
            }
            new NewAjax({
                url: '/f/user/pc/update_email/reset_email',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(json),
                dataType: 'json',
                success: function (res) {
                    if (res.status == 200 && res.message == 'OK') {
                        layer.msg("绑定成功");
                        isCanClickSend = true;
                        isSubmitCodeEmail = true;
                        $('#shop-send-code').css('cursor', 'pointer');
                        clearInterval(setTimeRun);
                        isShopTime = 60;
                        $('#shop-send-code').html('发送验证码');
                        $('#shopEmail').attr('disabled', true);
                        $('#email-code-area').hide();
                        $('#shop-send-code').hide();
                        var user = window.localStorage.getItem('user');
                        if (!!user) {
                            user = JSON.parse(user);
                            user.email = $('#shopEmail').val();
                            user = JSON.stringify(user);
                            window.localStorage.setItem('user', user);
                        }
                        isSubmitShopEmail = true;
                    } else {
                        // isCanClickSend = true;
                        // $('#shop-send-code').css('cursor','pointer');
                        // clearInterval(setTimeRun);
                        // isShopTime = 60;
                        // $('#shop-send-code').html('发送验证码');
                        layer.msg(res.message);
                        isSubmitShopEmail = false;
                    }
                },
                error: function (err) {
                    console.error(err);
                    isSubmitShopEmail = false;
                }
            })
        });
        // 点击返回按钮回到店铺状态
        $('.edit-myshop-form-div-head-back').click(function () {
            console.log(providerResult);
            if (!!providerResult.back_check_status && !!providerId) {
                window.open('/f/' + providerId + '/provider_detail.html?pc=true')
            } else {
                layer.msg("");
                // layer.msg($publishCaseButtonTips);
            }
        });
        // 提交店铺信息按钮
        submitBtn.off().click(function () {
            if (bIsCuting) {
                layer.msg('请先完成截图');
                return 0;
            }
            if (!isSubmitShopEmail) {
                $('#shopEmail .expert-info-error-tip').eq(0).text('邮箱输入错误，请重新输入！').show();
            }
            if (!$('#shopName').val()) {
                $('#shopName').next().find('.expert-info-error-tip').eq(0).text('店铺名称不能为空！').show();
            }
            if (!$('#concatPerson').val()) {
                $('#concatPerson').next().find('.expert-info-error-tip').eq(0).text('联系人不能为空！').show();
            }
            if (!isSubmitShopPhone) {
                $('#shopPhone .expert-info-error-tip').eq(0).text('手机号码输入错误，请重新输入！').show();
            }
            if (!isSubmitsShopFixedPhone) {
                $('#shopFixedPhone .expert-info-error-tip').eq(0).text('固话输入错误，请重新输入！').show();
            }
            if (!isSubmitInternet) {
                $('#internet .expert-info-error-tip').eq(0).text('网址格式错误，请重新输入！').show();
            }
            if (!isSubmitqq) {
                $('#qq .expert-info-error-tip').eq(0).text('qq号码输入错误，请重新输入！').show();
            }
            if ($('.shop-logo-download-img').attr('src') == '') {
                layer.msg("请选择LOGO");
                isSumbit = false;
                return 0;
            }
            if (!richText.getData()) {
                layer.msg("正确填写信息");
                $(".case-desc-area .error-info").children(".error-info-content").css("display", 'block');
                isSubmitDesc = false;
                return 0;
            }
            if (!$('.prompt-desc-area .case-desc').val()) {
                layer.msg("正确填写信息");
                $(".prompt-desc-area .error-info").children(".error-info-content").css("display", 'block');
                isSubmitIntroduction = false;
                return 0;
            }

            /*console.log('isSubmitCodeEmail', isSubmitCodeEmail);
            console.log('isSubmitCodePhone', isSubmitCodePhone);
            console.log('isSubmitShopName', isSubmitShopName);
            console.log('isSubmitShopName', isSubmitConcatPerson);
            console.log('isSubmitShopPhone', isSubmitShopPhone);
            console.log('isSubmitsShopFixedPhone', isSubmitsShopFixedPhone);
            console.log('isSubmitShopEmail', isSubmitShopEmail);
            console.log('isSubmitInternet', isSubmitInternet);
            console.log('isSubmitqq', isSubmitqq);
            console.log('isSubmitIntroduction', isSubmitIntroduction);
            console.log('isSubmitDesc', isSubmitDesc);
            console.log($('#provinceName').val() !== '');
            console.log($('#cityName').val() !== '');
            console.log($('#districtName').val() !== '');*/
            if (isSubmitCodeEmail && isSubmitCodePhone && isSubmitShopName && isSubmitConcatPerson && isSubmitShopPhone && isSubmitsShopFixedPhone && isSubmitShopEmail && isSubmitInternet && isSubmitqq && isSubmitIntroduction && isSubmitDesc && $('#provinceName').val() !== '' && $('#cityName').val() !== '' && $('#districtName').val() !== '') {
            } else {
                layer.msg("请正确填写信息");
                isSumbit = false;
                return 0;
            }
            $_applicationIndustryId = [];
            if (searchData.industry === undefined) {
                layer.msg("请选择行业");
                isSumbit = false;
                return 0;
            }
            if (!isSumbit) {
                isSumbit = true;
                var json = {};
                if (!!$('#shopName').val()) {
                    json.name = $('#shopName').val()
                }
                if (!!$('#concatPerson').val()) {
                    json.contact = $('#concatPerson').val()
                }
                if (!!$('#shopPhone').val()) {
                    json.phone = $('#shopPhone').val()
                }
                if (!!$('#shopFixedPhone').val()) {
                    json.fixedTelephone = $('#shopFixedPhone').val()
                }
                if (!!$('#shopEmail').val()) {
                    json.email = $('#shopEmail').val()
                }
                if (!!$('#qq').val()) {
                    json.contactQq = $('#qq').val()
                }
                if (!!$('#internet').val()) {
                    json.officialWebsite = $('#internet').val()
                }
                if (!!$('#provinceName').val()) {
                    json.provinceName = $('#provinceName').val()
                }
                if (!!$('#cityName').val()) {
                    json.cityName = $('#cityName').val()
                }
                if (!!$('#districtName').val()) {
                    json.districtName = $('#districtName').val()
                }
                if (!!$('#provinceName').val() && !!$('#cityName').val() && !!$('#districtName').val()) {
                    json.address = $('#provinceName').val() + $('#cityName').val() + $('#districtName').val();
                }
                if (!!$('#moreArea').val()) {
                    json.addressDetail = $('#moreArea').val();
                }
                /*json.skilledLabel = $('.edit-show .select-area .select-row').eq(0).find('.option-active').attr('data-id');
                json.skilledField = JSON.stringify($_applicationIndustryId);*/
                json.industryId = searchData.industry;
                if (searchData.subIndustry) {
                    json.subIndustryId = searchData.subIndustry;
                }
                json.introduction = $('.case-desc').val();
                json.mainBusiness = richText.getData();
                json.honor = glory.getData();
                json.logo = $_logo ? $_logo : $('.shop-logo-download-img').attr('src').split('/')[3];
                if (!!providerResult.id) {
                    json.id = providerResult.id;
                }
                new NewAjax({
                    url: '/f/serviceProvidersCheckRecords/pc/create_update?pc=true',
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(json),
                    success: function (res) {
                        if (res.status === 200) {
                            layer.msg("提交成功");
                            // 消除重要操作
                            set_IMPORTANTOPERATION(false);
                            // 重新初始化数据
                            _this.initData();
                        } else {
                            console.log('提交失败');
                            isSumbit = false;
                        }
                    },
                    error: function (err) {
                        isSumbit = false;
                        console.log(err)
                    }
                })
            }
        });
        // 处理更多、收起操作
        $(".select-row .open-more").click(function () {
            var optionArea = $(this).siblings(".option-area");
            if ($(optionArea).hasClass("option-area-hidden")) {
                $(optionArea).removeClass("option-area-hidden");
                $(this).html('收起<i class=\"icon-close-arrow\"></i>');
            } else {
                $(optionArea).addClass("option-area-hidden");
                $(this).html('更多<i class=\"icon-open-arrow\"></i>');
            }
        });
        // 处理点击类型筛选
        $(".edit-show .select-row").eq(0).find(".option-area .option").off().on("click", function () {
            $(this).siblings(".option").removeClass("option-active");
            $(this).addClass("option-active");
            if ($(this).parent().siblings(".row-label").children(".label-title").html() === (searchType[0].name + ':')) {
                $_industryFieldId = $(this).data('id');
            }
        });

        $(".edit-show .select-row").eq(1).find(".option-area .option").off().on("click", function () {
            var length = $(".edit-show .select-row").eq(1).find(".option-active").length;
            if ($(this).hasClass('option-active')) {
                $(this).removeClass('option-active');
            }
            else {
                if (length > 2) {
                    layer.msg("最多只能选择3个");
                    return 0;
                } else {
                    $(this).addClass('option-active');
                }
            }
        });

        $('.tab-index .more-case').click(function () {
            $('.tab .tab-item ').removeClass('active');
            $('.service-lib-detail .tab .tab-item').eq(1).addClass('active');
            resetCurrentPageCase();
            $_tabActive = 2;
            caseModel.siblings().css("display", 'none');
            caseModel.css("display", 'block');
            getSearchCaseDataPage(initSearchJson);
        })
        // 切换排序tab
        $(".service-lib-detail .tab .tab-item").on("click", function () {
            nowTab = $(this).eq(0);
            nowTab.siblings().removeClass("active");
            nowTab.addClass("active");
            nowTabName = nowTab.attr('name');
            if (nowTabName === "detail") {
                $_tabActive = 1;
                detailModel.siblings().css("display", 'none');
                detailModel.css("display", 'block');
                // getDetailData();
                setDetailData();
            } else if (nowTabName === "case") {
                resetCurrentPageCase();
                $_tabActive = 2;
                caseModel.siblings().css("display", 'none');
                caseModel.css("display", 'block');
                getSearchCaseDataPage(initSearchJson);
            } else if (nowTabName === "task") {
                resetCurrentPageComplete();
                $_tabActive = 3;
                taskModel.siblings().css("display", 'none');
                taskModel.css("display", 'block');
                getCompleteDataPage();
            } else if (nowTabName === "appraisal") {
                resetCurrentPageTransaction();
                // 重设默认显示好评
                $(".middle-evaluate div:first-child").addClass("evaluate-button-active").removeClass("evaluate-button").siblings().addClass("evaluate-button").removeClass("evaluate-button-active");
                evaluationStandard = 0;
                $_tabActive = 4;
                appraisal.siblings().css("display", 'none');
                appraisal.css("display", 'block');
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            } else if (nowTabName === 'home') {
                $_tabActive = 0;
                homeModel.siblings().css("display", 'none');
                homeModel.css("display", 'block');
                getNewCaseDatapage();
            }
        });
        // 监听案例展示分页跳转
        $(".case-hall #pageToolbar").on("click", function () {
            if (nowTab.data('currentpage') != currentPage) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getSearchCaseDataPage(initSearchJson);
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getSearchCaseDataPage(initSearchJson);
            }
        });
        // 监听完成任务分页跳转
        $(".complete-task #pageToolbar").on("click", function () {
            if ($("#pageToolbar").data('currentpage') != currentPage) {

                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getCompleteDataPage();
                // getServiceList($_typeId, $("#search-input").val());
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getCompleteDataPage();
            }
        });
        // 监听交易评价分页跳转
        $(".transaction-evaluate #pageToolbar").on("click", function () {
            if ($("#pageToolbar").data('currentpage') != currentPage) {

                clickPage = 0;
                currentPage = $("#pageToolbar").data('currentpage');
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $("#pageToolbar").data('currentpage');
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            }
        });
        // 切换交易评价按钮
        $(".middle-evaluate div").on("click", function () {
            $(this).addClass("evaluate-button-active").removeClass("evaluate-button").siblings().addClass("evaluate-button").removeClass("evaluate-button-active");
            // resetCurrentPage();
            if ($(this)[0].innerText === "中评") {
                clickPage = 1;
                currentPage = 1;
                evaluationStandard = 1;
                evaluationType = "中评";
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            } else if ($(this)[0].innerText === "差评") {
                clickPage = 1;
                currentPage = 1;
                evaluationStandard = 2;
                evaluationType = "差评";
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            } else {
                clickPage = 1;
                currentPage = 1;
                evaluationStandard = 0;
                evaluationType = "好评";
                if (!!providerResult['provider_id']) {
                    getTransactionDataPage();
                } else {
                    $('.transaction-evaluate-bottom').hide();
                }
            }
        });

        // editShop.click(function () {
        //     // 这两个变量在authentication.html定义，为注入变量
        //     $('.service-lib-detail').hide();
        //     $('.edit-show').show();
        // })

        /*** 完成任务跳转到需求大厅详情页 ***/

        /*** 完成任务跳转到需求大厅详情页 ***/
        $(".complete-task-card").on("click", function () {
            var id = $(this).data('id');
            window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
        });
        /*** 案例展示跳转到案例大厅详情页 ***/
        $(".solution-card").on("click", function () {
            var id = $(this).data('id');
            window.location.href = '/f/' + id + '/case_detail.html';
        });

        // 复选框点击事件
        eventOfCheckBoxChange();

        // 图片区域点击事件
        eventOfImageAreaClick();

        // 文件change事件
        eventOfFileInputChange();

        // 编辑按钮点击事件
        eventOfEditShowBtnClick();

        // 编辑提交按钮点击事件
        /*eventOfEditSubmitBtnClick();*/

        // 编辑取消按钮点击事件
        eventOfEditCancelBtnClick();

        /* 截图事件 */
        cutAvatarBtnEvent();
    }

    // 初始化富文本
    function initRichText() {
        var config = {
            resize_enabled: false,
            autoUpdateElement: true,
            height: 300,
            toolbarGroups: [
                {name: 'document', groups: []},
                {name: 'clipboard', groups: []},
                {name: 'editing', groups: []},
                {name: 'forms', groups: ['forms']},
                '/',
                {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
                {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
                {name: 'links', groups: ['links']},
                {name: 'insert', groups: ['insert']},
                '/',
                {name: 'styles', groups: ['styles']},
                {name: 'colors', groups: ['colors']},
                {name: 'tools', groups: ['tools']},
                {name: 'others', groups: ['others']},
                {name: 'about', groups: ['about']}
            ]
        };
        //初始化富文本插件
        richText = CKEDITOR.replace('ckedit', config);
        glory = CKEDITOR.replace('ckedit2', config);
    }

    // 文件上传
    function uploadFile(files, callback) {
        var formData = null;
        if (files instanceof FormData) {
            formData = files;
        } else {
            if (files.length === 0) {
                return 0; //如果文件为空
            }
            formData = new FormData();
            for (var i = 0; i < files.length; i++) {
                formData.append('files', files[i]);
            }
        }
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: formData,
            async: true,
            processData: false,
            contentType: false,
            success: function (res) {
                if (callback) {
                    callback(res)
                }
            },
            error: function (err) {
                console.error("上传头像：" + err)
            }
        });
    }

    // 初始化类型筛选
    function initTypeSelectModel() {
        // 判定是否加载了插件
        if (window.TypeSearch === undefined) {
            $.getScript("/static/front/js/fragments/selectTypeSearch.js", function () {
                createTypeList();
            });
        } else {
            createTypeList();
        }
    }

    // 创建选择列表
    function createTypeList() {
        if (!(typeSearch instanceof TypeSearch)) {
            // 引入搜索组件
            typeSearch = new TypeSearch();
        }
        // 初始化头部类型多选框
        typeSearch.canSetDefaultSelectOption(true)
            .setData(typeSearchData, false)
            .setBottomModelEnable(false)
            .setClickCallback(function (node, parentNode) {
                var optionId = node.data().id;
                var typeName = parentNode.attr('type');
                // 多选的情况
                if (searchData[typeName] instanceof Array) {
                    var index = $.inArray(optionId, searchData[typeName]);
                    // 若是不限的id
                    if ($.inArray(optionId, selectAllIdArr) > -1) {
                        searchData[typeName] = [optionId]
                    } else {
                        // 若存在不限的id，则情况数组
                        if ($.inArray(searchData[typeName][0], selectAllIdArr) > -1) {
                            searchData[typeName] = []
                        }
                        if (index > -1) {
                            searchData[typeName].splice(index, 1)
                        } else {
                            searchData[typeName].push(optionId)
                        }
                    }
                } else { // 非多选的情况
                    if (typeName === 'industry' && searchData.subIndustry) {
                        searchData.subIndustry = undefined;
                        delete searchData.subIndustry;
                    }
                    // 记录点击的分类数据
                    searchData[typeName] = optionId;
                }
            });
    }

    // 更新案例展示solutionCard的样式
    function updateSolutionCard() {
        $(".case-hall .solution-card .solution-card-content .detail").css("display", 'none');
        $(".case-hall .solution-card .solution-card-content .browse-money").css("display", 'block');
        $(".case-hall .solution-card .solution-card-content .browse-money .money").css("display", 'inline');
        // $(".case-hall .solution-card .type-area").append("<div class='type-area'><span>应用行业：</span><span class='application-content'>暂无</span></div>");
        // $(".case-hall .solution-card .type-area").css("height", "43px");
    }

    // 写入详细资料
    function setDetailData() {
        // 公司名(大标题)
        $(".detail-data .big-title").html(valueFilter($_serviceLibInfo['name'], '暂无数据')); //名字
        // 能力
        $(".transaction-num").html(valueFilter(($_serviceLibInfo['total_transaction'] !== 0 && $_serviceLibInfo['total_transaction']) ? $_serviceLibInfo['total_transaction'] + '万元' : '暂无数据', '暂无数据')); //交易总额
        $(".package-num").html(valueFilter(($_serviceLibInfo['task_amount'] !== 0 && $_serviceLibInfo['task_amount']) ? $_serviceLibInfo['task_amount'] : '暂无数据', '暂无数据')); //接包数
        $(".evaluate-num").html(($_serviceLibInfo['favorable_rate'] !== undefined && $_serviceLibInfo['favorable_rate'] !== null && $_serviceLibInfo['favorable_rate'] !== '' && $_serviceLibInfo['favorable_rate'] !== 0) ? $_serviceLibInfo['favorable_rate'] + '%' : '暂无数据'); //好评率
        $(".capacity-num").html(valueFilter($_serviceLibInfo['qualification'], '暂无数据')); //能力值
        // 联系方式
        $(".area-content").html(valueFilter($_serviceLibInfo['address'], '暂无数据')); //所属地区
        $(".telephone-content").html(valueFilter($_serviceLibInfo['fixed_telephone'], '暂无数据')); //电话
        $(".address-content").html(valueFilter($_serviceLibInfo['address_detail'], '暂无数据')); //详细地址
        if (!!$_serviceLibInfo['phone']) {
            $(".phone-content").html(valueFilter($_serviceLibInfo['phone'], '暂无数据')); //手机
            $(".phone-authentication i").removeClass("icon-false").addClass("icon-true");
        } else {
            $(".phone-content").html('暂无数据'); //手机
            $(".phone-authentication i").removeClass("icon-true").addClass("icon-false");
        }
        if (!!$_serviceLibInfo['contact']) {
            // var userInfo = JSON.parse($_serviceLibInfo['user_id']);
            $(".contacts-content").html($_serviceLibInfo['contact']); //联系人
            if (!!$_serviceLibInfo['email']) {
                $(".email-content").html($_serviceLibInfo['email']); //邮箱
                $(".email-authentication i").removeClass("icon-false").addClass("icon-true");
            }
        }
        // if (!!$_serviceLibInfo['official_website']){
        //     $(".wechat").attr("src", $(this).getAvatar($_serviceLibInfo['official_website']));
        //     $(".weixin-authentication i").removeClass("icon-false").addClass("icon-true");
        // } else {
        //     $(".weixin-authentication i").removeClass("icon-true").addClass("icon-false");
        // }
        $(".qq-content").html(valueFilter($_serviceLibInfo['contact_qq'], '暂无数据')); //QQ
        $(".contact-way-weixin-content").html(valueFilter($_serviceLibInfo['official_website'], '暂无数据')); //QQ
        // 服务商简介
        $(".service-lib-introduction-content").html(valueFilter($_serviceLibInfo['introduction'], '暂无数据'));
        // 主营业务
        $(".main-business-content").html(valueFilter($_serviceLibInfo['main_business'], '暂无数据'));
        $(".glory-qualifications-content").html(valueFilter($_serviceLibInfo['honor'], '暂无数据'));
    }

    // 请求最新案例数据
    function getNewCaseDatapage() {
        var json = {
            "providerId": providerResult.provider_id,
            "pager": {
                "current": 1,
                "size": 8
            }
        };
        new NewAjax({
            url: '/f/matureCase/get_lasted_mature_case?pc=true',
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_list;
                setNewCaseData(list);
            }
        })
    }

    // 根据搜索条件请求案例数据
    function getSearchCaseDataPage(type, title) {
        if (!!providerResult.provider_id) {
            var json = {
                "providerId": providerResult.provider_id,
                "title": title,
                "pager": {
                    "current": currentPage,
                    "size": 12
                },
            }
            json.sortPointers = []
            if (!!type) {
                json.sortPointers.push(type)
            }
            new NewAjax({
                type: "POST",
                url: "/f/matureCase/query?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    var list = res.data.data_list;
                    var totalRecord = res.data.total;
                    if (list.length > 0) {
                        $('.case-hall').find('#splitpage').show();
                        $(".case-hall .case-hall-list").css("display", "block");
                        $(".case-hall .noData").css("display", "none");
                        if (clickPage === 0) {
                            $('.case-hall #pageToolbar').Paging({
                                pagesize: searchSizeCase,
                                count: totalRecord,
                                toolbar: true
                            });
                            $('.case-hall #pageToolbar').find("div:eq(1)").remove();
                        } else {
                            $('.case-hall #pageToolbar').Paging({
                                pagesize: searchSizeCase,
                                count: totalRecord,
                                toolbar: true
                            });
                            $('.case-hall #pageToolbar').find("div:eq(0)").remove();
                            clickPage = 0;
                        }
                    } else {
                        $('.case-hall').find('#splitpage').hide();
                        $(".case-hall .noData").css("display", "block");
                        $(".case-hall .case-hall-list").css("display", "none");
                    }
                    setCaseData(list);
                }
            })
        } else {
            $('.case-hall').find('#splitpage').hide();
            $(".case-hall .noData").css("display", "block");
            $(".case-hall .case-hall-list").css("display", "none");
        }
    }

    // 请求案例数据
    function getCaseDataPage() {
        var json = {
            "id": providerResult.provider_id,
            "pager": {
                "current": currentPage,
                "size": searchSizeCase
            }
        };
        new NewAjax({
            type: "POST",
            url: "/f/serviceProviders/get_detail_provider_case?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_object;
                var totalRecord = res.data.total;
                if (list.length > 0) {
                    if (clickPage === 0) {
                        $('.case-hall #pageToolbar').Paging({
                            pagesize: searchSizeCase,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.case-hall #pageToolbar').find("div:eq(1)").remove();
                    } else {
                        $('.case-hall #pageToolbar').Paging({
                            pagesize: searchSizeCase,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.case-hall #pageToolbar').find("div:eq(0)").remove();
                        clickPage = 0;
                    }
                } else {
                    $(".case-hall .noData").css("display", "block");
                }
                setCaseData(list);
            }
        })
    }

    // 写入最细案例数据
    function setNewCaseData(list) {
        var caseHallCard = $(".tab-index .solution-card");
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                $(caseHallCard[i]).attr("data-id", list[i]['id']); // id
                $(caseHallCard[i]).find(".type-content").html(JSON.parse(list[i]['application_industry'])[0]['title']);  // 类型
                $(caseHallCard[i]).find(".belong-class-name").html(JSON.parse(list[i]['skilled_label'])['title']);  // 行业领域
                $(caseHallCard[i]).find(".text-overflow").html(list[i]['title']); // 大标题
                $(caseHallCard[i]).find(".text-overflow").attr('title', list[i]['title']); // 大标题
                $(caseHallCard[i]).find(".dock-num").html(list[i]['click_rate']); // 点击数
                if (list[i]['case_money'] !== 0) {
                    $(caseHallCard[i]).find(".money-area .money").html('￥' + list[i]['case_money'] + ' 万元'); // 金额
                } else {
                    $(caseHallCard[i]).find(".money-area .money").html('￥' + ' 面议'); // 金额
                }
                if (!!list[i]['picture_cover']) {
                    $(caseHallCard[i]).find(".solution-card-image").attr("src", $(this).getAvatar(parseInt(list[i]['picture_cover'].split(',')[0])));
                }
            }
            for (var j = list.length; j < 8; j++) {
                $('.tab-index .solution-card').eq(j).hide();
            }
        } else {
            $('.tab-index .solution-card').hide();
            $('.new-case-nodata').remove();
            var div = $('<div class="new-case-nodata" style="width: 100%;height: 300px;line-height: 300px;text-align: center;font-size: 25px">暂无数据</div>');
            $('.tab-index-new-case-content').append(div);
        }
    }

    // 写入案例数据
    function setCaseData(list) {
        var caseHallCard = $(".case-hall .solution-card");
        for (var k = 0; k < caseHallCard.length; k++) {
            if (list.length < 12 && k >= list.length) {
                $(caseHallCard[k]).css("display", 'none')
            } else {
                $(caseHallCard[k]).css("display", 'inline-block')
            }
        }
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                $(caseHallCard[i]).attr("data-id", list[i]['id']); // id
                $(caseHallCard[i]).find(".type-content").html(JSON.parse(list[i]['application_industry'])[0]['title']);  // 类型
                $(caseHallCard[i]).find(".belong-class-name").html(JSON.parse(list[i]['skilled_label'])['title']);  // 行业领域
                $(caseHallCard[i]).find(".text-overflow").html(list[i]['title']); // 大标题
                $(caseHallCard[i]).find(".browse-Number").html(list[i]['click_rate']); // 点击数
                if (list[i]['case_money'] !== 0) {
                    $(caseHallCard[i]).find(".money-part .money").html('￥' + list[i]['case_money'] + ' 万元'); // 金额
                } else {
                    $(caseHallCard[i]).find(".money-part .money").html('￥' + ' 面议'); // 金额
                }
                if (!!list[i]['picture_cover']) {
                    $(caseHallCard[i]).find(".solution-card-image").attr("src", $(this).getAvatar(list[i]['picture_cover']).split(',')[0]);
                }
            }
        }
    }

    // 获取完成任务数据
    function getCompleteDataPage() {
        var json = {
            "id": $_serviceLibInfo.id,
            "pager": {
                "current": currentPage,
                "size": searchSize
            }
        };
        new NewAjax({
            type: "POST",
            url: "/f/serviceProviders/get_detail_mission_page",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_object;
                var totalRecord = res.data.total;
                if (list.length > 0) {
                    if (clickPage === 0) {
                        $('.complete-task #pageToolbar').Paging({
                            pagesize: searchSize,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.complete-task #pageToolbar').find("div:eq(1)").remove();
                    } else {
                        $('.complete-task #pageToolbar').Paging({
                            pagesize: searchSize,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.complete-task #pageToolbar').find("div:eq(0)").remove();
                        clickPage = 0;
                    }
                } else {
                    $(".complete-task .noData").css("display", "block");
                }
                setCompleteData(list);
            }
        })
    }

    // 写入完成任务数据
    function setCompleteData(list) {
        var completeTaskCard = $(".complete-task-card");
        for (var k = 0; k < completeTaskCard.length; k++) {
            if (list.length < 5 && k >= list.length) {
                $(completeTaskCard[k]).css("display", 'none');
            } else {
                $(completeTaskCard[k]).css("display", 'block');
            }
        }
        for (var i = 0; i < list.length; i++) {
            $(completeTaskCard[i]).attr("data-id", list[i]['id']); // id
            $(completeTaskCard[i]).find(".title").html(list[i]['name']);  // 标题
            $(completeTaskCard[i]).find(".content").html(list[i]['illustration']);  // 内容
            $(completeTaskCard[i]).find(".time").html('截止报名时间：' + getMyDate(list[i]['deadline']));  // 最后期限
        }
    }

    // 获取评估数据
    function getTransactionDataPage() {
        var json = {
            "providerId": $_serviceLibInfo.provider_id,
            "evaluationType": 202103,
            "pager": {
                "current": currentPage,
                "size": searchSizeTransaction
            }
        };
        switch (evaluationStandard) {
            case 0:
                json.evaluationType = 202103;
                break;
            case 1:
                json.evaluationType = 202104;
                break;
            case 2:
                json.evaluationType = 202105;
                break;
        }
        new NewAjax({
            type: "POST",
            url: "/f/serviceProvidersEvaluation/get_provider_evaluation_page?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_object;
                if (list !== null && list.length > 0) {
                    $(".transaction-evaluate .noData").css("display", "none");
                    $('.transaction-evaluate #splitpage').css("display", "block");
                    if (clickPage === 0) {
                        $('.transaction-evaluate #pageToolbar').Paging({
                            pagesize: searchSizeTransaction,
                            count: list[0].total_comments,
                            toolbar: true
                        });
                        $('.transaction-evaluate #pageToolbar').find("div:eq(1)").remove();
                    } else {
                        $('.transaction-evaluate #pageToolbar').Paging({
                            pagesize: searchSizeTransaction,
                            count: list[0].total_comments,
                            toolbar: true
                        });
                        $('.transaction-evaluate #pageToolbar').find("div:eq(0)").remove();
                        clickPage = 0;
                    }
                    $(".transaction-evaluate .noData").css("display", "none");
                    setTransactionData(list);
                } else {
                    // 表示当前级别的没有数据
                    $('.transaction-evaluate-middle').eq(0).css({
                        display: ''
                    });
                    $('.transaction-evaluate-bottom').css({
                        display: 'none'
                    });
                    // $('.transaction-evaluate').eq(0).html('暂无数据')
                    $(".transaction-evaluate .noData").css("display", "block");
                    $('.transaction-evaluate #splitpage').css("display", "none");
                }
            }
        })
    }

    // 写入评估数据
    function setEvaluationHeadData() {
        if ($_evaluationHead !== null) {
            if ($_evaluationHead['favorable_rate']) {
                $(".praise-rate-num").html($_evaluationHead['favorable_rate'] + '%'); // 好评率
            } else {
                $(".praise-rate-num").html('暂无数据'); // 好评率
            }

            if ($_evaluationHead['total_comments']) {
                $(".evaluate-total-num").html($_evaluationHead['total_comments']); // 评价总数
            } else {
                $(".evaluate-total-num").html('暂无数据'); // 评价总数
            }

            if ($_evaluationHead.work_speed_star) {
                var work_speed_star = $(".average-speed-light"); // 平均工作速度
                $(work_speed_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead.work_speed_star);
                });
            } else {
                $(".average-speed-light").css("display", "none");
                $(".average-speed-normal").css("display", "none");
                $(".speed-no-comment").css("display", "block");
            }

            if ($_evaluationHead.work_quality_star) {
                var work_quality_star = $(".average-quality-light");  // 平均工作质量
                $(work_quality_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead.work_quality_star);
                });
            } else {
                $(".average-quality-light").css("display", "none");
                $(".average-quality-normal").css("display", "none");
                $(".quality-no-comment").css("display", "block");
            }

            if ($_evaluationHead.work_attitude_star) {
                var work_attitude_star = $(".average-attitude-light");  // 平均工作态度
                $(work_attitude_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead.work_attitude_star);
                });
            } else {
                $(".average-attitude-light").css("display", "none");
                $(".average-attitude-normal").css("display", "none");
                $(".attitude-no-comment").css("display", "block");
            }
        } else {
            $(".praise-rate-num").html('暂无数据'); // 好评率

            $(".evaluate-total-num").html('暂无数据'); // 评价总数

            $(".average-speed-light").css("display", "none");
            $(".average-speed-normal").css("display", "none");
            $(".speed-no-comment").css("display", "block");

            $(".average-quality-light").css("display", "none");
            $(".average-quality-normal").css("display", "none");
            $(".quality-no-comment").css("display", "block");

            $(".average-attitude-light").css("display", "none");
            $(".average-attitude-normal").css("display", "none");
            $(".attitude-no-comment").css("display", "block");
        }
    }

    // 写入交易数据
    function setTransactionData(list) {
        var transactionEvaluateCard = $(".transaction-evaluate-bottom");
        // 隐藏不需要的元素
        for (var k = 0; k < transactionEvaluateCard.length; k++) {
            if (list.length < 9 && k >= list.length) {
                $(transactionEvaluateCard[k]).css("display", 'none')
            } else {
                $(transactionEvaluateCard[k]).css("display", 'block')
            }
        }
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                if (list[i]['user_info']) {
                    // 头像
                    if ($.parseJSON(list[i]['user_info'])['avatar']) {
                        var avatarLeft = $(transactionEvaluateCard[i]).find(".avatar-information-left");
                        avatarLeft.attr('src', avatarLeft.getAvatar($.parseJSON(list[i]['user_info'])['avatar']));
                    }
                    // 图片
                    if ($.parseJSON(list[i]['user_info'])['user_name']) {
                        $(transactionEvaluateCard[i]).find(".avatar-name").html($.parseJSON(list[i]['user_info'])['user_name']);
                    }
                }
                $(transactionEvaluateCard[i]).find(".technology-name").html(list[i]['project_name']); // 名称
                $(transactionEvaluateCard[i]).find(".avatar-time").html(getMyDate(list[i]['created_at'])); // 日期
                $(transactionEvaluateCard[i]).find(".evaluation-describe").html(list[i]['comments']); // 评价
                $(transactionEvaluateCard[i]).find(".good-evaluation-text").html(evaluationType); // 评价分类
                if ($_evaluationHead) {
                    if ($_evaluationHead['work_quality_star']) {
                        // 评分星星
                        $(transactionEvaluateCard[i]).find(".good-evaluation-star-light").css("width", function () {
                            return Math.floor(146 * list[i].start_level);
                        });
                    } else {
                        $(transactionEvaluateCard[i]).find(".good-evaluation-star").css("display", "none");
                        $(transactionEvaluateCard[i]).find(".good-evaluation-star-light").css("display", "none");
                        $(transactionEvaluateCard[i]).find(".evaluation-no-comment").css("display", "block");
                    }
                } else {
                    $(transactionEvaluateCard[i]).find(".good-evaluation-star").css("display", "none");
                    $(transactionEvaluateCard[i]).find(".good-evaluation-star-light").css("display", "none");
                    $(transactionEvaluateCard[i]).find(".evaluation-no-comment").css("display", "block");
                }
            }
        }
    }

    /*** 复原案例展示currentPage ***/
    function resetCurrentPageCase() {
        $('.case-hall #pageToolbar').data('currentpage', 1);
        currentPage = 1;
        clickPage = 0;//hrz
        $('.case-hall #pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
    }

    /*** 复原完成任务currentPage ***/
    function resetCurrentPageComplete() {
        $('.complete-task #pageToolbar').data('currentpage', 1);
        currentPage = 1;
        clickPage = 0;//hrz
        $('.complete-task #pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
    }

    /*** 复原交易评价currentPage ***/
    function resetCurrentPageTransaction() {
        $('.transaction-evaluate #pageToolbar').data('currentpage', 1);
        currentPage = 1;
        clickPage = 0;//hrz
        $('.transaction-evaluate #pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
    }

    // 资料修改
    function submitEditData(callback) {
        // 遍历变量是否存在非法字符
        Object.keys(editData).forEach(function (key) {
            editData[key] = (editData[key] === undefined || editData[key] === '') ? null : editData[key]
        });
        editData.id = $_serviceLibInfo.id;
        // 修改商户首页请求
        new NewAjax({
            url: '/f/serviceProviders/create_update?pc=true',
            contentType: 'application/json',
            type: 'post',
            data: JSON.stringify(editData),
            success: function (res) {
                if (res.status === 200 && callback) {
                    callback(res.data)
                }
            },
            error: function (err) {
                console.error('修改我的店铺首页_err:' + err)
            }
        })
    }

    // 复选框选中事件
    function eventOfCheckBoxChange() {
        // 获取复选框节点
        var checkNode = $('#shopBgImageSelect');
        // 获取上传节点
        var uploadDiv = $('.shopHomeBgImageUpload').eq(0);
        // 获取默认背景图预览节点
        var defaultBgImgShowNode = $('.shopHomeDefaultBgImage').eq(0);
        // change监听
        checkNode.off().change(function () {
            if (checkNode.is(':checked')) {
                defaultBgImgShowNode.css({
                    display: ''
                });
                uploadDiv.css({
                    display: 'none'
                })
            } else {
                defaultBgImgShowNode.css({
                    display: 'none'
                });
                uploadDiv.css({
                    display: ''
                })
            }
        })
    }

    // 图片区域点击事件
    function eventOfImageAreaClick() {
        var inputNode = $('#shopBgImageUpload');
        var imageArea = inputNode.prev();
        imageArea.off().click(function () {
            inputNode.click()
        })
    }

    // 获取fileInput的上传事件
    function eventOfFileInputChange() {
        var fileInput = $('#shopBgImageUpload');
        var imgNode = fileInput.prev().find('img').eq(0);
        fileInput.off().change(function () {
            uploadFile(fileInput.get(0).files, function (data) {
                // 存入富文本信息
                editData.detailCover = data[0].id;
                editData.personalizedHomepage = undefined;
                // 提交修改信息
                submitEditData(function () {
                    // 写入url
                    imgNode.attr({
                        src: imgNode.getAvatar(data[0].id)
                    }).css({
                        display: ''
                    }).prev().css({
                        display: 'none'
                    })
                })
            })
        })
    }

    // 展示区域编辑按钮点击监听
    function eventOfEditShowBtnClick() {
        var showArea = editBtn.parent();
        var editArea = showArea.next();
        var showHtml = '';
        editBtn.off().on('click', function () {
            showHtml = editBtn.prev().html();
            richText.setData(showHtml);
            showArea.css({
                display: 'none'
            });
            editArea.css({
                display: ''
            })
        })
    }

    // 编辑区域提交按钮点击事件
    /*function eventOfEditSubmitBtnClick() {
        var editArea = submitBtn.parent();
        var showArea = editArea.prev();
        submitBtn.off().click(function () {
            // 获取富文本信息
            saveNewHomeContent = richText.getData();
            // 存入富文本信息
            editData.personalizedHomepage = saveNewHomeContent;
            // 提交修改信息
            submitEditData(function() {
                // 更新展示内容
                shopHomeShowNode.html(saveNewHomeContent);
                // 关闭编辑模式
                editArea.css({
                    display: 'none'
                });
                // 开启展示模式
                showArea.css({
                    display: ''
                })
            })
        })
    }*/

    // 编辑区域取消按钮点击事件
    function eventOfEditCancelBtnClick() {
        var cancelBtn = $('#shopHomeEditCancelBtn');
        var editArea = cancelBtn.parent();
        var showArea = editArea.prev();
        cancelBtn.off().on('click', function () {
            editArea.css({
                display: 'none'
            });
            showArea.css({
                display: ''
            })
        })
    }

    // 时间戳转日期
    function getMyDate(str) {
        var oDate = new Date(str),
            oYear = oDate.getFullYear(),
            oMonth = oDate.getMonth() + 1,
            oDay = oDate.getDate(),
            oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
        return oTime;
    }

    //补0操作
    function getzf(num) {
        if (parseInt(num) < 10) {
            num = '0' + num;
        }
        return num;
    }

    /*function uploadPicture (files) {
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: files,
            async: true,
            processData: false,
            contentType: false,
            success: function (res) {
                if (res.status === 200) {
                    isLoadImage = false;
                    var list = res.data.data_list;
                    $(".shop-logo-download-img").attr('src', '/adjuncts/file_download/' + list[0].id);
                    $_logo = list[0].id;
                    $(".shop-logo-download-img").css("display", 'block');
                    // $(".shop-logo-tips").hide();
                    $(".shop-logo").css('fontSize', 0);
                }
            }
        });
    }*/


    /* 行业多选框 */

    // 设置默认选中项
    function setDefualtSelect() {
        var stSave = null;
        if (providerResult.industry_id !== null && providerResult.industry_id !== undefined) {
            stSave = JSON.parse(providerResult.industry_id);
            typeSearchData[0].active = parseInt(stSave.id);
        }
        if (providerResult.sub_industry_id !== null && providerResult.sub_industry_id !== undefined) {
            stSave = JSON.parse(providerResult.sub_industry_id);
            typeSearchData[1].active = parseInt(stSave.id);
        }
    }

    // 提取行业数据
    function extractIndustryData(data) {
        data.forEach(function (item) {
            if (item.pid === 202052) {
                aIndustry.push(item);
            } else {
                aSubIndustry.push(item);
            }
        })
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
                    sNewPictureUrl = fileStr;
                }).setShowCanvasSize(oCutInstanceArea.width(), 300)
                    .setGetImageFromOutSide(true)
                    .setCutCanvasWidthHeightRatio(nCutWindowWidth, nCutWindowHeight);
                if (sNewPictureUrl) {
                    img.src = sNewPictureUrl;
                    oNowCutInstance.setNewImage(img);
                }
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
                sNewPictureUrl = fileStr;
            }).setShowCanvasSize(oCutInstanceArea.width(), 300)
                .setGetImageFromOutSide(true)
                .setCutCanvasWidthHeightRatio(nCutWindowWidth, nCutWindowHeight);
            if (sNewPictureUrl) {
                img.src = sNewPictureUrl;
                oNowCutInstance.setNewImage(img);
            }
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
            fd = null;
        oCutPictureBtnTrue.off().click(function () {
            if (bIsUploading) {
                layer.closeAll();
                layer.msg('正在上传中，请稍后操作');
                return 0;
            }
            if (!sNewPictureUrl) {
                layer.closeAll();
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
            uploadFile(fd, function (res) {
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
                    oCutInstanceArea.hide();
                } else {
                    layer.closeAll();
                    layer.msg('上传头像失败，请稍后重试');
                }
            });
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
            if (!sNewPictureUrl) {
                return 0;
            }
            sNewPictureUrl = null;
            bIsCuting = false;
            if (!sOldPictureUrl) {
                // 若没有旧链接，直接隐藏img 显示 + 号
                oNowCutPictureImageNode.hide().parent().css({
                    fontSize: '30px'
                });
            } else {
                oNowCutPictureImageNode.get(0).src = sOldPictureUrl;
            }
            delete sOldPictureUrl;
            // 隐藏截图区域
            oCutInstanceArea.hide();
        });
    }

}



