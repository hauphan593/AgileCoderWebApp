import React from 'react'
import { listUsers } from '../services/UserService'
import { useNavigate } from 'react-router-dom'
const HeaderComponent = () => {

  const navigator = useNavigate();

  function listUsers() {
    navigator('/users')
  }

  return (
    <div>
        <header>
            <nav className='navbar bg-dark border-bottom border-body' data-bs-theme="dark">
                <a className="navbar-brand" onClick={listUsers} >A91l3 C0d3r</a>
            </nav>
        </header>
    </div>
  )
}

export default HeaderComponent
