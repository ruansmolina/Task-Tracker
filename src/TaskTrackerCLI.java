

import model.Task;
import repository.TaskRepository;
import service.TaskService;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TaskTrackerCLI {

    private static final String PATH = "tasks.json";

    public static void main(String[] args) throws IOException {

        String help = """
                Usage:                      <command> [arguments...]
                * Add a new task            - add "description"
                * Update a task             - update ID "new description"
                * Delete a task             - delete ID
                * Mark task as in-progress  - mark-in-progress ID
                * Mark task as done         - mark-done ID
                * List task by status       - list [todo, in-progress, done]
                """;
        if(args.length< 1){
            System.out.println(help);
            return;
        }
        var taskService =new TaskService(new TaskRepository(PATH));
        taskService.start();
        int id;
        switch (args[0]){
            case "add": {
                if (args. length < 2 || args[1].isBlank()) {
                    System.err.println("Missing argument [description] - add \"description\"");
                    break;
                }
                id  = taskService.addTask(args[1]);
                System.out.printf("Task added successfully (ID: %d)", id);
                break;
            }
            case "update":{

                if (args. length < 3) {
                    System.err.println("Missing argument [ID] or [\"new description\"] - update ID \"new description\"");
                    break;
                }
                id = verifyId(args[1]);
                if(id==-1){
                    break;
                }
                id = taskService.updateTaskDescription(id,args[2]);
                System.out.printf("Task updated successfully (ID: %d)", id);
                break;
            }
            case "delete":{
                if (args. length < 2) {
                    System.err.println("Missing argument [ID] - delete ID");
                    break;
                }
                id = verifyId(args[1]);
                if(id==-1){
                    break;
                }
                taskService.removeTask(id);
                System.out.println("Task deleted successfully");
                break;


            }
            case "mark-in-progress":{
                if (args. length < 2) {
                    System.err.println("Missing argument [ID] - mark-in-progress ID");
                    break;
                }
                id = verifyId(args[1]);
                if(id==-1){
                    break;
                }
                id = taskService.updateTaskStatusInProgress(id);
                System.out.printf("Task status updated successfully (ID: %d)", id);
                break;
            }
            case "mark-done":{
                if (args. length < 2) {
                    System.err.println("Missing argument [ID] - mark-done ID");
                    break;
                }
                id = verifyId(args[1]);
                if(id==-1){
                    break;
                }
                id = taskService.updateTaskStatusDone(id);
                System.out.printf("Task status updated successfully (ID: %d)", id);
                break;
            }
            case "list":{
                List<Task> list;
                try {
                     list = taskService.getTasksByStatus(args.length < 2 ? "all" : args[1]);
                }catch (IllegalArgumentException e){
                    System.err.println("Invalid argument [status] - list [todo, in-progress, done]");
                    break;
                }
                printList(list);
                break;
            }
            case "--help":{
                System.out.println(help);
                break;
            }
            default:{
                System.err.println("Command invalid, use --help");
                break;
            }
        }

        taskService.finish();

    }
    public static int verifyId(String id){
        int value =-1;
        try{
            value = Integer.parseInt(id);
        }catch (NumberFormatException  e){
            System.err.printf("Invalid ID format: [%s], Only numbers are accepted.", id);
        }
        return value;
    }
    public static void printList(List<Task> list){
        String header = "| %-3s | %-30s | %-12s | %-16s | %-16s |";
        String formatedLine = "\n| %-3d | %-30s | %-12s | %-16s | %-16s |";
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        StringBuilder sb =  new StringBuilder(String
                .format(header,"ID","Description","Status","Created At", "Updated At"));
        list.forEach((t)-> sb.append(String.format(formatedLine,
                t.getId(),
                t.getDescription(),
                t.getStatus().getDescription(),
                t.getCreatedAt().format(formatter),
                t.getUpdatedAt().format(formatter))));
        System.out.println(sb.toString());
    }


}
