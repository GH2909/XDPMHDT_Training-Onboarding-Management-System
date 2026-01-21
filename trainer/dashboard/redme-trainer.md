# Cấu Trúc Thư Mục Trainer Module

## 1. Cấu Trúc Thư Mục Chi Tiết

```
frontend-Training-Onboarding-Management-System/
├── shared/                    
│   ├── components/
│   │   ├── Header.html        # Header chung cho tất cả roles
│   │   ├── Sidebar.html       # (tùy chọn) Sidebar chung
│   │   └── Footer.html
│   ├── css/
│   │   ├── colors.css         # Biến màu: #E6D9FF, #FFFFFF, #374151
│   │   ├── typography.css
│   │   └── global.css
│   ├── js/
│   │   ├── auth.js            # Xác thực & phân quyền
│   │   ├── api-client.js      # Gọi API chung
│   │   └── utils.js           # Hàm dùng chung
│   └── assets/
│       ├── icons/
│       ├── images/
│       └── fonts/
│
├── trainer/
│   ├── dashboard/
│   │   ├── index.html         # Trang chính Trainer ★ CẢI THIỆN
│   │   ├── dashboard.css
│   │   └── dashboard.js
│   │
│   ├── courses/
│   │   ├── index.html         # Danh sách khóa học
│   │   ├── list.css
│   │   └── list.js
│   │
│   ├── lessons/
│   │   ├── index.html         # Danh sách bài giảng
│   │   ├── create.html        # Tạo bài giảng (UC03)
│   │   ├── edit.html
│   │   ├── lessons.css
│   │   └── lessons.js
│   │
│   ├── grading/
│   │   ├── index.html         # Chấm điểm bài tập
│   │   ├── grading.css
│   │   └── grading.js
│   │
│   ├── quizzes/
│   │   ├── index.html         # Quản lý bài kiểm tra
│   │   ├── create.html        # Tạo quiz
│   │   ├── edit.html
│   │   ├── quizzes.css
│   │   └── quizzes.js
│   │
│   ├── analytics/
│   │   ├── index.html         # Cham diem
│   │   ├── analytics.css
│   │   └── analytics.js
│   │
│   ├── students/
│   │   ├── index.html         # Danh sách học viên
│   │   ├── detail.html        # Chi tiết học viên
│   │   ├── students.css
│   │   └── students.js
│   │
│   ├── profile/
│   │   ├── index.html         # Thông tin cá nhân Trainer
│   │   ├── profile.css
│   │   └── profile.js
│   │
│   ├── css/
│   │   └── trainer-style.css  # Style riêng cho Trainer
│   │
│   ├── js/
│   │   ├── trainer-app.js     # Logic chính Trainer
│   │   └── trainer-utils.js
│   │
│   └── assets/
│       └── (chia sẻ từ shared/assets/)
│
├── employee/
├── hr/
└── index.html                 # Trang login chung
```

---

## 2. Chức Năng Từng Module Trainer

### **dashboard/** - Trang Chính Trainer
**Chức năng:**
- Hiển thị thống kê: số học viên, khóa học đang giảng dạy, bài giảng chưa chấm
- Danh sách khóa học được phân công
- Thông báo & yêu cầu từ học viên
- Lịch giảng dạy

**Use Cases:** Hiển thị tổng quan công việc

---

### **courses/** - Quản Lý Khóa Học
**Chức năng:**
- Xem danh sách khóa học
- Thông tin chi tiết khóa học (mô tả, mục tiêu, số buổi, số học viên)

**Use Cases:** UC02 (một phần - xem danh sách)

---

### **lessons/** - Quản Lý Bài Giảng
**Chức năng:**
- Xem danh sách bài giảng
- Tạo bài giảng mới (UC03)
- Chỉnh sửa bài giảng
- Xóa bài giảng

**Use Cases:** UC03 - Tạo bài giảng

---

### **quizzes/** - Quản Lý Bài Kiểm Tra
**Chức năng:**
- Xem danh sách bài kiểm tra
- Tạo bài kiểm tra / Quiz (UC03)
- Chỉnh sửa cấu hình bài kiểm tra
- Xem kết quả làm bài

**Use Cases:** UC03 - Tạo bài kiểm tra (Quiz)

---

### **grading/** - Chấm Điểm
**Chức năng:**
- Xem danh sách bài cần chấm (bài tự luận)
- Chấm điểm từng bài (UC03 bước 8)
- Nhận xét, feedback cho học viên
- Lưu kết quả vào DB

**Use Cases:** UC03 bước 8-10 - Chấm điểm

---

### **analytics/** - Báo Cáo & Thống Kê
**Chức năng:**
- Thống kê tỉ lệ hoàn thành khóa học
- Điểm trung bình học viên
- Tiến độ học tập theo khóa
- Báo cáo 30-60-90 ngày

**Use Cases:** Hỗ trợ UC02, UC04

---

### **students/** - Quản Lý Học Viên
**Chức năng:**
- Danh sách học viên đang học
- Chi tiết học viên (tiến độ, điểm, bài nộp)
- Gửi thông báo cho học viên

**Use Cases:** Liên quan UC04

---

### **profile/** - Thông Tin Cá Nhân
**Chức năng:**
- Xem & cập nhật thông tin cá nhân
- Đổi mật khẩu

**Use Cases:** Tương tự UC04 (phần profile)

---

## 3. Quyền Hạn (Permissions)

Trainer có quyền:
- ✅ Tạo, sửa, xóa bài giảng
- ✅ Tạo, sửa, xóa bài kiểm tra
- ✅ Chấm điểm
- ✅ Xem danh sách học viên & tiến độ
- ✅ Không được: Quản lý người dùng, tạo khóa học (HR làm)

---

## 4. Color Scheme (Đồng Bộ)

```css
--primary-lavender: #E6D9FF;  /* Nền chính */
--white: #FFFFFF;             /* Nền card, text area */
--dark-gray: #374151;          /* Text đậm */
--light-gray: #F3F4F6;         /* Nền phụ */
--accent-purple: #9333EA;      /* Highlight */
--success: #10B981;            /* Thành công */
--danger: #EF4444;             /* Lỗi/Xóa */
--warning: #F59E0B;            /* Cảnh báo */
```