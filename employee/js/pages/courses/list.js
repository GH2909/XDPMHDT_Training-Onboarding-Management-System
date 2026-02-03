let courses = [];
const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};

document.addEventListener('DOMContentLoaded', () => {
    loadCourses();
});

async function getEmployee(){
    const res = await fetch(`${API_BASE}/hr/assignments/me`,{
        method: "GET",
        headers: getAuthHeader()
    });
    if (!res.ok) throw new Error("Ko lấy được thông tin user");
    return await res.json();
}

async function loadCourses(){
    try {
        const em = await getEmployee();
        const emId = em.id;
    
        const res = await fetch(`${API_BASE}/hr/assignments/${emId}`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        courses = (await res.json()).data ?? [];
        renderCourseList();
    } catch (e){
        alert(e.message);
    }
}

function renderCourseList(){
    const container = document.getElementById('courses-grid');

    if (courses.length === 0){
        container.innerHTML = '<p style="text-align: center; color: #718096; padding: 40px;">Chưa có khóa học nào được hiển thị.</p>';
        return;
    }

    container.innerHTML = courses.map(course => {
        const icon= categoryIcons[course.category];

        return `
        <div class="course-card">
            <div class="course-image">
                <div class="course-image-placeholder">${icon}</div>
            </div>

            <div class="course-info">
                <h3 class="course-name">${course.courseName}</h3>
                <div class = "course-compon">
                   <p class="course-desc">${course.description}</p>
                   <p class="course-duration">⏱ ${course.duration} giờ</p>
                </div>
                <a class="course-button"
                   href="/employee/courses/detail.html?courseId=${course.courseId}">
                   Xem chi tiết
                </a>
            </div>
        </div>
        `;
    }).join('');
}
