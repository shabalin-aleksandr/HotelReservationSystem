import {api} from "../../utils/api";

export const getRoomDetails = async (hotelId, roomId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/rooms/${hotelId}/${roomId}`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch room details', error);
        throw error;
    }
}
