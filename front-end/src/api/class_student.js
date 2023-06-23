import request from "@/request/index.js";

export function joinClass(invitationCode, userId) {
    return request({
        url: '/student/joinClass',
        method: 'post',
        data: {
            invitationCode,
            userId
        }
    })
}

export function exitClass(classId, userId) {
    return request({
        url: '/student/exitClass',
        method: 'delete',
        data: {
            classId,
            userId
        }
    })
}
export function getClassInfoByStudent(userId){
    return request({
        url: '/student/getClassInfo/'+userId,
        method: 'get',
    })
}