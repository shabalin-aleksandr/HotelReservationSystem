import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getHotelDetails } from '../services/HotelService/hotelService';
import { Box, Heading, Text, SimpleGrid, Flex } from '@chakra-ui/react';
import HotelCard from "../components/HotelPageComponents/PhotoOfHotel";
import { getAllRoomsByHotelId } from '../services/RoomService/roomService';
import RoomCards from "../components/HotelPageComponents/RoomCards";

const HotelPage = () => {
    const { hotelId } = useParams();
    const [hotel, setHotel] = useState(null);
    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        async function fetchHotel() {
            try {
                const hotelData = await getHotelDetails(hotelId);
                setHotel(hotelData);

                // Fetch rooms for the hotel
                const roomsData = await getAllRoomsByHotelId(hotelId);
                setRooms(roomsData);
            } catch (error) {
                // Handle errors
                console.error('Error fetching hotel or rooms:', error);
            }
        }
        fetchHotel();
    }, [hotelId]);

    if (!hotel) {
        return <div>Loading...</div>;
    }

    return (
        <Flex padding="4">
            <Box width="30%">
                <Box p="6">
                    <Heading size="2xl" mb="2" >{hotel.hotelName}</Heading>
                </Box>
                <HotelCard key={hotel.hotelId} hotel={hotel} />
            </Box>
            <Box width="70%">
                <Box p="6">
                    <Heading size="2xl" mb="2" > Rooms </Heading>
                </Box>
                <SimpleGrid columns={[1, 2]} spacing="4">
                    {rooms.map((room) => (
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
