<template>
  <el-container style="text-align: center">
    <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
      <el-menu
        :default-openeds="['1', '3']"
        style="height: 100%;"
        @select="handleSelect"
      >
        <el-submenu index="1">
          <template slot="title"
            ><i class="el-icon-setting"></i>训练设置</template
          >
          <el-menu-item-group>
            <template slot="title"
              >我的进程</template
            >
            <el-menu-item index="1-1"
              ><i class="el-icon-folder-add"></i>新建进程</el-menu-item
            >
            <el-menu-item index="1-2"
              ><i class="el-icon-s-data"></i>历史数据</el-menu-item
            >
          </el-menu-item-group>
        </el-submenu>
        <el-menu-item index="2">
          <i class="el-icon-setting"></i>
          <span slot="title">下载结果</span>
        </el-menu-item>
        <el-submenu index="3">
          <template slot="title"
            ><i class="el-icon-s-marketing"></i>显示图表</template
          >
          <el-menu-item-group>
            <template slot="title"
              >运行结果</template
            >
            <el-menu-item index="3-1">投资组合比例</el-menu-item>
            <el-menu-item index="3-2">资产变化曲线</el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; font-weight: bold">
        <el-select
          id="processSelector"
          v-model="currentProcess"
          placeholder="选择任务id"
          size="mini"
        >
          <el-option
            v-for="item in processes"
            :key="item.id"
            :label="item.id"
            :value="item.id"
            :disabled="item.disabled"
          >
          </el-option>
        </el-select>
        <el-button
          id="run"
          type="text"
          icon="el-icon-caret-right"
          @click="train"
          >模拟运行</el-button
        >
        <span></span>
        <el-dropdown @command="handleCommand">
          <i class="el-icon-setting" style="margin-left: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="refresh"
              ><i class="el-icon-refresh"></i>刷新数据</el-dropdown-item
            >
            <el-dropdown-item command="logout"
              ><i class="el-icon-back"></i>退出登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-main v-show="panel === 'rateChart'">
        <div id="rate" style="width: 900px; height: 480px"></div>
      </el-main>
      <el-main v-show="panel === 'propertyChart'">
        <div id="property" style="width: 900px; height: 480px"></div>
      </el-main>
      <el-main v-show="panel === 'parameter'">
        <el-row>
          <h3>设定训练参数</h3>
        </el-row>
        <el-form id="paras" :ref="paras" :model="paras" label-width="80px">
          <el-form-item label="任务名称">
            <el-input v-model="paras.processName"></el-input>
          </el-form-item>
          <el-form-item label="起止时间">
            <el-date-picker
              v-model="paras.date"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy/MM/dd"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="训练步数">
            <el-input-number
              v-model="paras.steps"
              :step="1000"
            ></el-input-number>
          </el-form-item>
          <el-form-item label="上传数据">
            <el-upload
              class="upload-demo"
              drag
              :action="uploadFileUrl"
              :on-progress="onUploadChanged"
              limit="1"
              multiple
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <div class="el-upload__tip" slot="tip">
                只能上传.db文件，且不超过10MB
              </div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="createProcess"
              :disabled="processSwitch"
              >创建任务</el-button
            >
            <el-button @click="cancelProcess">取消</el-button>
          </el-form-item>
        </el-form>
      </el-main>
      <el-main v-show="panel === 'historyData'">
        <el-table :data="processTableData">
          <el-table-column prop="id" label="进程Id" width="140">
          </el-table-column>
          <el-table-column prop="processName" label="进程名称" width="140">
          </el-table-column>
          <el-table-column prop="userId" label="用户Id" width="120">
          </el-table-column>
          <el-table-column prop="startDate" label="开始日期"> </el-table-column>
          <el-table-column prop="endDate" label="结束日期"> </el-table-column>
          <el-table-column prop="steps" label="训练步数"> </el-table-column>
          <el-table-column prop="status" label="状态" width="140">
          </el-table-column>
        </el-table>
      </el-main>
      <el-main v-show="panel === 'training'">
        <h3>正在训练，请稍后</h3>
        <i class="el-icon-loading" style="font-size: 40px"></i>
      </el-main>
      <el-main v-show="panel === 'download'">
        <el-row>
          <h3>下载训练结果</h3>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import ElAside from "element-ui/packages/aside/src/main";
import echarts from "echarts";
import { DEFAULT_URL } from "@/api/config";

export default {
  components: { ElAside },
  name: "Workspace",
  created() {
    this.searchProcessId();
  },
  data() {
    return {
      username: "刘猫",
      panel: "parameter",
      uploadFileUrl: DEFAULT_URL + "/file/uploadDataSet/",
      paras: {
        processName: "",
        date: ["2015/07/01", "2017/07/01"],
        steps: 80000,
        userId: localStorage.getItem("userId")
      },
      processSwitch: true,
      isTraining: false,
      processes: [],
      currentProcess: "",
      selectorDisabled: false,
      processTableData: []
    };
  },
  methods: {
    //后台即使上传成功返回的也是404（这个bug似乎是element的），所以文件状态改变的时候默认上传成功
    onUploadChanged: function() {
      this.processSwitch = false;
    },
    train: function() {
      this.isTraining = true;
      this.panel = "training";
      var _this = this;
      var processId = this.currentProcess;
      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          if (JSON.parse(xmlhttp.responseText).result === true) {
            _this.isTraining = false;
            _this.panel = "parameter";
            _this.$notify({
              title: "训练结束",
              message: "请前往图标页面查看结果",
              type: "info",
              duration: 2000
            });
          }
        }
      };
      xmlhttp.open(
        "GET",
        DEFAULT_URL + "/process/runProcess?&processId=" + processId,
        true
      );
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send();
    },
    handleSelect(key) {
      switch (key) {
        case "1-1": {
          if (this.isTraining) {
            this.panel = "training";
          } else {
            this.panel = "parameter";
          }
          break;
        }
        case "1-2": {
          this.searchProcess();
          this.panel = "historyData";
          break;
        }
        case "2": {
          this.panel = "download";
          break;
        }
        case "3-1": {
          //this.drawRateChart();
          this.panel = "rateChart";
          break;
        }
        case "3-2": {
          //this.drawPropertyChart();
          this.panel = "propertyChart";
          break;
        }
      }
    },
    handleCommand: function(command) {
      if (command === "refresh") {
        this.drawRateChart();
        this.drawPropertyChart();
      }
      if (command === "logout") {
        localStorage.setItem("username", undefined);
        localStorage.setItem("userId", undefined);
        var path = "/";
        this.$router.push({ path });
      }
    },
    createProcess: function() {
      var _this = this;
      console.log(this.paras.date);
      var startDate = this.paras.date[0];
      var endDate = this.paras.date[1];
      var id = this.generateProcessId();
      var paras = {
        username: localStorage.getItem("username"),
        startDate: startDate,
        endDate: endDate,
        steps: this.paras.steps,
        userId: this.paras.userId,
        processName: this.paras.processName,
        id: id
      };
      var xmlhttp = new XMLHttpRequest();
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          if (JSON.parse(xmlhttp.responseText).result === true) {
            _this.$notify({
              title: "创建成功",
              message: "新任务已创建，请选择任务开始训练",
              type: "success",
              duration: 2000
            });
            _this.currentProcess = id;
            _this.processes.push({
              id: id
            });
          } else {
            _this.$notify({
              title: "创建失败",
              message: "请重试",
              type: "error",
              duration: 2000
            });
          }
        }
      };
      xmlhttp.open("POST", DEFAULT_URL + "/process/createProcess", false);
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send(JSON.stringify(paras));
    },
    generateProcessId: function() {
      var id = "";
      for (var i = 0; i < 8; i++) {
        id += Math.floor(Math.random() * 10);
      }
      return id;
    },
    searchProcessId: function() {
      var userId = localStorage.getItem("userId");
      console.log(userId);
      var xmlhttp = new XMLHttpRequest();
      var _this = this;
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          var data = JSON.parse(xmlhttp.responseText);
          _this.processes = [];
          for (var i = 0; i < data.length; i++) {
            _this.processes.push({
              id: data[i].id
            });
          }
        }
      };
      xmlhttp.open(
        "GET",
        DEFAULT_URL + "/process/searchProcess?&userId=" + userId,
        false
      );
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send();
    },
    searchProcess: function() {
      var userId = localStorage.getItem("userId");
      console.log(userId);
      var xmlhttp = new XMLHttpRequest();
      var _this = this;
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          console.log(xmlhttp.responseText);
          var data = JSON.parse(xmlhttp.responseText);
          _this.processTableData = data;
        }
      };
      xmlhttp.open(
        "GET",
        DEFAULT_URL + "/process/searchProcess?&userId=" + userId,
        false
      );
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send();
    },
    drawPropertyChart: function() {
      var myChart = echarts.init(document.getElementById("property"));
      var data = [["2000-06-06", 129], ["2000-06-07", 129]];
      var option = {
        title: {
          text: "资产变化曲线"
        },
        tooltip: {
          trigger: "axis"
        },
        xAxis: {
          data: data.map(function(item) {
            return item[0];
          })
        },
        yAxis: {
          splitLine: {
            show: false
          }
        },
        toolbox: {
          left: "center",
          feature: {
            dataZoom: {
              yAxisIndex: "none"
            },
            restore: {},
            saveAsImage: {}
          }
        },
        dataZoom: [
          {
            startValue: "0"
          },
          {
            type: "inside"
          }
        ],
        visualMap: {
          top: 10,
          right: 10,
          pieces: [
            {
              gt: 0,
              lte: 20,
              color: "#096"
            },
            {
              gt: 20,
              lte: 40,
              color: "#ffde33"
            },
            {
              gt: 40,
              lte: 60,
              color: "#ff9933"
            },
            {
              gt: 60,
              lte: 80,
              color: "#cc0033"
            },
            {
              gt: 80,
              lte: 100,
              color: "#660099"
            },
            {
              gt: 100,
              color: "#7e0023"
            }
          ],
          outOfRange: {
            color: "#999"
          }
        },
        series: {
          name: "Beijing AQI",
          type: "line",
          data: data.map(function(item) {
            return item[1];
          }),
          markLine: {
            silent: true,
            data: [
              {
                yAxis: 20
              },
              {
                yAxis: 40
              },
              {
                yAxis: 60
              },
              {
                yAxis: 80
              },
              {
                yAxis: 100
              }
            ]
          }
        }
      };

      var processId = this.currentProcess;
      var xmlhttp = new XMLHttpRequest();
      // var _this = this;
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          data = JSON.parse(xmlhttp.responseText);
          option.series.data = data;
          option.xAxis.data = data;
          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        }
      };
      xmlhttp.open(
        "GET",
        DEFAULT_URL + "/chart/getPropertyData?&processId=" + processId,
        false
      );
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send();
    },
    drawRateChart: function() {
      var myChart = echarts.init(document.getElementById("rate"));
      //
      var option = {
        legend: {},
        tooltip: {
          trigger: "axis",
          showContent: true
        },
        dataset: {
          source: [
            ["product", "2012", "2013", "2014", "2015", "2016", "2017"],
            ["Matcha Latte", 41.1, 30.4, 65.1, 53.3, 83.8, 98.7],
            ["Milk Tea", 86.5, 92.1, 85.7, 83.1, 73.4, 55.1],
            ["Cheese Cocoa", 24.1, 67.2, 79.5, 86.4, 65.2, 82.5],
            ["Walnut Brownie", 55.2, 67.1, 69.2, 72.4, 53.9, 39.1]
          ]
        },
        xAxis: { type: "category" },
        yAxis: { gridIndex: 0 },
        grid: { top: "55%" },
        series: [
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          { type: "line", smooth: true, seriesLayoutBy: "row" },
          {
            type: "pie",
            id: "pie",
            radius: "30%",
            center: ["50%", "35%"],
            label: {
              formatter: "{b}: {@2012} ({d}%)"
            },
            encode: {
              itemName: "product",
              value: "1",
              tooltip: "2012"
            }
          }
        ]
      };

      myChart.on("updateAxisPointer", function(event) {
        var xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          var dimension = xAxisInfo.value + 1;
          myChart.setOption({
            series: {
              id: "pie",
              label: {
                formatter: "{b}: {@[" + dimension + "]} ({d}%)"
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      var processId = this.currentProcess;
      var xmlhttp = new XMLHttpRequest();
      // var _this = this;
      xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
          var data = JSON.parse(xmlhttp.responseText);
          console.log(data);
          option.dataset.source = data;

          // 使用刚指定的配置项和数据显示图表。
          myChart.setOption(option);
        }
      };
      xmlhttp.open(
        "GET",
        DEFAULT_URL + "/chart/getRateData?&processId=" + processId,
        false
      );
      xmlhttp.setRequestHeader(
        "Content-type",
        "application/json; charset=utf-8"
      );
      xmlhttp.send();
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
#run {
  margin-left: 5px;
  margin-right: 600px;
  color: white;
}
#run:hover {
  color: #409eff;
}
#processSelector {
  padding-right: 100px;
}
#paras {
  width: 50%;
  text-align: left;
  margin-left: 25%;
}
</style>
