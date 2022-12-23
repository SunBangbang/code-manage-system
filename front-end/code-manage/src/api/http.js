import axios from 'axios'
import router from '@/router'
import { ElMessage } from 'element-plus'

axios.defaults.timeout = 60000
// axios.defaults.baseURL = 'http://192.168.0.179:9999'
axios.defaults.baseURL = window.config.testUrl
// axios.defaults.baseURL = 'http://10.231.27.56:9999/'

axios.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json; charset=utf-8'
  if(config.url !== '/login') {
    const token = sessionStorage.getItem('token')
    if(token) config.headers.Authorization = token
  }

  return config
})

axios.interceptors.response.use(response => {
  if(response.data.code === 402) {
    ElMessage.error(response.data.msg)
    sessionStorage.clear()
    router.push('/')
  }
  return response
})

export function get(url, params) {
  return new Promise((resolve, reject) => {
    axios.get(url, { params }).then(
      res => {
        if(res.data.code === 200) resolve(res)
        else ElMessage.error(res.data.msg)
      }
    ).catch(err => {
      ElMessage.error(err.message + ',请联系管理员')
    })
  })
}

export function post(url, data = {}) {
  return new Promise((resolve, reject) => {
    axios.post(url, data).then(
      res => {
        console.log(res);
        if(res.data.code === 200) resolve(res)
        else reject(res)
        // else ElMessage.error(res.data.msg)
      }
    ).catch(err => {
      ElMessage.error(err.message + ',请联系管理员')
    })
  })
}
