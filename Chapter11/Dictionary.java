package Chapter11;

import java.io.*;
import java.net.*;

class HTMLDecoder {
	public static String htmlEntityDecode(String s) {
		int i = 0, j = 0, pos = 0;
		StringBuffer sb = new StringBuffer();
		while ((i = s.indexOf("&#", pos)) != -1 && (j = s.indexOf(';', i)) != -1) {
			int n = -1;
			for (i += 2; i < j; ++i) {
				char c = s.charAt(i);
				if ('0' <= c && c <= '9') {
					n = ( n == -1 ? 0 : n * 10) + c - '0';
				}
				else {
					break;
				}
			}
			if (i != j) {
				n = -1;
			}
			if (n != -1) {
				sb.append((char)n);
				i = j + 1; // skip ;
				
				if (';' == s.charAt(j))
					if (',' == s.charAt(j + 1)) {
						sb.append((char)',');
						sb.append((char)' ');
					}
					if (')' == s.charAt(j + 1)) 
						sb.append((char)' ');
					if (';' == s.charAt(j + 1)) { 
						sb.append((char)',');
						sb.append((char)' ');
					}
				}
				else {
					for (int k = pos; k < i; ++k) {
						
						sb.append(s.charAt(k));
					}
				}
				pos = i;
			}
			if (sb.length() == 0) {
				return s;
			} 
			else {
				sb.append(s.substring(pos, s.length()));
			}
			return sb.toString();
	}
}
public class Dictionary {
	static String urlPath1 = "https://www.ybmallinall.com/styleV2/dicview.asp?kwdseq=0&kwdseq2=0&DictCategory=DictEng&DictNum=1&ById=0&PageSize=5&StartNum=0&GroupMode=0&cmd=0&kwd=";
	static String urlPath2 = "&x=0&y=0";		
    static String pageContents;
    static String unicode;
    static String contents; // HTML Source storage
    static URL url;
    static String data;
    public static void main(String[] args) {
        try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String theLine = input.readLine();
			String urlPath = new String(urlPath1 + theLine + urlPath2);
			url = new URL(urlPath);
            HTMLsource(url); // HTML Source 가져오기
            
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public static void HTMLsource(URL url) {
    	try {
    		URLConnection con = (URLConnection)url.openConnection();
        	InputStreamReader reader = new InputStreamReader (con.getInputStream());
        	contents = new String();
        	BufferedReader buff = new BufferedReader(reader);
        
        	while ((pageContents = buff.readLine())!=null) {
        		if (pageContents.contains("<span class=\"ExplainNum\">1 </span>")) {        			
        			//byte[] utf8 = pageContents.getBytes();
        
        			String str = new String(pageContents.getBytes("utf-8"), "utf-8");
        			int i = str.indexOf("<span class=\"ExplainNum\">1 </span>");
        			String find = str.substring(i+35);
        			int j = find.indexOf("<");
        			String load = find.substring(0, j);
        			//contents.append("RRRR : " + (load) + "\r\n");
            		contents = (HTMLDecoder.htmlEntityDecode(load) + "\r\n");
            		//contents.append("\r\n");
            		

        		}
        		else {
        			
        		}
        	}
        	buff.close();
        	System.out.println(contents);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }  
}