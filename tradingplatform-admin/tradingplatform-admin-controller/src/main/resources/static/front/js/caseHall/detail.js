$(function () {
    // 存储数据
    var detailDataSource = window.case_detailData;
    var evaluationDataSource = window.case_evaluationData;

    var currentPage = 1;
    var searchSizeOfRecommend = 3;
    var searchSizeOfCompany = 4;

    // 相关产品内容区域
    var oRelateCaseArea = $(".relate-case-area");
    // 企业热卖内容区域
    var oCompanyHotCaseArea = $(".company-hot-case-area");
    // 相关产品、企业热卖item
    var sCaseItemHtml = '<a href="javascript:;" class="case-item">' +
                            '<div class="relate-case-img-area">' +
                                '<img class="relate-case-img" src="" alt="">' +
                            '</div>' +
                            '<p class="relate-case-title text-overflow"></p>' +
                        '</a>';
    // 产品参数item
    var sCaseInfoItemHtml = '<div class="case-info-item float-l clearfix">' +
                                '<span class="case-info-item-label float-l"></span>' +
                                '<span class="case-info-item-data float-l"></span>' +
                            '</div>';

    var aInfusedDataNode = $(".infused-data");  // 注入json数据节点
    var oCaseActiveBtn = $(".share-collect-btn");  // 收藏或分享按钮
    var oOnlineConsultBtn = $(".consult-btn");  // 在线咨询按钮
    var oNumChangeBtn = $(".num-change-btn"); // 加减数量按钮
    var oInputNum = $(".input-num");    // 数值输入框
    var oCasePrice = $(".case-price");  // 价钱


    /!*=== 屏外视频 ===*!/
    // 获取屏外视频节点
    var outWindowVideoNode = $('#outWindowVideo');
    // 存储视频数据
    var videoArr = null;
    // 存储截图数据
    var videoImageArr = [];
    // 存储flv对象
    var outPlayer = null;

    /!*=== 展示轮播图 ===*!/
    // 存储预览图项html
    var previewItemHtml = '<li class="result-picture-preview-item float-l">\n' +
        '                       <img class="img-select-item" src="" style="display: none">\n' +
        '                       <img class="img-select-loading" src="/static/front/assets/image/loading.gif">\n' +
        '                       <div class="result-picture-preview-video-mask icon-video"></div>\n' +
        '                   </li>';
    // 存储图片数据
    var resultPicData = null;
    // 获取展示图片节点
    var showImageNode = $('.result-picture-show').eq(0);
    // 获取预览图列表节点
    var previewListNode = $('.result-picture-preview-content-div ul.result-picture-preview-list').eq(0);
    // 获取预览图的左右箭头
    var previewArrowNode = $('.result-picture-preview-arrow');
    // 获取视频播放icon节点
    var showVideoIcon = showImageNode.parent().find('div.icon-video').eq(0);
    // 获取图片放大icon节点
    var showZoomIcon = showImageNode.parent().find('div.icon-search').eq(0);
    // 存储暂无图片链接
    var noPictureUrl = '/static/front/assets/image/noImage_people.jpg';
    // 存储资源无或损坏的链接
    var loseAssetUrl = '/static/front/assets/image/lose_people.jpg';

    /* 分享 */
    // 获取分享区域
    var oShareAreaNode = $('.share-collect-area').eq(0);
    // 获取分享按钮
    var oShareBtnNode = oShareAreaNode.find('.share-btn').eq(0);
    // 获取分享模块
    var oShareModelNode = oShareAreaNode.find('.share-model').eq(0);
    // 获取分享列表
    var oShareListNode = oShareModelNode.find('.share-method-list').eq(0);
    // 分享数据对象
    var oShareData = null;
    // url前缀获取正则
    var urlPrefixRule = /^(http(s)?|ftp)\:\/\/([a-zA-Z0-9\.\-]+)(:[0-9]+)?/;



    initResultPicture();
    initDataOfInfusedData();
    getRecommendCaseList(setRecommendCaseList);
    getCompanyHotCaseList(setCompanyHotCaseList);
    handleEvent();
    getVideoData();

    // 初始化注入的数据
    function initDataOfInfusedData () {
        var aInfuseDataName = ["type", "industry", "company", "favorableRate", "serviceAttitude", "workQuality", "caseInfo"];
        aInfusedDataNode.each(function (index, item) {
            var nowNode = $(item);
            if ($.inArray(nowNode.attr('name'), aInfuseDataName) < 0) {
                // 直接跳过单前循环
                return true;
            }
            var name = nowNode.attr("name");
            switch (name) {
                case 'type':
                    nowNode.text(detailDataSource.application_industry ? JSON.parse(detailDataSource.application_industry).title : '暂无数据');
                    break;
                case 'industry':
                    nowNode.text(detailDataSource.sub_application_industry ? JSON.parse(detailDataSource.sub_application_industry).title : '暂无数据');
                    break;
                case 'company':
                    // 如果不存在服务商
                    if (!detailDataSource.provider_id) {
                        $(".case-detail-bottom-left").css("display", "none");
                        $(".case-detail-bottom-right").css("width", "100%");
                        break;
                    }
                    var oProvider = JSON.parse(detailDataSource.provider_id);
                    nowNode.text(oProvider.name ? oProvider.name : '').attr("title", oProvider.name ? oProvider.name : '')
                        .siblings("img").attr("src", oProvider.logo ? $(this).getAvatar(oProvider.logo) : null).attr("alt", oProvider.name ? oProvider.name : '')
                        .parent().attr("href", oProvider.id ? "/f/" + oProvider.id +"/provider_detail.html" : 'javascript:;')
                        .parent().find(".to-service-provider").attr("href", oProvider.id ? "/f/" + oProvider.id +"/provider_detail.html" : 'javascript:;');
                    break;
                case 'favorableRate':
                    nowNode.text(evaluationDataSource && evaluationDataSource.favorable_rate ? evaluationDataSource.favorable_rate + '%' : '暂无评价');
                    break;
                case 'serviceAttitude':
                    if (evaluationDataSource && evaluationDataSource.work_attitude_star) {
                        nowNode.css({
                            width: evaluationDataSource.work_attitude_star * 100 + '%'
                        });
                    } else {
                        nowNode.parent().text("暂无评价").css({
                            color: '#ff4e00',
                            fontSize: '14px'
                        });
                    }
                    break;
                case 'workQuality':
                    if (evaluationDataSource && evaluationDataSource.work_quality_star) {
                        nowNode.css({
                            width: evaluationDataSource.work_quality_star * 100 + '%'
                        });
                    } else {
                        nowNode.parent().text("暂无评价").css({
                            color: '#ff4e00',
                            fontSize: '14px'
                        });
                    }
                    break;
                case 'caseInfo':
                    if (!detailDataSource.product_parameters) {
                        nowNode.parent().css("display", "none");
                    } else {
                        var list = JSON.parse(detailDataSource.product_parameters);
                        var oFrag = document.createDocumentFragment();
                        for (var i = 0, len = list.length; i < len; i++) {
                            var oCaseInfoItem = $(sCaseInfoItemHtml);
                            oCaseInfoItem.find(".case-info-item-label").text(list[i].key + "：");
                            oCaseInfoItem.find(".case-info-item-data").text(list[i].value);
                            oFrag.append(oCaseInfoItem[0]);
                        }
                        nowNode.append(oFrag);
                    }
                    break;
                default:
                    break
            }
        });
    }
    // 处理事件
    function handleEvent () {
        eventOfChangeNum();
        eventOfActivityBtnClick();
        eventOfOnlineConsult();
        eventOfAskSubmitBtnClick();
        eventOfVideoLoadData();
        eventOfTabClick();
        eventOfOperateBtnClick();
        /* 分享 */
        eventOfMouseEnterShareBtn();
        eventOfMouseLeaveShareBtn();
        eventOfShareListClick();
    }
    // 改变数量
    function eventOfChangeNum () {
        var reg = /^[0-9]+.?[0-9]*$/;
        var oReduceBtn = $(".reduce-btn");
        oNumChangeBtn.off("click").on("click", function (event) {
            var iInputNum = parseFloat(oInputNum.val());
            var targetNode = $(event.target);
            if (targetNode.hasClass("reduce-btn")) {
                if (iInputNum <= 1) {
                    targetNode.css("cursor", "no-drop");
                    return;
                }
                targetNode.css("cursor", "pointer");
                oInputNum.val(iInputNum - 1);
                if (detailDataSource.case_money) {
                    oCasePrice.text("￥" + (detailDataSource.case_money * (iInputNum - 1)).toFixed(2) + '万元')
                }
                if (iInputNum - 1 === 1) {
                    targetNode.css("cursor", "no-drop");
                }
            } else if (targetNode.hasClass("add-btn")) {
                oReduceBtn.css("cursor", "pointer");
                oInputNum.val(iInputNum + 1);
                if (detailDataSource.case_money) {
                    oCasePrice.text("￥" + (detailDataSource.case_money * (iInputNum + 1)).toFixed(2) + '万元')
                }
            }
        });
        oInputNum.off().on("input propertychange", function () {
            var iInputNum = parseFloat(oInputNum.val());
            if ((!reg.test(oInputNum.val()) && oInputNum.val() !== "") || iInputNum < 1) {
                $(this).val(1);
                return;
            }
            if (iInputNum === 1) {
                oReduceBtn.css("cursor", "no-drop");
            }
            if (detailDataSource.case_money && oInputNum.val() !== "") {
                oCasePrice.text("￥" + (detailDataSource.case_money * iInputNum).toFixed(2) + '万元')
            }
        });
    }
    // 在线咨询
    function eventOfOnlineConsult () {
        oOnlineConsultBtn.off().on('click', function () {
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
    }
    // 在线咨询提交按钮点击事件
    function eventOfAskSubmitBtnClick() {
        $(document).on('click', '.askBtnSubmit', function () {
            var json = {
                providerId: JSON.parse(detailDataSource.provider_id).id,
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
                            layer.msg("咨询成功,请前往个人中心查看");
                            setTimeout(function () {
                                layer.closeAll();
                            }, 1000)
                        } else {
                            layer.msg("咨询失败，请稍后再试");
                        }
                    }
                })
            }
        })
    }
    // 互动图标点击事件
    function eventOfActivityBtnClick() {
        oCaseActiveBtn.off('click').on('click', function (event) {
            var targetNode = $(event.target);
            var activeBtnNode = targetNode.hasClass("share-collect-btn") ? targetNode : targetNode.parent()
            if (activeBtnNode.hasClass("collect-btn")) {
                var iconNode = activeBtnNode.find("i");
                // 收藏、取消收藏案例
                if ($(iconNode).hasClass("icon-star-void")) {
                    toCollectTheCase(iconNode, detailDataSource.id, true);
                } else {
                    toCollectTheCase(iconNode, detailDataSource.id, false);
                }
            }
        });
    }
    // tab点击事件
    function eventOfTabClick () {
        $(".case-tab-item").off().on("click", function () {
           if ($(this).index() > 0) {
               layer.msg("此功能正在开发中...")
           }
        });
    }
    // 加入购物车、立即购买点击事件
    function eventOfOperateBtnClick () {
        $(".operate-btn").off().on("click", function () {
            layer.msg("此功能正在开发中...")
        });
    }
    // 用户收藏/ 取消收藏
    function toCollectTheCase(dom, id, isCollect) {
        var json = {
            "matureCaseId": id,
            "isCollection": isCollect
        };
        new NewAjax({
            type: "POST",
            url: "/f/matureCase/pc/collection_mature_case?pc=true",
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
                        $(dom).attr("class", "icon-star-void");
                    } else {
                        layer.msg("收藏成功！");
                        $(dom).attr("class", "icon-collect");
                    }
                    $(dom).next().text(res.data.total)
                }
            },
            error: function (err) {
                console.error('回复：' + err);
            }
        })
    }
    // 获取相关产品列表
    function getRecommendCaseList (callback) {
        var json = {
            pager: {
                current: currentPage,
                size: searchSizeOfRecommend
            }
        };
        new NewAjax({
            url: '/f/matureCase/get_recommend_mature_case?pc=true',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(json),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    // console.log(res.data.data_list)
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                console.error('综合', err);
            }
        })
    }
    function setRecommendCaseList (list) {
        var oFrag = document.createDocumentFragment();
        for (var i = 0, len = list.length; i < len; i++) {
            var oRecommendCaseItem = $(sCaseItemHtml);
            oRecommendCaseItem.attr("href", "/f/" + list[i].id + "/case_detail.html");
            oRecommendCaseItem.find(".relate-case-img").attr("src", list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(",")[0]) : null);
            oRecommendCaseItem.find(".relate-case-title").text(list[i].title).attr("title", list[i].title);
            oFrag.append(oRecommendCaseItem[0]);
        }
        oRelateCaseArea.append(oFrag);
    }
    // 获取企业热卖
    function getCompanyHotCaseList (callback) {
        // 如果不存在服务商
        if (!detailDataSource.provider_id) {
            return;
        }
        var json = {
            "id": detailDataSource.id,
            "pager": {
                "current": currentPage,
                "size": searchSizeOfCompany
            },
            "sortPointer": {
                "filed": "recommended_index",
                "order": "DESC"
            }
        };
        new NewAjax({
            url: '/f/matureCase/pc/query_withing_provider_id?pc=true',
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify(json),
            type: 'post',
            success: function (res) {
                if (res.status === 200) {
                    if (callback) {
                        callback(res.data.data_list);
                    }
                }
            },
            error: function (err) {
                console.error('综合', err);
            }
        });
    }
    function setCompanyHotCaseList (list) {
        var oFrag = document.createDocumentFragment();
        for (var i = 0, len = list.length; i < len; i++) {
            var oRecommendCaseItem = $(sCaseItemHtml);
            oRecommendCaseItem.attr("href", "/f/" + list[i].id + "/case_detail.html");
            oRecommendCaseItem.find(".relate-case-img").attr("src", list[i].picture_cover ? $(this).getAvatar(list[i].picture_cover.split(",")[0]) : null);
            oRecommendCaseItem.find(".relate-case-title").text(list[i].title).attr("title", list[i].title);
            oFrag.append(oRecommendCaseItem[0]);
        }
        oCompanyHotCaseArea.append(oFrag);
    }

    /!*=== 展示图 ===*!/
    // 判断是否需要兼容模式。 ps:1.true => ie9+; 2. false => ie9以下
    function isVersionOverNine() {
        var browser = window.BROWSERTYPE;
        var ieVersion = null;
        if (typeof browser === "boolean") {
            return Boolean(browser);
        } else if (typeof browser === "string") {
            if (browser.indexOf('IE') > -1) {
                ieVersion = parseInt(browser.slice(2));
                return !(ieVersion < 9);
            } else {// ie9+
                return true;
            }
        }
    }
    // 是否为ie/Edge
    function isVersionIE() {
        var browser = window.BROWSERTYPE;
        if (typeof browser === "boolean") {
            return false;
        } else if (typeof browser === "string") {
            return Boolean(browser.indexOf('IE') > -1 || browser.indexOf('Edge') > -1);
        }
    }
    // 初始化图片
    function initResultPicture() {
        extractResultPicture();
        setResultPicture();
        eventOfArrow();
        eventOfPreviewList();
        eventOfPreview();
        eventOfShowImageMask();
    }
    // 提取图片数据
    function extractResultPicture() {
        // 获取视频数据
        var videoData = null;
        // 获取图片数据
        var picData = null;
        // 获取图片数据
        var pic = detailDataSource.picture_cover;
        // 获取视频数据
        var video = detailDataSource.video_cover;
        // 记录数据个数
        var dataNumber = 0;
        // 版本是否超过ie8
        var version = isVersionOverNine();
        // 若图片数据存在
        if (pic) {
            // 防止数据无法解析导致js阻塞
            try {
                // 获取视频数据
                if (video) {
                    videoData = JSON.parse(video);
                    if (videoData instanceof Array) {
                        dataNumber += videoData.length;
                    } else {
                        dataNumber += 1;
                    }
                }
                // 获取图片数据
                if (pic) {
                    picData = JSON.parse(pic);
                    dataNumber += picData.length;
                }
                // 若有图片
                if (dataNumber > 0) {
                    resultPicData = [];
                    if (videoData) {
                        // 遍历视频数据
                        if (videoData instanceof Array) {
                            videoData.forEach(function (item) {
                                var obj = {};
                                obj.name = item.title;
                                obj.url = (version) ? null : noPictureUrl;
                                obj.type = item.prefix;
                                obj.isVideo = true;
                                obj.videoUrl = '/adjuncts/file_range_download/' + videoData.id;
                                obj.assetStatus = (version) ? null : 'have';
                                resultPicData.push(obj);
                            });
                        } else {
                            var obj = {};
                            obj.name = videoData.title;
                            obj.url = (version) ? null : noPictureUrl;
                            obj.type = videoData.prefix;
                            obj.isVideo = true;
                            obj.videoUrl = '/adjuncts/file_range_download/' + videoData.id;
                            obj.assetStatus = (version) ? null : 'have';
                            resultPicData.push(obj);
                        }
                    }
                    if (picData) {
                        // 遍历图片数据
                        picData.forEach(function (item) {
                            var obj = {};
                            obj.name = item.title;
                            obj.url = getAvatar(item.id);
                            obj.type = item.prefix;
                            obj.isVideo = false;
                            resultPicData.push(obj);
                        });
                    }
                } else {
                    resultPicData = null;
                }
            } catch (e) {
                resultPicData = null;
                console.error(e);
            }
        } else {
            resultPicData = null;
        }
    }
    // 写入图片
    function setResultPicture() {
        // 暂时存储
        var stSave = null;
        // 存储新的预览图节点
        var previewNode = null;
        // 存储video 数据
        var videoData = {};
        // 若有数据，则写入
        if (resultPicData) {
            // 遍历写入图片
            resultPicData.forEach(function (item, index) {
                // 已经创建过
                if (item.node) {
                    stSave = item.node;
                    // 是否加载过图片，已加载过图片的不做处理
                    if (!stSave.data('load')) {
                        stSave.find('.img-select-item').eq(0).attr({
                            src: item.url,
                            title: item.name,
                            alt: item.name
                        }).show();
                        // 删除loading
                        if (stSave.find('.img-select-loading').length > 0) {
                            stSave.find('.img-select-loading').eq(0).remove();
                        }
                        // 展示播放按钮 stSave.data('video')
                        if (item.isVideo) {
                            // 根据视频状态展示播放图标
                            stSave.data('asset', item.assetStatus).find('.result-picture-preview-video-mask').eq(0).css({
                                zIndex: (item.assetStatus === 'have') ? 1024 : -1024
                            });
                        } else {
                            // 不是视频直接删除播放按钮。ps:这里是预览图里的播放按钮
                            stSave.find('.result-picture-preview-video-mask').eq(0).remove();
                        }
                        // 若是第一张，填充展示图
                        if (index === 0) {
                            showImageNode.attr({
                                src: item.url,
                                title: item.name,
                                alt: item.name
                            }).show();
                            // 删除loading
                            if (showImageNode.parent().find('.result-picture-show-loading').length > 0) {
                                showImageNode.parent().find('.result-picture-show-loading').eq(0).remove();
                            }
                            // 判定是否为视频
                            if (item.isVideo) {
                                // 展示播放按钮
                                showVideoIcon.css({
                                    zIndex: (item.assetStatus === 'have') ? 1024 : -1024
                                }).data('asset', item.assetStatus);
                                // 隐藏缩放按钮
                                showZoomIcon.css({
                                    zIndex: -1024
                                });
                            } else {
                                // 隐藏播放按钮
                                showVideoIcon.css({
                                    zIndex: -1024
                                }).removeData('asset');
                                // 展示缩放按钮
                                showZoomIcon.css({
                                    zIndex: 1024
                                });
                            }

                        }
                    }
                } else { // 初始创建
                    stSave = previewItemHtml.replace(/>\s+<(\/)?/g, function (str) {
                        // 去除多余的空格换行
                        return (str.indexOf('/') > -1) ? '></' : '><';
                    });
                    // 获取节点
                    previewNode = $(stSave);
                    // 写入数据
                    previewNode.data('load', Boolean(item.url))
                        .find('.img-select-item').eq(0).attr({
                        src: item.url,
                        title: item.name,
                        alt: item.name
                    });
                    // 判定是否为视频写入视频数据。 ps: 不写入if(item.url)的条件里的原因：此时的视频不一定有封面图片的url
                    if (item.isVideo) {
                        videoData.url = item.videoUrl;
                        videoData.type = item.type;
                        previewNode.data('video', videoData);
                        if (item.assetStatus) {
                            previewNode.data('asset', item.assetStatus);
                        }
                    } else {
                        previewNode.removeData('video')
                            .removeData('asset');
                    }
                    // 判断封面图片判定是否出现loading
                    if (item.url) {
                        // 有图片移除loading
                        previewNode.find('.img-select-item').eq(0).show().nextAll('.img-select-loading').remove();
                        // 是否为视频
                        if (item.isVideo) {
                            previewNode.find('div.result-picture-preview-video-mask').eq(0).css({
                                zIndex: 1024
                            });
                        } else {
                            previewNode.find('div.result-picture-preview-video-mask').eq(0).remove();
                        }
                    } else {
                        // 没有则显示loading
                        previewNode.find('.img-select-item').eq(0).hide().nextAll('.img-select-loading').show();
                    }
                    // 插入节点
                    previewListNode.append(previewNode);

                    // 根据内容改变width
                    if (index > 4) {
                        previewListNode.width(previewListNode.width() + 54);
                    }
                    // 存储节点
                    item.node = previewNode;
                    // 若是第一个
                    if (index === 0) {
                        previewNode.addClass('active');
                        showImageNode.attr({
                            src: item.url,
                            title: item.name,
                            alt: item.name
                        });
                        // 是否为视频
                        if (item.isVideo) {
                            showVideoIcon.data('video', videoData);
                            if (item.assetStatus) {
                                showVideoIcon.data('asset', item.assetStatus);
                            }
                        } else {
                            showVideoIcon.removeData('video')
                                .removeData('asset');
                        }
                        // 是否有图片链接
                        if (item.url) {
                            showImageNode.show().nextAll('.result-picture-show-loading').remove();
                            // 是否展示播放
                            showVideoIcon.css({
                                zIndex: (item.isVideo) ? 1024 : -1024
                            });
                            // 是否展示缩放
                            showZoomIcon.css({
                                zIndex: (item.isVideo) ? -1024 : 1024
                            });
                        } else {
                            showImageNode.hide().nextAll('.result-picture-show-loading').show();
                        }
                    }
                }

            });
            /*// 判定图片数量决定是否开启左右箭头
            if (resultPicData.length > 5) {
                previewArrowNode.show();
            } else {
                previewArrowNode.hide();
            }*/
        }
    }
    // 左右箭头事件
    function eventOfArrow() {
        eventOfArrowClick();
    }
    // 左右箭头点击事件
    function eventOfArrowClick() {
        // 获取当前箭头节点
        var nowArrowNode = null;
        // 获取当前List的left
        var nowListLeft = 0;
        // 绑定点击事件
        previewArrowNode.off().click(function (event) {
            // 未超出四张图时不执行事件
            if (!(resultPicData.length > 5)) {
                return 0;
            }
            // 获取left
            nowListLeft = previewListNode.css('left');
            nowListLeft = (nowListLeft) ? (typeof nowListLeft === "string") ? parseInt(nowListLeft) : nowListLeft : 0;
            // 获取当前箭头节点
            nowArrowNode = $(event.target);
            // 判别左箭头
            if (nowArrowNode.attr('name') === 'left') {
                previewListNode.animate({
                    left: (nowListLeft < -54) ? (nowListLeft + 54) + 'px' : 0
                }, 'normal');
            } else { // 右箭头
                previewListNode.animate({
                    left: (!(54 - nowListLeft + previewListNode.parent().width() > previewListNode.width())) ? (nowListLeft - 54) + 'px' : (previewListNode.parent().width() - previewListNode.width()) + 'px'
                }, 'normal');
            }
        })
    }
    // 预览列表事件
    function eventOfPreviewList() {
        eventOfPreviewListInOut();
    }
    // 预览列表进出事件
    function eventOfPreviewListInOut() {
        previewListNode.parents('.result-picture-preview-div').eq(0).mouseenter(function () {
            if (resultPicData.length < 5) {
                return 0;
            }
            previewArrowNode.animate({
                opacity: 1
            }, 'normal')
        }).mouseleave(function () {
            if (resultPicData.length < 5) {
                return 0;
            }
            previewArrowNode.animate({
                opacity: 0
            }, 'normal')
        })
    }
    // 预览图事件
    function eventOfPreview() {
        eventOfPreviewListClick();
    }
    // 预览图点击事件委托
    function eventOfPreviewListClick() {
        // 获取当前节点
        var nowPreviewNode = null;
        // 获取选中的li
        var nowLiNode = null;
        // 获取节点名称
        var nowNodeName = null;
        // 获取视频数据
        var videoData = null;
        // 获取资源的状态
        var assetStatus = null;
        // 绑定点击事件
        previewListNode.off().click(function (event) {
            nowPreviewNode = $(event.target);
            nowNodeName = nowPreviewNode.get(0).tagName.toLowerCase();
            // 判定点击节点是否为li
            if (nowPreviewNode.parent('li').length > 0 || nowNodeName === 'li') {
                // 获取当前li
                nowLiNode = (nowNodeName !== 'li') ? nowPreviewNode.parent() : nowPreviewNode;
                // 点击选中项不执行
                if (nowLiNode.hasClass('active')) {
                    return 0;
                }
                // 获取预览图节点
                nowPreviewNode = nowLiNode.find('img').eq(0);
                // 清除所有选中
                previewListNode.find('li.active').eq(0).removeClass('active');
                // 选中当前li
                nowLiNode.addClass('active');
                // 获取资源状态
                assetStatus = nowLiNode.data('asset');
                // 修改展示图片
                showImageNode.attr({
                    src: nowPreviewNode.attr('src'),
                    title: nowPreviewNode.attr('title'),
                    alt: nowPreviewNode.attr('alt')
                });
                videoData = nowLiNode.data('video');
                if (videoData) {
                    // 开启/关闭蒙板
                    showVideoIcon.css({
                        zIndex: (assetStatus === 'have') ? 1024 : -1024
                    }).data('video', videoData);
                    if (assetStatus === 'have') {
                        showVideoIcon.data('asset', 'have');
                    } else {
                        showVideoIcon.data('asset', 'lose');
                    }
                } else {
                    // 开启/关闭蒙板
                    showVideoIcon.css({
                        zIndex: -1024
                    }).removeData('video').removeData('asset');
                }
                showZoomIcon.css({
                    zIndex: (videoData) ? -1024 : 1024
                });
            }
        })
    }
    // 展示图蒙板事件
    function eventOfShowImageMask() {
        eventOfPlayVideo();
        eventOfZoomImage();
    }
    // 播放视频点击事件
    function eventOfPlayVideo() {
        // 存储视频类型
        var videoType = null;
        // 存储链接
        var src = null;
        // 存储视频html
        var video = null;
        // 存储flv视频对象
        var flvPlayer = null;
        // 暂时存储
        var stSave = null;
        // 存储资源状态
        var assetStatus = null;
        // 绑定点击事件
        showVideoIcon.off().on("click", function () {
            stSave = showVideoIcon.data('video');
            assetStatus = showVideoIcon.data('asset');
            // 没有视频资源 或 视频资源已损坏 则不执行以下代码
            if (!(stSave && (assetStatus && assetStatus === 'have'))) {
                return 0;
            }
            src = stSave.url;
            video = '<video autoplay controls controlsList="nodownload" style="width: 750px; height: 422px;"/>';
            videoType = stSave.type;
            layer.open({
                title: false,
                type: 1,
                area: ['760px', '432px'],
                offset: 'auto',
                resize: false,
                content: video,
                move: false,
                shadeClose: true,
                success: function (layerNode) {
                    // 需要消除layer-anim, 否则qq浏览器无法全屏
                    var timer = setTimeout(function () {
                        if (layerNode.hasClass('layer-anim')) {
                            layerNode.removeClass('layer-anim');
                        }
                        clearTimeout(timer);
                    });
                    // 获取video节点
                    var videoNode = layerNode.find('video').eq(0);
                    if (flvjs.isSupported()) {
                        flvPlayer = flvjs.createPlayer({
                            //videoType
                            // type: 'mp4',
                            type: videoType,
                            url: src,
                            hasAudio: true,
                            hasVideo: true
                        });
                        flvPlayer.attachMediaElement(videoNode.get(0));
                        flvPlayer.load();
                        flvPlayer.play();
                    }
                },
                end: function () {
                    // 暂停
                    flvPlayer.pause();
                    // 断开加载
                    flvPlayer.unload();
                    // 消除媒体元素
                    flvPlayer.detachMediaElement();
                    // 销毁视频对象
                    flvPlayer.destroy();
                    // 变量制空
                    flvPlayer = null;
                }
            });
        });
    }
    // 放大缩小点击事件
    function eventOfZoomImage() {
        // 获取图片链接
        var src = null;
        // 存储img标签
        var imageNode = null;
        showZoomIcon.off().on('click', function () {
            src = showImageNode.attr('src');
            imageNode = '<img src="' + src + '" style="width: 100%; height: auto;"/>';
            layer.open({
                title: false,
                type: 1,
                resize: false,
                area: 'auto',
                maxWidth: document.body.clientWidth * 0.8,
                maxHeight: document.body.clientWidth * 0.8,
                offset: 'auto',
                content: imageNode,
                move: false,
                shadeClose: true
            });
        })
    }

    /!*=== 视频图片截取 ===*!/
    // 提取视频数据
    function getVideoData() {
        // 判定是否有视频
        if (!detailDataSource.video_cover) {
            return 0;
        }
        // 存储视频数据
        var videoData = JSON.parse(detailDataSource.video_cover);
        videoData = (videoData instanceof Array) ? videoData : [].concat(videoData);
        videoArr = [];
        videoData.forEach(function (item, index) {
            var obj = {};
            obj.type = item.prefix;
            obj.url = '/adjuncts/file_range_download/' + item.id;
            obj.title = item.title;
            obj.size = item.size;
            obj.index = index;
            videoArr.push(obj);
        });
        // 写入视频连接截取图片. ps:这里不使用循环来遍历所有视频连接的原因：为保证视频处理是单线程同步处理，预防多视频处理会出现的混乱情况.
        setVideoUrl(videoArr[0].url, videoArr[0].type);
        // setVideoUrl('/static/front/video/Screenrecording_20181028_122245.mp4', 'mp4');
    }
    // 写入视频连接
    function setVideoUrl(url, type) {
        type = type.toLowerCase();
        if (outWindowVideoNode.length > 0) {
            // 静音
            outWindowVideoNode.get(0).muted = true;
        }
        // 全部使用flv,因为浏览器对 mp4 的编码要求不同
        if (flvjs.isSupported()) {
            outPlayer = flvjs.createPlayer({
                type: type,
                url: url,
                hasAudio: true,
                hasVideo: true
            });
            outPlayer.attachMediaElement(outWindowVideoNode.get(0));
            outPlayer.load();
            outPlayer.play();
            outPlayer.on(event, function () {
                console.log(arguments);
            });
        }
    }
    // 屏外视频加载事件
    function eventOfVideoLoadData() {
        // 资源失效监听
        outWindowVideoNode.on('error', function () {
            console.error('资源加载失败');
            getAssetsFalse();
        });
        // 截图监听
        if (isVersionIE()) { // ie/edge
            // 加载成功监听
            outWindowVideoNode.on('playing', function () {
                getFramePicture();
            });
        } else { // 非ie/edge
            // 资源失效监听
            // 加载成功监听
            outWindowVideoNode.on('loadeddata', function () {
                getFramePicture();
            });
        }
    }
    // 视频获取失败
    function getAssetsFalse() {
        // 记录当前视频对应的下标
        var nowIndex = videoArr[0].index;
        resultPicData[nowIndex].url = loseAssetUrl;
        resultPicData[nowIndex].assetStatus = 'lose';
        if (videoArr.length > 0) {
            // 删除第一子项
            videoArr.shift();
            // 还有子项时重新复制
            if (videoArr.length > 0) {
                setVideoUrl(videoArr[0].url, videoArr[0].type);
            } else { // 当视频数据遍历完后删除video节点并重写图片
                outWindowVideoNode.remove();
                outWindowVideoNode = null;
                if (resultPicData) {
                    // 重新写入轮播图
                    setResultPicture();
                }
            }
        }
    }
    // 提取帧画面
    function getFramePicture() {
        // 定义倍数
        var scale = 0.8;
        // 存储base64图片
        var image64 = null;
        // 创建Canvas
        var canvas = document.createElement("canvas");
        canvas.width = outWindowVideoNode.get(0).videoWidth * scale;
        canvas.height = outWindowVideoNode.get(0).videoHeight * scale;
        // 截取当前帧画面
        canvas.getContext('2d').drawImage(outWindowVideoNode.get(0), 0, 0, canvas.width, canvas.height);
        // 存储图片
        image64 = canvas.toDataURL("image/png");
        // 消除Canvas
        canvas = null;
        // 存储视频截图
        videoImageArr.push(image64);
        outWindowVideoNode.get(0).pause();
        outWindowVideoNode.get(0).currentTime = 0;
        // 消除flv对象
        if (outPlayer) {
            // 暂停
            outPlayer.pause();
            // 断开加载
            outPlayer.unload();
            // 消除媒体元素
            outPlayer.detachMediaElement();
            // 销毁视频对象
            outPlayer.destroy();
            // 变量制空
            outPlayer = null;
        }
        if (videoArr.length > 0) {
            // 删除第一子项
            videoArr.shift();
            // 还有子项时重新复制
            if (videoArr.length > 0) {
                setVideoUrl(videoArr[0].url);
            } else { // 当视频数据遍历完后删除video节点并重写图片
                outWindowVideoNode.remove();
                outWindowVideoNode = null;
                if (resultPicData) {
                    videoImageArr.forEach(function (value, index) {
                        resultPicData[index].url = value;
                        resultPicData[index].assetStatus = 'have';
                    });
                    // 重新写入轮播图
                    setResultPicture();
                }
            }
        }
    }

    /* 分享 */
    // 分享按钮的移入事件
    function eventOfMouseEnterShareBtn() {
        oShareBtnNode.mouseenter(function () {
            if (oShareModelNode.is(':hidden')) {
                // 提取数据
                extractShareData();
                oShareModelNode.show();
            }
        })
    }
    // 分享按钮移出事件
    function eventOfMouseLeaveShareBtn() {
        oShareBtnNode.mouseleave(function () {
            if (oShareModelNode.is(':visible')) {
                oShareModelNode.hide();
            }
        })
    }
    // 事件委托：分享按钮的点击事件
    function eventOfShareListClick() {
        // 获取当前节点
        var oNowClickNode = null,
            oNowShareNode = null,
            sNowShareType = null,
            sShareUrl = null;
        oShareListNode.off().click(function (event) {
            oNowClickNode = $(event.target);
            if (oNowClickNode.hasClass('share-method-item') || oNowClickNode.parents('.share-method-item').length > 0) {
                oNowShareNode = (oNowClickNode.hasClass('share-method-item')) ? oNowClickNode : oNowClickNode.parents('.share-method-item').eq(0);
                sNowShareType = oNowShareNode.attr('type');
                sShareUrl = getShareUrl(sNowShareType);
                if (sShareUrl === null) {
                    layer.closeAll();
                    layer.msg('还未开放该分享功能');
                } else {
                    console.log(sShareUrl);
                    window.open(sShareUrl, 'newwindow', 'height=400,width=800,top=100,left=100');
                }
            }
        })
    }
    // 提取关键数据
    function extractShareData() {
        // 获取标题节点
        var oTitleNode = null;
        // 获取当前链接
        var sNowUrl = null;
        // 获取当前链接前缀
        var sUrlPrefix = null;
        // 获取
        if (oShareData === null) {
            oTitleNode = $('.case-detail-content .base-info-title').eq(0);
            sNowUrl = window.location.href;
            sUrlPrefix = sNowUrl.match(urlPrefixRule)[0];
            oShareData = {};
            oShareData.title = oTitleNode.text();
            oShareData.url = sNowUrl;
            if (resultPicData !== null && resultPicData.length > 0) {
                oShareData.pic = (resultPicData[0].url.indexOf(sUrlPrefix) < 0) ? sUrlPrefix + resultPicData[0].url : sUrlPrefix + resultPicData[0].url;
            }
            oShareData.desc = oTitleNode.text();
            console.log(oShareData);
        }
    }
    // 根据分享类型返回分享链接
    function getShareUrl(type) {
        if (type === 'weibo') {
            return createWeiboShareUrl(oShareData);
        } else if (type === 'QQ') {
            return createQQShareUrl(oShareData);
        } else if (type === 'QQSpace') {
            return createQQSpaceShareUrl(oShareData);
        } else if (type === 'postBar') {
            return createPostBarShareUrl(oShareData);
        }
    }
    // 创建微博的分享链接
    function createWeiboShareUrl(data) {
        var _shareUrl = 'http://v.t.sina.com.cn/share/share.php?';
        // 真实的appkey，必选参数
        _shareUrl += 'appkey=3789222353';
        // 参数url设置分享的内容链接|默认当前页location，可选参数
        _shareUrl += '&url='+ encodeURIComponent(data.url ||document.location);
        // 参数title设置分享的标题|默认当前页标题，可选参数
        _shareUrl += '&title=' + encodeURIComponent(data.title ||document.title);
        // 参数content设置页面编码gb2312|utf-8，可选参数
        _shareUrl += '&content=' + 'utf-8';
        // 参数pic设置图片链接|默认为空，可选参数
        _shareUrl += '&pic=' + encodeURIComponent(data.pic || '');
        return _shareUrl;
    }
    // 创建百度贴吧分享链接
    function createPostBarShareUrl(data) {
        var _shareUrl = 'http://tieba.baidu.com/f/commit/share/openShareApi?';
        //分享的标题
        _shareUrl += 'title=' + encodeURIComponent(data.title || '标题');
        //分享的链接
        _shareUrl += '&url=' + encodeURIComponent(data.url || window.location.href);
        //分享的图片
        _shareUrl += '&pic=' + encodeURIComponent(data.pic || '');
        return _shareUrl;
    }
    // 创建qq分享链接
    function createQQShareUrl(data) {
        var _shareUrl = 'https://connect.qq.com/widget/shareqq/wu.html?';
        // 跳转链接
        _shareUrl += 'url=' + encodeURIComponent(data.url || window.location.href) + '?sharesource=qzone';
        // 标题
        _shareUrl += '&title=' + encodeURIComponent(data.title || '标题');
        // 图片链接
        _shareUrl += '&pics=' + encodeURIComponent(data.pic || '');
        // 概述
        _shareUrl += '&summary=' + encodeURIComponent(data.desc || '');
        return _shareUrl;
    }
    // 创建qq空间分享链接
    function createQQSpaceShareUrl(data) {
        var _shareUrl = 'https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?';
        // 跳转链接
        _shareUrl += 'url=' + encodeURIComponent(data.url || window.location.href);
        // title
        _shareUrl += '&title=' + encodeURIComponent(data.title || '标题');
        // 摘要
        _shareUrl += '&summary=' + encodeURIComponent(data.desc || '分享的描述');
        // 图片
        _shareUrl += '&pics=' + encodeURIComponent(data.pic || '');
        return _shareUrl;
    }
});