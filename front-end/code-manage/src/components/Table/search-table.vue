<template>
  <div class="search-table">
    <el-table :data="tableData"
              border
    >
      <el-table-column type="index"
                       label="序号"
                       width="60"
                       align="center"
      ></el-table-column>
      <el-table-column v-for="item in tableNav" :key="item.label">
        <template #header>
          <el-input v-model="item.value"
                    clearable
                    @clear="handleSearch(tableNav)"
                    @keydown.enter="handleSearch(tableNav)"
                    placeholder="请输入"></el-input>
        </template>
        <el-table-column :label="item.label"
                         :prop="item.label"
                         show-overflow-tooltip
                         :width="setColumnWidth(item, tableNav)">
        </el-table-column>
      </el-table-column>
    </el-table>
  </div>

  <div class="search-pagination">
    <el-pagination background
                   :page-size="30"
                   @current-change="currentChange"
                   layout="total, prev, pager, next, jumper"
                   :total="total">
    </el-pagination>
  </div>
</template>

<script setup>
  import { defineProps, defineEmits } from 'vue'
  import { setColumnWidth, setOperaWidth } from '@/assets/js/column-width'
  import { getSort } from '@/assets/js/get-sort'
  const props = defineProps({
    tableNav: Array,
    tableData: Array,
    total: Number
  })
  const emits = defineEmits(['search', 'currentChange'])

  const handleSearch = (items) => {
    emits('search', items)
  }

  const currentChange = (val) => {
    emits('currentChange', val)
  }
</script>
