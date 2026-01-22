const API_BASE = "http://localhost:8080/training";

function getAuthHeader() {
    const token = localStorage.getItem("access_token");
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
    };
}