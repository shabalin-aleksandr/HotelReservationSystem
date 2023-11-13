import {api} from "../../utils/api";
import {getUserIdFromToken} from "./userService";

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