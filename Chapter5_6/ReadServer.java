package Chapter5_6;

import java.io.*;
import java.net.*;

 
public class ReadServer {
	 
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
 
        String urlPath = "https://www.ybmallinall.com/styleV2/dicview.asp?kwdseq=0&kwdseq2=0&DictCategory=DictEng&DictNum=1&ById=0&PageSize=5&StartNum=0&GroupMode=0&cmd=0&kwd=hello&x=0&y=0";
        String pageContents = "";
        StringBuilder contents = new StringBuilder();
 
        try{
 
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "utf-8");
 
            BufferedReader buff = new BufferedReader(reader);
 
            while((pageContents = buff.readLine())!=null){
                //System.out.println(pageContents);             
                contents.append(pageContents);
                contents.append("\r\n");
            }
 
            buff.close();
 
            System.out.println(contents.toString());
 
        }catch(Exception e){
            e.printStackTrace();
        }
 
    }
 
}