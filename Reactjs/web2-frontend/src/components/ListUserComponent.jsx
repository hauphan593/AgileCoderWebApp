import React, {useEffect, useState} from 'react'
import { deleteUser, listUsers } from '../services/UserService'
import { useNavigate } from 'react-router-dom'
const ListUserComponent = () => {

    const [users, setUsers] = useState([])

    const navigator = useNavigate();

    useEffect(() => {getAllUsers();}, [])

    function getAllUsers() {
        listUsers().then((response) => {
            setUsers(response.data);
        }).catch(error =>{
            console.error(error);
        })
    }

    function addNewUser() {
        navigator('/add-user')
    }

    function updateUser(userId) {
        navigator(`/edit-user/${userId}`)
    }

    function removeUser(userId) {
        console.log(userId);
        deleteUser(userId).then((response) => {
            getAllUsers();
        }).catch(error => {
            console.error(error);
        })
    }

  return (
    <div className='container '>
        <h2 className='text-center'>List of Users</h2>
        <button className='btn btn-primary mb-2' onClick={addNewUser}>Add user</button>
        <table className='table table-striped table-bordered '>
            <thead>
                <tr>
                    <th>User Id</th>
                    <th>User Name</th>
                    <th>User Password</th>
                    <th>User Email</th>
                    <th>Role id</th>
                    <th>Created at</th>
                    <th>Created by</th>
                    <th>Updated at</th>
                    <th>Updated by</th>
                </tr>
            </thead>
            <tbody>
                {
                    users.map(user =>
                        <tr key={user.userId}>
                            <td>{user.userId}</td>
                            <td>{user.username}</td>
                            <td>{user.password}</td>
                            <td>{user.email}</td>
                            <td>{user.roleId}</td>
                            <td>{user.createdAt}</td>
                            <td>{user.createdBy}</td>
                            <td>{user.updatedAt}</td>
                            <td>{user.updatedBy}</td>
                            <td>
                                <button className='btn btn-info' onClick={() => updateUser(user.userId)}>Update</button>
                                <button className='btn btn-danger' onClick={() => removeUser(user.userId) } style={{marginLeft:'10px'}} >Delete</button>
                            </td>
                        </tr>
                    )
                }
            </tbody>
        </table>
    </div>
  )
}

export default ListUserComponent