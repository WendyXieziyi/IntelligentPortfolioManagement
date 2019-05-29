<template>
  <div>
    <el-collapse v-model="open" accordion>
      <el-collapse-item
        v-for="(item, index) in contents"
        :title="BTCTeaching[index].name"
        :name="BTCTeaching[index].id"
        :key="BTCTeaching[index].id"
      >
        <div class="content" v-html="item.default"></div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import BTCTeaching from "@/assets/InvesterTeaching_v1/BTCTeaching/BTCTeaching.json";
export default {
  name: "VirtualCurrency",
  data() {
    return {
      contents: [],
      BTCTeaching,
      open: 1
    };
  },
  mounted() {
    const markdownPromises = BTCTeaching.map(item =>
      import(`@/assets/InvesterTeaching_v1/BTCTeaching/${item.location}.md`)
    );

    Promise.all(markdownPromises).then(result => (this.contents = result));
  }
};
</script>
