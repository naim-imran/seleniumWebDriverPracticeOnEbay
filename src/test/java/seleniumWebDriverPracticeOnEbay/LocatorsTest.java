package seleniumWebDriverPracticeOnEbay;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LocatorsTest extends InitialComponents {

	/*
	 * as the name suggest, it is used to locate web element in html DOM. There are
	 * eight different types of locators
	 * 
	 * 1. Id 2. Name 3. ClassName 4. TagName 5. LinkText 6. PartialLinkText 7. Xpath
	 * 8. CSS
	 * 
	 * *. in selenium version 4 relative locators also added which includes
	 * 
	 * 1.above(), 2.below(), 3.toRightOf() and 4.toLeftOf() methods.
	 * 
	 * to use relative locators we have to manually import
	 * (org.openqa.selenium.support.locators.RelativeLocator.with(By by))
	 * 
	 * we also can find webelement by using javaScriptExecutor
	 */
	
	@Test(priority = 1, description = "TC001 locate ebay logo by ID")
	public void id_LocatorTest() {
		Assert.assertTrue(launchBrowser().eBayLogo.isDisplayed());
	}

	@Test(priority = 2, description = "TC002 expand catagory filter list by using name locator")
	public void name_LocatorTest() {
		launchBrowser().catagoryDropList.click();
	}

	@Test(priority = 3, description = "TC003 print number of input tag present in the DOM by tagName")
	public void tagName_LocatorTest() {
		System.out.println("There are total " + launchBrowser().inputTag.size() + " input tag in DOM");
	}

	@Test(priority = 4, description = "TC004 type \"laptops\" in search textbox by xpath")
	public void xpath_LocatorTest() {
		/*
		 * Relative XPath syntax: //tagName[@attributeName=’attribute value’] Double
		 * forward slash then tag name then inside the square bracket @ then attribute
		 * name = and then within a single quotation attribute value. For tag and
		 * attribute, we can use a regular expression.
		 * 
		 */
		launchBrowser().searchBoxByXpath.sendKeys("laptops");

	}

	@Test(priority = 5, description = "TC005 type \"mobile\" in search textbox by cssSelector")
	public void cssSelector_LocatorTest() {
		/*
		 * syntax: TagName[attribute=’value’] For attribute ID, we can also write it as
		 * tagName#attributeValue input#identifierId we can also skip the tagName
		 * #identifierId For attribute class, syntax is tagName.attributeValue. if the
		 * class attribute value has whitespace, replace that with “.” <input
		 * class="gNO89b xyz" value="Google Search" aria-label="Google Search"
		 * name="btnK" role="button" tabindex="0" type="submit"
		 * data-ved="0ahUKEwjlpbOlyJj5AhWHM1kFHdEkDEAQ4dUDCA0">
		 * 
		 * Syntax attributeName.attributeValue Input.gN089b.xyz or Syntax
		 * .attributeValue .gN089b.xyz
		 * 
		 */
		launchBrowser().searchBoxByCSS.sendKeys("mobile");

	}

	@Test(priority = 6, description = "TC006 click on search button using className locator")
	public void className_LocatorTest() {
		launchBrowser().searchButton.click();
	}

	@Test(priority = 7, description = "TC007 click on Electronics link button using linkTest locator")
	public void linkText_LocatorTest() {
		launchBrowser().electronicsLinkButton.click();
	}

	@Test(priority = 8, description = "TC008 click on \"Home & Garden\" link button using partialLink locator")
	public void partialLinkText_LocatorTest() {
		launchBrowser().gardenAndHomeLinkButton.click();
	}


	@Test(priority = 9)
	public void verifyAllFooterLinks() throws IOException {
		List<WebElement> list = launchBrowser().allFooterLinks;
		verifyLink(list);
	}
}
