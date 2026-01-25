//Khóa học đề xuất
//  let suggestedCoursesData = [];

//Tất cả khóa học
let allCoursesData = [];

// Nhân viên
let employeesData = [];

const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};


// Biến lưu trạng thái
let selectedCourse = null;
let selectedEmployees = [];

// Khởi tạo khi trang load
document.addEventListener('DOMContentLoaded', function() {
    // renderSuggestedCourses();
    loadCourses();
    loadEmployees();
});

// // Render khóa học đề xuất
// function renderSuggestedCourses() {
//     const container = document.getElementById('suggestedCourses');
//     container.innerHTML = suggestedCoursesData.map(course => createCourseCard(course)).join('');
//     addCourseClickListeners();
// }

// Get course
async function loadCourses() {
    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        allCoursesData = await res.json();
        renderAllCourses();
    } catch (e) {
        alert(e.message);
    }
}

// Render tất cả khóa học
function renderAllCourses() {
    const container = document.getElementById('allCourses');
    if (allCoursesData.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #718096; padding: 40px;">Chưa có khóa học nào.</p>';
        return;
    }
    container.innerHTML = allCoursesData.map(course => {
    const icon = categoryIcons[course.category] || '📚';

    return `
        <div class="course-card" data-course-id="${course.id}">
            <div class="course-icon">${icon}</div>
            <div class="course-name">${course.courseName}</div>
            <div class="course-duration">⏱️ ${course.duration}</div>
            <div class="course-completion">${course.completionRule}</div>
        </div>
    `;
    }).join("");

    addCourseClickListeners();
}

// Click cho course cards
function addCourseClickListeners() {
    const courseCards = document.querySelectorAll('.course-card');
    courseCards.forEach(card => {
        card.addEventListener('click', function() {
            courseCards.forEach(c => c.classList.remove('selected'));
            
            this.classList.add('selected');
            
            const courseId = Number(this.getAttribute('data-course-id'));
            selectedCourse = [...suggestedCoursesData, ...allCoursesData].find(c => c.id === courseId);
            
            // Cập nhật UI
            updateSelectedInfo();
            updateSaveButton();
        });
    });
}

// Get employee
async function loadEmployees() {
    try {
        const res = await fetch(`${API_BASE}/employee/profile`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error("Không load được nhân viên");

        const resData = await res.json();
        employeesData.length = 0;
        employeesData.push(...resData.data);

        renderEmployees();
    } catch (e) {
        alert(e.message);
    }
}

// Render danh sách nhân viên
function renderEmployees() {
    const container = document.getElementById('employeeCheckboxes');
    container.innerHTML = employeesData.map(emp => `
        <div class="checkbox-item">
            <input type="checkbox" id="emp_${emp.id}" value="${emp.id}">
            <label for="emp_${emp.id}">
                <div class="employee-info">
                    <span class="employee-name">${emp.fullName}</span>
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
        const empId = Number(cb.value);
        return employeesData.find(e => e.id === empId);
    });
    
    updateSelectedInfo();
    updateSaveButton();
}

// Cập nhật thông tin đã chọn
function updateSelectedInfo() {
    const container = document.querySelector('.selected-info');
    const courseInfo = document.getElementById('selectedCourseInfo');
    const targetInfo = document.getElementById('selectedTargetInfo');
    
    if (selectedCourse || selectedEmployees.length > 0) {
        container.classList.add('show');
        
        if (selectedCourse) {
            courseInfo.innerHTML = `<strong>Khóa học đã chọn:</strong> ${selectedCourse.courseName}`;
        } else {
            courseInfo.innerHTML = '';
        }
        
        if (selectedEmployees.length > 0) {
            const empNames = selectedEmployees.map(e => e.fullName).join(', ');
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
        Đã gán khóa học <strong>"${selectedCourse.courseName}"</strong> 
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