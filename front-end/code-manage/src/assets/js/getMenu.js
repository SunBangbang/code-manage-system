import { getUserMenu } from '@/api'
export function getChildren(data) {
  let arr = [];
  let d = data || [];
  d.forEach(v => {
    if(v.children && v.children.length > 0) {
      let obj = {
        path: v.authObjExplTxt.split('&')[1],
        name: v.name,
        icon: v.authObjExplTxt.split('&')[0],
        component: () => import(`@/${v.authObjExplTxt.split('&')[2]}`),
        // component: () => resolve => require([`${v.authObjExplTxt.split('&')[2]}`], resolve),
        children: getChildren(v.children)
      }
      arr.push(obj)
    }else {
      let obj = {
        path: v.authObjExplTxt.split('&')[0],
        name: v.name,
        // icon: v.icon,
        component: () => import(`@/${v.authObjExplTxt.split('&')[1]}`)
        // component: () => import(`${v.authObjExplTxt.split('&')[1]}`)
      }
      arr.push(obj)
    }
  })
  return arr
}

export function setIn(data, router) {
  let arr = getChildren(data)
  arr.forEach(v => {
    router.addRoute('main', v)
  })
}

export function getMenus() {
  return new Promise((resolve, reject) => {
    getUserMenu().then(
        res => { resolve(res.data.data) },
        err => { reject(err) }
    )
  })
}
