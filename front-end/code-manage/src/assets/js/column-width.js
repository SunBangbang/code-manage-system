export function setColumnWidth(item, nav) {
  try {
    if (nav.length < 10) {
      if (item.label === '创建者ID') {
        return 280
      }
      if (item.label === '系统名称') {
        return 200
      }
      if (item.label === '历史开始日期') {
        return 160
      }
      if (item.label === '电子邮箱地址') {
        return 280
      }
      if (item.label === '菜单权限') {
        return 300
      }
      if (item.label.length >= 4) {
        return 60 + (item.label.length - 1) * 20
      }
      return
    }
    return 60 + (item.label.length - 1) * 18
  }
  catch (e) {
    return ''
  }
}

export function setOperaWidth(item) {
  return 60 + (item.length - 1) * 20
}
