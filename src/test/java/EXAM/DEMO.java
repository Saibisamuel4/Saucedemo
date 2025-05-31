package EXAM;

import java.util.List;

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
	
	@Test (priority = 2)
	public void empty_credinals() {
		
		driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
		
		driver.findElement(By.id("user-name")).sendKeys(" ");
		driver.findElement(By.id("password")).sendKeys(" ");	
		driver.findElement(By.id("login-button")).click();
		
		WebElement msg1 = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/h3"));
		String txt1 = msg1.getText();
		System.out.println(txt1);
	}
	
	@Test (priority = 3)
	public void display_product() {
		
		driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
		
		List<WebElement>products =driver.findElements(By.className("inventory_item"));
		s.assertTrue(products.size()>0);
		s.assertAll();
	}
	
	@Test(priority = 4)
	public void products_shows(){
		
		driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
		
		List<WebElement>products =driver.findElements(By.className("inventory_item"));
		
//use for-each method
		for(WebElement prod : products) {
			WebElement name= driver.findElement(By.className("inventory_item_name"));
			WebElement image = driver.findElement(By.className("inventory_item_img"));
			WebElement price = driver.findElement(By.className("inventory_item_price"));
			
			s.assertTrue(name.isDisplayed());
			s.assertTrue(image.isDisplayed());
			s.assertTrue(price.isDisplayed());
			s.assertAll();
		}
	}
	
	@Test(priority = 5)
	public void Add_products(){
		
		driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
//add 1st product		
		WebElement product_1 = driver.findElement(By.xpath("//button[text()='ADD TO CART']"));
		product_1.click();
		
//add 2nd product	
		WebElement product_2 = driver.findElement(By.xpath("//button[text()='ADD TO CART']"));
		product_2.click();
		
//verify count
		WebElement cart =driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
		String count = cart.getText();
		System.out.println(count);	
		
	}
	
	@Test(priority = 5)
	public void Remove_products(){
		driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/svg/path")).click();
		
//remove item
		driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/button")).click();
		
//verify count
		WebElement cart =driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span"));
		String count = cart.getText();
		System.out.println(count);
	}
	
		
	
	
	@AfterTest
	public void close() {
		driver.close();
		
	}

} 
