document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("lessonsContainer");
    if (!container) return;

    loadLessons();
});

async function loadLessons() {
    const token = localStorage.getItem("access_token");

    if (!token) {
        alert("Bạn chưa đăng nhập!");
        window.location.href = "/login.html";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/training/lessons/all", {
            method: "GET",
            headers: { 
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token 
            }
        });

        if (!response.ok) throw new Error("Không thể tải danh sách bài giảng");

        const result = await response.json();
        const lessons = result.data;

        const container = document.getElementById("lessonsContainer");
        container.innerHTML = "";

        lessons.forEach(lesson => {
            const lessonCard = document.createElement("div");
            lessonCard.className = "lesson-card";

            lessonCard.innerHTML = `
                <div class="lesson-header">
                    <div class="lesson-type video">Lesson</div>
                    <div class="lesson-actions">
                        <a href="edit-lesson.html?id=${lesson.id}" class="action-btn"><i class="fas fa-edit"></i></a>
                        <a href="#" class="action-btn" onclick="deleteLesson(${lesson.id})"><i class="fas fa-trash"></i></a>
                    </div>
                </div>
                <div class="lesson-content">
                    <h3 class="lesson-title">${lesson.title}</h3>
                    <p><i class="fas fa-clock"></i> ${lesson.duration} phút</p>
                    <p>${lesson.description ?? ""}</p>
                </div>
            `;

            container.appendChild(lessonCard);
        });

    } catch (error) {
        alert(error.message);
    }
}
