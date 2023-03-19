import { createAsyncThunk } from "@reduxjs/toolkit";
import { getTasks, getTask, createTask, editTask, getDailyTask } from "../../API";
import { sliceName, setError } from "./task.reducer";

export const getTasksAction=createAsyncThunk(`${sliceName}/getTasksAction`, async () => {
    return (await getTasks()).data;
})

export const getTaskAction=createAsyncThunk(`${sliceName}/getTaskAction`, async (id) => {
    return (await getTask(id)).data;
})

export const createTaskAction=createAsyncThunk(`${sliceName}/createTaskAction`, async (item) => {
    return (await createTask(item)).data;
})

export const editTaskAction=createAsyncThunk(`${sliceName}/editTaskAction`, async ({id, item}, {dispatch}) => {
    try {
        return (await editTask(id, item)).data;
    } catch (error) {
        dispatch(setError(error?.message));
        throw new Error(error?.message);
    }
})

export const getDailyTaskAction=createAsyncThunk(`${sliceName}/getDailyTaskAction`, async () => {
    return (await getDailyTask()).data;
})