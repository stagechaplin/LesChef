//로그인 모달
const loginModal = document.querySelector('.loginModal');
const loginBox = document.querySelector('.Login-box');
const loginButton = document.getElementById('loginButton');
const goToFindButton = document.querySelector('#FindIdPw');
const loginInputForm = document.querySelector('.login-form-container');
const findInputForm = document.querySelector('.find-form-container');
const findIdForm = document.querySelector('.id-form-container');
const findPwForm = document.querySelector('.pw-form-container');
const findIdButton = document.querySelector('.id-button');
const findPwButton = document.querySelector('.pw-button');
const findToLogin = document.querySelector('.find-to-login span');
const findToLoginText = document.querySelector('.find-to-login');
const joinToLogin = document.querySelector('#signup-box p span');

function loginModalActive() {
    loginInputForm.style.display = "block";
    findInputForm.style.display = "none";
    findToLoginText.style.display = "none";
    if(loginModal.classList.contains('active')){
        loginBox.style.transition = "none";
        loginBox.classList.remove('active');
        loginModal.classList.remove('active');
    }else{
        loginBox.style.transition = "all 0.5s";
        loginBox.querySelector("input").value = "";
        loginModal.classList.add('active');
        loginBox.classList.add('active');
    }
}

loginBox.addEventListener('click', function(event) {
    event.stopPropagation();
});

loginButton.addEventListener('click', loginModalActive);
loginModal.addEventListener('click', loginModalActive);
joinToLogin.addEventListener('click', loginModalActive);

goToFindButton.addEventListener('click', () => {
    loginInputForm.style.display = "none";
    findInputForm.style.display = "block";
    findIdForm.style.display = "block";
    findPwForm.style.display = "none";
    findIdButton.style.backgroundColor = "#79AE84";
    findPwButton.style.backgroundColor = "#28442d";
    findToLoginText.style.display = "block";
});

findIdButton.addEventListener('click',() => {
    findIdButton.style.backgroundColor = "#79AE84";
    findPwButton.style.backgroundColor = "#28442d";
    findIdForm.style.display = "block";
    findPwForm.style.display = "none";
});

findPwButton.addEventListener('click', () => {
    findIdButton.style.backgroundColor = "#28442d";
    findPwButton.style.backgroundColor = "#79AE84";
    findIdForm.style.display = "none";
    findPwForm.style.display = "block";
});

findToLogin.addEventListener('click', () => {
    loginInputForm.style.display = "block";
    findInputForm.style.display = "none";
    findToLoginText.style.display = "none";
});
