package test.advisor;

public class Impl
{
	public void add()
	{
		System.out.println("==============add["+step+"]");
	}
	public void del(){
		System.out.println("===========del["+step+"]");
	}
	private int step = 0;
	public int getStep()
	{
		return step;
	}
	public void setStep(int step)
	{
		this.step = step;
	}
}
