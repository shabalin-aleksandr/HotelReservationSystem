import React, {useContext, useEffect, useState} from 'react';
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
    Image,
    MenuDivider, Divider, Avatar, Input, Flex, InputRightElement, InputGroup, useColorModeValue
} from "@chakra-ui/react";
import {ChevronDownIcon, SearchIcon} from "@chakra-ui/icons"
import {
    ADMIN_DASHBOARD_ROUTE,
    LOGIN_ROUTE,
    MAIN_PAGE_ROUTE,
    PROFILE_ROUTE,
    REGISTRATION_ROUTE
} from "../../utils/routes";
import {AuthContext} from "./AuthContext";
import {getUserDetails} from '../../services/UserService/userService';
import {logout} from '../../services/UserService/authService';
import DefaultAvatar from "../../images/default-avatar.png";
import {UserDetailsContext} from "../../utils/context/UserDetailContext";
import SearchContext from "../../utils/context/SearchContext";
import CreateHotelModal from "../MainPageComponents/CreateHotelModal";
import HotelIcon from "../../images/header-icon.png";


const AppHeader = () => {
    const userId = localStorage.getItem('userId');
    const adminId = localStorage.getItem('adminId');
    const {isAuthenticated} = useContext(AuthContext);
    const {userDetails, setUserDetails} = useContext(UserDetailsContext);
    const { searchTerm, setSearchTerm } = useContext(SearchContext);
    const [isCreateHotelModalOpen, setIsCreateHotelModalOpen] = useState(false);
    const bg = useColorModeValue('white', 'gray.800');
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };
    const toggleCreateHotelModal = () => setIsCreateHotelModalOpen(!isCreateHotelModalOpen);

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
                bg={bg}
                align="center"
                justify="space-between"
                px={8}
                boxShadow="sm"
            >
                <HStack spacing={8}>
                    <Heading color="black">
                        <ReactRouterLink to={MAIN_PAGE_ROUTE}>
                            <Flex align="center">
                                <Box mr={2}>
                                    <Image src={HotelIcon} alt="Hotel" boxSize="40px" />
                                </Box>
                                Hotel Reservation System
                            </Flex>
                        </ReactRouterLink>
                    </Heading>
                    <Box d="flex" alignItems="center">
                        <InputGroup size="md" width="auto">
                            <Input
                                type="search"
                                placeholder="Search hotels..."
                                value={searchTerm}
                                onChange={handleSearchChange}
                                pr="2.5rem"
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
                                flex={1}
                                fontSize="sm"
                                rounded="full"
                                bg="transparent"
                                color="green.500"
                                border="2px"
                                borderColor="green.500"
                                _hover={{
                                    bg: 'green.500',
                                    color: 'white',
                                }}
                                _focus={{
                                    boxShadow: '0 0 0 3px rgba(66, 153, 225, 0.6)',
                                }}
                            >
                                Log In
                            </Button>
                            <Button
                                as={ReactRouterLink}
                                to={REGISTRATION_ROUTE}
                                flex={1}
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
                            >
                                Sign Up
                            </Button>
                        </>
                    )}
                    {isAuthenticated && userDetails && (
                        <Menu>
                            {({isOpen}) => (
                                <>
                                    {isAuthenticated && userDetails?.role === 'ADMIN' && (
                                        <Button
                                            flex={1}
                                            fontSize="sm"
                                            rounded="full"
                                            bg="transparent"
                                            color="gray.800"
                                            border="2px"
                                            borderColor="gray.200"
                                            _hover={{
                                                bg: 'green.500',
                                                color: 'white',
                                                borderColor: 'green.500'
                                            }}
                                            _focus={{
                                                boxShadow: '0 0 0 3px rgba(66, 153, 225, 0.6)',
                                            }}
                                            onClick={toggleCreateHotelModal}
                                        >
                                            Create Hotel
                                        </Button>

                                    )}
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
                                            {isAuthenticated && userDetails?.role === 'ADMIN' && (
                                                <MenuItem
                                                    as={ReactRouterLink}
                                                    to={`${ADMIN_DASHBOARD_ROUTE}/${adminId}`}
                                                >
                                                    Admin Dashboard
                                                </MenuItem>
                                            )}
                                        </MenuGroup>
                                        <MenuDivider/>
                                        <MenuGroup>
                                            <MenuItem
                                                color="red"
                                                onClick={logout}>Log Out</MenuItem>
                                        </MenuGroup>
                                    </MenuList>
                                    <CreateHotelModal isOpen={isCreateHotelModalOpen} onClose={toggleCreateHotelModal} />
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