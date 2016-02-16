package com.zszdevelop.planman.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @author 作者 ShengZhong E-mail: zszdevelop@163.com
 * @version 创建时间：2015年11月29日
 * 		UUID官方算法
 * 		通过Android设备和应用程序包名生成UUID，
 * 		这样在不同的Android设备以及不同的程序包名,
 * 		生成的UUID标识符必然是不一样的，保证其唯一性。
 */

public class UUIDInstallation {
	private static String sID = null;
	private static final String INSTALLATION = "INSTALLATION";

	 /**
	 * 返回该设备在此程序上的唯一标识符。
	 * @param context
	 *            Context对象。
	 * @return 表示该设备在此程序上的唯一标识符。
	*/
	public synchronized static String getUUID(Context context) {
		if (sID == null) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists())
					writeInstallationFile(installation);
				sID = readInstallationFile(installation);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return sID;
	}

	/**
	 * 读出保存在程序文件系统中的表示该设备在此程序上的唯一标识符。
	 * @param installation 
	 * 		保存唯一标识符的File对象。
	 * @return
	 * @throws IOException
	 */
	private static String readInstallationFile(File installation) throws IOException {
		RandomAccessFile f = new RandomAccessFile(installation, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	/**
	 *  将表示此设备在该程序上的唯一标识符写入程序文件系统中。
	 * @param installation
	 * 			保存唯一标识符的File对象。
	 * @throws IOException
	 */
	private static void writeInstallationFile(File installation) throws IOException {
		FileOutputStream out = new FileOutputStream(installation);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}

}
