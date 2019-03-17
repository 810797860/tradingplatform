$(function () {
    // 存储类型多选框数据
    // 存储全选的id数组
    var selectAllIdArr = [0, 202035, 202066];

    // 存储行业数据
    var aIndustry = [];
    var aSubIndustry = [{id: 0, title: '不限'}];
    // 存储状态数据
    var searchType = [
        {
            name: '行业',
            type: 'industry',
            active: 202151,
            data: aIndustry
        },
        {
            name: '子行业',
            type: 'subIndustry',
            active: 0,
            data: aSubIndustry,
            linkMethod: function () {
                var _this = this,
                    industryId = _this.selectData.industry,
                    data = [];
                _this.subIndustry.forEach(function (item) {
                    if (item.pid === industryId) {
                        data.push(item);
                    }
                });
                return (data.length > 0) ? [{id: 0, title: '不限'}].concat(data) : null;
            }
        }
    ];

    // index页面的全部数据
    var matureCaseCheckRecordsData = {
        name: '全部数据',
        type: 'Object',
        show: true,
        data: matureCaseCheckRecords
    };

    var $_industryFieldId = industryFieldTypes[0].id;
    var $_applicationIndustryId = applicationIndustrys[0].id;

    var $_picturesId = "";
    var $_videoId = "";
    var $_attachmentsId = "";
    var $_pictureData = [];
    var $_fileData = [];
    var isLoadVideo = false;
    var isLoadImage = false;
    var editor = null;

    // 附件table数据
    var table = new Table('upload-table');
    var tableAttachment = new Table("upload-attachment-table");
    var baseStyleArr = [];
    // 决定数据顺序
    var orderArr = ['title', 'size', 'id'];
    // 判断是否有视频
    var hasVideo = false;
    // 添加属性按钮
    var $_addAttrBtn = $('.case-add-configuration-head-add').eq(0);
    // 单独一个的配置
    var attrHtmlItem = '<div class="case-add-configuration-item">' +
        '<label class="case-add-configuration-item-label">属性名:</label>' +
        '<input class="case-add-configuration-item-attr-name" type="text">' +
        '<label class="case-add-configuration-item-label">属性值:</label>' +
        '<input class="case-add-configuration-item-attr-value" type="text">' +
        '<span class="case-add-configuration-item-attr-delete">删除</span>' +
        '</div>';
    // 属性配置容器
    var attrHtmlContent = $('.case-add-configuration-content').eq(0);

    // 手机正则匹配
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/

    set_IMPORTANTOPERATION(true);
    initDomInPublishCase();
    handleEventInPublishCase();

    // 初始化dom结构
    function initDomInPublishCase() {
        extractIndustryData(industry);
        // 初始化地区
        $('#addressBox').distpicker("destroy");
        $('#addressBox').distpicker({
            province: '广东省',
            city: '佛山市',
            district: '南海区'
        });

        //初始化富文本插件
        editor = CKEDITOR.replace('ckedit', {
            resize_enabled: false,
            autoUpdateElement: true,
            height: 300,
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
        });

        if (!!matureCaseCheckRecordsData.data.applicationIndustry) {
            searchType[0].active = JSON.parse(matureCaseCheckRecordsData.data.applicationIndustry).id;
            // searchType[0].active = projectDemandDatas[0].data.industry_id;
        }

        // if (!!matureCaseCheckRecordsData.data.sub_application_industry) {
        //     var optionLength = $('.publish-case .select-area .type-item').eq(1).find('.option').length;
        //     var option = $('.publish-case .select-area .type-item').eq(1).find('.option');
        //     option.removeClass('active');
        //     for (var i = 0; i < optionLength; i++) {
        //         if (JSON.parse(matureCaseCheckRecordsData.data.sub_application_industry).id === option.eq(i).attr('data-id')) {
        //             option.eq(i).addClass('active');
        //         }
        //     }
        // }
        // 初始化类型筛选
        // for (var i = 0; i < searchType.length; i++) {
        //     var selectRow = document.createElement("div");
        //     $(selectRow).addClass("select-row");
        //     $(selectRow).append('<span class="inline-block row-label"><i class="icon-star"></i><span class="inline-block label-title">' + searchType[i].name + ':</span></span>')
        //     var optionArea = document.createElement("div");
        //     $(optionArea).addClass("inline-block option-area option-area-hidden")
        //     for (var j = 0; j < searchType[i].data.length; j++) {
        //         if (j === 0) {
        //             $(optionArea).append('<span class="inline-block option option-active" data-id="' + searchType[i].data[j].id + '">' + searchType[i].data[j].title + '</span>')
        //         } else {
        //             $(optionArea).append('<span class="inline-block option" data-id="' + searchType[i].data[j].id + '">' + searchType[i].data[j].title + '</span>')
        //         }
        //     }
        //     $(selectRow).append(optionArea)
        //     if (searchType[i].data.length > 8) {
        //         $(selectRow).append('<span class="inline-block open-more">更多<i class="icon-open-arrow"></i></span>')
        //     }
        //     $(".publish-case .select-area").append(selectRow)
        //
        // }
        //
        // if (!!matureCaseCheckRecordsData.data.skilledLabel) {
        //     var skills = $('.select-area .select-row').eq(0).find(".option");
        //     skills.removeClass("option-active");
        //     for (var k=0 ;k<skills.length; k++) {
        //         if(JSON.parse(matureCaseCheckRecordsData.data.skilledLabel).id === skills[k].attributes['data-id']['value']){
        //             skills.eq(k).addClass('option-active')
        //         }
        //     }
        // }

        // 编辑时回显配置属性
        if (!!matureCaseCheckRecordsData.data.product_parameters) {
            var productParameters = JSON.parse(matureCaseCheckRecordsData.data.product_parameters);
            // console.log(productParameters);
            for (var x = 0; x < productParameters.length; x++) {
                attrHtmlContent.append(attrHtmlItem);
                $('.case-add-configuration-item').eq(x).find('.case-add-configuration-item-attr-name').val(productParameters[x].key);
                $('.case-add-configuration-item').eq(x).find('.case-add-configuration-item-attr-value').val(productParameters[x].value);
            }
        }


        if (matureCaseCheckRecordsData.data.pictureCover) {
            // 回显图片
            var pictures = JSON.parse(matureCaseCheckRecordsData.data.pictureCover)
            for (var h = 0; h < pictures.length; h++) {
                $(".case-video-area").css("display", 'block');
                if (!$_picturesId) {
                    $_picturesId = "" + pictures[h].id;
                } else {
                    $_picturesId += ',' + pictures[h].id;
                }
            }
            setTableData(table, $_pictureData, true, pictures)

            // 回显视频
            if (matureCaseCheckRecordsData.data.videoCover) {
                var video = JSON.parse(matureCaseCheckRecordsData.data.videoCover);
                $_videoId = video.id
                $(".video-play-area").css("display", "inline-block");
                $(".upload-video").attr('src', "/adjuncts/file_range_download/" + video.id);
                if (!!video.id) {
                    $('.upload-video-input-area').hide();
                } else {
                    $('.upload-video-input-area').show();
                }
            }
        }

        if (matureCaseCheckRecordsData.data.attachment) {
            // 回显附件
            var attachment = JSON.parse(matureCaseCheckRecordsData.data.attachment);
            for (var t = 0; t < attachment.length; t++) {
                if (!$_attachmentsId) {
                    $_attachmentsId = "" + attachment[t].id;
                } else {
                    $_attachmentsId += ',' + attachment[t].id;
                }
            }
            setTableData(tableAttachment, $_fileData, false, attachment)
        }


        initTypeSearch();

        // 回显子行业
        if (!!matureCaseCheckRecordsData.data.sub_application_industry) {
            var optionLength = $('.publish-case .select-area .type-item').eq(1).find('.option').length;
            var option = $('.publish-case .select-area .type-item').eq(1).find('.option');
            option.removeClass('active');
            // console.log(JSON.parse(matureCaseCheckRecordsData.data.sub_application_industry).id);
            // console.log(option);
            // console.log(optionLength);
            for (var i = 0; i < optionLength; i++) {
                if (JSON.parse(matureCaseCheckRecordsData.data.sub_application_industry).id === option.eq(i).attr('data-id')) {
                    option.eq(i).addClass('active');
                }
            }
        }

        // 提取行业数据
        function extractIndustryData(data) {
            data.forEach(function (item) {
                if (item.pid === 202052) {
                    aIndustry.push(item);
                } else {
                    aSubIndustry.push(item);
                }
            })
        }


        /***
         * 处理类型多选框
         */
        function initTypeSearch() {
            // 引入搜索框
            var typeSearch = new TypeSearch();

            $('.select-type-search').css('width', '100%');
            $('.search-area').remove();
            $('.select-type-search .select-area').css('box-shadow', 'none');
            $('#type-ul .type').css('text-align', 'right');

            // 初始化头部类型多选框
            typeSearch.setData(searchType, false);
            // 设置点击回调
            // typeSearch.setClickCallback(function (node, parentNode) {
            //     var optionId = node.data('id');
            //     var typeName = parentNode.attr('type');
            //     if (searchData[typeName] instanceof Array) {
            //         var index = $.inArray(optionId, searchData[typeName]);
            //         // 若是不限的id
            //         if ($.inArray(optionId, selectAllIdArr) > -1) {
            //             searchData[typeName] = [optionId]
            //         } else {
            //             // 若存在不限的id，则情况数组
            //             if ($.inArray(searchData[typeName][0], selectAllIdArr) > -1) {
            //                 searchData[typeName] = []
            //             }
            //             if (index > -1) {
            //                 searchData[typeName].splice(index, 1)
            //             } else {
            //                 searchData[typeName].push(optionId)
            //             }
            //         }
            //     } else {
            //         // 若是省份/城市点击
            //         if (typeName === 'province') {
            //             searchData.city = 0
            //             searchData.area = 0
            //         } else if (typeName === 'city') {
            //             searchData.area = 0
            //         }
            //         if (typeName === 'province' || typeName === 'city' || typeName === 'area') {
            //             optionId = node.text()
            //         }
            //         if (typeName === 'industry' && searchData.subIndustry) {
            //             searchData.subIndustry = undefined;
            //             delete searchData.subIndustry;
            //         }
            //         // 记录点击的分类数据
            //         searchData[typeName] = optionId
            //     }
            //     // 初始化分页的记录
            //     resetCurrentPage();
            //     // 重新请求数据
            //     // getDemandList();
            //     // console.log('clickCallback', optionId)
            // });
            // // 设置搜索按钮的click回调
            // typeSearch.setSearchBtnClickCallback(function (inputNode) {
            //     if (inputNode.val() === searchData.input) {
            //         return 0
            //     }
            //     // 赋值
            //     searchData.input = inputNode.val();
            //     // 初始化分页的记录
            //     resetCurrentPage();
            //     // 重新请求数据
            //     getDemandList();
            //     // console.log('btnCallback', searchData.input)
            // });
            // // 设置搜索input的keyDown回调
            // typeSearch.setSearchInputKeyDownCallback(function (event, inputNode) {
            //     if (event.keyCode === 13 && inputNode.val() !== searchData.input) {
            //         searchData.input = inputNode.val();
            //         // 初始化分页的记录
            //         resetCurrentPage();
            //         // 重新请求数据
            //         getDemandList();
            //         // console.log('inputCallback', searchData.input)
            //     }
            // })
        }
    }

// 处理事件
    function handleEventInPublishCase() {

        // 点击添加属性配置
        $_addAttrBtn.click(function () {
            attrHtmlContent.append(attrHtmlItem);
            // attrHtmlContent.append('<span class="case-add-configuration-head-add">设定好公交公司</span>')
        });

        // 点击删除添加的属性配置
        $(document).on('click', '.case-add-configuration-item-attr-delete', function () {
            $(this).parent().remove();
        });


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
        // $(".select-row .option-area .option").off().on("click", function () {
        //     $(this).siblings(".option").removeClass("option-active");
        //     $(this).addClass("option-active");
        //     if ($(this).parent().siblings(".row-label").children(".label-title").html() === (searchType[0].name + ':')) {
        //         $_industryFieldId = $(this).data('id');
        //     } else if ($(this).parent().siblings(".row-label").children(".label-title").html() === (searchType[1].name + ':')) {
        //         $_applicationIndustryId = $(this).data('id');
        //     }
        // });
        // 成果名称失去焦点
        $(".case-name").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).siblings(".error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 成果名称获得焦点
        $(".case-name").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
        });

        // 成果联系人获得焦点
        $(".case-concat").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).siblings(".error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 成果联系人获得焦点
        $(".case-concat").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
        });

        // 成果联系电话获得焦点
        $(".case-concat-phone").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).siblings(".error-info").children(".error-info-content").text('联系电话不能为空！').css("display", 'block');
            } else {
                if (!myreg.test($(this).val())) {
                    $(this).addClass("error-border");
                    $(this).siblings(".error-info").children(".error-info-content").text('手机格式输入错误，请重新输入').css("display", 'block');
                }
            }
        });
        // 成果联系电话获得焦点
        $(".case-concat-phone").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
        });

        // 成果详情失去焦点
        editor.on('blur', function () {
            if (!editor.getData()) {
                $(".case-desc-area .error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 成果详情获得焦点
        editor.on('focus', function () {
            $(".case-desc-area .error-info").children(".error-info-content").css("display", 'none');
        });
        // 回报周期失去焦点
        // $(".reward-cycle-input").on("blur", function () {
        //     if (!$(this).val()) {
        //         $(this).addClass("error-border");
        //         $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
        //     }
        // });
        // // 回报周期获得焦点
        // $(".reward-cycle-input").on("focus", function () {
        //     $(this).removeClass("error-border");
        //     $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
        // });

        // 价格失去焦点
        $(".case-money-area .start-money").on("blur", function () {
            console.log('a');
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 价格获得焦点
        $(".case-money-area .start-money").on("focus", function () {
            console.log('b');
            $(this).removeClass("error-border");
            $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
        });

        /****** 价格 输入值改变时 ******/
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

        // 地区判空
        $(".case-address-area .address-select").on("change", function () {
            if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
                if (!$("#provinceName").val()) {
                    $(".case-address-area .address-select").addClass("error-border");
                } else if (!$("#cityName").val()) {
                    $(".case-address-area #cityName").addClass("error-border");
                    $(".case-address-area #districtName").addClass("error-border");
                } else if (!$("#districtName").val()) {
                    $(".case-address-area #districtName").addClass("error-border");
                }
            } else if ($("#provinceName").val()) {
                $(".case-address-area .address-select").removeClass("error-border");
            } else if ($("#cityName").val()) {
                $(".case-address-area .address-select").removeClass("error-border");
            } else if ($("#districtName").val()) {
                $(".case-address-area .address-select").removeClass("error-border");
            }
        });

        // 上传图片
        $(".case-picture-area .input-file").off().on("change", function () {
            $(".upload-file-info").removeClass("upload-file-info-error");
            var files = $(this).get(0).files;
            var formData = new FormData();
            var isOtherFile = false;
            var length = 0;
            var imgFile = []
            for (var f = 0; f < files.length; f++) {
                if (files[f].type.split("/")[0] === "image") {
                    imgFile.push(files[f])
                } else {
                    isOtherFile = true
                }
            }
            if (isOtherFile) {
                layer.open({
                    title: '温馨提示',
                    content: '只能上传图片'
                })
            }
            $(this)[0].value = "";
            if (imgFile.length > 5 || ($_picturesId.split(",").length + imgFile.length) > 5) {
                layer.open({
                    title: '温馨提示',
                    content: '最多只能上传5张图片'
                })
            }
            if (!$_picturesId) {
                length = imgFile.length >= 5 ? 5 : imgFile.length;
            } else {
                if ($_picturesId.split(",").length >= 5) {
                    return
                } else {
                    length = ($_picturesId.split(",").length + imgFile.length) >= 5 ? 5 - $_picturesId.split(",").length : imgFile.length
                }
            }
            for (var p = 0; p < length; p++) {
                formData.append('files', imgFile[p]);
            }
            if (length) {
                isLoadImage = true
                uploadPicture(formData);
                $(".case-video-area").css("display", 'block');
            }
        });

        // 上传视频
        $(".upload-video-input").off().on("change", function () {
            var files = $(this).get(0).files;
            if (files[0].type.split("/")[0] === "video") {
                if (files[0].type === "video/mp4" || files[0].type === "video/webm" || files[0].type === "video/ogg") {
                    console.log('上传视频');
                    // var videoSrc = getFileURL(files[0])
                    // if (videoSrc) {
                    //     $(".video-play-area").css("display", "inline-block");
                    //     $(".upload-video").attr('src', videoSrc);
                    // }
                    $_videoId = "";
                    $(".upload-video").attr('src', "'");
                    $(".video-play-area").css("display", "none");
                    $(".video-area .video-process-area").css("display", "inline-block");
                    $('.upload-video-input-area').hide();
                    isLoadVideo = true
                    uploadVideo(files[0])
                } else {
                    layer.open({
                        title: '温馨提示',
                        content: '请上传mp4、webm、ogg格式的视频'
                    })
                }
            } else {
                layer.open({
                    title: '温馨提示',
                    content: '请选择视频'
                })
            }
            $(this)[0].value = "";
        });

        // 删除视频
        $(".video-play-delete").off().on("click", function () {
            $_videoId = "";
            $(".upload-video").attr('src', "'");
            $(".video-play-area").css("display", "none");
            $('.upload-video-input-area').show();
        });

        // 上传附件
        $(".case-attachment-area .input-file").off().on("change", function () {
            uploadFile($(this).get(0).files);
        });

        // 点击提交 发布案例
        $(".submit-area .submit").off().on("click", function () {
            publishCase();
        });
    }

// 设置附件表格数据
    function setTableData(table, fileData, isPicture, list) {
        // 提取数据
        list.forEach(function (item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function (key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '文件名'
                            styleItem.width = 360
                            break
                        case 'size':
                            styleItem.name = '大小'
                            styleItem.width = 180
                            break
                        case 'id':
                            styleItem.name = '操作'
                            break
                        default:
                            styleItem.name = key
                            break
                    }
                    styleItem.align = 'left'
                    baseStyleArr.push(styleItem)
                })
            }
            obj.title = item.title + '.' + item.prefix
            obj.size = item.size
            obj.id = [item.id, item.prefix]
            fileData.push(obj)
        })
        table.setTableData(fileData)
        table.setBaseStyle(baseStyleArr)
        table.setColOrder(orderArr)
        table.setOpenCheckBox(false, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
            if (type === 'title') {
                return (label === 'td') ? '<span class="file-title">' + content + '</span>' : content
            } else if (type === 'size') {
                return (label === 'td') ? content + 'KB' : content
            } else if (type === 'id') {
                var span = '<span class="see-file" data-id="' + content[0] + '" data-prefix="' + content[1] + '"><i class="icon-search"></i>查看</span>' +
                    '<span class="delete-file" data-id="' + content[0] + '" data-prefix="' + content[1] + '"><i class="icon-false"></i>删除</span>'
                return (label === 'td') ? span : content
            }
        }).setClickCallback(function (node) {
            if (node.hasClass("delete-file") || node.parent().hasClass("delete-file")) {
                var id = "";
                if (node.parent().hasClass("delete-file")) {
                    id = node.parent().data('id');
                } else {
                    id = node.data('id');
                }
                var attachments = isPicture ? $_picturesId.split(",") : $_attachmentsId.split(",");
                var index = attachments.searchArrayObj("" + id);
                if (index > -1) {
                    attachments.splice(index, 1);
                    if (isPicture) {
                        $_picturesId = ""
                        if (!attachments.length) {
                            $(".case-video-area").css("display", 'none');
                            $_videoId = ""
                        }
                        for (var i = 0; i < attachments.length; i++) {
                            if (!$_picturesId) {
                                $_picturesId = '' + attachments[i];
                            } else {
                                $_picturesId += ',' + attachments[i];
                            }
                        }
                    } else {
                        $_attachmentsId = ""
                        for (var i = 0; i < attachments.length; i++) {
                            if (!$_attachmentsId) {
                                $_attachmentsId = '' + attachments[i];
                            } else {
                                $_attachmentsId += ',' + attachments[i];
                            }
                        }
                    }
                    fileData.splice(index, 1);
                }
                table.setTableData(fileData);
                table.createTable();
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
                    layer.open({
                        title: false,
                        type: 1,
                        area: '500px',
                        offset: '200px;',
                        content: img,
                        move: '.layui-layer-content',
                        shadeClose: true
                    });
                } else {
                    window.open("/adjuncts/file_download/" + id)
                }
            }
        });
        table.createTable()
    }

    // 上传图片
    function uploadPicture(files) {
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: files,
            async: true,
            processData: false,
            contentType: false,
            success: function (res) {
                if (res.status === 200) {
                    isLoadImage = false
                    var list = res.data.data_list
                    for (var i = 0; i < list.length; i++) {
                        if (!$_picturesId) {
                            $_picturesId = '' + list[i].id
                        } else {
                            $_picturesId += ',' + list[i].id
                        }
                    }
                    setTableData(table, $_pictureData, true, list)
                }
            }
        });
    }

    // 上传视频
    function uploadVideo(file) {
        var formData = new FormData();
        formData.append("files", file);
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: formData,
            async: true,
            processData: false,
            contentType: false,
            xhr: function () {
                var myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { // check if upload property exists
                    myXhr.upload.addEventListener('progress', function (e) {
                        var loaded = e.loaded;                  //已经上传大小情况
                        var total = e.total;                      //附件总大小
                        var percent = Math.floor(100 * loaded / total) + "%";     //已经上传的百分比
                        // console.log("已经上传了：" + percent);
                        if (percent !== "100%") {
                            $(".video-area .video-process-area").html("上传中...已完成" + percent);
                        } else {
                            $(".video-area .video-process-area").css("display", "none");
                        }
                    }, false); // for handling the progress of the upload
                }
                return myXhr;
            },
            success: function (res) {
                if (res.status === 200) {
                    var list = res.data.data_list;
                    $_videoId = list[0].id;
                    isLoadVideo = false;
                    if ($_videoId) {
                        $(".video-play-area").css("display", "inline-block");
                        $(".upload-video").attr('src', "/adjuncts/file_range_download/" + $_videoId);
                    }
                }
            }
        })
    }

// 获取上传的视频url
    function getFileURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }

// 上传附件
    function uploadFile(files) {
        if (files.length == 0) return; //如果文件为空
        var formData = new FormData();
        for (var i = 0; i < files.length; i++) {
            formData.append('files', files[i]);
        }
        new NewAjax({
            type: "POST",
            url: "/adjuncts/file_upload",
            data: formData,
            async: true,
            processData: false,
            contentType: false,
            success: function (res) {
                var list = res.data.data_list
                for (var i = 0; i < list.length; i++) {
                    if (!$_attachmentsId) {
                        $_attachmentsId = "" + list[i].id
                    } else {
                        $_attachmentsId += ',' + list[i].id
                    }
                }
                // 设置附件表格数据
                setTableData(tableAttachment, $_fileData, false, list);
            }
        });
    }

// 发布案例前的验证
    function beforePublishCase() {
        var addAttrItem = $('.case-add-configuration-item');
        var res = true
        if (!$(".case-name").val()) {
            res = false
            $(".case-name").addClass("error-border");
            $(".case-name").siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        if (!$(".case-concat").val()) {
            res = false
            $(".case-concat").addClass("error-border");
            $(".case-concat").siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        // if (!$(".case-concat-phone").val()) {
        //     res = false
        //     $(".case-concat-phone").addClass("error-border");
        //     $(".case-concat-phone").siblings(".error-info").children(".error-info-content").css("display", 'block');
        // }

        if (!$(".case-concat-phone").val()) {
            $(".case-concat-phone").addClass("error-border");
            $(".case-concat-phone").siblings(".error-info").children(".error-info-content").text('联系电话不能为空！').css("display", 'block');
        } else {
            if (!myreg.test($(".case-concat-phone").val())) {
                $(".case-concat-phone").addClass("error-border");
                $(".case-concat-phone").siblings(".error-info").children(".error-info-content").text('手机格式输入错误，请重新输入').css("display", 'block');
            }
        }

        if (!editor.getData()) {
            res = false
            $(".case-desc").parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        if (!$_picturesId) {
            res = false
            $(".upload-file-info").addClass("upload-file-info-error");
        }
        if (!$(".input-money").val()) {
            res = false
            $(".input-money").addClass("error-border");
            $(".input-money").parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
            res = false;
            if (!$("#provinceName").val()) {
                $(".case-address-area .address-select").addClass("error-border");
            } else if (!$("#cityName").val()) {
                $(".case-address-area #cityName").addClass("error-border");
                $(".case-address-area #districtName").addClass("error-border");
            } else if (!$("#districtName").val()) {
                $(".case-address-area #districtName").addClass("error-border");
            }
        }
        if (isLoadImage) {
            layer.msg('正在上传图片，请稍后再提交');
            return false
        }
        if (isLoadVideo) {
            layer.msg('正在上传视频，请稍后再提交');
            return false
        }
        if (!res) {
            layer.open({
                title: '温馨提示',
                content: '请完善信息'
            })
        }
        // console.log(addAttrItem);
        for (var i = 0; i < addAttrItem.length; i++) {
            if (!!addAttrItem.eq(i).find('.case-add-configuration-item-attr-name').val()) {
                if (addAttrItem.eq(i).find('.case-add-configuration-item-attr-value').val() === '') {
                    layer.msg("属性配置不能只填入一个属性");
                    return false;
                }
            }
            if (!!addAttrItem.eq(i).find('.case-add-configuration-item-attr-value').val()) {
                if (addAttrItem.eq(i).find('.case-add-configuration-item-attr-name').val() === '') {
                    layer.msg("属性配置不能只填入一个属性");
                    return false;
                }
            }
        }
        // debugger;
        return res
    }

// 发布案例
    function publishCase() {
        var addAttrItem = $('.case-add-configuration-item');
        if (beforePublishCase()) {
            var json = {
                title: $(".case-name").val(),
                contact: $(".case-concat").val(),
                contactPhone: $(".case-concat-phone").val(),
                programIntroduction: editor.getData(),
                skilledLabel: $_industryFieldId,
                // applicationIndustry: $_applicationIndustryId,
                address: $("#provinceName").val() + $("#cityName").val() + $("#districtName").val(),
                provinceName: $("#provinceName").val(),
                cityName: $("#cityName").val(),
                districtName: $("#districtName").val(),
                caseMoney: $(".start-money").val() ? $(".start-money").val() : 0,
                returnCycle: $(".reward-cycle-input").val(),
                pictureCover: $_picturesId,   // 最多五个
                videoCover: $_videoId,    // 最多一个
                attachment: $_attachmentsId,
                applicationIndustry: $('.select-area .type-item').eq(0).find('.active').attr('data-id'),
                productParameters: []
            };
            for (var i = 0; i < addAttrItem.length; i++) {
                var obj = {
                    key: addAttrItem.eq(i).find('.case-add-configuration-item-attr-name').val(),
                    value: addAttrItem.eq(i).find('.case-add-configuration-item-attr-value').val()
                }
                json.productParameters.push(obj)
            }
            if ($('.select-area .type-item').eq(1).find('.active').attr('data-id') !== 0) {
                json.subApplicationIndustry = $('.select-area .type-item').eq(1).find('.active').attr('data-id');
            }
            if (!!matureCaseCheckRecordsData.data.id) {
                json.id = matureCaseCheckRecordsData.data.id
            }
            json.productParameters = JSON.stringify(json.productParameters);
            // debugger
            new NewAjax({
                type: "POST",
                url: "/f/matureCaseCheckRecords/pc/create_update?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        // 发布成功，消除离开判定
                        set_IMPORTANTOPERATION(false);
                        window.location.href = "/f/personal_center.html?pc=true#menu=publishCaseList"
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: '内部信息出错'
                        })
                    }
                }
            })
        }
    }

    function upperCase(obj) {//用户只能输入正数与小数
        if (isNaN(obj.value) && !/^$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 3));
        }
    }
});

function upperCase(obj) {//用户只能输入正数与小数
    if (isNaN(obj.value) && !/^$/.test(obj.value)) {
        obj.value = "";
    }
    if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
        obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 3));
    }
}
