package cn.zjut.controller;

import cn.zjut.common.R;
import cn.zjut.dto.ProductDto;
import cn.zjut.entity.Category;
import cn.zjut.entity.Product;
import cn.zjut.service.CategoryService;
import cn.zjut.service.DishFlavorService;
import cn.zjut.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
@RequestMapping("/dish")
@Slf4j
public class ProductController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param productDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody ProductDto productDto){
        log.info(productDto.toString());

        dishService.saveWithFlavor(productDto);

        return R.success("新增菜品成功");
    }

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
        Page<ProductDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Product::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Product::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");

        List<Product> records = pageInfo.getRecords();

        List<ProductDto> list = records.stream().map((item) -> {
            ProductDto productDto = new ProductDto();

            BeanUtils.copyProperties(item, productDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                productDto.setCategoryName(categoryName);
            }
            return productDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<ProductDto> get(@PathVariable Long id){

        ProductDto productDto = dishService.getByIdWithFlavor(id);

        return R.success(productDto);
    }

    /**
     * 修改菜品
     * @param productDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody ProductDto productDto){
        log.info(productDto.toString());

        dishService.updateWithFlavor(productDto);

        return R.success("新增菜品成功");
    }
}
