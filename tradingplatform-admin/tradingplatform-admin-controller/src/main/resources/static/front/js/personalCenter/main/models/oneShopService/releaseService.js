// 存储类型多选框数据
var searchType = [
    {
        name: '类别',
        type: 'category',
        show: true,
        data: category
    }
];
var $_categoryId = category[0].id;
var $_picturesId = "";
var $_pictureData = [];
var isLoadImage = false;
/**** 附件table数据 ****/
var table = new Table('upload-table');
var baseStyleArr = [];
// 决定数据顺序
var orderArr = ['title', 'size', 'id'];
var desc = null;
var apply = null;
var flow = null;
var successCase = null;
var editorOption = {
    resize_enabled: false,
    autoUpdateElement: true,
    height: 250,
    toolbarGroups: [
        {name: 'document', groups: []},
        {name: 'clipboard', groups: []},
        {name: 'editing', groups: []},
        {name: 'forms', groups: ['forms']},
        '/',
        {name: 'basicstyles', groups: ['basicstyles', 'cleanup']},
        {name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph']},
        {name: 'links', groups: ['links']},
        {name: 'insert', groups: ['insert']},
        '/',
        {name: 'styles', groups: ['styles']},
        {name: 'colors', groups: ['colors']},
        {name: 'tools', groups: ['tools']},
        {name: 'others', groups: ['others']},
        {name: 'about', groups: ['about']}
    ]
};
var serviceId = undefined;
$(function () {
    $('.menu-children .publish-service-item').css('color', '#337ab7');
    initDomInPublishService();
    // 修改iframe高度
    // resetIframeHeight(window, 'personalShowArea');
    handleEventInPublishService();
});

// 初始化dom结构
function initDomInPublishService() {
    /**** 初始化时间控件 ****/
    $('.service-validity').datetimepicker({
        format: 'YYYY-MM-DD',//显示格式
        locale: moment.locale('zh-cn'),
        minDate: $(this).formatTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)).split(" ")[0]
    });
    // 初始化地区
    $('#addressBox').distpicker("destroy");
    $('#addressBox').distpicker({
        province: '广东省',
        city: '佛山市',
        district: '南海区'
    });
    // 初始化类型筛选
    for (var i = 0; i < searchType.length; i++) {
        var selectRow = document.createElement("div");
        $(selectRow).addClass("select-row");
        $(selectRow).append('<span class="inline-block row-label"><i class="icon-star"></i><span class="inline-block label-title">' + searchType[i].name + ':</span></span>');
        var optionArea = document.createElement("div");
        $(optionArea).addClass("inline-block option-area option-area-hidden");
        for (var j = 0; j < searchType[i].data.length; j++) {
            if (j === 0) {
                $(optionArea).append('<span class="inline-block option option-active" data-id="' + searchType[i].data[j].id + '"><p class="type-item-text">' + searchType[i].data[j].title + '</p></span>');
            } else {
                $(optionArea).append('<span class="inline-block option" data-id="' + searchType[i].data[j].id + '"><p class="type-item-text">' + searchType[i].data[j].title + '</p></span>');
            }
        }
        $(selectRow).append(optionArea);
        if (searchType[i].data.length > 6) {
            $(selectRow).append('<span class="inline-block open-more">更多<i class="icon-open-arrow"></i></span>');
        }
        $(".publish-service .select-area").append(selectRow);
    }
    ;
    //初始化富文本插件
    desc = CKEDITOR.replace('desc', editorOption);
    apply = CKEDITOR.replace('apply', editorOption);
    flow = CKEDITOR.replace('flow', editorOption);
    successCase = CKEDITOR.replace('case', editorOption);
    if (!!service) {
        initPageData(service)
    }
}

//初始化页面数据
function initPageData(data) {
    if (!!data.category) {
        var skills = $('.select-area .select-row').eq(0).find(".option");
        skills.removeClass("option-active");
        for (var k = 0; k < skills.length; k++) {
            if (JSON.parse(data.category).title == skills.eq(k).text()) {
                $_categoryId = skills.eq(k).data('id');
                skills.eq(k).addClass('option-active')
            }
        }
    }
    if (!!data.detail) {
        desc.setData(data.detail);
    }
    if (!!data.conditions) {
        apply.setData(data.conditions);
    }
    if (!!data.workflow) {
        flow.setData(data.workflow);
    }
    if (!!data.successcase) {
        successCase.setData(data.successcase);
    }
    serviceId = data.id
    // 初始化编辑页面的图片列表
    if (!!data.icon) {
        var fileList = [];
        console.log(data);
        if (!(JSON.parse(data.icon) instanceof Array)) {
            fileList.push(JSON.parse(data.icon));
        } else {
            fileList = JSON.parse(data.icon);
        }
        for (var i = 0; i < fileList.length; i++) {
            if (i == 0) {
                $_picturesId += fileList[i].id
            } else {
                $_picturesId += ',' + fileList[i].id;
            }
        }
        setPictureData(table, $_pictureData, fileList);
    }
}

// 处理事件
function handleEventInPublishService() {
    // 处理更多、收起操作
    $(".select-row .open-more").click(function () {
        var optionArea = $(this).siblings(".option-area");
        if ($(optionArea).hasClass("option-area-hidden")) {
            $(optionArea).removeClass("option-area-hidden");
            $(this).html('收起<i class=\"icon-close-arrow\"></i>');
        } else {
            $(optionArea).addClass("option-area-hidden");
            $(this).html('更多<i class=\"icon-open-arrow\"></i>');
        }
    });
    // 处理点击类型筛选
    $(".select-row").off().on("click", ".option", function () {
        if ($(this).parents(".select-row").index() === 0) {
            $_categoryId = $(this).data('id');
        }
        $(this).siblings(".option").removeClass("option-active");
        $(this).addClass("option-active");
    });
    // 服务名称失去焦点、获得焦点
    $(".service-name").on("blur", function () {
        if (!$(this).val()) {
            $(this).addClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
    }).on("focus", function () {
        $(this).removeClass("error-border");
        $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
    });
    // 所属服务商失去焦点、获得焦点
    $(".service-provider").on("blur", function () {
        if (!$(this).val()) {
            $(this).addClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
    }).on("focus", function () {
        $(this).removeClass("error-border");
        $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
    });
    // 地区判空
    $(".service-address-area .address-select").on("change", function () {
        if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
            if (!$("#provinceName").val()) {
                $(".service-address-area .address-select").addClass("error-border");
            } else if (!$("#cityName").val()) {
                $(".service-address-area #cityName").addClass("error-border");
                $(".service-address-area #districtName").addClass("error-border");
            } else if (!$("#districtName").val()) {
                $(".service-address-area #districtName").addClass("error-border");
            }
        } else if ($("#provinceName").val()) {
            $(".service-address-area .address-select").removeClass("error-border");
        } else if ($("#cityName").val()) {
            $(".service-address-area .address-select").removeClass("error-border");
        } else if ($("#districtName").val()) {
            $(".service-address-area .address-select").removeClass("error-border");
        }
    });
    // 上传图片
    $(".input-file").off().on("change", function () {
        $(".upload-file-info").removeClass("upload-file-info-error");
        var files = $(this).get(0).files;
        var formData = new FormData();
        var imgFile = null;
        $_pictureData = [];
        if (files[0].type.split("/")[0] === "image") {
            imgFile = files[0];
        } else {
            parent.layer.open({
                title: '温馨提示',
                content: '只能上传图片'
            })
        }
        $(this)[0].value = "";
        formData.append('files', imgFile);
        uploadPicture(formData);
    });
    // 点击提交发布需求
    $(".submit-area .submit").off().on("click", function () {
        publishService();
    });
    $(".start-money").off().on("input propertychange", function () {
        var startMoney = $(".start-money").val();
        if (startMoney && parseInt(startMoney) === 0) {
            $(".zero").addClass("blue");
        } else {
            $(".zero").removeClass("blue");
        }
    });
    $(".zero").off().on("click", function () {
        $(".start-money").val(0);
        $(".zero").addClass("blue");
    });
    $('.insert-module').on("click", function (e) {
        e.stopPropagation();
    });
    //富文本失去焦点和获得焦点
    desc.on('blur', function () {
        if (!desc.getData()) {
            $(".service-desc-area .error-info").children(".error-info-content").css("display", 'block');
        }
    });
    desc.on('focus', function () {
        $(".service-desc-area .error-info").children(".error-info-content").css("display", 'none');
    });
    flow.on('blur', function () {
        if (!flow.getData()) {
            $(".service-flow-area .error-info").children(".error-info-content").css("display", 'block');
        }
    });
    flow.on('focus', function () {
        $(".service-flow-area .error-info").children(".error-info-content").css("display", 'none');
    });
    apply.on('blur', function () {
        if (!apply.getData()) {
            $(".service-apply-area .error-info").children(".error-info-content").css("display", 'block');
        }
    });
    apply.on('focus', function () {
        $(".service-apply-area .error-info").children(".error-info-content").css("display", 'none');
    });
    successCase.on('blur', function () {
        if (!successCase.getData()) {
            $(".service-case-area .error-info").children(".error-info-content").css("display", 'block');
        }
    });
    successCase.on('focus', function () {
        $(".service-case-area .error-info").children(".error-info-content").css("display", 'none');
    });
}

function beforePublishService() {
    var res = true;
    $(".service-name").focus().blur();
    // $(".service-provider").focus().blur();
    if (!$(".service-name").val()) {
        res = false;
    }
    if (!desc.getData()) {
        res = false;
        $(".service-desc-area .error-info").children(".error-info-content").css("display", 'block');
    }
    if (!flow.getData()) {
        res = false;
        $(".service-flow-area .error-info").children(".error-info-content").css("display", 'block');
    }
    if (!apply.getData()) {
        res = false;
        $(".service-apply-area .error-info").children(".error-info-content").css("display", 'block');
    }
    if (!successCase.getData()) {
        res = false;
        $(".service-case-area .error-info").children(".error-info-content").css("display", 'block');
    }
    if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
        res = false;
        if (!$("#provinceName").val()) {
            $(".service-address-area .address-select").addClass("error-border");
        } else if (!$("#cityName").val()) {
            $(".service-address-area #cityName").addClass("error-border");
            $(".service-address-area #districtName").addClass("error-border");
        } else if (!$("#districtName").val()) {
            $(".service-address-area #districtName").addClass("error-border");
        }
    }
    console.log($_picturesId);
    if (!$_picturesId) {
        res = false;
        $(".upload-file-info").addClass("upload-file-info-error");
    }
    if (!res) {
        parent.layer.open({
            title: '温馨提示',
            content: '请完善信息'
        });
    }
    return res;
}

function publishService() {
    if (beforePublishService()) {
        var json = {
            category: $_categoryId,
            title: $(".service-name").val(),
            serviceProvider: $(".service-provider").val(),
            icon: $_picturesId,
            price: $(".start-money").val() ? $(".start-money").val() : 0,
            area: $("#provinceName").val() + $("#cityName").val() + $("#districtName").val(),
            provinceName: $("#provinceName").val(),
            cityName: $("#cityName").val(),
            districtName: $("#districtName").val(),
            detail: desc.getData(),
            conditions: apply.getData(),
            workflow: flow.getData(),
            successcase: successCase.getData()
        };
        if (!!serviceId) {
            json.id = serviceId
        }
        new NewAjax({
            type: "POST",
            url: "/f/serviceMessage/pc/create_update?pc=true",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status === 200) {
                    /*parent.layer.open({
                     title: '发布成功',
                     content: "恭喜你发布服务成功"
                     });*/
                    var href = "/f/personal_center.html?pc=true#menu=deliveredService";
                    window.location.href = href;
                    var menuItemNode = parent.$(".menu-children-item");
                    menuItemNode.removeClass("menu-active");
                    for (var i = 0; i < menuItemNode.length; i++) {
                        if (menuItemNode.eq(i).data("href") === href) {
                            menuItemNode.eq(i).addClass("menu-active");
                        }
                    }
                } else {
                    parent.layer.open({
                        title: '温馨提示',
                        content: res.message
                    });
                }
            }
        });
    }
}

// 上传图片
function uploadPicture(files) {
    new NewAjax({
        type: "POST",
        url: "/adjuncts/file_upload?pc=true",
        data: files,
        async: true,
        processData: false,
        contentType: false,
        success: function (res) {
            if (res.status === 200) {
                isLoadImage = false;
                var list = res.data.data_list;
                for (var i = 0; i < list.length; i++) {
                    $_picturesId = list[i].id;
                }
                setPictureData(table, $_pictureData, list);
            }
        }
    });
}

// 设置附件表格数据
function setPictureData(table, fileData, list) {
    var arr = []
    if (!Array.isArray(list)) {
        list = arr.push(list);
    } else {
        arr = list;
    }
    // 提取数据
    arr.forEach(function (item) {
        var obj = {};
        if (baseStyleArr.length === 0) {
            Object.keys(item).forEach(function (key) {
                var styleItem = {};
                styleItem.type = key;
                switch (key) {
                    case 'title':
                        styleItem.name = '文件名';
                        styleItem.width = 360;
                        break;
                    case 'size':
                        styleItem.name = '大小';
                        styleItem.width = 180;
                        break;
                    case 'id':
                        styleItem.name = '操作';
                        break;
                    default:
                        styleItem.name = key;
                        break;
                }
                styleItem.align = 'left';
                baseStyleArr.push(styleItem);
            })
        }
        obj.title = item.title + '.' + item.prefix;
        obj.size = item.size;
        obj.id = [item.id, item.prefix];
        fileData.push(obj);
    });
    table.setTableData(fileData);
    table.setBaseStyle(baseStyleArr);
    table.setColOrder(orderArr);
    table.setOpenCheckBox(false, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        if (type === 'title') {
            return (label === 'td') ? '<span class="file-title">' + content + '</span>' : content;
        } else if (type === 'size') {
            return (label === 'td') ? content + 'KB' : content;
        } else if (type === 'id') {
            var span = '<span class="see-file" data-id="' + content[0] + '" data-prefix="' + content[1] + '"><i class="icon-search"></i>查看</span>' +
                '<span class="delete-file" data-id="' + content[0] + '" data-prefix="' + content[1] + '"><i class="icon-false"></i>删除</span>';
            return (label === 'td') ? span : content;
        }
    }).setClickCallback(function (node) {
        if (node.hasClass("delete-file") || node.parent().hasClass("delete-file")) {
            $_picturesId = null;
            $_pictureData = [];
            table.setTableData($_pictureData);
            table.createTable();
            // 修改iframe高度
            // resetIframeHeight(window, 'personalShowArea');
        } else if (node.hasClass("see-file") || node.parent().hasClass("see-file")) {
            var prefix = "";
            var id = "";
            if (node.parent().hasClass("see-file")) {
                prefix = node.parent().data('prefix');
                id = node.parent().data('id');
            } else {
                prefix = node.data('prefix');
                id = node.data('id');
            }
            if (prefix === "png" || prefix === "jpg" || prefix === "jpeg" || prefix === "gif" || prefix === "bmp") {
                var src = $(this).getAvatar(id);
                var img = '<img src="' + src + '" style="width: 500px; height: auto;"/>';
                parent.layer.open({
                    title: false,
                    type: 1,
                    area: '500px',
                    offset: '200px;',
                    content: img,
                    move: '.layui-layer-content',
                    shadeClose: true
                });
            }
        }
    });
    table.createTable();
    // 修改iframe高度
    // resetIframeHeight(window, 'personalShowArea');
}