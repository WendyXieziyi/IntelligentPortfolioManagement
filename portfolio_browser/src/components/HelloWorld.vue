<template>
  <el-container style="height: 550px; border: 1px solid #eee">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu :default-openeds="['1','4']"
        style="height: 100%;"
        @select="handleSelect">
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-message"></i>参数设置</template>
          <el-menu-item-group>
            <template slot="title">步数(steps)</template>
            <el-menu-item index="1-1">步数调参</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="起止日期">
            <el-menu-item index="1-2">日期调参</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-setting"></i>
          <span slot="title">上传数据</span>
        </el-menu-item>
        <el-menu-item index="3">
          <i class="el-icon-setting"></i>
          <span slot="title">下载结果</span>
        </el-menu-item>
        <el-submenu index="4">
          <template slot="title"><i class="el-icon-message"></i>显示图表</template>
          <el-menu-item-group>
            <template slot="title">运行结果</template>
            <el-menu-item index="4-1">投资组合比例</el-menu-item>
            <el-menu-item index="4-2">资产变化曲线</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; font-weight: bold">
        <el-button id="run" type="text" icon="el-icon-caret-right" @click="train">模拟运行</el-button>
        <span>{{username}}</span>
        <el-dropdown>
          <i class="el-icon-setting" style="margin-left: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><i class="el-icon-refresh"></i>刷新数据</el-dropdown-item>
            <el-dropdown-item><i class="el-icon-back"></i>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main v-if='panel==="chart"'>
        <el-table :data="tableData">
          <el-table-column prop="date" label="日期" width="140">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="120">
          </el-table-column>
          <el-table-column prop="address" label="地址">
          </el-table-column>
        </el-table>
      </el-main>
      <el-main v-if='panel==="upload"'>
        <el-row>
          <h3>上传数据集</h3>
        </el-row>
        <el-upload
          class="upload-demo"
          drag
          :action=uploadFileUrl
          :on-progress="onUploadChanged"
          limit="1"
          multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传.db文件，且不超过500kb</div>
        </el-upload>
      </el-main>
      <el-main v-if='panel==="parameter"'>
        <el-row>
          <h3>设定训练参数</h3>
        </el-row>
        <el-form id="paras" :ref="paras" :model="paras" label-width="80px" :disabled="parasSaved">
          <el-form-item label="任务名称">
            <el-input v-model="paras.name" ></el-input>
          </el-form-item>
          <el-form-item label="起止时间">
            <el-col :span="11">
              <el-date-picker type="date" placeholder="选择日期" v-model="paras.startDate" style="width: 100%;"></el-date-picker>
            </el-col>
            <el-col class="line" :span="1">-></el-col>
            <el-col :span="11">
              <el-date-picker placeholder="选择时间" v-model="paras.endDate" style="width: 100%;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="训练步数">
            <el-input-number v-model="paras.steps" :step="100"></el-input-number>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveParas">保存参数</el-button>
            <el-button @click="clearParas">取消</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import ElAside from "element-ui/packages/aside/src/main";

export default {
  components: {ElAside},
  name: 'HelloWorld',
  data () {
    return {
      username:"刘猫",
      panel:'upload',
      uploadFileUrl:"http://localhost:8082/data/uploadDataSet/",
      paras:{
        name:'',
        startDate:'',
        endDate:'',
        steps:8000,
      },
      parasSaved:false,
    }
  },
  methods:{
    //后台即使上传成功返回的也是404，所以文件状态改变的时候默认上传成功
    onUploadChanged:function (file,fileList) {
      this.$notify({
        title: '上传成功',
        message: '您的数据集已上传',
        type: 'success',
        duration:2000
      });
    },
    train:function () {

    },
    handleSelect(key, keyPath) {
      console.log(key);
      switch (key){
        case '1-1':{
          console.log("para")
          this.panel = 'parameter';
          break;
        }
        case '1-2':{
          this.panel = 'parameter';
          break;
        }
        case '2':{
          this.panel = 'upload';
          break;
        }
        case '3':{
          this.panel = 'download';
          break;
        }
        case '4-1':{
          this.panel = 'rateChart';
          break;
        }
        case '4-2':{
          this.panel = 'propertyChart';
          break;
        }
      }
    },
    saveParas:function () {
      if(this.paras.name===''||this.paras.startDate===''||this.paras.endDate===''||this.paras.steps===0){
        this.$notify({
          title:'警告',
          message:'参数未填写完整，为不影响后续运行，请补充完整',
          type:'warning',
          duration:2000
        })
      }
      else{
        this.parasSaved = true;
      }
    },
    clearParas:function () {
      this.paras = {};
    }

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }
  #run{
    margin-left: 5px;
    margin-right: 750px;
    color: white;
  }
  #run:hover{
    color: #409EFF;
  }
  #paras{
    width: 50%;
    text-align: left;
    margin-left: 25%;
  }

</style>
