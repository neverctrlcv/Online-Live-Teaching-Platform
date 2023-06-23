<template>
  <el-table :data="tableData" stripe
            style="width: 100%;height: calc(100vh - 150px);padding: 20px 30px;border-radius: 5px">
    <el-table-column prop="videoId" label="ID" min-width="130"/>
    <el-table-column prop="date" label="视频" min-width="160">
      <template #default="item">
        <img :src="item.row.cover" alt="视频封面" class="video-cover">
      </template>
    </el-table-column>
    <el-table-column prop="description" label="标题" min-width="230"/>
    <el-table-column prop="playCount" label="播放数量" min-width="120"/>
    <el-table-column prop="videoLength" label="时长" min-width="150"/>
    <el-table-column prop="uploadTime" label="上传时间" min-width="150"/>
    <el-table-column prop="nickname" label="上传者" min-width="150"/>
    <el-table-column label="操作" width="180">
      <template #default="item">
        <button @click="deleteVideo(item.$index,item.row.videoId)" class="delete-btn">删除</button>
      </template>
    </el-table-column>

  </el-table>
  <div class="video-info-tip">
    <span  v-if="loadCount!==15">没有更多的数据了！</span>
    <span class="load" v-if="loadCount===15" @click="loadVideo">加载更多</span>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {getAllVideoCount, getVideoInfoByAdmin, deleteVideoByAdmin} from "@/api/system_video";
import {ElMessage} from "element-plus";

let allVideoCount = reactive(0);
const tableData = reactive([]);
let loadCount=ref(15);

onMounted(async () => {
  await getAllVideoCount().then(result => {
    console.log(result)
    allVideoCount = result.data.data;
  })
  await getVideoInfoByAdmin(0).then(result => {
    console.log(result)
    let data = result.data.data;
    loadCount.value=data.length;
    for (let i = 0; i < data.length; i++) {
      tableData.push(data[i]);
    }
  })

})

async function deleteVideo(index, id) {
  await deleteVideoByAdmin(id).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      tableData.splice(index, 1);
      allVideoCount--;
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

async function loadVideo() {
  if (tableData.length<allVideoCount){
    await getVideoInfoByAdmin(tableData.length + 1).then(result => {
      console.log(result)
      let data = result.data.data;
      loadCount.value=data.length;
      for (let i = 0; i < data.length; i++) {
        tableData.push(data[i]);
      }
    })
  }
}
</script>

<style scoped>
.video-cover {
  width: 130px;
  height: 100px;
}
.delete-btn {
  background: #F56C6C;
  color: #ffffff;
  border: none;
  width: 80px;
  padding: 6px 10px;
  border-radius: 5px;
}

.video-info-tip {
  text-align: center;
  padding: 20px 0px 0px;
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