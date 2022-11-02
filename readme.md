# 오늘 공부한 내용

<br>
<br>
<br>

## Web server 와 WAS 공부 

+ Web server 

+ WAS 

## Spring mvc


스프링 부트 서블릿 환경 구성
서블릿 등록을 위해 main -> @ServletComponentScan 

서블릿이란 자바를 사용하여 웹페이지를 동적을 생성하는 서버측 프로그램 

web 서블릿을 통해 클라이언트와 통신, 요청, 응답등을 서블릿이 담당하도록하고 비지니스 로직은 개발자가 구현하도록 한다. 서블릿을 통해 비지니스 로직에만 집중!


서블릿 등록 
@WebServlet(name, urlPatterns)
urlPatterns에 실행될 url 인자로 입력

(protect)service 메소드을 통해 HttpServletrequest와 HttpServletresponse 이용

client가 웹 페이지(url)을 입력하면 AWS가 request와 response을 생성하고 urlPatterns에 해당하는 서블릿이 request의 정보를 이용해 request에 대한 response를 client에게 전달 // service 메소드 이용

### HttpServletRequest

Request format => request line, header, body // header 와 body사이에 공백으로 구분

+ Get 쿼리

url을 이용해 쿼리 파라미터 전달 => ?key1=value1&key2=value2

+ Html Form Post 쿼리

Html Form 이용해 request 와 데이터를 전달 
request => content-type : application/x-www-form-urlencoded , body : 입력한 form 데이터


==> Get과 Html Form Post 쿼리 모두 servlet에서 request.getParameter(key)로 파라미터 value 조회
파라미터 조회 방법 동일!

+ Http message body (text, json)

message body

1. text인 경우 / request content-type = text/plain
servlet에서 inputStream을 이용해 message body을 byte 형태로 수신(ServletInputStream)
수신한 inputStream을 StreamUtils.copyToString 메소드를 통해 String으로 변환
=>request.getInputStream() -> ServletInputStream -> StreamUtils.copyToString

2. json인 경우 / request content-type = application/json /ex) {"username": "hello", "age": 20}

Java에서 request message body을 클래스를 이용해 json으로 파싱
text와 같이 inputStream으로 message body를 수신하고 copyToString으로 String 형태로 만든 후
ObjectMapper 클래스를 이용해 json(클래스)형태로 파싱
objectMapper.readValue(messageBody, 파싱할 클래스)


### HttpServletResponse

request의 요청에 따라 서버가 client에게 적절한 response 반환해야 한다.
response format => 상태라인, header, body
response.getWriter(PrinterWriter 클래스) 이용해 message 송신
//PrintWriter writer = response.getWriter();


+ text response 

단순 text인 경우 writer.println("")을 통해 String 송신 

+ Html response 
response.setContentType("text/html")을 설정하고 
//추가로 response.setCharacterEncoding("utf-8") 

writer.println("<html>");
writer.println("<body>"); .... 와 같이 문자열을 html형식에 맞게 입력

+ Json response
response.setContentType("application/json") 설정
body 내용을 클래스로 설정하고(json) objectMapper.writeValueAsString(클래스 객체)을 이용해
문자열(json 형태)을 write.println(문자열)로 client한테 json/string 형태 송신



### MVC 패턴

먼저 JSP와 서블릿을 이용하여 MVC 패턴구현
JSP : 확장자가 jsp 로 html코드안에 java 블록을 삽입하는 형태
Servlet : 확장자가 java로 순수 자바 프로그램 / 추가로 PrintWriter 로 html코드 작성가능

M : model, V : view, C : controller
뷰와 컨트롤러를 나누어 확장성, 기능 특화에 도움


서블릿 라이프 사이클 => init , service <-> doGet(doPost), destroy

서블릿 service 메소드에서 request와 response 를 처리
controller 에서 requestDispatcher를 통해 다른 경로(뷰 또는 처리로직)로 이동 

service에서 GET request 에 대한 처리
만약 username 과 age가 온다면 request.getParameter 로 인자를 처리후
request.setAttribute로 데이터를 자바 객체로 매핑후 저장 // model 의 역할











