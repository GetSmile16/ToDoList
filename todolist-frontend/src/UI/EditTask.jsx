import React, { useState, useEffect } from 'react'
import { InputGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import { Form } from 'react-bootstrap';
import { editTaskAction } from '../store/tasks/task.actions';
import { useDispatch, useSelector } from 'react-redux';
import { getTaskById } from '../store/tasks/task.reducer';

export function EditTask({ isActive, onClose, id }) {
    const dispatch = useDispatch();

    const { task, error } = useSelector(state => state.task)
    useEffect(() => {
        dispatch(getTaskById(id))
    }, [dispatch, id]);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");
    const [hours, setHours] = useState(0);
    const [mins, setMins] = useState(0);

    useEffect(() => {
        if (task) {
            setName(task.name);
            setDescription(task.description);
            setDate(task.dateOfDeadline.slice(0, 10));
            setHours(new Date(task.dateOfDeadline).getHours());
            setMins(new Date(task.dateOfDeadline).getMinutes());
        }
    }, [task]);

    const handleName = (e) => setName(e.target.value)
    const handleDescription = (e) => setDescription(e.target.value)

    const handleDate = (e) => setDate(e.target.value)

    const handleHours = (e) => {
        var hours = e.target.value;
        if (hours > 23) {
            hours = 23;
        }
        setHours(hours);
    }

    const handleMins = (e) => {
        var mins = e.target.value;
        if (mins > 59) {
            mins = 59;
        }
        setMins(mins);
    }

    const save = (status) => {
        const curDate = new Date(date);

        const task = {
            id, item: {
                task: { name, description },
                time: {
                    day: curDate.getDate(),
                    month: curDate.getMonth() + 1,
                    year: curDate.getFullYear(),
                    hours: Number(hours),
                    minutes: Number(mins)
                }
            }
        };

        void dispatch(editTaskAction(task));
        onClose();

        if (task?.status === "FAILED") {
            alert("Задача провалена, изменить не получится");
        }
        if (task?.status === "RESOLVED") {
            alert("Задача решена, изменить не получится");
        }
    }

    return (
        <Modal show={isActive} onHide={onClose}>
            <Modal.Dialog>
                <Modal.Header closeButton>
                    <Modal.Title>Создание задачи</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <InputGroup className='mb-3'>
                        <Form.Control
                            placeholder="Название задачи" value={name} onChange={handleName}
                        />
                    </InputGroup>
                    <InputGroup className='mb-3'>
                        <Form.Control as="textarea"
                            placeholder="Описание задачи" value={description} onChange={handleDescription}
                        />
                    </InputGroup>
                    <InputGroup className='mb-3'>
                        {/* <Form.Label>Предпологаемая дата окончания</Form.Label> */}
                        <Form.Control type="date" value={date} onChange={handleDate} />
                    </InputGroup>
                    <InputGroup className='mb-3'>
                        <Form.Control type='number' placeholder='Часы' min={0} max={23} value={hours} onChange={handleHours}></Form.Control>
                        <Form.Control type='number' placeholder='Минуты' min={0} max={59} value={mins} onChange={handleMins}></Form.Control>
                    </InputGroup>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={save}>Сохранить</Button>
                </Modal.Footer>
            </Modal.Dialog>
        </Modal>
    )
}