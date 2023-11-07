import {api} from "../utils/api";
import jwt_decode from "jwt-decode";
import {MAIN_PAGE_ROUTE} from "../utils/routes";

export const login = async (email, password, setIsAuthenticated) => {
    try {
        const { data } = await api.post(`/auth/authenticate`, {email, password});
        const decoded = jwt_decode(data.token);
        localStorage.setItem('token', data.token)
        localStorage.setItem('userId', decoded.email);
        setIsAuthenticated(true);
        return decoded;
    } catch (error) {
        console.log('Error response:', error.response);
        if (error.response && error.response.status === 409) {
            throw new Error(error.response.data.error);
        } else {
            console.error(error);
            throw error;
        }
    }
};

export const register = async (firstName,
                               lastName,
                               email,
                               password,
                               phoneNumber,
                               country,
                               region,
                               city,
                               setIsAuthenticated) => {
    try {
        const {data} = await api.post(`auth/register`,
            {firstName, lastName, email, password, phoneNumber, country, region, city});
        const decoded = jwt_decode(data.token);
        localStorage.setItem('token', data.token)
        localStorage.setItem('email', decoded.email);
        setIsAuthenticated(true);
        return decoded;
    } catch (error) {
        console.log('Error response:', error.response);
        if (error.response && error.response.status === 409) {
            throw new Error(error.response.data.error);
        } else {
            console.error(error);
            throw error;
        }
    }
};

export const logout = () => {
    localStorage.removeItem('token');
    window.location.href = MAIN_PAGE_ROUTE;
};

export const getUserDetails = async () => {
    const token = localStorage.getItem('token');
    let userId;

    if (token) {
        try {
            const decoded = jwt_decode(token);
            userId = decoded.id; // Use the correct key based on your token's payload structure
        } catch (error) {
            console.error('Error decoding token:', error);
            throw new Error('Invalid token');
        }

        if (!userId) {
            console.error('User ID not found in decoded token');
            throw new Error('User ID not found');
        }
    } else {
        console.error('No token found in localStorage.');
        throw new Error('Authentication required');
    }

    const headers = {
        'Authorization': `Bearer ${token}`,
    };

    try {
        const response = await api.get(`users/${userId}`, { headers });
        console.log('User details:', response.data);
        return response.data;
    } catch (error) {
        console.error('Failed to fetch user details:', error);

        if (error.response) {
            console.error('Error response:', error.response);
            console.error('Error status:', error.response.status);
            console.error('Error data:', error.response.data);
        } else if (error.request) {
            console.error('Error request:', error.request);
        } else {
            console.error('Error message:', error.message);
        }

        console.log('Token:', token);
        console.log('UserID:', userId);
        throw error;
    }
};

