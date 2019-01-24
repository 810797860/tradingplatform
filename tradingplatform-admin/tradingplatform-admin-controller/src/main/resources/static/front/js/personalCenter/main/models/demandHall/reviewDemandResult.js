function ReviewDemandResult() {
    // 定义 this
    var _this = this;
    // 获取项目id
    var project_id = undefined;
    // 记录id
    var record_id = null;
    // 存储提交数据
    var showData = null;
    // 数据副本
    var copyData = null;
    // 提交数据
    var submitData = null;
    // 获取返回表格节点
    var goBackNode = $('.review-demand-result div[name="goBack"]').eq(0);
    // 文本展示节点
    var submitShowDetailNode = $('#reviewDemandResultShowDetail');
    // 审核印章
    var reviewSign = $('.review-demand-result .submitDemandResultReviewSign').eq(0);
    // 展示表格父框
    var submitShowTableDiv = $('#submitDemandResultShowFiles');
    // 展示文件表格
    var submitShowTable = new Table('submitDemandResultShowFilesTable');
    // 能否操作提交
    var isCanSubmit = true;
    // 获取审核展示模块
    var reviewShowModel = $('.review-demand-result .review-demand-result-show-div').eq(0);
    // 获取审核编辑模块
    var reviewEditModel = $('.review-demand-result .review-demand-result-edit-div').eq(0);
    // 获取审核展示内容区域
    // var reviewShowContentArea = $('.review-demand-result-show-div .review-demand-result-show').eq(0);
    // 获取审核材料展示表格父框
    var reviewFileShowTableDiv = $('#reviewDemandResultShowFinishFiles');
    // 获取审核材料展示表格
    var reviewFileShowTable = new Table('reviewDemandResultShowFinishFiles');
    // 获取不通过原因展示节点
    var reviewRejectShowNode = $('#rejectReasonShow');
    // 获取tabs节点
    var reviewTabsNode = $('#reviewDemandResultTabs');
    // 获取展示中的拒绝模块
    var showRejectDiv = reviewShowModel.find('div[name="reject"]').eq(0);
    // 获取展示中的结项材料模块
    var showFinishFileDiv = reviewShowModel.find('div[name="pass"]').eq(0);
    // 获取编辑中的拒绝模块
    var editRejectDiv = reviewEditModel.find('div[name="reject"]').eq(0);
    // 获取编辑中的结项材料模块
    var editFinishFileDiv = reviewEditModel.find('div[name="pass"]').eq(0);
    // 获取结项材料节点
    var reviewFinishFileUpLoadNode = $('#reviewDemandResultUpLoad');
    // 获取结项材料表格父框
    var reviewFileEditTableDiv = $('#reviewDemandResultEditFinishFiles');
    // 获取结项材料表格
    var reviewFileEditTable = new Table('reviewDemandResultEditFinishFiles');
    // 获取拒绝理由的编辑节点
    var reviewRejectEditNode = $('#rejectReasonEdit');
    // 获取上传按钮
    var upLoadBtn = $('.review-demand-result-edit button[name="finishFiles"]').eq(0);
    // 获取提交按钮
    var submitBtn = $('.review-demand-result-edit button[name="finishSubmit"]').eq(0);
    // 获取取消按钮
    var cancelBtn = $('.review-demand-result-edit button[name="finishCancel"]').eq(0);
    // 存储是否通过
    var isPass = true;
    // 获取文本提示节点
    var textTip = $('.review-demand-result-tip-div p[name="text"]').eq(0);
    // 获取文件提示节点
    var fileTip = $('.review-demand-result-tip-div p[name="file"]').eq(0);


    // 初始化表格
    initTable();
    // 所有事件监听
    allEvent();

    // 获取需求验收报告的回显数据
    _this.getDemandResult = function (projectId, callback) {
        project_id = projectId;
        new NewAjax({
            url:'/f/projectDemandCheck/'+ projectId +'/get_detail?pc=true',
            type: "GET",
            contentType: 'application/json',
            success: function (res) {
                if (res.status === 200){
                    if (res.data.data_object) {
                        showData = (res.data.data_object) ? res.data.data_object : {};
                        copyData = JSON.parse(JSON.stringify(showData));
                        record_id = (!!res.data.data_object) ? res.data.data_object.id : null;
                        // 清空table
                        cleanTable();
                        // 写入数据
                        setData();
                        console.table(showData);
                        if (showData.check_status !== undefined && showData.check_status !== null) {
                            // 根据审核状态展示数据
                            reviewStatus(showData.check_status);
                        } else {
                            // 改为编辑模式
                            changeShowStatus(false);
                            // 显示按钮
                            upLoadBtn.removeAttr('disabled').show();
                            submitBtn.removeAttr('disabled').show();
                            cancelBtn.removeAttr('disabled').show();
                            // 显示模块
                            showRejectDiv.hide();
                            showFinishFileDiv.show();
                            editRejectDiv.hide();
                            editFinishFileDiv.show();
                        }
                        if (callback) {
                            callback(res.data);
                        }
                    } else {
                        layer.msg('接包方未提交成果',{time: 1000});
                    }
                }
            },
            error: function (err) {
                showData = (!!copyData) ? JSON.parse(JSON.stringify(copyData)) : copyData;
                console.error("获取验收成果：" + err);
            }
        })
    };
    // 提交报告
    _this.submitDemandResult = function (data, callback) {
        new NewAjax({
            url: '/f/projectDemandCheck/'+ isPass +'/check?pc=true',
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                if (res.status === 200 && callback) {
                    callback(res.data);
                }
            },
            error: function (err) {
                console.error("获取验收成果：" + err);
            }
        })
    };

    /*=== 功能函数 ===*/
    // 初始化表格
    function initTable() {
        var orderArr = ['title','size','id'];
        var baseStyleArr = [
            {
                type: 'title',
                name: '名称',
                align: 'left',
                width: 450
            },
            {
                type: 'size',
                name: '大小',
                align: 'left',
                width: 150
            },
            {
                type: 'id',
                name: '操作',
                align: 'left'
            }
        ];
        // 初始化需求报告展示表格
        submitShowTable.setShowColArr(['title', 'size', 'id'])
            .setColOrder(orderArr)
            .setTableLineHeight(40)
            .setOpenIndex(true)
            .setBaseStyle(baseStyleArr)
            .resetHtmlCallBack(function (type, content, label){
                if(label === 'td') {
                    if (type === 'id') {
                        return '<button class="btn btn-info" name="download" style="background-color: #0066cc" data-id="' + content + '">查看</button>';
                    }
                } else {
                    return content;
                }
            }).setClickCallback(function (node) {
                if (node.get(0).tagName.toLowerCase() === 'button') {
                    if (node.attr('name') === 'download') {
                        // 获取文件id
                        var fileId = parseInt(node.attr('data-id'));
                        // 页面调整
                        window.location.href = '/adjuncts/file_download/' + fileId;
                    }
                }
            });
        // 初始化结项材料展示表格
        reviewFileShowTable.setShowColArr(['title', 'size', 'id'])
            .setColOrder(orderArr)
            .setTableLineHeight(40)
            .setOpenIndex(true)
            .setBaseStyle(baseStyleArr)
            .resetHtmlCallBack(function (type, content, label){
                if(label === 'td') {
                    if (type === 'id') {
                        return '<button class="btn btn-info" name="download" style="background-color: #0066cc" data-id="' + content + '">查看</button>';
                    }
                } else {
                    return content;
                }
            }).setClickCallback(function (node) {
                if (node.get(0).tagName.toLowerCase() === 'button') {
                    if (node.attr('name') === 'download') {
                        // 获取文件id
                        var fileId = parseInt(node.attr('data-id'));
                        // 页面调整
                        window.location.href = '/adjuncts/file_download/' + fileId;
                    }
                }
            });
        // 初始化结项材料编辑表格
        reviewFileEditTable.setShowColArr(['title', 'size', 'id'])
            .setColOrder(orderArr)
            .setTableLineHeight(40)
            .setOpenIndex(true)
            .setBaseStyle(baseStyleArr)
            .resetHtmlCallBack(function (type, content, label) {
                if (label === 'td') {
                    if (type === 'id') {
                        return '<button class="btn btn-info" name="download" style="background-color: #0066cc" data-id="' + content + '">查看</button>' +
                            '<button class="btn btn-danger" name="delete" style="background-color: #ff0000;margin-left: 15px" data-id="' + content + '">删除</button>';
                    } else if (type === 'size') {
                        var size = parseInt(content);
                        if (size > 1024) {
                            return (size / 1024).toFixed(2) + 'MB';
                        } else {
                            return size + 'KB';
                        }
                    } else {
                        return content;
                    }
                } else {
                    return content;
                }
            }).setClickCallback(function (node) {
                var fileId = null;
                var index = null;
                if (node.get(0).tagName.toLowerCase() === 'button') {
                    if (node.attr('name') === 'delete') {
                        var data = (typeof showData.post_material === "string") ? JSON.parse(showData.post_material) : showData.post_material;
                        // 获取id
                        fileId = parseInt(node.attr('data-id'));
                        // 获取id 对应的下标
                        index = data.searchArrayObj(fileId, 'id');
                        // 删除下标对应子项
                        data.splice(index, 1);
                        // 重写数据并重绘
                        reviewFileEditTable.setTableData(data).createTable();
                        // 重新赋值
                        showData.post_material = JSON.stringify(data);
                    } else if (node.attr('name') === 'download') {
                        // 获取文件id
                        fileId = parseInt(node.attr('data-id'));
                        // 页面调整
                        window.location.href = '/adjuncts/file_download/' + fileId;
                    }
                }
            });
    }
    // 清空表格
    function cleanTable() {
        // 展示表格清空
        submitShowTable.setTableData([]).createTable();
        reviewFileShowTable.setTableData([]).createTable();
        // 编辑表格清空
        reviewFileEditTable.setTableData([]).createTable();
    }
    // 写入回显数据
    function setData() {
        // 当没有数据的时候跳过
        if (showData === undefined || showData === null) {
            return 0;
        }
        setDemandResultData();
        setFinishFileShowData();
        setFinishFileEditData();
    }
    // 需求成果数据写入
    function setDemandResultData() {
        var content = (!!showData) ? (!!showData.illustration) ? showData.illustration.replace(/\n/g, '<br/>') : '暂无数据' : '暂无数据';
        submitShowDetailNode.html(content);
        setDemandResultTable();
    }
    // 写入展示数据
    function setFinishFileShowData() {
        var content = (!!showData) ? (!!showData.not_pass_reason) ? showData.not_pass_reason.replace(/\n/g, '<br/>') : '暂无数据' : '暂无数据';
        reviewRejectShowNode.html(content);
        setFinishFileTable(false);
    }
    // 写入编辑数据
    function setFinishFileEditData() {
        reviewRejectEditNode.val(showData.not_pass_reason);
        setFinishFileTable(true)
    }
    // 写入需求报告的表格
    function setDemandResultTable() {
        // 没有数据
        if (showData.attachment === undefined || showData.attachment === null) {
            if(submitShowTableDiv.css('display') !== 'none') {
                // 展示表格隐藏
                submitShowTableDiv.hide().parent().append('<p class="submitDemandResultShowFilesTip">暂无数据</p>');
            }
        } else {
            var data = JSON.parse(showData.attachment);
            // 写入数据
            submitShowTable.setTableData(data).createTable();
            // 删除没有内容的提示并展示
            submitShowTableDiv.show().next().remove();
        }
    }
    // 写入审核表格
    function setFinishFileTable(isEdit) {
        // 没有数据
        if (showData.post_material === undefined || showData.post_material === null) {
            if(reviewFileShowTableDiv.css('display') !== 'none') {
                // 展示表格隐藏
                reviewFileShowTableDiv.hide().parent().append('<p class="submitDemandResultShowFilesTip">暂无数据</p>');
            }
            if (reviewFileEditTableDiv.css('display') !== 'none') {
                // 编辑表格隐藏
                reviewFileEditTableDiv.hide();
            }
        } else {
            var data = JSON.parse(showData.post_material);
            // 写入编辑表格
            if (isEdit){
                // 写入数据
                reviewFileEditTable.setTableData(data).createTable();
                // 展示表格
                reviewFileEditTableDiv.show();
            } else { // 写入展示表格
                // 写入数据
                reviewFileShowTable.setTableData(data).createTable();
                // 删除没有内容的提示并展示
                reviewFileShowTableDiv.show().next().remove();
            }
        }
    }
    // 审核状态
    function reviewStatus(data) {
        // 获取当前的审核状态
        var nowStatus = JSON.parse(data).id;
        switch (nowStatus) {
            case '202049':// 审核中
                isCanSubmit = true;
                // 变为数据展示模式
                changeShowStatus(false);
                // 修改模块显示
                showRejectDiv.hide();
                showFinishFileDiv.hide();
                editRejectDiv.hide();
                editFinishFileDiv.show();
                // 修改印章
                reviewSign.text('审核中').css({
                    borderColor: '#aaaaaa',
                    color: '#aaaaaa'
                }).show();
                upLoadBtn.removeAttr('disabled');
                submitBtn.removeAttr('disabled').show();
                cancelBtn.removeAttr('disabled').show();
                break;
            case '202050': // 通过
                isCanSubmit = false;
                // 修改数据展示模式
                changeShowStatus(true);
                // 修改模块显示
                showRejectDiv.hide();
                showFinishFileDiv.show();
                editRejectDiv.hide();
                editFinishFileDiv.hide();
                // 修改印章
                reviewSign.text('通过').css({
                    borderColor: '#00B83F',
                    color: '#00B83F'
                }).show();
                upLoadBtn.attr({
                    disabled: 'disabled'
                });
                submitBtn.attr({
                    disabled: 'disabled'
                }).hide();
                cancelBtn.attr({
                    disabled: 'disabled'
                }).hide();
                break;
            case '202051': // 拒绝
                isCanSubmit = false;
                // 修改数据展示模式
                changeShowStatus(true);
                // 修改模块显示
                showRejectDiv.show();
                showFinishFileDiv.hide();
                editRejectDiv.hide();
                editFinishFileDiv.hide();
                // 修改印章
                reviewSign.text('不通过').css({
                    borderColor: '#ff0000',
                    color: '#ff0000'
                }).show();
                upLoadBtn.attr({
                    disabled: 'disabled'
                });
                submitBtn.attr({
                    disabled: 'disabled'
                }).hide();
                cancelBtn.attr({
                    disabled: 'disabled'
                }).hide();
                break;
            default:
                break;
        }
    }
    // 修改展示状态
    function changeShowStatus(isShow) {
        // 展示模式
        if (isShow) {
            reviewShowModel.show();
            reviewEditModel.hide();
            reviewSign.show();
        } else {// 编辑模式
            reviewShowModel.hide();
            reviewEditModel.show();
            reviewSign.hide();

        }
    }
    // 提取提交数据
    function getSubmitData() {
        var str = ''
        // 获取上传文件字段
        var files = (!!showData.post_material) ? JSON.parse(showData.post_material) : undefined;
        // 复制
        submitData = {};
        // 若是通过
        if (isPass) {
            submitData.notPassReason = null;
            // 若上传的文件不为空
            if (!!files) {
                files.forEach(function (item) {
                    if (str.length === 0) {
                        str += item.id;
                    } else {
                        str += ',' + item.id;
                    }
                });
                submitData.postMaterial = str;
            } else {
                submitData.postMaterial = null;
            }
        } else { // 不通过
            submitData.postMaterial = null;
            submitData.notPassReason = showData.not_pass_reason;
        }
        // 写入记录id
        submitData.id = record_id;
    }
    // 判断提交内容
    function examineSubmitResult(data) {
        var result = true;
        // 通过，必须有结项材料
        if (!isPass) {
            if (data.notPassReason === null || data.notPassReason === undefined || data.notPassReason.length === 0) {
                result = false;
                textTip.text('说明不能为空').show();
                reviewRejectEditNode.css({
                    borderColor:'#ff0000'
                })
            }
        }
        return result;
    }
    /*=== 监听事件 ===*/
    // 所有事件监听
    function allEvent() {
        eventOfTabsClick();
        eventOfRejectFocus();
        eventOfRejectBlur();
        eventOfUploadBtnClick();
        eventOfUploadInputChange();
        eventOfSubmitBtnClick();
        eventOfGoBackClick();
        eventOfCancelBtnClick();
    }
    // tabs的点击事件
    function eventOfTabsClick() {
        var nowNode = null;
        reviewTabsNode.off().click(function (event) {
            nowNode = $(event.target);
            if (nowNode.get(0).tagName.toLowerCase() === 'li' && !nowNode.hasClass('active')) {
                // 清除选中
                reviewTabsNode.find('li').removeClass('active');
                // 选中
                nowNode.addClass('active');
                // 根据当前选中项展示编写内容
                if (nowNode.attr('name') === 'pass') {
                    isPass = true;
                    editRejectDiv.hide();
                    editFinishFileDiv.show();
                } else {
                    isPass = false;
                    editRejectDiv.show();
                    editFinishFileDiv.hide();
                }
            }
        })
    }
    // 输入框的focus事件
    function eventOfRejectFocus() {
        reviewRejectEditNode.focus(function () {
            textTip.text('').hide();
            reviewRejectEditNode.css({
                borderColor:''
            })
        })
    }
    // 输入框的blur事件
    function eventOfRejectBlur() {
        reviewRejectEditNode.blur(function () {
            if (!isCanSubmit) {
                return 0;
            }
            showData.not_pass_reason = reviewRejectEditNode.val();
        })
    }
    // 上传文件点击事件
    function eventOfUploadBtnClick() {
        upLoadBtn.off().click(function () {
            if (!isCanSubmit) {
                return 0;
            }
            fileTip.text('').hide();
            reviewFinishFileUpLoadNode.click();
        })
    }
    // 上传事件
    function eventOfUploadInputChange() {
        reviewFinishFileUpLoadNode.change(function () {
            if (!isCanSubmit) {
                return 0;
            }
            var files = reviewFinishFileUpLoadNode.get(0).files;
            uploadFile(files, function (data) {
                var files = null;
                if (!!showData.post_material) {
                    files = JSON.parse(showData.post_material);
                } else {
                    files = [];
                }
                if (data instanceof Array) {
                    files = files.concat(data);
                } else {
                    files.push(data);
                }
                // 存入展示数据中
                showData.post_material = JSON.stringify(files);
                // 写入表格
                reviewFileEditTable.setTableData(files).createTable();
                // 展示编辑表格
                reviewFileEditTableDiv.show();
            })
        })
    }
    // 提交按钮点击事件
    function eventOfSubmitBtnClick() {
        submitBtn.off().click(function () {
            if (!isCanSubmit) {
                return 0;
            }
            getSubmitData();
            if(!examineSubmitResult(submitData)) {
                layer.msg('请先解决填写错误');
                return 0;
            }
            _this.submitDemandResult(submitData, function () {
                resetCurrentPagePublishDemand();
                getPublishDemandList();
                $(".right-page").removeClass("page-active").siblings(".publish-demand-list-area").addClass("page-active");
                // _this.getDemandResult(project_id);
            })
        })
    }
    // 返回列表点击事件
    function eventOfGoBackClick() {
        goBackNode.off().click(function () {
            resetCurrentPagePublishDemand();
            getPublishDemandList();
            $(".right-page").removeClass("page-active").siblings(".publish-demand-list-area").addClass("page-active");
            $('.review-demand-result-tabs li').removeClass('active');
            $('.review-demand-result-tabs li').eq(0).addClass('active');
        })
    }
    // 取消按钮点击事件
    function eventOfCancelBtnClick() {
        // 点击直接返回需求列表
        cancelBtn.off().click(function () {
            resetCurrentPagePublishDemand();
            getPublishDemandList();
            $(".right-page").removeClass("page-active").siblings(".publish-demand-list-area").addClass("page-active");
        })
    }
}