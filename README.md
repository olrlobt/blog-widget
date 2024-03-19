

# Blog Widget for GitHub README

> GitHub README, 블로그 위젯 

### 깃허브 리드미에서 블로그를 쉽고 효과적으로 공유하세요!

이 서비스를 이용하면 최신 블로그 포스트를 GitHub 유저 README에서 쉽고 효과적으로 공유할 수 있습니다.
또한, 프로젝트 README 파일에 블로그 포스팅을 위젯처럼 표시하여 프로젝트와 관련된 추가 컨텐츠를 효과적으로 공유해 보세요.

어떤 식으로 보여지는지 [저의 깃허브 리드미](https://github.com/olrlobt)에서 바로 확인하실 수 있습니다! 


[![블로그 정보](https://blogwidget.com/api/posting-info?blogName=olrlobt)](https://olrlobt.tistory.com/)


<br>

### 사용 화면 예시
![img.png](docs/240318capture.png)

<br>
<br>

## 시작하기

> 현재 1개의 테마, 1개의 블로그, 2개의 테마에서 사용 가능합니다.

- 사용 가능 테마 : Box
- 사용 가능 블로그 : Tistory
- 사용 가능 테마(Tistory) : 반응형#2, Blue Club

<br>

### 블로그 박스 위젯

사용 방법 :

```markdown
[![블로그 정보](https://blogwidget.com/api/posting-info?blogName={$당신의 블로그 이름})]({$링크})
[![블로그 정보](https://blogwidget.com/api/posting-info?blogName=olrlobt)](https://olrlobt.tistory.com/)
```

예시:

[![블로그 정보](https://blogwidget.com/api/posting-info?blogName=olrlobt)](https://olrlobt.tistory.com/)

<br>

### 포스팅 박스 위젯

사용 방법 :

```markdown
[![게시물](https://blogwidget.com/api/posting/{$번호}?blogName={$당신의 블로그 이름})](https://blogwidget.com/api/posting-link/{$번호}?blogName={$당신의 블로그 이름})
[![게시물](https://blogwidget.com/api/posting/0?blogName=olrlobt)](https://blogwidget.com/api/posting-link/0?blogName=olrlobt)
```

예시:

[![게시물](https://blogwidget.com/api/posting/0?blogName=olrlobt)](https://blogwidget.com/api/posting-link/0?blogName=olrlobt)

<br>
<br>

## 개발 노트

```text
24/03/11 - Tistory '반응형#2' 포스팅 박스
24/03/12 - Tistory 포스팅 바로가기
24/03/15 - 블로그 박스, 블로그 바로가기
24/03/18 - Tistory 'Blue Club' 서비스
24/03/19 - blogwidget.com 도메인 설정
```

<br>
<br>

## 추가 예정

```text
Tistory 모든 테마 지원 예정
Velog, Github.io, Naver Blog 지원 예정
여러가지 위젯 모양 지원 예정
게시물 1개만 효과적으로 표기하는 방법 지원 예정

```




