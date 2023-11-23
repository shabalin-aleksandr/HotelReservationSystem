import React, { useState, useEffect } from 'react';
import { Box, Text, Button, Flex, Image } from '@chakra-ui/react';
import DefaultRoomImage from "../../images/room.png";
import { getAmenityByRoomId_HotelId } from '../../services/AmenityService/amenityservice';

const ViewRoomCard = ({ room }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const [amenities, setAmenities] = useState([]);

    useEffect(() => {
        // Fetch amenity data when the component mounts
        const fetchAmenityData = async () => {
            try {
                const amenityData = await getAmenityByRoomId_HotelId(room.hotelId, room.roomId);
                // Assuming amenityData is an array of amenities
                setAmenities(amenityData);
            } catch (error) {
                console.error('Error fetching amenity:', error);
            }
        };

        fetchAmenityData();
    }, [room.hotelId, room.roomId]);

    const handleNextPhoto = () => {
        if (room.photos && room.photos.length > 1) {
            setCurrentPhotoIndex((prevIndex) => (prevIndex + 1) % room.photos.length);
        }
    };

    const handlePreviousPhoto = () => {
        if (room.photos && room.photos.length > 1) {
            setCurrentPhotoIndex(
                (prevIndex) => (prevIndex - 1 + room.photos.length) % room.photos.length
            );
        }
    };

    return (
        <Box>
            <Flex direction="column" align="center">
                <Image
                    src={room.photos && room.photos.length > 0 ? room.photos[currentPhotoIndex].url : DefaultRoomImage}
                    alt={`Image of ${room.roomNumber}`}
                    height="400px"
                    width="100%"
                    objectFit="contain"
                />
                {room.photos && room.photos.length > 1 && (
                    <Flex justify="space-between" width="100%" mt={2}>
                        <Button
                            fontSize="sm"
                            rounded="full"
                            bg="gray.400"
                            color="white"
                            onClick={handlePreviousPhoto}
                        >
                            Previous Photo
                        </Button>
                        <Button
                            fontSize="sm"
                            rounded="full"
                            bg="gray.400"
                            color="white"
                            onClick={handleNextPhoto}
                        >
                            Next Photo
                        </Button>
                    </Flex>
                )}
            </Flex>
            <Text fontSize="xl" fontWeight="bold" mb={2}>
                Room №{room.roomNumber}
            </Text>
            <Text fontSize="medium" fontWeight="bold" mb={2}>Category: {room.category}</Text>
            <Text fontSize="medium" fontWeight="bold" mb={2}>Price Per Night: ${room.pricePerNight}</Text>
            {amenities.length > 0 && (
                <Text fontSize="medium" fontWeight="bold" mb={2}>
                    Amenities: {amenities.map((amenity) => amenity.amenityName).join(', ')}
                </Text>
            )}

            <Button
                flex={1}
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
                onClick={() => {
                    // Add logic to navigate to the room details page
                    console.log(`View details for Room #${room.roomNumber}`);
                }}
            >
                Reserve a room now!
            </Button>
        </Box>
    );
};

export default ViewRoomCard;
