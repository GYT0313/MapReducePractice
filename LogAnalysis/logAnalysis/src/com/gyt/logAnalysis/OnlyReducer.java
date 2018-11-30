package com.gyt.logAnalysis;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @FileName: LogAnalysisReducer.java
 * @Package: com.gyt.logAnalysis
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月20日 下午4:22:27
 */

public class OnlyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		for (IntWritable value : values) {
			sum = sum + value.get();
		}
		context.write(key, new IntWritable(sum));
	}

}
