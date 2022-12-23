<template>
  <div :class="[ pageFlag === 'code' ? 'margin-general-table' : 'general-table' ]">
    <el-table :data="tableData"
              border
              ref="tableRef"
              class="table"
              :class="{ 'non-checkbox-table': isCheckbox }"
              v-loading="loading"
              @selection-change="handleSelectionChange"
              @select="handleSelect"
              @row-click="rowClick"
    >
      <el-table-column type="selection" width="60" align="center"></el-table-column>
      <el-table-column type="index"
                       label="序号"
                       width="60"
                       v-if="pageFlag"
                       align="center"
      ></el-table-column>
      <el-table-column v-for="item in tableNav"
                       :key="item.propDisplay ? item.propDisplay : item.prop"
                       :label="item.label"
                       show-overflow-tooltip
                       :width="setColumnWidth(item, tableNav)"
      >
        <template #default="scope">
          <template v-if="item.prop === 'userType'">
            <span v-if="scope.row.userType === 1">销售商用户</span>
            <span v-if="scope.row.userType === 2">生产商用户</span>
          </template>
          <template v-else-if="item.prop === 'sex'">
            <span v-if="scope.row.sex === '1'">男</span>
            <span v-if="scope.row.sex === '0'">女</span>
          </template>
          <template v-else-if="item.prop === 'forbiddenFlag'">
            <span v-if="scope.row.forbiddenFlag === 0">启用</span>
            <span v-if="scope.row.forbiddenFlag === 1">禁用</span>
          </template>
          <template v-else-if="item.prop === 'fnlYn'">
            <span v-if="scope.row.fnlYn === 'Y'">使用</span>
            <span v-else>不使用</span>
          </template>
          <template v-else-if="item.prop === 'cvBnacLckStCd'">
            <span v-if="scope.row.cvBnacLckStCd === 'Y'">是</span>
            <span v-else>否</span>
          </template>
          <template v-else>{{ scope.row[item.propDisplay ? item.propDisplay : item.prop] }}</template>
        </template>
      </el-table-column>
      <el-table-column label="操作"
                       fixed="right"
                       align="center"
                       :width="setOperaWidth(tableOpera)"
                       v-if="tableOpera && tableOpera.length > 0"
      >
        <template #default="scope">
          <div :class="tableOpera.length > 1 ? 'operation' : 'short-operation'">
            <el-tooltip v-for="item in tableOpera"
                        :key="item.label"
                        :content="item.title"
                        placement="top"
            >
              <el-icon @click="handleIconClick(item, scope.row)">
                <component :is="item.icon"></component>
              </el-icon>
            </el-tooltip>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <div class="general-pagination">
    <template v-if="!pageFlag">
      <el-pagination layout="total"
                     :total="total">
      </el-pagination>
    </template>
    <template v-else>
      <template v-if="pageFlag === 'code'">
        <el-pagination background
                       :page-size="pageSize"
                       :page-sizes="pageSizesBig"
                       @current-change="currentChange"
                       @size-change="sizeChange"
                       layout="sizes, total, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </template>
      <template v-else>
        <el-pagination background
                       :page-size="pageSize"
                       :page-sizes="pageSizes"
                       @current-change="currentChange"
                       @size-change="sizeChange"
                       layout="sizes, total, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </template>
    </template>
  </div>
</template>

<script setup>
import { nextTick, reactive, ref, watch} from 'vue'
  import { setColumnWidth, setOperaWidth } from '@/assets/js/column-width'
  import { getSort } from '@/assets/js/get-sort'
  const pageSizes = reactive([10, 20, 30])
  const pageSizesBig = reactive([20, 50, 100, 500])
  const tableRef = ref('')
  const props = defineProps({
    tableData: Array,
    tableNav: Array,
    tableOpera: Array,
    total: Number,
    pageSize: Number,
    pageFlag: String,
    loading: Boolean,
    cData: Array,
    isCheckbox: Boolean
  })
  const emits = defineEmits([
    'iconClick',
    'currentChange',
    'sizeChange',
    'selectionChange',
    'handleSelect',
    'clickRow'
  ])
  watch(() => props.cData, n => {
    nextTick(() => {
      n.forEach(v => {
        tableRef.value.toggleRowSelection(v)
      })
    })
  })
  watch(() => props.tableData, val => {
    console.log(val);
    console.log(props.isCheckbox);
    if(props.isCheckbox) {
      nextTick(() => {
        tableRef.value.toggleRowSelection(val[val.length - 1])
      })
    }
  })

  //点击图标
  const handleIconClick = (item, row) => {
    emits('iconClick', item, row)
  }

  //表格排序
  // const sortColumn = ({ prop, order }) => {
  //   const sortOrder = getSort({ order })
  //   emits('sortTable', prop, sortOrder)
  // }

  //分页
  const currentChange = (val) => {
    emits('currentChange', val)
  }

  //每页数量
  const sizeChange = (val) => {
    emits('sizeChange', val)
  }

  //多选
  const handleSelectionChange = (val) => {
    emits('selectionChange', val)
  }

  //单击行
  const rowClick = (row) => {
    tableRef.value.clearSelection()
    tableRef.value.toggleRowSelection(row)
    emits('clickRow', row)
  }

  //单选
  const handleSelect = (node, val) => {
    emits('handleSelect', node, val)
  }
</script>
