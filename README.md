
# Task Tracker

A Java command-line interface project for tracking and managing your tasks.
Challenge from [roadmap.sh](https://roadmap.sh/projects/task-tracker).




## How to Use

1. **Clone the repository:**

```bash
git clone https://github.com/ruansmolina/Task-Tracker.git
cd Task-Tracker
```


2. **Compile the source code:**
```bash
dir /s /B *.java > sources.txt
javac -d output_dir @sources.txt
```

**OR**

```bash
cd src
javac -d output_dir *.java
```


3. **Run the application:**
```bash
cd output_dir
java TaskTrackerCLI --help
```

# Usage
1. **Adding a new task:**
```bash
java TaskTrackerCLI add "Buy vegetables"
# Output: Task added successfully (ID: 1)
```
2. **Updating a task:**
```bash
java TaskTrackerCLI update 1 "Buy groceries and cook dinner"
# Output: Task updated successfully (ID: 1)
```
3. **Deleting a task:**
```bash
java TaskTrackerCLI delete 1
# Output: Task deleted successfully 
```
4. **Marking a task as in 'progress' or 'done':**
```bash 
java TaskTrackerCLI mark-in-progress 1
java TaskTrackerCLI mark-done 1
# Output: Task status updated successfully (ID: 1)
```
5. **Listing the tasks:**
```bash 
# Listing all tasks
java TaskTrackerCLI list
# Output:
| ID  | Description                    | Status       | Created At       | Updated At       |
| 1   | Buy vegetables                 | done         | 02-12-2025 15:35 | 02-12-2025 15:36 |
| 2   | Make a salad                   | in-progress  | 02-12-2025 15:35 | 02-12-2025 15:36 |
| 3   | Eat dinner                     | todo         | 02-12-2025 15:36 | 02-12-2025 15:36 |

# Listing the tasks by status
java TaskTrackerCLI list done
java TaskTrackerCLI list todo
java TaskTrackerCLI list in-progress

