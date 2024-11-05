import React from 'react';
import styled from 'styled-components';
import { FaGithub } from 'react-icons/fa';

const SectionSubContainer = styled.div`
    background-color: #f4f4f4;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 10px 30px;
    margin-bottom: 20px;
`;

const BoldText = styled.span`
    font-weight: bold;
    color: ${({ color }) => color || '#333'};
`;

const Links = styled.div`
    display: flex;
    align-items: center;

    a {
        display: flex;
        align-items: center;
        color: black;
        text-decoration: none;
    }
    p {
        padding-left: 10px;
        display: flex;
        align-items: center;
    }
`;

function NoticeSection() {
    return (
        <SectionSubContainer>
            <h3>
                해당 서비스는 아직 <BoldText color="#ec6653">Beta</BoldText> 버전이에요. 많은 버그가 있을 수 있어요.
            </h3>
            <Links>
                <a href="https://github.com/olrlobt" target="_blank" rel="noopener noreferrer" className="github-icon">
                    <FaGithub />
                    <p>https://github.com/olrlobt</p>
                </a>
            </Links>
        </SectionSubContainer>
    );
}

export default NoticeSection;
