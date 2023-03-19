import { createSlice } from "@reduxjs/toolkit";
import { getTasksAction, getTaskAction, getDailyTaskAction, editTaskAction, createTaskAction, resolveTaskAction } from "./task.actions";

const initialState = { tasks: null, task: null, error: null, randomTask: null};

export const sliceName = "task";

export const tasksSlice=createSlice({name: sliceName, initialState , reducers: {
    getTaskById: (state, {payload}) => {
        state.task=state?.tasks?.find((element) => element.id===payload)
    },
    setError: (state, {payload}) => {
        state.error = payload;
    },
    deleteTaskById: (state, {payload}) => {
        state.tasks = state.tasks.filter((element) => element.id !== payload );  
    }
}, extraReducers: (builder) => {
    builder
        .addCase(getTasksAction.fulfilled, (state, {payload}) => {
            state.tasks=payload;
        })
        .addCase(getTaskAction.fulfilled, (state, {payload}) => {
            state.task=payload;
        })
        .addCase(createTaskAction.fulfilled, (state, {payload}) => {
            state.tasks=[payload, ...state.tasks];
        })
        .addCase(editTaskAction.fulfilled, (state, {payload}) => {
            const index = state.tasks.findIndex((element) => element.id===payload.id);
            state.tasks.splice(index, 1);
            state.tasks=[payload, ...state.tasks];
        })
        .addCase(resolveTaskAction.fulfilled, (state, {payload}) => {
            const index = state.tasks.findIndex((element) => element.id===payload.id);
            state.tasks.splice(index, 1);
            state.tasks=[...state.tasks, payload];
        })
        .addCase(getDailyTaskAction.fulfilled, (state, {payload}) => {
            state.randomTask=payload;
        })
}})

export default tasksSlice.reducer
export const {getTaskById, setError, deleteTaskById, resolveTaskById}=tasksSlice.actions