import React, {useContext, useMemo} from 'react';
import {Box, SimpleGrid} from '@chakra-ui/react';
import HotelCard from "../components/MainPageComponents/HotelCard";
import SearchContext from "../utils/context/SearchContext";
import {useHotels} from "../utils/context/HotelContext";

const MainPage = () => {
    const { hotels } = useHotels();
    const { searchTerm } = useContext(SearchContext);

    const filteredAndSortedHotels = useMemo(() => {
        return searchTerm
            ? hotels
                .filter(hotel =>
                    hotel.hotelName.toLowerCase().includes(searchTerm.toLowerCase()))
                .sort((a, b) => b.rating - a.rating)
            : hotels.sort((a, b) => b.rating - a.rating);
    }, [searchTerm, hotels]);

    return (
        <Box padding="4">
            <SimpleGrid columns={[1, 2, 3, 4]} spacing="4">
                {filteredAndSortedHotels.map((hotel) => (
                    <HotelCard key={hotel.hotelId} hotel={hotel} />
                ))}
            </SimpleGrid>
        </Box>
    );
}

export default MainPage;
