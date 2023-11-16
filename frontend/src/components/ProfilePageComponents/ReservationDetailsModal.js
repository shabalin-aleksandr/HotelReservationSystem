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
    ListItem,
} from '@chakra-ui/react';

const ReservationDetailsModal = ({isOpen, onClose, reservation, room}) => {
    console.log('Reservation Data:', reservation);

    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)"/>
            <ModalContent>
                <ModalHeader>Reservation Details</ModalHeader>
                <ModalCloseButton/>
                <ModalBody>
                    <Text fontWeight="bold">Room Number:</Text>
                    <Text mb={4}>{room?.roomNumber}</Text>

                    <Text fontWeight="bold">Category:</Text>
                    <Text mb={4}>{room?.category}</Text>

                    <Text fontWeight="bold">Price Per Night:</Text>
                    <Text mb={4}>{room?.pricePerNight} Kč</Text>

                    <Text fontWeight="bold">Total Price:</Text>
                    <Text mb={4}>{reservation?.totalPrice} Kč</Text>

                    <Text fontWeight="bold">Amenities:</Text>
                    <UnorderedList>
                        {room?.amenities.map((amenity) => (
                            <ListItem key={amenity.amenityId}>{amenity.amenityName} - {amenity.description}</ListItem>
                        ))}
                    </UnorderedList>
                </ModalBody>
                <ModalFooter>
                    <Button colorScheme="blue" mr={3} onClick={onClose}>
                        Close
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default ReservationDetailsModal;
