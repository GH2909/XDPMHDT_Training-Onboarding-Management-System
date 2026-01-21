// API Service for Course Management
// Base URL configuration
const API_BASE_URL = 'http://localhost:8080/training';

// Export API_BASE_URL để các file khác có thể truy cập
if (typeof window !== 'undefined') {
    window.API_BASE_URL = API_BASE_URL;
}

// Get JWT token from localStorage
function getAuthToken() {
    return localStorage.getItem('token');
}

// Set JWT token to localStorage
function setAuthToken(token) {
    localStorage.setItem('token', token);
}

// Remove JWT token
function removeAuthToken() {
    localStorage.removeItem('token');
}

// Make API request with authentication
async function apiRequest(url, options = {}) {
    const token = getAuthToken();
    
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Add Authorization header if token exists
    if (token) {
        defaultOptions.headers['Authorization'] = `Bearer ${token}`;
    }

    // Merge with provided options
    const finalOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...(options.headers || {}),
        },
    };

    try {
        const response = await fetch(`${API_BASE_URL}${url}`, finalOptions);
        
        // Handle 403 Forbidden - Backend chặn request
        if (response.status === 403) {
            const responseText = await response.clone().text();
            let errorMessage = 'Backend từ chối truy cập (403 Forbidden).';
            
            try {
                const errorData = JSON.parse(responseText);
                errorMessage = errorData.message || errorData.error || errorMessage;
            } catch (e) {
                // Not JSON, use default message
            }
            
            throw new Error(`${errorMessage}\n\nCó thể do:\n1. Backend yêu cầu authentication (cần đăng nhập)\n2. Backend chặn CORS từ origin này\n3. Backend có security policy chặn request\n\nVui lòng kiểm tra:\n- Backend có yêu cầu token không?\n- CORS đã được cấu hình đúng chưa?\n- Backend có cho phép origin http://localhost:8000 không?`);
        }
        
        // Handle 401 Unauthorized - disabled redirect
        if (response.status === 401) {
            const responseText = await response.clone().text();
            let errorMessage = 'Chưa đăng nhập hoặc token không hợp lệ (401 Unauthorized).';
            
            try {
                const errorData = JSON.parse(responseText);
                errorMessage = errorData.message || errorData.error || errorMessage;
            } catch (e) {
                // Not JSON, use default message
            }
            
            // Không redirect tự động, chỉ throw error
            throw new Error(`${errorMessage}\n\nVui lòng đăng nhập để tiếp tục.`);
        }

        // Try to parse response as JSON first
        let data;
        const contentType = response.headers.get('content-type') || '';
        
        try {
            // Try to get response as text first to check what we got
            const responseText = await response.clone().text();
            
            // Check if it's JSON
            if (contentType.includes('application/json') || responseText.trim().startsWith('{') || responseText.trim().startsWith('[')) {
                try {
                    data = JSON.parse(responseText);
                } catch (parseError) {
                    // If JSON parse fails but looks like JSON, throw error
                    if (responseText.trim().startsWith('{') || responseText.trim().startsWith('[')) {
                        throw new Error(`Invalid JSON response from server. Status: ${response.status}`);
                    }
                    // Otherwise, it might be HTML error page
                    throw new Error(`Backend trả về HTML thay vì JSON. Có thể backend chưa chạy hoặc endpoint không tồn tại. Status: ${response.status}`);
                }
            } else {
                // Not JSON, likely HTML error page
                if (response.status === 404) {
                    throw new Error(`Endpoint không tồn tại (404). Kiểm tra URL: ${API_BASE_URL}${url}`);
                } else if (response.status >= 500) {
                    throw new Error(`Lỗi server (${response.status}). Backend có thể đang gặp sự cố.`);
                } else if (response.status === 0 || !response.ok) {
                    throw new Error(`Không thể kết nối đến backend. Kiểm tra:\n1. Backend đang chạy tại ${API_BASE_URL}\n2. CORS đã được cấu hình\n3. Firewall không chặn port 8080`);
                } else {
                    throw new Error(`Backend trả về dữ liệu không phải JSON. Status: ${response.status}`);
                }
            }
        } catch (textError) {
            // If we already have a meaningful error, throw it
            if (textError.message && !textError.message.includes('JSON')) {
                throw textError;
            }
            // Otherwise, try to parse as JSON anyway
            try {
                data = await response.json();
            } catch (jsonError) {
                throw new Error(`Không thể đọc phản hồi từ backend. Vui lòng kiểm tra:\n1. Backend đang chạy tại ${API_BASE_URL}\n2. Endpoint ${url} có tồn tại không\n3. Kiểm tra console để xem lỗi chi tiết`);
            }
        }

        if (!response.ok) {
            throw new Error(data.message || data.error || `HTTP error! status: ${response.status}`);
        }

        return data;
    } catch (error) {
        console.error('API Request Error:', error);
        console.error('URL:', `${API_BASE_URL}${url}`);
        console.error('Options:', finalOptions);
        
        // Handle network errors (CORS, connection refused, etc.)
        if (error.name === 'TypeError' && (error.message.includes('fetch') || error.message.includes('Failed to fetch'))) {
            throw new Error(`Không thể kết nối đến backend server.\n\nVui lòng kiểm tra:\n1. Backend đang chạy tại ${API_BASE_URL}\n2. CORS đã được cấu hình đúng trên backend\n3. Firewall không chặn port 8080\n4. Kiểm tra console (F12) để xem lỗi chi tiết`);
        }
        
        // Re-throw with user-friendly message
        if (error.message) {
            throw error;
        }
        
        throw new Error('Đã xảy ra lỗi khi gọi API: ' + (error.message || 'Lỗi không xác định'));
    }
}

// Course API Service
const courseAPI = {
    // Get all courses
    getAllCourses: async () => {
        const response = await apiRequest('/hr/course', {
            method: 'GET',
        });
        return response.result || [];
    },

    // Get course by ID
    getCourseById: async (id) => {
        const response = await apiRequest(`/hr/course/${id}`, {
            method: 'GET',
        });
        return response.result;
    },

    // Create new course
    createCourse: async (courseData) => {
        const response = await apiRequest('/hr/course', {
            method: 'POST',
            body: JSON.stringify(courseData),
        });
        return response.result;
    },

    // Update course
    updateCourse: async (id, courseData) => {
        const response = await apiRequest(`/hr/course/${id}`, {
            method: 'PUT',
            body: JSON.stringify(courseData),
        });
        return response.result;
    },

    // Delete course
    deleteCourse: async (id) => {
        await apiRequest(`/hr/course/${id}`, {
            method: 'DELETE',
        });
    },
};

// Authentication API Service
const authAPI = {
    // Login
    login: async (username, password) => {
        const response = await apiRequest('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password }),
        });
        
        // Save token if login successful
        if (response.token) {
            setAuthToken(response.token);
        }
        
        return response;
    },

    // Logout
    logout: () => {
        removeAuthToken();
        window.location.href = 'login.html'; // Adjust path if needed
    },
};

// Dashboard API Service
const dashboardAPI = {
    // Get dashboard statistics
    getDashboardStats: async () => {
        const response = await apiRequest('/hr/dashboard', {
            method: 'GET',
        });
        return response.result || {};
    },

    // Get recent courses
    getRecentCourses: async (limit = 5) => {
        const response = await apiRequest(`/hr/dashboard/recent-courses?limit=${limit}`, {
            method: 'GET',
        });
        return response.result || { courses: [], count: 0 };
    },
};

// Competency Framework API Service
const competencyAPI = {
    // Get all competency frameworks
    getAllCompetencies: async () => {
        const response = await apiRequest('/hr/competency-framework', {
            method: 'GET',
        });
        return response.result || [];
    },

    // Get competency framework by ID
    getCompetencyById: async (id) => {
        const response = await apiRequest(`/hr/competency-framework/${id}`, {
            method: 'GET',
        });
        return response.result;
    },

    // Create new competency framework
    createCompetency: async (data) => {
        const response = await apiRequest('/hr/competency-framework', {
            method: 'POST',
            body: JSON.stringify(data),
        });
        return response.result;
    },

    // Update competency framework
    updateCompetency: async (id, data) => {
        const response = await apiRequest(`/hr/competency-framework/${id}`, {
            method: 'PUT',
            body: JSON.stringify(data),
        });
        return response.result;
    },

    // Delete competency framework
    deleteCompetency: async (id) => {
        await apiRequest(`/hr/competency-framework/${id}`, {
            method: 'DELETE',
        });
    },
};

// Export for use in other files
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { courseAPI, authAPI, dashboardAPI, competencyAPI, getAuthToken, setAuthToken, removeAuthToken };
}
