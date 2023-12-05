import {api} from "../../utils/api";

export const getReservationDetails = async (userId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api
            .get(
                `/reservations/users/${userId}`,
                { headers }
            )
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch reservation details', error);
        throw error;
    }
}

export const getAllReservationsInHotel = async (hotelId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.get(`/reservations/${hotelId}`, { headers })
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error(`Failed to fetch reservations for hotel: ${hotelId}`, error);
        throw error;
    }
}

export const deleteSingleReservation = async (hotelId, roomId, reservationId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response =
            await api.delete(
                `/reservations/${hotelId}/rooms/${roomId}/reservations/${reservationId}`,
                { headers}
            );
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error(`Failed to delete reservation: ${reservationId}`, error);
        throw error;
    }
}

export const createReservation = async (hotelId, roomId, createReservationDto) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };

    try {
        const response = await api.post(
            `/reservations/create/${hotelId}/rooms/${roomId}`,
            createReservationDto,
            { headers }
        );
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to create reservation', error);
        throw error;
    }
}

export const getAllReservationsInRoom = async (hotelId, roomId) => {
    const headers = {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
    };
    try {
        const response = await api.get(`/reservations/${hotelId}/rooms/${roomId}`, { headers });
        console.log(response.data);
        return response.data;
    } catch (error) {
        console.error(`Failed to fetch reservations for room: ${roomId}`, error);
        throw error;
    }
}

