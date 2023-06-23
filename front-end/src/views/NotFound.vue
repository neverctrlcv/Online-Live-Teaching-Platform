<template>
  <div class="not-found-box">
    <img :src="notFound" alt="404">
    <p>网页<strong>{{ time }}</strong>秒后自动跳转到首页！</p>
    <a @click="toHome">返回首页</a>
  </div>
</template>

<script setup>
import notFound from '@/assets/img/404/404.png';
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();
let time = ref(5);
let timer = null;
onMounted(() => {
  timer = setInterval(() => {
    time.value--;
    if (time.value === 0) {
      clearInterval(timer);
      router.push("/");
    }
  }, 1000);
})

function toHome() {
  clearInterval(timer);
  router.push('/');
}
</script>

<style scoped>
.not-found-box {
  text-align: center;
  padding-top: 100px;
}

.not-found-box a:hover {
  cursor: pointer;
  color: #79bbff;
}
</style>