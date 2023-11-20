import {api} from "../../utils/api";
import jwt_decode from "jwt-decode";

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
};

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
};

export const getHotelIdFromToken = () => {
    const token = localStorage.getItem('token');
    if (!token) {
        throw new Error('Authentication required');
    }

    try {
        const decoded = jwt_decode(token);
        if (!decoded || !decoded.id) {
            throw new Error('Hotel ID not found');
        }
        localStorage.setItem('hotelId', decoded.id);
        return decoded.id;
    } catch (error) {
        console.error('Error decoding token:', error);
        throw new Error('Invalid token');
    }
};