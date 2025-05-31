package EXAM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DEMO {
	
	WebDriver driver;
	SoftAssert s = new SoftAssert();
	
	@BeforeTest
	public void start() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();		
	}
	
	@BeforeMethod
	public void url() {
		driver.get("https://www.saucedemo.com/v1/index.html");
	}
	
	@Test (priority = 0)
	public void valid_credinal() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");	
		driver.findElement(By.id("login-button")).click();
		
		String expurl ="https://www.saucedemo.com/v1/inventory.html";
		String acturl =driver.getCurrentUrl();
		s.assertEquals(expurl, acturl);
		s.assertAll();
	}
	
	@Test (priority = 1)
	public void incorrect_password() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret");	
		driver.findElement(By.id("login-button")).click();
		
		WebElement msg = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
		String txt = msg.getText();
		System.out.println(txt);
	}
	
	
	
	@AfterTest
	public void close() {
		driver.close();
	}

} 
