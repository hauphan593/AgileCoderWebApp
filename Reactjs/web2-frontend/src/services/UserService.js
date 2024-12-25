import axios from "axios";

const REST_API_BASE_URL = 'http://localhost:8090/api/users';

const REST_API_BASE_GENCODE = 'http://localhost:8090/api/gen-codes'

export const listUsers = () => axios.get(REST_API_BASE_URL);

export const createUser = (user) => axios.post(REST_API_BASE_URL, user);

export const getUser = (userId) => axios.get(REST_API_BASE_URL + '/' + userId);

export const updateUser = (userId, user) => axios.put(REST_API_BASE_URL + '/' + userId, user);

export const deleteUser = (userId) => axios.delete(REST_API_BASE_URL + '/' + userId);

export const loginUser = (user) => axios.post(REST_API_BASE_URL+"/login",user);

export const listProjects = () => axios.get(REST_API_BASE_GENCODE);