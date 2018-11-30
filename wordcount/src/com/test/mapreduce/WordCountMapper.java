package com.test.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: WordCountMapper.java
 * @Package: com.test.mapreduce
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月20日 上午8:44:24
 */

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString(); // 每行输入的Text类型转换为Stringe类型
		String[] words = line.split(" "); // 以空格分割
		for (String word : words) { // 计数
			context.write(new Text(word), new IntWritable(1));
		}
		
	}

}
