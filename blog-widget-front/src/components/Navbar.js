import React from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
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
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
`;

const NavbarContent = styled.div`
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    max-width: 1220px;
`;

const Title = styled.div`
    display: flex;
    align-items: center;
    font-size: 1.2em;
    a {
        color: black;
        text-decoration: none;
        display: flex;
        align-items: center; 
    }
`;

const Logo = styled.img`
    width: 35px;
    height: auto;
    border-radius: 8px;
    margin-right: 10px;
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
        font-size: 1em;

        &:hover {
            color: #4a90e2;
            text-decoration: underline;
        }
    }
`;

const IconLinks = styled.div`
    display: flex;
    align-items: center;

    a {
        color: black;
        margin-left: 10px;

        &:hover {
            color: #4a90e2;
        }
    }

    .github-icon {
        font-size: 1.4em;
    }

    .tistory-icon {
        font-size: 1.2em;
    }
`;

function Navbar() {
    return (
        <NavbarContainer>
            <NavbarContent>
                <LinksContainer>
                    <Title>
                        <Link to="/">
                            <Logo src={`${process.env.PUBLIC_URL}/blogwidget_logo2.png`} alt="Blog Widget Example" />
                            Blog Widget
                        </Link>
                    </Title>
                    <Links>
                        <Link to="/">소개</Link>
                        <Link to="/api-docs">API 시작하기</Link>
                        <Link to="/examples">세트 예제</Link>
                    </Links>
                </LinksContainer>
                <IconLinks>
                    <a
                        href="https://github.com/olrlobt"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="github-icon"
                    >
                        <FaGithub />
                    </a>
                    <a
                        href="https://olrlobt.tistory.com"
                        target="_blank"
                        rel="noopener noreferrer"
                        className="tistory-icon"
                    >
                        <SiTistory />
                    </a>
                </IconLinks>
            </NavbarContent>
        </NavbarContainer>
    );
}

export default Navbar;
