package com.company.rentstuff.customer.screen;

import io.jmix.ui.screen.*;
import com.company.rentstuff.customer.Customer;

@UiController("Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
public class CustomerBrowse extends StandardLookup<Customer> {
}