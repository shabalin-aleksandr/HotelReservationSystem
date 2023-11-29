import {Box, Heading, SimpleGrid, Text} from "@chakra-ui/react";
import React, {useContext, useEffect, useState} from "react";
import {AdminDetailsContext} from "../utils/context/AdminDetailsContext";
import {getAdminDetails} from "../services/UserService/adminService";
import {useParams} from "react-router-dom";
import {LoadingSpinner} from "../components/AppComponents/LoadingSpinner";
import AdminHotelCard from "../components/AdminDashboardComponents/AdminHotelCard";
import HotelRoomsModal from "../components/AdminDashboardComponents/HotelRoomsModal";

const AdminDashboardPage = () => {
    const { adminId } = useParams();
    const { adminDetails, setAdminDetails } = useContext(AdminDetailsContext);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [selectedHotelId, setSelectedHotelId] = useState(null);
    const [isRoomsModalOpen, setIsRoomsModalOpen] = useState(false);
    const sortedHotels = adminDetails
        ? [...adminDetails.hotels].sort((a, b) => a.hotelName.localeCompare(b.hotelName))
        : [];

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
        return <LoadingSpinner />
    }

    if (error) {
        return <Text>{error.message}</Text>
    }

    return (
        <Box>
            <Heading as="h2" size="lg" mb={4}>Admin Dashboard</Heading>
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

export default  AdminDashboardPage;
