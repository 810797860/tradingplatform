/**
 * Created by 空 on 2018/10/15.
 */

$(function () {
    var user = window.localStorage.getItem('user');
    var imgArr = [];
    var timer;
    var imgNum = 1
    initPage();
    getRightNewData();
    rightClickToDetail();
    initFileList();
    downloadFile();
    initImg();
    setTimeImg();
    stopImgInterval();
    carryOnImgInterval();
    toPeopelCenterPublishCase();

    function initPage() {

        if (!!user) {
            $('.outcome-mall-detail-category-file-title-right').remove();
        } else {
            var a = $('<a class="outcome-mall-detail-category-file-title-right" href="/f/login.html?pc=true" style="float: right;height: 60px;line-height: 60px;font-size: 12px;color: #0066cc;cursor: pointer">tips：需登录才可下载附件</a>');
            $('.outcome-mall-detail-category-file-title').append(a);
        }
    }

    // 初始化动画的信息
    function initImg() {
        var imgSrc = $('.outcome-mall-detail-info-logo').attr('data-img');
        if (!!imgSrc) {
            $('.outcome-mall-detail-info-logo img').attr('src', '/adjuncts/file_download/' + JSON.parse(imgSrc)[0].id);
            imgArr = JSON.parse(imgSrc);
            for (var i = 0; i < imgArr.length; i++) {
                if (i == 0) {
                    var img = $('<img class="outcome-mall-detail-info-logo-list-item active" src="/adjuncts/file_download/' + imgArr[i].id + '" style="display:inline-block;width: 50px;height: 50px;vertical-align: top;position: relative;margin-left: 20px;background-color: white;box-sizing: border-box;cursor: pointer"/>')
                    $('.outcome-mall-detail-info-logo-list').append(img);
                } else {
                    var img = $('<img class="outcome-mall-detail-info-logo-list-item" src="/adjuncts/file_download/' + imgArr[i].id + '" style="display:inline-block;width: 50px;height: 50px;vertical-align: top;position: relative;margin-left: 20px;background-color: white;box-sizing: border-box;cursor: pointer"/>')
                    $('.outcome-mall-detail-info-logo-list').append(img);
                }
            }
        } else {
            $('.outcome-mall-detail-info-logo img').attr('src', '/static/front/assets/image/empty3.jpg');
        }
        $('.outcome-mall-detail-info-logo-list-item').eq(0).css('margin-left', 0)
    }

    // 初始化动画
    function setTimeImg() {
        if (imgArr.length > 1) {
            timer = setInterval(function () {
                $('.outcome-mall-detail-info-logo-list-item').removeClass('active');
                $('.outcome-mall-detail-info-logo-list-item').eq(imgNum).addClass('active');
                $('.outcome-mall-detail-info-logo img').attr('src', '/adjuncts/file_download/' + imgArr[imgNum].id);
                imgNum++;
                if (imgNum === imgArr.length) {
                    imgNum = 0;
                }
            }, 2000)
        }
    }

    // 鼠标悬浮停止动画
    function stopImgInterval() {
        $('.outcome-mall-detail-info-logo-list-item').mouseenter(function () {
            if (imgArr.length > 1) {
                clearInterval(timer);
            }
            var imgSrc = $(this).attr('src')
            $('.outcome-mall-detail-info-logo img').attr('src', imgSrc);
            $('.outcome-mall-detail-info-logo-list-item').removeClass('active');
            $(this).addClass('active');
            imgNum = $(this).index();
        })
    }

    // 鼠标离开继续动画
    function carryOnImgInterval() {
        $('.outcome-mall-detail-info-logo-list-item').mouseleave(function () {
            ++imgNum;
            setTimeImg();
        })
    }

    // 拿到右侧栏信息
    function getRightNewData() {
        var json = {
            pager: {//分页信息
                "current": 1,   //当前页数0
                "size": 5       //每页条数
            }
        }
        new NewAjax({
            url: '/f/patents/get_latest_patents?pc=true',
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(json),
            success: function (res) {
                var list = res.data.data_list;
                for (var i = 0; i < list.length; i++) {
                    $('.recommand-experts .global-experts-card').eq(i).attr('data-toDetailId', list[i].id);
                    $('.recommand-experts .global-experts-card').eq(i).find('.title').text(list[i].title);
                    $('.recommand-experts .global-experts-card').eq(i).find('.title').attr('title', list[i].title);
                    $('.recommand-experts .global-experts-card').eq(i).find('.desc').text(list[i].summary);
                    if (!!list[i].type) {
                        $('.recommand-experts .global-experts-card').eq(i).find('.experts-type').text(JSON.parse(list[i].type).title);
                    } else {
                        $('.recommand-experts .global-experts-card').eq(i).find('.experts-type').text('暂无数据');
                    }
                }
            }
        })
    }

    // 右侧栏进入详情
    function rightToDetail(id) {
        var url = '/f/' + id + '/patents_detail.html?pc=true';
        window.open(url, '_self');
    }

    // 右侧栏进入详情
    function rightClickToDetail() {
        $('.global-experts-card').click(function () {
            var id = $(this).attr('data-todetailid');
            rightToDetail(id);
        })
    }

    // 初始化附件表格
    function initFileList() {
        if (!!user) {
            var files;
            if (!!$('.outcome-mall-detail-category-file').attr('data-fileList')) {
                files = JSON.parse($('.outcome-mall-detail-category-file').attr('data-fileList'));
            }
            var fileList = [];
            if (!Array.isArray(files) && !!files) {
                fileList.push(files);
            } else {
                fileList = files;
            }
            console.log(fileList);
            console.log(fileList);
            if (!!fileList) {
                $('.outcome-mall-detail-category-file-content').removeClass('noneData');
                $('.outcome-mall-detail-category-file-content').text();
                var dockResultListData = fileList;
                var data = [];
                var table = new Table('outcome-mall-detail-category-file-list');
                var baseStyleArr = [];
                var arr = [];
                if (dockResultListData != undefined && dockResultListData.length != 0) {
                    dockResultListData.forEach(function (item) {
                        var obj = {}
                        if (baseStyleArr.length === 0) {
                            Object.keys(item).forEach(function (key) {
                                var styleItem = {}
                                styleItem.type = key
                                switch (key) {
                                    case 'title':
                                        styleItem.name = '文件名'
                                        styleItem.width = 400
                                        break
                                    case 'size':
                                        styleItem.name = '大小'
                                        break
                                    case 'prefix':
                                        styleItem.name = '文件类型'
                                        break
                                    case 'id':
                                        styleItem.name = '操作'
                                        break
                                }
                                styleItem.align = 'left'
                                baseStyleArr.push(styleItem)
                            })
                        }
                        obj.title = item.title;
                        obj.size = item.size;
                        obj.prefix = item.prefix;
                        // obj.created_at = $(this).formatTime(new Date(item.created_at)).split(' ')[0]
                        // obj.reply =item.reply
                        // if (item.reply === true) {
                        //     for (var i=0; i<baseStyleArr.length; i++) {
                        //         if(baseStyleArr[i].type === 'id') {
                        //             baseStyleArr.splice(i, 1)
                        //         }
                        //     }
                        //     arr = ['title','consultant','contents','created_at','reply']
                        // } else {
                        // if (!!item['respondent']) {
                        //     obj.id = [item.id, item.pid, item.id, JSON.parse(item['respondent']).id]
                        // } else {
                        //     obj.id = [item.id, item.pid, item.id]
                        // }
                        obj.id = item.id;
                        arr = ['title', 'size', 'prefix', 'id'];
                        // }
                        data.push(obj);
                    })
                }
                var orderArr = arr
                table.setTableData(data)
                table.setBaseStyle(baseStyleArr)
                table.setColOrder(orderArr)
                table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
                    if (type === 'reply') {
                        var span;
                        if (content === true) {
                            span = '<span>已回复</span>';
                        } else {
                            span = '<span>未回复</span>';
                        }
                        return (label === 'td') ? span : content;
                    } else if (type === 'id') {
                        span = '<a class="download" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>下载</a>'
                        return (label === 'td') ? span : content;
                    }
                })
                table.createTable();
            } else {
                $('.outcome-mall-detail-category-file-content').addClass('noneData');
                $('.outcome-mall-detail-category-file-content').text('暂无数据');
            }
        } else {
            $('.outcome-mall-detail-category-file-content').addClass('noneData');
            $('.outcome-mall-detail-category-file-content').text('请先登录');
        }
    }

    // 下载文件
    function downloadFile() {
        $('#outcome-mall-detail-category-file-list .download').click(function () {
            var id = $(this).attr('data-id');
            $(this).attr('href', '/adjuncts/file_download/' + id);
        })
    }

    function toPeopelCenterPublishCase() {
        $('.outcome-mall-detail-main-right .first-botton').click(function () {
            var href = $(this).attr('data-href');
            new NewAjax({
                url: '/f/serviceProvidersCheckRecords/pc/latest_check_records?pc=true',
                contentType: 'application/json',
                type: 'get',
                success: function (res) {
                    if (res.data.data_object !== null && !!res.data.data_object.back_check_status) {
                        if (JSON.parse(res.data.data_object.back_check_status).id == 202050) {
                            window.open('/f/matureCaseCheckRecords/pc/to_create_update.html?pc=true', '_self');
                        } else {
                            pTipMessage('提示', '您未通过身份认证', 'warning', 2000, true);
                            window.open('/f/personal_center.html?pc=true&menu=authentication', '_self');
                        }
                    } else {
                        pTipMessage('提示', '您未通过身份认证', 'warning', 2000, true);
                        window.open('/f/personal_center.html?pc=true&menu=authentication', '_self');
                    }
                },
                error: function (err) {
                    console.error('身份验证请求数据失败，err：' + err)
                }
            })
        })
    }
})