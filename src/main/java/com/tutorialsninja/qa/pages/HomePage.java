package com.tutorialsninja.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;

	
	public HomePage(WebDriver driver) 
	
	{
		
	this.driver = driver;
	PageFactory.initElements(driver,this);
	
	}
	
		
	
	//@FindBy is an annotation provided by selenium page factory design pattern. it is used to 
	//build page object and page factory framework.
	@FindBy(xpath="//span[text()='My Account']")
	private WebElement myAccountDropMenu;
	
	//Actions
	public void clickOnMyAccount() 
	{
		myAccountDropMenu.click();
	}
	
	
	@FindBy(linkText="Login")
	private WebElement loginOption;

	
	//Actions
	public LoginPage selectLoginOption() 
	{
		loginOption.click();
		return new LoginPage(driver);
	}
	
	
	@FindBy(xpath="(//a[contains(@href,'account/register')])[1]")
	private WebElement registerOption;
	
	//Actions
	public RegisterPage selectRegisterOption() 
	{
		registerOption.click();
		return new RegisterPage(driver);
	}
	
	
	@FindBy(name="search")
	private WebElement searchBoxField;
	
	//Actions
	public void enterProductIntoSearchBoxField(String productText) 
	{
		searchBoxField.sendKeys(productText);
	}


	@FindBy(xpath="//*[@id=\"search\"]/span/button")
	private WebElement searchButton;
	
	//Actions
	public SearchPage clickOnSearchButton() 
	{
		searchButton.click();
		return new SearchPage(driver);
	}

	
	
}
