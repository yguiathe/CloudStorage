package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static java.lang.Thread.sleep;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	public String baseURL;

	private static final String FIRSTNAME = "Vanessa";
	private static final String LASTNAME = "Carlton";
	private static final String USERNAME = "vcarlton";
	private static final String PASSWORD = "100@L-23ds";
	private static final String HOME_PAGE_TITLE = "Home";
	private static final String SIGNUP_PAGE_TITLE = "Sign Up";
	private static final String LOGIN_PAGE_TITLE = "Login";

	// Notes
	private static final String FIRST_NOTE_TITLE = "My First Note";
	private static final String FIRST_NOTE_DESCRIPTION = "My First Note Description";
	private static final String SECOND_NOTE_TITLE = "My Second Note";
	private static final String SECOND_NOTE_DESCRIPTION = "My Second Note Description";
	private static final String THIRD_NOTE_TITLE = "My Third Note";
	private static final String THIRD_NOTE_DESCRIPTION = "My Third Note Description";
	private static final String UPDATED_NOTE_TITLE = "My Third Note Updt";
	private static final String UPDATED_NOTE_DESCRIPTION = "My Third Note Description (updated)";

	// Credentials
	private static final String CRED_URL_A = "https://m.facebook.com/";
	private static final String CRED_USERNAME_A = "masterkg";
	private static final String CRED_PASSWORD_A = "h54_32kr";
	private static final String UPDATED_USERNAME = "omarsy";
	private static final String UPDATED_PASSWORD = "rR33_p0s";
	private static final String CRED_URL_B = "https://m.gmail.com/";
	private static final String CRED_USERNAME_B = "vcarlton";
	private static final String CRED_PASSWORD_B = "ro0ro0T_";
	private static final String CRED_URL_C = "https://classroom.udacity.com/";
	private static final String CRED_USERNAME_C = "ssabri";
	private static final String CRED_PASSWORD_C = "r@viol1";

	// Files
	private static final String FILENAME_A = "/Users/tectoo/Downloads/output_15.pdf";
	private static final String FILENAME_B = "/Users/tectoo/Downloads/nd-term-invoice.pdf";

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = baseURL = "http://localhost:" + port;
	}

	@Test
	@Order(1)
	public void loginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals(LOGIN_PAGE_TITLE, driver.getTitle());
	}

	@Test
	@Order(2)
	public void successfulSignupAndLogin() throws InterruptedException {

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(FIRSTNAME, LASTNAME, USERNAME, PASSWORD);

		sleep(4000);

		Assertions.assertEquals(LOGIN_PAGE_TITLE, driver.getTitle());

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		Assertions.assertEquals(HOME_PAGE_TITLE, driver.getTitle());

	}

	@Test
	@Order(3)
	public void unSuccessfulSignupGivenDuplicateUsername() {

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(FIRSTNAME, LASTNAME, USERNAME, PASSWORD);

		Assertions.assertEquals(SIGNUP_PAGE_TITLE, driver.getTitle());

	}

	@Test
	@Order(4)
	public void successfulFileUpload() throws InterruptedException {
		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		sleep(5000);
		HomePage homePage = new HomePage(driver);
		homePage.uploadFile(FILENAME_A);
		sleep(5000);

		Assertions.assertEquals(true, homePage.fileExistsInList(FilenameUtils.getName(FILENAME_A)));

	}

	@Test
	@Order(5)
	public void successfulFileDeletion() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.uploadFile(FILENAME_B);
		sleep(5000);
		homePage.deleteFile(0);
		sleep(5000);

		Assertions.assertEquals(false, homePage.fileExistsInList(FILENAME_A));

	}

	@Test
	@Order(6)
	public void successfulNoteCreation() throws InterruptedException {

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		HomePage homePage = new HomePage(driver);
		homePage.addNote(FIRST_NOTE_TITLE, FIRST_NOTE_DESCRIPTION);
		sleep(5000);

		Assertions.assertEquals(true, homePage.noteExistsInList(FIRST_NOTE_TITLE, FIRST_NOTE_DESCRIPTION));

	}

	@Test
	@Order(7)
	public void successfulNoteUpdate() throws InterruptedException {
		HomePage homePage = new HomePage(driver);

		// Adding notes
		homePage.addNote(SECOND_NOTE_TITLE, SECOND_NOTE_DESCRIPTION);
		sleep(5000);
		homePage.addNote(THIRD_NOTE_TITLE, THIRD_NOTE_DESCRIPTION);
		sleep(5000);
		homePage.openEditNoteModal(2);
		sleep(5000);
		Assertions.assertEquals(true, homePage.noteModalHasCorrectValues(THIRD_NOTE_TITLE, THIRD_NOTE_DESCRIPTION));
		sleep(5000);

		// Editing third note
		homePage.editNote(UPDATED_NOTE_TITLE, UPDATED_NOTE_DESCRIPTION);
		sleep(5000);

		Assertions.assertEquals(true, homePage.noteExistsInList(UPDATED_NOTE_TITLE, UPDATED_NOTE_DESCRIPTION));
	}

	@Test
	@Order(8)
	public void successfulNoteDeletion() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.deleteNote(0);
		sleep(5000);

		Assertions.assertEquals(false, homePage.noteExistsInList(FIRST_NOTE_TITLE, FIRST_NOTE_DESCRIPTION));

	}

	@Test
	@Order(9)
	public void successfulCredentialCreation() throws InterruptedException {

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(USERNAME, PASSWORD);

		HomePage homePage = new HomePage(driver);
		sleep(5000);
		homePage.addCredential(CRED_URL_A, CRED_USERNAME_A, CRED_PASSWORD_A);
		homePage.addCredential(CRED_URL_B, CRED_USERNAME_B, CRED_PASSWORD_B);
		homePage.addCredential(CRED_URL_C, CRED_USERNAME_C, CRED_PASSWORD_C);
		sleep(5000);

		Assertions.assertEquals(true, homePage.credentialExistsInList(CRED_URL_B, CRED_USERNAME_B, CRED_PASSWORD_B));
		Assertions.assertEquals(true, homePage.credentialExistsInList(CRED_URL_A, CRED_USERNAME_A, CRED_PASSWORD_A));
		Assertions.assertEquals(true, homePage.credentialExistsInList(CRED_URL_C, CRED_USERNAME_C, CRED_PASSWORD_C));

	}

	@Test
	@Order(10)
	public void successfulCredentialViewAndUpdate() throws InterruptedException {
		HomePage homePage = new HomePage(driver);

		homePage.openEditCredentialModal(0);
		sleep(5000);
		Assertions.assertEquals(true, homePage.credentialModalHasCorrectValues(CRED_URL_A, CRED_USERNAME_A, CRED_PASSWORD_A));
		sleep(5000);

		homePage.editCredential(CRED_URL_A, UPDATED_USERNAME, UPDATED_PASSWORD);
		sleep(5000);

		Assertions.assertEquals(true, homePage.credentialExistsInList(CRED_URL_A, UPDATED_USERNAME, UPDATED_PASSWORD));
	}

	@Test
	@Order(11)
	public void successfulCredentialDeletion() throws InterruptedException {

		HomePage homePage = new HomePage(driver);
		homePage.deleteCredential(1);
		sleep(5000);

		Assertions.assertEquals(false, homePage.credentialExistsInList(CRED_URL_B, CRED_USERNAME_B, CRED_PASSWORD_B));

	}

}
