import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class TC4_293153 { //Review Role
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void setUp() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    // CMS_16: Test Case for Register Reviewer Account
    @Test
    public void testRegisterReviewerAccount() throws InterruptedException {
        driver.get("https://sandbox.soc-conferences.com/reviewer/login.php");

        // Click the "Create here." hyperlink
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/p/a[1]")).click();
        Thread.sleep(1000);

        // Fill the registration form
        new Select(driver.findElement(By.xpath("//*[@id='idfield']"))).selectByVisibleText("Field Name");
        new Select(driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/div[2]/div[1]/select"))).selectByVisibleText("Title");
        driver.findElement(By.xpath("//*[@id='idname']")).sendKeys("John Doe");
        driver.findElement(By.xpath("//*[@id='idemail']")).sendKeys("john.doe@example.com");
        driver.findElement(By.xpath("//*[@id='idphone']")).sendKeys("123456789");
        driver.findElement(By.xpath("//*[@id='idorg']")).sendKeys("Organization Name");
        driver.findElement(By.xpath("//*[@id='idaddress']")).sendKeys("Mailing Address");
        driver.findElement(By.xpath("//*[@id='idurl']")).sendKeys("http://example.com");
        new Select(driver.findElement(By.xpath("//*[@id='country']"))).selectByVisibleText("Country Name");
        driver.findElement(By.xpath("//*[@id='idtitle']")).sendKeys("/path/to/file");
        driver.findElement(By.xpath("//*[@id='idpass']")).sendKeys("password123");
        driver.findElement(By.xpath("//*[@id='idpassb']")).sendKeys("password123");
        driver.findElement(By.xpath("//*[@id='recaptcha-anchor']/div[1]")).click(); // Assuming reCAPTCHA can be bypassed for test
        driver.findElement(By.xpath("//*[@id='idsubmit']")).click();

        // Handle confirmation alert
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Register new account?", confirmAlert.getText());
        confirmAlert.accept();
        
        // Handle success alert
        Alert successAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Success", successAlert.getText());
        successAlert.accept();
    }

    // CMS_17: Test Case for Download News
    @Test
    public void testDownloadNews() throws InterruptedException {
        driver.get("https://sandbox.soc-conferences.com/reviewer/login.php");

        // Log in
        driver.findElement(By.xpath("//*[@id='idemail']")).sendKeys("muyya67@gmail.com");
        driver.findElement(By.xpath("//*[@id='idpass']")).sendKeys("Ipmr3avQ");
        driver.findElement(By.xpath("//*[@id='idremember']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/p[4]/button")).click();
        
        // Click the “News” tab
        driver.findElement(By.xpath("//*[@id='mySidebar']/div[2]/a[1]")).click();

        // Click the news icon button
        driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/div/table/tbody/tr[2]/td[6]/a/button")).click();

        // Click “Download” button
        driver.findElement(By.xpath("//*[@id='newsdetails1']/div/div/table/tbody/tr[4]/td/a/button")).click();

        // Check download success (assumed to be verified by the presence of a specific element or text)
        // Add necessary assertions here
    }

    // CMS_18: Test Case for Decline Review Paper
    @Test
    public void testDeclineReviewPaper() throws InterruptedException {
        driver.get("https://sandbox.soc-conferences.com/reviewer/login.php");

        // Log in
        driver.findElement(By.xpath("//*[@id='idemail']")).sendKeys("muyya67@gmail.com");
        driver.findElement(By.xpath("//*[@id='idpass']")).sendKeys("Ipmr3avQ");
        driver.findElement(By.xpath("//*[@id='idremember']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/p[4]/button")).click();
        
        // Click the “Review” tab
        driver.findElement(By.xpath("//*[@id='mySidebar']/div[2]/a[2]")).click();

        // Click the “Details” button
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/table/tbody/tr[2]/td[8]/a/button")).click();

        // Click “Decline” button
        driver.findElement(By.xpath("/html/body/div[3]/div[3]/div[2]/a[2]/button")).click();

        // Handle decline alert
        Alert declineAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Refuse to review this paper? The paper will no longer available to review", declineAlert.getText());
        declineAlert.accept();

        // Check decline success (assumed to be verified by the presence of a specific element or text)
        // Add necessary assertions here
    }

    // CMS_19: Test Case for Submit Review Paper
    @Test
    public void testSubmitReviewPaper() throws InterruptedException {
        driver.get("https://sandbox.soc-conferences.com/reviewer/login.php");

        // Log in
        driver.findElement(By.xpath("//*[@id='idemail']")).sendKeys("muyya67@gmail.com");
        driver.findElement(By.xpath("//*[@id='idpass']")).sendKeys("Ipmr3avQ");
        driver.findElement(By.xpath("//*[@id='idremember']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/p[4]/button")).click();
        
        // Click the “Review” tab
        driver.findElement(By.xpath("//*[@id='mySidebar']/div[2]/a[2]")).click();

        // Click the “Details” button
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/table/tbody/tr[3]/td[8]/a/button")).click();

        // Fill out the review form
        driver.findElement(By.xpath("/html/body/a/div[1]/form/div/div[1]/table/tbody/tr[2]/td[2]/textarea")).sendKeys("Remarks for rubric items");
        new Select(driver.findElement(By.xpath("//*[@id='rubric51']"))).selectByVisibleText("5");
        driver.findElement(By.xpath("//*[@id='idreview']")).sendKeys("Final remarks");
        driver.findElement(By.xpath("//*[@id='idremark']")).sendKeys("Remarks for conference organizers");
        driver.findElement(By.xpath("//*[@id='idsubmit']")).click();

        // Handle submit alert
        Alert submitAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Submit this review?", submitAlert.getText());
        submitAlert.accept();

        // Handle success alert
        Alert successAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Success", successAlert.getText());
        successAlert.accept();

        // Check submit success (assumed to be verified by the presence of a specific element or text)
        // Add necessary assertions here
    }

    // CMS_20: Test Case for Create New Message
    @Test
    public void testCreateNewMessage() throws InterruptedException {
        driver.get("https://sandbox.soc-conferences.com/reviewer/login.php");

        // Log in
        driver.findElement(By.xpath("//*[@id='idemail']")).sendKeys("muyya67@gmail.com");
        driver.findElement(By.xpath("//*[@id='idpass']")).sendKeys("Ipmr3avQ");
        driver.findElement(By.xpath("//*[@id='idremember']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/form/p[4]/button")).click();
        
        // Click the “Messages” tab
        driver.findElement(By.xpath("//*[@id='mySidebar']/div[2]/a[3]")).click();

        // Click the “New Message” button
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/a/div")).click();

        // Fill out the new message form
        new Select(driver.findElement(By.xpath("//*[@id='idconf']"))).selectByVisibleText("SANDBOX24");
        driver.findElement(By.xpath("//*[@id='idtitle']")).sendKeys("Message Title");
        driver.findElement(By.xpath("//*[@id='idmessage']")).sendKeys("Message content");
        driver.findElement(By.xpath("//*[@id='idsubmit']")).click();

        // Handle confirmation alert
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Are you sure", confirmAlert.getText());
        confirmAlert.accept();

        // Check message creation success (assumed to be verified by the presence of a specific element or text)
        // Add necessary assertions here
    }
}
