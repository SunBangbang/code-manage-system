import { createStore } from 'vuex'

export default createStore({
  state: {
    isCollapse: false,
    loginName: '',
    menuList: [],
    defaultIndex: '',
    breadList: []
  },
  mutations: {
    //改变侧边栏的宽度
    changeIsCollapse(state, value) {
      state.isCollapse = value
    },

    //获取登录名
    getLoginName(state, value) {
      state.loginName = value
    },

    //改变侧边栏
    changeMenu(state, value) {
      state.menuList = value
    },

    //改变侧边栏选中
    changeIndex(state, value) {
      state.defaultIndex = value
    },

    //面包屑
    changeBreadList(state, value) {
      state.breadList = value
    }
  },
  actions: {
  },
  modules: {
  }
})
