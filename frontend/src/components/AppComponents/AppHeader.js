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
    MenuDivider, Divider, Avatar, Input, Flex, InputRightElement, InputGroup
} from "@chakra-ui/react";
import {ChevronDownIcon, SearchIcon} from "@chakra-ui/icons"
import {LOGIN_ROUTE, MAIN_PAGE_ROUTE, PROFILE_ROUTE, REGISTRATION_ROUTE} from "../../utils/routes";
import {AuthContext} from "./AuthContext";
import {getUserDetails} from '../../services/UserService/userService';
import {logout} from '../../services/UserService/authService';
import DefaultAvatar from "../../images/default-avatar.png";
import {UserDetailsContext} from "../../utils/context/UserDetailContext";
import SearchContext from "../../utils/context/SearchContext";


const AppHeader = ({ onSearch }) => {
    const userId = localStorage.getItem('userId');
    const {isAuthenticated} = useContext(AuthContext);
    const {userDetails, setUserDetails} = useContext(UserDetailsContext);
    const { searchTerm, setSearchTerm } = useContext(SearchContext);
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

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
            <Flex
                as="header"
                h="100px"
                bg="white"
                align="center"
                justify="space-between"
                px={8}
                boxShadow="sm"
            >
                <HStack spacing={8}>
                    <Heading color="black">
                        <ReactRouterLink to={MAIN_PAGE_ROUTE}>🛏️ Hotel Reservation System</ReactRouterLink>
                    </Heading>
                    <Box d="flex" alignItems="center">
                        <InputGroup size="md" width="auto">
                            <Input
                                type="search"
                                placeholder="Search hotels..."
                                value={searchTerm}
                                onChange={handleSearchChange}
                                pr="2.5rem" // Adjust padding to fit the icon
                            />
                            <InputRightElement>
                                <SearchIcon color="gray.500" />
                            </InputRightElement>
                        </InputGroup>
                    </Box>
                </HStack>
                <HStack spacing={4}>
                    {!isAuthenticated && (
                        <>
                            <Button
                                as={ReactRouterLink}
                                to={LOGIN_ROUTE}
                                colorScheme='green'
                            >
                                Log In
                            </Button>
                            <Button
                                as={ReactRouterLink}
                                to={REGISTRATION_ROUTE}
                                colorScheme='green'
                            >
                                Sign Up
                            </Button>
                        </>
                    )}
                    {isAuthenticated && userDetails && (
                        <Menu>
                            {({isOpen}) => (
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
                                            <MenuDivider/>
                                            <MenuItem
                                                as={ReactRouterLink}
                                                to={`${PROFILE_ROUTE}/${userId}`}
                                            >
                                                My Account
                                            </MenuItem>
                                        </MenuGroup>
                                        <MenuDivider/>
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
            </Flex>
            <Divider/>
        </>
    );
}

export default AppHeader;