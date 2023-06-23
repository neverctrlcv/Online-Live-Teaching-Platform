<template>
  <div class="login-box-type1">
    <span>密码登录</span>
    <div class="login-row1">
      <span>手机号码：</span>
      <input type="text" inputmode="tel" placeholder="请输入手机号码" v-model="username">
      <p>{{ usernameResult }}</p>
    </div>
    <div class="login-row2">
      <span>密码：</span>
      <input type="password" placeholder="请输入密码" v-model="password">
      <p>{{ passwordResult }}</p>
    </div>
    <div class="login-row3">
      <span @click="toRegister">注册账号</span>
      <span @click="toCaptchaLogin">验证码登录</span>
    </div>
    <div class="login-row4">
      <button @click="login">登录</button>
    </div>
  </div>
</template>

<script>
import {ref, watch} from "vue";
import {useRouter} from "vue-router";
import {passwordLogin} from "@/api/user_login.js";
import {ElMessage} from "element-plus";
import {useStore} from "vuex";

export default {
  name: "PasswordLogin",
  setup() {
    const router = useRouter();
    const username = ref("");
    const password = ref("");
    const usernameResult = ref("");
    const passwordResult = ref("");
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

    function toCaptchaLogin() {
      router.push("/user/login/2");
    }

    function toRegister() {
      router.push("/user/register");
    }

    const store = useStore();

    async function login() {
      if (usernameResult.value.length === 0 && passwordResult.value.length === 0) {
        await passwordLogin(username.value, password.value).then(result => {
          if (result.data.code === 200) {
            ElMessage({message: result.data.message, type: "success"});
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


    return {
      username,
      password,
      usernameResult,
      passwordResult,
      toCaptchaLogin,
      toRegister,
      login
    }
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

.login-box-type1 input {
  width: 350px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  background: #f9f9f9;
  font-size: 16px;
  border-radius: 5px;
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
  margin: 20px 0px;
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