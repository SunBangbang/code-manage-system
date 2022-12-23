package com.codemanage.accesshistory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.accesshistory.dto.request.UserOnliHQueryDto;
import com.codemanage.accesshistory.dto.response.UserOnliHVo;
import com.codemanage.accesshistory.entity.UserOnliH;
import com.codemanage.accesshistory.mapper.UserOnliHMapper;
import com.codemanage.accesshistory.service.IUserOnliHService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserOnliHServiceImpl extends ServiceImpl<UserOnliHMapper, UserOnliH> implements IUserOnliHService {

    @Override
    public Page<UserOnliHVo> getList(UserOnliHQueryDto dto) {
        Page<UserOnliHVo> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getList(page, dto);
    }

    @Override
    public void logout(String cvUserId) {
        QueryWrapper<UserOnliH> query = new QueryWrapper<>();
        query.eq("cv_user_id", cvUserId);
        query.eq("onli_yn", "Y");

        List<UserOnliH> list = getBaseMapper().selectList(query);

        list.forEach((item) -> {
            QueryWrapper<UserOnliH> updateQuery = new QueryWrapper<>();
            updateQuery.eq("cv_user_id", item.getCvUserId());
            updateQuery.eq("last_lgn_dttm", item.getLastLgnDttm());

            item.setOnliYn("N");
            item.setFnlLoutDttm(new Date());
            getBaseMapper().update(item, updateQuery);
        });
    }
}
