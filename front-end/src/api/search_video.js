import request from "@/request/index.js";
export function search(start,keyword){
    return request({
        url:'/searchAndPlay/getVideo/'+start+'/'+keyword,
        method:'get'
    })
}
export function getVideoLink(videoId){
    return request({
        url:'/video/getVideoLink/'+videoId,
        method:'get'
    })
}