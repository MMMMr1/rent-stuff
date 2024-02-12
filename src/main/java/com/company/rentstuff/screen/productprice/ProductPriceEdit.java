package com.company.rentstuff.screen.productprice;

import io.jmix.ui.screen.*;
import com.company.rentstuff.product.ProductPrice;

@UiController("ProductPrice.edit")
@UiDescriptor("product-price-edit.xml")
@EditedEntityContainer("productPriceDc")
public class ProductPriceEdit extends StandardEditor<ProductPrice> {
}