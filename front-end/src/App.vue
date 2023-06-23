<script>
import {onBeforeMount,onBeforeUnmount} from "vue";
import {useStore} from "vuex";

export default {
  name: 'App',
  setup() {
    const store = useStore();
    onBeforeMount(() => {
      if (sessionStorage.getItem("store") ) {
        store.replaceState(Object.assign({}, store.state,JSON.parse(sessionStorage.getItem("store"))))
      }

      //在页面刷新时将vuex里的信息保存到sessionStorage里
      window.addEventListener("beforeunload",()=>{
        sessionStorage.setItem("store",JSON.stringify(store.state))
      })
    })
    onBeforeUnmount(()=>{
      window.removeEventListener("beforeunload",()=>{
        console.log("监听时间被移除了")
      });
    })


  }
}
</script>

<template>
  <router-view></router-view>
</template>

<style scoped>


</style>
