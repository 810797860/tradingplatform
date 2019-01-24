/**
 * Created by 空 on 2018/9/10.
 */
$(function () {
    var $_attachmentsId = "";
    var $_fileData = [];
    var editor = null;

    $('.demand-sign-up-quote input').blur(function () {
        if ($('.demand-sign-up-quote input').val() == '' || $('.demand-sign-up-quote input').val() == null) {
            $('.demand-sign-up-quote .checkIsWriteMoney').css('display', 'block');
        } else {
            $('.demand-sign-up-quote .checkIsWriteMoney').css('display', 'none');
        }
    })

    $('.demand-sign-up-advantage textarea').blur(function () {
        if ($('.demand-sign-up-advantage textarea').val() == '' || $('.demand-sign-up-advantage textarea').val() == null) {
            $('.demand-sign-up-advantage .checkIsWriteAdvantage').css('display', 'block');
        } else {
            $('.demand-sign-up-advantage .checkIsWriteAdvantage').css('display', 'none');
        }
    })


    // 文件上传事件
    $(".input-file").off().on("change", function () {
        uploadFile($(this).get(0).files);
    });

    // $(".demand-sign-up-submit .sign-up-submit").click(function () {
    //     var textContent = $(".demand-sign-up .demand-sign-up-quote input").val();
    //     var advantage = $(".demand-sign-up .demand-sign-up-advantage textarea").val();
    //     var json = {
    //         projectId: projectId,
    //         offerPrice: textContent,
    //         technicalSolutionIntroduction: advantage,
    //         technicalSolutionAttachment: $_attachmentsId
    //     }
    //     var url = '/f/projectDemandSignUp/pc/create_update?pc=true'
    //     new NewAjax({
    //         type: "POST",
    //         url: url,
    //         contentType: "application/json;charset=UTF-8",
    //         dataType: "json",
    //         data: JSON.stringify(json),
    //         success: function (res) {
    //             if(res.status === 200) {
    //                 layer.msg('报名成功,即将返回需求大厅');
    //                 setTimeout(function () {
    //                     window.open('/f/demand_hall.html?pc=true','_self');
    //                 },1500)
    //             }
    //         }
    //     })
    // })

    // 文件上传函数
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
                var table = new Table('upload-table');
                var baseStyleArr = [];
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
                })
                // 决定数据顺序
                var orderArr = ['title','size','id'];
                table.setTableData($_fileData);
                table.setBaseStyle(baseStyleArr);
                table.setColOrder(orderArr);
                table.setOpenCheckBox(false, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
                    if (type === 'title') {
                        return (label === 'td') ? '<span class="file-title">' + content + '</span>' : content;
                    } else if (type === 'size') {
                        return (label === 'td') ? content+'KB' : content;
                    } else if (type === 'id') {
                        var span = '<span class="see-file" data-id="' + content[0] + '" data-prefix="' + content[1] + '"><i class="icon-search"></i>查看</span>' +
                            '<span class="delete-file" data-id="'+content[0]+'" data-prefix="'+content[1]+'"><i class="icon-false"></i>删除</span>';
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
                            var img = '<img src="'+src+'" style="width: 500px; height: auto;"/>';
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
                            window.open("/adjuncts/file_download/" + id);
                        }
                    }
                });
                table.createTable()
            }
        });
    }

        $(".demand-sign-up-submit .sign-up-submit").off().click(function () {
            var textContent = $(".demand-sign-up .demand-sign-up-quote input").val();
            var advantage = $(".demand-sign-up .demand-sign-up-advantage textarea").val();
            var json = {
                projectId: projectId,
                offerPrice: textContent,
                technicalSolutionIntroduction: advantage,
                technicalSolutionAttachment: $_attachmentsId
            };
            if ( $('.demand-sign-up-quote input').val() != '' && $('.demand-sign-up-advantage textarea').val() != '') {
                var url = '/f/projectDemandSignUp/pc/create_update?pc=true'
                new NewAjax({
                    type: "POST",
                    url: url,
                    contentType: "application/json;charset=UTF-8",
                    dataType: "json",
                    data: JSON.stringify(json),
                    success: function (res) {
                        if(res.status === 200) {
                            $(".demand-sign-up-submit .sign-up-submit").hide();
                            layer.msg('报名成功,即将返回需求大厅');
                            setTimeout(function () {
                                window.open('/f/demand_hall.html?pc=true','_self');
                            },1500)
                        }
                    }
                })
            } else {
                layer.msg('报价与我的优势为必填项')
            }
        })

})

function upperCase(obj) {//用户只能输入正负数与小数
    if (isNaN(obj.value) && !/^$/.test(obj.value)) {
        obj.value = "";
    }
    if (!/^[+]?\d*\.{0,1}\d{0,1}$/.test(obj.value)) {
        obj.value = obj.value.replace(/\.\d{2,}$/, obj.value.substr(obj.value.indexOf('.'), 3));
    }
}