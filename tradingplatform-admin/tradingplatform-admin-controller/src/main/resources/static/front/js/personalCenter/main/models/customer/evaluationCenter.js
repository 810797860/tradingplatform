/**
 * Created by 空 on 2019/1/17.
 */
$(function () {
    var tabItem = $('.evaluation-center-tab-item');
    var sendEvaluationCurrent = 1;
    var sendEvaluationSize = 5;
    var obtainEvaluationCurrent = 1;
    var obtainEvaluationSize = 5;
    var pageInOrTabSend = 0;
    var pageInOrTabObtain = 0;
    var sendContent = $('.send-evaluation')[0];
    var isFirst = true;

    // var itemTitle = $('.send-evaluation-item-title');
    pageInit();
    pageClick();

    // 页面的初始化
    function pageInit() {
        // getSendEvaluation();
        console.log('a');
        getSendEvaluation();
        // getObtainEvaluation();
    }

    // 页面的点击事件
    function pageClick() {
        tabChanceClick();
        titleClickToDetail();
        listenToPage();
        listenToPageOfObtain();
    }

    // tab页点击切换事件
    function tabChanceClick() {
        tabItem.off().click(function (e) {
            // console.log($(this).classList);
            $(this).siblings().removeClass('evaluation-center-tab-item-active');
            $(this).addClass('evaluation-center-tab-item-active');
            if ( $(this).text() === '发出的评价') {
                $('.received-evaluation').hide();
                $('.send-evaluation').show();
                $('.send-evaluation-splitpage').show();
                $('.obtain-evaluation-splitpage').hide();
                resetCurrentPageOfObtain();
                pageInOrTabSend = 1;
                getSendEvaluation();
            } else {
                $('.received-evaluation').show();
                $('.send-evaluation').hide();
                $('.send-evaluation-splitpage').hide();
                $('.obtain-evaluation-splitpage').show();
                resetCurrentPagePublishDemand();
                getObtainEvaluation();
                // sendEvaluationCurrent = 0;
            }
        })
    }

    // 标题跳转
    function titleClickToDetail() {
        $(document).off().on('click','.send-evaluation-item-title',function () {
            var id = $(this).data('id');
            window.open('/f/'+ id +'/demand_detail.html?pc=true');
            $(this).removeData('id');
        })
    }

    /*** 复原currentPage ***/
    function resetCurrentPagePublishDemand() {
        $('.send-evaluation-splitpage >div').data('currentpage', 1);
        sendEvaluationCurrent = 1;
        $('.send-evaluation-splitpage >div').find('li[data-page="' + sendEvaluationCurrent + '"]').addClass('focus').siblings().removeClass('focus');
        // 上一页下一页重置
        $('.js-page-prev').addClass("ui-pager-disabled");
        $('.js-page-next').removeClass("ui-pager-disabled");
    }

    /*** 复原currentPage ***/
    function resetCurrentPageOfObtain() {
        $('.obtain-evaluation-splitpage >div').data('currentpage', 1);
        obtainEvaluationCurrent = 1;
        $('.obtain-evaluation-splitpage >div').find('li[data-page="' + obtainEvaluationCurrent + '"]').addClass('focus').siblings().removeClass('focus');
        // 上一页下一页重置
        $('.js-page-prev').addClass("ui-pager-disabled");
        $('.js-page-next').removeClass("ui-pager-disabled");
        if (!isFirst) {
            pageInOrTabObtain = 1;
        }
        isFirst = false;
    }

    // 监听分页跳转
    function listenToPage() {
        $(".send-evaluation-splitpage >div").on("click", function () {
            if ($('.send-evaluation-splitpage .focus')[0].innerText != sendEvaluationCurrent) {
                sendEvaluationCurrent = $('.send-evaluation-splitpage .focus')[0].innerText;
                console.log(sendEvaluationCurrent);

                // getServiceList($_typeId, $("#search-input").val());
                pageInOrTabSend = 0;
                getSendEvaluation();
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                sendEvaluationCurrent = $('.send-evaluation-splitpage .focus')[0].innerText;

                // getServiceList($_typeId, $("#search-input").val());
                pageInOrTabSend = 0;
                getSendEvaluation();
            }
        });
    }

    // 监听分页跳转
    function listenToPageOfObtain() {
        $(".obtain-evaluation-splitpage >div").on("click", function () {
            if ($('.obtain-evaluation-splitpage .focus')[0].innerText != obtainEvaluationCurrent) {
                obtainEvaluationCurrent = $('.obtain-evaluation-splitpage .focus')[0].innerText;

                // getServiceList($_typeId, $("#search-input").val());
                pageInOrTabObtain = 0;
                getObtainEvaluation();
            }
        }).keydown(function (e) {
            if (e.keyCode == 13) {
                obtainEvaluationCurrent = $('.obtain-evaluation-splitpage .focus')[0].innerText;

                // getServiceList($_typeId, $("#search-input").val());
                pageInOrTabObtain = 0;
                getObtainEvaluation();
            }
        });
    }

    // 获取我发的评价
    function getSendEvaluation() {
        $('.send-evaluation').find('.send-evaluation-item').remove();
        var json = {
            pager: {
                current: sendEvaluationCurrent,
                size: sendEvaluationSize
            }
        }
        new NewAjax({
            url: '/f/serviceProvidersEvaluation/pc/get_send_evaluation?pc=true',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                var totalRecord = res.data.total;
                createListOfSend(res.data.data_list);
                if (pageInOrTabSend === 0) {
                    $('.send-evaluation-splitpage >div').Paging({
                        pagesize: sendEvaluationSize,
                        count: totalRecord,
                        toolbar: true
                    });
                    $('.send-evaluation-splitpage >div').find("div:eq(1)").remove();
                }
                if (pageInOrTabSend === 1) {
                    $('.send-evaluation-splitpage >div').Paging({
                        pagesize: sendEvaluationSize,
                        count: totalRecord,
                        toolbar: true
                    });
                    $('.send-evaluation-splitpage >div').find("div:eq(0)").remove();
                }
            }
        })
    }

    // 收到的评价
    function getObtainEvaluation() {
        $('.received-evaluation').find('.received-evaluation-item').remove();
        var json = {
            pager: {
                current: obtainEvaluationCurrent,
                size: obtainEvaluationSize
            }
        }
        new NewAjax({
            url: '/f/serviceProvidersEvaluation/pc/get_recieve_evaluation?pc=true',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify(json),
            success: function (res) {
                console.log(res);
                var totalRecord = res.data.total;
                createListOfObtain(res.data.data_list);
                if (pageInOrTabObtain === 0) {
                    $('.obtain-evaluation-splitpage >div').Paging({
                        pagesize: obtainEvaluationSize,
                        count: totalRecord,
                        toolbar: true
                    });
                    $('.obtain-evaluation-splitpage >div').find("div:eq(1)").remove();
                }
                if (pageInOrTabObtain === 1) {
                    $('.obtain-evaluation-splitpage >div').Paging({
                        pagesize: obtainEvaluationSize,
                        count: totalRecord,
                        toolbar: true
                    });
                    $('.obtain-evaluation-splitpage >div').find("div:eq(0)").remove();
                }
            }
        })
    }

    function createListOfSend(list) {
        console.log(list);
        // var item =
        var fragment = document.createDocumentFragment();
        for (var i = 0; i < list.length; i++) {
            var item = '<div class="send-evaluation-item">' +
                '<div class="send-evaluation-item-left">' +
                '<span class="send-evaluation-item-title" title="'+ list[i].project_name +'">'+ list[i].project_name +'</span>' +
                '</div>' +
                '<div class="send-evaluation-item-right">' +
                '<div class="send-evaluation-item-right-head">' +
                '<span class="send-evaluation-item-right-head-item-title">工作速度</span>' +
                '<div class="send-evaluation-item-right-head-item">' +
                '<ul class="evaluation-stars-list">' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '</ul>' +
                '<ul class="evaluation-stars-list-normal">' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '</ul>' +
                '</div>' +
                '<span class="send-evaluation-item-right-head-item-title">工作质量</span>' +
                '<div class="send-evaluation-item-right-head-item">' +
                '<ul class="evaluation-stars-list">' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '</ul>' +
                '<ul class="evaluation-stars-list-normal">' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '</ul>'+
                '</div>' +
                '<span class="send-evaluation-item-right-head-item-title">工作态度</span>' +
                '<div class="send-evaluation-item-right-head-item">'+
                '<ul class="evaluation-stars-list">'+
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect active-star"></i></li>'+
                '</ul>'+
                '<ul class="evaluation-stars-list-normal">'+
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>'+
                '<li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>'+
                '</ul>'+
                '</div>'+
                '</div>'+
                '<div class="send-evaluation-item-right-footer">'+
                '<span class="send-evaluation-item-right-footer-time">'+ $(this).formatTime(new Date(list[i].created_at), false, 'YYYY-MM-DD') +'</span>'+
                '<p class="send-evaluation-item-right-footer-content">'+ list[i].comments +'</p>' +
                '</div>'+
                '</div>'+
                '</div>';

            $(fragment).append(item);
        }
        // console.log(fragment);
        $('.send-evaluation').append(fragment);
        // setTimeout(function () {
        for (var j = 0; j < list.length; j++) {
            $('.send-evaluation-item').eq(j).find('.evaluation-stars-list').eq(0).css('width', Math.round(list[j].work_speed_star * 105) + 'px');
            $('.send-evaluation-item').eq(j).find('.evaluation-stars-list').eq(1).css('width', Math.round(list[j].work_quality_star * 105) + 'px');
            $('.send-evaluation-item').eq(j).find('.evaluation-stars-list').eq(2).css('width', Math.round(list[j].work_attitude_star * 105) + 'px');
        }
        // }, 0)
    }

    function createListOfObtain (list) {
        // var item =
        var fragment = document.createDocumentFragment();
        for (var i = 0; i < list.length; i++) {
            var item = '<div class="received-evaluation-item">' +
                '<div class="received-evaluation-item-left">' +
                '<span class="received-evaluation-item-title" title="'+ list[i].project_name +'">'+ list[i].project_name +'</span>' +
                '</div>' +
                '<div class="received-evaluation-item-center">' +
                '<img src="\static\front\assets\image\empty3.jpg" alt="用户头像" class="received-evaluation-item-center-image">' +
                '<span class="received-evaluation-item-center-evaluater">评论人：'+ JSON.parse(list[i].user_id).user_name +'</span>' +
                '</div>' +
                '<div class="received-evaluation-item-right">' +
                '            <div class="received-evaluation-item-right-head">' +
                '            <span class="received-evaluation-item-right-head-item-title">工作速度</span>' +
                '            <div class="received-evaluation-item-right-head-item">' +
                '            <ul class="evaluation-stars-list">' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            </ul>' +
                '            <ul class="evaluation-stars-list-normal">' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            </ul>' +
                '            </div>' + '            <span class="received-evaluation-item-right-head-item-title">工作质量</span>' +
                '            <div class="received-evaluation-item-right-head-item">' +
                '            <ul class="evaluation-stars-list">' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            </ul>' +
                '            <ul class="evaluation-stars-list-normal">' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            </ul>' +
                '            </div>' +
                '            <span class="received-evaluation-item-right-head-item-title">工作态度</span>' +
                '            <div class="received-evaluation-item-right-head-item">' +
                '            <ul class="evaluation-stars-list">' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect active-star"></i></li>' +
                '            </ul>' +
                '            <ul class="evaluation-stars-list-normal">' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            <li style="cursor: pointer"><i class="icon-collect normal-star"></i></li>' +
                '            </ul>' +
                '            </div>' +
                '            </div>' +
                '            <div class="received-evaluation-item-right-footer">' +
                '            <span class="received-evaluation-item-right-footer-time">'+ $(this).formatTime(new Date(list[i].created_at), false, 'YYYY-MM-DD') +'</span>' +
                '            <p class="received-evaluation-item-right-footer-content">'+  list[i].comments +'</p>' +
                '        </div>' +
                '        </div>'

            $(fragment).append(item);
        }

        $('.received-evaluation').append(fragment);
        // setTimeout(function () {
        for (var j = 0; j < list.length; j++) {
            $('.received-evaluation-item').eq(j).find('.evaluation-stars-list').eq(0).css('width', Math.round(list[j].work_speed_star * 105) + 'px');
            $('.received-evaluation-item').eq(j).find('.evaluation-stars-list').eq(1).css('width', Math.round(list[j].work_quality_star * 105) + 'px');
            $('.received-evaluation-item').eq(j).find('.evaluation-stars-list').eq(2).css('width', Math.round(list[j].work_attitude_star * 105) + 'px');
        }
    }
})

