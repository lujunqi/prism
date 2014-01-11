package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test0904 {
	public static void main(String[] args) {
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put("wyp", "arg1");
		System.out.println(m);
		List<String> l = new ArrayList<String>();
		l.add("xx");
		l.add("yy");
		l.remove(1);
		System.out.println(l);
	}
}
