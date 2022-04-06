# toyseven

Gradle + h2 + jpa

SpringBoot 2.4.4
java 1.8


h2를 이용한 In-memory DB

model 을 사용하여 자동 테이블 생성,
data.sql 파일을 사용해 더미데이터 입력

swagger
	http://localhost:8000/toyseven/swagger-ui.html

h2 console
	http://localhost:8000/toyseven/h2-console
	jdbc:h2:mem:toyseven;MODE=MYSQL;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;
	test / test


API 활용 (Use Postman)

share link : https://www.getpostman.com/collections/21f459dc75f7db465b19
	
	Get Token
		Post http://YOUR_IP_ADDRESS:PORT/toyseven/auth/login
		RequestBody 
			{
				"username" : "guke",
				"password" : "1234"
			}
	
	Get Stations
		Get http://YOUR_IP_ADDRESS:PORT/toyseven/stations
	
	Get Stations/search
		Get http://YOUR_IP_ADDRESS:PORT/toyseven/stations/search?
		Parameter
			name : 102. 망원역 1번출구 앞
			
	Get Voc
		Get http://YOUR_IP_ADDRESS:PORT/toyseven/voc
	
	Post Voc
		Post http://YOUR_IP_ADDRESS:PORT/toyseven/voc
		RequestBody
			{
				"category" : 1,
				"title" : "test",
				"content" : "test",
				"username" : "username 1",
				"email" : "email",
				"stationId" : "4",
				"need_reply" : 1
			}
		
	Get Voc/search
		Get http://YOUR_IP_ADDRESS:PORT/toyseven/voc/search/1
	
	Post Voc/answer
		Post http://YOUR_IP_ADDRESS:PORT/toyseven/voc/answer
		RequestHeader
			Authorization Bearer accesstoken
		RequestBody
			{
				"questionId" : 1,
				"content" : "asdsa",
				"adminId" : 3
			} 
			
	Get weather
		Get http://YOUR_IP_ADDRESS:PORT/toyseven/weather?
		Parameter
			nx : 10
			ny : 18
			stationName : 구로구
