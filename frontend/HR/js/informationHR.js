
document.addEventListener("DOMContentLoaded", () => {
    console.log("Profile page loaded successfully");
});

function previewAvatar(event) {
    const file = event.target.files[0];
    const avatarImg = document.getElementById("avatarImg");

    if (!file) return;

    if (!file.type.startsWith("image/")) {
        alert("Vui lòng chọn file hình ảnh");
        return;
    }

    const maxSize = 2 * 1024 * 1024;
    if (file.size > maxSize) {
        alert("Ảnh không được vượt quá 2MB");
        return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
        avatarImg.src = e.target.result;
    };
    reader.readAsDataURL(file);
}

function logout(event) {
    event.preventDefault();

    const confirmLogout = confirm("Bạn có chắc chắn muốn đăng xuất không?");
    if (!confirmLogout) return;

    localStorage.removeItem("user");
    sessionStorage.clear();

    window.location.href = "login.html";
}
