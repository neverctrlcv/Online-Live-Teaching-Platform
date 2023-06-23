<template>
  <div class="student-attestation" :style="`background:url(${LoginBg}) no-repeat center center/cover;`">
    <div class="attestation-form">
      <h1>身份认证</h1>
      <div class="attestation-row1">
        <span>学/工号</span>
        <input type="text" placeholder="请输入学号或工号!" v-model="identityInfo.jobId">
      </div>
      <div class="attestation-row2">
        <span>身份类别</span>
        <select v-model="identityInfo.identity">
          <option value="2">学生</option>
          <option value="3">教师</option>
        </select>
      </div>
      <div class="attestation-row3">
        <span>真实姓名</span>
        <input type="text" placeholder="请输入真实姓名!" v-model="identityInfo.name">
      </div>
      <div class="attestation-row4">
        <button @click="authentication">保存</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {infoVerify} from "@/api/info_modify.js";
import {ElMessage} from "element-plus";
import {reactive} from "vue";
import {useStore} from "vuex";
import LoginBg from '@/assets/img/login/login-bg.jpg';
import {useRouter} from "vue-router";

const store = useStore();
const router = useRouter();
const identityInfo = reactive({
  jobId: store.state.jobId,
  identity: store.state.identity,
  name: store.state.name
})

async function authentication() {
  await infoVerify(identityInfo.jobId, identityInfo.identity, identityInfo.name).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      store.commit("verifyInfo", identityInfo);
      router.push("/");
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}
</script>

<style scoped>
.student-attestation {
  position: relative;
  width: 100vw;
  height: 100vh;
}

.attestation-form {
  width: 470px;
  height: 400px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -70%);
  background: #ffffff;
  border-radius: 5px;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
}

.attestation-form h1 {
  text-align: center;
  margin-bottom: 60px;
}

.attestation-form > div {
  margin-top: 35px;
  padding: 0px 30px;
}

.attestation-form span {
  display: inline-block;
  width: 80px;
  font-size: 16px;
  color: #666666;
}

.attestation-form input {
  width: 320px;
  height: 30px;
  outline: none;
  border: solid 1px #e0e0e0;
  line-height: 30px;
  font-size: 17px;
  border-radius: 5px;
}

.attestation-form select {
  width: 325px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  line-height: 30px;
  font-size: 17px;
  border-radius: 5px;
}

.attestation-form input:focus, .attestation-form select:focus {
  border: 1px solid #79bbff;
}

.attestation-row4 {
  text-align: center;
}

.attestation-row4 button {
  width: 120px;
  height: 39px;
  border: none;
  outline: none;
  background: #5096f5;
  border-radius: 5px;
  color: #ffffff;
  font-size: 17px;
}

.attestation-row4 button:hover {
  cursor: pointer;
  background: #79bbff;
}
</style>