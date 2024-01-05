package cn.zjut.service.impl;
import cn.zjut.entity.Product;
import cn.zjut.mapper.ProductMapper;
import cn.zjut.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
