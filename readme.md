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



