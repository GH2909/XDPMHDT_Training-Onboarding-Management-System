// ===== TOGGLE PASSWORD VISIBILITY =====
function togglePassword() {
    const passwordInput = document.getElementById('password');
    const toggleBtn = document.querySelector('.toggle-password i');

    if (!passwordInput || !toggleBtn) return;

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        toggleBtn.classList.remove('fa-eye');
        toggleBtn.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        toggleBtn.classList.remove('fa-eye-slash');
        toggleBtn.classList.add('fa-eye');
    }
}

// ===== VALIDATE EMAIL =====
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// ===== VALIDATE FORM =====
function validateLoginForm() {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const emailError = document.getElementById('emailError');
    const passwordError = document.getElementById('passwordError');

    let isValid = true;

    // Clear previous errors
    emailError.textContent = '';
    emailError.classList.remove('show');
    passwordError.textContent = '';
    passwordError.classList.remove('show');

    // Validate email
    if (!email) {
        emailError.textContent = 'Vui lòng nhập email';
        emailError.classList.add('show');
        isValid = false;
    } else if (!validateEmail(email)) {
        emailError.textContent = 'Email không hợp lệ';
        emailError.classList.add('show');
        isValid = false;
    }

    // Validate password
    if (!password) {
        passwordError.textContent = 'Vui lòng nhập mật khẩu';
        passwordError.classList.add('show');
        isValid = false;
    } else if (password.length < 6) {
        passwordError.textContent = 'Mật khẩu phải tối thiểu 6 ký tự';
        passwordError.classList.add('show');
        isValid = false;
    }

    return isValid;
}

// ===== GET SELECTED ROLE =====
function getSelectedRole() {
    const roleRadios = document.querySelectorAll('input[name="role"]');
    for (let radio of roleRadios) {
        if (radio.checked) {
            return radio.value;
        }
    }
    return 'employee';
}

// ===== HANDLE LOGIN SUBMIT =====
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            e.preventDefault();

            // Validate form
            if (!validateLoginForm()) {
                return;
            }

            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value.trim();
            const role = getSelectedRole();
            const rememberMe = document.querySelector('input[name="remember"]').checked;

            // TODO: Send login request to API
            // const loginData = {
            //     email: email,
            //     password: password,
            //     role: role
            // };
            // fetch('/api/auth/login', {
            //     method: 'POST',
            //     headers: { 'Content-Type': 'application/json' },
            //     body: JSON.stringify(loginData)
            // })
            // .then(res => res.json())
            // .then(data => {
            //     if (data.success) {
            //         // Store token
            //         localStorage.setItem('token', data.token);
            //         localStorage.setItem('role', role);
            //         if (rememberMe) {
            //             localStorage.setItem('email', email);
            //         }
            //         
            //         // Redirect based on role
            //         redirectByRole(role);
            //     } else {
            //         alert('Email hoặc mật khẩu không đúng');
            //     }
            // })
            // .catch(err => {
            //     console.error('Login error:', err);
            //     alert('Lỗi kết nối. Vui lòng thử lại.');
            // });

            // Temporary: Show alert and redirect
            console.log('Login attempt:', { email, password, role, rememberMe });
            alert(`Đăng nhập với vai trò: ${getRoleLabel(role)}`);
            redirectByRole(role);
        });
    }

    // Clear errors on input
    document.getElementById('email')?.addEventListener('input', function () {
        document.getElementById('emailError').classList.remove('show');
    });

    document.getElementById('password')?.addEventListener('input', function () {
        document.getElementById('passwordError').classList.remove('show');
    });
});

// ===== GET ROLE LABEL =====
function getRoleLabel(role) {
    const labels = {
        'employee': 'Nhân Viên',
        'trainer': 'Huấn Luyện Viên',
        'hr': 'Nhân Sự'
    };
    return labels[role] || 'Người Dùng';
}

// ===== REDIRECT BY ROLE =====
function redirectByRole(role) {
    const redirectPaths = {
        'employee': '../../../employee/pages/dashboard/index.html',
        'trainer': '../../../trainer/pages/dashboard/index.html',
        'hr': '../../../hr/pages/dashboard/index.html'
    };

    const redirectPath = redirectPaths[role] || '../../../employee/pages/dashboard/index.html';
    window.location.href = redirectPath;
}

// ===== CHECK IF ALREADY LOGGED IN =====
function checkAuthStatus() {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    if (token && role) {
        // Already logged in, redirect to dashboard
        redirectByRole(role);
    }
}

// Run on page load
checkAuthStatus();

// ===== LOGOUT FUNCTION (for other pages) =====
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
    window.location.href = '../shared/pages/auth/login.html';
} 