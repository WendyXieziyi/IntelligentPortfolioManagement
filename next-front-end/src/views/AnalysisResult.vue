<template>
  <div>
    <h1>评测结果</h1>
    <el-row>
      <el-col :span="12">
        <div style="height: 400px" ref="chart"></div>
      </el-col>
      <el-col :span="12" class="result">
        <div class="result-row">
          <div><i class="el-icon-s-platform icon"></i></div>
          <div>{{ ability.name }}</div>
        </div>
        <div class="result-row">
          <div><i class="el-icon-s-comment icon icon--green"></i></div>
          <div>{{ ability.description }}</div>
        </div>
      </el-col>
    </el-row>
    <div style="margin-bottom: 20px">
      <el-table :data="productType" style="width: 100%">
        <el-table-column prop="name" label="投资者类型" width="180">
        </el-table-column>
        <el-table-column prop="content" label="推荐投资产品"> </el-table-column>
      </el-table>
    </div>
    <router-link to="/risk">
      <el-button>再测一次</el-button>
    </router-link>
  </div>
</template>

<script>
import echarts from "echarts";
import productType from "@/assets/productType.json";
export default {
  name: "AnalysisResult",
  data() {
    return {
      ability: {
        name: "",
        description: ""
      },
      productType
    };
  },
  mounted() {
    const final = this.$route.params.point;
    if (final >= 65) {
      this.ability = {
        name: "激进型",
        description: "您适合低风险、中低风险、中等风险、中高风险、高风险投资"
      };
    } else if (final >= 50) {
      this.ability = {
        name: "进取型",
        description: "您适合低风险、中低风险、中等风险、中高风险投资"
      };
    } else if (final >= 35) {
      this.ability = {
        name: "稳健型",
        description: "您适合低风险、中低风险、中等风险投资"
      };
    } else if (final >= 20) {
      this.ability = {
        name: "谨慎型",
        description: "您适合低风险、中低风险投资"
      };
    } else {
      this.ability = {
        name: "保守型",
        description: "您适合低风险投资"
      };
    }

    const myChart = echarts.init(this.$refs.chart);
    const option = {
      tooltip: {
        formatter: "{a} <br/>{b} : {c}%"
      },
      toolbox: {
        feature: {
          restore: {},
          saveAsImage: {}
        }
      },
      series: [
        {
          name: "业务指标",
          type: "gauge",
          detail: { formatter: "{value}分" },
          data: [{ value: this.$route.params.point }]
        }
      ]
    };

    myChart.setOption(option);
  }
};
</script>

<style>
.icon {
  font-size: 40px;
  padding-right: 20px;
  color: #409eff;
}

.icon--green {
  color: #368e00;
}

.result {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 400px;
}

.result-row {
  display: flex;
  align-items: center;
  padding-bottom: 40px;
}
</style>
