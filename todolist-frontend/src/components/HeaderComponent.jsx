import React, { useState } from 'react'
import { Button } from 'react-bootstrap'
import { GetRandomTask } from '../UI/GetRandomTask';

export const HeaderComponent = () => {
    const [isActive, setIsActive] = useState(false);

    return (
        <header className="p-3 text-bg-dark rounded mb-2">
            <div className='container'>
                <div className='d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start'>
                    <GetRandomTask isActive={isActive} onClose={() => setIsActive(false)} />
                    <div className='nav justify-content-center mb-md-0 me-lg-auto'>
                        <h2 className='navbar-brand px-2'>ToDoList</h2>
                    </div>
                    <div className='text-end'>
                        <Button className='btn btn-light' onClick={() => setIsActive(true)}>Random Task</Button>
                    </div>
                </div>
            </div>
        </header>
    )
}