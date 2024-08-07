

# Blog Widget for GitHub README

> GitHub README, 블로그 위젯 

### 깃허브 리드미에서 블로그를 쉽고 효과적으로 공유하세요!

이 위젯을 이용하면 최신 블로그 포스트를 GitHub 유저 README에서 쉽고 효과적으로 공유할 수 있습니다.\
또한, 프로젝트 README 파일에 블로그 포스팅을 위젯처럼 표시하여 프로젝트와 관련된 추가 컨텐츠를 효과적으로 공유해 보세요.

어떤 식으로 보여지는지 [저의 깃허브 리드미](https://github.com/olrlobt)에서 바로 확인하실 수 있습니다! 


[![olrlobt tistory](https://blogwidget.com/api/t/blog?name=olrlobt)](https://olrlobt.tistory.com/)


<br>
<br>

# 시작하기

## 최신 포스팅 가져오기

> "최신 포스팅 가져오기"는 현재 Tistory와 Velog만 가능합니다.\
> Tistory의 경우, 일부 테마만 가능하며 테마를 임의로 수정하였다면 정상작동하지 않을 수 있습니다.

- 사용 가능한 Tistory 테마 :
  - Odyssey
  - Blue Club
  - 반응형#1
  - 반응형#2
  - Poster
  - Whatever
  - Letter
  - Portfolio
  - __hELLO


- 플랫폼 태그 :
  - Tistory - `t` , `tistory`
  - Velog - `v`, `velog`

<br>

#### 사용 방법 :
해당 API를 호출하여, `markdown` 파일에서 포스팅을 쉽게 **최신순으로 노출시키고 링크**할 수 있습니다.

> $PLATFORM : 플랫폼 태그\
> $POSTING_NUM : 최신으로부터 몇번째 게시물 (0이 가장 최근 게시물)\
> $BLOG_NAME : 블로그 이름\
> $THEME : 테마

```markdown
[![최신글](https://blogwidget.com/api/{$PLATFORM}/posting/{$POSTING_NUM}?name={$BLOG_NAME})]
(https://blogwidget.com/api/{$PLATFORM}/link/{$POSTING_NUM}?name={$BLOG_NAME})
```

```markdown
[![최신글](https://blogwidget.com/api/t/posting/0?name=olrlobt)](https://blogwidget.com/api/t/link/0?name=olrlobt)
```
<br>
예시:

[![게시물](https://blogwidget.com/api/t/posting/0?name=olrlobt)](https://blogwidget.com/api/t/link/0?name=olrlobt)
[![게시물](https://blogwidget.com/api/t/posting/1?name=olrlobt)](https://blogwidget.com/api/t/link/1?name=olrlobt)
[![게시물](https://blogwidget.com/api/t/posting/2?name=olrlobt)](https://blogwidget.com/api/t/link/2?name=olrlobt)

<br>
<br>




# 블로그 정보 가져오기

> "블로그 정보 가져오기"는 현재 Tistory와 Velog만 가능합니다.\
> 자세한 내용은 "최신글 가져오기"를 참고하세요.

<br>

#### 사용 방법 :

해당 API를 호출하여, `markdown`에서 블로그를 쉽게 노출할 수 있습니다.

> $PLATFORM : 플랫폼 태그\
> $BLOG_NAME : 블로그 이름\
> $LINK : 블로그 링크

```markdown
[![블로그정보](https://blogwidget.com/api/{$PLATFORM}/blog?name={$BLOG_NAME})]({$LINK})
```
```markdown
[![블로그정보](https://blogwidget.com/api/t/blog?name=olrlobt)](https://olrlobt.tistory.com/)
```
<br>


예시:

[![블로그 정보](https://blogwidget.com/api/t/blog?name=olrlobt)](https://olrlobt.tistory.com/)

<br>




# 포스팅 고정하기

> 포스팅이 아니어도 어떤 웹 페이지도 위젯형식으로 고정이 가능합니다.\
> 일부 조건에 따라, 표시되지 않는 정보가 있을 수 있습니다.
> 
<br>

#### 사용 방법 :
해당 API를 호출하여, `markdown` 파일에서 포스팅을 쉽게 고정할 수 있습니다.

> $URL : 고정할 URL\
> $LINK : 블로그 링크\
> $THEME : 테마

```markdown
[![고정](https://blogwidget.com/api/fix?url={$URL})]({$LINK}) // 테마 미사용
[![고정](https://blogwidget.com/api/fix?url={$URL}&theme={$THEME})]({$LINK}) // 테마 사용
```
```markdown
[![고정](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=b)](https://olrlobt.tistory.com/)
[![고정](https://blogwidget.com/api/fix?url=https://naver.com&theme=b)](https://naver.com)
```

<br>

#### 예시:
- 일반
  
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com/)](https://olrlobt.tistory.com/)
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com/91)](https://olrlobt.tistory.com/)

- theme=c

[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=c)](https://olrlobt.tistory.com/)
[![게시물](https://blogwidget.com/api/t/posting/0?name=olrlobt&theme=c)](https://olrlobt.tistory.com/)

- theme=i

[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=i)](https://olrlobt.tistory.com/)
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com/91&theme=i)](https://olrlobt.tistory.com/)

- theme=b
  
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=b)](https://olrlobt.tistory.com/)
[![게시물](https://blogwidget.com/api/t/posting/0?name=olrlobt&theme=b)](https://olrlobt.tistory.com/)

- theme=w
  
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com&theme=w)](https://olrlobt.tistory.com/)
[![게시물](https://blogwidget.com/api/fix?url=https://olrlobt.tistory.com/91&theme=w)](https://olrlobt.tistory.com/)

<br>
<br>







---










## 개발 노트

```text
24/03/11 - Tistory '반응형#2' 포스팅 박스
24/03/12 - Tistory 포스팅 바로가기
24/03/15 - 블로그 박스, 블로그 바로가기
24/03/18 - Tistory 'Blue Club' 서비스
24/03/19 - blogwidget.com 도메인 설정
24/03/24 - Velog 지원, info Theme 추가
24/03/24 - 포스팅 고정 위젯 추가
24/04/20 - 로그 파일 저장
24/04/24 - Blue/Green 무중단 배포 적용
24/05/16 - Wide Theme 추가
24/08/08 - Big Theme 추가
24/08/08 - 사진 없는 Card Theme 추가
```

<br>
<br>

## 향후 계획

```text
Github.io, Naver Blog 지원
```




