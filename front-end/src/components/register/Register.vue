<template>
  <div class="register-box-type1">
    <span>用户注册</span>
    <div class="register-row1">
      <span>手机号码：</span>
      <input type="text" inputmode="tel" placeholder="请输入手机号码" v-model="username">
      <p>{{ usernameResult }}</p>
    </div>
    <div class="register-row2">
      <span>验证码：</span>
      <div>
        <input type="text" placeholder="请输入验证码" v-model="captcha">
        <button @click="getCaptchaMethod">{{ captchaButtonText }}</button>
      </div>
    </div>
    <div class="register-row3">
      <span>密码：</span>
      <input type="password" placeholder="请输入密码" v-model="password">
      <p>{{ passwordResult }}</p>
    </div>
    <div class="register-row4">
      <span>确认密码：</span>
      <input type="password" placeholder="请输入密码" v-model="confirm">
      <p>{{ confirmResult }}</p>
    </div>
    <div class="register-row5">
      <span @click="toLogin">去登录</span>
    </div>
    <div class="register-row6">
      <button @click="userRegister">注册</button>
    </div>
  </div>
</template>

<script setup>
import {ref, watch} from "vue";
import {useRouter} from "vue-router";
import {getCaptchaInRegister, register} from "@/api/user_login.js";
import {ElMessage} from "element-plus";

const router = useRouter();
const username = ref("");
const captcha = ref("");
const password = ref("");
const confirm = ref("");
const usernameResult = ref("");
const passwordResult = ref("");
const confirmResult = ref("");
const phoneRegExp = /^1[3456789]\d{9}$/;
const passwordRegExp = /^(\w){8,20}$/;

watch(username, (newValue, oldValue) => {
  if (!phoneRegExp.test(newValue)) {
    usernameResult.value = "请输入正确的手机号码！";
  } else {
    usernameResult.value = "";
  }
})
watch(password, (newValue, oldValue) => {
  if (!passwordRegExp.test(newValue)) {
    passwordResult.value = "密码由8-20位数字或字母组成！";
  } else {
    passwordResult.value = "";
  }
})
watch(confirm, (newValue, oldValue) => {
  if (newValue !== password.value) {
    confirmResult.value = "两次密码不一致！";
  } else {
    confirmResult.value = "";
  }
})

function toLogin() {
  router.push("/user/login/1");
}

const captchaButtonText = ref("验证码");
let time = 60;

async function getCaptchaMethod() {
  if (!phoneRegExp.test(username.value)) {
    ElMessage({message: "请输入正确的手机号码", type: "error"})
    return;
  }
  if (captchaButtonText.value === "验证码") {
    await getCaptchaInRegister(username.value).then((result) => {
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

async function userRegister() {
  if (usernameResult.value.length === 0 && passwordResult.value.length === 0 && confirmResult.value.length === 0) {
    await register(username.value, captcha.value, password.value).then(result => {
      if (result.data.code === 200) {
        ElMessage({message: result.data.message, type: "success"})
        setTimeout(() => {
          toLogin()
        }, 1000);
      } else {
        ElMessage({message: result.data.message, type: "error"})
      }
    })
  }
}
</script>

<style scoped>
.register-box-type1 {
  width: 395px;
  background: #ffffff;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 30px 25px 30px;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
  border-radius: 5px;
}

.register-box-type1 > span {
  font-size: 30px;
  line-height: 65px;
  color: #666666;
}

.register-box-type1 > div {
  padding: 5px 20px;
}

.register-row1 input, .register-row3 input, .register-row4 input {
  width: 350px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  background: #f9f9f9;
  font-size: 16px;
  border-radius: 5px;
}

.register-box-type1 p {
  margin: 0px;
  font-size: 13px;
  color: #ff0000;
  height: 20px;
}


.register-row3 span:hover {
  cursor: pointer;
  color: #409EFF;
}

.register-row6 {
  text-align: center;
  margin: 10px 0px;

}

.register-row6 button {
  width: 175px;
  height: 35px;
  border: none;
  outline: none;
  background: #5096f5;
  border-radius: 5px;
  color: #ffffff;
  font-size: 17px;
}

.register-row6 button:hover {
  background: #79bbff;
  cursor: pointer;
}


.register-row1 span, .register-row2 span, .register-row3 span, .register-row4 span {
  color: #A8ABB2;
  line-height: 45px;
}

.register-row2 {
  margin-bottom: 20px;
}

.register-row2 input {
  width: 200px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  background: #f9f9f9;
  font-size: 16px;
  border-radius: 5px;
}

.register-row2 button {
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

.register-row5 {
  text-align: right;
  font-size: 14px;
  color: #666666;
  margin: 5px 0px;
}

.register-row5 span:hover {
  cursor: pointer;
  color: #409EFF;
}

input:focus {
  border: solid 1px #409EFF;
}
</style>