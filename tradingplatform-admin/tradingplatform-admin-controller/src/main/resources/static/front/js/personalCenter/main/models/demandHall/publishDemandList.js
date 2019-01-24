var currentPageOfPublishDemandList = 1;
var searchSizeOfPublishDemandList = 10;
var publishDemandListTypeId;
var pageInOrTabPublishDemand = 0; // 判断本分类点击还是切换分类点击，0为本分类点击

var workSpeed = 1;
var workQuality = 1;
var workAttitude = 1;
var evaluationTypeId = "202103";
var publishDemandListProjectId;
var publishIsRead = false;

/* 报名者列表 */
var publisherGetListDataId;

// 搜索条件对象
var selectConditionOfPublishDemandList = {
    searchVal: '',
    startTime: '',
    endTime: ''
};
// 用来存储设置表格的变量
var publishDemandListData = [];

// 获取报名者表格
var oApplicantTable = null;
var currentPageOfPublishDemandLists = 1;
var searchSizeOfPublishDemandLists = 10;
var pageInOrTabPublishDemands = 0; // 判断本分类点击还是切换分类点击，0为本分类点击


/*** 监听分页跳转 ***/
function listenToPage() {
    $(".publish-demand-splitpage >div").on("click", function () {
        if ($('.publish-demand-splitpage .focus')[0].innerText != currentPageOfPublishDemandList) {
            currentPageOfPublishDemandList = $('.publish-demand-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabPublishDemand = 0;
            getPublishDemandList();
        }
    }).keydown(function (e) {
        if (e.keyCode == 13) {
            currentPageOfPublishDemandList = $('.publish-demand-splitpage .focus')[0].innerText;

            // getServiceList($_typeId, $("#search-input").val());
            pageInOrTabPublishDemand = 0;
            getPublishDemandList();
        }
    });
}

/*** 复原currentPage ***/
function resetCurrentPagePublishDemand() {
    $('.publish-demand-splitpage >div').data('currentpage', 1);
    currentPageOfPublishDemandList = 1;
    $('.publish-demand-splitpage >div').find('li[data-page="' + currentPageOfPublishDemandList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

$(function () {
    init_dom();
    initDateTimePicker('publish-demand-list');
    publishDemandListSearch();
    listenToPage();
    initApplicantTable();
});

function init_dom() {
    $('.publish-demand-list .personal-center-search-list ul').html(' <li class="searchByAll active"><span>全部</span><span></span></li> <li class="searchByOne" data-typeId="202067"><span>待审核</span><span>1</span></li> <li class="searchByTwo" data-typeId="202069"><span>报名中</span><span>2</span></li> <li class="searchByTree" data-typeId="202074"><span>已完成</span><span>3</span></li> <li class="searchByFour" data-typeId="202074"><span>报名已截止</span><span>4</span></li><span class="publishTypeMore extend" style="font-size: 14px;display: inline-block;vertical-align: top;cursor: pointer;color: #0066cc;margin-left: 50px">更多<i class="icon-open-arrow"></i></span>');
    $('.publish-demand-list .personal-center-search-time').children().eq(0).html('发布时间：');
    $(document).on('click', '.extend', function () {
        $('.publishTypeMore').html('收起<i></i>')
        $('.publishTypeMore').removeClass('extend');
        $('.publishTypeMore').addClass('noExtend');
        $('.publishTypeMore>i').addClass('icon-close-arrow');
        $('.publish-demand-list .personal-center-search-list').css('height', '50px');
        var ul = $('<ul style="line-height: 22px;height: 22px;margin-bottom: 10px;margin-left: 84px"><li class="searchByOne" data-typeId="202068"><span>初审不通过</span><span>1</span></li> <li class="searchByTwo" data-typeId="202071"><span>合作中</span><span>2</span></li> <li class="searchByTree" data-typeId="202072"><span>验收付款</span><span>3</span></li> <li class="searchByFour" data-typeId="202073"><span>待评价</span><span>4</span></li></ul>');
        $('.publish-demand-list .personal-center-search-list').append(ul);
    })
    $(document).on('click', '.noExtend', function () {
        $('.publishTypeMore').html('展开<i></i>')
        $('.publishTypeMore').removeClass('noExtend');
        $('.publishTypeMore').addClass('extend');
        $('.publishTypeMore>i').addClass('icon-open-arrow');
        $('.publish-demand-list .personal-center-search-list').css('height', '20px');
        $('.publish-demand-list .personal-center-search-list ul').eq(1).remove();
    })
    $(document).on('click', '.publish-demand-list .personal-center-search-list ul li', function () {
        $('.publish-demand-list').find('ul li').removeClass('active');
        $(this).addClass('active');
        publishDemandListTypeId = $(this).attr('data-typeId');

        pageInOrTabPublishDemand = 1;
        currentPageOfPublishDemandList = 1;

        getPublishDemandList();
    })
    // $('.publish-demand-list .personal-center-search-list ul li').click(function () {
    //     $('.publish-demand-list').find('ul li').removeClass('active');
    //     $(this).addClass('active');
    //     publishDemandListTypeId = $(this).attr('data-typeId');
    //
    //     pageInOrTabPublishDemand = 1;
    //     currentPageOfPublishDemandList = 1;
    //
    //     getPublishDemandList();
    // })
}

function publishDemandListSearch() {
    $('.publish-demand-list').find('.searchByTime').click(function () {
        selectConditionOfPublishDemandList.searchVal = $('.publish-demand-list').find('.searchByNameContent').val();
        selectConditionOfPublishDemandList.startTime = $('.publish-demand-list').find('.search-star-time').val();
        selectConditionOfPublishDemandList.endTime = $('.publish-demand-list').find('.search-end-time').val();
        pageInOrTabPublishDemand = 1;
        currentPageOfPublishDemandList = 1;
        getPublishDemandList();
    });
    $('.publish-demand-list').find('.searchByName').click(function () {
        selectConditionOfPublishDemandList.searchVal = $('.publish-demand-list').find('.searchByNameContent').val();
        pageInOrTabPublishDemand = 1;
        currentPageOfPublishDemandList = 1;
        getPublishDemandList();
    })
}

// 获取发布的需求列表
function getPublishDemandList() {
    var json = {
        "pager": {
            "current": currentPageOfPublishDemandList,
            "size": searchSizeOfPublishDemandList
        },
        "sortPointer": {
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (publishDemandListTypeId) {
        json.status = publishDemandListTypeId
    }
    if (selectConditionOfPublishDemandList.searchVal) {
        json.name = selectConditionOfPublishDemandList.searchVal;
    }
    if (selectConditionOfPublishDemandList.startTime) {
        json.createdAtStart = selectConditionOfPublishDemandList.startTime;
    }
    if (selectConditionOfPublishDemandList.endTime) {
        json.createdAt = selectConditionOfPublishDemandList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/projectDemand/pc/query_by_user_id?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            if (pageInOrTabPublishDemand === 0) {
                $('.publish-demand-splitpage >div').Paging({
                    pagesize: searchSizeOfPublishDemandList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.publish-demand-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabPublishDemand === 1) {
                $('.publish-demand-splitpage >div').Paging({
                    pagesize: searchSizeOfPublishDemandList,
                    count: totalRecord,
                    toolbar: true
                });
                $('.publish-demand-splitpage >div').find("div:eq(0)").remove();
            }
            $('.publish-demand-total').html("共" + totalRecord + "条");

            publishDemandListData = list;
            setPublishDemandListData();
            publishDemandListHandleClick();
            if (list.length === 0) {
                $('.publish-demand-list').find('.noData').remove()
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>'
                $('.publish-demand-list').append(div)
                $('.publish-demand-splitpage').css("display", "none")
                $('.publish-demand-total').css("display", "none")
            } else {
                $('.publish-demand-list').find('.noData').remove()
                $('.publish-demand-splitpage').css("display", "block")
                $('.publish-demand-total').css("display", "block")
            }
        }
    });
}

// 设置报名者详情数据
function setSignUpCompanyInfo(object) {
    $(".sign-up-company-info .info-title").html(object.name);
    $(".sign-up-company-info .company-logo").attr('src', $(this).getAvatar(object.logo));
    $(".sign-up-company-info .info-phone").html(object.phone);
    $(".sign-up-company-info .desc").html(object.introduction);
    $(".sign-up-company-info .info-money").html(object.offer_price);
    $(".sign-up-company-info .info-time").html($(this).formatTime(new Date(object.created_at)).split(" ")[0]);

    var data = []
    var table = new Table('table-area')
    var baseStyleArr = []
    if (object.technical_solution_attachment != undefined) {
        // 提取数据
        JSON.parse(object.technical_solution_attachment).forEach(function (item) {
            var obj = {}
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function (key) {
                    var styleItem = {}
                    styleItem.type = key
                    switch (key) {
                        case 'title':
                            styleItem.name = '文件名'
                            styleItem.width = 423
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
            obj.id = item.id
            data.push(obj)
        })
        // 决定数据顺序
        var orderArr = ['title', 'size', 'id']
        table.setTableData(data)
        table.setBaseStyle(baseStyleArr)
        table.setColOrder(orderArr)
        table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
            if (type === 'title') {
                return (label === 'td') ? '<span class="file-title">' + content + '</span>' : content
            } else if (type === 'size') {
                return (label === 'td') ? content + 'KB' : content
            } else if (type === 'id') {
                var span = '<span class="see-file"><i class="icon-search"></i>查看</span>' +
                    '<span class="download-file"><i class="icon-download"></i>下载</span>'
                return (label === 'td') ? span : content
            }
        });
        table.createTable()
    }
}

// 写入发布需求列表
function setPublishDemandListData() {
    var data = [];
    var table = new Table('publish-demand-list-table');
    var baseStyleArr = [];
    if (publishDemandListData !== undefined && publishDemandListData.length !== 0) {
        publishDemandListData.forEach(function (item) {
            var obj = {};
            if (baseStyleArr.length === 0) {
                Object.keys(item).forEach(function (key) {
                    var styleItem = {};
                    styleItem.type = key;
                    switch (key) {
                        case 'name':
                            styleItem.name = '需求名称';
                            styleItem.width = 170;
                            baseStyleArr.push(styleItem);
                            break;
                        case 'industry_id':
                            styleItem.name = '需求行业';
                            styleItem.width = 120;
                            baseStyleArr.push(styleItem);
                            break;
                        case 'sub_industry_id':
                            styleItem.name = '子行业';
                            styleItem.width = 100;
                            baseStyleArr.push(styleItem);
                            break;
                        case 'status':
                            styleItem.name = '状态';
                            styleItem.width = 100;
                            baseStyleArr.push(styleItem);
                            break;
                        case 'created_at':
                            styleItem.name = '发布时间';
                            styleItem.width = 100;
                            baseStyleArr.push(styleItem);
                            break;
                        case 'total_docking':
                            styleItem.name = '对接人数';
                            baseStyleArr.push(styleItem);
                            break;
                        case 'id':
                            styleItem.name = '操作';
                            styleItem.width = 220;
                            baseStyleArr.push(styleItem);
                            break;
                        default:
                            break;
                    }
                    styleItem.align = 'left';
                })
            }
            obj.name = [item.name, item.id];
            obj.total_docking = [item.total_docking, item.id];
            if (!!item.sub_industry_id) {
                obj.sub_industry_id = JSON.parse(item.sub_industry_id).title;
            }
            if (!!item.industry_id) {
                obj.industry_id = JSON.parse(item.industry_id).title;
            }
            obj.status = JSON.parse(item.status).title;
            obj.created_at = fmtDate(item.created_at);
            obj.id = [item.id, JSON.parse(item.status).id];
            data.push(obj);
        })
    }
    var orderArr = ['name', 'industry_id', 'sub_industry_id', 'status', 'created_at', 'total_docking', 'id'];
    table.setTableData(data);
    table.setBaseStyle(baseStyleArr);
    table.setColOrder(orderArr);
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        var span = null
        if (type === 'size') {
            return (label === 'td') ? content + 'KB' : content
        } else if (type === 'total_docking') {
            if (content[0] > 0) {
                span = '<span title="' + content[0] + '" class="publishDemandListPeopleNum" data-pronumid="' + content[1] + '" style="cursor: pointer;color: #0db5fb">' + content[0] + '</span>'
            } else {
                span = '<span title="' + content[0] + '">' + content[0] + '</span>'
            }
            return (label === 'td') ? span : content
        } else if (type === 'id') {
            if (content[1] === '202067' || content[1] === '202068') {
                span = '<span class="publishedit" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>编辑</span>' +
                    '<span class="cancel" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>取消</span>' +
                    '<span class="look" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>'
            } else if (content[1] === '202073') {
                span = '<span class="look" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                    '<span class="evaluation" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>评价</span>' +
                    '<span class="reviewResult" data-id="' + content + '" style="display: inline-block;height: 30px;padding: 0 10px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="publishDemandListSubmitResult(' + content[0] + ')"></i>查看成果</span>'
            } else if (content[1] === '202074') {
                span = '<span class="look" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                    '<span class="reviewResult" data-id="' + content + '" style="display: inline-block;height: 30px;padding: 0 10px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="publishDemandListSubmitResult(' + content[0] + ')"></i>查看成果</span>' +
                    '<span class="viewComments" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看评价</span> '
            } else if (content[1] === '202072') {
                span = '<span class="look" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                    '<span class="reviewResult" data-id="' + content + '" style="display: inline-block;height: 30px;padding: 0 10px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="publishDemandListSubmitResult(' + content[0] + ')"></i>成果审核</span>'
            } else {
                span = '<span class="look" data-id="' + content + '" style="display: inline-block;width: 40px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>'
            }
            return (label === 'td') ? span : content
        } else if (type === 'name') {
            span = '<span title="' + content[0] + '" class="publishDemandListToDetail" data-proId="' + content[1] + '" style="cursor: pointer;color: #0066cc">' + content[0] + '</span>'
            return (label === 'td') ? span : content
        }
    });
    table.createTable()
}

// 发布需求列表点击事件
function publishDemandListHandleClick() {
    $(document).on('click', '.publishDemandListToDetail', function () {
        var id = $(this).attr('data-proId')
        window.open('/f/' + id + '/demand_detail.html?pc=true')
    })
    $(".publishDemandListUploadFile").change(function () {
        var formData = new FormData();
        var id = $(this).attr('data-id').split(',')[0];
        if (!!$(this)[0].files[0]) {
            formData.append("files", $(this).get(0).files[0])
            new NewAjax({
                url: "/adjuncts/file_upload",
                type: "POST",
                data: formData,
                async: true,
                processData: false,
                contentType: false,
                success: function (res) {
                    if (res.status === 200) {
                        var json = {
                            id: id,
                            post_material: res.data.data_list[0].id
                        }
                        new NewAjax({
                            url: "/f/projectDemand/pc/create_update?pc=true",
                            type: "POST",
                            contentType: "application/json;charset=UTF-8",
                            dataType: "json",
                            data: JSON.stringify(json),
                            success: function (res) {
                                if (res.status === 200) {
                                    layer.msg("附件上传成功")
                                }
                            }
                        })
                    } else {
                        layer.msg("附件上传失败")
                    }
                }
            })
        } else {
            layer.msg("没有选择文件")
        }
    })
    $('.publish-demand-list').find('.publishedit').click(function () {
        var arr = $(this).attr('data-id').split(',')
        window.open('/f/projectDemand/pc/' + arr[0] + '/to_create_update.html?pc=true')
    })
    $('.publish-demand-list').find('.look').click(function () {
        var arr = $(this).attr('data-id').split(',')
        window.open('/f/' + arr[0] + '/demand_detail.html?pc=true&publisher')
    })
    $('.publish-demand-list').find('.cancel').click(function () {
        var arr = $(this).attr('data-id').split(',')
        var json = [arr[0]]
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            pageInOrTabPublishDemand = 1
            currentPageOfPublishDemandList = 1
            new NewAjax({
                type: "POST",
                url: "/f/projectDemand/batch_delete?pc=true",
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                data: JSON.stringify(json),
                success: function (res) {
                    if (res.status === 200) {
                        getPublishDemandList()
                        layer.closeAll()
                    } else {
                        layer.open({
                            title: '温馨提示',
                            content: '内部信息出错'
                        })
                    }
                }
            });
        }, function () {
            layer.msg('取消了这次删除', {
                time: 1000, //20s后自动关闭
            });
        });
    })
    $('.publish-demand-list').find('.evaluation').off().click(function () {
        publishIsRead = false;
        publishDemandListProjectId = $(this).attr('data-id').split(',')[0];
        $('.publish-demand-list div').removeClass('show')
        $('.publish-demand-list').find('.Discuss').addClass('show')
        $('.publish-demand-list .feedback-content-textarea').text('');
        $('.publish-demand-list .feedback-content-textarea').attr('disabled', false);
        $('.publish-demand-list .feedback-tips p').text('请你为服务商的工作进行评价');
        var workSpeed = 196
        $('.publish-demand-list .workSpeed .stars-list').eq(0).css('width', workSpeed + 'px');
        var workQuality = 196
        $('.publish-demand-list .workQuality .stars-list').eq(0).css('width', workQuality + 'px');
        var workAttitude = 196
        $('.publish-demand-list .workAttitude .stars-list').eq(0).css('width', workAttitude + 'px');
        $('.publish-demand-list .evaluationStar .evaluationSapn').eq(0).addClass('spanActive')
        $('.publish-demand-list>div').find('.submit-btn').show();
        // getPublishDemandList(data)
    })
    $('.publish-demand-list .service-feedback-div .back').click(function () {
        $('.publish-demand-list div').removeClass('show')
        $('.publish-demand-list div').eq(0).addClass('show')
    })

    $(".publish-demand-list .workSpeed ul li").off().click(function () {
        if (publishIsRead === false) {
            var i = $(this).index()
            var width = (i + 1) * 40
            console.log(width)
            $('.publish-demand-list .workSpeed .stars-list').eq(0).css("width", width + "px")
            workSpeed = (i + 1) * 0.2
        }
    })
    $(".publish-demand-list .workQuality ul li").off().click(function () {
        if (publishIsRead === false) {
            var i = $(this).index()
            var width = (i + 1) * 40
            $('.publish-demand-list .workQuality .stars-list').eq(0).css("width", width + "px")
            workQuality = (i + 1) * 0.2
        }
    })
    $(".publish-demand-list .workAttitude ul li").off().click(function () {
        if (publishIsRead === false) {
            var i = $(this).index()
            var width = (i + 1) * 40
            $('.publish-demand-list .workAttitude .stars-list').eq(0).css("width", width + "px")
            workAttitude = (i + 1) * 0.2
        }
    })
    $(".publish-demand-list .evaluationSapn").off().click(function () {
        if (publishIsRead === false) {
            $(".publish-demand-list .evaluationSapn").removeClass("spanActive")
            $(this).addClass("spanActive")
            evaluationTypeId = $(this).attr("data-typeId")
        }
    })
    $('.publish-demand-list').find('.viewComments').off().click(function () {
        publishIsRead = true;
        $('.publish-demand-list>div').removeClass('show');
        $('.publish-demand-list>div').eq(1).addClass('show');
        $('.publish-demand-list>div').find('.submit-btn').hide();
        $('.publish-demand-list .feedback-tips p').text('查看你的评价');
        console.log($(this).attr('data-id'));
        var proId = $(this).attr('data-id').split(',')[0]
        var url = '/f/serviceProvidersEvaluation/' + proId + '/get_by_id?pc=true'
        new NewAjax({
            url: url,
            type: 'GET',
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            success: function (res) {
                console.log(res);
                if (!!res.data.data_object) {
                    if (!!res.data.data_object.comments) {
                        $('.publish-demand-list .feedback-content-textarea').text(res.data.data_object.comments);
                    } else {
                        $('.publish-demand-list .feedback-content-textarea').text('');
                    }
                    $('.publish-demand-list .feedback-content-textarea').attr('disabled', 'true');
                    var workSpeed = 195.6 * res.data.data_object.work_speed_star;
                    $('.publish-demand-list .workSpeed .stars-list').eq(0).css('width', workSpeed + 'px');

                    var workQuality = 195.6 * res.data.data_object.work_quality_star;
                    $('.publish-demand-list .workQuality .stars-list').eq(0).css('width', workQuality + 'px');

                    var workAttitude = 195.6 * res.data.data_object.work_attitude_star;
                    $('.publish-demand-list .workAttitude .stars-list').eq(0).css('width', workAttitude + 'px');
                    if (!!res.data.data_object.evaluation_type) {
                        var typeId = JSON.parse(res.data.data_object.evaluation_type).id
                        if (typeId == 202103) {
                            $('.publish-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.publish-demand-list .evaluationStar .evaluationSapn').eq(0).addClass('spanActive')
                        } else if (typeId == 202104) {
                            $('.publish-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.publish-demand-list .evaluationStar .evaluationSapn').eq(1).addClass('spanActive')
                        } else {
                            $('.publish-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.publish-demand-list .evaluationStar .evaluationSapn').eq(2).addClass('spanActive')
                        }
                    }
                }
            }
        })
    })
    $(".publish-demand-list .service-feedback-div .submit-btn p").off().click(function () {
        var content = $(".publish-demand-list .feedback-content #contents").val()
        var json = {
            projectId: parseInt(publishDemandListProjectId),
            comments: filterSensitiveWord(content),
            evaluationType: evaluationTypeId,
            workSpeedStar: workSpeed,
            workAttitudeStar: workAttitude,
            workQualityStar: workQuality
        }
        new NewAjax({
            url: "/f/serviceProvidersEvaluation/pc/create_update?pc=true",
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            dataType: "json",
            data: JSON.stringify(json),
            success: function (res) {
                if (res.status == 200) {
                    $(".publish-demand-list .service-feedback-div .submit-btn").hide();
                    layer.msg("评论成功，1秒后返回页面");
                    setTimeout(function () {
                        $('.publish-demand-list div').removeClass('show')
                        $('.publish-demand-list div').eq(0).addClass('show')
                    }, 1000);
                }
            }
        })
    })

    eventOfApplicantClick();
    eventOfBackBtnClick();
}

// 需求提交成果点击按钮事件
function publishDemandListSubmitResult(projectId) {
    // 获取成果对象
    var demandResult = new ReviewDemandResult();
    // 调用请求数据
    demandResult.getDemandResult(projectId, function (data) {
        $(".right-page").removeClass("page-active").siblings(".review-demand-result-area").addClass("page-active");
    })
}

/* 报名人员模块 */

// 初始化报名者表格
function initApplicantTable() {
    var baseStyleArr = [
        {
            type: 'created_at',
            name: '报名时间',
            width: 100,
            align: 'left'
        },
        {
            type: 'is_winning',
            name: '雇佣状态',
            align: 'left'
        },
        {
            type: 'id',
            name: '操作',
            width: 240,
            align: 'left'
        },
        {
            type: 'phone',
            name: '手机',
            align: 'left'
        },
        {
            type: 'name',
            name: '昵称',
            align: 'left'
        }
    ];
    var orderArr = ['name', 'phone', 'is_winning', 'created_at', 'id'];
    oApplicantTable = new Table('publish-demand-list-publisher-table');
    oApplicantTable.setBaseStyle(baseStyleArr)
        .setColOrder(orderArr)
        .setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        if (!content) {
            return (label === 'td') ? '暂无数据' : content;
        } else if (type === 'id') {
            var span = '<a class="Applicant-detail-link" href="/f/projectDemandSignUp/' + content[0] + '/toDetail.html?pc=true" target="_blank">详情</a>';
            return (label === 'td') ? span : content
        }
    });
}

// 获取报名者列表
function getListOfSignUpData() {
    var json = {
        projectId: publisherGetListDataId,
        pager: {
            current: currentPageOfPublishDemandLists,
            size: searchSizeOfPublishDemandLists
        },
        sortPointer: {
            filed: "created_at",
            order: "DESC"
        }
    };
    console.log(json);
    new NewAjax({
        type: "POST",
        url: "/f/projectDemandSignUp/pc/get_by_project_id",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            console.log(res);
            var totalRecord = res.data.total;
            // var list = res.data.data_list;

            $('.publish-demand-publisher-total').html("共" + totalRecord + "条");

            if (pageInOrTabPublishDemand === 0) {
                $('.publish-demand-publisher-splitpage >div').Paging({
                    pagesize: searchSizeOfPublishDemandLists,
                    count: totalRecord,
                    toolbar: true
                });
                $('.publish-demand-publisher-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabPublishDemand === 1) {
                $('.publish-demand-publisher-splitpage >div').Paging({
                    pagesize: searchSizeOfPublishDemandLists,
                    count: totalRecord,
                    toolbar: true
                });
                $('.publish-demand-publisher-splitpage >div').find("div:eq(0)").remove();
            }

            if (res.data.data_list.length > 0) {
                setSignUpList(res.data.data_list);
            }
        }
    })
}

// 设置报名者列表
function setSignUpList(list) {
    var dataList = list;
    var data = [];
    if (dataList != undefined && dataList.length != 0) {
        dataList.forEach(function (item) {
            var obj = {};
            if (!!item.user_id) {
                obj.name = JSON.parse(item.user_id).user_name;
                obj.phone = JSON.parse(item.user_id).phone;
            }
            if (!!item.is_winning) {
                obj.is_winning = JSON.parse(item.is_winning).title;
            }
            obj.created_at = fmtDate(item.created_at);
            obj.id = [item.id, JSON.parse(item.is_winning).id];
            data.push(obj);
        })
    }
    oApplicantTable.setTableData(data)
        .createTable()
}

// 报名者详情点击事件
function eventOfApplicantClick() {
    $(document).on('click', '.publishDemandListPeopleNum', function () {
        publisherGetListDataId = $(this).data('pronumid');
        console.log(publisherGetListDataId);
        $('.publish-demand-list-people').addClass('show');
        $('.publish-demand-list div').eq(0).removeClass('show');
        $('.publish-demand-list .Discuss').removeClass('show');
        getListOfSignUpData();
    });
}

// 报名者列表返回按钮点击事件
function eventOfBackBtnClick() {
    // 返回发布需求列表
    $('.back-publish-demand-list-btn').click(function () {
        $('.publish-demand-list-people').removeClass('show');
        $('.publish-demand-list div').eq(0).addClass('show');
        $('.publish-demand-list .Discuss').removeClass('show');
    })
}

