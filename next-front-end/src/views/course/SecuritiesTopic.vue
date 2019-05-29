<template>
  <div>
    <el-collapse v-model="open" accordion>
      <el-collapse-item
        v-for="(item, index) in contents"
        :title="bondTeaching[index].name"
        :name="bondTeaching[index].id"
        :key="bondTeaching[index].id"
      >
        <div class="content" v-html="item.default"></div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import bondTeaching from "@/assets/InvesterTeaching_v1/bondTeaching/bondTeaching.json";
export default {
  name: "SecuritiesTopic",
  data() {
    return {
      contents: [],
      bondTeaching,
      open: 1
    };
  },
  mounted() {
    const markdownPromises = bondTeaching.map(item =>
      import(`@/assets/InvesterTeaching_v1/bondTeaching/${item.location}.md`)
    );

    Promise.all(markdownPromises).then(result => (this.contents = result));
  }
};
</script>
