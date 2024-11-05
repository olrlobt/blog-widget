import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Content from './components/Content';
import styled from 'styled-components';

const AppContainer = styled.div`
    font-family: Arial, sans-serif;
    background-color: #f3f5f7; /* 메인 배경색 설정 */
    min-height: 100vh; /* 화면 전체를 채우기 위해 최소 높이 설정 */
`;

const MainContainer = styled.div`
    display: flex;
`;


const ContentContainer = styled(Content)`
    flex: 3; // Content가 3/4 정도 차지
    padding: 20px;
`;

function App() {
    return (
        <Router>
            <AppContainer>
                <Navbar />
                <MainContainer>
                    <Routes>
                        <Route path="/" element={<ContentContainer />} />
                    </Routes>
                </MainContainer>
            </AppContainer>
        </Router>
    );
}

export default App;
