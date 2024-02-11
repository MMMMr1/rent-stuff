package com.company.rentstuff.customer;

import com.company.rentstuff.entity.Address;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerIntegrationTest {
    @Autowired
    DataManager dataManager;
    @Autowired
    SystemAuthenticator systemAuthenticator;
    @Autowired
    Validator validator;
    private Customer customer;

    @BeforeAll
    void setUp(){
        customer = dataManager.create(Customer.class);
    }

    @Test
    void given_validCustomer_when_saveCustomer_then_customerIsSaved() {

        customer.setFirstName("Foo");
        customer.setLastName("Bar");
        customer.setEmail("foo@bar.com");
        Address address = dataManager.create(Address.class);
        address.setCity("Bar City");
        address.setPostCode("1234");
        address.setStreet("Foo Streer");
        customer.setAddress(address);

        //when
        Customer savedCustomer = systemAuthenticator.withSystem(() ->  dataManager.save(customer));

        //then
        assertThat(savedCustomer.getId())
                .isNotNull();
    }

    @Test
    void given_InvalidMailCustomer_when_saveCustomer_then_customerIsInvalid() {
        customer.setEmail("invalidEmailAddress");

        //when
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer, Default.class);

        //then
        assertThat(violations)
                .hasSize(1);
        ConstraintViolation<Customer> emailViolation = firstViolation(violations);
        //then
        assertThat(emailViolation.getPropertyPath().toString())
                .isEqualTo("email");
    }

    private ConstraintViolation<Customer> firstViolation(Set<ConstraintViolation<Customer>> violations) {
        return violations.stream().findFirst().orElseThrow();
    }
}