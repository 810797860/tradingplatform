function Table(parentId) {
    var _this = this;
    // 定义colGroup标签html
    var colGroupHtml = '<colgroup></colgroup>';
    // 定义col标签html
    var colHtml = '<col>';
    // 定义tbody标签的html
    var tbodyHtml = '<tbody></tbody>';
    // 定义tr标签的html
    var trHtml = '<tr></tr>';
    // 定义th标签的html
    var thHtml = '<th></th>';
    // 定义td标签的html
    var tdHtml = '<td></td>';
    // 存储样式格式
    var baseStyleArr = [];
    // 存储数据源
    var tableData = [];
    // 是否开启序号
    var isIndexEnable = true;
    // 是否开启复选框
    var isCheckBoxEnable = false;
    // 复选框模式
    var checkBoxModel = 1;
    // 存储行高
    var tableLineHeight = 30;
    // 是否生成table
    var isCreateFinish = false;
    // 存储自定义回调
    var resetCallback = null;
    // 存储列顺序数组
    var colOrderArr = [];
    // 限制列显示
    var showColArr = [];
    // 存储点击回调
    var clickCallback = null;
    // 存储选中的下标
    var selectIndexArr = [];
    // 是否存创建过一遍
    var isDealWidth = false;
    // 存储table父级节点
    var tableNode = $('#' + parentId).find('.global-table').eq(0);

    /*=== 调用函数 ===*/
    eventOfTableClick();

    /*=== 功能函数 ===*/

    // 初始化表格
    function createdTable() {
        var table = tableNode;
        var result = '';
        var colStr = '';
        var thStr = '';
        var tdStr = '';
        // 遍历数据 ， col, th, td
        tableData.forEach(function (dataItem, itemIndex) {
            // 判别是否需要补全限制
            if (showColArr.length === 0) {
                showColArr = Object.keys(dataItem);
            }
            // 获取列顺序 (只需要自行一次)
            if (itemIndex === 0) {
                if (colOrderArr.length === 0) {
                    colOrderArr = Object.keys(dataItem);
                } else {
                    // 遍历数据源子项，将colOrderArr缺的数据补上
                    Object.keys(dataItem).forEach(function (key) {
                        if (colOrderArr.searchArrayObj(key) < 0) {
                            colOrderArr.push(key);
                        }
                    });
                }
            }
            // 当允许序号
            if (isIndexEnable && !isDealWidth) {
                showColArr.unshift('index');
                colOrderArr.unshift('index');
            }
            // 当允许复选框
            if (isCheckBoxEnable && !isDealWidth) {
                // 只有有序号才能开启2模式
                if (checkBoxModel === 2 && isIndexEnable) {
                    showColArr[0] = 'checkIndex';
                    colOrderArr[0] = 'checkIndex';
                } else {
                    showColArr.unshift('check');
                    colOrderArr.unshift('check');
                    // console.log('只有开启了序号才能开启复选框2，否则添加复选框1')
                }
            }
            // 当还没设置列宽的时候
            if (colStr.length === 0) {
                // 字符串替换
                colStr = colGroupHtml.replace(/><\/colgroup>/, function () {
                    // 临时存储
                    var stSave = '';
                    // 判定基础样式是否有
                    if (baseStyleArr.length > 0) {
                        // 遍历列顺序
                        colOrderArr.forEach(function (colItem) {
                            if (showColArr.searchArrayObj(colItem) > -1) {
                                stSave += colHtml.replace(/>/, function () {
                                    // 查找当前type在基础样式中的对应项
                                    var obj = baseStyleArr.searchArrayObj(colItem, 'type', true);
                                    // 存储实际宽度
                                    var trueWidth = null;
                                    // 判定obj情况返回
                                    if (obj !== -1 && obj.width !== undefined) {
                                        trueWidth = ' width="' + parseInt(obj.width) + 'px">';
                                    } else if (colItem === 'index') {
                                        trueWidth = ' width="60px">';
                                    } else if (colItem === 'check') {
                                        trueWidth = ' width="40px">';
                                    } else if (colItem === 'checkIndex') {
                                        trueWidth = ' width="80px">';
                                    } else {
                                        trueWidth = '>';
                                    }
                                    return trueWidth;
                                })
                            }
                        })
                    } else {
                        colOrderArr.forEach(function (colItem, index) {
                            //data-type="' + (colItem) + '"
                            if (colItem === 'index') {
                                stSave += colHtml.replace(/>/, ' width="60px">');
                            } else if (colItem === 'check') {
                                stSave += colHtml.replace(/>/, ' width="40px">');
                            } else if (colItem === 'checkIndex') {
                                stSave += colHtml.replace(/>/, ' width="80px">');
                            } else {
                                if (index === colOrderArr.length - 1) {
                                    stSave += colHtml;
                                } else {
                                    stSave += colHtml.replace(/>/, ' width="' + (100 / colOrderArr.length) + '%">');
                                }
                            }
                        })
                    }
                    return '>' + stSave + '</colgroup>';
                })
                result = colStr + tbodyHtml;
            }
            // 当还没设置表头
            if (thStr.length === 0) {
                thStr = trHtml.replace(/><\/tr>/, function () {
                    var stSave = '';
                    colOrderArr.forEach(function (colItem) {
                        if (showColArr.searchArrayObj(colItem) > -1) {
                            // 存储回调的返回值
                            var resetStr = '';
                            // 存储基础样式中的对应项
                            var obj = baseStyleArr.searchArrayObj(colItem, 'type', true);
                            // 获取默认要填写的内容
                            var name = (obj !== -1 && obj.name !== undefined) ? obj.name : colItem;
                            // 临时存储
                            var indexCheckSave = '';
                            stSave += thHtml.replace(/><\/th>/, function () {
                                // 若自定义回调
                                if (resetCallback) {
                                    // 获取自定义
                                    resetStr = resetCallback(colItem, name, 'th');
                                }
                                if (colItem === 'checkIndex' || colItem === 'check' || colItem === 'index') {
                                    if (!!resetStr) {
                                        indexCheckSave = resetStr;
                                    } else {
                                        // 为0的情况
                                        if (typeof resetStr === "number") {
                                            indexCheckSave = resetStr;
                                        } else if (colItem === 'checkIndex') {
                                            indexCheckSave = '<input type="checkbox" name="selectAll"><p style="display: inline;margin-left: 10px">' + (name !== 'checkIndex' ? name : '序号') + '</p>';
                                        } else if (colItem === 'check') {
                                            indexCheckSave = '<input type="checkbox" name="selectAll">';
                                        } else {
                                            indexCheckSave = (name !== 'index' ? name : '序号');
                                        }
                                    }
                                    return ' data-type="' + (colItem) + '" style="text-align:' + ((obj !== -1 && obj.align !== undefined) ? obj.align : (colItem === 'checkIndex') ? 'left' : 'center') + '">' + indexCheckSave + '</th>';
                                } else {
                                    // 根据自定义回调返回值赋值
                                    return ' data-type="' + colItem + '" style="text-align:' + ((obj !== -1 && obj.align !== undefined) ? obj.align : 'center') + '">' + ((!!resetStr) ? resetStr : name) + '</th>';
                                }
                            })
                        }
                    });
                    return ' style="height:' + tableLineHeight + 'px">' + stSave + '</tr>';
                });
                result = result.replace(/<\/tbody>/, thStr + '</tbody>');
            }
            // 清空td
            tdStr = '';
            // 开始设置td
            tdStr += trHtml.replace(/><\/tr>/, function () {
                var stSave = '';
                colOrderArr.forEach(function (colItem) {
                    if (showColArr.searchArrayObj(colItem) > -1) {
                        // 存储基础样式中的对应项
                        var obj = baseStyleArr.searchArrayObj(colItem, 'type', true);
                        // 暂时存储
                        var indexCheckSave = '';
                        // 存储回调的返回值
                        var resetStr = '';
                        stSave += tdHtml.replace(/><\/td>/, function () {
                            // 若自定义回调存在
                            if (resetCallback) {
                                // 获取自定义内容
                                resetStr = resetCallback(colItem, dataItem[colItem], 'td');
                            }
                            // 序号/复选框区域
                            if (colItem === 'checkIndex' || colItem === 'check' || colItem === 'index') {
                                // 自定义内容不为undefined/null/空
                                if (!!resetStr) {
                                    indexCheckSave = resetStr;
                                } else { // 自定义内容为undefined/null/空
                                    // 为0的情况
                                    if (typeof resetStr === "number") {
                                        indexCheckSave = resetStr;
                                    } else if (colItem === 'checkIndex') {
                                        // 自定义不能用，根据数据源进行写入
                                        indexCheckSave = '<input type="checkbox" name="selectItem" data-index="' + itemIndex + '"><p style="display: inline;margin-left: 10px">' + (itemIndex + 1) + '</p>';
                                    } else if (colItem === 'check') {
                                        // 自定义不能用，回复复选框
                                        indexCheckSave = '<input type="checkbox" name="selectItem" data-index="' + itemIndex + '">';
                                    } else {
                                        // 自定义不能用，回复序号
                                        indexCheckSave = itemIndex + 1;
                                    }
                                }
                                return ' data-type="' + colItem + '" style="text-align:' + ((obj !== -1 && obj.align !== undefined) ? obj.align : (colItem === 'checkIndex') ? 'left' : 'center') + '">' + indexCheckSave + '</th> ';
                            } else { // 内容区域
                                // 存储数据 (序号/复选框不会用到)
                                var content = (!!dataItem[colItem]) ? dataItem[colItem] : (typeof dataItem[colItem] === "number") ? dataItem[colItem] : '';
                                // 根据自定义回调返回值赋值
                                return ' data-type="' + colItem + '" style="text-align:' + ((obj !== -1 && obj.align !== undefined) ? obj.align : 'center') + '">' + ((!!resetStr) ? resetStr : content) + '</td>';
                            }
                        })
                    }
                });
                return ' style="height:' + tableLineHeight + 'px">' + stSave + '</tr>';
            });
            result = result.replace(/<\/tbody>/, tdStr + '</tbody>');
        });
        table.html(result);
    }

    // 点击回调
    function eventOfTableClick() {
        var table = tableNode;
        table.off('click').on('click', function (event) {
            var nowNode = $(event.target);
            if (nowNode.get(0).tagName.toLowerCase() === 'input' && nowNode.attr('type') === 'checkbox') {
                clickCheckBox(nowNode);
            }
            if (clickCallback) {
                clickCallback(nowNode);
            }
        })
    }

    // 复选框点击处理函数
    function clickCheckBox(node) {
        var checkAll = tableNode.find('input[name="selectAll"]').eq(0);
        var checkItem = tableNode.find('input[name="selectItem"]');
        var name = node.attr('name');
        if (name === 'selectAll') {
            if (node.prop('checked')) {
                checkItem.prop('checked', true);
                tableData.forEach(function (item, index) {
                    selectIndexArr.push(index);
                })
            } else {
                checkItem.prop('checked', false);
                selectIndexArr.splice(0, selectIndexArr.length);
            }
        } else if (name === 'selectItem') {
            var index = node.data().index;
            if (node.prop('checked')) {
                selectIndexArr.push(index);
                if (selectIndexArr.length === tableData.length) {
                    checkAll.prop('checked', true);
                }
            } else {
                selectIndexArr.splice(selectIndexArr.searchArrayObj(index), 1);
                if (checkAll.prop('checked')) {
                    checkAll.prop('checked', false);
                }
            }
        }
    }

    /*=== 调用方法 ===*/
    // 设置基础样式
    _this.setBaseStyle = function (styleArr) {
        try {
            if (styleArr === undefined) {
                throw new Error('styleArr为undefined，将不会设置自定义样式')
            } else if (!(styleArr instanceof Array)) {
                throw new Error('styleArr格式错误，需为数组（Array）')
            } else if (styleArr.length > 0) {
                styleArr.forEach(function (item) {
                    if (!item.type && typeof item.type !== "number") {
                        throw new Error('styleArr中type不能为undefined/null/空字符串')
                    }
                })
                baseStyleArr = JSON.parse(JSON.stringify(styleArr))
            }
            /*else if (styleArr.length === 0){
                throw new Error('styleArr为空数组，将不会设置自定义样式')
            }*/
        } catch (err) {
            console.error('table_error: setBaseStyle ' + err)
        }
        return _this
    };
    // 设置表格数据
    _this.setTableData = function (data) {
        try {
            if (data === undefined) {
                throw new Error('data不能为undefined')
            } else if (!(data instanceof Array)) {
                throw new Error('data格式错误，需为数组（Array）')
            }
            if (data.length > 0) {
                if (tableNode.css('display') === 'none') {
                    tableNode.removeAttr('style')
                }
                data.forEach(function (item) {
                    if (typeof item !== "object") {
                        throw new Error('data的子项必须为对象')
                    } else if (!(/[^{}\s]/.test(JSON.stringify(item)))) {
                        throw new Error('data的子项不能为空对象')
                    }
                });
                // table展示
                tableNode.show();
                tableData = JSON.parse(JSON.stringify(data));
                // 重置第二次写入
                isCreateFinish = false
            } else {
                // 隐藏
                tableNode.hide();
                tableData = []
            }
        } catch (err) {
            console.error('table_error: setTableData ' + err)
        }
        return _this
    };
    // 创建表格
    _this.createTable = function () {
        isCreateFinish = true
        isDealWidth = true
        if (tableData.length > 0) {
            createdTable()
        }
    };
    // 设置自定义内容回调
    _this.resetHtmlCallBack = function (callback) {
        try {
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则无法设置自定义内容')
            } else if (callback === undefined) {
                throw new Error('callback为undefined，将不会设置自定义内容')
            } else if (typeof callback !== "function") {
                throw new Error('callback格式错误，需为函数（function）')
            } else if (resetCallback === null) {
                resetCallback = callback
            }
            /*else if (resetCallback !== null) {
                // 暂时关闭此提示
                throw new Error('resetHtmlCallBack 不支持多次调用')
                // return 0
            }*/
        } catch (err) {
            console.error('table_error: resetHtmlCallBack ' + err)
        }
        return _this
    };
    // 设置列顺序
    _this.setColOrder = function (orderArr) {
        try {
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则无法设置列顺序');
            } else if (orderArr === undefined) {
                throw new Error('orderArr为undefined，将不会设置自定义列顺序');
            } else if (!(orderArr instanceof Array)) {
                throw new Error('orderArr格式错误，需为数组（array）');
            } else if (orderArr.length > 0) {
                colOrderArr = JSON.parse(JSON.stringify(orderArr));
            }
            /*else if (orderArr.length === 0){
                throw new Error('styleArr为空数组，将不会设置自定义列顺序')
            }*/
        } catch (err) {
            console.error('table_error: setColOrder ' + err);
        }
        return _this;
    };
    // 设置是否启用序号
    _this.setOpenIndex = function (isOpen) {
        try {
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则默认设定序号');
            } else if (isOpen === undefined) {
                throw new Error('isOpen为undefined，将默认设定序号');
            } else if (typeof isOpen !== "boolean") {
                throw new Error('isOpen格式错误，需为布尔值(boolean）');
            }
            isIndexEnable = isOpen;
        } catch (err) {
            console.error('table_error: setOpenIndex ' + err);
        }
        return _this;
    };
    // 设置是否启用复选框
    _this.setOpenCheckBox = function (isOpen, model) {
        try {
            if (model === undefined) {
                model = 1
            }
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则无法开启复选框')
            } else if (isOpen === undefined) {
                throw new Error('isOpen为undefined，将无法设定复选框的开启')
            } else if (typeof isOpen !== "boolean") {
                throw new Error('isOpen格式错误，需为布尔值(boolean）')
            } else if (typeof model !== "number" && model !== 1 && model !== 2) {
                throw new Error('model需为数值的1或2')
            }
            isCheckBoxEnable = isOpen;
            checkBoxModel = model;
        } catch (err) {
            console.error('table_error: setOpenCheckBox ' + err);
        }
        return _this;
    };
    // 设置显示列
    _this.setShowColArr = function (colArr) {
        try {
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则无法限制列显示');
            } else if (colArr === undefined) {
                throw new Error('colArr为undefined，将不会限制列显示');
            } else if (!(colArr instanceof Array)) {
                throw new Error('colArr格式错误，需为数组（array）');
            } else if (colArr.length > 0) {
                showColArr = JSON.parse(JSON.stringify(colArr));
            }
            /*else if (colArr.length === 0){
                throw new Error('styleArr为空数组，将不会设置自定义列顺序')
            }*/
        } catch (err) {
            console.error('table_error: setShowColArr ' + err);
        }
        return _this;
    };
    // 设置行高
    _this.setTableLineHeight = function (height) {
        try {
            if (isCreateFinish) {
                throw new Error('请在"createTable"方法前调用，否则无法自定义行高')
            } else if (height === undefined) {
                throw new Error('height为undefined，将使用默认行高')
            } else if (typeof height !== "number") {
                throw new Error('height格式错误，需为数值(number）')
            }
            tableLineHeight = height
        } catch (err) {
            console.error('table_error: setTableLineHeight ' + err)
        }
        return _this
    };
    // 获取选中的下标数组
    _this.getSelectArr = function (callback) {
        try {
            if (callback === undefined) {
                throw new Error('callback为undefined')
            } else if (typeof callback !== "function") {
                throw new Error('callback格式错误，需为函数(function）')
            }
            if (callback) {
                callback(selectIndexArr)
            }
        } catch (err) {
            console.error('table_error: getSelectArr ' + err)
        }
        return _this
    };
    // 设置点击回调方法
    _this.setClickCallback = function (callback) {
        try {
            if (callback === undefined) {
                throw new Error('callback为undefined')
            } else if (typeof callback !== "function") {
                throw new Error('callback格式错误，需为函数(function）')
            }
            clickCallback = callback
        } catch (err) {
            console.error('table_error: setClickCallback ' + err)
        }
        return _this
    };
}