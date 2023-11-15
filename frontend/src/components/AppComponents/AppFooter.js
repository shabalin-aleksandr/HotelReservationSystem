import React from 'react';
import { Box, Divider, Text } from '@chakra-ui/react';

const AppFooter = () => {
    return (
        <Box
            as="footer"
            h="50px"
            display="flex"
            alignItems="center"
            flexDirection="column"
        >
            <Divider />
            <Box as="footer" p={4}>
                <Text as="p">
                    &copy; {new Date().getFullYear()} &middot; Shabalin & Zaitsau
                </Text>
            </Box>
        </Box>
    );
}

export default AppFooter;
