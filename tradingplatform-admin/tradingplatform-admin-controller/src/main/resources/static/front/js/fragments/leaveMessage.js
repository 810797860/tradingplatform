function LeaveMessage(mainAreaId) {
    var messageHtml = '<div id="" class="leave-message-item-div">\n' +
        '            <div class="leave-message-item-head-div">\n' +
        '                <a class="leave-message-item-user-link" href=""></a>\n' +
        '                <p class="leave-message-item-time"></p>\n' +
        '                <button class="leave-message-item-reply-show-btn">查看回复</>\n' +
        '            </div>\n' +
        '            <div class="leave-message-item-content-div">\n' +
        '                <div class="leave-message-content-div">\n' +
        '                    <div class="leave-message-content"><i class="icon-message"></i></div>\n' +
        '                </div>\n' +
        '                <div class="leave-message-reply-div" data-mark=""></div>\n' +
        '                <div class="leave-message-reply-input-div" style="display: none">\n' +
        '                    <div class="leave-message-reply-input-ask-div">\n' +
        '                        <p class="leave-message-reply-input-ask-name">xxx：</p>\n' +
        '                        <p class="leave-message-reply-input-ask-content">appbpp</p>\n' +
        '                    </div>\n' +
        '                    <div class="leave-message-reply-input-answer-div">\n' +
        '                        <p class="leave-message-reply-input-answer-title">回复：</p>\n' +
        '                        <textarea class="leave-message-reply-input-answer"></textarea>\n' +
        '                    </div>\n' +
        '                    <div class="leave-message-reply-input-button-div">\n' +
        '                        <button class="leave-message-reply-input-button-true">确定</button>\n' +
        '                        <button class="leave-message-reply-input-button-false">取消</button>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="leave-message-reply-page-div" id="" style="display:none;"></div>\n' +
        '            </div>\n' +
        '        </div>';
    var replyHtml = '<div class="leave-message-reply-item-div">\n' +
        '                        <div class="leave-message-reply-item-head-div">\n' +
        '                            <p class="leave-message-reply-item-user-info">\n' +
        '                                <a class="leave-message-reply-item-answer-link" href=""></a>\n' +
        '                                回复\n' +
        '                                <a class="leave-message-reply-item-ask-link" href=""></a>\n' +
        '                            </p>\n' +
        '                            <p class="leave-message-reply-item-time"></p>\n' +
        '                        </div>\n' +
        '                        <div class="leave-message-reply-item-content-div">\n' +
        '                            <div class="leave-message-reply-content"><i class="icon-message"></i></div>\n' +
        '                        </div>\n' +
        '                    </div>';
    var pageHTMl = '<div class="plugin-pagination">\n' +
        '        <div class="plugin-pagination-content-div plugin-pagination-content-div__ie11">\n' +
        '            <div class="plugin-pagination-content plugin-pagination-content__ie11">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>';
    var deleteHtml = '<span class="leave-message-delete">删除</span>';
    // 存储指针
    var _this = this;
    // 存储数据源
    var dataSource = null;
    // 存储最终写入的框
    var messageDiv = $('#' + mainAreaId).children('.leave-message-div').eq(0);
    // 记录提问者信息
    var askInfo = null;
    // 存储点击确定按钮的回调函数
    var trueBtnCallBack = undefined;
    // 存储删除按钮的回调函数
    var deleteCallBack = undefined;
    // 记录全留言节点数组
    var allLeaveMessageArr = [];
    // 记录改变信息
    var changeInfo = {
        leaveMessage: {
            isChange: true
        },
        reply: {
            isChange: true,
            mark: null
        }
    };
    // 存储回复数据
    var replyData = [];
    // 是否开启分页
    var isOpenPage = true;
    // 记录page实例集合
    var pageInstanceArr = [];
    // 记录page实例配置
    var pageConfigArr = [];
    // 分页配置修改记录
    var pageConfigChangeArr = [];
    // 获取当前用户id
    var nowUserId = undefined;
    // 存储每页的条数
    // var pageInfoSize = 5;
    // 记录分页的每段的页码个数
    // var pageNumberShowSize = 5;
    // 存储当前页码
    // var nowPageNumber = 1;
    // 存储最大页码

    /*=== 功能函数 ===*/

    // 写入数据
    function createLeaveMessage() {
        // 留言被修改了
        if (changeInfo.leaveMessage.isChange) {
            // 消除留言修改
            changeInfo.leaveMessage.isChange = false;
            // 写入留言
            var newHtml = setLeaveMessageData();
            // 写入回复
            newHtml = setReplyData(newHtml);
            // 写入留言区域
            messageDiv.html(newHtml);
            // 提取分页实例 + 更新留言节点
            extractPageInstance();
        } else if (changeInfo.reply.isChange) { // 回复修改了
            // 消除回复修改
            changeInfo.reply.isChange = false;
            // 只修改回复
            setReplyData();
        }
        // 写入二级分页
        setPage();
    }

    // 提取留言信息
    function extractLeaveMessage(messageData) {
        // 遍历数据
        messageData.forEach(function (item) {
            var obj = {};
            obj.mark = item.mark;
            obj.id = item.data.message.id + new Date().getTime().toString(16).slice(2);
            // 获取留言区域节点（ps:此时还未写入dom结构，暂不获取）
            obj.node = null;
            allLeaveMessageArr.push(obj);
        });
    }

    // 提取分页实例信息（ps:只在更新留言才调用；结合分页配置数据 + 留言节点数据）
    function extractPageInstance() {
        // 存储对应数据项
        var pageConfigItem = null;
        // 是否创建分页实例
        var isCreatePage = false;
        // 情况分页数据数组
        pageInstanceArr = [];
        // 遍历提取数据
        allLeaveMessageArr.forEach(function (item) {
            // 填写数据项
            var obj = {};
            // 记录留言节点(ps:已写入dom,可以获取)
            item.node = $('#' + item.id);
            // 是否创建节点
            isCreatePage = false;
            // 获取对应配置数据
            pageConfigItem = pageConfigArr.searchArrayObj(item.mark, 'mark', true);
            // 若配置数据不存在，则不创建二级分页
            if (pageConfigItem !== -1) {
                isCreatePage = valueFilter(pageConfigItem.data.isOpenPage, false);
            } else {
                // 获取默认配置数据
                pageConfigItem = pageConfigArr.searchArrayObj('all', 'mark', true);
                if (pageConfigItem !== -1) {
                    isCreatePage = valueFilter(pageConfigItem.data.isOpenPage, false);
                }
            }
            obj.mark = item.mark;
            obj.page = new PluginPagination('page' + item.id);
            obj.node = item.node;
            // 存入分页实例数组
            pageInstanceArr.push(obj);
        });
    }

    // 写入留言数据
    function setLeaveMessageData() {
        // 存储留言字符串
        var result = '';
        // 存储id记录的次数,用于区分留言id 和 分页id
        var idIndex = 0;
        // 获取当前mark
        var nowMark = null;
        // 获取留言数据
        var leaveMessageData = null;
        // 获取提取数据项
        var extractDataItem = null;
        dataSource.forEach(function (messageItem, index) {
            idIndex = 0;
            nowMark = messageItem.mark;
            leaveMessageData = messageItem.data;
            extractDataItem = allLeaveMessageArr[index];
            result += messageHtml.replace(/id=""/g, function (idStr) {
                if (idIndex === 0) {
                    idIndex += 1;
                    return idStr.slice(0, -1) + extractDataItem.id + idStr.slice(-1);
                } else {
                    return idStr.slice(0, -1) + 'page' + extractDataItem.id + idStr.slice(-1);
                }
            }).replace(/<a class="leave-message-item-user-link((?!<\/a>).)+<\/a>/, function (userStr) {
                // 写入留言者
                return userStr.replace(/><\/a>/, 'data-id="' + leaveMessageData.message.user.id + '" data-pid="' + leaveMessageData.message.user.pid + '">' + leaveMessageData.message.user.name + '</a>');
            }).replace(/<p class="leave-message-item-time((?!<\/p>).)+<\/p>/, function (timeStr) {
                // 写入留言的创建时间
                return timeStr.slice(0, -4) + messageDiv.formatTime(new Date(leaveMessageData.message.time), false, 'YYYY-MM-DD hh:mm') + timeStr.slice(-4)
            }).replace(/<div class="leave-message-content((?!<\/div>).)+<\/div>/, function (contentStr) {
                // 写入留言内容
                return contentStr.replace(/><i/, 'data-id="' + leaveMessageData.message.id + '">' + leaveMessageData.message.content.replace(/\n/g, '<br>') + '<i')
            }).replace(/data-mark=""/, function (markStr) {
                return markStr.slice(0, -1) + nowMark + markStr.slice(-1);
                //    <div id="" class="leave-message-reply-page-div"></div>
            }).replace(/<div class="leave-message-reply-page-div"((?!<\/div>)[^>])+><\/div>/, function (pageDivStr) {
                return pageDivStr.slice(0, -6) + pageHTMl + pageDivStr.slice(-6);
            }).replace(/<i class="icon-message"><\/i><\/div>/g, function (pStr) {
                if (parseInt(leaveMessageData.message.user.id) === nowUserId) {
                    return pStr.slice(0, -6) + deleteHtml + pStr.slice(-6);
                } else {
                    return pStr;
                }
            }).replace(/>\s+<(\/)?/g, function (str) {
                // 去除多余的空格换行
                return (str.indexOf('/') > -1) ? '></' : '><';
            })
        });
        return result;
    }

    // 写入回复数据
    function setReplyData(leaveMessageStr) {
        // 获取当前回复区域节点
        var nowReplyNode = null;
        // 获取当前mark
        var nowMark = '';
        // 获取当前回复数据
        var nowReplyData = null;
        // 获取当前页码的配置
        var nowPageConfig = null;
        // 若留言字符串存在（ps:传入新的留言，表示留言重写了）
        if (leaveMessageStr !== undefined) {
            // <div class="leave-message-reply-div" data-mark=""></div>
            leaveMessageStr = leaveMessageStr.replace(/<div class="leave-message-reply-div"((?!<\/div>).)+<\/div>/g, function (areaStr) {
                // 记录回复内容
                var stSave = '';
                // 获取mark
                nowMark = areaStr.match(/data-mark="[^"]*"/g)[0].slice(11, -1);
                // 获取回复数据
                nowReplyData = replyData.searchArrayObj(nowMark, 'mark', true);
                // 若当前回复对应的数据存在
                if (nowReplyData !== -1 && nowReplyData.data.length !== 0) {
                    // 获取当前分页数据
                    nowPageConfig = pageConfigArr.searchArrayObj(nowMark, 'mark', true);
                    // 写入数据
                    nowReplyData.data.forEach(function (replyItem, index) {
                        // 若开启了分页 且 index 超过每页显示条数
                        if (isOpenPage) {
                            if (nowPageConfig !== -1 && index > nowPageConfig.pageShowSize - 1) {
                                return 0;
                            }
                        }
                        // 写入回复内容
                        stSave += replyHtml.replace(/<a class="leave-message-reply-item-answer-link((?!<\/a>).)+<\/a>/, function (answerStr) {
                            // 写入回答者信息
                            return answerStr.replace(/><\/a>/, 'data-id="' + replyItem.answer.id + '" data-pid="' + replyItem.answer.pid + '">' + replyItem.answer.name + '</a>');
                        }).replace(/<a class="leave-message-reply-item-ask-link((?!<\/a>).)+<\/a>/, function (askStr) {
                            // 写入提问者信息
                            return askStr.replace(/><\/a>/, 'data-id="' + replyItem.ask.id + '">' + replyItem.ask.name + '</a>');
                        }).replace(/<p class="leave-message-reply-item-time((?!<\/p>).)+<\/p>/, function (timeStr) {
                            // 写入回答时间
                            return timeStr.slice(0, -4) + messageDiv.formatTime(new Date(replyItem.time), false, 'YYYY-MM-DD hh:mm') + timeStr.slice(-4);
                        }).replace(/<div class="leave-message-reply-content((?!<\/div>).)+<\/div>/, function (contentStr) {
                            // 写入回答内容 (这里的pid是留言id)
                            if (replyItem.pid !== undefined && replyItem.pid !== null) {
                                return contentStr.replace(/><i/, 'data-id="' + replyItem.id + '" data-pid="' + replyItem.pid + '">' + replyItem.content.replace(/\n/g, '<br>') + '<i');
                            } else {
                                return contentStr.replace(/><i/, 'data-id="' + replyItem.id + '">' + replyItem.content.replace(/\n/g, '<br>') + '<i');
                            }
                        }).replace(/<i class="icon-message"><\/i><\/div>/g, function (pStr) {
                            if (parseInt(replyItem.answer.id) === nowUserId) {
                                return pStr.slice(0, -6) + deleteHtml + pStr.slice(-6);
                            } else {
                                return pStr;
                            }
                        }).replace(/>\s+<(\/)?/g, function (str) {
                            // 去除多余的空格换行
                            return (str.indexOf('/') > -1) ? '></' : '><';
                        });
                    });
                }
                return areaStr.slice(0, -6) + stSave + areaStr.slice(-6);
            });
            return leaveMessageStr;
        } else { // 表示留言没有重写，只需修改回复区域
            replyData.forEach(function (replyItem) {
                // 记录回复内容
                var stSave = '';
                // 获取当前标记
                var mark = replyItem.mark;
                // 获取data
                var data = replyItem.data;
                // 获取当前分页配置
                nowPageConfig = (pageConfigArr.searchArrayObj(mark, 'mark', true) !== -1) ? pageConfigArr.searchArrayObj(mark, 'mark', true).data : -1;
                // 获取当前回复的节点
                nowReplyNode = (allLeaveMessageArr.searchArrayObj(mark, 'mark', true) !== -1) ? allLeaveMessageArr.searchArrayObj(mark, 'mark', true).node : -1;
                // 若当前的回复数据存在
                if (data.length !== 0) {
                    // 写入回复数据
                    data.forEach(function (item, index) {
                        // 若开启了分页 且 index 超过每页显示条数 则 跳过
                        if (isOpenPage) {
                            if (nowPageConfig !== -1 && index > nowPageConfig.pageShowSize - 1) {
                                return 0;
                            }
                        }
                        // 写入回复内容
                        stSave += replyHtml.replace(/<a class="leave-message-reply-item-answer-link((?!<\/a>).)+<\/a>/, function (answerStr) {
                            // 写入回答者信息
                            return answerStr.replace(/><\/a>/, 'data-id="' + item.answer.id + '" data-pid="' + item.answer.pid + '">' + item.answer.name + '</a>');
                        }).replace(/<a class="leave-message-reply-item-ask-link((?!<\/a>).)+<\/a>/, function (askStr) {
                            // 写入提问者信息
                            return askStr.replace(/><\/a>/, 'data-id="' + item.ask.id + '">' + item.ask.name + '</a>');
                        }).replace(/<p class="leave-message-reply-item-time((?!<\/p>).)+<\/p>/, function (timeStr) {
                            // 写入回答时间
                            return timeStr.slice(0, -4) + messageDiv.formatTime(new Date(item.time), false, 'YYYY-MM-DD hh:mm') + timeStr.slice(-4);
                        }).replace(/<div class="leave-message-reply-content((?!<\/div>).)+<\/div>/, function (contentStr) {
                            // 写入回答内容 (这里的pid是留言id)
                            if (item.pid !== undefined && item.pid !== null) {
                                return contentStr.replace(/><i/, 'data-id="' + item.id + '" data-pid="' + item.pid + '">' + item.content.replace(/\n/g, '<br>') + '<i');
                            } else {
                                return contentStr.replace(/><i/, 'data-id="' + item.id + '">' + item.content.replace(/\n/g, '<br>') + '<i');
                            }
                        }).replace(/<i class="icon-message"><\/i><\/div>/g, function (pStr) {
                            if (parseInt(item.answer.id) === nowUserId) {
                                return pStr.slice(0, -6) + deleteHtml + pStr.slice(-6);
                            } else {
                                return pStr;
                            }
                        }).replace(/>\s+<(\/)?/g, function (str) {
                            // 去除多余的空格换行
                            return (str.indexOf('/') > -1) ? '></' : '><';
                        });
                    });
                }
                // 若对应回复节点存在
                if (nowReplyNode !== -1) {
                    // 重新插入回复内容
                    nowReplyNode.find('.leave-message-reply-div').eq(0).html(stSave);
                }
            })
        }
    }

    // 点击事件
    function eventOfLeaveMessageMouseUp() {
        messageDiv.mouseup(function (event) {
            var nowNode = $(event.target);
            // 存储删除信息
            var deleteInfo = {};
            // 存储变量
            var askNode, askUserNode, replyNode, replyArea, stSave;
            if (nowNode.hasClass('icon-message')) {
                var user = window.localStorage.getItem('user');
                if (!!user) {
                    // 初始化提问者信息
                    askInfo = {};
                    // 获取问题节点
                    askNode = nowNode.parent();
                    // 若是回复
                    if (nowNode.parents('.leave-message-reply-div').length > 0) {
                        // 获取问题者的节点
                        askUserNode = nowNode.parents('.leave-message-reply-item-content-div').eq(0).prev().find('.leave-message-reply-item-answer-link').eq(0);
                        // 获取回答区域
                        replyNode = nowNode.parents('.leave-message-reply-div').eq(0).next();
                    } else {// 若是留言
                        // 获取问题者的节点
                        askUserNode = nowNode.parents('.leave-message-item-content-div').eq(0).prev().find('.leave-message-item-user-link').eq(0);
                        // 获取回答区域
                        replyNode = nowNode.parents('.leave-message-content-div').eq(0).nextAll('.leave-message-reply-input-div').eq(0);
                    }
                    // 写入问题信息
                    askInfo.pid = askNode.data('pid');
                    askInfo.id = askNode.data('id');
                    askInfo.content = askNode.html().replace(/<i((?!<\/i>).)*<\/i>(<span((?!<\/span>).)*<\/span>)?/g, '');
                    // 提取问题者的信息
                    askInfo.user = {};
                    askInfo.user.id = askUserNode.data('id');
                    askInfo.user.pid = askUserNode.data('pid');
                    askInfo.user.name = askUserNode.text();
                    // 写入回答区域并展示
                    initReplyInput(replyNode, askInfo);
                } else {
                    if (layer) {
                        layer.msg('请先登录');
                    }
                    setTimeout(function () {
                        window.open('/f/login.html?pc=true', '_self');
                    }, 500);
                }
            } else if (nowNode.get(0).tagName.toLowerCase() === 'textarea' && nowNode.hasClass('leave-message-reply-input-answer')) {
                nowNode.css({
                    borderColor: ''
                })
            } else if (nowNode.hasClass('leave-message-reply-input-button-false')) {
                closeReply(nowNode.parents('.leave-message-reply-input-div').eq(0));
            } else if (nowNode.hasClass('leave-message-reply-input-button-true')) {
                // 这里直接调用 askInfo 是因为经过了点击的icon-message点击
                if (askInfo !== null && /[^{} ]/.test(JSON.stringify(askInfo))) {
                    if (trueBtnCallBack) {
                        trueBtnCallBack(askInfo, nowNode);
                    }
                }
            } else if (nowNode.hasClass('leave-message-delete')) { // 点击了删除
                // 获取问题节点
                askNode = nowNode.parent();
                // 写入问题信息
                deleteInfo.id = askNode.data('id');
                if (deleteCallBack) {
                    deleteCallBack(deleteInfo, nowNode);
                }
            } else if (nowNode.hasClass('leave-message-item-reply-show-btn')) {
                // 获取回复区域
                replyArea = nowNode.parent().next().find('.leave-message-reply-div').eq(0);
                // 判断展开收起
                if (nowNode.data('status')) {
                    stSave = nowNode.data('status');
                    if (stSave === 'show') {
                        replyArea.height(0);
                        nowNode.data('status', 'hide').text('查看回复');
                        if (replyArea.data('openPage')) {
                            replyArea.nextAll('.leave-message-reply-page-div').eq(0).hide();
                        }
                    } else {
                        replyArea.height('auto');
                        nowNode.data('status', 'show').text('收起回复');
                        if (replyArea.data('openPage')) {
                            replyArea.nextAll('.leave-message-reply-page-div').eq(0).show();
                        }
                    }
                } else {
                    replyArea.height('auto');
                    nowNode.data('status', 'show').text('收起回复');
                    if (replyArea.data('openPage')) {
                        replyArea.nextAll('.leave-message-reply-page-div').eq(0).show();
                    }
                }
            }
        })
    }

    // 注入初始化回答区域
    function initReplyInput(node, data) {
        // 获取提问者节点
        var askUserNode = node.find('.leave-message-reply-input-ask-name').eq(0);
        // 获取提问者内容节点
        var askContentNode = askUserNode.next();
        // 获取回答框
        var askInputNode = node.find('.leave-message-reply-input-answer').eq(0);
        // 写入数据
        askUserNode.text(data.user.name + ':');
        // 写入提问内容
        askContentNode.text(data.content);
        // 获取焦点
        askInputNode.focus();
        // 回答区域清空
        askInputNode.val('');
        // 展示回答区域
        node.show();
    }

    // 关闭回答区域
    function closeReply(node) {
        node.css({display: 'none'})
    }

    // 写入二级分页插件
    function setPage() {
        // 遍历实例节点
        pageInstanceArr.forEach(function (item) {
            // 当前page实例
            var nowPage = null;
            // 获取当前mark
            var nowMark = item.mark;
            // 当前配置项
            var nowPageConfig = null;
            // 当前的配置数据
            var nowPageConfigData = null;
            // 从改变记录里获取对应的改变项
            var nowConfigChangeItem = pageConfigChangeArr.searchArrayObj(nowMark, 'mark', true);
            // 获取回复区域节点
            var replyArea = item.node.find('.leave-message-reply-div').eq(0);
            // 改变项是否存在 且 该项确实改变了
            if (nowConfigChangeItem !== -1 && nowConfigChangeItem.isChange) {
                // 修正修改状态
                nowConfigChangeItem.isChange = false;
                // 获取当前节点
                nowPage = item.page;
                // 获取当前配置项
                nowPageConfig = pageConfigArr.searchArrayObj(nowMark, 'mark', true);
                // 当分页配置存在
                if (nowPageConfig !== -1) {
                    // 开启分页模式
                    if (nowPageConfig.data.isOpenPage) {
                        // 写入判定
                        replyArea.data('openPage', true).parent().prev().find('button').eq(0).show();
                        if (replyArea.height() > 0) {
                            // 展示分页
                            nowPage.showPage(true);
                        } else {
                            // 展示分页
                            nowPage.showPage(false);
                        }
                        // 获取配置数据
                        nowPageConfigData = nowPageConfig.data;
                        // 能否快速跳转
                        nowPageConfigData.canLift = false;
                        // 调用分页方法
                        nowPage.setMaxPageNumber((!!nowPageConfigData.maxPageNumber) ? nowPageConfigData.maxPageNumber : 5)
                            .setNowPageNumber((!!nowPageConfigData.nowPageNumber) ? nowPageConfigData.nowPageNumber : 1)
                            .openFirstEndModel(false)
                            .openLift(true)
                            .setGetInputNodeCallback(function (inputNode) {
                                var rule = /[^\d]/g;
                                inputNode.bind('input propertychange', function () {
                                    var str = inputNode.val().replace(rule, function () {
                                        return ''
                                    });
                                    if (str.length > 0) {
                                        str = parseInt(str);
                                        if (str < 1) {
                                            str = 1;
                                        } else if (str > nowPageConfigData.maxPageNumber) {
                                            str = nowPageConfigData.maxPageNumber;
                                        }
                                        nowPageConfigData.nowPageNumber = str;
                                        nowPageConfigData.canLift = true;
                                    } else {
                                        nowPageConfigData.canLift = false;
                                    }
                                    inputNode.val(str);
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
                                            nowPageConfigData.nowPageNumber = newNumber;
                                            _this.setNowPageNumber(nowPageConfigData.nowPageNumber)
                                                .createPage();
                                            if (nowPageConfigData.clickCallback) {
                                                nowPageConfigData.clickCallback(nowMark, newNumber);
                                            }
                                        }
                                    } else {// 我是上下页
                                        if (node.data('mark') === 'prev') {
                                            newNumber = oldPageNumber - 1;
                                            if (newNumber > 0) {
                                                nowPageConfigData.nowPageNumber = newNumber;
                                                _this.setNowPageNumber(nowPageConfigData.nowPageNumber)
                                                    .createPage();
                                                if (nowPageConfigData.clickCallback) {
                                                    nowPageConfigData.clickCallback(nowMark, newNumber);
                                                }
                                            }
                                        } else if (node.data('mark') === 'next') {
                                            newNumber = oldPageNumber + 1;
                                            if (newNumber < nowPageConfigData.maxPageNumber + 1) {
                                                nowPageConfigData.nowPageNumber = newNumber;
                                                _this.setNowPageNumber(nowPageConfigData.nowPageNumber)
                                                    .createPage();
                                                if (nowPageConfigData.clickCallback) {
                                                    nowPageConfigData.clickCallback(nowMark, newNumber);
                                                }
                                            }
                                        }
                                    }
                                } else if (labelType === 'button') {
                                    // 按钮
                                    if (nowPageConfigData.canLift) {
                                        _this.setNowPageNumber(nowPageConfigData.nowPageNumber)
                                            .createPage();
                                        if (nowPageConfigData.clickCallback) {
                                            nowPageConfigData.clickCallback(nowMark, nowPageConfigData.nowPageNumber);
                                        }
                                    }
                                }
                            });
                    } else { // 关闭分页
                        // 写入判定
                        replyArea.data('openPage', false).parent().prev().find('button').eq(0).hide();
                        nowPage.showPage(false);
                    }
                } else {
                    // 写入判定
                    replyArea.data('openPage', false).parent().prev().find('button').eq(0).hide();
                    nowPage.showPage(false);
                }
            } else {
                // 写入判定
                replyArea.data('openPage', false).parent().prev().find('button').eq(0).hide();
            }
        });
    }

    /*=== 功能函数调用 ===*/
    eventOfLeaveMessageMouseUp();

    /*=== 方法 ===*/
    // 生成留言
    _this.createLeaveMessage = function () {
        // 写入数据
        createLeaveMessage();
    };
    // 设置数据
    _this.setData = function (array) {
        try {
            if (!(array instanceof Array)) {
                throw new Error('设置的数据源格式错误，需为array')
            }
            array.forEach(function (item) {
                if (typeof item !== "object") {
                    throw new Error('数据array的每个子项类型需为object');
                } else if (item.mark === undefined) {
                    throw new Error('数据array的子项找不到mark属性');
                }
            });
            // 获取数据
            dataSource = JSON.parse(JSON.stringify(array));
            // 清空记录数据
            allLeaveMessageArr = [];
            // 提取必要信息
            extractLeaveMessage(dataSource);
            // 改变了数据
            changeInfo.leaveMessage.isChange = true;
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setData方法错误:' + err)
        }
    };
    // 设置回复数据
    _this.setReplyData = function (array) {
        try {
            if (!(array instanceof Array)) {
                throw new Error('设置的数据源格式错误，需为array');
            }
            array.forEach(function (item) {
                if (typeof item !== "object") {
                    throw new Error('数据array的每个子项类型需为object');
                } else if (item.mark === undefined) {
                    throw new Error('数据array的子项找不到mark属性');
                }
            });
            // 当前mark
            var nowMark = null;
            // 中间存储
            var obj = null;
            // 对应的数据项
            var getDataItem = null;
            // 获取数据
            array.forEach(function (item) {
                nowMark = item.mark;
                getDataItem = replyData.searchArrayObj(nowMark, 'mark', true);
                if (getDataItem !== -1) {
                    getDataItem.mark = item.mark;
                    getDataItem.data = JSON.parse(JSON.stringify(item.data));
                } else {
                    obj = {};
                    obj.mark = item.mark;
                    obj.data = JSON.parse(JSON.stringify(item.data));
                    replyData.push(obj);
                }
            });
            changeInfo.reply.isChange = true;
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setReplyData方法错误:' + err);
        }
    };
    // 设置分页配置
    _this.setPageConfig = function (array) {
        try {
            if (!(array instanceof Array)) {
                throw new Error('设置的数据源格式错误，需为array');
            }
            array.forEach(function (item) {
                if (typeof item !== "object") {
                    throw new Error('数据array的每个子项类型需为object');
                } else if (item.mark === undefined) {
                    throw new Error('数据array的子项找不到mark属性');
                }
            });
            var nowMark = null;
            // 对应的数据项
            var getDataItem = null;
            // 获取改变记录
            var getChangeItem = null;
            // 获取数据
            array.forEach(function (item) {
                // 中间存储
                var obj = null;
                nowMark = item.mark;
                if (nowMark === 'all') {
                    allLeaveMessageArr.forEach(function (messageItem) {
                        // 获取原对应项
                        getDataItem = pageConfigArr.searchArrayObj(messageItem.mark, 'mark', true);
                        getChangeItem = pageConfigChangeArr.searchArrayObj(messageItem.mark, 'mark', true);
                        // 若对应项存在修改对应项
                        if (getDataItem !== -1) {
                            getDataItem.data = JSON.parse(JSON.stringify(item.data));
                            getDataItem.data.clickCallback = (!!item.data.clickCallback) ? item.data.clickCallback : function () {
                                return 0;
                            };
                            if (getChangeItem !== -1) {
                                getChangeItem.isChange = true;
                            }
                        } else { // 不存在则添加对应项
                            // 获取分页的配置
                            obj = {};
                            obj.mark = messageItem.mark;
                            obj.data = JSON.parse(JSON.stringify(item.data));
                            obj.data.clickCallback = (!!item.data.clickCallback) ? item.data.clickCallback : function () {
                                return 0;
                            };
                            pageConfigArr.push(obj);
                            // 获取变化记录
                            obj = {};
                            obj.mark = messageItem.mark;
                            obj.isChange = true;
                            pageConfigChangeArr.push(obj);
                        }
                    })
                } else {
                    getDataItem = pageConfigArr.searchArrayObj(nowMark, 'mark', true);
                    getChangeItem = pageConfigChangeArr.searchArrayObj(nowMark, 'mark', true);
                    // 若有对应项
                    if (getDataItem !== -1) {
                        getDataItem.data = JSON.parse(JSON.stringify(item.data));
                        getDataItem.data.clickCallback = (!!item.data.clickCallback) ? item.data.clickCallback : function () {
                            return 0;
                        };
                        if (getChangeItem !== -1) {
                            getChangeItem.isChange = true;
                        }
                    } else {
                        // 获取分页的配置
                        obj = {};
                        obj.mark = item.mark;
                        obj.data = JSON.parse(JSON.stringify(item.data));
                        obj.data.clickCallback = (!!item.data.clickCallback) ? item.data.clickCallback : function () {
                            return 0;
                        };
                        pageConfigArr.push(obj);
                        // 用于记录是否变化
                        obj = {};
                        obj.mark = item.mark;
                        obj.isChange = true;
                        pageConfigChangeArr.push(obj);
                    }
                }
            });
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setPageConfig方法错误:' + err)
        }
    };
    // 设置确定按钮点击的回调函数
    _this.setTrueBtnCallBack = function (callback) {
        try {
            if (typeof callback !== "function") {
                throw new Error('参数callback类型错误，需为function');
            }
            if (callback !== undefined) {
                trueBtnCallBack = callback;
            }
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setTrueBtnCallBack方法错误:' + err);
        }
    };
    // 关闭回复框
    _this.closeReplyInput = function (node) {
        closeReply(node)
    };
    // 设置当前用户id
    _this.setNowUserId = function (userId) {
        try {
            if (typeof userId !== "number" && typeof userId !== 'string') {
                throw new Error('参数userId错误！类型需为"number"或"string"');
            }
            // 存储当前用户id
            nowUserId = userId;
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setNowUserId方法错误:' + err)
        }
    };
    // 设置删除回调函数
    _this.setDeleteCallBack = function (callback) {
        try {
            if (typeof callback !== "function") {
                throw new Error('参数callback类型错误，需为function');
            }
            if (callback !== undefined) {
                deleteCallBack = callback;
            }
            return _this;
        } catch (err) {
            console.error('LeaveMessage 插件 (id = ' + mainAreaId + ') 的setDeleteCallBack方法错误:' + err);
        }
    }
}