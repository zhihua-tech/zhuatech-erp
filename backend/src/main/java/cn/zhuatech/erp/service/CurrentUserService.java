/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.erp.service;

import cn.zhuatech.erp.common.BusinessException;
import cn.zhuatech.erp.model.UserAccount;
import cn.zhuatech.erp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    private final UserRepository users;

    public CurrentUserService(UserRepository users) { this.users = users; }

    public UserAccount get() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return users.findByUsername(username).orElseThrow(() -> new BusinessException("当前用户不存在"));
    }
}

