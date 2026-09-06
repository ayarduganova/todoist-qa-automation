package com.example.UntitledTestSuite.generate;

import com.example.UntitledTestSuite.data.TaskData;
import com.example.UntitledTestSuite.data.TaskDataList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskDataGenerator {
    private static final String[] PRIORITIES = {"Приоритет 1", "Приоритет 2", "Приоритет 3", "Приоритет 4"};
    private static final Random random = new Random();

    public static void generateTasksToXml(int count, String filename) throws JAXBException {
        List<TaskData> tasks = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            TaskData task = new TaskData();
            task.setName(generateRandomString(5, 20));
            task.setDescription(generateRandomString(10, 40));
            task.setPriority(PRIORITIES[random.nextInt(PRIORITIES.length)]);
            task.setDate(generateRandomDate());
            task.setProject(generateRandomString(5, 15));
            tasks.add(task);
        }

        TaskDataList taskList = new TaskDataList(tasks);

        String fullPath = Paths.get("src/test/resources", filename).toString();
        JAXBContext context = JAXBContext.newInstance(TaskDataList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(taskList, new File(fullPath));
    }

    private static String generateRandomString(int minLength, int maxLength) {
        String chars = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        int length = minLength + random.nextInt(maxLength - minLength + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private static String generateRandomDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        int daysToAdd = random.nextInt(30);
        return LocalDate.now().plusDays(daysToAdd).format(formatter);
    }

}
