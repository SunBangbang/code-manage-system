export function getSort({ order }) {
  let sortOrder = ''
  if(order === 'ascending') sortOrder = 'asc'
  else if(order === 'descending') sortOrder = 'desc'
  else sortOrder = order
  return sortOrder
}
