package util;

import model.Status;
import model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class JsonTaskMapper {


    public static String convertTaskToJson(Task task){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String taskFormatter ="{\"id\":\"%d\",\"status\":\"%s\",\"description\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}";
        return String.format(taskFormatter,
                task.getId(),
                task.getStatus(),
                task.getDescription(),
                task.getCreatedAt().format(formatter),
                task.getUpdatedAt().format(formatter));
    }
    public static Task convertJsonToTask(String json){
        String[] rawTask = json.replaceAll("\"","").replace("}","").split(",");
        String desc = null;
        Integer id = null;
        LocalDateTime created = null;
        LocalDateTime updated = null;
        Status status = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String key,value;
        int splitI;

        for(String r : rawTask){
            splitI =r.indexOf(":");
            key = r.substring(0,splitI);
            value = r.substring(splitI+1);
            switch(key){
                case "id"-> id =Integer.parseInt(value);
                case "description" -> desc = value;
                case "updatedAt"-> updated = LocalDateTime.parse(value,formatter);
                case "createdAt"-> created = LocalDateTime.parse(value,formatter);
                case "status" -> status = Status.valueOf(value);
            }

        }
        return new Task(updated,id,desc,status,created);
    };
    public static List<Task> convertJsonToTasks(String json){
        List<Task> list = new ArrayList<>();
        if(json.isBlank()){
            return list;
        }

        int latestStart = json.indexOf("\"latestId\"") + "\"latestId\":".length();
        int latestEnd = json.indexOf(",", latestStart);
        Task.latestId = Integer.valueOf(json.substring(latestStart, latestEnd).trim());

        int tasksStart = json.indexOf("\"tasks\"") + "\"tasks\":".length();
        int tasksEnd = json.lastIndexOf("]") + 1;
        String rawTasks = json.substring(tasksStart, tasksEnd).trim();
        String[] tasks = rawTasks.replace("[","").replace("]","").replaceAll("\\{","")
                .replaceAll("\n","").split("},");
        for (String task: tasks){
            list.add(convertJsonToTask(task));
        }
        return list ;
    }
    public static String convertTasksToJson(List<Task> tasks){
        if(tasks.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("{\"latestId\":").append(Task.latestId).append(",\n");
        sb.append("\"tasks\":[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(convertTaskToJson(tasks.get(i)));
            if(i < tasks.size() - 1){
                sb.append(",\n");
            }
        }
        sb.append("\n]}");

        return sb.toString();
    }
}
