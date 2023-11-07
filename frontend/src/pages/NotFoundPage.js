import React from 'react';
import { Box, Button, Heading, Text, Center } from "@chakra-ui/react";
import { Link } from "react-router-dom";
import {MAIN_PAGE_ROUTE} from "../utils/routes";

const NotFoundPage = () => (
    <Center h="100vh">
        <Box textAlign="center">
            <Heading mb={4}>404: Not Found</Heading>
            <Text mb={4}>The page you are looking for does not exist.</Text>
            <Button colorScheme="green" as={Link} to={MAIN_PAGE_ROUTE}>
                Go to Home Page
            </Button>
        </Box>
    </Center>
);

export default NotFoundPage;