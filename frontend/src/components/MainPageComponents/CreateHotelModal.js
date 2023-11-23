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
    Button, FormErrorMessage,
} from '@chakra-ui/react';
import {createHotel} from "../../services/HotelService/hotelService";
import {useHotels} from "../../utils/context/HotelContext";

const CreateHotelModal = ({ isOpen, onClose }) => {
    const { addHotel } = useHotels();
    const initialFormState = {
        hotelName: '',
        country: '',
        city: '',
        address: '',
        receptionNumber: '',
    };
    const [formErrors, setFormErrors] = useState(initialFormState);
    const validateForm = () => {
        const errors = {};
        if (!hotelForm.hotelName) errors.hotelName = "Hotel name is required";
        if (!hotelForm.country) errors.country = "Country is required";
        if (!hotelForm.city) errors.city = "City is required";
        if (!hotelForm.address) errors.address = "Address is required";
        if (!hotelForm.receptionNumber) errors.receptionNumber = "Reception number is required";
        return errors;
    };
    const [hotelForm, setHotelForm] = useState(initialFormState);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setHotelForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const errors = validateForm();
        setFormErrors(errors);
        if (Object.keys(errors).length === 0) {
            try {
                const newHotel = await createHotel(
                    hotelForm.hotelName,
                    hotelForm.country,
                    hotelForm.city,
                    hotelForm.address,
                    hotelForm.receptionNumber
                );
                console.log('Hotel created:', newHotel);
                addHotel(newHotel);
                handleModalClose();
            } catch (error) {
                console.error('Failed to create hotel:', error);
            }
        }
    };

    const handleModalClose = () => {
        setHotelForm(initialFormState);
        setFormErrors(initialFormState);
        onClose();
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)" />
            <ModalContent>
                <ModalHeader>Create a New Hotel</ModalHeader>
                <ModalCloseButton />
                <ModalBody pb={6}>
                    <FormControl
                        isInvalid={formErrors.hotelName}
                        isRequired
                    >
                        <FormLabel>Hotel Name</FormLabel>
                        <Input
                            placeholder="Hotel Name"
                            name="hotelName"
                            value={hotelForm.hotelName}
                            onChange={handleInputChange}
                            required
                        />
                        {formErrors.hotelName && <FormErrorMessage>{formErrors.hotelName}</FormErrorMessage>}
                    </FormControl>
                    <FormControl
                        mt={4}
                        isInvalid={formErrors.country}
                        isRequired
                    >
                        <FormLabel>Country</FormLabel>
                        <Input
                            placeholder="Country"
                            name="country"
                            value={hotelForm.country}
                            onChange={handleInputChange}
                            required
                        />
                        {formErrors.country && <FormErrorMessage>{formErrors.country}</FormErrorMessage>}
                    </FormControl>
                    <FormControl
                        mt={4}
                        isInvalid={formErrors.city}
                        isRequired
                    >
                        <FormLabel>City</FormLabel>
                        <Input
                            placeholder="City"
                            name="city"
                            value={hotelForm.city}
                            onChange={handleInputChange}
                            required
                        />
                        {formErrors.city && <FormErrorMessage>{formErrors.city}</FormErrorMessage>}
                    </FormControl>
                    <FormControl
                        mt={4}
                        isInvalid={formErrors.address}
                        isRequired
                    >
                        <FormLabel>Address</FormLabel>
                        <Input
                            placeholder="Address"
                            name="address"
                            value={hotelForm.address}
                            onChange={handleInputChange}
                            required
                        />
                        {formErrors.address && <FormErrorMessage>{formErrors.address}</FormErrorMessage>}
                    </FormControl>
                    <FormControl
                        mt={4}
                        isInvalid={formErrors.receptionNumber}
                        isRequired
                    >
                        <FormLabel>Reception Number</FormLabel>
                        <Input
                            placeholder="Reception Number"
                            name="receptionNumber"
                            value={hotelForm.receptionNumber}
                            onChange={handleInputChange}
                            required
                        />
                        {formErrors.receptionNumber && <FormErrorMessage>{formErrors.receptionNumber}</FormErrorMessage>}
                    </FormControl>
                </ModalBody>
                <ModalFooter>
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
                        mr={3}
                        onClick={handleSubmit}
                    >
                        Create
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
                        onClick={handleModalClose}
                    >
                        Cancel
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default CreateHotelModal;
