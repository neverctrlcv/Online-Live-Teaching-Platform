import {createRouter, createWebHistory, useRouter} from "vue-router";
import store from "@/store/index.js";
import {ElMessage} from "element-plus";

import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import PasswordLogin from "../components/login/PasswordLogin.vue";
import CaptchaLogin from "../components/login/CaptchaLogin.vue";
import Register from "../components/register/Register.vue";
import Classroom from "../views/Classroom.vue";
import About from "@/views/About.vue";
import Video from "@/views/Video.vue";
import Authentication from "@/views/Authentication.vue";
import StudentCenter from '@/components/sudent/index.vue';
import StudentClass from "@/components/sudent/component/OurClass.vue";
import StudentSetting from '@/components/sudent/component/Setting.vue'
import TeacherCenter from '@/components/teacher/index.vue';
import TeacherResource from '@/components/teacher/component/MyResource.vue';
import TeacherClass from '@/components/teacher/component/OurClass.vue';
import TeacherSetting from '@/components/teacher/component/Setting.vue';
import VideoPage from "@/components/VideoPage.vue";
import NotFound from "@/views/NotFound.vue";

function intercept(to, from, next) {
    const token = store.state.token;
    if (token === "" || token == null) {
        ElMessage({message: '您还没有登录，请登录!', type: 'error'});
        next("/user/login/1");
    } else {
        next();
    }
}

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            name: 'home',
            path: '/',
            component: Home,
            meta: {
                title: '首页'
            }
        },
        {
            name: 'authentication',
            path: '/authentication',
            component: Authentication,
            meta: {
                title: '身份验证'
            },
            beforeEnter: (to, from, next) => {
                intercept(to, from, next);
            }
        },
        {
            name: 'login',
            path: '/user',
            component: Login,
            meta: {
                title: '登录'
            },
            children: [
                {
                    name: 'password',
                    path: 'login/1',
                    component: PasswordLogin
                },
                {
                    name: 'captcha',
                    path: 'login/2',
                    component: CaptchaLogin
                }, {
                    name: 'register',
                    path: 'register',
                    component: Register
                }
            ]
        },
        {
            name: 'student',
            path: '/student-visitor',
            component: StudentCenter,
            meta: {
                title: '个人中心',
            },
            children: [
                {
                    name: 'our-class-s',
                    path: 'our-class',
                    component: StudentClass,
                },
                {
                    name: 'setting-s',
                    path: 'setting',
                    component: StudentSetting,
                }
            ],
            beforeEnter: (to, from, next) => {
                intercept(to, from, next);
            }
        },
        {
            name: 'teacher',
            path: '/teacher',
            component: TeacherCenter,
            meta: {
                title: '个人中心',
            },
            children: [
                {
                    name: 'our-class-t',
                    path: 'our-class',
                    component: TeacherClass,
                },
                {
                    name: 'setting-t',
                    path: 'setting',
                    component: TeacherSetting,
                },
                {
                    name: 'my-resource',
                    path: 'my-resource',
                    component: TeacherResource,
                }
            ],
            beforeEnter: (to, from, next) => {
                intercept(to, from, next);
            }
        },

        {
            name: 'classroom',
            path: '/classroom',
            component: Classroom,
            meta: {
                title: '直播间'
            },
            beforeEnter: (to, from, next) => {
                intercept(to, from, next);
            }
        },
        {
            name: 'about',
            path: '/about-me',
            component: About,
            meta: {
                title: '关于我们'
            },
        },
        {
            name: 'video',
            path: '/video',
            component: Video,
            meta: {
                title: '视频'
            }
        },
        {
            name: 'video-page',
            path: '/video-page',
            component: VideoPage,
        },
        {
            name:'not-found',
            path:'/404',
            component:NotFound,
            meta:{
                title:'404'
            }
        },
        {
            path: '/:pathMatch(.*)*',
            redirect: '/404'
        }
    ]
})

router.afterEach((to, from, failure) => {
    document.title = to.meta.title;
})

export default router;