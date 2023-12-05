import React, { useState } from 'react';
import { Box, Flex, Button, Text, Image } from '@chakra-ui/react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import DefaultRoomImage from "../../images/room.png";
import {
    createReservation,
    getAllReservationsInRoom,
} from '../../services/ReservationService/reservationService';

const ViewReservation = ({ room, handleCloseModal, pricePerNight, roomPhotos }) => {
    const [reservationFrom, setReservationFrom] = useState(null);
    const [reservationTo, setReservationTo] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [totalPrice, setTotalPrice] = useState(0);

    const handleConfirmReservation = async () => {
        try {
            // Check if the selected date range is valid
            if (!reservationFrom || !reservationTo || reservationTo <= reservationFrom) {
                setErrorMessage('Please select a valid date range.');
                setSuccessMessage(null);
                return;
            }

            // Check if the selected date range overlaps with existing reservations
            const existingReservations = await getAllReservationsInRoom(
                room.hotelId,
                room.roomId
            );
            const isOverlap = existingReservations.some((reservation) => {
                return (
                    (reservationFrom >= new Date(reservation.reservationFrom) &&
                        reservationFrom <= new Date(reservation.reservationTo)) ||
                    (reservationTo >= new Date(reservation.reservationFrom) &&
                        reservationTo <= new Date(reservation.reservationTo))
                );
            });

            if (isOverlap) {
                setErrorMessage(
                    'The room is already reserved for the selected date range. Please choose different dates.'
                );
                setSuccessMessage(null);
                return;
            }

            // Calculate the total number of nights
            const numberOfNights = Math.ceil((reservationTo - reservationFrom) / (1000 * 60 * 60 * 24));

            // Calculate the total price
            const total = numberOfNights * pricePerNight;
            setTotalPrice(total);

            // Example reservation data (modify this according to your needs)
            const createReservationDto = {
                reservationFrom,
                reservationTo,
                totalPrice: total,
                // Other reservation data as needed
            };

            // Call the createReservation function
            await createReservation(room.hotelId, room.roomId, createReservationDto);

            // Additional actions after successful reservation (e.g., closing the modal)
            setErrorMessage(null);
            setSuccessMessage('Reservation confirmed!');

            setTimeout(() => {
                handleCloseModal();
            }, 2000); // Close the modal after 2 seconds (adjust the duration as needed)
        } catch (error) {
            if (
                error.response &&
                error.response.status === 401 &&
                error.response.data.error === 'Token has expired'
            ) {
                // Handle token expiration (e.g., prompt the user to reauthenticate)
                console.log('Token has expired. Please log in again.');
            } else {
                // Handle other reservation creation errors
                console.error('Error creating reservation:', error);
                setErrorMessage('Error creating reservation. Please try again.');
                setSuccessMessage(null);
            }
        }
    };

    const handleToDateChange = (date) => {
        // Update reservationTo and recalculate total price
        setReservationTo(date);

        // Check if the selected date range is valid
        if (!reservationFrom || !date || date <= reservationFrom) {
            setErrorMessage('Please select a valid date range.');
            setSuccessMessage(null);
            setTotalPrice(0);
            return;
        }

        // Calculate the total number of nights
        const numberOfNights = Math.ceil((date - reservationFrom) / (1000 * 60 * 60 * 24));

        // Calculate the total price
        const total = numberOfNights * pricePerNight;
        setTotalPrice(total);

        // Clear error message if any
        setErrorMessage(null);
    };

    return (
        <Flex direction="column" padding="4">
            {/* Display the selected room photo or a default photo */}
            <Image
                src={roomPhotos && roomPhotos.length > 0 ? roomPhotos[0].url : DefaultRoomImage}
                alt={`Image of ${room.roomNumber}`}
                height="400px"
                width="100%"
                objectFit="contain"

                mb="4"
            />

            <Text fontSize="xl" fontWeight="bold" mb={2}>
                Room №{room.roomNumber}
            </Text>

            <Box mb="2">
                <DatePicker
                    selected={reservationFrom}
                    onChange={(date) => setReservationFrom(date)}
                    placeholderText="From Date"
                    style={{ width: '100%', border: 'none', outline: 'none' }}
                />
            </Box>
            <Box mb="2">
                <DatePicker
                    selected={reservationTo}
                    onChange={handleToDateChange}
                    placeholderText="To Date"
                    style={{ width: '100%', border: 'none', outline: 'none' }}
                />
            </Box>
            <Text fontSize="medium" fontWeight="bold" mb={2}>
                Total Price: ${totalPrice}
            </Text>
            {errorMessage && (
                <Text color="red" mb={2}>
                    {errorMessage}
                </Text>
            )}
            {successMessage && (
                <Text color="green" mb={2}>
                    {successMessage}
                </Text>
            )}
            <Button
                fontSize="sm"
                rounded="full"
                bg="blue.400"
                color="white"
                boxShadow="0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)"
                _hover={{
                    bg: 'blue.500',
                }}
                _focus={{
                    bg: 'blue.500',
                }}
                onClick={handleConfirmReservation}
                mt={4}
            >
                Confirm Reservation
            </Button>
        </Flex>
    );
};

// DefaultRoomImage should be imported or defined based on your project structure


export default ViewReservation;
