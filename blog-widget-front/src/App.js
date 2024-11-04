import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import styled from 'styled-components';

const AppContainer = styled.div`
  font-family: Arial, sans-serif;
`;

const MainContainer = styled.div`
  display: flex;
`;

function App() {
  return (
      <Router>
        <AppContainer>
          <Navbar />
          <MainContainer>
            <Sidebar />
            <Routes>
            </Routes>
          </MainContainer>
        </AppContainer>
      </Router>
  );
}

export default App;
