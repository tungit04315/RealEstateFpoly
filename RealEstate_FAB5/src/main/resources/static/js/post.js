var currentTab = 0;
showTab(currentTab);

function showTab(n) {
    var x = document.getElementsByClassName("step");
    x[n].style.display = "block";
    if (n == 0) {
        document.getElementById("prevBtn").style.display = "none";
    } else {
        document.getElementById("prevBtn").style.display = "inline";
    }
    // if (n == (x.length - 1)) {
    //     document.getElementById("nextBtn").innerHTML = "Đồng ý";
    // } else {
    //     document.getElementById("nextBtn").innerHTML = "Tiếp";
    // }
    if (n == (x.length - 1)) {
        document.getElementById("nextBtn").style.display = "none";
        document.getElementById("submitBtn").style.display = "inline";
    } else {
        document.getElementById("nextBtn").style.display = "inline";
        document.getElementById("submitBtn").style.display = "none";
    }
    fixStepIndicator(n)
}

function nextPrev(n) {

    var x = document.getElementsByClassName("step");
    if (n == 1 && !validateForm()) return false;
    x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        document.getElementById("signUpForm").submit();
        return false;
    }
    showTab(currentTab);
}

// function validateForm() {
//     var x, y, i, valid = true;
//     x = document.getElementsByClassName("step");
//     y = x[currentTab].getElementsByTagName("input");
//     var valueLink = document.getElementById("linkYoutube");
//     valueLink.value = "URL video youtube ...";
//     for (i = 0; i < y.length; i++) {
//         if (y[i].value == "") {
//             y[i].className += " invalid";
//             valid = false;
//         }
//     }
//     if (valid) {
//         document.getElementsByClassName("stepIndicator")[currentTab].className += " finish";
//     }
//     return valid;
// }

function validateForm() {
    var x, y, i, valid = true;
    x = document.getElementsByClassName("step");
    y = x[currentTab].getElementsByTagName("input");
    var valueLink = document.getElementById("linkYoutube");
    valueLink.value = "URL video youtube ...";

    // Kiểm tra các input text
    for (i = 0; i < y.length; i++) {
        if (y[i].value == "") {
            y[i].className += " invalid";
            valid = false;
            swal("Error!", "Vui lòng kiểm tra lại thông tin!", "error");
        }
    }

    // Kiểm tra các select element
    var selects = x[currentTab].getElementsByTagName("select");
    for (i = 0; i < selects.length; i++) {
        if (selects[i].value == "") {
            selects[i].className += " invalid";
            valid = false;
            swal("Error!", "Vui lòng kiểm tra lại thông tin!", "error");
        }
    }

    // Kiểm tra các radio buttons
    var radioGroups = x[currentTab].getElementsByTagName("input");
    for (i = 0; i < radioGroups.length; i++) {
        if (radioGroups[i].type === "radio" && !isChecked(radioGroups[i].name)) {
            valid = false;
            swal("Error!", "Vui lòng kiểm tra lại thông tin!", "error");
        }
    }

    if (valid) {
        document.getElementsByClassName("stepIndicator")[currentTab].className += " finish";
    }

    return valid;
}

function isChecked(name) {
    var radioButtons = document.getElementsByName(name);
    for (var i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            return true;
        }
    }
    return false;
}


function fixStepIndicator(n) {
    var i, x = document.getElementsByClassName("stepIndicator");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }
    x[n].className += " active";
}

// const quantityInput = document.getElementById('quantityInput');
// const decreaseBtn = document.getElementById('decreaseBtn');
// const increaseBtn = document.getElementById('increaseBtn');

// decreaseBtn.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput.value);
//     if (quantity > 1) {
//         quantity--;
//         quantityInput.value = quantity;
//     }
// });

// increaseBtn.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput.value);
//     quantity++;
//     quantityInput.value = quantity;
// });

// const quantityInput_two = document.getElementById('quantityInput-two');
// const decreaseBtn_two = document.getElementById('decreaseBtn-two');
// const increaseBtn_two = document.getElementById('increaseBtn-two');

// decreaseBtn_two.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput_two.value);
//     if (quantity > 1) {
//         quantity--;
//         quantityInput_two.value = quantity;
//     }
// });

// increaseBtn_two.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput_two.value);
//     quantity++;
//     quantityInput_two.value = quantity;
// });

// const quantityInput_three = document.getElementById('quantityInput-three');
// const decreaseBtn_three = document.getElementById('decreaseBtn-three');
// const increaseBtn_three = document.getElementById('increaseBtn-three');

// decreaseBtn_three.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput_three.value);
//     if (quantity > 1) {
//         quantity--;
//         quantityInput_three.value = quantity;
//     }
// });

// increaseBtn_three.addEventListener('click', () => {
//     let quantity = parseInt(quantityInput_three.value);
//     quantity++;
//     quantityInput_three.value = quantity;
// });