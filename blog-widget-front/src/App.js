import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import IntroducePage from './pages/IntroducePage';
import styled from 'styled-components';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import ExamplePage from './pages/ExamplePage';
import ApiDocsPage from './pages/ApiDocsPage';

const AppContainer = styled.div`
    font-family: Arial, sans-serif;
    background: linear-gradient(
            to bottom right,
            rgba(148, 158, 213, 0.3),
            rgba(145, 160, 213, 0.3),
            rgba(153, 155, 213, 0.3),
            rgba(127, 172, 215, 0.3),
            rgba(125, 173, 215, 0.3),
            rgba(150, 157, 213, 0.3),
            rgba(122, 175, 215, 0.3),
            rgba(130, 170, 215, 0.3),
            rgba(140, 163, 213, 0.3)
    );
    min-height: 100vh;
`;

const MainContainer = styled.div`
    display: flex;
`;

const ContentContainer = styled(IntroducePage)`
    flex: 3;
    padding: 20px;
    overflow: visible;
`;

function App() {
    return (
        <Router>
            <AppContainer>
                <Navbar />
                <MainContainer>
                    <Routes>
                        <Route path="/" element={<ContentContainer />} />
                        <Route path="/api-docs" element={<ApiDocsPage />} />
                        <Route path="/examples" element={<ExamplePage />} />
                    </Routes>
                </MainContainer>
            </AppContainer>
        </Router>
    );
}

export default App;
