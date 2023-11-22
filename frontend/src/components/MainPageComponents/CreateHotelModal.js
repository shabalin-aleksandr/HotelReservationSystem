import React, { useState } from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    FormControl,
    FormLabel,
    Input,
    Button,
} from '@chakra-ui/react';
import {createHotel} from "../../services/HotelService/hotelService";

const CreateHotelModal = ({ isOpen, onClose }) => {
    const [hotels, setHotels] = useState([]);

    const [hotelForm, setHotelForm] = useState({
        hotelName: '',
        country: '',
        city: '',
        address: '',
        receptionNumber: '',
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setHotelForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newHotel = await createHotel(
                hotelForm.hotelName,
                hotelForm.country,
                hotelForm.city,
                hotelForm.address,
                hotelForm.receptionNumber
            );
            console.log('Hotel created:', newHotel);
            setHotels(prevHotels => [...prevHotels, newHotel]);
            onClose();
        } catch (error) {
            console.error('Failed to create hotel:', error);
        }
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay />
            <ModalContent>
                <ModalHeader>Create a New Hotel</ModalHeader>
                <ModalCloseButton />
                <ModalBody pb={6}>
                    <FormControl>
                        <FormLabel>Hotel Name</FormLabel>
                        <Input
                            placeholder="Hotel Name"
                            name="hotelName"
                            value={hotelForm.hotelName}
                            onChange={handleInputChange}
                        />
                    </FormControl>

                    <FormControl mt={4}>
                        <FormLabel>Country</FormLabel>
                        <Input
                            placeholder="Country"
                            name="country"
                            value={hotelForm.country}
                            onChange={handleInputChange}
                        />
                    </FormControl>

                    <FormControl mt={4}>
                        <FormLabel>City</FormLabel>
                        <Input
                            placeholder="City"
                            name="city"
                            value={hotelForm.city}
                            onChange={handleInputChange}
                        />
                    </FormControl>

                    <FormControl mt={4}>
                        <FormLabel>Address</FormLabel>
                        <Input
                            placeholder="Address"
                            name="address"
                            value={hotelForm.address}
                            onChange={handleInputChange}
                        />
                    </FormControl>

                    <FormControl mt={4}>
                        <FormLabel>Reception Number</FormLabel>
                        <Input
                            placeholder="Reception Number"
                            name="receptionNumber"
                            value={hotelForm.receptionNumber}
                            onChange={handleInputChange}
                        />
                    </FormControl>
                </ModalBody>

                <ModalFooter>
                    <Button colorScheme="blue" mr={3} onClick={handleSubmit}>
                        Create
                    </Button>
                    <Button onClick={onClose}>Cancel</Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default CreateHotelModal;
