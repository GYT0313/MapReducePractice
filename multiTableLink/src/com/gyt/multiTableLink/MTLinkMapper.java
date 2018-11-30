package com.gyt.multiTableLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @FileName: MTLinkMapper.java
 * @Package: com.gyt.multiTableLink
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月30日 上午10:51:59
 */

public class MTLinkMapper extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		StringTokenizer stringTokenizer = new StringTokenizer(value.toString()); // 好像用String.split(" ")分割更简单点
		String column1 = stringTokenizer.nextToken();
		/*
		 *  读取文件内容可分为两种：编号-地址、公司-地址编号, 处理方法如下：
		 *  使用正则表达式判断数据的类型
		 *  将属于编号-地址类数据存入静态便利
		 *  将属于公司-地址编号类输出到reducer端
		 */
		// 正则
		Matcher matcher = Pattern.compile("^[0-9]*$").matcher(column1);
		if (matcher.find()) { // 匹配到地址编号，属于编号-地址类型数据
			MTLinkJob.numberAddress.put(column1, stringTokenizer.nextToken());
		} else { // 属于公司-地址编号类型
			// 由于公司名称会被分割，所以先存储分割的片段，最后一个片段则为地址编号
			String companyName = "" + column1; // 公司名称
			String numberOfaddress = null;
			while (stringTokenizer.hasMoreTokens()) {
				if (stringTokenizer.countTokens() > 1) { // countTokens=剩余数量。不是最后一个片段则拼接为公司名称
					companyName = companyName + " " + stringTokenizer.nextToken().toString();
				} else { // 最后一个片段为地址编号
					numberOfaddress = stringTokenizer.nextToken().toString();
				}
			}
			context.write(new Text(companyName), new Text(numberOfaddress));
		}
	}

}
