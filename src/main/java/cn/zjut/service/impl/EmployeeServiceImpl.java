package cn.zjut.service.impl;

import cn.zjut.entity.Employee;
import cn.zjut.mapper.EmployeeMapper;
import cn.zjut.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
