document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("access_token");

    if (!token) {
        alert("Bạn chưa đăng nhập!");
        window.location.href = "/login.html";
        return;
    }

    const saveBtn = document.querySelector(".btn-primary");
    if (!saveBtn) return;

    saveBtn.addEventListener("click", async (e) => {
    e.preventDefault();

    const quizId = document.getElementById("quiz-id").value;

    // Chưa có quiz--> lưu inf quiz trước
    if (!quizId) {
        await createQuiz(token);
    } 
    // Có quiz rồi --> lưu câu hỏi
    else {
        await saveQuestions(token, quizId);
    }
});

    const questionsContainer = document.querySelector(".questions-container");
    const addQuestionBtn = document.querySelector(".btn-add-question");

    // ===== TEMPLATE CHO 1 ĐÁP ÁN =====
    function createAnswerElement() {
        const div = document.createElement("div");
        div.className = "answer-item";
        div.innerHTML = `
            <input type="checkbox" class="choice-correct">
            <input type="text" class="form-input choice-content" placeholder="Nội dung đáp án">
            <input type="number" class="form-input choice-score" placeholder="Điểm" style="width:80px">
            <button class="btn-delete-answer" type="button"><i class="fas fa-times"></i></button>
        `;
        return div;
    }

    // ===== TEMPLATE CHO 1 CÂU HỎI =====
    function createQuestionElement() {
        const div = document.createElement("div");
        div.className = "question-card";
        div.innerHTML = `
            <div class="question-header">
                <span class="question-number">Câu hỏi</span>
                <div class="question-actions">
                    <button class="btn-icon btn-delete-question" type="button">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </div>

            <div class="question-content">
                <div class="form-group">
                    <label>Nội dung câu hỏi *</label>
                    <textarea class="form-input question-content-input" rows="3" placeholder="Nhập câu hỏi..."></textarea>
                </div>

                <div class="form-group">
                    <label>Loại câu hỏi *</label>
                    <select class="form-input question-type">
                        <option value="SINGLE_CHOICE">Trắc nghiệm</option>
                        <option value="TRUE_FALSE">Đúng/Sai</option>
                    </select>
                </div>

                <div class="answers-container">
                    <label style="margin-bottom: 1rem; display: block;">
                        Đáp án: <span style="color: #ef4444;">*</span>
                    </label>

                    <div class="choices-list"></div>

                    <button class="btn-add-answer" type="button">
                        <i class="fas fa-plus"></i> Thêm đáp án
                    </button>
                </div>
            </div>
        `;
        return div;
    }

    // ===== THÊM CÂU HỎI =====
    addQuestionBtn.addEventListener("click", () => {
        const newQuestion = createQuestionElement();
        questionsContainer.insertBefore(newQuestion, addQuestionBtn);
    });

    questionsContainer.addEventListener("click", (e) => {

        // Thêm đáp án
        if (e.target.closest(".btn-add-answer")) {
            const questionCard = e.target.closest(".question-card");
            const choicesList = questionCard.querySelector(".choices-list");
            choicesList.appendChild(createAnswerElement());
        }

        // Xoá đáp án
        if (e.target.closest(".btn-delete-answer")) {
            e.target.closest(".answer-item").remove();
        }

        // Xoá câu hỏi
        if (e.target.closest(".btn-delete-question")) {
            e.target.closest(".question-card").remove();
        }
    });
});

async function createQuiz(token) {
    const quizData = {
        lessonId: parseInt(document.getElementById("lesson-id").value) || 1,
        title: document.getElementById("quiz-title").value.trim(),
        description: document.getElementById("quiz-description").value.trim(),
        durationMinutes: parseInt(document.getElementById("quiz-time").value),
        maxScore: parseInt(document.getElementById("quiz-max-score").value),
        passScore: parseInt(document.getElementById("quiz-pass-score").value),
        attemptLimit: parseInt(document.getElementById("quiz-attempt-limit").value),
        status: document.getElementById("quiz-status").value,
        startTime: document.getElementById("quiz-start-time").value || null,
        endTime: document.getElementById("quiz-end-time").value || null,
        showAnswers: document.getElementById("show-answers").checked,
        shuffleQuestions: document.getElementById("shuffle-questions").checked,
        shuffleChoices: document.getElementById("shuffle-choices").checked
    };

    try {
        const res = await fetch("http://localhost:8080/training/quizzes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(quizData)
        });

        const result = await res.json();
        if (!res.ok) throw new Error(result.message || "Không thể tạo quiz");

        const quizId = result.id;
        document.getElementById("quiz-id").value = quizId;

        alert("Đã lưu thông tin Quiz! Bây giờ hãy thêm câu hỏi.");

        // --> sang tab câu hỏi
        document.querySelector('[data-tab="questions"]').click();

    } catch (err) {
        console.error(err);
        alert("Lỗi khi tạo quiz: " + err.message);
    }
}


async function saveQuestions(token, quizId) {
    const questionCards = document.querySelectorAll(".question-card");

    for (const card of questionCards) {
        const content = card.querySelector(".question-content-input").value.trim();
        const type = card.querySelector(".question-type").value;

        const answerItems = card.querySelectorAll(".answer-item");
        const choices = [];

        answerItems.forEach((ans, index) => {
            const choiceContent = ans.querySelector(".choice-content").value.trim();

            if (!choiceContent) return; // bỏ đáp án rỗng

            choices.push({
                content: choiceContent,
                isAnswer: ans.querySelector(".choice-correct").checked,
                score: parseInt(ans.querySelector(".choice-score").value) || 0,
                orderIndex: index + 1
            });
        });

        // ==== VALIDATE ====
        if (!content) continue;

        if (choices.length < 2) {
            alert("Mỗi câu hỏi phải có ít nhất 2 đáp án");
            throw new Error("Thiếu đáp án");
        }

        if (!choices.some(c => c.isAnswer)) {
            alert("Mỗi câu hỏi phải có ít nhất 1 đáp án đúng");
            throw new Error("Chưa chọn đáp án đúng");
        }

        const questionData = { quizId: quizId, content, type, choices };

        const res = await fetch(`http://localhost:8080/training/questions`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            },
            body: JSON.stringify(questionData)
        });

        const result = await res.json();
        if (!res.ok) throw new Error(result.message || "Không thể lưu câu hỏi");
    }
}

