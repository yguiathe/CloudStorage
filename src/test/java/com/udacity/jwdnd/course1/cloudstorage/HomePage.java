package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.Thread.sleep;

public class HomePage {

    private WebDriver webDriver;

    @FindBy(css = "#openNoteModal")
    private WebElement noteModalButton;

    @FindBy(css = "#notesList")
    private WebElement notesTable;

    @FindBy(css = "#credentialsList")
    private WebElement credentialsTable;

    @FindBy(css = "#filesList")
    private WebElement filesTable;

    @FindBy(css = "#openCredentialModal")
    private WebElement credentialModalButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitleField;

    @FindBy(css = "#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css = "#saveNote")
    private WebElement saveNoteButton;

    @FindBy(css = "#credential-url")
    private WebElement credUrlField;

    @FindBy(css = "#credential-username")
    private WebElement credUnameField;

    @FindBy(css = "#credential-password")
    private WebElement credPassField;

    @FindBy(css = "#saveCredential")
    private WebElement saveCredentialButton;

    @FindBy(css = "#logoutBtn")
    private WebElement logoutButton;

    @FindBy(css = "#fileUpload")
    private WebElement fileUpload;

    @FindBy(css = "#uploadBtn")
    private WebElement uploadButton;

    @FindBy(css = "#nav-files-tab")
    private WebElement filesTabButton;

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTabButton;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTabButton;

    @FindBy(css = ".delete-note")
    private List<WebElement> deleteNoteButtons;

    @FindBy(css = ".delete-file")
    private List<WebElement> deleteFileButtons;

    @FindBy(css = ".delete-credential")
    private List<WebElement> deleteCredentialButtons;

    @FindBy(css = ".edit-note")
    private List<WebElement> editNoteButtons;

    @FindBy(css = ".view-file")
    private List<WebElement> viewFileButtons;

    @FindBy(css = ".edit-credential")
    private List<WebElement> editCredentialButtons;

    private JavascriptExecutor jse;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.jse = (JavascriptExecutor) webDriver;
    }

    private void waitForVisibility(WebElement element) throws Error{
        new WebDriverWait(webDriver, 60)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void logout() {
        this.logoutButton.click();
    }

    public void uploadFile(String filename) throws InterruptedException {
        this.fileUpload.sendKeys(filename);
        this.uploadButton.click();
        sleep(5000);
    }

    public void addNote(String noteTitle, String noteDescription) throws InterruptedException {
        this.jse.executeScript("arguments[0].click()", this.notesTabButton);
        sleep(5000);
        this.jse.executeScript("arguments[0].click()", this.noteModalButton);
        sleep(5000);
        this.noteTitleField.sendKeys(noteTitle);
        this.noteDescriptionField.sendKeys(noteDescription);
        this.saveNoteButton.click();
    }

    public void addCredential(String url, String username, String password) throws InterruptedException {
        this.jse.executeScript("arguments[0].click()", this.credentialsTabButton);
        sleep(5000);
        this.jse.executeScript("arguments[0].click()", this.credentialModalButton);
        sleep(5000);
        this.credUrlField.sendKeys(url);
        this.credUnameField.sendKeys(username);
        this.credPassField.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public void openEditNoteModal(int index) {
        this.editNoteButtons.get(index).click();
    }

    public void editNote(String noteTitle, String noteDescription) {
        this.noteTitleField.clear();
        this.noteTitleField.sendKeys(noteTitle);
        this.noteDescriptionField.clear();
        this.noteDescriptionField.sendKeys(noteDescription);
        this.saveNoteButton.click();
    }

    public void openEditCredentialModal(int index) {
        this.editCredentialButtons.get(index).click();
    }

    public void editCredential(String url, String username, String password) {
        this.credUrlField.clear();
        this.credUnameField.clear();
        this.credPassField.clear();
        this.credUrlField.sendKeys(url);
        this.credUnameField.sendKeys(username);
        this.credPassField.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public boolean noteModalHasCorrectValues(String noteTitle, String noteDescription) {
        return (this.noteTitleField.getAttribute("value").equals(noteTitle) && this.noteDescriptionField.getAttribute("value").equals(noteDescription));
    }

    public boolean credentialModalHasCorrectValues(String url, String username, String password) {
        return (this.credUrlField.getAttribute("value").equals(url) && this.credUnameField.getAttribute("value").equals(username) && this.credPassField.getAttribute("value").equals(password));
    }

    public void deleteNote(int index) throws InterruptedException {
        this.jse.executeScript("arguments[0].click()", this.notesTabButton);
        sleep(5000);
        this.deleteNoteButtons.get(index).click();
    }

    public void deleteCredential(int index) throws InterruptedException {
        this.jse.executeScript("arguments[0].click()", this.credentialsTabButton);
        sleep(5000);
        this.deleteCredentialButtons.get(index).click();
    }

    public void deleteFile(int index) {
        this.filesTabButton.click();
        this.deleteFileButtons.get(index).click();
    }

    public boolean noteExistsInList(String noteTitle, String noteDescription) {
        boolean flag = false;
        List<WebElement> notes = this.notesTable.findElements(By.tagName("tr"));
        for(WebElement note: notes) {
            if(note.findElement(By.tagName("th")).getText().equals(noteTitle) && note.findElement(By.xpath("td[2]")).getText().equals(noteDescription)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean credentialExistsInList(String url, String username, String password) {
        boolean flag = false;
        List<WebElement> credentials = this.credentialsTable.findElements(By.tagName("tr"));
        for(WebElement credential: credentials) {
            if(credential.findElement(By.tagName("th")).getText().equals(url) && credential.findElement(By.xpath("td[2]")).getText().equals(username) && !credential.findElement(By.xpath("td[3]")).getText().equals(password)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean fileExistsInList(String filename) {
        boolean flag = false;
        List<WebElement> files = this.filesTable.findElements(By.tagName("tr"));
        for(WebElement file: files) {
            if(file.findElement(By.tagName("th")).getText().equals(filename)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

}
