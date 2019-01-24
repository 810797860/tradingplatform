var oBroseRecord = new BrowseRecord();
oBroseRecord.browseRecordMain();

function BrowseRecord () {
    var self = this;
    var CONTENT_WIDTH = 920;    // 内容区域的宽度
    var COMPANY_RESULT_WIDTH = 180; // 企业的案例内容区域
    var LIMIT_SIZE = 5;     // 企业的成果限制数量

    var currentPage = 1;
    var searchSizeOfResult = 12;
    var searchSizeOfDemand = 7;
    var searchSizeOfCompany = 3;
    var aTotalRecord = [];  // 存储成果、需求、服务商的总量
    var currentTab = 'result';  // 存储当前的tab
    var clickPageWay = 1;   // 点击分页的方式：是新创建还是原来的
    var isOnlyOneOrFill = false;    // 删除时当前页是否只有一条或者跟size相等

    // 存储成果页的成果项
    var sResultItem = '<a class="result-item float-l" href="javascript:;">\n' +
        '                            <div class="img-box">\n' +
        '                                <img class="result-img" src="">\n' +
        '                            </div>\n' +
        '                            <div class="result-info">\n' +
        '                                <p class="result-title text-overflow"></p>\n' +
        '                                <p class="result-browse-time"></p>\n' +
        '                            </div>\n' +
        '                            <div class="delete-btn footprint-btn"><i class="icon-delete"></i></div>\n' +
        '                        </a>';

    // 存储需求页的需求项
    var sDemandItem = '<a class="demand-item" href="javascript:;">\n' +
        '                            <div class="demand-item-row clearfix">\n' +
        '                                <p class="demand-money demand-item-col-180 float-l"></p>\n' +
        '                                <p class="demand-name demand-item-col-330 text-overflow float-l"></p>\n' +
        '                                <p class="demand-status demand-item-col-180 float-l"></p>\n' +
        '                                <p class="demand-browse-time demand-item-col-180 float-l"></p>\n' +
        '                            </div>\n' +
        '                            <div class="demand-item-row clearfix">\n' +
        '                                <p class="demand-sign-click demand-item-col-510 float-l"><span class="demand-sign-num">0</span>人报名 / <i class="icon-eye"></i><span class="demand-click-num">0</span></p>\n' +
        '                                <p class="demand-deadline demand-item-col-180 float-l">剩余有效期：<span class="demand-deadline-num">0</span>天</p>\n' +
        '                            </div>\n' +
        '                            <div class="delete-btn footprint-btn"><i class="icon-delete"></i></div>\n' +
        '                        </a>';

    // 存储服务商页的服务商项
    var sCompanyItem = '<div class="company-item clearfix" href="javascript:;">\n' +
        '                            <div class="company-item-top float-l">\n' +
        '                                <p class="company-name-box"><a class="company-name" href="javascript:;"></a></p>\n' +
        '                            </div>\n' +
        '                            <div class="company-item-left float-l">\n' +
        '                                <div class="certificate-icon">\n' +
        '                                    <i class="icon-shimingrenzheng1" title="未进行实名认证"></i>\n' +
        '                                    <i class="icon-shimingrenzheng active content-hidden" title="已完成个人认证"></i>\n' +
        '                                    <i class="icon-qiyerenzheng active content-hidden" title="已完成企业认证"></i>\n' +
        '                                    <i class="icon-tuanduirenzheng active content-hidden" title="已完成团队认证"></i>\n' +
        '                                    <i class="icon-m-phone"></i>\n' +
        '                                    <i class="icon-email"></i>\n' +
        '                                </div>\n' +
        '                                <div class="company-logo-box">\n' +
        '                                    <img class="company-logo" src="" />\n' +
        '                                </div>\n' +
        '                                <p class="company-browse-time"></p>\n' +
        '                            </div>\n' +
        '                            <div class="company-item-right float-l clearfix">\n' +
        '                                <div class="change-btn change-left-btn float-l">&lt;</div>\n' +
        '                                <div class="company-result-box float-l">\n' +
        '                                    <ul class="company-result-ul clearfix">\n' +
        '                                    </ul>\n' +
        '                                </div>\n' +
        '                                <div class="change-btn change-right-btn float-l">&gt;</div>\n' +
        '                            </div>\n' +
        '                            <div class="delete-btn footprint-btn"><i class="icon-delete"></i></div>\n' +
        '                        </div>';

    // 存储服务商页的案例项
    var sCompanyResultItem = '<li class="company-result-li float-l">\n' +
        '                        <a class="company-result-item" href="javascript:;">\n' +
        '                            <div class="company-result-img-box">\n' +
        '                                 <img class="company-result-img" src="">\n' +
        '                            </div>\n' +
        '                            <p class="company-result-name text-overflow"></p>\n' +
        '                         </a>\n' +
        '                      </li>';

    // 存储服务商页的更多按钮
    var sLookMore = '<li class="company-result-li float-l">\n' +
                        '<a class="look-more" href="javascript:;">查看更多</a>\n' +
                    '</li>';

    var oFootprintContentUl = $(".footprint-content-ul").eq(0);     // 内容UL
    var aFootprintTabItem = $(".footprint-tab-item");   // tab项
    var oFootprintContentResult = $(".footprint-content-result").eq(0); // 成果内容UL
    var oFootprintContentDemand = $(".footprint-content-demand").eq(0); // 需求内容UL
    var oFootprintContentCompany = $(".footprint-content-company").eq(0);   // 服务商内容UL
    var oFootprintTotalNum = $(".my-footprint .footprint-total-num").eq(0); // 足迹的总数
    var oFootprintNoticeBtn = $('.my-footprint .footprint-notice-btn').eq(0); // 批量删除按钮

    // 主函数入口
    self.browseRecordMain = function () {
        initPage();
        handleEvent();
    };

    // 重置页面内容
    self.resetPage = function () {
        // aFootprintTabItem.eq(0).click();
        aFootprintTabItem.eq(0).addClass('bg-orange').siblings().removeClass('bg-orange');
        oFootprintContentUl.css({
            left: 0
        })
    };

    // 初始化页面
    function initPage() {
        oFootprintContentUl.css({
            width: oFootprintContentUl.find('.footprint-content').length * CONTENT_WIDTH + 'px'
        }).find(".footprint-content").removeClass('content-hidden');

        getResultList();
    }

    // 处理事件的监听
    function handleEvent() {
        // tab栏的点击
        $(".footprint-tab-item").off("click").on("click", function () {
            oFootprintNoticeBtn.isActive = false;
            // 记录当前的tab
            currentTab = $(this).attr('name');
            clickPageWay = 0;
            // 判断当前点击的tab进行数据获取
            if (currentTab === 'result') {
                getResultList();
            } else if (currentTab === 'demand') {
                getDemandList();
            } else if (currentTab === 'company') {
                getCompanyList();
            }
            // 修改最近足迹个数
            oFootprintTotalNum.text(aTotalRecord[$(this).index()]);
            // 设置tab的active
            $(this).addClass('bg-orange').siblings().removeClass('bg-orange');
            // 动画
            var left = $(this).index() * CONTENT_WIDTH;
            oFootprintContentUl.stop().animate({
                left: -left
            });
        });

        // 删除按钮显示和隐藏
        eventOfDeleteShowAndHide();

        // 删除按钮的点击
        $(document).on('click', '.my-footprint .delete-btn', function (e) {
            e.preventDefault();
            e.stopPropagation();
            var length = 0;
            if (currentTab === 'result') {
                length = oFootprintContentResult.find('.result-item').length;
                if (length === 1 || length === searchSizeOfResult) {
                    isOnlyOneOrFill = true;
                }
            } else if (currentTab === 'demand') {
                length = oFootprintContentDemand.find('.demand-item').length;
                if (length === 1 || length === searchSizeOfDemand) {
                    isOnlyOneOrFill = true;
                }
            } else if (currentTab === 'company') {
                length = oFootprintContentCompany.find('.company-item').length;
                if (length === 1 || length === searchSizeOfCompany) {
                    isOnlyOneOrFill = true;
                }
            }
            clickPageWay = isOnlyOneOrFill ? 0 : 1;
            isOnlyOneOrFill = false;
            var id = currentTab === 'result' ? $(this).parents(".result-item").data('id') :
                currentTab === 'demand' ? $(this).parents(".demand-item").data('id') : $(this).parents(".company-item").data('id');
            deleteMyFootprint([id], function () {
                if (currentTab === 'result') {
                    getResultList();
                } else if (currentTab === 'demand') {
                    getDemandList();
                } else if (currentTab === 'company') {
                    getCompanyList();
                }
            });
        });

        // 批量管理按钮的点击
        oFootprintNoticeBtn.off("click").on("click", function () {
            oFootprintNoticeBtn.isActive = !oFootprintNoticeBtn.isActive;
            if (oFootprintNoticeBtn.isActive) {
                $('.my-footprint .delete-btn').show();
            } else {
                $('.my-footprint .delete-btn').hide();
            }
        });

        // 服务商的成果展示左箭头点击
        $(document).on('click', '.footprint-content-company .change-left-btn', function () {
            var oCompanyResultUl = $(this).parent().find(".company-result-ul").eq(0);
            var curLeft = parseFloat(oCompanyResultUl.css("left"));
            var left = -(curLeft + COMPANY_RESULT_WIDTH + 15);
            oCompanyResultUl.stop().animate({
                left: left > 0 ? -left : 0
            });
        });
        // 服务商的成果展示右箭头点击
        $(document).on('click', '.footprint-content-company .change-right-btn', function () {
            var oCompanyResultUl = $(this).parent().find(".company-result-ul").eq(0);
            var curLeft = Math.abs(parseFloat(oCompanyResultUl.css("left")));
            var width = parseFloat(oCompanyResultUl.css("width"));
            var visibleWidth = COMPANY_RESULT_WIDTH * 3 + 15 * 2;
            var left = curLeft + COMPANY_RESULT_WIDTH + 15;
            if (left > width - visibleWidth) {
                return;
            }
            oCompanyResultUl.stop().animate({
                left: -left
            });
        });

        // 监听分页跳转
        eventOfPageClick();
    }

    function getResultList () {
        oFootprintContentResult.empty();
        getMyFootprintByType('result', function (list) {
            if (!list.length) {
                $('.my-footprint .ui-paging-count').val(currentPage - 1).siblings('a').trigger('click');
                return;
            }
            var oFlag = document.createDocumentFragment();
            for (var i = 0; i < list.length; i++) {
                var oResultItem = $(sResultItem);
                oResultItem.attr('href', '/f/' + list[i].id + '/case_detail.html');
                oResultItem.attr("data-id", list[i].footprint_id);
                oResultItem.find(".result-img").attr('src', list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover) : null)
                oResultItem.find(".result-title").text(list[i].title);
                oResultItem.find(".result-browse-time").text('浏览日期：' + $(this).formatTime(new Date(list[i].browse_date)).split(' ')[0]);
                oResultItem.find(".delete-btn").attr("data-id", list[i].footprint_id);

                oFlag.append(oResultItem[0]);
            }
            oFootprintContentResult.append(oFlag);
        });
    }

    function getDemandList () {
        oFootprintContentDemand.empty();
        getMyFootprintByType('demand', function (list) {
            if (!list.length) {
                $('.my-footprint .ui-paging-count').val(currentPage - 1).siblings('a').trigger('click');
                return;
            }
            var oFlag = document.createDocumentFragment();
            for (var i = 0; i < list.length; i++) {
                var oDemandItem = $(sDemandItem);
                oDemandItem.attr("data-id", list[i].footprint_id).attr('href', '/f/' + list[i].id + '/demand_detail.html?pc=true');
                if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) && (list[i].budget_amount >= 0 && list[i].budget_amount <= 0)) {
                    oDemandItem.find(".demand-money").html("￥ 面议");     // 金额
                } else if (parseInt(list[i].budget_amount_start) === parseInt(list[i].budget_amount) || !list[i].budget_amount_start) {
                    oDemandItem.find(".demand-money").html('￥' + list[i].budget_amount + "万");     // 金额
                } else {
                    oDemandItem.find(".demand-money").html('￥' + list[i].budget_amount_start + "万" + '-' + list[i].budget_amount + '万');     // 金额
                }
                oDemandItem.find(".demand-name").text(list[i].name);
                if (list[i].status) {
                    var status = JSON.parse(list[i].status);
                    if (status.id == 202069) {
                        oDemandItem.find(".demand-status").text(status.title).css('color', '#FF6000');
                    } else if (status.id == 202074) {
                        $('.demand-card .demand-card-row .status-area').eq(i).css('color', '#1BBBB4');
                    }
                    oDemandItem.find(".demand-status").text('[ ' + status.title + ' ]');
                } else {
                    oDemandItem.find(".demand-status").text('暂无数据');
                }
                oDemandItem.find(".demand-browse-time").text('浏览日期：' + $(this).formatTime(new Date(list[i].browse_date)).split(' ')[0]);
                oDemandItem.find(".demand-sign-num").text(list[i].total_docking ? list[i].total_docking : '暂无');
                oDemandItem.find(".demand-click-num").text(list[i].click_rate);
                oDemandItem.find(".demand-deadline-num").text(list[i].validdate);

                oFlag.append(oDemandItem[0]);
            }
            oFootprintContentDemand.append(oFlag);
        });
    }

    function getCompanyList () {
        oFootprintContentCompany.empty();
        getMyFootprintByType('company', function (list) {
            if (!list.length) {
                $('.my-footprint .ui-paging-count').val(currentPage - 1).siblings('a').trigger('click');
                return;
            }
            var oFlag = document.createDocumentFragment();
            var oFlagOfResult = document.createDocumentFragment();
            for (var i = 0; i < list.length; i++) {
                var oCompanyItem = $(sCompanyItem);
                var oCompanyResultUl = oCompanyItem.find('.company-result-ul').eq(0);
                var aResultList = list[i].mature_case ? JSON.parse(list[i].mature_case.replace(/\\/g, '')) : [];
                var result_len = aResultList.length;
                oCompanyItem.attr("data-id", list[i].footprint_id);
                oCompanyItem.find('.company-name').text(list[i].name).attr('href', '/f/' + list[i].id + '/provider_detail.html');
                oCompanyItem.find('.company-logo').text(list[i].logo ? $(this).getAvatar(list[i].logo) : null);
                oCompanyItem.find('.company-browse-time').text('浏览日期：' + $(this).formatTime(new Date(list[i].browse_date)).split(' ')[0]);

                if (list[i].category) {
                    oCompanyItem.find('.icon-shimingrenzheng1').hide();
                    var category = JSON.parse(list[i].category);
                    if (category.id == 202135) {    // 个人认证
                        oCompanyItem.find('.icon-shimingrenzheng').removeClass('content-hidden');
                    } else if (category.id == 202136) { // 团队认证
                        oCompanyItem.find('.icon-tuanduirenzheng').removeClass('content-hidden');
                    } else if (category.id == 202137) { // 企业认证
                        oCompanyItem.find('.icon-qiyerenzheng').removeClass('content-hidden');
                    }
                } else {
                    oCompanyItem.find('.icon-shimingrenzheng1').show();
                    oCompanyItem.find('.icon-shimingrenzheng').addClass('content-hidden');
                    oCompanyItem.find('.icon-tuanduirenzheng').addClass('content-hidden');
                    oCompanyItem.find('.icon-qiyerenzheng').addClass('content-hidden');
                }
                if (list[i].phone) {
                    oCompanyItem.find('.icon-m-phone').attr('title', '已绑定手机号').addClass('active');
                } else {
                    oCompanyItem.find('.icon-m-phone').attr('title', '未绑定手机号').removeClass('active');
                }
                if (list[i].email) {
                    oCompanyItem.find('.icon-email').attr('title', '已绑定邮箱').addClass('active');
                } else {
                    oCompanyItem.find('.icon-email').attr('title', '未绑定邮箱').removeClass('active');
                }

                // 设置oCompanyResultUl的宽度
                oCompanyResultUl.css({
                    width: result_len > LIMIT_SIZE ?
                        COMPANY_RESULT_WIDTH * (LIMIT_SIZE + 1) + (LIMIT_SIZE) * 15 + 'px' :
                        COMPANY_RESULT_WIDTH * result_len + (result_len - 1) * 15 + 'px'
                });
                for (var j = 0; j < result_len && j < LIMIT_SIZE; j++) {
                    var oCompanyResultItem = $(sCompanyResultItem);
                    oCompanyResultItem.find(".company-result-item").attr('href', '/f/' + aResultList[j].id + '/case_detail.html');
                    oCompanyResultItem.find('.company-result-img').attr('src', aResultList[j].picture_cover ? $(this).getAvatar(aResultList[j].picture_cover) : null);
                    oCompanyResultItem.find('.company-result-name').text(aResultList[j].title);
                    oFlagOfResult.append(oCompanyResultItem[0]);
                }
                if (result_len > LIMIT_SIZE) {
                    var oLookMore = $(sLookMore);
                    oLookMore.find('.look-more').attr('href', '/f/' + list[i].id + '/provider_detail.html');
                    oFlagOfResult.append(oLookMore[0]);
                }
                oCompanyResultUl.append(oFlagOfResult);
                oFlag.append(oCompanyItem[0]);
            }
            oFootprintContentCompany.append(oFlag);
        });
    }

    // 根据type值请求数据（成果、需求、服务商）
    function getMyFootprintByType (type, callback) {
        //202236-成果 202237-需求
        var json = {
            "footprintType": type === 'result' ? 202236 : type === 'demand' ? 202237 : 202238,
            "pager": {
                "current": currentPage,
                "size": type === 'result' ? searchSizeOfResult : type === 'demand' ? searchSizeOfDemand : searchSizeOfCompany
            }
        };
        new NewAjax({
            type: "POST",
            url: "/f/myFootprint/pc/query?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if(res.status === 200) {
                    var list = res.data.data_list;
                    var total = res.data.total;
                    if (type === 'result') {
                        aTotalRecord[0] = total;
                    } else if (type === 'demand') {
                        aTotalRecord[1] = total;
                    } else if (type === 'company') {
                        aTotalRecord[2] = total;
                    }
                    if (currentTab === type) {
                        oFootprintTotalNum.text(total);
                    }

                    $('.browse-record-splitpage >div').Paging({
                        pagesize: type === 'result' ? searchSizeOfResult : type === 'demand' ? searchSizeOfDemand : searchSizeOfCompany,
                        count: total,
                        toolbar: true
                    });
                    if (clickPageWay === 0){
                         clickPageWay = 1;
                        $('.browse-record-splitpage >div').find("div:eq(0)").remove();
                    } else {
                        $('.browse-record-splitpage >div').find("div:eq(1)").remove();
                    }
                    callback && callback(list);
                } else {
                    layer.open({
                        title: '温馨提示',
                        content: '内部信息出错'
                    })
                }
            }
        });
    }

    // 删除记录
    function deleteMyFootprint (arr, callback) {
        new NewAjax({
            type: "POST",
            url: "/f/myFootprint/pc/batch_delete?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(arr),
            success: function (res) {
                if(res.status === 200) {
                    // layer.alert('删除成功！')
                    callback && callback();
                } else {
                    layer.open({
                        title: '温馨提示',
                        content: '内部信息出错'
                    })
                }
            }
        });
    }

    // 删除按钮显示和隐藏
    function eventOfDeleteShowAndHide () {
        // 删除按钮显示和隐藏
        $(document).on("mouseover", '.my-footprint .result-item', eventOfShowDelMouseOver)
            .on("mouseleave", '.my-footprint .result-item', eventOfHideDelMouseLeave)
            .on("mouseover", '.my-footprint .demand-item', eventOfShowDelMouseOver)
            .on("mouseleave", '.my-footprint .demand-item', eventOfHideDelMouseLeave)
            .on("mouseover", '.my-footprint .company-item', eventOfShowDelMouseOver)
            .on("mouseleave", '.my-footprint .company-item', eventOfHideDelMouseLeave);
    }

    // 处理鼠标移入显示删除按钮
    function eventOfShowDelMouseOver() {
        $(this).find('.delete-btn').show();
    }
    // 处理鼠标移出隐藏删除按钮
    function eventOfHideDelMouseLeave() {
        $(this).find('.delete-btn').hide();
    }

    /*** 监听分页跳转 ***/
    function eventOfPageClick() {
        $(".browse-record-splitpage >div").on("click", function(){
            clickPageWay = 1;
            var focusPage = $('.browse-record-splitpage .focus')[0].innerText;
            if(focusPage != currentPage) {
                currentPage = focusPage;
                if (currentTab === 'result') {
                    getResultList();
                } else if (currentTab === 'demand') {
                    getDemandList();
                } else if (currentTab === 'company') {
                    getCompanyList();
                }
            }
        }).keydown(function(e) {
            if (e.keyCode == 13) {
                clickPageWay = 1;
                currentPage = $('.browse-record-splitpage .focus')[0].innerText;
                if (currentTab === 'result') {
                    getResultList();
                } else if (currentTab === 'demand') {
                    getDemandList();
                } else if (currentTab === 'company') {
                    getCompanyList();
                }
            }
        });
    }
}