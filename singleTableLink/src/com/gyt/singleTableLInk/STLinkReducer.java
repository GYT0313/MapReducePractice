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
		ArrayList<String> parents = new ArrayList<>();
		// 通过遍历父母，查询map关系来确定爷奶
		for (Text parent : values) { // 父母
			String par = parent.toString();
			if (STLinkJob.relationship.containsKey(par)) {
				parents = STLinkJob.relationship.get(par);
				for (String grand : parents) { // 爷奶
					context.write(key, new Text(grand));
				}
			}
		}
	}

}
