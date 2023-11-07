import { useState, useEffect } from "react";
import {fetchCities, fetchCountries, fetchRegions} from "../../services/locationService";

export function  useLocation() {
    const [country, setCountry] = useState("");
    const [countries, setCountries] = useState([]);
    const [region, setRegion] = useState('');
    const [regions, setRegions] = useState([]);
    const [city, setCity] = useState("");
    const [cities, setCities] = useState([]);

    useEffect(() => {
        const getCountries = async () => {
            const countries = await fetchCountries();
            console.log('Fetched countries:', countries);
            setCountries(countries);
        };

        getCountries();
    }, []);

    useEffect(() => {
        const getRegions = async () => {
            const regions = await fetchRegions(country);
            console.log('Fetched regions:', regions);
            setRegions(regions);
        };

        if (country) {
            getRegions();
        }
    }, [country]);

    useEffect(() => {
        const getCities = async () => {
            const cities = await fetchCities(region);
            console.log('Fetched cities:', cities);
            setCities(cities);
        };

        if (region) {
            getCities();
        }
    }, [region]);

    return { country, region, city, countries, regions, cities, setCountry, setRegion, setCity };
}
