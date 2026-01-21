// Dashboard - Connected to Backend API

// Load dashboard data when page loads
document.addEventListener('DOMContentLoaded', async function() {
    // Test connection first (nếu function có sẵn)
    if (typeof testBackendConnection === 'function') {
        console.log('🔍 Đang kiểm tra kết nối backend...');
        try {
            const isConnected = await testBackendConnection();
            
            if (!isConnected && typeof getBackendConnectionStatus === 'function') {
                const status = getBackendConnectionStatus();
                console.warn('⚠️ Backend không kết nối được:', status.error);
                // Hiển thị thông báo cảnh báo nhưng vẫn thử load dữ liệu
                showConnectionWarning(status.error);
            } else if (isConnected) {
                console.log('✅ Backend đã kết nối thành công');
            }
        } catch (error) {
            console.warn('⚠️ Không thể test kết nối:', error);
        }
    } else {
        console.warn('⚠️ connectionTest.js chưa được load');
    }
    
    await loadDashboardData();
    await loadRecentCourses();
    await loadAllCourses();
    await loadCompetencyFrameworks();
    
    // Setup view buttons
    setupViewButtons();
});

// Show connection warning
function showConnectionWarning(error) {
    // Tạo banner cảnh báo ở đầu trang
    const warningBanner = document.createElement('div');
    warningBanner.id = 'connectionWarning';
    warningBanner.style.cssText = `
        background: #fff3cd;
        border: 2px solid #ffc107;
        border-radius: 8px;
        padding: 15px 20px;
        margin: 20px 50px;
        color: #856404;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    `;
    
    warningBanner.innerHTML = `
        <div style="flex: 1;">
            <strong>⚠️ Cảnh báo kết nối Backend</strong>
            <p style="margin: 5px 0 0 0; font-size: 14px;">${error || 'Không thể kết nối đến backend server'}</p>
            <p style="margin: 5px 0 0 0; font-size: 12px; color: #6c757d;">
                Vui lòng đảm bảo backend đang chạy tại <code>http://localhost:8080</code>
            </p>
        </div>
        <button onclick="this.parentElement.remove()" style="
            background: #ffc107;
            border: none;
            border-radius: 4px;
            padding: 8px 15px;
            cursor: pointer;
            font-weight: bold;
            color: #856404;
        ">✕ Đóng</button>
    `;
    
    // Thêm vào sau top-banner
    const topBanner = document.querySelector('.top-banner');
    if (topBanner && topBanner.parentNode) {
        topBanner.parentNode.insertBefore(warningBanner, topBanner.nextSibling);
    }
}

// Load dashboard statistics
async function loadDashboardData() {
    try {
        const stats = await dashboardAPI.getDashboardStats();
        
        // Update total courses card
        updateCard('totalCourses', stats.totalCourses || 0, 'Tổng số khóa học');
        
        // Update completed courses card
        updateCard('completedCourses', stats.completedCourses || 0, 'Khóa học đã hoàn thành');
        
        // Update registered users card
        updateCard('registeredUsers', stats.totalUsers || 0, 'Người dùng đã đăng ký');
        
        // Update certificates card
        updateCard('certificates', stats.totalCertificates || 0, 'Giấy chứng nhận đã cấp');
        
    } catch (error) {
        console.error('Error loading dashboard data:', error);
        // Show default values on error
        updateCard('totalCourses', 0, 'Tổng số khóa học');
        updateCard('completedCourses', 0, 'Khóa học đã hoàn thành');
        updateCard('registeredUsers', 0, 'Người dùng đã đăng ký');
        updateCard('certificates', 0, 'Giấy chứng nhận đã cấp');
    }
}

// Load recent courses
async function loadRecentCourses() {
    const container = document.querySelector('.right-content .placeholder-box1');
    if (!container) return;
    
    try {
        const data = await dashboardAPI.getRecentCourses(5);
        const courses = data.courses || [];
        
        if (courses.length === 0) {
            container.innerHTML = '<h4>Chưa có khóa học nào được hiển thị</h4>';
            return;
        }
        
        const categoryIcons = {
            'Onboarding': '🎯',
            'Soft_Skills': '🗣️',
            'Professional_Skills': '💻',
            'Regulations': '⚠️'
        };
        
        container.innerHTML = `
            <div class="recent-courses-list">
                ${courses.map(course => {
                    const icon = categoryIcons[course.category] || '📚';
                    const categoryDisplay = course.category ? course.category.replace('_', ' ') : '';
                    return `
                        <div class="recent-course-item" onclick="window.location.href='course_management.html'">
                            <div class="course-icon-small">${icon}</div>
                            <div class="course-info-small">
                                <div class="course-name-small">${course.courseName || 'N/A'}</div>
                                <div class="course-meta-small">
                                    <span>⏱️ ${course.duration || 0} giờ</span>
                                    <span>🏷️ ${categoryDisplay}</span>
                                </div>
                            </div>
                        </div>
                    `;
                }).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error loading recent courses:', error);
        if (container) {
            // Hiển thị lỗi nhẹ nhàng hơn cho phần recent courses
            container.innerHTML = '<h4 style="color: #718096;">Không thể tải khóa học gần đây. Vui lòng kiểm tra kết nối backend.</h4>';
        }
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

// Load all courses for dashboard
async function loadAllCourses() {
    const container = document.getElementById('allCoursesGrid');
    if (!container) return;
    
    try {
        const courses = await courseAPI.getAllCourses();
        
        // Kiểm tra nếu courses không phải là array
        if (!Array.isArray(courses)) {
            console.warn('Courses data is not an array:', courses);
            container.innerHTML = '<p style="text-align: center; color: #718096; padding: 20px;">Chưa có khóa học nào. <a href="course_management.html">Tạo khóa học mới</a></p>';
            return;
        }
        
        if (courses.length === 0) {
            container.innerHTML = '<p style="text-align: center; color: #718096; padding: 20px;">Chưa có khóa học nào. <a href="course_management.html">Tạo khóa học mới</a></p>';
            return;
        }
        
        // Hiển thị tối đa 6 khóa học đầu tiên
        const displayCourses = courses.slice(0, 6);
        
        const categoryIcons = {
            'Onboarding': '🎯',
            'Soft_Skills': '🗣️',
            'Professional_Skills': '💻',
            'Regulations': '⚠️'
        };
        
        container.innerHTML = displayCourses.map(course => {
            const icon = categoryIcons[course.category] || '📚';
            const categoryDisplay = course.category ? course.category.replace('_', ' ') : '';
            const durationDisplay = course.duration ? `${course.duration} giờ` : 'N/A';
            
            return `
                <div class="course-card-small" onclick="window.location.href='course_management.html'">
                    <div class="course-icon-small">${icon}</div>
                    <div class="course-name-small">${course.courseName || 'N/A'}</div>
                    <div class="course-meta-small">
                        <span>⏱️ ${durationDisplay}</span>
                        <span>🏷️ ${categoryDisplay}</span>
                    </div>
                </div>
            `;
        }).join('');
        
        // Nếu có nhiều hơn 6 khóa học, thêm thông báo
        if (courses.length > 6) {
            container.innerHTML += `<p style="text-align: center; margin-top: 15px;"><a href="course_management.html">Xem thêm ${courses.length - 6} khóa học khác →</a></p>`;
        }
    } catch (error) {
        console.error('Error loading all courses:', error);
        if (container) {
            const errorMsg = error.message || 'Lỗi không xác định';
            // Hiển thị lỗi ngắn gọn hơn
            const shortMsg = errorMsg.split('\n')[0]; // Chỉ lấy dòng đầu
            container.innerHTML = `
                <div style="text-align: center; color: #e53e3e; padding: 20px; background: #fff5f5; border-radius: 8px; border: 1px solid #fc8181;">
                    <p style="margin-bottom: 10px;"><strong>⚠️ Không thể tải danh sách khóa học</strong></p>
                    <p style="font-size: 13px; color: #718096; margin-bottom: 15px;">${shortMsg}</p>
                    <div style="font-size: 12px; color: #4a5568;">
                        <p>💡 <strong>Hướng dẫn khắc phục:</strong></p>
                        <ul style="text-align: left; display: inline-block; margin: 10px 0;">
                            <li>Kiểm tra backend đang chạy tại <code>http://localhost:8080</code></li>
                            <li>Kiểm tra CORS đã được cấu hình trên backend</li>
                            <li>Mở Console (F12) để xem lỗi chi tiết</li>
                        </ul>
                    </div>
                </div>
            `;
        }
    }
}

// Load competency frameworks for dashboard
async function loadCompetencyFrameworks() {
    const container = document.getElementById('competencyGrid');
    if (!container) return;
    
    try {
        const competencies = await competencyAPI.getAllCompetencies();
        
        // Kiểm tra nếu competencies không phải là array
        if (!Array.isArray(competencies)) {
            console.warn('Competencies data is not an array:', competencies);
            container.innerHTML = '<p style="text-align: center; color: #718096; padding: 20px;">Chưa có khung năng lực nào. <a href="competency_framework.html">Tạo khung năng lực mới</a></p>';
            return;
        }
        
        if (competencies.length === 0) {
            container.innerHTML = '<p style="text-align: center; color: #718096; padding: 20px;">Chưa có khung năng lực nào. <a href="competency_framework.html">Tạo khung năng lực mới</a></p>';
            return;
        }
        
        // Hiển thị tối đa 6 khung năng lực đầu tiên
        const displayCompetencies = competencies.slice(0, 6);
        
        const positionIcons = {
            'developer': '💻',
            'designer': '🎨',
            'marketing': '📱',
            'hr': '👥',
            'sales': '💼',
            'accountant': '💰',
            'manager': '📊'
        };
        
        const positionNames = {
            'developer': 'Lập trình viên',
            'designer': 'Thiết kế đồ họa',
            'marketing': 'Chuyên viên Marketing',
            'hr': 'Nhân viên Nhân sự',
            'sales': 'Nhân viên Kinh doanh',
            'accountant': 'Kế toán',
            'manager': 'Quản lý dự án'
        };
        
        container.innerHTML = displayCompetencies.map(comp => {
            const icon = positionIcons[comp.position] || '🎯';
            const positionDisplay = positionNames[comp.position] || comp.position || 'N/A';
            const skillsCount = comp.skills ? (Array.isArray(comp.skills) ? comp.skills.length : comp.skills.split(',').length) : 0;
            
            return `
                <div class="competency-card-small" onclick="window.location.href='competency_framework.html'">
                    <div class="competency-icon-small">${icon}</div>
                    <div class="competency-name-small">${comp.competencyName || 'N/A'}</div>
                    <div class="competency-meta-small">
                        <span>👔 ${positionDisplay}</span>
                        <span>🛠️ ${skillsCount} kỹ năng</span>
                    </div>
                </div>
            `;
        }).join('');
        
        // Nếu có nhiều hơn 6 khung năng lực, thêm thông báo
        if (competencies.length > 6) {
            container.innerHTML += `<p style="text-align: center; margin-top: 15px;"><a href="competency_framework.html">Xem thêm ${competencies.length - 6} khung năng lực khác →</a></p>`;
        }
    } catch (error) {
        console.error('Error loading competency frameworks:', error);
        if (container) {
            const errorMsg = error.message || 'Lỗi không xác định';
            // Hiển thị lỗi ngắn gọn hơn
            const shortMsg = errorMsg.split('\n')[0]; // Chỉ lấy dòng đầu
            container.innerHTML = `
                <div style="text-align: center; color: #e53e3e; padding: 20px; background: #fff5f5; border-radius: 8px; border: 1px solid #fc8181;">
                    <p style="margin-bottom: 10px;"><strong>⚠️ Không thể tải danh sách khung năng lực</strong></p>
                    <p style="font-size: 13px; color: #718096; margin-bottom: 15px;">${shortMsg}</p>
                    <div style="font-size: 12px; color: #4a5568;">
                        <p>💡 <strong>Hướng dẫn khắc phục:</strong></p>
                        <ul style="text-align: left; display: inline-block; margin: 10px 0;">
                            <li>Kiểm tra backend đang chạy tại <code>http://localhost:8080</code></li>
                            <li>Kiểm tra CORS đã được cấu hình trên backend</li>
                            <li>Mở Console (F12) để xem lỗi chi tiết</li>
                        </ul>
                    </div>
                </div>
            `;
        }
    }
}

// Setup view buttons
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
