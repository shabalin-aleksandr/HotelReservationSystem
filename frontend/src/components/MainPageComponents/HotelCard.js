import React from 'react';
import {Box, Image, Text, Flex, Heading, useColorModeValue, Button, Stack} from '@chakra-ui/react';
import RatingStars from "../ProfilePageComponents/RatingStars";
import DefaultHotelImage from '../../images/default-hotel-image.png'
import {Link as ReactRouterLink} from 'react-router-dom';
import {HOTEL_ROUTE} from "../../utils/routes";

const HotelCard = ({ hotel }) => {
    const bg = useColorModeValue('white', 'gray.800');

    return (
        <Box
            maxW="sm"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
        >
            <Image
                src={hotel.imageUrl || DefaultHotelImage}
                alt={`Image of ${hotel.hotelName}`}
                height="200px"
                width="100%"
                objectFit="contain"
            />
            <Box p="6">
                <Heading size="lg" mb="2">{hotel.hotelName}</Heading>
                <Text fontWeight="bold">{hotel.address}</Text>
                <Text mb="2">{`${hotel.city}, ${hotel.country}`}</Text>
                <Flex align="center">
                    <RatingStars rating={hotel.rating} />
                </Flex>
                <Stack direction="row" spacing={4} mt="3">
                    <Button
                        as={ReactRouterLink}
                        to={`${HOTEL_ROUTE}/${hotel.hotelId}`}
                        flex={1}
                        fontSize="sm"
                        rounded="full"
                        bg="green.400"
                        color="white"
                        boxShadow="0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)"
                        _hover={{
                            bg: 'green.500',
                        }}
                        _focus={{
                            bg: 'green.500',
                        }}
                    >
                        View Hotel
                    </Button>
                </Stack>
            </Box>
        </Box>
    );
};

export default HotelCard;