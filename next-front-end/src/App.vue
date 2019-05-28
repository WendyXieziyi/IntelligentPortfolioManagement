<template>
  <div id="app">
    <el-container>
      <el-header class="header">
        <div class="header-content">
          <div class="header-content_left">
            <div class="header-logo">Portfolio</div>
            <div>
              <router-link
                class="header-link"
                active-class="header-link--active"
                to="/login"
                >资产管理</router-link
              >
<<<<<<< HEAD
			  <router-link
                class="header-link"
                active-class="header-link--active"
                to="/risk"
                >风险评测</router-link
              >
			  <router-link
=======
              <router-link
>>>>>>> 58a1b5a17d2f399fa5e74b59293b5aca182d9a6a
                class="header-link"
                active-class="header-link--active"
                to="/course"
                >学习园地</router-link
              >
              <router-link
                class="header-link"
                active-class="header-link--active"
                exact
                to="/"
                >首页</router-link
              >
            </div>
          </div>
          <div class="header-content_right">
            <router-link
              class="header-link"
              active-class="header-link--active"
              to="/login"
              >登陆/注册</router-link
            >
          </div>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /*text-align: center;*/
  /*color: #2c3e50;*/
}

.header {
  background-color: #333333;
  height: 60px;
}
.header-content {
  color: white;
  max-width: 900px;
  margin: 10px auto;
  line-height: 40px;
  display: flex;
  justify-content: space-between;

  padding: 0 20px;
  box-sizing: border-box;
}
.header-content_left {
  display: flex;
}
.header-logo {
  font-weight: bold;
  padding-right: 10px;
}

.header-link {
  color: white;
  text-decoration: none;
  padding: 0 10px;
}

.main {
  max-width: 900px;
  margin: 0 auto;
  width: 100%;
  min-height: calc(100vh - 60px);
}

.header-link--active {
  color: red;
}

.content p {
  line-height: 1.5em;
  color: #2d2c2f;
}
</style>
