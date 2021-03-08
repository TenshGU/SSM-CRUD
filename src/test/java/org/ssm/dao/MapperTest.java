package org.ssm.dao;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssm.beans.Department;
import org.ssm.beans.Employee;

import java.util.UUID;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/2/26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testDeptMapperCRUD() {
        departmentMapper.insertSelective(new Department(1, "开发部"));
        departmentMapper.insertSelective(new Department(2, "公关部"));
        System.out.println("测试插入完成");
    }

    @Test
    public void testEmpMapperCRUD() {
        //执行批量查询
        EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
        for (int i=0; i<1000; i++) {
            String name = UUID.randomUUID().toString().substring(0, 5) + i;
            employeeMapper.insertSelective(new Employee(null, name, "M", name+"@163.com", 1));
        }
        System.out.println("批量插入完成");
    }
}
