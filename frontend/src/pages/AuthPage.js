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
    Alert,
    AlertIcon
} from '@chakra-ui/react';
import {useContext, useState} from "react";
import {useNavigate} from "react-router-dom";
import {AuthContext} from "../components/AuthContext";
import {MAIN_PAGE_ROUTE, REGISTRATION_ROUTE} from "../utils/routes";
import {login} from '../services/UserService/authService';
import {LoadingSpinner} from "../components/LoadingSpinner";


const AuthPage = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const [errors, setErrors] = useState({ email: false, password: false });
    const [serverError, setServerError] = useState("");
    const navigate = useNavigate();
    const [show, setShow] = useState(false)
    const { setIsAuthenticated } = useContext(AuthContext);
    const handleClick = () => setShow(!show)
    const handleEmail = (event) => setEmail(event.target.value);
    const handlePassword = (event) => setPassword(event.target.value);

    const handleAuthentication = async (event) => {
        event.preventDefault();
        setErrors({ email: !email, password: !password, invalidEmail: false, invalidPassword: false });

        if (!email || !password) {
            return;
        }
        setIsLoading(true);

        try {
            const user = await login(email, password, setIsAuthenticated);
            console.log('Logged in user:', user);
            navigate(MAIN_PAGE_ROUTE);
        } catch (error) {
            console.error('Failed to register user', error);
            setServerError(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    if (isLoading) {
        return <LoadingSpinner />
    }

    return (
        <Box display="flex" justifyContent="center" alignItems="center" minH="100vh">
            <Stack spacing={8} mx="auto" maxW="lg" py={12} px={6}>
                <Stack align="center">
                    <Heading>Log In</Heading>
                </Stack>
                <Box rounded="lg" bg="white" boxShadow="lg" p={8} color="black">
                    <form onSubmit={handleAuthentication}>
                        <Stack spacing={4}>
                            <FormControl id="email" isInvalid={errors.email}>
                                <FormLabel>Email address</FormLabel>
                                <Input
                                    type="email"
                                    placeholder="Your email"
                                    value={email}
                                    onChange={handleEmail}
                                />
                                {errors.email && <FormErrorMessage>Email is required</FormErrorMessage>}
                            </FormControl>
                            <FormControl id="password" isInvalid={errors.password}>
                                <FormLabel>Password</FormLabel>
                                <InputGroup size='md'>
                                    <Input
                                        pr='4.5rem'
                                        type={show ? 'text' : 'password'}
                                        placeholder='Your password'
                                        value={password}
                                        onChange={handlePassword}
                                    />
                                    <InputRightElement width='4.5rem'>
                                        <Button h='1.75rem' size='sm' onClick={handleClick}>
                                            {show ? 'Hide' : 'Show'}
                                        </Button>
                                    </InputRightElement>
                                </InputGroup>
                                {errors.password && <FormErrorMessage>Password is required</FormErrorMessage>}
                            </FormControl>
                            <FormControl>
                                <FormLabel>Don't have an account yet?</FormLabel>
                                <Box mx={2}></Box>
                                <Link href={REGISTRATION_ROUTE} color="#2faff5">
                                    Register now
                                </Link>
                            </FormControl>
                            <Button
                                type="submit"
                                colorScheme="green"
                                size="lg"
                                fontSize="md"
                                loadingText="Signing in"
                                onClick={handleAuthentication}
                            >
                                Sign in
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

export default AuthPage;