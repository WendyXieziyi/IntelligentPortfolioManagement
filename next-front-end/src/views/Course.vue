<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-menu
          :default-active="$route.path"
          :default-openeds="['1', '2']"
          router
        >
          <el-submenu index="1">
            <template slot="title">
              <span>必修课</span>
            </template>
            <el-menu-item index="/course">入市手册</el-menu-item>
            <el-menu-item index="/course/risk-education">风险教育</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <span>选修课</span>
            </template>
            <el-menu-item index="/course/stock-option"
              >股票期权专题</el-menu-item
            >
            <el-menu-item index="/course/securities-topic"
              >证券专题</el-menu-item
            >
            <el-menu-item index="/course/virtual-currency"
              >虚拟货币专题</el-menu-item
            >
          </el-submenu>
          <a
            target="_blank"
            style="text-decoration: none;color: inherit"
            href="http://nrng.gtja.com/nrngWeb/resources/nrng/sjMockIndex.html"
          >
            <el-menu-item>模拟交易</el-menu-item>
          </a>
        </el-menu>
      </el-col>
      <el-col :span="18">
        <router-view />
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Course",
  data() {
    return {
      myData: ""
    };
  },
  mounted() {}
};
</script>
