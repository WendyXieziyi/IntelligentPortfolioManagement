<template>
  <div>
    <el-collapse v-model="open" accordion>
      <el-collapse-item
        v-for="item in optionTeaching"
        :title="item.name"
        :name="item.id"
        :key="item.id"
        ><embed
          :src="
            `https://jinyong-memory.oss-cn-shanghai.aliyuncs.com/homework-sub/optionTeaching/${
              item.location
            }.pdf`
          "
          width="100%"
          height="400px"
          type="application/pdf"
        />
        <div class="content" v-html="item.default"></div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import optionTeaching from "@/assets/InvesterTeaching_v1/optionTeaching/optionTeaching.json";
export default {
  name: "StockOption",
  data() {
    return {
      optionTeaching,
      open: 1
    };
  },
  mounted() {}
};
</script>
