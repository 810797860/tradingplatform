var $_platformAdInfo = platformAdInfo;

$(function () {
    init_data();
    handleEvent();
});

function init_data() {
    $(".platform-ad-report-title").html($_platformAdInfo.title);
    $(".platform-ad-report-title").attr('title', $_platformAdInfo.title);
    $(".report-author").html($_platformAdInfo.author);
    $(".release-time").html($(this).formatTime(new Date($_platformAdInfo.created_at)).split(":")[0] + ":" + $(this).formatTime(new Date($_platformAdInfo.created_at)).split(":")[1]);
    $(".read-number").html($_platformAdInfo.click_rate);
    $(".platform-ad-report-content-div").html($_platformAdInfo.detail);

    getRecommendList();
}

/*** 处理事件 ***/
function handleEvent() {
    /*** 跳转到详情页 开始 ***/
    $(".consultation-card").click(function () {
        var id = $(this).data('id');
        // window.location.href = '/f/' + id + '/platform_ad_detail.html';
        window.open('/f/' + id + '/platform_ad_detail.html');
    });
    /*** 跳转到详情页 结束 ***/
}

/*** 获取右栏推荐行业资讯列表 ***/
function getRecommendList() {
    var json = {
        "pager": {//分页信息
            "current": 1,   //当前页数0
            "size": 4        //每页条数
        }
    };
    new NewAjax({
        type: "POST",
        url: "/f/industryInformation/get_recommend_industry_information",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var list = res.data.data_list;
            setRecommendList(list);
        }
    });
}

/*** 设置右栏推荐行业资讯列表 ***/
function setRecommendList(list) {
    var consultationCards = $(".consultation-card");
    for (var i = 0; i < list.length; i++) {
        $(consultationCards[i]).attr("data-id", list[i].id); // id
        if (list[i].type) {
            var type = JSON.parse(list[i].type);
            $(consultationCards[i]).find(".title").html(type.title);
        }
        $(consultationCards[i]).find(".desc").html(list[i].title);  // 描述
        $(consultationCards[i]).find(".desc").attr('title', list[i].title);  // 描述
        $(consultationCards[i]).find(".time").html($(this).formatTime(new Date(list[i].created_at)).split(" ")[0]);  // 时间
        $(consultationCards[i]).find(".look-num").html(list[i].click_rate);  // 点击量
    }
}