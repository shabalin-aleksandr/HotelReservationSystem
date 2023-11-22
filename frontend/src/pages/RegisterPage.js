import React, {useContext, useState} from 'react';
import {
    Box,
    FormControl,
    FormLabel,
    Input,
    Stack,
    Button,
    Heading,
    Link,
    FormErrorMessage,
    InputGroup,
    InputRightElement,
    HStack,
    Alert,
    AlertIcon, FormHelperText, InputLeftAddon,
} from '@chakra-ui/react';
import { register } from '../services/UserService/authService';
import {useNavigate} from "react-router-dom";
import Select from 'react-select';
import {LOGIN_ROUTE, MAIN_PAGE_ROUTE} from "../utils/routes";
import {LoadingSpinner} from "../components/AppComponents/LoadingSpinner";
import {AuthContext} from "../components/AppComponents/AuthContext";
import {useLocation} from "../utils/hooks/useLocation";

const RegisterPage = () => {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('')
    const { country,
        region,
        city,
        countries,
        regions,
        cities,
        setCountry,
        setRegion,
        setCity  } = useLocation();
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState({nickname: false, email: false, password: false});
    const [serverError, setServerError] = useState("");
    const navigate = useNavigate();
    const [show, setShow] = React.useState(false);
    const { isAuthenticated, setIsAuthenticated } = useContext(AuthContext);
    const [passwordStrength, setPasswordStrength] = useState(null);
    const handleClick = () => setShow(!show)
    const handleFirstName = (event) => setFirstName(event.target.value);
    const handleLastName = (event) => setLastName(event.target.value);
    const handleEmail = (event) => setEmail(event.target.value);
    const handlePassword = (event) => setPassword(event.target.value);
    const handlePhoneNumber = (event) => setPhoneNumber(event.target.value);

    const handleRegistration = async (event) => {
        event.preventDefault();
        const errors = {
            email: !email,
            password: !password,
            invalidEmail: false,
            invalidPassword: false
        };
        setErrors(errors);

        if (errors.email || errors.password) {
            return;
        }
        setIsLoading(true);

        try {
            const user = await register(firstName,
                lastName,
                email,
                password,
                phoneNumber,
                country,
                region,
                city,
                setIsAuthenticated);
            console.log('Registered user:', user)
            navigate(MAIN_PAGE_ROUTE);
        } catch (error) {
            console.error('Failed to register user', error);
            console.log(isAuthenticated)
            setServerError(error.message);
        } finally {
            setIsLoading(false);
        }
    }

    const handlePasswordChange = (e) => {
        const password = e.target.value;
        let strength = 0;

        if (password.length >= 8) {
            strength++;
        }
        if (/[A-Z]/.test(password) && /[a-z]/.test(password)) {
            strength++;
        }
        if (/\d/.test(password)) {
            strength++;
        }
        if (/[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/.test(password)) {
            strength++;
        }
        handlePassword(e);
        setPasswordStrength(strength);
    };

    const handleCountryChange = (selectedOption) => {
        setCountry(selectedOption.value);
    };

    const handleRegionChange = (selectedOption) => {
        setRegion(selectedOption.value);
    };

    const handleCityChange = (selectedOption) => {
        setCity(selectedOption.value);
    };

    if (isLoading) {
        return <LoadingSpinner/>
    }

    return (
        <Box display="flex" justifyContent="center" alignItems="center" minH="100vh">
            <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
                <Stack align="center">
                    <Heading>Registration</Heading>
                </Stack>
                <Box rounded="lg" bg="white" boxShadow="lg" p={8}>
                    <form onSubmit={handleRegistration}>
                        <Stack spacing={4}>
                            <HStack>
                                <FormControl id="name">
                                    <FormLabel>First name</FormLabel>
                                    <Input
                                        type="name"
                                        placeholder="Your first name"
                                        value={firstName}
                                        onChange={handleFirstName}
                                    />
                                </FormControl>
                                <FormControl id="surname">
                                    <FormLabel>Last name</FormLabel>
                                    <Input
                                        type="surname"
                                        placeholder="Your last name"
                                        value={lastName}
                                        onChange={handleLastName}
                                    />
                                </FormControl>
                            </HStack>
                            <FormControl id="phoneNumber" isInvalid={errors.phoneNumber} isRequired>
                                <FormLabel>Phone number</FormLabel>
                                <InputGroup>
                                    <InputLeftAddon children='🇨🇿 +420' />
                                    <Input
                                        type="phoneNumber"
                                        placeholder="Your phone number"
                                        value={phoneNumber}
                                        onChange={handlePhoneNumber}
                                    />
                                </InputGroup>

                            </FormControl>
                            <HStack>
                                <FormControl id="country">
                                    <FormLabel>Country</FormLabel>
                                    <Select
                                        options={countries}
                                        value={countries.find(obj => obj.value === country)}
                                        onChange={handleCountryChange}
                                    />
                                </FormControl>
                                <FormControl id="region">
                                    <FormLabel>Region</FormLabel>
                                    <Select
                                        options={regions}
                                        value={regions.find(obj => obj.value === region)}
                                        onChange={handleRegionChange}
                                    />
                                </FormControl>
                            </HStack>
                            <FormControl id="city">
                                <FormLabel>City</FormLabel>
                                <Select
                                    options={cities}
                                    value={cities.find(obj => obj.value === city)}
                                    onChange={handleCityChange}
                                />
                            </FormControl>
                            <FormControl id="email" isInvalid={errors.email} isRequired>
                                <FormLabel>Email address</FormLabel>
                                <Input
                                    type="email"
                                    placeholder="Your email"
                                    value={email}
                                    onChange={handleEmail}
                                />
                                {errors.email && <FormErrorMessage>Email is required</FormErrorMessage>}
                            </FormControl>
                            <FormControl id="password" isInvalid={errors.password} isRequired>
                                <FormLabel>Password</FormLabel>
                                <InputGroup size='md'>
                                    <Input
                                        pr='4.5rem'
                                        type={show ? 'text' : 'password'}
                                        placeholder='Enter password'
                                        value={password}
                                        onChange={handlePasswordChange}
                                        // onChange={handlePassword}
                                    />
                                    <InputRightElement width='4.5rem'>
                                        <Button h='1.75rem' size='sm' onClick={handleClick}>
                                            {show ? 'Hide' : 'Show'}
                                        </Button>
                                    </InputRightElement>
                                </InputGroup>
                                <FormErrorMessage>
                                    {errors.password ? 'Password is required' : serverError}
                                </FormErrorMessage>
                                <Box display="flex" h="5px" mt={1} bg="gray.300">
                                    <Box
                                        bg={passwordStrength <= 2 ? "red" : passwordStrength <= 3 ? "orange" : "green"}
                                        transition="all 0.3s ease"
                                        h="100%"
                                        w={`${passwordStrength * 25}%`}
                                    />
                                </Box>
                                {serverError && (
                                    <FormHelperText>
                                        <Box pb={1}>• Use at least 8 characters</Box>
                                        <Box pb={1}>• Use upper and lower case characters</Box>
                                        <Box pb={1}>• Use 1 or more numbers</Box>
                                        <Box pb={1}>• Use special characters</Box>
                                    </FormHelperText>
                                )}
                                {errors.password && <FormErrorMessage>Password is required</FormErrorMessage>}
                            </FormControl>
                            <FormControl>
                                <FormLabel>Already have an account?</FormLabel>
                                <Box mx={2}></Box>
                                <Link href={LOGIN_ROUTE} color="#2faff5">
                                    Sign In
                                </Link>
                            </FormControl>
                            <Button
                                type="submit"
                                colorScheme="green"
                                size="lg"
                                fontSize="md"
                                onClick={handleRegistration}
                            >
                                Create Account
                            </Button>
                            {serverError &&
                                <Alert status="error" mt={3}>
                                    <AlertIcon/>
                                    {serverError}
                                </Alert>
                            }
                        </Stack>
                    </form>
                </Box>
            </Stack>
        </Box>
    );
}

export default RegisterPage;
