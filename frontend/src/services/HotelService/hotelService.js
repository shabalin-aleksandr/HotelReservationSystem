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

export const getAllHotels = async () => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/hotels`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch hotels', error);
        throw error;
    }
}