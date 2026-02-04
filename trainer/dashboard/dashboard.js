const dashboardAPI ={
    async getRecentLesson(limit = 3) {
        const res = await fetch(`${API_BASE}/lessons/all`, {
            method:"GET",
            headers: getAuthHeader()
        });
        if (!res.ok) throw new Error('Không load lesson');

        const lessons = await res.json();

        return {
            lessons: Array.isArray(lessons)
                ? lessons.slice(0, limit)
                : []
        };
    },
    async getRecentLesson(limit = 3) {
        const res = await fetch(`${API_BASE}/lessons/all`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error('Không load lesson');

        const json = await res.json();
        const data = json.data ?? [];

        return data.slice(0, limit);
    }
    
}

document.addEventListener('DOMContentLoaded', async () => {
try {
        await loadRecentLesson();
    } catch (e) {
        console.warn('Dashboard stats lỗi', e);
    }

});

async function loadRecentLesson(){
    const container = document.querySelector('.content-grid .card .list-lesson');
    if (!container) return;
    try {
        const lessons = await dashboardAPI.getRecentLesson(3);
        
        
        if (!lessons.length) {
            container.innerHTML = '<h4>Chưa có lesson nào được hiển thị</h4>';
            return;
        }

        container.innerHTML = `
                ${lessons.map(lesson => {

                    return `
                    <div class="list-item">
                        <div class="item-info">
                            <div class="item-title">${lesson.title ?? 'N/A'}</div>
                            <div class="item-meta">${lesson.courseName ?? 'N/A'}</div>
                        </div>
                    `;
                }).join('')}
        `;
    } catch (error) {
        console.error(error);
        container.innerHTML = '<h4 style="color:#718096">Không thể tải bài giảng gần đây</h4>';
    }
    
}