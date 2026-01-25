document.addEventListener("DOMContentLoaded", loadLessons);

async function loadLessons() {
    const token = localStorage.getItem("access_token");

    try {
        const response = await fetch("http://localhost:8080/training/lessons/all", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Không thể tải danh sách bài giảng");
        }

        const result = await response.json();
        const lessons = result.data;   // 🔥 QUAN TRỌNG

        const container = document.getElementById("lessonsContainer");
        container.innerHTML = "";

        lessons.forEach(lesson => {
            const lessonCard = document.createElement("div");
            lessonCard.className = "lesson-card";

            lessonCard.innerHTML = `
                <div class="lesson-header">
                    <div class="lesson-type video">Lesson</div>
                    <div class="lesson-actions">
                        <a href="edit-lesson.html?id=${lesson.id}" class="action-btn" title="Sửa">
                            <i class="fas fa-edit"></i>
                        </a>
                        <a href="#" class="action-btn" title="Xóa" onclick="deleteLesson(${lesson.id})">
                            <i class="fas fa-trash"></i>
                        </a>
                    </div>
                </div>

                <div class="lesson-content">
                    <h3 class="lesson-title">${lesson.title}</h3>
                    <p class="lesson-meta">
                        <span><i class="fas fa-clock"></i> ${lesson.duration} phút</span>
                    </p>
                    <p class="lesson-description">${lesson.description}</p>
                </div>

            `;

            container.appendChild(lessonCard);
        });

    } catch (error) {
        alert(error.message);
    }
}
