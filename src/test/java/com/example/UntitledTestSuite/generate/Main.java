package com.example.UntitledTestSuite.generate;

import jakarta.xml.bind.JAXBException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите кол-во задач и имя файла через пробел: ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        if (parts.length != 2) {
            System.out.println("Ошибка: введите ровно два значения (кол-во задач и имя файла)");
            return;
        }

        try {
            int count = Integer.parseInt(parts[0]);
            String fileName = parts[1];

            TaskDataGenerator.generateTasksToXml(count, fileName);
            System.out.println("Удачно сгенерировано " + count + " задач в " + fileName);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: первое значение должно быть цифрой");
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }
}