import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        System.out.println("1. Создание задач");
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.IN_PROGRESS);
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 0);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 0);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        subtask1.setEpicId(epic2.getId());
        subtask2.setEpicId(epic2.getId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        System.out.println("Все задачи после добавления:");
        printTasks(manager.getAllTasks());

        System.out.println("\n2. Обновление задач");
        task1.setDescription("Новое описание задачи 1");
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);

        System.out.println("Обновленная задача с id = " + task1.getId() + ":");
        System.out.println(manager.getTaskById(task1.getId()));

        System.out.println("\n3. Удаление задач");
        System.out.println("Удаляем задачу с id = " + task2.getId());
        manager.deleteTask(task2.getId());

        System.out.println("Все задачи после удаления:");
        printTasks(manager.getAllTasks());

        System.out.println("\n4. Получение задач по ID");
        System.out.println("Получаем задачу с id = " + task1.getId() + ":");
        Task foundTask = manager.getTaskById(task1.getId());
        if (foundTask != null) {
            System.out.println(foundTask);
        } else {
            System.out.println("Задача с id = " + task1.getId() + " не найдена.");
        }

        System.out.println("\n5. Получение списков задач");
        System.out.println("Все задачи:");
        printTasks(manager.getAllTasks());

        System.out.println("\nВсе эпики:");
        printEpics(manager.getAllEpics());

        System.out.println("\nВсе подзадачи:");
        printSubtasks(manager.getAllSubtasks());

        System.out.println("\n6. Получение подзадач эпика");
        List<Subtask> subtasksOfEpic2 = manager.getSubtasksByEpicId(epic2.getId());
        System.out.println("Подзадачи эпика с id = " + epic2.getId() + ":");
        printSubtasks(subtasksOfEpic2);

        System.out.println("\n7. Проверка статусов эпиков");
        System.out.println("Изначальный статус эпика 2: " + epic2.getStatus());

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);
        manager.updateTask(subtask1);
        manager.updateTask(subtask2);

        Epic updatedEpic2 = manager.getEpicById(epic2.getId());
        System.out.println("Статус эпика 2 после завершения всех подзадач: " + updatedEpic2.getStatus());

        System.out.println("\n8. Удаление всех задач, эпиков и подзадач");
        manager.deleteAllTasks();
        printTasks(manager.getAllTasks());

        manager.deleteAllEpics();
        printEpics(manager.getAllEpics());

        manager.deleteAllSubtasks();
        printSubtasks(manager.getAllSubtasks());

        System.out.println("\n9. Итоговый список всех задач после удаления");
        printTasks(manager.getAllTasks());
    }

    private static void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static void printEpics(List<Epic> epics) {
        if (epics.isEmpty()) {
            System.out.println("Список эпиков пуст.");
        } else {
            for (Epic epic : epics) {
                System.out.println(epic);
            }
        }
    }

    private static void printSubtasks(List<Subtask> subtasks) {
        if (subtasks.isEmpty()) {
            System.out.println("Список подзадач пуст.");
        } else {
            for (Subtask subtask : subtasks) {
                System.out.println(subtask);
            }
        }
    }
}