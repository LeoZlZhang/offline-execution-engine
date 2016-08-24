package leo.offlineExectionEngine.TestCase;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;

public enum SupportedDataSourceType
{
    JSON, CSV, NULL, ALL;


    private SuffixFileFilter fileFilter;

    SupportedDataSourceType()
    {
        if (this.name().equals("ALL"))
        {
            this.fileFilter = new SuffixFileFilter(new String[]{"JSON", "CSV"}, IOCase.INSENSITIVE);
        } else
            this.fileFilter = new SuffixFileFilter(this.name(), IOCase.INSENSITIVE);
    }


    public boolean accept(File file)
    {
        return fileFilter.accept(file);
    }

}
