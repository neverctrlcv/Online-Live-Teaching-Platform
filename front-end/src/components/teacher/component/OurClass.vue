<template>
  <div class="class-container">
    <div class="class-top">
      <div class="c-top-left">
        <el-select v-model="value" class="m-2" placeholder="选择班级" size="large">
          <el-option
              v-for="item in classList"
              :key="item.classId"
              :label="item.className"
              :value="item.classId"
          />
        </el-select>
      </div>
      <div class="c-top-right">
        <button @click="isJoinClass=true">加入班级</button>
        <button @click="isAddClass=true">创建班级</button>
      </div>
    </div>
    <div class="class-bottom" v-show="value!==''">
      <div class="c-bottom-left">
        <div class="class-info">
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
                  <td class="t-content">{{ classInfo.founder }}</td>
                </tr>
                <tr>
                  <td class="t-title"><span>创</span><span>建</span><span>时</span><span>间</span><span>：</span></td>
                  <td class="t-content">{{ classInfo.createTime }}</td>
                </tr>
                <tr>
                  <td class="t-title"><span>邀</span><span>请</span><span>码</span><span>：</span></td>
                  <td class="t-content">{{ classInfo.inviteCode }}</td>
                </tr>
                <tr>
                  <td class="t-title"><span>班</span><span>级</span><span>状</span><span>态</span><span>：</span></td>
                  <td class="t-content">
                    <span class="class-status" v-if="!classStatus" @click="toClassroom">开启上课</span>
                    <span class="class-status" v-if="classStatus" @click="toClassroom">上课中</span>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
            <div class="basic-col3">
              <button v-if="store.state.id!==classInfo.founderId" @click="exit">退出班级</button>
              <button v-if="store.state.id===classInfo.founderId" @click="dissolve">解散班级</button>
            </div>
          </div>
          <div class="info-stu">
            <div class="class-data-title">
              <span>班级成员</span>
              <span>{{ classMemberData.length }}人</span>
            </div>
            <el-table :data="classMemberData" stripe
                      style="width: 100%;height: calc(100vh - 500px); padding: 10px;border-radius: 5px;">
              <el-table-column prop="name" label="姓名" width="200"/>
              <el-table-column prop="jobId" label="学号" width="200"/>
              <el-table-column prop="identity" label="身份" width="200"/>
              <el-table-column prop="joinTime" label="加入时间" width="200"/>
            </el-table>
          </div>
        </div>
      </div>
      <div class="c-bottom-right">
        <p class="status-title">学生状态</p>
        <div class="status-box">
          <el-table :data="studentStatus"
                    style="width: 100%;height: calc(100vh - 270px); padding: 10px;border-radius: 5px;">
            <el-table-column prop="date" label="日期" width="150"/>
            <el-table-column label="开始时间" width="150">
              <template #default="item">
                <span>{{ item.row.startTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template #default="item">
                <a @click="download(item.row.id)">下载</a>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
    </div>
  </div>
  <div class="add-class" v-show="isAddClass" @click.self="isAddClass=false">
    <div class="add-form">
      <div class="form-row1">
        <input type="text" placeholder="请输入班级名称" v-model="classInfoByCreate.className">
      </div>
      <div class="form-row2">
        <input type="text" placeholder="请输入班级口号" v-model="classInfoByCreate.slogan">
      </div>
      <div class="form-row3">
        <button @click="createClassByTeacher">创建</button>
      </div>
    </div>
  </div>
  <div class="join-class" v-show="isJoinClass" @click.self="isJoinClass=false">
    <div class="join-form">
      <div class="form-row1">
        <input type="text" placeholder="请输入班级邀请码" v-model="inviteCode">
      </div>
      <div class="form-row2">
        <button @click="join">加入</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useStore} from "vuex";
import {onBeforeMount, reactive, ref, watch} from "vue";
import {
  getClassIdByUser, createClass, exitClass, dissolveClass
  , getClassAllInfo, joinClass, getClassroomStatus, downloadStatus
} from '@/api/my_class';
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const store = useStore();
const router = useRouter();
const isAddClass = ref(false);
const isJoinClass = ref(false);

let classList = reactive([]);
onBeforeMount(async () => {
  await getClassIdByUser().then(result => {
    let data = result.data.data;
    for (let i = 0; i < data.length; i++) {
      classList.push(data[i]);
    }
  })
})


const classMemberData = reactive([]);
const studentStatus = reactive([]);
const classInfo = reactive({
  className: '',
  slogan: '',
  createTime: '',
  founder: '',
  avatar: '',
  inviteCode: '',
  founderId: ''
});
let value = ref("");
let classStatus = ref(false);
watch(value, async (newValue, oldValue) => {
  classMemberData.length = 0;
  studentStatus.length = 0;
  await getClassAllInfo(value.value).then(result => {
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
    if (classInfo.founderId === store.state.id) {
      for (let i = 0; i < data.classT.length; i++) {
        console.log(data.classT[i]);
        studentStatus.push(data.classT[i]);
      }
    }
  })
  await getClassroomStatus(value.value).then(result => {
    console.log("hhhhhhh" + result);
    classStatus.value = result.data.data;
  });
  console.log(classMemberData.length)
})


const classInfoByCreate = reactive({
  className: '',
  slogan: ''
})

async function createClassByTeacher() {
  await createClass(classInfoByCreate.className, classInfoByCreate.slogan).then(result => {
    console.log(result);
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      setTimeout(() => {
        router.go(0);
      }, 500);
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

let inviteCode = ref("");

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
  await exitClass(value.value).then(result => {
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

async function dissolve() {
  await dissolveClass(value.value).then(result => {
    if (result.data.code == 200) {
      ElMessage({message: result.data.message, type: 'success'});
      setTimeout(() => {
        router.go(0)
      }, 500);
    } else {
      ElMessage({message: result.data.message, type: 'error'});
    }
  })
}

function toClassroom() {
  router.push("/classroom?p=" + value.value);
}

async function download(id) {
  await downloadStatus(id).then(result => {
    console.log(result);
    let data = result.data.data;
    let str = '';
    for (let i = 0; i < data.length; i++) {
      for (let item in data[i]) {
        str += `${data[i][item] + '\t'},`;
      }
      str += '\n';
    }
    let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
    // 通过创建a标签实现
    let link = document.createElement("a");
    link.href = uri;
    //对下载的文件命名
    link.download = new Date().getTime() + ".csv";
    link.click();

  })
}

</script>
<style scoped>
.class-container {
  min-width: 1100px;
  min-height: 500px;
}

.class-top {
  width: 95%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}

.c-top-right button {
  background: #409EFF;
  border: none;
  color: #ffffff;
  width: 120px;
  padding: 6px 10px;
  margin: 0px 10px;
  border-radius: 5px;
}

.c-top-right button:hover {
  cursor: pointer;
  background: #79bbff;
}

.class-bottom {
  width: 100%;
  height: calc(100vh - 200px);
  margin-top: 30px;
}

.info-basic {
  height: 176px;
  background: #ffffff;
  border-radius: 5px;
  padding: 30px 5px;
  margin-bottom: 20px;
}

.basic-col1 {
  display: inline-block;
  vertical-align: top;
  width: 150px;
  padding: 10px 0px;
}

.basic-row1 {
  text-align: center;
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
  display: inline-block;
  vertical-align: top;
  margin-left: 15px;
  color: #333333;
  line-height: 32px;
}

.basic-col3 {
  display: inline-block;
  vertical-align: bottom;
}

.basic-col3 button {
  border: none;
  color: #ffffff;
  background: #F56C6C;
  width: 100px;
  padding: 6px 10px;
  margin-left: 100px;
  border-radius: 5px;
}

.basic-col3 button:hover {
  cursor: pointer;
  background: #f89898;
}

.t-title {
  display: flex;
  justify-content: space-between;
  width: 110px;
}

.class-data-title {
  display: flex;
  justify-content: space-between;
  line-height: 30px;
  color: #333333;
}

.c-bottom-left {
  display: inline-block;
  vertical-align: bottom;
  width: calc(100vw - 720px);
}

.c-bottom-right {
  display: inline-block;
  background: #ffffff;
  width: 400px;
  margin-left: 30px;
  height: calc(100vh - 210px);
  border-radius: 5px;
}

.status-title {
  font-weight: 550;
  font-size: 15px;
  margin-left: 15px;
}

.add-class, .join-class {
  position: absolute;
  z-index: 1000;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(150, 150, 150, 0.5);
}

.add-form, .join-form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -70%);
  width: 390px;
  background: #ffffff;
  padding: 20px 0px;
  border-radius: 5px;
}

.add-form > div, .join-form > div {
  width: 390px;
  text-align: center;
  padding: 20px 0px;
}

.add-form input, .join-form input {
  width: 360px;
  border: solid 1px #e0e0e0;
  height: 30px;
  border-radius: 5px;
  outline: none;
}

.add-form input:focus, .join-form iput:focus {
  border: solid 1px #409EFF;
}

.add-form button, .join-form button {
  color: #ffffff;
  border: none;
  outline: none;
  background: #409EFF;
  width: 80px;
  padding: 5px 10px;
  border-radius: 5px;
}

.add-form button:hover, .join-form button:hover {
  cursor: pointer;
  background: #79bbff;
}

.class-status {
  color: #5096f5;
}

.class-status:hover {
  cursor: pointer;
  color: #79bbff;
}
</style>