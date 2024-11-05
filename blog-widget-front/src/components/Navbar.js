import React from 'react';
import styled from 'styled-components';
import { FaGithub } from 'react-icons/fa';
import { SiTistory } from 'react-icons/si';

const NavbarContainer = styled.nav`
    background-color: #ffffff;
    color: black;
    padding: 10px 0;
    height: 65px;
    border-bottom: solid 1px #e9ebed;
    display: flex;
    justify-content: center;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05); /* 그림자 추가 */
`;

const NavbarContent = styled.div`
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 1220px; /* 가운데에 고정된 너비 */
`;

const Title = styled.h1`
    font-size: 1.5em;
    margin: 0;
`;

const LinksContainer = styled.div`
    display: flex;
    align-items: center;
`;

const Links = styled.div`
    display: flex;
    align-items: center;
    margin-left: 20px;

    a {
        color: black;
        margin-left: 15px;
        text-decoration: none;
    }
`;

const IconLinks = styled.div`
    display: flex;
    align-items: center;

    a {
        color: black;
        margin-left: 10px;
    }

    .github-icon {
        font-size: 1.4em; /* GitHub 아이콘 크기 증가 */
    }

    .tistory-icon {
        font-size: 1.1em; /* Tistory 아이콘 기본 크기 */
    }
`;

function Navbar() {
    return (
        <NavbarContainer>
            <NavbarContent>
                <LinksContainer>
                    <Title>Blog Widget</Title>
                    <Links>
                        <a href="/about">소개</a>
                        <a href="/get-started">API 시작하기</a>
                        <a href="/examples">세트 예제</a>
                    </Links>
                </LinksContainer>
                <IconLinks>
                    <a href="https://github.com/olrlobt" target="_blank" rel="noopener noreferrer" className="github-icon">
                        <FaGithub />
                    </a>
                    <a href="https://olrlobt.tistory.com" target="_blank" rel="noopener noreferrer" className="tistory-icon">
                        <SiTistory />
                    </a>
                </IconLinks>
            </NavbarContent>
        </NavbarContainer>
    );
}

export default Navbar;
