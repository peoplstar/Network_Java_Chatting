package Chapter03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.io.*;

public class content {
		public static void main(String args[]) {

			

			//junghn.tistory.com/entry/JAVA-�ڹ�-��¥-����-����-���SimpleDateFormat-yyyyMMdd [�ڵ� �ñ׳�]
	}
}
/*
file class : �ü���� ���� ���� ���Ͽ� ���� �̷ΰ��� ������� ���� ����
public File(String pathname) => ������ ���Ͽ� ���� File ��ü ����, ���丮�� ���� ��ü ���� ����, ��κ��� �� �Է��ؾ���
public File(String directory, String filename) => ���丮 ��ο� ���� �̸� ����
public String getAbsoultePath() => ������ \..\.\�� ����
public getCanonicalPath() => ���԰�� \..\.\�� ���� {
	C:\temp\file.txt - This is a path, an absolute path, and a canonical path.
	.\file.txt - This is a path. It's neither an absolute path nor a canonical path.
	C:\temp\myapp\bin\..\\..\file.txt - This is a path and an absolute path. It's not a canonical path.
	A canonical path is always an absolute path.
	}

public String getParent() => ������� ����
mkdir  => ex) c:/b �ϳ��� ��� ����
mkdirs => ex) c:/a/b/c �� ���� �������� ��θ� ����
public RandomAccessFile(...) throws FileNotFoundException �ش� ������ ���� �� ���� �߻�
native : ���̺귯�� �׼��� �� �� �̿�Ǵ� Ű���� / �����ϼ���!  
seek() Ȱ���� �� �˾ƾ���. ��ġ ����. FileOutputStream���� ū ������
*/