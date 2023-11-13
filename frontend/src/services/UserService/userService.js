import {api} from "../../utils/api";

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

