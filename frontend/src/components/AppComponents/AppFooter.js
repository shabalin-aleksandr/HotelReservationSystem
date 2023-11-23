import React from 'react';
import {Box, Divider, HStack, Link, Text, VStack, Image} from '@chakra-ui/react';
import FaiIcon from "../../images/fai-icon.png"
import OutlookIcon from "../../images/outlook-icon.png";

const AppFooter = () => {
    return (
        <Box
            as="footer"
            h="auto"
            display="flex"
            alignItems="center"
            flexDirection="column"
        >
            <Divider />
            <Box p={4}>
                <VStack>
                    <Text as="p">
                        &copy; {new Date().getFullYear()} &middot; Shabalin & Zaitsau
                    </Text>
                    <Text>Hotel Reservation System</Text>

                    <HStack spacing={4}>
                        <Link href="https://fai.utb.cz" isExternal>
                            <Image src={FaiIcon} alt="FAI" boxSize="30px" />
                        </Link>
                        <Link href="mailto:a_shabalin@utb.cz,i_zaitsau@utb.cz">
                            <Image src={OutlookIcon} alt="Outlook" boxSize="30px" />
                        </Link>
                    </HStack>
                </VStack>
            </Box>
        </Box>
    );
}

export default AppFooter;
