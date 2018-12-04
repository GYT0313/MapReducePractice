package com.gyt.singleTableLInk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @FileName: STLinkJob.java
 * @Package: com.gyt.singleTableLInk
 * @Author: Gu Yongtao
 * @Description: 单表关联
 *
 * @Date: 2018年11月27日 下午12:26:17
 */

public class STLinkJob {
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		
		job.setJarByClass(STLinkJob.class);
		// mapper
		job.setMapperClass(STLinkMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		// reducer
		job.setReducerClass(STLinkReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		Boolean flag = job.waitForCompletion(true);
		if (flag) {
			System.out.println("Job Success!");
		} else {
			System.out.println("Job failed!");
		}
	}

}
