package kweber.uno.hadoop.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;


public class Main {

    public static void main(String[] args) throws Exception {
        if(args.length < 2){
            throw new IllegalArgumentException("Invalid Usage: inputDirectory outputDirectory");
        }



        int res = ToolRunner.run(new Configuration(), new WordCount(), args);
        System.exit(res);
    }


}
