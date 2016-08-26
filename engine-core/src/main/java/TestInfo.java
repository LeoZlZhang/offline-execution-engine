import TestCase.TestCase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestInfo {
    Class<? extends TestCase> testCaseClass() default TestCase.class;
    String testDataFilterRegex() default ".*";
    int threadNumber() default 1;
    String profileFolderName() default "Profile";
    String dataFolderName() default "temp";
    String dataFlowFolderName() default "temp";
    int repeatTime() default 0;
}
