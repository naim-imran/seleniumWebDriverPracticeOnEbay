package seleniumWebDriverPracticeOnEbay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class Reuseables {
	private Properties prop;

	public Properties loadProperty() {
		try {
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\seleniumWebDriverPracticeOnEbay\\config.properties");
			prop = new Properties();
			prop.load(fs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public String getCurrentTimeToFormatedString() {
		String currentTime = LocalDateTime.now().toString();
		return ((currentTime.replace(".", "_")).replace(":", "_"));
	}

	public void verifyLink(WebElement link) throws IOException {
		
		String url = link.getAttribute("href");
		HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(url).openConnection();
		urlConnection.setRequestMethod("HEAD");
		urlConnection.connect();
		int responseCode = urlConnection.getResponseCode();
		System.out.println(link.getText() + " Status code " + responseCode);
		if (responseCode>=400) {
			Assert.assertTrue(false, link.getText()+ " failed");
		}
	}

	public void verifyLink(List<WebElement> links) throws IOException {
		SoftAssert softAssert = new SoftAssert();
		int i;
		for (i = 0; i < links.size(); i++) {
			String url = links.get(i).getAttribute("href");
			HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod("HEAD");
			urlConnection.connect();
			int responseCode = urlConnection.getResponseCode();
			System.out.println("(link= "+(i+1)+ ") "+links.get(i).getText() + " Status code " + responseCode);
			if (responseCode>=400) {
				softAssert.assertTrue(false, links.get(i).getText()+ " failed");
			}
		}
		softAssert.assertAll();
	}
}
