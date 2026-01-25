// Ds khóa học
let courseData = [];

let currentEditingId = null;

const categoryIcons = {
    'Onboarding': '🎯',
    'Soft_Skills': '🗣️',
    'Professional_Skills': '💻',
    'Regulations': '⚠️'
};

// Khởi tạo khi trang load
document.addEventListener('DOMContentLoaded', function() {
    loadCourses();
});

function clearInput(inputId) {
    document.getElementById(inputId).value = '';
}

// Get
async function loadCourses() {
    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "GET",
            headers: getAuthHeader()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        courseData = await res.json();
        renderCourseList();
        loadEditOptions();
    } catch (e) {
        alert(e.message);
    }
}


// Render danh sách khóa học
function renderCourseList() {
    const container = document.getElementById('courseGrid');
    
    if (courseData.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #718096; padding: 40px;">Chưa có khóa học nào. Nhấn "Tạo mới" để bắt đầu.</p>';
        return;
    }
    
    container.innerHTML = courseData.map(course => {
    const icon = categoryIcons[course.category] || '📚';

    return `
        <div class="course-card" onclick="viewCourseDetail('${course.id}')">
            <div class="course-icon">${icon}</div>
            <div class="course-name">${course.courseName}</div>
            <div class="course-category">${course.category.replace('_', ' ')}</div>
            <div class="course-duration">⏱️ ${course.duration}</div>
            <div class="course-modules">${course.modules?.length || 0} module</div>
        </div>
    `;
}).join('');
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
            <div class="detail-value">${course.courseName}</div>
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
        // <div class="detail-row">
        //     <div class="detail-label">📦 Module:</div>
        //     <div class="module-badges">
        //         ${course.modules.map(m => `<span class="module-badge">${m}</span>`).join('')}
        //     </div>
        // </div>
        <div class="detail-row">
            <div class="detail-label">✅ Điều kiện hoàn thành:</div>
            <div class="detail-value">${course.completionRule}</div>
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
async function saveCourse() {
    const name = document.getElementById('courseName').value.trim();
    const duration = document.getElementById('duration').value.trim();
    const description = document.getElementById('description').value.trim();
    const category = document.getElementById('category').value;
    // const modules = getSelectedModules();
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
    
    // if (modules.length === 0) {
    //     showMessage('Lỗi', 'Vui lòng chọn ít nhất 1 module!', 'warning');
    //     return;
    // }
    
    if (!completion) {
        showMessage('Lỗi', 'Vui lòng nhập điều kiện hoàn thành!', 'warning');
        return;
    }
    const body = {
        courseName: name,
        duration: Number(duration),
        description: description,
        category: category,
        completionRule: completion
    };

    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "POST",
            headers: getAuthHeader(),
            body: JSON.stringify(body)
        });

        if (!res.ok) {
            const errText = await res.text();
            throw new Error(errText || "Tạo khóa học thất bại");
        }

        hideLoading();
        showMessage("Thành công!", `Đã tạo khóa học "${name}"`, "success");

        // reload lại danh sách từ server
        await loadCourses();

        // quay lại danh sách
        setTimeout(() => {
            closeModal();
            cancelForm();
        }, 1200);

    } catch (e) {
        hideLoading();
        showMessage("Lỗi", e.message, "warning");
    }
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
        option.textContent = course.courseName;
        select.appendChild(option);
    });
}

// Load dữ liệu để chỉnh sửa
function loadCourseForEdit() {
    const select = document.getElementById('editCourseSelect');
    const selectedId = Number(select.value);
     editFields = document.getElementById('editFormFields');
    
    if (!selectedId) {
        editFields.style.display = 'none';
        return;
    }
    editFields.style.display = 'block';
    
    const course = courseData.find(c => c.id === selectedId);
    if (!course) return;
    
    currentEditingId = selectedId;
    
    // Điền dữ liệu
    document.getElementById('editCourseName').value = course.courseName;
    document.getElementById('editDuration').value = course.duration;
    document.getElementById('editDescription').value = course.description;
    document.getElementById('editCategory').value = course.category;
    document.getElementById('editCompletion').value = course.completionRule;
    
    // Check modules
    const checkboxes = document.querySelectorAll('#editModuleOptions input[type="checkbox"]');
    checkboxes.forEach(cb => {
        cb.checked = course.modules.includes(cb.value);
    });
    
}

// Get selected modules in edit form
function getSelectedEditModules() {
    const checkboxes = document.querySelectorAll('#editModuleOptions input[type="checkbox"]:checked');
    return Array.from(checkboxes).map(cb => cb.value);
}

// Update
async function updateCourse() {
    if (!currentEditingId) return;
    const name = document.getElementById('editCourseName').value.trim();
    const duration = document.getElementById('editDuration').value.trim();
    const description = document.getElementById('editDescription').value.trim();
    const category = document.getElementById('editCategory').value;
    // const modules = getSelectedModules();
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
    
    // if (modules.length === 0) {
    //     showMessage('Lỗi', 'Vui lòng chọn ít nhất 1 module!', 'warning');
    //     return;
    // }
    
    if (!completion) {
        showMessage('Lỗi', 'Vui lòng nhập điều kiện hoàn thành!', 'warning');
        return;
    }
    const body = {
        courseName: name,
        duration: Number(duration),
        description: description,
        category: category,
        completionRule: completion
    };
        showLoading();
    
    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "PUT",
            headers: getAuthHeader(),
            body: JSON.stringify(body)
        });
        console.log(getAuthHeader());

        if (!res.ok) {
            const errText = await res.text();
            throw new Error(errText || "Cập nhật thất bại");
        }

        hideLoading();
        showMessage("Thành công!", "Đã cập nhật khóa học", "success");

        await loadCourses();

        setTimeout(() => {
            closeModal();
            cancelEdit();
        }, 1200);

    } catch (e) {
        hideLoading();
        showMessage("Lỗi", e.message, "warning");
    }
}

// Xóa khóa học
function deleteCourse() {
    if (!currentEditingId) return;
    
    const modal = document.getElementById('confirmModal');
    modal.classList.add('show');
}

// Xác nhận xóa
async function confirmDelete() {
    closeConfirmModal();
    showLoading();
    
    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "DELETE",
            headers: getAuthHeader()
        });

        if (!res.ok) {
            const errText = await res.text();
            throw new Error(errText || "Xóa thất bại");
        }

        hideLoading();
        showMessage("Thành công!", "Đã xóa khóa học", "success");

        await loadCourses(); // reload từ server

        setTimeout(() => {
            closeModal();
            cancelEdit();
        }, 1200);

    } catch (e) {
        hideLoading();
        showMessage("Lỗi", e.message, "warning");
    }
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