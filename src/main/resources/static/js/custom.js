let alertMessage = "";

$(".signupbtn").on('click',function(){

    if(verifyForm()){
        $('.signupbtn').attr('type','submit');
    }
    else{
        alert(alertMessage);
    }

});


$(".updatebtn").on('click',function(){

    if(verifyQuantity()){
        $('.updatebtn').attr('type','submit');
    }
    else{
        alert("Total quantity can not be less than the available quantity");
    }

});

function verifyQuantity(){

    if( $("#availableQuantity_Update").val() > $(".totalQuantity_Update").val() ){
        return false;
    }
    return true;
}


function verifyForm(){
    if(checkPersonalInformation()){
        if(checkPasswordFormat()){
            return true;
        }
    }
    return false;
}

function checkPersonalInformation(){

    let personalInformationStatus = false;
    if($("#name").val().length==0){
        alertMessage = "Please enter name.";
    }
    else if($("#email").val().length==0){
        alertMessage = "Please enter email.";
    }
    else if($("#contact").val().length==0){
        alertMessage = "Please enter contact.";
    }
    else{
        personalInformationStatus = true;
    }
    return personalInformationStatus;
    }

function checkPasswordFormat(){
    let passwordFormatStatus = false;
        if(checkLengthPassword()==false){
            alertMessage = "Check length in password.";
        }
        else if(checkCapitalLetter()==false){
            alertMessage = "Password must have one capital letter.";
        }
        else if(checkNumeric()==false){
            alertMessage = "Password must have one numeric value.";
        }
        else if(checkPassowordMatch()==false){
            alertMessage = "Password and confirm password does not match.";
        }
        else{
            passwordFormatStatus = true;
        }
    return passwordFormatStatus;
}

function checkLengthPassword(){
    if($("#password").val().length>=8 && $("#password").val().length <=15){
        return true;
    }
    return false;
}

function checkCapitalLetter(){
    let regExpCapital = /[A-Z]/;
    if(regExpCapital.test($("#password").val())){
        return true;
    }
    return false;
}

function checkNumeric(){
    let regExpNumeric = /\d/;
    if(regExpNumeric.test($("#password").val())){
        return true;
    }
    return false;
}

function checkPassowordMatch(){
    if($('#password').val() == $('#confirm_password').val()){
        return true;
    }
    return false;
}

$('#password, #confirm_password').on('keyup', function () {
    let regExpCapital = /[A-Z]/;
    let regExpNumeric = /\d/;
    if($("#password").val().length>=8 && $("#password").val().length <=15){
        $('#eightchar').css('color','green');
    }
    else{
        $('#eightchar').css('color','red');
    }

    if(regExpCapital.test($("#password").val())){
        $('#capital_letter').css('color','green');
    }
    else{
        $('#capital_letter').css('color','red');
    }

    if(regExpNumeric.test($("#password").val())){
        $('#numeric').css('color','green');
    }
    else{
        $('#numeric').css('color','red');
    }

    if ($('#password').val() == $('#confirm_password').val()) {
        $('#pwd-cnfpwd-same').css('color', 'green');
    }
    else{
        $('#pwd-cnfpwd-same').css('color', 'red');
    }

});




$(".dropbtn").on("click",function(){

    document.getElementById("myDropdown").classList.toggle("show");
    window.onclick = function(event) {
      if (!event.target.matches('.dropbtn')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
          var openDropdown = dropdowns[i];
          if (openDropdown.classList.contains('show')) {
            openDropdown.classList.remove('show');
          }
        }
      }
    }

})




