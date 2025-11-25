package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TaskPage extends BasePage {
    private final By formTitle = By.xpath("//h5[contains(normalize-space(.), 'Редактирование задачи')]");
    private final By taskNameInput = By.xpath(
            "//label[contains(normalize-space(.), 'Название')]/following::input[1]"
    );
    private final By descriptionTextArea = By.xpath(
            "//label[contains(normalize-space(.), 'Описание')]/following::textarea[1]"
    );
    private final By projectSelectValue = By.xpath(
            "//label[contains(normalize-space(.), 'Проект')]/following::div[contains(@class,'MuiSelect-select')][1]"
    );
    private final By prioritySelectValue = By.xpath(
            "//label[contains(normalize-space(.), 'Приоритет')]/following::div[contains(@class,'MuiSelect-select')][1]"
    );
    private final By statusSelectValue = By.xpath(
            "//label[contains(.,'Статус')]/following-sibling::div" +
                    "//div[contains(@class,'MuiSelect-select')]"
    );
    private final By assigneeSelectValue = By.xpath(
            "//label[contains(normalize-space(.), 'Исполнитель')]/following::div[contains(@class,'MuiSelect-select')][1]"
    );

    public TaskPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOpened() {
        return !driver.findElements(formTitle).isEmpty();
    }

    public String getTaskNameValue() {
        return wait.until(d -> d.findElement(taskNameInput))
                .getAttribute("value")
                .trim();
    }

    private String getSelectText(By locator) {
        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        String text = el.getText();
        if (text == null || text.isBlank()) {
            text = el.getDomProperty("textContent");
        }
        return text == null ? "" : text.trim();
    }
    public boolean hasNonEmptyProject() {
        return !getSelectText(projectSelectValue).isEmpty();
    }

    public boolean hasNonEmptyPriority() {
        return !getSelectText(prioritySelectValue).isEmpty();
    }

    public boolean hasNonEmptyStatus() {
        return !getSelectText(statusSelectValue).isEmpty();
    }

    public boolean hasNonEmptyAssignee() {
        return !getSelectText(assigneeSelectValue).isEmpty();
    }

    public boolean hasDescriptionField() {
        return !driver.findElements(descriptionTextArea).isEmpty();
    }
}

