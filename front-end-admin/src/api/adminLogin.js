import request from "@/request/index.js";

export function login(username, password) {
    return request({
        url: '/admin/login/' + username + "/" + password,
        method: 'post'
    })
}