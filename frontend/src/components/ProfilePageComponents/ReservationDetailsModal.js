import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Button,
    Text,
    UnorderedList,
    ListItem, Box, Flex, Divider,
} from '@chakra-ui/react';
import {Link as ReactRouterLink} from "react-router-dom";
import {HOTEL_ROUTE} from "../../utils/routes";
import React from "react";
import {Badge} from "react-bootstrap";

const ReservationDetailsModal = ({isOpen, onClose, reservation, room}) => {
    console.log('Reservation Data:', reservation);

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)"/>
            <ModalContent>
                <ModalHeader
                    fontSize="2xl"
                    textAlign="center"
                    bg="green.500"
                    color="white"
                >
                    Reservation Details
                </ModalHeader>
                <ModalCloseButton color="white" />
                <ModalBody>
                    <Flex direction="column" gap={4}>
                        <Box>
                            <Text fontWeight="bold">Room Number:</Text>
                            <Badge colorscheme="blue" ml={2}>{room?.roomNumber}</Badge>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Category:</Text>
                            <Text>{room?.category}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Reservation From:</Text>
                            <Text mb={4}>{reservation?.[0]?.reservationFrom}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Reservation To:</Text>
                            <Text mb={4}>{reservation?.[0]?.reservationTo}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Total stay days:</Text>
                            <Text mb={4}>{reservation?.[0]?.totalDays}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Price Per Night:</Text>
                            <Text mb={4}>{room?.pricePerNight} Kč</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Total Price:</Text>
                            <Text mb={4}>{reservation?.[0]?.totalPrice} Kč</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Amenities:</Text>
                            <UnorderedList styleType="disc" ml={5}>
                                {room?.amenities.map((amenity) => (
                                    <ListItem key={amenity.amenityId}>
                                        <Text
                                            as="span"
                                            fontWeight="semibold"
                                        >
                                            {amenity.amenityName}
                                        </Text> - {amenity.description}
                                    </ListItem>
                                ))}
                            </UnorderedList>
                        </Box>
                    </Flex>
                </ModalBody>
                <Divider />
                <ModalFooter>
                    <Button
                        as={ReactRouterLink}
                        to={`/${HOTEL_ROUTE}/${room?.hotelId}`}
                        mr={2}
                        fontSize="sm"
                        rounded="full"
                        bg="transparent"
                        color="green.500"
                        border="2px"
                        borderColor="green.500"
                        _hover={{
                            bg: 'green.500',
                            color: 'white',
                        }}
                        _focus={{
                            boxShadow: '0 0 0 3px rgba(66, 153, 225, 0.6)',
                        }}
                    >
                        Hotel Page
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
                        mr={3}
                        onClick={onClose}>
                        Close
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default ReservationDetailsModal;
