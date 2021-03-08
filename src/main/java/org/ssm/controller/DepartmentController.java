package org.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ssm.beans.Department;
import org.ssm.service.DepartmentService;
import org.ssm.utils.ResultJson;

import java.util.List;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/3/1
 */
@Controller
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @ResponseBody
    @RequestMapping("/depts")
    public ResultJson getDepts() {
        List<Department> depts = departmentService.getAllDepartments();
        return ResultJson.success().addInfo("depts",depts);
    }
}
