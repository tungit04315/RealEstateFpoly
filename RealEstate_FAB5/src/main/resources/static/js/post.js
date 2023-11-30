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
        document.getElementById("createPostForm").submit();
        return false;
    }
    showTab(currentTab);
}

function nextPrevUpdate(n) {
    var x = document.getElementsByClassName("step");
    //if (n == 1 && !validateFormUpdatePost()) return false;
    x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        document.getElementById("updatePost").submit();
        return false;
    }
    showTab(currentTab);
}

function nextPrevUpdatePostExpired(n) {
    var x = document.getElementsByClassName("step");
    if (n == 1 && !validateFormUpdatePostExpired()) return false;
    x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        document.getElementById("updatePostExpiredForm").submit();
        return false;
    }
    showTab(currentTab);
}

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
            swal("Lỗi A!", "Vui lòng kiểm tra lại thông tin!", "error");
        }
    }

    // Kiểm tra các select element
    var selects = x[currentTab].getElementsByTagName("select");
    for (i = 0; i < selects.length; i++) {
        if (selects[i].value == "") {
            selects[i].className += " invalid";
            valid = false;
            swal("Lỗi B!", "Vui lòng kiểm tra lại thông tin!", "error");
        }
    }

    // Kiểm tra các radio buttons
    var radioGroups = x[currentTab].getElementsByTagName("input");
    for (i = 0; i < radioGroups.length; i++) {
        if (radioGroups[i].type === "radio" && !isChecked(radioGroups[i].name)) {
            valid = false;
            swal("Lỗi C!", "Vui lòng kiểm tra lại thông tin!", "error");
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

/**/
// var searchParams = new URLSearchParams($window.location.search);
// var postIdFromQueryString = searchParams.get('id');
// var apiData = "/post-id/";
// $.ajax({
//     url: apiData + postIdFromQueryString,
//     type: 'GET',
//     success: function(dataFromApi) {
//         console.log(dataFromApi);
//     },
//     error: function(error) {
//         console.error('Error fetching data from API:', error);
//     }
// });
// goongjs.accessToken = 'GsUEtexN59WDYJ8cfpBllo4zFhQU17QbU1yGYNx2'; 

// let checkC = 0;
// var marker;
// var map = new goongjs.Map({
//     container: 'map',
//     style: 'https://tiles.goong.io/assets/goong_map_web.json',
//     center: [105.74241504658403, 10.060186701320404],
//     zoom: 13
// });

// // Add the control to the map.
// map.addControl(
//     new GoongGeocoder({
//         accessToken: 'HjqHdYaa2gKzvL9CZO903kwifZjFrj1cGPcTWdus',
//         goongjs: goongjs,
//         marker: false,
//         placeholder: "Nhập địa chỉ vào đây..."
//     })
// );
// map.addControl(
//     new goongjs.GeolocateControl({
//         positionOptions: {
//             enableHighAccuracy: true,
//             timeout: 1000
//         },
//         trackUserLocation: true,
//         showUserLocation: true
//     })
// );