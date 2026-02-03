// Dashboard
const dashboardAPI = {
    async getDashboardStats() {
        const [coursesRes, usersRes] = await Promise.all([
            fetch(`${API_BASE}/hr/course`, {method:"GET", headers: getAuthHeader() }),
            fetch(`${API_BASE}/employee/profile`, {method:"GET", headers: getAuthHeader() })
        ]);

        if (!coursesRes.ok || !usersRes.ok) {
            throw new Error('Không load dashboard stats');
        }

        const coursesData = await coursesRes.json();
        const usersData = await usersRes.json();

        return {
            totalCourses: Array.isArray(coursesData) ? coursesData.length : 0,
            totalUsers: Array.isArray(usersData.data) ? usersData.data.length : 0,
            completedCourses: 0,
            totalCertificates: 0
        };
    },

    async getRecentCourses(limit = 5) {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method:"GET",
            headers: getAuthHeader()
        });
        if (!res.ok) throw new Error('Không load course');

        const courses = await res.json();

        return {
            courses: Array.isArray(courses)
                ? courses.slice(0, limit)
                : []
        };
    }
};

// Courses
const courseAPI ={
    async getAllCourses(){
        const res = await fetch(`${API_BASE}/hr/course`, {
            method:"GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error('Ko load được courses');
        return res.json();
    }
};


document.addEventListener('DOMContentLoaded', async () => {
    try {
        await loadDashboardData();
    } catch (e) {
        console.warn('Dashboard stats lỗi', e);
    }

    try {
        await loadRecentCourses();
    } catch (e) {
        console.warn('Recent courses lỗi', e);
    }

    try {
        await loadAllCourses();
    } catch (e) {
        console.warn('All courses lỗi', e);
    }

    try {
        await loadCompetencyFrameworks();
    } catch (e) {
        console.warn('Competency lỗi', e);
    }

    setupViewButtons();
});

async function loadDashboardData() {
    try {
        const stats = await dashboardAPI.getDashboardStats();

        updateCard('courses', stats.totalCourses, 'Tổng số khóa học');
        updateCard('completed', stats.completedCourses, 'Khóa học đã hoàn thành');
        updateCard('users', stats.totalUsers, 'Người dùng đã đăng ký');
        updateCard('certificates', stats.totalCertificates, 'Giấy chứng nhận đã cấp');

    } catch (error) {
        console.error('Dashboard stats error:', error);
        showConnectionWarning(error.message);
    }
}

// Count employee
async function enrichCoursesWithEmployees(courses) {
    const res = await fetch(`${API_BASE}/hr/assignments`, {
        method:"GET",
        headers: getAuthHeader()
    });

    if (!res.ok) throw new Error('Không load được assignments');

    const assignments = await res.json();

    return courses.map(course => {
        const count = assignments.filter(
            a => a.course?.id === course.id
        ).length;

        return {
            id: course.id,
            courseName: course.courseName,
            category: course.category,
            duration: course.duration,
            totalEmployees: count
        };
    });
}


// Load dashboard 
async function loadRecentCourses() {
    const container = document.querySelector('.right-content .placeholder-box1');
    if (!container) return;

    try {
        const { courses: rawCourses } = await dashboardAPI.getRecentCourses(5);
        const courses = await enrichCoursesWithEmployees(rawCourses);
        
        
        if (!courses.length) {
            container.innerHTML = '<h4>Chưa có khóa học nào được hiển thị</h4>';
            return;
        }

        const categoryIcons = {
            Onboarding: '🎯',
            Soft_Skills: '🗣️',
            Professional_Skills: '💻',
            Regulations: '⚠️'
        };

        container.innerHTML = `
            <div class="recent-courses-list">
                ${courses.map(course => {
                    const icon = categoryIcons[course.category] || '📚';
                    const categoryDisplay = course.category?.replace(/_/g, ' ') ?? 'N/A';

                    return `
                        <div class="recent-course-card"
onclick="window.location.href='course_management.html'">

                            <div class="course-left">
                                <div class="course-icon-big">${icon}</div>
                            </div>

                            <div class="course-right">
                                <div class="course-name">
                                    ${course.courseName ?? 'N/A'}
                                </div>

                                <div class="course-values">
                                    <div class="value-box">
                                        <span class="value-label">⏱️ Thời lượng</span>
                                        <span class="value-number">${course.duration ?? 0} giờ</span>
                                    </div>

                                    <div class="value-box">
                                        <span class="value-label">👥 Nhân viên</span>
                                        <span class="value-number">${course.totalEmployees}</span>
                                    </div>

                                    <div class="value-box tag">
                                        🏷️ ${categoryDisplay}
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                }).join('')}
            </div>
        `;
    } catch (error) {
        console.error(error);
        container.innerHTML = '<h4 style="color:#718096">Không thể tải khóa học gần đây</h4>';
    }
}

// Update card with data
function updateCard(cardType, value, title) {
    // Find the card by title
    const cards = document.querySelectorAll('.left-sidebar .card');
    cards.forEach(card => {
        const cardTitle = card.querySelector('.card-title h2');
        if (cardTitle && cardTitle.textContent.includes(title)) {
            const subtitle = card.querySelector('.card-title h4');
            if (subtitle) {
                if (value === 0) {
                    subtitle.textContent = getDefaultMessage(title);
                } else {
                    subtitle.textContent = `${value} ${getUnit(title)}`;
                }
            }
        }
    });
}

// Get default message for empty state
function getDefaultMessage(title) {
    const messages = {
        'Tổng số khóa học': 'Chưa có khóa học nào được hiển thị',
        'Khóa học đã hoàn thành': 'Chưa có khóa học nào được hiển thị',
        'Người dùng đã đăng ký': 'Chưa có người dùng nào được đăng ký',
        'Giấy chứng nhận đã cấp': 'Chưa có giấy chứng nhận nào được cấp'
    };
    return messages[title] || 'Chưa có dữ liệu';
}

// Get unit for value
function getUnit(title) {
    if (title.includes('khóa học')) return 'khóa học';
if (title.includes('người dùng')) return 'người dùng';
    if (title.includes('chứng nhận')) return 'chứng nhận';
    return '';
}

function setupViewButtons() {
    const viewButtons = document.querySelectorAll('.view-btn');
viewButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            const card = this.closest('.card');
            const title = card.querySelector('.card-title h2').textContent;
            
            // Navigate based on card type
            if (title.includes('Tổng số khóa học') || title.includes('Khóa học đã hoàn thành')) {
                window.location.href = 'course_management.html';
            } else if (title.includes('Người dùng')) {
                alert('Trang quản lý người dùng đang được phát triển...');
            } else if (title.includes('chứng nhận')) {
                alert('Trang quản lý chứng nhận đang được phát triển...');
            }
        });
    });
    
    // Setup banner buttons
    const bannerButtons = document.querySelectorAll('.banner-btn');
    bannerButtons.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            if (index === 0) {
                // Tài nguyên
                alert('Trang tài nguyên đang được phát triển...');
            } else if (index === 1) {
                // Khóa học gần đây
                window.location.href = 'course_management.html';
            } else if (index === 2) {
                // Tất cả khóa học
                window.location.href = 'course_management.html';
            }
        });
    });
}