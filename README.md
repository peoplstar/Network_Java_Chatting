# Network_Java_Chatting

`It is a task of Java Chat in the second semester of our university's third year.`   
`Regarding TCP and UDP, socket programming and file input/output streams are mainly designed.`

---
> Chapter 1   

1. 커맨드 라인으로부터 2개의 파일 이름들을 입력 받아 모니터로 출력하는 클래스를 작성하시오. 화면에
출력 시 파일 이름과 함께 내용을 출력하고 각 파일의 출력 내용은 ‘======‘ 기호로 구분하시오.
파일이름은 편의상 file1.txt, file2.txt로 하시오.

2. GUI 기반(스윙)으로 그림과 같이 파일을 복사하고, 파일 내용을 TextArea에 출력하는 클래스를 작성하시오.
(입력파일 입력창과 출력 파일 입력 창에 이름을 입력 후 확인 버튼을 누르면 파일입력스트림 객체와
파일출력스트림 객체를 생성하여 입력 파일의 내용을 출력파일에 저장하고 그 내용을 TextArea에 출력 함)



> Chapter 2
1. 예제 2.1 과 2.3을 통합하세요.
2. 예제 2.2와 2.4를 통합하세요.
3. 예제 2.5 수정. 고객 전화번호 입력란을 추가하세요. ‘종료’ 버튼을 ‘출력’ 버튼으로 수정하여 ‘출력’ 버튼을
누르면 구좌번호에 해당하는 이름과 잔고, 전화번호를 텍스트 필드에 출력함.
윈도우 종료는 x버튼을 눌러 시행함
4. FilterOutputStream 클래스를 상속하여 2개의 기본 스트림에 동시에 write 하는 클래스를 작성하시오.
즉, 한 번 write() 하면 2개의 기반 스트림 객체에 write가 되도록 write(int a), write(byte[]), flush(), close() 메소드를
재정의하시오. 그 다음, 이 클래스를 이용하여 한 개의 파일을 2개의 새로운 파일에 복사하는 클래스를
작성하시오. 단, 파일 이름들은 명령행 인자로 받아들이고, 복사에 사용하는 메소드는 예제 2.7의 ‘copy()’ 를
활용하시오.

> Chapter 3

1. 예제 3.1을다음과 같이 수정하시오.
- 파일에 관한 정보 출력은 BorderLayout.CENTER에 출력
- 디렉토리 및 파일 내용은 BorderLayout.SOUTH에 출력
- 파일 정보 출력 시 CanonicalPath 추가
- 최종 수정 시각은 yyyy년 MM월 dd일 (w요일) hh시 mm분의 형식으로 출력함
2. jpg 파일을 입력 받아 copy.jpg로 복사하는 클래스를 작성하시오. 한 번에 1KB 단위로 데이터를
복사하고 복사를 진행하는 동안 10% 진행할 때마다 ‘*’를 하나씩 출력하며 RandomAccessFile
클래스를 이용하시오.
3. 예제 3.2를 수정하여 의해 ‘입력’ 버튼을 누르면 정보를 저장하고, 출력 버튼을 누르면 계좌번호 또는 이름에
해당하는 Record를 읽어 출력하는 클래스를 작성하세요. 윈도우의 x버튼을 클릭하면 윈도우는 닫히고
프로그램이 종료된다.

> Chapter 4
1. 문자열을 키보드로 입력 받아 파일에 저장한 후 저장된 파일을 읽어 화면에 출력(PrintWriter이용)하는 클래스를
작성하세요.
2. 파일명을 입력 받아 그 파일의 내용을 읽어들여 줄 번호를 붙여 새로운 파일(파일명은 numbered_입력파일명)에
저장한 후 저장된 파일의 내용을 읽어 화면에 출력하는 GUI 프로그램을 작성하시오(LineNumberReader 이용).
3. 사용자로부터 2개의 파일 이름을 입력 받고 첫 번째 파일 뒤에 두 번째 파일을 덧붙여 새로운 파일을 생성하는
프로그램을 작성하시오(BufferedReader/BufferedWriter 이용)
4. 사용자로부터 2개의 파일 이름을 입력 받고 두 파일의 내용을 비교하여 같으면 각 파일의 최종 수정 시간을
출력하고, 다르면 각 파일의 길이를 출력하는 프로그램을 작성하시오.

> Chapter 5_6
1. 예제 5.6에 추가하여 로컬 호스트의 루프백 주소를 출력할 수 있도록 하시오.
원격 호스트가 여러 개의 주소를 갖는 경우 모든 주소를 함께 출력하시오.
입력된 원격 호스트의 IP 주소와 메소드 ‘static InetAddress getByAddress(byte[] addr)’를 이용하여 새로운 InetAddress
객체를 생성한 후 두 객체가 같은 지 비교하여 그 결과를 출력하시오.
2. 예제 5.7을 다음과 같이 수정하세요.
- 입력된 호스트에 할당된 모든 주소를 2번째 Panel의 TextArea 에 출력
- 3번째 Panel을 추가한 후 호스트의 대표 IP 주소의 클래스 유형을 TextField에 출력
3. 예제 6.8을 다음과 같이 수정하세요.
- TextArea를 하나 추가
- 첫 번째 TextArea에는 protocol, host name, port no., file name, hash code 등 원격호스트의 정보를 출력한다.
- 두 번째 TextArea에는 URL이 가리키는 객체에 따라 텍스트이면 내용을 읽어와 모두 출력하고, 이미지, 오디오,
비디오 객체인 경우에는 그 유형만 나타내시오.

> Chapter 7_8

1. DayTime 클라이언트와 서버를 아래의 내용을 참조하여 수정하세요.
 - 서버는 클라이언트에 시간 정보를 송싱한 후 송신채널만 종료
 - 클라이언트는 시간 정보를 수신한 후 "Thank you"를 송신하고 소켓을 닫음
 - 서버는 클라이언트로부터의 "Thank you" 메세지를 수신하여 출력한 후 소켓을 닫음
2. 예제 7.4를 수정하여 스레드를 이용한 다중처리와 주소 재사용이 가능한 echo 서버로 만드세요
3. 다음 메소드들의 실행 결과를 출력하여 확인하세요.
toString(), getSendBufferSize()/setSendBufferSize(), getReceiveBuffersize()/setReceiveBufferSize(),
getKeepAlive()/setKeepAlive(), getTcpNoDelay()/setTcpNoDelay(), getReuseAddress()
4. 파일 다운로드 서버를 아래의 내용을 참조하여 수정하세요.
- 클라이언트(웹브라우저)가 주소창에 파일을 요청하면 서버는 파일을 클라이언트로 전송
 
> Chapter 9
- OneToOneC2 / OneToOneS2   
1. 클라이언트와 서버에 각각 연결 정보 출력
2. 클라이언트 연결 종료 시 입력 내용 출력 안되게 하기
3. 클라이언트 재접속 기능 추가 
4. 클라이언트 윈도우 종료 시 서버에 접속 중단 메세지 출력
5. 한 클라이언트 접속 중 다른 클라이언트가 접속 시 서버가 종료시킴

- MultipleCathC2 / MultipleChatS2
1. 서버의 info label 제거, TextArea에 클라이언트 연결 정보(IP:Port) 출력 
2. 'quit' 입력 시 서버와의 연결 종료하며 창을 닫음
3. 클라이언트 연결 설정/종료 시 접속 돼 있는 다른 클라이언트들에게 정보 전송
4. 사용자 이름 입력 창 추가
5. 대화말 송신자에게는 브로드캐스트 안되게 함
    
> Chapter 11

1. UDP 소켓을 이용하여 daytime 서버와 클라이언트를 구현하세요.
2. 예제 11.7 및 11.8을 참조하여 일대일 채팅 프로그램을 작성하세요 (UDP 이용).
3. 온라인 사전 서버와 클라이언트 만들기
- 클라이언트가 영어 단어를 서버로 보내면 서버는 해당 단어에 대한 뜻을 클라이언트로 전송
- 사전은 파일로 관리