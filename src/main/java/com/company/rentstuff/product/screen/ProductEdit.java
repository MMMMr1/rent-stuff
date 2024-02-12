package com.company.rentstuff.product.screen;

import io.jmix.ui.screen.*;
import com.company.rentstuff.product.Product;

@UiController("Product.edit")
@UiDescriptor("product-edit.xml")
@EditedEntityContainer("productDc")
public class ProductEdit extends StandardEditor<Product> {
}