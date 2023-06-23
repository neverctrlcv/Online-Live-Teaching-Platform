<template>
  <div class="teacher-video">
    <div class="box-top">
      <div class="title">我的视频</div>
      <div class="add-btn">
        <button @click="isAddVideo=true">添加</button>
      </div>
    </div>
    <el-table :data="tableData" style=" margin:10px auto; width: 1500px; height:calc(100vh - 190px);padding: 10px 20px;border-radius: 5px">
      <el-table-column prop="cover" label="视频" width="190">
        <template #default="item">
          <img :src="item.row.cover" alt="视频" class="video-cover">
        </template>
      </el-table-column>
      <el-table-column prop="uploadTime" label="上传时间" width="130"/>
      <el-table-column prop="description" label="描述" width="500"/>
      <el-table-column prop="playCount" label="播放数量" width="140"/>
      <el-table-column prop="videoLength" label="时长" width="140"/>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="item">
          <el-tag v-if="item.row.status==='Y'" class="ml-2" type="success">公开</el-tag>
          <el-tag v-if="item.row.status!=='Y'" class="ml-2" type="danger">未公开</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operate" label="操作" width="240">
        <template #default="item">
          <button v-if="item.row.status!=='Y'" class="share-btn" @click="shareVideo(item.$index,item.row.videoId)">公开
          </button>
          <button v-if="item.row.status==='Y'" class="hidden-btn" @click="hiddenVideo(item.$index,item.row.videoId)">
            隐藏
          </button>
          <button class="delete-btn" @click="deleteVideo(item.$index,item.row.videoId)">删除</button>
        </template>
      </el-table-column>

    </el-table>

  </div>
  <div class="add-material" v-show="isAddVideo" @click.self="isAddVideo=false">
    <div class="add-form">
      <h2>上传视频</h2>
      <textarea v-model="video.desc" rows="6" cols="50" placeholder="请输入相关描述！"></textarea>
      <input @change="getCover($event)" type="file" accept="image/jpeg,image/png">
      <input @change="getVideo($event)" type="file" accept="video/mp4">
      <button @click="addVideo">提交</button>
    </div>
  </div>
</template>

<script setup>
import {reactive, onMounted, ref} from "vue";
import {useStore} from "vuex";
import {hiddenVideos, shareVideos, deleteVideos, getVideos, addVideos} from "@/api/my_video.js";
import {ElMessage} from "element-plus";
import ConsoleClose from '@/assets/icon/console/console-close.png';
import {useRouter} from "vue-router";

const store = useStore();
const router = useRouter();
const tableData = reactive([]);
onMounted(async () => {
  await getVideos().then(result => {
    if (result.data.code !== 200) {
      console.log(result)
      ElMessage({message: result.data.message, type: 'error'});
    } else {
      for (let i = 0; i < result.data.data.length; i++) {
        tableData.push(result.data.data[i]);
      }
    }
  })
})

async function hiddenVideo(index, id) {
  console.log(id)
  await hiddenVideos(id).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      tableData[index].status = 'N';
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

async function shareVideo(index, id) {
  console.log(id)
  await shareVideos(id).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      tableData[index].status = 'Y';
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

async function deleteVideo(index, id) {
  await deleteVideos(id).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      tableData.splice(index, 1);
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}

let isAddVideo = ref(false);


const video = reactive({
  desc: '',
  cover: '',
  file: '',
  videoLength: ''
});

async function addVideo() {
  await addVideos(video.desc, video.videoLength, video.cover, video.file).then(result => {
    if (result.data.code === 200) {
      ElMessage({message: result.data.message, type: "success"});
      // store.commit("verifyInfo", identityInfo);
      isAddVideo.value = false;
      router.go(0);
    } else {
      ElMessage({message: result.data.message, type: "error"});
    }
  })
}


function getCover(e) {
  console.log(e.target.files[0]);
  video.cover = e.target.files[0];
}

function getVideo(e) {
  console.log(e.target.files[0]);
  video.file = e.target.files[0];
  const file = e.target.files[0];
  // 定义一个音频实例对象
  let audio = new Audio()
  try {
    audio.srcObject = file
  } catch {
    audio.src = URL.createObjectURL(file)
  }
  let url = URL.createObjectURL(file);
  //获取视频时长
  audio.addEventListener('loadedmetadata', () => {
    let length = Math.floor(audio.duration);
    if (length >= 3600) {
      video.videoLength = Math.floor(length / 3600) + ":" + Math.floor(length % 3600 / 60) + ":" + Math.floor(length % (3600 * 60));
    } else {
      video.videoLength = Math.floor(length / 60) + ":" + Math.floor(length % 60);
    }
  })
  console.log(typeof video.cover)
  //获取视频的某一帧画面

  if (video.cover === '') {
    const videos = document.createElement("video");
    videos.setAttribute("crossOrigin", "anonymous");
    videos.setAttribute("src", url);
    videos.setAttribute("preload", "auto");
    videos.addEventListener("loadeddata", function () {
      const canvas = document.createElement("canvas");
      videos.currentTime = 13;
      canvas.getContext("2d").drawImage(videos, 0, 0, videos.videoWidth, videos.videoHeight);
      video.cover = getFileFromBase64(canvas.toDataURL("image/jpeg"), "hello.jpg");
    });
  }
}

// base64转图片
function getFileFromBase64(base64URL, filename) {
  let arr = base64URL.split(","),
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new File([u8arr], filename, {type: "image/png"});
}

</script>

<style scoped>
.teacher-video {
  width: 98%;
  margin: 0 auto;
}

.box-top {
  width: 95%;
  margin: 15px auto;
  display: flex;
  justify-content: space-between;
}

.title {
  font-size: 19px;
  font-weight: 600;
}

.add-btn button {
  width: 100px;
  height: 30px;
  background: #409EFF;
  border: none;
  outline: none;
  color: #ffffff;
  border-radius: 5px;
}

.add-btn button:hover {
  cursor: pointer;
  background: #79bbff;
}


.share-btn {
  width: 60px;
  height: 30px;
  background: #67C23A;
  border: none;
  outline: none;
  color: #ffffff;
  border-radius: 5px;
  margin: 0px 20px;
}

.hidden-btn {
  width: 60px;
  height: 30px;
  background: #409EFF;
  border: none;
  outline: none;
  color: #ffffff;
  border-radius: 5px;
  margin: 0px 20px;
}

.delete-btn {
  width: 60px;
  height: 30px;
  background: #F56C6C;
  border: none;
  outline: none;
  color: #ffffff;
  border-radius: 5px;
  margin: 0px 20px;
}

.share-btn:hover, .hidden-btn:hover, .delete-btn:hover {
  cursor: pointer;
}

.video-cover {
  width: 160px;
  height: 90px;
}

.add-material {
  position: absolute;
  z-index: 1000;
  top: 0px;
  width: 100%;
  height: 100%;
  min-width: 1100px;
  background: rgba(200, 200, 200, 0.8);
}

.add-form {
  width: 390px;
  padding: 20px 30px;
  position: absolute;
  top: 50%;
  left: 50%;
  background: #ffffff;
  transform: translate(-50%, -50%);
  border-radius: 5px;
}

.add-form h2 {
  text-align: center;
}

.add-form input[type=text] {
  width: 390px;
  height: 30px;
  outline: none;
  border: solid 1px #e0e0e0;
  line-height: 30px;
  font-size: 17px;
  border-radius: 5px;
  margin: 20px 0px 30px;
}

.add-form textarea {
  width: 390px;
  border-radius: 5px;
  border: solid 1px #e0e0e0;
  resize: none;
  outline: none;
  font-size: 17px;
  font-family: "Microsoft YaHei";
  letter-spacing: 1px;
  line-height: 25px;
}

.add-form input[type=file] {
  width: 390px;
  margin: 20px 0px 30px;
}

.add-form button {
  margin: 10px 0px 20px 135px;
  width: 120px;
  height: 30px;
  background: #409EFF;
  border: none;
  outline: none;
  color: #ffffff;
  border-radius: 5px;

}


</style>