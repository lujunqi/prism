package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TestUtf
{
	public static void main(String[] args) throws Exception
	{
		File inputfile = new File("e:/1300W.TXT");
		FileReader fr = new FileReader(inputfile);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(fr);
		String s;
		Long l1 = new Date().getTime();
		List<FileWriter> lFile  = files(10);
		
		while ((s = br.readLine()) != null) {
			//s.endsWith()
			for(int i =0;i<lFile.size();i++){
				if(s.endsWith(""+i)){
					FileWriter fw = lFile.get(i);
					fw.write(s);
					fw.flush();
					break;
				}
			}
		}
		for(int i =0;i<lFile.size();i++){
			lFile.get(i).close();
		}
		Long l2 = new Date().getTime();
		System.out.println(l2-l1);
	}
	public static List<FileWriter> files(int max){
		List<FileWriter> l = new ArrayList<FileWriter>();
		for(int i=0;i<max;i++){
			File file = new File("e:/1225/"+i+".txt");
			try
			{ 
				file.createNewFile();
				FileWriter fw = new FileWriter(file,true);
				l.add(fw);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return l;
	}
}
