package tp.p3.util;

import java.io.*;
public class MyStringUtils {
	// used in text representation of Game and Board objects
	public static final int MARGIN_SIZE = 4;
	
	
	public static String repeat(String elmnt, int numOfTimes) {
		String result = "";
		for (int i = 0; i < numOfTimes; i++) {
			result += elmnt;
		}
		return result;
	}
	
	
	public static String centre(String text, int len){
		String out = String.format(" %"+len+"s %s %"+len+"s", "",text,"");
		float mid = (out.length()/2);
		float start = mid - (len/2);
		float end = start + len;
		return out.substring((int)start, (int)end);
	}
	// Used to exist method: org.eclipse . core. internal . resources . OS.isNameValid(filename).
	// This method is not completely reliable since exception could also be thrown due to:
	// incorrect permissions , no space on disk , problem accessing the device ,...
	public static boolean validFileName(String filename) {
		try {
			File file = new File(filename);
			if (file.exists()) {
				return canWriteLocal(file);
			} else {
				try {
					file.createNewFile();
					file.delete() ;
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		}catch(NullPointerException e) {
			System.out.println("Invalid filename: the filename contains invalid characters" + e);
			return false;
		}
	}
	
	
	
	
	public static boolean canWriteLocal(File file) {
		// works OK on Linux but not on Windows (apparently!)
		if (!file.canWrite()) {
			return false;
		}
		// works on Windows
		try {
			new FileOutputStream(file, true).close();
		} catch (IOException e) {
			return false;
		}
			return true;
	}
}