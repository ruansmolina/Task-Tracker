package service;


import model.Status;
import model.Task;
import repository.TaskRepository;

import java.io.IOException;
import java.util.List;

public class TaskService {
    private final TaskRepository taskRepository;
    private List<Task> tasks;
    public TaskService(TaskRepository repository){
        this.taskRepository = repository;
    }

    public void start() throws IOException {
        tasks = taskRepository.getTasks();
    }

    public void finish() throws IOException {
        taskRepository.saveTasks(this.tasks);
    }

    public List<Task> getTasks() {
        return tasks;
    }
    public List<Task> getTasksToDo() {
        return tasks.stream().filter((t)-> t.getStatus()==Status.TO_DO).toList();

    }

    public List<Task> getTasksInProgress() {
        return tasks.stream().filter((t)-> t.getStatus()==Status.IN_PROGRESS).toList();
    }

    public List<Task> getTasksDone() {
        return tasks.stream().filter((t)-> t.getStatus()==Status.DONE).toList();
    }

    public Integer addTask(String desc){
        var task =  new Task(desc);
        tasks.add(task);
        return task.getId();
    }

    public Task findById(int id) throws IllegalArgumentException{
        return tasks.stream().filter((t)-> t.getId()==id).findFirst().orElseThrow(()-> new IllegalArgumentException("ID not found."));
    }

    private int updateTaskStatus(int id, Status status){
        var originalTask = findById(id);
        originalTask.setStatus(status);
        return originalTask.getId();
    }
    public int updateTaskStatusInProgress(int id){
        return updateTaskStatus(id,Status.IN_PROGRESS);
    }
    public int updateTaskStatusDone(int id){
        return updateTaskStatus(id,Status.DONE);
    }
    public Integer updateTaskDescription(int id, String desc){
        var originalTask = findById(id);
        originalTask.setDescription(desc);
        return originalTask.getId();
    }

    public void removeTask(int id){
        tasks.remove(findById(id));
    }

    public List<Task> getTasksByStatus(String arg) throws IllegalArgumentException {
        List<Task> result;
        switch(arg.toLowerCase()){
            case "todo"-> result = getTasksToDo();
            case "in-progress"->result =  getTasksInProgress();
            case "done"-> result = getTasksDone();
            case "all"->result = getTasks();
            default -> throw  new IllegalArgumentException();
        }
        return result;
    }
}
