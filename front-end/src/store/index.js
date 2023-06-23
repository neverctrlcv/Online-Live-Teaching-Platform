import {createStore} from "vuex";

export default createStore({
    state: {
        identity: "",
        id: '',
        age: '',
        sex: '',
        nickname: '',
        signature: '',
        phone: '',
        portrait: '',
        registerTime: '',
        tempPortrait: '',
        jobId: '',
        name: '',
        token: ''//用户登录的token串，将由后端返回
    },
    mutations: {
        setInfo(state, data) {
            state.identity = data.identity;
            state.id = data.userId;
            state.age = data.age;
            state.sex = data.sex;
            state.nickname = data.nickname;
            state.signature = data.signature;
            state.phone = data.phone;
            state.portrait = data.avatar;
            state.registerTime = data.registerTime;
            state.tempPortrait = data.portrait;
            state.jobId = data.jobId;
            state.name = data.name;
            state.token = data.token;
        },
        modifyInfo1(state, personInfo) {
            state.age = personInfo.age;
            state.nickname = personInfo.nickname;
            state.sex = personInfo.sex;
            state.signature = personInfo.signature;
        },
        verifyInfo(state, identityInfo) {
            state.jobId = identityInfo.jobId;
            state.name = identityInfo.name;
            state.identity = identityInfo.identity;
        },
        modifyPortrait(state, portrait) {
            state.portrait = portrait.data;
        },
        deleteAllData(state) {
            state.identity = null;
            state.id = null;
            state.age = null;
            state.sex = null;
            state.nickname = null;
            state.signature = null;
            state.phone = null;
            state.portrait = null;
            state.registerTime = null;
            state.tempPortrait = null;
            state.jobId = null;
            state.name = null;
            state.token = null;
        }

    }

})
