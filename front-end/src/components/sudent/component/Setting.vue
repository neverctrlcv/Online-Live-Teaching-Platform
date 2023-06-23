<template>
  <div class="personal-info">
    <div class="info-row1">
      <img :src="portrait" alt="me" @click="changePortrait">
      <span @click="upload">确认</span>
    </div>
    <div class="info-row2">
      <h3>基本信息</h3>
      <ul>
        <li>
          <div><span>用户昵称</span></div>
          <div><span>{{ store.state.nickname }}</span></div>
        </li>
        <li>
          <div><span>用户ID</span></div>
          <div><span>{{ store.state.id }}</span></div>
        </li>
        <li>
          <div><span>电话号码</span></div>
          <div><span>{{ store.state.phone }}</span></div>
        </li>
        <li>
          <div><span>性别</span></div>
          <div><span>{{ store.state.sex == null ? "空" : store.state.sex }}</span></div>
        </li>
        <li>
          <div><span>年龄</span></div>
          <div><span>{{ store.state.age == null ? "空" : store.state.age }}</span></div>
        </li>
        <li>
          <div><span>注册时间</span></div>
          <div><span>{{ store.state.registerTime }}</span></div>
        </li>
        <li>
          <div><span>个人签名</span></div>
          <div><span class="signature">{{ store.state.signature == null ? "空" : store.state.signature }}</span></div>
        </li>
      </ul>
      <div class="modify-btn">
        <button @click="isVerify1=true">修改</button>
      </div>
    </div>
    <div class="info-row3">
      <h3>教育信息</h3>
      <span v-show="store.state.jobId == null" class="authentication-btn"
            @click="router.push('/authentication')">身份验证</span>
      <ul>
        <li>
          <div><span>学/工号</span></div>
          <div><span>{{ store.state.jobId == null ? "空" : store.state.jobId }}</span></div>
        </li>
        <li>
          <div><span>真实姓名</span></div>
          <div><span>{{ store.state.name == null ? "空" : store.state.name }}</span></div>
        </li>
      </ul>
    </div>
    <div class="modify-info" v-show="isVerify1" @click.self="isVerify1=false">
      <div class="info-form">
        <h2>信息修改</h2>
        <div class="form-row1">
          <span>用户昵称</span>
          <input type="text" placeholder="请输入用户名!" v-model="personInfo.nickname">
        </div>
        <div class="form-row2">
          <span>性别</span>
          <select v-model="personInfo.sex">
            <option value="男">男</option>
            <option value="女">女</option>
          </select>
        </div>
        <div class="form-row3">
          <span>年龄</span>
          <input type="number" placeholder="请输入年龄!" v-model="personInfo.age">
        </div>
        <div class="form-row4">
          <span>个性签名</span>
          <textarea rows="6" cols="41" maxlength="200" v-model="personInfo.signature"></textarea>
        </div>
        <div class="form-row5">
          <button @click="infoModify">保存</button>
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>

import {useStore} from "vuex";
import {onMounted, reactive, ref} from 'vue'
import { personalInfoModify, uploadImage} from "@/api/info_modify.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";


const store = useStore();
const router = useRouter();
const portrait = ref();
onMounted(() => {
  console.log(store.state.portrait)
  portrait.value = store.state.portrait;
})


let reader = new FileReader()
let fileInput = document.createElement("input");
fileInput.setAttribute("type", "file");
fileInput.setAttribute("accept", "image/jpeg,image/png,image/jpg");

function changePortrait() {

  fileInput.click();
  fileInput.onchange = function () {
    let file = fileInput.files[0];
    reader.readAsDataURL(file)
    reader.onload = function (e) {
      portrait.value = reader.result;
    }
  }
}

async function upload() {
  await uploadImage( fileInput.files[0]).then(result => {
    console.log(result);
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      store.commit("modifyPortrait", result.data);
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

const isVerify1 = ref(false);
const personInfo = reactive({
  nickname: store.state.nickname,
  sex: store.state.sex,
  age: store.state.age,
  signature: store.state.signature
})

async function infoModify() {
  await personalInfoModify( personInfo.nickname, personInfo.sex, personInfo.age, personInfo.signature).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      store.commit("modifyInfo1", personInfo);
      isVerify1.value = false;
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

</script>

<style scoped>
.personal-info {
  position: relative;
}

.info-row1 {
  text-align: center;
  padding: 20px 0px;
}

.info-row1 img {
  width: 200px;
  height: 200px;
  border-radius: 100px;
}

.info-row2, .info-row3 {
  width: 900px;
  margin: 80px auto 0px;
  padding: 30px;
  position: relative;
  background: #ffffff;
  border-radius: 6px;
}

.info-row2 ul, .info-row3 ul {
  margin: 0;
  padding-left: 20px;
}

.info-row2 li, .info-row3 li {
  list-style: none;
  margin: 40px 0px;
  color: #555666;
}

li > div:nth-child(1) {
  width: 200px;
  display: inline-block;
  vertical-align: top;
}

li > div:nth-child(2) {
  display: inline-block;
  vertical-align: top;
  width: 500px;
}

.signature {
  line-height: 26px;
  text-align: justify;
}

.modify-btn {
  text-align: center;
}

.modify-btn button {
  width: 150px;
  border: none;
  height: 30px;
  background: #79bbff;
  color: #ffffff;
  border-radius: 5px;
}

.modify-info {
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100%;
  min-width: 1100px;
  background: rgba(200, 200, 200, 0.8);
}

.close-modify-btn {
  position: absolute;
  top: 10%;
  left: 75%;
}

.info-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -70%);
  background: #ffffff;
  width: 575px;
  height: 620px;
  border-radius: 5px;
}

.info-form h2 {
  padding: 30px 0px;
  text-align: center;
}

.info-form span {
  text-align: left;
  display: inline-block;
  line-height: 30px;
  width: 100px;
  color: #666666;
  font-size: 19px;
  vertical-align: top;
}

.info-form > div {
  width: 530px;
  padding: 15px 20px;
  text-align: center;
}

.info-form input {
  width: 390px;
  height: 30px;
  outline: none;
  border: solid 1px #e0e0e0;
  line-height: 30px;
  font-size: 17px;
  border-radius: 5px;
}

.info-form select {
  width: 395px;
  height: 35px;
  outline: none;
  border: solid 1px #e0e0e0;
  line-height: 30px;
  font-size: 17px;
  border-radius: 5px;
}

.info-form select option {
  line-height: 30px;
  border-radius: 0px;
  background: #e0e0e0;
}

.info-form textarea {
  border-radius: 5px;
  border: solid 1px #e0e0e0;
  resize: none;
  outline: none;
  font-size: 17px;
  font-family: "Microsoft YaHei";
  letter-spacing: 1px;
  line-height: 25px;
}

.info-form button {
  width: 120px;
  height: 39px;
  border: none;
  outline: none;
  background: #5096f5;
  border-radius: 5px;
  color: #ffffff;
  font-size: 17px;
}

.authentication-btn {
  position: absolute;
  top: 45px;
  right: 30px;
  color: #409EFF;
  font-size: 13px;
}

.authentication-btn:hover {
  cursor: pointer;
  color: #79bbff;
}

</style>