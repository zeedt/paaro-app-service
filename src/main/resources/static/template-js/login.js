
var paaro_host = "http://127.0.0.1:7071/";
var user_host = "http://127.0.0.1:8011/";

function login() {

    var email = $("#email").val();
    var password = $("#password").val();
    var base64String = Base64.encode("paaro-service:secret");

    $.ajax({
        type: "POST",
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", "Basic " + base64String);
        },
        url:paaro_host + "user/login",
        contentType: "application/json; charset=utf-8",
        data : JSON.stringify({email:email,password:password}),
        async:false,
        success : function (result) {
            localStorage.clear();
            localStorage.setItem("paaro_access_token",result.access_token );
            localStorage.setItem("email",result.email );
            localStorage.setItem("firstName",result.firstName );
            localStorage.setItem("lastName",result.lastName);
            localStorage.setItem("role",result.category );
            window.location.href = "/";
        },
        error : function (error) {
            $("#error-message").val(error.message);
            console.log("Error " + JSON.stringify(error));
        }
    })


}