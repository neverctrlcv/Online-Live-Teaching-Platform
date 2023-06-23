import request from "@/request/index.js";

export function getSmsCountAndRtcTime(){
    return request({
        url:'resource-admin/getSmsCountAndRtcTime',
        method:'get'
    })
}
export function getRTCUsage(day){
    return request({
        url:'resource-admin/getRTCUsage/'+day,
        method:'get'
    })
}
export function getLogInfo(day){
    return request({
        url:'resource-admin/getLogInfo/'+day,
        method:'get'
    })
}