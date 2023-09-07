package pageObjectsFactory;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "gh-logo0")
	public WebElement eBayLogo;
	@FindBy(name = "_sacat")
	public WebElement catagoryDropList;
	@FindBy(tagName= "input")
	public List<WebElement> inputTag;
	@FindBy(xpath = "//input[@id='gh-ac']")
	public WebElement searchBoxByXpath;
	@FindBy(css= "input[id='gh-ac']")
	public WebElement searchBoxByCSS;
	@FindBy(linkText = "Electronics")
	public WebElement electronicsLinkButton;
	@FindBy(className = "btn-prim")
	public WebElement searchButton;
	@FindBy(partialLinkText = "Home & Gard")
	public WebElement gardenAndHomeLinkButton;
	@FindBy(xpath = "//table[@class='gf-t']//li[@class='gf-li']/a")
	public List<WebElement> allFooterLinks;

}	
