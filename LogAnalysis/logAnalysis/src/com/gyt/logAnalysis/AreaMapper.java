package com.gyt.logAnalysis;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: LogAnalysisMapper.java
 * @Package: com.gyt.logAnalysis
 * @Author: Gu Yongtao
 * @Description: 日志访问IP地址分析
 *
 * @Date: 2018年11月20日 下午3:54:19
 */

public class AreaMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		StringTokenizer iTokenizer = new StringTokenizer(value.toString());
		if (iTokenizer.hasMoreTokens()) {
			String IP = iTokenizer.nextToken();
			String address = iTokenizer.nextToken();
			context.write(new Text(address), new IntWritable(1));
		}
	}

}
