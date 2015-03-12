package mw.filesystem;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ProjectFolder {

	private static final String SRC_FOLDER = initialize();
	
	/* ========================
	 * 		Static methods
	 * ========================
	 */

	public static String getPath() {
		return SRC_FOLDER;
	}
	
	private static String initialize()
	{
		try
		{
			String s = Files.readAllLines(Paths.get("srcpath.txt"), Charset.defaultCharset()).get(0);
			s = s.replace('\\', '/');
			
			if (s.charAt(s.length()-1)!='/')
				s = s+'/';
			
			System.out.println("The source folder is "+s);
			return s;
		}
		catch (IOException e)
		{
			System.out.println("Error while loading the file 'srcpath.txt'");
			System.exit(1);
			return null;
		}
	}
	
}