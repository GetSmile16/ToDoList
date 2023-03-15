import React, { Component } from 'react'
import TaskService from '../services/TaskService'
import AddTask from '../UI/AddTask';

class ListTaskComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            tasks: []
        }
        this.addTask = this.addTask.bind(this);
    }

    componentDidMount() {
        TaskService.getTasks().then((res) => {
            this.setState({ tasks: res.data});
        });
    }

    addTask(){
        console.log(this.props);
        console.log(this.props.history);
        this.props.history.push('/add-task');
    }

    render() {
        return (
            <div>
                <h2 className="text-center"> Tasks List</h2>
                <div className="row">
                    {/* <button className='btn btn-primary' onClick={AddTask}>Создать задачу</button> */}
                </div>
                <div className='row'>
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Название задачи</th>
                                <th>Дата создания</th>
                                <th>Предполагаемая дата окончания</th>
                                <th>Статус</th>
                            </tr>
                            
                        </thead>
                        <tbody>
                            {
                                this.state.tasks.map(
                                    task => 
                                    <tr key= {task.id} >
                                        <td>{task.name}</td>
                                        <td>{task.dateOfCreated}</td>
                                        <td>{task.dateOfDeadline}</td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListTaskComponent