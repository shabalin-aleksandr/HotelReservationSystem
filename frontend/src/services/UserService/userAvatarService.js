import jwt_decode from "jwt-decode";
import {api} from "../../utils/api";

const getUserIdFromToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Authentication required');
    }

    try {
        const decoded = jwt_decode(token);
        if (!decoded || !decoded.id) {
            throw new Error('User ID not found');
        }
        localStorage.setItem('userId', decoded.id);
        return decoded.id;
    } catch (error) {
        console.error('Error decoding token:', error);
        throw new Error('Invalid token');
    }
};

export const getUserDetails = async () => {
    const userId = getUserIdFromToken();

    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`users/${userId}`, { headers });
        console.log('User details:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch user details:', error);
        throw error;
    }
};


export const uploadAvatar = async (file) => {
    const userId = getUserIdFromToken();
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'multipart/form-data'
    };
    const formData = new FormData();
    formData.append('avatar', file);

    try {
        const response = await api.post(`/users/${userId}/avatar`, formData, { headers });
        return response.data;
    } catch (error) {
        console.error('Failed to upload avatar:', error);
        throw error;
    }
};

export const deleteAvatar = async () => {
    const userId = getUserIdFromToken();
    const token = localStorage.getItem('token');
    const headers = {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'multipart/form-data'
    };

    try {
        const response = await api.delete(`/users/${userId}/avatar/delete`, { headers });
        return response.data;
    } catch (error) {
        console.error('Failed to delete avatar:', error);
        throw error;
    }
};