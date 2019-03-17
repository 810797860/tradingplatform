/**
 * Created by 空 on 2018/9/13.
 */
var helpCenterData = helpCenter

$(function () {
    var herf = (window.location.search).split('&')
    console.log(herf);
    initPage();
    console.log(herf[1]);
    if (herf[1] == 11) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(2).addClass('active')
        $('.help-hall-left-content>ul>div').eq(2).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(6).addClass('right-active')
        if (!!helpCenterData.serviceRules) {
            $('.help-hall-right').html(helpCenterData.serviceRules)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 12) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(1).addClass('active')
        $('.help-hall-left-content>ul>div').eq(1).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(5).addClass('right-active')
        if (!!helpCenterData.demandRelease) {
            $('.help-hall-right').html(helpCenterData.demandRelease)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 13) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(4).addClass('active')
        $('.help-hall-left-content>ul>div').eq(4).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(11).addClass('right-active')
        if (!!helpCenterData.receivingPartyDeclaration) {
            $('.help-hall-right').html(helpCenterData.receivingPartyDeclaration)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 14) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(6).addClass('active')
        $('.help-hall-left-content>ul>div').eq(6).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(14).addClass('right-active')
        if (!!helpCenterData.expertArgumentation) {
            $('.help-hall-right').html(helpCenterData.expertArgumentation)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 21) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(0).addClass('active')
        $('.help-hall-left-content>ul>div').eq(0).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(0).addClass('right-active')
        if (!!helpCenterData.security) {
            $('.help-hall-right').html(helpCenterData.security)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 22) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(0).addClass('active')
        $('.help-hall-left-content>ul>div').eq(0).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(3).addClass('right-active')
        if (!!helpCenterData.policyPrivacy) {
            $('.help-hall-right').html(helpCenterData.policyPrivacy)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 23) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(0).addClass('active')
        $('.help-hall-left-content>ul>div').eq(0).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(4).addClass('right-active')
        if (!!helpCenterData.disputeArbitration) {
            $('.help-hall-right').html(helpCenterData.disputeArbitration)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 31) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(7).addClass('active')
        $('.help-hall-left-content>ul>div').eq(7).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(15).addClass('right-active')
        if (!!helpCenterData.crowdsourcingPlatformIntroduction) {
            $('.help-hall-right').html(helpCenterData.crowdsourcingPlatformIntroduction)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    } else if (herf[1] == 34) {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $('.help-hall-left-content>ul>div').removeClass('in')
        $('.help-hall-left-content>ul>li').eq(7).addClass('active')
        $('.help-hall-left-content>ul>div').eq(7).addClass('in')
        $('.panel-collapse li').removeClass('right-active')
        $('.panel-collapse li').eq(16).addClass('right-active')
        if (!!helpCenterData.contactInformation) {
            $('.help-hall-right').html(helpCenterData.contactInformation)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    }
    handleClick();
})

function initPage() {
    if (!!helpCenterData.security) {
        $('.help-hall-right').html(helpCenterData.security)
    } else {
        $('.help-hall-right').html("暂无数据")
    }
}

// 左侧栏的点击事件
function handleClick() {
    //左侧控制右侧时的点击事件
    $('#safetyPrecautions .safetyPrecautions-ul li').eq(0).click(function () {
        if (!!helpCenterData.security) {
            $('.help-hall-right').html(helpCenterData.security)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#safetyPrecautions .safetyPrecautions-ul li').eq(1).click(function () {
        if (!!helpCenterData.integrityCommitment) {
            $('.help-hall-right').html(helpCenterData.integrityCommitment)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#safetyPrecautions .safetyPrecautions-ul li').eq(2).click(function () {
        if (!!helpCenterData.technicalTransactionContract) {
            $('.help-hall-right').html(helpCenterData.technicalTransactionContract)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#safetyPrecautions .safetyPrecautions-ul li').eq(3).click(function () {
        if (!!helpCenterData.policyPrivacy) {
            $('.help-hall-right').html(helpCenterData.policyPrivacy)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#safetyPrecautions .safetyPrecautions-ul li').eq(4).click(function () {
        if (!!helpCenterData.disputeArbitration) {
            $('.help-hall-right').html(helpCenterData.disputeArbitration)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#contractStatementRules .contractStatementRules-ul li').eq(0).click(function () {
        if (!!helpCenterData.demandRelease) {
            $('.help-hall-right').html(helpCenterData.demandRelease)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#serviceProvisions .serviceProvisions-ul li').eq(0).click(function () {
        if (!!helpCenterData.serviceRules) {
            $('.help-hall-right').html(helpCenterData.serviceRules)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#serviceProvisions .serviceProvisions-ul li').eq(1).click(function () {
        if (!!helpCenterData.serviceAgreement) {
            $('.help-hall-right').html(helpCenterData.serviceAgreement)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#serviceProvisions .serviceProvisions-ul li').eq(2).click(function () {
        if (!!helpCenterData.avatarNicknameRegulations) {
            $('.help-hall-right').html(helpCenterData.avatarNicknameRegulations)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#contractClass .contractClass-ul li').eq(0).click(function () {
        if (!!helpCenterData.tripartiteCooperationAgreement) {
            $('.help-hall-right').html(helpCenterData.tripartiteCooperationAgreement)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#contractClass .contractClass-ul li').eq(1).click(function () {
        if (!!helpCenterData.fundCustodyAgreement) {
            $('.help-hall-right').html(helpCenterData.fundCustodyAgreement)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#packetRule .packetRule-ul li').eq(0).click(function () {
        if (!!helpCenterData.receivingPartyDeclaration) {
            $('.help-hall-right').html(helpCenterData.receivingPartyDeclaration)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#descriptionClass .descriptionClass-ul li').eq(0).click(function () {
        if (!!helpCenterData.paymentStatement) {
            $('.help-hall-right').html(helpCenterData.paymentStatement)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#descriptionClass .descriptionClass-ul li').eq(1).click(function () {
        if (!!helpCenterData.glossary) {
            $('.help-hall-right').html(helpCenterData.glossary)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    $('#expertReview .expertReview-ul li').eq(0).click(function () {
        if (!!helpCenterData.expertArgumentation) {
            $('.help-hall-right').html(helpCenterData.expertArgumentation)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })

    toCall();

    toCompanyIntroduction();

    leftChange();
}

function toCompanyIntroduction() {
    $('#platformIntroduction .platformIntroduction-ul li').eq(0).click(function () {
        if (!!helpCenterData.crowdsourcingPlatformIntroduction) {
            $('.help-hall-right').html(helpCenterData.crowdsourcingPlatformIntroduction)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })
}

function toCall() {
    $('#platformIntroduction .platformIntroduction-ul li').eq(1).click(function () {
        if (!!helpCenterData.contactInformation) {
            $('.help-hall-right').html(helpCenterData.contactInformation)
        } else {
            $('.help-hall-right').html("暂无数据")
        }
    })
}

function leftChange() {
    //点击左侧标题栏样式的更改
    $('.help-hall-left-content>ul>li').click(function () {
        $('.help-hall-left-content>ul>li').removeClass('active')
        $(this).addClass('active')
    })

    $('.panel-collapse li').click(function () {
        $('.panel-collapse li').removeClass('right-active')
        $(this).addClass('right-active')
    })
}
