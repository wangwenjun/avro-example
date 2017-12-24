package com.wangwenjun.avro;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;

/***************************************
 * @author:Alex Wang
 * @Date:2017/12/24
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class AvroCompiler
{
    public static void main(String[] args) throws InterruptedException
    {
        try
        {
            String baseDir = System.getProperty("user.dir");
            System.out.println();
            File avroDir = new File(baseDir, "avro");
            System.out.println(avroDir);

            CompilerTool.compile(new String[]{"compile", "schema",
                    "C:\\Users\\wangwenjun\\IdeaProjects\\avro-example\\avro\\user.avsc",
                    "C:\\Users\\wangwenjun\\IdeaProjects\\avro-example\\source\\user" });
            JavaCompiler systemJavaCompiler = ToolProvider.getSystemJavaCompiler();
            Iterable<String> options = Arrays.asList("-d", "C:\\Users\\wangwenjun\\IdeaProjects\\avro-example\\compile\\user");
            StandardJavaFileManager fileManager = systemJavaCompiler
                    .getStandardFileManager(null, null, null);

            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromFiles(Arrays.asList(new File(
                            "C:\\Users\\wangwenjun\\IdeaProjects\\avro-example\\source\\user\\example\\avro\\User.java")));

            JavaCompiler.CompilationTask task = systemJavaCompiler.getTask(null, fileManager, null, options,
                    null, compilationUnits);
            Boolean result = task.call();
            System.out.println("Compile:"+result);
            fileManager.close();
        }catch (Throwable e){
            e.printStackTrace();
            System.out.println("ERROR.");
        }
    }
}