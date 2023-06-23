<template>
  <el-table :data="tableData" style="width: 100%;padding: 20px 30px;border-radius: 5px">
    <el-table-column prop="userId" label="用户ID"/>
    <el-table-column prop="avatar" label="头像" min-width="130">
      <template #default="item">
        <img :src="item.row.avatar" alt="用户头像" class="user-avatar">
      </template>
    </el-table-column>
    <el-table-column prop="nickname" label="昵称" min-width="130"/>
    <el-table-column prop="signature" label="个性签名" min-width="200"/>
    <el-table-column prop="identity" label="身份">
      <template #default="item">
        <el-tag v-if="item.row.identity==='教师'" class="ml-2" type="success">{{ item.row.identity }}</el-tag>
        <el-tag v-if="item.row.identity==='学生'">{{ item.row.identity }}</el-tag>
        <el-tag v-if="item.row.identity==='访客'" class="ml-2" type="info">{{ item.row.identity }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="registerTime" label="注册时间" min-width="100"/>
    <el-table-column prop="address" label="操作">
      <template #default="item">
        <button @click="deleteUser(item.$index,item.row.userId)" class="delete-btn">删除</button>
      </template>
    </el-table-column>
  </el-table>
  <div class="user-info-tip">
    <span v-if="index===tableData.length">没有更多的数据了！</span>
    <span class="load" @click="loadUser" v-if="index!==tableData.length">加载更多</span>
  </div>
</template>

<script setup>
import {onMounted, reactive} from "vue";
import {getUserInfoByAdmin, logoutUser} from "@/api/system_user";
import {ElMessage} from "element-plus";

let index = reactive(0);
const tableData = reactive([]);

onMounted(async () => {
  await getUserInfoByAdmin(0).then(result => {
    console.log(result);
    index = result.data.data.count;
    let data = result.data.data.userInfo;
    for (let i = 0; i < data.length; i++) {
      tableData.push(data[i]);
    }
  })
})

async function deleteUser(index, id) {
  await logoutUser(id).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      tableData.splice(index, 1);
      index--;
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

async function loadUser() {
  await getUserInfoByAdmin(tableData.length + 1).then(result => {
    console.log(result)
    let data = result.data.data.userInfo;
    for (let i = 0; i < data.length; i++) {
      tableData.push(data[i]);
    }
  })
}
</script>

<style scoped>
.delete-btn {
  background: #F56C6C;
  color: #ffffff;
  border: none;
  width: 80px;
  padding: 6px 10px;
  border-radius: 5px;
}

.user-avatar {
  width: 100px;
  height: 100px;
}

.user-info-tip {
  text-align: center;
  padding: 20px 0px;
}

.user-info-tip > span {
  color: #999999;
  font-size: 15px;
}

.load:hover {
  color: #5096f5;
  cursor: pointer;
}
</style>