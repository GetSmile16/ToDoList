import axios from "axios"

const TASKS_URL = "http://localhost:8080/tasks";
const DAY_URL = "http://localhost:8080/day";

export const getTasks = async () => {
    return axios.get(TASKS_URL);
}

export const createTask = async (item) => {
    return axios.post(`${TASKS_URL}/create`, item, {
        headers: {
          'Access-Control-Allow-Origin' : '*',
          'Access-Control-Allow-Methods':'GET,PUT,POST,DELETE,PATCH,OPTIONS'
        }} 
      );
}

export const editTask = async (id, item) => {
    return axios.put(`${TASKS_URL}/${id}`, item);
}

export const deleteTask =  async (id) => {
    axios.delete(`${TASKS_URL}/${id}`);
}

export const resolveTask = async (id) => {
    return axios.put(`${TASKS_URL}/resolve/${id}`);
}

export const getTask = async (id) => {
    return axios.get(`${TASKS_URL}/${id}`);
}

export const getDailyTask = async () => {
    return axios.get(DAY_URL);
}