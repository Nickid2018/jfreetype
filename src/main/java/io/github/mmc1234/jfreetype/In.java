package io.github.mmc1234.jfreetype;

import java.lang.annotation.*;

/**
 * An annotation means data will be push in from the parameter.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface In {
}
