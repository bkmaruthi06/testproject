package com.tutorialsninja.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.tutorialsninja.qa.utils.utilities;

public class Base {
	
	WebDriver driver;
	public Properties prop;
	public Properties dataProp;
	
	
	public Base() 
	{
		prop = new Properties();
		File propFile = new File(System.getProperty("user.dir")+("\\src\\main\\java\\com\\tutorialsninja\\qa\\config\\config.properties"));
		
		
		dataProp = new Properties();
		File dataPropFile = new File(System.getProperty("user.dir")+
				("\\src\\main\\java\\com\\tutorialsninja\\qa\\testdata\\testdata.properties"));
		try {
			FileInputStream dataFis = new FileInputStream(dataPropFile);
			dataProp.load(dataFis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			FileInputStream fis = new FileInputStream(propFile);
			prop.load(fis);
		} catch (IOException err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
	}
	
	
	public WebDriver initializeBrowserAndOpenApplicationURL(String browserName)
	{
		if(browserName.equalsIgnoreCase("chrome"))
		{
		
			driver = new ChromeDriver();
			
		}else if(browserName.equalsIgnoreCase("edge"))
		{
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(utilities.IMPLICIT_WAIT_TIME));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(utilities.PAGE_LOAD_TIME));
		driver.get(prop.getProperty("url"));	
		return driver;
	}

}
