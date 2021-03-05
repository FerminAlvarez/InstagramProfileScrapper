package Scrap.Instagram.InstagramProfileScrapper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverController {
	
	
	private  WebDriver driver = null;
	private InstagramCredentials instagramCredentials;
	private final String chromeDriverPath= System.getProperty("user.dir") + "/ChromeDriver/chromedriver.exe";
	private final String instragramURL = "https://instagram.com/";
	
	private final String notNow = "Ahora no"; //Esta variable cambia de acuerdo al idioma que se esté usando en Instagram.
	
	
	public ChromeDriverController(InstagramCredentials instagramCredentials) {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver = new ChromeDriver();
		this.instagramCredentials = instagramCredentials;
	}
	
	public void login() throws InstagramCredentialsException {
		if(instagramCredentials==null) 
			throw new InstagramCredentialsException("First you must create Instagram Credentials!");
		
		driver.get(instragramURL);
		
		//No es necesario la creación de dos objetos pero simplifica la lectura.
		WebElement usernameElement = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='username']")));
		WebElement passwordElement = new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='password']")));
		usernameElement.sendKeys(instagramCredentials.getUsername());
		passwordElement.sendKeys(instagramCredentials.getPassword());

		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();	
	}
	
	public void closePopUps(){
		try {
			Thread.sleep(5000);
			closePopUp();
			Thread.sleep(5000);
			closePopUp();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}	
	}
	
	private void closePopUp(){
		driver.findElement(By.xpath("//button[text()='"+notNow+"']")).click();
	}
	
	
	
	public void goToProfile() {
		driver.get(instragramURL+instagramCredentials.getUsername());
	}
	
	public List<String> getAllImagesLinks() {
		scrollear();
		List<String> imagenes = new LinkedList<String>();
		for(WebElement a : driver.findElements(By.tagName("img"))) {
			imagenes.add(a.getAttribute("src"));
		}
		return imagenes;
	}
	
	
	public void downloadImages(String folderPath) {
		int offset = 1;
		URL URL;
		try {
			for(String imageURL: getAllImagesLinks()) {
				URL = new URL(imageURL);
				BufferedImage saveImage = ImageIO.read(URL);
				ImageIO.write(saveImage, "png", new File(folderPath+"/Instagram"+  offset++  +".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private  void scrollear() {
		boolean isBottom = false;
	    System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/Driver/chromedriver.exe");
	    try {
		    driver.get(driver.getCurrentUrl());
	        long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
	        while (!isBottom) {
	            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500000);");
			    Thread.sleep(5000);
	            long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
	            if (newHeight == lastHeight) {
	            	isBottom = true;
	            }
	            lastHeight = newHeight;
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	public void closeDriver() {
		driver.close();
	}

	
}
