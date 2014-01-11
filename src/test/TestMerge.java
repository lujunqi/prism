package test;

import java.util.ArrayList;
import java.util.List;

public class TestMerge {
	public static void main(String[] args) {
		List<String> l1 = new ArrayList<String>();
		l1.add("BEGIN");
		l1.add("A");
		l1.add("B");
		l1.add("C");
		l1.add("D");
		l1.add("E");
		l1.add("F");
		l1.add("G");

		l1.add("END");
		List<String> l2 = new ArrayList<String>();
		l2.add("BEGIN");
		l2.add("W");
		l2.add("A");
		l2.add("B");
		l2.add("X");
		l2.add("Y");
		l2.add("Z");
		l2.add("E");
		l2.add("END");

	}
}
