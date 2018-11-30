package com.gyt.logAnalysis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @FileName: LogAnalysisJob.java
 * @Package: com.gyt.logAnalysis
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月20日 下午4:25:23
 */

public class MainJob {
	
	// 设置配置
	public boolean Work(String desc, Job job, MainJob mainJob, Class<?> mapperClass, Class<?> reducerClass, 
			String inputPath, String outputPath) throws IOException, ClassNotFoundException, InterruptedException {
		System.out.println("This is processing " + desc + " job: ");
		// 主类
		job.setJarByClass(mainJob.getClass());
		
		// mapper
		job.setMapperClass((Class<? extends Mapper>) mapperClass);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		// reducer
		job.setReducerClass((Class<? extends Reducer>) reducerClass);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 输入输出
		FileInputFormat.addInputPath(job, new Path(inputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPath));
		
		boolean flag = job.waitForCompletion(true);
		if (flag) {
			System.out.println("Job success!");
		} else {
			System.out.println("Job failed!");
		}
		return flag;
	}
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		
		MainJob mainJob = new MainJob();
		// 提取IP及访问次数
		mainJob.Work("IP and Count", Job.getInstance(configuration), mainJob, IPAndCountMapper.class, 
				OnlyReducer.class, args[0], args[1]);
		// 按地区名称提取
		boolean nameFlag = mainJob.Work("area", Job.getInstance(configuration), mainJob, AreaMapper.class, 
				OnlyReducer.class, args[2], args[3]);
		if (nameFlag) { // 按市、省、国家提取
			mainJob.Work("city, province and county", Job.getInstance(configuration), mainJob, DetailMapper.class, 
					OnlyReducer.class, args[3], args[4]); // 重点：输入目录为第二个job的输出目录
		}
	}
}
