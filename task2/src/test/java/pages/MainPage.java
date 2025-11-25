package pages;

import model.TaskData;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends BasePage {

    private static final String BASE_URL = "https://avito-tech-internship-psi.vercel.app/";

    private final By createTaskButton = By.xpath("//button[contains(., 'Создать задачу') or contains(., 'СОЗДАТЬ ЗАДАЧУ')]");
    private final By boardsNavButton = By.xpath("//a[contains(text(),'Проекты')]");
    private final By searchInput = By.id(":r3:");

    private final By taskTitles = By.xpath("//div[contains(@class,'MuiStack-root') and contains(@class,'css-1ov46kg')]//h6[contains(@class,'MuiTypography-subtitle1')]");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(BASE_URL);
        return this;
    }

    public CreateTaskPage openCreateTaskForm() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(createTaskButton)
        );
        button.click();
        return new CreateTaskPage(driver);
    }

    public BoardsPage openBoardsPage() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(boardsNavButton)
        );
        button.click();
        return new BoardsPage(driver);
    }

    public List<String> getVisibleTaskTitles() {
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(taskTitles)
        );
        return elements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public int getTasksCount() {
        return getVisibleTaskTitles().size();
    }

    public boolean hasNoTasks() {
        return driver.findElements(taskTitles).isEmpty();
    }

    public boolean isTaskVisible(String title) {
        return getVisibleTaskTitles().stream()
                .anyMatch(t -> t.equals(title));
    }

    public boolean isTaskVisible(TaskData task) {
        return isTaskVisible(task.getTitle());
    }

    public long getTasksCountByTitle(String title) {
        return getVisibleTaskTitles().stream()
                .filter(t -> t.equals(title))
                .count();
    }

    public boolean containsTaskIgnoreCase(String title) {
        return getVisibleTaskTitles().stream()
                .anyMatch(t -> t.equalsIgnoreCase(title));
    }

    public String getTaskTitleByIndex(int index) {
        List<WebElement> titles = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(taskTitles)
        );
        return titles.get(index).getText().trim();
    }


    public TaskPage openTaskByIndex(int index) {
        List<WebElement> titles = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(taskTitles)
        );
        titles.get(index).click();
        return new TaskPage(driver);
    }


    public MainPage searchByTitle(String query) {
        WebElement input = getSearchInput();
        clearAndType(input, query);
        waitForSearchSettled();
        return this;
    }

    public MainPage clearSearch() {
        WebElement input = getSearchInput();
        clearAndType(input, "");
        waitForSearchSettled();
        return this;
    }

    public boolean searchInputIsVisible() {
        return !driver.findElements(searchInput).isEmpty();
    }

    private WebElement getSearchInput() {
        return wait.until(ExpectedConditions.elementToBeClickable(searchInput));
    }

    private void clearAndType(WebElement input, String text) {
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        if (text != null && !text.isEmpty()) {
            input.sendKeys(text);
        }
    }

    private void waitForSearchSettled() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
