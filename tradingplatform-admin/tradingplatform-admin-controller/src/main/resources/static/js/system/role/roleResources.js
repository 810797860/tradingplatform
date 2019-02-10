/**
 * Created by mu-HUN on 2018/6/23.
 */
var SINGLE_CHOICE = 1011,
    MULTIPLE_CHOICE = 1012,
    MULTIPLE_TREE_CHOICE = 1013;

// 存储所选项
var selections = [];
var searchOptions = {};
// layer弹出层的下标
var layerIndex = 0;
var $table = $('#table'),
    $saveSel=$("#saveSelection"),
    $search = $('#search'),
    $remove = $('#remove');
var $authority = $('#authorityTable');
var SEARCHING = false;
$(function(){
    selections = initSeletor(selectedRoleResources);
    console.log("---------------------")
    console.log(selectedRoleResources)
    console.log(selections)
    initTable();
});
function initTable() {
    $table.bootstrapTable({
        height: getHeight,
        toolbar: "#toolbar",
        url: '/admin/resources/query',
        method: 'post',
        responseHandler: responseHandler,
        queryParams: queryParams,
        striped: true,
        showRefresh: true,
        showColumns: true,
        showToggle: true,
        detailView: true,
        minimumCountColumns: 2,
        pagination: true,
        pageList: "[15, 20, 25, 50, 100]",
        pageNumber: 1,
        pageSize: 15,
        showFooter: false,
        sidePagination: "server"
    });
    // sometimes footer render error.
    setTimeout(function () {
        $table.bootstrapTable('resetView');
    }, 200);
    $table.on('expand-row.bs.table', function (e, index, row, $detail) {
        var html = [];
        $.each(row, function (key, value) {
            html.push('<p><b>' + key + ':</b> ' + value + '</p>');
        });
        return $detail.html(html);
    }).on('check.bs.table', function (row) {
        $('.shuttle-add').attr('disabled',false).addClass('btn-primary');
    }).on('check-all.bs.table',function (rows) {
        $('.shuttle-add').attr('disabled',false).addClass('btn-primary');
    }).on('uncheck-all.bs.table',function (rows) {
        $('.shuttle-add').attr('disabled','disabled').removeClass('btn-primary');
    }).on('uncheck.bs.table',function (row) {
        if(!$table.bootstrapTable('getAllSelections').length) {
            $('.shuttle-add').attr('disabled','disabled').removeClass('btn-primary');
        }
    });
    $(window).resize(function () {
        $table.bootstrapTable('resetView', {
            height: getHeight()
        });
    });
    $search.on('click', function(e) {
        e.stopPropagation();
        searchInfo(true);
    });
    $("#search-form input.search-control").on('keydown',function(e){
        // e.preventDefault();
        e.stopPropagation();
        if(e.keyCode == "13") {
            searchInfo(true);
        }
    });

    // 搜索第二个表单域中绑定的事件
    $("#search-form1 input.search-control").on('keydown',function(e){
        // e.preventDefault();
        e.stopPropagation();
        if(e.keyCode == "13") {
            // searchInfo(true);
            var json = {}
            if (!!$('#title1').val()) {
                json.title = $('#title1').val();
            }
            if (!!$('#description1').val()) {
                json.description = $('#description1').val();
            }
            $.ajax({
                type:"post",
                url:'/role/'+roleId+'/role_permissions',
                contentType:'application/json',
                data:JSON.stringify(json),
                success:function(res){
                    initShuttle(res.data);
                },
                error:function(error){
                    console.log("提交失败");
                }
            })
        }
    });

    buttonClickSreach();
    $saveSel.off().on("click",function(e) {
        var data = [];
        for (var i = 0; i < selections.length;i++) {
            data.push(selections[i].id);
        }
        // 升序排序（从小到大，用于菜单）
        function sortUp(array){
            return array.sort(function (a, b) {
                return a - b;
            })
        }
        data = sortUp(data);
        submitData(data);
    })
    $(document).on('click','.shuttle-add',function (e) {
        e.stopPropagation();
        var selector = $table.bootstrapTable('getAllSelections');
        for(var i = 0; i < selector.length;i++) {
            var flag = true
            for(var j = 0; j < selections.length;j++) {
                if(selector[i].id === selections[j].id) flag=false
            }
            if(flag) {
                selections.push({
                    id: selector[i].id,
                    title: selector[i].title
                })
            }
            $authority.bootstrapTable('load',selections);
            $('.shuttle-add').attr('disabled','disabled').removeClass('btn-primary');
            $table.bootstrapTable('refresh');
        }
    }).on('click','.shuttle-delete',function (e) {
        e.stopPropagation();
        var selector = $authority.bootstrapTable('getAllSelections');
        var tempSelections = [];
        for(var i = 0; i < selections.length; i++) {
            var flag = true;
            for(var j = 0; j < selector.length; j++) {
                if(selections[i].id === selector[j].id) {
                    flag = false;
                    break;
                }
            }
            if(flag) {
                tempSelections.push({
                    id: selections[i].id,
                    title: selections[i].title
                })
            }
        }
        selections = tempSelections;
        $authority.bootstrapTable('load',selections);
        $('.shuttle-delete').attr('disabled','disabled').removeClass('btn-primary');
        $table.bootstrapTable('refresh');
    })
}
//删除数组元素
function deleteElem (array, value) {
    var newArr = [];
    for(var i = 0;i < array.length;i++) {
        if(array[i]===value) continue;
        newArr.push(array[i]);
    }
    return newArr;
}
//初始化选项
function initSeletor(data) {
    initShuttle(data);
    var list = []
    for(var i = 0;i < data.length;i++) {
        list.push({
            id: data[i].id,
            title: data[i].title
        });
    }
    return list
}
//初始化穿梭框
function initShuttle(list) {
    $authority.bootstrapTable({
        height: getHeight,
        data: [],
        striped: true,
        minimumCountColumns: 2,
        pagination: true,
        pageList: "[15, 20, 25, 50, 100]",
        pageNumber: 1,
        pageSize: 15,
        sidePagination: "client"
    });
    $authority.on('check.bs.table', function (row) {
        $('.shuttle-delete').attr('disabled',false).addClass('btn-primary');
    }).on('check-all.bs.table',function (rows) {
        $('.shuttle-delete').attr('disabled',false).addClass('btn-primary');
    }).on('uncheck-all.bs.table',function (rows) {
        $('.shuttle-delete').attr('disabled','disabled').removeClass('btn-primary');
    }).on('uncheck.bs.table',function (row) {
        if(!$authority.bootstrapTable('getAllSelections').length) {
            $('.shuttle-delete').attr('disabled','disabled').removeClass('btn-primary');
        }
    }).on('page-change.bs.table',function (number,size) {
        $('.shuttle-delete').attr('disabled','disabled').removeClass('btn-primary');
    });
    $authority.bootstrapTable('load',list);
}
//提交数据
function submitData(data){
    $.ajax({
        type:"post",
        url:'/admin/roleResources/create_update/' + roleId,
        contentType:'application/json',
        data:JSON.stringify(data),
        success:function(data){
            setTimeout(function () {
                layer.msg("提交成功", function(){});
            }, 1000);
            refreshTable();
        },
        error:function(error){
            console.log("提交失败");
        }
    })
}
// 刷新列表
function refreshTable() {
    $('#table').bootstrapTable('refresh',{pageNumber:1});
}

// 数据返回处理
function responseHandler(res) {
    var data=res;
    var data_list;
    data_list=CheckresponseHander(data);
    $('.shuttle-add').attr('disabled','disabled').removeClass('btn-primary');
    return {
        "total": res.recordsTotal,//总页数
        "rows": res.data   //数据
    };
}
function CheckresponseHander(res){
    var list=res.data;
    for(var i=0;i<selections.length;i++){
        for(var j=0;j<list.length;j++){
            if(list[j].id == selections[i].id)list[j].state=true;
            else continue;
        }
    }
    return list;
}
function searchInfo(flag) {
    SEARCHING = !!flag
    initSearchOptions();
    if (SEARCHING) $('#table').bootstrapTable('refresh', {pageNumber:1}); // 重置页码
    else $table.bootstrapTable(('refresh'));
}
function initSearchOptions() {
    searchOptions['title'] = $("#title").val();
    searchOptions['url'] = $("#url").val();
    searchOptions['description'] = $("#description").val();
}
//表格数据获取的参数
function queryParams(params) {
    var page = {};
    page.current = params.offset/params.limit + 1;
    page.size = params.limit;
    var sorts = [];
    var defaultSort = {};
    defaultSort.field = 'id';
    defaultSort.isAsc = true;
    sorts.push(defaultSort);
    var postData = {
        page: page,
        sorts:sorts
    };
    if(StringNoEmpty(searchOptions.title))postData['title'] = searchOptions.title;
    if(StringNoEmpty(searchOptions.url))postData['url'] = searchOptions.url;
    if(StringNoEmpty(searchOptions.description))postData['description'] = searchOptions.description;
    return postData;
}
// 获取bootstrap table高度
function getHeight() {
    var searchHeight = 0;
    if($("#search-part").css('display') !== 'none') {
        searchHeight = $('#search-part').height();
    }
    return $(window).height() - 20 - searchHeight;
}
// 刷新列表并且弹出提示信息
function refreshAndShowMessage(options){
    new PNotify(options);
    $('#table').bootstrapTable('refresh');
}
// 确定按钮控制
function confirmHandle() {
    var selectionsLen = selections.length;
    if(selectionsLen === 0) {
        layer.msg("未选择任何项！", function(){});
        return;
    }
    switch(parseInt(relevanceObj['fieldType'])) { // 转换为数字
        case SINGLE_CHOICE:
            if(selectionsLen > 1) {
                layer.msg("只能选择一项！", function(){});
                return;
            }
            break;
        case MULTIPLE_CHOICE:
        case MULTIPLE_TREE_CHOICE:
            break;
    }
    if(parent.window.handleRelevance) {
        parent.window.handleRelevance(selections, relevanceObj.selector);
        layerIndex = parent.layer.getFrameIndex(window.name);
        parent.layer.close(layerIndex);
    }
}
// 撤销控制操作
function repealHandle() {
    if(parent.layer) {
        parent.window.handleRelevance([], relevanceObj.selector);
        layerIndex = parent.layer.getFrameIndex(window.name);
        parent.layer.close(layerIndex);
    }
}
// 消息框位置控制
var stack_bottomright = {"dir1": "up", "dir2": "left", "firstpos1": 25, "firstpos2": 25};
function StringNoEmpty(str){
    if(str!=null&&str!=""&&str!=undefined){
        return true;
    }else return false;
}

// 搜索按钮点击事件
function buttonClickSreach () {
    $('#search1').click(function () {
        var postData = {};
        if (!!$('#title1').val()) {
            postData.title = $('#title1').val();
        }
        if (!!$('#description1').val()) {
            postData.description = $('#description1').val();
        }
        $.ajax({
            type:"post",
            url:'/admin/resources/query_by_role/'+ roleId,
            contentType:'application/json',
            data:JSON.stringify(postData),
            success:function(res){
                initShuttle(res.data);
            },
            error:function(error){
                console.log("提交失败");
            }
        })
    })
}