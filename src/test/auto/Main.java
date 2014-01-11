package test.auto;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("/test/auto/spring.xml");
		Impl h = (Impl) context.getBean("id14");
		h.tRun();
		System.out.println("================");
		h.xxx();
	}

	
}
