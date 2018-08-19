
redirectToPage();

function redirectToPage() {
    $.ajax({
        type : "GET",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url : "/user/authority",
        error : function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
            }
        }
    })
}

function activateOrDeactivateUser(email, id) {

    var toggleId = '#toggle'+id;
    var activate = $(''+toggleId+'').is(':checked');
    console.log("id is " + id + " is checked " + activate);

    var url = "";

    if (activate) {
        url = "/user/activateUser?email="
    } else {
        url = "/user/deactivateUser?email=";
    }
    $.ajax({
        type : "GET",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url : url + email,
        error : function (e) {
            $(''+toggleId+'').prop("checked",!activate);
            console.log("status code is " + e.status);
            if (e.status == '401') {
                localStorage.clear();
                window.location.href = "/login";
                return;
            }
            if (e.status == '403') {
                if (activate) {
                    alert("You are not authorised to activate user");
                } else {
                    alert("You are not authorised to deactivate user");
                }
            } else {
                alert("Unable to complete operation due to system error");
            }
        }
    });
}

function previousPage() {
    var pageData = $("#currentPage").html();
    var filter = $("#user-filter").val();
    var pageNo = parseInt(pageData);
    if (pageNo < 1) {
        return;
    }
    window.location.href = "/view/users?pageNo="+(pageNo-1)+"&filter="+filter;
}

function filterUser() {
    var pageData = $("#user-filter").val();
    window.location.href = "/view/users?pageNo=0&filter="+pageData;
}

function nextPage() {
    var pageData = $("#currentPage").html();
    var filter = $("#user-filter").val();
    var pageNo = parseInt(pageData);
    var totalNoOfPagesData = $("#totalNoOfPages").html();
    var totalNoOfPages = parseInt(totalNoOfPagesData);
    if ((pageNo+1) >= totalNoOfPages) {
        return;
    }
    window.location.href = "/view/users?pageNo="+(pageNo+1)+"&filter="+filter;
}

function showUserModal(email) {

    $.ajax({
        type : "GET",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url : "/view/user/getUserDetailsByEmail?email=" + email,
        error : function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
                return;
            }

            if (e.status == '403') {
                alert("You are not authorised to view full user details");
                return;
            }
        },
        success : function (data) {
            $("#modal-user-content").html(data);
            console.log("data generated");
            $("#view-user").modal();
        }
    });

}

function showUserTransactionModal(email, pageNo) {

    var filter = $("#transfer-filter").val();
    if (pageNo == undefined || pageNo == null || pageNo < 0) {
        pageNo = 0;
    }

    var requestData = {
        "email": email,
        "pageNo": pageNo,
        "pageSize": 10,
        "filter" : filter
    };

    $.ajax({
        type: "POST",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url: "/view/user/findAllWalletTransferTransactionsByEmailPaged",
        async:false,
        data: JSON.stringify(requestData),
        contentType: "application/json; charset=utf-8",
        error: function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
                return;
            }

            if (e.status == '403') {
                alert("You are not authorised to view transaction details of user");
                return;
            }
        },
        success: function (data) {
            $("#modal-user-content").html(data);
            console.log("data generated");
            $("#transfer-request-email").html(email);
            $("#transfer-filter").val(filter);
            $("#view-user").modal();
            $("#transfer-filter").focus();
        }
    });

}
    function transferRequestPreviousPage() {
        var email = $("#transfer-request-email").html();
        var pageData = $("#transferReqCurrentPage").html();
        var pageNo = parseInt(pageData);

        if ((pageNo-1) < 0) {
            return;
        }
        return showUserTransactionModal(email,(pageNo-1));
    }

    function transferRequestNextPage() {

        var email = $("#transfer-request-email").html();
        var pageData = $("#transferReqCurrentPage").html();
        var pageNo = parseInt(pageData);
        var totalNoOfPagesData = $("#transferReqTotalNoOfPages").html();
        totalNoOfPagesData = parseInt(totalNoOfPagesData);

        if ((pageNo+1) >= totalNoOfPagesData) {
            return;
        }
        return showUserTransactionModal(email,(pageNo+1));
    }

    function filterTransferRequestTransaction() {
        var email = $("#transfer-request-email").html();

        return showUserTransactionModal(email,0);
    }



function showAddAuthorityModal(email) {

    $.ajax({
        type: "POST",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url: "/view/user/authority-to-user-view",
        async:false,
        data: JSON.stringify(requestData),
        contentType: "application/json; charset=utf-8",
        error: function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
                return;
            }

            if (e.status == '403') {
                alert("You are not authorised to view transaction details of user");
                return;
            }
        },
        success: function (data) {
            $("#modal-user-content").html(data);
            console.log("data generated");
            $("#transfer-request-email").html(email);
            $("#transfer-filter").val(filter);
            $("#view-user").modal();
            $("#transfer-filter").focus();
        }
    });

}


function addSelectedAuthorities() {
    var authoritiesString =  $("#authorities").val();
    if (authoritiesString == undefined || authoritiesString == null || authoritiesString == 'null' || authoritiesString == '') {
        alert("No authority selected");
        return;
    }

    var authoritiesArray = String(authoritiesString).split(',');
    console.log("Value is " + JSON.stringify(authoritiesArray));
    console.log("Size is " + authoritiesArray.length);
    var email = $("#user-email").html();

    var requestData = {
        "authorities" : authoritiesArray,
        "email" : email
    };

    var url = "/authority/mapAuthoritiesToUser";
    $.ajax({
        type : "POST",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        data: JSON.stringify(requestData),
        url : url,
        contentType: "application/json; charset=utf-8",
        error : function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                localStorage.clear();
                window.location.href = "/login";
                return;
            }
            if (e.status == '403') {
                    alert("You are not authorised to add authority to user");

            } else {
                alert("Unable to complete operation due to system error");
            }
        },
        success : function (data) {
            alert(data.message);
            showUserModal(email);
        }
    });

}



function showUserFundWalletTransactionsModal(email, pageNo) {

    var filter = $("#transfer-filter").val();
    if (pageNo == undefined || pageNo == null || pageNo < 0) {
        pageNo = 0;
    }

    var requestData = {
        "email": email,
        "pageNo": pageNo,
        "pageSize": 10
    };

    $.ajax({
        type: "POST",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url: "/view/user/findAllFundWalletTransactionsByEmailPaged",
        async:false,
        data: JSON.stringify(requestData),
        contentType: "application/json; charset=utf-8",
        error: function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
                return;
            }

            if (e.status == '403') {
                alert("You are not authorised to view transaction details of user");
                return;
            }
        },
        success: function (data) {
            $("#modal-user-content").html(data);
            console.log("data generated");
            $("#transfer-request-email").html(email);
            $("#transfer-filter").val(filter);
            $("#view-user").modal();
            $("#transfer-filter").focus();
        }
    });

}

function walletFundingTransactionsPreviousPage() {
    var email = $("#transfer-request-email").html();
    var pageData = $("#transferReqCurrentPage").html();
    var pageNo = parseInt(pageData);

    if ((pageNo-1) < 0) {
        return;
    }
    return showUserFundWalletTransactionsModal(email,(pageNo-1));
}

function walletFundingTransactionsNextPage() {

    var email = $("#transfer-request-email").html();
    var pageData = $("#transferReqCurrentPage").html();
    var pageNo = parseInt(pageData);
    var totalNoOfPagesData = $("#transferReqTotalNoOfPages").html();
    totalNoOfPagesData = parseInt(totalNoOfPagesData);

    if ((pageNo+1) >= totalNoOfPagesData) {
        return;
    }
    return showUserFundWalletTransactionsModal(email,(pageNo+1));
}

function filterwalletFundingTransactions() {
    var email = $("#transfer-request-email").html();

    return showUserFundWalletTransactionsModal(email,0);
}


function deleteUserAuthority(userId, authorityId) {
    var email = $("#user-email").html();
    $.ajax({
        type : "GET",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("paaro_access_token"));
        },
        url : "/view/user/delete-user-autority/"+""+userId+"/"+authorityId,
        error : function (e) {
            console.log("status code is " + e.status);
            if (e.status == '401') {
                console.log("status code is " + 401);
                localStorage.clear();
                window.location.href = "/login";
            }
            if (e.status == '403') {
                alert("You are not authorised to delete user's authority");
                return;
            } else {
                alert("System errror occured");
            }
        },
        success : function (data) {
            alert(data);
            showUserModal(email);
        }
    })

}