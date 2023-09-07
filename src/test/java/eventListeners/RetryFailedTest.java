package eventListeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import seleniumWebDriverPracticeOnEbay.Reuseables;

public class RetryFailedTest extends Reuseables implements IRetryAnalyzer{
	private byte i=0;
	private byte maxRetry= Byte.parseByte(loadProperty().getProperty("failedTestRetry"));
	@Override
	public boolean retry(ITestResult result) {
		if ( i<maxRetry ) {
			i++;
			return true;
		}
		return false;
	}

}
