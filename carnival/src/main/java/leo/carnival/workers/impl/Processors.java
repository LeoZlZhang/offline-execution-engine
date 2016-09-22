package leo.carnival.workers.impl;


import leo.carnival.workers.impl.Evaluator.FileEvaluator;
import leo.carnival.workers.impl.Evaluator.FolderEvaluator;
import leo.carnival.workers.impl.FileUtils.FileCollection2FileMap;
import leo.carnival.workers.impl.FileUtils.FileFilter;
import leo.carnival.workers.impl.FileUtils.FileFilterAdvance;
import leo.carnival.workers.impl.FileUtils.FolderFilter;
import leo.carnival.workers.impl.GearicUtils.ArrayClone;
import leo.carnival.workers.impl.GearicUtils.ClassLoader;
import leo.carnival.workers.impl.GearicUtils.DeepClone;
import leo.carnival.workers.impl.GearicUtils.NumberParser;
import leo.carnival.workers.impl.JsonUtils.ClassDecorator;
import leo.carnival.workers.impl.ReflectUtils.ReflectMethodFilter;
import leo.carnival.workers.prototype.Evaluator;

import java.lang.reflect.Method;

/**
 * Created by leo_zlzhang on 9/22/2016.
 * Container of all worker
 */
@SuppressWarnings("unused")
public class Processors {

    public static FileFilter FileFilter(FileEvaluator fileEvaluator) {
        return new FileFilter().setFileEvaluator(fileEvaluator);
    }

    public static FolderFilter FolderFilter(FolderEvaluator folderEvaluator) {
        return new FolderFilter().setFolderEvaluator(folderEvaluator);
    }

    public static FileFilterAdvance FileFilterAdvance(FolderFilter folderFilter, FileFilter fileFilter) {
        return new FileFilterAdvance().setFileFilter(fileFilter).setFolderFilter(folderFilter);
    }

    public static ReflectMethodFilter ReflectMethodFilter(Evaluator<Method> evaluator){
        return new ReflectMethodFilter().setEvaluator(evaluator);
    }

    public static <T> ArrayClone<T> ArrayClone(int timeToClone) {
        return new ArrayClone<T>().setCloneNum(timeToClone);
    }

    public static ClassLoader ClassLoader(){
        return new ClassLoader();
    }

    public static <T> DeepClone<T> DeepClone(){
        return new DeepClone<>();
    }

    public static <T> ClassDecorator<T> ClassDecorator(Class<T> cls){
        return new ClassDecorator<T>().setTargetClass(cls);
    }

    public static FileCollection2FileMap FileCollection2FileMap(){
        return new FileCollection2FileMap();
    }

    public static NumberParser NumberParser(){
        return new NumberParser();
    }



    private static final Processors processors = new Processors();

    private Processors(){}

    public static Processors build(){
        return processors;
    }
}
