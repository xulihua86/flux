package com.github.flux.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(MysqlUtils.class);

	private static final String dbuser = ConfigUtil
			.getStringValue("jdbc.username");
	private static final String dbpasswd = ConfigUtil
			.getStringValue("jdbc.password");
	private static final String mysqlbin = ConfigUtil
			.getStringValue("mysql.bin");
	private static final String backuppath = ConfigUtil
			.getStringValue("db.backup.path");

	/**
	 * 具体用法如下： mysqldump -u用户名 -p密码 -d 数据库名 表名 脚本名;
	 * 
	 * 1、导出数据库为dbname的表结构（其中用户名为root,密码为dbpasswd,生成的脚本名为db.sql） mysqldump -uroot
	 * -pdbpasswd -d dbname >db.sql;
	 * 
	 * 2、导出数据库为dbname某张表(test)结构 mysqldump -uroot -pdbpasswd -d dbname
	 * test>db.sql;
	 * 
	 * 3、导出数据库为dbname所有表结构及表数据（不加-d） mysqldump -uroot -pdbpasswd dbname >db.sql;
	 * 
	 * 4、导出数据库为dbname某张表(test)结构及表数据（不加-d） mysqldump -uroot -pdbpasswd dbname
	 * test>db.sql;
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// backup();
		// load();
	}

	/**
	 * 备份检验一个sql文件是否可以做导入文件用的一个判断方法：把该sql文件分别用记事本和ultra
	 * edit打开，如果看到的中文均正常没有乱码，则可以用来做导入的源文件（不管sql文件的编码格式如何，也不管db的编码格式如何）
	 */
	public static void backup(String db) {
		try {
			Runtime rt = Runtime.getRuntime();

			// 调用 mysql 的 cmd:
			Process child = rt
					.exec(mysqlbin + "mysqldump -u" + dbuser +" -p"+ dbpasswd + " " + db);// 设置导出编码为utf8。这里必须是utf8

			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

			InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			BufferedReader br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			// 要用来做导入用的sql目标文件：
			FileOutputStream fout = new FileOutputStream(backuppath + "all.sql");
			OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();

			// 别忘记关闭输入输出流
			in.close();
			xx.close();
			br.close();
			writer.close();
			fout.close();

			System.out.println("/* Output OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导入 导入的时候需要数据库已经建好。
	 */
	public static void load(String db) {
		try {
			String fPath = backuppath + "all.sql";
			Runtime rt = Runtime.getRuntime();

			// 调用 mysql 的 cmd:
			// rt.exec("create database demo");
			Process child = rt.exec(mysqlbin + "mysql -u"+ dbuser +" -p"+ dbpasswd +" " + db);
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(fPath), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();

			System.out.println("/* Load OK! */");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
