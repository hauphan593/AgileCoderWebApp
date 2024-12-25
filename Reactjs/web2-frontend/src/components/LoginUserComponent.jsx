import React, { useState } from 'react'
import { loginUser } from '../services/UserService';
import { useNavigate } from 'react-router-dom';

const LoginComponent = () => {
    const[username, setUserName] = useState('')
    const[password, setpassword] = useState('')

    const navigator = useNavigate();
    
    function checkUser(e) {
        e.preventDefault();

        const user = {username, password}
        console.log(user);
        loginUser(user).then((response) => {
                if (response.data!=null) {
                    console.log(response.data);
                    navigator('/users');
                }
                else {
                    console.log("invalid user or password")
                }
        })
    }

  return (
    <div className='container'>
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                <h2 className='text-center'> Login </h2>
                <div className='card-body'>
                    <form>
                        <div className='form-group mb-2'>
                            <label className='form-label'>User name:</label>
                            <input  type='text' 
                                    placeholder='User Name'
                                    name='userName' 
                                    value={username} 
                                    className='form-control' 
                                    onChange={(e) => setUserName(e.target.value)}>
                            </input>
                        </div>
                        
                        <div className='form-group mb-2'>
                            <label className='form-label'>Password:</label>
                            <input  type='password' 
                                    placeholder='Password'
                                    name='password' 
                                    value={password} 
                                    className='form-control' 
                                    onChange={(e) => setpassword(e.target.value)}>
                            </input>
                        </div>

                        <button className='btn btn-success' onClick={checkUser}>Login</button>

                    </form>

                </div>
            </div>
        </div>
    </div>
  )
}

export default LoginComponent