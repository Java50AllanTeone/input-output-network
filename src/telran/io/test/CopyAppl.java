package telran.io.test;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

public class CopyAppl {
	public static final int BUFFER_LENGTH = 1024 * 1024;
	
	public static void main(String[] args) {
		
		if (args.length < 2)
			System.out.printf("Too few arguments\n");
		else if (!Files.exists(Path.of(args[0])))
			System.out.printf("file %s must exist\n", args[0]);
		else if (args.length < 3 || !Objects.equals(args[2], "overwrite"))
			System.out.printf("file %s cannot be overwritten\n", args[1]);
		else {
			System.out.println(copyFile(Path.of(args[0]), Path.of(args[1])));
		}
		
	}
	
	static String copyFile(Path in, Path out) {
		String msg;
		long startTime = System.currentTimeMillis();
		
		try (OutputStream output = Files.newOutputStream(in); 
				InputStream input = Files.newInputStream(out)) {
			
			int counter;
			long bytes = 0;
			byte[] buffer = new byte[BUFFER_LENGTH];
			
			while ((counter = input.read(buffer)) > 0) {
				output.write(buffer, 0, counter);
				bytes += counter;
			}
			
			msg = String.format("Succesful copying of %d bytes have been copying "
					+ "from the file %s to the file %s. Time %d millis", bytes, in, out, System.currentTimeMillis() - startTime);
			
		} catch (FileNotFoundException e) {
			msg = String.format("The directory %s must exist", getErrDirName(out));
		} catch (IOException e) {
			msg = "Acess exception";
		}
		return msg;
	}
	
	
	
	
	static String getErrDirName(Path path) {
		StringBuilder sb = new StringBuilder();	
		String[] dirs = path.toString().split(File.separator);
		
		for (String dir : dirs) {
			sb.append(dir).append(File.separator);
			path = Path.of(sb.toString());
					
			if (!Files.exists(path)) {
				return path.getName(path.getNameCount() - 1).toString();
			}
		}
		return null;		
	}

}
