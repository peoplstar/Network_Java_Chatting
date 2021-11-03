package Chapter4;

public class content {

}
/*
문자 입출력 스트림 필요한 이유 : 자바는 문자를 두바이트 유니코드로 사용하는데, 문자에 대한 인코딩 방식을 변형하여 하는데 DataInput,Output은 불가능
세 바이트 이상인 다중 바이트 문자를 다루기 위함에 있어서 문자 입출력 스트림을 이용한다.
프로그램와 장치간의 데이터 전송을 문자 단위로 처리.

바이트 입출력(Input Output)스트림은 1바이트단위, Writer클래스는 2바이트 단위로 데이터 전송

Filewriter & reader class 기본 버퍼 1024 byte 사용, 명시적으로 인코딩 방식을 지정 불가
append : true 일경우 덮붙이기 가능

bufferedwrite & reader class : 문자 스트림 사이에 버퍼를 추가해 입출력 속도 증가
생성자 메소드는 인수로서 문자 스트림을 지정함
newLine() : 줄바꿈 메소드, 서버쪽에서는 줄바꿈이 일어났을지 안일어났을지 모름
 => 윈도우의 경우 \n를 이용하는 것이 바람직
 
bufferedreader : readLine() 줄단위로 읽을 때 유용하게 사용, EOF일 때 return 값이 null이다.

In, OutputStreamWriter : MS-949 인코딩 등 명시적으로 변환 가능, 한글(KSC5061)

PrintWriter : 다중 바이트 문자 집합 처리, 첫번째 인자로 Writer or OutputStream /
두번째 인자 autoflush에 true를 해야 자동으로 flush가 된다. 별도의 예외 처리 X
checkError()로 error 확인 가능

FilterWriter & Reader class : 추상클래스(상속해야함)
*/