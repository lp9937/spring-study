package com.study.security.service;

import com.study.security.domain.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements
        ResourceService {
    @Override
    public List<Resource> listAll() {
        return new ArrayList<>();
    }
}
