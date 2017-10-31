package gmarket.itheima.cn.gmarket.utils;

import org.xutils.common.util.LogUtil;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				LogUtil.e(String.valueOf(e));
			}
		}
		return true;
	}
}
