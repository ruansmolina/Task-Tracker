package repository;

import model.Task;
import util.JsonTaskMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class TaskRepository {
    private final Path PATH;
    public TaskRepository(String path){
        this.PATH= Path.of(path);
    }
    public List<Task> getTasks() throws IOException {
        if(!Files.exists(PATH)) {
            return new ArrayList<>();
        }
        var json = Files.readString(PATH);
        return JsonTaskMapper.convertJsonToTasks(json);
        
    }
    public void saveTasks(List<Task> tasks) throws IOException {
        if(!Files.exists(PATH)){
            Files.createFile(PATH);
        }
        Files.writeString(PATH, JsonTaskMapper.convertTasksToJson(tasks));

    }

}
