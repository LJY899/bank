package cn.zjut.service;

import cn.zjut.dto.ProductDto;
import cn.zjut.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;


public interface DishService extends IService<Product> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(ProductDto productDto);

    //根据id查询菜品信息和对应的口味信息
    public ProductDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(ProductDto productDto);
}
