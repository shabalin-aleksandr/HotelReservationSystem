import React, {useState} from 'react';
import {
    Box,
    Image,
    Button,
    Stack,
    useColorModeValue
} from '@chakra-ui/react';
import DefaultHotelImage from '../../images/default-hotel-image.png'
import RatingStars from "./RatingStars";
import {getRoomDetails} from "../../services/RoomService/roomService";
import {getReservationDetails} from "../../services/ReservationService/reservationService";
import ReservationDetailsModal from "./ReservationDetailsModal";

const UserReservationCard = ({ reservation }) => {
    const bg = useColorModeValue('white', 'gray.800');
    const priceColor = useColorModeValue('gray.600', 'gray.400');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [roomDetails, setRoomDetails] = useState(null);
    const [reservationDetails, setReservationDetails] = useState(null);

    const handleDetailsClick = async () => {
        try {
            const roomData = await getRoomDetails(reservation.hotelId, reservation.roomId);
            const reservationData = await
                getReservationDetails(reservation.userId);
            setRoomDetails(roomData);
            setReservationDetails(reservationData);
            setIsModalOpen(true);
        } catch (error) {
            console.error('Failed to fetch details', error);
        }
    };

    return (
        <Box
            key={reservation.reservationId}
            maxW="sm"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
        >
            <Image
                src={DefaultHotelImage}
                alt={`Image of ${reservation.hotelDetails?.hotelName}`}
                objectFit="cover"
                w="full"
                h="250px"
            />
            <Box p="6">
                <Box display="flex" alignItems="baseline">
                    <Box
                        color={priceColor}
                        fontWeight="semibold"
                        letterSpacing="wide"
                        fontSize="xs"
                        textTransform="uppercase"
                        ml="2"
                    >
                        {reservation.totalDays} {reservation.totalDays === 1 ? 'day' : 'days'} &bull; {reservation.hotelDetails?.city}
                    </Box>
                </Box>
                <Box mt="1" fontWeight="semibold" as="h3" lineHeight="tight">
                    {reservation.hotelDetails?.hotelName}
                </Box>
                <Box>
                    {reservation.totalPrice}
                    <Box as="span" color={priceColor} fontSize="sm">
                        Kč
                    </Box>
                </Box>
                <Box display="flex" mt="2" alignItems="center">
                    <RatingStars rating={reservation.hotelDetails?.rating} />
                </Box>
                <Stack direction="row" spacing={4} mt="3">
                    <Button
                        onClick={handleDetailsClick}
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
                    >
                        Details
                    </Button>
                </Stack>
                <ReservationDetailsModal
                    isOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                    reservation={reservationDetails}
                    room={roomDetails}
                />
            </Box>
        </Box>
    );
};

export default UserReservationCard;
