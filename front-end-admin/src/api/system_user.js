import request from "@/request/index.js";

export function getUserInfoByAdmin(index) {
    return request({
        url: 'user-admin/getUserInfoByAdmin/' + index,
        method: 'get'
    })
}

export function logoutUser(userId) {
    return request({
        url: 'user-admin/logoutUser/' + userId,
        method: 'delete'
    })
}