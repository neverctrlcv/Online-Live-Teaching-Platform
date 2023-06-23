<template>
  <Header></Header>
  <main>
    <section class="section1" :style="`background:url(${HomeBg1}) no-repeat center center/cover;`">
      <div class="inner-bg"></div>
      <div class="title">
        <span>知识创造财富</span>
        <span>读书破万卷</span>
      </div>
    </section>
    <section class="section2">
      <div class="content-box">
        <div class="content-item" :style="`background:url(${HomeBg2}) no-repeat 50% 13%`">
          <div class="item-row1">
            <img :src="HomeIcon1" alt="icon1" width="50" height="50">
          </div>
          <div class="item-row2">聚集全国的教学资源</div>
          <div class="item-row3">
            全国各地的教师教学经验、资料在本平台都可以得到分享，每个人都可以自主学习，让教育变得无障碍。
          </div>
        </div>
        <div class="content-item" :style="`background:url(${HomeBg2}) no-repeat 50% 13%`">
          <div class="item-row1">
            <img :src="HomeIcon2" alt="icon2" width="50" height="50">
          </div>
          <div class="item-row2">创新引领课堂革命</div>
          <div class="item-row3">推动线上与线下混合教学，加强线上教学的管理，提高线上教学的质量，提高学生自主学习能力。
          </div>
        </div>
        <div class="content-item" :style="`background:url(${HomeBg2}) no-repeat 50% 13%`">
          <div class="item-row1">
            <img :src="HomeIcon3" alt="icon3" width="50" height="50">
          </div>
          <div class="item-row2">突破时间与空间的限制</div>
          <div class="item-row3">让全国各地的学生都能获取优秀的学习资源，突破时间与空间上的限制，促进教育公平的发展。</div>
        </div>
      </div>
    </section>
    <section class="section3" :style="`background:url(${HomeBg3}) no-repeat center center/cover;`">
      <p>为什么选择本平台</p>
      <div class="section3-box">
        <div class="section3-item">
          <div class="section3-row1">
            <img :src="HomeIcon4" alt="icon4" width="130" height="130">
          </div>
          <div class="section3-row2">优质的学习环境</div>
          <div class="section3-row3">本平台汇聚了丰富的教学资源，学生随时可以进行学习，没有时间和空间上的限制</div>
        </div>
        <div class="section3-item">
          <div class="section3-row1">
            <img :src="HomeIcon5" alt="icon5" width="130" height="130">
          </div>
          <div class="section3-row2">提高自主学习能力</div>
          <div class="section3-row3">学生在本平台可以根据自己的兴趣搜索对应的资料进行学习，不需要很高的成本。</div>
        </div>
        <div class="section3-item">
          <div class="section3-row1">
            <img :src="HomeIcon6" alt="icon6" width="130" height="130">
          </div>
          <div class="section3-row2">灵活的监督功能</div>
          <div class="section3-row3">线上教学时可以很好地监督学生，禁止在课堂上做与学习无关的事情，提高教学质量。</div>
        </div>
      </div>
    </section>
    <section class="section4"></section>

  </main>
  <Footer></Footer>
</template>

<script>
import Header from "../components/Header.vue";
import HomeBg1 from "../assets/img/home/home-bg1.jpg";
import HomeIcon1 from '../assets/icon/home/home-icon1.png';
import HomeIcon2 from '../assets/icon/home/home-icon2.png';
import HomeIcon3 from '../assets/icon/home/home-icon3.png';
import HomeBg2 from '../assets/img/home/home-bg2.png';
import HomeIcon4 from '../assets/icon/home/home-icon4.png';
import HomeIcon5 from '../assets/icon/home/home-icon5.png';
import HomeIcon6 from '../assets/icon/home/home-icon6.png';
import HomeBg3 from '../assets/img/home/home-bg3.png';
import Footer from "../components/Footer.vue";

export default {
  name: "Home",
  components: {
    Footer,
    Header
  },
  setup() {
    return {
      HomeBg1,
      HomeIcon1,
      HomeIcon2,
      HomeIcon3,
      HomeBg2,
      HomeIcon4,
      HomeIcon5,
      HomeIcon6,
      HomeBg3
    }
  }
}
</script>

<style scoped>

.section1 {
  position: relative;
  width: 100%;
  height: 500px;
}

.inner-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0px;
  top: 0px;
  background: rgba(0, 0, 0, 0.6);
}

.title {
  position: absolute;
  width: 550px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #ffffff;
  font-size: 54px;
  line-height: 80px;
}

.title span:nth-child(1) {
  display: block;
  width: 550px;
  text-align: left;
}

.title span:nth-child(2) {
  display: block;
  width: 550px;
  text-align: right;
}

.section2 {
  width: 100%;
  height: 344px;
  background: #151527;
}

.content-box {
  width: 1200px;
  margin: 0 auto;
}

.content-item {
  display: inline-block;
  width: 400px;
  vertical-align: top;
  color: #ffffff;
}

.item-row1 {
  text-align: center;
  margin: 50px 0px 20px;
}

.item-row2 {
  font-size: 25px;
  line-height: 60px;
  text-align: center;
}
.item-row3{
  width: 80%;
  margin: 10px auto;
  line-height: 26px;
  text-align: justify;
}
.section3{
  width: 100%;
  margin-bottom: 150px;

}
.section3>p{
  font-size: 40px;
  color: #32325D;
  padding: 60px 0px 50px;
  width: 1200px;
  margin: 30px auto;
}

.section3-box{
  width: 1200px;
  margin: 0 auto;
}
.section3-item{
  display: inline-block;
  width: 300px;
  height: 340px;
  padding: 0px 30px;
  background: #ffffff;
  box-shadow: 0px 2px 14px 0px rgba(100, 104, 108, 0.16);
  margin: 0px 20px;
  border-radius: 5px;
}
.section3-row1{
  text-align: center;
margin-bottom: 40px;
  border-bottom: solid 1px #EEEEEE;
}
.section3-row2{
  font-size: 24px;
  color: #32325D;
  line-height: 30px;
  padding-bottom: 20px;
  text-align: center;
}
.section3-row3{
  font-size: 18px;
  color: #6B7C93;
  line-height: 32px;
  text-align: left;
  padding-bottom: 25px;
}
.section3-item:hover{
  box-shadow: 0px 2px 14px 0px rgba(110 , 104, 108, 0.4);
}
</style>