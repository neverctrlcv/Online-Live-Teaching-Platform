<template>
  <div class="common-layout">
    <el-container>
      <el-aside class="console-aside" width="230px">
        <div class="title">
          <img :src="Logo" alt="logo" width="40" height="40">
          <span>后台管理</span>
        </div>
        <nav>
          <el-menu style="border-right: none">
            <el-menu-item style="background: #1e222d;font-size: 17px">
              <router-link to="/student-visitor/our-class" active-class="active">
                <el-icon>
                  <ChatRound/>
                </el-icon>
                我的班级
              </router-link>
            </el-menu-item>
            <el-menu-item style="background: #1e222d;font-size: 17px">
              <router-link to="/student-visitor/setting" active-class="active">
                <el-icon>
                  <User/>
                </el-icon>
                个人中心
              </router-link>
            </el-menu-item>
          </el-menu>
        </nav>
      </el-aside>
      <el-container>
        <el-header class="console-header" height="75px">
          <div class="header-box">
            <div class="username">{{ store.state.nickname }}</div>
            <div class="login-info">
              <el-dropdown>
                <img :src="store.state.portrait" alt="me">
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="router.push('/')">首页</el-dropdown-item>
                    <el-dropdown-item @click="router.push('student-visitor/our-class')">控制台</el-dropdown-item>
                    <el-dropdown-item @click="userLogout">退出</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </el-header>
        <el-main class="console-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>

</template>

<script setup>

import Logo from '@/assets/logo/logo.png';
import {ChatRound, Document, Folder, Position, User, VideoPlay} from '@element-plus/icons-vue';
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {logout} from "@/api/user_login.js";
import {ElMessage} from "element-plus";

const store = useStore();
const router=useRouter();
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


.common-layout {
  min-width: 1100px;
}

.console-aside {
  background: #1e222d;
  height: 100vh;
}

.console-header {
  text-align: right;
  background: #262f3e;
}

.console-main {
  background: #EEEEEE;
  position: relative;
  overflow: auto;
  height: calc(100vh - 75px);
}



.console-main::-webkit-scrollbar {
  width: 8px;
}

.console-main::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}

.console-main::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: rgba(0, 0, 0, 0.1);
}

.title {
  border-bottom: 1px solid #EEEEEE;
  line-height: 75px;
  text-align: center;
}

.title img {
  vertical-align: middle;
}

.title span {
  font-size: 30px;
  padding: 0px 10px;
  color: #c6e2ff;
  line-height: 40px;
  vertical-align: middle;
}


.header-box {
  width: 90%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.login-info {
  margin-top: 8px;
}

.username {
  font-size: 25px;
  line-height: 75px;
  color: #EEEEEE;
}

.login-info img {
  width: 60px;
  height: 60px;
  border-radius: 30px;
}

.active {
  color: #79bbff;
}
</style>