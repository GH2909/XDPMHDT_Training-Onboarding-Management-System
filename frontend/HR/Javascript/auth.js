// ===== CONSTANTS =====
const ANIMATION_DURATION = 300;
const ERROR_DISPLAY_TIME = 3000;

// ===== DOM ELEMENTS =====
const loginForm = document.getElementById('loginForm');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const togglePasswordBtn = document.getElementById('togglePasswordBtn');
const emailError = document.getElementById('emailError');
const passwordError = document.getElementById('passwordError');
const roleRadios = document.querySelectorAll('input[name="role"]');
const rememberCheckbox = document.querySelector('input[name="remember"]');

// ===== UTILITY FUNCTIONS =====

/**
 * Validate email format
 */
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

/**
 * Validate password (minimum 6 characters)
 */
function isValidPassword(password) {
    return password.length >= 6;
}

/**
 * Show error message
 */
function showError(element, message) {
    element.textContent = message;
    element.classList.add('show');
    
    // Auto hide after 3 seconds
    setTimeout(() => {
        hideError(element);
    }, ERROR_DISPLAY_TIME);
}

/**
 * Hide error message
 */
function hideError(element) {
    element.classList.remove('show');
    element.textContent = '';
}

/**
 * Add shake animation to element
 */
function shakeElement(element) {
    element.style.animation = 'shake 0.5s';
    setTimeout(() => {
        element.style.animation = '';
    }, 500);
}

/**
 * Get selected role
 */
function getSelectedRole() {
    const selectedRole = document.querySelector('input[name="role"]:checked');
    return selectedRole ? selectedRole.value : 'employee';
}

/**
 * Store login data in localStorage (if remember me is checked)
 */
function storeLoginData(email, role, remember) {
    if (remember) {
        localStorage.setItem('rememberedEmail', email);
        localStorage.setItem('rememberedRole', role);
    } else {
        localStorage.removeItem('rememberedEmail');
        localStorage.removeItem('rememberedRole');
    }
}

/**
 * Load remembered data
 */
function loadRememberedData() {
    const rememberedEmail = localStorage.getItem('rememberedEmail');
    const rememberedRole = localStorage.getItem('rememberedRole');
    
    if (rememberedEmail) {
        emailInput.value = rememberedEmail;
        rememberCheckbox.checked = true;
    }
    
    if (rememberedRole) {
        const roleRadio = document.querySelector(`input[name="role"][value="${rememberedRole}"]`);
        if (roleRadio) {
            roleRadio.checked = true;
        }
    }
}

/**
 * Show loading state on button
 */
function setButtonLoading(button, isLoading) {
    if (isLoading) {
        button.disabled = true;
        button.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
    } else {
        button.disabled = false;
        button.innerHTML = '<i class="fas fa-sign-in-alt"></i> Đăng Nhập';
    }
}

// ===== MOCK LOGIN FUNCTION =====
/**
 * Simulate API call for login
 * In production, replace this with actual API call
 */
async function loginUser(email, password, role) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            // Mock validation - replace with actual API call
            if (email === 'admin@uth.edu.vn' && password === '123456') {
                resolve({
                    success: true,
                    user: {
                        email: email,
                        role: role,
                        name: 'Admin User'
                    },
                    token: 'mock_token_' + Date.now()
                });
            } else {
                reject({
                    success: false,
                    message: 'Email hoặc mật khẩu không chính xác'
                });
            }
        }, 1500);
    });
}

// ===== EVENT HANDLERS =====

/**
 * Toggle password visibility - FIXED VERSION
 */
togglePasswordBtn.addEventListener('click', function(e) {
    e.preventDefault();
    e.stopPropagation();
    
    const type = passwordInput.type === 'password' ? 'text' : 'password';
    passwordInput.type = type;
    
    const icon = this.querySelector('i');
    if (type === 'password') {
        icon.className = 'fas fa-eye';
    } else {
        icon.className = 'fas fa-eye-slash';
    }
});

/**
 * Clear error on input
 */
emailInput.addEventListener('input', function() {
    hideError(emailError);
    this.parentElement.style.borderColor = '';
});

passwordInput.addEventListener('input', function() {
    hideError(passwordError);
    this.parentElement.style.borderColor = '';
});

/**
 * Form submission handler
 */
loginForm.addEventListener('submit', async function(e) {
    e.preventDefault();
    
    // Clear previous errors
    hideError(emailError);
    hideError(passwordError);
    
    // Get form values
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const role = getSelectedRole();
    const remember = rememberCheckbox.checked;
    
    let hasError = false;
    
    // Validate email
    if (!email) {
        showError(emailError, 'Vui lòng nhập email');
        shakeElement(emailInput.parentElement);
        hasError = true;
    } else if (!isValidEmail(email)) {
        showError(emailError, 'Email không hợp lệ');
        shakeElement(emailInput.parentElement);
        hasError = true;
    }
    
    // Validate password
    if (!password) {
        showError(passwordError, 'Vui lòng nhập mật khẩu');
        shakeElement(passwordInput.parentElement);
        hasError = true;
    } else if (!isValidPassword(password)) {
        showError(passwordError, 'Mật khẩu phải có ít nhất 6 ký tự');
        shakeElement(passwordInput.parentElement);
        hasError = true;
    }
    
    if (hasError) {
        return;
    }
    
    // Show loading state
    const submitButton = this.querySelector('.btn-login');
    setButtonLoading(submitButton, true);
    
    try {
        // Call login API (mock function)
        const response = await loginUser(email, password, role);
        
        // Store login data if remember is checked
        storeLoginData(email, role, remember);
        
        // Store auth token
        sessionStorage.setItem('authToken', response.token);
        sessionStorage.setItem('userRole', role);
        sessionStorage.setItem('userEmail', email);
        
        // Show success message
        showSuccessMessage('Đăng nhập thành công! Đang chuyển hướng...');
        
        // Redirect based on role
        setTimeout(() => {
            redirectUser(role);
        }, 1000);
        
    } catch (error) {
        setButtonLoading(submitButton, false);
        showError(passwordError, error.message || 'Đăng nhập thất bại. Vui lòng thử lại.');
        shakeElement(loginForm);
    }
});

/**
 * Show success message
 */
function showSuccessMessage(message) {
    const successDiv = document.createElement('div');
    successDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: linear-gradient(135deg, #10b981 0%, #059669 100%);
        color: white;
        padding: 1rem 1.5rem;
        border-radius: 10px;
        box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
        z-index: 9999;
        animation: slideInRight 0.3s ease-out;
        font-weight: 600;
    `;
    successDiv.innerHTML = `<i class="fas fa-check-circle"></i> ${message}`;
    document.body.appendChild(successDiv);
    
    setTimeout(() => {
        successDiv.remove();
    }, 3000);
}

/**
 * Redirect user based on role
 */
function redirectUser(role) {
    const redirectUrls = {
        'employee': '/dashboard/employee.html',
        'trainer': '/dashboard/trainer.html',
        'hr': '/dashboard/hr.html'
    };
    
    const url = redirectUrls[role] || '/dashboard/employee.html';
    
    // In development, show alert instead of redirect
    alert(`Đăng nhập thành công với vai trò: ${role.toUpperCase()}\nSẽ chuyển đến: ${url}`);
    // window.location.href = url; // Uncomment for production
}

// ===== ADD SHAKE ANIMATION =====
const style = document.createElement('style');
style.textContent = `
    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
        20%, 40%, 60%, 80% { transform: translateX(5px); }
    }
    
    @keyframes slideInRight {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
`;
document.head.appendChild(style);

// ===== INITIALIZE =====
document.addEventListener('DOMContentLoaded', function() {
    // Load remembered data
    loadRememberedData();
    
    // Focus on email input
    emailInput.focus();
    
    // Add role selection animation
    roleRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            const label = this.closest('.role-radio');
            label.style.transform = 'scale(0.95)';
            setTimeout(() => {
                label.style.transform = 'scale(1)';
            }, 100);
        });
    });
    
    // Console log for developers
    console.log('%c🎊 Chúc Mừng Năm Mới 2026 - Bính Ngọ 🐴', 'color: #FFD700; font-size: 20px; font-weight: bold;');
    console.log('%cUTH LMS - Login System Ready', 'color: #8b5cf6; font-size: 14px;');
    console.log('%cDemo Login: admin@uth.edu.vn / 123456', 'color: #10b981; font-size: 12px;');
});

// ===== PREVENT FORM RESUBMISSION ON REFRESH =====
if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}

// ===== EXPORT FOR TESTING (Optional) =====
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        isValidEmail,
        isValidPassword,
        getSelectedRole
    };
}