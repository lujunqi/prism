package test.aop;

public class Dao
{
	private String str;

	public String getStr()
	{
		return str;
	}

	public void setStr(String str)
	{
		this.str = str;
	}

	
	public void insert(){
		System.out.println(str);
	}
}
