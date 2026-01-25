// Dữ liệu mẫu - Khóa học đề xuất
const suggestedCoursesData = [
    {
        id: 'sg1',
        name: 'Leadership & Management',
        duration: '8 tuần',
        level: 'advanced',
        icon: '👔',
        description: 'Khóa học quản lý lãnh đạo nâng cao'
    },
    {
        id: 'sg2',
        name: 'Data Analysis with Excel',
        duration: '6 tuần',
        level: 'intermediate',
        icon: '📊',
        description: 'Phân tích dữ liệu với Excel'
    },
    {
        id: 'sg3',
        name: 'Communication Skills',
        duration: '4 tuần',
        level: 'basic',
        icon: '💬',
        description: 'Kỹ năng giao tiếp hiệu quả'
    }
];

//Tất cả khóa học
const allCoursesData = [];

// Nhân viên
const employeesData = [];

// Biến lưu trạng thái
let selectedCourse = null;
let selectedEmployees = [];

// Khởi tạo khi trang load
document.addEventListener('DOMContentLoaded', function() {
    renderSuggestedCourses();
    renderAllCourses();
    renderEmployees();
    initEventListeners();
});

// Render khóa học đề xuất
function renderSuggestedCourses() {
    const container = document.getElementById('suggestedCourses');
    container.innerHTML = suggestedCoursesData.map(course => createCourseCard(course)).join('');
    addCourseClickListeners();
}

// Render tất cả khóa học
function renderAllCourses() {
    const container = document.getElementById('allCourses');
    container.innerHTML = allCoursesData.map(course => createCourseCard(course)).join('');
    addCourseClickListeners();
}

// Tạo HTML cho course card
function createCourseCard(course) {
    const levelClass = `level-${course.level}`;
    const levelText = {
        'basic': 'Cơ bản',
        'intermediate': 'Trung cấp',
        'advanced': 'Nâng cao'
    }[course.level];

    return `
        <div class="course-card" data-course-id="${course.id}">
            <div class="course-icon">${course.icon}</div>
            <div class="course-name">${course.name}</div>
            <div class="course-duration">⏱️ ${course.duration}</div>
            <div class="course-level ${levelClass}">${levelText}</div>
        </div>
    `;
}

// Thêm sự kiện click cho course cards
function addCourseClickListeners() {
    const courseCards = document.querySelectorAll('.course-card');
    courseCards.forEach(card => {
        card.addEventListener('click', function() {
            // Bỏ chọn tất cả courses khác
            courseCards.forEach(c => c.classList.remove('selected'));
            
            // Chọn course này
            this.classList.add('selected');
            
            // Lưu course đã chọn
            const courseId = this.getAttribute('data-course-id');
            selectedCourse = [...suggestedCoursesData, ...allCoursesData].find(c => c.id === courseId);
            
            // Cập nhật UI
            updateSelectedInfo();
            updateSaveButton();
        });
    });
}

// Render danh sách nhân viên
function renderEmployees() {
    const container = document.getElementById('employeeCheckboxes');
    container.innerHTML = employeesData.map(emp => `
        <div class="checkbox-item">
            <input type="checkbox" id="emp_${emp.id}" value="${emp.id}" data-dept="${emp.department}">
            <label for="emp_${emp.id}">
                <div class="employee-info">
                    <span class="employee-name">${emp.name}</span>
                    <span class="employee-dept">${emp.departmentName}</span>
                </div>
            </label>
        </div>
    `).join('');

    // Thêm sự kiện cho checkboxes
    const checkboxes = container.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(cb => {
        cb.addEventListener('change', handleEmployeeSelection);
    });
}

// Xử lý khi chọn nhân viên
function handleEmployeeSelection() {
    const checkboxes = document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]:checked');
    selectedEmployees = Array.from(checkboxes).map(cb => {
        const empId = cb.value;
        return employeesData.find(e => e.id === empId);
    });
    
    updateSelectedInfo();
    updateSaveButton();
}

// Khởi tạo event listeners
function initEventListeners() {
    // Chọn phòng ban
    const departmentSelect = document.getElementById('departmentSelect');
    departmentSelect.addEventListener('change', function() {
        const selectedDept = this.value;
        
        if (selectedDept) {
            // Chọn tất cả nhân viên trong phòng ban
            const checkboxes = document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]');
            checkboxes.forEach(cb => {
                if (cb.getAttribute('data-dept') === selectedDept) {
                    cb.checked = true;
                } else {
                    cb.checked = false;
                }
            });
        } else {
            // Bỏ chọn tất cả
            document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => {
                cb.checked = false;
            });
        }
        
        handleEmployeeSelection();
    });

    // Nút Lưu
    const saveBtn = document.getElementById('saveBtn');
    saveBtn.addEventListener('click', handleSave);

    // Nút Hủy
    const cancelBtn = document.getElementById('cancelBtn');
    cancelBtn.addEventListener('click', handleCancel);

    // Đóng modal
    const closeModalBtn = document.getElementById('closeModalBtn');
    closeModalBtn.addEventListener('click', closeModal);
}

// Cập nhật thông tin đã chọn
function updateSelectedInfo() {
    const container = document.querySelector('.selected-info');
    const courseInfo = document.getElementById('selectedCourseInfo');
    const targetInfo = document.getElementById('selectedTargetInfo');
    
    if (selectedCourse || selectedEmployees.length > 0) {
        container.classList.add('show');
        
        if (selectedCourse) {
            courseInfo.innerHTML = `<strong>Khóa học đã chọn:</strong> ${selectedCourse.name}`;
        } else {
            courseInfo.innerHTML = '';
        }
        
        if (selectedEmployees.length > 0) {
            const empNames = selectedEmployees.map(e => e.name).join(', ');
            targetInfo.innerHTML = `<strong>Nhân viên đã chọn (${selectedEmployees.length}):</strong> ${empNames}`;
        } else {
            targetInfo.innerHTML = '';
        }
    } else {
        container.classList.remove('show');
    }
}

// Cập nhật trạng thái nút Lưu
function updateSaveButton() {
    const saveBtn = document.getElementById('saveBtn');
    if (selectedCourse && selectedEmployees.length > 0) {
        saveBtn.disabled = false;
    } else {
        saveBtn.disabled = true;
    }
}

// Xử lý khi nhấn Lưu
function handleSave() {
    if (!selectedCourse || selectedEmployees.length === 0) {
        alert('Vui lòng chọn khóa học và nhân viên!');
        return;
    }

    // Hiển thị loading
    showLoading();

    // Giả lập gọi API
    setTimeout(() => {
        // Ẩn loading
        hideLoading();

        // Hiển thị thông báo thành công
        showSuccessModal();

        // Reset form
        resetForm();
    }, 1500);
}

// Xử lý khi nhấn Hủy
function handleCancel() {
    if (confirm('Bạn có chắc muốn hủy? Tất cả dữ liệu đã chọn sẽ bị xóa.')) {
        resetForm();
    }
}

// Reset form
function resetForm() {
    // Bỏ chọn course
    document.querySelectorAll('.course-card').forEach(card => {
        card.classList.remove('selected');
    });
    selectedCourse = null;

    // Bỏ chọn department
    document.getElementById('departmentSelect').value = '';

    // Bỏ chọn employees
    document.querySelectorAll('#employeeCheckboxes input[type="checkbox"]').forEach(cb => {
        cb.checked = false;
    });
    selectedEmployees = [];

    // Cập nhật UI
    updateSelectedInfo();
    updateSaveButton();
}

// Hiển thị loading
function showLoading() {
    document.getElementById('loadingOverlay').classList.add('show');
}

// Ẩn loading
function hideLoading() {
    document.getElementById('loadingOverlay').classList.remove('show');
}

// Hiển thị modal thành công
function showSuccessModal() {
    const modal = document.getElementById('successModal');
    const message = document.getElementById('successMessage');
    
    message.innerHTML = `
        Đã gán khóa học <strong>"${selectedCourse.name}"</strong> 
        cho <strong>${selectedEmployees.length} nhân viên</strong>.<br><br>
        Thông báo đã được gửi đến các nhân viên được chọn.
    `;
    
    modal.classList.add('show');
}

// Đóng modal
function closeModal() {
    document.getElementById('successModal').classList.remove('show');
}

// Đóng modal khi click bên ngoài
window.onclick = function(event) {
    const modal = document.getElementById('successModal');
    if (event.target === modal) {
        modal.classList.remove('show');
    }
}