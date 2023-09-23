package telran.io;

import java.io.*;

public class ReadBufferCopy implements CopyFile {
	private final int bufferSize;
	public ReadBufferCopy(int bufferSize) {
		super();
		this.bufferSize = bufferSize;
	}
	@Override
	public void copy(String pathToSource, String pathToDestination) throws Exception{
		try(FileInputStream input = new FileInputStream(pathToSource);
			FileOutputStream output = new FileOutputStream(pathToDestination)) {
			int length = 0;
			byte[] buffer = new byte[bufferSize];
			while((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}

		}

	}

}