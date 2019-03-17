// 用来存储设置表格的变量
var messageInformListData = [];
var currentPageOfMessageInformList = 1;
var searchSizeOfMessageInformList = 10;
var messageInformListTypeId = 0;
var pageInOrTabMessageInformList = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
// 搜索条件对象
var selectConditionOfMessageInformList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};
var $_readId = [];
var $_readAllId = [];
var $_deleteAllId = [];

// 消除消息显示
messageInfoResetMessageNumber();

// 修改消息显示
function messageInfoResetMessageNumber() {
    var messageNode = $('#head-personal').find('span[name="messageNumber"]').eq(0);
    // 修改新信息的查阅状态
    if (window.localStorage !== undefined) {
        saveToLocalStorage('check', '0');
        messageNode.data('number', 0).text(0).hide();
    }
}

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".message-inform-splitpage >div").on("click", function () {
        // console.log($('.message-inform-splitpage .focus')[0].innerText);
        if ($('.message-inform-splitpage .focus')[0].innerText != currentPageOfMessageInformList) {
            currentPageOfMessageInformList = $('.message-inform-splitpage .focus')[0].innerText;
            pageInOrTabMessageInformList = 0;
            getMessageInformList();
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            currentPageOfMessageInformList = $('.message-inform-splitpage .focus')[0].innerText;
            pageInOrTabMessageInformList = 0;
            getMessageInformList();
        }
    });
}

/*** 复原currentPage ***/
function resetCurrentPageMessageInform() {
    $('.message-inform-splitpage >div').data('currentpage', 1);
    currentPageOfMessageInformList = 1;
    $('.message-inform-splitpage >div').find('li[data-page="' + currentPageOfMessageInformList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('message-inform-list')
    messageInformListSearch();
    handleEventInMessageInfo();

});

// 初始化dom
function init_dom() {
    $('.message-inform-list .personal-center-search-list ul').html(' <li class="active" data-isread="0"><span>全部</span><span></span></li> <li data-isread="1"><span>已读</span><span></span></li> <li data-isread="2"><span>未读</span><span></span></li> ');
    $('.message-inform-list .personal-center-search-time').children().eq(0).html('时间：');
    $('.message-inform-list .personal-center-search-list ul li').click(function () {
        $('.message-inform-list').find('ul li').removeClass('active');
        $(this).addClass('active');
        messageInformListTypeId = $(this).attr('data-isread');
        pageInOrTabMessageInformList = 1;
        currentPageOfMessageInformList = 1;
        $_readId = [];
        $_readAllId = [];
        $_deleteAllId = [];
        getMessageInformList();
    })
}

function messageInformListSearch() {
    $('.message-inform-list').find('.searchByTime').click(function () {
        selectConditionOfMessageInformList.searchVal = $('.message-inform-list').find('.searchByNameContent').val();
        selectConditionOfMessageInformList.startTime = $('.message-inform-list').find('.search-star-time').val();
        selectConditionOfMessageInformList.endTime = $('.message-inform-list').find('.search-end-time').val();

        pageInOrTabMessageInformList = 1;
        currentPageOfMessageInformList = 1;

        getMessageInformList();
    });
    $('.message-inform-list').find('.searchByName').click(function () {
        selectConditionOfMessageInformList.searchVal = $('.message-inform-list').find('.searchByNameContent').val();
        selectConditionOfMessageInformList.startTime = $('.message-inform-list').find('.search-star-time').val();
        selectConditionOfMessageInformList.endTime = $('.message-inform-list').find('.search-end-time').val();

        pageInOrTabMessageInformList = 1;
        currentPageOfMessageInformList = 1;

        getMessageInformList();
    });
    $('.message-inform-list').find('.searchByNameContent').keydown(function (e) {
        if (e.keyCode == 13) {
            selectConditionOfMessageInformList.searchVal = $('.message-inform-list').find('.searchByNameContent').val();
            selectConditionOfMessageInformList.startTime = $('.message-inform-list').find('.search-star-time').val();
            selectConditionOfMessageInformList.endTime = $('.message-inform-list').find('.search-end-time').val();

            pageInOrTabMessageInformList = 1;
            currentPageOfMessageInformList = 1;

            getMessageInformList();
        }
    });
}

// 处理事件操作
function handleEventInMessageInfo() {
    // 打开/关闭消息
    $(".message-inform-list-table").on("click", ".search-result-item-title", function () {
        if ($(this).siblings().hasClass("show")) {
            $(this).siblings().removeClass("show")
            $(this).parent().attr("data-isread", true)
        } else {
            $(this).siblings().addClass("show")
            var had = judgmentIsHadInArr($(this).parent().data('id'), $_readId);
            if (!had && !$(this).parent().data('isread')) {
                $_readId.push($(this).parent().data('id'));
                $(this).children(".message-status").html("已读");
                $(this).children(".message-status").css("color", '#333');
                setMessagetoRead($_readId)
                $_readId = []
            }
        }
    });
    $(".message-inform-list-table").on("click", ".delete-button", function (e) {
        e.stopPropagation();
        deleteMessage([$(this).parents(".search-result-tr").data('id')])
    });

    // 多选
    $(".message-inform-list-table").on("click", ".search-result-th .checkbox", function () {
        $_readAllId = [];
        $_deleteAllId = [];
        if ($(this).is(":checked")) {
            $(".message-inform-list-table .search-result-tr .checkbox").prop("checked", true);
            $(".message-inform-list-table .search-result-tr .checkbox").each(function (index, ele) {
                $_deleteAllId.push($(ele).parents(".search-result-tr").data("id"));
                if (!$(ele).parents(".search-result-tr").data("isread")) {
                    $_readAllId.push($(ele).parents(".search-result-tr").data("id"));
                }
            });
        } else {
            $(".message-inform-list-table .search-result-tr .checkbox").prop("checked", false);
        }
    });
    $(".message-inform-list-table").on("click", ".search-result-tr .checkbox", function (e) {
        e.stopPropagation();
        if ($(this).is(":checked")) {
            $_deleteAllId.push($(this).parents(".search-result-tr").data("id"));
            if (!$(this).parents(".search-result-tr").data("isread")) {
                $_readAllId.push($(this).parents(".search-result-tr").data("id"));
            }
            $(".message-inform-list-table .search-result-tr .checkbox").each(function (index, ele) {
                if (!$(ele).is(":checked")) {
                    $(".message-inform-list-table .search-result-th .checkbox").prop("checked", false);
                    return false;
                } else if (ele.checked && index === $(".message-inform-list-table .search-result-tr .checkbox").length - 1) {
                    $(".message-inform-list-table .search-result-th .checkbox").prop("checked", true);
                }
            });
        } else {
            judgmentIsHadInArr($(this).parents(".search-result-tr").data("id"), $_deleteAllId, true);
            if (!$(this).parents(".search-result-tr").data("isread")) {
                judgmentIsHadInArr($(this).parents(".search-result-tr").data("id"), $_readAllId, true);
            }
            $(".message-inform-list-table .search-result-th .checkbox").prop("checked", false);
        }
    });
    // 全部已读
    $(".read-all-button").off().on("click", function () {
        if ($_readAllId.length) {
            setMessagetoRead($_readAllId);
            $(".message-inform-list-table .search-result-tr .checkbox").each(function (index, ele) {
                if (judgmentIsHadInArr($(ele).parents(".search-result-tr").data("id"), $_readAllId)) {
                    $(ele).next().html("已读");
                    $(ele).next().css('color', '#333')
                }
            });
        }
    });
    // 全部删除
    $(".delete-all-button").off().on("click", function () {
        if ($_deleteAllId.length) {
            deleteMessage($_deleteAllId);
            $(".message-inform-list-table .search-result-th .checkbox").prop("checked", false);
        }
    });
}

// 判断数组中是否存在某个值
function judgmentIsHadInArr(id, arr, isDelete) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === id) {
            if (isDelete) {
                arr.splice(i, 1);
            }
            return true;
        }
    }
    return false;
}

// 获取消息通知列表
function getMessageInformList() {
    // 清空所有列表数据
    $(".message-inform-list-table .search-result-tr").remove()
    var json = {
        "pager": {
            "current": currentPageOfMessageInformList,
            "size": searchSizeOfMessageInformList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (parseInt(messageInformListTypeId) !== 0) {
        json.isRead = parseInt(messageInformListTypeId) === 1 ? true : false;
    }
    if (selectConditionOfMessageInformList.searchVal) {
        json.title = selectConditionOfMessageInformList.searchVal;
    }
    if (selectConditionOfMessageInformList.startTime) {
        json.createdAtStart = selectConditionOfMessageInformList.startTime;
    }
    if (selectConditionOfMessageInformList.endTime) {
        json.createdAt = selectConditionOfMessageInformList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/userMessage/pc/query?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            if (totalRecord == 0) {
                $(".message-inform-list-table").append(
                    '<div class="search-result-tr" style="text-align: center;height: 200px;line-height: 200px;">暂无通知</div>');
                $('.message-inform-splitpage').css("display", "none");
                $('.message-inform-area .message-inform-total').css("display", "none");
            } else {
                $('.message-inform-splitpage').css("display", "block");
                $('.message-inform-area .message-inform-total').css("display", "block");
            }
            // console.log(res);
            if (pageInOrTabMessageInformList === 0) {
                $('.message-inform-splitpage >div').Paging({
                    pagesize: searchSizeOfMessageInformList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.message-inform-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabMessageInformList === 1) {
                $('.message-inform-splitpage >div').Paging({
                    pagesize: searchSizeOfMessageInformList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.message-inform-splitpage >div').find("div:eq(0)").remove();
            }
            $('.message-inform-total').html("共" + totalRecord + "条");

            setMessageInformList(list);
        }
    });
}

// 设置消息通知列表
function setMessageInformList(list) {
    for (var k = 0; k < list.length; k++) {
        $(".message-inform-list-table").append(
            '<div class="search-result-tr">' +
            '<div class="search-result-item-title">' +
            '<input type="checkbox" class="inline-block checkbox">' +
            '<span class="inline-block status-span message-status"></span>' +
            '<span class="inline-block text-overflow title-span message-title"></span>' +
            '<span class="inline-block time-span message-time"></span>' +
            '<span class="inline-block do-span message-do"><span class="delete-button">删除</span></span>' +
            '</div>' +
            '<p class="search-result-item-content"></p>' +
            '</div>');
    }
    var messageCards = $(".search-result-tr");
    for (var i = 0; i < list.length; i++) {
        $(messageCards[i]).attr("data-id", list[i].id); // id
        $(messageCards[i]).attr("data-isread", list[i].is_read); // 是否已读
        $(messageCards[i]).find(".message-status").html(list[i].is_read ? "已读" : "未读"); // 是否已读
        if (!!list[i].is_read) {
            $(messageCards[i]).find(".message-status").css('color', '#333')
        } else {
            $(messageCards[i]).find(".message-status").css('color', 'red')
        }
        $(messageCards[i]).find(".message-title").html(list[i].title);  // 标题
        $(messageCards[i]).find(".message-title").attr("title", list[i].title);  // 标题
        $(messageCards[i]).find(".message-time").html($(this).formatTime(new Date(list[i].created_at)).split(" ")[0]);              // 时间
        $(messageCards[i]).find(".search-result-item-content").html(list[i].content);              // 内容
    }
}

// 设置是否已读
function setMessagetoRead(arr) {
    new NewAjax({
        type: "POST",
        url: "/f/userMessage/update_is_read?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(arr),
        success: function (res) {
            // console.log(res);
        }
    });
}

// 删除消息
function deleteMessage(arr) {
    new NewAjax({
        type: "POST",
        url: "/f/userMessage/batch_delete?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(arr),
        success: function (res) {
            pageInOrTabMessageInformList = 1;
            currentPageOfMessageInformList = 1;
            if (res.status === 200) {
                layer.msg("删除成功！");
                getMessageInformList();
            }
        }
    });
}
