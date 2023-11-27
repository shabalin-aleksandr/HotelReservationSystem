import React, {useState, useEffect} from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    Button,
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    Text,
    IconButton,
    useToast, TableContainer, Center
} from '@chakra-ui/react';
import {deleteSingleReservation, getAllReservationsInHotel} from "../../services/ReservationService/reservationService";
import {LoadingSpinner} from "../AppComponents/LoadingSpinner";
import {DeleteIcon} from "@chakra-ui/icons";

export const HotelReservationsModal = ({hotelId, isOpen, onClose}) => {
    const [reservations, setReservations] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);
    const [selectedReservation, setSelectedReservation] = useState({ id: null, roomId: null });
    const [error, setError] = useState('');
    const toast = useToast();

    const openDeleteConfirmation = (reservationId, roomId) => {
        setSelectedReservation({ id: reservationId, roomId: roomId });
        setIsDeleteConfirmationOpen(true);
    };

    const handleDeleteReservation = async (reservationId, roomId) => {
        try {
            await deleteSingleReservation(hotelId, roomId, reservationId);
            setReservations(reservations.filter(reservation => reservation.reservationId !== reservationId));
            toast({
                title: "Reservation deleted.",
                description: "The reservation has been successfully deleted.",
                status: "success",
                duration: 5000,
                isClosable: true,
            });
            setIsDeleteConfirmationOpen(false);
        } catch (error) {
            toast({
                title: "Error deleting reservation.",
                description: error.message || "There was an error deleting the reservation.",
                status: "error",
                duration: 5000,
                isClosable: true,
            });
            setIsDeleteConfirmationOpen(false);
        }
    };

    useEffect(() => {
        const fetchReservations = async () => {
            setIsLoading(true);
            try {
                const data = await getAllReservationsInHotel(hotelId);
                setReservations(data);
            } catch (error) {
                setError('Failed to fetch reservations. Please try again later.');
                console.error(error);
            }
            setIsLoading(false);
        };

        if (isOpen) {
            fetchReservations();
        }
    }, [hotelId, isOpen]);

    return (
        <Modal isOpen={isOpen} onClose={onClose} size="6xl" isCentered>
            <ModalOverlay backdropFilter="blur(10px)"/>
            <ModalContent>
                <ModalHeader>Hotel Reservations</ModalHeader>
                <ModalCloseButton/>
                <ModalBody maxH="70vh" overflowY="auto">
                    {isLoading ? (
                        <LoadingSpinner/>
                    ) : error ? (
                        <Text color="red.500">{error.message}</Text>
                    ) : reservations.length > 0 ? (
                        <TableContainer>
                            <Table variant="simple" size="sm">
                                <Thead>
                                    <Tr>
                                        <Th textAlign="center">Room Number</Th>
                                        <Th textAlign="center">Category</Th>
                                        <Th textAlign="center">Reservation From</Th>
                                        <Th textAlign="center">Reservation To</Th>
                                        <Th textAlign="center">Total Days</Th>
                                        <Th textAlign="center">Total Price</Th>
                                        <Th textAlign="center" color="red">Delete</Th>
                                    </Tr>
                                </Thead>
                                <Tbody>
                                    {reservations.map((reservation) => (
                                        <Tr key={reservation.reservationId}>
                                            <Td textAlign="center">{reservation.room.roomNumber}</Td>
                                            <Td textAlign="center">{reservation.room.category}</Td>
                                            <Td textAlign="center">{reservation.reservationFrom}</Td>
                                            <Td textAlign="center">{reservation.reservationTo}</Td>
                                            <Td textAlign="center">{reservation.totalDays}</Td>
                                            <Td textAlign="center">{reservation.totalPrice} Kč</Td>
                                            <Td textAlign="center">
                                                <IconButton
                                                    icon={<DeleteIcon/>}
                                                    isRound="true"
                                                    aria-label="Delete reservation"
                                                    size="sm"
                                                    variant="ghost"
                                                    colorScheme="red"
                                                    onClick={() => openDeleteConfirmation(reservation.reservationId, reservation.room.roomId)}
                                                    _hover={{
                                                        background: "red.100",
                                                        color: "red.500",
                                                    }}
                                                />
                                            </Td>
                                        </Tr>
                                    ))}
                                </Tbody>
                            </Table>
                        </TableContainer>
                    ) : (
                        <Center>
                            <Text>This hotel doesn't have any reservations.</Text>
                        </Center>
                    )}
                </ModalBody>
                <ModalFooter>
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
                        onClick={onClose}
                    >
                        Close
                    </Button>
                </ModalFooter>
            </ModalContent>
            <Modal isOpen={isDeleteConfirmationOpen} onClose={() => setIsDeleteConfirmationOpen(false)}>
                <ModalOverlay backdropFilter="blur(10px)" />
                <ModalContent>
                    <ModalHeader>Confirm Deletion</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        Are you sure you want to delete this reservation?
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
                            onClick={() => handleDeleteReservation(selectedReservation.id, selectedReservation.roomId)}
                        >
                            Delete
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
                            onClick={() => setIsDeleteConfirmationOpen(false)}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </Modal>
    );
};
