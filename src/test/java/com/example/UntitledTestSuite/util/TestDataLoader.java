package com.example.UntitledTestSuite.util;

import com.example.UntitledTestSuite.data.TaskData;
import com.example.UntitledTestSuite.data.TaskDataList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.List;

public class TestDataLoader {
        public static List<TaskData> loadTasksFromResource(String resourcePath) throws Exception {
            try (InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
                JAXBContext context = JAXBContext.newInstance(TaskDataList.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                TaskDataList taskList = (TaskDataList) unmarshaller.unmarshal(is);
                return taskList.getTasks();
            }
        }
}
