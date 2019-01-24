$(function () {
    var which = "";
    var location = $(".personal-center .address-title .link-location").eq(0);

    var aRightAreas = $('.right-page');

    //当前菜单子项
    var aMenuItems = $(".menu-children-item");

    // 调用设置重要操作标记函数
    setMarkOfImportantOperation();
    // 设置historyHref
    historyHrefSetGetMethod(function (newUrl) {
        if (newUrl === '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true'){
            // 原验证
            /*new NewAjax({
                url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    if (res.data.data_object !== null && !!res.data.data_object.back_check_status && parseInt(JSON.parse(res.data.data_object.back_check_status).id) === 202050){
                        window.location.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
                    } else {
                        window.location.href = '/f/personal_center.html?pc=true#menu=authentication';
                        saveToLocalStorage('history', '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true');
                        toMenu('authentication');
                    }
                },
                error: function (err) {
                    console.error('身份验证请求数据失败，err：' + err);
                }
            })*/
            // 新验证
            new NewAjax({
                url: '/f/serviceProviders/judge_provider?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    var stSave = null;
                    if (res.status === 200) {
                        stSave = res.data.data_object;
                        if (stSave) {
                            window.location.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
                        } else {
                            layer.open({
                                title: '温馨提示',
                                content: '您还未通过企业认证!'
                            });
                            window.location.href = '/f/personal_center.html?pc=true#menu=authentication';
                            saveToLocalStorage('history', '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true');
                            toMenu('myShopList');
                        }
                    }
                },
                error: function (err) {
                    console.error('身份验证请求数据失败，err：' + err);
                }
            })
        }
    });

    toMenu();
    which = window.location.pathname.split("/")[2].split(".")[0];
    handleEventInIndex();
    eventOfLeftMenuModelClick();
    eventOfLeftMenuAnimation();
    clickPublishCaseButton();

    // 切换右模块
    function changeRightPage(className) {
        var oNowArea = null;
        className = className || 'personal-information-div';
        aRightAreas.each(function (index, item) {
            oNowArea = $(item);
            if (className && oNowArea.hasClass(className)){
                oNowArea.addClass('page-active');
            } else {
                oNowArea.removeClass('page-active');
            }
        });
    }
    
    // 页面渲染时从url中获取参数进行匹配显示右侧区域块
    function toMenu (menu) {
        var rightPart = $('.personal-center-right').eq(0),
            tag_a = null;
        menu = menu || getQueryString(window.location.href, 'menu');
        if (menu) {
            // 修改右边框的模式 （非我的商铺模块）
            if (menu !== 'myShopList') {
                // 还原左菜单栏
                leftMenuModelChange();
                // 还原右展示区域
                rightPart.css({
                    width: ''
                })
            }
            // 菜单栏选项选中
            tag_a = getDomByMenu(menu);
            aMenuItems.each(function (index, item) {
                var oNowItem = $(item);
                if (oNowItem.hasClass('menu-active')) {
                    oNowItem.removeClass('menu-active');
                    return false;
                }
            });
            // 选中标注
            $(tag_a).addClass("menu-active");
            /** 切换右边区域块 **/
            if (menu === "userInfo") {
                openModelOfUserInfo();
            } else if (menu === 'authentication') {
                openModelOfAuthentication();
            } else if (menu === "publishDemandList") {
                openModelOfReleaseDemandList();
            } else if (menu === "dockDemandList") {
                openModelOfJoinDemandList();
            } else if (menu === "publishCaseList") {
                openModelOfCaseList();
            } else if (menu === "consultationInformationList") {
                openModelOfUserInfoInCaseList();
            } else if (menu === "consultationResultList") {
                openModelOfUserAskInCaseList();
            } else if (menu === "myShopList") {// 服务商库
                openModelOfCompanyDetail();
            } else if (menu === "messageInformList") {
                openModelOfInfoNotice();
            } else if (menu === "modifyPassword") {
                openModelOfResetPassWord();
            } else if (menu === "demandCollectionList") {
                openModelOfDemandCollectionList();
            } else if (menu === "caseCollectionList") {
                openModelOfCaseCollectionList();
            } else if (menu === "expertCollectionList") {
                openModelOfExpertCollectionList();
            } else if (menu === "merchantCollectionList") {
                openModelOfCompanyCollectionList();
            } else if (menu === "verifyContact") {
                openModelOfResetContact();
            } else if (menu === "deliveredService") {
                openModelOfServerList();
            } else if (menu === "serviceConsultationList") {
                openModelOfUserAskInServerList();
            } else if (menu === "serviceInformationList") {
                openModelOfUserInfoInServerList();
            } else if (menu === "publishService") {
                openModelOfPublishServer();
            } else if (menu === "myShopInformationList") {
                openMyShopInformationList();
            } else if (menu === "myShopConsultationList") {
                openMyShopConsultationList();
            } else if(menu === 'recommend') {
                // 智能推荐
                openModelOfRecommend();
            } else if (menu === 'browseRecord') {
                openModelOfBrowseRecord();
            } else if (menu === 'evaluationCenter') {
                openModelOfEvaluationCenter();
            }
            /*if(menu === 'expertPersonalInfo') {
                openModelOfUserInfoInExpertLibs();
            } else if (menu === "expertConsultationList") {
                openModelOfUserMessageInExpertLibs();
            } else if (menu === "myConsultationList") {
                openModelOfUserAskInExperLibs();
            } else */
        }
    }

    // 开启评论中心模块
    function openModelOfEvaluationCenter() {
        location.text("客户模块");
        changeRightPage('evaluation-center-area');
    }

    // 开启个人信息模块
    function openModelOfUserInfo() {
        location.text("个人信息");
        changeRightPage('personal-information-div');
    }

    // 开启修改联系模块
    function openModelOfResetContact() {
        location.text("联系方式验证");
        // 进行模块判别，若没有则加载对应的js
        if (typeof oVerifyContact === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/myAccount/verifyContact.js", function () {
                oVerifyContact.initDataInVerifyContact();
            });
        } else {
            oVerifyContact.initDataInVerifyContact();
        }
        changeRightPage('verify-contact-area');
    }

    // 开启身份验证模块
    function openModelOfAuthentication() {
        location.text("身份验证");
        var stSave = getLocalStorage('history');
        if (stSave && stSave === '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true') {
            pTipMessage('提示', '您未通过身份认证', 'warning', 2000, true);
            // 消除记录
            saveToLocalStorage('history', null);
        }
        // 进行模块判别，若没有则加载对应的js
        if (typeof windowCompanyAuthentication === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/myAccount/authentication.js", function () {
                windowCompanyAuthentication.initDom();
            });
        } else {
            windowCompanyAuthentication.initDom();
        }

        changeRightPage('authentication-form-area');
        // aRightAreas.removeClass("page-active").siblings(".authentication-form-area").eq(0).addClass("page-active");
    }

    // 开启消息通知模块
    function openModelOfInfoNotice() {
        location.text("消息通知");
        if (typeof messageInformListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/myAccount/messageInform.js", function () {
                // 消除消息显示
                messageInfoResetMessageNumber();
                resetCurrentPageMessageInform();
                getMessageInformList();
            });
        } else {
            // 消除消息显示
            messageInfoResetMessageNumber();
            resetCurrentPageMessageInform();
            getMessageInformList();
        }
        changeRightPage('message-inform-area');
        // aRightAreas.removeClass("page-active").siblings(".message-inform-area").addClass("page-active");
    }

    // 开启密码修改模块
    function openModelOfResetPassWord() {
        location.text("修改密码");

        if (typeof codeUrl === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/myAccount/modifyPassword.js")
        }
        changeRightPage('modify-password-area');
        // aRightAreas.removeClass("page-active").siblings(".modify-password-area").addClass("page-active");
    }

    // 开启发布的需求模块
    function openModelOfReleaseDemandList() {
        var aTabs = $('.review-demand-result-tabs li'),
            aDivs = $('.publish-demand-list > div');
        location.text("发布的需求");

        if (typeof publishDemandListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/demandHall/publishDemandList.js", function () {
                resetCurrentPagePublishDemand();
                getPublishDemandList();
            });
        } else {
            resetCurrentPagePublishDemand();
            getPublishDemandList();
        }

        aTabs.removeClass('active').eq(0).addClass('active');
        aDivs.removeClass('show').eq(0).addClass('show');

        changeRightPage('publish-demand-list-area');
        // aRightAreas.removeClass("page-active").siblings(".publish-demand-list-area").addClass("page-active");
    }

    // 开启报名的需求模块
    function openModelOfJoinDemandList() {
        location.text("对接的需求");

        if (typeof dockDemandListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/demandHall/dockDemandList.js", function () {
                resetCurrentPageDockDemand();
                getDockDemandList();
            });
        } else {
            resetCurrentPageDockDemand();
            getDockDemandList();
        }

        changeRightPage('dock-demand-list-area');
        // aRightAreas.removeClass("page-active").siblings(".dock-demand-list-area").addClass("page-active");
    }

    // 开启已发产品模块
    function openModelOfCaseList() {
        location.text("已发产品");

        if (typeof publishResultListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/caseHall/publishResultList.js", function () {
                resetCurrentPagePublishResult();
                getPublishResultList();
            });
        } else {
            resetCurrentPagePublishResult();
            getPublishResultList();
        }

        changeRightPage('publish-result-area');
        // aRightAreas.removeClass("page-active").siblings(".publish-result-area").addClass("page-active");
    }

    // 开启我的信息（案例中心）模块
    function openModelOfUserInfoInCaseList() {
        location.text("我的信息");

        if (typeof dockResultListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/caseHall/dockResultList.js", function () {
                resetCurrentPageDockResult();
                getDockResultList();
            });
        } else {
            resetCurrentPageDockResult();
            getDockResultList();
        }

        changeRightPage('dock-result-area');
        // aRightAreas.removeClass("page-active").siblings(".dock-result-area").addClass("page-active");
    }
    
    // 开启我的资讯（案例中心）模块
    function openModelOfUserAskInCaseList() {
        location.text("我的咨询");

        if (typeof consultationResultListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/caseHall/consultationResultList.js", function () {
                resetCurrentPageConsultationResult();
                getConsultationResultList();
            });
        } else {
            resetCurrentPageConsultationResult();
            getConsultationResultList();
        }

        changeRightPage('consultation-result-area');
        // aRightAreas.removeClass("page-active").siblings(".consultation-result-area").addClass("page-active");
    }

    // 开启我的企业模块
    function openModelOfCompanyDetail() {
        var rightPart = $('.personal-center-right').eq(0);
        if (window.MyShop === undefined) {
            $.getScript("/static/front/js/personalCenter/main/models/serviceLib/myShop.js", function () {
                // 收起左菜单栏
                // var leftMenu = $('.personal-center-left').eq(0);
                // 添加收起动画
                // leftMenu.addClass('animate-left-collapse');
                // 店铺名称写入缓存
                // window.localStorage.setItem('serviceName', data.name);
                // 我的商铺初始化
                personalCenterMyShop.initData();
                // 更改左边菜单栏模式
                // leftMenuModelChange('expand');
                // 更改右边展示框宽度
                // rightPart.css({
                //     width: '1170px'
                // });
                // 位置改变
                location.text("我的企业");
                // 切换到我的商铺模块
                changeRightPage('my-shop-area');
            });
        } else {
            // 收起左菜单栏
            // var leftMenu = $('.personal-center-left').eq(0);
            // 添加收起动画
            // leftMenu.addClass('animate-left-collapse');
            // 店铺名称写入缓存
            // window.localStorage.setItem('serviceName', data.name);
            // 我的商铺初始化
            personalCenterMyShop.initData();
            // 更改左边菜单栏模式
            // leftMenuModelChange('expand');
            // 更改右边展示框宽度
            // rightPart.css({
            //     width: '1170px'
            // });
            // 位置改变
            location.text("我的企业");
            // 切换到我的商铺模块
            changeRightPage('my-shop-area');
        }
        /*isService(function (data) {
            // 若是服务商
            if (data !== null) {

            } else {// 还没通过服务商验证
                pTipMessage('提示', '您未通过企业认证', 'warning', 2000, true);
                window.localStorage.removeItem('serviceName');

                // 进行模块判别，若没有则加载对应的js
                if (typeof windowCompanyAuthentication === "undefined") {
                    $.getScript("/static/front/js/personalCenter/main/models/myAccount/authentication.js", function () {
                        windowCompanyAuthentication.initDom();
                    });
                } else {
                    windowCompanyAuthentication.initDom();
                }
            }
        })*/
    }
    
    // 开启发布服务模块
    function openModelOfPublishServer() {
        window.location.href = '/f/serviceMessage/pc/to_create_update.html?pc=true';
    }

    // 开启已发服务列表模块
    function openModelOfServerList() {
        location.text("已发服务");

        if (typeof publishServiceListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/oneShopService/publishServiceList.js", function () {
                resetCurrentPagePublishService();
                getPublishServiceList();
            });
        } else {
            resetCurrentPagePublishService();
            getPublishServiceList();
        }

        changeRightPage('publish-service-area');
        // aRightAreas.removeClass("page-active").siblings(".publish-service-area").addClass("page-active");
    }

    // 开启我的信息（服务大厅）模块
    function openModelOfUserInfoInServerList() {
        location.text("我的信息");

        if (typeof informationServiceListData  === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/oneShopService/serviceInformation.js", function () {
                resetCurrentPageInformationService();
                getInformationServiceList();
            });
        } else {
            resetCurrentPageInformationService();
            getInformationServiceList();
        }

        changeRightPage('service-information-area');
        // aRightAreas.removeClass("page-active").siblings(".service-information-area").addClass("page-active");
    }

    // 开启我的资讯（服务大厅）模块
    function openModelOfUserAskInServerList() {
        location.text("我的咨询");

        if (typeof consultationServiceListData  === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/oneShopService/serviceConsultation.js", function () {
                resetCurrentPageConsultationService();
                getConsultationServiceList();
            });
        } else {
            resetCurrentPageConsultationService();
            getConsultationServiceList();
        }

        changeRightPage('service-consultation-area');
        // aRightAreas.removeClass("page-active").siblings(".service-consultation-area").addClass("page-active");
    }

    // 开启我的资料（专家智库）模块
    function openModelOfUserInfoInExpertLibs() {
        location.text("我的资料");

        expertPersonalInfo.getExpertPersonalInfo(function (data) {
            if (data.data_object !== null) {
                expertPersonalInfo.changeModel('show');
                // 函数调用
                expertPersonalInfo.initIndustryIdList();
                expertPersonalInfo.initProfessionFieldList();
                expertPersonalInfo.setData()
            }
        });

        changeRightPage('expert-personal-info-area');
        // aRightAreas.removeClass("page-active").siblings(".expert-personal-info-area").addClass("page-active");
    }

    // 开启我的消息（专家智库）模块
    function openModelOfUserMessageInExpertLibs() {
        location.text("我的信息");

        if (typeof consultationResultListData  === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/expertWisdomLib/consultationInformation.js", function () {
                resetCurrentPageConsultationInformation();
                getConsultationInformationList();
            });
        } else {
            resetCurrentPageConsultationInformation();
            getConsultationInformationList();
        }

        changeRightPage('consultation-information-area');
        // aRightAreas.removeClass("page-active").siblings(".consultation-information-area").addClass("page-active");
    }

    // 开启我的资讯（专家智库）模块
    function openModelOfUserAskInExperLibs() {
        location.text("我的咨询");

        if (typeof myConsultationListData   === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/expertWisdomLib/myConsultation.js", function () {
                resetCurrentPageMyConsultation();
                getMyConsultationList();
            });
        } else {
            resetCurrentPageMyConsultation();
            getMyConsultationList();
        }

        changeRightPage('my-consultation-area');
        // aRightAreas.removeClass("page-active").siblings(".my-consultation-area").addClass("page-active");
    }

    // 开启需求收藏模块
    function openModelOfDemandCollectionList() {
        location.text("需求收藏");

        if (typeof demandCollectionListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/collection/demandCollection.js", function () {
                resetCurrentPageDemandCollection();
                getDemandCollectionList();
            });
        } else {
            resetCurrentPageDemandCollection();
            getDemandCollectionList();
        }

        changeRightPage('demand-collection-area');
        // aRightAreas.removeClass("page-active").siblings(".demand-collection-area").addClass("page-active");
    }

    // 开启案例收藏模块
    function openModelOfCaseCollectionList() {
        location.text("案例收藏");

        if (typeof caseCollectionListData    === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/collection/caseCollection.js", function () {
                resetCurrentPageCaseCollection();
                getCaseCollectionList();
            });
        } else {
            resetCurrentPageCaseCollection();
            getCaseCollectionList();
        }

        changeRightPage('case-collection-area');
        // aRightAreas.removeClass("page-active").siblings(".case-collection-area").addClass("page-active");
    }

    // 开启专家收藏模块
    function openModelOfExpertCollectionList() {
        location.text("专家收藏");

        if (typeof expertCollectionListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/collection/expertCollection.js", function () {
                resetCurrentPageExpertCollection();
                getExpertCollectionList();
            });
        } else {
            resetCurrentPageExpertCollection();
            getExpertCollectionList();
        }

        changeRightPage('expert-collection-area');
        // aRightAreas.removeClass("page-active").siblings(".expert-collection-area").addClass("page-active");
    }

    // 开启服务商收藏模块
    function openModelOfCompanyCollectionList() {
        location.text("服务商收藏");

        if (typeof merchantCollectionListData    === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/collection/merchantCollection.js", function () {
                resetCurrentPageMerchantCollection();
                getMerchantCollectionList();
            });
        } else {
            resetCurrentPageMerchantCollection();
            getMerchantCollectionList();
        }

        changeRightPage('merchant-collection-area');
        // aRightAreas.removeClass("page-active").siblings(".merchant-collection-area").addClass("page-active");
    }

    // 打开我的企业店铺信息
    function openMyShopInformationList () {
        location.text("我的信息");
        if (typeof myShopInformationListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/serviceLib/myShopInformation.js", function () {
                resetCurrentPageMyShopInformation();
                getMyShopInformationList();
            });
        } else {
            resetCurrentPageMyShopInformation();
            getMyShopInformationList();
        }

        changeRightPage('my-shop-information-area');
    }

    // 打开我的企业店铺咨询
    function openMyShopConsultationList () {
        location.text("我的咨询");
        if (typeof myShopConsultationListData === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/serviceLib/myShopConsultation.js", function () {
                resetCurrentPageMyShopConsultation();
                getMyShopConsultationList();
            });
        } else {
            resetCurrentPageMyShopConsultation();
            getMyShopConsultationList();
        }
        changeRightPage('my-shop-consultation-area');
    }

    // 开启智能推荐
    function openModelOfRecommend() {
        var recommend = null;
        location.text('智能推荐');
        if (typeof IntelligentRecommendation === 'undefined') {
            $.getScript("/static/front/js/personalCenter/main/models/customer/recommend.js", function () {
                // 初始化
                recommend = new IntelligentRecommendation();
                recommend.initModuleOfRecommend();
            });
        } else {
            // 重新初始化
            recommend = new IntelligentRecommendation();
            recommend.initModuleOfRecommend();
        }
        changeRightPage('customer-recommend-area');
        return 0;
    }

    // 开启我的足迹
    function openModelOfBrowseRecord() {
        location.text("我的足迹");
        if (typeof oBroseRecord === "undefined") {
            $.getScript("/static/front/js/personalCenter/main/models/customer/browseRecord.js", function () {
                // resetPage();
            });
        } else {
            oBroseRecord.resetPage();
        }
        changeRightPage('browse-record-area');
        return 0;
    }


    // 处理事件
    function handleEventInIndex () {
        // 菜单栏模块的收起展开
        eventOfMenuShow();
        // 菜单栏子项点击事件
        eventOfMenuItemClick();
    }

    // 菜单栏的收起展开
    function eventOfMenuShow() {
        // 收缩菜单栏
        $(".menu-title").off().on("click", function () {
            var icon = $(this).find("i").eq(0);
            if (icon.hasClass("icon-down-triangle")) {
                icon.removeClass("icon-down-triangle");
                icon.addClass("icon-up-triangle");
                $(this).siblings(".menu-children").css("display", "none");
            } else {
                icon.removeClass("icon-up-triangle");
                icon.addClass("icon-down-triangle");
                $(this).siblings(".menu-children").css("display", "block");
            }
        });
    }

    // 菜单栏子项的点击事件
    function eventOfMenuItemClick() {
        // 当前点击项
        var oNowClickNode = null,
            oNowMenuList = $('.menu-area-ul').eq(0),
            menu = null,
            sNowHref = null;
        // 监听所有的menu-children-item
        oNowMenuList.off().on("click", function (event) {
            // 不是菜单子项则不执行以下代码
            if (event.target.tagName.toLowerCase() !== 'li' || !$(event.target).hasClass('menu-children-item')) {
                return 0;
            }
            oNowClickNode = $(event.target);
            // 防止选中后重复点击
            if (oNowClickNode.hasClass('menu-active')) {
                return 0;
            }
            // 当出现重要操作时,进行判定
            if (window.importantOperation && !confirm('系统可能不会保存您所做的更改。')) {
                return 0;
            } else {
                // 消除重要操作状态
                set_IMPORTANTOPERATION(false);
            }
            aMenuItems.each(function (index, item) {
                var oNowItem = $(item);
                if (oNowItem.hasClass('menu-active')) {
                    oNowItem.removeClass('menu-active');
                    return false;
                }
            });
            oNowClickNode.addClass('menu-active');
            if (oNowClickNode.data('href')) {
                sNowHref = oNowClickNode.data('href');
                window.location.href = sNowHref;
                menu = getQueryString(sNowHref.split("?")[1], 'menu');
                toMenu(menu);
            }
        });
    }




    // 获取url里的menu参数值
    function getQueryString(paramString, name) {
        // 原代码
        /*var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        //search,查询？后面的参数，并匹配正则
        var r = paramString.substr(1).match(reg);
        // var rule = /(?=#)[^&]*menu=[^&]*!/;
        if (r != null) return decodeURI(r[2]);*/

        // 修改后/(?<=(#\S*))&?menu=[^&]*/
        // 获取#后字符串正则
        var rule = /#\S*/;
        // 获取对应 name 值的字符串
        var nameRule = new RegExp(name + '=[^&]*');
        // 暂时存储
        var stSave = paramString.match(rule);
        // 获取 name 对应字符串
        if (stSave) {
            stSave = stSave[0].match(nameRule);
            return (stSave) ? stSave[0].split('=').length > 1 ? decodeURI(stSave[0].split('=')[1]) : 'userInfo' : null;
        } else {
            return null;
        }
    }

    // 获取a标签里的href值含有某menu值的dom结构
    function getDomByMenu (menu) {
        var arr = $(".menu-children-item");
        for (var i = 0; i < arr.length; i++) {
            if ($(arr[i]).data('href')) {
                var paramString = $(arr[i]).data('href').split("?")[1];
                var itemMenu = getQueryString(paramString, 'menu');
                if (itemMenu === menu) {
                    return arr[i];
                }
            }
        }
    }

    // 请求当前用户是否为服务商
    function isService (callback) {
        new NewAjax({
            url: '/f/serviceProviders/judge_provider?pc=true',
            contentType: 'application/json',
            type: 'get',
            success: function (res) {
                if (res.status === 200 && callback) {
                    callback(res.data.data_object)
                }
            },
            error: function (err) {
                if (callback) {
                    callback(false)
                }
                console.error("验证用户服务商身份失败，err:" + err)
            }
        })
    }

    // 菜单栏模式变化
    function leftMenuModelChange(model) {
        model = model || 'normal';
        var leftMenu = $('.personal-center-left').eq(0);
        // 若修改模式与当前模式一致，则不修改
        if (leftMenu.attr('type') === model) {
            return 0
        }
        var expandDiv = leftMenu.find('.menu-expand-div').eq(0);
        if (model === 'normal') {
            leftMenu.css({
                position: '',
                top: '',
                left: '',
                zIndex: ''
            }).attr({
                type: 'normal'
            });
            expandDiv.css({
                display: 'none'
            })
        } else if (model === 'expand') {
            leftMenu.css({
                position: 'absolute',
                top: '0',
                left: '0',
                zIndex: 100
            }).attr({
                type: 'expand'
            });
            expandDiv.css({
                display: ''
            })
        }
    }

    // 菜单展开收起模块点击监听
    function eventOfLeftMenuModelClick() {
        var expandDiv = $('.personal-center-left .menu-expand-div').eq(0)
        var leftMenu = expandDiv.parent()
        expandDiv.off().click(function () {
            // 动画为完成时不执行
            if (leftMenu.hasClass('animate-left-expand') || leftMenu.hasClass('animate-left-collapse')) {
                return 0
            }
            // 准备展开
            if (expandDiv.attr('type') === 'hidden') {
                leftMenu.addClass('animate-left-expand')
            } else if (expandDiv.attr('type') === 'show') { // 准备收起
                leftMenu.addClass('animate-left-collapse')
            }
        })
    }

    // 菜单动画监听
    function eventOfLeftMenuAnimation() {
        var leftMenu = $('.personal-center-left').eq(0)
        var expandDiv = leftMenu.find('.menu-expand-div').eq(0)
        leftMenu.on('webkitAnimationEnd', function () {
            // 表示刚做完展开动画
            if (expandDiv.attr('type') === 'hidden') {
                leftMenu.removeClass('animate-left-expand')
                leftMenu.css({
                    left: 0
                })
                expandDiv.text('收起菜单').attr({
                    type: 'show'
                })
            } else if (expandDiv.attr('type') === 'show') {// 表示刚做完收起动画
                leftMenu.removeClass('animate-left-collapse');
                leftMenu.css({
                    left: '-210px'
                });
                expandDiv.text('展开菜单').attr({
                    type: 'hidden'
                })
            }
        })
    }

    // 发布案例的按钮的验证和跳转
    function clickPublishCaseButton() {
        $('.publish-case-button').click(function () {
            if (typeof window.historyHref === "object") { // 没有definePrototype的情况
                window.historyHref.href = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
            } else {
                window.historyHref = '/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true';
            }
        })
    }
});

// 时间戳转换格式
function fmtDate(data){
    var date =  new Date(data);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}

// 初始化时间控件
function initDateTimePicker(parentsClass) {
    $("." + parentsClass).find(".search-star-time").datetimepicker({
        format: 'YYYY-MM-DD',//显示格式
        locale: moment.locale('zh-cn')
    })
    $("." + parentsClass).find(".search-end-time").datetimepicker({
        format: 'YYYY-MM-DD',//显示格式
        locale: moment.locale('zh-cn')
    })
}