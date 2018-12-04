package com.gyt.multiTableLink;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @FileName: MTLinkReducer.java
 * @Package: com.gyt.multiTableLink
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月30日 上午10:52:23
 */

public class MTLinkReducer extends Reducer<Text, Text, Text, Text> {
	private int tol = 0;
	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		if (tol++ == 0) { // 行首
			context.write(new Text("\tfactory"), new Text("\t\t\t\t\tcity"));
		}
		// 具体实现
		Text address = new Text(); 
		ArrayList<Text> companies = new ArrayList<>();
		// 区分地址和公司
		for (Text val : values) {
			if (val.toString().startsWith("+")) { // 地址
				address.set(val.toString().substring(1));
			} else { // 公司
				companies.add(new Text(val.toString())); // 直接赋值val，将导致companies值相同
			}
		}
		// 写入
		for (Text company : companies) {
			// 控制输出位置，尽量对齐
			String spaceCounts = "";
			for (int i=0; i<30-company.toString().length(); i++) {
				spaceCounts = spaceCounts + " ";
			}
			company.set(company.toString() + spaceCounts);
			context.write(company, address);
		}
	}

}
