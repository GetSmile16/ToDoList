import React, { useState } from 'react'
import { InputGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import { Form } from 'react-bootstrap';
import { createTaskAction } from '../store/tasks/task.actions';
import { useDispatch } from 'react-redux';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';

function AddTask() {
    const [show, setShow] = useState(false);
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const today = new Date();
    const [date, setDate] = useState(new Date(today).toISOString().slice(0, 10));
    const [hours, setHours] = useState(0);
    const [mins, setMins] = useState(0);

    const handleClose = () => setShow(false);
    const handleShow = () => {
        setShow(true);
    }

    const handleName = (e) => setName(e.target.value)
    const handleDescription = (e) => setDescription(e.target.value)

    const handleDate = (e) => setDate(e.target.value)

    const handleHours = (e) => setHours(e.target.value)

    const handleMins = (e) => setMins(e.target.value)

    const dispatch = useDispatch();

    const save = () => {
        const curDate = new Date(date);
        void dispatch(createTaskAction({
            task: { name, description },
            time: {
                day: curDate.getDate(),
                month: curDate.getMonth() + 1,
                year: curDate.getFullYear(),
                hours: Number(hours),
                minutes: Number(mins)
            }
        }));
        setShow(false);
    }

    const disabled = new Date(date).getDate() < today.getDate();
    
    return (
        <>
            <InputGroup className='mb-3'>
                <Form.Control placeholder="Название задачи" value={name} onChange={handleName} />
                <Button id="button-addon2" variant="primary" onClick={handleShow}>
                    <FontAwesomeIcon icon={faPlus} />
                </Button>
            </InputGroup>
            <Modal show={show} onHide={handleClose}>
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
                        <Button variant="primary" disabled={disabled} onClick={save}>Сохранить</Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </Modal>
        </>
    )
}

export default AddTask