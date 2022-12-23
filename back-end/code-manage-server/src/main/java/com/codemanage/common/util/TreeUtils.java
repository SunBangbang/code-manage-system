package com.codemanage.common.util;
import com.codemanage.common.entity.TreeDto;
import org.apache.commons.lang3.StringUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 树工具类
 * @author hyh
 */
public class TreeUtils{
    /**
     * 根节点
     * @param treeDtoList
     * @return
     */
    public static List<TreeDto> getTree(List<? extends TreeDto> treeDtoList) {
        List<TreeDto> dtoList = new ArrayList<>();
        List<TreeDto> childList = new ArrayList<>();
        if (treeDtoList != null && treeDtoList.size() > 0) {
            for (TreeDto treeDto : treeDtoList) {

                boolean flag = true;
                for (TreeDto treeDto1 : treeDtoList) {
                    if (treeDto1.getId().equals(treeDto.getParentId())) {
                        flag = false;
                    }
                }
                // 上级部门ID为空则此部门为根部门
                if (StringUtils.isEmpty(treeDto.getParentId()) || "0".equals(treeDto.getParentId())) {
                    flag = true;
                }

                if (flag) {
                    dtoList.add(treeDto);
                } else {
                    childList.add(treeDto);
                }

            }
        }
        if (childList.size() > 0) {
            for (TreeDto dto : dtoList) {
                List<TreeDto> dtos = getChildDept(dto.getId(), childList);
                if (dtos != null){
                    dto.setChildren(dtos);
                }
            }
        }
        return dtoList;
    }

    /**
     * 子节点
     * @param id
     * @param treeDtoList
     * @return
     */
    private static List<TreeDto> getChildDept(String id,List<TreeDto>treeDtoList) {
        List<TreeDto> childList = new ArrayList<>();
        for (TreeDto treeDto : treeDtoList) {
            if (StringUtils.isNotEmpty(treeDto.getParentId())) {
                if (treeDto.getParentId().equals(id)) {
                    childList.add(treeDto);
                }
            }
        }
        // 递归
        for (TreeDto vo : childList) {
            vo.setChildren(getChildDept(vo.getId(),treeDtoList));
        }
        // 递归退出
        if (treeDtoList.size() == 0) {
            return null;
        }
        return childList;
    }

}
