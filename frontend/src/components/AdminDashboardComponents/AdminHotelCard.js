import React, { useState} from 'react';
import {
    Box,
    Flex,
    Text,
    IconButton,
    Image,
    Menu,
    MenuButton,
    MenuItem,
    MenuList,
    useToast,
    ModalOverlay,
    ModalHeader,
    ModalCloseButton,
    ModalBody, Button,
    ModalFooter,
    ModalContent,
    Modal,
    FormControl,
    FormLabel,
    Input,
    VStack,
    Select, FormErrorMessage, InputGroup, InputLeftAddon
} from '@chakra-ui/react';
import { HamburgerIcon, EditIcon, DeleteIcon, ViewIcon, CalendarIcon } from '@chakra-ui/icons';
import { Link as ReactRouterLink } from 'react-router-dom';
import DefaultHotelImage from "../../images/default-hotel-image.png";
import {deleteHotel, editHotel} from "../../services/HotelService/hotelService";
import {useHotels} from "../../utils/context/HotelContext";
import {addRoomToHotel} from "../../services/RoomService/roomService";

const AdminHotelCard = ({ hotel, onHotelDeleted, onHotelUpdated }) => {
    const [hovered, setHovered] = useState(false);
    const { removeHotel } = useHotels();
    const [errors, setErrors] = useState({});
    const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isAddingNewRoom, setIsAddingNewRoom] = useState(false);
    const [editFormData, setEditFormData] = useState({
        hotelName: hotel.hotelName,
        country: hotel.country,
        city: hotel.city,
        address: hotel.address,
        receptionNumber: hotel.receptionNumber,
    });
    const [newRoomData, setNewRoomData] = useState({
        roomNumber: '',
        category: '',
        pricePerNight: '',
    });
    const toast = useToast();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewRoomData({ ...newRoomData, [name]: value });
        let newErrors = {...errors};
        if (!value) {
            newErrors[name] = 'This field is required';
        } else if ((name === 'roomNumber' || name === 'pricePerNight') && isNaN(Number(value))) {
            newErrors[name] = 'This field must be a number';
        } else {
            delete newErrors[name];
        }
        setErrors(newErrors);
    };

    const openConfirmModal = () => {
        setIsConfirmModalOpen(true);
    };

    const openEditModal = () => {
        setIsEditModalOpen(true);
    };

    const handleEditHotel = async () => {
        try {
            const updatedHotelData = await editHotel(hotel.hotelId, editFormData);
            toast({
                title: "Hotel updated.",
                description: `${hotel.hotelName} has been updated successfully.`,
                status: "success",
                duration: 3000,
                isClosable: true,
            });
            setIsEditModalOpen(false);
            onHotelUpdated(updatedHotelData);
        } catch (error) {
            toast({
                title: "Error updating hotel.",
                description: error.message,
                status: "error",
                duration: 3000,
                isClosable: true,
            });
        }
    };

    const handleEditFormChange = (e) => {
        const { name, value } = e.target;
        setEditFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleDelete = async () => {
        try {
            await deleteHotel(hotel.hotelId);
            toast({
                title: "Hotel deleted.",
                description: `${hotel.hotelName} has been removed successfully.`,
                status: "success",
                duration: 9000,
                isClosable: true,
            });
            onHotelDeleted(hotel.hotelId);
            removeHotel(hotel.hotelId);
            setIsConfirmModalOpen(false);
        } catch (error) {
            toast({
                title: "Error deleting hotel.",
                description: error.message,
                status: "error",
                duration: 9000,
                isClosable: true,
            });
            setIsConfirmModalOpen(false);
        }
    };

    const handleSubmitNewRoom = async (e) => {
        e.preventDefault();

        if (!newRoomData.roomNumber || !newRoomData.category || !newRoomData.pricePerNight) {
            toast({
                title: "Validation Error",
                description: "All fields are required.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
        if (Object.keys(errors).length > 0) {
            toast({
                title: "Validation Error",
                description: "Please correct all errors before submitting.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
            return;
        }
        try {
            const newRoom = await addRoomToHotel(
                hotel.hotelId,
                newRoomData.roomNumber,
                newRoomData.category,
                newRoomData.pricePerNight
            );
            toast({
                title: "Room added.",
                description: `Room ${newRoom.roomNumber} has been added to ${hotel.hotelName}.`,
                status: "success",
                duration: 300,
                isClosable: true,
            });
            setIsAddingNewRoom(false);
            onHotelUpdated({
                ...hotel,
                rooms: [...(hotel.rooms ?? []), newRoom]
            });
        } catch (error) {
            toast({
                title: "Error adding room.",
                description: error.response.data.error,
                status: "error",
                duration: 3000,
                isClosable: true,
            });
        }
    };

    return (
        <Flex
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            boxShadow="lg"
            m={4}
            p={4}
            alignItems="center"
            justifyContent="space-between"
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
            bg="white"
        >
            <Image
                src={hotel.image ? hotel.image.url : DefaultHotelImage}
                alt={`Image of ${hotel.hotelName}`}
                height="100px"
                width="100px"
                objectFit="cover"
                mr={4}
            />
            <Box flex="1">
                <Text fontWeight="bold" fontSize="xl">{hotel.hotelName}</Text>
                <Text>{hotel.address}</Text>
                <Text>{`${hotel.city}, ${hotel.country}`}</Text>
            </Box>
            {hovered && (
                <Flex direction="row" mr={4}>
                    <IconButton
                        icon={<CalendarIcon />}
                        isRound="true"
                        variant="outline"
                        colorScheme="blue"
                        mr={2}
                        aria-label="View Reservations"
                        _hover={{ bg: 'blue.400', color: 'white' }}
                    />
                    <ReactRouterLink to={`/hotel/${hotel.hotelId}`}>
                        <IconButton
                            icon={<ViewIcon />}
                            isRound="true"
                            variant="outline"
                            colorScheme="green"
                            mr={2}
                            aria-label="View Hotel"
                            _hover={{ bg: 'green.500', color: 'white' }}
                        />
                    </ReactRouterLink>
                    <IconButton
                        icon={<EditIcon />}
                        isRound="true"
                        variant="outline"
                        colorScheme="orange"
                        mr={2}
                        aria-label="Edit Hotel"
                        _hover={{ bg: 'orange.400', color: 'white' }}
                        onClick={openEditModal}
                    />
                    <IconButton
                        icon={<DeleteIcon />}
                        isRound="true"
                        variant="outline"
                        colorScheme="red"
                        aria-label="Delete Hotel"
                        _hover={{ bg: 'red.600', color: 'white' }}
                        onClick={openConfirmModal}
                    />
                </Flex>
            )}
            <Menu>
                <MenuButton
                    as={IconButton}
                    aria-label="Options"
                    icon={<HamburgerIcon />}
                    variant="outline"
                />
                <MenuList>
                    <MenuItem
                        as={ReactRouterLink}
                        to={`/hotel/${hotel.hotelId}`}
                    >
                        See Hotel
                    </MenuItem>
                    <MenuItem
                        onClick={openEditModal}
                    >
                        Edit Hotel
                    </MenuItem>
                    <MenuItem
                        color="red"
                        onClick={openConfirmModal}
                    >
                        Delete Hotel
                    </MenuItem>
                </MenuList>
            </Menu>
            <Modal isOpen={isEditModalOpen} onClose={() => setIsEditModalOpen(false)}>
                <ModalOverlay backdropFilter="blur(10px)" />
                <ModalContent>
                    <ModalHeader>Edit {hotel.hotelName}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <VStack spacing={4} align="stretch">
                            <FormControl>
                                <FormLabel>Hotel Name</FormLabel>
                                <Input
                                    name="hotelName"
                                    value={editFormData.hotelName}
                                    onChange={handleEditFormChange}
                                />
                            </FormControl>
                            <FormControl>
                                <FormLabel>Country</FormLabel>
                                <Input
                                    name="country"
                                    value={editFormData.country}
                                    onChange={handleEditFormChange}
                                />
                            </FormControl>
                            <FormControl>
                                <FormLabel>City</FormLabel>
                                <Input
                                    name="city"
                                    value={editFormData.city}
                                    onChange={handleEditFormChange}
                                />
                            </FormControl>
                            <FormControl>
                                <FormLabel>Address</FormLabel>
                                <Input
                                    name="address"
                                    value={editFormData.address}
                                    onChange={handleEditFormChange}
                                />
                            </FormControl>
                            <FormControl>
                                <FormLabel>Reception Number</FormLabel>
                                <Input
                                    name="receptionNumber"
                                    value={editFormData.receptionNumber}
                                    onChange={handleEditFormChange}
                                />
                            </FormControl>
                        </VStack>
                        <Button
                            onClick={() => setIsAddingNewRoom(!isAddingNewRoom)}
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
                            mt={4}
                        >
                            Add New Room
                        </Button>
                        {isAddingNewRoom && (
                            <form onSubmit={handleSubmitNewRoom}>
                                <FormControl mt={4} isInvalid={errors.roomNumber} isRequired>
                                    <FormLabel>Room Number</FormLabel>
                                    <InputGroup>
                                        <InputLeftAddon children='№' />
                                        <Input
                                            type="number"
                                            name="roomNumber"
                                            value={newRoomData.roomNumber}
                                            onChange={handleChange}
                                            required
                                        />
                                    </InputGroup>

                                    {errors.roomNumber && <FormErrorMessage>{errors.roomNumber}</FormErrorMessage>}
                                </FormControl>
                                <FormControl mt={4} isInvalid={errors.category} isRequired>
                                    <FormLabel>Category</FormLabel>
                                    <Select
                                        placeholder="Select category"
                                        name="category"
                                        value={newRoomData.category}
                                        onChange={handleChange}
                                        required
                                    >
                                        <option value="DELUXE">DELUXE</option>
                                        <option value="STANDARD">STANDARD</option>
                                    </Select>
                                    {errors.category && <FormErrorMessage>{errors.category}</FormErrorMessage>}
                                </FormControl>
                                <FormControl mt={4} isInvalid={errors.pricePerNight} isRequired>
                                    <FormLabel>Price Per Night</FormLabel>
                                    <InputGroup>
                                        <InputLeftAddon children='Kč' />
                                        <Input
                                            type="number"
                                            name="pricePerNight"
                                            value={newRoomData.pricePerNight}
                                            onChange={handleChange}
                                            required
                                        />
                                    </InputGroup>
                                    {errors.pricePerNight && <FormErrorMessage>{errors.pricePerNight}</FormErrorMessage>}
                                </FormControl>
                                <Button
                                    mt={4}
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
                                    type="submit"
                                    onClick={handleSubmitNewRoom}
                                >
                                    Submit New Room
                                </Button>
                            </form>
                        )}
                    </ModalBody>
                    <ModalFooter flexDirection="column" alignItems="flex-start">
                        <Button
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
                            w="full"
                            mb={2}
                            onClick={handleEditHotel}
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
                            w="full"
                            onClick={() => setIsEditModalOpen(false)}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
            <Modal isOpen={isConfirmModalOpen} onClose={() => setIsConfirmModalOpen(false)}>
                <ModalOverlay backdropFilter="blur(10px)" />
                <ModalContent>
                    <ModalHeader>Confirm Hotel Deletion</ModalHeader>
                    <ModalCloseButton />
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
                            onClick={handleDelete}
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
                            onClick={() => setIsConfirmModalOpen(false)}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </Flex>
    );
};

export default AdminHotelCard;
