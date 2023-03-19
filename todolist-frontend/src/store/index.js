import taskReducer from "./tasks/task.reducer"
import { configureStore } from "@reduxjs/toolkit"

export const store = configureStore({
    reducer: { task: taskReducer },
  })

export default store