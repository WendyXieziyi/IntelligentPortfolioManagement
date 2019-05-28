<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <div class="new-main_title">财经要闻</div>
      <div class="new-main_divider" />
      <div class="new-list">
        <div class="new-list_item" v-for="item in news" :key="item.img">
          <img :src="item.img" alt="" />
          <div class="new-list_right">
            <div>{{ item.title }}</div>
            <div class="new-list_time">{{ item.time }}</div>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="8">
      <div class="hq-title">市场概况</div>
      <div class="hq-divider" />
      <div v-if="hq.length === 4" class="hq">
        <div class="hq-line">
          <div class="hq-item">
            <div>{{ hq[0].name }}</div>
            <div class="hq-green">{{ hq[0].nowPrice }}</div>
            <div class="hq-green">
              {{ `${hq[0].changedPrice} ${hq[0].percent}%` }}
            </div>
          </div>
          <div class="hq-line-vertical" />
          <div class="hq-item">
            <div>{{ hq[1].name }}</div>
            <div class="hq-green">{{ hq[1].nowPrice }}</div>
            <div class="hq-green">
              {{ `${hq[1].changedPrice} ${hq[1].percent}%` }}
            </div>
          </div>
        </div>
        <div class="hq-line-horizontal" />
        <div class="hq-line">
          <div class="hq-item">
            <div>{{ hq[2].name }}</div>
            <div class="hq-green">{{ hq[2].nowPrice }}</div>
            <div class="hq-green">
              {{ `${hq[2].changedPrice} ${hq[2].percent}%` }}
            </div>
          </div>
          <div class="hq-line-vertical" />
          <div class="hq-item">
            <div>{{ hq[3].name }}</div>
            <div class="hq-green">{{ hq[3].nowPrice }}</div>
            <div class="hq-green">
              {{ `${hq[3].changedPrice} ${hq[3].percent}%` }}
            </div>
          </div>
        </div>
      </div>

      <div class="hq-title" style="margin-top: 20px">领涨板块</div>
      <div class="hq-divider" />
      <table class="stock">
        <tr class="stock-th">
          <th></th>
          <th>领涨板块</th>
          <th></th>
          <th>领涨个股</th>
          <th></th>
        </tr>
        <tr v-for="(item, index) in stock" :key="item.block">
          <td class="index">{{ index }}</td>
          <td>{{ item.block }}</td>
          <td class="red">{{ "+" + item.block_stockup }}</td>
          <td>{{ item.comm }}</td>
          <td class="red">{{ "+" + item.comm_stockup }}</td>
        </tr>
      </table>
    </el-col>
  </el-row>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "News",
  data() {
    return {
      news: [],
      hq: [],
      stock: []
    };
  },
  mounted() {
    request("/api/news").then(res => (this.news = res));
    request("/api/news/hq").then(res => {
      console.log(res);
      const nextHq = [];
      for (let i = 0; i < 4; i++) {
        console.log(res[i]);
        nextHq.push(res[i]);
      }
      this.hq = nextHq;
    });
    request("/api/news/gushi").then(res => {
      console.log(res);
      this.stock = res;
    });
  }
};
</script>

<style>
.new-main_title {
  background-color: #806d4f;
  color: white;
  padding: 5px 6px;
  font-size: 16px;
  display: inline-block;
}
.new-main_divider {
  background-color: #806d4f;
  width: 100%;
  height: 1px;
}

.new-list {
}
.new-list_item {
  height: 80px;
  margin: 20px 0;
  display: flex;
}
.new-list_right {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding-left: 10px;
}
.new-list_time {
  font-size: 12px;
  color: gray;
}

.hq-title {
  color: #806d4f;
  margin: 5px 5px;
  padding: 0 6px;
  border-left: 4px solid #806d4f;
}
.hq-divider {
  background-color: #806d4f;
  width: 100%;
  height: 1px;
  margin-bottom: 20px;
}
.hq-line {
  display: flex;
}
.hq-item {
  padding: 10px 10px;
  display: flex;
  flex: 1;
  flex-direction: column;
  align-items: center;
}

.hq-line-vertical {
  background: #e4eaf1;
  width: 1px;
}

.hq-line-horizontal {
  background: #e4eaf1;
  width: 100%;
  height: 1px;
}

.hq-green {
  color: green;
  font-size: 12px;
}

.stock {
  font-size: 14px;
  border-collapse: collapse;
  width: 100%;
}
.stock-th {
  background: #e7eaf1;
}

.stock th,
.stock td {
  padding: 6px;
}

.stock td {
  font-size: 12px;
}
.stock tr {
  border-bottom: 1px solid #e7eaf1;
}
.stock tr:last-child {
  border-bottom: 0px;
}

.stock .index {
  color: #8db9fc;
}

.green {
  color: green;
}
.red {
  color: red;
}
</style>
