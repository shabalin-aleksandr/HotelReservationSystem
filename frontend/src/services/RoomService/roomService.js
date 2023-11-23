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
    try {
        const response = await api.get(`/rooms/${hotelId}/available`, {
            params: {
                fromDate: fromDate.toISOString().split('T')[0],
                toDate: toDate.toISOString().split('T')[0],
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
    try {
        const response = await api.get(`/rooms/${hotelId}`);
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch rooms in hotel', error);
        throw error;
    }
};


