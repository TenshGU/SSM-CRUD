package org.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.beans.Department;
import org.ssm.dao.DepartmentMapper;

import java.util.List;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/3/1
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.selectByExample(null);
    }
}
