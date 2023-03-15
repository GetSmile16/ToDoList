import React, { Component } from 'react'

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            
        }
    }

    render() {
        return (
            <div>
                <header>
                    <nav className='navbar navbar-expand-lg navbar-dark bg-dark'>
                        <div><p className='navbar-brand'>ToDoList</p></div>
                    </nav>
                </header>
            </div>
        )
    }
}

export default HeaderComponent