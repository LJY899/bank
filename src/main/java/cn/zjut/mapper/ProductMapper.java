package cn.zjut.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.zjut.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}

