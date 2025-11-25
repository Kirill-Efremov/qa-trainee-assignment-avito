package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateTaskPage extends BasePage {
    private final By titleInputLocator = By.xpath("//label[contains(., 'Название')]/following::input[1]");
    private final By descriptionTextareaLocator = By.xpath("//label[contains(., 'Описание')]/following::textarea[1]");

    private final By projectSelectLocator   = By.xpath("//label[contains(., 'Проект')]/following::div[@role='combobox'][1]");
    private final By prioritySelectLocator  = By.xpath("//label[contains(., 'Приоритет')]/following::div[@role='combobox'][1]");
    private final By assigneeSelectLocator  = By.xpath("//label[contains(., 'Исполнитель')]/following::div[@role='combobox'][1]");

    private final By createButtonInModalLocator = By.xpath("//button[normalize-space()='Создать']");
    private final By backdrop = By.cssSelector(".MuiBackdrop-root");
    private final By anyOpenListbox = By.cssSelector("ul[role='listbox']");
    private final By listboxOptionLocator = By.cssSelector("ul[role='listbox'] li.MuiMenuItem-root");

    public CreateTaskPage(WebDriver driver) {
        super(driver);
    }


    public CreateTaskPage setTitle(String title) {
        WebElement titleInput = wait.until(
                ExpectedConditions.elementToBeClickable(titleInputLocator)
        );
        titleInput.clear();
        titleInput.sendKeys(title);
        return this;
    }

    public CreateTaskPage setDescription(String description) {
        WebElement descriptionTextarea = wait.until(
                ExpectedConditions.elementToBeClickable(descriptionTextareaLocator)
        );
        descriptionTextarea.clear();
        descriptionTextarea.sendKeys(description);
        return this;
    }

    public CreateTaskPage selectFirstProject() {
        selectFirstFromCombobox(projectSelectLocator);
        return this;
    }

    public CreateTaskPage selectFirstPriority() {
        selectFirstFromCombobox(prioritySelectLocator);
        return this;
    }

    public CreateTaskPage selectFirstAssignee() {
        selectFirstFromCombobox(assigneeSelectLocator);
        return this;
    }


    private CreateTaskPage selectFirstFromCombobox(By comboboxLocator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(anyOpenListbox));
        } catch (TimeoutException ignored) {
                   }
        WebElement combobox = wait.until(
                ExpectedConditions.elementToBeClickable(comboboxLocator)
        );
        try {
            combobox.click();
        } catch (ElementClickInterceptedException e) {

        }
        WebElement listbox;
        try {
            listbox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(anyOpenListbox)
            );
        } catch (TimeoutException e) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            try {
                combobox.click();
            } catch (ElementClickInterceptedException ignored) {
            }
            listbox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(anyOpenListbox)
            );
        }

        WebElement firstOption = wait.until(
                ExpectedConditions.elementToBeClickable(listboxOptionLocator)
        );
        firstOption.click();
        wait.until(ExpectedConditions.invisibilityOf(listbox));

        return this;
    }



    public MainPage clickCreate() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(anyOpenListbox));
        } catch (TimeoutException ignored) {

        }

        WebElement createButton = wait.until(
                ExpectedConditions.elementToBeClickable(createButtonInModalLocator)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});" +
                        "arguments[0].click();",
                createButton
        );

        wait.until(ExpectedConditions.invisibilityOfElementLocated(backdrop));

        return new MainPage(driver);
    }


    public boolean isCreateButtonEnabled() {
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(createButtonInModalLocator));
        return button.isEnabled();
    }


}

