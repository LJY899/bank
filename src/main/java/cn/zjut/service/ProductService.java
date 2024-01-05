package cn.zjut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.zjut.entity.Product;

public interface ProductService extends IService<Product> {


    //根据id查询菜品信息和对应的口味信息
    public Product getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(Product product);
}
