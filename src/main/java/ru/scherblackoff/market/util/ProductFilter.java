package ru.scherblackoff.market.util;


import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import ru.scherblackoff.market.models.Product;
import ru.scherblackoff.market.repositories.specifications.ProductSpecifications;

import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();
        if (map.containsKey("min_price") && !map.get("min_price").isEmpty()) {
            int minPrice = Integer.parseInt(map.get("min_price"));
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
            filterDefinition.append("&min_price=").append(minPrice);
        }
        if (map.containsKey("max_price") && !map.get("max_price").isEmpty()) {
            int maxPrice = Integer.parseInt(map.get("max_price"));
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(maxPrice));
            filterDefinition.append("&max_price=").append(maxPrice);
        }
        if(map.containsKey("start_name") && !map.get("start_name").isEmpty()){
            String name = map.get("start_name");

            if (name.length() == 0) {
                name = "";
            }else if(name.length() == 1){
                name = name.toUpperCase();
            }else{
                name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            }
            spec = spec.and(ProductSpecifications.nameLike(name));
            filterDefinition.append("&start_name=").append(name);
        }
    }
}
