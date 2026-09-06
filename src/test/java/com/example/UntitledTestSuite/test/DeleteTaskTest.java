package com.example.UntitledTestSuite.test;

import com.example.UntitledTestSuite.data.TaskData;
import com.example.UntitledTestSuite.util.TestDataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class DeleteTaskTest extends AuthBase {

  static List<TaskData> testTasks() throws Exception {
    return TestDataLoader.loadTasksFromResource("delTasks.xml");
  }

  @BeforeEach
  public void setUp() throws Exception {
    super.setUpLogin();
  }

  @ParameterizedTest
  @MethodSource("testTasks")
  public void testDeleteTask(TaskData task) throws Exception {
    manager.getNavigationHelper().openAddNewTaskModal();
    manager.getTaskHelper().addNewTask(task);
    manager.getNavigationHelper().openUpcoming();

    String deletingTaskId = manager.getTaskHelper().deleteTask(task);
    manager.getTaskHelper().verifyTaskWasDeleted(task, deletingTaskId);
  }

}
