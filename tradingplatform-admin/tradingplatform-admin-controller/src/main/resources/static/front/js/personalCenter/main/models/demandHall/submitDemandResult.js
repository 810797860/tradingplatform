function SubmitDemandResult() {
    // 定义 this
    var _this = this;
    // 获取项目id
    var project_id = undefined;
    // 存储提交数据
    var showData = null;
    // 数据副本
    var copyData = null;
    // 提交数据
    var submitData = null;
    // 获取返回表格节点
    var goBackNode = $('.submit-demand-result div[name="goBack"]').eq(0);
    // 获取标题节点
    // var titleNode = $('.submit-demand-result div[name="title"]').eq(0);
    // 文本展示节点
    var showDetailNode = $('#submitDemandResultShowDetail');
    // 审核印章
    var reviewSign = $('.submitDemandResultReviewSign').eq(0);
    // 文本编辑节点
    var editDetailNode = $('#submitDemandResultEditDetail');
    // 上传节点
    var upLoadNode = $('#submitDemandResultUpload');
    // 展示表格父框
    var showTableDiv = $('#submitDemandResultShowFiles');
    // 编辑表格父框
    var editTableDiv = $('#submitDemandResultEditFiles');
    // 展示文件表格
    var showTable = new Table('submitDemandResultShowFiles');
    // 编辑文件表格
    var editTable = new Table('submitDemandResultEditFiles');
    // 获取修改按钮
    var resetBtn = $('.submit-demand-result-show-div button[name="reset"]').eq(0);
    // 获取上传文件按钮
    var upLoadBtn = $('.submit-demand-result-edit-div button[name="upLoad"]').eq(0);
    // 获取提交按钮
    var submitBtn = $('.submit-demand-result-edit-div button[name="submit"]').eq(0);
    // 获取取消按钮
    var cancelBtn = $('.submit-demand-result-edit-div button[name="cancel"]').eq(0);
    // 获取文本提示
    var textTip = $('.submit-demand-result-tip-div p[name="text"]').eq(0);
    // 获取文件提示
    var fileTip = $('.submit-demand-result-tip-div p[name="file"]').eq(0);
    // 能否操作提交
    var isCanSubmit = true;
    // 防抖变量
    var submitDemandResultNum = true;

    // 初始化表格
    initTable();
    // 所有事件监听
    allEvent();

    _this.isUnderReview = false;
    // 获取需求验收报告的回显数据
    _this.getDemandResult = function (projectId, callback) {
        project_id = projectId;
        new NewAjax({
            url: '/f/projectDemandCheck/' + projectId + '/get_detail?pc=true',
            type: "GET",
            contentType: 'application/json',
            success: function (res) {
                if (res.status === 200) {
                    _this.isUnderReview = true;
                    showData = (!!res.data.data_object) ? res.data.data_object : {};
                    copyData = JSON.parse(JSON.stringify(showData));
                    // 清空表格数据
                    cleanTable();
                    // 写入数据
                    setData();
                    if (showData.check_status !== undefined && showData.check_status !== null) {
                        // 根据审核状态展示数据
                        reviewStatus(showData.check_status)
                    } else {
                        // 改为编辑模式
                        changeShowStatus(false);
                        // 修改按钮样式
                        upLoadBtn.removeAttr('disabled');
                        resetBtn.removeAttr('disabled').show();
                        submitBtn.removeAttr('disabled').show();
                        cancelBtn.removeAttr('disabled').show();
                    }
                    if (callback) {
                        callback(res.data);
                    }
                }
            },
            error: function (err) {
                _this.isUnderReview = false;
                showData = (!!copyData) ? JSON.parse(JSON.stringify(copyData)) : copyData;
                console.error("获取验收成果：" + err);
            }
        })
    };
    // 提交报告
    _this.submitDemandResult = function (data, callback) {
        new NewAjax({
            url: '/f/projectDemandCheck/create_update?pc=true',
            type: "POST",
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                if (res.status === 200 && callback) {
                    callback(res.data);
                    submitDemandResultNum = true;
                }
            },
            error: function (err) {
                console.error("获取验收成果：" + err);
            }
        })
    };

    /*=== 功能函数 ===*/

    // 写入回显数据
    function setData() {
        // 当没有数据的时候跳过
        if (showData === undefined || showData === null) {
            return 0;
        }
        setShowData();
        setEditData();
    }

    // 写入展示数据
    function setShowData() {
        var content = (!!showData) ? (!!showData.illustration) ? showData.illustration.replace(/\n/g, '<br/>') : '暂无数据' : '暂无数据';
        showDetailNode.html(content);
        setTable(false);
    }

    // 写入编辑数据
    function setEditData() {
        editDetailNode.val(showData.illustration);
        setTable(true)
    }

    // 初始化表格
    function initTable() {
        var orderArr = ['title', 'size', 'id'];
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
        // 初始化展示表格
        showTable.setShowColArr(['title', 'size', 'id'])
            .setColOrder(orderArr)
            .setTableLineHeight(40)
            .setOpenIndex(true)
            .setBaseStyle(baseStyleArr)
            .resetHtmlCallBack(function (type, content, label) {
                if (label === 'td') {
                    if (type === 'id') {
                        return '<button class="btn btn-info" name="download" style="background-color: #0db5fb" data-id="' + content + '">查看</button>';
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
        // 初始化编辑表格
        editTable.setShowColArr(['title', 'size', 'id'])
            .setColOrder(orderArr)
            .setTableLineHeight(40)
            .setOpenIndex(true)
            .setBaseStyle(baseStyleArr)
            .resetHtmlCallBack(function (type, content, label) {
                if (label === 'td') {
                    if (type === 'id') {
                        return '<button class="btn btn-info" name="download" style="background-color: #0db5fb" data-id="' + content + '">查看</button>' +
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
                    var data = (typeof showData.attachment === "string") ? JSON.parse(showData.attachment) : showData.attachment;
                    // 获取id
                    fileId = parseInt(node.attr('data-id'));
                    // 获取id 对应的下标
                    index = data.searchArrayObj(fileId, 'id');
                    // 删除下标对应子项
                    data.splice(index, 1);
                    // 重写数据并重绘
                    editTable.setTableData(data).createTable();
                    // 重新赋值
                    showData.attachment = JSON.stringify(data);
                } else if (node.attr('name') === 'download') {
                    // 获取文件id
                    fileId = parseInt(node.attr('data-id'));
                    // 页面调整
                    window.location.href = '/adjuncts/file_download/' + fileId;
                }
            }
        });
        //
    }

    // 清空表格数据
    function cleanTable() {
        // 展示表格清空
        showTable.setTableData([]).createTable();
        // 编辑表格清空
        editTable.setTableData([]).createTable();
    }

    // 写入表格
    function setTable(isEdit) {
        // 没有数据
        if (showData.attachment === undefined || showData.attachment === null) {
            if (showTableDiv.css('display') !== 'none') {
                // 展示表格隐藏
                showTableDiv.hide().parent().append('<p class="submitDemandResultShowFilesTip">暂无数据</p>');
            }
            if (showTableDiv.css('display') !== 'none') {
                // 编辑表格隐藏
                editTableDiv.hide();
            }
        } else {
            var data = JSON.parse(showData.attachment);
            // 写入编辑表格
            if (isEdit) {
                // 写入数据
                editTable.setTableData(data).createTable();
                // 展示表格
                editTableDiv.show();
            } else { // 写入展示表格
                // 写入数据
                showTable.setTableData(data).createTable();
                // 删除没有内容的提示并展示
                showTableDiv.show().next().remove();
            }
        }
    }

    // 审核状态
    function reviewStatus(data) {
        var nowStatus = JSON.parse(data).id;
        switch (nowStatus) {
            case '202049':// 审核中
                isCanSubmit = false;
                // 变为数据展示模式
                changeShowStatus(true);
                // 修改印章
                reviewSign.text('审核中').css({
                    borderColor: '#aaaaaa',
                    color: '#aaaaaa'
                }).show();
                // 修改按钮样式
                upLoadBtn.attr({
                    disabled: 'disabled'
                });
                resetBtn.attr({
                    disabled: 'disabled'
                }).hide();
                submitBtn.attr({
                    disabled: 'disabled'
                }).hide();
                cancelBtn.attr({
                    disabled: 'disabled'
                }).hide();
                break;
            case '202050':
                isCanSubmit = false;
                // 修改数据展示模式
                changeShowStatus(true);
                // 修改印章
                reviewSign.text('通过').css({
                    borderColor: '#00B83F',
                    color: '#00B83F'
                }).show();
                // 修改按钮样式
                upLoadBtn.attr({
                    disabled: 'disabled'
                });
                resetBtn.attr({
                    disabled: 'disabled'
                }).hide();
                submitBtn.attr({
                    disabled: 'disabled'
                }).hide();
                cancelBtn.attr({
                    disabled: 'disabled'
                }).hide();
                break;
            case '202051':
                isCanSubmit = true;
                // 修改数据展示模式
                changeShowStatus(true);
                // 修改印章
                reviewSign.text('不通过').css({
                    borderColor: '#ff0000',
                    color: '#ff0000'
                }).show();
                // 修改按钮样式
                upLoadBtn.removeAttr('disabled');
                resetBtn.removeAttr('disabled').show();
                submitBtn.removeAttr('disabled').show();
                cancelBtn.removeAttr('disabled').show();
                break;
            default:
                break;
        }
    }

    // 修改展示状态
    function changeShowStatus(isShow) {
        // 展示框
        var showDiv = $('.submit-demand-result-show-div').eq(0);
        // 编辑框
        var editDiv = $('.submit-demand-result-edit-div').eq(0);
        // 展示状态
        if (isShow) {
            showDiv.show();
            editDiv.hide();
        } else {// 编辑状态
            showDiv.hide();
            editDiv.show();
        }
    }

    // 提取提交数据
    function getSubmitData() {
        var str = '';
        // 获取上传文件字段
        var files = (!!showData.attachment) ? JSON.parse(showData.attachment) : undefined;
        // 复制
        submitData = JSON.parse(JSON.stringify(showData));
        // 若上传的文件不为空
        if (!!files) {
            files.forEach(function (item) {
                if (str.length === 0) {
                    str += item.id;
                } else {
                    str += ',' + item.id;
                }
            });
            submitData.attachment = str;
        } else {
            submitData.attachment = null;
        }
        submitData.illustration = valueFilter(submitData.illustration, null);
        // 写入id
        submitData.projectId = project_id;
    }

    // 判断提交内容
    function examineSubmitResult(data) {
        var pass = true;
        if (data.attachment === null || data.attachment === undefined || data.attachment.length === 0) {
            pass = false;
            fileTip.text('附件不能为空').show();
        }
        if (data.illustration === null || data.attachment === undefined || data.illustration.length === 0) {
            pass = false;
            textTip.text('说明不能为空').show();
            editDetailNode.css({
                borderColor: '#ff0000'
            })
        }
        return pass;
    }

    /*=== 监听函数 ===*/

    // 所有事件监听
    function allEvent() {
        eventOfEditDetailFocus();
        eventOfEditDetailBlur();
        eventOfResetBtnClick();
        eventOfUploadBtnClick();
        eventOfUploadInputChange();
        eventOfSubmitBtnClick();
        eventOfGoBackClick();
        eventOfCancelBtnClick();
    }

    // 输入框的focus监听事件
    function eventOfEditDetailFocus() {
        editDetailNode.focus(function () {
            textTip.text('').hide();
            editDetailNode.css({
                borderColor: ''
            })
        })
    }

    // 输入框的blur监听事件
    function eventOfEditDetailBlur() {
        editDetailNode.blur(function () {
            if (!isCanSubmit) {
                return 0;
            }
            showData.illustration = filterSensitiveWord(editDetailNode.val());
        })
    }

    // 修改按钮点击事件
    function eventOfResetBtnClick() {
        resetBtn.off().click(function () {
            if (!isCanSubmit) {
                return 0;
            }
            changeShowStatus(false);
        })
    }

    // 上传文件点击事件
    function eventOfUploadBtnClick() {
        upLoadBtn.off().click(function () {
            if (!isCanSubmit) {
                return 0;
            }
            fileTip.text('').hide();
            upLoadNode.click();
        })
    }

    // 上传事件
    function eventOfUploadInputChange() {
        upLoadNode.change(function () {
            if (!isCanSubmit) {
                return 0;
            }
            var files = upLoadNode.get(0).files;
            uploadFile(files, function (data) {
                var files = null;
                if (!!showData.attachment) {
                    files = JSON.parse(showData.attachment);
                } else {
                    files = [];
                }
                if (data instanceof Array) {
                    files = files.concat(data);
                } else {
                    files.push(data);
                }
                // 存入展示数据中
                showData.attachment = JSON.stringify(files);
                // 写入表格
                editTable.setTableData(files).createTable();
                // 展示编辑表格
                editTableDiv.show();
            })
        })
    }

    // 提交按钮点击事件
    function eventOfSubmitBtnClick() {
        submitBtn.off().click(function () {
            if (!submitDemandResultNum) {
                return;
            }
            submitDemandResultNum = false;
            if (!isCanSubmit) {
                submitDemandResultNum = true;
                return 0;
            }
            getSubmitData();
            if (!examineSubmitResult(submitData)) {
                submitDemandResultNum = true;
                layer.msg('请先解决填写错误');
                return 0;
            }
            _this.submitDemandResult(submitData, function () {
                _this.getDemandResult(project_id);
            })
        })
    }

    // 返回列表点击事件
    function eventOfGoBackClick() {
        goBackNode.off().click(function () {
            getDockDemandList();
            $(".right-page").removeClass("page-active").siblings(".dock-demand-list-area").addClass("page-active");
        })
    }

    // 取消按钮点击事件
    function eventOfCancelBtnClick() {
        // 点击直接返回需求列表
        cancelBtn.off().click(function () {
            /* getDockDemandList();
             $(".right-page").removeClass("page-active").siblings(".dock-demand-list-area").addClass("page-active");*/
            if (!isCanSubmit) {
                return 0;
            }
            changeShowStatus(true);
        })
    }
}