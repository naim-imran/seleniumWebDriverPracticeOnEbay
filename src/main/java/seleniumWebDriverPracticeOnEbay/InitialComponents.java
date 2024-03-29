package seleniumWebDriverPracticeOnEbay;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.stream.Stream;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pageObjectsFactory.HomePage;

public class InitialComponents extends Reuseables {

	private ThreadLocal<WebDriver> threadLocaldriver = new ThreadLocal<WebDriver>();
	private ThreadLocal<ExtentReports> threadLocalReporter = new ThreadLocal<ExtentReports>();
	private ExtentSparkReporter reporter;
	private ExtentReports extentReports;
	public WebDriver driver;

	public ExtentReports configureExtentReport() {
		reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\reportsAndScreenshoots\\"
				+"seleniumWebDriverPracticeOnEbay"+ getCurrentTimeToFormatedString() + ".html");
		reporter.config().setDocumentTitle("Selenium WebDriver Practice on eBay");
		reporter.config().setReportName("Report name " + getCurrentTimeToFormatedString());

		extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Operating System ", System.getProperty("os.name"));
		threadLocalReporter.set(extentReports);
		return extentReports;
	}
	

	public HomePage launchBrowser( ) {
		
		  String browserName = System.getProperty("browser") != null ?
		  System.getProperty("browser") : loadProperty().getProperty("browser");
		 

		if (browserName.equalsIgnoreCase("chrome") && threadLocaldriver.get() == null)  {
			ChromeOptions co = new ChromeOptions();
			co.setBrowserVersion("116");
			co.setAcceptInsecureCerts(Boolean.parseBoolean(loadProperty().getProperty("insecureCertificate")));
			driver = new ChromeDriver(co);
			threadLocaldriver.set(driver);
			driver = threadLocaldriver.get();

		} else if (browserName.equalsIgnoreCase("firefox") && threadLocaldriver.get() == null) {
			threadLocaldriver.set(ThreadGuard.protect(new FirefoxDriver()));
			driver = threadLocaldriver.get();

		}

		long implicitWaitTime = Long.parseLong(loadProperty().getProperty("implicitWaitTime"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
		driver.manage().window().maximize();
		driver.get("https://www.ebay.com/");
		return new HomePage(driver);
	}
	@BeforeSuite
	public void cleanTestsBed() {
		String folderPath = System.getProperty("user.dir")+"\\reportsAndScreenshoots\\";
        
        try (Stream<Path> paths = Files.walk(Path.of(folderPath))) {
            paths.filter(Files::isRegularFile)
                 .filter(path -> path.getFileName().toString().endsWith(".png"))
                 .forEach(path -> {
                     try {
                         Files.delete(path);
                         System.out.println("Deleted: " + path);
                     } catch (IOException e) {
                         System.err.println("Failed to delete: " + path);
                         e.printStackTrace();
                     }
                 });
        } catch (IOException e) {
            e.printStackTrace();
        }
    
		
		
	    // Specify the source folder path
        Path sourceFolderPath = Path.of(System.getProperty("user.dir")+"\\reportsAndScreenshoots\\");

        // Specify the target folder path
        Path targetFolderPath = Path.of(System.getProperty("user.dir")+"\\testResultArchive\\");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceFolderPath)) {
            for (Path sourceFilePath : stream) {
                if (Files.isRegularFile(sourceFilePath)) {
                    Path targetFilePath = targetFolderPath.resolve(sourceFilePath.getFileName());
                    Files.move(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved: " + sourceFilePath.getFileName());
                }
            }
            System.out.println("All files moved successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    
    
	System.out.println("TestBed cleaned up");
	}
	
	@AfterMethod
	public void quitDriver() {

		WebDriver driver = threadLocaldriver.get();
		if (driver != null) {
			driver.quit();
			threadLocaldriver.remove();
		}
	}

}
