const loginForm = document.getElementById("loginForm");

loginForm.addEventListener("submit", async function (e) {
    e.preventDefault();
    e.stopPropagation();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();
    const role = document.querySelector("input[name='role']:checked").value;

    // reset lỗi
    document.getElementById("emailError").innerText = "";
    document.getElementById("passwordError").innerText = "";

    if (!email) {
        document.getElementById("emailError").innerText = "Email không được để trống";
        return;
    }

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
        }

        const data = await response.json();

        // Lưu JWT
        localStorage.setItem("access_token", data.token);
        localStorage.setItem("role", role);

        alert("Đăng nhập thành công 🎉");

        // Điều hướng theo role
        switch (role) {
            case "employee":
                window.location.href = "/employee/index.html";
                break;
            case "trainer":
                window.location.href = "/trainer/index.html";
                break;
            case "hr":
                window.location.href = "/HR/dashboard.html";
                break;
        }

    } catch (err) {
        document.getElementById("passwordError").innerText = err.message;
    }
});
