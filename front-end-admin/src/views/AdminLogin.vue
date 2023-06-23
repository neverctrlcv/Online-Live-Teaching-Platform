<template>
  <div class="container" :style="`background:url(${LoginBg}) no-repeat center center/cover;`">
    <div class="title">
      <div>在线直播教学平台</div>
      <div>后台管理系统</div>
    </div>
    <div class="login-form">
      <div class="form-row1">
        <input type="text" placeholder="请输入管理员账号" v-model="username">
      </div>
      <div class="form-row2">
        <input type="password" placeholder="请输入管理员密码" v-model="password">
      </div>
      <div class="form-row3">
        <button @click="adminLogin">登录</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import LoginBg from '@/assets/img/login-bg.png';
import {ref} from "vue";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
import {login} from "@/api/adminLogin";

const router = useRouter();
const username = ref("");
const password = ref("");

async function adminLogin() {
  await login(username.value, password.value).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: 'success'});
      sessionStorage.setItem("token", JSON.stringify(result.data.data));
      setTimeout(() => {
        router.push("/console/system-management");
      }, 1500)
    } else {
      ElMessage({message: result.data.message, type: 'error'});
    }
  })
}


</script>

<style scoped>
.container {
  width: 100vw;
  height: 100vh;
}

.title {
  position: absolute;
  top: 15%;
  left: 50%;
  transform: translateX(-50%);
  width: 500px;
}

.title > div:nth-child(1) {
  font-size: 50px;
  letter-spacing: 3px;
  text-align: center;
  color: #eeeeee;
}

.title > div:nth-child(2) {
  font-size: 45px;
  letter-spacing: 3px;
  text-align: center;
  color: #eeeeee;
  padding-top: 25px;
}

.login-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(150, 150, 150, 0.35);
  border-radius: 5px;
  padding-top: 60px;
}

.login-form h1 {
  font-size: 40px;
  text-align: center;
  margin: 30px 0px 50px;
  color: #eeeeee;
}

.login-form > div {
  margin-bottom: 35px;
  padding: 0px 35px;
}

.login-form > div input {
  width: 320px;
  height: 30px;
  font-size: 16px;
  border: solid 1px #eeeeee;
  outline: none;
  border-radius: 5px;
}

.login-form > div input:focus {
  border: solid 1px #79bbff;
}

.form-row3 button {
  width: 120px;
  height: 30px;
  color: #ffffff;
  background: #5096f5;
  border: none;
  border-radius: 5px;
}

.form-row3 {
  text-align: center;
}

.form-row3 button:hover {
  background: #79bbff;
  cursor: pointer;
}
</style>