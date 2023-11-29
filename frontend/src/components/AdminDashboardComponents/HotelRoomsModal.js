import React, {useEffect, useState} from "react";
import {
    Button,
    Center,
    IconButton,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent, ModalFooter,
    ModalHeader,
    ModalOverlay, Table,
    TableContainer, Tbody, Td,
    Text, Th, Thead, Tr, useToast
} from "@chakra-ui/react";
import {deleteRoomFromHotel, getAllRoomsByHotelId} from "../../services/RoomService/roomService";
import {LoadingSpinner} from "../AppComponents/LoadingSpinner";
import {DeleteIcon, EditIcon} from "@chakra-ui/icons";
import EditRoomModal from "./EditRoomModal";

const HotelRoomsModal = ({isOpen, onClose, hotelId}) => {
    const [rooms, setRooms] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);
    const [selectedRoom, setSelectedRoom] = useState({id: null, hotelId: null});
    const toast = useToast();

    const openDeleteConfirmation = (roomId, hotelId) => {
        setSelectedRoom({id: roomId, hotelId: hotelId});
        setIsDeleteConfirmationOpen(true);
    };

    const openEditModal = (room) => {
        setSelectedRoom({
            roomId: room.roomId,
            hotelId: hotelId,
            roomNumber: room.roomNumber,
            category: room.category,
            pricePerNight: room.pricePerNight,
        });
        setIsEditModalOpen(true);
    };

    const handleDeleteRoom = async (roomId, hotelId) => {
        try {
            await deleteRoomFromHotel(hotelId, roomId);
            setRooms(rooms.filter(room => room.roomId !== roomId));
            toast({
                title: "Room deleted.",
                description: "The room has been successfully deleted.",
                status: "success",
                duration: 3000,
                isClosable: true,
            });
            setIsDeleteConfirmationOpen(false);
        } catch (error) {
            toast({
                title: "Error deleting room.",
                description: error.message || "There was an error deleting the room.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            setIsDeleteConfirmationOpen(false);
        }
    };

    useEffect(() => {
        const fetchRooms = async () => {
            setIsLoading(true);
            try {
                const fetchedRooms = await getAllRoomsByHotelId(hotelId);
                setRooms(fetchedRooms);
            } catch (error) {
                console.error('Failed to fetch rooms:', error);
                setError('Failed to fetch rooms. Please try again later.');
            }
            setIsLoading(false);
        };
        if (isOpen && hotelId) {
            fetchRooms();
        }
    }, [hotelId, isOpen]);

    return (
        <Modal isOpen={isOpen} onClose={onClose} size="6xl" isCentered>
            <ModalOverlay backdropFilter="blur(10px)"/>
            <ModalContent>
                <ModalHeader>Hotel Rooms</ModalHeader>
                <ModalCloseButton/>
                <ModalBody maxH="70vh" overflowY="auto">
                    {isLoading ? (
                        <LoadingSpinner/>
                    ) : error ? (
                        <Text color="red.500">{error.message}</Text>
                    ) : rooms.length > 0 ? (
                        <TableContainer>
                            <Table variant="simple" size="sm">
                                <Thead>
                                    <Tr>
                                        <Th textAlign="center">Room Number</Th>
                                        <Th textAlign="center">Category</Th>
                                        <Th textAlign="center">Price Per Night</Th>
                                        <Th textAlign="center" color="orange">Edit Room Details</Th>
                                        <Th textAlign="center" color="red">Delete</Th>
                                    </Tr>
                                </Thead>
                                <Tbody>
                                    {rooms.map((room) => (
                                        <Tr key={room.roomId}>
                                            <Td textAlign="center">{room.roomNumber}</Td>
                                            <Td textAlign="center">{room.category}</Td>
                                            <Td textAlign="center">{room.pricePerNight} Kč</Td>
                                            <Td textAlign="center">
                                                <IconButton
                                                    icon={<EditIcon/>}
                                                    isRound="true"
                                                    aria-label="Edit room"
                                                    size="sm"
                                                    variant="ghost"
                                                    colorScheme="orange"
                                                    onClick={() => openEditModal(room)}
                                                    _hover={{
                                                        background: "orange.100",
                                                        color: "orange.500",
                                                    }}
                                                />
                                            </Td>
                                            <Td textAlign="center">
                                                <IconButton
                                                    icon={<DeleteIcon/>}
                                                    isRound="true"
                                                    aria-label="Delete room"
                                                    size="sm"
                                                    variant="ghost"
                                                    colorScheme="red"
                                                    onClick={() => openDeleteConfirmation(room.roomId, room.hotelId)}
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
                            <Text>This hotel doesn't have any rooms.</Text>
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
            isEditModalOpen && (
            <EditRoomModal
                isOpen={isEditModalOpen}
                onClose={() => setIsEditModalOpen(false)}
                roomDetails={selectedRoom}
                onRoomUpdated={(updatedRoom) => {
                    setRooms(rooms.map((room) => (room.id === updatedRoom.id ? updatedRoom : room)));
                }}
            />
            );
            <Modal isOpen={isDeleteConfirmationOpen} onClose={() => setIsDeleteConfirmationOpen(false)}>
                <ModalOverlay backdropFilter="blur(10px)"/>
                <ModalContent>
                    <ModalHeader>Confirm Deletion</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        Are you sure you want to delete this room?
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
                            onClick={() => handleDeleteRoom(selectedRoom.id, selectedRoom.hotelId)}
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

export default HotelRoomsModal;