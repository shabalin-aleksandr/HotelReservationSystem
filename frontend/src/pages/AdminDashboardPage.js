import {Box, Button, Divider, Heading, SimpleGrid, Text} from "@chakra-ui/react";
import React, {useContext, useEffect, useState} from "react";
import {AdminDetailsContext} from "../utils/context/AdminDetailsContext";
import {getAdminDetails, getAdminTypeFromToken} from "../services/UserService/adminService";
import {useParams} from "react-router-dom";
import {LoadingSpinner} from "../components/AppComponents/LoadingSpinner";
import AdminHotelCard from "../components/AdminDashboardComponents/AdminHotelCard";
import HotelRoomsModal from "../components/AdminDashboardComponents/HotelRoomsModal";
import CreateHotelModal from "../components/MainPageComponents/CreateHotelModal";
import {getAllUsersDetails} from "../services/UserService/userService";
import UserDetailsCard from "../components/AdminDashboardComponents/UsersDetailsCard";
import UserDetailsModal from "../components/AdminDashboardComponents/UserDetailsModal";

const AdminDashboardPage = () => {
    const {adminId} = useParams();
    const adminType = getAdminTypeFromToken();
    const isSuperAdmin = adminType === 'SUPER_ADMIN';
    const [users, setUsers] = useState([]);
    const {adminDetails, setAdminDetails} = useContext(AdminDetailsContext);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [selectedHotelId, setSelectedHotelId] = useState(null);
    const [isRoomsModalOpen, setIsRoomsModalOpen] = useState(false);
    const [isCreateHotelModalOpen, setIsCreateHotelModalOpen] = useState(false);
    const [selectedUser, setSelectedUser] = useState(null);
    const [isUserModalOpen, setIsUserModalOpen] = useState(false);
    const sortedHotels = adminDetails
        ? [...adminDetails.hotels].sort((a, b) => a.hotelName.localeCompare(b.hotelName))
        : [];
    const toggleCreateHotelModal = () => setIsCreateHotelModalOpen(!isCreateHotelModalOpen);

    useEffect(() => {
        const fetchAdminDetails = async () => {
            setIsLoading(true);
            try {
                const details = await getAdminDetails(adminId);
                setAdminDetails(details);
            } catch (error) {
                console.error('Error fetching admin details:', error);
                setError(error);
            } finally {
                setIsLoading(false);
            }
        };
        fetchAdminDetails();
    }, [adminId, setAdminDetails]);

    useEffect(() => {
        if (isSuperAdmin) {
            setIsLoading(true);
            getAllUsersDetails()
                .then(data => {
                    setUsers(data);
                    setIsLoading(false);
                })
                .catch(error => {
                    console.error('Failed to fetch users details:', error);
                    setError(error);
                    setIsLoading(false);
                });
        }
    }, [isSuperAdmin]);

    useEffect(() => {
        if (isSuperAdmin) {
            getAllUsersDetails()
                .then(setUsers)
                .catch(console.error);
        }
    }, [isSuperAdmin]);

    const showRoomsForHotel = (hotelId) => {
        setSelectedHotelId(hotelId);
        setIsRoomsModalOpen(true);
    };

    const updateHotelInState = (updatedHotel) => {
        setAdminDetails(prevDetails => ({
            ...prevDetails,
            hotels: prevDetails.hotels.map(hotel => {
                if (hotel.hotelId === updatedHotel.hotelId) {
                    return updatedHotel;
                }
                return hotel;
            })
        }));
    };

    const removeHotelFromState = (hotelId) => {
        setAdminDetails(prevDetails => ({
            ...prevDetails,
            hotels: prevDetails.hotels.filter(hotel => hotel.hotelId !== hotelId)
        }));
    };

    const handleUserDelete = (userId) => {
        setUsers(currentUsers => currentUsers.filter(user => user.userId !== userId));
    };

    const handleUserClick = (user) => {
        setSelectedUser(user);
        setIsUserModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsUserModalOpen(false);
        setSelectedUser(null);
    };


    if (isLoading) {
        return <LoadingSpinner/>
    }

    if (error) {
        return <Text>{error.message}</Text>
    }

    return (
        <Box p={8}>
            <Heading as="h2" size="lg" mb={4}>Admin Dashboard</Heading>
            {sortedHotels.length > 0 ? (
                <SimpleGrid columns={[1, 2, 3]} spacing="4">
                    {sortedHotels.map(hotel => (
                        <AdminHotelCard
                            key={hotel.hotelId}
                            hotel={hotel}
                            onHotelDeleted={removeHotelFromState}
                            onHotelUpdated={updateHotelInState}
                            onShowRooms={showRoomsForHotel}
                        />
                    ))}
                </SimpleGrid>
            ) : (
                <Box textAlign="center" p={4} mt={6}>
                    <Text
                        fontWeight="bold"
                        fontSize="xl"
                        mb={4}
                    >
                        You currently don't have any hotels.
                    </Text>
                    <Button
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
                        mt={3}
                        onClick={toggleCreateHotelModal}
                    >
                        Create Hotel
                    </Button>
                    <CreateHotelModal isOpen={isCreateHotelModalOpen} onClose={toggleCreateHotelModal}/>
                </Box>
            )}
            <Divider mt={3}/>
            {isSuperAdmin && users.length > 0 && (
                <Box p={8} mt={1}>
                    <Heading as="h3" size="lg" mb={4}>User List</Heading>
                    <SimpleGrid columns={{sm: 1, md: 2, lg: 3}} spacing={4}>
                        {users.map(user => (
                            <UserDetailsCard
                                key={user.userId}
                                user={user}
                                onClick={handleUserClick}
                                onDeleteSuccess={handleUserDelete}
                            />
                        ))}
                    </SimpleGrid>
                    {selectedUser && (
                        <UserDetailsModal
                            user={selectedUser}
                            isOpen={isUserModalOpen}
                            onClose={handleCloseModal}
                        />
                    )}
                </Box>
            )}
            {isRoomsModalOpen && (
                <HotelRoomsModal
                    isOpen={isRoomsModalOpen}
                    onClose={() => setIsRoomsModalOpen(false)}
                    hotelId={selectedHotelId}
                />
            )}
        </Box>
    );
};

export default AdminDashboardPage;
