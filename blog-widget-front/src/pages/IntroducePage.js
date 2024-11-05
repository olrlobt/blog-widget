import React from 'react';
import styled from 'styled-components';
import {SiGithub, SiTistory, SiVelog} from "react-icons/si";
import {AiOutlineMail} from 'react-icons/ai';
import NoticeSection from "../components/NoticeSection";
import TableOfContents from "../components/TableOfContents";
import {FaHandsHelping} from "react-icons/fa";

const ContentContainer = styled.div`
    padding: 20px 20px 200px;
    line-height: 1.6;
    text-align: left;
    flex: 1;
    overflow: visible;
`;

const Section = styled.div`
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 10px 30px;
    margin-bottom: 20px;
`;

const Image = styled.img`
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin-top: 15px;
    margin-bottom: 15px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
`;


const Logo = styled.img`
    width: 150px;
    height: auto;
    border-radius: 8px;
    margin: 15px auto;
`;

const LogoBox = styled.div`
    width: 100%;
    display: flex;
    justify-content: center;
    margin: 20px 0;
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

const LinkUrl = styled.a`
    p {
        display: flex;
        align-items: center;
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

const Wrapper = styled.div`
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    overflow: visible;
`;


const StyledSubHeading = styled.h2`
    font-size: 1.5em;
    font-weight: 600;
    color: #333;
    margin-top: 20px;
    margin-bottom: 15px;
    padding: 10px 20px;
    border-left: 5px solid #4a90e2;
    background-color: #f9f9f9;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const BoldText = styled.span`
    font-weight: bold;
    color: ${({color}) => color || '#333'};
`;

const LinksRow = styled.div`
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 10px;
`;

const StyledLinkItem = styled.a`
    display: flex;
    flex-direction: column;
    align-items: center;
    text-decoration: none;
    font-size: 1em;
    color: inherit;

    svg {
        font-size: 2em;
        margin-bottom: 5px;
    }

    .icon-tistory polyline, .icon-tistory path {
        stroke: #f36c00;
        color: #f36c00;
    }
    .icon-velog polyline, .icon-velog path {
        stroke: #20c997;
        color: #20c997;
    }
    .icon-github polyline, .icon-github path {
        stroke: #333;
        color: #333;
    }

    &:hover {
        color: #4a90e2;
    }
`;


const Divider = styled.span`
    color: #ddd;
    font-size: 1.5em;
    margin: 0 10px;
`;

const tocItems = [
    { title: '블로그 위젯', link: '#blog-widget' },
    { title: '이런 경험 있지 않나요?', link: '#issues' },
    { title: '이렇게 해결했어요', link: '#solutions' },
    { title: '시작하기', link: '#start' },
    { title: '제작자', link: '#creator' }
];


function IntroducePage() {
    return (
        <Wrapper>
            <TableOfContents items={tocItems} />

            <ContentContainer>
                <NoticeSection/>

                <Section>
                    <StyledSubHeading id="#blog-widget">깃허브 리드미에서 블로그를 쉽고 효과적으로 공유하세요!</StyledSubHeading>
                    <p>
                        이 위젯을 이용하면 최신 <BoldText color="#5046c5">블로그 포스트</BoldText>를 GitHub README.md에서 쉽고 효과적으로 공유할 수
                        있어요.
                    </p>
                    <p>
                        또한, 프로젝트 README 파일에 <BoldText color="#5046c5">블로그 포스팅을 위젯처럼 표시</BoldText>하여 프로젝트와 관련된 추가 컨텐츠를
                        효과적으로 공유해 보세요.
                    </p>
                    <br/>
                    <p>
                        어떤 식으로 보여지는지{" "}
                        <a href="https://github.com/olrlobt" target="_blank" rel="noopener noreferrer"
                           className="github-icon">
                            <BoldText color="#6fafef">
                                저의 깃허브 리드미
                            </BoldText>
                        </a>
                        에서 바로 확인하세요!
                    </p>
                    <br/>
                    <LogoBox>
                        <Logo src={`${process.env.PUBLIC_URL}/blogwidget_logo2.png`} alt="Blog Widget Example"/>
                    </LogoBox>
                </Section>


                <Section>
                    <StyledSubHeading id="issues">이런 경험 있지 않나요?</StyledSubHeading>
                    <p>
                        Github README.md에 내 블로그를 표기하고 싶은데, 링크로는 잘 보이지 않아요.
                    </p>
                    <p>
                        남들처럼 태그를 쓴다고 해도 잘 안 보이고, 별로 궁금하지 않아 클릭하지 않게 되더라구요.
                    </p>
                    <br/>
                    <p>
                        <BoldText>아래 이미지에서 블로그가 궁금한가요?</BoldText>
                    </p>
                    <br/>
                    <Image src={`${process.env.PUBLIC_URL}/before_using_project.png`} alt="Getting Started Guide"/>
                </Section>

                <Section>
                    <StyledSubHeading id="solutions">이렇게 해결했어요.</StyledSubHeading>
                    <p>
                        여러가지 방법을 고민하다가 생각난 방법이 바로 <BoldText>"위젯으로 만들자!"</BoldText> 였습니다.
                    </p>
                    <br/>
                    <p>
                        이때 여러가지 Open Source들이 어떻게 구현 되어 있나 확인하고,
                    </p>
                    <p>
                        이를 제가 가장 자신있는 Java/Spring으로 옮겼습니다 !
                    </p>
                    <br/>
                    <p>
                        <BoldText>적용한 모습은 아래 이미지를 참고하세요!</BoldText>
                    </p>
                    <br/>

                    <Image src={`${process.env.PUBLIC_URL}/after_using_project.png`} alt="Blog Widget Example"/>
                </Section>

                <Section>
                    <StyledSubHeading id="start">시작하기</StyledSubHeading>
                    <p>아래 링크를 통해 바로 시작해보세요.</p>
                    <br />
                    <br />
                    <LinksRow>
                        <StyledLinkItem href="https://tistory.com" target="_blank" rel="noopener noreferrer">
                            <SiTistory className="icon-tistory" />
                            Tistory
                        </StyledLinkItem>
                        <Divider>|</Divider>
                        <StyledLinkItem href="https://velog.io" target="_blank" rel="noopener noreferrer">
                            <SiVelog className="icon-velog" />
                            Velog
                        </StyledLinkItem>
                        <Divider>|</Divider>
                        <StyledLinkItem href="https://github.com" target="_blank" rel="noopener noreferrer">
                            <SiGithub className="icon-github" color="#fffff" />
                            GitHub Pages
                        </StyledLinkItem>

                    </LinksRow>

                    <br />
                </Section>

                <Section>
                    <StyledHeading id="creator">제작자</StyledHeading>
                    <p>서비스에 관한 오류 문의나, 건의사항은 아래 이메일을 이용해주세요!</p>
                    <br/>
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
                    <Links>
                        <FaHandsHelping style={{ color: 'goldenrod' }} />
                        <p>
                            Special Thanks to&nbsp;
                            <BoldText color="#5046c5">Linzy</BoldText>
                            &nbsp;for our amazing logo!
                        </p>
                    </Links>
                    <br/>
                </Section>
            </ContentContainer>
        </Wrapper>
    );
}

export default IntroducePage;
