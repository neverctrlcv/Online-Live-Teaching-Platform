import axios from "axios";
import router from "@/router/index.js";
import store from "@/store/index.js";


const request = axios.create({
    baseURL: '/api',
    timeout: 12000,
    withCredentials: true
})
request.interceptors.request.use(config => {
    if (store.state.token) {
        config.headers['Authorization'] = store.state.token
    }
    return config;
},error => {
    console.log(error)
})

export default request;