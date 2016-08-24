package leo.carnival.worker;

import leo.carnival.worker.baseType.Processor;

import java.util.ArrayList;
import java.util.List;

public class ListCloner implements Processor<List, List> {

    private int cloneNum;
    public ListCloner(int cloneNum) {
        this.cloneNum = cloneNum;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List process(List list) {
        List rtnList = new ArrayList<>(list);
        for(int i=0;i<cloneNum;i++)
            rtnList.addAll(list);
        return rtnList;
    }
}
