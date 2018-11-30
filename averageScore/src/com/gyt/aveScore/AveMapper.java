package com.gyt.aveScore;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: AveMapper.java
 * @Package: com.gyt.aveScore
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月22日 下午2:25:05
 */

public class AveMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		String[] values = line.split(" ");
		context.write(new Text(values[0] + " " + values[1]), new FloatWritable(Float.parseFloat(values[2])));
	}

}
