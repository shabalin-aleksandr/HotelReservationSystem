import {api} from "../../utils/api";

export const getReservationDetails = async (userId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api
            .get(
                `reservations/users/${userId}`,
                { headers }
            )
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch reservation details', error);
        throw error;
    }
}