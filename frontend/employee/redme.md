```
employee/
├── assets/
│   ├── css/
│   │   ├── components/
│   │   │   ├── button.css             #  THÊM: Style nút bấm (Primary, Secondary, Danger)
│   │   │   ├── card.css               #  THÊM: Style card khóa học, card thống kê
│   │   │   ├── progress-bar.css       #  THÊM: Style thanh tiến độ học tập (0-100%)
│   │   │   ├── modal.css              #  THÊM: Style popup xác nhận, chi tiết
│   │   │   └── table.css              #  THÊM: Style bảng danh sách khóa học
│   │   │
│   │   └── pages/
│   │       ├── dashboard.css          #  THÊM: Style trang chính (grid layout 3 cột)
│   │       ├── courses.css            #  THÊM: Style danh sách & chi tiết khóa học
│   │       ├── learning.css           #  THÊM: Style trang học bài (main content + sidebar)
│   │       ├── quiz.css               #  THÊM: Style câu hỏi, đáp án, kết quả
            ├── progress.css           
            ├── change-password.css
│   │       └── profile.css            #  THÊM: Style form thông tin, form đổi mật khẩu
│   │
│   ├── images/
│   │   ├── icons/
│   │   │   ├── dashboard.svg          #  THÊM: Icon trang chính (header menu)
│   │   │   ├── course.svg             #  THÊM: Icon khóa học (16x16, 24x24)
│   │   │   ├── certificate.svg        #  THÊM: Icon chứng chỉ (header menu)
│   │   │   ├── progress.svg           #  THÊM: Icon tiến độ (header menu)
│   │   │   ├── quiz.svg               #  THÊM: Icon bài quiz (header menu)
│   │   │   ├── profile.svg            #  THÊM: Icon profile (header dropdown menu)
│   │   │   └── logout.svg             #  THÊM: Icon đăng xuất (header dropdown)
│   │   │
│   │   ├── illustrations/
│   │   │   ├── empty-state.svg        #  ĐỔI TÊN: Hình minh họa khi chưa có khóa học
│   │   │   └── success.svg            #  ĐỔI TÊN: Hình minh họa hoàn thành khóa/quiz
│   │   │
│   │   └── avatars/
│   │       └── default-avatar.png     #  THÊM: Avatar mặc định (128x128px)
│   │
│   └── js/
│       └── lib/
│           └── chart.min.js           #  THÊM: Thư viện vẽ biểu đồ (Chart.js)
│
├── components/
│   ├── layout/
│   │   ├── header.html                #  THÊM: Navigation bar (Logo, Menu, User dropdown)
│   │   ├── sidebar.html               #  THÊM: Menu bên trái (Dashboard, Courses, Quiz, Progress, Certificates, Profile)
│   │   └── footer.html                #  THÊM: Footer (Copyright, Contact, Links)
│   │
│   ├── cards/
│   │   ├── course-card.html           #  THÊM: Card khóa học (Ảnh, Tên, Tiến độ, Button)
│   │   └── stats-card.html            #  ĐỔI TÊN: Card thống kê (Số khóa, % hoàn thành, Chứng chỉ)
│   │
│   └── modals/
│       ├── certificate-view-modal.html #  ĐỔI TÊN: Popup xem chứng chỉ (preview, download)
│       └── confirm-modal.html         #  THÊM: Popup xác nhận (submit quiz, logout, xóa)
│
├── pages/
│   ├── dashboard/
│   │   └── index.html                 # 🏠 TRANG CHÍNH: Tổng quan (3 stats card + 4 khóa học gợi ý + Biểu đồ tiến độ)
│   │
│   ├── courses/
│   │   ├── list.html                  #  ĐỔI TÊN: Danh sách tất cả khóa học (Grid, Filter, Search)
│   │   ├── detail.html                #  ĐỔI TÊN: Chi tiết khóa học (Mô tả, Nội dung, Button "Học ngay")
│   │   └── learning.html              #  THÊM: Trang học bài (Video/PDF + Video bên phải)
│   │
│   ├── quiz/
│   │   ├── list.html                  #  ĐỔI TÊN: Danh sách quiz có sẵn (Khóa học nào, Điểm cao, Trạng thái)
│   │   ├── take.html                  #  ĐỔI TÊN: Trang làm quiz (Timer, Câu hỏi, Đáp án, Progress)
│   │   └── result.html                #  ĐỔI TÊN: Kết quả quiz (Điểm, % đúng, Giải thích, Nút lặp lại)
│   │
│   ├── progress/
│   │   └── index.html                 #  ĐỔI TÊN: Trang tiến độ cá nhân (Biểu đồ, Bảng khóa, Timeline)
│   │
│   ├── certificates/
│   │   └── index.html                 #  ĐỔI TÊN: Danh sách chứng chỉ (Card, Download, Share)
│   │
│   └── profile/
│       ├── index.html                 #  ĐỔI TÊN: Xem thông tin cá nhân (Ảnh, Email, Bộ phận, Ngày tham gia)
│       ├── edit.html                  #  ĐỔI TÊN: Form chỉnh sửa thông tin (Name, Email, Avatar)
│       └── change-password.html       #  THÊM: Form đổi mật khẩu (Old Password, New Password)
│
└── js/
    ├── config/
    │   └── api-endpoints.js           #  THÊM: Config URL API (BASE_URL, endpoints)
    │
    ├── services/
    │   ├── course.service.js          #  THÊM: API call khóa học (list, detail, enroll, unenroll)
    │   ├── quiz.service.js            #  THÊM: API call quiz (list, questions, submit, result)
    │   ├── progress.service.js        #  THÊM: API call tiến độ (get stats, chart data)
    │   ├── certificate.service.js     #  THÊM: API call chứng chỉ (list, view, download)
    │   └── profile.service.js         #  THÊM: API call profile (get, update, change password)
    │
    ├── components/
    │   ├── header.js                  #  THÊM: Logic header (dropdown, logout, notifications)
    │   ├── sidebar.js                 #  THÊM: Logic sidebar (active menu, responsive toggle)
    │   └── notification.js            #  THÊM: Logic toast notification (success, error, warning)
    │
    └── pages/
        ├── dashboard.js               # 🏠 TRANG CHÍNH: Load stats, featured courses, chart
        ├── courses/
        │   ├── list.js                #  ĐỔI TÊN: Render danh sách, filter, search
        │   ├── detail.js              #  ĐỔI TÊN: Render chi tiết khóa, button enroll
        │   └── learning.js            #  THÊM: Render nội dung bài học, progress bar
        ├── quiz/
        │   ├── list.js                #  Render danh sách quiz
        │   ├── take.js                #  Render form quiz, timer, validation
        │   └── result.js              #  Render kết quả, biểu đồ
        ├── progress/
        │   └── index.js               #  Render chart, stats, timeline
        ├── certificates/
        │   └── index.js               #  Render certificate grid, download
        └── profile/
            ├── index.js               #  Render thông tin cá nhân
            ├── edit.js                #  Logic form edit, upload avatar
            └── change-password.js     #  Logic form đổi mật khẩu, validation
```

---

## 📋 Ghi Chú Quan Trọng

### 🏠 **Trang Chính (Dashboard - index.html)**
**Vị trí:** `pages/dashboard/index.html`

**Nội dung chính:**
- **Stats Cards (3 card):** Tổng khóa học, % hoàn thành, Chứng chỉ đạt
- **Featured Courses (4 khóa):** Các khóa học được đề xuất
- **Progress Chart:** Biểu đồ tiến độ học tập (Pie/Bar chart)
- **Quick Links:** Nút nhanh (Xem tất cả khóa, Quiz mới)

---

### 📁 **Cấu Trúc Tổng Quan**

| Mục | Chức năng | File chính |
|-----|---------|-----------|
| **Dashboard** | Trang chủ, tổng quan | `pages/dashboard/index.html` |
| **Courses** | Danh sách & chi tiết khóa học | `pages/courses/list.html`, `detail.html` |
| **Learning** | Học bài nội dung | `pages/courses/learning.html` |
| **Quiz** | Làm bài kiểm tra | `pages/quiz/take.html` |
| **Progress** | Xem tiến độ cá nhân | `pages/progress/index.html` |
| **Certificates** | Xem chứng chỉ | `pages/certificates/index.html` |
| **Profile** | Thông tin & cài đặt | `pages/profile/index.html`, `edit.html` |

---

### 🎨 **Component Tái Sử Dụng**

- **header.html**: Xuất hiện trên mọi trang
- **sidebar.html**: Menu навігації (có thể ẩn trên mobile)
- **course-card.html**: Dùng trong dashboard, list.html
- **stats-card.html**: Dùng trong dashboard, progress
- **confirm-modal.html**: Dùng cho xác nhận hành động

---

### 🔗 **Flow Chính**
```
Dashboard → Xem khóa học → Chọn khóa → Learning → Quiz → Certificate
           ↓
        Profile → Edit info / Change password
```