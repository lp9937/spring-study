package com.study.security.service;

import com.study.security.domain.Resource;

import java.util.List;

public interface ResourceService {
    /**
     * 查询全部资源
     */
    List<Resource> listAll();
}
