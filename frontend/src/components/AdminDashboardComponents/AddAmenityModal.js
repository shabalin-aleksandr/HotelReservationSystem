import React, { useState } from "react";
import {
    Button,
    FormControl,
    FormLabel,
    Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Textarea,
    useToast,
} from "@chakra-ui/react";
import { createAmenity } from "../../services/AmenityService/amenityservice";

const AddAmenityModal = ({ isOpen, onClose, hotelId, roomId, onAmenityAdded }) => {
    const [amenityName, setAmenityName] = useState('');
    const [description, setDescription] = useState('');
    const toast = useToast();

    const handleCreateAmenity = async () => {
        try {
            const createdAmenity = await createAmenity(hotelId, roomId, amenityName, description);

            toast({
                title: "Amenity created.",
                description: "The amenity has been successfully created.",
                status: "success",
                duration: 3000,
                isClosable: true,
            });

            setAmenityName(''); // Clear the input fields
            setDescription('');
            onAmenityAdded(createdAmenity); // Notify the parent component about the new amenity
            onClose(); // Close the modal
        } catch (error) {
            toast({
                title: "Error creating amenity.",
                description: error.message || "There was an error creating the amenity.",
                status: "error",
                duration: 3000,
                isClosable: true,
            });
        }
    };

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)" />
            <ModalContent>
                <ModalHeader>Add Amenity</ModalHeader>
                <ModalCloseButton />
                <ModalBody>
                    <FormControl>
                        <FormLabel>Amenity Name</FormLabel>
                        <Input
                            value={amenityName}
                            onChange={(e) => setAmenityName(e.target.value)}
                        />
                    </FormControl>
                    <FormControl mt={4}>
                        <FormLabel>Amenity Description</FormLabel>
                        <Textarea
                            value={description}
                            onChange={(e) => setDescription(e.target.value)}
                        />
                    </FormControl>
                </ModalBody>
                <ModalFooter>
                    <Button
                        fontSize="sm"
                        rounded="full"
                        bg="green.500"
                        color="white"
                        _hover={{
                            bg: 'green.600',
                        }}
                        _focus={{
                            bg: 'green.600',
                        }}
                        mr={3}
                        onClick={handleCreateAmenity}
                    >
                        Create Amenity
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
                            borderColor: 'gray.300',
                        }}
                        _focus={{
                            bg: 'gray.200',
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

export default AddAmenityModal;
