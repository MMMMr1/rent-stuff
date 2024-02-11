package com.company.rentstuff.customer.screen;

import io.jmix.ui.screen.*;
import com.company.rentstuff.customer.Customer;

@UiController("Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
public class CustomerEdit extends StandardEditor<Customer> {
}