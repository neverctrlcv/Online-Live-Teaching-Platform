import request from "@/request/index.js";

export function getVideoInfoByAdmin(index) {
    return request({
        url: '/video-admin/getVideoInfoByAdmin/' + index,
        method: 'get'
    })
}


export function getAllVideoCount() {
    return request({
        url: '/video-admin/getAllVideoCount',
        method: 'get'
    })
}

export function deleteVideoByAdmin(videoId) {
    return request({
        url: '/video-admin/deleteVideoByAdmin/' + videoId,
        method: 'delete'
    })
}

