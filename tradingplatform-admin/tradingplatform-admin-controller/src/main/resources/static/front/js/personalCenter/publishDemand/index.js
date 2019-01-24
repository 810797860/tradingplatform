// 存储类型多选框数据
// var searchType = [
//     {
//         name: '类型',
//         type: 'type',
//         show: true,
//         data: industryFieldTypes
//     }
// ];



$(function () {
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
            active: 202051,
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
    // 传ID之后，所拿到的数据
    var projectDemandDatas = [
        {
            name: '全部数据',
            type: 'Object',
            show: true,
            data: projectDemandData
        }
    ];

    var $_industryFieldId = industryFieldTypes[0].id;
    var $_attachmentsId = "";
    var $_fileData = [];

    /**** 附件table数据 ****/
    var table = new Table('upload-table');
    var baseStyleArr = [];
    // 决定数据顺序
    var orderArr = ['title','size','id'];
    var publishDemandDeline;

    set_IMPORTANTOPERATION(true);
    initDomInPublishDemand();
    handleEventInPublishDemand();

    // 初始化dom结构
    function initDomInPublishDemand () {

        extractIndustryData(industry);
        /**** 初始化时间控件 ****/
        $('.demand-validity').datetimepicker({
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
        // for (var i = 0; i < searchType.length; i++) {
        //     var selectRow = document.createElement("div");
        //     $(selectRow).addClass("select-row");
        //     $(selectRow).append('<span class="inline-block row-label"><i class="icon-star"></i><span class="inline-block label-title">' + searchType[i].name + ':</span></span>');
        //     var optionArea = document.createElement("div");
        //     $(optionArea).addClass("inline-block option-area option-area-hidden");
        //     for (var j = 0; j < searchType[i].data.length; j++) {
        //         if (j === 0) {
        //             $(optionArea).append('<span class="inline-block option option-active" data-id="' + searchType[i].data[j].id + '">' + searchType[i].data[j].title + '</span>');
        //         } else {
        //             $(optionArea).append('<span class="inline-block option" data-id="' + searchType[i].data[j].id + '">' + searchType[i].data[j].title + '</span>');
        //         }
        //     }
        //     $(selectRow).append(optionArea);
        //     if (searchType[i].data.length > 8) {
        //         $(selectRow).append('<span class="inline-block open-more">更多<i class="icon-open-arrow"></i></span>');
        //     }
        //     $(".publish-demand .select-area").append(selectRow);
        // }
        //
        // if (!!projectDemandDatas[0].data.industry_field) {
        //     var arr = [];
        //     arr = $('.select-area .option');
        //     for (var k = 0; k < arr.length; k++) {
        //         $('.select-area .option').eq(k).removeClass('option-active');
        //         if(JSON.parse(projectDemandDatas[0].data.industry_field).id === arr[k].attributes['data-id']['value']){
        //             $('.select-area .option').eq(k).addClass('option-active');
        //         }
        //     }
        // }

        // 回填行业信息
        if (!!projectDemandDatas[0].data.industry_id) {
            searchType[0].active = JSON.parse(projectDemandDatas[0].data.industry_id).id;
            // searchType[0].active = projectDemandDatas[0].data.industry_id;
        }
        console.log(searchType);

        // 初始化回填截止报名日期
        if (!!projectDemandDatas[0].data.deadline) {
            $(".demand-validity").val($(this).formatTime(new Date(projectDemandDatas[0].data.deadline)).split(" ")[0]);
        } else{
            $(".demand-validity").val($(this).formatTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)).split(" ")[0]);
        }
        // 初始化回填地区
        if (!!projectDemandDatas[0].data.address) {
            var provinceName =  $('#provinceName').attr('data-province');
            $("#provinceName").val(provinceName);
            var cityName =  $('#cityName').attr('data-city');
            $("#cityName").val(cityName);
            var districtName =  $('#districtName').attr('data-districName');
            $("#districtName").val(districtName);
        }

        // 初始化回填附件
        if (projectDemandDatas[0].data.attachment) {
            var attachment = JSON.parse(projectDemandDatas[0].data.attachment);
            for (var h = 0; h < attachment.length; h++) {
                if (!$_attachmentsId) {
                    $_attachmentsId = "" + attachment[h].id;
                } else {
                    $_attachmentsId += ',' + attachment[h].id;
                }
            }
            // 设置附件表格数据
            setTableData(attachment);
        }
        console.log(projectDemandDatas[0])
        if(!!projectDemandDatas[0].data.validdate) {
            if (projectDemandDatas[0].data.validdate != 7 && projectDemandDatas[0].data.validdate != 30 && projectDemandDatas[0].data.validdate != 90) {
                $('.input-validity-area-input').val(projectDemandDatas[0].data.validdate);
                $('.input-validity-area-span').removeClass('default-selection');
            } else if (projectDemandDatas[0].data.validdate == 7) {
                $('.input-validity-area-span').removeClass('default-selection');
                $('.input-validity-area-span').eq(0).addClass('default-selection');
                $('.input-validity-area-input').val(projectDemandDatas[0].data.validdate);
            } else if (projectDemandDatas[0].data.validdate == 30) {
                $('.input-validity-area-span').removeClass('default-selection');
                $('.input-validity-area-span').eq(1).addClass('default-selection');
                $('.input-validity-area-input').val(projectDemandDatas[0].data.validdate);
            } else {
                $('.input-validity-area-span').removeClass('default-selection');
                $('.input-validity-area-span').eq(2).addClass('default-selection');
                $('.input-validity-area-input').val(projectDemandDatas[0].data.validdate);
            }
        } else {
            $('.input-validity-area-input').val(projectDemandDatas[0].data.validdate);
            $('.input-validity-area-span').removeClass('default-selection');
        }

        initTypeSearch();

        // 回显子行业
        if (!!projectDemandDatas[0].data.sub_industry_id) {
            var optionLength = $('.publish-demand .select-area .type-item').eq(1).find('.option').length;
            var option = $('.publish-demand .select-area .type-item').eq(1).find('.option');
            option.removeClass('active');
            for (var i = 0; i < optionLength; i++) {
                if (JSON.parse(projectDemandDatas[0].data.sub_industry_id).id === option.eq(i).attr('data-id')) {
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
    function handleEventInPublishDemand () {
        // 点击有效期
        $('.input-validity-area-span').off().on('click',function () {
            $('.input-validity-area-span').removeClass('default-selection');
            $(this).addClass('default-selection');
            $('.input-validity-area-input').val(null);
            publishDemandDeline = $(this).attr('data-day');
            $('.input-validity-area-input').val(publishDemandDeline);
        });
        // 有效期输入失焦
        $('.input-validity-area-input').blur(function () {
            var inputCentent = $('.input-validity-area-input').val();
            // if (parseFloat(inputCentent).toString() == "NaN" && publishDemandDeline !== '') {
            //     // $('.deline-input-error').show();
            // } else {
            // $('.deline-input-error').hide();
            $('.input-validity-area-span').removeClass('default-selection');
            publishDemandDeline = inputCentent;
            // }
        })
        // 模拟点击有效期
        $(".select-button").on("click", function () {
            $(".demand-validity").focus();
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
        // 需求名称失去焦点
        $(".demand-name").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).siblings(".error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 需求名称获得焦点
        $(".demand-name").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).siblings(".error-info").children(".error-info-content").css("display", 'none');
        });
        // 需求简介失去焦点
        $(".demand-desc").on("blur", function () {
            if (!$(this).val()) {
                $(this).addClass("error-border");
                $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
            }
        });
        // 需求简介获得焦点
        $(".demand-desc").on("focus", function () {
            $(this).removeClass("error-border");
            $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
        });

        /****** 预算金额 输入值改变时 ******/
        $(".start-money").off().on("input propertychange", function () {
            var startMoney = $(".start-money").val();
            var endMoney = $(".end-money").val();
            if (endMoney !== '') {
                startMoney = startMoney ? startMoney : 0;
                if (parseFloat(endMoney) < parseFloat(startMoney)) {
                    $(".start-money").addClass("error-border");
                    $(".end-money").addClass("error-border");
                    $(".zero").removeClass("blue");
                    $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
                } else if (parseInt(endMoney) === parseInt(startMoney) && parseInt(endMoney) === 0) {
                    $(".start-money").removeClass("error-border");
                    $(".end-money").removeClass("error-border");
                    $(".zero").addClass("blue");
                    $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
                } else {
                    $(".start-money").removeClass("error-border");
                    $(".end-money").removeClass("error-border");
                    $(".zero").removeClass("blue");
                    $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
                }
            }
        });
        $(".end-money").off().on("input propertychange", function () {
            var startMoney = $(".start-money").val();
            var endMoney = $(".end-money").val();
            startMoney = startMoney ? startMoney : 0;
            endMoney = endMoney ? endMoney : 0;
            if (parseFloat(endMoney) < parseFloat(startMoney)) {
                $(".start-money").addClass("error-border");
                $(".end-money").addClass("error-border");
                $(".zero").removeClass("blue");
                $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
            } else if (endMoney && endMoney && parseInt(endMoney) === parseInt(startMoney) && parseInt(endMoney) === 0) {
                $(".start-money").removeClass("error-border");
                $(".end-money").removeClass("error-border");
                $(".zero").addClass("blue");
                $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
            } else {
                $(".start-money").removeClass("error-border");
                $(".end-money").removeClass("error-border");
                $(".zero").removeClass("blue");
                $(this).parent().siblings(".error-info").children(".error-info-content").css("display", 'none');
            }
        });
        $(".zero").off().on("click", function () {
            $(".start-money").val(0);
            $(".end-money").val(0);
            $(".zero").addClass("blue");
        });

        // 地区判空
        $(".demand-address-area .address-select").on("change", function () {
            if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
                if (!$("#provinceName").val()) {
                    $(".demand-address-area .address-select").addClass("error-border");
                } else if (!$("#cityName").val()) {
                    $(".demand-address-area #cityName").addClass("error-border");
                    $(".demand-address-area #districtName").addClass("error-border");
                } else if (!$("#districtName").val()) {
                    $(".demand-address-area #districtName").addClass("error-border");
                }
            } else if ($("#provinceName").val()) {
                $(".demand-address-area .address-select").removeClass("error-border");
            } else if ($("#cityName").val()) {
                $(".demand-address-area .address-select").removeClass("error-border");
            } else if ($("#districtName").val()) {
                $(".demand-address-area .address-select").removeClass("error-border");
            }
        });

        // 设置时间不可删除
        $(".demand-validity-area .demand-validity").on("input propertychange", function () {
            $(this).val($(this).formatTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)).split(" ")[0]);
        });

        // 上传附件
        $(".input-file").off().on("change", function () {
            uploadFile($(this).get(0).files);
        });
        // 点击提交发布需求
        $(".submit-area .submit").off().on("click", function () {
            publishDemand();
        });
    }

    function setTableData (list) {
        // 提取数据
        list.forEach(function (item) {
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
            $_fileData.push(obj);
        });
        table.setTableData($_fileData);
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
                var id = "";
                if (node.parent().hasClass("delete-file")) {
                    id = node.parent().data('id');
                } else {
                    id = node.data('id');
                }
                var attachments = $_attachmentsId.split(",");
                var index = attachments.searchArrayObj(String(id));
                if (index > -1) {
                    attachments.splice(index, 1);
                    $_attachmentsId = "";
                    for (var i = 0 ; i< attachments.length; i++) {
                        if (!$_attachmentsId) {
                            $_attachmentsId = "" + attachments[i];
                        } else {
                            $_attachmentsId += ',' + attachments[i];
                        }
                    }
                    $_fileData.splice(index, 1);
                }
                table.setTableData($_fileData);
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
                if (prefix === "png" || prefix === "jpg" || prefix === "jpeg" || prefix === "gif") {
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
        table.createTable();
    }

    function uploadFile (files) {
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
                var list = res.data.data_list;
                for (var i = 0; i < list.length; i++) {
                    if (!$_attachmentsId) {
                        $_attachmentsId = "" + list[i].id;
                    } else {
                        $_attachmentsId += ',' + list[i].id;
                    }
                }
                // 设置附件表格数据
                setTableData(list);
            }
        });
    }

    function beforePublishDemand () {
        var res = true;
        if (!$(".demand-name").val()) {
            res = false;
            $(".demand-name").addClass("error-border");
            $(".demand-name").siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        // if (!editor.getData()) {
        //     res = false
        //     $(".demand-desc").parent().siblings(".error-info").children(".error-info-content").css("display", 'block')
        // }
        if (!$(".demand-desc").val()) {
            res = false;
            $(".demand-desc").addClass("error-border");
            $(".demand-desc").parent().siblings(".error-info").children(".error-info-content").css("display", 'block');
        }
        if (parseInt($(".start-money").val()) > parseInt($(".end-money").val())) {
            res = false;
        }
        if (!$("#provinceName").val() || !$("#cityName").val() || !$("#districtName").val()) {
            res = false;
            if (!$("#provinceName").val()) {
                $(".demand-address-area .address-select").addClass("error-border");
            } else if (!$("#cityName").val()) {
                $(".demand-address-area #cityName").addClass("error-border");
                $(".demand-address-area #districtName").addClass("error-border");
            } else if (!$("#districtName").val()) {
                $(".demand-address-area #districtName").addClass("error-border");
            }
        }
        if (!res) {
            layer.open({
                title: '温馨提示',
                content: '请完善信息'
            });
        }
        return res;
    }

    function publishDemand () {
        if (beforePublishDemand()) {
            var json = {
                name: $(".demand-name").val(),
                illustration: $(".demand-desc").val(),
                // industryField: $_industryFieldId,
                address: $("#provinceName").val() + $("#cityName").val() + $("#districtName").val(),
                budgetAmountStart: $(".start-money").val() ? $(".start-money").val() : 0,
                budgetAmount: $(".end-money").val() ? $(".end-money").val() : 0,
                // deadline: $("#demand-validity").val() ? $("#demand-validity").val() : $("#demand-validity").formatTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)).split(" ")[0],
                // deadline: publishDemandDeline.formatTime(new Date() + publishDemandDeline * 1000 * 60 * 60 * 24),
                validdate: publishDemandDeline ? publishDemandDeline : 0,
                provinceName: $("#provinceName").val(),
                cityName: $("#cityName").val(),
                districtName: $("#districtName").val(),
                attachment: $_attachmentsId,
                industryId: $('.select-area .type-item').eq(0).find('.active').attr('data-id'),
            };
            if ($('.select-area .type-item').eq(1).find('.active').attr('data-id') !== 0) {
                json.subIndustryId = $('.select-area .type-item').eq(1).find('.active').attr('data-id');
            }
            if (!!projectDemandDatas[0].data.id) {
                json.id = projectDemandDatas[0].data.id;
            }
            new NewAjax({
                type: "POST",
                url: "/f/projectDemand/pc/create_update?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {
                        // 已经发布成功，消除离开判定
                        set_IMPORTANTOPERATION(false);
                        window.location.href="/f/personal_center.html?pc=true#menu=publishDemandList";
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: '内部信息出错'
                        });
                    }
                }
            });
            /*new NewAjax({
                type: "POST",
                url: "/f/projectDemand/pc/create_update?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if(res.status === 200) {
                        window.location.href="/f/personal_center.html?pc=true&menu=publishDemandList"
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: '内部信息出错'
                        })
                    }
                }
            });*/
        }
    }


    function upperCase(obj) {//用户只能输入正负数与小数
        if (isNaN(obj.value) && !/^$/.test(obj.value)) {
            obj.value = "";
        }
        if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
            obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 3));
        }
    }
});


