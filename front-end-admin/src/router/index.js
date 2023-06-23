import {createRouter, createWebHistory} from "vue-router";
import {ElMessage} from "element-plus";
import AdminLogin from "@/views/AdminLogin.vue";
import Console from "@/views/Console.vue";
import SystemManagement from "@/components/SystemManagement.vue";
import UserManagement from "@/components/UserManagement.vue";
import VideoManagement from "@/components/VideoManagement.vue";
import NotFound from "@/views/NotFound.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            name: 'login',
            path: '/',
            component: AdminLogin
        }, {
            name: 'console',
            path: '/console',
            component: Console,
            children: [
                {
                    name: 'system-management',
                    path: 'system-management',
                    component: SystemManagement
                },
                {
                    name: 'user-management',
                    path: 'user-management',
                    component: UserManagement
                },
                {
                    name: 'video-management',
                    path: 'video-management',
                    component: VideoManagement
                }
            ],
            beforeEnter: (to, from, next) => {
                let token = sessionStorage.getItem("token");
                if (token == null) {
                    ElMessage({message: '您还没有等录，请登录！', type: 'error'});
                    next("/");
                } else {
                    next();
                }
            }
        },
        {
            name: "not-found",
            path: '/404',
            component: NotFound
        },
        {
            path: '/:pathMatch(.*)*',
            redirect: '/404'
        }
    ]
});
export default router;