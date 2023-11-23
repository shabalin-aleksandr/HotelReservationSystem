import jwt_decode from "jwt-decode";
import {api} from "../../utils/api";


export const getAdminIdFromToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Authentication required');
    }

    try {
        const decoded = jwt_decode(token);
        if (!decoded || !decoded.adminId) {
            throw new Error('AdminId ID not found');
        }
        localStorage.setItem('userId', decoded.adminId);
        return decoded.adminId;
    } catch (error) {
        console.error('Error decoding token:', error);
        throw new Error('Invalid token');
    }
}

export const getAdminDetails = async () => {
    const adminId = getAdminIdFromToken();

    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/admins/${adminId}`, { headers });
        console.log('Admin details:', response.data);
        return response.data;
    } catch(error) {
        console.error('Failed to fetch admin details:', error);
        throw error;
    }
}