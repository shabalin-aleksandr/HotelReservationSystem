import React, { useState } from 'react';
import {
    Image,
    Text,
    Flex,
    Heading,
    useColorModeValue,
    Button,
    Stack,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
} from '@chakra-ui/react';
import DefaultRoomImage from '../../images/room.png';
import ViewRoomCard from './ViewRoomCard';

const RoomCards = ({ room }) => {
    const bg = useColorModeValue('white', 'gray.800');
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleViewRoom = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    return (
        <Flex
            maxW="xl"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
            width="100%"
        >
            <Image
                src={room.image ? room.image.url : DefaultRoomImage}
                alt={`Image of ${room.roomNumber}`}
                height="200px"
                width="35%"
                objectFit="contain"
            />
            <Flex
                direction="column"
                p="6"
                flex="1"
                justifyContent="space-between"
                align="center"
            >
                <Flex direction="column" align="flex-start">
                    <Heading size="lg" mb="2" ml="7">
                        №{room.roomNumber}
                    </Heading>
                    <Text fontWeight="bold">Category: {room.category}</Text>
                    <Text mb="2">Price Per Night: {`${room.pricePerNight}`} Kč</Text>
                </Flex>
                <Stack direction="row" spacing={4} mt="3">
                    <Button
                        flex={1}
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
                        onClick={handleViewRoom} // Open the modal on button click
                    >
                        View Room
                    </Button>
                </Stack>
            </Flex>

            {/* Modal for detailed room view */}
            <Modal isOpen={isModalOpen} onClose={handleCloseModal}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Room Details</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {/* Pass the handleCloseModal function to ViewRoomCard */}
                        <ViewRoomCard room={room} handleCloseModal={handleCloseModal} />
                    </ModalBody>
                    <ModalFooter>
                        {/* Add any modal footer content here */}
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </Flex>
    );
};

export default RoomCards;
