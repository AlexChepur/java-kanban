package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private int taskIdCounter = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();

    public Task createTask(Task task) {
        task.setId(taskIdCounter++);
        tasks.put(task.getId(), task);
        return task;
    }

    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            if (task.getType() == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                Epic epic = getEpicById(subtask.getEpicId());
                if (epic != null) {
                    Status newStatus = calculateEpicStatus(epic);
                    epic.setStatus(newStatus);
                }
            }
            return task;
        }
        return task;
    }

    public void deleteTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
            if (task.getType() == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                Epic epic = getEpicById(subtask.getEpicId());
                if (epic != null) {
                    epic.removeSubtask(subtask);
                    Status newStatus = calculateEpicStatus(epic);
                    epic.setStatus(newStatus);
                }
            }
        }
    }

    public Status calculateEpicStatus(Epic epic) {
        List<Subtask> subtasks = getSubtasksByEpicId(epic.getId());
        if (subtasks.isEmpty()) {
            return Status.NEW;
        }
        boolean allNew = true;
        boolean allDone = true;
        for (Subtask subtask : subtasks) {
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }
        if (allNew) {
            return Status.NEW;
        } else if (allDone) {
            return Status.DONE;
        } else {
            return Status.IN_PROGRESS;
        }
    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        return task;
    }

    public Epic getEpicById(int id) {
        Task task = tasks.get(id);
        if (task != null && task.getType() == TaskType.EPIC) {
            return (Epic) task;
        }
        return null;
    }

    public Subtask getSubtaskById(int id) {
        Task task = tasks.get(id);
        if (task != null && task.getType() == TaskType.SUBTASK) {
            return (Subtask) task;
        }
        return null;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        List<Epic> epics = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.EPIC) {
                epics.add((Epic) task);
            }
        }
        return epics;
    }

    public List<Subtask> getAllSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.SUBTASK) {
                subtasks.add((Subtask) task);
            }
        }
        return subtasks;
    }

    public List<Subtask> getSubtasksByEpicId(int epicId) {
        List<Subtask> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.SUBTASK) {
                Subtask subtask = (Subtask) task;
                if (subtask.getEpicId() == epicId) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }

    public void addEpic(Epic epic) {
        epic.setId(taskIdCounter++);
        tasks.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(taskIdCounter++);
        tasks.put(subtask.getId(), subtask);
        Epic epic = getEpicById(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);
            Status newStatus = calculateEpicStatus(epic);
            epic.setStatus(newStatus);
        }
    }

    public void deleteAllTasks() {
        tasks.entrySet().removeIf(entry -> entry.getValue().getType() == TaskType.TASK);
    }

    public void deleteAllEpics() {
        tasks.entrySet().removeIf(entry -> entry.getValue().getType() == TaskType.EPIC);
    }

    public void deleteAllSubtasks() {
        tasks.entrySet().removeIf(entry -> entry.getValue().getType() == TaskType.SUBTASK);

        for (Task task : tasks.values()) {
            if (task.getType() == TaskType.EPIC) {
                Epic epic = (Epic) task;
                epic.getSubtasks().clear();
                epic.setStatus(Status.NEW);
            }
        }
    }
}