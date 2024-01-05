package cn.zjut.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.zjut.common.R;
import cn.zjut.entity.Product;
import cn.zjut.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品管理
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    //通过 @Autowired 注解进行依赖注入，注入了 productService、productFlavorService 和 CategoryService，这些服务类用于处理与菜品相关的业务逻辑。
    @Autowired
    private ProductService productService;



    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        //构造分页构造器对象
        Page<Product> pageInfo = new Page<>(page,pageSize);
        Page<Product> productPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Product::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Product::getUpdateTime);

        //执行分页查询
        productService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,productPage,"records");

        List<Product> records = pageInfo.getRecords();

        List<Product> list = records.stream().map((item) -> {
            Product product = new Product();

            BeanUtils.copyProperties(item,product);


            return product;
        }).collect(Collectors.toList());

        productPage.setRecords(list);

        return R.success(productPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Product> get(@PathVariable Long id){

        Product product = productService.getByIdWithFlavor(id);

        return R.success(product);
    }

    /**
     * 修改菜品
     * @param product
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Product product){
        log.info(product.toString());

        productService.updateWithFlavor(product);

        return R.success("修改产品成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     * @param product
     * @return
     */
    /*@GetMapping("/list")
    public R<List<product>> list(product product){
        //构造查询条件
        LambdaQueryWrapper<product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(product.getCategoryId() != null ,product::getCategoryId,product.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(product::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(product::getSort).orderByDesc(product::getUpdateTime);

        List<product> list = productService.list(queryWrapper);

        return R.success(list);
    }*/

    @GetMapping("/list")
    public R<List<Product>> list(Product product){
        //构造查询条件
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件，查询状态为1（正在上线状态）的菜品
        queryWrapper.eq(Product::getProductStatus,"1");

        //添加排序条件
        queryWrapper.orderByAsc(Product::getSort).orderByDesc(Product::getUpdateTime);

        List<Product> list = productService.list(queryWrapper);

        List<Product> productList = list.stream().map((item) -> {
            Product productDto = new Product();

            BeanUtils.copyProperties(item,product);


            return productDto;
        }).collect(Collectors.toList());

        return R.success(productList);
    }

}

