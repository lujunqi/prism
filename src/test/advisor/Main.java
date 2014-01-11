package test.advisor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{

	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("/test/advisor/xx.xml");
		Impl h = (Impl)context.getBean("aopF");
		h.del();
	}

}
