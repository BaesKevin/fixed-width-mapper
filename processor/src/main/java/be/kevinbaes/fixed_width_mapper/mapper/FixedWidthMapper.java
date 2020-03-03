package be.kevinbaes.fixed_width_mapper.mapper;

import be.kevinbaes.fixed_width_mapper.testmodel.Product;

public class FixedWidthMapper {

    public Product stringToProduct(String encoded) {
        return new Product().fromFixedWidthString(encoded);
    }

}
