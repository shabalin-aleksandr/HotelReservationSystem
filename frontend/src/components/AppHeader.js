import React, {useContext, useEffect} from 'react';
import {Link as ReactRouterLink} from 'react-router-dom';
import {
    Box,
    Heading,
    HStack,
    Button,
    Menu,
    MenuButton,
    MenuList,
    MenuGroup,
    MenuItem,
    MenuDivider, Divider, Avatar
} from "@chakra-ui/react";
import {ChevronDownIcon} from "@chakra-ui/icons"
import {LOGIN_ROUTE, MAIN_PAGE_ROUTE, PROFILE_ROUTE, REGISTRATION_ROUTE} from "../utils/routes";
import {AuthContext} from "./AuthContext";
import {getUserDetails, logout} from "../services/userService";
import DefaultAvatar from "../images/default-avatar.png"
import { UserDetailsContext } from "../utils/UserDetailContext";


export function AppHeader() {
    const userId  = localStorage.getItem('userId');
    const { isAuthenticated } = useContext(AuthContext);
    const { userDetails, setUserDetails } = useContext(UserDetailsContext);

    useEffect(() => {
        if (isAuthenticated) {
            getUserDetails()
                .then((response) => {
                    setUserDetails(response);
                })
                .catch((error) => {
                    console.error('Failed to fetch user details', error);
                });
        } else {
            setUserDetails(null);
        }
    }, [isAuthenticated, setUserDetails]);


    return (
        <>
            <Box
                as="header"
                h="100px"
                bg="white"
                display="flex"
                alignItems="center"
                justifyContent="space-between"
            >
                <Heading ml={8} color="black">
                    <ReactRouterLink to={MAIN_PAGE_ROUTE}>🛏️ Hotel Reservation System</ReactRouterLink>
                </Heading>
                <Box mr={4}>
                    <HStack p={2} spacing={6}>
                        {!isAuthenticated && (
                            <>
                                <Button
                                    color="green"
                                    as={ReactRouterLink}
                                    to={LOGIN_ROUTE}
                                >
                                    Log In
                                </Button>
                                <Button
                                    colorScheme='green'
                                    as={ReactRouterLink}
                                    to={REGISTRATION_ROUTE}
                                >
                                    Sign Up
                                </Button>
                            </>
                        )}
                        {isAuthenticated && userDetails && (
                            <Menu>
                                {({ isOpen }) => (
                                    <>
                                        <MenuButton as={Box} borderRadius='full' cursor="pointer">
                                            <HStack spacing={0}>
                                                <Avatar src={userDetails.avatar ? userDetails.avatar.url : DefaultAvatar}
                                                />
                                                <ChevronDownIcon
                                                    boxSize="1.5rem"
                                                    transform={isOpen ? "rotate(180deg)" : "rotate(0)"}
                                                    transition="transform 0.3s ease-in-out"
                                                />
                                            </HStack>
                                        </MenuButton>
                                        <MenuList>
                                            <MenuGroup title={userDetails.email} fontSize='20px'>
                                                <MenuDivider />
                                                <MenuItem
                                                    as={ReactRouterLink}
                                                    to={`${PROFILE_ROUTE}/${userId}`}
                                                >
                                                    My Account
                                                </MenuItem>
                                                <MenuItem
                                                >
                                                    ...
                                                </MenuItem>
                                                <MenuItem
                                                >
                                                    ...
                                                </MenuItem>
                                            </MenuGroup>
                                            <MenuDivider />
                                            <MenuGroup>
                                                <MenuItem
                                                    color="red"
                                                    onClick={logout}>Log Out</MenuItem>
                                            </MenuGroup>
                                        </MenuList>
                                    </>
                                )}
                            </Menu>
                        )}
                    </HStack>
                </Box>
            </Box>
            <Divider />
        </>

    );
}
