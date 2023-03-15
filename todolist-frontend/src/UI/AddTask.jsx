import React, { useState } from 'react'
import { InputGroup } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Modal from 'react-bootstrap/Modal'
import { Form } from 'react-bootstrap';
import TimeInput from 'react-time-input';

function AddTask() {
    const [show, setShow] = useState(false);

    const [name, setName] = useState("");

    const [date, setDate] = useState(new Date());

    const [hours, setHours] = useState(0);
    const [mins, setMins] = useState(0);

    const handleClose = () => setShow(false);
    const handleShow = () => {
        console.log(name)
        setShow(true);
    }

    const handleName = (e) => setName(e.target.value)

    const handleDate = (e) => setDate(e.target.value)

    const handleHours = (e) => setHours(e.target.value)

    const handleMins = (e) => setMins(e.target.value)

    const save = () => {
        console.log(date);
        console.log(date.getFullYear());
        console.log(date.getMonth());
        console.log(date.getDate());
    }

    return (
        <>
            <InputGroup className='mb-3'>
                <Form.Control placeholder="Название задачи" value={name} onChange={handleName}/>
                <Button id="button-addon2" variant="primary" onClick={handleShow}>
                    Создать задачу
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
                                placeholder="Название задачи" defaultValue={name}
                            />
                        </InputGroup>
                        <InputGroup className='mb-3'>
                            <Form.Control
                                placeholder="Описание задачи"
                            />
                        </InputGroup>
                        <InputGroup className='mb-3'>
                            {/* <Form.Label>Предпологаемая дата окончания</Form.Label> */}
                            <Form.Control type="date"  value={date} onChange={handleDate}/>
                        </InputGroup>
                        <InputGroup className='mb-3'>
                            <Form.Control type='number' placeholder='Часы' min={0} max={24} value={hours} onChange={handleHours}></Form.Control>
                            <Form.Control type='number' placeholder='Минуты' min={0} max={60} value={mins} onChange={handleMins}></Form.Control>
                        </InputGroup>
                    </Modal.Body>
                     <Modal.Footer>
                        <Button variant="primary" onClick={save}>Сохранить</Button>
                    </Modal.Footer>
                </Modal.Dialog>
            </Modal>
        </>
    )
}

export default AddTask