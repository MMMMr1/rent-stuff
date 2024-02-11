package com.company.rentstuff.customer;

import com.company.rentstuff.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationVerification {
    @Autowired
    Validator validator;

    public List<ValidationResult> validate(Address address) {
        return validator.validate(address, Default.class)
                .stream()
                .map(ValidationResult::new)
                .collect(Collectors.toList());
    }

    public static class ValidationResult {
        private final ConstraintViolation<Address> addressConstraintViolation;

        public ValidationResult(ConstraintViolation<Address> addressConstraintViolation) {
            this.addressConstraintViolation = addressConstraintViolation;
        }

        public String getAttribute() {
            return addressConstraintViolation.getPropertyPath().toString();
        }
    }
}
