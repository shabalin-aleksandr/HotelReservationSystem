import {api} from "../../utils/api";

export const getHotelDetails = async (hotelId) => {
    try {
        const response = await api.get(`/hotels/${hotelId}`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch hotel details', error);
        throw error;
    }
};

export const getAllHotels = async () => {
    try {
        const response = await api.get(`/hotels`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch hotels', error);
        throw error;
    }
};

export const createHotel = async (hotelName, country, city, address, receptionNumber) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };
    const hotelData = {
        hotelName,
        country,
        city,
        address,
        receptionNumber,
    };

    try {
        const response = await api.post('/hotels/create', hotelData, { headers });
        console.log('Hotel created:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to create hotel:', error);
        throw error;
    }
};

export const editHotel = async (hotelId, updates) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.patch(`/hotels/update/${hotelId}`, updates, { headers });
        console.log('Hotel updated', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to update hotel details', error);
        throw error;
    }
}

export const deleteHotel = async (hotelId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.delete(`/hotels/delete/${hotelId}`, { headers });
        console.log('Hotel deleted:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to delete hotel:', error);
        throw error;
    }
};

export const addRatingToHotel = async (hotelId, rating) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    const rateData = {
        rating,
    };

    try {
        const response = await api.patch(`/hotels/${hotelId}/rate`, rateData, { headers });
        console.log('Rating added successfully:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to add rating:', error);
        throw error;
    }
};
