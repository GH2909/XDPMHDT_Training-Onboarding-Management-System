// ===== PASSWORD VALIDATION RULES =====
const passwordRules = {
    minLength: { regex: /.{8,}/, id: 'req1' },
    uppercase: { regex: /[A-Z]/, id: 'req2' },
    lowercase: { regex: /[a-z]/, id: 'req3' },
    numbers: { regex: /[0-9]/, id: 'req4' },
    special: { regex: /[!@#$%^&*(),.?":{}|<>]/, id: 'req5' }
};

// ===== TOGGLE PASSWORD VISIBILITY =====
function togglePassword(fieldId) {
    const input = document.getElementById(fieldId);
    const button = event.target.closest('.toggle-password');
    const icon = button.querySelector('i');

    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// ===== CHECK PASSWORD STRENGTH =====
function checkPasswordStrength() {
    const newPassword = document.getElementById('newPassword').value;
    let strength = 0;
    let strengthColor = '#ef4444'; // Red
    let strengthText = 'Yếu';

    // Count met requirements
    for (let rule in passwordRules) {
        if (passwordRules[rule].regex.test(newPassword)) {
            strength++;
            document.getElementById(passwordRules[rule].id).classList.add('met');
        } else {
            document.getElementById(passwordRules[rule].id).classList.remove('met');
        }
    }

    // Update strength bar
    const strengthFill = document.getElementById('strengthFill');
    if (strength <= 2) {
        strengthColor = '#ef4444'; // Red - Yếu
        strengthText = 'Yếu';
        strengthFill.style.width = '20%';
    } else if (strength === 3) {
        strengthColor = '#f59e0b'; // Orange - Trung bình
        strengthText = 'Trung bình';
        strengthFill.style.width = '50%';
    } else if (strength === 4) {
        strengthColor = '#84cc16'; // Lime - Mạnh
        strengthText = 'Mạnh';
        strengthFill.style.width = '75%';
    } else if (strength === 5) {
        strengthColor = '#10b981'; // Green - Rất mạnh
        strengthText = 'Rất mạnh';
        strengthFill.style.width = '100%';
    }

    strengthFill.style.background = strengthColor;
    document.getElementById('strengthText').textContent = `Độ mạnh: ${strengthText}`;
}

// ===== VALIDATE PASSWORD FORM =====
function validatePasswordForm() {
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    let isValid = true;

    // Clear previous errors
    document.getElementById('currentPasswordError').textContent = '';
    document.getElementById('newPasswordError').textContent = '';
    document.getElementById('confirmPasswordError').textContent = '';

    // Validate current password
    if (!currentPassword.trim()) {
        document.getElementById('currentPasswordError').textContent = 'Vui lòng nhập mật khẩu hiện tại';
        document.getElementById('currentPasswordError').classList.add('show');
        isValid = false;
    }

    // Validate new password
    if (!newPassword.trim()) {
        document.getElementById('newPasswordError').textContent = 'Vui lòng nhập mật khẩu mới';
        document.getElementById('newPasswordError').classList.add('show');
        isValid = false;
    } else if (newPassword.length < 8) {
        document.getElementById('newPasswordError').textContent = 'Mật khẩu phải tối thiểu 8 ký tự';
        document.getElementById('newPasswordError').classList.add('show');
        isValid = false;
    } else {
        // Check all requirements
        let unmetRequirements = [];
        for (let rule in passwordRules) {
            if (!passwordRules[rule].regex.test(newPassword)) {
                unmetRequirements.push(rule);
            }
        }
        if (unmetRequirements.length > 0) {
            document.getElementById('newPasswordError').textContent = 'Mật khẩu không đạt yêu cầu độ mạnh';
            document.getElementById('newPasswordError').classList.add('show');
            isValid = false;
        }
    }

    // Validate confirm password
    if (!confirmPassword.trim()) {
        document.getElementById('confirmPasswordError').textContent = 'Vui lòng xác nhận mật khẩu';
        document.getElementById('confirmPasswordError').classList.add('show');
        isValid = false;
    } else if (newPassword !== confirmPassword) {
        document.getElementById('confirmPasswordError').textContent = 'Mật khẩu xác nhận không khớp';
        document.getElementById('confirmPasswordError').classList.add('show');
        isValid = false;
    }

    return isValid;
}

// ===== CHANGE PASSWORD =====
function changePassword() {
    if (!validatePasswordForm()) {
        return;
    }

    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;

    // TODO: Send request to API
    // const passwordData = {
    //     currentPassword: currentPassword,
    //     newPassword: newPassword
    // };
    // fetch('/api/profile/change-password', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify(passwordData)
    // })
    // .then(res => res.json())
    // .then(data => {
    //     if (data.success) {
    //         alert('Đổi mật khẩu thành công!');
    //         document.getElementById('passwordForm').reset();
    //         // Reset strength indicators
    //         document.querySelectorAll('.requirement').forEach(el => el.classList.remove('met'));
    //     } else {
    //         alert('Mật khẩu hiện tại không đúng');
    //     }
    // })
    // .catch(err => console.error(err));

    alert('Đổi mật khẩu thành công!');
    document.getElementById('passwordForm').reset();
    // Reset strength indicators
    document.querySelectorAll('.requirement').forEach(el => el.classList.remove('met'));
}

// ===== EVENT LISTENERS =====
document.addEventListener('DOMContentLoaded', function () {
    // Real-time password strength checking
    const newPasswordInput = document.getElementById('newPassword');
    if (newPasswordInput) {
        newPasswordInput.addEventListener('input', checkPasswordStrength);
    }

    // Clear errors on input
    document.getElementById('currentPassword')?.addEventListener('input', function () {
        document.getElementById('currentPasswordError').classList.remove('show');
    });

    document.getElementById('newPassword')?.addEventListener('input', function () {
        document.getElementById('newPasswordError').classList.remove('show');
    });

    document.getElementById('confirmPassword')?.addEventListener('input', function () {
        document.getElementById('confirmPasswordError').classList.remove('show');
    });
});