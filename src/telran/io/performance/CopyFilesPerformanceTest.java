package telran.io.performance;

import telran.io.CopyFile;
import telran.performance.PerformanceTest;

public class CopyFilesPerformanceTest extends PerformanceTest {

	String pathToSource;
	String pathToDestination;
	CopyFile copyFile;
	public CopyFilesPerformanceTest(String testName, int nRuns, String pathToSource, String pathToDestination,
									CopyFile copyFile) {
		super(testName, nRuns);
		this.pathToSource = pathToSource;
		this.pathToDestination = pathToDestination;
		this.copyFile = copyFile;
	}
	@Override
	protected void runTest() {
		try {
			copyFile.copy(pathToSource, pathToDestination);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}