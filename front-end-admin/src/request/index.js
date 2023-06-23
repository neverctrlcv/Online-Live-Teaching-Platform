import axios from "axios";

const request = axios.create({
    baseURL: '/api',
    timeout: 3000,
    withCredentials: true
})
export default request;