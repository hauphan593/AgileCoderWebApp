import React, { useEffect, useState } from 'react'
import "../assets/css/sideBar.css";
import "../assets/javascripts/sideBar.js";
import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';
import { listProjects} from '../services/UserService.js';
import { useNavigate, useLocation } from 'react-router-dom';

const SideBarComponent = ({ onReset }) => {
  // Lay danh sach
  const navigator = useNavigate();
  const location = useLocation();

  function toUserList() {
    navigator('/users')
  }

  function createProject() {
    navigator('/create-project')
  }

  function updateProject(projectId) {
    navigator(`/edit-project/${projectId}`)
  }

  const [projects,setProjects] = useState([])

  useEffect(() => {getAllProject();
    if (onReset) {
      getAllProject();
    }

  }, [onReset]);

  function getAllProject() {
      listProjects().then((response) => {
          setProjects(response.data);
      }).catch(error => {
          console.error(error);
      })
  }

 

  return (
    <div className='sidebar-main-container'>
      <div className='sidebar-header'>
            Rikkei Agile Coder
      </div>
      <div className='sideBar-container'>
          {/* sidebar */}
          <Sidebar className='sidebars'>
            <Menu className='sidebar-menu' >

              <MenuItem className='main-menu' onClick={createProject} >
                プロジェクト作成
              </MenuItem>

              <SubMenu label="Project List" className='main-menu scrollable'>
                { projects.map(project => 
                  <ul className='no-bullets' key={project.projectId} >
                      <MenuItem className='sidebar-SubMenu' onClick= {() => updateProject(project.projectId) }><li >{project.projectName}</li></MenuItem>
                  </ul>
                ) } 
              </SubMenu>

              <MenuItem className='main-menu' onClick={toUserList}> 
                User's list
              </MenuItem>


            </Menu>
          </Sidebar>
      </div>
      <div className='sidebar-footer'>this is footer of sider bar</div>
  </div>

    
  )
}

export default SideBarComponent