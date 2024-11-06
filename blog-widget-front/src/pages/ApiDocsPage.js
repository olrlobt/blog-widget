import React, { useState } from 'react';
import styled from 'styled-components';
import NoticeSection from "../components/NoticeSection";

const ContentContainer = styled.div`
    padding: 20px 20px 200px;
    line-height: 1.6;
    text-align: left;
    flex: 1;
    overflow: visible;
`;

const Section = styled.div`
    display: flex;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px 30px;
    margin-bottom: 20px;
`;

const StyledHeading = styled.h1`
    font-size: 2em;
    font-weight: bold;
    color: #333;
    margin-top: 20px;
    margin-bottom: 10px;
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
    color: ${({ color }) => color || '#333'};
`;

const CodeBlock = styled.pre`
    background-color: #f4f4f4;
    padding: 15px;
    border-radius: 8px;
    overflow-x: auto;
    //font-family: 'Courier New', Courier, monospace;
    line-height: 1.5;
    font-size: 0.9em; 
    margin-bottom: 20px;
`;

const Wrapper = styled.div`
    max-width: 1600px;
    margin: 0 auto;
    display: flex;
    overflow: visible;
`;

const Sidebar = styled.div`
    width: 200px;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-right: 20px;
    margin-top: 15px;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;

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

const TextContainer = styled.div`
    width: 50%;
    padding-right: 15px;
    border-right: 1px solid #eee;
`;

const CodeContainer = styled.div`
    width: 50%;
    padding-left: 15px;
`;

const ThemeList = styled.ul`
    list-style: none;
    padding: 0;
    display: flex;
    flex-wrap: wrap; /* 줄바꿈을 허용하여 화면이 좁을 경우 다음 줄로 넘어가도록 설정 */
    gap: 10px; /* 아이템 간 간격 설정 */
`;

const ThemeItem = styled.li`
    background-color: #f5f5f5;
    padding: 10px 15px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-weight: 500;
    color: #333;

    &:before {
        content: "🎨";
        margin-right: 8px;
    }

    &:hover {
        background-color: #e0e7ff;
    }
`;

const Input = styled.input`
    width: 100%;
    padding: 8px;
    margin-bottom: 10px;
    border-radius: 4px;
    border: 1px solid #ccc;
`;

const RadioContainer = styled.div`
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
`;

const RadioButtonLabel = styled.label`
    padding: 10px 20px;
    border-radius: 8px;
    border: 2px solid #ccc;
    cursor: pointer;
    font-weight: 500;
    transition: all 0.3s;
    color: ${({ isSelected }) => (isSelected ? '#fff' : '#333')};
    background-color: ${({ isSelected }) => (isSelected ? '#4a90e2' : '#f9f9f9')};
    border-color: ${({ isSelected }) => (isSelected ? '#4a90e2' : '#ccc')};

    &:hover {
        background-color: ${({ isSelected }) => (isSelected ? '#357ABD' : '#e0e0e0')};
        border-color: #4a90e2;
    }
`;

const HiddenRadio = styled.input`
    display: none;
`;


function ApiDocsPage() {
    const [platform, setPlatform] = useState('');
    const [postingNum, setPostingNum] = useState('');
    const [blogName, setBlogName] = useState('');
    const [theme, setTheme] = useState('');

    return (
        <Wrapper>
            <Sidebar>
                <h2>목차</h2>
                <ul>
                    <li><a href="#fetch-latest-posts">최신 포스팅 가져오기</a></li>
                    <li><a href="#fetch-blog-info">블로그 정보 가져오기</a></li>
                    <li><a href="#pin-post">포스팅 고정하기</a></li>
                    <li><a href="#contact">문의하기</a></li>
                </ul>
            </Sidebar>

            <ContentContainer>
                <NoticeSection />

                <Section id="fetch-latest-posts">
                    <TextContainer>
                        <StyledSubHeading>최신 포스팅 가져오기</StyledSubHeading>
                        <p>자신의 블로그에서 최신 포스팅 순으로 포스팅을 위젯화 할 수 있습니다.</p>
                        <p>블로그 플랫폼에 따라 유의사항은 아래를 참고하세요.</p>
                        <br/>

                        <BoldText color="#f36c00">Tistory :</BoldText>
                        <p> tistory 플랫폼은 tistory api 정책이 종료됨에따라 scrapping 방식을 사용합니다. </p>
                        <p>이에 따라, 부득이하게 지원하지 않는 테마가 있습니다. </p>
                        <p>또한, 공식 테마의 태그를 수정한 경우 작동하지 않을 수 있습니다.</p>
                        <br/>

                        <p>사용 가능한 테마:</p>
                        <ThemeList>
                            <ThemeItem>Odyssey</ThemeItem>
                            <ThemeItem>Blue Club</ThemeItem>
                            <ThemeItem>반응형#1</ThemeItem>
                            <ThemeItem>반응형#2</ThemeItem>
                            <ThemeItem>Poster</ThemeItem>
                            <ThemeItem>Whatever</ThemeItem>
                            <ThemeItem>Letter</ThemeItem>
                            <ThemeItem>Portfolio</ThemeItem>
                            <ThemeItem>__hELLO</ThemeItem>
                        </ThemeList>

                        <br/>
                        <BoldText color="#20c997">Velog :</BoldText>
                        <p> Velog 플랫폼은 GraphQL 방식의 velog api를 사용합니다. </p>
                        <p>아직까지 발견된 버그는 없습니다.</p>
                        <br/>

                        <BoldText color="#333">Github Pages :</BoldText>
                        <p> jekyll로 작동하는 Github Pages 플랫폼은 GraphQL 방식의 Github api를 사용합니다. </p>
                        <p> 썸네일이 없는 Github Pages는 썸네일이 표기되지 않습니다. </p>
                        <p> 또한, githubio 레포지토리 구성에 따라 작동하지 않을 수 있습니다.</p>
                        <br/>


                    </TextContainer>

                    <CodeContainer>
                        <StyledSubHeading>사용 방법</StyledSubHeading>

                        <label>플랫폼:</label>
                        <RadioContainer>
                            <RadioButtonLabel isSelected={platform === 't'}>
                                <HiddenRadio
                                    type="radio"
                                    value="t"
                                    checked={platform === "t"}
                                    onChange={(e) => setPlatform(e.target.value)}
                                />
                                Tistory
                            </RadioButtonLabel>
                            <RadioButtonLabel isSelected={platform === 'v'}>
                                <HiddenRadio
                                    type="radio"
                                    value="v"
                                    checked={platform === "v"}
                                    onChange={(e) => setPlatform(e.target.value)}
                                />
                                Velog
                            </RadioButtonLabel>
                            <RadioButtonLabel isSelected={platform === 'g'}>
                                <HiddenRadio
                                    type="radio"
                                    value="g"
                                    checked={platform === "g"}
                                    onChange={(e) => setPlatform(e.target.value)}
                                />
                                Github Pages
                            </RadioButtonLabel>
                        </RadioContainer>

                        <label>최신 순서:</label>
                        <Input
                            type="text"
                            value={postingNum}
                            onChange={(e) => setPostingNum(e.target.value)}
                            placeholder="0"
                        />

                        <label>블로그 이름:</label>
                        <Input
                            type="text"
                            value={blogName}
                            onChange={(e) => setBlogName(e.target.value)}
                            placeholder="olrlobt"
                        />

                        <label>테마:</label>
                        <Input
                            type="text"
                            value={theme}
                            onChange={(e) => setTheme(e.target.value)}
                            placeholder="b"
                        />

                        <CodeBlock>
                            {`[![최신글](https://blogwidget.com/api/${platform}/posting/${postingNum}?name=${blogName}&theme=${theme})]
(https://blogwidget.com/api/${platform}/link/${postingNum}?name=${blogName})`}
                        </CodeBlock>


                        <p>예시:</p>
                        <CodeBlock>
                            {`[![고정](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=b)](https://olrlobt.tistory.com/)`}
                        </CodeBlock>
                    </CodeContainer>
                </Section>

                <Section id="fetch-blog-info">
                    <TextContainer>
                        <StyledSubHeading>블로그 정보 가져오기</StyledSubHeading>
                        <p>
                            <BoldText>블로그 정보 가져오기</BoldText>는 현재 Tistory와 Velog에서만 가능합니다.
                            자세한 사용 방법은 <BoldText>최신글 가져오기</BoldText>와 유사합니다.
                        </p>

                    </TextContainer>
                    <CodeContainer>
                        <StyledSubHeading>사용 방법</StyledSubHeading>
                        <CodeBlock>
                            {`[![블로그정보](https://blogwidget.com/api/{$PLATFORM}/blog?name={$BLOG_NAME})]({$LINK})`}
                        </CodeBlock>
                        <p>예시:</p>
                        <CodeBlock>
                            {`[![블로그정보](https://blogwidget.com/api/t/blog?name=olrlobt)](https://olrlobt.tistory.com/)`}
                        </CodeBlock>
                    </CodeContainer>



                </Section>

                <Section id="pin-post">
                    <TextContainer>
                        <StyledSubHeading>포스팅 고정하기</StyledSubHeading>
                        <p>포스팅이 아니더라도, 원하는 웹 페이지를 고정된 형태로 위젯으로 표시할 수 있습니다.</p>

                    </TextContainer>
                    <CodeContainer>
                        <StyledSubHeading>사용 방법</StyledSubHeading>
                        <CodeBlock>
                            {`[![고정](https://blogwidget.com/api/fix?url={$URL})]({$LINK}) // 테마 미사용
[![고정](https://blogwidget.com/api/fix?url={$URL}&theme={$THEME})]({$LINK}) // 테마 사용`}
                        </CodeBlock>
                        <p>예시:</p>
                        <CodeBlock>
                            {`[![고정](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=b)](https://olrlobt.tistory.com/)`}
                        </CodeBlock>
                    </CodeContainer>


                </Section>

                <Section id="contact">
                    <StyledHeading>문의하기</StyledHeading>
                    <p>서비스 관련 문의사항은 아래 이메일로 연락해주세요.</p>

                </Section>
            </ContentContainer>
        </Wrapper>
    );
}

export default ApiDocsPage;
