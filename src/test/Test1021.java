package test;

import java.util.List;

import test.diff_match_patch.Diff;

public class Test1021 {

	public static void main(String[] args) {
		diff_match_patch dmp = new diff_match_patch();
		String t1 = "abcdf";
		String t2 = "wabde";

		List<Diff> l = dmp.diff_main(t1, t2);
//		System.out.println(l);
		StringBuilder sb = new StringBuilder(t1);
//		String step = "";
		for (Diff diff : l) {
			String t = diff.text;
			String n = diff.operation.name();
			
			System.out.println(t + "==" + n);
//			if(diff.operation.name().equals("EQUAL")){
//				System.out.println(diff.text.length());
//			}
		}
		System.out.println(sb+"=======");
		// [Diff(INSERT,"w"), Diff(EQUAL,"ab"), Diff(DELETE,"c"),
		// Diff(EQUAL,"d")]

	}

}
