import React from "react";
import AddTaskComponent from "./UI/AddTask";
import FooterComponent from "./components/FooterComponent";
import { HeaderComponent } from "./components/HeaderComponent";
import { ListTaskComponent } from "./components/ListTaskComponent";
import { Provider } from "react-redux";
import { Container } from "react-bootstrap"
import store from "./store";

function App() {

  return (
    <Container>
      <Provider store={store}>
        <HeaderComponent />
        <div className="container">
          <AddTaskComponent />
          <ListTaskComponent />
        </div>
        <FooterComponent />
      </Provider>
      </Container>
  );
}

export default App;
