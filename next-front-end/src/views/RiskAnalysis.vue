<template>
  <div>
    <div>
      <el-form ref="form" :model="form" label-position="top">
        <el-form-item
          v-for="(item, index) in riskData"
          :key="item.id"
          :label="`${index + 1}、${item.title}`"
        >
          <el-radio-group v-model="form[index].value">
            <el-radio
              style="display: block;margin: 5px"
              :key="option.id"
              v-for="option in item.options"
              :label="option"
              >{{ option.name }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">立即测试</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import riskData from "@/assets/riskEvaluation";
export default {
  name: "RiskAnalysis",
  data() {
    return {
      riskData,
      form: { ...riskData }
    };
  },
  mounted() {
    console.log(riskData);
  },
  methods: {
    onSubmit() {
      let final = 0;
      for (let i = 0; i < riskData.length; i++) {
        const item = this.form[i];
        if (item.value) {
          final += item.value.value;
        } else {
          this.$message.error("请选择全部后提交");
        }
      }
      this.$router.push(`/risk/${final}`);
    }
  }
};
</script>

<style>
.el-form--label-top .el-form-item__label {
  font-size: 16px;
  line-height: 20px;
}
</style>
