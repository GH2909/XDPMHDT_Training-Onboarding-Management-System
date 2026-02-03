const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};

document.addEventListener('DOMContentLoaded', () => {
    const params = new URLSearchParams(window.location.search);
    const courseId = params.get("courseId");
    if (!courseId){
        alert("Ko thấy id");
        return;
    }
    loadLesson();
});


async function loadLesson(courseId){
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