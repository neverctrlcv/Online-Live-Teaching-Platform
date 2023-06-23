<template>
  <header>
    <div class="h-container">
      <div class="h-logo-nav">
        <div class="h-logo">
          <img :src="Logo" alt="logo" width="40" height="40">
        </div>
        <div class="h-nav">
          <router-link to="/" exact-active-class="active">首页</router-link>
          <router-link to="/video" exact-active-class="active">学习视频</router-link>
          <router-link to="/about-me" exact-active-class="active">关于我们</router-link>
        </div>
      </div>
      <div class="h-user">
        <div class="login-btn" v-show="!isLogin">
          <router-link to="/user/login/1" exact-active-class="active">登录|注册</router-link>
        </div>
        <div class="login-info" v-show="isLogin">
          <el-dropdown>
            <img :src="store.state.portrait" alt="me">
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item
                    @click="store.state.identity==3?router.push('/teacher/our-class'):router.push('/student-visitor/our-class')">
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item @click="userLogout">退出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>

      </div>
    </div>
  </header>
</template>

<script setup>
import Logo from '../assets/logo/logo.png';
import {ref, onMounted} from "vue";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {logout} from "@/api/user_login.js";
import {ElMessage} from "element-plus";

const store = useStore();
const router = useRouter();
const isLogin = ref(false);
onMounted(() => {
  if (store.state.token!=null&&store.state.token.length !== 0) {
    isLogin.value = true;
  }
})

async function userLogout() {
  await logout(store.state.phone).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: 'success'});
      store.commit("deleteAllData");
      router.push("/");
    }
  })
}

</script>

<style scoped>
header {
  position: fixed;
  z-index: 100;
  height: 65px;
  width: 100%;
  min-width: 1100px;
  background: rgba(0, 0, 0, 0.8);
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
}

.h-container {
  width: 85%;
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.h-logo-nav > div, .login-btn {
  display: inline-block;
  margin: 0px 10px;
  vertical-align: top;
  line-height: 65px;
}

.h-logo img {
  margin-top: 12px;
}

.h-nav a, .h-user a {
  margin: 0px 25px;
  text-decoration: none;
}

a:hover {
  cursor: pointer;
  color: #ffffff;
}

.active {
  color: #ffffff;
}

.login-info {
  margin-top: 12px;
  text-decoration: none;
}

.login-info img {
  width: 40px;
  height: 40px;
  border-radius: 20px;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>