// Test Connection Script
// Chạy script này trong Developer Console để kiểm tra kết nối backend

async function testBackendConnection() {
    console.log('🔍 Đang kiểm tra kết nối backend...\n');
    
    const API_BASE_URL = 'http://localhost:8080/training';
    
    // Test 1: Kiểm tra backend có đang chạy không
    console.log('Test 1: Kiểm tra backend server...');
    try {
        const response = await fetch(`${API_BASE_URL}/hr/course`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        if (response.ok) {
            console.log('✅ Backend đang chạy và phản hồi!');
            const data = await response.json();
            console.log('📊 Response:', data);
        } else {
            console.log(`⚠️ Backend phản hồi nhưng có lỗi: ${response.status} ${response.statusText}`);
        }
    } catch (error) {
        console.error('❌ Không thể kết nối đến backend!');
        console.error('Lỗi:', error.message);
        console.log('\n💡 Kiểm tra:');
        console.log('1. Backend có đang chạy trên http://localhost:8080 không?');
        console.log('2. CORS đã được cấu hình đúng chưa?');
        console.log('3. URL trong apiService.js có đúng không?');
        return false;
    }
    
    // Test 2: Kiểm tra CORS
    console.log('\nTest 2: Kiểm tra CORS...');
    try {
        const response = await fetch(`${API_BASE_URL}/hr/course`, {
            method: 'OPTIONS',
            headers: {
                'Origin': window.location.origin,
                'Access-Control-Request-Method': 'GET'
            }
        });
        console.log('✅ CORS preflight request thành công');
    } catch (error) {
        console.warn('⚠️ CORS preflight có thể có vấn đề:', error.message);
    }
    
    // Test 3: Kiểm tra Dashboard API
    console.log('\nTest 3: Kiểm tra Dashboard API...');
    try {
        const response = await fetch(`${API_BASE_URL}/hr/dashboard`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        
        if (response.ok) {
            const data = await response.json();
            console.log('✅ Dashboard API hoạt động!');
            console.log('📊 Data:', data);
        } else {
            console.log(`⚠️ Dashboard API trả về: ${response.status}`);
        }
    } catch (error) {
        console.warn('⚠️ Dashboard API có vấn đề:', error.message);
    }
    
    // Test 4: Kiểm tra token (nếu có)
    console.log('\nTest 4: Kiểm tra authentication token...');
    const token = localStorage.getItem('token');
    if (token) {
        console.log('✅ Token đã được lưu trong localStorage');
        console.log('🔑 Token (một phần):', token.substring(0, 20) + '...');
    } else {
        console.log('ℹ️ Chưa có token (có thể cần đăng nhập)');
    }
    
    console.log('\n✅ Hoàn thành kiểm tra!');
    return true;
}

// Export để sử dụng trong console
if (typeof window !== 'undefined') {
    window.testBackendConnection = testBackendConnection;
    console.log('💡 Chạy testBackendConnection() trong console để kiểm tra kết nối');
}
