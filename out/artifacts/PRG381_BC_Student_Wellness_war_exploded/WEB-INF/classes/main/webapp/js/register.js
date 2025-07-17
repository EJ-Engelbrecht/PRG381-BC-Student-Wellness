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
