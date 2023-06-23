<template>
  <div class="balance">
    <span class="sms-balance">短信余额：{{ smsCount }}/1000条</span>
    <span class="rtc-balance">音视频余额：{{ rtcCount }}/10000分钟</span>
  </div>
  <div class="box-bottom">
    <div class="rtc-usage-details">
      <div>
        <el-select v-model="value1" class="m-2" placeholder="近七天" size="large">
          <el-option label="近七天" value="7"/>
          <el-option label="近十五天" value="15"/>
        </el-select>
      </div>
      <div class="rtc-title">实时音视频使用情况</div>
      <div id="echarts"></div>
    </div>
    <div class="log-table">
      <div class="log-title">系统日志信息</div>
      <div class="log-selector">
        <el-select v-model="value2" class="m-2" placeholder="近七天" size="large">
          <el-option label="近七天" value="7"/>
          <el-option label="近十五天" value="15"/>
        </el-select>
      </div>
      <div class="log-info">
        <el-table :data="tableData" stripe style="width: 100%">
          <el-table-column prop="logInfo" label="日志" width="480"/>
          <el-table-column prop="time" label="时间" width="120"/>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref, reactive, watch} from "vue";
import * as echarts from 'echarts'
import {getLogInfo, getRTCUsage, getSmsCountAndRtcTime} from "@/api/system_resource";

const value1 = ref("7");
const value2 = ref("7");
const tableData = reactive([]);
const smsCount = ref("")
const rtcCount = ref("")

onMounted(async () => {

  await getSmsCountAndRtcTime().then(result => {
    console.log(result);
    smsCount.value = result.data.data.smsCount;
    rtcCount.value = result.data.data.rtcTime;
  })
  await getRTCUsageByAdmin();
  await getLogInfoByAdmin();
  // 绘制图表

  window.onresize = function () { // 自适应大小
    myChart.resize();
  };
})
watch(value1, async (newValue, oldValue) => {
  await getRTCUsageByAdmin();
})
watch(value2, async (newValue, oldValue) => {
  await getLogInfoByAdmin();
})

async function getRTCUsageByAdmin() {
  let myChart = echarts.init(document.getElementById("echarts"));
  await getRTCUsage(value1.value).then(result => {
    console.log(result);
    let rtcArr = [];
    let dateArr = [];
    for (let i = 0; i < result.data.data.length; i++) {
      rtcArr.push(result.data.data[i].rtcUsage);
      dateArr.push(result.data.data[i].useDate);
      myChart.setOption({
        xAxis: {
          name: '日期',
          data: dateArr
        },
        yAxis: {
          name: '使用量/分钟'
        },
        series: [
          {
            name: "用户量",
            type: "line",
            data: rtcArr
          }
        ]
      });
    }
  })
}

async function getLogInfoByAdmin() {
  tableData.length = 0;
  await getLogInfo(value2.value).then(result => {
    for (let i = 0; i < result.data.data.length; i++) {
      tableData.push(result.data.data[i]);
    }
    console.log(result);
  })
}


</script>

<style scoped>
.balance {
  margin-top: 20px;
}

.balance span {
  margin-right: 30px;
  background: #ffffff;
  padding: 15px 20px;
  border-radius: 5px;
}

.box-bottom {
  margin-top: 60px;
  width: calc(100vw - 270px);
}

.box-bottom > div {
  display: inline-block;
  vertical-align: bottom;
}

.rtc-usage-details {

  width: calc(100vw - 230px - 700px);
}

.rtc-title {
  text-align: right;
  line-height: 30px;
  padding-right: 20px;
  color: #666666;
  margin: 30px 0px 10px;
}

#echarts {
  width: calc(100vw - 230px - 700px);
  height: calc(100vh - 60px - 320px);
  background: #ffffff;
  border-radius: 5px;
  overflow: auto;
}

.log-table {
  position: relative;
  margin-left: 35px;
  width: 600px;
}

.log-info {
  height: calc(100vh - 60px - 320px);
  background: #ffffff;
  overflow: auto;
  border-radius: 5px;
}

.log-info::-webkit-scrollbar {
  display: none;
}

.log-title {
  font-size: 25px;
  color: #666666;
  margin: 0px 0px 30px 0px;
}

.log-selector {
  position: absolute;
  top: 0px;
  right: 0px;
}
</style>