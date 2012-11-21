package base;
// ambient class
public class params {
	public static boolean debug = true;
	public static void println(Object s) {
		if(debug) System.out.println(s);
	}
}
