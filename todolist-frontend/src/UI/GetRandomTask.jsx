import React, { useState, useEffect } from 'react'
import { InputGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import { Form } from 'react-bootstrap';
import { getDailyTaskAction, createTaskAction } from '../store/tasks/task.actions';
import { useDispatch, useSelector } from 'react-redux';

export function GetRandomTask({ isActive, onClose }) {
    const dispatch = useDispatch();

    const { randomTask } = useSelector(state => state.task)
    useEffect(() => {
        dispatch(getDailyTaskAction());
    }, [dispatch]);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");
    const [hours, setHours] = useState(0);
    const [mins, setMins] = useState(0);

    useEffect(() => {
        if (randomTask) {
            setName(randomTask.name);
            setDescription("");
            setDate(new Date().toISOString().slice(0, 10));
            setHours(0);
            setMins(0);
        }
    }, [randomTask]);

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

    const save = () => {
        const curDate = new Date(date);

        const task = {
            task: { name, description },
            time: {
                day: curDate.getDate(),
                month: curDate.getMonth() + 1,
                year: curDate.getFullYear(),
                hours: Number(hours),
                minutes: Number(mins)
            }
        };


        void dispatch(createTaskAction(task));
        onClose();
        
        dispatch(getDailyTaskAction());
    };

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