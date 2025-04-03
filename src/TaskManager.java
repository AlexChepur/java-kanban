import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private static int taskIdCounter = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private List<Task> allTasks = new ArrayList<>();
    private List<Epic> epics = new ArrayList<>();
    private List<Subtask> subtasks = new ArrayList<>();

    public Task createTask(Task task) {
        task.setId(taskIdCounter++);
        tasks.put(task.getId(), task);
        allTasks.add(task);
        return task;
    }

    public Task updateTask(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    public void deleteTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
            allTasks.remove(task);

            if (task instanceof Epic) {
                epics.remove(task);
            } else if (task instanceof Subtask) {
                subtasks.remove(task);
            }
        }
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return allTasks;
    }

    public List<Epic> getAllEpics() {
        return epics;
    }

    public List<Subtask> getAllSubtasks() {
        return subtasks;
    }

    public List<Subtask> getSubtasksByEpicId(int epicId) {
        List<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            if (subtask.getEpicId() == epicId) {
                result.add(subtask);
            }
        }
        return result;
    }

    public void addEpic(Epic epic) {
        epic.setId(taskIdCounter++);
        tasks.put(epic.getId(), epic);
        allTasks.add(epic);
        epics.add(epic);
    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(taskIdCounter++);
        tasks.put(subtask.getId(), subtask);
        allTasks.add(subtask);
        subtasks.add(subtask);

        Epic epic = getEpicById(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(subtask);
        }
    }

    private Epic getEpicById(int epicId) {
        for (Epic epic : epics) {
            if (epic.getId() == epicId) {
                return epic;
            }
        }
        return null;
    }
}