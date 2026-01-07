// ===== AVATAR PREVIEW =====
function previewAvatar(event) {
    const file = event.target.files[0];
    if (file) {
        // Validate file size (max 5MB)
        if (file.size > 5 * 1024 * 1024) {
            alert('Kích thước file tối đa 5MB');
            return;
        }

        // Validate file type
        const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'];
        if (!validTypes.includes(file.type)) {
            alert('Chỉ hỗ trợ các định dạng: JPG, PNG, GIF, WebP');
            return;
        }

        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('avatarImg').src = e.target.result;
            // TODO: Upload file lên server khi BE xong
            // uploadAvatar(file);
        }
        reader.readAsDataURL(file);
    }
}

// ===== SAVE PROFILE =====
function saveProfile() {
    const fullName = document.getElementById('fullName').value;
    const phone = document.getElementById('phone').value;

    // Validation
    if (!fullName.trim()) {
        alert('Vui lòng nhập họ và tên');
        return;
    }

    if (!phone.trim()) {
        alert('Vui lòng nhập số điện thoại');
        return;
    }

    // Phone validation (Vietnam format)
    const phoneRegex = /^(0[1-9][0-9]{8})$/;
    if (!phoneRegex.test(phone.replace(/\s/g, ''))) {
        alert('Số điện thoại không hợp lệ');
        return;
    }

    // TODO: Send data to API
    // const profileData = {
    //     fullName: fullName,
    //     phone: phone
    // };
    // fetch('/api/profile/update', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify(profileData)
    // })
    // .then(res => res.json())
    // .then(data => {
    //     if (data.success) {
    //         alert('Cập nhật thông tin thành công!');
    //     }
    // })
    // .catch(err => console.error(err));

    alert('Cập nhật thông tin thành công!');
}

// ===== LOAD PROFILE DATA =====
document.addEventListener('DOMContentLoaded', function () {
    // TODO: Load data từ API khi BE xong
    // fetch('/api/profile')
    //     .then(res => res.json())
    //     .then(data => {
    //         document.getElementById('fullName').value = data.fullName;
    //         document.getElementById('phone').value = data.phone;
    //         document.getElementById('avatarImg').src = data.avatarUrl;
    //         // Load achievements
    //     })
    //     .catch(err => console.error(err));
}); 