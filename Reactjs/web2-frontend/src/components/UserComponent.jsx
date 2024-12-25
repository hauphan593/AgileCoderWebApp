import React, { useEffect, useState } from 'react'
import { createUser, getUser, updateUser } from '../services/UserService'
import { useNavigate, useParams } from 'react-router-dom';

const UserComponent = () => {
  
  const [username, setUserName] = useState('')
  const [password, setPassword] = useState('')
  const [email, setEmail] = useState('')

  const {userId} = useParams();

  const [errors, setErrors] = useState({
    username :'',
    password :'',
    email :''
  })

  const navigator = useNavigate();

  useEffect(() => {
    if(userId){
      getUser(userId).then((response) => {
        setUserName(response.data.username);
        setPassword(response.data.password);
        setEmail(response.data.email);
      }) .catch(error => {
        console.error(error);
      })
    }
  }, [userId])
  
  function saveNUpdateUser(e) {
    e.preventDefault();
    const user = {username, password, email}
      console.log(user)

    if(validateForm()) {

      if(userId) {
        updateUser(userId, user).then((response) => {
          console.log(response.data);
          navigator('/users');
        }).catch(error => {
          console.error(error);
        })
      } else {
          createUser(user).then((response) => {
          console.log(response.data);
          navigator('/users') 
      }).catch(error => {
        console.error(error);
      })

      }
       
    }

  }

  function validateForm(){
    let valid = true;
    const errorCopy = {...errors}
    if(username.trim()) {
      errorCopy.username = '';
    } else {
      errorCopy.username = 'Username required';
      valid = false;
    }

    if(password.trim()) {
      errorCopy.password = '';
    } else {
      errorCopy.password = 'Password required';
      valid = false;
    }

    if(email.trim()) {
      errorCopy.email = '';
    } else {
      errorCopy.email = 'Email required';
      valid = false;
    }

    setErrors(errorCopy);
    return valid;

  }

  function pageTitle() {
    if(userId) {
      return <h2 className='text-center'>Update User</h2>
    }
    else {
      return <h2 className='text-center'>Create User</h2>
    }
  }

  return (
    <div className='container'>
      <div className='row'>
        <div className='card col-md-6 offset-md-3 offset-md-3 ' >
          {
            pageTitle()
          }
          <div className='card-body'>

            <form>
              <div className='form-group mb-2'>
                <label className='form-label'>User Name:</label>
                <input 
                  type='text' 
                  placeholder='Enter username' 
                  name='username' 
                  value={username} 
                  className={`form-control ${ errors.username ? 'is-invalid' : '' }`} 
                  onChange={(e) => setUserName(e.target.value)}>                    
                </input>
                { errors.username && <div className='invalid-feedback' >{errors.username}</div> }
                  
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'> Password:</label>
                <input 
                  type='password' 
                  placeholder='Enter password' 
                  name='password' 
                  value={password} 
                  className={`form-control ${ errors.password ? 'is-invalid' : '' }`} 
                  onChange={(e) => setPassword(e.target.value)}></input>
                  { errors.password && <div className='invalid-feedback' >{errors.password}</div> }
              </div>

              <div className='form-group mb-2'>
                <label className='form-label'> E-mail:</label>
                <input 
                  type='email' 
                  placeholder='Enter E-mail' 
                  name='email' 
                  value={email} 
                  className={`form-control ${ errors.email ? 'is-invalid' : '' }`} 
                  onChange={(e) => setEmail(e.target.value)}></input>
                  { errors.email && <div className='invalid-feedback' >{errors.email}</div> }
              </div>
              <button className='btn btn-success' onClick={saveNUpdateUser}>Submit</button>
            </form>

          </div>
        </div>
      </div>
    </div>
  )
}

export default UserComponent