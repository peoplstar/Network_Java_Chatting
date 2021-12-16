package Chapter12;
//Chatper 12 : URLconnection
public class content {
/*
 * URLConnection Class : �߻� Ŭ����, URL�� �����ϴ� ������ �а� POST ������� �� ������ �����͸� ����
 * �� ���� ����, ���� ����, ����, MIME ��� ����
 * protected URLConnection(URL url), url.openConnection() �� return ���� URLconnection ��, ���� Ŭ�����κ��� ��ü �����ؾ���
 * ex)  URL u; URLConnection uc;
 * 		try { u = new URL(...); uc = u.openConnection(); } catch(MalformedURLException e) {} }
 * MIME �м� �Ϲ� / �������� : text/html, text/plain, image/gif, image/jpeg, video/mpeg... => ����� ���� �� ��� getConectType()���� �� �� ����, ���� X �� null return
 * getContentLength() : ���� X �� -1 return, getContentEncoding() : ���� base64, quoted printable,
 * 
 * URLConnection�� setup ����, connected ����, �⺻�� setup ���·� �Ǿ� �ִ�. ȯ�� ������ ���ؼ��� setup ���°� �Ǿ�� �Ѵ�.
 * connected ���´� connect() �Լ� ȣ�� �� connected ���·� �����. or getContentType(), getContentLength() : ��� ���� �� connected ����
 * �����κ��� ������ �а�/���� > getInputStream(), getOutputStream(), setDoInput(true) :: default�� true�� ���ص� �� :: Output default : false
 * getOutputStream() ����� setDoOutput(true)�� output ����ؾ� ���� �߻� X
 * Content ���̸� �˰� �ش� ���̱����� ���� �� �ְ� ���� . because : binary������ EOF�� null ���� �� �� ���� ������
 * setRequestProperty(String key, String value) : ��û �޼��� ��� ����,
 * ����ڿ� ��ȣ�ۿ��� ���ؼ��� setAllowUserInteraction(true)
 * => default ���� setup ���� �� �� ��� ȯ�� ������ ������ connect()�� ���� connected ���·� �����, setDoInOutput(true)�� ���� ������� �����Ѵ�.
 * 
 */
}
