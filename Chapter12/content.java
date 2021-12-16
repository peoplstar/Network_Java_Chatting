package Chapter12;
//Chatper 12 : URLconnection
public class content {
/*
 * URLConnection Class : 추상 클래스, URL이 지정하는 파일을 읽고 POST 방식으로 웹 서버에 데이터를 전송
 * 웹 서버 정보, 파일 형식, 길이, MIME 헤더 정보
 * protected URLConnection(URL url), url.openConnection() 의 return 값이 URLconnection 즉, 서브 클래스로부터 객체 생성해야함
 * ex)  URL u; URLConnection uc;
 * 		try { u = new URL(...); uc = u.openConnection(); } catch(MalformedURLException e) {} }
 * MIME 분석 일반 / 세부유형 : text/html, text/plain, image/gif, image/jpeg, video/mpeg... => 헤더에 포함 된 경우 getConectType()으로 알 수 있음, 포함 X 시 null return
 * getContentLength() : 포함 X 시 -1 return, getContentEncoding() : 보통 base64, quoted printable,
 * 
 * URLConnection은 setup 상태, connected 상태, 기본은 setup 상태로 되어 있다. 환경 설정를 위해서는 setup 상태가 되어야 한다.
 * connected 상태는 connect() 함수 호출 시 connected 상태로 변경됨. or getContentType(), getContentLength() : 헤더 접근 시 connected 상태
 * 서버로부터 데이터 읽고/쓰는 > getInputStream(), getOutputStream(), setDoInput(true) :: default가 true가 안해도 됨 :: Output default : false
 * getOutputStream() 사용전 setDoOutput(true)로 output 허용해야 에러 발생 X
 * Content 길이를 알고 해당 길이까지만 읽을 수 있게 설정 . because : binary파일은 EOF이 null 인지 알 수 없기 때문에
 * setRequestProperty(String key, String value) : 요청 메세지 헤더 설정,
 * 사용자와 상호작용을 위해서는 setAllowUserInteraction(true)
 * => default 상태 setup 상태 일 때 모든 환경 설정을 끝내고 connect()를 통해 connected 상태로 만들고, setDoInOutput(true)를 통해 입출력을 진행한다.
 * 
 */
}
