import {
    Alert,
    AlertDescription,
    AlertIcon,
    AlertTitle,
    Avatar,
    Box,
    Button,
    Center,
    Grid,
    GridItem,
    Heading,
    Input,
    Modal,
    ModalBody,
    ModalContent,
    ModalOverlay, SimpleGrid,
    Table,
    Tbody,
    Td,
    Text,
    Tr, useColorModeValue,
    useToast,
    VStack
} from "@chakra-ui/react";
import {deleteAvatar, uploadAvatar} from "../services/UserService/userAvatarService";
import {getUserDetails, updateUserDetails} from '../services/UserService/userService';
import {useContext, useEffect, useRef, useState} from "react";
import {AuthContext} from "../components/AppComponents/AuthContext";
import {LoadingSpinner} from "../components/AppComponents/LoadingSpinner";
import {useLocation, useParams} from "react-router-dom";
import DefaultAvatar from "../images/default-avatar.png"
import AvatarEditorWrapper from "../components/ProfilePageComponents/AvatarEditorWrapper";
import { UserDetailsContext } from "../utils/UserDetailContext";
import UpdateInformationForm from "../components/ProfilePageComponents/UpdateInformationForm";
import {getHotelDetails} from "../services/HotelService/hotelService";
import UserReservationCard from "../components/ProfilePageComponents/UserReservationCard";

const ProfilePage = () => {
    const { userId } = useParams();
    const { setIsAuthenticated } = useContext(AuthContext);
    const { userDetails, setUserDetails } = useContext(UserDetailsContext);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState('');
    const [isHovering, setIsHovering] = useState(false);
    const fileInputRef = useRef();
    const [selectedImage, setSelectedImage] = useState(null);
    const [showEditor, setShowEditor] = useState(false);
    const [showUpdateAlert, setShowUpdateAlert] = useState(false);
    const [showDeleteAlert, setShowDeleteAlert] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const toast = useToast();
    const bg = useColorModeValue('white', 'gray.800');
    const {
        countries,
        regions,
        cities} = useLocation();

    useEffect(() => {
        const fetchData = async () => {
            setIsLoading(true);
            try {
                const userDetailsResponse = await getUserDetails(userId);
                setUserDetails(userDetailsResponse);

                const hotelDetailsPromises = userDetailsResponse.reservations.map(reservation =>
                    getHotelDetails(reservation.hotelId)
                );
                const hotels = await Promise.all(hotelDetailsPromises);

                const combinedDetails = userDetailsResponse.reservations.map((reservation, index) => {
                    return { ...reservation, hotelDetails: hotels[index] };
                });

                setUserDetails({...userDetailsResponse, reservations: combinedDetails});
            } catch (error) {
                console.error('Failed to fetch data', error);
                setError(error);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();
    }, [userId, setUserDetails]);

    useEffect(() => {
        if (showUpdateAlert) {
            toast({
                position: "top",
                duration: 2000,
                render: () => (
                    <Alert status="success"
                           variant="subtle"
                           flexDirection="column"
                           alignItems="center"
                           justifyContent="center"
                           textAlign="center"
                           height="200px"
                    >
                        <AlertIcon boxSize="40px" mr={0} />
                        <AlertTitle mt={4} mb={1} fontSize="lg">
                            Avatar updated successfully!
                        </AlertTitle>
                        <AlertDescription maxWidth="sm">
                            Your avatar has been updated and changes are now live.
                        </AlertDescription>
                    </Alert>
                )
            })
        }
    }, [showUpdateAlert, toast]);

    const handleAvatarUpload = async (event) => {
        const file = event.target.files[0];
        event.target.value = null;

        try {
            setSelectedImage(URL.createObjectURL(file));
            setShowEditor(true);
        } catch (error) {
            console.error('Failed to upload avatar', error);
        }
    };

    const handleAvatarDelete = async () => {
        try {
            const response = await deleteAvatar();
            console.log('Avatar has been successfully deleted', response);
            setUserDetails(prevState => ({
                ...prevState,
                avatar: null
            }));
            setShowDeleteAlert(true);
        } catch (error) {
            console.error('Failed to delete avatar', error);
        }
    };

    useEffect(() => {
        if (showDeleteAlert) {
            toast({
                position: "top",
                duration: 2000,
                render: () => (
                    <Alert status="success"
                           variant="subtle"
                           flexDirection="column"
                           alignItems="center"
                           justifyContent="center"
                           textAlign="center"
                           height="200px"
                    >
                        <AlertIcon boxSize="40px" mr={0} />
                        <AlertTitle mt={4} mb={1} fontSize="lg">
                            Avatar deleted successfully!
                        </AlertTitle>
                        <AlertDescription maxWidth="sm">
                            Your avatar has been removed successfully.
                        </AlertDescription>
                    </Alert>
                )
            });
            setShowDeleteAlert(false);
        }
    }, [showDeleteAlert, toast]);

    const handleSaveEditedAvatar = async (imgData, imgDataScaled) => {
        fileInputRef.current.value = null;
        const fetchRes = await fetch(imgDataScaled);
        const blob = await fetchRes.blob();
        const file = new File([blob], 'newAvatar.png', { type: 'image/png' });

        try {
            const response = await uploadAvatar(file, setIsAuthenticated);
            console.log('Avatar upload response:', response);
            setUserDetails(prevState => ({
                ...prevState,
                avatar: { url: response.avatar.url }
            }));
            setShowEditor(false);
            setShowUpdateAlert(true);
        } catch (error) {
            console.error('Failed to upload avatar', error);
        }
    }

    const openFileSelectionDialog = () => {
        fileInputRef.current.click();
    }

    const handleUpdateUserDetails = async (updates) => {
        try {
            const userId = localStorage.getItem('userId');
            const updatedUser = await updateUserDetails(userId, updates);
            setUserDetails(updatedUser);
            setIsEditing(false);
        } catch (error) {
            console.error('Failed to update user details', error);
        }
    };

    const renderReservations = () => {
        if (userDetails.reservations && userDetails.reservations.length > 0) {
            return (
                <SimpleGrid columns={{ sm: 1, md: 2, lg: 3 }} spacing={10}>
                    {userDetails.reservations.map((reservation) => (
                        <UserReservationCard key={reservation.reservationId} reservation={reservation} />
                    ))}
                </SimpleGrid>
            );
        } else {
            return (
                <Box textAlign="center" p={4} mt={6}>
                    <Text fontWeight="bold" fontSize="xl">
                        You don't have any reservations yet, reserve your first room!
                    </Text>
                    <Button colorScheme="green" mt={3}>Reserve</Button>
                </Box>
            );
        }
    };

    if (isLoading) {
        return <LoadingSpinner />
    }

    if (error) {
        return <Text>{error.message}</Text>
    }

    return (
        <Grid
            templateAreas={[
                `"nav main"`,
            ]}
            templateColumns="1fr 2fr"
            gap={6}
            p={5}
        >
            {userDetails && (
                <>
                    <GridItem area={'nav'}>
                        <Center>
                            <VStack spacing={5}>
                                <Box
                                    onMouseEnter={() => setIsHovering(true)}
                                    onMouseLeave={() => setIsHovering(false)}
                                    position="relative"
                                >
                                    <Avatar
                                        src={userDetails.avatar ? userDetails.avatar.url : DefaultAvatar}
                                        borderRadius='full'
                                        boxSize='300px'
                                        alt="User Avatar"
                                        onClick={openFileSelectionDialog}
                                        cursor="pointer"
                                    />
                                    {isHovering && (
                                        <VStack
                                            position="absolute"
                                            bottom="0"
                                            left="0"
                                            width="100%"
                                            align="center"
                                            spacing={1}
                                        >
                                            <Button
                                                onClick={openFileSelectionDialog}
                                                size="sm"
                                                color="green"
                                            >
                                                Update Avatar
                                            </Button>
                                            {userDetails.avatar && (
                                                <Button
                                                    onClick={handleAvatarDelete}
                                                    size="sm"
                                                    color="red"
                                                >
                                                    Delete Avatar
                                                </Button>
                                            )}
                                        </VStack>
                                    )}
                                    <Input
                                        type="file"
                                        accept="image/*"
                                        ref={fileInputRef}
                                        onChange={handleAvatarUpload}
                                        style={{ display: 'none' }}
                                    />
                                </Box>
                                <Box textAlign="center">
                                    <Text fontSize='40px' fontWeight='bold'>
                                        {userDetails.firstName} {userDetails.lastName}
                                    </Text>
                                    <Text fontSize='25px' color='blackAlpha.700'>
                                        {userDetails.email}
                                    </Text>
                                    <Button
                                        size='md'
                                        mt={4}
                                        bg={bg}
                                        border='2px'
                                        borderColor='green.500'
                                        _hover={{
                                            bg: 'green.100',
                                        }}
                                        onClick={() => setIsEditing(true)}
                                    >
                                        Edit Profile
                                    </Button>
                                    {isEditing && userDetails && (
                                        <UpdateInformationForm
                                            userId={userDetails.userId}
                                            onClose={() => setIsEditing(false)}
                                            onSubmit={handleUpdateUserDetails}
                                            userDetails={userDetails}
                                            countries={countries}
                                            regions={regions}
                                            cities={cities}
                                        />

                                    )}
                                </Box>
                                <Table variant="simple">
                                    <Tbody>
                                        <Tr>
                                            <Td fontWeight='bold' color='blackAlpha.700'>Country</Td>
                                            <Td>{userDetails.country}</Td>
                                        </Tr>
                                        <Tr>
                                            <Td fontWeight='bold' color='blackAlpha.700'>Region</Td>
                                            <Td>{userDetails.region}</Td>
                                        </Tr>
                                        <Tr>
                                            <Td fontWeight='bold' color='blackAlpha.700'>City</Td>
                                            <Td>{userDetails.city}</Td>
                                        </Tr>
                                    </Tbody>
                                </Table>
                            </VStack>
                        </Center>
                    </GridItem>
                    <GridItem area={'main'}>
                        <Heading size="xl" mb={6}>Reservations</Heading>
                        {renderReservations()}
                    </GridItem>
                </>
            )}
            <Modal isOpen={showEditor} onClose={() => setShowEditor(false)}>
                <ModalOverlay />
                <ModalContent>
                    <ModalBody>
                        <AvatarEditorWrapper
                            image={selectedImage}
                            onClose={() => setShowEditor(false)}
                            onSubmit={handleSaveEditedAvatar}
                        />
                    </ModalBody>
                </ModalContent>
            </Modal>
        </Grid>
    );
};

export default ProfilePage;
