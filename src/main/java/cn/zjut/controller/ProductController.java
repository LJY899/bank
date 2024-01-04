package cn.zjut.controller;
import cn.zjut.common.R;
import cn.zjut.entity.Product;
import cn.zjut.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增产品
     * @param product 包含产品信息的请求体
     * @return 新增成功的产品信息
     */
    @PostMapping
    public R<String> saveProduct(@RequestBody Product product) {
        log.info("新增产品，产品信息：{}", product.toString());

        boolean result = productService.save(product);

        if (result) {
            return R.success("新增产品成功");
        } else {
            return R.error("新增产品失败");
        }
    }
}
