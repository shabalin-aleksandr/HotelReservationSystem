import React, {useContext, useEffect, useMemo, useState} from 'react';
import {Box, SimpleGrid} from '@chakra-ui/react';
import {getAllHotels} from "../services/HotelService/hotelService";
import HotelCard from "../components/MainPageComponents/HotelCard";
import SearchContext from "../utils/context/SearchContext";

const MainPage = () => {
    const [hotels, setHotels] = useState([]);
    const { searchTerm } = useContext(SearchContext);

    useEffect(() => {
        async function fetchHotels() {
            const hotelsData = await getAllHotels();
            setHotels(hotelsData);
        }
        fetchHotels();
    }, []);

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
