$(function () {
    var $_serviceLibInfo = serviceLibInfo;
    var $_evaluationHead = evaluationHead;
    var $_tabActive = 1;
    var searchSize = 5;
    var searchSizeTransaction = 5;
    var searchSizeProduct = 8;
    var currentPage = 1;
    var clickPage = 0;  // 判断是否点击页数,0为是
    var evaluationStandard = 0; // 判断交易评价的评价标签
    var evaluationType = "好评";

    /**
     * 轮播区域变量
     * author: GloryAce
     */
    var oSmall = $(".service-lib-detail .slider-area")[0];
    var oUlSmall = $(".service-lib-detail .slider-area ul")[0];
    var oOrderBtn = $('.service-lib-detail .slider-area .orderBtn');
    var oBtnPrev = $('.service-lib-detail .slider-area .prev')[0];
    var oBtnNext = $('.service-lib-detail .slider-area .next')[0];
    var vrBtn = $('.detail-header .vr-button').eq(0);
    var iSmallPicWidth = 290;   // Li的宽度
    var now = 0;            // 当前轮播序号
    var timer = null;
    var interval = 3000;    // 轮播时间间隔

    var sliders = [
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/1134045435-1.jpg',
            title: '11111'
        },
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/1134041S9-2.jpg',
            title: '22222'
        },
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/1134044641-3.jpg',
            title: '33333'
        },
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/1134043T4-4.jpg',
            title: '44444'
        },
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/1134043S0-5.jpg',
            title: '55555'
        },
        {
            src: 'https://www.gexings.com/uploads/allimg/130516/11340460D-6.jpg',
            title: '66666'
        }
    ];

    /**
     * 函数调用
     * author：GloryAce
     */
    init_dom();
    init_data();
    // initRichText()
    // 事件处理
    handleEvent();
    // 设置超出提示
    setTextOverTip();

    /**
     * 初始化数据
     * author：GloryAce
     */
    // 初始数据
    function init_data() {
        // 轮播图区域
        // getSliderList(eventOfSlider);
        // 设置评价的头部数据
        setEvaluationHeadData();
        // 获取最新产品列表
        getNewestProductList();
        // 获取热门产品列表
        getPopularProductList();
    }

    // 初始化dom结构
    function init_dom() {
        $("#detail-data").css("display", 'none');
        $("#case-hall").css("display", 'none');
        $("#complete-task").css("display", 'none');
        $("#transaction-evaluate").css("display", 'none');
        $("#detail-data").siblings().css("display", 'none');
        $("#detail-data").css("display", 'block');
        if (!!providerResult) {
            if (!!providerResult.logo) {
                $('.detail-header-left-logo').attr('src', '/adjuncts/file_download/' + JSON.parse(providerResult.logo).id)
            }

            $('.detail-header-right-area .expertise-item').remove();
            // console.log(JSON.parse(providerResult.skilled_field));
            // 设置头部的擅长领域
        }
    }

    /**
     * 事件处理函数
     * author: GloryAce
     */
    function handleEvent() {
        /***** 切换排序tab *****/
        $(".service-lib-detail .tab .tab-item").on("click", eventOfChangeTab);

        /****** 监听分页 ******/
        // 监听产品展示分页跳转
        $(".service-lib-detail #pageToolbar").on("click", function () {
            eventOfPageClick.call(this, getProductList);
        }).keydown(function (e) {
            eventOfPageKeydown.call(this, e, getProductList);
        });
        // 监听交易评价分页跳转
        $(".transaction-evaluate #pageToolbar").on("click", function () {
            eventOfPageClick.call(this, getTransactionDataPage);
        }).keydown(function (e) {
            eventOfPageKeydown.call(this, e, getTransactionDataPage);
        });

        /***** 切换交易评价按钮 *****/
        $(".middle-evaluate div").on("click", function () {
            eventOfChangeEvaluateType.call(this, getTransactionDataPage);
        });

        /*** 跳转到对应详情页 ***/
        eventOfToDetailPage();


        // VR展厅按钮点击跳转
        vrBtn.click(function () {
            if (!!providerResult.digital_id) {
                window.open('/f/' + providerResult.digital_id + '/number_hall_detail.html?pc=true');
            }
        })

        // 点击图标跳到关于我们
        $('.about-us').click(function () {
            $('.tab .tab-item ').removeClass('active');
            $('.tab .tab-item').eq(2).addClass('active');
            // resetCurrentPageCase();
            $_tabActive = 3;
            $("#about-us").siblings().css("display", 'none');
            $("#about-us").css("display", 'block');
            // getDetailData();
            setDetailData();
        })
        // 收藏/取消收藏服务商
        // $(".collect-area").off().on("click", eventOfCollect);
    }

    // 事件：改变tab
    function eventOfChangeTab() {
        $(".service-lib-detail .tab .tab-item").removeClass("active");
        $(this).addClass("active");

        if ($(this).index() === 0) {
            // 详细资料
            $_tabActive = 1;
            $('#splitpage').hide();
            $("#detail-data").siblings().css("display", 'none');
            $("#detail-data").css("display", 'block');
        } else if ($(this).index() === 1) {
            $('#splitpage').show();
            // 产品展示
            resetCurrentPage();
            $_tabActive = 2;
            $("#case-hall").siblings().css("display", 'none');
            $("#case-hall").css("display", 'block');
            getProductList();
        } else if ($(this).index() === 2) {
            $('#splitpage').hide();
            // 关于我们
            resetCurrentPage();
            $_tabActive = 3;
            $("#about-us").siblings().css("display", 'none');
            $("#about-us").css("display", 'block');
            setDetailData();
            // getAboutUsData();
        } else if ($(this).index() === 3) {
            // 交易评价
            resetCurrentPage();
            // 重设默认显示好评
            $(".middle-evaluate div:first-child").addClass("evaluate-button-active").removeClass("evaluate-button").siblings().addClass("evaluate-button").removeClass("evaluate-button-active");
            evaluationStandard = 0;
            $_tabActive = 4;
            $("#transaction-evaluate").siblings().css("display", 'none');
            $("#transaction-evaluate").css("display", 'block');
            getTransactionDataPage();
        }
    }

    // 事件：监听分页
    function eventOfPageClick(func) {
        if ($(this).data('currentpage') != currentPage) {
            clickPage = 0;
            currentPage = $(this).data('currentpage');
            func();
        }
    }

    function eventOfPageKeydown(e, func) {
        if (e.keyCode == 13) {
            clickPage = 0;
            currentPage = $(this).data('currentpage');
            func();
        }
    }

    // 切换评价类型：好评、中评、差评
    function eventOfChangeEvaluateType(func) {
        $(this).addClass("evaluate-button-active").removeClass("evaluate-button").siblings().addClass("evaluate-button").removeClass("evaluate-button-active");
        clickPage = 1;
        currentPage = 1;
        if ($(this).index() === 1) {
            evaluationStandard = 1;
            evaluationType = "中评";
        } else if ($(this).index() === 2) {
            evaluationStandard = 2;
            evaluationType = "差评";
        } else {
            evaluationStandard = 0;
            evaluationType = "好评";
        }
        func();
    }

    // 收藏
    // function eventOfCollect () {
    //     if ($(this).children("i").hasClass("icon-star-void")) {
    //         toCollectTheService($(this), $_serviceLibInfo.id, true);
    //     } else {
    //         toCollectTheService($(this), $_serviceLibInfo.id, false);
    //     }
    // }
    $(".collection-shop").off().on("click", function () {
        if ($(this).attr("collection") == 'false') {
            toCollectTheService($(this), $_serviceLibInfo.id, true);
        } else {
            toCollectTheService($(this), $_serviceLibInfo.id, false);
        }
    });

    // 在线咨询
    $('.online-contact').click(function () {
        var user = window.localStorage.getItem('user');
        if (!!user) {
            layer.open({
                type: 1,
                title: "在线咨询",
                skin: 'layui-layer-lan', //加上边框
                area: ['500px', '330px'], //宽高
                content: '<div class="askBtnContents"><textarea style="width: 90%;height: 190px;padding: 10px;box-sizing: border-box;margin-left: 5%;margin-top: 20px"></textarea><button class="askBtnSubmit" style="margin-left: 200px;width: 100px;height: 30px;line-height: 30px;font-size: 14px;color: white;background-color: #0066cc;border: none;border-radius: 10px;outline: none;margin-top: 20px;cursor: pointer">提交</button></div>'
            });
        } else {
            layer.msg('登录后才能咨询');
            setTimeout(function () {
                window.open('/f/login.html?pc=true', '_self');
            }, 1000);
        }
    })

    // 在线咨询提交按钮点击事件
    $(document).on('click', '.askBtnSubmit', function () {
        var json = {
            providerId: providerResult.id,
            contents: filterSensitiveWord($('.askBtnContents textarea').val())
        };
        if (json.contents == '') {
            layer.msg("咨询内容不能为空")
        } else {
            $('.askBtnSubmit').attr('disabled', true);
            new NewAjax({
                url: "/f/serviceProvidersConsulting/pc/create_update?pc=true",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        layer.msg("资讯成功,请前往个人中心查看");
                        setTimeout(function () {
                            layer.closeAll();
                        }, 1000)
                    } else {
                        layer.msg("资讯失败");
                    }
                }
            })
        }
    })


    // 跳转到详情页
    function eventOfToDetailPage() {
        // 完成的任务
        $(".complete-task-card .title").on("click", function () {
            var id = $(this).parents(".complete-task-card").data('id');
            // window.location.href = '/f/' + id + '/demand_detail.html?pc=true';
            window.open('/f/' + id + '/demand_detail.html?pc=true');
        });
        // 案例展示
        $(".solution-card .title").on("click", function () {
            var id = $(this).parents(".solution-card").data('id');
            // window.location.href = '/f/' + id + '/case_detail.html';
            window.open('/f/' + id + '/case_detail.html');
        });
        // 交易评价
        $('.service-lib-detail .transaction-evaluate .technology-name').click(function () {
            // console.log($(this).attr('demandId'));
            var demandId = $(this).attr('demandId');
            window.open('/f/' + demandId + '/demand_detail.html?pc=true')
        });
    }


    /**
     * 轮播图区域
     * @param callback
     * author: GloryAce
     */
    function getSliderList(callback) {
        setSliderList(sliders, callback);
    }

    function setSliderList(list, callback) {
        var fragmentOfSmall = document.createDocumentFragment();
        for (var i = 0; i < list.length; i++) {
            var liItemOfSmall = null;
            if (i === 0) {
                liItemOfSmall = $('<li style="filter: alpha(opacity: 100); opacity: 1;"><img src="' + list[i].src + '" alt="' + list[i].title + '"/></li>');
            } else {
                liItemOfSmall = $('<li><img src="' + list[i].src + '" alt="' + list[i].title + '"/></li>');
            }
            fragmentOfSmall.appendChild(liItemOfSmall[0]);
        }
        oUlSmall.appendChild(fragmentOfSmall);
        $(oUlSmall).css('width', list.length * iSmallPicWidth + 'px');
        // 事件监听
        if (callback) {
            callback();
        }
    }

    // 轮播图事件监听函数
    function eventOfSlider() {
        var aLiSmall = $(oUlSmall).find("li");
        /**** 左右按钮点击 ****/
        oBtnPrev.onclick = function () {
            now--;
            if (now === -1) {
                now = aLiSmall.length - 1;
            }
            sliderMove();
        };
        oBtnNext.onclick = function () {
            now++;
            if (now === aLiSmall.length) {
                now = 0;
            }
            sliderMove();
        };
        /**** 点击小图 ****/
        for (var i = 0; i < aLiSmall.length; i++) {
            aLiSmall[i].index = i;
            aLiSmall[i].onclick = function () {
                if (this.index === now) return;
                now = this.index;
                sliderMove();
            };
            aLiSmall[i].onmouseover = function () {
                startMove(this, {opacity: 100});
            };
            aLiSmall[i].onmouseout = function () {
                if (this.index != now) {
                    startMove(this, {opacity: 60});
                }
            };
        }
        /**** 启动动画 ****/
        timer = setInterval(oBtnNext.onclick, interval);
        oSmall.onmouseover = function () {
            oOrderBtn.css("display", 'block');
            clearInterval(timer);
            timer = null;
        };
        oSmall.onmouseout = function () {
            oOrderBtn.css("display", 'none');
            clearInterval(timer);
            timer = setInterval(oBtnNext.onclick, interval);
        };
    }

    // 动画执行函数
    function sliderMove() {
        var aLiSmall = $(oUlSmall).find("li");
        for (var j = 0; j < aLiSmall.length; j++) {
            startMove(aLiSmall[j], {opacity: 60});
        }
        startMove(aLiSmall[now], {opacity: 100});
        if (now === 0) {
            startMove(oUlSmall, {left: 0});
        } else if (now === aLiSmall.length - 2) {
            startMove(oUlSmall, {left: -(now - 2) * aLiSmall[0].offsetWidth});
        } else if (now === aLiSmall.length - 1) {
            startMove(oUlSmall, {left: -(now - 3) * aLiSmall[0].offsetWidth});
        } else {
            startMove(oUlSmall, {left: -(now - 1) * aLiSmall[0].offsetWidth});
        }
    }


    /**
     * 数据请求与显示
     * author: GloryAce
     */
    // 用户收藏/ 取消收藏
    function toCollectTheService(dom, id, isCollect) {
        var json = {
            "serviceProvidersId": id,
            "isCollection": isCollect
        };
        console.log(isCollect);
        new NewAjax({
            type: "POST",
            url: "/f/serviceProviders/pc/collection_service_providers?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                // 403： 未登录
                if (res.status === 403) {
                    window.location.href = "/f/login.html?pc=true";
                } else if (res.status === 200) {
                    if (!isCollect) {
                        layer.msg("取消收藏成功！");
                        $('.collection-shop').text('收藏店铺').attr('collection', 'false')
                        // $(dom).children("i").attr("class", "icon-star-void")
                    } else {
                        layer.msg("收藏成功！");
                        $('.collection-shop').text('取消收藏').attr('collection', 'true')
                    }
                    $(dom).children(".collect-num").html(res.data.total)
                }
            }
        })
    }

    // 请求最新展品数据
    function getNewestProductList() {
        var json = {
            "providerId": $_serviceLibInfo.id,
            "pager": {
                "current": 1,
                "size": 4
            }
        };
        new NewAjax({
            type: "POST",
            url: "/f/matureCase/get_lasted_mature_case?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    setNewestProductList(list);
                }
            }
        })
    }

    // 设置最新展品数据
    function setNewestProductList(list) {
        var aNewestProduceNode = $(".newest-produce-area .product-item");
        for (var i = 0; i < aNewestProduceNode.length; i++) {
            if (i >= list.length) {
                aNewestProduceNode.eq(i).css("display", 'none');
            } else {
                aNewestProduceNode.eq(i).find(".product-card").eq(0).attr("href", '/f/' + list[i].id + '/case_detail.html?pc=true');
                aNewestProduceNode.eq(i).find(".title").text(list[i].title);
                aNewestProduceNode.eq(i).find(".type").text('行业：' + (list[i].application_industry ? JSON.parse(list[i].application_industry)[0].title : '暂无数据'));
                aNewestProduceNode.eq(i).find(".address").text('所属地区：' + (list[i].address ? list[i].address : '暂无数据'));
                aNewestProduceNode.eq(i).find(".product-img").attr("src", list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(',')[0]) : null);
                aNewestProduceNode.eq(i).find(".see-num").text(list[i].click_rate);
                aNewestProduceNode.eq(i).find(".money").text('￥ ' + (list[i].case_money ? list[i].case_money + '万元' : '面议'));
            }
        }
    }

    // 请求热门展品数据
    function getPopularProductList() {
        var json = {
            "providerId": $_serviceLibInfo.id,
            "pager": {
                "current": 1,
                "size": 4
            }
        };
        $.ajax({
            url: '/f/matureCase/get_recommend_mature_case?pc=true',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    setPopularProductList(list)
                }
            },
            error: function (err) {
                console.error('产品', err);
            }
        });
    }

    // 设置热门展品数据
    function setPopularProductList(list) {
        var aPopularProduceNode = $(".popular-produce-area .product-item");
        for (var i = 0; i < aPopularProduceNode.length; i++) {
            if (i >= list.length) {
                aPopularProduceNode.eq(i).css("display", 'none');
            } else {
                aPopularProduceNode.eq(i).find(".product-card").eq(0).attr("href", '/f/' + list[i].id + '/case_detail.html?pc=true');
                aPopularProduceNode.eq(i).find(".title").text(list[i].title);
                aPopularProduceNode.eq(i).find(".type").text('行业：' + (list[i].application_industry ? JSON.parse(list[i].application_industry).title : '暂无数据'));
                aPopularProduceNode.eq(i).find(".address").text('所属地区：' + (list[i].address ? list[i].address : '暂无数据'));
                aPopularProduceNode.eq(i).find(".product-img").attr("src", list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(',')[0]) : null);
                aPopularProduceNode.eq(i).find(".see-num").text(list[i].click_rate);
                aPopularProduceNode.eq(i).find(".money").text('￥ ' + (list[i].case_money ? list[i].case_money + '万元' : '面议'));
            }
        }
    }

    // 请求产品展示数据
    function getProductList() {
        var json = {
            "id": $_serviceLibInfo.id,
            "pager": {
                "current": currentPage,
                "size": searchSizeProduct
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
                if (totalRecord > 0) {
                    if (currentPage >= totalRecord / searchSizeProduct) {
                        $('.service-lib-detail #pageToolbar').find('li.js-page-next').eq(0).addClass('ui-pager-disabled');
                    } else {
                        $('.service-lib-detail #pageToolbar').find('li.js-page-next').eq(0).removeClass('ui-pager-disabled');
                    }
                    if (clickPage === 0) {
                        $('.service-lib-detail #pageToolbar').Paging({
                            pagesize: searchSizeProduct,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.service-lib-detail #pageToolbar').find("div:eq(1)").remove();
                    } else {
                        $('.service-lib-detail #pageToolbar').Paging({
                            pagesize: searchSizeProduct,
                            count: totalRecord,
                            toolbar: true
                        });
                        $('.service-lib-detail #pageToolbar').find("div:eq(0)").remove();
                        clickPage = 0;
                    }
                    $('.service-lib-detail #splitpage').css("display", "block");
                    $(".case-hall .noData").css("display", "none");
                } else {
                    $('.service-lib-detail #splitpage').css("display", "none");
                    $(".case-hall .noData").css("display", "block");
                }
                setProductList(list);
            }
        })
    }

    // 写入产品展示数据
    function setProductList(list) {
        var productItem = $(".case-hall .product-item");
        for (var k = 0; k < productItem.length; k++) {
            if (k >= list.length) {
                $(productItem[k]).css("display", 'none')
            } else {
                $(productItem[k]).css("display", 'inline-block')
            }
        }
        if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                $(productItem[i]).find('.product-card').eq(0).attr("href", "/f/" + list[i]['id'] + "/case_detail.html"); // href
                $(productItem[i]).find(".title").html(list[i]['title']); // 标题
                $(productItem[i]).find(".title").attr('title', list[i]['title']); // 标题
                // 行业
                if (list[i]['application_industry']) {
                    var aInd = JSON.parse(list[i]['application_industry']);
                    for (var j = 0; j < aInd.length; j++) {
                        if (j === 0) {
                            $(productItem[i]).find(".type").text('行业：' + aInd[j].title);
                        } else {
                            $(productItem[i]).find(".type").text($(productCard[i]).find(".type").text() + '、' + aInd[j].title);
                        }
                    }
                }
                $(productItem[i]).find(".address").html('所属地区：' + (list[i]['address'] ? list[i]['address'] : '暂无数据')); // 地区

                $(productItem[i]).find(".see-num").html(list[i]['click_rate']); // 点击数
                if (list[i]['case_money'] !== 0) {
                    $(productItem[i]).find(".money").html('￥' + list[i]['case_money'] + ' 万元'); // 金额
                } else {
                    $(productItem[i]).find(".money").html('￥' + ' 面议'); // 金额
                }
                if ($.parseJSON(list[i]['picture_cover'])) {
                    $(productItem[i]).find(".product-img").attr("src", $(this).getAvatar($.parseJSON(list[i]['picture_cover'])[0]['id']));
                }
            }
        } else {
            console.log("暂无数据");
        }

    }

    // 写入详细资料
    function setDetailData() {
        // console.log($_serviceLibInfo);
        if ($_serviceLibInfo) {
            // 公司名(大标题)
            $(".detail-data .big-title").html($_serviceLibInfo['name']); //名字
            // 能力
            if (!!$_serviceLibInfo['total_transaction'] && $_serviceLibInfo['total_transaction'] !== 0) {
                $(".transaction-num").html($_serviceLibInfo['total_transaction'] + '万元'); //交易总额
                $(".transaction-num").attr('title', $_serviceLibInfo['total_transaction'] + '万元'); //交易总额
            } else if (!!$_serviceLibInfo['total_transaction'] && $_serviceLibInfo['total_transaction'] == 0) {
                $(".transaction-num").text('面议');
            } else {
                $(".transaction-num").text('暂无数据');
            }
            $(".package-num").html(valueFilter($_serviceLibInfo['task_amount'] !== 0 ? $_serviceLibInfo['task_amount'] : '暂无数据', '暂无数据')); //接包数
            if ($_serviceLibInfo) {
                if ($_serviceLibInfo['favorable_rate']) {
                    $(".evaluate-num").html($_serviceLibInfo['favorable_rate'] + '%'); //好评率
                } else {
                    $(".evaluate-num").html('暂无数据'); //好评率
                }
            } else {
                $(".evaluate-num").html('暂无数据'); //好评率
            }

            $(".capacity-num").html($_serviceLibInfo['qualification']); //能力值
            $(".capacity-num").attr('title', $_serviceLibInfo['qualification']); //能力值
            // 联系方式
            $(".area-content").html($_serviceLibInfo['address']); //所属地区
            $(".area-content").attr('title', $_serviceLibInfo['address']); //所属地区
            $(".telephone-content").html($_serviceLibInfo['fixed_telephone']); //电话
            $(".address-content").html($_serviceLibInfo['address_detail']); //详细地址
            $(".address-content").attr({
                title: $_serviceLibInfo['address_detail']
            }); //详细地址
            if ($_serviceLibInfo['phone']) {
                $(".phone-content").html($_serviceLibInfo['phone']); //手机
                $(".phone-authentication i").removeClass("icon-false").addClass("icon-true");
            }
            if (!!$_serviceLibInfo['user_id']) {
                var userInfo = JSON.parse($_serviceLibInfo['user_id']);
                $(".contacts-content").html(userInfo['user_name']); //联系人
                if (!!$_serviceLibInfo['email']) {
                    $(".email-content").html($_serviceLibInfo['email']); //邮箱
                    $(".email-authentication i").removeClass("icon-false").addClass("icon-true");
                }
            }
            // if (!!$_serviceLibInfo['contact_wechat']){
            //     // $(".weixin-content").html(list.contact_wechat); //微信
            //     $(".wechat").attr("src", $(this).getAvatar($_serviceLibInfo['contact_wechat']));
            //     $(".weixin-authentication i").removeClass("icon-false").addClass("icon-true");
            // }
            $(".qq-content").html($_serviceLibInfo['contact_qq']); //QQ
            $(".contact-way-weixin-content").html($_serviceLibInfo['official_website']);
            // 服务商简介
            $(".service-lib-introduction-content").html($_serviceLibInfo['introduction']);
            // 主营业务
            $(".main-business-content").html($_serviceLibInfo['main_business']);
            $(".glory-qualifications-content").html($_serviceLibInfo['honor']);
        } else {
            console.log("详细资料注入没数据")
        }
    }

    // 请求关于我们数据
    /*function getAboutUsData () {
        $('.service-lib-detail #splitpage').css("display", "none");
        $(".about-us .noData").css("display", "block");
    }*/

    // 获取评价数据
    function getTransactionDataPage() {
        if ($_serviceLibInfo) {
            var json = {
                // $_serviceLibInfo.id
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
                    var totalRecord = res.data.total;
                    console.log(res);
                    if (totalRecord > 0) {
                        if (currentPage >= totalRecord / searchSizeTransaction) {
                            $('.service-lib-detail #pageToolbar').find('li.js-page-next').eq(0).addClass('ui-pager-disabled');
                        } else {
                            $('.service-lib-detail #pageToolbar').find('li.js-page-next').eq(0).removeClass('ui-pager-disabled');
                        }
                        if (clickPage === 0) {
                            $('.service-lib-detail #pageToolbar').Paging({
                                pagesize: searchSizeTransaction,
                                count: totalRecord,
                                toolbar: true
                            });
                            $('.service-lib-detail #pageToolbar').find("div:eq(1)").remove();
                        } else {
                            $('.service-lib-detail #pageToolbar').Paging({
                                pagesize: searchSizeTransaction,
                                count: totalRecord,
                                toolbar: true
                            });
                            $('.service-lib-detail #pageToolbar').find("div:eq(0)").remove();
                            clickPage = 0;
                        }
                        $('.service-lib-detail #splitpage').css("display", "block");
                        $(".transaction-evaluate .noData").css("display", "none");
                    } else {
                        $('.service-lib-detail #splitpage').css("display", "none");
                        $(".transaction-evaluate .noData").css("display", "block");
                    }
                    setTransactionData(list);
                }
            })
        }
    }

    // 写入评价数据
    function setEvaluationHeadData() {
        if ($_evaluationHead) {
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

            if ($_evaluationHead['work_speed_star']) {
                var work_speed_star = $(".average-speed-light"); // 平均工作速度
                $(work_speed_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead['work_speed_star']);
                });
            } else {
                $(".average-speed-light").css("display", "none");
                $(".average-speed-normal").css("display", "none");
                $(".speed-no-comment").css("display", "block");
            }

            if ($_evaluationHead['work_quality_star']) {
                var work_quality_star = $(".average-quality-light");  // 平均工作质量
                $(work_quality_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead['work_quality_star']);
                });
            } else {
                $(".average-quality-light").css("display", "none");
                $(".average-quality-normal").css("display", "none");
                $(".quality-no-comment").css("display", "block");
            }

            if ($_evaluationHead['work_attitude_star']) {
                var work_attitude_star = $(".average-attitude-light");  // 平均工作态度
                $(work_attitude_star).css("width", function () {
                    return Math.floor(146 * $_evaluationHead['work_attitude_star']);
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
        // transaction-evaluate-bottom
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
                $(transactionEvaluateCard[i]).find(".technology-name").attr('demandId', list[i]['project_id'])
                $(transactionEvaluateCard[i]).find(".technology-name").html(list[i]['project_name']); // 名称
                $(transactionEvaluateCard[i]).find(".avatar-time").html(getMyDate(list[i]['created_at'])); // 日期
                $(transactionEvaluateCard[i]).find(".evaluation-describe").html(list[i]['comments']); // 评价
                $(transactionEvaluateCard[i]).find(".good-evaluation-text").html(evaluationType); // 评价分类

                if ($_evaluationHead) {
                    if ($_evaluationHead['work_quality_star']) {
                        // 评分星星
                        $(transactionEvaluateCard[i]).find(".good-evaluation-star-light").css("width", function () {
                            return Math.floor(146 * list[i]['start_level']);
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

    /*** 复原currentPage ***/
    function resetCurrentPage() {
        $('.service-lib-detail #pageToolbar').data('currentpage', 1);
        currentPage = 1;
        clickPage = 0;
        $('.service-lib-detail #pageToolbar').find('li[data-page="' + currentPage + '"]').addClass('focus').siblings().removeClass('focus');
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

    // 设置文本超出提示
    function setTextOverTip() {
        setTextOverTipOfCompanyDetail();
    }

    // 绑定企业父框事件
    function setTextOverTipOfCompanyDetail() {
        // 获取列表父框
        var nListParent = $('.service-lib-content').eq(0);
        // 绑定事件
        nListParent.mouseover(eventOfCompanyTextOver);
    }

    // 推荐产品文本超出事件
    function eventOfCompanyTextOver(event) {
        // 获取当前作用节点
        var nNowActive = null;
        // 节点标签名
        var nodeName = event.target.tagName.toLowerCase();
        if (nodeName === 'p' && $(event.target).hasClass('text-overflow')) {
            nNowActive = $(event.target);
            layer.closeAll();
            layer.tips(nNowActive.text(), nNowActive, {
                tips: [1, '#000000']
            });
        }
    }
});

