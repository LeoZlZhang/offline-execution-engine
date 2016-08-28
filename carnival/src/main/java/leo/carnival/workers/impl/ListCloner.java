package leo.carnival.workers.impl;

import leo.carnival.workers.baseType.Processor;

import java.util.ArrayList;
import java.util.List;

public class ListCloner implements Processor<List, List> {

    private int n;
    public ListCloner(int n) {
        this.n = n;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List process(List list) {
        List rtnList = new ArrayList<>(list);
        for(int i = 0; i< n; i++)
            rtnList.addAll(list);
        return rtnList;
    }

    @Override
    public List execute(List list) {
        return process(list);
    }
}
