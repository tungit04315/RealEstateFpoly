function recaptchaCallback() {
        var captchaAlert = document.getElementById("captcha-alert");
        
        if (typeof grecaptcha !== 'undefined' && grecaptcha.getResponse().length > 0) {
            // Người dùng đã xác nhận CAPTCHA.
            // Ẩn thông báo
            captchaAlert.style.display = "none";
        } else {
            // Người dùng chưa xác nhận CAPTCHA.
            // Hiển thị thông báo
            captchaAlert.style.display = "block";
        }
    }
     function validateFormSignUp() {
            if (typeof grecaptcha !== 'undefined' && grecaptcha.getResponse().length > 0) {
                // Người dùng đã xác nhận CAPTCHA.
                // Bạn có thể thực hiện các hành động tiếp theo tại đây.

                // Ẩn thông báo
                document.getElementById("captcha-alert").style.display = "none";
            } else {
                // Người dùng chưa xác nhận CAPTCHA.
                // Hiển thị thông báo
                document.getElementById("captcha-alert").style.display = "block";
            }
        }



function Validator(options) {
	/*options là đối tượng Validator bên trang html*/
    var selectorRules = {};
    //Lấy các thành phần của form cần validate
    var formElement = document.querySelector(options.form);/*options.form là form #dangky hoặc #dangnhap*/

    if (formElement) {
        formElement.onsubmit = function(e) {
            e.preventDefault();
            var isFormValid = true;
            options.rules.forEach(function(rule) {
                var inputElement = formElement.querySelector(rule.selector);
                var isValid = validate(inputElement, rule);
                if(!isValid){
					isFormValid = false;
				}
            });
            if(isFormValid){
				console.log('Không có lỗi');
				formElement.submit();
/*				Swal.fire(
  							'Thành công!',
  							'',
  							'success'
							)
		        setTimeout(function() {
					formElement.submit();
		        }, 1100);*/		
			}
        }
        options.rules.forEach(function(rule) {
            //lưu lại rule
            if (Array.isArray(selectorRules[rule.selector])) {
                selectorRules[rule.selector].push(rule.test);
            } else {
                selectorRules[rule.selector] = [rule.test];
            }

            var inputElement = formElement.querySelector(rule.selector);

            if (inputElement) {
                inputElement.onblur = function() {
                    validate(inputElement, rule);
                }
                inputElement.oninput = function() {
                    var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
                    errorElement.innerText = '';
                    inputElement.parentElement.classList.remove('invalid');
                }
            }
        });

    }
    //Lấy element của form cần validate
    //tiến hành validate
    function validate(inputElement, rule) {
        var errorMessage;
        var errorElement = inputElement.parentElement.querySelector(options.errorSelector);
        var rules = selectorRules[rule.selector];
        for (var i = 0; i < rules.length; ++i) {
            errorMessage = rules[i](inputElement.value);
            if (errorMessage) break;
        }
        if (errorMessage) {
            errorElement.innerText = errorMessage;
            inputElement.parentElement.classList.add('invalid');
        } else {
            errorElement.innerText = '';
            inputElement.parentElement.classList.remove('invalid');
        }
        
        return !errorMessage;
    }
    //tiến hành validate
}

Validator.isRequired = function(selector, message) {
    return {
		/*selector là các thẻ input #fullname,...*/
        selector: selector,
        test: function(value) {
            return value.trim() ? undefined : message || 'Vui lòng nhập trường này';
        }
    };
}
Validator.isEmail = function(selector) {
    return {
				/*selector là các thẻ input #fullname,...*/
        selector: selector,
        test: function(value) {
            var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value) ? undefined : 'Trường này phải là email';
        }
    };
}
Validator.isPhone = function(selector) {
    return {
				/*selector là các thẻ input #fullname,...*/
        selector: selector,
        test: function(value) {
            var regex = /(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$/;
            return regex.test(value) ? undefined : 'Số điện thoại không hợp lệ';
        }
    };
}
Validator.isConfirmed = function(selector, getConfirmValue, message) {
    return {
				/*selector là các thẻ input #fullname,...*/
        selector: selector,
        test: function(value) {
            return value === getConfirmValue() ? undefined : message || 'Trường không khớp';
        }
    }
}
Validator.chckPass = function(selector, message){
    return {
				/*selector là các thẻ input #fullname,...*/
        selector: selector,
        test: function(value) {
            var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}|:\"<>?~`]).{6,}$/;
            return regex.test(value) ? undefined : message||'Mật khẩu chưa đủ mạnh';
        }
    };
}