const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};

document.addEventListener('DOMContentLoaded', () => {
    loadCourseDetail();
});

function getCourseId(){
    return new URLSearchParams(window.location.search).get("courseId");
}

async function getEmployee(){
    const res = await fetch(`${API_BASE}/hr/assignments/me`, {
        headers: getAuthHeader()
    });
    if (!res.ok) throw new Error("Ko lấy được thông tin user");
    return await res.json();
} 

async function loadCourseDetail(){
    try {
        const courseId = getCourseId();
        if (!courseId) throw new Error("Thiếu courseId");
        const em = await getEmployee();
        const emId = em.id;
    
        const res = await fetch(`${API_BASE}/hr/assignments`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        const assigns = await res.json();
        const assign = assigns.find(a =>
            a.users?.id === emId
            && a.course?.id === courseId
        )
        renderCourseDetail(assign.course);
    } catch (e){
        alert(e.message);
    }
}

function renderCourseDetail(course) {
    // ===== HERO =====
    document.querySelector('.course-hero-text h1').innerText = course.courseName;
    document.querySelector('.course-hero-text p').innerText = course.description;

    // category → level
    const levelMap = {
        Onboarding: 'BEGINNER',
        Soft_Skills: 'INTERMEDIATE',
        Professional_Skills: 'ADVANCED',
        Regulations: 'BASIC'
    };

    const level = levelMap[course.category] ?? '---';

    document.querySelector('.course-category').innerText = level;
    document.querySelector('.course-hero-meta .fa-star')
        .nextElementSibling.innerText = level;

    document.querySelector('.course-hero-meta .fa-clock')
        .nextElementSibling.innerText = `${course.duration} giờ`;

    // ===== ICON =====
    const iconMap = {
        Onboarding: '🎯',
        Soft_Skills: '🗣️',
        Professional_Skills: '💻',
        Regulations: '⚠️'
    };

    document.getElementById('courseIcon').innerText =
        iconMap[course.category] ?? '📚';

    // ===== SIDEBAR =====
    document.getElementById('courseLevel').innerText = level;
    document.getElementById('courseDuration').innerText = `${course.duration} giờ`;
    document.getElementById('courseLessons').innerText = course.lessons.length + ' bài';

    // ===== GIẢNG VIÊN (lấy từ lesson đầu) =====
    if (course.lessons.length > 0) {
        const trainer = course.lessons[0].createdBy;
        document.getElementById('instructorName').innerText = trainer.fullName;
        document.getElementById('instructorRole').innerText = trainer.role.roleName;
        document.getElementById('instructorBio').innerText =
            `Giảng viên phụ trách ${course.courseName}`;
    }

    // ===== NỘI DUNG KHÓA HỌC =====
    const moduleContainer = document.querySelector('.course-modules');
    moduleContainer.innerHTML = `
        <div class="module">
            <div class="module-header">
                <i class="fas fa-folder-open"></i>
                <span class="module-title">Danh sách bài học</span>
            </div>
            <ul class="module-lessons">
                ${course.lessons.map((l, i) => `
                    <li>
                        ${i + 1}. ${l.title} 
                        <span style="color:#718096">(${l.duration} phút)</span>
                    </li>
                `).join('')}
            </ul>
        </div>
    `;
}
