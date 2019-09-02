package com.selenium.objectsRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectRepoParser {
	private FileInputStream stream;
	private String objRepoFile;
	private Properties propertyFile = new Properties();
	
	public ObjectRepoParser (String propertyFileName) throws IOException
	{
		this.objRepoFile = propertyFileName;
		stream = new FileInputStream(objRepoFile);
		propertyFile.load(stream);
	}
	
	public By getObjectLocator (String elementName)
	{
		String locatorProperty = propertyFile.getProperty(elementName);
		String locatorType = locatorProperty.split(":")[0];
		String locatorValue = locatorProperty.split(":")[1];
		
		By locator = null;
		switch (locatorType)
		{
		case "Id": locator = By.id(locatorValue); break;
		case "Name": locator = By.name(locatorValue); break;
		case "CssSelector": locator = By.cssSelector(locatorValue); break;
		case "ClassName": locator = By.className(locatorValue); break;
		case "LinkText": locator = By.linkText(locatorValue); break;
		case "PartialLinkText": locator = By.partialLinkText(locatorValue); break;
		case "TagName": locator = By.tagName(locatorValue); break;
		case "XPath": locator = By.xpath(locatorValue); break;		
		}
		return locator;
	}
}
