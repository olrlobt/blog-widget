import React from 'react';
import styled from 'styled-components';

const SidebarContainer = styled.aside`
  width: 200px;
  background-color: #f6f8fa;
  padding: 20px;
`;

const SidebarList = styled.ul`
  list-style: none;
  padding: 0;
`;

const SidebarItem = styled.li`
  margin-bottom: 10px;
  a {
    text-decoration: none;
    color: #0366d6;
  }
`;

function Sidebar() {
    return (
        <SidebarContainer>
            <SidebarList>
                <SidebarItem><a href="/docs/intro">Introduction</a></SidebarItem>
                <SidebarItem><a href="/docs/getting-started">Getting Started</a></SidebarItem>
                <SidebarItem><a href="/docs/api">API Reference</a></SidebarItem>
            </SidebarList>
        </SidebarContainer>
    );
}

export default Sidebar;
