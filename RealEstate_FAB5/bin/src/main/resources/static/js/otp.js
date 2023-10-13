const codes = document.querySelectorAll('.code')

codes[0].focus()

codes.forEach((code, idx) => {
    code.addEventListener('keydown', (e) => {
        if (e.key >= 0 && e.key <= 9) {
            codes[idx].value = ''
            setTimeout(() => codes[idx + 1].focus(), 10)
        } else if (e.key === 'Backspace') {
            setTimeout(() => codes[idx - 1].focus(), 10)
        }
    })
})

let timerOn = true;

function timer(remaining) {
    var m = Math.floor(remaining / 60);
    var s = remaining % 60;

    m = m < 10 ? '0' + m : m;
    s = s < 10 ? '0' + s : s;
    document.getElementById('timer').innerHTML = m + ':' + s;
    remaining -= 1;

    if (remaining >= 0 && timerOn) {
        setTimeout(function() {
            timer(remaining);
        }, 1000);
        return;
    }

    if (!timerOn) {
        // Do validate stuff here
        return;
    }

    // Do timeout stuff here
    alert('Timeout for otp');

}

timer(120);