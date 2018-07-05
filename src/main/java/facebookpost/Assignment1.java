package facebookpost;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.opencsv.CSVReader;
/**
 * 
 * @author AnSrivastava
 *This class is intended to post a message on a logged in user on facebook. 
 */
public class Assignment1 {	
	WebDriver driver;
	String workingDir = System.getProperty("user.dir");
	String driverpath = workingDir;
	String CSV_PATH = workingDir;
			
	public void invokeBroswer() {
		/**
		 * This method launches firefox browser and navigates to facebook login page.
		 */
		try {
			System.setProperty("webdriver.gecko.driver",driverpath+"\\files\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
						
			driver.get("http://www.facebook.com");
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}
	
	public void loginFacebook() {
		/**
		 * This method reads user credentials from csv file at ./files location.
		 */
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(CSV_PATH+"\\files\\credentials.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] csvCell;
		  try {
			while ((csvCell = reader.readNext()) != null) {   
			   String username = csvCell[0];
			   String password = csvCell[1];
			   driver.findElement(By.id("email")).sendKeys(username);
			   driver.findElement(By.id("pass")).sendKeys(password); 
			   driver.findElement(By.id("loginbutton")).click();
				
			  }
		} catch (IOException e) {			
			e.printStackTrace();
		}  						
	}
	
	public void postOnWall() {		
		/**
		 * This method posts a message on the logged in user's wall.
		 */
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {		
			e.printStackTrace();
		}
		driver.findElement(By.name("xhpc_message")).sendKeys("Hello World"); 
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Post')]")).click();
	}
		
	public static void main(String[] args) {				
		Assignment1 obj1 = new Assignment1();
		obj1.invokeBroswer();
		obj1.loginFacebook();
		obj1.postOnWall();

	}

}
