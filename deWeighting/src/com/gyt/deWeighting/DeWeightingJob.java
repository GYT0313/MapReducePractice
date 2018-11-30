package com.gyt.deWeighting;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;



/**
 * @FileName: DeWeightingJob.java
 * @Package: com.gyt.deWeighting
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月27日 上午8:32:37
 */

public class DeWeightingJob {

	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration(); // 加载 xml 文件
		
		Job job = Job.getInstance(configuration);
		job.setJarByClass(DeWeightingJob.class); // 主类
		
		job.setMapperClass(DeWeightingMapper.class); // map
		job.setMapOutputKeyClass(Text.class); // key输出类型
		job.setMapOutputValueClass(Text.class); // value输出类型
		
		job.setReducerClass(DeWeightingReducer.class); // reduce
		job.setOutputKeyClass(Text.class); // reduce端 key输出类型
		job.setOutputValueClass(Text.class); // reduce端 value输出类型
		
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
