import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { createBrowserHistory } from "history"
import AddTaskComponent from "./UI/AddTask";
import FooterComponent from "./components/FooterComponent";
import HeaderComponent from "./components/HeaderComponent";
import ListTaskComponent from "./components/ListTaskComponent";

function App() {
  const history = createBrowserHistory();

  return (
    <div>
      <Router>
        <HeaderComponent />
        <div className="container">
          <AddTaskComponent/>
          <Routes>
            <Route path="/" exact element={<ListTaskComponent history={history} />} />
            <Route path="/tasks" element={<ListTaskComponent history={history} />} />
          </Routes>
        </div>
        <FooterComponent />
      </Router>
    </div>
  );
}

export default App;
