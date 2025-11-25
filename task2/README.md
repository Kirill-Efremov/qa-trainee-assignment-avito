# Автоматизированные UI-тесты Avito Tech Internship

> https://avito-tech-internship-psi.vercel.app/

Тесты покрывают основные пользовательские сценарии:

1. Создание задачи
2. Открытие карточки задачи
3. Поиск задачи
4. Переход на доску проекта

Полный набор тест-кейсов описан в файле **TESTCASES.md**.

---

##  Технологии

- Java 24
- Maven
- JUnit 4
- Selenium WebDriver 4
- WebDriverManager (автоматическая установка драйвера браузера)
- Браузер: Firefox (по умолчанию)

---

##  Структура проекта

Проектная структура (папка `src/test/java`):

```text
testcases_bug
├── BUG.md              # Баги задания 2.2   
└── TESTCASES.md        # Тест-кейсы задания 2.2   
src
└── test
    └── java
        ├── model
        │   └── TaskData.java           # Модель данных задачи
        │
        ├── pages                       # Page Object’ы
        │   ├── BasePage.java
        │   ├── BoardDetailsPage.java   # Страница конкретной доски
        │   ├── BoardsPage.java         # Страница “Проекты” (список досок)
        │   ├── CreateTaskPage.java     # Модалка создания задачи
        │   ├── MainPage.java           # Главная страница со списком задач
        │   └── TaskPage.java           # Модалка карточки задачи
        │
        └── test                        # JUnit-тесты
            ├── BaseTest.java           # Базовый класс: настройка WebDriver
            ├── CreateTaskTest.java     # Тесты создания задачи
            ├── OpenBoardsTest.java     # Тесты перехода на доски проектов
            ├── OpenTaskTest.java       # Тесты открытия карточки задачи
            ├── OrderTest.java          # Сьют: прогон всех тестов
            └── SearchTaskTest.java     # Тесты поиска задач

```

---
##  Запуск проекта
**Java 17+** 
Проверить:
java -version

**Maven**
Проверить:
mvn -version  

**Браузер Firefox**  
Тесты используют Selenium + WebDriverManager.  
WebDriverManager сам скачает geckodriver, но Firefox должен быть установлен в системе.  

**Клонировать репозиторий**  
gh repo clone Kirill-Efremov/qa-trainee-assignment-avito

**Перейти в папку проекта**  
cd qa-trainee-assignment-avito/task2  
ls
должен быть файл pom.xml и папка src/

**Сборка проекта**  
mvn clean test

**Запуск тестов**  
mvn -Dtest=OrderTest test

