import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        System.out.println("Создание задач");
        Task task1 = new Task("Задача 1", "Описание", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание", Status.IN_PROGRESS);

        Epic epic1 = new Epic("Эпик 1", "Описание", Status.NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание", Status.NEW);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание", Status.NEW, 0);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", Status.NEW, 0);

        manager.createTask(task1);
        manager.createTask(task2);
        manager.addEpic(epic1);
        manager.addEpic(epic2);

        subtask1.setEpicId(epic2.getId());
        subtask2.setEpicId(epic2.getId());

        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);

        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nОбновление задач");
        task1.setDescription("Новое описание");
        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);

        System.out.println("Обновленная задача:");
        System.out.println(manager.getTaskById(task1.getId()));

        System.out.println("\nУдаление задач");
        System.out.println("Удаляем задачу с id = " + task2.getId());
        manager.deleteTask(task2.getId());

        System.out.println("Все задачи после удаления:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nПолучение задачи по ID");
        Task foundTask = manager.getTaskById(task1.getId());
        if (foundTask != null) {
            System.out.println("Задача с id = " + task1.getId() + ": " + foundTask);
        } else {
            System.out.println("Задача с id = " + task1.getId() + " не найдена.");
        }

        System.out.println("\nПолучение списков задач");
        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("\nВсе эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);
        }

        System.out.println("\nВсе подзадачи:");
        for (Subtask subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("\nПолучение подзадач эпика");
        List<Subtask> subtasksOfEpic2 = manager.getSubtasksByEpicId(epic2.getId());
        System.out.println("Подзадачи эпика с id = " + epic2.getId() + ":");
        for (Subtask subtask : subtasksOfEpic2) {
            System.out.println(subtask);
        }

        System.out.println("\nПроверка статусов эпиков");
        System.out.println("Изначальный статус эпика 2: " + epic2.getStatus());

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);

        epic2.setStatus(epic2.calculateStatus());
        System.out.println("Статус эпика 2 после завершения всех подзадач: " + epic2.getStatus());

        System.out.println("\nУдаление всех задач");
        manager.deleteTask(task1.getId());
        manager.deleteTask(epic1.getId());
        manager.deleteTask(epic2.getId());
        manager.deleteTask(subtask1.getId());
        manager.deleteTask(subtask2.getId());

        System.out.println("Все задачи после удаления:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
    }
}