// Connection Test Utility
// Kiểm tra kết nối backend trước khi load dữ liệu

let backendConnectionStatus = {
    isConnected: false,
    lastCheck: null,
    error: null
};

// Test backend connection
async function testBackendConnection() {
    const API_BASE_URL = 'http://localhost:8080/training';
    
    try {
        // Tạo AbortController cho timeout
        const controller = new AbortController();
        const timeoutId = setTimeout(() => controller.abort(), 5000); // 5 giây timeout
        
        // Test với endpoint đơn giản nhất
        const response = await fetch(`${API_BASE_URL}/hr/course`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            signal: controller.signal
        });
        
        clearTimeout(timeoutId);
        
        // Kiểm tra response
        const contentType = response.headers.get('content-type') || '';
        
        // Xử lý các status code khác nhau
        if (response.status === 403) {
            backendConnectionStatus.isConnected = false;
            backendConnectionStatus.error = `Backend từ chối truy cập (403). Có thể cần authentication hoặc CORS chưa được cấu hình đúng.`;
            return false;
        } else if (response.status === 401) {
            backendConnectionStatus.isConnected = false;
            backendConnectionStatus.error = `Chưa đăng nhập (401). Backend yêu cầu authentication.`;
            return false;
        } else if (response.status === 404) {
            backendConnectionStatus.isConnected = false;
            backendConnectionStatus.error = `Endpoint không tồn tại (404). Kiểm tra URL backend.`;
            return false;
        } else if (response.ok || contentType.includes('application/json')) {
            backendConnectionStatus.isConnected = true;
            backendConnectionStatus.lastCheck = new Date();
            backendConnectionStatus.error = null;
            return true;
        } else {
            backendConnectionStatus.isConnected = false;
            backendConnectionStatus.error = `Backend trả về status ${response.status}`;
            return false;
        }
    } catch (error) {
        backendConnectionStatus.isConnected = false;
        backendConnectionStatus.lastCheck = new Date();
        
        if (error.name === 'AbortError') {
            backendConnectionStatus.error = 'Timeout: Backend không phản hồi sau 5 giây. Kiểm tra backend có đang chạy không.';
        } else if (error.name === 'TypeError' && (error.message.includes('fetch') || error.message.includes('Failed to fetch'))) {
            backendConnectionStatus.error = 'Không thể kết nối đến backend. Có thể:\n1. Backend chưa chạy\n2. CORS chưa được cấu hình\n3. Firewall chặn port 8080';
        } else {
            backendConnectionStatus.error = error.message || 'Lỗi không xác định';
        }
        
        return false;
    }
}

// Get connection status
function getBackendConnectionStatus() {
    return backendConnectionStatus;
}

// Export for use in other files
if (typeof window !== 'undefined') {
    window.testBackendConnection = testBackendConnection;
    window.getBackendConnectionStatus = getBackendConnectionStatus;
}
