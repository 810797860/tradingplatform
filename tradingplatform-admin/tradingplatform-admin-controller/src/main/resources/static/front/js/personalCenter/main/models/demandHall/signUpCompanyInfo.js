/**
 * Created by 空 on 2018/9/7.
 */

$(function () {
    // 获取当前雇佣详情记录的id
    var nNowDetailId = (signUpCompanyInfodata.id) ? signUpCompanyInfodata.id : null;
    // 获取当前项目id
    var nNowProjectId = (signUpCompanyInfodata.project_id) ? JSON.parse(signUpCompanyInfodata.project_id).id : null;
    // 获取当前用户数据
    var userInfo = getLocalStorage('user');
    // 获取当前用户id
    var nowUserId = (userInfo) ? JSON.parse(userInfo).id : null;
    // 获取loading节点
    var oLoadingNode = $('.sign-up-company-info .nav-content-loading-Mask').eq(0);
    // 当前留言id
    var nNowMessageId = null;

    /*=== 留言 ===*/
    // 每项html模板
    var sMessageHTML = '<li class="leave-message-item">\n' +
        '                            <div class="leave-message-item-top">\n' +
        '                                <span class="leave-message-answer"></span>\n' +
        '                                <span class="leave-message-questioner"></span>\n' +
        '                                <span class="leave-message-reply-time"></span>\n' +
        '                                <span class="leave-message-reply"></span>\n' +
        '                                <span class="leave-message-delete"></span>\n' +
        '                            </div>\n' +
        '                            <div class="leave-message-item-bottom">\n' +
        '                                <p class="leave-message-content"></p>\n' +
        '                            </div>\n' +
        '                        </li>';
    // 获取留言列表
    var oMessageListNode = $('.sign-up-company-info .leave-message-list').eq(0);
    // 获取留言输入框
    var oMessageInputNode = $('.sign-up-company-info .leave-message-submit-input').eq(0);
    // 获取留言按钮
    var oMessageTrueBtnNode = $('.sign-up-company-info .leave-message-submit-btn[name="send"]').eq(0);
    // 获取取消按钮
    var oMessageCancelBtnNode = $('.sign-up-company-info .leave-message-submit-btn[name="cancel"]').eq(0);
    // 获取提问者信息
    var oQuestionerInfo = {};
    // 留言模式，0为普通留言，1为回复
    var nMessageType = 0;
    // 获取留言数据列表
    var aMessageList = [];
    // 记录评论（留言）每次请求的条数
    var leaveMessageSearchSize = 5;

    /*=== 分页 ===*/
    // 获取分页插件
    var messagePage = null;
    // 默认每页展示的条数
    var defaultPageSize = 5;
    // 默认当前页页码
    var defaultPageNumber = 1;
    // 记录最大页码
    var maxPageNumber = 0;
    // 记录当前页码
    var nowPageNumber = 1;

    initPage();
    handleClick();

    function initPage() {
        var LocalUserInfo = window.localStorage.getItem('user')
        if (JSON.parse(signUpCompanyInfodata.project_id).publisher != JSON.parse(LocalUserInfo).id) {
            $('.submit-area').remove();
        }
        if (!!signUpCompanyInfodata) {
            if (JSON.parse(signUpCompanyInfodata.is_winning).id != '202077') {
                $('.sign-up-company-info .submit-area .submit').remove()
            }
        }
        var userInfo = $('.company-info-area .info-title').attr('data-username');
        var title = JSON.parse(userInfo).user_name;
        $('.company-info-area .info-title').html(title);
        var phone = JSON.parse(userInfo).phone;
        $('.company-info-area .info-phone').html(phone);
        var created_at = $('.company-info-area .info-time').attr('data-time');
        var time = $(this).formatTime(new Date(created_at)).split(' ')[0];
        $('.company-info-area .info-time').html(time);
        var money = $('.company-info-area .info-money').attr('data-money');
        if (parseFloat(money) == 0) {
            $('.company-info-area .info-money').html('面议');
        } else if (parseInt(money) == 0) {
            $('.company-info-area .info-money').html('面议');
        } else {
            $('.company-info-area .info-money').html(money + '万元');
        }
        var imgSrc = JSON.parse(userInfo).avatar;
        if (!!imgSrc) {
            $('.company-info-area .company-logo').attr("src", '/adjuncts/file_download/' + imgSrc + '');
        } else {
            $('.company-info-area .company-logo').attr("src", '/static/assets/defalutexpretTitle.png');
        }
        var tableData = $('#sign-up-company-info-file-table').attr('data-table');

        if (!!tableData && tableData.length !== 0) {
            var data = [];
            var table = new Table('sign-up-company-info-file-table');
            var baseStyleArr = [];
            if (tableData != undefined && tableData.length != 0) {
                JSON.parse(tableData).forEach(function (item) {
                    var obj = {};
                    if (baseStyleArr.length === 0) {
                        Object.keys(item).forEach(function (key) {
                            var styleItem = {};
                            styleItem.type = key;
                            switch (key) {
                                case 'title':
                                    styleItem.name = '文件名称';
                                    break;
                                case 'size':
                                    styleItem.name = '文件大小';
                                    break;
                                case 'id':
                                    styleItem.name = '操作';
                                    break;
                            }
                            styleItem.align = 'left';
                            baseStyleArr.push(styleItem);
                        })
                    }
                    obj.title = item.title;
                    obj.size = item.size;
                    obj.id = item.id;
                    data.push(obj);
                })
            }
            var orderArr = ['title', 'size', 'id'];
            table.setTableData(data);
            table.setBaseStyle(baseStyleArr);
            table.setColOrder(orderArr);
            table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
                if (type === 'id') {
                    var span = '<a href="/adjuncts/file_download/' + content + '" class="loadFile" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer">下载</a>';
                    return (label === 'td') ? span : content;
                }
            })
            table.createTable();
        }
    }
    // 点击事件
    function handleClick() {
        // 点击下载事件
        $(document).on('click', '.loadFile', function (e) {
            var id = $(this).attr('data-id');
            new NewAjax({
                type: "GET",
                url: '/adjuncts/file_download/' + id,
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                success: function (res) {
                    if (res.status === 200) {
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: '文件不存在'
                        })
                    }
                }
            });
        })
        // 点击雇佣事件
        $('.sign-up-company-info .submit-area .submit').off().click(function () {
            var userInfo = window.localStorage.getItem('user');
            if (!!signUpCompanyInfodata) {
                if (JSON.parse(signUpCompanyInfodata.project_id).publisher == JSON.parse(userInfo).id) {
                    var projectId = JSON.parse(signUpCompanyInfodata.project_id).id;
                    var id = signUpCompanyInfodata.id;
                    var json = {
                        id: id,
                        projectId: projectId
                    };
                    new NewAjax({
                        type: "POST",
                        url: "/f/projectDemandSignUp/pc/projectWinning?pc=true",
                        contentType: "application/json;charset=UTF-8",
                        dataType: "json",
                        data: JSON.stringify(json),
                        success: function (res) {
                            if (res.status === 200) {
                                $('.sign-up-company-info .submit-area .submit').hide();
                                layer.msg("雇佣成功,即将跳转页面");
                                setTimeout(function () {
                                    window.open('/f/' + projectId + '/demand_detail.html?pc=true');
                                }, 500)
                            } else {
                                layer.msg(res.message);
                            }
                        }
                    })
                }
            } else {
                window.open('/f/login.html?pc=true', '_self');
            }
        });
        eventOfNavClick();
        eventOfMessageListClick();
        eventOfMessageInputKeyDown();
        eventOfMessageSubmit();
        eventOfSubmitMouseEnter();
        eventOfSubmitMouseOut();
        eventOfMessageCancel();
    }

    // nav点击事件
    function eventOfNavClick() {
        // 获取navList
        var oNavListNode = $('.sign-up-company-info .nav-list').eq(0);
        // 获取navContentItem数组
        var aNavContentLists = $('.sign-up-company-info .nav-content-item');

        // navList的点击事件
        oNavListNode.off().click(function (event) {
            // 当前点击节点
            var oNowClickNode = $(event.target),
                stSave = null,
                // 获取当前的模块类型
                sNowNavType = null;
            console.log()
            if (oNowClickNode.get(0).tagName.toLowerCase() === 'li' || oNowClickNode.parents('li.nav-item').length > 0) {
                if (oNowClickNode.get(0).tagName.toLowerCase() !== 'li') {
                    oNowClickNode = oNowClickNode.parents('li.nav-item').eq(0);
                }
                if (!oNowClickNode.hasClass('active')) {
                    sNowNavType = oNowClickNode.attr('type');
                    // 消除现有的active
                    oNowClickNode.siblings().each(function (index, node) {
                        var jqNode = $(node);
                        if (jqNode.hasClass('active')) {
                            jqNode.removeClass('active');
                        }
                    });
                    // 添加active
                    oNowClickNode.addClass('active');
                    if (sNowNavType === 'message') {
                        stSave = oNowClickNode.find('span.unreadNumberTip');
                        if (stSave.length > 0) {
                            stSave[0].hide();
                        }
                    }
                    aNavContentLists.each(function (index, node) {
                        var jqNode = $(node);
                        if (jqNode.attr('type') === sNowNavType) {
                            jqNode.show();
                        } else {
                            jqNode.hide();
                        }
                    });
                    // 执行对应函数
                    doFunc(sNowNavType);
                }
            }
        })
    }

    // 根据参数：navType 执行对应的函数
    function doFunc(navType) {
        switch (navType) {
            case 'message':
                getLeaveMessage();
                break;
            default:
                break;
        }
    }

    /** 留言 **/
    // 留言请求
    function getLeaveMessage() {
        // 请求的传参
        var dataJson = {
            current: nowPageNumber,
            size: leaveMessageSearchSize
        };
        // 节点展示
        oLoadingNode.show();
        // 请求留言
        new NewAjax({
            url: '/f/demandSignUpComments/' + nNowDetailId + '/get_by_sign_up_id',
            contentType: 'application/json',
            type: 'post',
            data: JSON.stringify(dataJson),
            success: function (res) {
                if (res.status === 200) {
                    var data = res.data;
                    if (data.total > defaultPageSize) {
                        if (messagePage === null) {
                            // 初始化分页
                            messagePage = new PluginPagination('page');
                            // 初始化
                            initPlusPage();
                            // 展示
                            messagePage.showPage(true);
                        }
                        // 计算最大页数
                        maxPageNumber = Math.ceil(data.total / defaultPageSize);
                        // 写入基础数值
                        messagePage.setMaxPageNumber(maxPageNumber)
                            .setNowPageNumber(nowPageNumber)
                            .createPage();
                    } else if (messagePage !== null) {
                        messagePage.showPage(false);
                    }
                    // 提取数组列表
                    extractMessageList(data);
                    // 初始化列表
                    initLeaveMessageList();
                    // 节点隐藏
                    oLoadingNode.hide();
                }
            },
            error: function (err) {
                console.error('留言：' + (err));
            }
        })
    }

    // 提取数据列表
    function extractMessageList(data) {
        var list = data.data_list;
        // 清空原数组
        aMessageList = [];
        list.forEach(function (item) {
            var oAnswerInfo = (item.responder_id) ? JSON.parse(item.responder_id) : null;
            var oQuestionerInfo = (item.respondent_id) ? JSON.parse(item.respondent_id) : null;
            var obj = {
                answerId: (oAnswerInfo) ? oAnswerInfo.id : oAnswerInfo,
                answerName: (oAnswerInfo) ? oAnswerInfo.user_name : oAnswerInfo,
                questionerId: (oQuestionerInfo) ? oQuestionerInfo.id : oQuestionerInfo,
                questionerName: (oQuestionerInfo) ? oQuestionerInfo.user_name : oQuestionerInfo,
                createTime: extractTime(item.created_at),
                messageId: (item.id) ? item.id : null,
                content: (item.contents) ? item.contents : null
            };
            aMessageList.push(obj);
        });
    }

    // 提取时间
    function extractTime(time) {
        if (time == null) {
            return null;
        }
        var date = new Date(time);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        return year + '-' + month + '-' + day + ' ' + hour + ':' + ((minute < 10) ? '0' + minute : minute) + ':' + ((second < 10) ? '0' + second : second);
    }

    // 留言初始化
    function initLeaveMessageList() {
        var rSpaceRule = />\s|\s</g;
        var rAnswerRule = /<span class="leave-message-answer[^>]+><\/span>/;
        var rQuestionerRule = /<span class="leave-message-questioner[^>]+><\/span>/;
        var rTimeRule = /<span class="leave-message-reply-time[^>]+><\/span>/;
        var rReplyRule = /<span class="leave-message-reply[^>]+><\/span>/;
        var rDeleteRule = /<span class="leave-message-delete[^>]+><\/span>/;
        var rContentRule = /<p class="leave-message-content[^>]+><\/p>/;
        var result = '';
        aMessageList.forEach(function (item) {
            result += sMessageHTML.replace(rSpaceRule, function (sSpaceStr) {
                if (sSpaceStr.indexOf('>') > -1) {
                    return '>';
                } else {
                    return '<';
                }
            }).replace(rAnswerRule, function (sAnswerStr) {
                if (item.answerName) {
                    return sAnswerStr.slice(0, -8) + ' data-id="' + item.answerId + '">' + item.answerName + sAnswerStr.slice(-7);
                } else {
                    return '';
                }
            }).replace(rQuestionerRule, function (sQuestionerStr) {
                if (item.questionerName) {
                    return sQuestionerStr.slice(0, -8) + ' data-id="' + item.questionerId + '">' + item.questionerName + sQuestionerStr.slice(-7);
                } else {
                    return '';
                }
            }).replace(rTimeRule, function (sTimeStr) {
                if (item.createTime) {
                    return sTimeStr.slice(0, -7) + item.createTime + sTimeStr.slice(-7);
                } else {
                    return '';
                }
            }).replace(rReplyRule, function (sReplyStr) {
                // 跟回答者不是同个人则可以回答
                if (item.answerId != nowUserId) {
                    return sReplyStr.slice(0, -8) + 'data-id="' + item.messageId + '">' + '回复' + sReplyStr.slice(-7);
                } else {
                    return ''
                }
            }).replace(rDeleteRule, function (sDeleteStr) {
                // 跟回答者是同个人则可以删除
                if (item.answerId == nowUserId) {
                    return sDeleteStr.slice(0, -8) + 'data-id="' + item.messageId + '">' + '删除' + sDeleteStr.slice(-7);
                } else {
                    return ''
                }
            }).replace(rContentRule, function (sContentStr) {
                return sContentStr.slice(0, -4) + ((item.content) ? item.content : '') + sContentStr.slice(-4);
            })
        });
        oMessageListNode.html(result);
    }

    // 留言列表点击事件
    function eventOfMessageListClick() {
        var oNowClickNode = null,
            sNowNodeTagName = null;
        oMessageListNode.off().click(function (event) {
            oNowClickNode = $(event.target);
            sNowNodeTagName = oNowClickNode.get(0).tagName.toLowerCase();
            if (sNowNodeTagName === 'span') {
                // 回复
                if (oNowClickNode.hasClass('leave-message-reply')) {
                    handleOfReplyClick(oNowClickNode);
                } else if (oNowClickNode.hasClass('leave-message-delete')) { // 删除
                    handleOfDeleteClick(oNowClickNode);
                }
            }
        })
    }

    // 点击了回复执行的函数
    function handleOfReplyClick(node) {
        // 获取当前留言项
        var oNowMessageItemNode = node.parents('.leave-message-item').eq(0),
            // 获取留言项回答者节点
            oNowAnswerNode = oNowMessageItemNode.find('span.leave-message-answer').eq(0);
        // 初始化提问者信息
        oQuestionerInfo = {};
        // 这里提问者信息从留言的回答者节点获取
        oQuestionerInfo.id = oNowAnswerNode.data('id');
        oQuestionerInfo.name = oNowAnswerNode.text();
        // 修改textarea里的提示
        oMessageInputNode.attr('placeholder', '回复 ' + oQuestionerInfo.name);
        // 鼠标选中
        oMessageInputNode.blur();
        // 修改提交按钮提示
        oMessageTrueBtnNode.text('回复');
        // 修改留言模式
        nMessageType = 1;
        // 获取回复的留言id
        nNowMessageId = node.data('id');
    }

    // 点击删除执行的函数
    function handleOfDeleteClick(node) {
        layer.confirm('是否要删除该留言?', function (index) {
            // 获取留言id
            var messageId = node.data('id');
            new NewAjax({
                url: '/f/demandSignUpComments/' + messageId + '/delete',
                contentType: "application/json",
                type: "POST",
                data: '',
                success: function (res) {
                    if (res.status === 200) {
                        getLeaveMessage();
                    }
                }
            });
            layer.close(index);
        });

    }

    // 留言input框的按钮事件
    function eventOfMessageInputKeyDown() {
        oMessageInputNode.keydown(function (event) {
            switch (event.keyCode) {
                case 13:
                    if (event.ctrlKey === true) {
                        oMessageTrueBtnNode.click();
                    }
                    break;
                case 9:
                    handleOfTab(event);
                    break;
                default:
                    break;
            }
        })
    }

    // tab按钮
    function handleOfTab(event) {
        var inputNode = oMessageInputNode.get(0),
            position = inputNode.selectionStart + 1;
        inputNode.value = inputNode.value.substr(0, inputNode.selectionStart) + '\t' + inputNode.value.substr(inputNode.selectionStart);
        inputNode.selectionStart = position;
        inputNode.selectionEnd = position;
        oMessageInputNode.focus();
        event.preventDefault();
    }

    // 提交按钮的mouseEnter事件
    function eventOfSubmitMouseEnter() {
        oMessageTrueBtnNode.mouseenter(function () {
            layer.tips('快捷键：ctrl + Enter', oMessageTrueBtnNode, {
                tips: 4
            })
        });
    }

    // 提交按钮的mouseOut事件
    function eventOfSubmitMouseOut() {
        oMessageTrueBtnNode.mouseout(function () {
            layer.closeAll();
        })
    }

    // 提交留言事件
    function eventOfMessageSubmit() {
        var stSave = null,
            json = {
                signUpId: nNowDetailId,
                projectId: nNowProjectId,
                pid: null,
                contents: null
            };
        oMessageTrueBtnNode.on('click', function () {
            // 修改内容的换行符号
            stSave = oMessageInputNode.val().replace(/[^\s]+/g, function (str) {
                return '<span style="word-break: break-all">' + str + '</span>';
            }).replace(/\n/g, '</br>').replace(/\t/g, '<span style="display: inline-block;width: 2em"></span>');
            // 内容过滤
            stSave = filterSensitiveWord(stSave);
            // 写入回复内容
            json.contents = stSave;
            // 回复模式
            if (nMessageType) {
                json.pid = nNowMessageId
            } else {// 普通留言
                json.pid = null;
            }
            new NewAjax({
                url: "/f/demandSignUpComments/create_update",
                contentType: "application/json",
                type: "POST",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        oMessageCancelBtnNode.click();
                        getLeaveMessage();
                    }
                }
            });
        });
    }

    // 留言取消事件
    function eventOfMessageCancel() {
        oMessageCancelBtnNode.off().click(function () {
            // 改为留言模式
            nMessageType = 0;
            // 修改输入提示
            oMessageInputNode.val('');
            oMessageInputNode.attr('placeholder', '留言');
            oMessageInputNode.blur();
            // 修改按钮提示
            oMessageTrueBtnNode.text('留言');
        });
    }

    /** 分页 **/
    // 初始化留言分页
    function initPlusPage() {
        // 测试分页
        messagePage.openFirstEndModel(false)
            .openLift(true)
            .setGetInputNodeCallback(function (inputNode) {
                var rule = /[^\d]/g;
                inputNode.bind('input propertychange', function () {
                    var pageNumber = inputNode.val().replace(rule, function () {
                        return ''
                    });
                    if (pageNumber.length > 0) {
                        pageNumber = parseInt(pageNumber);
                        if (pageNumber < 1) {
                            pageNumber = 1;
                        } else if (pageNumber > maxPageNumber) {
                            pageNumber = maxPageNumber;
                        }
                        nowPageNumber = pageNumber;
                        canLift = true;
                    } else {
                        canLift = false;
                    }
                    inputNode.val(pageNumber);
                });
            })
            .createPage()
            .setClickCallback(function (node, oldPageNumber) {
                var _this = this;
                var labelType = node.get(0).tagName.toLowerCase();
                var newNumber = 0;
                if (labelType === 'li') {
                    // 页码按钮
                    if (node.data('status') !== undefined) {
                        newNumber = parseInt(node.data('mark'));
                        if (node.data('status') !== 'active' && newNumber !== oldPageNumber) {
                            nowPageNumber = newNumber;
                            _this.setNowPageNumber(nowPageNumber)
                                .createPage();
                            // 获取留言
                            getLeaveMessage();
                        }
                    } else {// 我是上下页
                        if (node.data('mark') === 'prev') {
                            newNumber = oldPageNumber - 1;
                            if (newNumber > 0) {
                                nowPageNumber = newNumber;
                                _this.setNowPageNumber(nowPageNumber)
                                    .createPage();
                                // 获取留言
                                getLeaveMessage();
                            }
                        } else if (node.data('mark') === 'next') {
                            newNumber = oldPageNumber + 1;
                            if (newNumber < maxPageNumber + 1) {
                                nowPageNumber = newNumber;
                                _this.setNowPageNumber(nowPageNumber)
                                    .createPage();
                                // 获取留言
                                getLeaveMessage();
                            }
                        }
                    }
                } else if (labelType === 'button') {
                    // 按钮
                    if (canLift) {
                        _this.setNowPageNumber(nowPageNumber)
                            .createPage();
                        // 获取留言
                        getLeaveMessage();
                    }
                }
            });
    }
});

