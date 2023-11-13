import {AuthProvider} from "./components/AppComponents/AuthContext";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {ChakraProvider} from "@chakra-ui/react";
import {AppLayout} from "./components/AppComponents/AppLayout";
import {
    LOGIN_ROUTE,
    MAIN_PAGE_ROUTE,
    NOT_FOUND_ROUTE,
    PROFILE_ROUTE,
    REGISTRATION_ROUTE
} from "./utils/routes";
import RegisterPage from "./pages/RegisterPage";
import AuthPage from "./pages/AuthPage";
import MainPage from "./pages/MainPage";
import NotFoundPage from "./pages/NotFoundPage";
import ProfilePage from "./pages/ProfilePage";

export function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <ChakraProvider>
                    <AppLayout>
                        <Routes>
                            <Route path={MAIN_PAGE_ROUTE} element={<MainPage />} />
                            <Route path={NOT_FOUND_ROUTE} element={<NotFoundPage />} />
                            <Route path={LOGIN_ROUTE} element={<AuthPage />} />
                            <Route path={REGISTRATION_ROUTE} element={<RegisterPage />} />
                            <Route path={PROFILE_ROUTE + "/:userId"} element={<ProfilePage />} />
                        </Routes>
                    </AppLayout>
                </ChakraProvider>
            </BrowserRouter>
        </AuthProvider>
    )
}
