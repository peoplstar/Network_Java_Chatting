package Chapter07_08;

public class content {
/*
 * TCP
 
 * Socket ���� : ���α׷� ���� ����� ��ũ�� ���� ����, Ư�� ��Ʈ ��ȣ�� ����Ǿ�����
 * socket�� ���� bind �ʿ� bind�� ������ IP,port ä������
 * [server] listen() : socket() => �ɵ��� ����(���� �õ� �� ���) => ���������� ��ȯ
 * [client] connect() : server�� ���� (3-way-handshaking �õ�) => serversocket�� client IP, port ä����
 * accept() : Block ���� O, ���� �� Block End, return ������ ������ ������ ��ſ� �������� ��ȯ (���� �����ӿ��� ������ ���� ��û�� ����)
 * ServerSocket : ����� ����, ��ſ� ���� 2�� ����, ���ÿ� ���� ������ ���� ���� ���� ServerSocket(int port, int backlog_queue); default 50��
 * setSoTimeout() : Block method�� ��� �ð� ����, 10�ʴ� 10000
 
 * ������ �����ʿ� �ش� ��������(�����)���� �ϳ� ���� ��, accept() method�� ���
 * Ŭ���̾�Ʈ �������κ��� ���� ��û�� ���� �� ���������� ��ſ� �������� �����.
 * ������ ������� ���� ����.getOut(or In)putStream(); ���
 
 * Socket : Client�� ���� (�ϳ��� ����) Socket(String host or InetAddress address, int port);
 * readLine() : null �� ��� ��Ʈ������ ���� �����Ͱ� ����. ��, Ŭ���̾�Ʈ�� ������ ����ٴ� ���� �ǹ�, ���� ��ſ� ������ close()
 * TCP UDP echo ���� : ������ �̷�� �� ���� ������ �� �� �ֳ� ����, ������ Ŭ���̾�Ʈ�� ���� ���� ������ �𸥴�.
 * 30s ~ 60s ���� FIN ��Ŷ�� �ް� �ٷ� ���� ������ ���� �ʰ� (����) ��ٸ���.
 * REUSEADDR : ���� ���� ���� ��Ʈ ��ȣ ���� ����, ��Ƽĳ���� ������ ������ ��Ʈ��ȣ ��� ���� 
 
 * 				socketname = the ServerSocket();							*
 * �� ���� ���� ��, socketname.setReuseAddress(true); ������ bind ������ �ؾ��� ��		*
 * 				socketname.bind(new InetSocketAddress(7));					*
 
 * bindingExpection ���� X
 * Probe Packet : �ַ� �������� setKeepAlive(true)�� Ŭ���̾�Ʈ���� �޼����� ���� ���� �� ���, �ڿ� ������ ����
 * SoLinger : close()�� �۽Ź��ۿ� �����Ͱ� �����ִ°��� �� �����ϰ� ���� ����, ������ ���� �߻� �� ������ �����ϱ� ������ ����� ������ �Ǿ����� Ȯ���� ���ϰ� �ȴ�. close�� ���� linger �ð����� BLOCK ����. default = false
 * true, 0s �̸� ��Ŷ ��� 
 * TCP No delay Option = setTcpNoDelay(on): �۽� �����Ͱ� ���� ũ�⸸ŭ ������ �ʾƵ� �ٷ� ����(Nagle �˰��� ����) default = off
 * ShutDownIn(Out)put : ���� ���
 */
}
 