import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getHotelDetails } from '../services/HotelService/hotelService';
import { Box, Heading, SimpleGrid, Flex, Button } from '@chakra-ui/react';
import HotelCard from "../components/HotelPageComponents/PhotoOfHotel";
import { getAllRoomsByHotelId, findAvailableRoomsInHotelForDateRange } from '../services/RoomService/roomService';
import RoomCards from "../components/HotelPageComponents/RoomCards";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const HotelPage = () => {
    const { hotelId } = useParams();
    const [hotel, setHotel] = useState(null);
    const [rooms, setRooms] = useState([]);
    const [selectedFromDate, setSelectedFromDate] = useState(null);
    const [selectedToDate, setSelectedToDate] = useState(null);
    const [availableRooms, setAvailableRooms] = useState([]);
    const [showAvailableRooms, setShowAvailableRooms] = useState(false);

    useEffect(() => {
        async function fetchHotel() {
            try {
                const hotelData = await getHotelDetails(hotelId);
                setHotel(hotelData);

                // Fetch rooms for the hotel
                const roomsData = await getAllRoomsByHotelId(hotelId);
                setRooms(roomsData);
            } catch (error) {
                console.error('Error fetching hotel or rooms:', error);
            }
        }
        fetchHotel();
    }, [hotelId]);

    const handleFromDateChange = (date) => {
        setSelectedFromDate(date);
    };

    const handleToDateChange = (date) => {
        setSelectedToDate(date);
    };

    const handleFindAvailableRooms = async () => {
        if (!selectedFromDate || !selectedToDate) {
            console.error('Please select both From Date and To Date.');
            return;
        }

        try {
            const availableRoomsData = await findAvailableRoomsInHotelForDateRange(
                hotelId,
                selectedFromDate,
                selectedToDate
            );
            setAvailableRooms(availableRoomsData);
            setShowAvailableRooms(true);
        } catch (error) {
            console.error('Error fetching available rooms:', error);
        }
    };

    if (!hotel) {
        return <div>Loading...</div>;
    }

    return (
        <Flex padding="4">
            <Box width="30%">
                <Box p="6">
                    <Heading size="2xl" mb="2">{hotel.hotelName}</Heading>
                </Box>
                <HotelCard key={hotel.hotelId} hotel={hotel} />
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
                    <Button onClick={handleFindAvailableRooms} colorScheme="green" bgColor="green.400" color="white">
                        Find Available Rooms
                    </Button>
                </Flex>
            </Box>
            <Box width="70%">
                <Box p="6">
                    <Heading size="2xl" mb="2"> Rooms </Heading>
                </Box>

                <SimpleGrid columns={[1, 2]} spacing="4">
                    {(showAvailableRooms ? availableRooms : rooms).map((room) => (
                        <Flex key={room.roomid} width="100%">
                            <RoomCards room={room} />
                        </Flex>
                    ))}
                </SimpleGrid>
            </Box>
        </Flex>
    );
};



export default HotelPage;
