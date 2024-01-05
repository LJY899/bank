package cn.zjut.service.impl;

import cn.zjut.entity.Setmeal;
import cn.zjut.mapper.SetmealMapper;
import cn.zjut.service.SetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
