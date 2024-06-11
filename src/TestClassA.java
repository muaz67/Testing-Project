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

public class TestClassA { //Admin role
    WebDriver driver;
    WebDriverWait wait;

    @Before
    public void beforeTest() throws InterruptedException {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "/Users/choon/Documents/Java Libraries/chromedriver-win64/chromedriver-win64/chromedriver.exe");
        // Initialize the ChromeDriver
        driver = new ChromeDriver();

        // Wait for browser open
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        // Navigate to the admin login page
        driver.get("https://sandbox.soc-conferences.com/admin/login.php");

        // Perform login
        // Enter email
        WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
        emailField.sendKeys("sandbox@soc-conferences.com");

        Thread.sleep(1000);

        //Enter password
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
        passwordField.sendKeys("Abc123");

        Thread.sleep(1000);

        // Find the drop-down element and select "SANDBOX24"
        Select conferenceDropdown = new Select(driver.findElement(By.xpath("//select[@name='conf']")));
        conferenceDropdown.selectByValue("SANDBOX24");

        Thread.sleep(1000);

        // Check the 'remember me' checkbox
        WebElement rememberCheckbox = driver.findElement(By.xpath("//input[@name='remember']"));
        if (!rememberCheckbox.isSelected()) {
            rememberCheckbox.click();
        }

        Thread.sleep(1000);

        // Handle the save preference alert
        Alert prefAlert = driver.switchTo().alert();
        String prefAlertText = prefAlert.getText();
        assertEquals("Credentials Stored Success", prefAlertText);
        prefAlert.accept();

        Thread.sleep(1000);


        // Click the login button
        WebElement loginButton = driver.findElement(By.xpath("//button[@name='submit']"));
        loginButton.click();

        // Handle the login alert
        Alert loginAlert = driver.switchTo().alert();
        String loginAlertText = loginAlert.getText();
        assertEquals("Login Successful", loginAlertText);
        loginAlert.accept();

        // Wait for the login to complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.titleContains("SANDBOX24 - Admin"));

        // Validate the title of the page after login
        String title = driver.getTitle();
        assertEquals("SANDBOX24 - Admin", title);
        System.out.println("Login successfully!");
    }//end for beforeTest

    //CMS_01 Add Field - Tests the functionality to add field settings
    @Test
    public void CMS_01() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Thread.sleep(1000);

        // Click the “Settings” tab
        WebElement settingsTab = driver.findElement(By.xpath("//a[@href='settings.php' and contains(@class, 'w3-bar-item')]"));
        settingsTab.click();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Click the “Add Field” button
        WebElement addFieldButton = driver.findElement(By.xpath("//button[contains(text(),'Add Field')]"));
        wait.until(ExpectedConditions.elementToBeClickable(addFieldButton)).click();

        // Insert the new field name with "Data Center Efficiency"
        WebElement fieldInput = driver.findElement(By.xpath("//input[@name='field']"));
        wait.until(ExpectedConditions.elementToBeClickable(fieldInput)).sendKeys("Data Center Efficiency");

        Thread.sleep(1000);

        // Click the "Submit" button
        WebElement submitButton = driver.findElement(By.xpath("/html/body/div[2]/div[7]/div/div/form/p/button"));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        Thread.sleep(1000);
        // Handle the alert

        Alert successAlert = driver.switchTo().alert();
        String successAlertText = successAlert.getText();
        assertEquals("Success", successAlertText);
        successAlert.accept();

        // Verify the new field is in "Fields Settings"
        WebElement tableElement = driver.findElement(By.xpath("/html/body/div[2]/div[4]/div[2]/table"));
        String tableText = tableElement.getAttribute("textContent");
        if (tableText.contains("Data Center Efficiency")) {
            System.out.println("New field successfully added!");
        } else {
            System.out.println("New field unsuccessfully added!");
        }
    }

    //CMS_02 Add news - Tests the functionality to add new news.
    @Test
    public void CMS_02() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Thread.sleep(1000);

        // Click the “Add News” button
        WebElement addNewsButton = driver.findElement(By.xpath("/html/body/div[2]/div[2]/a/div"));
        wait.until(ExpectedConditions.elementToBeClickable(addNewsButton)).click();

        // Insert the news title with "Testing add news functionality"
        WebElement newsTitle = driver.findElement(By.xpath("//*[@id=\"idtitle\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(newsTitle)).sendKeys("Testing add news functionality");

        Thread.sleep(1000);

        // Find the dropdown element
        WebElement selectUser = driver.findElement(By.xpath("//*[@id='idnewnews']/div/div/form/div[1]/div[1]/select"));
        Select dropdown = new Select(selectUser);// Create a Select object
        dropdown.selectByVisibleText("All");// Select the option 'All'

        Thread.sleep(1000);

        //Upload file
        WebElement uploadFile = driver.findElement(By.xpath(" //*[@id=\"idfile\"]"));
        uploadFile.sendKeys("C:/Users/choon/Downloads/testing.docx");
        Thread.sleep(1000);

        //Insert news content
        WebElement newsContent = driver.findElement(By.xpath("//*[@id=\"idocntent\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(newsContent)).sendKeys("This is just testing!");

        Thread.sleep(1000);

        // Click the "Submit" button
        WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"idsubmit\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();

        Thread.sleep(1000);

        // Handle confirmation alert
        Alert confirmAlert = driver.switchTo().alert();
        String confirmAlertText = confirmAlert.getText();
        assertEquals("Insert this news?", confirmAlertText);
        confirmAlert.accept();

        // Handle success alert
        Alert successAlert = driver.switchTo().alert();
        String successAlertText = successAlert.getText();
        assertEquals("Success", successAlertText);
        successAlert.accept();

        // Verify the new news is added in "News"
        WebElement tableElement = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[2]/table"));
        String tableText = tableElement.getAttribute("textContent");
        if (tableText.contains("Testing add news functionality")) {
            System.out.println("New news successfully added!");
        } else {
            System.out.println("New news unsuccessfully added!");
        }
    }

    //CMS_03.01 Assign Reviewer (Expertise) - Tests the functionality to assign reviewers for the paper received.
    @Test
    public void CMS_03_01() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Thread.sleep(1000);

        // Click the “Papers” tab
        WebElement papersTab = driver.findElement(By.xpath("//a[@href='papers.php' and contains(@class, 'w3-bar-item')]"));
        papersTab.click();

        //Click on "details"
        WebElement detailsTextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Submitted']/following-sibling::td/a[contains(text(),'Details')]")));
        detailsTextButton.click();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Switch to the new tab
        String currentHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Sleep for 1 seconds
        Thread.sleep(1000);

        //Click on "Review Management"
        WebElement reviewButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/a[2]/button")));
        reviewButton.click();

        Thread.sleep(2000);

        //Enter reviewer's Expertise
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"idtitle\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBar)).sendKeys("Cybersecurity");

        Thread.sleep(2000);

        //Select "Expertise" from drop-down button
        WebElement selectArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[4]/div/form/div[2]/select")));
        Select dropdown = new Select(selectArea);// Create a Select object
        dropdown.selectByVisibleText("Expertise");// Select the option 'All'
        System.out.println("Expertise is selected");

        Thread.sleep(2000);

        //Click the "Search" button
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"idsubmit\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        Thread.sleep(2000);

        //Click on the "Assign" button
        WebElement assignButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[3]/div/table/tbody/tr[11]/td[6]/a/input")));
        assignButton.click();

        Thread.sleep(2000);

        // Handle confirmation alert
        Alert confirmDeleteAlert = driver.switchTo().alert();
        String confirmDeleteAlertText = confirmDeleteAlert.getText();
        assertEquals("Are you sure?", confirmDeleteAlertText);
        confirmDeleteAlert.accept();

        Thread.sleep(2000);

        // Handle success alert
        Alert successAlert = driver.switchTo().alert();
        String successAlertText = successAlert.getText();
        assertEquals("Success", successAlertText);
        successAlert.accept();

        Thread.sleep(2000);


        System.out.println("The reviewer is successfully assigned to review the paper.");
    }

    //CMS_03.02 Assign Reviewer (Organization) - Tests the functionality to assign reviewers for the paper received.
    @Test
    public void CMS_03_02() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Thread.sleep(1000);

        // Click the “Papers” tab
        WebElement papersTab = driver.findElement(By.xpath("//a[@href='papers.php' and contains(@class, 'w3-bar-item')]"));
        papersTab.click();

        Thread.sleep(1000);

        //Click on "details"
        WebElement detailsTextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Under Review']/following-sibling::td/a[contains(text(),'Details')]")));
        detailsTextButton.click();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Switch to the new tab
        String currentHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Sleep for 1 seconds
        Thread.sleep(1000);

        //Click on "Review Management"
        WebElement reviewButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[3]/div/div/div/a[2]/button")));
        reviewButton.click();

        Thread.sleep(2000);

        //Enter reviewer's organization
        WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"idtitle\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchBar)).sendKeys("UUM");

        Thread.sleep(2000);

        //Select "Organization" from drop-down button
        WebElement selectArea = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[4]/div/form/div[2]/select")));
        Select dropdown = new Select(selectArea);// Create a Select object
        dropdown.selectByVisibleText("Organization");// Select the option 'All'
        System.out.println("Organization is selected");

        Thread.sleep(2000);

        //Click the "Search" button
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"idsubmit\"]"));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        Thread.sleep(2000);

        //Click on the "Assign" button
        WebElement assignButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[3]/div/table/tbody/tr[11]/td[6]/a/input")));
        assignButton.click();

        Thread.sleep(2000);

        // Handle confirmation alert
        Alert confirmDeleteAlert = driver.switchTo().alert();
        String confirmDeleteAlertText = confirmDeleteAlert.getText();
        assertEquals("Are you sure?", confirmDeleteAlertText);
        confirmDeleteAlert.accept();

        Thread.sleep(2000);

        // Handle success alert
        Alert successAlert = driver.switchTo().alert();
        String successAlertText = successAlert.getText();
        assertEquals("Success", successAlertText);
        successAlert.accept();

        Thread.sleep(2000);


        System.out.println("The reviewer is successfully assigned to review the paper.");

    }

    //CMS_04.01 Update journal status (Received) - Tests the functionality to update the status of a journal to "Received".
    @Test
    public void CMS_04_01() throws InterruptedException {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        Thread.sleep(1000);

        // Click the “Papers” tab
        WebElement papersTab = driver.findElement(By.xpath("//a[@href='papers.php' and contains(@class, 'w3-bar-item')]"));
        papersTab.click();

        //Click on "details"
        WebElement detailsTextButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Submitted']/following-sibling::td/a[contains(text(),'Details')]")));
        detailsTextButton.click();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Switch to the new tab
        String currentHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Sleep for 1 seconds
        Thread.sleep(1000);

        //Select "Received" from the drop-down button
        WebElement selectStatus = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[2]/div/div[2]/a/div/div/form/p[1]/select")));
        Select dropdown = new Select(selectStatus);// Create a Select object
        dropdown.selectByVisibleText("Received");// Select the option 'All'
        System.out.println("Received is selected");

        // Sleep for 1 seconds
        Thread.sleep(1000);

        //Enter conference remarks
        WebElement remarksText = driver.findElement(By.xpath("//*[@id=\"idremark\"]"));
        remarksText.clear(); // Clear any existing text
        wait.until(ExpectedConditions.elementToBeClickable(remarksText)).sendKeys("The paper is received and ready to proceed.");

        // Sleep for 1 seconds
        Thread.sleep(1000);

        //Click the "Update" button
        WebElement updateButton = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/a/div/div/form/p[3]/button"));
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Handle confirmation alert
        Alert confirmDeleteAlert = driver.switchTo().alert();
        String confirmDeleteAlertText = confirmDeleteAlert.getText();
        assertEquals("Are you sure?", confirmDeleteAlertText);
        confirmDeleteAlert.accept();

        // Sleep for 1 seconds
        Thread.sleep(1000);

        // Handle success alert
        Alert successAlert = driver.switchTo().alert();
        String successAlertText = successAlert.getText();
        assertEquals("Success", successAlertText);
        successAlert.accept();

        System.out.println("The journal status successfully updated to Received.");
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

}
