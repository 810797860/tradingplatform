// 获取MyShop对象
var personalCenterMyShop = new MyShop();
// 初始化dom
personalCenterMyShop.initDom();
// 初始化富文本
personalCenterMyShop.initRichText();
// 点击事件处理
personalCenterMyShop.handleClickEvent();

function MyShop() {
    var _this = this;
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

    // 存储富文本
    var saveNewHomeContent = null;
    // 存储请求的审核数据
    var editData = {
        id: null,
        detailCover: null,
        personalizedHomepage: null
    };
    // 获取富文本节点
    var richText = $('#myShopHome');
    // 获取展示div
    var shopHomeShowNode = $('#shopHomeContent');
    // 商店名节点
    var oServiceNameNode = $('#serviceShopName');
    // 提交状态
    // var isSubmitSuccess = false
    // 编辑按钮
    var editBtn = $('#shopHomeEditBtn');
    // 提交按钮
    var submitBtn = $('#shopHomeEditTrueBtn');

    // 初始化富文本
    _this.initRichText = function () {
        //初始化富文本插件
        richText = CKEDITOR.replace('myShopHome', {
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
        });
    };
    // 初始数据
    _this.initData = function () {
        //  设置评价的头部数据
        setEvaluationHeadData();
    };
    // 初始化dom结构
    _this.initDom = function () {
        $("#detail-data").show();
        $("#case-hall").hide();
        $("#complete-task").hide();
        $("#transaction-evaluate").hide();
        setDetailData();
        // 更新案例展示solutionCard的样式
        updateSolutionCard();
    };
    // 点击事件
    _this.handleClickEvent = function () {
        var nowTab = null;
        var nowTabName = '';
        var homeModel = $('#tab-index');
        var detailModel = $("#detail-data");
        var caseModel = $("#case-hall");
        var taskModel = $("#complete-task");
        var appraisal = $("#transaction-evaluate");
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
                getCaseDataPage();
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
                getTransactionDataPage();
            } else if (nowTabName === 'home') {
                $_tabActive = 0;
                homeModel.siblings().css("display", 'none');
                homeModel.css("display", 'block');
            }
        });
        // 监听案例展示分页跳转
        $(".case-hall #pageToolbar").on("click", function () {
            if (nowTab.data('currentpage') != currentPage) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getCaseDataPage();
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $('#pageToolbar').data('currentpage');
                getCaseDataPage();
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
                getTransactionDataPage();
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                clickPage = 0;
                currentPage = $("#pageToolbar").data('currentpage');
                getTransactionDataPage();
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
                getTransactionDataPage();
            } else if ($(this)[0].innerText === "差评") {
                clickPage = 1;
                currentPage = 1;
                evaluationStandard = 2;
                evaluationType = "差评";
                getTransactionDataPage();
            } else {
                clickPage = 1;
                currentPage = 1;
                evaluationStandard = 0;
                evaluationType = "好评";
                getTransactionDataPage();
            }
        });
        /*** 完成任务跳转到需求大厅详情页 ***/

        $(".complete-task-card .title").on("click", function () {
            var id = $(this).parents(".complete-task-card").data('id');
            window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
        });

        /*** 案例展示跳转到案例大厅详情页 ***/

        $(".solution-card .title").on("click", function () {
            var id = $(this).parents(".solution-card").data('id');
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
        eventOfEditSubmitBtnClick();

        // 编辑取消按钮点击事件
        eventOfEditCancelBtnClick();
    };
    // 获取服务商名称 （在全局点击事件中写入名称）
    _this.getServiceName = function () {
        oServiceNameNode.text(window.localStorage.getItem('serviceName'));
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
        $(".transaction-num").html(valueFilter($_serviceLibInfo['total_transaction'], '暂无数据')); //交易总额
        $(".package-num").html(valueFilter($_serviceLibInfo['task_amount'], '暂无数据')); //接包数
        $(".evaluate-num").html(($_serviceLibInfo['favorable_rate'] !== undefined && $_serviceLibInfo['favorable_rate'] !== null && $_serviceLibInfo['favorable_rate'] !== '') ? $_serviceLibInfo['favorable_rate'] + '%' : '暂无数据'); //好评率
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
        if (!!$_serviceLibInfo['user_id']) {
            var userInfo = JSON.parse($_serviceLibInfo['user_id']);
            $(".contacts-content").html(userInfo['user_name']); //联系人
            if (!!userInfo['email']) {
                $(".email-content").html(userInfo['email']); //邮箱
                $(".email-authentication i").removeClass("icon-false").addClass("icon-true");
            }
        }
        if (!!$_serviceLibInfo['contact_wechat']) {
            $(".wechat").attr("src", $(this).getAvatar($_serviceLibInfo['contact_wechat']));
            $(".weixin-authentication i").removeClass("icon-false").addClass("icon-true");
        } else {
            $(".weixin-authentication i").removeClass("icon-true").addClass("icon-false");
        }
        $(".qq-content").html(valueFilter($_serviceLibInfo['contact_qq'], '暂无数据')); //QQ
        // 服务商简介
        $(".service-lib-introduction-content").html(valueFilter($_serviceLibInfo['introduction'], '暂无数据'));
        // 主营业务
        $(".main-business-content").html(valueFilter($_serviceLibInfo['main_business'], '暂无数据'));
    }

    // 请求案例数据
    function getCaseDataPage() {
        var json = {
            "id": $_serviceLibInfo.id,
            "pager": {
                "current": currentPage,
                "size": searchSizeCase
            }
        };
        new NewAjax({
            type: "POST",
            url: "/f/serviceProviders/get_detail_provider_case",
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
                if ($.parseJSON(list[i]['picture_cover'])) {
                    $(caseHallCard[i]).find(".solution-card-image").attr("src", $(this).getAvatar($.parseJSON(list[i]['picture_cover'])[0]['id']));
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
            "providerId": $_serviceLibInfo.id,
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
            url: "/f/serviceProvidersEvaluation/get_provider_evaluation_page",
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

    // 文件上传
    function uploadFile(files, callback) {
        if (files.length === 0) return; //如果文件为空
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
                if (res.status === 200 && callback) {
                    callback(res.data.data_list)
                }
            },
            error: function (err) {
                console.error('上传头像：' + err)
            }
        });
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
    function eventOfEditSubmitBtnClick() {
        var editArea = submitBtn.parent();
        var showArea = editArea.prev();
        submitBtn.off().click(function () {
            // 获取富文本信息
            saveNewHomeContent = richText.getData();
            // 存入富文本信息
            editData.personalizedHomepage = saveNewHomeContent;
            // 提交修改信息
            submitEditData(function () {
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
    }

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
}

// 时间戳转日期
function getMyDate(str) {
    var oDate = new Date(str),
        oYear = oDate.getFullYear(),
        oMonth = oDate.getMonth() + 1,
        oDay = oDate.getDate(),
        oTime = oYear + '-' + getzf(oMonth) + '-' + getzf(oDay);//最后拼接时间
    return oTime;
};

//补0操作
function getzf(num) {
    if (parseInt(num) < 10) {
        num = '0' + num;
    }
    return num;
}
