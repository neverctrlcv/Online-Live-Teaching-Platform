<template>
  <Header :headerStyle="headerStyle"></Header>
  <main>
    <div style="height: 65px"></div>
    <div class="about-box">
      <div class="info-row1">
        <img src="src/assets/img/me.jpg" alt="image">
      </div>
      <div class="info-row2">
        <div class="my-name">
          <span>never debug</span>
        </div>
        <div class="my-info">
          <span>男</span>
          <span>23</span>
        </div>
        <div class="signature">
          <span>不要假装努力，结果不会陪你演戏！</span>
        </div>
      </div>
      <div class="info-row3">
        <p>项目目的及意义：</p>
        <div>
          <span>打造一套操作简单且功能齐全的在线直播教学平台，探索一种优化在线教学效果的新模式，从而激发学生在线学习的主观能动性和师生之间的互动，提高教与学的效率。
          同时促进公平教育的发展，让偏远地区的学生可以随时享受一二线城市的优质学习资源。</span>
        </div>
      </div>
      <div class="info-row4">
        <p>项目开发环境：</p>
        <div>
          <span>Windows11、IDEA2022.3.3、WebStorm2022.3.4、DataGrip2022.3.3、JDK-1.8</span>
        </div>
      </div>
      <div class="info-row5">
        <p>相关技术栈：</p>
        <div>
          <table>
            <tbody>
            <tr>
              <td><a href="https://cn.vuejs.org/" target="_blank">Vue.js</a></td>
              <td><a href="https://element-plus.org/zh-CN/" target="_blank">Element-Plus</a></td>
              <td><a href="https://www.typescriptlang.org/" target="_blank">TypeScript</a></td>
              <td><a href="https://axios-http.com/" target="_blank">axios</a></td>
              <td><a href="https://cn.vitejs.dev/" target="_blank">Vite</a></td>
              <td><a href="https://router.vuejs.org/zh/" target="_blank">Vue-Route</a></td>
            </tr>
            <tr>
              <td><a href="https://vuex.vuejs.org/zh/" target="_blank">Vuex</a></td>
              <td><a href="https://nodejs.org/en" target="_blank">Nodejs</a></td>
              <td><a href="https://spring.io/" target="_blank">SpringBoot</a></td>
              <td><a href="https://mybatis.org/mybatis-3/" target="_blank">MyBatis</a></td>
              <td><a href="https://redis.io/" target="_blank">Redis</a></td>
              <td><a href="https://maven.apache.org/" target="_blank">Maven</a></td>
            </tr>
            <tr>
              <td><a href="https://www.mysql.com/" target="_blank">MySQL</a></td>
              <td><a href="https://www.nginx.com/" target="_blank">Nginx</a></td>
              <td><a href="https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html"
                     target="_blank">JDK</a></td>
              <td><a href="https://cloud.tencent.com/" target="_blank">实时音视频SDK</a></td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div class="info-row6">
        <p>相关链接：</p>
        <div>
          <span>CSDN:<a href="https://blog.csdn.net/m0_73845616"
                        target="_blank">https://blog.csdn.net/m0_73845616</a></span>
          <span>Github:<a href="https://github.com/runhuo" target="_blank">https://github.com/runhuo</a></span>
        </div>
      </div>
    </div>
  </main>
  <Footer></Footer>

</template>

<script>
import Header from "../components/Header.vue";
import Footer from "../components/Footer.vue";
import {ref} from "vue";

export default {
  name: "About",
  components: {Header, Footer},
  setup() {
    const headerStyle = ref('font-black');
    return {
      headerStyle
    }
  }
}
</script>

<style scoped>
main {
  position: relative;
  background: #F2F4F7;
  height: 1050px;
}

.about-box {
  position: relative;;
  background: #ffffff;
  width: 1000px;
  margin: 50px auto;
  min-height: 300px;
  border-radius: 10px;
}

.info-row1 {
  width: 150px;
  padding: 30px 0px 15px;
  margin: 0 auto;
}

.info-row1 img {
  width: 150px;
  height: 150px;
  border-radius: 75px;
}

.info-row1 img:hover {
  animation-name: img-animation;
  animation-duration: 1s;
  animation-direction: normal;
  animation-timing-function: ease;
}

@keyframes img-animation {
  0% {
    transform: scale(100%, 100%);
  }
  100% {
    transform: scale(110%, 110%);
  }
}

.info-row2 {
  width: 500px;
  margin: 0 auto;
}

.my-name {
  text-align: center;
  font-size: 19px;
}

.my-info {
  width: 80px;
  margin: 10px auto;
  display: flex;
  font-size: 15px;
  justify-content: space-between;
  color: #909399;
}

.signature {
  color: #909399;
  font-size: 15px;
  text-align: center;
}

.info-row3, .info-row4, .info-row5, .info-row6 {
  width: 850px;
  margin: 30px auto;
}

.info-row3 p, .info-row4 p, .info-row5 p, .info-row6 p {
  margin: 10px 0px;
  color: #606266;
  font-size: 16px;
}

.info-row3 div, .info-row4 div {
  color: #909399;
  text-align: justify;
  margin: 5px 0px;
  line-height: 30px;
  font-size: 16px;
}

.info-row5 td {
  width: 155px;
  text-align: center;
  line-height: 50px;
}

.info-row5 a, .info-row6 a {
  text-decoration: none;
  color: #909399;
}

.info-row5 a:hover, .info-row6 a:hover {
  color: #409EFF;
}

.info-row6 div {
  display: flex;
  justify-content: space-between;
  color: #909399;
  padding-bottom: 50px;
}
</style>