package com.gyt.logAnalysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.DocFlavor.STRING;

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

public class DetailMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		StringTokenizer iTokenizer = new StringTokenizer(value.toString());
		
		String[] provinceCountries = {"北京市", "天津市", "上海市", "重庆市", "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", 
				"江苏省", "浙江省","安徽省", "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省", "海南省",
				"四川省", "贵州省", "云南省", "陕西省", "甘肃省", "青海省", "台湾省", "内蒙古", "广西", "西藏", "宁夏", "新疆",
				"香港", "澳门", "美国", "英国", "荷兰", "德国", "马来西亚", "韩国", "日本", "俄罗斯", "加拿大", "印度", 
				"澳大利亚", "瑞士", "拉脱维亚", "意大利", "波兰", "新加坡", "摩洛哥", "泰国", "白俄罗斯", "瑞典", "墨西哥", 
				"马恩岛", "乌克兰", "法国", "柬埔寨", "玻利维亚", "其他国家和地区"};
		// 获得分割
		String str1 = iTokenizer.nextToken();
		String str2 = iTokenizer.nextToken();
		// 正则匹配
		for (int i=0; i<provinceCountries.length; i++) {
			// Pattern对象
			Pattern r = Pattern.compile("^" + provinceCountries[i]);
			// Mather对象
			Matcher m = r.matcher(str1);
			if (m.find()) { // 匹配到
				context.write(new Text(provinceCountries[i]), 
						new IntWritable(Integer.parseInt(str2)));
				break;
			}
			// 属于其他国家和地区
			if (i == provinceCountries.length-1) {
				context.write(new Text(provinceCountries[provinceCountries.length-1]), 
						new IntWritable(Integer.parseInt(str2)));
				break;
			}
		}
	}

}
