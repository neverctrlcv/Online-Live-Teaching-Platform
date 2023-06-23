<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="230px">
        <div class="title">后台管理系统</div>
        <div class="link">
          <div>
            <router-link to="/console/system-management" active-class="active">系统管理</router-link>
          </div>
          <div>
            <router-link to="/console/user-management" active-class="active">用户管理</router-link>
          </div>
          <div>
            <router-link to="/console/video-management" active-class="active">资源管理</router-link>
          </div>
        </div>
      </el-aside>
      <el-container>
        <el-header>
          <div class="portrait">
            <el-dropdown>
              <img :src="Admin" alt="admin">
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>退出</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main><router-view></router-view></el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import Admin from '@/assets/img/admin.png'
</script>

<style scoped>
.el-aside {
  background: #1e222d;
  height: 100vh;

}

.el-header {
  position: relative;
  height: 60px;
  background: #262f3e;
  width: calc(100vw - 230px);
}
.el-main{
  background: #eeeeee;
}

.title {
  line-height: 60px;
  text-align: center;
  font-size: 30px;
  color: #eeeeee;
  border-bottom: solid 1px #666666;
}

.link {
  margin-top: 30px;
}

.link > div {
  font-size: 20px;
  line-height: 60px;
  text-align: center;
}

.portrait {
  position: absolute;
  top: 50%;
  right: 50px;
  transform: translateY(-50%);
}

.portrait img {
  width: 40px;
  height: 40px;
}
.active{
  color: #79bbff;
}
</style>