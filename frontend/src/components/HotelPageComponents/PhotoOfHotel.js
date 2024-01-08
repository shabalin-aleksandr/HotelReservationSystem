import React, { useState } from 'react';
import {Box, Image, Text, Flex, Button, Heading, Center} from '@chakra-ui/react';
import RatingStars from "../ProfilePageComponents/RatingStars";
import hotelImage1 from "../../images/default-hotel-image.png";
import hotelImage2 from "../../images/hotel_image_2.jpg";
import hotelImage3 from "../../images/hotel_image_3.jpeg";
import { ChevronLeftIcon, ChevronRightIcon } from '@chakra-ui/icons';

const PhotoOfHotel = ({ hotel }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const photos = [hotelImage1, hotelImage2, hotelImage3];

    const handleNextClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex + 1) % photos.length);
    };

    const handleBeforeClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex - 1 + photos.length) % photos.length);
    };

    return (
        <Box>
            <Center position="relative">
                <Image
                    src={photos[currentPhotoIndex]}
                    height="300px"
                    width="200%"
                    objectFit="contain"
                />
                <Button
                    onClick={handleBeforeClick}
                    position="absolute"
                    left="10px"
                    top="50%"
                    transform="translateY(-50%)"
                    size="sm"
                    colorScheme="green"
                    variant="ghost"
                    _hover={{ bg: 'green.300', transform: 'translateY(-50%) scale(1.1)' }}
                >
                    <ChevronLeftIcon />
                </Button>
                <Button
                    onClick={handleNextClick}
                    position="absolute"
                    right="10px"
                    top="50%"
                    transform="translateY(-50%)"
                    size="sm"
                    colorScheme="green"
                    variant="ghost"
                    _hover={{ bg: 'green.300', transform: 'translateY(-50%) scale(1.1)' }}
                >
                    <ChevronRightIcon />
                </Button>
            </Center>
            <Box p="6">
                <Heading size="lg" mb="2">{hotel.address}</Heading>
                <Text mb="5">{`${hotel.city}, ${hotel.country}`}</Text>
                <Flex align="center">
                    <RatingStars rating={hotel.rating} />
                </Flex>
            </Box>
        </Box>
    );
};

export default PhotoOfHotel;