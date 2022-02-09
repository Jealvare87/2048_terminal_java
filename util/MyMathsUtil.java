package tp.p3.util;


public class MyMathsUtil {
	// convert from long to int since we will not need to use large numbers
	public static int nextFib(int previous) {
		double phi = (1 + Math.sqrt(5)) / 2;
		return (int) Math.round(phi * previous);
	}
}
