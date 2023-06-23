<template>
  <Header></Header>
  <div class="v-container">
    <div class="c-left">
      <div class="c-left-top">
        <p class="video-title" :title=" videoInfo.description">{{ videoInfo.description }}</p>
        <div class="video-info">
          <div class="play-count">
            <el-icon size="16">
              <VideoPlay/>
            </el-icon>
            <span>{{ videoInfo.playCount }}</span>
          </div>
          <div class="video-length">
            <el-icon size="16">
              <Timer/>
            </el-icon>
            <span>{{ videoInfo.videoLength }}</span>
          </div>
          <div class="upload-time">{{ videoInfo.uploadTime }}</div>
        </div>
      </div>
      <div class="c-left-bottom">
        <video autoplay controls controlslist="nodownload" :poster="videoInfo.cover" :src="videoInfo.link"></video>
      </div>
    </div>
    <div class="c-right">
      <div class="c-right-top">
        <div class="uploader-avatar">
          <img :src="userInfo.avatar" alt="上传者">
        </div>
        <div class="uploader-info">
          <p class="username">{{ userInfo.nickname }}</p>
          <p class="signature">{{ userInfo.signature }}</p>
        </div>
      </div>
      <div class="c-right-bottom">
        <span class="suggest-title">系统推荐</span>
        <div class="suggest-box">
          <div v-show="suggest.length===0" style="text-align: center;line-height: 50px;color: #999999">没有多余的数据了</div>
          <div class="suggest-item" v-for="(item,index) in suggest" :key="index">
            <div class="item-left">
              <img :src="item.cover" alt="视频">
              <span class="item-video-length">{{ item.videoLength }}</span>
            </div>
            <div class="item-right">
              <p class="item-title" :title="item.description" @click="playVideo(item.videoId)">{{ item.description }}</p>
              <p class="item-uploader">
                <el-icon>
                  <UserFilled/>
                </el-icon>
                {{ item.nickname }}
              </p>
              <div class="item-info">
                <span class="item-play-count"> <el-icon size="16">
              <VideoPlay/>
            </el-icon>{{ item.playCount }}</span>
                <span class="item-upload-time"> <el-icon size="16">
              <Timer/>
            </el-icon>{{ item.upoadTime }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import Header from "@/components/Header.vue";
import {Timer, UserFilled, VideoPlay} from "@element-plus/icons-vue";
import {onBeforeMount, onMounted, reactive, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {getVideoLink} from "@/api/search_video.js";

const router = useRouter();
const route = useRoute();
let userInfo = ref();
let suggest = reactive([]);
let videoInfo = ref();
onBeforeMount(async () => {
  const videoId = route.query.p;
  await getVideoLink(videoId).then(result => {
    console.log(result);
    let data = result.data.data;
    userInfo.value = data.userInfo;
    videoInfo.value = data.videoInfo;
    suggest = data.suggest;
    document.title = data.videoInfo.description;
  })
})

function playVideo(id) {
  router.push("/video-page?p=" + id);
  router.go(0);
}
</script>

<style scoped>
.v-container {
  padding-top: 110px;
  margin: 0 auto;
  min-width: 1100px;
  width: 80vw;
  height: 80vh;
  min-height: 600px;

}

.c-left {
  display: inline-block;
  vertical-align: top;
  height: calc(100% - 100px);
  width: calc(100% - 430px);
  margin-right: 30px;

}

.c-right {
  display: inline-block;
  vertical-align: top;
  width: 400px;
  height: calc(100% - 120px);
  min-height: 505px;
}

.c-left-bottom {
  width: 100%;
  height: 100%;
  margin-top: 20px;
  display: flex;
  align-items: center;
  background: #000000;
  border-radius: 5px;
  box-shadow: 0px 3px 14px 0px rgba(100, 104, 108, 0.3);
}

.c-left-bottom video {
  width: 100%;
  object-fit: fill;

}

.c-right-bottom {
  background: #F1F2F3;
  padding: 10px 0px;
  border-radius: 5px;
  width: 100%;
  height: 100%;
  box-shadow: 0px 3px 14px 0px rgba(100, 104, 108, 0.3);
}

.video-title {
  font-size: 20px;
  margin: 0px 0px 10px;
  line-height: 40px;
}

.video-info {
  margin-bottom: 10px;
  margin-left: 5px;
}

.video-info > div {
  display: inline-block;
  font-size: 14px;
  color: #999999;
  padding-right: 15px;
}

.video-info i {
  line-height: 20px;
}

.video-info span {
  vertical-align: top;
}

.uploader-avatar, .uploader-info {
  display: inline-block;
  vertical-align: middle;
  height: 90px;
}

.uploader-info {
  display: inline-block;
  margin-left: 10px;
}

.uploader-avatar img {
  width: 60px;
  height: 60px;
  border-radius: 30px;
}

.username {
  margin: 5px 0px;
  font-size: 20px;
}

.signature {
  margin: 0px;
  font-size: 15px;
  color: #999999;
}


.suggest-title {
  margin-left: 10px;
  font-size: 17px;
  font-weight: 550;
}

.suggest-box {
  margin-top: 20px;
  height: calc(100% - 80px);
  min-height: 300px;
  padding: 15px 20px;
  overflow: auto;
}

.suggest-box::-webkit-scrollbar {
  display: none;
}

.suggest-item {
  margin-bottom: 10px;
}

.item-left {
  position: relative;
  display: inline-block;
  vertical-align: top;
  width: 144px;
}

.item-left img {
  width: 144px;
  height: 81px;
}

.item-video-length {
  position: absolute;
  bottom: 5px;
  right: 5px;
  background: rgba(100, 100, 100, 0.3);
  border-radius: 5px;
  color: #e0e0e0;
  padding: 3px 5px;
  font-size: 13px;
}

.item-right {
  display: inline-block;
  vertical-align: top;
  width: 200px;
  margin-left: 10px;
}

.item-title {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 5px;
}

.item-title:hover {
  cursor: pointer;
  color: #5096f5;
}

.item-uploader {
  margin: 5px;
  font-size: 14px;
  color: #999999;
}

.item-info {
  margin: 5px;
}

.item-info > span {
  display: inline-block;
  margin-right: 15px;
  font-size: 14px;
  color: #999999;
}
</style>