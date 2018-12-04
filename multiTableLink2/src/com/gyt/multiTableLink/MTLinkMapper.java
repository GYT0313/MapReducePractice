package com.gyt.multiTableLink;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

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
		StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
		String column1 = stringTokenizer.nextToken();
		/*
		 *  读取文件内容可分为两种：编号-地址、公司-地址编号, 处理方法如下：
		 *  使用正则表达式判断数据的类型
		 */
		// 使用判断输入数据文件名进行分类
		FileSplit inputsplit = (FileSplit) context.getInputSplit();
		String filename = inputsplit.getPath().getName().toString();
		// 分类判断
		if (filename.endsWith("address.txt")) { // 属于编号-地址类型数据
			context.write(new Text(column1), new Text("+" + stringTokenizer.nextToken()));
		} else if (filename.endsWith("factory.txt")) { // 属于公司-地址编号类型
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
			context.write(new Text(numberOfaddress), new Text(companyName));
		} else {
			context.write(null, null);
		}
		
//		context.write(new Text(1), new Text(2));
		
		
		
		
		
		
		
/*		StringTokenizer stringTokenizer = new StringTokenizer(value.toString());
		String column1 = stringTokenizer.nextToken();
		
		 *  读取文件内容可分为两种：编号-地址、公司-地址编号, 处理方法如下：
		 *  使用正则表达式判断数据的类型
		 
		// 正则
		Matcher matcher = Pattern.compile("^[0-9]*$").matcher(column1);
		if (matcher.find()) { // 匹配到地址编号，属于编号-地址类型数据
//			MTLinkJob.numberAddress.put(column1, stringTokenizer.nextToken());
			context.write(new Text(column1), new Text("+"+stringTokenizer.nextToken()));
			
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
			context.write(new Text(numberOfaddress), new Text(companyName));
		}*/
	}



}
