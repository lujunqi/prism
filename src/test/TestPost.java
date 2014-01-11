package test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class TestPost
{
	public static void main(String[] args)
	{
//		 System.out.println(999 % 9); 
		reMove();
		//doPost("http://127.0.0.1:8080/p/manager.Left.do", "id=1000");
	}
	public static void reMove(){
		List<String> list = new LinkedList<String>();
		Set<String> s = new HashSet<String>();
//		List<String> s = new LinkedList<String>();
		
		for(int i=0;i<500000;i++){
			list.add("pmy"+i);
			if(i % 2 == 0){
				s.add("pmy"+i);
			}
		}
		System.out.println(list.size());
		/*
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			if(s.contains(it.next())){
				it.remove();
			}
		}
		*/
		
		System.out.println(list.retainAll(s));
		System.out.println(list.size());
	}
	public static String doPost(String urlStr, String params)
	{
		HttpURLConnection con = null;
		String responseContent = null;
		try
		{
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
			out.write(params);
			out.flush();
			out.close();
			InputStream in = con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String tmpLine = rd.readLine();
			StringBuffer tmpStr = new StringBuffer();
			while (tmpLine != null)
			{
				tmpStr.append(tmpLine + "\n");
				tmpLine = rd.readLine();
			}
			responseContent = tmpStr.toString();
			return responseContent;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
