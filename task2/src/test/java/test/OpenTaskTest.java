package test;

import org.junit.Test;
import pages.MainPage;
import pages.TaskPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpenTaskTest extends BaseTest{
    @Test
    public void shouldOpenTaskCardFromMainPage() throws InterruptedException {
        MainPage mainPage = new MainPage(driver).open();

        int tasksCount = mainPage.getTasksCount();
        assertTrue("Ожидалась хотя бы одна задача на главной странице", tasksCount > 0);

        int taskIndex = 0;
        assertTrue(
                "Некорректный индекс задачи: " + taskIndex,
                taskIndex >= 0 && taskIndex < tasksCount
        );

        String expectedTaskTitle = mainPage.getTaskTitleByIndex(taskIndex);

        TaskPage taskPage = mainPage.openTaskByIndex(taskIndex);
        assertTrue("Форма редактирования задачи не открылась", taskPage.isOpened());
        Thread.sleep(1000);
        String actualTaskTitle = taskPage.getTaskNameValue();

        assertEquals("Название задачи в карточке не совпадает с названием на главной",
                expectedTaskTitle, actualTaskTitle);

        assertTrue("Поле 'Проект' пустое", taskPage.hasNonEmptyProject());
        assertTrue("Поле 'Приоритет' пустое", taskPage.hasNonEmptyPriority());
        assertTrue("Поле 'Статус' пустое", taskPage.hasNonEmptyStatus());
        assertTrue("Поле 'Исполнитель' пустое", taskPage.hasNonEmptyAssignee());
        assertTrue("Отсутствует поле описания задачи", taskPage.hasDescriptionField());
    }


}
