package cn.zjut.service.impl;

import cn.zjut.entity.ProductFlavor;
import cn.zjut.mapper.DishFlavorMapper;
import cn.zjut.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, ProductFlavor> implements DishFlavorService {
}
