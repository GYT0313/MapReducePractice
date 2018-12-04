package com.gyt.singleTableLInk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: STLinkMapper.java
 * @Package: com.gyt.singleTableLInk
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月27日 上午11:58:58
 */

public class STLinkMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
		String sun = stringTokenizer.nextToken();
		String par = stringTokenizer.nextToken();
		// 利用标识符区分
		context.write(new Text(sun), new Text("+" + par));
		context.write(new Text(par), new Text("-" + sun));
	}

}
