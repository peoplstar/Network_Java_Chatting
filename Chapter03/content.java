package Chapter03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.io.*;

public class content {
		public static void main(String args[]) {

			

			//junghn.tistory.com/entry/JAVA-자바-날짜-포맷-변경-방법SimpleDateFormat-yyyyMMdd [코딩 시그널]
	}
}
/*
file class : 운영체제에 관계 없이 파일에 대한 이로간된 방식으로 접근 가능
public File(String pathname) => 지정한 파일에 대한 File 객체 생성, 디렉토리로 파일 객체 생성 가능, 경로부터 다 입력해야함
public File(String directory, String filename) => 디렉토리 경로와 파일 이름 구별
public String getAbsoultePath() => 절대경로 \..\.\를 포함
public getCanonicalPath() => 정규경로 \..\.\가 없음 {
	C:\temp\file.txt - This is a path, an absolute path, and a canonical path.
	.\file.txt - This is a path. It's neither an absolute path nor a canonical path.
	C:\temp\myapp\bin\..\\..\file.txt - This is a path and an absolute path. It's not a canonical path.
	A canonical path is always an absolute path.
	}

public String getParent() => 상위경로 리턴
mkdir  => ex) c:/b 하나의 경로 생성
mkdirs => ex) c:/a/b/c 와 같이 여러개의 경로를 생성
public RandomAccessFile(...) throws FileNotFoundException 해당 파일이 없을 때 오류 발생
native : 라이브러리 액세스 할 때 이용되는 키워드 / 무시하세요!  
seek() 활용할 줄 알아야함. 위치 선별. FileOutputStream과의 큰 차이점
*/