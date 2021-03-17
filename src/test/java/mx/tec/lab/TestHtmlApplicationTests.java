package mx.tec.lab;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@SpringBootTest
class TestHtmlApplicationTests {
	private WebClient webClient;
	
	@BeforeEach
	public void init() throws Exception {
		webClient = new WebClient();
	}
	
	@AfterEach
	public void close () throws Exception {
		webClient.close();
	}

	@Test
	public void givenAClient_whenEnteringAutomationPractice_thenPageTitleIsCorrect() 
		throws Exception {
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
			
			assertEquals("My Store", page.getTitleText());
	}
	
	@Test
	public void givenAClient_whenEnteringLoginCredentials_thenAccountPageIsDisplayed()
		throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("cesaringo24.martinez@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("Hello123");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		assertEquals("My account - My Store", resultPage.getTitleText());
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenAuthenticationPageIsDisplayed()
		throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("cesaringo24.martinez@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("123");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		assertEquals("Login - My Store", resultPage.getTitleText());
	}
	
	@Test
	public void givenAClient_whenEnteringWrongLoginCredentials_thenErrorMessageIsDisplayed()
			throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("cesaringo24.martinez@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("123");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage = submitButton.click();
		
		HtmlElement warning = resultPage.getFirstByXPath("//div[@class='alert alert-danger']/ol/li");
		assertEquals("Invalid password.", warning.getTextContent().trim());
	}
	@Test
	public void givenAClient_whenSearchingNotExistingProduct_thenNoResultsDisplayed()
			throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		HtmlTextInput searchField = (HtmlTextInput) page.getElementById("search_query_top");
		searchField.setValueAttribute("asdf");
		HtmlButton submitButton = (HtmlButton) page.getElementByName("submit_search");
		HtmlPage resultPage = submitButton.click();
		
		HtmlElement warning = resultPage.getFirstByXPath("//p[@class='alert alert-warning']");
		assertEquals("No results were found for your search \"asdf\"", warning.getTextContent().trim());
	}
	@Test
	public void givenAClient_whenSearchingEmptyString_thenPleaseEnterDisplayed()
			throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php");
		HtmlTextInput searchField = (HtmlTextInput) page.getElementById("search_query_top");
		searchField.setValueAttribute("");
		HtmlButton submitButton = (HtmlButton) page.getElementByName("submit_search");
		HtmlPage resultPage = submitButton.click();
		
		HtmlElement warning = resultPage.getFirstByXPath("//p[@class='alert alert-warning']");
		assertEquals("Please enter a search keyword", warning.getTextContent().trim());
	}
	@Test
	public void givenAClient_whenSigninOut_thenAuthenticationPageIsDisplayed()
			throws Exception {
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		HtmlPage page = webClient.getPage("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		HtmlTextInput emailField = (HtmlTextInput) page.getElementById("email");
		emailField.setValueAttribute("cesaringo24.martinez@gmail.com");
		HtmlPasswordInput passwordField = (HtmlPasswordInput) page.getElementById("passwd");
		passwordField.setValueAttribute("Hello123");
		HtmlButton submitButton = (HtmlButton) page.getElementById("SubmitLogin");
		HtmlPage resultPage1 = submitButton.click();
		HtmlAnchor signOutButton = (HtmlAnchor) resultPage1.getFirstByXPath("//a[@class='logout']");
		HtmlPage resultPage2 = signOutButton.click();
		
		assertEquals("Login - My Store", resultPage2.getTitleText());
	}

}
