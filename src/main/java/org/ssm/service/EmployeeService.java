package org.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssm.beans.Employee;
import org.ssm.beans.EmployeeExample;
import org.ssm.dao.EmployeeMapper;
import java.util.List;
import org.ssm.beans.EmployeeExample.Criteria;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/2/26
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getAllEmployees() {
        EmployeeExample example = new EmployeeExample();
        example.setOrderByClause("emp_id");
        return employeeMapper.selectWithDeptByExample(example);
    }

    public void saveEmployee(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    public boolean checkEmpName(String empName) {
        EmployeeExample example = new EmployeeExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }

    public Employee getEmp(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public void deleteSingleEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatchEmps(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
