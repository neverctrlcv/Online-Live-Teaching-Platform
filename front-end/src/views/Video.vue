<template>
  <Header></Header>
  <main>
    <div class="m-top" :style="!isSearch?'top:300px':'top:100px'">
      <div class="m-search">
        <input type="text" placeholder="请输入关键字！" v-model="keywords">
        <button @click="searchVideo">搜索</button>
      </div>
    </div>
    <div class="m-bottom" v-infinite-scroll="load">

      <div class="m-video-box" v-show="isSearch" v-loading="isLoading">
        <p v-show="isNull" style="color: #666666;text-align: center;margin-top: 150px">
          没有找到相应的数据！</p>
        <div class="m-video-item" v-for="(item,index) in videoData" :key="index" v-show="!isLoading">
          <div class="m-video-row1">
            <div class="video-image">
              <img :src="item.cover" alt="video">
            </div>
            <div class="video-info">
              <span>{{ item.playCount }}</span>
              <span>{{ item.videoLength }}</span>
            </div>
          </div>
          <div class="m-video-row2">
            <span @click="playVideo(item.videoId)" class="video-title"
                  :title="item.description">{{ item.description }}</span>
          </div>
          <div class="m-video-row3">
            <span class="video-author">{{ item.nickname }}</span>
            <span class="video-date">{{ item.uploadTime }}</span>
          </div>
        </div>
      </div>
      <div v-show="loadingCount!==15" class="loading-result">没有多余的数据了！</div>
    </div>
  </main>
  <Footer></Footer>
</template>

<script setup>
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import {reactive, ref, watch} from "vue";
import {search} from "@/api/search_video.js";
import {ElMessage} from "element-plus";
import {useRouter} from "vue-router";

const router=useRouter();
const isSearch = ref(false);
const isLoading = ref(false);
const keywords = ref("");
const videoData = reactive([]);
let loadingCount = ref(15);
let index = 0;
let isNull = ref(false);
console.log("最开始"+index);
async function searchVideo() {
  index=0;
  isSearch.value = true;
  isLoading.value = true;
  videoData.length = 0;
  await search(index, keywords.value).then(result => {
    console.log(result);
    let videoList = result.data.data.result;
    isNull.value=videoList.length===0?true:false;
    for (let i = 0; i < videoList.length; i++) {
      videoData.push(videoList[i]);
    }
    index = result.data.data.index;
    isLoading.value = false;
    loadingCount.value = videoList.length;
    console.log("第一次："+index);
  })
}

async function load() {
  if( isSearch.value&&loadingCount.value===15){
    await search(index, keywords.value).then(result => {
      console.log(result);
      let videoList = result.data.data.result;
      for (let i = 0; i < videoList.length; i++) {
        videoData.push(videoList[i]);
      }
      index = result.data.data.index;
      loadingCount.value = videoList.length;
      console.log("之后："+index);
    })
  }

}

function playVideo(id) {
  router.push("/video-page?p=" + id);
}

</script>

<style scoped>
main {
  z-index: 0;
}

.m-top {
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  width: 574px;
  margin: 0px auto;
  z-index: 50;
}

.m-search input {
  width: 460px;
  height: 35px;
  outline: none;
  border: solid 2px #d4d6d9;
  border-radius: 5px 0px 0px 5px;
  padding: 0px 10px;
}

.m-search input:focus {
  border: solid 2px #79bbff;
}

.m-search button {
  width: 90px;
  height: 38px;
  border: none;
  vertical-align: bottom;
  font-size: 16px;
  color: #ffffff;
  background: #3A8BFF;
  border-radius: 0px 5px 5px 0px;
}

.m-search button:hover {
  background: #79bbff;
}

.m-bottom {
  padding-top: 160px;
  min-height: 800px;
}

/*视频模块的样式*/
.m-video-box {
  width: 1300px;
  margin: 20px auto;
}

.m-video-box > div {
  display: inline-block;
  vertical-align: top;
  margin: 10px 10px 20px;
}

.m-video-item {
  width: 240px;
}

.m-video-row1 {
  position: relative;
  width: 100%;
}

.video-image img {
  width: 100%;
  height: 135px;
  border-radius: 5px;
}

.video-info {
  position: absolute;
  bottom: 5px;
  left: 5%;
  color: #ffffff;
  display: flex;
  width: 90%;
  justify-content: space-between;
}

.m-video-row2 {
  width: 100%;
  color: #18191C;

}

.m-video-row3 {
  width: 100%;
  margin: 10px auto;
  display: flex;
  justify-content: space-between;
  color: #909399;
  font-size: 14px;
}

.video-title {
  width: 240px;
  overflow: hidden; /*超出部分隐藏*/
  text-overflow: ellipsis; /*超出部分省略号表示*/
  white-space: nowrap; /*强制单行显示*/
  display: inline-block; /*转换为行内块元素*/
}

.video-title:hover {
  color: #409EFF;
  cursor: pointer;
}
.loading-result{
  text-align: center;
  font-size: 15px;
  color: #999999;
}
</style>