package com.gyt.multiTableLink;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


/**
 * @FileName: MTLinkJob.java
 * @Package: com.gyt.multiTableLink
 * @Author: Gu Yongtao
 * @Description: 多表关联
 *
 * @Date: 2018年11月30日 上午10:52:42
 */

public class MTLinkJob {
	public static Map<String, String> numberAddress = new HashMap<>();
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		// 主类
		job.setJarByClass(MTLinkJob.class);
		// Mapper
		job.setMapperClass(MTLinkMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		// Reducer
		job.setReducerClass(MTLinkReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		// 输入输出路径
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		Boolean flag = job.waitForCompletion(true);
		if (flag) {
			System.out.println("Job success!");
		} else {
			System.out.println("Job failed!");
		}
	}
}
