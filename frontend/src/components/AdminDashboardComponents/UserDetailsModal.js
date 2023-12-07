// UserDetailsModal.js
import React from 'react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    Text,
    Flex, Divider, ModalFooter, Button, Box
} from '@chakra-ui/react';

const UserDetailsModal = ({ user, isOpen, onClose }) => {
    return (
        <Modal isOpen={isOpen} onClose={onClose}>
            <ModalOverlay backdropFilter="blur(10px)" />
            <ModalContent>
                <ModalHeader
                    fontSize="2xl"
                    textAlign="center"
                    bg="green.500"
                    color="white"
                >
                    {user.firstName} {user.lastName}
                </ModalHeader>
                <ModalCloseButton color="white" />
                <ModalBody>
                    <Flex direction="column" gap={4}>
                        <Box>
                            <Text fontWeight="bold">Role:</Text>
                            <Text color={user.role === 'ADMIN' ? 'yellow.500' : 'green.500'}>{user.role}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Email:</Text>
                            <Text>{user.email}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Phone:</Text>
                            <Text>{user.phoneNumber}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Country:</Text>
                            <Text>{user.country}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">Region:</Text>
                            <Text>{user.region}</Text>
                        </Box>
                        <Box>
                            <Text fontWeight="bold">City:</Text>
                            <Text>{user.city}</Text>
                        </Box>
                    </Flex>
                </ModalBody>
                <Divider />
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
                        onClick={onClose}>
                        Close
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
};

export default UserDetailsModal;
