package com.example.UntitledTestSuite.helper;

import com.example.UntitledTestSuite.data.TaskData;
import com.example.UntitledTestSuite.manager.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class TaskHelper extends HelperBase {

    public TaskHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addNewTask(TaskData task) throws InterruptedException {
        WebElement taskName = waitDriver.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[contenteditable='true'][aria-label='Название задачи'")));
        taskName.click();
        taskName.clear();
        taskName.sendKeys(task.getName());

        WebElement description = driver.findElement(By.cssSelector("div[contenteditable='true'][aria-label='Описание'"));
        description.click();
        description.clear();
        description.sendKeys(task.getDescription());

        clickWhenReady(By.cssSelector("div[aria-label='Выбрать дату']"));
        clickWhenReady(By.cssSelector("button[aria-label='%s']".formatted(task.getDate())));
        clickWhenReady(By.cssSelector("div[aria-label='Установить приоритет']"));
        clickWhenReady(By.xpath("//span[text()='%s']".formatted(task.getPriority())));

        clickWhenReady(By.cssSelector("button[aria-label='Выбрать проект']"));
        WebElement newProject = waitDriver.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[aria-label='Введите название проекта'")));
        newProject.click();
        newProject.clear();
        newProject.sendKeys(task.getProject());

        clickIfPresent(By.xpath("//button[contains(text(), 'Создать')]"));

        clickWhenReady(By.cssSelector("div[data-indentation-level='0'"));

        clickWhenReady(By.cssSelector("button[data-testid='task-editor-submit-button']"));
        Thread.sleep(1000);
    }

    public String deleteTask(TaskData task) throws InterruptedException {
        WebElement targetDayList = getTasksByDate(task.getDate());
        Thread.sleep(3000);
        List<WebElement> tasksWithName = getTasksByName(task.getName(), targetDayList);
        Thread.sleep(3000);
        List<WebElement> elementsWithDescription = getElementsByDescription(task.getDescription(), tasksWithName);
        Thread.sleep(3000);
        List<WebElement> elementsByTag = getElementsByTag(task.getProject(), elementsWithDescription);
        Thread.sleep(3000);
        List<WebElement> elementWithRightPriority = getElementsByPriority(task.getPriority(), elementsByTag);

        WebElement element = elementWithRightPriority.get(0);
        String elementId = element.getAttribute("data-item-id");

        new Actions(driver).moveToElement(element).perform();
        element.findElement(By.xpath(".//button[@aria-label='Другие действия']"))
                .click();

        Thread.sleep(1000);

        waitDriver.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//div[@role='menuitem' and @data-action-hint='task-overflow-menu-delete']"
        ))).click();

        WebElement dialog = waitDriver.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@role='dialog' and @data-open='true']")
        ));

        Thread.sleep(1000);

        dialog.findElement(By.xpath(
                ".//footer//button[.//span[contains(., 'Удалить')]]"
        )).click();

        return elementId;
    }

    public void verifyTaskExists(TaskData task) throws InterruptedException {
        WebElement targetDayList = getTasksByDate(task.getDate());
        assertNotNull("Нет задач в этот день", targetDayList);
        Thread.sleep(3000);

        List<WebElement> tasksWithName = getTasksByName(task.getName(), targetDayList);
        assertFalse("Задач с таким именем в эту дату не найдено", tasksWithName.isEmpty());
        Thread.sleep(3000);

        List<WebElement> elementsWithDescription = getElementsByDescription(task.getDescription(), tasksWithName);
        assertFalse("Задач с таким описанием в эту дату и с этим именем не найдено", tasksWithName.isEmpty());
        Thread.sleep(3000);

        List<WebElement> elementsByTag = getElementsByTag(task.getProject(), elementsWithDescription);
        assertFalse("Задач с этим тегом в эту дату, с этим именем и описанием не найдено",
                elementsByTag.isEmpty());

        List<WebElement> elementWithRightPriority = getElementsByPriority(task.getPriority(), elementsByTag);
        assertFalse("Задач с этим приоритетом в эту дату, с этим именем, описанием и тегом не найдено",
                elementWithRightPriority.isEmpty());
    }

    public void verifyTaskWasDeleted(TaskData task, String taskId) throws InterruptedException {
        WebElement targetDayList = getTasksByDate(task.getDate());
        Thread.sleep(3000);
        List<WebElement> tasks = getTaskObjects(targetDayList);

        for (WebElement element : tasks) {
            assertNotEquals("Задача с id - %s, не удалена".formatted(taskId),
                    element.getAttribute("data-item-id"), taskId);
        }
    }

    private List<WebElement> getElementsByPriority(String taskPriority, List<WebElement> elementsByTag) throws InterruptedException {
        List<WebElement> elementsWithPriority = new ArrayList<>();
        for (WebElement element : elementsByTag) {
            new Actions(driver).moveToElement(element).perform();
            Thread.sleep(4000);
            WebElement otherActions = element.findElement
                    (By.xpath(".//button[@aria-label='Другие действия']"));
            otherActions.click();
            try {
                WebElement priorityElement = element.findElement(By
                        .xpath("//div[contains(@aria-label, 'Priority') and contains(@class, 'JeNVROI')]"));
                String priority = priorityElement.getAttribute("aria-label")
                        .replaceFirst("^Priority\\s+", "Приоритет ");
                priorityElement.click();

                if (priority.equals(taskPriority)) {
                    elementsWithPriority.add(element);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        return elementsWithPriority;
    }

    private List<WebElement> getElementsByTag(String taskTag, List<WebElement> elementsWithDescription) {
        List<WebElement> elementsWithTag = new ArrayList<>();
        for (WebElement element : elementsWithDescription) {
            String projectName = "";
            try {
                projectName = element.findElement(By
                        .xpath(".//div[@data-testid='task-info-tags']//span")).getText();
                if (projectName.equals(taskTag)) {
                    elementsWithTag.add(element);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
        return elementsWithTag;
    }

    private List<WebElement> getElementsByDescription(String taskDescription, List<WebElement> tasks) {
        List<WebElement> elementWithDescription = new ArrayList<>();
        for (WebElement element : tasks) {
            try {
                String description = element.findElement(By
                        .xpath(".//div[contains(@class, 'task_description')]/p")).getText();
                if (description.equals(taskDescription)) {
                    elementWithDescription.add(element);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }

        return elementWithDescription;
    }

    private List<WebElement> getTasksByName(String taskName, WebElement targetDayList) {
        List<WebElement> tasks = new ArrayList<>();
        if (targetDayList != null) {
            List<WebElement> allTasks = getTaskObjects(targetDayList);
            for (WebElement taskElement : allTasks) {
                try {
                    String name = taskElement.findElement(By
                            .xpath(".//div[@class='task_content']")).getText();
                    if (name.equals(taskName)) {
                        tasks.add(taskElement);
                    }
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
        }
        return tasks;
    }

    private List<WebElement> getTaskObjects(WebElement targetDayList) {
        List<WebElement> tasks = new ArrayList<>();
        if (targetDayList != null) {
            tasks = targetDayList.findElements(By.xpath(".//li[@class='task_list_item']"));
        }
        return tasks;
    }

    private WebElement getTasksByDate(String taskDate) {
        WebElement targetDayList = null;
        WebElement lastElement = null;
        int attempts = 0;
        final int maxAttempts = 35;

        while (attempts < maxAttempts && targetDayList == null) {
            List<WebElement> dayLists = driver.findElements(By.xpath("//ul[@class='items']"));

            for (WebElement dayList : dayLists) {
                String date = dayList.getAttribute("data-day-list-id");
                if (taskDate.equals(date)) {
                    targetDayList = dayList;
                    break;
                }
            }

            if (targetDayList == null && !dayLists.isEmpty()) {

                lastElement = dayLists.get(dayLists.size() - 1);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                        lastElement);

                try {
                    new WebDriverWait(driver, Duration.ofSeconds(3))
                            .until(ExpectedConditions.numberOfElementsToBeMoreThan(
                                    By.xpath("//ul[@class='items']"), dayLists.size()));
                } catch (TimeoutException e) {
                    attempts++;
                }
            }
        }

        return targetDayList;
    }

//    private WebElement getTasksByDate(String taskDate, List<WebElement> allDayLists) {
//        WebElement targetDayList = null;
//        for (WebElement dayList : allDayLists) {
//            if (dayList.getAttribute("data-day-list-id").equals(taskDate)) {
//                targetDayList = dayList;
//                break;
//            }
//        }
//        return targetDayList;
//    }
//
//    private List<WebElement> getAllTasks() {
//        return driver.findElements(By.xpath("//ul[@class='items']"));
//    }

    private void clickWhenReady(By locator) {
        waitDriver.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void clickIfPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            elements.get(0).click();
        }
    }

}
