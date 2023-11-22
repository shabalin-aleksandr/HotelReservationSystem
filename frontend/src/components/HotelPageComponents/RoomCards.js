import React from 'react';
import { Image, Text, Flex, Heading, useColorModeValue, Button, Stack } from '@chakra-ui/react';
import DefaultHotelImage from '../../images/room.png';
import { Link as ReactRouterLink } from 'react-router-dom';
import { HOTEL_ROUTE } from "../../utils/routes";

const RoomCards = ({ room }) => {
    const bg = useColorModeValue('white', 'gray.800');

    return (
        <Flex
            maxW="xl"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            bg={bg}
            boxShadow="lg"
            m={4}
            width="100%"
        >
            <Image
                src={room.image ? room.image.url : DefaultHotelImage}
                alt={`Image of ${room.roomNumber}`}
                height="200px"
                width="35%"
                objectFit="contain"
            />
            <Flex
                direction="column"
                p="6"
                flex="1"
                justifyContent="space-between"
                align="center"
            >
                <Flex direction="column" align="flex-start">
                    <Heading size="lg" mb="2">{room.roomNumber}</Heading>
                    <Text fontWeight="bold">{room.category}</Text>
                    <Text mb="2">{`${room.pricePerNight}`}</Text>
                    <Text mb="2">{`${room.amenities}`}</Text>
                </Flex>
                <Stack direction="row" spacing={4} mt="3">
                    <Button
                        as={ReactRouterLink}
                        to={`${HOTEL_ROUTE}/${room.roomid}`}
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
                        View Room
                    </Button>
                </Stack>
            </Flex>
        </Flex>
    );
};

export default RoomCards;
