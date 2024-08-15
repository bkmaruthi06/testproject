package com.tutorialsninja.qa.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.SearchPage;

public class SearchTest extends Base {
	
	SearchPage searchPage;
	
	public SearchTest()
	{
		super();
	}

	
	public WebDriver driver;
	
	@BeforeMethod
	public void setup()
	{

		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));	
		
	}
	
	
	@AfterMethod
	public void tearDown()
	{
	driver.quit();	
	}
	
	@Test(priority=1)
	public void verifySearchWithValidProduct()
	
	{
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("validProduct"));
		searchPage = homePage.clickOnSearchButton();
		
		Assert.assertTrue(searchPage.displayStatusOfHPValidProduct(),"Valid HP Product is not diaplayed");
	}

	@Test(priority=2)
	public void verifySearchWithInValidProduct()
	
	{
		HomePage homePage = new HomePage(driver);
		homePage.enterProductIntoSearchBoxField(dataProp.getProperty("invalidProduct"));
		homePage.clickOnSearchButton();

		searchPage = homePage.clickOnSearchButton();
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductTextInSearchResults"),"No Product found");
	}

	@Test(priority=3)
	public void verifySearchWithoutAnyProduct()
	
	{
		HomePage homePage = new HomePage(driver);
		searchPage = homePage.clickOnSearchButton();
		String actualSearchMessage = searchPage.retrieveNoProductMessageText();
		Assert.assertEquals(actualSearchMessage,dataProp.getProperty("NoProductTextInSearchResults"),"No Product found");
	}

}
