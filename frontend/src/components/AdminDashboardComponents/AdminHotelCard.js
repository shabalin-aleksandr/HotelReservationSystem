import React, { useState } from 'react';
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
    useToast, ModalOverlay, ModalHeader, ModalCloseButton, ModalBody, Button, ModalFooter, ModalContent, Modal,
} from '@chakra-ui/react';
import { HamburgerIcon, EditIcon, DeleteIcon, ViewIcon } from '@chakra-ui/icons';
import { Link as ReactRouterLink } from 'react-router-dom';
import DefaultHotelImage from "../../images/default-hotel-image.png";
import {deleteHotel} from "../../services/HotelService/hotelService";

const AdminHotelCard = ({ hotel, onHotelDeleted }) => {
    const [hovered, setHovered] = useState(false);
    const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
    const toast = useToast();
    const openConfirmModal = () => {
        setIsConfirmModalOpen(true);
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
                    <MenuItem>Edit Hotel</MenuItem>
                    <MenuItem
                        color="red"
                        onClick={openConfirmModal}
                    >
                        Delete Hotel
                    </MenuItem>
                </MenuList>
            </Menu>
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
