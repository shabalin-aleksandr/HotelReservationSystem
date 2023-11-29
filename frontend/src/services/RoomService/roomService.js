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

export const addRoomToHotel = async (hotelId, roomNumber, category, pricePerNight) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    const roomData = {
        roomNumber,
        category,
        pricePerNight
    };

    try {
        const response = await api.post(`/rooms/create/${hotelId}`, roomData, { headers });
        console.log('Room created:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to create room:', error);
        throw error;
    }
}

export const editRoomInHotel = async (hotelId, roomId, updates) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.patch(`/rooms/update/${hotelId}/${roomId}`, updates, { headers });
        console.log('Room updated', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to update room:', error);
        throw error;
    }
}

export const deleteRoomFromHotel = async (hotelId, roomId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.delete(`/rooms/delete/${hotelId}/${roomId}`, { headers });
        console.log(`Room ${roomId} has been successfully deleted from hotel ${hotelId}`, response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to delete room', error);
        throw error;
    }
}
