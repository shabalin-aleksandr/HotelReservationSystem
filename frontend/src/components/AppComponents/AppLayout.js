import React, {useState} from 'react';
import {Box} from '@chakra-ui/react';
import AppHeader from "./AppHeader";
import AppFooter from "./AppFooter";
import {UserDetailsContext} from "../../utils/context/UserDetailContext";
import SearchContext from "../../utils/context/SearchContext"
import {AdminDetailsContext} from "../../utils/context/AdminDetailsContext";

export function AppLayout({ children }) {
    const [userDetails, setUserDetails] = useState(null);
    const [adminDetails, setAdminDetails] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearch = (term) => {
        setSearchTerm(term);
    };

    return (
        <SearchContext.Provider value={{ searchTerm, setSearchTerm }}>
            <UserDetailsContext.Provider value={{ userDetails, setUserDetails }}>
                <AdminDetailsContext.Provider value={{adminDetails, setAdminDetails}}>
                    <AppHeader onSearch={handleSearch} />
                    <Box mt={5} mb={5}>
                        {children}
                    </Box>
                    <AppFooter />
                </AdminDetailsContext.Provider>
            </UserDetailsContext.Provider>
        </SearchContext.Provider>
    );
}

