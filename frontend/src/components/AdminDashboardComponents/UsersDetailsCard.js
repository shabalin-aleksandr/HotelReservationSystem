import React from 'react';
import {Avatar, Box, HStack, IconButton, Text, useToast} from '@chakra-ui/react';
import DefaultAvatar from "../../images/default-avatar.png";
import {DeleteIcon} from "@chakra-ui/icons";
import {deleteUser} from "../../services/UserService/userService";

const UserDetailsCard = ({ user, onDeleteSuccess, onClick }) => {
    const toast = useToast();

    const handleDelete = async (e) => {
        e.stopPropagation();
        try {
            await deleteUser(user.userId);
            onDeleteSuccess(user.userId);
            toast({
                title: 'User deleted.',
                description: `User ${user.email} has been successfully removed.`,
                status: 'success',
                duration: 3000,
                isClosable: true,
            });
        } catch (error) {
            toast({
                title: 'Error deleting user.',
                description: error.message,
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    return (
        <HStack
            p={5}
            shadow="md"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            justifyContent="space-between"
            alignItems="center"
            onClick={() => onClick(user)}
            cursor="pointer"
        >
            <Avatar
                src={user.avatar ? user.avatar.url : DefaultAvatar}
                size="xl"
                alignSelf="center"
            />
            <Box flex="1" ml={4}>
                <Text fontWeight="bold" fontSize="lg" mt={-2}>{user.firstName} {user.lastName}</Text>
                <Text fontSize="sm">{user.email}</Text>
            </Box>
            <IconButton
                aria-label="Delete user"
                isRound="true"
                colorScheme="red"
                variant="outline"
                _hover={{ bg: 'red.600', color: 'white' }}
                icon={<DeleteIcon />}
                onClick={(e) => handleDelete(e)}
            />
        </HStack>
    );
};

export default UserDetailsCard;
