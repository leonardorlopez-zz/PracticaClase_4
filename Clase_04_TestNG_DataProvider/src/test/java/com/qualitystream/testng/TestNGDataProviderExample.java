package com.qualitystream.testng;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

public class TestNGDataProviderExample {

	WebDriver driver;
	WebDriverWait wait;
	By signInLocator = By.xpath("/html/body/div/div[1]/header/div[2]/div/div/nav/div[1]/a");
	By emailLocator = By.id("email");
	By passwordLocator = By.id("passwd");
	// localizamos el boton de login
	By signInButtonLogin = By.id("SubmitLogin");
	// localizamos el boton de signOut que nos servira en el test como chequeo
	// de que el usuario se logueo correctamente
	By signOutButtonLocator = By.cssSelector("a.logout"); // es un link por eso utilizamos esta estrategia de
															// localizacion link.class

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", "Driver/chromedriver.exe");
		driver = new ChromeDriver(); // se abre el chrome aqui
		driver.get("http://automationpractice.com/index.php");

	}

	@Test(dataProvider = "authenticationData")
	public void login(String email, String password) {
			wait = new WebDriverWait(driver, 10);
        //Buscamos el boton SignIn
		WebElement signInButton;
		try {
			signInButton = wait.until(ExpectedConditions.presenceOfElementLocated(signInLocator));
		} catch (Exception e) {
			System.out.println("Entro en el catch de SignInLocator");
			signInButton = wait.until(ExpectedConditions.elementToBeClickable(signInLocator));
		}
		
		//Si lo encuentra pregunta si es disponible. Y si esta disponible le hace un click.
		if (signInButton.isDisplayed()) {
			driver.findElement(signInLocator).click(); 

			// Busca el emailAddress y la Password y le envia las credenciales
			WebElement emailAddress = wait.until(ExpectedConditions.presenceOfElementLocated(emailLocator));
			emailAddress.sendKeys(email);
			WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(passwordLocator));
			passwordField.sendKeys(password);
			
			// damos click en el boton SignIn buton
			WebElement signInButtonClick = wait.until(ExpectedConditions.presenceOfElementLocated(signInButtonLogin));
			signInButtonClick.click();

			// chequeamos si el link de SignOut existe una vez logueados
			assertEquals(driver.findElement(signOutButtonLocator).getText(), "Sign out");

			// Una vez que encontramos el SignOut le vamos a dar click en el
			driver.findElement(signOutButtonLocator).click();
		} else {
			System.out.println("Sign in Link is not present");
		}

	}

	@DataProvider(name = "authenticationData")
	public Object[][] getData() {
		Object[][] data = new Object[2][2];

		// 1er usuario - Harcodeados
		data[0][0] = "leonardorlopez39@hotmail.com";
		data[0][1] = "1234567";
		// 2do usuario
		data[1][0] = "leonardorlopez40@hotmail.com";
		data[1][1] = "1234567";

		return data;
	}

	@AfterClass
	public void afterClass() {
		 driver.close();
	}

}
