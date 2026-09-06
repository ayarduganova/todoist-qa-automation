# Todoist Selenium Automation

Автоматизированное UI-тестирование веб-приложения [Todoist]), написанное на Java с использованием Selenium WebDriver и JUnit 5.

## О проекте

Пет-проект для отработки построения фреймворка автоматизации с нуля: архитектура, работа с тестовыми данными, параметризованные тесты, взаимодействие с динамическим UI.

Покрытые сценарии:
- Авторизация (успешная и с невалидными данными)
- Создание задачи с полным набором параметров (название, описание, дата, приоритет, проект)
- Удаление задачи с проверкой, что она действительно исчезла из списка

## Стек

- **Java**
- **Selenium WebDriver** — автоматизация браузера
- **JUnit 5** (Jupiter) — параметризованные тесты
- **JAXB** — сериализация/десериализация тестовых данных в XML
- **Maven** — сборка проекта

## Архитектура

Фреймворк построен по паттерну **Page Object / Helper**:

```
├── data/              # модели данных (AccountData, TaskData, TaskDataList)
├── generate/          # генератор тестовых данных (TaskDataGenerator, Main)
├── helper/            # хелперы для взаимодействия с UI
│   ├── HelperBase
│   ├── LoginHelper
│   ├── NavigationHelper
│   └── TaskHelper
├── manager/           # ApplicationManager — управление драйвером и хелперами
├── test/              # тестовые классы
│   ├── TestBase / AuthBase
│   ├── LoginTest
│   ├── AddNewTaskTest
│   └── DeleteTaskTest
└── util/              # Settings, TestDataLoader
```

- **ApplicationManager** — синглтон (ThreadLocal), управляет жизненным циклом WebDriver и хранит ссылки на все хелперы.
- **Helper-классы** инкапсулируют логику взаимодействия с конкретными частями UI (логин, навигация, работа с задачами), наследуются от `HelperBase`.
- **TestBase / AuthBase** — базовые классы тестов, выносят общую подготовку (инициализация менеджера, авторизация) в `@Before`.
- Тестовые данные хранятся в XML и генерируются/загружаются через JAXB (`TaskDataGenerator`, `TestDataLoader`), что отделяет данные от логики тестов.

## Настройка перед запуском

1. Скопируйте `src/test/resources/Settings.xml.example` в `Settings.xml` и укажите свои данные:

```xml
<settings>
    <BaseUrl>https://app.todoist.com</BaseUrl>
    <Email>your_email@example.com</Email>
    <Password>your_password</Password>
    <Username>your_username</Username>
</settings>
```

2. Укажите путь к `chromedriver` в `ApplicationManager.java` (переменная `webdriver.chrome.driver`) в соответствии с вашей системой.

3. При необходимости сгенерируйте тестовые данные для задач:

```bash
mvn compile exec:java -Dexec.mainClass="com.example.UntitledTestSuite.generate.Main"
```
Скрипт запросит количество задач и имя файла, после чего сохранит XML в `src/test/resources`.

## Запуск тестов

```bash
mvn test
```
- [ ] Заменить `Thread.sleep` на явные ожидания там, где это возможно

## Автор

Анастасия Ардуганова
