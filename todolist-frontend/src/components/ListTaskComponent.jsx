import React, { useEffect, useState } from 'react'
import { Button } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux'
import { deleteTask } from '../API';
import { deleteTaskById } from '../store/tasks';
import { getTasksAction, resolveTaskAction } from '../store/tasks/task.actions';
import { EditTask } from '../UI/EditTask';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCheck, faXmark, faPen } from '@fortawesome/free-solid-svg-icons';

export const ListTaskComponent = () => {
    const dispatch = useDispatch();
    const { tasks } = useSelector(state => state.task)

    const [isActive, setIsActive] = useState(false);
    const [id, setId] = useState(null);

    useEffect(() => {
        dispatch(getTasksAction())
    }, [dispatch]);

    return (
        <div>
            <EditTask isActive={isActive} onClose={() => { setIsActive(false) }} id={id} />
            <h2 className="text-center"> Задачи</h2>
            <div className='row'>
                <table className="table table-borderless table-hover">
                    <thead>
                        <tr>
                            <th scope='col'>Название задачи</th>
                            <th scope='col'>Дата создания</th>
                            <th scope='col'>Предполагаемая дата окончания</th>
                            <th scope='col'>Статус</th>
                            <th scope='col'></th>
                        </tr>

                    </thead>
                    <tbody>
                        {
                            tasks?.map(
                                task =>
                                    <tr key={task.id} className={
                                        task?.status === 'FAILED' ? 'table-danger'
                                            : task?.status === 'RESOLVED' ? 'table-success'
                                                : ''
                                    }>
                                        <td scope='row'>{task.name}</td>
                                        <td>{task.dateOfCreated}</td>
                                        <td>{task.dateOfDeadline}</td>
                                        <td>{task.status}</td>
                                        <td>
                                            <Button className="mr-1" disabled={task?.status === 'FAILED' || task?.status === 'RESOLVED'} onClick={() => {
                                                setIsActive(true);
                                                setId(task.id);
                                            }}>
                                                <FontAwesomeIcon icon={faPen} />
                                            </Button><Button className="mr-1" onClick={() => {
                                                dispatch(deleteTaskById(task.id));
                                                deleteTask(task.id);
                                            }}> <FontAwesomeIcon icon={faXmark} /> </Button>
                                            <Button className="btn-primary mr-1" disabled={task?.status === 'FAILED' || task?.status === 'RESOLVED'} onClick={() => {
                                                dispatch(resolveTaskAction(task.id));
                                            }}> <FontAwesomeIcon icon={faCheck} /> </Button>
                                        </td>
                                    </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}