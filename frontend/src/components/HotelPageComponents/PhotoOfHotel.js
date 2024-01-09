import React, {useEffect, useState} from 'react';
import {
    Box,
    Image,
    Text,
    Flex,
    Button,
    Heading,
    Center,
    useDisclosure,
    ModalOverlay,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    NumberInput,
    NumberInputField,
    ModalContent,
    Modal,
    ModalFooter,
    NumberIncrementStepper,
    NumberInputStepper,
    NumberDecrementStepper,
} from '@chakra-ui/react';
import RatingStars from "../ProfilePageComponents/RatingStars";
import hotelImage1 from "../../images/default-hotel-image.png";
import hotelImage2 from "../../images/hotel_image_2.jpg";
import hotelImage3 from "../../images/hotel_image_3.jpeg";
import {ChevronLeftIcon, ChevronRightIcon} from '@chakra-ui/icons';
import {addRatingToHotel} from "../../services/HotelService/hotelService";


const PhotoOfHotel = ({ hotel }) => {
    const [currentPhotoIndex, setCurrentPhotoIndex] = useState(0);
    const photos = [hotelImage1, hotelImage2, hotelImage3];
    const [ratingValue, setRatingValue] = useState(0);
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [hasRated, setHasRated] = useState(false);
    const [currentHotelRating, setCurrentHotelRating] = useState(hotel.rating);

    useEffect(() => {
        const ratedHotels = JSON.parse(localStorage.getItem('ratedHotels')) || {};
        if (ratedHotels[hotel.hotelId]) {
            setHasRated(true);
        }
    }, [hotel.hotelId]);

    useEffect(() => {
        setCurrentHotelRating(hotel.rating);
    }, [hotel.rating]);

    const handleNextClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex + 1) % photos.length);
    };

    const handleBeforeClick = () => {
        setCurrentPhotoIndex((prevIndex) => (prevIndex - 1 + photos.length) % photos.length);
    };

    const handleRateHotel = async () => {
        try {
            await addRatingToHotel(hotel.hotelId, ratingValue);
            const ratedHotels = JSON.parse(localStorage.getItem('ratedHotels')) || {};
            ratedHotels[hotel.hotelId] = true;
            localStorage.setItem('ratedHotels', JSON.stringify(ratedHotels));
            setHasRated(true);
            setCurrentHotelRating(ratingValue);
            onClose();
        } catch (error) {
            console.error('Error rating hotel:', error);
        }
    };

    const enhancedOnOpen = () => {
        if (!hasRated) {
            onOpen();
        }
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
                    <RatingStars rating={currentHotelRating} />
                </Flex>
                <Button
                    mt={3}
                    colorScheme={hasRated ? "gray" : "green"}
                    onClick={enhancedOnOpen}
                    disabled={hasRated}
                    bg={hasRated ? 'gray.400' : 'green.400'}
                    color="white"
                    _hover={{ bg: hasRated ? 'gray.400' : 'green.500' }}
                    cursor={hasRated ? 'not-allowed' : 'pointer'}
                    opacity={hasRated ? 0.6 : 1}
                    fontSize="sm"
                    rounded="full"
                    boxShadow="0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)"
                    _focus={{
                        bg: 'green.500',
                    }}
                >
                    {hasRated ? 'Already Rated' : 'Rate Hotel'}
                </Button>

            </Box>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Rate Hotel</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <NumberInput
                            max={5}
                            min={0}
                            step={1}
                            onChange={(valueString) => {
                                const value = parseFloat(valueString);
                                setRatingValue(isNaN(value) ? 0 : value);
                            }}
                            value={ratingValue}
                            clampValueOnBlur={false}
                        >
                            <NumberInputField />
                            <NumberInputStepper>
                                <NumberIncrementStepper />
                                <NumberDecrementStepper />
                            </NumberInputStepper>
                        </NumberInput>
                    </ModalBody>
                    <ModalFooter>
                        <Button
                            mr={3}
                            onClick={handleRateHotel}
                            fontSize="sm"
                            rounded="full"
                            bg="green.500"
                            color="white"
                            _hover={{
                                bg: 'green.600',
                            }}
                            _focus={{
                                bg: 'green.600',
                            }}
                        >
                            Submit Rating
                        </Button>
                        <Button
                            onClick={onClose}
                            fontSize="sm"
                            rounded="full"
                            bg="transparent"
                            color="gray.800"
                            border="2px"
                            borderColor="gray.300"
                            _hover={{
                                bg: 'gray.200',
                                color: 'black',
                                borderColor: 'gray.300'
                            }}
                            _focus={{
                                bg: 'gray.200',
                            }}
                        >
                            Cancel
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </Box>
    );
};

export default PhotoOfHotel;