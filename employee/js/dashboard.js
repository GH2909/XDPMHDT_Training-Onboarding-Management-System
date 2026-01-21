// ===== EMPLOYEE DASHBOARD - dashboard.js =====
// UC04: Tham gia khóa học và cập nhật thông tin cá nhân

// ===== API ENDPOINTS =====
const API_BASE_URL = 'http://localhost:8080/api'; // TODO: Cấu hình đúng URL BE
const endpoints = {
    dashboard: `${API_BASE_URL}/employee/dashboard`,
    courses: `${API_BASE_URL}/employee/courses`,
    progress: `${API_BASE_URL}/employee/progress`,
    certificates: `${API_BASE_URL}/employee/certificates`,
    profile: `${API_BASE_URL}/employee/profile`,
    stats: `${API_BASE_URL}/employee/stats`
};

// ===== GLOBAL STATE =====
let dashboardData = {
    employee: null,
    stats: {
        enrolledCourses: 0,
        completedCourses: 0,
        completionPercentage: 0,
        certificates: 0
    },
    courses: [],
    recentCourses: [],
    certificates: []
};

// ===== CHECK AUTH STATUS =====
function checkAuthStatus() {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    if (!token || role !== 'employee') {
        window.location.href = '../../../shared/pages/auth/login.html';
        return false;
    }
    return true;
}

// ===== INIT DASHBOARD =====
document.addEventListener('DOMContentLoaded', function () {
    console.log('Dashboard JS loaded');

    if (!checkAuthStatus()) return;

    initializeDashboard();
    attachEventListeners();
});

// ===== INITIALIZE DASHBOARD =====
async function initializeDashboard() {
    try {
        showLoadingState();

        // Load data từ API
        const [dashData, statsData, coursesData, certificatesData] = await Promise.all([
            fetchDashboardData(),
            fetchStatsData(),
            fetchCoursesData(),
            fetchCertificatesData()
        ]);

        // Update state
        dashboardData.employee = dashData?.employee;
        dashboardData.stats = statsData;
        dashboardData.courses = coursesData || [];
        dashboardData.certificates = certificatesData || [];
        dashboardData.recentCourses = coursesData?.slice(0, 6) || [];

        // Render UI
        renderDashboard();
        hideLoadingState();

        console.log('Dashboard initialized successfully');
    } catch (error) {
        console.error('Error initializing dashboard:', error);
        showErrorNotification('Lỗi tải dashboard. Vui lòng tải lại trang.');
        hideLoadingState();
    }
}

// ===== FETCH DATA FROM API =====
async function fetchDashboardData() {
    try {
        const response = await fetch(endpoints.dashboard, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error('Failed to fetch dashboard');
        return await response.json();
    } catch (error) {
        console.error('Fetch dashboard error:', error);
        // Fallback mock data
        return getMockDashboardData();
    }
}

async function fetchStatsData() {
    try {
        const response = await fetch(endpoints.stats, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error('Failed to fetch stats');
        return await response.json();
    } catch (error) {
        console.error('Fetch stats error:', error);
        return getMockStatsData();
    }
}

async function fetchCoursesData() {
    try {
        const response = await fetch(endpoints.courses, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error('Failed to fetch courses');
        return await response.json();
    } catch (error) {
        console.error('Fetch courses error:', error);
        return getMockCoursesData();
    }
}

async function fetchCertificatesData() {
    try {
        const response = await fetch(endpoints.certificates, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error('Failed to fetch certificates');
        return await response.json();
    } catch (error) {
        console.error('Fetch certificates error:', error);
        return [];
    }
}

// ===== RENDER DASHBOARD UI =====
function renderDashboard() {
    renderHeroSection();
    renderFeaturesSection();
    renderCoursesSection();
}

function renderHeroSection() {
    // Hero section đã được render từ HTML, chỉ cần update dữ liệu nếu cần
    const heroText = document.querySelector('.hero-text h1');
    if (heroText && dashboardData.employee?.fullName) {
        heroText.textContent = `Welcome, ${dashboardData.employee.fullName}`;
    }
}

function renderFeaturesSection() {
    const featuresSection = document.querySelector('.features');
    if (!featuresSection) return;

    const stats = dashboardData.stats;

    // Cập nhật stats nếu có element trong HTML
    const featureCards = document.querySelectorAll('.feature-card');
    if (featureCards.length >= 3) {
        // Card 1: Specialized Courses
        featureCards[0].querySelector('h3').textContent = `${stats.enrolledCourses} Khóa Học`;
        featureCards[0].querySelector('p').textContent = `Bạn đã tham gia ${stats.enrolledCourses} khóa học`;

        // Card 2: Active Community
        featureCards[1].querySelector('h3').textContent = `${stats.completionPercentage}% Hoàn Thành`;
        featureCards[1].querySelector('p').textContent = `Tiến độ học tập hiện tại của bạn`;

        // Card 3: Certifications
        featureCards[2].querySelector('h3').textContent = `${stats.certificates} Chứng Chỉ`;
        featureCards[2].querySelector('p').textContent = `Bạn đã đạt được ${stats.certificates} chứng chỉ`;
    }
}

function renderCoursesSection() {
    const coursesGrid = document.querySelector('.courses-grid');
    if (!coursesGrid || !dashboardData.recentCourses.length) return;

    // Clear existing courses
    const courseCards = coursesGrid.querySelectorAll('.course-card');
    if (courseCards.length > 0) {
        // Update dữ liệu vào các card đã có
        dashboardData.recentCourses.slice(0, 6).forEach((course, index) => {
            if (courseCards[index]) {
                updateCourseCard(courseCards[index], course);
            }
        });
    }
}

function updateCourseCard(cardElement, courseData) {
    try {
        // Update category
        const categoryEl = cardElement.querySelector('.course-category');
        if (categoryEl) categoryEl.textContent = courseData.level?.toUpperCase() || 'BEGINNER';

        // Update title
        const titleEl = cardElement.querySelector('.course-title');
        if (titleEl) titleEl.textContent = courseData.title || 'Untitled Course';

        // Update description
        const descEl = cardElement.querySelector('.course-desc');
        if (descEl) descEl.textContent = courseData.description || 'No description';

        // Update level display
        const levelEl = cardElement.querySelector('.course-level');
        if (levelEl) {
            levelEl.innerHTML = `<i class="fas fa-star"></i> ${courseData.level || 'Beginner'}`;
        }

        // Update button click handler
        const button = cardElement.querySelector('.course-button');
        if (button) {
            button.onclick = () => enrollOrStartCourse(courseData.id, courseData.enrolled);
            button.textContent = courseData.enrolled ? 'Tiếp tục' : 'Học ngay';
        }

        // Update image placeholder if exists
        const imagePlaceholder = cardElement.querySelector('.course-image-placeholder');
        if (imagePlaceholder && courseData.icon) {
            imagePlaceholder.textContent = courseData.icon;
        }
    } catch (error) {
        console.error('Error updating course card:', error);
    }
}

// ===== COURSE ACTIONS =====
async function enrollOrStartCourse(courseId, isEnrolled) {
    try {
        if (!isEnrolled) {
            // Enroll course
            const response = await fetch(`${endpoints.courses}/${courseId}/enroll`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) throw new Error('Failed to enroll course');

            showSuccessNotification('Đăng ký khóa học thành công!');

            // Update stats
            dashboardData.stats.enrolledCourses += 1;
            renderFeaturesSection();
        }

        // Redirect to learning page
        window.location.href = `../courses/learning.html?courseId=${courseId}`;
    } catch (error) {
        console.error('Enroll error:', error);
        showErrorNotification('Lỗi khi tham gia khóa học. Vui lòng thử lại.');
    }
}

// ===== UTILITY FUNCTIONS =====
function showLoadingState() {
    const loader = document.querySelector('.loading-spinner') || createLoadingSpinner();
    loader.style.display = 'flex';
}

function hideLoadingState() {
    const loader = document.querySelector('.loading-spinner');
    if (loader) loader.style.display = 'none';
}

function createLoadingSpinner() {
    const spinner = document.createElement('div');
    spinner.className = 'loading-spinner';
    spinner.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
    spinner.style.cssText = `
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        font-size: 3rem;
        color: #8b5cf6;
        z-index: 9999;
        display: flex;
        align-items: center;
        justify-content: center;
    `;
    document.body.appendChild(spinner);
    return spinner;
}

function showSuccessNotification(message) {
    showNotification(message, 'success');
}

function showErrorNotification(message) {
    showNotification(message, 'error');
}

function showWarningNotification(message) {
    showNotification(message, 'warning');
}

function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `
        <div class="notification-content">
            <i class="fas fa-${type === 'success' ? 'check-circle' : type === 'error' ? 'exclamation-circle' : 'info-circle'}"></i>
            <span>${message}</span>
        </div>
    `;

    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 1rem 1.5rem;
        background: ${type === 'success' ? '#10b981' : type === 'error' ? '#ef4444' : '#3b82f6'};
        color: white;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        z-index: 10000;
        animation: slideIn 0.3s ease;
        display: flex;
        align-items: center;
        gap: 0.8rem;
    `;

    document.body.appendChild(notification);

    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => notification.remove(), 300);
    }, 4000);
}

// ===== ATTACH EVENT LISTENERS =====
function attachEventListeners() {
    // Course buttons
    document.querySelectorAll('.course-button').forEach(button => {
        button.addEventListener('click', function (e) {
            e.preventDefault();
            const courseCard = this.closest('.course-card');
            const courseTitle = courseCard?.querySelector('.course-title')?.textContent;
            console.log('Course clicked:', courseTitle);
        });
    });

    // Explore Courses button
    document.querySelector('.explore-courses-btn')?.addEventListener('click', function (e) {
        e.preventDefault();
        window.location.href = '../courses/list.html';
    });

    // View All button
    document.querySelector('.view-all-btn')?.addEventListener('click', function () {
        window.location.href = '../courses/list.html';
    });

    // Get Started button
    document.querySelector('.btn-primary')?.addEventListener('click', function () {
        window.location.href = '../courses/list.html';
    });

    // User avatar - open profile
    document.querySelector('.user-avatar')?.addEventListener('click', function () {
        window.location.href = '../profile/index.html';
    });

    // Search functionality
    const searchInput = document.querySelector('.search-box input');
    if (searchInput) {
        searchInput.addEventListener('keyup', debounce(handleSearch, 500));
    }
}

// ===== SEARCH FUNCTIONALITY =====
function handleSearch(event) {
    const query = event.target.value.trim().toLowerCase();

    if (query.length < 2) {
        return;
    }

    const courseCards = document.querySelectorAll('.course-card');
    courseCards.forEach(card => {
        const title = card.querySelector('.course-title')?.textContent.toLowerCase() || '';
        const desc = card.querySelector('.course-desc')?.textContent.toLowerCase() || '';

        if (title.includes(query) || desc.includes(query)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}

// ===== DEBOUNCE UTILITY =====
function debounce(func, delay) {
    let timeoutId;
    return function (...args) {
        clearTimeout(timeoutId);
        timeoutId = setTimeout(() => func.apply(this, args), delay);
    };
}

// ===== MOCK DATA (for testing without backend) =====
function getMockDashboardData() {
    return {
        employee: {
            id: 1,
            fullName: 'Nguyễn Văn A',
            email: 'nguyenvana@company.com',
            avatar: 'https://inkythuatso.com/uploads/thumbnails/800/2022/03/anh-da-dien-fb-hai-32-29-13-54-49.jpg'
        }
    };
}

function getMockStatsData() {
    return {
        enrolledCourses: 5,
        completedCourses: 2,
        completionPercentage: 45,
        certificates: 2
    };
}

function getMockCoursesData() {
    return [
        {
            id: 1,
            title: 'Khóa Học Làm Màu',
            description: 'Học cơ bản về thiết kế và tạo hình ảnh sáng tạo',
            level: 'Beginner',
            icon: '👨‍👩‍👧‍👦',
            enrolled: true,
            progress: 75
        },
        {
            id: 2,
            title: 'Lập Trình Web Truyền Thuyết',
            description: 'Nắm vững các kỹ năng lập trình web từ HTML đến JavaScript',
            level: 'Intermediate',
            icon: '📚',
            enrolled: true,
            progress: 60
        },
        {
            id: 3,
            title: 'Làm Hắc Cơ',
            description: 'Học Cybersecurity & Ethical Hacking từ những chuyên gia',
            level: 'Advanced',
            icon: '🛡️',
            enrolled: false,
            progress: 0
        },
        {
            id: 4,
            title: 'Game Development Basics',
            description: 'Bắt đầu hành trình phát triển game với Unity & Unreal',
            level: 'Beginner',
            icon: '🎮',
            enrolled: false,
            progress: 0
        },
        {
            id: 5,
            title: 'AI & Machine Learning',
            description: 'Khám phá thế giới AI, ML và Deep Learning',
            level: 'Advanced',
            icon: '🤖',
            enrolled: false,
            progress: 0
        },
        {
            id: 6,
            title: 'Cloud Computing',
            description: 'Tìm hiểu AWS, Azure, Google Cloud và công nghệ cloud',
            level: 'Intermediate',
            icon: '☁️',
            enrolled: false,
            progress: 0
        }
    ];
}

// ===== LOGOUT FUNCTIONALITY =====
function logout() {
    if (confirm('Bạn chắc chắn muốn đăng xuất?')) {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('email');
        window.location.href = '../../../shared/pages/auth/login.html';
    }
}

// ===== EXPORT FOR EXTERNAL USE =====
window.dashboardApp = {
    enrollOrStartCourse,
    logout,
    handleSearch
}; 