<template>
  <div>
    <el-collapse v-model="open" accordion>
      <el-collapse-item
        v-for="(item, index) in contents"
        :title="riskTeaching[index].name"
        :name="riskTeaching[index].id"
        :key="riskTeaching[index].id"
      >
        <div class="content" v-html="item.default"></div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import riskTeaching from "@/assets/InvesterTeaching_v1/riskTeaching/riskTeaching.json";
export default {
  name: "RiskEducation",
  data() {
    return {
      contents: [],
      riskTeaching,
      open: 1
    };
  },
  mounted() {
    const markdownPromises = riskTeaching.map(item =>
      import(`@/assets/InvesterTeaching_v1/riskTeaching/${item.location}.md`)
    );

    Promise.all(markdownPromises).then(result => (this.contents = result));
  }
};
</script>
