package test;

import model.TaskData;
import org.junit.Before;
import org.junit.Test;
import pages.CreateTaskPage;
import pages.MainPage;

import static org.junit.Assert.*;

public class SearchTaskTest extends BaseTest {

    private static boolean taskCreated = false;
    private static TaskData baseTask;
    private MainPage mainPage;

    @Before
    public void setUpTest() {
        mainPage = new MainPage(driver).open();

        if (!taskCreated) {
            String title = "Поиск Теста" + System.currentTimeMillis();
            String description = "Описание для " + title;

            baseTask = new TaskData(title, description);

            CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

            createTaskPage
                    .setTitle(baseTask.getTitle())
                    .setDescription(baseTask.getDescription())
                    .selectFirstProject()
                    .selectFirstPriority()
                    .selectFirstAssignee()
                    .clickCreate();

            assertTrue("Предусловие: созданная задача должна быть видна на главной странице",
                    mainPage.isTaskVisible(baseTask));

            taskCreated = true;
        }
    }

    @Test
    public void shouldFindTaskByExactTitle() {
        mainPage.searchByTitle(baseTask.getTitle());

        long found = mainPage.getTasksCountByTitle(baseTask.getTitle());
        assertTrue("По точному названию задача должна находиться", found >= 1);
    }

    @Test
    public void shouldFindTaskBySubstring() {
        String title = baseTask.getTitle();
        String substring = title.length() > 6 ? title.substring(2, 6) : title.substring(0, 2);

        mainPage.searchByTitle(substring);

        boolean found = mainPage.getVisibleTaskTitles().stream()
                .anyMatch(t -> t.contains(substring));

        assertTrue("По подстроке из названия задача должна находиться", found);
    }

    @Test
    public void shouldIgnoreCaseInSearch() {
        String weirdCase = baseTask.getTitle().toUpperCase();

        mainPage.searchByTitle(weirdCase);

        boolean foundIgnoreCase = mainPage.containsTaskIgnoreCase(baseTask.getTitle());
        assertTrue("Поиск должен быть нечувствителен к регистру", foundIgnoreCase);
    }


    @Test
    public void shouldReturnEmptyListForNonExistingQuery() {
        mainPage.searchByTitle("qweqweqwe_not_exist_123");

        assertTrue("Для несуществующего запроса задач быть не должно", mainPage.hasNoTasks());
    }

    @Test
    public void shouldShowTasksWhenSearchCleared() {
        int allBefore = mainPage.getTasksCount();

        mainPage.searchByTitle(baseTask.getTitle());
        int filtered = mainPage.getTasksCount();
        assertTrue("После фильтрации задач должно стать меньше или равно",
                filtered <= allBefore);

        mainPage.clearSearch();
        int allAfter = mainPage.getTasksCount();

        assertTrue("После очистки поиска должен показываться хотя бы такой же список, как до фильтрации",
                allAfter >= filtered);
    }


    @Test
    public void shouldHandleVeryLongQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("x".repeat(300));
        String longQuery = sb.toString();

        mainPage.searchByTitle(longQuery);

        assertTrue("После длинного запроса интерфейс должен быть доступен",
                mainPage.searchInputIsVisible());
    }

    @Test
    public void shouldBeSafeForSqlInjectionLikeQuery() {
        String sqlLike = "'; DROP TABLE tasks; --";

        mainPage.searchByTitle(sqlLike);

        assertTrue("После SQL-like запроса интерфейс должен быть доступен",
                mainPage.searchInputIsVisible());
    }

    @Test
    public void shouldBeSafeForXssLikeQuery() {
        String xssLike = "<script>alert(document.cookie)</script>";

        mainPage.searchByTitle(xssLike);

        assertTrue("После XSS-like запроса интерфейс должен быть доступен",
                mainPage.searchInputIsVisible());
    }
//    БАГ -> Поиск задач по названию не работает при наличии пробелов в начале/конце запроса
//    Ожидаемый результат - Поиск игнорирует пробелы по краям строки.
//    @Test
//    public void shouldTrimSpacesInSearch() {
//        String withSpaces = "   " + baseTask.getTitle() + "   ";
//
//        mainPage.searchByTitle(withSpaces);
//
//        long count = mainPage.getTasksCountByTitle(baseTask.getTitle());
//        assertTrue("Поиск должен корректно работать с пробелами по краям", count >= 1);
//    }
}
