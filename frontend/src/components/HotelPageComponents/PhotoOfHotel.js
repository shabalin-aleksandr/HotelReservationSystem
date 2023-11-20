import React, { useState } from 'react';
import {Box, Image, Text, Flex, Button, Stack, Heading, VStack, Center} from '@chakra-ui/react';
import RatingStars from "../ProfilePageComponents/RatingStars";
import hotelImage1 from "../../images/default-hotel-image.png";
import hotelImage2 from "../../images/one_more_Hotel_Photo.png";
import {ButtonGroup} from "react-bootstrap";

const PhotoOfHotel = ({ hotel }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const photos = [hotelImage1, hotelImage2 /* Add more photo URLs or import statements as needed */];


    const handleNextClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex + 1) % photos.length);
    };
    const handleBeforeClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex - 1 + photos.length) % photos.length);
    };

    return (
        <Box>
            <Center>
            <Image
                src={photos[currentPhotoIndex]}
                height="300px"
                width="200%"
                objectFit="contain"
            />
            </Center>
            <Center>
            <Stack direction="row" spacing={4} mt="3">
            <ButtonGroup
                disableElevation
                variant="contained"
                aria-label="Disabled elevation buttons"
            >


                <Button
                    onClick={handleBeforeClick}
                    size="sm"
                    color="green"
                >
                    Before Photo
                </Button>

                <Button
                    onClick={handleNextClick}
                    size="sm"
                    color="green"
                >
                    Next Photo

                </Button>

            </ButtonGroup>
            </Stack>
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
