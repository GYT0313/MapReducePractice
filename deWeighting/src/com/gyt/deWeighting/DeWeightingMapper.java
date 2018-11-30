package com.gyt.deWeighting;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: DeWeightingMain.java
 * @Package: com.gyt.deWeighting
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月27日 上午8:31:47
 */

public class DeWeightingMapper extends Mapper<LongWritable, Text, Text, Text>{

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		String lines = value.toString();
		// Pattern对象
		Pattern r = Pattern.compile("([\\u4E00-\\u9FA5A-Za-z_]+)");
		// Mather对象 
		Matcher m = r.matcher(lines);
		// 遍历
		while (m.find()) {
			String string = m.group(1);
			context.write(new Text(string), new Text(""));
		}
	}
	
}
