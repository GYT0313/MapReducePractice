package com.gyt.invertedIndex;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @FileName: InvertedIndex.java
 * @Package: com.gyt.invertedIndex
 * @Author: Gu Yongtao
 * @Description: 倒排索引
 *
 * @Date: 2018年12月6日 下午4:24:20
 */

public class InvertedIndex {
	// 属性
	private static FileSplit inputSplit;
	
	/**
	 * Mapper静态内部类
	 * @author hadoop
	 *
	 */
	public static class InvertedMapper extends Mapper<LongWritable, Text, Text, Text> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Starting Mapper: ");
			// 分割
			StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
			
			// 获取切片来源文件名
			inputSplit = (FileSplit) context.getInputSplit();
			String fileName = inputSplit.getPath().getName().toString();
			// 按 key=单词:文件名  value=1 输出，如 map:file1.txt
			// 只匹配英文字母
			// Pattern对象
			Pattern r = Pattern.compile("([\\u4E00-\\u9FA5A-Za-z_]+)");
			while (stringTokenizer.hasMoreTokens()) {
				// Mather对象 
				Matcher m = r.matcher(stringTokenizer.nextToken());
				// 遍历
				while (m.find()) {
					String wordFile = m.group(1) + ":" + fileName;
					context.write(new Text(wordFile), new Text("1"));
				}
			}
		}
	}
	
	/**
	 * Combiner静态内部类
	 * @author hadoop
	 *
	 */
	public static class InvertedCombiner extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Starting Combiner: ");
			// 将 key=word:file，value=1 进行计数
			int sum = 0;
			for (Text val : values) {
				sum += Integer.parseInt(val.toString());
			}
			// 将 key=word:file拆分
			int index = key.toString().indexOf(":");
			String word = key.toString().substring(0, index);
			String fileName = key.toString().substring(index+1);

			// 按 value=filename:counts 输出
			context.write(new Text(word), new Text(fileName + ":" + sum));
		}
		
	}
	
	
	
	/**
	 * Reducer静态内部类
	 * @author hadoop
	 *
	 */
	public static class InvertedReducere extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Starting Reducer: ");
			
			// 遍历 values，用 ";" 组合
			String result = "\t";
			for (Text val : values) {
				result += val.toString() + ";";
			}
			context.write(key, new Text(result));
		}
		
	}
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// 驱动类
		Configuration configuration = new Configuration();
		// 若存在输出目录，则先删除，解决每次运行任务需要先把输出目录删除
		/*
		 * 两种方式得到FileSystem
		 * 1. 使用new Path().getFileSystem()
		 * 2. 使用FileSystem.get()    该方法将报错，解决办法：将core-site.xml和hdfs-site.xml复制到 src目录
		 */
//		FileSystem fileSystem = FileSystem.get(configuration);
		FileSystem fileSystem = new Path(args[1]).getFileSystem(configuration);
		fileSystem.delete(new Path(args[1]), true);
		
		// Job
		Job job = Job.getInstance(configuration);
		// 设置Class
		job.setMapperClass(InvertedMapper.class);
		job.setCombinerClass(InvertedCombiner.class);
		job.setReducerClass(InvertedReducere.class);
		// Map输出类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		// Reduce输出类型
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
