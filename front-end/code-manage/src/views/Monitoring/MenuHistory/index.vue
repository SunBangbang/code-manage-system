<template>
  <div class="user">
    <div class="menu-button">
      <div class="button-search">
        <span>用户ID:</span>
        <el-input v-model="form.cvLgnId" placeholder="请输入"></el-input>
        <span>用户名:</span>
        <el-input v-model="form.userNm" placeholder="请输入"></el-input>
        <span>时间:</span>
        <el-date-picker v-model="form.date" type="daterange" range-separator="至" start-placeholder="开始"
                        end-placeholder="结束"/>
        <el-button type="primary" @click="getTableData">查询</el-button>
      </div>
    </div>
    <div class="menu-table">
      <div class="margin-general-table">
        <el-table :data="tableData" class="table" border>
          <el-table-column label="访问时间" prop="recTimeDttm" show-overflow-tooltip width="100" align="center" />
          <el-table-column label="用户名" prop="userNm" show-overflow-tooltip width="80" align="center" />
          <el-table-column label="菜单名" prop="oprtSmptExplTxt" show-overflow-tooltip width="100" align="center" />
          <el-table-column label="访问路径" prop="oprtDtlExplTxt" show-overflow-tooltip align="center" />
        </el-table>
      </div>

      <div class="general-pagination">
        <el-pagination background
                       :current-page="pageCurrent"
                       :page-size="pageSize"
                       @current-change="handleCurrentChange"
                       layout="total, prev, pager, next, jumper"
                       :total="tableDataTotal">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {getSysOprtGList} from "@/api"

const form = ref({
  cvLgnId: '',
  userNm: '',
  date: []
});

const tableData = ref([])
const tableDataTotal = ref(0)
const pageCurrent = ref(1)
const pageSize = ref(30)

const getTableData = () => {
  const postData = {
    current: pageCurrent.value,
    size: pageSize.value,
    cvLgnId: form.value.cvLgnId,
    userNm: form.value.userNm,
    beginTime: form.value.date.length === 2 ? form.value.date[0] : undefined,
    endTime: form.value.date.length === 2 ? form.value.date[1] : undefined,
  };

  console.log(postData);
  getSysOprtGList(postData).then(res => {
    console.log(res.data.data)
    const {current, size, records, total} = res.data.data;

    tableData.value = records
    tableDataTotal.value = total
    pageCurrent.value = current
    pageSize.value = size
  });
}

const handleCurrentChange = (val) => {
  pageCurrent.value = val
  getTableData()
}

getTableData();
</script>

<style lang="less" scoped>
.user-button {
  display: inline-flex;

  > * {
    margin: 5px;
  }

  > span {
    min-width: 50px;
  }

  > div {
    min-width: 120px;
  }

  > button {
    margin-left: 20px;
  }
}
</style>