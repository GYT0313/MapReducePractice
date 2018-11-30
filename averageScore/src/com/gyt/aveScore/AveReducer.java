package com.gyt.aveScore;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
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

public class AveReducer extends Reducer<Text, FloatWritable, Text, FloatWritable>{

	@Override
	protected void reduce(Text key, Iterable<FloatWritable> values, // HDFS类型迭代器
			Context context) throws IOException, InterruptedException {
		float sum = 0f;
		float i = 0f;
		for (FloatWritable value : values) { // 计数
			i++;
			sum = sum + value.get();
		}
		String[] keys = key.toString().split(" ");
		context.write(new Text(keys[1]), new FloatWritable(Float.parseFloat(String.format("%.1f", sum/i))));
	}

}
