package cn.zjut.service.impl;

import cn.zjut.dto.ProductDto;
import cn.zjut.entity.Product;
import cn.zjut.entity.ProductFlavor;
import cn.zjut.mapper.DishMapper;
import cn.zjut.service.DishFlavorService;
import cn.zjut.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Product> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param productDto
     */
    @Transactional
    public void saveWithFlavor(ProductDto productDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(productDto);

        Long dishId = productDto.getId();//菜品id

        //菜品口味
        List<ProductFlavor> flavors = productDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    public ProductDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Product product = this.getById(id);

        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<ProductFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductFlavor::getDishId, product.getId());
        List<ProductFlavor> flavors = dishFlavorService.list(queryWrapper);
        productDto.setFlavors(flavors);

        return productDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(ProductDto productDto) {
        //更新dish表基本信息
        this.updateById(productDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<ProductFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ProductFlavor::getDishId, productDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<ProductFlavor> flavors = productDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(productDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }
}
