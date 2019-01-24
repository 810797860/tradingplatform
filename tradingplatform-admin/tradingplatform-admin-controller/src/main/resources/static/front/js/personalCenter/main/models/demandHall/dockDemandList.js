var currentPageOfDockDemandList = 1;
var searchSizeOfDockDemandList = 10;
var dockDemandListTypeId;
var dockDemandListStatusId;
var pageInOrTabDockDemand = 0; // 判断本分类点击还是切换分类点击，0为本分类点击
var isUnderReview1 = false; // 判断是否处于审核状态

/*** 监听分页跳转 ***/
function listenToPage() {
    $(".dock-demand-splitpage >div").on("click", function(){
        console.log($('.dock-demand-splitpage .focus')[0].innerText);
        if($('.dock-demand-splitpage .focus')[0].innerText != currentPageOfDockDemandList) {
            currentPageOfDockDemandList = $('.dock-demand-splitpage .focus')[0].innerText;
            pageInOrTabDockDemand = 0;
            getDockDemandList();
        }
    }).keydown(function(e) {
        if (e.keyCode == 13) {
            currentPageOfDockDemandList = $('.dock-demand-splitpage .focus')[0].innerText;
            pageInOrTabDockDemand = 0;
            getDockDemandList();
        }
    });
}
/*** 复原currentPage ***/
function resetCurrentPageDockDemand () {
    $('.dock-demand-splitpage >div').data('currentpage', 1);
    currentPageOfDockDemandList = 1;
    $('.dock-demand-splitpage >div').find('li[data-page="' + currentPageOfDockDemandList + '"]').addClass('focus').siblings().removeClass('focus');
    // 上一页下一页重置
    $('.js-page-prev').addClass("ui-pager-disabled");
    $('.js-page-next').removeClass("ui-pager-disabled");
}

// 搜索条件对象
var selectConditionOfDockDemandList = {
    searchVal: '',
    startTime: '',
    endTime: ''
}

// 用来存储设置表格的变量
var dockDemandListData = [];

$(function () {
    listenToPage();
    init_dom();
    initDateTimePicker('dock-demand-list');
    publishDemandListSearch();
});

function init_dom() {
    /*<li data-statusId="202072"><span>验收中</span><span>3</span></li><li data-statusId="202073"><span>待评价</span><span>3</span></li><li data-statusId="202075"><span>已完成</span><span>3</span></li>*/
    $('.dock-demand-list .personal-center-search-list ul').html(' <li class="active"><span>全部</span><span></span></li> <li data-typeId="202077"><span>待雇佣</span><span>1</span></li> <li data-typeId="202078"><span>中标</span><span>2</span></li> <li data-typeId="202079"><span>未中标</span><span>3</span></li>');
    $('.dock-demand-list .personal-center-search-time').children().eq(0).html('发布时间：');
    $('.dock-demand-list .personal-center-search-list ul li').click(function () {
        $('.dock-demand-list').find('ul li').removeClass('active');
        $(this).addClass('active');
        dockDemandListTypeId = $(this).attr('data-typeId');
        dockDemandListStatusId = $(this).attr('data-statusId');

        pageInOrTabDockDemand = 1;
        currentPageOfDockDemandList = 1;

        getDockDemandList();
    })
    $('.dock-demand-list').find('.feedback-tips>p').text('查看工作评价');
    $('.dock-demand-list .submit-btn').remove();
}

function publishDemandListSearch() {
    $('.dock-demand-list').find('.searchByTime').click(function () {
        selectConditionOfDockDemandList.searchVal = $('.dock-demand-list').find('.searchByNameContent').val();
        selectConditionOfDockDemandList.startTime = $('.dock-demand-list').find('.search-star-time').val();
        selectConditionOfDockDemandList.endTime = $('.dock-demand-list').find('.search-end-time').val();

        pageInOrTabDockDemand = 1;
        currentPageOfDockDemandList = 1;

        getDockDemandList();
    })
    $('.dock-demand-list').find('.searchByName').click(function () {
        selectConditionOfDockDemandList.searchVal = $('.dock-demand-list').find('.searchByNameContent').val();

        pageInOrTabDockDemand = 1;
        currentPageOfDockDemandList = 1;

        getDockDemandList();
    })
}


// 获取报名的需求列表
function getDockDemandList () {
    var json = {
        "pager":{
            "current": currentPageOfDockDemandList,
            "size": searchSizeOfDockDemandList
        },
        "sortPointer":{
            "filed": "created_at",
            "order": "DESC"
        }
    };
    if (!!dockDemandListTypeId) {
        json.isWinning   = dockDemandListTypeId;
    } else {
        json.isWinning = null;
    }
    if (!!dockDemandListStatusId) {
        json.status = dockDemandListStatusId;
    } else {
        json.status = null;
    }
    if (selectConditionOfDockDemandList.searchVal) {
        json.name = selectConditionOfDockDemandList.searchVal;
    }
    if (selectConditionOfDockDemandList.startTime) {
        json.createdAtStart = selectConditionOfDockDemandList.startTime;
    }
    if (selectConditionOfDockDemandList.endTime) {
        json.createdAt = selectConditionOfDockDemandList.endTime;
    }
    new NewAjax({
        type: "POST",
        url: "/f/projectDemandSignUp/pc/query_sign_up_project?pc=true",
        contentType: "application/json;charset=UTF-8",
        dataType: "json",
        data: JSON.stringify(json),
        success: function (res) {
            var totalRecord = res.data.total;
            var list = res.data.data_list;
            if (pageInOrTabDockDemand === 0){
                $('.dock-demand-splitpage >div').Paging({pagesize:searchSizeOfDockDemandList,count:totalRecord,toolbar:true});
                $('.dock-demand-splitpage >div').find("div:eq(1)").remove();
            }
            if (pageInOrTabDockDemand === 1){
                $('.dock-demand-splitpage >div').Paging({pagesize:searchSizeOfDockDemandList,count:totalRecord,toolbar:true});
                $('.dock-demand-splitpage >div').find("div:eq(0)").remove();
            }
            $('.dock-demand-total').html("共" + totalRecord + "条");

            dockDemandListData = list;
            setfileInfo();
            dockDemandListHandleClick();
            if (list.length === 0) {
                $('.dock-demand-list').find('.noData').remove();
                var div = '<div class="noData" style="width: 100%;height: 180px;line-height: 180px;text-align: center;background-color: white;color: #333333;font-size: 14px">暂无数据</div>';
                $('.dock-demand-list').append(div);
                $('.dock-demand-splitpage').css("display", "none");
                $('.dock-demand-total').css("display", "none");
            } else {
                $('.dock-demand-list').find('.noData').remove();
                $('.dock-demand-splitpage').css("display", "block");
                $('.dock-demand-total').css("display", "block");
            }
        }
    });
}

function setfileInfo() {
    var data = [];
    var table = new Table('dock-demand-list-table');
    var baseStyleArr = [];
    // 是否中标
    var isJoin = false;
    if (dockDemandListData != undefined && dockDemandListData.length != 0) {
        console.log(dockDemandListData);
        dockDemandListData.forEach(function(item) {
            var obj = {};
            // if (baseStyleArr.length === 0) {
            //     Object.keys(item).forEach(function(key) {
            //         var styleItem = {}
            //         styleItem.type = key;
            //         // console.log(key)
            //         switch (key) {
            //             case 'name':
            //                 styleItem.name = '需求名称';
            //                 styleItem.width = 200;
            //                 break;
            //             case 'is_winning':
            //                 styleItem.name = '雇佣状态';
            //                 break;
            //             case 'created_at':
            //                 styleItem.name = '报名时间';
            //                 styleItem.width = 120;
            //                 break;
            //             case 'publisher':
            //                 styleItem.name = '联系方式';
            //                 styleItem.width = 120;
            //                 break;
            //             case 'id':
            //                 styleItem.name = '操作';
            //                 styleItem.width = 360;
            //                 break;
            //             default:
            //                 // styleItem.name = key;
            //                 break
            //         }
            //         styleItem.align = 'left';
            //         baseStyleArr.push(styleItem);
            //         // console.log(baseStyleArr)
            //     })
            // }
            baseStyleArr = [
                {
                    type: 'name',
                    name: '需求名称',
                    width: '200',
                    align: 'left'
                },
                {
                    type: 'is_winning',
                    name: '雇用状态',
                    align: 'left'
                },
                {
                    type: 'created_at',
                    name: '报名时间',
                    width: '120',
                    align: 'left'
                },
                {
                    type: 'publisher',
                    name: '联系方式',
                    width: '120',
                    align: 'left'
                },
                {
                    type: 'id',
                    name: '操作',
                    width: '360',
                    align: 'left'
                }
            ]
            obj.name = [item.name, item.project_id];
            //加上一个判断来控制联系方式
            if (!!item.publisher) {
                //判is_winning空
                if (!!item.is_winning && JSON.parse(item.is_winning).id == 202078) {
                    obj.publisher = JSON.parse(item.publisher).phone;
                }else if (!!item.status && JSON.parse(item.status).id >= 202071 && JSON.parse(item.status).id <= 202074){
                    obj.publisher = JSON.parse(item.publisher).phone;
                }else {
                    obj.publisher = " ";
                }
            }else {
                obj.publisher = " ";
            }
            obj.created_at = fmtDate(item.created_at);
            if (!!item.is_winning) {
                if (!!item.status) {
                    obj.id = [item.id, JSON.parse(item.is_winning).id, item.project_id, JSON.parse(item.status).id];
                } else {
                    obj.id = [item.id, JSON.parse(item.is_winning).id, item.project_id, ];
                }
                if (!!item.status) {
                    if (JSON.parse(item.is_winning).id == 202078 && JSON.parse(item.status).id == 202074) {
                        obj.is_winning = '已完成';
                    } else if (JSON.parse(item.status).id == 202072 && JSON.parse(item.is_winning).id == 202078){
                        obj.is_winning = '验收中';
                    } else if (JSON.parse(item.status).id == 202073 && JSON.parse(item.is_winning).id == 202078){
                        obj.is_winning = '待评价';
                    }else {
                        obj.is_winning = JSON.parse(item.is_winning).title;
                    }
                } else {
                    obj.is_winning = JSON.parse(item.is_winning).title;
                }
            } else {
                if (!!item.status) {
                    obj.id = [item.id, , item.project_id, JSON.parse(item.status).id];
                } else {
                    obj.id = [item.id, , item.project_id, ];
                }
                obj.is_winning = '';
            }
            data.push(obj);
        })
    }
    var orderArr = ['name','is_winning','created_at','publisher','id'];
    table.setTableData(data);
    table.setBaseStyle(baseStyleArr);
    table.setColOrder(orderArr);
    table.setOpenCheckBox(true, 2).setTableLineHeight(40).resetHtmlCallBack(function (type, content, label) {
        if (type === 'size') {
            return (label === 'td') ? content + 'KB' : content;
        } else if (type === 'id') {
            if (label === 'td') {
                var span;
                var isJoin = (parseInt(content[1]) === 202078);
                var isPendingEmployment = (parseInt(content[1]) === 202077)
                var status = (parseInt(content[3]) === 202074);
                if (isJoin && status) {
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                        '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                        '<span class="viewComments" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看评价</span>' +
                        '<span class="submitResult" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="dockDemandListSubmitResult(' + content[2] + ')"></i>查看资料</span>';
                } else if (isJoin && !status && parseInt(content[3]) !== 202073) {
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                        '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                        '<span class="submitResult" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="dockDemandListSubmitResult(' + content[2] + ')"></i>提交验收资料</span>';
                }else if (isJoin && parseInt(content[3]) === 202073) {
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                        '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                        '<span class="submitResult" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer" onclick="dockDemandListSubmitResult(' + content[2] + ')"></i>查看验收资料</span>';
                } else if (status && isPendingEmployment) {
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                        '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                        '<span class="deleteDock" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>取消</span>' +
                        '<span class="viewComments" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看评价</span>';
                } else if (!status && isPendingEmployment){
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                    '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>' +
                    '<span class="deleteDock" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>取消</span>';
                }
                else {
                    span = '<span class="dockingInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>对接信息</span>' +
                        '<span class="lookInfo" data-id="' + content + '" style="display: inline-block;padding: 0 10px;height: 30px;background-color: #0066cc;border: none;line-height: 30px;text-align: center;margin-left: 5px;border-radius: 5px;color: white;cursor: pointer"></i>查看</span>';
                }
                return span;
            } else {
                return content;
            }
        } else if (type=== 'name') {
            var span = '<span title="'+content[0]+'" class="dockDemandListToDetail" data-proId="'+content[1]+'" style="cursor: pointer;color: #0066cc">'+ content[0] +'</span>'
            return (label === 'td') ? span : content
        }
    });
    table.createTable();
}

function dockDemandListHandleClick() {
    $(document).on('click','.dockDemandListToDetail',function () {
        var id = $(this).attr('data-proId')
        window.open('/f/'+ id +'/demand_detail.html?pc=true')
    })

    $('.dock-demand-list').find('.dockingInfo').click(function () {
        window.open('/f/projectDemandSignUp/' + $(this).attr('data-id').split(',')[0] + '/toDetail.html?pc=true');
    });
    $('.dock-demand-list').find('.lookInfo').click(function () {
        window.open('/f/' + $(this).attr('data-id').split(',')[2] + '/demand_detail.html?pc=true');
    });
    $('.dock-demand-list').find('.deleteDock').click(function () {
        var proId = $(this).attr('data-id').split(',')[2];
        var url = '/f/projectDemandSignUp/pc/'+ proId +'/cancel_sign_up?pc=true';
        layer.confirm('你确定要删除此需求？', {
            btn: ['确定','取消'] //按钮
        }, function(){

            pageInOrTabDockDemand = 1;
            currentPageOfDockDemandList = 1;

            new NewAjax({
                url: url,
                type: 'POST',
                contentType: "application/json;charset=UTF-8",
                dataType:"json",
                success: function (res) {
                    console.log(res);
                    if (res.status === 200) {
                        getDockDemandList()
                        layer.closeAll()
                    }else {
                        layer.open({
                            title: '温馨提示',
                            content: '内部信息出错'
                        })
                    }
                }
            });
        }, function(){
            layer.msg('取消了这次删除', {
                time: 1000, //20s后自动关闭
            });
        });
    })
    $('.dock-demand-list').find('.viewComments').click(function () {
        $('.dock-demand-list>div').removeClass('dockDemandListShow');
        $('.dock-demand-list>div').eq(1).addClass('dockDemandListShow');
        var proId = $(this).attr('data-id').split(',')[2]
        var url = '/f/serviceProvidersEvaluation/'+ proId +'/get_by_id?pc=true'
        new NewAjax({
            url: url,
            type: 'GET',
            contentType: "application/json;charset=UTF-8",
            dataType:"json",
            success: function (res) {
                console.log(res);
                if (!!res.data.data_object) {
                    if (!!res.data.data_object.comments) {
                        $('.dock-demand-list .feedback-content-textarea').text(res.data.data_object.comments);
                    } else {
                        $('.dock-demand-list .feedback-content-textarea').text('');
                    }
                    $('.dock-demand-list .feedback-content-textarea').attr('disabled','true');
                    var workSpeed = 195.6 * res.data.data_object.work_speed_star;
                    $('.dock-demand-list .workSpeed .stars-list').eq(0).css('width', workSpeed + 'px');

                    var workQuality = 195.6 * res.data.data_object.work_quality_star;
                    $('.dock-demand-list .workQuality .stars-list').eq(0).css('width', workQuality + 'px');

                    var workAttitude = 195.6 * res.data.data_object.work_attitude_star;
                    $('.dock-demand-list .workAttitude .stars-list').eq(0).css('width', workAttitude + 'px');
                    if (!!res.data.data_object.evaluation_type) {
                        var typeId = JSON.parse(res.data.data_object.evaluation_type).id
                        if (typeId == 202103) {
                            $('.dock-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.dock-demand-list .evaluationStar .evaluationSapn').eq(0).addClass('spanActive')
                        }else  if (typeId == 202104) {
                            $('.dock-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.dock-demand-list .evaluationStar .evaluationSapn').eq(1).addClass('spanActive')
                        }else {
                            $('.dock-demand-list .evaluationStar .evaluationSapn').removeClass('spanActive')
                            $('.dock-demand-list .evaluationStar .evaluationSapn').eq(2).addClass('spanActive')
                        }
                    }
                }
            }
        })
    })
    $('.dock-demand-list').find('span.back').click(function () {
        $('.dock-demand-list>div').removeClass('dockDemandListShow');
        $('.dock-demand-list>div').eq(0).addClass('dockDemandListShow');
    });
}

// 需求提交成果点击按钮事件
function dockDemandListSubmitResult(projectId) {
    // 获取成果对象
    var demandResult = new SubmitDemandResult();
    // 调用请求数据
    demandResult.getDemandResult(projectId,function () {
        $(".right-page").removeClass("page-active").siblings(".submit-demand-result-area").addClass("page-active");
    })
}