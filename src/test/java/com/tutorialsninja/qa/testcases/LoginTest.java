package com.tutorialsninja.qa.testcases;

import org.testng.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.utils.utilities;
import com.tutorialsninja.qa.base.*;
import com.tutorialsninja.qa.pages.AccountPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.LoginPage;

import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class LoginTest extends Base{
	
	LoginPage loginPage;
	
	public LoginTest()
	{
		super();
	}

	public WebDriver driver;
	
	
	@BeforeMethod
	public void setup()
	{

		driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));	
		HomePage homePage = new HomePage(driver);
		homePage.clickOnMyAccount();
		loginPage =  homePage.selectLoginOption();
	}
	
	
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	@Test(priority=1,dataProvider="validCredentialsSupplier")
	public void verifyLoginWithValidCredentials(String email,String password)
	{
		
		loginPage.enterEmailAddress(email);
		loginPage.enterPassword(password);
		AccountPage accountPage = loginPage.clickOnLoginButton();
		Assert.assertTrue(accountPage.getDisplayStatusOfEditYourAccountInformationOption(),"Edit your account information is not diaplayed");

	}

	
	
	@DataProvider(name="validCredentialsSupplier")
	public Object [][] supplyTestData()
	{
		Object[][] data = utilities.getTestDataFromExcel("Login");
				return data;
	}
	
	
	
	@Test(priority=2)
	public void verifyLoginWithInvalidCredentials()
	{
	
		loginPage.enterEmailAddress(utilities.generatEmailWithTimeStamp());
		loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected Warning Message is not displayed");
	}
	

	
	@Test(priority=3)
	public void verifyLoginWithInvalidEmailandValidPassword()
	{
	
		loginPage.enterEmailAddress(utilities.generatEmailWithTimeStamp());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected Warning Message is not displayed");
	}
	
	
	
	@Test(priority=4)
	public void verifyLoginWithValidEmailandInvalidPassword()
	{
	
		loginPage.enterEmailAddress(prop.getProperty("validEmail"));
		loginPage.enterPassword(dataProp.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		
		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected Warning Message is not displayed");
	}
	
	
	
	@Test(priority=5)
	public void verifyLoginWithoutProvidingCredentials()
	{
	
		loginPage.clickOnLoginButton();

		String actualWarningMessage = loginPage.retrieveEmailPasswordNotMatchingWarningMessageText();
		String expectedWarningMessage = dataProp.getProperty("emailPasswordNoMatchWarning");
		Assert.assertTrue(actualWarningMessage.contains(expectedWarningMessage),"Expected Warning Message is not displayed");

		
	}
	
	
}
