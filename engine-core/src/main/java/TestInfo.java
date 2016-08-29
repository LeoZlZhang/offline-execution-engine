import testData.TestData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@SuppressWarnings("WeakerAccess")
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestInfo {
    Class<? extends TestData> testDataClass() default TestData.class;
    String testDataFilterRegex() default ".*";
    int threadNumber() default 1;
    String profileFolderName() default "Profile";
    String dataFolderName() default "temp";
    String dataFlowFolderName() default "temp";
    int repeatTime() default 0;
}
