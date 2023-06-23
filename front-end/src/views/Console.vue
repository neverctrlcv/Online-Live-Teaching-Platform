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
            <el-menu-item>
              <router-link to="/console/my-class" active-class="active"><el-icon><ChatRound/></el-icon>我的班级</router-link>
            </el-menu-item>
            <el-menu-item>
              <router-link to="/console/homework" active-class="active"><el-icon><Document/></el-icon>作业管理</router-link>
            </el-menu-item>
            <el-sub-menu>
              <template #title><a><el-icon><Position/></el-icon>资源管理</a></template>
              <el-menu-item>
                <router-link to="/console/resource/my-material" active-class="active"><el-icon><Folder/></el-icon>我的资料</router-link>
              </el-menu-item>
              <el-menu-item>
                <router-link to="/console/resource/my-video" active-class="active"><el-icon><VideoPlay/></el-icon>我的视频</router-link>
              </el-menu-item>
            </el-sub-menu>
            <el-menu-item>
              <router-link to="/console/setting" active-class="active"><el-icon><User/></el-icon>个人中心</router-link>
            </el-menu-item>
          </el-menu>
        </nav>
      </el-aside>
      <el-container>
        <el-header class="console-header" height="75px">
          <div class="header-box">
            <div class="username">11903990702</div>
            <div class="login-info">
              <el-dropdown>
                <img :src="store.state.portrait" alt="me">
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item>控制台</el-dropdown-item>
                    <el-dropdown-item>退出</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </el-header>
        <el-main class="console-main">
          <div class="main-box">
            <router-view></router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>

</template>

<script>

import Logo from '@/assets/logo/logo.png';
import {ChatRound, Document, Folder, Position, User, VideoPlay} from '@element-plus/icons-vue';
import {useStore} from "vuex";

export default {
  name: "Console",
  components: {User, VideoPlay, Folder, Document, ChatRound, Position},
  setup() {
    const store=useStore();
    return {
      Logo,
      store
    }
  }
}
</script>

<style scoped>


.common-layout {
  min-width: 1400px;
}

.console-aside {
  background: #ffffff;
  height: 100vh;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
}

.console-header {
  text-align: right;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
}

.console-main {
  /*background: #79aaff;*/
  position: relative;
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

/*nav ul {*/
/*  margin: 20px 0px 0px;*/
/*  padding: 0;*/
/*}*/

/*nav li {*/
/*  list-style: none;*/
/*  text-align: center;*/
/*  font-size: 19px;*/
/*  line-height: 60px;*/
/*}*/

/*nav a {*/
/*  text-decoration: none;*/
/*}*/

/*nav a:hover {*/
/*  cursor: pointer;*/
/*  color: #409EFF;*/
/*}*/

/*.active {*/
/*  color: #409EFF;*/
/*  font-size: 20px;*/
/*}*/

.main-box {
  position: absolute;
  width: 90%;
  height: 90%;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.3);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 6px;
  overflow: auto;
}

.main-box::-webkit-scrollbar {
  width: 8px;
}

.main-box::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}

.main-box::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: rgba(0, 0, 0, 0.1);
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