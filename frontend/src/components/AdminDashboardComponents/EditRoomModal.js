import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    Button,
    FormControl,
    FormLabel,
    Input,
    Select,
    useToast,
} from '@chakra-ui/react';
import {useEffect, useState} from 'react';
import {editRoomInHotel} from "../../services/RoomService/roomService";

const EditRoomModal = ({ isOpen, onClose, roomDetails, onRoomUpdated }) => {
    const [roomData, setRoomData] = useState({
        roomNumber: roomDetails.roomNumber || '',
        category: roomDetails.category || '',
        pricePerNight: roomDetails.pricePerNight || '',
        hotelId: roomDetails.hotelId || '',
        roomId: roomDetails.roomId || '',
    });
    const toast = useToast();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setRoomData(prevRoomData => ({
            ...prevRoomData,
            [name]: value
        }));
    };

    const handleEditRoom = async () => {
        const updatedFields = {};
        if (roomData.roomNumber !== roomDetails.roomNumber) {
            updatedFields.roomNumber = roomData.roomNumber;
        }
        if (roomData.category !== roomDetails.category) {
            updatedFields.category = roomData.category;
        }
        if (roomData.pricePerNight !== roomDetails.pricePerNight) {
            updatedFields.pricePerNight = roomData.pricePerNight;
        }
        try {
            const updatedRoom = await editRoomInHotel(roomData.hotelId, roomData.roomId, {
                roomNumber: roomData.roomNumber,
                category: roomData.category,
                pricePerNight: roomData.pricePerNight,
            });
            toast({
                title: 'Room updated.',
                description: `Room ${updatedRoom.roomNumber} has been updated successfully.`,
                status: 'success',
                duration: 3000,
                isClosable: true,
            });
            onRoomUpdated(updatedRoom);
            onClose();
        } catch (error) {
            toast({
                title: 'Error updating room.',
                description: error.response.data.error,
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    useEffect(() => {
        if (roomDetails) {
            setRoomData({
                roomNumber: roomDetails.roomNumber || '',
                category: roomDetails.category || '',
                pricePerNight: roomDetails.pricePerNight || '',
                hotelId: roomDetails.hotelId || '',
                roomId: roomDetails.roomId || '',
            });
        }
    }, [roomDetails]);

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)" />
            <ModalContent>
                <ModalHeader>Edit Room</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <FormControl>
                        <FormLabel>Room Number</FormLabel>
                        <Input
                            name="roomNumber"
                            type="number"
                            placeholder={roomDetails.roomNumber}
                            onChange={handleInputChange}
                        />
                    </FormControl>
                    <FormControl mt={4}>
                        <FormLabel>Category</FormLabel>
                        <Select
                            name="category"
                            placeholder={roomDetails.category}
                            onChange={handleInputChange}
                        >
                            <option value="DELUXE">DELUXE</option>
                            <option value="STANDARD">STANDARD</option>
                        </Select>
                    </FormControl>
                    <FormControl mt={4}>
                        <FormLabel>Price Per Night</FormLabel>
                        <Input
                            name="pricePerNight"
                            type="number"
                            placeholder={roomDetails.pricePerNight}
                            onChange={handleInputChange}
                        />
                    </FormControl>
                </ModalBody>
                <ModalFooter>
                    <Button
                        mr={2}
                        type="submit"
                        fontSize="sm"
                        rounded="full"
                        bg="green.400"
                        color="white"
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                        onClick={handleEditRoom}
                    >
                        Save Changes
                    </Button>
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
                        onClick={onClose}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default EditRoomModal;
