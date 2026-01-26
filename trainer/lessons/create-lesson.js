document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("access_token");

    if (!token) {
        alert("Bạn chưa đăng nhập!");
        window.location.href = "/login.html";
        return;
    }

    const form = document.querySelector(".lesson-form");
    if (!form) return; // chỉ chạy ở trang create

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const lessonData = {
            course: parseInt(document.getElementById("course").value),
            title: document.getElementById("title").value.trim(),
            duration: parseInt(document.getElementById("duration").value),
            description: document.getElementById("description").value.trim(),
            createdBy: document.getElementById("createdBy").value
                ? parseInt(document.getElementById("createdBy").value)
                : null
        };

        try {
            const response = await fetch("http://localhost:8080/training/lessons/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + token
                },
                body: JSON.stringify(lessonData)
            });

            const result = await response.json();

            if (response.ok) {
                alert("Tạo bài giảng thành công!");
                window.location.href = "index.html";
            } else {
                alert("Lỗi: " + (result.message || "Không thể tạo bài giảng"));
            }

        } catch (error) {
            console.error(error);
            alert("Không thể kết nối tới server!");
        }
    });
});
