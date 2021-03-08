package org.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.ssm.beans.Employee;
import org.ssm.service.EmployeeService;
import org.ssm.utils.ResultJson;

import java.util.*;

/**
 * @description:
 * @author: Tensh
 * @createDate: 2021/2/26
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @ResponseBody
    @RequestMapping("/emps")
    public ResultJson getEmps(@RequestParam(value = "pn",defaultValue = "1") int pn) {
        /*引入PageHelper分页插件
        设定第几页，以及每一页的size
        在查询之前调用*/
        PageHelper.startPage(pn, 5);
        //startPage后紧跟的这个查询就是一个分页查询
        List<Employee> employees = employeeService.getAllEmployees();
        //使用pageInfo包装查询后的结果，只需将pageInfo交给页面即可
        //封装了详细的分页信息，包括查询出来的数据，可以传入连续显示的页数
        PageInfo<Employee> pageInfo = new PageInfo<>(employees,5);

        ResultJson resultJson = ResultJson.success().addInfo("pageInfo", pageInfo);
        //以Json形式返回结果
        return resultJson;
    }

    @ResponseBody
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    public ResultJson saveEmp(@Validated Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return ResultJson.fail().addInfo("errorFields",map);
        } else {
            employeeService.saveEmployee(employee);
            return ResultJson.success();
        }
    }

    @ResponseBody
    @RequestMapping("/checkEmpName")
    public ResultJson checkEmpName(@RequestParam("empName")String empName) {
        //判断用户名是否为合法表达式
        String regx = "(^[a-zA-Z0-9]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if (!empName.matches(regx)) {
            //不符合
            return ResultJson.fail().addInfo("vad_msg","用户名必须是6-16位数字和字母或2-5位汉字的组合");
        }

        //数据库查询是否存在同名
        boolean repeat = employeeService.checkEmpName(empName);
        if (!repeat) {
            return ResultJson.fail().addInfo("vad_msg","用户名已经存在");
        } else {
            return ResultJson.success().addInfo("vad_msg","用户名可用");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public ResultJson getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return ResultJson.success().addInfo("emp",employee);
    }

    /**
     * 如果直接发送ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014, empName=null, gender=null, email=null, dId=null]
     *
     * 问题：
     * 请求体中有数据；
     * 但是Employee对象封装不上；
     * update tbl_emp  where emp_id = 1014;
     *
     * 原因：
     * Tomcat：
     * 		1、将请求体中的数据，封装一个map。
     * 		2、request.getParameter("empName")就会从这个map中取值。
     * 		3、SpringMVC封装POJO对象的时候。
     * 				会把POJO中每个属性的值，request.getParamter("email");
     * AJAX发送PUT请求引发的血案：
     * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * org.apache.catalina.connector.Request--parseParameters() (3111);
     *
     * protected String parseBodyMethods = "POST";
     * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     success = true;
     return;
     }
     *
     *
     * 解决方案；
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 1、配置上FormContentFilter；
     * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     * 员工更新方法
     * @param employee
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public ResultJson UpdateEmp(@Validated Employee employee,BindingResult result) {
        System.out.println(employee);
        if (result.hasErrors()) {
            Map<String, Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return ResultJson.fail().addInfo("errorFields",map);
        } else {
            employeeService.updateEmp(employee);
            return ResultJson.success();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
    public ResultJson deleteEmp(@PathVariable("ids") String ids) {
        if (ids.contains("-")) {
            //批量删除
            List<Integer> list = new ArrayList<>();
            String[] split = ids.split("-");
            for (String s : split) {
                list.add(Integer.parseInt(s));
            }
            employeeService.deleteBatchEmps(list);
            return ResultJson.success();
        } else {
            //单个删除
            employeeService.deleteSingleEmp(Integer.parseInt(ids));
            return ResultJson.success();
        }
    }

    /*@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") int pn, Model model) {
        *//*引入PageHelper分页插件
         设定第几页，以及每一页的size
         在查询之前调用*//*
        PageHelper.startPage(pn, 5);
        //startPage后紧跟的这个查询就是一个分页查询
        List<Employee> employees = employeeService.getAllEmployees();
        //使用pageInfo包装查询后的结果，只需将pageInfo交给页面即可
        //封装了详细的分页信息，包括查询出来的数据，可以传入连续显示的页数
        PageInfo<Employee> pageInfo = new PageInfo<>(employees,5);
        model.addAttribute("pageInfo",pageInfo);

        return "list";
    }*/
}
