// Dữ liệu mẫu - Danh sách khóa học
let courseData = [
    {
        id: 'course1',
        name: 'Khóa học JavaScript cơ bản',
        duration: '40 giờ',
        description: 'Học JavaScript từ cơ bản đến nâng cao, bao gồm ES6+, DOM, Event handling và các khái niệm lập trình hiện đại.',
        category: 'Professional_Skills',
        modules: ['Video', 'PDF', 'Slide'],
        completion: 'Hoàn thành 100% bài học và đạt 80% bài kiểm tra',
        icon: '💻'
    },
    {
        id: 'course2',
        name: 'Onboarding - Chào mừng nhân viên mới',
        duration: '8 giờ',
        description: 'Hướng dẫn toàn diện về văn hóa công ty, quy trình làm việc và các công cụ cần thiết cho nhân viên mới.',
        category: 'Onboarding',
        modules: ['Video', 'PDF'],
        completion: 'Hoàn thành tất cả module và khảo sát đánh giá',
        icon: '🎯'
    },
    {
        id: 'course3',
        name: 'Kỹ năng giao tiếp hiệu quả',
        duration: '20 giờ',
        description: 'Phát triển kỹ năng giao tiếp, thuyết trình và làm việc nhóm trong môi trường doanh nghiệp.',
        category: 'Soft_Skills',
        modules: ['Video', 'Slide', 'SCORM'],
        completion: 'Tham gia đầy đủ các buổi workshop và hoàn thành bài tập thực hành',
        icon: '🗣️'
    },
    {
        id: 'course4',
        name: 'Quy định an toàn lao động',
        duration: '12 giờ',
        description: 'Các quy định về an toàn, sức khỏe và môi trường làm việc theo tiêu chuẩn quốc tế.',
        category: 'Regulations',
        modules: ['PDF', 'Video'],
        completion: 'Đạt 100% bài kiểm tra cuối khóa',
        icon: '⚠️'
    }
];

// Biến lưu trạng thái
let currentEditingId = null;

// Icon mapping cho category
const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};

// Khởi tạo khi trang load
document.addEventListener('DOMContentLoaded', function() {
    renderCourseList();
    loadEditOptions();
});

// Clear input function
function clearInput(inputId) {
    document.getElementById(inputId).value = '';
}

// Render danh sách khóa học
function renderCourseList() {
    const container = document.getElementById('courseGrid');
    
    if (courseData.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #718096; padding: 40px;">Chưa có khóa học nào. Nhấn "Tạo mới" để bắt đầu.</p>';
        return;
    }
    
    container.innerHTML = courseData.map(course => `
        <div class="course-card" onclick="viewCourseDetail('${course.id}')">
            <div class="course-icon">${course.icon}</div>
            <div class="course-name">${course.name}</div>
            <div class="course-category">${course.category.replace('_', ' ')}</div>
            <div class="course-duration">⏱️ ${course.duration}</div>
            <div class="course-modules">${course.modules.length} module</div>
        </div>
    `).join('');
}

// Xem chi tiết khóa học
function viewCourseDetail(id) {
    const course = courseData.find(c => c.id === id);
    if (!course) return;
    
    const modal = document.getElementById('detailModal');
    const detailBody = document.getElementById('detailBody');
    
    detailBody.innerHTML = `
        <div class="detail-row">
            <div class="detail-label">📚 Tên khóa học:</div>
            <div class="detail-value">${course.name}</div>
        </div>
        <div class="detail-row">
            <div class="detail-label">⏱️ Thời lượng:</div>
            <div class="detail-value">${course.duration}</div>
        </div>
        <div class="detail-row">
            <div class="detail-label">📝 Mô tả:</div>
            <div class="detail-value">${course.description}</div>
        </div>
        <div class="detail-row">
            <div class="detail-label">🏷️ Phân loại:</div>
            <div class="detail-value">${course.category.replace('_', ' ')}</div>
        </div>
        <div class="detail-row">
            <div class="detail-label">📦 Module:</div>
            <div class="module-badges">
                ${course.modules.map(m => `<span class="module-badge">${m}</span>`).join('')}
            </div>
        </div>
        <div class="detail-row">
            <div class="detail-label">✅ Điều kiện hoàn thành:</div>
            <div class="detail-value">${course.completion}</div>
        </div>
    `;
    
    modal.classList.add('show');
}

// Đóng detail modal
function closeDetailModal() {
    document.getElementById('detailModal').classList.remove('show');
}

// Hiển thị form tạo mới
function showCreateForm() {
    document.getElementById('courseListCard').style.display = 'none';
    document.getElementById('formCard').style.display = 'block';
    switchTab('create');
    resetCreateForm();
}

// Chuyển đổi tab
function switchTab(tabName) {
    const createTab = document.getElementById('createTab');
    const editTab = document.getElementById('editTab');
    const createContent = document.getElementById('createFormContent');
    const editContent = document.getElementById('editFormContent');
    
    if (tabName === 'create') {
        createTab.classList.add('active');
        createTab.classList.remove('inactive');
        editTab.classList.remove('active');
        editTab.classList.add('inactive');
        createContent.style.display = 'block';
        editContent.style.display = 'none';
        resetCreateForm();
    } else {
        editTab.classList.add('active');
        editTab.classList.remove('inactive');
        createTab.classList.remove('active');
        createTab.classList.add('inactive');
        editContent.style.display = 'block';
        createContent.style.display = 'none';
        loadEditOptions();
    }
}

// Reset form tạo mới
function resetCreateForm() {
    document.getElementById('courseName').value = '';
    document.getElementById('duration').value = '';
    document.getElementById('description').value = '';
    document.getElementById('category').value = '';
    document.getElementById('completion').value = '';
    
    // Uncheck all module checkboxes
    const checkboxes = document.querySelectorAll('.module-options input[type="checkbox"]');
    checkboxes.forEach(cb => cb.checked = false);
}

// Get selected modules
function getSelectedModules() {
    const checkboxes = document.querySelectorAll('.module-options input[type="checkbox"]:checked');
    return Array.from(checkboxes).map(cb => cb.value);
}

// Lưu khóa học mới
function saveCourse() {
    const name = document.getElementById('courseName').value.trim();
    const duration = document.getElementById('duration').value.trim();
    const description = document.getElementById('description').value.trim();
    const category = document.getElementById('category').value;
    const modules = getSelectedModules();
    const completion = document.getElementById('completion').value.trim();
    
    // Validate
    if (!name) {
        showMessage('Lỗi', 'Vui lòng nhập tên khóa học!', 'warning');
        return;
    }
    
    if (!duration) {
        showMessage('Lỗi', 'Vui lòng nhập thời lượng!', 'warning');
        return;
    }
    
    if (!description) {
        showMessage('Lỗi', 'Vui lòng nhập mô tả!', 'warning');
        return;
    }
    
    if (!category) {
        showMessage('Lỗi', 'Vui lòng chọn phân loại!', 'warning');
        return;
    }
    
    if (modules.length === 0) {
        showMessage('Lỗi', 'Vui lòng chọn ít nhất 1 module!', 'warning');
        return;
    }
    
    if (!completion) {
        showMessage('Lỗi', 'Vui lòng nhập điều kiện hoàn thành!', 'warning');
        return;
    }
    
    // Hiển thị loading
    showLoading();
    
    // Giả lập gọi API
    setTimeout(() => {
        // Thêm vào data
        const newCourse = {
            id: 'course' + (courseData.length + 1),
            name: name,
            duration: duration,
            description: description,
            category: category,
            modules: modules,
            completion: completion,
            icon: categoryIcons[category] || '📚'
        };
        
        courseData.push(newCourse);
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', `Đã tạo khóa học "${name}"`, 'success');
        
        // Cập nhật UI
        renderCourseList();
        loadEditOptions();
        
        // Quay lại danh sách
        setTimeout(() => {
            closeModal();
            cancelForm();
        }, 1500);
    }, 1000);
}

// Hủy form tạo mới
function cancelForm() {
    document.getElementById('formCard').style.display = 'none';
    document.getElementById('courseListCard').style.display = 'block';
    resetCreateForm();
}

// Load options cho dropdown chỉnh sửa
function loadEditOptions() {
    const select = document.getElementById('editCourseSelect');
    select.innerHTML = '<option value="">-- Chọn khóa học --</option>';
    
    courseData.forEach(course => {
        const option = document.createElement('option');
        option.value = course.id;
        option.textContent = course.name;
        select.appendChild(option);
    });
}

// Load dữ liệu để chỉnh sửa
function loadCourseForEdit() {
    const select = document.getElementById('editCourseSelect');
    const selectedId = select.value;
    const editFields = document.getElementById('editFormFields');
    
    if (!selectedId) {
        editFields.style.display = 'none';
        return;
    }
    
    const course = courseData.find(c => c.id === selectedId);
    if (!course) return;
    
    currentEditingId = selectedId;
    
    // Điền dữ liệu
    document.getElementById('editCourseName').value = course.name;
    document.getElementById('editDuration').value = course.duration;
    document.getElementById('editDescription').value = course.description;
    document.getElementById('editCategory').value = course.category;
    document.getElementById('editCompletion').value = course.completion;
    
    // Check modules
    const checkboxes = document.querySelectorAll('#editModuleOptions input[type="checkbox"]');
    checkboxes.forEach(cb => {
        cb.checked = course.modules.includes(cb.value);
    });
    
    editFields.style.display = 'block';
}

// Get selected modules in edit form
function getSelectedEditModules() {
    const checkboxes = document.querySelectorAll('#editModuleOptions input[type="checkbox"]:checked');
    return Array.from(checkboxes).map(cb => cb.value);
}

// Cập nhật khóa học
function updateCourse() {
    if (!currentEditingId) return;
    
    const name = document.getElementById('editCourseName').value.trim();
    const duration = document.getElementById('editDuration').value.trim();
    const description = document.getElementById('editDescription').value.trim();
    const category = document.getElementById('editCategory').value;
    const modules = getSelectedEditModules();
    const completion = document.getElementById('editCompletion').value.trim();
    
    // Validate
    if (!name) {
        showMessage('Lỗi', 'Vui lòng nhập tên khóa học!', 'warning');
        return;
    }
    
    if (!duration) {
        showMessage('Lỗi', 'Vui lòng nhập thời lượng!', 'warning');
        return;
    }
    
    if (!description) {
        showMessage('Lỗi', 'Vui lòng nhập mô tả!', 'warning');
        return;
    }
    
    if (!category) {
        showMessage('Lỗi', 'Vui lòng chọn phân loại!', 'warning');
        return;
    }
    
    if (modules.length === 0) {
        showMessage('Lỗi', 'Vui lòng chọn ít nhất 1 module!', 'warning');
        return;
    }
    
    if (!completion) {
        showMessage('Lỗi', 'Vui lòng nhập điều kiện hoàn thành!', 'warning');
        return;
    }
    
    // Hiển thị loading
    showLoading();
    
    // Giả lập gọi API
    setTimeout(() => {
        // Cập nhật data
        const course = courseData.find(c => c.id === currentEditingId);
        if (course) {
            course.name = name;
            course.duration = duration;
            course.description = description;
            course.category = category;
            course.modules = modules;
            course.completion = completion;
            course.icon = categoryIcons[category] || '📚';
        }
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', 'Đã cập nhật khóa học', 'success');
        
        // Cập nhật UI
        renderCourseList();
        loadEditOptions();
        
        // Quay lại danh sách
        setTimeout(() => {
            closeModal();
            cancelEdit();
        }, 1500);
    }, 1000);
}

// Xóa khóa học
function deleteCourse() {
    if (!currentEditingId) return;
    
    const modal = document.getElementById('confirmModal');
    modal.classList.add('show');
}

// Xác nhận xóa
function confirmDelete() {
    closeConfirmModal();
    showLoading();
    
    // Giả lập gọi API
    setTimeout(() => {
        // Xóa khỏi data
        courseData = courseData.filter(c => c.id !== currentEditingId);
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', 'Đã xóa khóa học', 'success');
        
        // Cập nhật UI
        renderCourseList();
        loadEditOptions();
        
        // Quay lại danh sách
        setTimeout(() => {
            closeModal();
            cancelEdit();
        }, 1500);
    }, 1000);
}

// Hủy chỉnh sửa
function cancelEdit() {
    document.getElementById('formCard').style.display = 'none';
    document.getElementById('courseListCard').style.display = 'block';
    document.getElementById('editCourseSelect').value = '';
    document.getElementById('editFormFields').style.display = 'none';
    currentEditingId = null;
}

// Hiển thị loading
function showLoading() {
    document.getElementById('loadingOverlay').classList.add('show');
}

// Ẩn loading
function hideLoading() {
    document.getElementById('loadingOverlay').classList.remove('show');
}

// Hiển thị thông báo
function showMessage(title, message, type = 'success') {
    const modal = document.getElementById('successModal');
    const modalTitle = document.getElementById('modalTitle');
    const modalMessage = document.getElementById('modalMessage');
    const modalHeader = modal.querySelector('.modal-header');
    
    modalTitle.textContent = title;
    modalMessage.textContent = message;
    
    // Đổi class cho header
    modalHeader.className = 'modal-header';
    if (type === 'warning') {
        modalHeader.classList.add('warning');
        modalHeader.querySelector('.modal-icon').textContent = '⚠️';
    } else {
        modalHeader.classList.add('success');
        modalHeader.querySelector('.modal-icon').textContent = '✅';
    }
    
    modal.classList.add('show');
}

// Đóng modal
function closeModal() {
    document.getElementById('successModal').classList.remove('show');
}

// Đóng confirm modal
function closeConfirmModal() {
    document.getElementById('confirmModal').classList.remove('show');
}

// Đóng modal khi click bên ngoài
window.onclick = function(event) {
    const successModal = document.getElementById('successModal');
    const confirmModal = document.getElementById('confirmModal');
    const detailModal = document.getElementById('detailModal');
    
    if (event.target === successModal) {
        successModal.classList.remove('show');
    }
    
    if (event.target === confirmModal) {
        confirmModal.classList.remove('show');
    }
    
    if (event.target === detailModal) {
        detailModal.classList.remove('show');
    }
}