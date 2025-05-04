package almosafer;

import java.time.Duration;
//import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class myTest {

	WebDriver driver = new EdgeDriver();
	Random rand = new Random();
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	String URL = "https://www.almosafer.com/en?ncr=1";
	String theWebsiteLanguage = "";

	@BeforeTest
	public void mySetup() {

		driver.get(URL);
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	public void EnglishLanguage() {
		// Default language is correct (EN)

		theWebsiteLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		Assert.assertEquals(theWebsiteLanguage, "en", "English is NOT Default Language");

	}

	@Test(priority = 2)
	public void CurrencyIsSAR() {
		// Default currency is correct (SAR)

		String theCurrency = driver.findElement(By.cssSelector("button[data-testid='Header__CurrencySelector']"))
				.getText();
		Assert.assertEquals(theCurrency, "SAR", "SAR is NOT Default currency");

	}

	@Test(priority = 3)
	public void ContactNumber() {

		// Contact numbers are correct
		String theContactNumber = driver.findElement(By.linkText("+966554400000")).getText();
		Assert.assertEquals(theContactNumber, "+966554400000");

	}

	@Test(priority = 4)
	public void QitafLogo() {

		WebElement qitafLogo = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("footer svg[data-testid='Footer__QitafLogo']")));
		Assert.assertTrue(qitafLogo.isDisplayed(), "Qitaf logo is NOT displayed inside the footer.");
	}

	@Test(priority = 5)
	public void HotelsSearchNotDefault() {

		// o Hotels search tab is NOT selected by default.

		WebElement StaysTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		String StaySelected = StaysTab.getDomAttribute("aria-selected");

		Assert.assertEquals(StaySelected, "false", "Hotels search tab is selected by default");

	}

	@Test(priority = 6)
	public void FlightDate() throws InterruptedException {

		// Flight departure date is set to “today+1day” by default
		WebElement departureDateContainer = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div[data-testid='FlightSearchBox__FromDateButton']")));

		String depMonth = departureDateContainer.findElements(By.tagName("span")).get(0).getText();
		String depDay = departureDateContainer.findElements(By.tagName("span")).get(1).getText();

		String depDateStr = depMonth + " " + depDay + " " + LocalDate.now().getYear();
		LocalDate actualDepartureDate = LocalDate.parse(depDateStr,
				DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH));
		LocalDate expectedDepartureDate = LocalDate.now().plusDays(1);
		Assert.assertEquals(expectedDepartureDate, actualDepartureDate, " Departure date is not today + 1.");

		// Validate Return Date (today + 2)
		WebElement returnDateContainer = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("div[data-testid='FlightSearchBox__ToDateButton']")));
		String retMonth = returnDateContainer.findElements(By.tagName("span")).get(0).getText();
		String retDay = returnDateContainer.findElements(By.tagName("span")).get(1).getText();
		String retDateStr = retMonth + " " + retDay + " " + LocalDate.now().getYear();
		LocalDate actualReturnDate = LocalDate.parse(retDateStr,
				DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH));
		LocalDate expectedReturnDate = LocalDate.now().plusDays(2);
		Assert.assertEquals(expectedReturnDate, actualReturnDate, " Return date is not today + 2.");

	}

	@Test(priority = 7)
	public void ChangeLanguageRandomly() throws InterruptedException {

		theWebsiteLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");
		String[] language = { "ar", "en" };
		int RandLang = rand.nextInt(language.length);

		String RandomLanguage = language[RandLang];

		WebElement changeLanguage = driver.findElement(By.cssSelector("[data-testid='Header__LanguageSwitch']"));

		Thread.sleep(2000);
		if (!theWebsiteLanguage.equalsIgnoreCase(RandomLanguage)) {
			changeLanguage.click();
			Thread.sleep(2000);
			theWebsiteLanguage = driver.findElement(By.tagName("html")).getDomAttribute("lang");

		}

		Assert.assertEquals(theWebsiteLanguage, RandomLanguage, "language is not updated as chosen");
	}

	@Test(priority = 8)
	public void SearchHotels() throws InterruptedException {
		WebElement HotelTab = driver.findElement(By.id("uncontrolled-tab-example-tab-hotels"));
		HotelTab.click();

		String[] countryEnglish = { "Dubai", "Jeddah", "Riyadh" };
		String[] countryArabic = { "دبي", "جدّة", "رياض" };

		String theCountry = "";

		if (theWebsiteLanguage.equalsIgnoreCase("en")) {
			int randomCountryEnglish = rand.nextInt(countryEnglish.length);
			String countryEN = countryEnglish[randomCountryEnglish];
			theCountry = countryEN;

		} else {
			int randomCountryArabic = rand.nextInt(countryArabic.length);
			String countryAR = countryArabic[randomCountryArabic];
			theCountry = countryAR;

		}

		WebElement SearchTab = driver.findElement(By.cssSelector(".sc-phbroq-2.uQFRS.AutoComplete__Input"));
		SearchTab.sendKeys(theCountry);

		System.out.println("Selected country : "+theCountry);
		
		Thread.sleep(2000);
		SearchTab.sendKeys(Keys.chord(Keys.ENTER));

		// Randomly select “1 room, 2 adults, 0 children” or “1 room, 1 adult, 0
		// children” option.

		String[] roomsAndPepole = { "A", "B" };
		int randomIndex = rand.nextInt(roomsAndPepole.length);

		WebElement dropdownElement = driver.findElement(By.cssSelector(".sc-tln3e3-1.gvrkTi"));
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByValue(roomsAndPepole[randomIndex]);

		// 6. Click on search hotels button.

		WebElement searchButton = driver.findElement(By.cssSelector("[data-testid='HotelSearchBox__SearchButton']"));
		searchButton.click();

	}

	@Test(priority = 9)
	public void SearchResultsPage() {

		
		By resultsCount = By.cssSelector("span[data-testid='srp_properties_found']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(resultsCount));

		// get hotels count
		WebElement countElement = driver.findElement(resultsCount);
		String resultText = countElement.getText();
		System.out.println("Result hotels count: " + resultText);

		List<WebElement> hotelCards = driver.findElements(By.cssSelector("div[data-testid='hotel_card_hotelName']"));
		Assert.assertTrue(hotelCards.size() > 0, "No hotel cards are displayed");
		System.out.println("Number of hotels found in first result page: " + hotelCards.size());

		
		List<WebElement> priceElements = driver.findElements(By.cssSelector("div[data-testid='hotel_card_price']"));

		// Verify that each hotel card has a visible price
		Assert.assertEquals(priceElements.size(), hotelCards.size(), "Some hotels do not have visible prices");

	}

	
	
	@AfterTest
	public void finish() {
		
		driver.quit();
		
		
	}
}
