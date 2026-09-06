package com.example.UntitledTestSuite.data;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
public class TaskData {
    private String name;
    private String description;
    private String priority;
    private String date;
    private String project;

    public TaskData() {}

    public TaskData(String name, String description, String priority, String date, String project) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public String setPriority(String priority) {
        return this.priority = priority;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
