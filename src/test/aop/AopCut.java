package test.aop;

import org.aspectj.lang.JoinPoint;

public class AopCut
{

	public void card( String argvalue)
	{
		 
			System.out.println("==========切入card[" +1 + "]");
	
	}

	public void user(JoinPoint jp)
	{
		System.out.println("==========切入user");
	}

}
