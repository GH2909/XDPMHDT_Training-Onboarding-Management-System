document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("access_token");
    if (!token) {
        alert("Bạn chưa đăng nhập!");
        window.location.href = "../shared/auth/login.html";
        return;
    }

    const courseSelect = document.getElementById("courseSelect"); 
    const form = document.querySelector(".lesson-form");

    fetch("http://localhost:8080/training/hr/course", {
        method: "GET",
        headers: { "Authorization": "Bearer " + token }
    })
    .then(res => {
        if (!res.ok) throw new Error("HTTP " + res.status);
        return res.json();
    })
    .then(data => {
        console.log("Course API trả về:", data);

        data.forEach(course => {
            const option = document.createElement("option");
            option.value = course.courseName;              // giá trị là tên khóa học
            option.textContent = course.courseName; // hiển thị tên
            courseSelect.appendChild(option);
        });
    })
    .catch(err => {
        console.error(err);
        alert("Không tải được danh sách khóa học");
    });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        const lessonData = {
            courseName: courseSelect.value,   // Lấy tên khóa học từ select
            title: document.getElementById("title").value.trim(),
            duration: parseInt(document.getElementById("duration").value),
            description: document.getElementById("description").value.trim()
        };

        console.log("Gửi lên backend:", lessonData);

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
                console.error(result);
                alert("Lỗi: " + (result.message || "Validation failed"));
            }
        } catch (error) {
            console.error(error);
            alert("Không thể kết nối tới server!");
        }
    });
});
