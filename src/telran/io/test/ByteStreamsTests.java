package telran.io.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ByteStreamsTests {
	
	private static final String DATA = "Hello world!!!";
	private static final String FILE_NAME = "hello.txt";

	@Test
	@Order(1)
	void fileOutputStream() throws Exception {

		try (OutputStream output = new FileOutputStream(FILE_NAME)) {
			output.write(DATA.getBytes());
		}
	}
	
	@Test
	@Order(2)
	void fileInputStream() throws FileNotFoundException, IOException  {
		try(InputStream input = new FileInputStream(FILE_NAME)) {
			int length = input.available();
			byte[] buffer = new byte[length];
			input.read(buffer);
			assertEquals(DATA, new String(buffer));
			
		}
	}

	

}
