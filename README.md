## toyseven

- ``SpringBoot 2.4.4``
- ``java 1.8``
- ``Gradle 7.2``
- ``h2 In-memory DB``
- ``JPA``
- ``AWS Cognito``

- ``swagger``
  - http://localhost:8000/toyseven/swagger-ui.html

- ``h2 console``
  - http://localhost:8000/toyseven/h2-console
  - jdbc:h2:mem:toyseven;MODE=MYSQL;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;
  - test / test

- ``Postman``
  - share link : https://api.postman.com/collections/15553250-447514e3-cd1c-4178-9b07-fb5ed00bf7c0?access_key=PMAT-01GRQD6M4FRFGDPMHSKAQGF48C
  
- ``Admin Server``
  - https://github.com/ymkmoon/admin

## Used API

  - 서울시 공공자전거 실시간 대여정보 
    - https://data.seoul.go.kr/dataList/OA-15493/A/1/datasetView.do
  - 기상청 동네예보 조회서비스 
    - https://www.data.go.kr/iim/api/selectAPIAcountView.do
  - 한국환경공단 에어코리아 대기오염정보 
    - https://www.data.go.kr/iim/api/selectAPIAcountView.do

## Entity

- aQuery (현재 재정적인 문제로 구독이 종료됐습니다. 이미지 참고 부탁드립니다)
  - url : https://aquerytool.com/aquerymain/index/?rurl=89e9a03e-c93e-4dc6-9617-79ce1bc5fe64
  - pass : vtz0tb

- ERD 엑셀
  - https://github.com/ymkmoon/toyseven/tree/develop/src/main/resources/assets/xls

- 테이블 생성 쿼리
  - https://github.com/ymkmoon/toyseven/blob/develop/src/main/resources/assets/txt/Create_Table.txt

![ERD](/src/main/resources/assets/image/toyseven_erd.PNG)
