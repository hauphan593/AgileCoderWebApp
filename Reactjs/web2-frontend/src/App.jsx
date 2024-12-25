
import './App.css'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListUserComponent from './components/ListUserComponent'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import UserComponent from './components/UserComponent'
import LoginComponent from './components/LoginUserComponent'
import SideBarComponent from './components/SideBarComponent'
import ProjectComponent from './components/ProjectComponent'
import React, { useState } from 'react'

const hideSideBarPages = ['/'];
function App() {
  
  const [resetSidebarFlag, setResetSiderFlag] = useState(false);
  const handleSidebarReset = () => {
    setResetSiderFlag((prev) => !prev);
  }

  
  return (
    <>
      <BrowserRouter>
        <div className={`app-layout ${hideSideBarPages.includes(location.pathname) ? 'no-sidebar' : ''}`}>
          <div className='sidebar-container'>
            {!hideSideBarPages.includes(location.pathname) && (<div className='sidebar-contain'>
              {/* Sidebar (Will be visible on all routes) */}
              <SideBarComponent onReset={resetSidebarFlag}/>
              {/* Main content area */}
            </div>)} 
          </div>
          <div className='main-contain'>
            <HeaderComponent/>
            <Routes>
                {/* //http:localhost:3000 */}
                <Route path='/' element = {<LoginComponent/>} ></Route>

                {/* //http:localhost:3000/users */}            
                <Route path='/users' element = {<ListUserComponent/>} ></Route>

                {/* //http:localhost:3000/add-user */}            
                <Route path='/add-user' element = {<UserComponent/>} ></Route>

                {/* //http:localhost:3000/update-user */}            
                <Route path='/edit-user/:userId' element = {<UserComponent/>} ></Route>

                {/* //http:localhost:3000/create-project */}
                <Route path ='/create-project' element = {<ProjectComponent onSidebarReset={handleSidebarReset} />}></Route>

                {/* //http:localhost:3000/updated-project */}
                <Route path ='/edit-project/:projectId' element = {<ProjectComponent/>}></Route>

            </Routes> 
            <FooterComponent/>
          </div>
          
        </div>
      </BrowserRouter>
        
    </>
  )
}

export default App
