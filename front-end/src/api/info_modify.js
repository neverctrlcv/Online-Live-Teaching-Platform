import request from "@/request/index.js";

export function personalInfoModify( nickname, sex, age, signature) {
    return request({
        url: '/userInfo/updateUserInfo',
        method: 'put',
        data: {
            nickname,
            sex,
            age,
            signature
        }
    })
}

export function infoVerify( jobId, identity, name) {
    return request({
        url: '/userInfo/updateUserIdentityInfo',
        method: 'put',
        data: {
            jobId,
            identity,
            name
        }
    })
}

export function uploadImage(file) {
    return request({
        url: '/userInfo/updateAvatar',
        method: 'put',
        data: {
            file: file
        },
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
