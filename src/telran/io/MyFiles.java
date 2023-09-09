package telran.io;

import java.io.IOException;
import java.nio.file.*;

public class MyFiles {
	public static void displayDir(String path, int maxDepth) throws IOException {

		try {
			Path actPath = Path.of(path).normalize();
			int pLength = actPath.getNameCount();

			if (!Files.isDirectory(actPath) || maxDepth < 0) {
				throw new IllegalArgumentException();
			}
				
			Files.walk(actPath, maxDepth)
			.map(p -> " ".repeat(p.getNameCount() - pLength) + p.getFileName())
			.forEach(System.out::println);

		} catch (InvalidPathException e) {
			throw new IllegalArgumentException();
		}
	}
}