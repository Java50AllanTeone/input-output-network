package telran.io;
import java.io.*;
public class TransferToCopy implements CopyFile {

	@Override
	public void copy(String pathToSource, String pathToDestination)throws Exception {
		try(InputStream input = new FileInputStream(pathToSource);
			OutputStream output = new FileOutputStream(pathToDestination)) {
			input.transferTo(output);
		}

	}

}