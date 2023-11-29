import {Box, Button, Heading, SimpleGrid, Text} from "@chakra-ui/react";
import React, {useContext, useEffect, useState} from "react";
import {AdminDetailsContext} from "../utils/context/AdminDetailsContext";
import {getAdminDetails} from "../services/UserService/adminService";
import {useParams} from "react-router-dom";
import {LoadingSpinner} from "../components/AppComponents/LoadingSpinner";
import AdminHotelCard from "../components/AdminDashboardComponents/AdminHotelCard";
import HotelRoomsModal from "../components/AdminDashboardComponents/HotelRoomsModal";
import CreateHotelModal from "../components/MainPageComponents/CreateHotelModal";

const AdminDashboardPage = () => {
    const {adminId} = useParams();
    const {adminDetails, setAdminDetails} = useContext(AdminDetailsContext);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [selectedHotelId, setSelectedHotelId] = useState(null);
    const [isRoomsModalOpen, setIsRoomsModalOpen] = useState(false);
    const [isCreateHotelModalOpen, setIsCreateHotelModalOpen] = useState(false);
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

    if (isLoading) {
        return <LoadingSpinner/>
    }

    if (error) {
        return <Text>{error.message}</Text>
    }

    return (
        <Box p={8}> {/* This adds padding all around the Box */}
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
