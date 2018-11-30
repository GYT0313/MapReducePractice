package com.gyt.deWeighting;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @FileName: DeWeightingReducer.java
 * @Package: com.gyt.deWeighting
 * @Author: Gu Yongtao
 * @Description: [文件描述]
 *
 * @Date: 2018年11月27日 上午8:32:20
 */

public class DeWeightingReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	protected void reduce(Text key, Iterable<Text> values,
			Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		context.write(key, new Text(""));
	}

}
