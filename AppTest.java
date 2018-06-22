
	package com.qait.maven.mavenTatocTest;

	import org.testng.annotations.Test;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;

	import org.testng.annotations.Test;
	import org.testng.annotations.BeforeClass;
	import org.testng.Assert;

	import org.testng.annotations.BeforeClass;
	import org.testng.asserts.Assertion;
	import org.openqa.selenium.*;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.interactions.Actions;



	public class AppTest
	{
		WebDriver driver;
		@BeforeClass
		public void init()
		{
			System.setProperty("webdriver.chromedriver", "C:\\Users\\sanchitjain\\Downloads\\chromedriver_win32.zip\\chromedriver.exe");
			driver = new ChromeDriver();

		}
		@Test
		public void Step_01_launchBrowser() {
			driver.get("http://10.0.1.86/tatoc/basic/grid/gate");

		}

		@Test(dependsOnMethods = {"Step_01_launchBrowser"})

		public void Step_02_greenBox()
		{
			Assert.assertEquals(driver.findElement(By.className("greenbox")).isDisplayed(),true);
			this.driver.findElement(By.className("greenbox")).click();
			String expectedUrl = ("http://10.0.1.86/tatoc/basic/frame/dungeon");
			Assert.assertEquals(expectedUrl, driver.getCurrentUrl(),"Did not navigate");


		}

		@Test(dependsOnMethods = {"Step_02_greenBox"})
		public void Step_03_Dungen()
		{ 
			driver.switchTo().frame("main");
			String Box1 = driver.findElement(By.id("answer")).getAttribute("class");
			System.out.println(Box1);
			String Box2 = null;
			Assert.assertEquals(driver.findElement(By.id("answer")).isDisplayed(), true);

			do {
				driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#child")));
				Box2 = driver.findElement(By.id("answer")).getAttribute("class");
				driver.switchTo().parentFrame();
				if(Box1.equals(Box2)){
					driver.findElement(By.linkText("Proceed")).click();
					break;
				}
				else driver.findElement(By.linkText("Repaint Box 2")).click();
			}while(!Box1.equals(Box2));
			
			String expectedUrl = ("http://10.0.1.86/tatoc/basic/drag");
			Assert.assertEquals(expectedUrl,driver.getCurrentUrl());
		}
		@Test(dependsOnMethods ="Step_03_Dungen")
		public void Step_04_Drag() {
			WebElement Drag = driver.findElement(By.id("dragbox"));
			WebElement Drop = driver.findElement(By.id("dropbox"));
			Actions builder = new Actions(driver);

			builder.dragAndDrop(Drag,Drop).build().perform();

			driver.findElement(By.linkText("Proceed")).click();
			String expectedUrl = ("http://10.0.1.86/tatoc/basic/windows");
			
			Assert.assertEquals(expectedUrl, driver.getCurrentUrl());


		}

	@Test(dependsOnMethods="Step_04_Drag")
	public void Step_05_Windows() {
		driver.findElement(By.linkText("Launch Popup Window")).click();

		String parentWindow = driver.getWindowHandle(); 
		String childWindow = null;

		java.util.Set<String> windows = driver.getWindowHandles();

		driver.findElement(By.linkText("Launch Popup Window")).click();
		String MainWindow = driver.getWindowHandle();	
		for(String popWindow : driver.getWindowHandles()) {
			driver.switchTo().window(popWindow);
		}
		WebElement text = driver.findElement(By.id("name"));
		text.sendKeys("Vibhu");
		driver.findElement(By.id("submit")).click();
		driver.switchTo().window(MainWindow);
		driver.findElement(By.linkText("Proceed")).click();	
		String expectedUrl =("http://10.0.1.86/tatoc/basic/cookie");
		Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
	}

	@Test(dependsOnMethods="Step_05_Windows")
	public void Step_06_Token() {
		
		driver.findElement(By.linkText("Generate Token")).click();
		String token1 = driver.findElement(By.id("token")).getText();
		String Token2 = token1.substring(token1.indexOf(":")+2);
		Cookie cookie = new Cookie("Token",Token2);
		driver.manage().addCookie(cookie);
		driver.findElement(By.linkText("Proceed")).click();
		
		String expectedUrl = ("http://10.0.1.86/tatoc/end");
		Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
	}
		@AfterClass
		public void xEnd()
		{
			//driver.quit();

		}

	}

