import {api} from "../../utils/api";

export const getAmenityByRoomId_HotelId = async (hotelId,roomId) => {
    // const headers = {
    //     'Authorization': `Bearer ${localStorage.getItem('token')}`,
    // };

    try {
        const response = await api.get(`/amenities/${hotelId}/${roomId}`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch rooms in hotel', error);
        throw error;
    }
};