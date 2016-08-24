package TestCase.Annotation;

import com.vipabc.vliveshow.TestExecutionEngine.TestCase.SupportedDataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Usage
{
    SupportedDataSourceType value() default SupportedDataSourceType.NULL;
}
