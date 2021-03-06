package com.gyt.aveScore;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @FileName: WordCountJob.java
 * @Package: com.test.mapreduce
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月20日 上午9:32:19
 */

public class AveJob {

	public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration(); // 加载 xml 文件
		
		Job job = Job.getInstance(configuration);
		job.setJarByClass(AveJob.class); // 主类
		
		job.setMapperClass(AveMapper.class); // map
		job.setMapOutputKeyClass(Text.class); // key输出类型
		job.setMapOutputValueClass(FloatWritable.class); // value输出类型
		
		job.setReducerClass(AveReducer.class); // reduce
		job.setOutputKeyClass(Text.class); // reduce端 key输出类型
		job.setOutputValueClass(FloatWritable.class); // reduce端 value输出类型
		
		// 输入输出路径
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean flag = job.waitForCompletion(true);
		if (flag) {
			System.out.println("Job success!");
		} else {
			System.out.println("Job failed!");
		}
	}

}
