<template>
  <div class="login-window"
       :style="`background:url(${LoginBg}) no-repeat center center/cover;`">
    <div class="login-left">
      <img :src="LoginLogo" alt="logo">
      <img :src="Whale1" alt="whale">
      <img :src="Whale2" alt="whale">
      <img :src="Whale3" alt="whale">
    </div>
    <div class="login-right">
      <router-view></router-view>
    </div>


  </div>
</template>

<script>
import LoginBg from '../assets/img/login/login-bg.jpg';
import LoginLogo from '../assets/img/login/login-logo.png';
import Whale1 from '../assets/img/login/whale1.png';
import Whale2 from '../assets/img/login/whale2.png';
import Whale3 from '../assets/img/login/whale3.png';

export default {
  name: "Login",
  setup() {
    return {
      LoginBg,
      LoginLogo,
      Whale1,
      Whale2,
      Whale3
    }
  }
}
</script>

<style scoped>
.login-window {
  width: 100%;
  height: 100vh;
  min-width: 1300px;
}

.login-window > div {
  position: relative;
  display: inline-block;
  vertical-align: top;
  width: 50%;
  height: 100%;
}

.login-left img {
  width: 300px;
  height: 300px;
}

.login-left img:nth-child(1) {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.login-left img:nth-child(2) {
  position: absolute;
  top: 40%;
  left: 5%;
  transform: translate(-50%, -50%) rotateZ(5deg);
  animation: whale1-move 1s linear forwards;
}

.login-left img:nth-child(3) {
  position: absolute;
  top: 10%;
  left: 85%;
  animation: whale2-move 1s linear forwards;
  transform: translate(-50%, -50%) rotateZ(30deg);

}

.login-left img:nth-child(4) {
  position: absolute;
  top: 90%;
  left: 95%;
  transform: translate(-50%, -50%) rotateZ(30deg);
  animation: whale3-move 1s linear forwards;
}


@keyframes whale1-move {
  0% {
    top: 40%;
    left: 5%;
  }
  100% {
    top: 60%;
    left: 32%;
  }
}

@keyframes whale2-move {
  0% {
    top: 10%;
    left: 85%;
  }
  100% {
    top: 35%;
    left: 55%;
  }
}

@keyframes whale3-move {
  0% {
    top: 90%;
    left: 95%;
  }
  100% {
    top: 60%;
    left: 70%;
  }
}



</style>