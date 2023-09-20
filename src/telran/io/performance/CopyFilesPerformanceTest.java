package telran.io.performance;

import telran.io.CopyFile;
import telran.performance.PerformanceTest;

public class CopyFilesPerformanceTest extends PerformanceTest {
	String sourceFile;
	String destinationFile;
	CopyFile copyFile;
	
	
	public CopyFilesPerformanceTest(String testName, String sourceFile, String destinationFile, CopyFile copyFile) {
		super();
		this.sourceFile = sourceFile;
		this.destinationFile = destinationFile;
		this.copyFile = copyFile;
	}
	
	protected void runTest() {
		
	}
	
	

}
