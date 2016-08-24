package leo.carnival.worker.filter;

import com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Processor;

import java.io.File;
import java.util.List;

public interface Filter extends com.vipabc.vliveshow.TestExecutionEngine.Util.Worker.Filter<File>, Processor<File, List<File>>{
}
