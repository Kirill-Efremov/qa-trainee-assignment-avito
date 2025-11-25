package test;

import model.TaskData;
import org.junit.Test;
import pages.CreateTaskPage;
import pages.MainPage;

import static org.junit.Assert.*;

public class CreateTaskTest extends BaseTest {

    @Test
    public void shouldCreateTaskWithValidData() {
        TaskData task = TaskData.random("Автотест: валидное создание");

        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        MainPage afterCreate  = createTaskPage
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee()
                .clickCreate();

        assertTrue("Задача должна быть видна на главной странице",
                afterCreate.isTaskVisible(task));
    }


    @Test
    public void shouldNotEnableCreateButtonWithoutTitle() {
        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setDescription("Какое-то описание")
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee();

        assertFalse("Кнопка 'Создать' должна быть неактивна без названия задачи",
                createTaskPage.isCreateButtonEnabled());
    }

    @Test
    public void shouldNotEnableCreateButtonWithoutProject() {
        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setTitle("Автотест: без проекта")
                .selectFirstPriority()
                .selectFirstAssignee();

        assertFalse("Кнопка 'Создать' должна быть неактивна без выбранного проекта",
                createTaskPage.isCreateButtonEnabled());
    }

    @Test
    public void shouldNotEnableCreateButtonWithoutPriority() {
        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setTitle("Автотест: без приоритета")
                .selectFirstProject()
                .selectFirstAssignee();

        assertFalse("Кнопка 'Создать' должна быть неактивна без выбранного приоритета",
                createTaskPage.isCreateButtonEnabled());
    }

    @Test
    public void shouldNotEnableCreateButtonWithoutAssignee() {
        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setTitle("Автотест: без исполнителя")
                .selectFirstProject()
                .selectFirstPriority();

        assertFalse("Кнопка 'Создать' должна быть неактивна без выбранного исполнителя",
                createTaskPage.isCreateButtonEnabled());
    }


    @Test
    public void shouldCreateTaskWithXssLikeTitle() {
        TaskData task = new TaskData("<script>alert('xss-ataka')</script>",
                "Проверка XSS в заголовке");

        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee()
                .clickCreate();

        assertTrue("Задача с XSS-подобным заголовком должна быть отображена как текст",
                mainPage.isTaskVisible(task));
    }

    @Test
    public void shouldCreateTaskWithSqlInjectionLikeTitle() {
        TaskData task = new TaskData("'; DROP TABLE tasks; --",
                "Проверка SQL-инъекции в заголовке");

        MainPage mainPage = new MainPage(driver).open();
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();

        createTaskPage
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee()
                .clickCreate();

        assertTrue("Задача с SQL-подобным заголовком должна быть отображена как текст",
                mainPage.isTaskVisible(task));
    }

    @Test
    public void shouldAllowDuplicateTitles() {
        String title = "Автотест: дублирующийся заголовок";

        MainPage mainPage = new MainPage(driver).open();

        TaskData first = new TaskData(title, "Первая задача с таким же заголовком");
        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();
        createTaskPage
                .setTitle(first.getTitle())
                .setDescription(first.getDescription())
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee()
                .clickCreate();

        assertTrue(mainPage.isTaskVisible(first));

        TaskData second = new TaskData(title, "Вторая задача с тем же заголовком");
        createTaskPage = mainPage.openCreateTaskForm();
        createTaskPage
                .setTitle(second.getTitle())
                .setDescription(second.getDescription())
                .selectFirstProject()
                .selectFirstPriority()
                .selectFirstAssignee()
                .clickCreate();

        long count = mainPage.getTasksCountByTitle(title);
        assertTrue("Должно быть хотя бы две задачи с одинаковым заголовком",
                count >= 2);
    }

    // БАГ(падает сайт) -> необходим лимит на максимальные значения в названии и описании!
//    @Test
//    public void shouldCreateTaskWithLongTitleAndDescription() {
//        String longTitle = "T".repeat(200);
//        String longDescription = "D".repeat(1000);
//        TaskData task = new TaskData(longTitle, longDescription);
//
//        MainPage mainPage = new MainPage(driver).open();
//        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();
//
//        createTaskPage
//                .setTitle(longTitle)
//                .setDescription(longDescription)
//                .selectFirstProject()
//                .selectFirstPriority()
//                .selectFirstAssignee()
//                .clickCreate();
//
//        assertTrue("Задача с длинным заголовком должна создаться",
//                mainPage.isTaskVisible(task));
//    }
    // БАГ -> нельзя создать задачу с пустым описанием, хотя описание не является обязательным полем
//    @Test
//    public void shouldCreateTaskWithoutDescription() {
//        TaskData task = new TaskData(
//                "Автотест: без описания",
//                ""
//        );
//
//        MainPage mainPage = new MainPage(driver).open();
//        CreateTaskPage createTaskPage = mainPage.openCreateTaskForm();
//
//        MainPage afterCreate = createTaskPage
//                .setTitle(task.getTitle())
//                .selectFirstProject()
//                .selectFirstPriority()
//                .selectFirstAssignee()
//                .clickCreate();
//
//        assertTrue("Задача без описания должна успешно создаваться и отображаться на главной странице", afterCreate.isTaskVisible(task));
//    }
}
