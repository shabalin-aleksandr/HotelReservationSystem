import React, {useState} from 'react';
import {Box} from '@chakra-ui/react';
import {AppHeader} from "./AppHeader";
import {AppFooter} from "./AppFooter";
import {UserDetailsContext} from "../utils/UserDetailContext";

export function AppLayout({ children }) {
    const [userDetails, setUserDetails] = useState(null);

    return (
        <UserDetailsContext.Provider value={{ userDetails, setUserDetails }}>
            <AppHeader />
            <Box mt={5} mb={5}>{children}</Box>
            <AppFooter />
        </UserDetailsContext.Provider>
    );
}
