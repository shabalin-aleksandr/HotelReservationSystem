// Assuming each hotel object has a property called 'country'

// ... (other imports)

import HotelCard from "../components/MainPageComponents/HotelCard";
import {useContext, useMemo} from "react";
import {Box, SimpleGrid} from "@chakra-ui/react";
import {useHotels} from "../utils/context/HotelContext";
import SearchContext from "../utils/context/SearchContext";

const MainPage = () => {
    const { hotels } = useHotels();
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
        <Box padding="4" display="flex" flexDirection="row">
            <Box flex="3">
                <SimpleGrid columns={[1, 2, 3, 4]} spacing="4">
                    {filteredAndSortedHotels.map((hotel) => (
                        <HotelCard key={hotel.hotelId} hotel={hotel} />
                    ))}
                </SimpleGrid>
            </Box>
        </Box>
    );
};

export default MainPage;