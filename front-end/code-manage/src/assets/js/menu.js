export const tableNav = [
  {
    label: '菜单名',
    prop: 'menuName'
  },{
    label: '路径',
    prop: 'menuPath'
  }
]

export const tableData = [
  {
    id: 1,
    menuName: '系统管理',
    menuPath: 'src/components',
    menuSort: 1,
    children: [
      {
        id: '1-1',
        menuName: '菜单管理',
        menuPath: 'src/components/menu',
        menuSort: 2
      }
    ]
  }
]
