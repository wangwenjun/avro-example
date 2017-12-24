package com.wangwenjun.avro;

import org.apache.avro.tool.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/***************************************
 * @author:Alex Wang
 * @Date:2017/12/24
 * QQ: 532500648
 * QQ群:463962286
 ***************************************/
public class CompilerTool
{
    final static Map<String, Tool> tools = new TreeMap();
    static int maxLen = 0;

    CompilerTool() {
        Tool[] toolArrays = new Tool[]{new CatTool(), new SpecificCompilerTool(), new InduceSchemaTool(), new JsonToBinaryFragmentTool(), new BinaryFragmentToJsonTool(), new CreateRandomFileTool(), new DataFileReadTool(), new DataFileWriteTool(), new DataFileGetMetaTool(), new DataFileGetSchemaTool(), new DataFileRepairTool(), new IdlTool(), new IdlToSchemataTool(), new RecodecTool(), new ConcatTool(), new RpcReceiveTool(), new RpcSendTool(), new RpcProtocolTool(), new FromTextTool(), new ToTextTool(), new ToTrevniTool(), new TetherTool(), new TrevniCreateRandomTool(), new TrevniMetadataTool(), new TrevniToJsonTool()};
        int len$ = toolArrays.length;

        for(int i = 0; i < len$; ++i) {
            Tool tool = toolArrays[i];
            Tool prev = (Tool)this.tools.put(tool.getName(), tool);
            if (prev != null) {
                throw new AssertionError("Two tools with identical names: " + tool + ", " + prev);
            }

            this.maxLen = Math.max(tool.getName().length(), this.maxLen);
        }

    }

    public static void compile(String[] args) throws Exception{
        run(args);
    }

    private static int run(String[] args) throws Exception {
        if (args.length != 0) {
            Tool tool = (Tool)tools.get(args[0]);
            if (tool != null) {
                return tool.run(System.in, System.out, System.err, Arrays.asList(args).subList(1, args.length));
            }
        }

        System.err.print("Version ");
        printStream(Main.class.getClassLoader().getResourceAsStream("VERSION.txt"));
        System.err.print(" of ");
        printHead(Main.class.getClassLoader().getResourceAsStream("META-INF/NOTICE"), 5);
        System.err.println("----------------");
        System.err.println("Available tools:");
        Iterator i$ = tools.values().iterator();

        while(i$.hasNext()) {
            Tool k = (Tool)i$.next();
            System.err.printf("%" + maxLen + "s  %s\n", k.getName(), k.getShortDescription());
        }

        return 1;
    }

    private static void printStream(InputStream in) throws Exception {
        byte[] buffer = new byte[1024];

        for(int i = in.read(buffer); i != -1; i = in.read(buffer)) {
            System.err.write(buffer, 0, i);
        }

    }

    private static void printHead(InputStream in, int lines) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));

        for(int i = 0; i < lines; ++i) {
            String line = r.readLine();
            if (line == null) {
                break;
            }

            System.err.println(line);
        }

    }
}
