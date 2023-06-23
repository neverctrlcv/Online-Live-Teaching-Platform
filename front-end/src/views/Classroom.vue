<template>
  <div class="common-layout" id="container">
    <el-container>
      <el-main class="main">
        <div class="btn-list" v-if="store.state.identity==3">
          <ul>
            <li @click="audioSwitch">
              <img src="../assets/icon/classroom/classroom-audio.png" alt="audio" width="30" height="30">
              <p>选择音频</p>
            </li>
            <li @click="videoSwitch">
              <img src="../assets/icon/classroom/classroom-camera.png" alt="camera" width="30" height="30">
              <p>开启视频</p>
            </li>
            <li @click="screenSwitch">
              <img src="../assets/icon/classroom/classroom-computer.png" alt="computer" width="30" height="30">
              <p>共享屏幕</p>
            </li>
            <li @click="spotCheck">
              <img src="../assets/icon/classroom/classroom-jushou.png" alt="computer" width="30" height="30">
              <p>随机抽查</p>
            </li>
            <li>
              <button @click="leaveRoom">退出会议</button>
            </li>
          </ul>
        </div>
        <div class="btn-list" v-if="store.state.identity!=3">
          <ul>
            <li @click="audioSwitch">
              <img src="../assets/icon/classroom/classroom-audio.png" alt="audio" width="30" height="30">
              <p>选择音频</p></li>
            <li @click="videoSwitch">
              <img src="../assets/icon/classroom/classroom-camera.png" alt="camera" width="30" height="30">
              <p>开启视频</p></li>
            <li @click="screenSwitch">
              <img src="../assets/icon/classroom/classroom-computer.png" alt="computer" width="30" height="30">
              <p>共享屏幕</p></li>
            <li>
              <button @click="leaveRoom">退出会议</button>
            </li>
          </ul>
        </div>
        <div id="share_screen"></div>
        <div id="video_screen" :style="isShowVideo?'opacity:1':'opacity:0'"></div>
      </el-main>
      <el-aside class="aside-right" width="360px">
        <div class="aside-right-nav">
          <span @click="isChat=true" :style="isChat?'color:#409EFF':''">聊天</span>
          <span @click="isChat=false" :style="!isChat?'color:#409EFF':''">成员</span>
        </div>
        <div class="aside-right-main">
          <div class="chat" v-if="isChat">
            <div class="chat-top">
              <div class="chat-room">
                <div class="message" v-for="(item,index) in message" :key="index">
                  <div class="message-row1">
                    <span :class="userId===item.userId?'green':'blue'">{{ item.userId }}</span>
                    <span :class="userId===item.userId?'green':'blue'">{{ item.time }}</span>
                  </div>
                  <div class="message-row2">{{ item.message }}</div>
                </div>
              </div>
            </div>
            <div class="chat-bottom">
              <textarea rows="7" cols="36" v-model="mess"></textarea>
              <button @click="sendMessage">发送</button>
            </div>
          </div>
          <div class="member" v-if="!isChat">
            <div class="member-item" v-for="(item,index) in classMember" :key="index">
              <span>{{ item.name }}</span>
              <span>{{ item.jobId }}</span>
            </div>
            <div class="flush-btn" @click="getMember">
              刷新
            </div>
          </div>
        </div>
      </el-aside>
    </el-container>
  </div>
  <div class="spot-check-box" v-show="isSpotCheck&&store.state.identity===3">
    <p>抽查人员：{{ memberInfo }}</p>
    <button @click="isSpotCheck=false">确定</button>
  </div>
  <div class="spot-check-result" v-show="isSpotCheckResult&&store.state.identity===2">
    <p>{{ spotCheckResult }}</p>
    <button @click="isSpotCheckResult=false">确定</button>
  </div>
</template>


<script setup>
import TRTC from "trtc-js-sdk";
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref, reactive, onBeforeMount, onBeforeUnmount, watch} from "vue";
import {getClassMember, recordClassStatus, recordStatus} from '@/api/classroom';
import LibGenerateTestUserSig from '@/utils/lib-generate-test-usersig-es.min';
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";

const store = useStore();
const route = useRoute();
const router = useRouter();
const isChat = ref(true);
const mess = ref("");
let message = reactive([]);
let clientA = null;
let shareStream = null;
let localStream = null;
const sdkAppId = ;
let userId = store.state.name + "-" + store.state.jobId;
let userSig = "";
let secretKey = "";
let roomId = route.query.p;
let socket = null;
let socket1 = null;
let isSpotCheckResult = ref(false);
let spotCheckResult = ref("");
let classTId = 0;

let timer=0;
onBeforeMount(async () => {
  ElMessage({message: '系统自动开启课堂功能！', type: 'info'});
  await recordClassStatus(roomId, store.state.identity).then(result => {
    if (result.data.code === 200) {
      classTId = result.data.data;
    }
  })
  socket = new WebSocket("ws://127.0.0.1:8080/websocket/" + userId + "/" + roomId+"/"+classTId);
  socket1 = new WebSocket("ws://127.0.0.1:8080/classroomSocket/" + userId + "/" + roomId);
  //打开事件
  socket.onopen = function () {
    console.log("websocket已打开");
  };
  socket1.onopen = function () {
    console.log("websocket已打开");
  };
  socket.onmessage = function (msg) {
    mess.value = "";
    message.push(JSON.parse(msg.data));
  };
  socket1.onmessage = function (msg) {
    isSpotCheckResult.value = true;
    spotCheckResult.value = msg.data;
  };
 timer= setInterval(async ()=>{
    await getMember()
  },1500);


  userSig = genTestUserSig();
  //创建客户端
  clientA = TRTC.createClient({mode: 'rtc', sdkAppId, userId, userSig, useStringRoomId: true});
  //创建音频流
  localStream = TRTC.createStream({userId: userId, audio: true, video: true});
  //创建屏幕分享流
  shareStream = TRTC.createStream({userId: userId, screen: true});

  clientA.on('stream-added', async event => {
    const remoteStream = event.stream;
    await clientA.subscribe(remoteStream);
    if (remoteStream.getType() === 'auxiliary') {
      await remoteStream.play("share_screen")
    } else {
      const remoteId = 'remote' + remoteStream.getId();
      const videoDiv = document.createElement("div");
      videoDiv.style.height = '130px';
      videoDiv.style.width = '200px';
      videoDiv.className = 'remoteVideo';
      videoDiv.id=remoteId;
      document.getElementById("video_screen").appendChild(videoDiv);
      await remoteStream.play(videoDiv);
    }
  });


  // 屏幕分享流监听屏幕分享停止事件
  shareStream.on('screen-sharing-stopped', async event => {
    await clientA.unpublish(shareStream);
    shareStream.close();
    switch3 = !switch3;
  });
  await clientA.join({roomId: roomId});
  await localStream.initialize();
  await clientA.publish(localStream);
  const remoteId = 'remote' + localStream.getId();
  const videoDiv = document.createElement("div");
  videoDiv.style.height = '130px';
  videoDiv.style.width = '200px';
  videoDiv.className = 'remoteVideo';
  videoDiv.id=remoteId;
  document.getElementById("video_screen").appendChild(videoDiv);
  await localStream.play(videoDiv);
  localStream.muteAudio();
  localStream.muteVideo();

})

let isLeave = ref(false);
onMounted(async () => {
  window.addEventListener("keydown",handleEvent);
  if (store.state.identity === 2) {
    document.addEventListener('visibilitychange', (e) => {
      e.stopPropagation();
      if (document.visibilityState === 'hidden') { // 离开当前tab标签
        console.log('离开当前tab标签');
        isLeave.value = true;
        // 暂停视频
      } else {
        // 播放视频
        console.log('回到当前tab标签');
        isLeave.value = false;
      }
    })
  }
})
onBeforeUnmount(() => {
  clearInterval(timer);
  window.removeEventListener("keydown",handleEvent);
  document.removeEventListener('visibilitychange', () => {
    console.log("事件撤销了")
  });
})
const isShowVideo=ref(true);
function handleEvent(e){
  if (e.ctrlKey&&e.keyCode===70){
    e.preventDefault();
    isShowVideo.value=!isShowVideo.value;
  }
}
watch(isLeave, async (newValue, oldValue) => {
  if (newValue) {
    await recordStatus(userId, classTId).then(result => {
      if (result.data.code === 200) {
        console.log("记录成功！");
      }
    })
  }
})

/**
 * 发送消息
 */
function sendMessage() {
  socket.send(mess.value);

}

let switch1 = false;

/**
 * 音频开关
 */
function audioSwitch() {
  switch1 = !switch1;
  if (switch1) {
    localStream.unmuteAudio();
    ElNotification({title:'音频分享',message:'麦克风已打开！'})
  } else {
    localStream.muteAudio();
    ElNotification({title:'音频分享',message:'麦克风已关闭！'})
  }
}

let switch2 = ref(false);

/**
 * 视频开关
 * @returns {Promise<void>}
 */
function videoSwitch() {
  switch2.value = !switch2.value;
  if (switch2.value) {
    localStream.unmuteVideo();
    ElNotification({title:'视频分享',message:'视频已打开！'})
  } else {
    localStream.muteVideo();
    ElNotification({title:'视频分享',message:'视频已关闭！'})
  }
}

let switch3 = false;

/**
 * 分享屏幕
 * @returns {Promise<void>}
 */
async function screenSwitch() {
  switch3 = !switch3;
  if (switch3) {
    try {
      await shareStream.initialize();
      await clientA.publish(shareStream, {isAuxiliary: true});
      ElNotification({title:'屏幕分享',message:'成功分享屏幕！'})
    } catch (error) {
      console.log("屏幕分享失败!" + error);
    }
  } else {
    await clientA.unpublish(shareStream);
    shareStream.close();
    ElNotification({title:'屏幕分享',message:'屏幕分享已关闭！'})
  }
}

/**
 * 获取userSig
 * @returns {*}
 */
function genTestUserSig() {
  const generator = new LibGenerateTestUserSig(sdkAppId, secretKey, 604800);
  return generator.genTestUserSig(userId)
}

async function leaveRoom() {
  await clientA.leave();
  socket.close();
  socket1.close();
  ElMessage({message:'成功退出直播间！',type:'success'});
  await router.push("/");

}

let classMember = reactive([]);
let min = 0;
let max = 0;
async function getMember() {
  await getClassMember(roomId).then(result => {
    classMember.length = 0;
    let data = result.data.data;
    for (let i = 0; i < data.length; i++) {
      classMember.push({
        name: data[i].split("-")[0],
        jobId: data[i].split("-")[1],
      })
    }
    max = classMember.length - 1;
  })
}

let memberInfo = ref("");

let isSpotCheck = ref(false);

function spotCheck() {
  let flag = true;
  let name = null;
  while (flag) {
    let index = Math.floor(Math.random() * (max - min + 1)) + min;
    name = classMember[index].name + "-" + classMember[index].jobId;
    if (name !== store.state.name + "-" + store.state.jobId) {
      flag = false;
    }
  }
  isSpotCheck.value = true
  memberInfo.value = name;
  socket1.send(name);
}


</script>

<style scoped>
.common-layout {

}

.aside-right {
  position: relative;
  height: 100vh;
  border-left: 1px solid #EEEEEE;
  overflow: auto;
}

.main {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
  background: #222222;
  min-width: 1200px;
}


.btn-list {
  z-index: 100;
  position: absolute;
  top: 0px;
  left: 50%;
  transform: translateX(-50%);
  background: #ffffff;
  border-radius: 0px 0px 5px 5px;
}

.btn-list li:hover {
  cursor: pointer;
}

.btn-list ul {
  margin: 0;
  padding: 0;
}

.btn-list li {
  text-align: center;
  margin: 5px 0px;
  width: 70px;
  display: inline-block;
  padding: 0px 15px;
  vertical-align: middle;
}

.btn-list p {
  margin: 0px;
  font-size: 13px;
  width: 70px;
  color: #8a8a8a;
}

.btn-list button {
  width: 70px;
  height: 32px;
  border: 1px solid #f89898;
  font-size: 13px;
  background: #ffffff;
  border-radius: 5px;
  color: #f89898;
}

.aside-right-nav {
  position: fixed;
  width: 360px;
  display: flex;
  justify-content: space-around;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
}

.aside-right-nav span {
  font-size: 17px;
  padding: 10px 10px;
}

.aside-right-main {
  padding-top: 50px;
}

.chat-top {
  width: 100%;
  height: calc(100vh - 210px - 60px);
  overflow: auto;
}

.chat-top::-webkit-scrollbar {
  display: none;
}

.chat-bottom {
  background: #ffffff;
  position: absolute;
  bottom: 0px;
  width: 100%;
  height: 210px;
  border-top: 1px solid #e0e0e0;
}

.chat-bottom textarea {
  width: 350px;
  font-size: 17px;
  font-family: "Microsoft YaHei";
  border: solid 2px #e0e0e0;
  resize: none;
  outline: none;
  border-radius: 5px;
  margin-top: 5px;
}

.chat-bottom textarea:focus {
  border: solid 2px #79bbff;
}

.chat-bottom button {
  width: 80px;
  background: #409EFF;
  border: none;
  outline: none;
  font-size: 17px;
  color: #ffffff;
  padding: 5px 10px;
  border-radius: 5px;
  margin-left: 275px;
}

.message {
  width: 95%;
  margin: 0px auto 10px;

}

.message-row1 {
  display: flex;
  justify-content: space-between;
  font-size: 15px;
}

.message-row2 {
  padding: 5px 0px;
  font-size: 17px;
}

.member {
  height: calc(100vh - 60px);
  overflow: auto;
}

.member::-webkit-scrollbar {
  display: none;
}

.member-item {
  width: 90%;

  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  font-size: 17px;
  font-family: "Microsoft YaHei";
  line-height: 30px;
}

#share_screen {
  width: 100%;
  height: 100%;
}

#video_screen {
  position: absolute;
  top: 20%;
  left: 0%;
  width: 200px;
  height: 260px;
  overflow: auto;
}
#video_screen::-webkit-scrollbar{
  display: none;
}
.blue {
  color: #79bbff;
}

.green {
  color: #95d475;
}

.flush-btn {
  font-size: 13px;
  text-align: center;
}

.flush-btn:hover {
  cursor: pointer;
  color: #79bbff;
}

.spot-check-box, .spot-check-result {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -60%);
  width: 300px;
  background: #ffffff;
  text-align: center;
  border-radius: 5px;
  color: #999999;
}

.spot-check-box button, .spot-check-result button {
  border: none;
  outline: none;
  padding: 5px 10px;
  margin: 20px 0px;
  background: #5096f5;
  color: #ffffff;
  width: 100px;
  border-radius: 5px;
}

.spot-check-box button:hover, .spot-check-result button:hover {
  background: #79bbff;
  cursor: pointer;
}


</style>