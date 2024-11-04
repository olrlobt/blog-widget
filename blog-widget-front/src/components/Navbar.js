import React from 'react';
import styled from 'styled-components';

const NavbarContainer = styled.nav`
  background-color: #24292e;
  color: white;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.h1`
  font-size: 1.5em;
  margin: 0;
`;

const Links = styled.div`
  a {
    color: white;
    margin-left: 15px;
    text-decoration: none;
  }
`;

function Navbar() {
    return (
        <NavbarContainer>
            <Title>My Docs</Title>
            <Links>
                <a href="/">Home</a>
                <a href="/docs">Docs</a>
            </Links>
        </NavbarContainer>
    );
}

export default Navbar;
