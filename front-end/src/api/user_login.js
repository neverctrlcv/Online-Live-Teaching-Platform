import request from "@/request/index.js";

export function getCaptchaInLogin(phone) {
    return request({
        url: '/user/getCaptcha/login/' + phone,
        method: 'get'
    })
}
export function getCaptchaInRegister(phone) {
    return request({
        url: '/user/getCaptcha/register/' + phone,
        method: 'get'
    })
}

export function passwordLogin(phone, password) {
    return request({
        url: '/user/userLogin/password/'+phone+'/'+password,
        method: 'post',
    })
}

export function captchaLogin(phone, captcha) {
    return request({
        url: '/user/userLogin/captcha/'+phone+'/'+captcha,
        method: 'post',
    })
}

export function register(phone, captcha, password) {
    return request({
        url: '/user/userRegister/',
        method: 'post',
        data: {
            phone,
            captcha,
            password
        }
    })
}
export function logout(phone) {
    return request({
        url: '/user/userLogout/'+phone,
        method: 'post',
    })
}