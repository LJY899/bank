package cn.zjut.dto;
import cn.zjut.entity.Product;
import cn.zjut.entity.ProductFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto extends Product {

    private List<ProductFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
