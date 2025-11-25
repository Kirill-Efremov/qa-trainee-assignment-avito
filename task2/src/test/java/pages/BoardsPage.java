package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
public class BoardsPage extends BasePage {
    private final By boardCards = By.xpath(
            "//div[contains(@class,'MuiStack-root')]" +
                    "//div[contains(@class,'MuiPaper-root') and contains(@class,'MuiPaper-outlined')]"
    );
    private final By boardTitleInCard = By.xpath(
            ".//h6[contains(@class,'MuiTypography-subtitle1')]"
    );
    private final By goToBoardButton = By.xpath(
            ".//a[contains(@href, '/board') and contains(., 'Перейти к доске')]"
    );

    public BoardsPage(WebDriver driver) {
        super(driver);
    }

    public int getBoardsCount() {
        List<WebElement> cards = wait.until(d -> d.findElements(boardCards));
        return cards.size();
    }

    public String getBoardNameByIndex(int index) {
        List<WebElement> cards = wait.until(d -> d.findElements(boardCards));
        WebElement card = cards.get(index);
        return card.findElement(boardTitleInCard).getText().trim();
    }

    public BoardDetailsPage openBoardByIndex(int index) {
        List<WebElement> cards = wait.until(d -> d.findElements(boardCards));
        WebElement card = cards.get(index);
        card.findElement(goToBoardButton).click();
        return new BoardDetailsPage(driver);
    }
}
