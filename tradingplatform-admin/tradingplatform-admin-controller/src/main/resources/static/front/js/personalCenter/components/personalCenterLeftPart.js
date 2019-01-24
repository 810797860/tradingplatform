
// 函数调用
setUserInfo();

// 更新左框的用户信息
function setUserInfo() {
    var avatarNode = $('.personal-center-left .user-avatar').eq(0);
    var userNameNode = $('.personal-center-left .user-name').eq(0);
    var userCompleteness = $('.personal-center-left .perfection').eq(0);
    var userCompletenessProcess = $('.personal-center-left .perfection-down').eq(0);
    var storage = window.localStorage;
    var userInfo = (storage.getItem('user') !== null) ? JSON.parse(storage.getItem('user')) : undefined;
    // console.log(userInfo);
    if (userInfo !== undefined) {
        // 获取图片url
        var imageUrl = (userInfo.avatar !== undefined) ? avatarNode.getAvatar(userInfo.avatar) : '/static/assets/logo.png';
        // 写入图片
        avatarNode.attr({
            src: imageUrl
        })
        // 写入名称
        userNameNode.text(userInfo.userName);
        userNameNode.attr("title", userInfo.userName);
        // 写入资料完善度
        userCompleteness.text(parseInt(userInfo.dataCompleteness * 100));
        userCompletenessProcess.css('width', parseInt(userInfo.dataCompleteness * 100) + '%');
    }
}