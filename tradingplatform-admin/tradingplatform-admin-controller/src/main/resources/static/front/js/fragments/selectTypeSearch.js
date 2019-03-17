/*===方法函数===*/

// 定义类
function TypeSearch() {
    // 存储筛选类型的html代码
    var selectTypeHtml = '<li class="type-item" style="" type=""><span class="type"></span><div class="option-area option-area-hidden"></div><span class="open-all" name="hidden" style="">更多<i class="icon-open-arrow" style="pointer-events: none"></i></span></li>';
    // 存储筛选子项的html代码
    var selectTypeOptionHtml = '<span class="option" data-id="" title=""></span>';
    //定义this
    var _this = this;
    // 获取地区数据（调用了后端插件的数据）
    var addressData = window.ChineseDistricts;
    // 获取筛选选项的数据(默认数据)
    var RawData = [];
    // 获取省份
    var provinceArr = [{
        id: 0,
        title: '不限'
    }];
    // 默认地址格式
    var defaultAddressFormat = [
        {
            name: '按省份',
            type: 'province',
            active: 0,
            selectMore: false,
            isImportant: false,
            data: provinceArr
        },
        {
            name: '按市级',
            type: 'city',
            active: 0,
            selectMore: false,
            isImportant: false,
            data: [{id: 0, title: '不限'}].concat([addressData]),
            linkMethod: function () {
                var _this = this,
                    provinceId = _this.selectData.province,
                    data = _this.city[1][provinceId],
                    cityData = [{id: 0, title: '不限'}];
                if (data) {
                    Object.keys(data).forEach(function (key) {
                        var obj = {};
                        obj.id = Number(key);
                        obj.title = data[key];
                        cityData.push(obj);
                    });
                    return cityData;
                } else {
                    return null;
                }
            }
        },
        {
            name: '按地区',
            type: 'area',
            active: 0,
            selectMore: false,
            isImportant: false,
            data: [{id: 0, title: '不限'}].concat([addressData]),
            linkMethod: function () {
                var _this = this,
                    cityId = _this.selectData.city,
                    data = _this.area[1][cityId],
                    areaData = [{id: 0, title: '不限'}];
                if (data) {
                    Object.keys(data).forEach(function (key) {
                        var obj = {};
                        obj.id = Number(key);
                        obj.title = data[key];
                        areaData.push(obj);
                    });
                    return areaData;
                } else {
                    return null;
                }
            }
        }
    ];
    // 获取类型多选框展示区域
    var typeSearchListShowDiv = $('.select-type-search .select-area').eq(0);
    // 获取类型多选框列表区域
    var typeSearchListDiv = $('.select-type-search .type-ul').eq(0);
    // 获取类型多选框
    var selectTypeSearch = $('.select-type-search').eq(0);
    // 获取多选框输入框
    var searchInput = $('#search-input');
    // 存储数组验证
    var isDataTrue = false;
    // 是否加入默认地址
    var addDefaultAddress = false;
    // 存储点击回调函数
    var optionClickCallBack = undefined;
    // 存储搜索按钮的回调函数
    var searchBtnClickCallBack = undefined;
    // 存储input的回调函数
    var searchInputKeyDownCallBack = undefined;
    // 获取可以多选的类型名称数组
    var canSelectMoreTypeArr = [];
    // 获取不限/全部的id数组
    var selectAllIdArr = [];
    // 是否引入了jquery
    var isHaveJQuery = false;
    // 存储列表数据和选中数据
    var _oListData = null;
    // 数据关联方法集合
    var _oDataLink = null;
    // 写入数据源
    var _writeData = null;
    // 超出界限
    var _overIndex = 7;
    // 是否需要底部模块
    var bNeedBottomModel = true;
    // 是否为初始化模式
    var bIsInitModel = true;


    //判定是否引入jquery
    if (!$ && !JQuery) {
        console.error('本组件需jquery支持，请先引入jquery');
        isHaveJQuery = false;
        return 0;
    } else {
        isHaveJQuery = true;
    }

    /*=== 内部功能函数 ===*/

    // 提取数据
    function _extractListData(data) {
        // 初始化列表数据和选中数据
        if (_oListData === null) {
            _oListData = {};
            _oListData.selectData = {};
        }
        // 初始化关联方法
        if (_oDataLink === null) {
            _oDataLink = {};
        }
        data.forEach(function (dataItem) {
            if (dataItem.data) {
                _oListData[dataItem.type] = JSON.parse(JSON.stringify(dataItem.data));
            } else {
                _oListData[dataItem.type] = null;
            }
            // 获取选中数据
            if (dataItem.active !== undefined) {
                if (typeof dataItem.active === "number" || dataItem.active instanceof Array) {
                    _oListData.selectData[dataItem.type] = dataItem.active;
                } else if (typeof dataItem.active === "string") {
                    _oListData.selectData[dataItem.type] = Number(dataItem.active);
                } else {
                    _oListData.selectData[dataItem.type] = null;
                }
            } else {
                _oListData.selectData[dataItem.type] = null;
            }
            if (dataItem.linkMethod !== undefined) {
                _oDataLink[dataItem.type] = dataItem.linkMethod.bind(Object.create(_oListData));
            }
        });
    }

    // 查看选中数据对应的下标是否有超出界限
    function isSelectIndexOver(selectData, listData) {
        var isOver = false;
        if (selectData === null) {
            return false;
        } else {
            if (selectData instanceof Array) {
                for (var item in selectData) {
                    if (listData.searchArrayObj(item, 'id') > _overIndex - 1) {
                        isOver = true;
                    }
                    break;
                }
            } else {
                if (listData.searchArrayObj(selectData, 'id') > _overIndex - 1) {
                    isOver = true;
                }
            }
            return isOver;
        }
    }

    // 获取省份数据
    function _getProvinceArr() {
        var data = addressData['86'];
        // 初始化省数组
        if (provinceArr.length > 1) {
            provinceArr.splice(1, provinceArr.length - 1);
        }
        Object.keys(data).forEach(function (key) {
            var obj = {};
            obj.id = Number(key);
            obj.title = data[key];
            provinceArr.push(obj);
        })
    }

    // 初始化类型多选框
    function _createTypeSearch() {
        var selectContent = '';
        var itemRule = /<li class="type-item" style="" type="">/;
        var titleRule = /<span class="type"><\/span>/;
        var contentDivRule = /<div class="option-area((?!<\/div>).)+<\/div>/;
        var moreRule = /<span class="open-all"((?!<\/span>).)+<\/span>/;
        // 暂时存储
        var stSave = null;

        if (!_writeData) {
            _writeData = JSON.parse(JSON.stringify(RawData));
        }
        // 提取写入数据
        _writeData.forEach(function (itemData) {
            if (_oDataLink[itemData.type] !== undefined) {
                stSave = _oDataLink[itemData.type]();
                // 在非初始化模式下才执行，保证初始化时默认选中项不会被修改
                if (!bIsInitModel) {
                    // 在未返回列表数据 或 列表数据与前一次数据不一致的情况下
                    if (!stSave || (stSave && !itemData.data) || (itemData.data && stSave && JSON.stringify(stSave) !== JSON.stringify(itemData.data))) {
                        if (itemData.data) {
                            _oListData.selectData[itemData.type] = itemData.data[0].id;
                        } else if (stSave) {
                            _oListData.selectData[itemData.type] = stSave[0].id;
                        } else {
                            _oListData.selectData[itemData.type] = null;
                        }
                    }
                }
                itemData.data = (stSave !== undefined) ? stSave : null;
            }
        });
        _writeData.forEach(function (typeData) {
            // 提取可以多选的类型名
            if (typeData.selectMore) {
                $.inArray(typeData.type, canSelectMoreTypeArr) < 0 && canSelectMoreTypeArr.push(typeData.type);
            }
            // 修改标题
            selectContent += selectTypeHtml.replace(titleRule, function () {
                if (typeData.isImportant) {
                    return '<span class="type type-important"><i class="icon-star"></i>' + typeData.name + '：</span>';
                } else {
                    return '<span class="type">' + typeData.name + '：</span>';
                }
            }).replace(contentDivRule, function (divStr) {
                if (typeData.data !== null) {
                    // 判定是否需要删除 option-area-hidden 来展开列表
                    if (isSelectIndexOver(_oListData.selectData[typeData.type], typeData.data)) {
                        divStr = divStr.replace('option-area-hidden', '');
                    }
                    // 初始化
                    stSave = '';
                    typeData.data.forEach(function (optionData, index) {
                        // 获取每类不限的id,需要保证是number
                        if (optionData.title === '不限' || optionData.title === '全部') {
                            $.inArray(optionData.id, selectAllIdArr) < 0 && selectAllIdArr.push(parseInt(optionData.id));
                        }
                        // 生成html
                        stSave += selectTypeOptionHtml.replace(/<\/span>/, function (str) { // 写入选项
                            if (typeof optionData === "object") {
                                return '<p class="type-item-text">' + optionData.title + '</p>' + str;
                            } else if (typeof optionData === "string") {
                                return '<p class="type-item-text">' + optionData.title + '</p>' + str;
                            }
                        }).replace(/title=""/, function () {
                            if (typeof optionData === "object") {
                                return 'title="' + optionData.title + '"';
                            } else if (typeof optionData === "string") {
                                return 'title="' + optionData + '"';
                            }
                        }).replace(/data-id=""/, function () {
                            if (typeof optionData === "object") {
                                return 'data-id="' + optionData.id + '"';
                            } else if (typeof optionData === "string") {
                                return 'data-id="' + optionData + '"';
                            }
                        }).replace(/class="((?!")[^"]*)"/, function (classStr) { // 判定显示选中项
                            var selectIndex = 0;
                            if (_oListData.selectData[typeData.type] !== null) {
                                if (_oListData.selectData[typeData.type] instanceof Array) {
                                    selectIndex = $.inArray(Number(optionData.id), _oListData.selectData[typeData.type]);
                                    if (selectIndex > -1) {
                                        return classStr.slice(0, -1) + ' active"';
                                    } else {
                                        return classStr;
                                    }
                                } else if (_oListData.selectData[typeData.type] === Number(optionData.id)) {
                                    return classStr.slice(0, -1) + ' active"';
                                } else {
                                    return classStr;
                                }
                            } else {
                                if (index === 0) {
                                    return classStr.slice(0, -1) + ' active"';
                                } else {
                                    return classStr;
                                }
                            }
                        })
                    });
                    return divStr.slice(0, divStr.indexOf('>') + 1) + stSave + divStr.slice(-6);
                } else {
                    return divStr;
                }
            }).replace(moreRule, function (moreStr) { // 控制更多按钮的展示
                if (typeData.data !== null) {
                    if (typeData.data.length > _overIndex) {
                        // 选中项对应下标超出一行的个数限制
                        if (isSelectIndexOver(_oListData.selectData[typeData.type], typeData.data)) {
                            return moreStr.replace(/style=""/, '')
                                .replace('name="hidden"', 'name="show"')
                                .replace('更多', '收起')
                                .replace('class="icon-open-arrow"', 'class="icon-close-arrow"');
                        } else {
                            return moreStr.replace(/style=""/, '');
                        }
                    } else {
                        return moreStr.replace(/style=""/, 'style="display:none"');
                    }
                } else {
                    return moreStr.replace(/style=""/, 'style="display:none"');
                }
            }).replace(itemRule, function () { // 控制列表显不显示
                if (typeData.data === null) {
                    return '<li class="type-item" style="display: none" type="' + typeData.type + '">';
                } else {
                    return '<li class="type-item" style="display: block" type="' + typeData.type + '">';

                }
            })
        });
        typeSearchListDiv.html(selectContent);
        // 初始化结束，修改初始化模式
        if (bIsInitModel) {
            bIsInitModel = false;
        }
    }

    // 类型复选框的点击事件
    function _eventOfSelectTypeSearchClick() {
        var parent, typeName, stSave;
        // var defaultAddressTypeArr = ['province', 'city'];
        selectTypeSearch.mouseup(function (event) {
            // 获取当前点击的节点
            var nowNode = $(event.target),
                actNodes;
            // 当前节点是选中项
            if (nowNode.hasClass('option') && event.target.tagName.toLowerCase() === 'span' || nowNode.parents('span').length > 0) { // 这里不能限制是否已被选中的原因：可以多选
                if (event.target.tagName.toLowerCase() !== 'span') {
                    nowNode = nowNode.parents('span').eq(0);
                }
                parent = nowNode.parents('li.type-item').eq(0);
                typeName = parent.attr('type');
                actNodes = parent.find('span[class$="active"]');
                // 只能单选
                if ($.inArray(typeName, canSelectMoreTypeArr) < 0) {
                    // 当前选项没被选中
                    if (!nowNode.hasClass('active')) {
                        _oListData.selectData[typeName] = Number(nowNode.data('id'));
                        // 在这里执行的意义：防止在已选中的情况下，点击做无用功
                        _createTypeSearch();
                    }
                } else { // 可以多选
                    if (nowNode.hasClass('active')) {// 点击了选中项
                        if (actNodes.length > 1) {
                            stSave = $.inArray(nowNode.data('id'), _oListData.selectData[typeName]);
                            _oListData.selectData[typeName].splice(stSave, 1);
                        }
                    } else {
                        // 若点击了“不限”
                        if ($.inArray(nowNode.data().id, selectAllIdArr) > -1) {
                            _oListData.selectData[typeName] = [nowNode.data().id];
                        } else {
                            // 在选中“不限”的情况下，点击其他项
                            if ($.inArray(actNodes.eq(0).data().id, selectAllIdArr) > -1) {
                                _oListData.selectData[typeName] = [nowNode.data().id];
                            } else {
                                _oListData.selectData[typeName].push(nowNode.data().id);
                            }
                        }
                    }
                    _createTypeSearch();
                }
                // 回调不为空
                if (optionClickCallBack) {
                    optionClickCallBack(nowNode, parent);
                }
            } else {
                if (nowNode.hasClass('open-all')) {
                    if (nowNode.attr('name') === 'hidden') {
                        nowNode.prev().removeClass('option-area-hidden');
                        nowNode.html('收起<i class="icon-close-arrow" style="pointer-events: none"></i>');
                        nowNode.attr({name: 'show'});
                    } else {
                        nowNode.prev().addClass('option-area-hidden');
                        nowNode.html('更多<i class="icon-open-arrow" style="pointer-events: none"></i>');
                        nowNode.attr({name: 'hidden'});
                    }
                } else if (bNeedBottomModel) {
                    if (nowNode.hasClass('close-button')) {
                        if (nowNode.attr('name') === 'show') {
                            typeSearchListShowDiv.css({height: 0, padding: 0});
                            nowNode.html('展开筛选<i class="icon-open-arrow" style="pointer-events: none"></i>');
                            nowNode.attr('name', 'hidden');
                        } else {
                            typeSearchListShowDiv.css({height: 'auto', padding: '15px 20px'});
                            nowNode.html('收起筛选<i class="icon-close-arrow" style="pointer-events: none"></i>');
                            nowNode.attr('name', 'show');
                        }
                    } else if (nowNode.hasClass('search-text') || nowNode.parent().hasClass('search-text')) {
                        var btnNode = (nowNode.hasClass('search-text')) ? nowNode : nowNode.parent();
                        if (searchBtnClickCallBack) {
                            searchBtnClickCallBack(btnNode.prev());
                        }
                    }
                }
            }
        });
    }

    // 监听多选框中input的keydown事件
    function _eventOfSearchInputKeyDown() {
        searchInput.keydown(function (event) {
            if (searchInputKeyDownCallBack) {
                searchInputKeyDownCallBack(event, searchInput)
            }
        })
    }

    /*=== 方法 ===*/
    // 获取数据源
    _this.setData = function (dataArr, isShowDefaultAddress) {
        try {
            if (isShowDefaultAddress === undefined) {
                isShowDefaultAddress = false;
            }
            if (!isHaveJQuery) {
                throw new Error('本组件需jquery支持，请先引入jquery');
            }
            // 验证模块
            if (!(dataArr instanceof Array)) {
                throw new Error('参数dataArr不为Array');
            }
            dataArr.forEach(function (item) {
                if (item.name === undefined || item.type === undefined || item.data === undefined) {
                    var result = '属性：' + ((item.name === undefined) ? 'name' : (item.type === undefined) ? 'type' : 'data') + '为undefined';
                    throw new Error('参数dataArr中子项格式不符合规则,' + result);
                }
            });
            // 重置写入数据
            _writeData = null;
            // 赋值给内部数据变量(防止外部数据变化导致内部错误)
            RawData = JSON.parse(JSON.stringify(dataArr));
            // 初始化部分属性
            RawData.forEach(function (item, index) {
                // 默认单选
                item.selectMore = item.selectMore || false;
                // 复制linkMethod
                if (dataArr[index].linkMethod) {
                    item.linkMethod = dataArr[index].linkMethod;
                }
            });
            // 是否添加默认地址数据
            if (isShowDefaultAddress) {
                // 改变全局数值
                addDefaultAddress = isShowDefaultAddress;
                // 获取省内数据
                _getProvinceArr();
                // 合并数据
                RawData = RawData.concat(defaultAddressFormat);
            }
            // 记录默认选中项
            /*RawData.forEach(function (item) {
                _this.selectData[item.type] = item.active;
            });*/
            _extractListData(RawData);
            // 初始化多选框
            _createTypeSearch();
            // 初始化点击函数
            _eventOfSelectTypeSearchClick();
            // 初始化监听函数
            _eventOfSearchInputKeyDown();
            return _this;
        } catch (err) {
            // 数据错误
            isDataTrue = false;
            console.error('类型多选框_error:' + err);
        }
    };
    // 设置点击子项的回调函数
    _this.setClickCallback = function (callback) {
        if (!isHaveJQuery) {
            console.error('类型多选框_error:本组件需jquery支持，请先引入jquery');
            return 0
        }
        if (callback === undefined || typeof callback !== "function") {
            console.error('请正确设置回调函数')
        } else {
            optionClickCallBack = callback
        }
        return _this;
    };
    // 设置点击搜索按钮的回调函数
    _this.setSearchBtnClickCallback = function (callback) {
        if (!isHaveJQuery) {
            console.error('类型多选框_error:本组件需jquery支持，请先引入jquery');
            return 0;
        }
        if (callback === undefined || typeof callback !== "function") {
            console.error('请正确设置回调函数');
        } else {
            searchBtnClickCallBack = callback;
        }
        return _this;

    };
    // 设置搜索input的回调函数
    _this.setSearchInputKeyDownCallback = function (callback) {
        if (!isHaveJQuery) {
            console.error('类型多选框_error:本组件需jquery支持，请先引入jquery');
            return 0;
        }
        if (callback === undefined || typeof callback !== "function") {
            console.error('请正确设置回调函数');
        } else {
            searchInputKeyDownCallBack = callback;
        }
        return _this;
    };
    // 设置是否需要底部模块
    _this.setBottomModelEnable = function (isEnable) {
        var bottomModel = null;
        try {
            if (isEnable === undefined) {
                isEnable = true;
            }
            if (typeof isEnable !== "boolean") {
                throw new Error('参数 isEnable 必须为 Boolean 类型');
            }
            bNeedBottomModel = isEnable;
            bottomModel = typeSearchListDiv.parent().next();
            // 底部模块存在
            if (bottomModel.length > 0) {
                if (bNeedBottomModel) {// 启用
                    typeSearchListDiv.parent().next().show();
                } else {
                    typeSearchListDiv.parent().next().hide();
                }
            }
            return _this;
        } catch (err) {
            console.error('setBottomModelEnable 方法，原因：' + err);
        }
    };
    // 启用可以设置默认选中项模式
    _this.canSetDefaultSelectOption = function (bCan) {
        try {
            if (bCan === undefined) {
                bCan = false;
            }
            if (typeof bCan !== "boolean") {
                throw new Error('参数 bCan 必须为 Boolean 类型');
            }
            bIsInitModel = bCan;
            return _this;
        } catch (err) {
            console.error('canSetDefaultSelectOption 方法，原因：' + err);
        }
    }
}
