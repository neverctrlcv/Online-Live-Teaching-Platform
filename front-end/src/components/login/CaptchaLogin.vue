<template>
  <div class="login-box-type1">
    <span>验证码登录</span>
    <div class="login-row1">
      <span>手机号码：</span>
      <input type="text" inputmode="tel" placeholder="请输入手机号码" v-model="username">
      <p>{{ usernameResult }}</p>
    </div>
    <div class="login-row2">
      <span>验证码：</span>
      <div>
        <input type="text" placeholder="请输入验证码" v-model="captcha">
        <button @click="getCaptchaMethod">{{ captchaButtonText }}</button>
      </div>
    </div>
    <div class="login-row3">
      <span @click="toRegister">注册账号</span>
      <span @click="toPasswordLogin">密码登录</span>
    </div>
    <div class="login-row4">
      <button @click="login">登录</button>
    </div>
  </div>
</template>

<script setup>
import {ref, watch} from "vue";
import {useRouter} from "vue-router";
import {getCaptchaInLogin, captchaLogin} from "@/api/user_login.js";
import {ElMessage} from "element-plus";
import {useStore} from "vuex";


const router = useRouter();
const username = ref("");
const captcha = ref("");
const usernameResult = ref("");
const phoneRegExp = /^1[3456789]\d{9}$/;

watch(username, (newValue, oldValue) => {
  if (!phoneRegExp.test(newValue)) {
    usernameResult.value = "请输入正确的手机号码！";
  } else {
    usernameResult.value = "";
  }
})

function toPasswordLogin() {
  router.push("/user/login/1");
}

function toRegister() {
  router.push("/user/register");
}

const captchaButtonText = ref("验证码");
let time = 60;

async function getCaptchaMethod() {
  if (!phoneRegExp.test(username.value)) {
    ElMessage({message: "请输入正确的手机号码", type: "error"})
    return;
  }
  if (captchaButtonText.value === "验证码") {
    await getCaptchaInLogin(username.value).then((result) => {
      if (result.data.code === 200) {
        ElMessage({message: result.data.message, type: "success"})
        const timer = setInterval(() => {
          if (time > 0) {
            captchaButtonText.value = time + "s";
            time--;
          } else {
            captchaButtonText.value = "验证码";
            time = 60;
            clearInterval(timer);
          }
        }, 1000)
      } else {
        ElMessage({message: result.data.message, type: "error"})
      }
    })
  }
}

const store = useStore();

async function login() {
  if (usernameResult.value.length === 0) {
    await captchaLogin(username.value, captcha.value).then(result => {
      if (result.data.code === 200) {
        ElMessage({message: result.data.message, type: "success"});
        console.log(result.data.data.age);
        store.commit("setInfo", result.data.data);
        setTimeout(() => {
          router.push("/")
        }, 1000);
      } else {
        ElMessage({message: result.data.message, type: "error"});
      }
    })
  }
}
</script>

<style scoped>
.login-box-type1 {
  width: 395px;
  background: #ffffff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 30px 20px 30px;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
  border-radius: 5px;
}

.login-box-type1 > span {
  font-size: 30px;
  line-height: 65px;
  color: #666666;
}

.login-box-type1 > div {
  padding: 5px 20px;
}

.login-row1 input {
  width: 350px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  background: #f9f9f9;
  font-size: 16px;
  border-radius: 5px;
}

.login-row2 input {
  width: 200px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  background: #f9f9f9;
  font-size: 16px;
  border-radius: 5px;
}

.login-row2 button {
  width: 120px;
  height: 39px;
  border: none;
  outline: none;
  background: #5096f5;
  border-radius: 5px;
  color: #ffffff;
  font-size: 17px;
  margin-left: 29px;
  vertical-align: middle;
}

.login-row2 button:hover {
  background: #79bbff;
  cursor: pointer;
}

.login-box-type1 p {
  margin: 0px;
  font-size: 13px;
  color: #ff0000;
  height: 20px;
}

.login-row3 {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #666666;
  margin: 32px 0px 20px;
}

.login-row3 span:hover {
  cursor: pointer;
  color: #409EFF;
}

.login-row4 {
  text-align: center;
  margin: 10px 0px;

}

.login-row4 button {
  width: 175px;
  height: 35px;
  border: none;
  outline: none;
  background: #5096f5;
  border-radius: 5px;
  color: #ffffff;
  font-size: 17px;
}

.login-row4 button:hover {
  background: #79bbff;
  cursor: pointer;
}

input:focus {
  border: solid 1px #409EFF;
}


.login-row1 span, .login-row2 span {
  color: #A8ABB2;
  line-height: 45px;
}

</style>