package com.company.rentstuff.customer.screen;

import com.company.rentstuff.RentStuffApplication;
import com.company.rentstuff.customer.Customer;
import com.company.rentstuff.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "MainScreen", screenBasePackages = "com.company.rentstuff")
@ContextConfiguration(classes = {RentStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerBrowseTest {
    @Autowired
    DataManager dataManager;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = dataManager.create(Customer.class);
        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        Address address = dataManager.create(Address.class);
        address.setCity("Bar City");
        address.setPostCode("1234");
        address.setStreet("Foo Streer");
        customer.setAddress(address);
        dataManager.save(customer);
    }

    @Test
    void given_oneCustomerExists_when_openCustomerBrowse_then_tableContainsTheCustomer(Screens screens) {
        // given:
        CustomerBrowse customerBrowse = openCustomerBrowse(screens);
        // expect:
        assertThat(firstLoadedCustomer(customerBrowse))
                .isEqualTo(customer);
    }

    @Test
    void given_oneCustomerExists_when_editCustomerBrowse_then_editCustomerEditorIsShown(Screens screens) {
        // given:
        CustomerBrowse customerBrowse = openCustomerBrowse(screens);
        //and
        Customer firstCustomer = firstLoadedCustomer(customerBrowse);
        //and
        selectCustomerInTable(customerBrowse, firstCustomer);
        //when
        button(customerBrowse, "editBtn").click();
        //then
        CustomerEdit customerEdit = screenOfType(screens, CustomerEdit.class);
        assertThat(customerEdit.getEditedEntity())
                .isEqualTo(firstCustomer);
    }

    private void selectCustomerInTable(CustomerBrowse customerBrowse, Customer firstCustomer) {
        Table<Customer> customerTable = getCustomerTable(customerBrowse);
        customerTable.setSelected(firstCustomer);
    }

    @NotNull
    private <T> T screenOfType(Screens screens, Class <T> tClass) {
        Screen screen = screens.getOpenedScreens().getActiveScreens().stream().findFirst().orElseThrow();
        assertThat(screen)
                .isInstanceOf(tClass);
        return (T) screen;
    }

    @Nullable
    private Button button(CustomerBrowse customerBrowse, String buttonId) {
        return (Button) customerBrowse.getWindow().getComponent(buttonId);
    }

    private Customer firstLoadedCustomer(CustomerBrowse customerBrowse) {
        Collection<Customer> customers = loadedCustomers(customerBrowse);
        return customers.stream().findFirst().orElseThrow();
    }

    @Nullable
    private Collection<Customer> loadedCustomers(CustomerBrowse customerBrowse) {
        return getCustomerTable(customerBrowse).getItems().getItems();
    }

    @NotNull
    private Table<Customer> getCustomerTable(CustomerBrowse customerBrowse) {
        return (Table<Customer>) customerBrowse.getWindow().getComponent("customersTable");
    }

    @NotNull
    private CustomerBrowse openCustomerBrowse(Screens screens) {
        CustomerBrowse customerBrowse = screens.create(CustomerBrowse.class);
        customerBrowse.show();
        return customerBrowse;
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(customer);
    }
}