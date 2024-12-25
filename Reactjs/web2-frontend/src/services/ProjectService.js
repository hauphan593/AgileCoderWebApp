import axios from "axios";

const REST_API_BASE_GENCODE = 'http://localhost:8090/api/gen-codes'

export const listProjects = () => axios.get(REST_API_BASE_GENCODE);

export const createProject = (project) => axios.post(REST_API_BASE_GENCODE, project);

export const getProject = (projectId) => axios.get(REST_API_BASE_GENCODE + '/' + projectId);

export const updateProject = (projectId, project) => axios.put(REST_API_BASE_URL + '/' + projectId, project);

export const downloadProject = (projectId) => axios.get(REST_API_BASE_GENCODE + '/download/'+projectId );

// export const deleteUser = (userId) => axios.delete(REST_API_BASE_URL + '/' + userId);

// export const loginUser = (user) => axios.post(REST_API_BASE_URL+"/login",user);

