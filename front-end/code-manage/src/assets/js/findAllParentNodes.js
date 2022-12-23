//查找某个节点的所有父节点(包括本身)
export function findAll(arr, item) {
  for (let i in arr) {
    if (arr[i].path === item.path) return [arr[i]]

    if (arr[i].children) {
      let node = findAll(arr[i].children, item)
      if (node) return node.concat(arr[i])
    }
  }
}
