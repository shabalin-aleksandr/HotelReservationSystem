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
};
export const findAvailableRoomsInHotelForDateRange = async (hotelId, fromDate, toDate) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/rooms/${hotelId}/available`, {
            headers,
            params: {
                fromDate: fromDate.toISOString().split('T')[0], // Format date as yyyy-MM-dd
                toDate: toDate.toISOString().split('T')[0],   // Format date as yyyy-MM-dd
            },
        });

        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch available rooms', error);
        throw error;
    }
};
export const getAllRoomsByHotelId = async (hotelId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/rooms/${hotelId}`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch rooms in hotel', error);
        throw error;
    }
};
