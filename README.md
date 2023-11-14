# toyseven

JPA 를 이용한 DB 관리, AWS Cognito 와 자체적으로 발급한 JWT 토큰을 이용하여 다중 필터링 환경 구성

## 설치방법

### 전제조건 
> Java 8 버전이 설치되어 있어야 합니다.

> 해당 저장소(프로젝트)를 Clone 또는 다운로드 후 알집해제가 된 상태여야 합니다.


### Eclipse

`File -> Import -> Existing Maven Projects -> Root Directory 설정 -> Finish`


### 버전정보

- ``SpringBoot 2.4.4``
- ``java 1.8``
- ``Gradle 7.2``
- ``h2 In-memory DB``
- ``JPA``
- ``AWS Cognito``


## 실행

### Eclipse

1. 프로젝트를 우클릭하여 'Run AS -> Maven install' 을 눌러 프로젝트를 빌드합니다.
   > (querydsl 의 QEntity 파일 생성을 위한 작업입니다.)
2. ToysevenApplication.java 파일을 열어줍니다..
3. 열린 파일을 선택 한 다음 'Ctrl + Alt + X' 를 입력 후 'J' 를 입력합니다.

### swagger
> http://localhost:8000/toyseven/swagger-ui.html

### h2 console
> http://localhost:8000/toyseven/h2-console

- URL : jdbc:h2:mem:toyseven;MODE=MYSQL;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;
- ID : test
- PW : test

### Postman
> share link : https://api.postman.com/collections/15553250-447514e3-cd1c-4178-9b07-fb5ed00bf7c0?access_key=PMAT-01GRQD6M4FRFGDPMHSKAQGF48C
  
### Admin Server
> https://github.com/ymkmoon/admin

### Used API
  - 서울시 공공자전거 실시간 대여정보 
    - https://data.seoul.go.kr/dataList/OA-15493/A/1/datasetView.do
  - 기상청 동네예보 조회서비스 
    - https://www.data.go.kr/iim/api/selectAPIAcountView.do
  - 한국환경공단 에어코리아 대기오염정보 
    - https://www.data.go.kr/iim/api/selectAPIAcountView.do
   


## 업데이트 내역
- 0.0.1
  - 작업 진행 중


## Entity

- aQuery (현재 재정적인 문제로 구독이 종료됐습니다. 이미지 참고 부탁드립니다)
  - url : https://aquerytool.com/aquerymain/index/?rurl=89e9a03e-c93e-4dc6-9617-79ce1bc5fe64
  - pass : vtz0tb

- ERD 엑셀
  - https://github.com/ymkmoon/toyseven/tree/develop/src/main/resources/assets/xls

- 테이블 생성 쿼리
  - https://github.com/ymkmoon/toyseven/blob/develop/src/main/resources/assets/txt/Create_Table.txt

![ERD](/src/main/resources/assets/image/toyseven_erd.PNG)
