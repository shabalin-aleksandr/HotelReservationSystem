// ViewReservation.jsx
import React, { useState, useEffect } from 'react';
import {
    Box,
    Text,
    Button,
    Flex,
    Image,
} from '@chakra-ui/react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import DefaultRoomImage from "../../images/room.png";
import { createReservation } from '../../services/ReservationService/reservationService';

const ViewReservation = ({ room, handleCloseModal, pricePerNight, roomPhotos, selectedFromDate, selectedToDate }) => {
    const [reservationFrom] = useState(selectedFromDate || null);
    const [reservationTo] = useState(selectedToDate || null);
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [totalPrice, setTotalPrice] = useState(0);

    // Calculate total price when the modal is opened
    useEffect(() => {
        calculateTotalPrice();
    }, []); // Empty dependency array ensures it runs only once, similar to componentDidMount

    const calculateTotalPrice = () => {
        if (reservationFrom && reservationTo) {
            const numberOfNights = Math.ceil((reservationTo - reservationFrom) / (1000 * 60 * 60 * 24));
            const total = numberOfNights * pricePerNight;
            setTotalPrice(total);
        }
    };

    const handleConfirmReservation = async () => {
        try {
            if (!reservationFrom || !reservationTo || reservationTo <= reservationFrom) {
                setErrorMessage('Please select a valid date range.');
                setSuccessMessage(null);
                return;
            }

            calculateTotalPrice();

            const createReservationDto = {
                reservationFrom,
                reservationTo,
                totalPrice,
            };

            // Perform reservation creation without checking for room availability
            await createReservation(room.hotelId, room.roomId, createReservationDto);

            setErrorMessage(null);
            setSuccessMessage('Reservation confirmed!');

            setTimeout(() => {
                handleCloseModal();
            }, 2000);
        } catch (error) {
            console.error('Error creating reservation:', error);
            setErrorMessage('Error creating reservation. Please try again.');
            setSuccessMessage(null);
        }
    };

    return (
        <Flex direction="column" padding="4">
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
                    placeholderText="From Date"
                    style={{ width: '100%', border: 'none', outline: 'none' }}
                    readOnly
                />
            </Box>
            <Box mb="2">
                <DatePicker
                    selected={reservationTo}
                    placeholderText="To Date"
                    style={{ width: '100%', border: 'none', outline: 'none' }}
                    readOnly
                />
            </Box>
            <Text fontSize="medium" fontWeight="bold" mb={2}>
                Total Price: {totalPrice} Kč
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
                disabled={errorMessage || successMessage}
            >
                Confirm Reservation
            </Button>
        </Flex>
    );
};

export default ViewReservation;
