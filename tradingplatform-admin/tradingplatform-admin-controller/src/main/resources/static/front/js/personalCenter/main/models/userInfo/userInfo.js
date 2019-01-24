$(function () {
    // 存储类型列表子项
    var typeItemHtml = '<li class="type-item"></li>';
    // 获取用户所属名称数据
    var userTypesData = typeList;
    // 获取存储
    var storage = window.localStorage;
    // 获取用户信息
    var userInfo = (storage.getItem('user') !== null) ? JSON.parse(storage.getItem('user')) : undefined;
    // 数据备份
    var submitData = JSON.parse(JSON.stringify(userInfo));
    // 获取用户头像input节点
    var oUserAvatarInputNode = $('#userAvatar');
    // 获取用户头像节点
    var oUserAvatarImageNode = oUserAvatarInputNode.prev().find('.user-avatar-image').eq(0);
    // 获取用户头像截图区域
    var oCutPictureArea = $('.cutPictureArea');
    // 截图区域蒙板
    var oCutPictureMark = oCutPictureArea.find('.cutPicturePluginLoadingMark').eq(0);
    // 截图确定按钮
    var oCutPictureBtnTrue = $('#userInfo .cutPictureBtn[name="cutTrue"]').eq(0);
    // 截图取消按钮
    var oCutPictureBtnCancel = $('#userInfo .cutPictureBtn[name="cutCancel"]').eq(0);
    // 是否正在切图
    var bIsCuting = false;
    // 是否正在上传
    var bIsUploading = false;
    // 获取用户名输入节点
    var userNameNode = $('#userName');
    // 获取地区区域
    var areaDiv = $('#userAddressBox');
    // 获取详细地址节点
    var detailAddressNode = $('#userDetailAddress');
    // 获取用户生日
    var userBirthdayNode = $('#userBirthday');
    // 获取用户所属类型节点
    var userTypeListNode = $('#typeList');
    // 获取用户简介节点
    var userIntroductionNode = $('#userIntroduction');
    // 获取编辑按钮
    var editBtn = $('.user-info-div .btn-edit').eq(0);
    // 获取提交按钮
    var submitBtn = $('.user-info-div .btn-submit').eq(0);
    // 获取取消按钮
    var cancelBtn = $('.user-info-div .btn-cancel').eq(0);
    // 存储截图对象
    var oCutPicture = null;
    // 头像的旧链接
    var sOldPictureUrl = null;
    // 头像的新链接
    var sNewPictureUrl = null;
    // 判定是否为编辑模式
    var isEditModel = false;
    // 违法输入正则
    var errorInputRule = /<[^<>]*\/?>|function.*/;
    // 是否提交
    var isSubmit = false;
    // 通过项纪录
    var passNotes = {};
    var isCanSubmit = true;


    $('.user-name-tip').show();


    initArea();
    initTypesList();
    resetNode(userInfo);
    userAvatarEvent();
    cutPictureBtnEvent();
    userNameEvent();
    userDetailAddressEvent();
    userIntroductionEvent();
    typeListEvent();
    eventOfEditBtnClick();
    eventOfSubmitBtnClick();
    eventOfCancelBtnClick();

    /*=== 初始化 ===*/

    // 初始化地区选项
    function initArea() {
        // 初始化地区
        var addressNode = $('#userAddressBox');
        // 获取省节点
        var provinceNode = $('#userProvinceName');
        // 获取城市节点
        var cityNode = $('#userCityName');
        // 获取地方节点
        var districtNode = $('#userDistrictName');
        // 初始化
        addressNode.distpicker();
        // 赋给初始值
        if (userInfo.provinceName !== undefined) {
            provinceNode.val(userInfo.provinceName);
            provinceNode.trigger("change");
        }
        if (userInfo.cityName !== undefined) {
            cityNode.val(userInfo.cityName);
            cityNode.trigger("change");
        }
        if (userInfo.districtName) {
            districtNode.val(userInfo.districtName);
        }
    }

    // 初始化归属类型选项
    /*function initBelongTypes() {
        // 引入搜索框
        var search = new TypeSearch()
        // 初始数据
        var initData = [{
            name: '类型',
            type: 'type',
            show: true,
            data: userTypesData
        }]
        // 设置类型
        search.setData(initData)
        // 设置回调函数
        search.setClickCallback((node) => {
        })
    }*/

    // 类型列表初始化
    function initTypesList() {
        var ulList = $('#typeList');
        var moreNode = ulList.next();
        var result = '';
        var userType = userInfo.study;
        var oldTypeIndex = 0;
        var lineSize = ulList.width() / 105;
        if (userTypesData.length > lineSize) {
            moreNode.css({
                display: 'block'
            });
            ulList.css({
                height: '40px'
            });
        }
        if (userType !== undefined) {
            oldTypeIndex = (userTypesData.searchArrayObj(Number(userType.id), 'id') > -1) ? userTypesData.searchArrayObj(Number(userType.id), 'id') : 0
        }
        // 转化数值类型
        oldTypeIndex = +oldTypeIndex;
        userTypesData.forEach(function (item, index) {
            result += typeItemHtml.replace(/class="type-item"/, function (classStr) {
                if (oldTypeIndex === index) {
                    // 这里为用户的归属类添加 active
                    return 'class="type-item active"'
                } else {
                    return classStr
                }
            }).replace(/><\/li>/, function () {
                // return `data-id="${item.id}" data-pid="${item.pid}">${item.title}</li>`
                return 'data-id="' + item.id + '" data-pid="' + item.pid + '">' + item.title + '</li>'
            })
        });
        ulList.html(result);
    }

    // 初始化截图插件
    function initCutPicturePlugin() {
        var img = new Image();
        // 引入截图插件
        if (window.ScreenshotPlugin === undefined) {
            $.getScript("/static/front/js/fragments/screenShot.js", function () {
                oCutPicture = new ScreenshotPlugin('cutPictureAreaOfUserInfo');
                oCutPicture.setResultCallback(function (fileStr) {
                    // 若非截图时间
                    if (!bIsCuting) {
                        return 0;
                    }
                    oUserAvatarImageNode.attr({
                        src: fileStr
                    });
                    sNewPictureUrl = fileStr;
                }).setShowCanvasSize(oCutPictureArea.width(), 300)
                    .setGetImageFromOutSide(true)
                    .setCutCanvasWidthHeightRatio(1, 1);
                if (sNewPictureUrl) {
                    img.src = sNewPictureUrl;
                    oCutPicture.setNewImage(img);
                }
                oCutPictureMark.hide();
            });
        } else if (!oCutPicture) {
            oCutPicture = new ScreenshotPlugin('cutPictureAreaOfUserInfo');
            oCutPicture.setResultCallback(function (fileStr) {
                // 若非截图时间
                if (!bIsCuting) {
                    return 0;
                }
                oUserAvatarImageNode.attr({
                    src: fileStr
                });
                sNewPictureUrl = fileStr;
            }).setShowCanvasSize(oCutPictureArea.width(), 300)
                .setGetImageFromOutSide(true)
                .setCutCanvasWidthHeightRatio(1, 1);
            if (sNewPictureUrl) {
                img.src = sNewPictureUrl;
                oCutPicture.setNewImage(img);
            }
            oCutPictureMark.hide();
        }
    }

    /*=== 事件监听 ===*/

    // 用户头像事件
    function userAvatarEvent() {
        eventOfImageClick();
        eventOfInputChange()
    }

    function eventOfImageClick() {
        var imageCover = $('.user-avatar-image-div').eq(0);
        imageCover.off().on('click', function () {
            if (!isEditModel || bIsUploading) {
                return 0
            }
            var fileInput = imageCover.next();
            fileInput.click()
        })
    }

    // avatar: input的change事件
    function eventOfInputChange() {
        // 个人信息修改页面里的头像
        var files = null,
            img = new Image();
        oUserAvatarInputNode.off().change(function () {
            files = oUserAvatarInputNode.get(0).files;
            // 判定
            if (!files[0].type) {
                return 0;
            }
            if (files[0].type.split("/")[0] !== "image") {
                layer.closeAll();
                layer.open({
                    title: '温馨提示',
                    content: '只能上传图片'
                });
                return 0;
            }
            bIsCuting = true;
            // 存储旧链接
            if (!sOldPictureUrl) {
                sOldPictureUrl = oUserAvatarImageNode.get(0).src;
            }
            // 存储新链接
            img.src = getObjectURL(files[0]);
            // 为截图插件接入外部图片资源
            oCutPicture.setNewImage(img);
            // 展示截图区域
            if (oCutPictureArea.is(':hidden')) {
                oCutPictureArea.show();
            }
            /*uploadFile(userAvatarNode.get(0).files, function (data) {
                userAvatarNode.next().find('.user-avatar-tip').eq(0).show();
                userAvatarNode.prev().removeClass('error-border');
                userAvatarNode.next().find('.user-info-error-tip').eq(0).text('请上传用户头像').hide();
                submitData.avatar = data[0].id;
                // 修改个人信息头像
                imgNode.attr({
                    src: imgNode.getAvatar(data[0].id)
                })
            })*/
        })
    }

    // 截图按钮事件
    function cutPictureBtnEvent() {
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
                if (res.status === 200) {
                    data = res.data.data_list;
                    submitData.avatar = data[0].id;
                    sOldPictureUrl = oUserAvatarImageNode.get(0).src;
                    // 隐藏区域
                    oCutPictureArea.hide();
                } else {
                    layer.closeAll();
                    layer.open({
                        title: '温馨提示',
                        content: '上传头像失败，请稍后重试'
                    });
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
            // 隐藏截图区域
            oCutPictureArea.hide();
            oUserAvatarImageNode.get(0).src = sOldPictureUrl;
        });
    }

    // 用户名称事件
    function userNameEvent() {
        eventOfUserNameFocus();
        eventOfUserNameBlur();
    }

    function eventOfUserNameFocus() {
        userNameNode.focus(function () {
            userNameNode.removeClass('error-border');
            userNameNode.next().find('.user-info-error-tip').eq(0).hide();
        })
    }

    function eventOfUserNameBlur() {
        var value = null;
        userNameNode.blur(function () {
            value = userNameNode.val();
            // 输入内容不为空
            if (value.length > 0) {
                var str1 = filterSensitiveWord(value);
                var str = '***';
                // 输入内容错误
                if (errorInputRule.test(value)) {
                    $('.user-name-tip').hide();
                    userNameNode.addClass('error-border');
                    userNameNode.next().find('.user-info-error-tip').eq(0).text('您输入的内容存在违法内容，请重新输入').show();
                    isCanSubmit = false;
                } else if (str1.indexOf(str) > -1) {
                    $('.user-name-tip').hide();
                    userNameNode.addClass('error-border');
                    userNameNode.next().find('.user-info-error-tip').eq(0).text('您输入的内容存在敏感词，请重新输入').show();
                    isCanSubmit = false;
                } else {
                    $('.user-name-tip').hide();
                    userNameNode.removeClass('error-border');
                    userNameNode.next().find('.user-info-error-tip').eq(0).hide();
                    // 存入用户名称
                    submitData.userName = value;
                    isCanSubmit = true;
                }
            } else {
                $('.user-name-tip').hide();
                userNameNode.addClass('error-border');
                userNameNode.next().find('.user-name-tip').eq(0).hide();
                userNameNode.next().find('.user-info-error-tip').eq(0).text('用户名称不能为空').show();
                isCanSubmit = false;
            }
        })
    }

    // 用户详细地址
    function userDetailAddressEvent() {
        eventOfUserDetailAddressFocus();
        eventOfUserDetailAddressBlur();
    }

    function eventOfUserDetailAddressFocus() {
        detailAddressNode.focus(function () {
            detailAddressNode.removeClass('error-border');
            detailAddressNode.next().find('.user-info-error-tip').eq(0).hide();
        })
    }

    function eventOfUserDetailAddressBlur() {
        var value = null;
        detailAddressNode.blur(function () {
            value = filterSensitiveWord(detailAddressNode.val());
            // 输入内容不为空
            if (value.length > 0) {
                // 输入内容错误
                if (errorInputRule.test(value)) {
                    detailAddressNode.addClass('error-border');
                    detailAddressNode.next().find('.user-info-error-tip').eq(0).text('您输入的内容存在违法内容，请重新输入').show();
                } else {
                    detailAddressNode.removeClass('error-border');
                    detailAddressNode.next().find('.user-info-error-tip').eq(0).hide();
                    // 存入详细地址
                    submitData.detailedAddress = value;
                }
            } else {
                detailAddressNode.addClass('error-border');
                detailAddressNode.next().find('.user-info-error-tip').eq(0).show().text('用户详细地址不能为空');
            }
        })
    }

    // 用户自述事件
    function userIntroductionEvent() {
        eventOfUserIntroductionFocus();
        eventOfUserIntroductionBlur();
    }

    function eventOfUserIntroductionFocus() {
        userIntroductionNode.focus(function () {
            userIntroductionNode.removeClass('error-border');
            userIntroductionNode.next().find('.user-info-error-tip').eq(0).hide();
        })
    }

    function eventOfUserIntroductionBlur() {
        var value = null;
        userIntroductionNode.blur(function () {
            value = filterSensitiveWord(userIntroductionNode.val());
            // 输入内容不为空
            if (value.length > 0) {
                // 输入内容错误
                if (errorInputRule.test(value)) {
                    userIntroductionNode.addClass('error-border');
                    userIntroductionNode.next().find('.user-info-error-tip').eq(0).text('您输入的内容存在违法内容，请重新输入').show();
                } else {
                    userIntroductionNode.removeClass('error-border');
                    userIntroductionNode.next().find('.user-info-error-tip').eq(0).hide();
                    // 存入自述
                    submitData.selfStatement = value;
                }
            } else {
                userIntroductionNode.removeClass('error-border');
                userIntroductionNode.next().find('.user-info-error-tip').eq(0).hide();
                // 存入自述
                submitData.selfStatement = value;
            }
        })
    }

    // 类型多选框事件
    function typeListEvent() {
        eventOfTypeListClick();
        eventOfTypeMoreClick();
    }

    function eventOfTypeListClick() {
        userTypeListNode.off().click('click', function (event) {
            var nowNode = $(event.target);
            if (nowNode.hasClass('type-item') && !nowNode.hasClass('active')) {
                nowNode.siblings().removeClass('active');
                nowNode.addClass('active');
            }
        })
    }

    function eventOfTypeMoreClick() {
        var moreNode = userTypeListNode.next();
        moreNode.off('click').on('click', function () {
            if (moreNode.attr('name') === 'more') {
                moreNode.html('收起 <i class="icon-close-arrow"></i>');
                userTypeListNode.css({
                    height: 'auto'
                });
                moreNode.attr({
                    name: 'collapse'
                });
            } else {
                moreNode.html('展开 <i class="icon-open-arrow"></i>');
                userTypeListNode.css({
                    height: '40px'
                });
                moreNode.attr({
                    name: 'more'
                });
            }
        })
    }

    // 编辑按钮的点击事件
    function eventOfEditBtnClick() {
        // 绑定编辑按钮的点击事件
        editBtn.off().click(function () {
            // 修改当前的编辑状态
            isEditModel = true;
            // 初始化截图插件
            initCutPicturePlugin();
            // 修改展示
            changeShowModel(isEditModel);
        });
    }

    // 提交按钮点击事件
    function eventOfSubmitBtnClick() {
        submitBtn.off().click(function () {
            if (oUserAvatarImageNode.attr('src') === undefined) {
                oUserAvatarInputNode.next().find('.user-avatar-tip').eq(0).hide();
                oUserAvatarInputNode.prev().addClass('error-border');
                oUserAvatarInputNode.next().find('.user-info-error-tip').eq(0).text('请上传用户头像').show();
                return 0;
            }
            // 修改信息
            resetUserInfo(function (res) {
                // 过滤提交数据
                filterSubmitData();
                // 非修改
                isEditModel = false;
                // 复制数据
                userInfo = JSON.parse(JSON.stringify(submitData));
                // 修改缓存
                storage.setItem('user', JSON.stringify(userInfo));
                // 重写数据
                resetNode(userInfo);
                // 更新全头像
                updateGlobalShowInfo(res);
                // 切换模式
                changeShowModel(isEditModel);
            })
        })
    }

    // 取消按钮点击事件
    function eventOfCancelBtnClick() {
        cancelBtn.off().click(function () {
            isEditModel = false;
            if (sNewPictureUrl) {
                sNewPictureUrl = null;
                // 隐藏截图区域
                oCutPictureArea.hide();
                oUserAvatarImageNode.get(0).src = sOldPictureUrl;
            }
            sOldPictureUrl = undefined;
            // 修改展示
            changeShowModel(isEditModel);
        })
    }

    /*=== 功能函数 ===*/

    // 修改请求
    function resetUserInfo(callback) {
        if (isSubmit) {
            return 0;
        }
        isSubmit = true;
        // 获取选中类型结果
        getTypeListResult();
        // 获取地区结果
        getAreaResult();
        // 获取生日结果
        getBirthdayResult();
        // 提交请求
        var value = userNameNode.val();
        // 输入内容不为空
        if (value.length > 0) {
            var str1 = filterSensitiveWord(value);
            var str = '***';
            if (str1.indexOf(str) > -1) {
                isCanSubmit = false;
                isSubmit = false;
            }
        } else {
            isCanSubmit = false;
            isSubmit = false;
        }
        if (isCanSubmit === true) {
            let json = {
                userName: submitData.userName,
                address: submitData.address,
                avatar: submitData.avatar,
                cityName: submitData.cityName,
                districtName: submitData.districtName,
                provinceName: submitData.provinceName,
                professionalField: submitData.professionalField,
                birthday: submitData.birthday,
                detailedAddress: submitData.detailedAddress,
                selfStatement: submitData.selfStatement
            };
            new NewAjax({
                url: '/f/user/pc/create_update?pc=true',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200 && callback) {
                        isSubmit = false;
                        callback(res);
                    }
                },
                error: function () {
                    isSubmit = false;
                    // 重置修改过的副本
                    submitData = JSON.parse(JSON.stringify(userInfo));
                }
            })
        } else {
            layer.msg("请正确填写信息");
        }
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
                /*if (res.status === 200 && callback) {

                }*/
            },
            error: function (err) {
                console.error("上传头像：" + err)
            }
        });
    }

    // 切换展示模式
    function changeShowModel(isEdit) {
        if (isEdit === undefined) {
            isEdit = true
        }
        // 编辑模式
        if (isEdit) {
            // 获取用户名输入节点
            userNameNode.show();
            userNameNode.prev().hide();
            // 获取地区区域
            areaDiv.show();
            areaDiv.prev().hide();
            // 获取详细地址节点
            detailAddressNode.show();
            detailAddressNode.prev().hide();
            detailAddressNode.next().find('.user-info-error-tip').eq(0).show();
            // 获取用户生日
            userBirthdayNode.show();
            userBirthdayNode.prev().hide();
            userBirthdayNode.next().find('.user-info-error-tip').eq(0).show();
            // 获取用户所属类型节点
            userTypeListNode.parent().show();
            userTypeListNode.parent().prev().hide();
            // 获取用户简介节点
            userIntroductionNode.show();
            userIntroductionNode.prev().hide();
            userIntroductionNode.next().find('.user-info-error-tip').eq(0).show();
            // 获取编辑按钮
            editBtn.attr('disabled', 'disabled').hide();
            // 获取提交按钮
            submitBtn.removeAttr('disabled').show();
            // 获取取消按钮
            cancelBtn.removeAttr('disabled').show()
        } else {// 展示模式
            // 用户头像节点
            oUserAvatarInputNode.next().find('.user-avatar-tip').eq(0).show();
            oUserAvatarInputNode.next().find('.user-info-error-tip').eq(0).hide();
            // 获取用户名输入节点
            userNameNode.hide();
            userNameNode.prev().show();
            userNameNode.next().find('.user-name-tip').eq(0).show();
            userNameNode.next().find('.user-info-error-tip').eq(0).hide();
            // 获取地区区域
            areaDiv.hide();
            areaDiv.prev().show();
            // 获取详细地址节点
            detailAddressNode.hide();
            detailAddressNode.prev().show();
            detailAddressNode.next().find('.user-info-error-tip').eq(0).hide();
            // 获取用户生日
            userBirthdayNode.hide();
            userBirthdayNode.prev().show();
            userBirthdayNode.next().find('.user-info-error-tip').eq(0).hide();
            // 获取用户所属类型节点
            userTypeListNode.parent().hide();
            userTypeListNode.parent().prev().show();
            // 获取用户简介节点
            userIntroductionNode.hide();
            userIntroductionNode.prev().show();
            userIntroductionNode.next().find('.user-info-error-tip').eq(0).hide();
            // 获取编辑按钮
            editBtn.removeAttr('disabled').show();
            // 获取提交按钮
            submitBtn.attr('disabled', 'disabled').hide();
            // 获取取消按钮
            cancelBtn.attr('disabled', 'disabled').hide();
        }
    }

    // 写入数据
    function resetNode(data) {
        // 头像url
        var avatarUrl = (data.avatar !== undefined) ? getAvatar(data.avatar) : '/static/assets/logo.png';
        // 图片写入
        oUserAvatarImageNode.attr({
            src: avatarUrl
        });
        // 名称写入
        userNameNode.val(data.userName);
        userNameNode.prev().text((data.userName !== undefined) ? data.userName : '暂无内容');
        // 写入工作地
        areaDiv.prev().text((data.address !== undefined) ? data.address : '暂无内容');
        // 写入地址
        detailAddressNode.val(data.detailedAddress);
        detailAddressNode.prev().text((data.detailedAddress !== undefined) ? data.detailedAddress : '暂无内容');
        // 写入类型
        userTypeListNode.parent().prev().text((data.study !== undefined) ? data.study.title : '暂无内容');
        if (userTypeListNode.find('li').length > 0) {
            var newNode = null;
            userTypeListNode.find('li').each(function (index, li) {
                newNode = $(li).eq(0);
                if (data.study !== undefined) {
                    if (newNode.data('id') === data.study.id) {
                        newNode.addClass('active');
                        return false;
                    }
                } else {
                    newNode.addClass('active');
                    return false;
                }
            });
        }
        // 初始化时间输入组件
        userBirthdayNode.datetimepicker({
            format: 'YYYY-MM-DD',//显示格式
            locale: moment.locale('zh-cn')
        });
        // 若生日不为空
        if (!!data.birthday) {
            // 写入生日
            userBirthdayNode.prev().text((data.birthday !== undefined) ? userBirthdayNode.formatTime(new Date(data.birthday)).split(" ")[0] : '暂无内容');
            // 写入组件
            userBirthdayNode.val(userBirthdayNode.formatTime(new Date(data.birthday)).split(" ")[0]);
        }
        // 写入自述
        userIntroductionNode.val(data.selfStatement)
        userIntroductionNode.prev().text((data.selfStatement !== undefined) ? data.selfStatement : '暂无内容')
    }

    // 更新全局展示信息
    function updateGlobalShowInfo(res) {
        // 获取头像id
        var avatar = submitData.avatar;
        // 头部登录信息里的头像
        var headAvatar = $('#head-avatar').find('img').eq(0);
        // 左侧信息里的头像
        var leftAvatar = $('.personal-center-left .user-avatar').eq(0);
        // 获取左侧名称
        var leftUserName = $('.personal-center-left .user-name').eq(0);
        // 获取资料完善度
        var leftUserCompleteness = $('.personal-center-left .perfection').eq(0);
        var leftUserCompletenessProcess = $('.personal-center-left .perfection-down').eq(0);
        // 修改登录信息头像
        headAvatar.attr({
            src: headAvatar.getAvatar(avatar)
        });
        // 修改左边栏头像
        leftAvatar.attr({
            src: leftAvatar.getAvatar(avatar)
        });
        // 修改名称
        leftUserName.text(submitData.userName);
        // 写入资料完善度
        leftUserCompleteness.text(parseInt(res.data.data_object.dataCompleteness * 100));
        leftUserCompletenessProcess.css('width', parseInt(res.data.data_object.dataCompleteness * 100) + '%');
        // 修改缓存
        userInfo.dataCompleteness = res.data.data_object.dataCompleteness;
        storage.setItem('user', JSON.stringify(userInfo))
    }

    // 获取类型列表结果
    function getTypeListResult() {
        // 当前节点
        var nowNode = null;
        // 遍历li节点
        userTypeListNode.find('li').each(function (index, li) {
            nowNode = $(li);
            if (nowNode.hasClass('active')) {
                submitData.professionalField = nowNode.data('id');
                submitData.study.id = nowNode.data('id');
                submitData.study.title = nowNode.text();
                return false;
            }
        })
    }

    // 获取地区结果
    function getAreaResult() {
        // 获取省节点
        var provinceNode = $('#userProvinceName');
        // 获取城市节点
        var cityNode = $('#userCityName');
        // 获取地方节点
        var districtNode = $('#userDistrictName');
        // 获取地区数据
        submitData.provinceName = provinceNode.find('option:selected').val();
        submitData.cityName = cityNode.find('option:selected').val();
        submitData.districtName = districtNode.find('option:selected').val();
        // 存入地区
        submitData.address = ''
        if (submitData.provinceName !== undefined) {
            submitData.address += submitData.provinceName
        }
        if (submitData.cityName !== undefined) {
            submitData.address += '-' + submitData.cityName
        }
        if (submitData.districtName !== undefined) {
            submitData.address += '-' + submitData.districtName
        }
    }

    // 获取生日结果
    function getBirthdayResult() {
        // 获取生日结果
        submitData.birthday = userBirthdayNode.val()
    }

    // 过滤提交数据
    function filterSubmitData() {
        Object.keys(submitData).forEach(function (key) {
            if (submitData[key] === '') {
                submitData[key] = null
            }
        })
    }
});


