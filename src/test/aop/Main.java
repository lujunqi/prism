package test.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main
{
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("/test/aop/applicationContext.xml");
		Dao h = (Dao)context.getBean("card");

		System.out.println("===============");
		h.getStr();
		h.insert();
		System.out.println("===============+++++++++++++");
		
//		h = (Dao)context.getBean("user");
//		System.out.println("===============");
//		h.insert();
		
		
	}
}
