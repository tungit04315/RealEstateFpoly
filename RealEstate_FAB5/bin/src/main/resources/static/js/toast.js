const toast = document.querySelector(".toast");
const closeIcon = document.querySelector(".close");
const progress = document.querySelector(".progress");

let timer1;

// Hàm để ẩn toast và progress bar
function hideToast() {
    toast.classList.remove("active");
    progress.classList.remove("active");
}

// Sự kiện khi trang được tải
window.onload = function() {
    // Đợi 5 giây rồi ẩn toast
    timer1 = setTimeout(hideToast, 5000);
};

closeIcon.addEventListener("click", () => {
    hideToast();

    clearTimeout(timer1); // Xóa hẹn giờ ẩn tự động sau 5 giây
});