package com.company.rentstuff.customer.screen;

import com.company.rentstuff.RentStuffApplication;
import com.company.rentstuff.customer.Customer;
import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.UiTestAssistConfiguration;
import io.jmix.ui.testassist.junit.UiTest;
import io.jmix.ui.util.OperationResult;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@UiTest(authenticatedUser = "admin", mainScreenId = "MainScreen", screenBasePackages = "com.company.rentstuff")
@ContextConfiguration(classes = {RentStuffApplication.class, UiTestAssistConfiguration.class})
@AutoConfigureTestDatabase
class CustomerEditTest {
    @Autowired
    DataManager dataManager;
    FormInteractions formInteractions;

    @Test
    void given_validCustomer_when_saveCustomerThroughTheForm_then_customerisSaved(Screens screens) {
        // given:
        CustomerEdit customerEdit = openCustomerEdit(screens);
        formInteractions = FormInteractions.of(customerEdit);
        // and:
        String firstName = "Foo" + UUID.randomUUID();
        formInteractions.setFieldValue("firstNameField", firstName);
        String lastName = "Bar" + UUID.randomUUID();
        formInteractions.setFieldValue("lastNameField", lastName);
        String validStreetAddress = "Foo Street 12";
        formInteractions.setFieldValue("addressStreetField", validStreetAddress);
        // when:
        OperationResult operationResult = formInteractions.saveForm();
        assertThat(operationResult)
                .isEqualTo(OperationResult.success());
        //then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);
        assertThat(savedCustomer)
                .isPresent()
                .get()
                .extracting("lastName")
                .isEqualTo(lastName);
    }

    @Test
    void given_CustomerWithoutScreen_when_saveCustomerThroughTheForm_then_customerisNotSaved(Screens screens) {
        // given:
        CustomerEdit customerEdit = openCustomerEdit(screens);
        formInteractions = FormInteractions.of(customerEdit);
        // and:
        String firstName = "Foo" + UUID.randomUUID();
        formInteractions.setFieldValue("firstNameField", firstName);
        String invalidStreetName = "";
        formInteractions.setFieldValue("addressStreetField", invalidStreetName);
        // when:
        OperationResult operationResult = formInteractions.saveForm();
        assertThat(operationResult)
                .isEqualTo(OperationResult.fail());
        //then:
        Optional<Customer> savedCustomer = findCustomerByAttribute("firstName", firstName);
        assertThat(savedCustomer)
                .isNotPresent();
    }

    @NotNull
    private Optional<Customer> findCustomerByAttribute(String attribute, String value) {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.equal(attribute, value))
                .optional();
    }

    @NotNull
    private CustomerEdit openCustomerEdit(Screens screens) {
        CustomerEdit screen = screens.create(CustomerEdit.class);
        Customer customer = dataManager.create(Customer.class);
        screen.setEntityToEdit(customer);
        screen.show();
        return screen;
    }
}