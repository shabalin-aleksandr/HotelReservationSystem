import axios from 'axios';

const API_TOKEN = process.env.REACT_APP_UNIVERSAL_TUTORIAL_API_TOKEN;
const API_URL = process.env.REACT_APP_UNIVERSAL_TUTORIAL_BASE_URL;
const API_USER_EMAIL = process.env.REACT_APP_UNIVERSAL_TUTORIAL_USER_EMAIL;
export const fetchCountries = async () => {
    try {
        const accessToken = await getAccessToken();
        const response = await axios.get(`${API_URL}/countries/`, {
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Accept": "application/json"
            }
        });
        return response.data.map(({ country_name }) => ({ label: country_name, value: country_name }));
    } catch (error) {
        console.error('Error fetching countries:', error);
        throw error;
    }
};

export const fetchRegions = async (country) => {
    try {
        const accessToken = await getAccessToken();
        const response = await axios.get(`${API_URL}/states/${country}`, {
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Accept": "application/json"
            }
        });
        return response.data.map(({ state_name }) => ({ label: state_name, value: state_name }));
    } catch (error) {
        console.error('Error fetching regions:', error);
        throw error;
    }
};

export const fetchCities = async (region) => {
    try {
        const accessToken = await getAccessToken();
        const response = await axios.get(`${API_URL}/cities/${region}`, {
            headers: {
                "Authorization": `Bearer ${accessToken}`,
                "Accept": "application/json"
            }
        });
        return response.data.map(({ city_name }) => ({ label: city_name, value: city_name }));
    } catch (error) {
        console.error('Error fetching cities:', error);
        throw error;
    }
};

export const getAccessToken = async () => {
    try {
        const response = await axios.get(`${API_URL}/getaccesstoken`, {
            headers: {
                "Accept": "application/json",
                "api-token": API_TOKEN,
                "user-email": API_USER_EMAIL
            }
        });
        return response.data.auth_token;
    } catch (error) {
        console.error('Error getting access token:', error);
        throw error;
    }
};