import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.time.Duration;

public class PracticeFormTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void fillAllFieldsInForm() {
        driver.get("https://demoqa.com/automation-practice-form");

        driver.findElement(By.id("firstName")).sendKeys("Иван");
        driver.findElement(By.id("lastName")).sendKeys("Петров");
        driver.findElement(By.id("userEmail")).sendKeys("ivan@example.com");
        driver.findElement(By.id("userNumber")).sendKeys("1234567890");

        // Исправленный клик по полу
        WebElement maleRadio = driver.findElement(By.cssSelector("label[for='gender-radio-1']"));
        js.executeScript("arguments[0].click();", maleRadio);

        driver.findElement(By.id("dateOfBirthInput")).click();
        driver.findElement(By.className("react-datepicker__month-select")).click();
        driver.findElement(By.xpath("//option[text()='January']")).click();
        driver.findElement(By.className("react-datepicker__year-select")).click();
        driver.findElement(By.xpath("//option[text()='2000']")).click();
        driver.findElement(By.xpath("//div[text()='15']")).click();

        WebElement subjects = driver.findElement(By.id("subjectsInput"));
        subjects.sendKeys("Maths");
        subjects.sendKeys(Keys.ENTER);

        // Исправленные клики по хобби через JavaScript
        WebElement sports = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='hobbies-checkbox-1']")));
        js.executeScript("arguments[0].click();", sports);

        WebElement reading = driver.findElement(By.cssSelector("label[for='hobbies-checkbox-2']"));
        js.executeScript("arguments[0].click();", reading);

        WebElement music = driver.findElement(By.cssSelector("label[for='hobbies-checkbox-3']"));
        js.executeScript("arguments[0].click();", music);

        File file = new File("src/test/resources/test-image.jpg");
        driver.findElement(By.id("uploadPicture")).sendKeys(file.getAbsolutePath());

        driver.findElement(By.id("currentAddress")).sendKeys("Москва, ул. Тестовая, 123");

        // Исправленные клики для штата и города
        driver.findElement(By.id("state")).click();
        WebElement stateOption = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-3-option-0")));
        stateOption.click();

        driver.findElement(By.id("city")).click();
        WebElement cityOption = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-select-4-option-0")));
        cityOption.click();

        // Клик по кнопке отправки через JavaScript
        WebElement submitButton = driver.findElement(By.id("submit"));
        js.executeScript("arguments[0].click();", submitButton);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("example-modal-sizes-title-lg")));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}