import React, {useContext, useMemo} from 'react';
import {Box, SimpleGrid} from '@chakra-ui/react';
import HotelCard from "../components/MainPageComponents/HotelCard";
import SearchContext from "../utils/context/SearchContext";
import {useHotels} from "../utils/context/HotelContext";

const MainPage = () => {
    const { hotels, removeHotel } = useHotels();
    const { searchTerm } = useContext(SearchContext);



    const filteredAndSortedHotels = useMemo(() => {
        const sortFunction = (a, b) => {
            if (b.rating !== a.rating) {
                return b.rating - a.rating;
            }
            return a.hotelName.localeCompare(b.hotelName);
        };

        return searchTerm
            ? hotels
                .filter(
                    (hotel) =>
                        hotel.hotelName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                        hotel.country.toLowerCase().includes(searchTerm.toLowerCase()) ||
                        hotel.city.toLowerCase().includes(searchTerm.toLowerCase())
                )
                .sort(sortFunction)
            : hotels.sort(sortFunction);
    }, [searchTerm, hotels]);


    return (
        <Box padding="4">
            <SimpleGrid columns={[1, 2, 3, 4]} spacing="4">
                {filteredAndSortedHotels.map((hotel) => (
                    <HotelCard
                        key={hotel.hotelId}
                        hotel={hotel}
                        onHotelDeleted={removeHotel}
                    />
                ))}
            </SimpleGrid>
        </Box>
    );
}

export default MainPage;
