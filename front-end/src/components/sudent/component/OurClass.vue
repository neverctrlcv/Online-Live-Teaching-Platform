<template>
  <div class="ban-box" v-if="store.state.name==null">
    您还没有进行身份验证，该模块不能使用！
  </div>
  <div class="class-info" v-if="store.state.name!==null&&!isAddClass" >
    <div class="info-basic">
      <div class="basic-col1">
        <div class="basic-row1">
          <img :src="classInfo.avatar" alt="班级头像">
        </div>
        <div class="basic-row2">
          <span>{{ classInfo.className }}</span>
        </div>
      </div>
      <div class="basic-col2">
        <table>
          <tbody>
          <tr>
            <td class="t-title"><span>口</span><span>号</span><span>：</span></td>
            <td class="t-content">{{ classInfo.slogan }}</td>
          </tr>
          <tr>
            <td class="t-title"><span>创</span><span>建</span><span>者</span><span>：</span></td>
            <td class="t-content">{{classInfo.founder}}</td>
          </tr>
          <tr>
            <td class="t-title"><span>创</span><span>建</span><span>时</span><span>间</span><span>：</span></td>
            <td class="t-content">{{classInfo.createTime}}</td>
          </tr>
          <tr>
            <td class="t-title"><span>班</span><span>级</span><span>状</span><span>态</span><span>：</span></td>
            <td class="t-content">
              <span v-if="!classStatus">未上课</span>
              <span v-if="classStatus" @click="toClassroom" class="class-status">上课中</span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="basic-col3">
        <button @click="exit">退出班级</button>
      </div>
    </div>
    <div class="info-stu">
      <div class="class-data-title">
        <span>班级成员</span>
        <span>{{classMemberData.length}}人</span>
      </div>
      <el-table :data="classMemberData" stripe
                style="width: 100%;min-width:650px;height: 520px; padding: 10px;border-radius: 5px;">
        <el-table-column prop="name" label="姓名" width="200"/>
        <el-table-column prop="jobId" label="学号" width="200"/>
        <el-table-column prop="identity" label="身份" width="200"/>
        <el-table-column prop="joinTime" label="加入时间"/>
      </el-table>
    </div>
  </div>
  <div class="join-class" v-if="store.state.name!==null&&isAddClass">
    <div class="join-form">
      <div class="form-row1">
        <input type="text" placeholder="请输入班级邀请码" v-model="inviteCode">
      </div>
      <div class="form-row2">
        <button @click="join" >加入</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Me from '@/assets/img/me.jpg';
import {useStore} from "vuex";
import {onBeforeMount, reactive, ref} from "vue";
import {exitClass, getClassAllInfo, getClassIdByUser, getClassroomStatus, joinClass} from "@/api/my_class.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";
const router=useRouter();
const store = useStore();
const isAddClass=ref(false);
const classId=ref("");


const classMemberData = reactive([]);
const classInfo = reactive({
  className: '',
  slogan: '',
  createTime: '',
  founder: '',
  avatar: '',
  inviteCode: '',
  founderId: ''
});
let classStatus=ref(false);
onBeforeMount(async () => {
  await getClassIdByUser().then(async result => {
    console.log(result);
    if (result.data.data === null) {
      isAddClass.value = true;
    } else {
      classId.value = result.data.data[0].classId;
      await getClassAllInfo(classId.value).then(result => {
        console.log(result);
        let data = result.data.data;
        classInfo.className = data.classInfo.className;
        classInfo.slogan = data.classInfo.slogan;
        classInfo.createTime = data.classInfo.createTime;
        classInfo.founder = data.classInfo.founder;
        classInfo.avatar = data.classInfo.avatar;
        classInfo.inviteCode = data.classInfo.inviteCode;
        classInfo.founderId = data.classInfo.founderId;
        for (let i = 0; i < data.memberInfo.length; i++) {
          classMemberData.push(data.memberInfo[i]);
        }
      })
      await getClassroomStatus(classId.value).then(result => {
        console.log(result);
        classStatus.value = result.data.data;
        console.log(classStatus.value);
      })
    }
  })
})

const inviteCode=ref("");
async function join() {
  await joinClass(inviteCode.value).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: 'success'});
      setTimeout(() => {
        router.go(0)
      }, 500);
    } else {
      ElMessage({message: result.data.message, type: 'error'});
    }
  })
}

async function exit() {
  await exitClass(classId.value).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: 'success'});
      setTimeout(() => {
        router.go(0)
      }, 500);
    } else {
      ElMessage({message: result.data.message, type: 'error'});
    }
  })
}
function toClassroom(){
  router.push("/classroom?p="+classId.value);
}
</script>

<style scoped>
.ban-box {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -85%);
  color: #666666;
  font-size: 20px;
}

.class-info {
  width: 100%;
  height: 580px;
  min-width: 1100px;
  margin-top: 30px;
}

.info-basic {
  display: inline-block;
  vertical-align: bottom;
  width: 380px;
  height: 480px;
  background: #ffffff;
  padding: 20px;
  border-radius: 5px;
}

.basic-row1 {
  text-align: center;
  padding-top: 10px;
}

.basic-row1 img {
  width: 120px;
  height: 120px;
  border-radius: 60px;
}

.basic-row2 {
  font-size: 17px;
  font-weight: 550;
  text-align: center;
  margin-top: 20px;
}

.basic-col2 {
  font-size: 17px;
  margin-top: 30px;
}

.t-title {
  display: flex;
  justify-content: space-between;
  width: 110px;
  line-height: 40px;
}

.info-stu {
  display: inline-block;
  vertical-align: bottom;
  width: calc(100% - 470px);
  margin-left: 30px;
}

.class-data-title {
  display: flex;
  justify-content: space-between;
  font-size: 17px;
  line-height: 30px;
}

.basic-col3 {
  text-align: center;
}

.basic-col3 button {
  border: none;
  color: #ffffff;
  background: #F56C6C;
  width: 100px;
  padding: 6px 10px;
  margin-top: 20px;
  border-radius: 5px;
}

.basic-col3 button:hover {
  cursor: pointer;
  background: #f89898;
}


.join-class {
  width: 100%;
  height: 100%;
  top: 0px;
  background: rgba(150, 150, 150, 0.5);
}

.join-form {
  width: 400px;
  background: #ffffff;
  text-align: center;
  padding: 20px 0px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%,-70%);
  border-radius: 5px;
}

.join-form > div {
  padding: 20px 0px;
}

.form-row1 input {
  width: 300px;
  height: 30px;
  border-radius: 5px;
  border: solid 1px #e0e0e0;
  outline: none;
}
.form-row2 button {
  background: #409EFF;
  border: none;
  color: #ffffff;
  width: 120px;
  padding: 6px 10px;
  margin: 0px 10px;
  border-radius: 5px;
}

.form-row2 button:hover {
  cursor: pointer;
  background: #79bbff;
}
.class-status{
  color: #5096f5;
}
.class-status:hover{
  cursor: pointer;
  color: #79bbff;
}
</style>