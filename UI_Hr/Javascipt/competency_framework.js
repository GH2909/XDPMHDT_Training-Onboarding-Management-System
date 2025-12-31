// Dữ liệu mẫu - Khung năng lực theo vị trí
let competencyData = [
    {
        id: 'comp1',
        position: 'developer',
        positionName: 'Lập trình viên',
        icon: '💻',
        competencyName: 'Năng lực kỹ thuật cốt lõi',
        skills: ['JavaScript', 'React', 'Node.js', 'Database', 'Git']
    },
    {
        id: 'comp2',
        position: 'designer',
        positionName: 'Thiết kế đồ họa',
        icon: '🎨',
        competencyName: 'Năng lực thiết kế sáng tạo',
        skills: ['Photoshop', 'Illustrator', 'Figma', 'UI/UX Design', 'Typography']
    },
    {
        id: 'comp3',
        position: 'marketing',
        positionName: 'Chuyên viên Marketing',
        icon: '📱',
        competencyName: 'Năng lực marketing số',
        skills: ['SEO/SEM', 'Content Marketing', 'Social Media', 'Google Analytics', 'Email Marketing']
    },
    {
        id: 'comp4',
        position: 'sales',
        positionName: 'Nhân viên Kinh doanh',
        icon: '💼',
        competencyName: 'Năng lực bán hàng chuyên nghiệp',
        skills: ['Tư vấn khách hàng', 'Đàm phán', 'Quản lý quan hệ', 'Phân tích thị trường', 'Thuyết trình']
    }
];

// Biến lưu trạng thái
let currentEditingId = null;

// Khởi tạo khi trang load
document.addEventListener('DOMContentLoaded', function() {
    renderPositionList();
    loadEditOptions();
});

// Render danh sách khung năng lực theo vị trí
function renderPositionList() {
    const container = document.getElementById('positionGrid');
    
    if (competencyData.length === 0) {
        container.innerHTML = '<p style="text-align: center; color: #718096; padding: 40px;">Chưa có khung năng lực nào. Nhấn "Tạo mới" để bắt đầu.</p>';
        return;
    }
    
    container.innerHTML = competencyData.map(comp => `
        <div class="position-card" onclick="viewCompetencyDetail('${comp.id}')">
            <div class="position-icon">${comp.icon}</div>
            <div class="position-name">${comp.positionName}</div>
            <div class="position-competency">${comp.competencyName}</div>
            <div class="position-skills">${comp.skills.length} kỹ năng</div>
        </div>
    `).join('');
}

// Xem chi tiết khung năng lực
function viewCompetencyDetail(id) {
    const comp = competencyData.find(c => c.id === id);
    if (!comp) return;
    
    const modal = document.getElementById('successModal');
    const modalTitle = document.getElementById('modalTitle');
    const modalMessage = document.getElementById('modalMessage');
    
    modalTitle.textContent = comp.positionName;
    modalMessage.innerHTML = `
        <div style="text-align: left;">
            <p style="margin-bottom: 15px;"><strong>Năng lực:</strong> ${comp.competencyName}</p>
            <p style="margin-bottom: 10px;"><strong>Kỹ năng yêu cầu:</strong></p>
            <ul style="list-style: none; padding-left: 0;">
                ${comp.skills.map(skill => `<li style="padding: 5px 0;">✓ ${skill}</li>`).join('')}
            </ul>
        </div>
    `;
    
    modal.classList.add('show');
}

// Hiển thị form tạo mới
function showCreateForm() {
    document.getElementById('positionListCard').style.display = 'none';
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
    document.getElementById('positionSelect').value = '';
    document.getElementById('competencyName').value = '';
    
    const container = document.getElementById('skills-container');
    container.innerHTML = `
        <div class="skill-item">
            <input type="text" class="form-control skill-input" placeholder="VD: JavaScript, React">
            <button class="btn-delete" onclick="removeSkill(this)">❌ Xóa</button>
        </div>
    `;
}

// Thêm kỹ năng mới
function addSkill() {
    const container = document.getElementById('skills-container');
    
    const div = document.createElement('div');
    div.className = 'skill-item';
    div.innerHTML = `
        <input type="text" class="form-control skill-input" placeholder="Nhập kỹ năng">
        <button class="btn-delete" onclick="removeSkill(this)">❌ Xóa</button>
    `;
    
    container.appendChild(div);
}

// Xóa kỹ năng
function removeSkill(button) {
    const container = button.closest('#skills-container') || button.closest('#edit-skills-container');
    const items = container.querySelectorAll('.skill-item');
    
    // Không cho xóa nếu chỉ còn 1 item
    if (items.length <= 1) {
        showMessage('Cảnh báo', 'Phải có ít nhất 1 kỹ năng!', 'warning');
        return;
    }
    
    button.parentElement.remove();
}

// Lưu khung năng lực mới
function saveCompetency() {
    const position = document.getElementById('positionSelect').value;
    const competencyName = document.getElementById('competencyName').value.trim();
    const skillInputs = document.querySelectorAll('#skills-container .skill-input');
    
    // Validate
    if (!position) {
        showMessage('Lỗi', 'Vui lòng chọn vị trí công việc!', 'warning');
        return;
    }
    
    if (!competencyName) {
        showMessage('Lỗi', 'Vui lòng nhập tên năng lực!', 'warning');
        return;
    }
    
    const skills = Array.from(skillInputs)
        .map(input => input.value.trim())
        .filter(skill => skill !== '');
    
    if (skills.length === 0) {
        showMessage('Lỗi', 'Vui lòng nhập ít nhất 1 kỹ năng!', 'warning');
        return;
    }
    
    // Kiểm tra vị trí đã tồn tại chưa
    const existingComp = competencyData.find(c => c.position === position);
    if (existingComp) {
        showMessage('Cảnh báo', 'Vị trí này đã có khung năng lực. Vui lòng chọn "Chỉnh sửa" để cập nhật!', 'warning');
        return;
    }
    
    // Hiển thị loading
    showLoading();
    
    // Giả lập gọi API
    setTimeout(() => {
        // Lấy thông tin vị trí
        const positionSelect = document.getElementById('positionSelect');
        const positionName = positionSelect.options[positionSelect.selectedIndex].text;
        
        // Icon mapping
        const iconMap = {
            'developer': '💻',
            'designer': '🎨',
            'marketing': '📱',
            'hr': '👥',
            'sales': '💼',
            'accountant': '💰',
            'manager': '📊'
        };
        
        // Thêm vào data
        const newComp = {
            id: 'comp' + (competencyData.length + 1),
            position: position,
            positionName: positionName,
            icon: iconMap[position] || '📋',
            competencyName: competencyName,
            skills: skills
        };
        
        competencyData.push(newComp);
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', `Đã tạo khung năng lực cho vị trí "${positionName}"`, 'success');
        
        // Cập nhật UI
        renderPositionList();
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
    document.getElementById('positionListCard').style.display = 'block';
    resetCreateForm();
}

// Load options cho dropdown chỉnh sửa
function loadEditOptions() {
    const select = document.getElementById('editCompetencySelect');
    select.innerHTML = '<option value="">-- Chọn khung năng lực --</option>';
    
    competencyData.forEach(comp => {
        const option = document.createElement('option');
        option.value = comp.id;
        option.textContent = `${comp.positionName} - ${comp.competencyName}`;
        select.appendChild(option);
    });
}

// Load dữ liệu để chỉnh sửa
function loadCompetencyForEdit() {
    const select = document.getElementById('editCompetencySelect');
    const selectedId = select.value;
    const editFields = document.getElementById('editFormFields');
    
    if (!selectedId) {
        editFields.style.display = 'none';
        return;
    }
    
    const comp = competencyData.find(c => c.id === selectedId);
    if (!comp) return;
    
    currentEditingId = selectedId;
    
    // Điền dữ liệu
    document.getElementById('editPosition').value = comp.positionName;
    document.getElementById('editCompetencyName').value = comp.competencyName;
    
    // Render skills
    const container = document.getElementById('edit-skills-container');
    container.innerHTML = comp.skills.map(skill => `
        <div class="skill-item">
            <input type="text" class="form-control skill-input" value="${skill}">
            <button class="btn-delete" onclick="removeSkill(this)">❌ Xóa</button>
        </div>
    `).join('');
    
    editFields.style.display = 'block';
}

// Thêm kỹ năng trong form edit
function addEditSkill() {
    const container = document.getElementById('edit-skills-container');
    
    const div = document.createElement('div');
    div.className = 'skill-item';
    div.innerHTML = `
        <input type="text" class="form-control skill-input" placeholder="Nhập kỹ năng">
        <button class="btn-delete" onclick="removeSkill(this)">❌ Xóa</button>
    `;
    
    container.appendChild(div);
}

// Cập nhật khung năng lực
function updateCompetency() {
    if (!currentEditingId) return;
    
    const competencyName = document.getElementById('editCompetencyName').value.trim();
    const skillInputs = document.querySelectorAll('#edit-skills-container .skill-input');
    
    // Validate
    if (!competencyName) {
        showMessage('Lỗi', 'Vui lòng nhập tên năng lực!', 'warning');
        return;
    }
    
    const skills = Array.from(skillInputs)
        .map(input => input.value.trim())
        .filter(skill => skill !== '');
    
    if (skills.length === 0) {
        showMessage('Lỗi', 'Vui lòng nhập ít nhất 1 kỹ năng!', 'warning');
        return;
    }
    
    // Hiển thị loading
    showLoading();
    
    // Giả lập gọi API
    setTimeout(() => {
        // Cập nhật data
        const comp = competencyData.find(c => c.id === currentEditingId);
        if (comp) {
            comp.competencyName = competencyName;
            comp.skills = skills;
        }
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', 'Đã cập nhật khung năng lực', 'success');
        
        // Cập nhật UI
        renderPositionList();
        loadEditOptions();
        
        // Quay lại danh sách
        setTimeout(() => {
            closeModal();
            cancelEdit();
        }, 1500);
    }, 1000);
}

// Xóa khung năng lực
function deleteCompetency() {
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
        competencyData = competencyData.filter(c => c.id !== currentEditingId);
        
        // Ẩn loading
        hideLoading();
        
        // Hiển thị thông báo thành công
        showMessage('Thành công!', 'Đã xóa khung năng lực', 'success');
        
        // Cập nhật UI
        renderPositionList();
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
    document.getElementById('positionListCard').style.display = 'block';
    document.getElementById('editCompetencySelect').value = '';
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
    
    if (event.target === successModal) {
        successModal.classList.remove('show');
    }
    
    if (event.target === confirmModal) {
        confirmModal.classList.remove('show');
    }
}