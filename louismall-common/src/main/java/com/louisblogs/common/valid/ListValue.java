package com.louisblogs.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ListValueConstraintVAlidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {

	String message() default "{com.louisblogs.common.valid.ListValue.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	int[] vals() default {};

}
