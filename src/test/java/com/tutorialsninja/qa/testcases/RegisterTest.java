package com.tutorialsninja.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.tutorialsninja.qa.base.Base;
import com.tutorialsninja.qa.pages.AccountSuccessPage;
import com.tutorialsninja.qa.pages.HomePage;
import com.tutorialsninja.qa.pages.RegisterPage;
import com.tutorialsninja.qa.utils.*;


public class RegisterTest extends Base{
	
	AccountSuccessPage accountSuccessPage;
	RegisterPage registerPage;
	
	public RegisterTest()
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
		registerPage = homePage.selectRegisterOption();		
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	
	@Test(priority=1)
	
	public void verifyRegisteringAnAccountWithMandatoryFields()
	
	{
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(utilities.generatEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertNotEquals(dataProp.getProperty("accountSuccessfullyCreatedHeading"),actualSuccessHeading,"Your Account Has Been Created!");

	}
	
	
	@Test(priority=2)
	
	public void verifyRegisteringAnAccountByProvidingAllFields()
	
	{
	
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(utilities.generatEmailWithTimeStamp());
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsLetterOption();
		registerPage.selectPrivacyPolicy();
		accountSuccessPage = registerPage.clickOnContinueButton();
		
		String actualSuccessHeading = accountSuccessPage.retrieveAccountSuccessPageHeading();
		Assert.assertNotEquals(actualSuccessHeading,dataProp.getProperty("accountSuccessfullyCreatedHeading"),"Your Account is not displayed");
	}

	
	@Test(priority=3)
	
	public void verifyRegisteringAccountWithExistingEmailAddress()
	
	{
	
		registerPage.enterFirstName(dataProp.getProperty("firstName"));
		registerPage.enterLastName(dataProp.getProperty("lastName"));
		registerPage.enterEmailAddress(prop.getProperty("validEmail"));
		registerPage.enterTelephoneNumber(dataProp.getProperty("telephoneNumber"));
		registerPage.enterPassword(prop.getProperty("validPassword"));
		registerPage.enterConfirmPassword(prop.getProperty("validPassword"));
		registerPage.selectYesNewsLetterOption();
		registerPage.selectPrivacyPolicy();
		registerPage.clickOnContinueButton();
		String actualWarning = registerPage.retrieveDuplicateEmailAddressWarning();
		Assert.assertTrue(actualWarning.contains(dataProp.getProperty("duplicateEmailWarning")),"Duplicate Email Warning message is not matching");
		driver.quit();
	}

	
	@Test(priority=4)
	
	public void verifyRegisteringAccountWithoutFillingAnyDetails()
	
	{
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.clickOnContinueButton();
		String actualPrivaryPolicyWarning = registerPage.retrievePrivacyPolicyWarning();
		Assert.assertNotEquals(actualPrivaryPolicyWarning.contains(dataProp.getProperty("privacyPolicyWarning")),"Warning message without registering is not matching");
		driver.quit();
	}

	
	
	
	
}
