package Chapter11;

public class Contents {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
/*
UDP : �޽��� ���� ��������, ���ἳ���� ���� �񿬰��� ��Ÿ�, ª�� ������ ���� ��ſ� ���
��� 8����Ʈ, �ɼ� X, overhead�� ������
���� : ���� ���α׷� �������̽�(API), ��Ʈ��ũ �ý����� â�� ����, �ü���� �ڿ��� ���� ����ϹǷ� �ݵ�� close()�ؾ��Ѵ�.
���ἳ�� ������ ���⶧���� TCP�ʹ� �޸� ��Ŷ�� ������ �ּҰ� ���� �Ǿ�� �Ѵ�.
SocketAddress(�߻� Ŭ����) = IPAddress + PortNumber
=> InetSocketAddress(InetAddress addr, int port);
�۽ſ� DatagramPacket ���� ��, address Ÿ���� �߸��� ��� Error ��, InetAddress�� �����ڷ� ���� ���� �߻�


*/