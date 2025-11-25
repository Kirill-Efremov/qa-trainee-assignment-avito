package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class BoardDetailsPage extends BasePage {

    private final By boardTitle = By.xpath(
            "//*[contains(@class,'MuiTypography-h4')]"
    );
    
    public BoardDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getBoardTitle() {
        return wait.until(d -> d.findElement(boardTitle)).getText().trim();
    }

    public BoardsPage goBackToBoards() {
        driver.navigate().back();
        return new BoardsPage(driver);
    }
}
