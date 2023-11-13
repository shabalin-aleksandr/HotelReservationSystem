import {api} from "../../utils/api";

export const getHotelDetails = async (hotelId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/hotels/${hotelId}`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch hotel details', error);
        throw error;
    }
}