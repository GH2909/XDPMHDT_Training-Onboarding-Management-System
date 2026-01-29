// ===== TOGGLE PASSWORD VISIBILITY =====
const togglePasswordBtn = document.getElementById("togglePasswordBtn");
const passwordInput = document.getElementById("password");

if (togglePasswordBtn && passwordInput) {
    togglePasswordBtn.addEventListener("click", function (e) {
        e.preventDefault();

        // Toggle input type
        const isPassword = passwordInput.type === "password";
        passwordInput.type = isPassword ? "text" : "password";

        // Toggle icon
        const icon = togglePasswordBtn.querySelector("i");
        if (icon) {
            if (isPassword) {
                // Thay đổi icon từ eye thành eye-slash
                icon.classList.remove("fa-eye");
                icon.classList.add("fa-eye-slash");
            } else {
                // Thay đổi icon từ eye-slash thành eye
                icon.classList.remove("fa-eye-slash");
                icon.classList.add("fa-eye");
            }
        }
    });
}

// ===== LOGIN FORM =====
const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (e) {
    e.preventDefault();
    e.stopPropagation();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.querySelector("input[name='role']:checked").value;

    // Reset lỗi
    document.getElementById("emailError").innerText = "";
    document.getElementById("passwordError").innerText = "";

    // Validate email
    if (!email) {
        document.getElementById("emailError").innerText = "Email không được để trống";
        return;
    }

    // Validate password
    if (!password) {
        document.getElementById("passwordError").innerText = "Mật khẩu không được để trống";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/training/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error("Email hoặc mật khẩu không đúng");
        } else {
            const data = await response.json();

            // Lưu JWT
            localStorage.setItem("access_token", data.token);
            localStorage.setItem("role", role);

            alert("Đăng nhập thành công");

            // Điều hướng theo role
            switch (role) {
                case "employee":
                    window.location.href = "/employee/dashboard.html";
                    break;
                case "trainer":
                    window.location.href = "/trainer/dashboard/index.html";
                    break;
                case "hr":
                    window.location.href = "/HR/dashboard.html";
                    break;
            }
        }

    } catch (err) {
        document.getElementById("passwordError").innerText = err.message;
    }
}); 