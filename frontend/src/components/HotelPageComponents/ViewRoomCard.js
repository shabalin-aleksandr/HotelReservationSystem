// ViewRoomCard.jsx
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
import { getAmenityByRoomId_HotelId } from '../../services/AmenityService/amenityservice';

const ViewRoomCard = ({ room }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const [amenities, setAmenities] = useState([]);
    const [isReserveClicked, setReserveClicked] = useState(false);

    useEffect(() => {
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
                <Text fontSize="medium" fontWeight="bold" mb={2}>
                    Category: {room.category}
                </Text>
                <Text fontSize="medium" fontWeight="bold" mb={2}>
                    Price Per Night: {room.pricePerNight} Kč
                </Text>
                {amenities.length > 0 && (
                    <Text fontSize="medium" fontWeight="bold" mb={2}>
                        Amenities: {amenities.map((amenity) => amenity.description).join(', ')}
                    </Text>
                )}
                {/* Button for reservation removed */}
            </Flex>

            {/* Reservation Modal */}
            <Modal isOpen={isReserveClicked} onClose={handleCloseModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Reservation details</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {/* ... (rest of the modal content) ... */}
                    </ModalBody>
                    <ModalFooter></ModalFooter>
                </ModalContent>
            </Modal>
        </Box>
    );
};

export default ViewRoomCard;
