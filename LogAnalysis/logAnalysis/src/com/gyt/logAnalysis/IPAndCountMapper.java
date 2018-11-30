package com.gyt.logAnalysis;

import java.io.IOException;
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
 * @Description: 提取IP及访问次数
 *
 * @Date: 2018年11月20日 下午3:54:19
 */

public class IPAndCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		// 使用正则表达匹配IP地址
		String pattern = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		// Pattern
		Pattern r = Pattern.compile(pattern);
		// Mather对象ext
		Matcher m = r.matcher(line);
		if (m.find()) {
			context.write(new Text(m.group(0)), new IntWritable(1));
		}
	}
	// String pattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
}
