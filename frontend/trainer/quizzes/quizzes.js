// ========== ADD ANSWER ==========
function attachAddAnswerHandler(btn) {
    btn.addEventListener('click', function (e) {
        e.preventDefault();
        const answersContainer = this.parentElement;
        const answerItems = answersContainer.querySelectorAll('.answer-item');
        const lastItem = answerItems[answerItems.length - 1];
        const radioName = lastItem.querySelector('input[type="radio"]').name;
        const nextLetter = String.fromCharCode(97 + answerItems.length); // a, b, c, d...

        const newAnswer = document.createElement('div');
        newAnswer.className = 'answer-item';
        newAnswer.innerHTML = `
            <input type="radio" name="${radioName}" value="${nextLetter}">
            <input type="text" class="form-input" placeholder="Đáp án ${nextLetter.toUpperCase()}" style="margin: 0 10px; flex: 1;">
            <input type="number" class="form-input" placeholder="Điểm" style="width: 80px;">
            <button class="btn-delete-answer" type="button" title="Xóa">
                <i class="fas fa-times"></i>
            </button>
        `;

        answersContainer.insertBefore(newAnswer, this);

        // Attach delete handler to new answer
        newAnswer.querySelector('.btn-delete-answer').addEventListener('click', function (e) {
            e.preventDefault();
            const items = answersContainer.querySelectorAll('.answer-item');
            if (items.length > 2) {
                this.closest('.answer-item').remove();
            } else {
                alert('Phải giữ lại ít nhất 2 đáp án!');
            }
        });
    });
}// ========== TAB SWITCHING ==========
const tabBtns = document.querySelectorAll('.tab-btn');
const tabContents = document.querySelectorAll('.tab-content');

tabBtns.forEach(btn => {
    btn.addEventListener('click', switchTab);
});

function switchTab(e) {
    e.preventDefault();
    const tabBtn = e.currentTarget;
    const tabName = tabBtn.getAttribute('data-tab');

    // Remove active class from all tabs
    tabBtns.forEach(btn => btn.classList.remove('active'));
    tabContents.forEach(content => content.classList.remove('active'));

    // Add active class to clicked tab
    tabBtn.classList.add('active');
    document.getElementById(tabName).classList.add('active');
}

// ========== ADD QUESTION ==========
const addQuestionBtn = document.querySelector('.btn-add-question');
let questionCount = 1;

if (addQuestionBtn) {
    addQuestionBtn.addEventListener('click', addNewQuestion);
}

function addNewQuestion() {
    questionCount++;

    const questionsContainer = document.querySelector('.questions-container');
    const newQuestion = document.createElement('div');
    newQuestion.className = 'question-card';
    newQuestion.innerHTML = `
        <div class="question-header">
            <span class="question-number">Câu hỏi ${questionCount}</span>
            <div class="question-actions">
                <button class="btn-icon" title="Sửa" type="button"><i class="fas fa-edit"></i></button>
                <button class="btn-icon" title="Xóa" type="button"><i class="fas fa-trash"></i></button>
            </div>
        </div>

        <div class="question-content">
            <div class="form-group">
                <label>Nội dung câu hỏi *</label>
                <textarea class="form-input" rows="3" placeholder="Nhập câu hỏi..."></textarea>
            </div>

            <div class="form-group">
                <label>Loại câu hỏi *</label>
                <select class="form-input">
                    <option value="multiple">Trắc nghiệm (Một đáp án)</option>
                    <option value="checkbox">Trắc nghiệm (Nhiều đáp án)</option>
                    <option value="essay">Tự luận</option>
                    <option value="true-false">Đúng/Sai</option>
                </select>
            </div>

            <div class="answers-container">
                <label style="margin-bottom: 1rem; display: block;">Đáp án: <span style="color: #ef4444;">*</span></label>
                <div class="answer-item">
                    <input type="radio" name="answer-${questionCount}" value="a" checked>
                    <input type="text" class="form-input" placeholder="Đáp án A" style="margin: 0 10px; flex: 1;">
                    <input type="number" class="form-input" placeholder="Điểm" style="width: 80px;">
                    <button class="btn-delete-answer" type="button" title="Xóa">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="answer-item">
                    <input type="radio" name="answer-${questionCount}" value="b">
                    <input type="text" class="form-input" placeholder="Đáp án B" style="margin: 0 10px; flex: 1;">
                    <input type="number" class="form-input" placeholder="Điểm" style="width: 80px;">
                    <button class="btn-delete-answer" type="button" title="Xóa">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="answer-item">
                    <input type="radio" name="answer-${questionCount}" value="c">
                    <input type="text" class="form-input" placeholder="Đáp án C" style="margin: 0 10px; flex: 1;">
                    <input type="number" class="form-input" placeholder="Điểm" style="width: 80px;">
                    <button class="btn-delete-answer" type="button" title="Xóa">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <div class="answer-item">
                    <input type="radio" name="answer-${questionCount}" value="d">
                    <input type="text" class="form-input" placeholder="Đáp án D" style="margin: 0 10px; flex: 1;">
                    <input type="number" class="form-input" placeholder="Điểm" style="width: 80px;">
                    <button class="btn-delete-answer" type="button" title="Xóa">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <button class="btn-add-answer" type="button">
                    <i class="fas fa-plus"></i> Thêm đáp án
                </button>
            </div>

            <div class="form-group">
                <label>Ghi chú/Giải thích</label>
                <textarea class="form-input" rows="2" placeholder="Giải thích đáp án đúng..."></textarea>
            </div>
        </div>
    `;

    // Insert before the "Add Question" button
    questionsContainer.insertBefore(newQuestion, addQuestionBtn);

    // Attach handlers
    attachDeleteHandler(newQuestion.querySelector('.btn-icon:nth-child(2)'));
    attachAddAnswerHandler(newQuestion.querySelector('.btn-add-answer'));
    attachDeleteAnswerHandlers(newQuestion);
}

// ========== DELETE QUESTION ==========
function attachDeleteHandler(btn) {
    btn.addEventListener('click', function (e) {
        e.preventDefault();
        const card = this.closest('.question-card');
        if (confirm('Bạn có chắc muốn xóa câu hỏi này?')) {
            card.style.opacity = '0.5';
            card.style.pointerEvents = 'none';
            setTimeout(() => card.remove(), 300);
        }
    });
}

// ========== DELETE ANSWER ==========
function attachDeleteAnswerHandlers(container) {
    container.querySelectorAll('.btn-delete-answer').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const answerItems = this.closest('.answers-container').querySelectorAll('.answer-item');
            if (answerItems.length > 2) {
                this.closest('.answer-item').remove();
            } else {
                alert('Phải giữ lại ít nhất 2 đáp án!');
            }
        });
    });
}

// ========== INITIAL DELETE HANDLERS ==========
document.querySelectorAll('.btn-icon').forEach(btn => {
    if (btn.innerHTML.includes('trash')) {
        attachDeleteHandler(btn);
    }
});

// ========== INITIAL ADD ANSWER HANDLERS ==========
document.querySelectorAll('.btn-add-answer').forEach(btn => {
    attachAddAnswerHandler(btn);
});

// ========== INITIAL DELETE ANSWER HANDLERS ==========
document.querySelectorAll('.btn-delete-answer').forEach(btn => {
    btn.addEventListener('click', function (e) {
        e.preventDefault();
        const answersContainer = this.closest('.answers-container');
        const answerItems = answersContainer.querySelectorAll('.answer-item');
        if (answerItems.length > 2) {
            this.closest('.answer-item').remove();
        } else {
            alert('Phải giữ lại ít nhất 2 đáp án!');
        }
    });
});

// ========== FORM SUBMISSION ==========
const formActions = document.querySelector('.form-actions');

if (formActions) {
    const saveDraftBtn = formActions.querySelector('.btn-secondary:nth-child(2)');
    const publishBtn = formActions.querySelector('.btn-primary');

    if (saveDraftBtn) {
        saveDraftBtn.addEventListener('click', saveQuizDraft);
    }

    if (publishBtn) {
        publishBtn.addEventListener('click', publishQuiz);
    }
}

function saveQuizDraft(e) {
    e.preventDefault();
    const quizData = collectQuizData();
    console.log('Saving draft...', quizData);
    alert('Bài quiz đã được lưu nháp!');
}

function publishQuiz(e) {
    e.preventDefault();
    const quizData = collectQuizData();

    // Validate
    if (!quizData.title) {
        alert('Vui lòng nhập tiêu đề bài quiz!');
        return;
    }

    if (quizData.questions.length === 0) {
        alert('Vui lòng thêm ít nhất một câu hỏi!');
        return;
    }

    console.log('Publishing quiz...', quizData);
    alert('Bài quiz đã được công bố!');
}

function collectQuizData() {
    const data = {
        title: document.getElementById('quiz-title').value,
        course: document.getElementById('quiz-course').value,
        category: document.getElementById('quiz-category').value,
        description: document.getElementById('quiz-description').value,
        time: document.getElementById('quiz-time').value,
        maxScore: document.getElementById('quiz-max-score').value,
        passingScore: document.getElementById('quiz-passing-score').value,
        attempts: document.getElementById('quiz-attempts').value,
        showAnswers: document.getElementById('show-answers').checked,
        shuffleQuestions: document.getElementById('shuffle-questions').checked,
        shuffleAnswers: document.getElementById('shuffle-answers').checked,
        questions: []
    };

    // Collect questions
    document.querySelectorAll('.question-card').forEach((card, index) => {
        const question = {
            number: index + 1,
            content: card.querySelector('textarea').value,
            type: card.querySelectorAll('select')[1].value,
            answers: []
        };

        card.querySelectorAll('.answer-item').forEach(item => {
            question.answers.push({
                isCorrect: item.querySelector('input[type="radio"]').checked,
                text: item.querySelector('input[type="text"]').value,
                points: item.querySelector('input[type="number"]').value
            });
        });

        data.questions.push(question);
    });

    return data;
}

// ========== INITIALIZE ==========
document.addEventListener('DOMContentLoaded', function () {
    console.log('Quiz edit page loaded');
}); 