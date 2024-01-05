package cn.zjut.controller;

import cn.zjut.common.R;
import cn.zjut.entity.Employee;
import cn.zjut.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 员工信息前端控制器
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    ThreadLocal threadLocal = new ThreadLocal();

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request 如果登陆成功把对象放入Session中，方便后续拿取
     * @param employee 利用@RequestBody注解来解析前端传来的Json，同时用对象来封装
     * @return
     */
    @PostMapping("/login")
    R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        String username = employee.getUsername();
        log.info("登录");
        //md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //通过用户名查这个员工对象
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,username);
        Employee emp = employeeService.getOne(queryWrapper);
        if (!emp.getUsername().equals(username)){
            return R.error("账户不存在");
            //密码是否正确
        }else if (!emp.getPassword().equals(password)){
            return R.error("账户密码错误");
            //员工账户状态是否正常，1：状态正常，0：封禁
        }else if (emp.getStatus()!=1){
            return R.error("当前账户正在封禁");
            //状态正常允许登陆
        }else {
            //登陆成功，将用户id存入Session并返回登录成功结果
            log.info("登陆成功，账户存入session");
            request.getSession().setAttribute("employee",emp.getId());
            return R.success(employee);
        }
    }

    /**
     * 员工登出
     * @param request 删除request作用域中的session对象
     * @return 删除结果
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //尝试删除
        try {
            request.getSession().removeAttribute("employee");
        }catch (Exception e){
            //删除失败
            return R.error("登出失败");
        }
        return R.success("登出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee) {
        //设置默认密码，顺手加密了
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //账户默认状态1
        employee.setStatus(1);
        //获取当前新增操作人员的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        //Mybatis-plus自动CRUD的功能，封装好了save方法
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}
