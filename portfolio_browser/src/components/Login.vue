<template>
  <el-main>
    <h1>Portfolio</h1>
    <span>基于深度强化学习构建投资组合的动态管理系统</span>
    <div class="formWrapper">
      <el-form id="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="user.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
        <span style="font-size: 6px; ">*已注册请点击登录，注册将自动创建新账户</span>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
          <el-button @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-main>
</template>

<script>
  export default {
    name: "login",
    data() {
      return {
        user: {
          username: '',
          password: '',
        }
      }
    },
    methods:{
      login:function () {
        var _this = this;
        var username = this.user.username;
        var password = this.user.password;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
          if(xmlhttp.readyState===4&&xmlhttp.status===200){
            if(JSON.parse(xmlhttp.responseText).result===true) {
              localStorage.setItem('username', username);
              var path = '/' + username + '/Workspace';
              _this.$router.push({path: path});
            }
            else{
              _this.$notify({
                title:'登录失败',
                message:'用户名和密码不匹配，请重试',
                type:'error',
                duration:2000
              })
            }
          }
        }
        xmlhttp.open('GET','http://localhost:8082/user/login?&username='+username+'&password='+password,false);
        xmlhttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
        xmlhttp.send();
      },
      register:function () {
        var _this = this;
        var username = this.user.username;
        var password = this.user.password;
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
          if(xmlhttp.readyState===4&&xmlhttp.status===200){
            if(JSON.parse(xmlhttp.responseText).result===true) {
              _this.$notify({
                title:'注册成功',
                message:'您的账户已注册成功，请登录',
                type:'success',
                duration:2000
              })
            }
            else{
              _this.$notify({
                title:'注册失败',
                message:'用户已存在，请登录',
                type:'error',
                duration:2000
              })
            }
          }
        }
        xmlhttp.open('GET','http://localhost:8082/user/register?&username='+username+'&password='+password,false);
        xmlhttp.setRequestHeader("Content-type", "application/json; charset=utf-8");
        xmlhttp.send();
      },

    }
  }
</script>

<style scoped>
  #form {
    text-align: center;
    width: 80%;
    padding: 5%;

  }
  .formWrapper{
    width: 35%;
    margin-left: 33%;
    background-color: rgb(238, 241, 246);
    margin-top: 20px;
  }

</style>
