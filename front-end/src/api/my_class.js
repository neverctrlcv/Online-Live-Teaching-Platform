import request from "@/request/index.js";

export function createClass(className, slogan) {
    return request({
        url: '/class/create',
        method: 'post',
        data: {
            className: className,
            slogan: slogan
        }
    })
}

export function getClassIdByUser() {
    return request({
        url: '/class/getClassIdByUser',
        method: 'get'
    })
}

export function getClassAllInfo(classId) {
    return request({
        url: '/class/getClassAllInfo/' + classId,
        method: 'get'
    })
}

export function joinClass(inviteCode) {
    return request({
        url: '/class/join/' + inviteCode,
        method: 'post'
    })
}

export function dissolveClass(classId) {
    return request({
        url: '/class/dissolve/' + classId,
        method: 'delete'
    })
}

export function exitClass(classId) {
    return request({
        url: '/class/exit/' + classId,
        method: 'delete'
    })
}

export function getClassroomStatus(classId) {
    return request({
        url: '/class/getClassroomStatus/' + classId,
        method: 'get'
    })
}

export function downloadStatus(id){
    return request({
        url: '/class/downloadStatus/' + id,
        method: 'get'
    })
}