import {api} from "../../utils/api";

export const getAmenityByRoomId_HotelId = async (hotelId,roomId) => {
    try {
        const response = await api.get(`/amenities/${hotelId}/${roomId}`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch rooms in hotel', error);
        throw error;
    }
};

export const createAmenity = async (hotelId, roomId, amenityName, description) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    const amenityData = {
        amenityName,
        description
    };
    try {
        const response = await api.post(`/amenities/create/${hotelId}/${roomId}`, amenityData,{headers});
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to create amenity', error);
        throw error;
    }
};
