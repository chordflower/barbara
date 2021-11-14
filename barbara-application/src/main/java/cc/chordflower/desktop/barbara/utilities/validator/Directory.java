package cc.chordflower.desktop.barbara.utilities.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention( RUNTIME )
@Target( { FIELD, METHOD, PARAMETER, ANNOTATION_TYPE } )
@Constraint( validatedBy = DirectoryValidator.class )
public @interface Directory {

  String message( ) default "The given directory ( {value} ) is not valid";

  Class< ? >[ ] groups( ) default {};

  Class< ? extends Payload >[ ] payload( ) default {};

}
