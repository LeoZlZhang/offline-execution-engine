package leo.offlineExectionEngine.TestCase;


import com.vipabc.vliveshow.TestExecutionEngine.TestCase.Annotation.AutoWired;
import com.vipabc.vliveshow.TestExecutionEngine.TestCase.Annotation.Usage;
import com.vipabc.vliveshow.TestExecutionEngine.Util.UpdateInstanceUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class EETestCaseImpl implements TestCase
{
    @Override
    @Usage(SupportedDataSourceType.CSV)
    public void fill(String titleName, String cell) throws Exception
    {
        StringBuilder sb = new StringBuilder(titleName.toLowerCase());
        sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
        sb.insert(0, "set");
        Method method = this.getClass().getMethod(sb.toString(), String.class);
        method.invoke(this, cell);
    }

    @Override
    @Usage(SupportedDataSourceType.CSV)
    public <T> T get(String titleName, Class<T> cls) throws Exception
    {
        StringBuilder sb = new StringBuilder(titleName);
        sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
        sb.insert(0, "get");
        Method method = this.getClass().getMethod(sb.toString());
        return cls.cast(method.invoke(this));
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    @Usage(SupportedDataSourceType.CSV)
    public boolean validate(String titleName, String cell) throws Exception
    {
        if (cell.isEmpty())
        {
            Field fields = this.getClass().getDeclaredField(titleName.toLowerCase());
            AutoWired mandatoryInput = fields.getAnnotation(AutoWired.class);
            return !mandatoryInput.isMandatory();
        } else
        {
            fill(titleName, cell);
            return true;
        }
    }


    @Override
    @Usage(SupportedDataSourceType.ALL)
    public TestCase update(Map<String, Object> profile) throws Exception
    {
        UpdateInstanceUtil.update(this, profile);
        return this;
    }



}
