// Content.js
import React from 'react';
import styled from 'styled-components';
import {FaGithub} from "react-icons/fa";
import {SiTistory} from "react-icons/si";
import { AiOutlineMail } from 'react-icons/ai';

const ContentContainer = styled.div`
    padding: 20px;
    line-height: 1.6;
    text-align: left;
    flex: 1; /* 나머지 공간을 차지하도록 설정 */
`;

const Section = styled.div`
    background-color: #ffffff; /* 박스 배경 흰색 */
    border-radius: 8px; /* 모서리 둥글게 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 박스 그림자 */
    padding: 10px 30px;
    margin-bottom: 20px; /* 각 박스 간 간격 */
`;

const SectionSub = styled.div`
    background-color: #f4f4f4; /* 박스 배경 흰색 */
    border-radius: 8px; /* 모서리 둥글게 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 박스 그림자 */
    padding: 10px 30px;
    margin-bottom: 20px; /* 각 박스 간 간격 */
`;

const Image = styled.img`
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin-top: 15px;
`;

const Links = styled.div`
    display: flex;
    align-items: center;

    a {
        display: flex; /* 아이콘을 세로 중앙에 맞추기 위해 flex 설정 */
        align-items: center; /* 아이콘의 세로 가운데 정렬 */
        color: black;
        margin-left: 15px;
        text-decoration: none;
    }

    p {
        padding-left: 10px;
        display: flex; /* 텍스트도 flex로 설정 */
        align-items: center; /* 텍스트 세로 중앙 정렬 */
    }
`;

const LinkUrl = styled.a`
    p {
        display: flex; /* 텍스트도 flex로 설정 */
        align-items: center; /* 텍스트 세로 중앙 정렬 */
        color: cornflowerblue;
        text-decoration: none;
    }
`;

const StyledHeading = styled.h1`
    font-size: 2em;
    font-weight: bold;
    color: #333;
    margin-top: 20px;
    margin-bottom: 10px;
`;

const TableOfContents = styled.div`
    background-color: #f9f9f9;
    padding: 10px;
    margin-bottom: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

    h2 {
        font-size: 1.5em;
        margin-bottom: 10px;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        margin-bottom: 8px;
    }

    a {
        color: #333;
        text-decoration: none;
        &:hover {
            text-decoration: underline;
        }
    }
`;

const Wrapper = styled.div`
    max-width: 1000px; /* 전체 너비를 넓게 설정하여 Sidebar와 ContentContainer가 같이 맞도록 설정 */
    margin: 0 auto;
    display: flex;
`;

const Sidebar = styled(TableOfContents)`
    width: 200px;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-right: 20px; /* ContentContainer와의 간격 */
    margin-top: 15px;
    flex-shrink: 0; /* Sidebar가 좁아지지 않도록 설정 */
    position: sticky;
    top: 20px; /* 스크롤 시 상단에서 20px 떨어진 상태로 고정 */
    align-self: flex-start; /* 컨테이너 내에서 시작 위치에 배치 */
`;





function Content() {
    return (
        <Wrapper>
            <Sidebar>
                <h2>목차</h2>
                <ul>
                    <li><a href="#blog-widget">Blog Widget</a></li>
                    <li><a href="#issues">이러지 않았나요?</a></li>
                    <li><a href="#solutions">이렇게 해결하세요</a></li>
                    <li><a href="#warnings">주의해요!</a></li>
                    <li><a href="#creator">제작자</a></li>
                </ul>
            </Sidebar>

            <ContentContainer>
                <SectionSub>
                    <h3>Blog Widget 소개 페이지입니다.</h3>
                    <Links>
                        <a href="https://github.com/olrlobt" target="_blank" rel="noopener noreferrer"
                           className="github-icon">
                            <FaGithub/>
                            <p>https://github.com/olrlobt</p>
                        </a>
                    </Links>
                </SectionSub>

                <Section>
                    <StyledHeading id="blog-widget">Blog Widget</StyledHeading>
                    <h2>깃허브 리드미에서 블로그를 쉽고 효과적으로 공유하세요!</h2>
                    <p>이 위젯을 이용하면 최신 블로그 포스트를 GitHub 유저 README에서 쉽고 효과적으로 공유할 수 있습니다.
                        또한, 프로젝트 README 파일에 블로그 포스팅을 위젯처럼 표시하여 프로젝트와 관련된 추가 컨텐츠를 효과적으로 공유해 보세요.</p>
                    <p>어떤 식으로 보여지는지 저의 깃허브 리드미에서 바로 확인하실 수 있습니다!</p>
                    <Image src={`${process.env.PUBLIC_URL}/after_using_project.png`} alt="Blog Widget Example" />
                </Section>

                <Section>
                    <StyledHeading id="issues">이러지 않았나요?</StyledHeading>
                    <p>
                        최신 포스팅 가져오기"는 현재 Tistory와 Velog만 가능합니다.
                        Tistory의 경우, 일부 테마만 가능하며 테마를 임의로 수정하였다면 정상작동하지 않을 수 있습니다.
                    </p>
                    <Image src="https://via.placeholder.com/800x200" alt="Getting Started Guide"/>
                </Section>

                <Section>
                    <StyledHeading id="solutions">이렇게 해결하세요</StyledHeading>
                    <p>
                        Detailed descriptions of each endpoint, including request parameters, response
                        formats, and example requests.
                    </p>
                </Section>

                <Section>
                    <StyledHeading id="warnings">주의해요!</StyledHeading>
                    <p>
                        Detailed descriptions of each endpoint, including request parameters, response
                        formats, and example requests.
                    </p>
                </Section>

                <Section>
                    <StyledHeading id="creator">제작자</StyledHeading>
                    <p>서비스에 관한 오류 문의나, 건의사항은 아래 이메일을 이용해주세요!</p>
                    <Links>
                        <a href="https://olrlobt.tistory.com" target="_blank" rel="noopener noreferrer"
                           className="tistory-icon">
                            <SiTistory/>
                            <p>Blog :</p>
                            <LinkUrl>
                                <p>https://olrlobt.tistory.com</p>
                            </LinkUrl>
                        </a>
                    </Links>
                    <Links>
                        <a href="mailto:317tmdgjs@naver.com" target="_blank" rel="noopener noreferrer">
                            <AiOutlineMail />
                            <p>Contact :</p>
                            <LinkUrl>
                                <p>317tmdgjs@naver.com</p>
                            </LinkUrl>
                        </a>
                    </Links>
                </Section>
            </ContentContainer>
        </Wrapper>
    );
}


export default Content;
