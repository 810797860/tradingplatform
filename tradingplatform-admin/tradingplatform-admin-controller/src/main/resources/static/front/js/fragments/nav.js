$(function () {
    hoverAddNav();

    function hoverAddNav() {
        var href = window.location.href.split('/')[4];
        href = href ? href.split(".")[0] : href;
        if (href && href !== 'index') {
            $('.fastPassage').mouseenter(function () {
                $('.fastPassage .homeExpresswayDiv').css('display', 'block');
            });
            $('.fastPassage').mouseleave(function () {
                $('.fastPassage .homeExpresswayDiv').css('display', 'none');
            })
        }
    }

    /*// 匹配nav的路由
    $('.pageItem').each(function () {
        var $this = $(this).children(".pageLink");
        var str = $this[0].href;
        var subStr = new RegExp('index');
        var detailHref = str.replace(subStr,"detail").split("?")[0];

        if (String($this[0].href) === String(window.location.href)
            || detailHref === arrayToUrl(getArrayBySplitUrl(String(window.location.href))).split("?")[0]
            || (String(window.location.href) === String($this[0].href).replace("/home",""))) {
            $(this).addClass('pageItem__select');
        }
    });*/

    var aLocationPath = String(window.location.pathname).split("/");
    var locationPathName = aLocationPath[aLocationPath.length - 1].split("_")[0];
    $('.pageItem').each(function () {
        var $this = $(this).find(".pageLink");
        var aPathItem = String($this[0].href).split("?")[0].split("/");
        var pathName = aPathItem[aPathItem.length - 1].split("_")[0];
        if (!locationPathName) {
            $('.pageItem').eq(0).addClass('pageItem__select');
        } else if (pathName.indexOf(locationPathName) > -1) {
            $(this).addClass('pageItem__select');
        }
    });

    // 修改行业图片和样式
    resetIconAndStyleOfIndustry($('.navDiv .homeExpresswayDiv').eq(0));

    /*// 用'/'切割url，返回数组
    function getArrayBySplitUrl (href) {
        var arr = href.split("/");
        arr.splice(getNumberInUrl(href), 1);
        return arr;
    }

    // 返回url用'/'切割后的 纯数字 的index，例如：'http://localhost:8089/f/demand/36/detail.html?pc=true'，返回 36 的 index
    function getNumberInUrl (href) {
        var arr = href.split("/");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] && !isNaN(parseInt(arr[i]))) {
                return i;
            }
        }
        return 0;
    }

    // 将数组转换成url
    function arrayToUrl (arr) {
        var res = "";
        for (var i = 0; i < arr.length; i++) {
            if (i === arr.length - 1) {
                res += arr[i];
            } else {
                res += arr[i] + '/';
            }
        }
        return res;
    }*/
});