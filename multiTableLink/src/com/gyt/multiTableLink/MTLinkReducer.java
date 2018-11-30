package com.gyt.multiTableLink;

import java.io.IOException;

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
	private int tol = 0; // 写入行首提示
	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		if (tol++ == 0) {
			context.write(new Text("\tfactory"), new Text("\t\t\t\t\tcity"));
		}
		// 根据key，查找静态变量来确定地址
		String numberOfaddress = values.iterator().next().toString();
		if (MTLinkJob.numberAddress.containsKey(numberOfaddress)) {
			// 控制输出对其
			String spaceCounts = "";
			for (int i=0; i<30-key.toString().length(); i++) {
				spaceCounts = spaceCounts + " ";
			}
			context.write(new Text(key), new Text(spaceCounts + MTLinkJob.numberAddress.get(numberOfaddress)));
		}
	}

}
