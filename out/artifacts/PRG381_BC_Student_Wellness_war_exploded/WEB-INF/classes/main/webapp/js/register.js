document.getElementById('phone').addEventListener('input', function (e) {
    let input = e.target;
    let value = input.value.replace(/\D/g, ''); // Remove non-digits
    // Limit to 9 digits
    if (value.length > 10) {
        value = value.slice(0, 10);
    }
    // Apply live formatting: (###)-###-###
    let formatted = '';
    if (value.length > 0) {
        formatted += '(' + value.substring(0, 3);
    }
    if (value.length >= 4) {
        formatted += ')-' + value.substring(3, 6);
    }
    if (value.length >= 7) {
        formatted += '-' + value.substring(6, 10);
    }
    input.value = formatted;
});


function showPasswordStep() {
    document.getElementById("step1").style.display = "none";
    document.getElementById("step2").style.display = "block";
}

function backToInfo() {
    document.getElementById("step2").style.display = "none";
    document.getElementById("step1").style.display = "block";
}

function validateStep1() {
    let valid = true;

    const email = document.getElementById("email");
    const phone = document.getElementById("phone");

    const emailMsg = document.getElementById("email-error");
    const phoneMsg = document.getElementById("phone-error");

    email.classList.remove("invalid");
    phone.classList.remove("invalid");
    emailMsg.textContent = "";
    phoneMsg.textContent = "";

    const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    const phoneRegex = /^\(\d{3}\)-\d{3}-\d{4}$/;

    // Email validation
    if (email.value.trim().length === 0) {
        email.classList.add("invalid");
        emailMsg.innerHTML += "<p>Please fill in a Email address.</p>";
        valid = false;
    } else {
        //Email complexity validation
        if (!emailRegex.test(email.value)) {
            email.classList.add("invalid");
            emailMsg.innerHTML += "<p>Not a real Email address.</p>";
            valid = false;
        }
    }
    //Phone validation
    if (!phoneRegex.test(phone.value)) {
        phone.classList.add("invalid");
        phoneMsg.textContent = "Phone number must be 10 digits.";
        valid = false;
    }

    if (valid) {
        showPasswordStep();
    }

    return false; // prevent form from submitting yet
}

function copyStep1ToStep2() {
    document.getElementById('studentNumberHidden').value = document.querySelector('input[name="studentNumber"]').value;
    document.getElementById('nameHidden').value = document.querySelector('input[name="name"]').value;
    document.getElementById('surnameHidden').value = document.querySelector('input[name="surname"]').value;
    document.getElementById('emailHidden').value = document.querySelector('input[name="email"]').value;
    document.getElementById('phoneHidden').value = document.querySelector('input[id="phone"]').value;
}

document.getElementById('step2').addEventListener('submit', function(event) {
    copyStep1ToStep2();
});
function validateStep2() {
    let valid = true;
    const password = document.getElementById("password");
    const confirm = document.getElementById("confirm-password");
    const passwordMsg = document.getElementById("password-error");

    password.classList.remove("invalid");
    confirm.classList.remove("invalid");
    passwordMsg.textContent = "";
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d).{8,}$/;

    // Password validation
    if (password.value.trim().length === 0) {
        password.classList.add("invalid");
        passwordMsg.innerHTML += "<p>Password cannot be empty.</p>";
        valid = false;
    } else {

        if (password.value !== confirm.value) {
            confirm.classList.add("invalid");
            passwordMsg.textContent = "Passwords do not match.";
            valid = false;
        }

        // Password complexity validation
        if (!passwordRegex.test(password.value)) {
            password.classList.add("invalid");
            passwordMsg.innerHTML += "<p>Password must contain at least 1 lowercase, 1 uppercase, 1 digit, and be at least 8 characters.</p>";
            valid = false;
        }

        // Custom condition
        if (password.value.includes(".")) {
            password.classList.add("invalid");
            passwordMsg.innerHTML += "<p>Password cannot contain a dot (.) character.</p>";
            valid = false;
        }
    }
    return valid;
}
function togglePassword(inputId, toggleId) {
    const passwordField = document.getElementById(inputId);
    const toggleIcon = document.getElementById(toggleId);

    if (passwordField.type === "password") {
        passwordField.type = "text";
        toggleIcon.src = "./images/eye-open.png";
    } else {
        passwordField.type = "password";
        toggleIcon.src = "./images/eye-closed.png";
    }
}
