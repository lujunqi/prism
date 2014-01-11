package test;

import java.util.Random;

public class Test0322 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 4个红球，5个绿球，6个蓝球。第二个袋子里有13个红球，6个绿球和1个蓝球
		int r1 = 4, g1 = 5, b1 = 6, r2 = 13, g2 = 6, b2 = 1;
		for (int i = 0; i < 500; i++) {
			//袋子1
			Random random = new Random();
			int s = random.nextInt(15)%(15+1)+1;
//			System.out.print(s+"==");
			if(s<=r1){//袋1抽中红球
				r1 = r1- 1;
				r2 = r2+1;
			}
			if(s> r1 && s<=r1+g1){//袋1抽中绿球
				g1 = g1- 1;
				g2 = g2+1;
			}
			if(s> r1+g1 && s<=r1+g1+b1){//袋1抽中蓝球
				b1 = b1- 1;
				b2 = b2+1;
			}
			Random random2 = new Random();
			int s2 = random2.nextInt(20)%(20+1)+1;
//			System.out.print("=="+s2+"==="+s+"==");
			if(s2<=r2){//袋1抽中红球
				r2 = r2- 1;
				r1 = r1+1;
			}
			if(s2> r2 && s2<=r2+g2){//袋1抽中绿球
				g2 = g2- 1;
				g1 = g1+1;
			}
			if(s2> r2+g2 && s2<=r2+g2+b2){//袋1抽中蓝球
				b2 = b2- 1;
				b1 = b1+1;
			}
			System.out.println(r1+","+g1+","+b1//+","+(float)r1/15
					+","+r2+","+g2+","+b2//+","+(float)r2/20+"]"
					
					);
			
		}

	}

}
