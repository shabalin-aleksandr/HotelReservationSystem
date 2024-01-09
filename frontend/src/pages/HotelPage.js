import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getHotelDetails } from '../services/HotelService/hotelService';
import { Box, Heading, SimpleGrid, Flex, Button, Text } from '@chakra-ui/react';
import HotelCard from "../components/HotelPageComponents/PhotoOfHotel";
import { getAllRoomsByHotelId, findAvailableRoomsInHotelForDateRange } from '../services/RoomService/roomService';
import RoomCards from "../components/HotelPageComponents/RoomCards";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { LoadingSpinner } from "../components/AppComponents/LoadingSpinner";

const HotelPage = () => {
    const { hotelId } = useParams();
    const [hotel, setHotel] = useState(null);
    const [rooms, setRooms] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [selectedFromDate, setSelectedFromDate] = useState(null);
    const [selectedToDate, setSelectedToDate] = useState(null);
    const [availableRooms, setAvailableRooms] = useState([]);
    const [showAvailableRooms, setShowAvailableRooms] = useState(false);
    const [searchSuccess, setSearchSuccess] = useState(true);
    const [dateSelectionError, setDateSelectionError] = useState(false);
    const [hasUserPressedButton, setHasUserPressedButton] = useState(false);

    useEffect(() => {
        const fetchHotel = async () => {
            setIsLoading(true);
            try {
                const hotelData = await getHotelDetails(hotelId);
                setHotel(hotelData);

                // Fetch rooms for the hotel
                const roomsData = await getAllRoomsByHotelId(hotelId);
                setRooms(roomsData);
            } catch (error) {
                console.error('Error fetching hotel or rooms:', error);
                setError(error);
            } finally {
                setIsLoading(false);
            }
        }
        fetchHotel();
    }, [hotelId]);

    const handleFromDateChange = (date) => {
        setSelectedFromDate(date);
        setDateSelectionError(false);
    };

    const handleToDateChange = (date) => {
        setSelectedToDate(date);
        setDateSelectionError(false);
    };

    const handleFindAvailableRooms = async () => {
        if (!selectedFromDate || !selectedToDate) {
            setDateSelectionError(true);
            setShowAvailableRooms(false);
            setSearchSuccess(true);
            return;
        }

        try {
            const availableRoomsData = await findAvailableRoomsInHotelForDateRange(
                hotelId,
                selectedFromDate,
                selectedToDate
            );

            if (availableRoomsData.length === 0) {
                setSearchSuccess(false);
            } else {
                setAvailableRooms(availableRoomsData);
                setShowAvailableRooms(true);
                setSearchSuccess(true);
            }
        } catch (error) {
            console.error('Error fetching available rooms:', error);
        }

        setHasUserPressedButton(true);
    };

    if (isLoading) {
        return <LoadingSpinner />;
    }

    if (error) {
        return <Text>{error.message}</Text>;
    }

    return (
        <Flex padding="4">
            <Box width="30%">
                {hotel && (
                    <>
                        <Box p="6">
                            <Heading size="2xl" mb="2">{hotel.hotelName}</Heading>
                        </Box>
                        <HotelCard key={hotel.hotelId} hotel={hotel} />
                    </>
                )}
                <Flex flexDirection="column" mb="4">
                    <Box mb="2">
                        <DatePicker
                            selected={selectedFromDate}
                            onChange={handleFromDateChange}
                            selectsStart
                            startDate={selectedFromDate}
                            endDate={selectedToDate}
                            minDate={new Date()}
                            placeholderText="From Date"
                            style={{ width: '100%', border: 'none', outline: 'none' }}
                        />
                    </Box>
                    <Box mb="2" >
                        <DatePicker
                            selected={selectedToDate}
                            onChange={handleToDateChange}
                            selectsEnd
                            startDate={selectedFromDate}
                            endDate={selectedToDate}
                            minDate={selectedFromDate || new Date()}
                            placeholderText="To Date"
                            style={{ width: '100%', border: 'none', outline: 'none' }}
                        />
                    </Box>
                    <Button
                        onClick={handleFindAvailableRooms}
                        colorScheme="green"
                        bgColor="green.400"
                        color="white"
                        fontSize="sm"
                        rounded="full"
                        bg="green.400"
                        boxShadow="0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)"
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                    >
                        Find Available Rooms
                    </Button>
                    {dateSelectionError && (
                        <Text fontWeight="bold" fontSize="xl" color={"red"}>
                            Please also choose the start date or end date.
                        </Text>
                    )}
                </Flex>
            </Box>
            <Box width="70%">
                <Box p="6">
                    <Heading size="2xl" mb="2"> Rooms </Heading>
                    {!selectedFromDate || !selectedToDate ? (
                        <Text fontWeight="bold" fontSize="xl" color={"gray"}>
                            Please select a start date and end date.
                        </Text>
                    ) : null}
                </Box>
                {hasUserPressedButton && (
                    <SimpleGrid columns={[1, 2]} spacing="4">
                        {searchSuccess ? (
                            (showAvailableRooms ? availableRooms : rooms).map((room) => (
                                <Flex key={room.roomId} width="100%">
                                    <RoomCards
                                        room={room}
                                        selectedFromDate={selectedFromDate}
                                        selectedToDate={selectedToDate}
                                    />
                                </Flex>
                            ))
                        ) : (
                            <Text fontWeight="bold" fontSize="xl" color={"red"}>
                                No available rooms found. Please try different dates.
                            </Text>
                        )}
                    </SimpleGrid>
                )}
            </Box>
        </Flex>
    );
};

export default HotelPage;
