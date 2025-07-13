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
            const phoneRegex = /^[0-9]{10}$/;

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


        function validateStep2() {
            const password = document.getElementById("password");
            const confirm = document.getElementById("confirm-password");
            const passwordMsg = document.getElementById("password-error");

            password.classList.remove("invalid");
            confirm.classList.remove("invalid");
            passwordMsg.textContent = "";

            if (password.value.length < 8) {
                password.classList.add("invalid");
                passwordMsg.textContent = "Password must be at least 8 characters.";
                return false;
            }

            if (password.value !== confirm.value) {
                confirm.classList.add("invalid");
                passwordMsg.textContent = "Passwords do not match.";
                return false;
            }

            return true;
        }


        function togglePassword(inputId, toggleId) {
            const passwordField = document.getElementById(inputId);
            const toggleIcon = document.getElementById(toggleId);

            if (passwordField.type === "password") {
                passwordField.type = "text";
                toggleIcon.src = "images/eye-open.png";
            } else {
                passwordField.type = "password";
                toggleIcon.src = "images/eye-closed.png";
            }
        }
