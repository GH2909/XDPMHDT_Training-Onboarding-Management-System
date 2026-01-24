let courseData = [];
let currentEditingId = null;

// Get
document.addEventListener("DOMContentLoaded", loadCourses);

async function loadCourses() {
    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        courseData = await res.json();
        renderCourseList();
        loadEditOptions();
    } catch (e) {
        alert(e.message);
    }
}

// render
function renderCourseList() {
    const container = document.getElementById("courseGrid");

    if (courseData.length === 0) {
        container.innerHTML = "<p>Chưa có khóa học nào</p>";
        return;
    }

    container.innerHTML = courseData.map(course => `
        <div class="course-card" onclick="viewCourseDetail(${course.id})">
            <div class="course-icon">${categoryIcons[course.category] || "📚"}</div>
            <div class="course-name">${course.courseName}</div>
            <div class="course-category">${course.category}</div>
            <div class="course-duration">⏱ ${course.duration} giờ</div>
        </div>
    `).join("");
}

// Chi tiết kh
function viewCourseDetail(id) {
    const course = courseData.find(c => c.id === id);
    if (!course) return;

    document.getElementById("detailBody").innerHTML = `
        <p><b>Tên:</b> ${course.courseName}</p>
        <p><b>Thời lượng:</b> ${course.duration}</p>
        <p><b>Mô tả:</b> ${course.description}</p>
        <p><b>Phân loại:</b> ${course.category}</p>
        <p><b>Điều kiện:</b> ${course.completionRule}</p>
    `;

    document.getElementById("detailModal").classList.add("show");
}

// Create
async function saveCourse() {
    const body = {
        courseName: document.getElementById("courseName").value.trim(),
        duration: document.getElementById("duration").value,
        description: document.getElementById("description").value.trim(),
        category: document.getElementById("category").value,
        completionRule: document.getElementById("completion").value.trim()
    };

    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(body)
        });

        if (!res.ok) throw new Error("Tạo khóa học thất bại");

        hideLoading();
        showMessage("Thành công", "Đã tạo khóa học");
        loadCourses();
        cancelForm();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}

function loadCourseForEdit() {
    const id = document.getElementById("editCourseSelect").value;
    if (!id) return;

    const course = courseData.find(c => c.id == id);
    if (!course) return;

    currentEditingId = id;

    document.getElementById("editCourseName").value = course.courseName;
    document.getElementById("editDuration").value = course.duration;
    document.getElementById("editDescription").value = course.description;
    document.getElementById("editCategory").value = course.category;
    document.getElementById("editCompletion").value = course.completionRule;

    document.getElementById("editFormFields").style.display = "block";
}

// Put
async function updateCourse() {
    if (!currentEditingId) return;

    const body = {
        courseName: document.getElementById("editCourseName").value,
        duration: document.getElementById("editDuration").value,
        description: document.getElementById("editDescription").value,
        category: document.getElementById("editCategory").value,
        completionRule: document.getElementById("editCompletion").value
    };

    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(body)
        });

        if (!res.ok) throw new Error("Cập nhật thất bại");

        hideLoading();
        showMessage("Thành công", "Đã cập nhật khóa học");
        loadCourses();
        cancelEdit();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}


// Delete
async function confirmDelete() {
    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (!res.ok) throw new Error("Xóa thất bại");

        hideLoading();
        showMessage("Thành công", "Đã xóa khóa học");
        loadCourses();
        cancelEdit();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}
 ==================
 const categoryIcons = {
    Onboarding: "🎯",
    Soft_Skills: "🗣️",
    Professional_Skills: "💻",
    Regulations: "⚠️"
};

async function loadCourses() {
    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "GET",
            headers: getAuthHeaders()
        });

        if (!res.ok) throw new Error("Không load được khóa học");

        const apiData = await res.json();

        // MAP API → UI DATA
        courseData = apiData.map(c => ({
            id: c.id,
            name: c.courseName,
            duration: c.duration + " giờ",
            description: c.description,
            category: c.category,
            modules: [], // backend chưa có → để rỗng
            completion: c.completionRule,
            icon: categoryIcons[c.category] || "📚"
        }));

        renderCourseList();
        loadEditOptions();

    } catch (e) {
        alert(e.message);
    }
}

function renderCourseList() {
    const container = document.getElementById('courseGrid');

    if (courseData.length === 0) {
        container.innerHTML =
            '<p style="text-align:center;color:#718096;padding:40px;">Chưa có khóa học nào</p>';
        return;
    }

    container.innerHTML = courseData.map(course => `
        <div class="course-card" onclick="viewCourseDetail('${course.id}')">
            <div class="course-icon">${course.icon}</div>
            <div class="course-name">${course.name}</div>
            <div class="course-category">${course.category.replace('_',' ')}</div>
            <div class="course-duration">⏱ ${course.duration}</div>
            <div class="course-modules">${course.modules.length} module</div>
        </div>
    `).join('');
}

function viewCourseDetail(id) {
    const course = courseData.find(c => c.id == id);
    if (!course) return;

    document.getElementById('detailBody').innerHTML = `
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
            <div class="detail-value">${course.category}</div>
        </div>
        <div class="detail-row">
            <div class="detail-label">✅ Điều kiện hoàn thành:</div>
            <div class="detail-value">${course.completion}</div>
        </div>
    `;

    document.getElementById("detailModal").classList.add("show");
}

// Create
async function saveCourse() {
    const body = {
        courseName: document.getElementById("courseName").value.trim(),
        duration: document.getElementById("duration").value,
        description: document.getElementById("description").value.trim(),
        category: document.getElementById("category").value,
        completionRule: document.getElementById("completion").value.trim()
    };

    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course`, {
            method: "POST",
            headers: getAuthHeaders(),
            body: JSON.stringify(body)
        });

        if (!res.ok) throw new Error("Tạo khóa học thất bại");

        hideLoading();
        showMessage("Thành công", "Đã tạo khóa học");
        loadCourses();
        cancelForm();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}

function loadCourseForEdit() {
    const id = document.getElementById("editCourseSelect").value;
    if (!id) return;

    const course = courseData.find(c => c.id == id);
    if (!course) return;

    currentEditingId = id;

    document.getElementById("editCourseName").value = course.courseName;
    document.getElementById("editDuration").value = course.duration;
    document.getElementById("editDescription").value = course.description;
    document.getElementById("editCategory").value = course.category;
    document.getElementById("editCompletion").value = course.completionRule;

    document.getElementById("editFormFields").style.display = "block";
}

// Put
async function updateCourse() {
    if (!currentEditingId) return;

    const body = {
        courseName: document.getElementById("editCourseName").value,
        duration: document.getElementById("editDuration").value,
        description: document.getElementById("editDescription").value,
        category: document.getElementById("editCategory").value,
        completionRule: document.getElementById("editCompletion").value
    };

    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "PUT",
            headers: getAuthHeaders(),
            body: JSON.stringify(body)
        });

        if (!res.ok) throw new Error("Cập nhật thất bại");

        hideLoading();
        showMessage("Thành công", "Đã cập nhật khóa học");
        loadCourses();
        cancelEdit();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}


// Delete
async function confirmDelete() {
    showLoading();

    try {
        const res = await fetch(`${API_BASE}/hr/course/${currentEditingId}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (!res.ok) throw new Error("Xóa thất bại");

        hideLoading();
        showMessage("Thành công", "Đã xóa khóa học");
        loadCourses();
        cancelEdit();

    } catch (e) {
        hideLoading();
        alert(e.message);
    }
}
