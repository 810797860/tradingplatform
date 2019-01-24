/*=== 函数调用 ===*/
var expertPersonalInfo = new ExpertPersonalInfo();

expertPersonalInfo.unableFormSubmit();
expertPersonalInfo.allListen();

$(function () {
    expertPersonalInfo.initIndustryIdList()
    expertPersonalInfo.initProfessionFieldList()
})

function ExpertPersonalInfo() {
    var _this = this;
    // 存储类型列表子项
    var typeOptionHtml = '<li class="type-item"></li>';
    // 获取类型列表
    var industryIdList = typeList;
    // 获取技术领域列表
    var professionFieldList = applicationList;
    // 获取表单信息
    var form = $('.expert-lib-personal-info-form-div #expertInfoForm');
    // 获取专家名称input
    var expertNameInput = $('.expert-lib-personal-info-form-div #expertName');
    // 获取职称Input
    var technicalTitleInput = $('.expert-lib-personal-info-form-div #technicalTitle');
    // 获取工作单位input
    var workingUnitInput = $('.expert-lib-personal-info-form-div #workingUnit');
    // 获取个人电话input
    var phoneInput = $('.expert-lib-personal-info-form-div #expertPhone');
    // 获取个人邮箱
    var emailInput = $('.expert-lib-personal-info-form-div #expertEmail');
    // 获取头像
    var personalImageInput = $('.expert-lib-personal-info-form-div #expertAvatar');
    // 获取个人简介input
    var personalProfileInput = $('.expert-lib-personal-info-form-div #personalProfile');
    // 获取工作经验input
    var workExperienceInput = $('.expert-lib-personal-info-form-div #workExperience');
    // 学历情况
    var educationInput = $('.expert-lib-personal-info-form-div #education');
    // 获取参与项目input
    var participateProjectInput = $('.expert-lib-personal-info-form-div #participateProject');
    // 获取主要荣誉input
    var mainHonorInput = $('.expert-lib-personal-info-form-div #mainHonor');
    // 获取知识产权/成果input
    var intellectualPropertyInput = $('.expert-lib-personal-info-form-div #intellectualProperty');
    // 获取应用行业节点
    var industryIdUlList = $('.expert-lib-personal-info-form-div #industryId');
    // 获取技术领域节点
    var professionFieldUlList = $('.expert-lib-personal-info-form-div #professionField');
    // 提交参数
    var submitData = {};
    // 获取副本数据
    var dataCopy = {}
    // 是否提交审核
    var isSubmitSuccess = false;
    // 展示节点
    var showDivNode = $('.expert-lib-personal-info-form-div div[model="show"]');
    var editBtn = $('.expert-lib-personal-info-form-div .form-edit-btn').eq(0);
    // 编辑节点
    var editDivNode = $('.expert-lib-personal-info-form-div div[model="edit"]');
    var submitBtn = $('.expert-lib-personal-info-form-div .form-true-btn').eq(0);
    var cancelBtn = $('.expert-lib-personal-info-form-div .form-cancel-btn').eq(0);
    var editTextAreaNode = $('.expert-lib-personal-info-form-div textarea[model="edit"]');
    // 获取提示框
    var tipMessage = $('.expert-lib-personal-info-form-div .expert-lib-personal-info-Tip-div');
    // 违法输入正则
    var errorInputRule = /<[^<>]*\/?>|function.*/;
    // 电话正则
    var phoneRule = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/;
    // 邮箱正则
    var emailRule = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    // 判定是否为编辑状态
    var isEditModel = true
    // 记录通过情况
    var passStatus = {}

    // 获取专家个人信息
    _this.getExpertPersonalInfo = function (callback) {
        new NewAjax({
            url: '/f/expertsCheckRecords/pc/getByUserId?pc=true',
            contentType: 'application/json',
            type: 'get',
            success: function (res) {
                if (res.status === 200 && callback) {
                    var stSave = res.data.data_object
                    console.log(res.data.data_object);
                    if (stSave != null) {
                        // console.table(stSave)
                        extractData(stSave)
                        initPassStatus(stSave)
                        isReview()
                    } else {
                        console.log('ceshi');
                        $('#expertAvatar').attr('disabled', false);
                    }
                    callback(res.data)
                }
            },
            error: function (err) {
                console.error('获取专家个人数据失败，err' + err)
            }
        })
    }
    // 切换模式
    _this.changeModel = function(model) {
        // 根据模式切换
        switch (model) {
            case 'show':
                // 展示展示项
                showDivNode.css({
                    display: ''
                })
                // 隐藏编辑项
                editDivNode.css({
                    display: 'none'
                })
                // 隐藏textarea的编辑项
                editTextAreaNode.css({
                    display: 'none'
                })
                break
            case 'edit':
                // 隐藏展示项
                showDivNode.css({
                    display: 'none'
                })
                // 显示编辑项
                editDivNode.css({
                    display: ''
                })
                // 展开textarea的编辑项
                editTextAreaNode.css({
                    display: ''
                })
                break
            default:
                break
        }
    }
    // 写入数据
    _this.setData = function() {
        // 写入展示数据
        setShowData()
        // 写入编辑数据
        setEditData()
    }
    // 应用行业列表初始化
    _this.initIndustryIdList = function () {
        var moreNode = industryIdUlList.next()
        var result = ''
        // 获取回显的内容
        var userType = submitData.industryId
        var oldTypeIndex = 0
        if (typeList.length > 6) {
            moreNode.css({
                display: 'block'
            })
            industryIdUlList.css({
                height: '50px'
            })
        }
        if (userType !== undefined) {

        }
        if (userType !== undefined) {
            if (userType instanceof Array) {
                oldTypeIndex = (industryIdList.searchArrayObj(Number(userType[0].id), 'id') > -1) ? industryIdList.searchArrayObj(Number(userType[0].id), 'id') : 0
            } else {
                oldTypeIndex = (industryIdList.searchArrayObj(Number(userType.id), 'id') > -1) ? industryIdList.searchArrayObj(Number(userType.id), 'id') : 0
            }
        }
        // 转化数值类型
        oldTypeIndex = +oldTypeIndex
        industryIdList.forEach(function (item, index) {
            result += typeOptionHtml.replace(/class="type-item"/, function (classStr) {
                if (oldTypeIndex === index) {
                    // 这里为用户的归属类添加 active
                    return 'class="type-item active"'
                } else {
                    return classStr
                }
            }).replace(/><\/li>/, function () {
                return 'data-id="' + item.id + '" data-pid="' + item.pid + '">' + item.title + '</li>';
            })
        });
        // console.log(ulList)
        industryIdUlList.html(result)
    };
    // 技术领域列表初始化
    _this.initProfessionFieldList = function () {
        var result = '';
        // 获取回显的内容
        var userType = submitData.professionField;
        professionFieldList.forEach(function (item, index) {
            result += typeOptionHtml.replace(/><\/li>/, function () {
                return 'data-id="' + item.id + '" data-pid="' + item.pid + '">' + item.title + '</li>';
            })
        });
        professionFieldUlList.html(result);

        if (userType !== undefined && !!userType) {
            for (var i = 0; i < userType.length; i++) {
                professionFieldList.forEach(function (item, index) {
                    if (userType[i] == item.title) {
                        professionFieldUlList.find('.type-item').eq(index).addClass('active');
                    }
                })
            }
            for (var j = 0; j < userType.length; j++) {
                for (var k = 0; k < professionFieldList.length; k++) {
                    if (userType[j] == professionFieldList[k].title) {
                        break;
                    } else if (k == professionFieldList.length - 1 && userType[j] != professionFieldList[k].title) {
                        var type = $('<li class="type-item active" style="position: relative">' + userType[j] + '<i style="font-size: 16px;position: absolute;top: -15px;right: 2px" class="deleteType">×</i></li>');
                        console.log(type);
                        professionFieldUlList.append(type);
                    }
                }
            }
        }
        var span = $(' <span class="addType" style="width: 90px;float: left;margin: 5px 15px 5px 0;height: 30px; line-height: 30px;border: 1px solid #0066cc;color: #0066cc;text-align: center;font-size: 30px;border-radius: 5px;cursor: pointer">+</span>');
        professionFieldUlList.append(span);
    };
    // 禁止表单跳转
    _this.unableFormSubmit = function() {
        var forms = $('.expert-lib-personal-info-form-div form')
        forms.submit(function() {
            return false
        })
    }
    // 调用所有监听函数
    _this.allListen = function() {
        expertNameEvent();
        technicalTitleEvent();
        workingUnitEvent();
        expertPhoneEvent();
        emailInputEvent();
        expertAvatarEvent();
        personalProfileEvent();
        workExperienceEvent();
        educationEvent();
        participateProjectEvent();
        mainHonorEvent();
        intellectualPropertyEvent()
        industryIdEvent();
        professionFieldEvent();
        eventOfEditBtnClick();
        eventOfSubmitBtnClick();
        eventOfCancelBtnClick();
        clickAddType();
        deleteTypeClick();
    }
    // 表单验证
    _this.formValidator = function () {
        form.bootstrapValidator({
            excluded: [':disabled', ':hidden', ':not(:visible)'],
            submitButtons: '.expert-form-submit-btn',
            message: '请填写正确信息',//好像从来没出现过
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: '名称不能为空'
                        }
                    }
                },

            }
        });
        $(".expert-lib-personal-info-form-div #btn-test").click(function () {//非submit按钮点击后进行验证，如果是submit则无需此句直接验证
            $(".expert-lib-personal-info-form-div #form-test").bootstrapValidator('validate');//提交验证
            if ($(".expert-lib-personal-info-form-div #form-test").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                alert("yes");//验证成功后的操作，如ajax
            }
        });
    }


    /*=== 功能函数 ===*/
    // 点击添加类型按钮
    function clickAddType() {
        $(document).on('click','#professionField .addType',function () {
            var length = $('.expert-lib-personal-info-List-div #professionField').find('.active').length;
            if (length === 3) {
                layer.msg("最多只能选择3个");
                return;
            }
            layer.prompt({title: '请输入擅长领域', formType: 2}, function(pass, index){
                layer.close(index);
                $('.expert-lib-personal-info-List-div #professionField').find('.addType').remove();
                var type = $('<li class="type-item active" style="position: relative">'+ pass +'<i style="font-size: 16px;position: absolute;top: -15px;right: 2px" class="deleteType">×</i></li>');
                $('.expert-lib-personal-info-List-div #professionField').append(type);
                var span = $(' <span class="addType" style="width: 90px;float: left;margin: 5px 15px 5px 0;height: 30px; line-height: 30px;border: 1px solid #0066cc;color: #0066cc;text-align: center;font-size: 30px;border-radius: 5px;cursor: pointer">+</span>');
                $('.expert-lib-personal-info-List-div #professionField').append(span);
            })
        })
    }

    // 删除自定义的擅长领域
    function deleteTypeClick() {
        $(document).on('click','.deleteType',function () {
            console.log('a');
            $(this).parent().remove();
        })
    }

    // 提取返回值
    function extractData(data) {
        if (data !== null) {
            submitData = {};
            submitData.id = data.id;
            submitData.status = (data.back_check_status !== undefined) ? JSON.parse(data.back_check_status).id : null;
            submitData.name = data.name;
            submitData.personalImage = (data.personal_image !== undefined) ? data.personal_image : null;
            submitData.industryId = (data.profession_field !== undefined) ? JSON.parse(data.profession_field) : null;
            submitData.contactPhone = data.contact_phone;
            submitData.email = data.email
            submitData.professionField = (data.skilled_field !== undefined) ? JSON.parse(data.skilled_field) : null;
            submitData.technicalTitle = data.technical_title;
            submitData.workingUnit = data.working_unit;
            submitData.workExperience = data.work_experience;
            submitData.personalProfile = data.personal_profile;
            submitData.educationBackground = data.education_background;
            submitData.participateProject = data.participate_project;
            submitData.mainHonor = data.main_honor;
            submitData.intellectualProperty = data.intellectual_property;
            dataCopy = JSON.parse(JSON.stringify(submitData))
        }
    }
    // 初始化通过情况
    function initPassStatus(data) {
        if (data !== null) {
            Object.keys(submitData).forEach(function (key) {
                if (key === 'id' || key === 'status' || key === 'industryId' || key === 'professionField' || key === "personalImage") {
                    passStatus[key] = true
                } else {
                    passStatus[key] = false
                }
            })
        }
    }
    // 展示数据写入
    function setShowData() {
        var stSave = '';
        var nowNode;
        var nowName;
        $('.moreField li').remove();
        showDivNode.each(function (index, div) {
            nowNode = $(div);
            nowName = nowNode.attr('name');
            if (submitData[nowName] !== undefined) {
                if (submitData[nowName] instanceof Array) {
                    submitData[nowName].forEach(function (item, index) {
                        if (typeof item === 'object') {
                            if (index < submitData[nowName].length - 1) {
                                stSave += (item.title + ',');
                            } else {
                                stSave += item.title;
                            }
                        } else {
                            console.log($('.moreField').attr('model') == 'show');
                            if ($('.moreField').attr('model') == 'show') {
                                var type = $('<li style="text-decoration: none;list-style: none;width: 100px;height: 30px;line-height: 30px;color: white;background-color: #0066cc;display: inline-block;vertical-align: top;border-radius: 5px;margin-left: 5px;text-align: center;margin-top: 10px">'+ item +'</li>');
                                $('.moreField').append(type);
                                // stSave += type;
                            }
                        }
                    });
                    // nowNode.text((stSave !== '') ? stSave : '暂无内容');
                } else if (typeof submitData[nowName] === 'object' && !!submitData[nowName]) {
                    console.log('submitData[nowName]',submitData[nowName]);
                    nowNode.text((submitData[nowName].title !== undefined && submitData[nowName].title !== null) ? submitData[nowName].title : '暂无内容')
                } else if (typeof submitData[nowName] === 'string') {
                    nowNode.html(submitData[nowName] !== '' ? getHtmlStr(submitData[nowName]) : '暂无内容')
                }
            }
        });
        // 写入头像
        personalImageInput.prev().find('img').eq(0).attr({
            src: personalImageInput.getAvatar(submitData.personalImage)
        }).prev().hide()
    }
    // 编辑数据写入
    function setEditData() {
        // 名称
        expertNameInput.val(valueFilter(submitData.name, ''));
        // 职称
        technicalTitleInput.val(valueFilter(submitData.technicalTitle, ''));
        // 工作单位
        workingUnitInput.val(valueFilter(submitData.workingUnit, ''));
        // 电话
        phoneInput.val(valueFilter(submitData.contactPhone, ''));
        // 邮箱
        emailInput.val(valueFilter(submitData.email, ''));
        // 个人简介
        personalProfileInput.val(valueFilter(submitData.personalProfile, ''));
        // 工作经验
        workExperienceInput.val(valueFilter(submitData.workExperience, ''));
        // 学历情况
        educationInput.val(valueFilter(submitData.educationBackground, ''));
        // 参与项目
        participateProjectInput.val(valueFilter(submitData.participateProject, ''));
        // 主要荣誉
        mainHonorInput.val(valueFilter(submitData.mainHonor, ''));
        // 知识产权/成果
        intellectualPropertyInput.val(valueFilter(submitData.intellectualProperty, ''));
    }
    // 编辑状态验证
    function isReview() {
        if (submitData.status !== undefined) {
            if (submitData.status === '202049') { // 待审核
                isSubmitSuccess = true
                // 隐藏三个按钮
                editBtn.css({
                    display: 'none'
                }).attr({
                    disabled: 'disabled'
                }).text('编辑')
                submitBtn.css({
                    display: 'none'
                }).attr({
                    disabled: 'disabled'
                })
                cancelBtn.css({
                    display: 'none'
                }).attr({
                    disabled: 'disabled'
                })
                // 修改提示框
                tipMessage.css({
                    display: '',
                    borderColor: '#aaaaaa',
                    color: '#aaaaaa'
                }).text('审核中')
            } else if (submitData.status === '202050') { //通过
                isSubmitSuccess = false
                // 展示并激活编辑按钮
                editBtn.css({
                    display: ''
                }).removeAttr('disabled').text('修改')
                // 激活提交按钮
                submitBtn.removeAttr('disabled').css({
                    display: 'none'
                })
                // submitBtn.remove()
                // 激活取消按钮
                cancelBtn.removeAttr('disabled').css({
                    display: 'none'
                })
                // cancelBtn.remove()
                // 修改提示框
                tipMessage.css({
                    display: '',
                    borderColor: '#00B83F',
                    color:'#00B83F'
                }).text('通过')
            } else if (submitData.status === '202051') { // 不通过
                isSubmitSuccess = false
                // 展示并激活编辑按钮
                editBtn.css({
                    display: ''
                }).removeAttr('disabled').text('修改')
                // 激活提交按钮
                submitBtn.removeAttr('disabled').css({
                    display: 'none'
                })
                // 激活取消按钮
                cancelBtn.removeAttr('disabled').css({
                    display: 'none'
                })
                // 修改提示框
                tipMessage.css({
                    display: '',
                    borderColor: '#ff0000',
                    color:'#ff0000'
                }).text('不通过')
            }
            _this.changeModel('show')
        } else {
            isSubmitSuccess = false
        }
    }
    // 获取类型多选数值
    function getListValue() {
        var idLis = industryIdUlList.find('li');
        var FieldLis = professionFieldUlList.find('li');
        idLis.each(function (index, li) {
            var nowLi = $(li);
            if (nowLi.hasClass('active')) {
                submitData.professionField = nowLi.data('id');
                industryIdUlList.parent().prev().text(nowLi.text());
            }
        });
        submitData.skilledField= [];
        FieldLis.each(function (index, li) {
            var nowLi = $(li);
            if (nowLi.hasClass('active')) {
                submitData.skilledField.push(nowLi.text());
                // professionFieldUlList.parent().prev().text(nowLi.text());
            }
        });
        submitData.skilledField = JSON.stringify(submitData.skilledField);
    }
    // 转str为html
    function getHtmlStr(str) {
        return str.replace(/\n/g, function () {
            return '<br/>'
        })
    }
    // 文件上传
    function uploadFile (files, callback) {
        if (files.length == 0) return; //如果文件为空
        var formData = new FormData();
        for (var i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: formData,
            async: true,
            processData: false,
            contentType: false,
            success: function (res) {
                if (res.status === 200 && callback){
                    callback(res.data.data_list)
                }
            },
            error: function (err) {
                console.error("上传头像：" + err)
            }
        });
    }
    // 判断字段是否为空
    function searchVoidAttr() {
        expertNameInput.focus().blur();
        technicalTitleInput.focus().blur();
        workingUnitInput.focus().blur();
        phoneInput.focus().blur();
        emailInput.focus().blur();
        personalProfileInput.focus().blur();
        workExperienceInput.focus().blur();
        educationInput.focus().blur();
        participateProjectInput.focus().blur();
        mainHonorInput.focus().blur();
        intellectualPropertyInput.focus().blur();
        if (!submitData.personalImage) {
            personalImageInput.prev().addClass('error-border');
            personalImageInput.next().find('.expert-info-error-tip').eq(0).text('头像不能为空！').show();
            passStatus.personalImage = false;
        }
    }

    /*=== 监听函数 ===*/
    // 公司名input 事件
    function expertNameEvent() {
        eventOfExpertNameInputFocus()
        eventOfExpertNameInputBlur()
    }
    function eventOfExpertNameInputFocus() {
        expertNameInput.focus(function(){
            expertNameInput.removeClass('error-border');
            expertNameInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfExpertNameInputBlur() {
        var value = null;
        expertNameInput.blur(function () {
            value = expertNameInput.val()
            if (value.length > 0) {
                // 符合正确格式
                if (!errorInputRule.test(value)) {
                    expertNameInput.removeClass('error-border');
                    expertNameInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.name = value;
                    passStatus.name = true;
                } else {
                    // 不符合正确格式
                    expertNameInput.addClass('error-border');
                    expertNameInput.next().find('.expert-info-error-tip').eq(0).text('您输入的名称存在违规内容，请重新输入！').show();
                    passStatus.name = false;
                }
            } else {
                expertNameInput.addClass('error-border');
                expertNameInput.next().find('.expert-info-error-tip').eq(0).text('名称不能为空！').show();
                passStatus.name = false;
            }
        })
    }
    // 职称Input 事件
    function technicalTitleEvent() {
        eventOfTechnicalTitleFocus()
        eventOfTechnicalTitleBlur()
    }
    function eventOfTechnicalTitleFocus() {
        technicalTitleInput.change(function(){
            technicalTitleInput.removeClass('error-border');
            technicalTitleInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfTechnicalTitleBlur() {
        var value = null;
        technicalTitleInput.blur(function () {
            value = technicalTitleInput.val();
            if (value.length > 0) {
                // 符合正确格式
                if (!errorInputRule.test(value)) {
                    technicalTitleInput.removeClass('error-border');
                    technicalTitleInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.technicalTitle = value;
                    passStatus.technicalTitle = true;
                } else {
                    // 不符合正确格式
                    technicalTitleInput.addClass('error-border');
                    technicalTitleInput.next().find('.expert-info-error-tip').eq(0).text('您输入的职称存在违规内容，请重新输入！').show();
                    passStatus.technicalTitle = false;
                }
            } else {
                technicalTitleInput.addClass('error-border');
                technicalTitleInput.next().find('.expert-info-error-tip').eq(0).text('职称不能为空！').show();
                passStatus.technicalTitle = false;
            }
        })
    }
    // 工作单位input 事件
    function workingUnitEvent() {
        eventOfWorkingUnitInputFocus()
        eventOfWorkingUnitInputBlur()
    }
    function eventOfWorkingUnitInputFocus() {
        workingUnitInput.focus(function(){
            workingUnitInput.removeClass('error-border');
            workingUnitInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfWorkingUnitInputBlur() {
        var value = null;
        workingUnitInput.blur(function () {
            value = workingUnitInput.val();
            if (value.length > 0) {
                // 符合正确格式
                if (!errorInputRule.test(value)) {
                    workingUnitInput.removeClass('error-border');
                    workingUnitInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.workingUnit = value;
                    passStatus.workingUnit = true;
                } else {
                    // 不符合正确格式
                    workingUnitInput.addClass('error-border')
                    workingUnitInput.next().find('.expert-info-error-tip').eq(0).text('您输入的工作单位存在违规内容，请重新输入！').show()
                    passStatus.workingUnit = false;
                }
            } else {
                workingUnitInput.addClass('error-border')
                workingUnitInput.next().find('.expert-info-error-tip').eq(0).text('工作单位不能为空！').show()
                passStatus.workingUnit = false;
            }
        })
    }
    // 电话input 事件
    function expertPhoneEvent() {
        eventOfExpertPhoneInputFocus()
        eventOfExpertPhoneInputBlur()
    }
    function eventOfExpertPhoneInputFocus() {
        phoneInput.focus(function () {
            phoneInput.removeClass('error-border');
            phoneInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfExpertPhoneInputBlur() {
        var value = null;
        phoneInput.blur(function () {
            value = phoneInput.val();
            if (value.length > 0) {
                // 电话格式正确
                if (phoneRule.test(value)) {
                    phoneInput.removeClass('error-border');
                    phoneInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.contactPhone = value;
                    passStatus.contactPhone = true;
                } else {
                    phoneInput.addClass('error-border');
                    phoneInput.next().find('.expert-info-error-tip').eq(0).text('您输入的手机号不正确，请重新输入！').show();
                    passStatus.contactPhone = false;
                }
            } else {
                phoneInput.addClass('error-border');
                phoneInput.next().find('.expert-info-error-tip').eq(0).text('手机号不能为空！').show();
                passStatus.contactPhone = false;
            }
        })
    }
    // 邮箱input 事件
    function emailInputEvent() {
        eventOfExpertEmailInputFocus()
        eventOfExpertEmailInputBlur()
    }
    function eventOfExpertEmailInputFocus() {
        emailInput.focus(function () {
            emailInput.removeClass('error-border');
            emailInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfExpertEmailInputBlur() {
        var value = null;
        emailInput.blur(function () {
            value = emailInput.val()
            if(value.length > 0) {
                // 邮箱格式正确
                if (emailRule.test(value)) {
                    emailInput.removeClass('error-border');
                    emailInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.email = value;
                    passStatus.email = true;
                } else {
                    emailInput.addClass('error-border');
                    emailInput.next().find('.expert-info-error-tip').eq(0).text('您输入的邮箱不正确，请重新输入！').show();
                    passStatus.email = false;
                }
            } else {
                emailInput.addClass('error-border');
                emailInput.next().find('.expert-info-error-tip').eq(0).text('邮箱不能为空！').show();
                passStatus.email = false;
            }
        })
    }
    // 头像input 事件
    function expertAvatarEvent() {
        eventOfExpertAvatarCoverClick()
        eventOfExpertAvatarInputChange()
    }
    function eventOfExpertAvatarCoverClick() {
        var cover = personalImageInput.prev();
        cover.off().click(function () {
            if (!isEditModel) {
                return 0;
            }
            personalImageInput.click();
        })
    }
    function eventOfExpertAvatarInputChange() {
        var imgNode = personalImageInput.prev().find('img').eq(0);
        personalImageInput.change(function () {
            uploadFile(personalImageInput.get(0).files, function (data) {
                personalImageInput.prev().removeClass('error-border');
                personalImageInput.next().find('.expert-info-error-tip').eq(0).text('头像不能为空！').hide();
                passStatus.personalImage = true;
                submitData.personalImage = data[0].id;
                // 修改个人信息头像
                imgNode.attr({
                    src: imgNode.getAvatar(data[0].id)
                });
                // 隐藏 + 号
                imgNode.prev().hide();
            })
        })
    }
    // 个人简介input 事件
    function personalProfileEvent() {
        eventOfPersonalProfileInputFocus()
        eventOfPersonalProfileInputBlur()
    }
    function eventOfPersonalProfileInputFocus() {
        personalProfileInput.change(function(){
            personalProfileInput.removeClass('error-border');
            personalProfileInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfPersonalProfileInputBlur() {
        var value = null;
        personalProfileInput.blur(function () {
            value = personalProfileInput.val();
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    personalProfileInput.removeClass('error-border');
                    personalProfileInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.personalProfile = value;
                    passStatus.personalProfile = true;
                } else {
                    personalProfileInput.addClass('error-border');
                    personalProfileInput.next().find('.expert-info-error-tip').eq(0).text('您输入的个人简介中存在违规内容，请重新输入！').show();
                    passStatus.personalProfile = false;
                }
            } else {
                personalProfileInput.addClass('error-border');
                personalProfileInput.next().find('.expert-info-error-tip').eq(0).text('个人简介不能为空！').show();
                passStatus.personalProfile = false;
            }
        })
    }
    // 工作经验input 事件
    function workExperienceEvent() {
        eventOfWorkExperienceInputBlur()
        eventOfWorkExperienceInputFocus()
    }
    function eventOfWorkExperienceInputBlur() {
        workExperienceInput.change(function(){
            workExperienceInput.removeClass('error-border');
            workExperienceInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfWorkExperienceInputFocus() {
        var value = null;
        workExperienceInput.blur(function () {
            value = workExperienceInput.val();
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    workExperienceInput.removeClass('error-border');
                    workExperienceInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.workExperience = value;
                    passStatus.workExperience = true;
                } else {
                    workExperienceInput.addClass('error-border');
                    workExperienceInput.next().find('.expert-info-error-tip').eq(0).text('您输入的工作经验中存在违规内容，请重新输入！').show();
                    passStatus.workExperience = false;
                }
            } else {
                workExperienceInput.addClass('error-border');
                workExperienceInput.next().find('.expert-info-error-tip').eq(0).text('工作经验不能为空！').show();
                passStatus.workExperience = false;
            }
        })
    }
    // 学历情况
    function educationEvent() {
        eventOfEducationEventInputFocus()
        eventOfEducationEventInputBlur()
    }
    function eventOfEducationEventInputFocus() {
        educationInput.change(function() {
            submitData.educationBackground = educationInput.val()
            educationInput.prev().html(getHtmlStr(submitData.educationBackground))
        })
    }
    function eventOfEducationEventInputBlur() {
        var value = null;
        educationInput.blur(function () {
            value = educationInput.val();
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    educationInput.removeClass('error-border');
                    educationInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.educationBackground = value;
                    passStatus.educationBackground = true;
                } else {
                    educationInput.addClass('error-border');
                    educationInput.next().find('.expert-info-error-tip').eq(0).text('您输入的学历情况中存在违规内容，请重新输入！').show();
                    passStatus.educationBackground = false;
                }
            } else {
                educationInput.removeClass('error-border');
                educationInput.next().find('.expert-info-error-tip').eq(0).hide();
                submitData.educationBackground = null;
                passStatus.educationBackground = true;

            }
        })
    }

    // 参与项目input
    function participateProjectEvent() {
        eventOfParticipateProjectInputFocus()
        eventOfParticipateProjectInputBlur()
    }
    function eventOfParticipateProjectInputFocus() {
        participateProjectInput.change(function() {
            submitData.participateProject = participateProjectInput.val()
            participateProjectInput.prev().html(getHtmlStr(submitData.participateProject))
        })
    }
    function eventOfParticipateProjectInputBlur() {
        var value = null;
        participateProjectInput.blur(function () {
            value = participateProjectInput.val();
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    participateProjectInput.removeClass('error-border');
                    participateProjectInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.participateProject = value;
                    passStatus.participateProject = true;
                } else {
                    participateProjectInput.addClass('error-border');
                    participateProjectInput.next().find('.expert-info-error-tip').eq(0).text('您输入的参与项目中存在违规内容，请重新输入！').show();
                    passStatus.participateProject = false;
                }
            } else {
                participateProjectInput.removeClass('error-border');
                participateProjectInput.next().find('.expert-info-error-tip').eq(0).hide();
                submitData.participateProject = null;
                passStatus.participateProject = true;

            }
        })
    }
    // 主要荣誉input
    function mainHonorEvent() {
        eventOfMainHonorInputFocus()
        eventOfMainHonorInputBlur()
    }
    function eventOfMainHonorInputFocus() {
        mainHonorInput.change(function(){
            mainHonorInput.removeClass('error-border');
            mainHonorInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfMainHonorInputBlur() {
        var value = null;
        mainHonorInput.blur(function () {
            value = mainHonorInput.val()
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    mainHonorInput.removeClass('error-border');
                    mainHonorInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.mainHonor = value;
                    passStatus.mainHonor = true;
                } else {
                    mainHonorInput.addClass('error-border');
                    mainHonorInput.next().find('.expert-info-error-tip').eq(0).text('您输入的主要荣誉中存在违规内容，请重新输入！').show();
                    passStatus.mainHonor = false;
                }
            } else {
                mainHonorInput.removeClass('error-border');
                mainHonorInput.next().find('.expert-info-error-tip').eq(0).hide();
                submitData.mainHonor = null;
                passStatus.mainHonor = true;
            }
        })
    }
    // 知识产权/成果input
    function intellectualPropertyEvent() {
        eventOfIntellectualPropertyInputFocus()
        eventOfIntellectualPropertyInputBlur()
    }
    function eventOfIntellectualPropertyInputFocus() {
        intellectualPropertyInput.change(function(){
            intellectualPropertyInput.removeClass('error-border');
            intellectualPropertyInput.next().find('.expert-info-error-tip').eq(0).hide();
        })
    }
    function eventOfIntellectualPropertyInputBlur() {
        var value = null;
        intellectualPropertyInput.blur(function () {
            value = intellectualPropertyInput.val()
            if(value.length > 0) {
                // 格式正确
                if (!errorInputRule.test(value)) {
                    intellectualPropertyInput.removeClass('error-border');
                    intellectualPropertyInput.next().find('.expert-info-error-tip').eq(0).hide();
                    submitData.intellectualProperty = value;
                    passStatus.intellectualProperty = true;
                } else {
                    intellectualPropertyInput.addClass('error-border');
                    intellectualPropertyInput.next().find('.expert-info-error-tip').eq(0).text('您输入的知识产权/成果中存在违规内容，请重新输入！').show();
                    passStatus.intellectualProperty = false;
                }
            } else {
                intellectualPropertyInput.removeClass('error-border');
                intellectualPropertyInput.next().find('.expert-info-error-tip').eq(0).hide();
                submitData.intellectualProperty = null;
                passStatus.intellectualProperty = true;
            }
        })
    }
    // 类型事件
    function industryIdEvent() {
        eventOfIndustryIdListClick()
        eventOfIndustryIdMoreClick()
    }
    function eventOfIndustryIdListClick() {
        var typeId = null;
        industryIdUlList.off().click('click',function(event) {
            var nowNode = $(event.target);
            if (nowNode.hasClass('type-item') && !nowNode.hasClass('active')) {
                nowNode.siblings().removeClass('active');
                nowNode.addClass('active');
                typeId = nowNode.data('id');
                submitData.professionField = typeId;
                submitData.industryId = null;
                submitData.industryId = typeId;
            }
        })
    }
    function eventOfIndustryIdMoreClick() {
        var moreNode = industryIdUlList.next();
        moreNode.off('click').on('click', function() {
            if (moreNode.attr('name') === 'more') {
                moreNode.html('收起 <i class="icon-close-arrow"></i>');
                industryIdUlList.css({
                    height: 'auto'
                });
                moreNode.attr({
                    name: 'collapse'
                });
            } else {
                moreNode.html('展开 <i class="icon-open-arrow"></i>')
                industryIdUlList.css({
                    height: '50px'
                })
                moreNode.attr({
                    name: 'more'
                })
            }
        })
    }
    // 擅长领域事件
    function professionFieldEvent() {
        eventOfProfessionFieldListClick()
        // eventOfProfessionFieldMoreClick()
    }
    function eventOfProfessionFieldListClick() {
        var typeId = null
        professionFieldUlList.off().click('click',function(event) {
            var length = $('.expert-lib-personal-info-List-div #professionField').find('.active').length;
            var nowNode = $(event.target);
            if (nowNode.hasClass('type-item') && !nowNode.hasClass('active')) {
                if (length === 3) {
                    layer.msg("最多只能选择3个");
                    return;
                }
                // nowNode.siblings().removeClass('active');
                nowNode.addClass('active');
            } else if (nowNode.hasClass('type-item') && nowNode.hasClass('active')) {
                nowNode.removeClass('active');
            }
            var newField = $('#professionField').find('.active');
            for (var i = 0; i < newField.length; i++) {
                console.log($(newField[i]).text());
                // submitData.professionField.push($(newField[i]).text());
                // console.log(submitData.professionField);
            }
            typeId = nowNode.data('id');
            // submitData.professionField = typeId;
        })
    }
    // function eventOfProfessionFieldMoreClick() {
    //     var moreNode = professionFieldUlList.next();
    //     moreNode.off('click').on('click', function() {
    //         if (moreNode.attr('name') === 'more') {
    //             moreNode.html('收起 <i class="icon-close-arrow"></i>');
    //             professionFieldUlList.css({
    //                 height: 'auto'
    //             });
    //             moreNode.attr({
    //                 name: 'collapse'
    //             });
    //         } else {
    //             moreNode.html('展开 <i class="icon-open-arrow"></i>');
    //             professionFieldUlList.css({
    //                 height: '50px'
    //             });
    //             moreNode.attr({
    //                 name: 'more'
    //             });
    //         }
    //     })
    // }

    // 编辑按钮监听
    function eventOfEditBtnClick() {
        // 编辑按钮
        var editBtn = $('.expert-lib-personal-info-form-div .form-edit-btn').eq(0)
        // 转换模式
        editBtn.off().click(function() {
            $('#expertAvatar').attr('disabled', false);
            // 写入编辑框数据
            setEditData()
            _this.changeModel('edit')
            editBtn.css({
                display: 'none'
            })
            submitBtn.css({
                display: ''
            }).text('提交').removeAttr('disabled')
            cancelBtn.css({
                display: ''
            }).removeAttr('disabled')
            tipMessage.css({
                display: 'none'
            })
        })
    }
    // 提交按钮监听
    function eventOfSubmitBtnClick() {
        var submitBtn = $('.expert-lib-personal-info-form-div .form-true-btn').eq(0)
        submitBtn.off('click').click(function() {
            // 提交成功
            if (isSubmitSuccess) {
                // 正在审核
                return 0
            }
            var testIndex = 0;
            var keyArr = null;
            // 获取类型多选的值
            getListValue();
            // 判断是否有违法字段
            searchVoidAttr();
            keyArr = Object.keys(passStatus);
            keyArr.forEach(function (key) {
                if (passStatus[key]) {
                    testIndex += 1;
                }
            });
            // 存在未通过的字段
            if (testIndex !== keyArr.length) {
                // console.table(passStatus);
                return 0
            }
            submitData.industryId = submitData.professionField;
            console.log(submitData);
            // 发起请求
            new NewAjax({
                url: '/f/expertsCheckRecords/pc/create_update?pc=true',
                contentType: 'application/json',
                type: 'post',
                data: JSON.stringify(submitData),
                success: function (res) {
                    if (res.status === 200 && res.message === 'OK') {
                        $('#expertAvatar').attr('disabled', 'disabled');
                        // 提交成功
                        isSubmitSuccess = true
                        // 提交按钮隐藏
                        submitBtn.attr({
                            disabled: 'disabled'
                        }).css({
                            display: 'none'
                        })
                        // 隐藏取消按钮
                        cancelBtn.attr({
                            disabled: 'disabled'
                        }).css({
                            display: 'none'
                        })
                        // 修改提示框
                        tipMessage.text('审核中').css({
                            display: '',
                            borderColor: '#aaaaaa',
                            color: '#aaaaaa'
                        })
                        // 更新备份数据
                        dataCopy = JSON.parse(JSON.stringify(submitData));
                        // 切换状态
                        _this.changeModel('show');
                        submitData.professionField = JSON.parse(submitData.skilledField);
                        setShowData()
                    }
                },
                error: function (err) {
                    console.error(err)
                }
            })
        })
    }
    // 取消按钮监听
    function eventOfCancelBtnClick() {
        // 编辑按钮
        var cancelBtn = $('.expert-lib-personal-info-form-div .form-cancel-btn').eq(0)
        // 转换模式
        cancelBtn.off().click(function() {
            $('#expertAvatar').attr('disabled', 'disabled');
            // 重置备份数据
            submitData = JSON.parse(JSON.stringify(dataCopy))
            _this.changeModel('show')
            cancelBtn.css({
                display: 'none'
            })
            submitBtn.css({
                display: 'none'
            })
            editBtn.css({
                display: ''
            })
        })
    }
}