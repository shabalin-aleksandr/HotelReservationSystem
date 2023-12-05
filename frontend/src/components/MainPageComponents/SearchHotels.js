// CountryFilter.js
import React, { useContext } from 'react';
import { Input, Flex, Button } from '@chakra-ui/react';
import SearchContext from '../../utils/context/SearchContext';

const CountryFilter = () => {
    const { countryFilter, updateCountryFilter } = useContext(SearchContext);

    const handleCountryChange = (event) => {
        updateCountryFilter(event.target.value);
    };

    const clearCountryFilter = () => {
        updateCountryFilter('');
    };

    return (
        <Flex mt={4} alignItems="center">
            <Input
                placeholder="Filter by country"
                value={countryFilter}
                onChange={handleCountryChange}
                mr={2}
            />
            <Button size="sm" onClick={clearCountryFilter}>
                Clear
            </Button>
        </Flex>
    );
};

export default CountryFilter;
