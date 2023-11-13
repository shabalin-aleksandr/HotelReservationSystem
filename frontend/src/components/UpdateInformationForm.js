import React, {useState, useRef, useEffect, useContext} from "react";
import {useNavigate} from 'react-router-dom';
import {
    Input,
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    FormControl,
    FormLabel,
    ModalFooter,
    HStack,
    Stack,
    Alert,
    AlertIcon,
    useToast, InputGroup, InputRightElement
} from '@chakra-ui/react';
import {UserDetailsContext} from "../utils/UserDetailContext";
import {updateUserDetails, updateUserPassword} from "../services/UserService/userService";
import Select from "react-select";
import {useLocation} from "../utils/hooks/useLocation";

const UpdateInformationForm = ({ onClose, onSubmit }) => {
    const { userDetails, setUserDetails } = useContext(UserDetailsContext);
    const [updates, setUpdates] = useState(userDetails || {});
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [isChangingPassword, setIsChangingPassword] = useState(false);
    const initialRef = useRef(null);
    const finalRef = useRef(null);
    const [showAlert, setShowAlert] = useState(false);
    const toast = useToast();
    const navigate = useNavigate();
    const [showOldPassword, setShowOldPassword] = useState(false);
    const [showNewPassword, setShowNewPassword] = useState(false);
    const handleClickShowOldPassword = () => setShowOldPassword(!showOldPassword);
    const handleClickShowNewPassword = () => setShowNewPassword(!showNewPassword);
    const { country,
        region,
        city,
        countries,
        regions,
        cities,
        setCountry,
        setRegion,
        setCity  } = useLocation();

    const handleInputChange = (e) => {
        const { name, value} = e.target;
        setUpdates((prevUpdates) => ({ ...prevUpdates, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setShowAlert(true);
        try {
            const updatedUser = await updateUserDetails(userDetails.userId, updates);
            localStorage.setItem('userId', updatedUser.userId);
            onSubmit(updatedUser);
            setUserDetails(updatedUser);
            onClose();
            navigate(`/profile/${updatedUser.userId}`);
        } catch (error) {
            console.error('Failed to update user details', error);
        }
    };

    const handleCountryChange = (selectedOption) => {
        setCountry(selectedOption.value);
        setUpdates(prev => ({...prev, country: selectedOption.value}));
    };

    const handleRegionChange = (selectedOption) => {
        setRegion(selectedOption.value);
        setUpdates(prev => ({...prev, region: selectedOption.value}));
    };

    const handleCityChange = (selectedOption) => {
        setCity(selectedOption.value);
        setUpdates(prev => ({...prev, city: selectedOption.value}));
    };

    const handlePasswordChange = async (e) => {
        e.preventDefault();
        try {
            await updateUserPassword(userDetails.userId, oldPassword, newPassword);
            setIsChangingPassword(false);
            toast({
                title: "Password updated successfully.",
                description: "Your password has been changed.",
                status: "success",
                duration: 5000,
                isClosable: true,
            });
        } catch (error) {
            console.error('Failed to change password', error);
            const errorMessage = error.response?.data?.error || "An error occurred. Please try again.";
            toast({
                title: "Failed to change password.",
                description: errorMessage,
                status: "error",
                duration: 5000,
                isClosable: true,
            });
        }
    };

    useEffect(() => {
        if (showAlert) {
            toast({
                position: "top",
                duration: 2000,
                render: () => (
                    <Alert status="success">
                        <AlertIcon />
                        User information updated successfully!
                    </Alert>
                )
            });
            setShowAlert(false);
        }
    }, [showAlert, toast]);

    return (
        <>
            <Modal
                initialFocusRef={initialRef}
                finalFocusRef={finalRef}
                onSubmit={handleSubmit}
                isOpen={true}
                onClose={onClose}
            >
                <ModalOverlay
                    bg='blackAlpha.300'
                    backdropFilter='blur(10px)'
                />
                <ModalContent>
                    <ModalHeader>Update your information</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody pb={6}>
                        <Stack>
                            <HStack spacing={4}>
                                <FormControl mt={4}>
                                    <FormLabel>First name</FormLabel>
                                    <Input
                                        onChange={handleInputChange}
                                        ref={initialRef}
                                        value={updates.firstName}
                                        type="text"
                                        name="firstName"
                                        placeholder='First name' />
                                </FormControl>
                                <FormControl mt={4}>
                                    <FormLabel>Last name</FormLabel>
                                    <Input
                                        onChange={handleInputChange}
                                        value={updates.lastName}
                                        type="text"
                                        name="lastName"
                                        placeholder='Last name' />
                                </FormControl>
                            </HStack>
                            <FormControl mt={4}>
                                <FormLabel>Country</FormLabel>
                                <Select
                                    options={countries}
                                    value={countries ? countries.find(obj => obj.value === country) : null}
                                    onChange={handleCountryChange}
                                />
                            </FormControl>
                            <FormControl mt={4}>
                                <FormLabel>Region</FormLabel>
                                <Select
                                    options={regions}
                                    value={regions ? regions.find(obj => obj.value === region) : null}
                                    onChange={handleRegionChange}
                                />
                            </FormControl>
                            <FormControl mt={4}>
                                <FormLabel>City</FormLabel>
                                <Select
                                    options={cities}
                                    value={cities ? cities.find(obj => obj.value === city): null}
                                    onChange={handleCityChange}
                                />
                            </FormControl>
                            <Button onClick={() => setIsChangingPassword(!isChangingPassword)} mt={4}>
                                Change password
                            </Button>
                            {isChangingPassword && (
                                <form onSubmit={handlePasswordChange}>
                                    <FormControl mt={4} id="oldPassword">
                                        <FormLabel>Old password</FormLabel>
                                        <InputGroup size='md'>
                                            <Input
                                                pr='4.5rem'
                                                type={showOldPassword ? 'text' : 'password'}
                                                value={oldPassword}
                                                placeholder='Old password'
                                                onChange={e => setOldPassword(e.target.value)}
                                                required
                                            />
                                            <InputRightElement width='4.5rem'>
                                                <Button h='1.75rem' size='sm' onClick={handleClickShowOldPassword}>
                                                    {showOldPassword ? 'Hide' : 'Show'}
                                                </Button>
                                            </InputRightElement>
                                        </InputGroup>
                                    </FormControl>
                                    <FormControl mt={4} id="newPassword">
                                        <FormLabel>New password</FormLabel>
                                        <InputGroup size='md'>
                                            <Input
                                                pr='4.5rem'
                                                type={showNewPassword ? 'text' : 'password'}
                                                value={newPassword}
                                                placeholder='New password'
                                                onChange={e => setNewPassword(e.target.value)}
                                                required
                                            />
                                            <InputRightElement width='4.5rem'>
                                                <Button h='1.75rem' size='sm' onClick={handleClickShowNewPassword}>
                                                    {showNewPassword ? 'Hide' : 'Show'}
                                                </Button>
                                            </InputRightElement>
                                        </InputGroup>
                                    </FormControl>
                                    <Button mt={4} colorScheme="messenger" type="submit">
                                        Submit New Password
                                    </Button>
                                </form>
                            )}
                        </Stack>
                    </ModalBody>
                    <ModalFooter>
                        <Button
                            type="submit"
                            colorScheme='green'
                            mr={3}
                            onClick={handleSubmit}
                        >
                            Save
                        </Button>
                        <Button
                            colorScheme='red'
                            variant='outline'
                            onClick={onClose}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default UpdateInformationForm;