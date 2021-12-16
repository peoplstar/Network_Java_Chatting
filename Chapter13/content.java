package Chapter13;

public class content {
/*
 * 멀티캐스트로 전송하려면 그룹 가입이 되어야 함.
 * > joinGroup(), leaveGroup()를 이용해 라우터에게 알려야 함.
 * 
 * TTL의 값은 setTimeToLive(int n)을 통해 설정, 전송을 위한 send(datagram, ttl)은 사용 X
 * 
 * joinGroup(SocketAddress, MulticastSocket.getNetworkInterface()) 이용
 * 
 */
}
