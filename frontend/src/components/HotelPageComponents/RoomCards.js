// RoomCards.jsx
import React, {useEffect, useState} from 'react';
import {
    Image,
    Text,
    Flex,
    Heading,
    useColorModeValue,
    Button,
    Stack,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
} from '@chakra-ui/react';
import DefaultRoomImage from '../../images/room.png';
import ViewRoomCard from './ViewRoomCard';
import ViewReservation from './ViewReservation';
import {getAmenityByRoomId_HotelId} from "../../services/AmenityService/amenityservice";

const RoomCards = ({ room, selectedFromDate, selectedToDate }) => {
    const [amenities, setAmenities] = useState([]);
    const bg = useColorModeValue('white', 'gray.800');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isReserveClicked, setReserveClicked] = useState(false);
    const [isCreateReservationModalOpen, setCreateReservationModalOpen] = useState(false);
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
    const handleViewRoom = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleCloseReservationModal = () => {
        setReserveClicked(false);
    };

    const handleOpenCreateReservationModal = () => {
        setCreateReservationModalOpen(true);
    };

    const handleCloseCreateReservationModal = () => {
        setCreateReservationModalOpen(false);
    };

    return (
        <Flex
            maxW="xl"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
            width="100%"
        >
            <Image
                src={room.image ? room.image.url : DefaultRoomImage}
                alt={`Image of ${room.roomNumber}`}
                height="200px"
                width="35%"
                objectFit="contain"
            />
            <Flex
                direction="column"
                p="6"
                flex="1"
                justifyContent="space-between"
                align="center"
            >
                <Flex direction="column" align="flex-start">
                    <Heading size="lg" mb="2" ml="7">
                        №{room.roomNumber}
                    </Heading>
                    <Text fontWeight="bold">Category: {room.category}</Text>
                    <Text mb="2">Price Per Night: {`${room.pricePerNight}`} Kč</Text>
                    <Text mb="2"> Amenities: {amenities.map((amenity) => amenity.amenityName).join(', ')}.
                    </Text>
                </Flex>
                <Stack direction="row" spacing={4} mt="3">
                    <Button
                        flex={1}
                        fontSize="sm"
                        rounded="full"
                        bg="green.400"
                        color="white"
                        boxShadow="0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)"
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                        onClick={handleViewRoom}
                    >
                        View Room
                    </Button>
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
                        onClick={handleOpenCreateReservationModal}
                    >
                        Create Reservation
                    </Button>
                </Stack>
            </Flex>

            {/* Detailed Room View Modal */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Room Details</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <ViewRoomCard room={room} handleCloseModal={handleCloseModal} />
                    </ModalBody>
                    <ModalFooter>
                        {/* Add any modal footer content here */}
                    </ModalFooter>
                </ModalContent>
            </Modal>

            {/* Reservation Modal */}
            <Modal isOpen={isReserveClicked} onClose={handleCloseReservationModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Create Reservation</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {/* Add reservation creation form or relevant content here */}
                        {/* Example: <ReservationForm room={room} onClose={handleCloseReservationModal} /> */}
                    </ModalBody>
                    <ModalFooter>
                        {/* Add any reservation modal footer content here */}
                    </ModalFooter>
                </ModalContent>
            </Modal>

            {/* Create Reservation Modal */}
            <Modal isOpen={isCreateReservationModalOpen} onClose={handleCloseCreateReservationModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Create Reservation</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <ViewReservation
                            room={room}
                            handleCloseModal={handleCloseCreateReservationModal}
                            pricePerNight={room.pricePerNight}
                            roomPhotos={room.photos}
                            selectedFromDate={selectedFromDate}
                            selectedToDate={selectedToDate}
                        />
                    </ModalBody>
                    <ModalFooter>
                        {/* Add any create reservation modal footer content here */}
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </Flex>
    );
};

export default RoomCards;
