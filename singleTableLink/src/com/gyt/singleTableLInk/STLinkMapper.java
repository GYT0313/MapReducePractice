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
		// 利用集合将父子关系存储记录
		ArrayList<String> arrayList = new ArrayList<>();
		if (!STLinkJob.relationship.containsKey(sun)) { // 第一次记录
			arrayList.add(par);
			STLinkJob.relationship.put(sun, arrayList);
		} else { // 第二次记录
			arrayList = STLinkJob.relationship.get(sun);
			arrayList.add(par);
			STLinkJob.relationship.put(sun, arrayList);
		}
		context.write(new Text(sun), new Text(par));
	}

}
