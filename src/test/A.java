package test;

import java.util.Calendar;

public class A {
	public static void main(String[] args) {
		
		Calendar cal = Calendar.getInstance();//
		cal.add(Calendar.DAY_OF_MONTH, 7);
		System.out.println(cal.getTime().getTime());
	}
}

class B {
	public void callback(C c) {
		System.out.println("=======3");
		String t = "line 18";
		try {
			Thread.sleep(1000);
			t = "line 19";
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.d(t);
		System.out.println("=======4");
	}
}

interface C {
	public void d(String t);
}
