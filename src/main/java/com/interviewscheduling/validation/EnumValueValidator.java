package com.interviewscheduling.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return true; // @NotNull should be used for required fields
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
