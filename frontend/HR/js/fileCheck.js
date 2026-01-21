// File Check Utility - Kiểm tra tất cả các file đã được load đúng chưa
console.log('🔍 Đang kiểm tra các file JavaScript...');

// Kiểm tra các biến global quan trọng
const checks = {
    'apiService.js': typeof courseAPI !== 'undefined' && typeof dashboardAPI !== 'undefined' && typeof competencyAPI !== 'undefined',
    'connectionTest.js': typeof testBackendConnection !== 'undefined' && typeof getBackendConnectionStatus !== 'undefined',
    'dashboard.js': typeof loadDashboardData !== 'undefined' && typeof loadAllCourses !== 'undefined'
};

console.log('📋 Kết quả kiểm tra:');
Object.keys(checks).forEach(file => {
    const status = checks[file] ? '✅' : '❌';
    console.log(`${status} ${file}: ${checks[file] ? 'Đã load' : 'CHƯA load'}`);
});

// Kiểm tra API_BASE_URL
if (typeof API_BASE_URL !== 'undefined') {
    console.log(`✅ API_BASE_URL: ${API_BASE_URL}`);
} else {
    console.log('⚠️ API_BASE_URL chưa được định nghĩa (có thể được định nghĩa trong apiService.js)');
}

// Test kết nối nếu có thể
if (typeof testBackendConnection === 'function') {
    console.log('🧪 Đang test kết nối backend...');
    testBackendConnection().then(result => {
        console.log(`🔌 Kết nối backend: ${result ? '✅ Thành công' : '❌ Thất bại'}`);
        if (!result && typeof getBackendConnectionStatus === 'function') {
            const status = getBackendConnectionStatus();
            console.log('📝 Chi tiết lỗi:', status.error);
        }
    }).catch(err => {
        console.error('❌ Lỗi khi test kết nối:', err);
    });
} else {
    console.log('⚠️ Không thể test kết nối: testBackendConnection chưa được định nghĩa');
}
