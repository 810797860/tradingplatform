$(function () {
    var service = new serviceDetail();
    service.initPage();
    service.eventHandle();

    function serviceDetail() {
        var _this = this;
        // 获取tab
        var $_tab = $('.tab');
        var $_time = $('.publish-time .type-item-text');
        var $_img = $('.show-img');
        // 获取左侧卡片
        var $_serviceLeftCard = $('.service-text-card');

        _this.initPage = function () {
            var time = $_time.attr('data-time').split(' ')[0];
            $_time.text(time);
            if (!!generalServiceData.icon) {
                $_img.attr('src', '/adjuncts/file_download/' + JSON.parse(generalServiceData.icon).id);
            }
            _this.getRightDataForServiceCard();
        };

        _this.eventHandle = function () {
            _this.tabChangeClick();
            _this.isCollectionService();
            _this.onlineConsultation();
            _this.consultationSubmit();
            // 设置超出提示
            setTextOverTip();
        };

        _this.tabChangeClick = function () {
            $_tab.find('.tab-item').click(function () {
                $(this).siblings().removeClass('active');
                $(this).addClass('active');
                $('.tab-desc-area>div').eq(0).nextAll().hide();
                $('.tab-desc-area>div').eq($(this).index() + 1).show();
            })
        };

        // 点击收藏按钮
        _this.isCollectionService = function () {
            $(".collect-area").off().on("click", function () {
                if ($(this).attr("collection") == 'false') {
                    _this.toCollectTheService($(this), generalServiceData.id, true);
                } else {
                    _this.toCollectTheService($(this), generalServiceData.id, false);
                }
            });
        };

        // 收藏
        _this.toCollectTheService = function (dom, id, isCollect) {
            var json = {
                "serviceId": id,
                "isCollection": isCollect
            };
            console.log(isCollect);
            new NewAjax({
                type: "POST",
                url: "/f/serviceMessage/pc/collection_service_message?pc=true",
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
                            $('.collect-area>span').eq(0).text('收藏');
                            $('.collect-area').attr('collection', 'false');
                            // $(dom).children("i").attr("class", "icon-star-void")
                        } else {
                            layer.msg("收藏成功！");
                            $('.collect-area>span').eq(0).text('已收藏');
                            $('.collect-area').attr('collection', 'true');
                        }
                        $(dom).children(".collect-num").html(res.data.total)
                    }
                }
            })
        }

        // 获取右侧栏卡片数据
        _this.getRightDataForServiceCard = function () {
            var json = {
                "types": [
                    "c_business_service_message"
                ],
                "page": 1,
                "size": 6,
                "sortObjects": [
                    {
                        "field": "_score",//必传
                        "direction": "DESC"
                    },
                    {
                        "field": "recommended",
                        "direction": "DESC"
                    },
                    {
                        "field": "recommended_index",
                        "direction": "DESC"
                    }
                ]
            }
            var url = '/searchEngine/customSearch?pc=true'
            new NewAjax({
                url: url,
                type: 'POST',
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(json),
                success: function (res) {
                    console.log(res);
                    var list = res.data.data_object.data;
                    _this.setRightDataForServiceCard(list);
                },
                error: function () {
                }
            })
        }

        _this.setRightDataForServiceCard = function (list) {
            var serviceLength = $_serviceLeftCard.length;
            for (var j = 0; j < list.length; j++) {
                $_serviceLeftCard.eq(j).show();
                $_serviceLeftCard.eq(j).attr('href', '/f/' + list[j].data_id + '/general_service_detail.html');
                $_serviceLeftCard.eq(j).find('.title').text(list[j].title);
                if (!!list[j].provider_id) {
                    $_serviceLeftCard.eq(j).find('.provider-data').text(JSON.parse(list[j].provider_id).name);
                }
                $_serviceLeftCard.eq(j).find('.time').text($(this).formatTime(new Date(list[j].created_at)).split(' ')[0]);
            }
            if (serviceLength > list.length) {
                for (var i = list.length; i < serviceLength; i++) {
                    $_serviceLeftCard.eq(i).hide();
                }
            }
        };

        // 在线咨询
        _this.onlineConsultation = function () {
            $('.online-consultation').click(function () {
                var user = window.localStorage.getItem('user');
                if (!!user) {
                    layer.open({
                        type: 1,
                        title: "在线咨询",
                        skin: 'layui-layer-lan', //加上边框
                        area: ['500px', '330px'], //宽高
                        content: '<div class="askBtnContents"><textarea style="width: 90%;height: 190px;padding: 10px;box-sizing: border-box;margin-left: 5%;margin-top: 20px"></textarea><button class="askBtnSubmit" style="margin-left: 200px;width: 100px;height: 30px;line-height: 30px;font-size: 14px;color: white;background-color: #0db5fb;border: none;border-radius: 10px;outline: none;margin-top: 20px;cursor: pointer">提交</button></div>'
                    });
                } else {
                    layer.msg('登录后才能咨询');
                    setTimeout(function () {
                        window.open('/f/login.html?pc=true', '_self');
                    }, 1000);
                }
            })
        };

        // 在线咨询提交按钮点击事件
        _this.consultationSubmit = function () {
            $(document).on('click', '.askBtnSubmit', function () {
                var json = {
                    serviceId: generalServiceData.id,
                    contents: filterSensitiveWord($('.askBtnContents textarea').val())
                };
                if (json.contents == '') {
                    layer.msg("咨询内容不能为空")
                } else {
                    $('.askBtnSubmit').attr('disabled', true);
                    new NewAjax({
                        url: "/f/serviceMessageConsulting/pc/create_update?pc=true",
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
        }

        // 设置文本超出提示
        function setTextOverTip() {
            setTextOverTipOfRecommandServerList();
        }

        // 绑定推荐企业父框事件
        function setTextOverTipOfRecommandServerList() {
            // 获取列表父框
            var nListParent = $('.content-right .recommand-service').eq(0);
            // 绑定事件
            nListParent.mouseover(eventOfRecommandServerTextOver);
        }

        // 推荐企业文本超出事件
        function eventOfRecommandServerTextOver(event) {
            // 获取当前作用节点
            var nNowActive = null;
            // 节点标签名
            var nodeName = event.target.tagName.toLowerCase();
            if (nodeName === 'p' && $(event.target).hasClass('title')) {
                nNowActive = $(event.target);
            } else if (nodeName === 'span' && $(event.target).hasClass('provider-data')) {
                nNowActive = $(event.target);
            }
            if (nNowActive) {
                layer.closeAll();
                layer.tips(nNowActive.text(), nNowActive, {
                    tips: [1, '#000000']
                });
            }
        }
    }
});