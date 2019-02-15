package kweber.uno.hadoop.wordcount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.Tool;

import java.util.ArrayList;
import java.util.List;

public class WordCount extends Configured implements Tool {

    public int run(String[] args) throws Exception {

        JobConf job = new JobConf(getConf(), WordCount.class);
        job.setJobName("wordcount");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(Map.class);
        job.setCombinerClass(Reduce.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormat(TextInputFormat.class);
        job.setOutputFormat(TextOutputFormat.class);

        List<String> other_args = new ArrayList<String>();
        for (int i = 0; i < args.length; ++i) {
            if ("-skip".equals(args[i])) {
                DistributedCache.addCacheFile(new Path(args[++i]).toUri(), job);
                job.setBoolean("wordcount.skip.patterns", true);
            } else {
                other_args.add(args[i]);
            }
        }

        FileInputFormat.setInputPaths(job, new Path(other_args.get(0)));
        FileOutputFormat.setOutputPath(job, new Path(other_args.get(1)));

        JobClient.runJob(job);
        return 0;
    }
}
