import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import echarts from "echarts";
import ElementUI from "element-ui";
import "normalize.css";
import "element-ui/lib/theme-chalk/index.css";

Vue.prototype.$echarts = echarts;

// Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
