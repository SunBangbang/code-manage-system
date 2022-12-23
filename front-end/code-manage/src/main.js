import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store'
import './assets/css/reset.css'
import './assets/css/common-table.less'
import './assets/css/common.less'
import './assets/css/common1.less'
import './assets/css/common-dialog.less'
import './assets/css/common-dialog1.less'
import './assets/css/common-scrollbar.less'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElIcons from '@element-plus/icons-vue'
import zhCh from 'element-plus/lib/locale/lang/zh-cn'
import GeneralTable from '@/components/Table/general-table'
import SearchTable from '@/components/Table/search-table'

const app = createApp(App)
app.use(store).use(router)
    .use(ElementPlus, { locale: zhCh })
    .component('general-table', GeneralTable)
    .component('search-table', SearchTable)
    .mount('#app')

for (const [key, component] of Object.entries(ElIcons)) {
  app.component(key, component)
}
