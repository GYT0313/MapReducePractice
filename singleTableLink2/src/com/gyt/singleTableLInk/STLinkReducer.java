package com.gyt.singleTableLInk;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @FileName: STLinkReducer.java
 * @Package: com.gyt.singleTableLInk
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月27日 下午12:25:56
 */

public class STLinkReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		ArrayList<Text> suns = new ArrayList<>();
		ArrayList<Text> grands = new ArrayList<>();
		
		for (Text val : values) {
			if (val.toString().startsWith("+")) { // 爷
				grands.add(new Text(val.toString().substring(1)));
			} else if (val.toString().startsWith("-")) { // 孙
				suns.add(new Text(val.toString().substring(1)));
			}
		}
		// 遍历输出
		for (Text sun : suns) {
			for (Text grand : grands) {
				context.write(sun, grand);
			}
		}
	}

}
