package test.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class Test1 implements MethodInterceptor
{
	
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable
	{
		System.out.println("before....");
		Impl i = (Impl)arg0.getThis();
		i.setStep(2);
		Object result =  arg0.proceed(); 
		System.out.println("after....");
		return null;
	}

}
