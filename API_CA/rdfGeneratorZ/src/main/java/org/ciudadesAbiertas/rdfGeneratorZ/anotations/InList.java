package org.ciudadesAbiertas.rdfGeneratorZ.anotations;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.ciudadesAbiertas.rdfGeneratorZ.validator.InListValidator;



@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = InListValidator.class)
@Documented
public @interface InList {

    String message() default "Valor no permitido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String[] value();

}
