package com.test.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @FileName: WordCountReduccer.java
 * @Package: com.test.mapreduce
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月20日 上午9:18:21
 */

public class WordCountReduccer extends Reducer<Text, IntWritable, Text, IntWritable>{

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, // HDFS类型迭代器
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) { // 计数
			sum = sum + value.get();
		}
		context.write(key, new IntWritable(sum));
	}

}
