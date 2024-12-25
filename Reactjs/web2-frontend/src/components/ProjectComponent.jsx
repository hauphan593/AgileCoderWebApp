import React, { useEffect, useState } from 'react'
import { createProject, getProject, updateProject, downloadProject } from '../services/ProjectService'
import { useNavigate, useParams } from 'react-router-dom';

const ProjectComponent = ({ onSidebarReset }) => {
  
  const [companyName, setCompanyName] = useState('')
  const [projectName, setProjectName] = useState('')
  const [projectRequire, setprojectRequire] = useState('')

  const [updateInput, setUpdateInput] = useState(false);
  const [createInput, setCreateInput] = useState(true);

  const [loading, setLoading] = useState(false); // State cho trạng thái loading
  const [success, setSuccess] = useState(false); // State cho trạng thái thành công
  const [error, setError] = useState(false);

  const {projectId} = useParams();

  const [errors, setErrors] = useState({
    companyName :'',
    projectName :'',
    projectRequire :''
  })

  const navigator = useNavigate();

  useEffect(() => {
    if(!projectId) {
      setUpdateInput(false);
      setCreateInput(true);
    }
    else if(projectId) {
      getProject(projectId).then((response) => {
        setprojectRequire(response.data.projectRequire);
        setCompanyName(response.data.companyName);
        setProjectName(response.data.projectName);
        
        setUpdateInput(true);
        setCreateInput(false);
      }) .catch(error => {
        console.error(error);
      })
    }
  }, [projectId])
  
  function saveUpdateProject(e) {
    e.preventDefault();

    const user = {projectRequire}
    console.log(user)

    if(validateForm()) {
          setLoading(true);
          setSuccess(false);
          createProject(user).then((response) => {
          console.log(response.data);
          const newProjectId = response.data.projectId;

          setLoading(false);
          setSuccess(true);
          // setTimeout(() => setSuccess(false),3000);

          if(onSidebarReset) {
            onSidebarReset();
          }

          navigator(`/edit-project/${Number(newProjectId)}`);
      }).catch(error => {
        console.error(error);
        setLoading(false);
        setError(true);
      })
       
    }

  }

  function validateForm(){
    let valid = true;
    const errorCopy = {...errors}

    if(projectRequire.trim()) {
      errorCopy.projectRequire = '';
    } else {
      errorCopy.projectRequire = 'project require required';
      valid = false;
    }

    setErrors(errorCopy);
    return valid;
  }

  function pageTitle() {
    if(projectId) {
    }
    else {
    }
  }

  function download(projectId) {
    setLoading(true);
    setSuccess(false);
    downloadProject(projectId).then((response) => {
      console.log(response.data)
      setLoading(false);
      setSuccess(true);
      // setTimeout(() => setSuccess(false),3000);
    })
  }

  function toUpdate() {

  }



  return (
    <div className='container'>
      {/* thong bao */}
        <div className='row vh-100 d-flex flex-column'>
          <div style={{position:'fixed, width:80%'}}> 
                {loading && <div className='alert alert-info alert-dismissible fade show '>Processing...  
                      <div class="spinner-border ms-auto" role="status" aria-hidden="true"></div>
                  </div>}
                  
                {success && <div className='alert alert-success alert-dismissible fade show'>Success!
                  <button type='button' className='btn-close' onClick={() => {setSuccess(false)}}></button>
                  </div>}
                {error && <div className='alert alert-danger alert-dismissible fade show'>
                  something wrong happen!
                  <button type='button' className='btn-close' onClick={() => {setError(false)}}></button>
                </div>}
          </div>
        {/* update */}
        <div >
          <form>
              {updateInput && 
                <div>
                  {/* <div className='input-group input-group mb-3' >
                    <span className='input-group-text' id = 'inputGroup-sizing-default'>Company Name:</span>
                    <input 
                      type='text' 
                      placeholder='Enter Company name' 
                      name='companyName' 
                      value={companyName} 
                      className={`form-control ${ errors.companyName ? 'is-invalid' : '' }`} 
                      onChange={(e) => setCompanyName(e.target.value)}>                    
                    </input>
                    { errors.companyName && <div className='invalid-feedback' >{errors.companyName}</div> }
                      
                  </div>
                  <div className='input-group input-group mb-3'>
                    <span className='input-group-text' id = 'inputGroup-sizing-default'>Project Name:</span>
                    <input 
                      type='text' 
                      placeholder='Enter project name' 
                      name='projectName' 
                      value={projectName} 
                      className={`form-control ${ errors.projectName ? 'is-invalid' : '' }`} 
                      onChange={(e) => setProjectName(e.target.value)}></input>
                      { errors.projectName && <div className='invalid-feedback' >{errors.projectName}</div> }
                  </div> */}

                  <div class="d-flex flex-row justify-content-end mb-4">
                    <div>
                      <p class=" p-2 me-3 mb-1 text-white rounded-3 bg-primary" style={{fontSize:"20px"}}>
                          <strong>Company name : </strong> {companyName || 'No company name'}
                          <br/>
                          <strong>Project name : </strong> {projectName || 'No project name'}
                          <br/>
                          <strong>Project promp : </strong> <p style={{maxWidth:'500px'}}>{projectRequire || 'No project Require'}</p>
                      </p>
                    </div>
                    
                  </div>
                  <div class="d-flex flex-row justify-content-start mb-4">
                      <i class="bi bi-person"></i>
                      <div>
                        {/* <p class=" h5 p-2 ms-3 mb-1 rounded-3 bg-secondary text-white"> */}
                          <button type='button' class="btn btn-outline-primary rounded-5" onClick={() => download(projectId) }>Download file</button>
                        {/* </p> */}
                      </div>
                  </div>
                </div>}
              
          </form>
        </div>
        {createInput && <div className='col-md-10 offset-md-o col mt-auto mb-4' style={{position: 'fixed', top: '80vh'  }}>
          {/* style="position: fixed;top: 585px;" */}
          <div className='card-body'>
            <form>
              <div className='form-floating mb-1 d-flex align-items-start'>
                  { errors.projectRequire && <div className='invalid-feedback' >{errors.projectRequire}</div> }
                <textarea 
                  placeholder='Enter project requirements' 
                  name='projectRequire' 
                  value={projectRequire}
                  className={`form-control ${ errors.projectRequire ? 'is-invalid' : '' }`} 
                  id='floatingTextarea'
                  onChange={(e) => setprojectRequire(e.target.value)}
                  readOnly = {!!projectId || loading}
                />
                <label for="floatingTextarea">Comments</label>
                <button className='btn btn-success mx-4' onClick={saveUpdateProject} disabled={loading}>
                  {loading ? 'Submitting...':'Submit'}
                </button>
              </div>
              
            </form>      
          </div>
        </div> }
        
      </div>
    </div>
  )
}

export default ProjectComponent