import {api} from "../../utils/api";
import jwt_decode from "jwt-decode";
import {MAIN_PAGE_ROUTE} from "../../utils/routes";

export const login = async (email, password, setIsAuthenticated) => {
    try {
        const { data } = await api.post(`/auth/authenticate`, {email, password});
        const decoded = jwt_decode(data.token);
        localStorage.setItem('token', data.token)
        localStorage.setItem('userId', decoded.email);
        if(decoded.adminId) {
            localStorage.setItem('adminId', decoded.adminId);
        }
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

export const registerAdmin = async (firstName,
                               lastName,
                               email,
                               password,
                               phoneNumber,
                               country,
                               region,
                               city,
                               setIsAuthenticated) => {
    try {
        const {data} = await api.post(`auth/admin/register`,
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