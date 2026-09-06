package com.example.UntitledTestSuite.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "tasks")
public class TaskDataList {
    private List<TaskData> tasks;

    public TaskDataList() {
    }

    public TaskDataList(List<TaskData> tasks) {
        this.tasks = tasks;
    }

    @XmlElement(name = "task")
    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }

}
