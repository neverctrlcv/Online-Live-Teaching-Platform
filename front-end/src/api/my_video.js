import request from "@/request/index.js";

export function hiddenVideos(id) {
    return request({
        url: '/video/hiddenVideo/'+id,
        method: 'put',
    })
}

export function shareVideos(id) {
    return request({
        url: '/video/shareVideo/'+id,
        method: 'put',
    })
}

export function deleteVideos(id) {
    return request({
        url: '/video/deleteVideo/'+id,
        method: 'delete',
    })
}

export function getVideos() {
    return request({
        url: '/video/getVideoByFounder/',
        method: 'get',
    })
}

export function addVideos(desc,videoLength,cover,video){
    return request({
        url: '/video/uploadVideo/',
        method: 'post',
        data:{
            desc,
            videoLength,
            cover,
            video
        },
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}