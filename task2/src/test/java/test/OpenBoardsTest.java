package test;

import org.junit.Test;
import pages.BoardDetailsPage;
import pages.BoardsPage;
import pages.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenBoardsTest extends BaseTest {

    @Test
    public void shouldOpenEachBoardFromProjectsPage() {
        MainPage mainPage = new MainPage(driver).open();
        BoardsPage boardsPage = mainPage.openBoardsPage();
        int boardsCount = boardsPage.getBoardsCount();
        assertTrue("Ожидалось хотя бы одно доступное «Перейти к доске»", boardsCount > 0);

        for (int i = 0; i < boardsCount; i++) {
            String expectedBoardName = boardsPage.getBoardNameByIndex(i);
            BoardDetailsPage boardPage = boardsPage.openBoardByIndex(i);
            String actualBoardName = boardPage.getBoardTitle();
            assertEquals("Открыта неверная доска по индексу " + i,
                    expectedBoardName, actualBoardName);
            boardsPage = boardPage.goBackToBoards();
        }
    }

}
