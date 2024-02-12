package com.company.rentstuff.product.screen;

import io.jmix.ui.screen.*;
import com.company.rentstuff.product.Product;

@UiController("Product.browse")
@UiDescriptor("product-browse.xml")
@LookupComponent("productsTable")
public class ProductBrowse extends StandardLookup<Product> {
}