import React, {useState} from 'react';
import {
    Box,
    Image,
    Text,
    Flex,
    Heading,
    useColorModeValue,
    Button,
    Stack,
    Modal,
    ModalOverlay,
    ModalContent, ModalHeader, ModalBody, ModalFooter
} from '@chakra-ui/react';
import RatingStars from "../ProfilePageComponents/RatingStars";
import DefaultHotelImage from '../../images/default-hotel-image.png'
import {Link as ReactRouterLink} from 'react-router-dom';
import {HOTEL_ROUTE} from "../../utils/routes";
import {getAdminTypeFromToken} from "../../services/UserService/adminService";
import {deleteHotel} from "../../services/HotelService/hotelService";

const HotelCard = ({ hotel, onHotelDeleted }) => {
    const adminType = getAdminTypeFromToken();
    const isSuperAdmin = adminType === 'SUPER_ADMIN';
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [selectedHotelId, setSelectedHotelId] = useState(null);
    const bg = useColorModeValue('white', 'gray.800');

    const openDeleteConfirmation = (hotelId) => {
        setSelectedHotelId(hotelId);
        setIsDeleteModalOpen(true);
    };

    const handleDeleteHotel = async (hotelId) => {
        try {
            await deleteHotel(hotelId);
            onHotelDeleted(hotelId);
            setIsDeleteModalOpen(false);
        } catch (error) {
            console.error('Error deleting hotel', error);
        }
    };

    return (
        <Box
            maxW="sm"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
        >
            <Image
                src={hotel.image ? hotel.image.url : DefaultHotelImage}
                alt={`Image of ${hotel.hotelName}`}
                height="200px"
                width="100%"
                objectFit="contain"
            />
            <Box p="6">
                <Heading size="lg" mb="2">{hotel.hotelName}</Heading>
                <Text fontWeight="bold">{hotel.address}</Text>
                <Text mb="2">{`${hotel.city}, ${hotel.country}`}</Text>
                <Flex align="center">
                    <RatingStars rating={hotel.rating}/>
                </Flex>
                <Stack direction="column" spacing={4} mt="3">
                    <Button
                        as={ReactRouterLink}
                        to={`${HOTEL_ROUTE}/${hotel.hotelId}`}
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
                        View Hotel
                    </Button>
                    {isSuperAdmin && (
                        <Button
                            fontSize="sm"
                            rounded="full"
                            bg="transparent"
                            color="red.500"
                            border="2px"
                            borderColor="red.500"
                            _hover={{
                                bg: 'red.500',
                                color: 'white',
                            }}
                            _focus={{
                                boxShadow: '0 0 0 3px rgba(66, 153, 225, 0.6)',
                            }}
                            onClick={() => openDeleteConfirmation(hotel.hotelId)}
                        >
                            Delete Hotel
                        </Button>
                    )}
                </Stack>
                <Modal isOpen={isDeleteModalOpen} onClose={() => setIsDeleteModalOpen(false)} isCentered>
                    <ModalOverlay backdropFilter="blur(10px)"/>
                    <ModalContent>
                        <ModalHeader>Delete Hotel</ModalHeader>
                        <ModalBody>
                            Are you sure you want to delete the
                            <Text as="span" fontWeight="bold"> {hotel.hotelName} </Text>
                            ? This action cannot be undone.
                        </ModalBody>
                        <ModalFooter>
                            <Button
                                fontSize="sm"
                                rounded="full"
                                bg="red.500"
                                color="white"
                                _hover={{
                                    bg: 'red.600',
                                }}
                                _focus={{
                                    bg: 'red.600',
                                }}
                                mr={3}
                                onClick={() => handleDeleteHotel(selectedHotelId)}
                            >
                                Confirm Delete
                            </Button>
                            <Button
                                fontSize="sm"
                                rounded="full"
                                bg="transparent"
                                color="gray.800"
                                border="2px"
                                borderColor="gray.300"
                                _hover={{
                                    bg: 'gray.200',
                                    color: 'black',
                                    borderColor: 'gray.300'
                                }}
                                _focus={{
                                    bg: 'gray.200',
                                }}
                                onClick={() => setIsDeleteModalOpen(false)}
                            >
                                Cancel
                            </Button>
                        </ModalFooter>
                    </ModalContent>
                </Modal>
            </Box>
        </Box>
    );
};

export default HotelCard;
