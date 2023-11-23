import {api} from "../../utils/api";
import jwt_decode from "jwt-decode";

export const getUserIdFromToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Authentication required');
    }

    try {
        const decoded = jwt_decode(token);
        if (!decoded || !decoded.userId) {
            throw new Error('User ID not found');
        }
        localStorage.setItem('userId', decoded.userId);
        return decoded.userId;
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
        const response = await api.get(`/users/${userId}`, { headers });
        console.log('User details:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch user details:', error);
        throw error;
    }
};

export const updateUserDetails = async (userId, updates) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };
    try {
        const response = await api.patch(`/users/update/${userId}`, updates, { headers });
        console.log(response.data)
        return response.data;
    } catch (error) {
        console.error('Failed to fetch user details', error);
        throw error;
    }
};

export const updateUserPassword = async (userId, oldPassword, newPassword) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };
    const data = { oldPassword, newPassword };
    try {
        const response = await api.put(`/users/${userId}/password`, data, { headers });
        return response.data;
    } catch (error) {
        console.error('Failed to update password', error);
        throw error;
    }
};

export const deleteOwnAccount = async () => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };
    try {
        const response = await api.delete(`/users/deleteAccount`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to delete account', error);
        throw error;
    }
}

