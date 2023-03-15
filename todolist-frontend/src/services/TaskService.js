import axios from "axios";

const GET_TASKS_URL = "http://localhost:8080/tasks";

class TaskService {

    async getTasks(){
        return await axios.get(GET_TASKS_URL);
    }
}

TaskService = new TaskService();

export default TaskService