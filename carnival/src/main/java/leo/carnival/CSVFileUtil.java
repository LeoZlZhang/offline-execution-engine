package leo.carnival;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "JavaDoc"})
public class CSVFileUtil
{
    public static final String ENCODE = "UTF-8";
    private BufferedReader br = null;


    public CSVFileUtil(String filename) throws Exception
    {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isw = new InputStreamReader(fis, ENCODE);
        br = new BufferedReader(isw);
    }
    public CSVFileUtil(File file) throws Exception
    {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isw = new InputStreamReader(fis, ENCODE);
        br = new BufferedReader(isw);
    }


    public List<List<String>> readAll() throws Exception
    {
        List<List<String>> returnList = new ArrayList<>();
        String line;
        while ((line = readLine()) != null)
            returnList.add(fromCSVLinetoArray(line));
        return returnList;
    }


    public List<String> readLineToArray() throws Exception
    {
        return fromCSVLinetoArray(readLine());
    }


    /**
     * Read one line
     *
     * @throws Exception
     */
    public String readLine() throws Exception
    {

        StringBuilder readLine = new StringBuilder();
        boolean bReadNext = true;

        while (bReadNext)
        {
            if (readLine.length() > 0)
                readLine.append("\r\n");
            String strReadLine = br.readLine();

            if (strReadLine == null)
                return null;
            readLine.append(strReadLine);

            bReadNext = countChar(readLine.toString(), '"', 0) % 2 == 1;
        }
        return readLine.toString();
    }

    /**
     * Transfer csv line into string array
     */
    @SuppressWarnings("UnusedDeclaration")
    public static String[] fromCSVLine(String source, int size)
    {
        ArrayList tmpArray = fromCSVLinetoArray(source);
        if (size < tmpArray.size())
            size = tmpArray.size();
        String[] rtnArray = new String[size];
        tmpArray.toArray(rtnArray);
        return rtnArray;
    }

    /**
     * Transfer csv line into string list
     */
    public static ArrayList<String> fromCSVLinetoArray(String source)
    {
        if (source == null || source.length() == 0)
            return new ArrayList<>();
        int currentPosition = 0;
        int maxPosition = source.length();
        int nextComma;
        ArrayList<String> rtnArray = new ArrayList<>();
        while (currentPosition < maxPosition)
        {
            nextComma = nextComma(source, currentPosition);
            rtnArray.add(nextToken(source, currentPosition, nextComma));
            currentPosition = nextComma + 1;
            if (currentPosition == maxPosition)
                rtnArray.add("");
        }
        return rtnArray;
    }


    /**
     * Transfer string array into CSV line
     */
    public static String toCSVLine(String[] strArray)
    {
        if (strArray == null)
            return "";
        StringBuilder cvsLine = new StringBuilder();
        for (int idx = 0; idx < strArray.length; idx++)
        {
            String item = addQuote(strArray[idx]);
            cvsLine.append(item);
            if (strArray.length - 1 != idx)
                cvsLine.append(',');
        }
        return cvsLine.toString();
    }

    /**
     * Transfer string list into CSV line
     */
    @SuppressWarnings("UnusedDeclaration")
    public static String toCSVLine(ArrayList strArrList)
    {
        if (strArrList == null)
            return "";
        String[] strArray = new String[strArrList.size()];
        for (int idx = 0; idx < strArrList.size(); idx++)
            strArray[idx] = (String) strArrList.get(idx);
        return toCSVLine(strArray);
    }


    private int countChar(String str, char c, int start)
    {
        int i = 0;
        int index = str.indexOf(c, start);
        return index == -1 ? i : countChar(str, c, index + 1) + 1;
    }

    private static int nextComma(String source, int st)
    {
        int maxPosition = source.length();
        boolean inquote = false;
        while (st < maxPosition)
        {
            char ch = source.charAt(st);
            if (!inquote && ch == ',')
                break;
            else if ('"' == ch)
                inquote = !inquote;
            st++;
        }
        return st;
    }

    private static String nextToken(String source, int st, int nextComma)
    {
        StringBuilder strb = new StringBuilder();
        int next = st;
        while (next < nextComma)
        {
            char ch = source.charAt(next++);
            if (ch == '"')
            {
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"'))
                {
                    strb.append(ch);
                    next++;
                }
            } else
                strb.append(ch);
        }
        return strb.toString();
    }

    private static String addQuote(String item)
    {
        if (item == null || item.length() == 0)
            return "\"\"";
        StringBuilder sb = new StringBuilder();
        sb.append('"');
        for (int idx = 0; idx < item.length(); idx++)
        {
            char ch = item.charAt(idx);
            if ('"' == ch)
                sb.append("\"\"");
            else
                sb.append(ch);
        }
        sb.append('"');
        return sb.toString();
    }
}
