package com.codemanage.accesshistory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.accesshistory.dto.request.UserOnliHQueryDto;
import com.codemanage.accesshistory.dto.response.UserOnliHVo;
import com.codemanage.accesshistory.entity.UserOnliH;
import org.springframework.stereotype.Service;

@Service
public interface IUserOnliHService extends IService<UserOnliH> {
    Page<UserOnliHVo> getList(UserOnliHQueryDto dto);

    void logout(String userId);
}