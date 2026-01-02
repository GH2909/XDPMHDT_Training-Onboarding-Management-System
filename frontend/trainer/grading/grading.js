// ========== MODAL FUNCTIONS ==========
function openGradingModal(btn) {
    const modal = document.getElementById('gradingModal');
    const overlay = document.getElementById('modalOverlay');
    const card = btn.closest('.grading-card');

    const studentName = card.querySelector('.student-name').textContent;
    const assignmentName = card.querySelector('.student-meta').textContent;

    document.getElementById('modal-student-name').textContent = studentName;
    document.getElementById('modal-assignment-name').textContent = assignmentName;

    // Reset form
    document.getElementById('score-input').value = '';
    document.getElementById('feedback-textarea').value = '';
    document.querySelectorAll('.rubric-item input').forEach(checkbox => {
        checkbox.checked = false;
    });

    modal.classList.add('active');
    overlay.classList.add('active');
}

function closeGradingModal() {
    const modal = document.getElementById('gradingModal');
    const overlay = document.getElementById('modalOverlay');

    modal.classList.remove('active');
    overlay.classList.remove('active');
}

// Close modal when pressing ESC
document.addEventListener('keydown', function (e) {
    if (e.key === 'Escape') {
        closeGradingModal();
    }
});

// ========== GRADING SUBMISSION ==========
function submitGrading() {
    const score = document.getElementById('score-input').value;
    const feedback = document.getElementById('feedback-textarea').value;
    const rubrics = Array.from(document.querySelectorAll('.rubric-item input:checked')).length;

    // Validation
    if (!score) {
        alert('Vui lòng nhập điểm số!');
        return;
    }

    if (!feedback.trim()) {
        alert('Vui lòng viết đánh giá chi tiết!');
        return;
    }

    // Prepare data
    const gradingData = {
        score: parseFloat(score),
        feedback: feedback,
        rubrics: rubrics,
        timestamp: new Date().toLocaleString('vi-VN')
    };

    console.log('Grading submitted:', gradingData);

    // Show success message
    alert(`✓ Chấm điểm thành công!\nĐiểm: ${score}/10\nThời gian: ${gradingData.timestamp}`);

    // Close modal
    closeGradingModal();

    // Update card status (in a real app, this would be done on the server)
    updateCardStatus();
}

// ========== UPDATE CARD STATUS ==========
function updateCardStatus() {
    // This would typically be updated by the server response
    // For demo purposes, we'll just show it in console
    console.log('Card status updated to graded');
}

// ========== FILTER FUNCTIONALITY ==========
const courseFilter = document.getElementById('course-filter');
const statusFilter = document.getElementById('status-filter');
const sortFilter = document.getElementById('sort-filter');

if (courseFilter) courseFilter.addEventListener('change', filterCards);
if (statusFilter) statusFilter.addEventListener('change', filterCards);
if (sortFilter) sortFilter.addEventListener('change', sortCards);

function filterCards() {
    const course = courseFilter.value;
    const status = statusFilter.value;
    const cards = document.querySelectorAll('.grading-card');
    let visibleCount = 0;

    cards.forEach(card => {
        let show = true;

        // Check course filter
        if (course && !card.textContent.includes(getCourseText(course))) {
            show = false;
        }

        // Check status filter
        if (status) {
            if (status === 'pending' && !card.classList.contains('pending')) show = false;
            if (status === 'graded' && !card.classList.contains('graded')) show = false;
            if (status === 'returned' && !card.classList.contains('returned')) show = false;
        }

        card.style.display = show ? 'block' : 'none';
        if (show) visibleCount++;
    });

    // Show empty state if no results
    if (visibleCount === 0) {
        showEmptyState();
    }
}

function getCourseText(courseValue) {
    const courseMap = {
        'web': 'Lập Trình Web',
        'mobile': 'Lập Trình Mobile',
        'ui-ux': 'Thiết Kế UI/UX'
    };
    return courseMap[courseValue] || '';
}

function sortCards() {
    const sortValue = sortFilter.value;
    const list = document.querySelector('.grading-list');
    const cards = Array.from(document.querySelectorAll('.grading-card'));

    cards.sort((a, b) => {
        if (sortValue === 'newest') {
            // Sort by newest (reverse order - would need data attribute in real app)
            return 0;
        } else if (sortValue === 'oldest') {
            return 0;
        } else if (sortValue === 'name') {
            const nameA = a.querySelector('.student-name').textContent;
            const nameB = b.querySelector('.student-name').textContent;
            return nameA.localeCompare(nameB);
        }
    });

    // Re-append sorted cards
    cards.forEach(card => list.appendChild(card));
}

// ========== SCORE INPUT VALIDATION ==========
const scoreInput = document.getElementById('score-input');
if (scoreInput) {
    scoreInput.addEventListener('input', function () {
        if (this.value > 10) this.value = 10;
        if (this.value < 0) this.value = 0;
    });
}

// ========== RUBRIC CALCULATOR ==========
const rubricCheckboxes = document.querySelectorAll('.rubric-item input[type="checkbox"]');
rubricCheckboxes.forEach(checkbox => {
    checkbox.addEventListener('change', calculateRubricScore);
});

function calculateRubricScore() {
    const checkedBoxes = document.querySelectorAll('.rubric-item input[type="checkbox"]:checked');
    const points = Array.from(checkedBoxes).reduce((total, checkbox) => {
        const pointsText = checkbox.closest('.rubric-item').querySelector('.points').textContent;
        const points = parseInt(pointsText.match(/\d+/)[0]);
        return total + points;
    }, 0);

    if (points > 0) {
        console.log(`Rubric score: ${points}/10`);
    }
}

// ========== EMPTY STATE ==========
function showEmptyState() {
    const list = document.querySelector('.grading-list');
    let emptyState = document.querySelector('.empty-state');

    if (!emptyState) {
        emptyState = document.createElement('div');
        emptyState.className = 'empty-state';
        emptyState.innerHTML = `
            <div style="text-align: center; padding: 3rem 1rem; color: #9ca3af;">
                <i class="fas fa-inbox" style="font-size: 3rem; margin-bottom: 1rem; color: #d1d5db;"></i>
                <p style="font-size: 1rem; color: #1a1625; margin-bottom: 0.5rem;">Không có bài tập</p>
                <small>Hãy thử thay đổi bộ lọc</small>
            </div>
        `;
        list.appendChild(emptyState);
    }
}

// ========== SEARCH FUNCTIONALITY ==========
const searchInput = document.querySelector('.search-box input');
if (searchInput) {
    searchInput.addEventListener('input', function (e) {
        const searchTerm = e.target.value.toLowerCase();
        const cards = document.querySelectorAll('.grading-card');
        let visibleCount = 0;

        cards.forEach(card => {
            const studentName = card.querySelector('.student-name').textContent.toLowerCase();
            const assignmentName = card.querySelector('.student-meta').textContent.toLowerCase();

            if (studentName.includes(searchTerm) || assignmentName.includes(searchTerm)) {
                card.style.display = 'block';
                visibleCount++;
            } else {
                card.style.display = 'none';
            }
        });

        if (visibleCount === 0) {
            showEmptyState();
        }
    });
}

// ========== INITIALIZE ==========
document.addEventListener('DOMContentLoaded', function () {
    console.log('Grading page loaded');
});