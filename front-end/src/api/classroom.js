import request from "@/request/index.js";

export function getClassMember(classId){
    return request({
        url:'/classroom/getClassMember/'+classId,
        method:'get'
    })
}

export function recordStatus(userId,classTId){
    return request({
        url:'/classroom/recordStatus/'+userId+"/"+classTId,
        method:'post'
    })
}
export function recordClassStatus(classId,identity){
    return request({
        url:'/classroom/recordClassStatus/'+classId+"/"+identity,
        method:'post'
    })
}