package test.auto;

import org.aspectj.lang.annotation.Before;

//@Aspect
public class TestAop
{
//	@Pointcut("execution(* test.auto.Impl.*(..))")
	private void run1()
	{
	}

	@Before("run1()")
	public void before()
	{
		System.out.println("Aop before");
	}
}
