"# almosafer_automation_senario" 
# 🧪 Almosafer.com Automation Testing

> Automated UI testing of [Almosafer.com](https://www.almosafer.com/en?ncr=1) using **Selenium WebDriver**, **Java**, and **TestNG**.

---

## 📁 Project Structure

```bash
almosafer/
└── myTest.java
```

---

## ✅ Prerequisites

* Java 8 or higher
* TestNG
* Selenium WebDriver
* EdgeDriver or ChromeDriver
* Internet connection

---

## 🚀 Getting Started

1. **Clone the repository** or create a new Java project.
2. **Add the provided source code** into your project.
3. **Configure your browser driver** (EdgeDriver or ChromeDriver).
4. **Add required libraries** for Selenium and TestNG.
5. **Run the test class** via your IDE or TestNG XML suite.

---

## 🔮 Test Scenarios

### 1. ✨ English Language Validation

* Verifies the default site language is English.
* HTML tag must contain `lang="en"`.

---

### 2. 💰 Currency Check

* Confirms the default displayed currency is **SAR**.
* Located using: `data-testid='Header__CurrencySelector'`.

---

### 3. 📞 Contact Number Verification

* Validates the presence of contact number: `+966554400000`.
* Located using: `By.linkText()`.

---

### 4. 🌟 Qitaf Logo Visibility

* Checks for Qitaf logo in the footer.
* CSS: `footer svg[data-testid='Footer__QitafLogo']`

---

### 5. ❌ Hotels Tab Not Selected by Default

* Ensures "Hotels" tab is not the default selected tab.
* Uses `aria-selected` attribute check.

---

### 6. 🗓️ Flight Dates Validation

* Departure date = Today + 1
* Return date = Today + 2
* Uses `LocalDate` and `DateTimeFormatter` for date handling.

---

### 7. 🔀 Change Language Randomly

* Randomly toggles between **Arabic** and **English**.
* Validates the applied language reflects correctly.

---

### 8. 🏨 Hotel Search Flow

* Random city selection based on current language:

  * English: `Dubai`, `Jeddah`, `Riyadh`
  * Arabic: `دبي`, `جدّة`, `رياض`
* Random guest configuration:

  * `1 room, 2 adults` or `1 room, 1 adult`
* Initiates hotel search.

---

### 9. 📊 Search Results Page Validation

* Waits for search results to load.
* Asserts:

  * Hotels are listed.
  * Each has a visible price element.

---

## 🛠️ Technologies Used

* **Java** – Test logic
* **Selenium WebDriver** – UI automation
* **TestNG** – Testing framework
* **EdgeDriver / ChromeDriver** – Browser drivers
* **WebDriverWait** – Explicit waits for dynamic content

---

## 🔧 Cleanup

Ensures the browser is closed after test execution:

```java
@AfterTest
public void finish() {
    driver.quit();
}
```

---

## 📆 Notes

* Test order is controlled using `@Test(priority = X)` annotations.
* Randomized tests add variability and improve coverage.
* Synchronization is handled using explicit waits.

---

Feel free to contribute, optimize, or extend the tests!
