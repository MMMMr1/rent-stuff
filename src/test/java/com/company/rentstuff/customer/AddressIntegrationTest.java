package com.company.rentstuff.customer;

import com.company.rentstuff.entity.Address;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressIntegrationTest {
    @Autowired
    DataManager dataManager;
    @Autowired
    Validator validator;
    @Autowired
    ValidationVerification validationVerification;

    @Test
    void given_addressWithInvalidStreet_when_validateAddress_then_addressIsInvalid() {

        Address address = dataManager.create(Address.class);
        address.setCity(null);

        //when
        List<ValidationVerification.ValidationResult> violations = validationVerification.validate(address);

        //then
        assertThat(violations.get(0).getAttribute())
                .isEqualTo("street");
    }

    private ConstraintViolation<Address> firstViolation(List<ConstraintViolation<Address>> violations) {
        return violations.stream().findFirst().orElseThrow();
    }
}