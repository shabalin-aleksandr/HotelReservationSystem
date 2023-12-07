
import React, { useState, useEffect } from 'react';
import {
    Box,
    Text,
    Button,
    Flex,
    Image,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
} from '@chakra-ui/react';
import DefaultRoomImage from "../../images/room.png";
import ViewReservation from './ViewReservation'; // Import the new component
import { getAmenityByRoomId_HotelId } from '../../services/AmenityService/amenityservice';

const ViewRoomCard = ({ room }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const [amenities, setAmenities] = useState([]);
    const [isReserveClicked, setReserveClicked] = useState(false);

    useEffect(() => {
        // Fetch amenity data when the component mounts
        const fetchAmenityData = async () => {
            try {
                const amenityData = await getAmenityByRoomId_HotelId(room.hotelId, room.roomId);
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

    const handleReserveClick = () => {
        setReserveClicked(true);
    };

    const handleCloseModal = () => {
        setReserveClicked(false);
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

            <Text fontSize="xl" fontWeight="bold" mb={2}>
                Room №{room.roomNumber}
            </Text>
            <Text fontSize="medium" fontWeight="bold" mb={2}>Category: {room.category}</Text>
            <Text fontSize="medium" fontWeight="bold" mb={2}>Price Per Night: {room.pricePerNight} Kč</Text>
            {amenities.length > 0 && (
                <Text fontSize="medium" fontWeight="bold" mb={2}>
                    Amenities: {amenities.map((amenity) => amenity.amenityName).join(', ')}
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
                onClick={handleReserveClick}
                mt={4} >

                Reserve a room now!
            </Button>
            </Flex>



            <Modal isOpen={isReserveClicked} onClose={handleCloseModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Reservation details</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {/* Pass room, pricePerNight, and room photos to ViewReservation */}
                        <ViewReservation room={room} pricePerNight={room.pricePerNight} roomPhotos={room.photos} />
                    </ModalBody>
                    <ModalFooter></ModalFooter>
                </ModalContent>
            </Modal>
        </Box>
    );
};

export default ViewRoomCard;
