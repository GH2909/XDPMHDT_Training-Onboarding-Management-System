// Mock Data
const mockData = {
    gapAnalysis: {
        labels: ['Lập trình', 'Quản lý', 'Giao tiếp', 'Phân tích', 'Leadership', 'Kỹ thuật'],
        currentSkills: [65, 45, 70, 55, 40, 60],
        requiredSkills: [85, 75, 80, 80, 70, 85]
    },
    suggestedCourses: [
        { id: 1, name: 'Advanced JavaScript & ES6+', duration: '40h', level: 'Nâng cao', gap: 'Lập trình' },
        { id: 2, name: 'Leadership Fundamentals', duration: '24h', level: 'Trung cấp', gap: 'Leadership' },
        { id: 3, name: 'Technical Architecture Design', duration: '32h', level: 'Nâng cao', gap: 'Kỹ thuật' }
    ],
    allCourses: [
        { id: 4, name: 'Project Management Professional', duration: '50h', level: 'Nâng cao' },
        { id: 5, name: 'Effective Communication Skills', duration: '16h', level: 'Cơ bản' },
        { id: 6, name: 'Data Analysis with Python', duration: '35h', level: 'Trung cấp' },
        { id: 7, name: 'Agile & Scrum Mastery', duration: '20h', level: 'Trung cấp' },
        { id: 8, name: 'Cloud Computing Essentials', duration: '28h', level: 'Cơ bản' },
        { id: 9, name: 'UX/UI Design Principles', duration: '30h', level: 'Trung cấp' }
    ],
    employees: [
        { id: 1, name: 'Nguyễn Văn An', department: 'it' },
        { id: 2, name: 'Trần Thị Bình', department: 'it' },
        { id: 3, name: 'Lê Văn Cường', department: 'hr' },
        { id: 4, name: 'Phạm Thị Dung', department: 'sales' },
        { id: 5, name: 'Hoàng Văn Em', department: 'marketing' },
        { id: 6, name: 'Vũ Thị Phương', department: 'it' }
    ]
};

let selectedCourse = null;
let selectedEmployees = [];

// Initialize Chart
function initChart() {
    const ctx = document.getElementById('gapAnalysisChart').getContext('2d');
    new Chart(ctx, {
        type: 'radar',
        data: {
            labels: mockData.gapAnalysis.labels,
            datasets: [
                {
                    label: 'Kỹ năng Hiện tại',
                    data: mockData.gapAnalysis.currentSkills,
                    borderColor: 'rgb(102, 126, 234)',
                    backgroundColor: 'rgba(102, 126, 234, 0.2)',
                    borderWidth: 2
                },
                {
                    label: 'Kỹ năng Yêu cầu',
                    data: mockData.gapAnalysis.requiredSkills,
                    borderColor: 'rgb(255, 152, 0)',
                    backgroundColor: 'rgba(255, 152, 0, 0.2)',
                    borderWidth: 2
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                r: {
                    beginAtZero: true,
                    max: 100,
                    ticks: {
                        stepSize: 20
                    }
                }
            },
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

// Render Courses
function renderCourses() {
    const suggestedContainer = document.getElementById('suggestedCourses');
    const allCoursesContainer = document.getElementById('allCourses');

    suggestedContainer.innerHTML = mockData.suggestedCourses.map(course => `
        <div class="course-item suggested" data-id="${course.id}">
            <div class="course-name">${course.name}</div>
            <div class="course-info">⏱️ ${course.duration}</div>
            <div class="course-info">📊 ${course.level}</div>
            <div class="course-info">🎯 Khoảng cách: ${course.gap}</div>
        </div>
    `).join('');

    allCoursesContainer.innerHTML = mockData.allCourses.map(course => `
        <div class="course-item" data-id="${course.id}">
            <div class="course-name">${course.name}</div>
            <div class="course-info">⏱️ ${course.duration}</div>
            <div class="course-info">📊 ${course.level}</div>
        </div>
    `).join('');

    // Add click handlers
    document.querySelectorAll('.course-item').forEach(item => {
        item.addEventListener('click', function() {
            document.querySelectorAll('.course-item').forEach(i => i.classList.remove('selected'));
            this.classList.add('selected');
            selectedCourse = this.dataset.id;
            updateSaveButton();
        });
    });
}

// Render Employees
function renderEmployees() {
    const container = document.getElementById('employeeCheckboxes');
    container.innerHTML = mockData.employees.map(emp => `
        <div class="checkbox-item">
            <input type="checkbox" id="emp-${emp.id}" value="${emp.id}" data-dept="${emp.department}">
            <label for="emp-${emp.id}">${emp.name} (${getDepartmentName(emp.department)})</label>
        </div>
    `).join('');

    // Add change handlers
    document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => {
        cb.addEventListener('change', function() {
            if (this.checked) {
                selectedEmployees.push(this.value);
            } else {
                selectedEmployees = selectedEmployees.filter(id => id !== this.value);
            }
            updateSaveButton();
        });
    });
}

// Get Department Name
function getDepartmentName(code) {
    const names = {
        'it': 'IT',
        'hr': 'Nhân sự',
        'sales': 'Kinh doanh',
        'marketing': 'Marketing'
    };
    return names[code] || code;
}

// Update Save Button State
function updateSaveButton() {
    const btn = document.getElementById('saveBtn');
    if (btn) {
        btn.disabled = !(selectedCourse && selectedEmployees.length > 0);
    }
}

// Save Handler
function handleSave() {
    if (!selectedCourse || selectedEmployees.length === 0) return;

    // Show loading
    document.getElementById('loadingOverlay').classList.add('show');

    // Simulate API call
    setTimeout(() => {
        // Hide loading
        document.getElementById('loadingOverlay').classList.remove('show');

        // Update stats
        const statEl = document.getElementById('assignedCourses');
        const currentCount = parseInt(statEl.textContent);
        statEl.textContent = currentCount + 1;
        statEl.style.transform = 'scale(1.2)';
        setTimeout(() => statEl.style.transform = 'scale(1)', 300);

        // Show success toast
        const toast = document.getElementById('toast');
        toast.classList.add('show');
        setTimeout(() => toast.classList.remove('show'), 4000);

        // Console log
        console.log('✅ Dữ liệu đã lưu:');
        console.log('Khóa học ID:', selectedCourse);
        console.log('Nhân viên IDs:', selectedEmployees);
        console.log('🔄 Triggering KPI/Career Path evaluation flow...');
        console.log('📧 Notification sent to employees');

        // Reset form
        setTimeout(() => {
            document.querySelectorAll('.course-item').forEach(i => i.classList.remove('selected'));
            document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => cb.checked = false);
            document.getElementById('departmentSelect').value = '';
            selectedCourse = null;
            selectedEmployees = [];
            updateSaveButton();
        }, 1500);
    }, 1500);
}

// Cancel Handler
function handleCancel() {
    document.querySelectorAll('.course-item').forEach(i => i.classList.remove('selected'));
    document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => cb.checked = false);
    document.getElementById('departmentSelect').value = '';
    selectedCourse = null;
    selectedEmployees = [];
    updateSaveButton();
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    // Initialize components
    initChart();
    renderCourses();
    renderEmployees();

    // Department Selection Handler
    const departmentSelect = document.getElementById('departmentSelect');
    if (departmentSelect) {
        departmentSelect.addEventListener('change', function() {
            const dept = this.value;
            document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => {
                if (dept === '') {
                    cb.checked = false;
                } else if (cb.dataset.dept === dept) {
                    cb.checked = true;
                } else {
                    cb.checked = false;
                }
            });
            
            selectedEmployees = Array.from(document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]:checked'))
                .map(cb => cb.value);
            updateSaveButton();
        });
    }

    // Save Button Handler
    const saveBtn = document.getElementById('saveBtn');
    if (saveBtn) {
        saveBtn.addEventListener('click', handleSave);
    }

    // Cancel Button Handler
    const cancelBtn = document.getElementById('cancelBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', handleCancel);
    }

    // Action Buttons Handler
    const actionButtons = document.querySelectorAll('.action-btn');
    actionButtons.forEach((btn, index) => {
        btn.addEventListener('click', function() {
            const actions = ['Tài nguyên', 'Khóa học gần đây', 'Tất cả khóa học'];
            console.log(`Clicked: ${actions[index]}`);
            alert(`Chức năng "${actions[index]}" đang được phát triển...`);
        });
    });

    // Menu Icon Handler
    const menuIcon = document.querySelector('.menu-icon');
    if (menuIcon) {
        menuIcon.addEventListener('click', function() {
            console.log('Menu clicked');
            alert('Menu navigation\nChức năng đang được phát triển...');
        });
    }

    // User Icon Handler
    const userIcon = document.querySelector('.user-icon');
    if (userIcon) {
        userIcon.addEventListener('click', function() {
            console.log('User profile clicked');
            alert('Thông tin người dùng\nChức năng đang được phát triển...');
        });
    }

    console.log('HR Training Management System initialized successfully! ✅');
    console.log('Total courses available:', mockData.allCourses.length + mockData.suggestedCourses.length);
});