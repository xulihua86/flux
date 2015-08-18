package com.sinowel.netflow.common;
//package com.sinowel.wechat.common;
//
//import java.awt.Container;
//import java.awt.Image;
//import java.awt.MediaTracker;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import org.apache.log4j.Logger;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//public class FileCompress {
//
//	Logger logger = Logger.getLogger(FileCompress.class);
//
//	/**
//	 * 
//	 * @param f
//	 *            图片输出路径
//	 * @param filelist
//	 *            图片路径
//	 * @param ext
//	 *            缩略图扩展名
//	 * @param n
//	 *            图片名
//	 * @param w
//	 *            目标宽
//	 * @param h
//	 *            目标高
//	 * @param per
//	 *            百分比
//	 */
//	public void Tosmallerpic(File oldFile, File newFile, int width, int height,
//			float quality) {
//		if (!newFile.getParentFile().exists()) {
//			newFile.getParentFile().mkdirs();
//		}
//		Image src = null;
//		BufferedImage tag = null;
//		FileOutputStream newimage = null;
//		try {
//			try {
//				src = javax.imageio.ImageIO.read(oldFile); // 构造Image对象
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.info(oldFile.getName() + "图片的ICC信息可能已经被破坏开始转换:");
//				try {
//					ThumbnailConvert convert = new ThumbnailConvert();
//					// convert.setCMYK_COMMAND(oldFile.getPath());
//					String CMYK_COMMAND = "mogrify -colorspace RGB -quality 100 file1";// 转换cmyk格式
//					convert.exeCommand(CMYK_COMMAND.replace("file1",
//							oldFile.getPath()));
//					src = Toolkit.getDefaultToolkit().getImage(
//							oldFile.getPath());
//					MediaTracker mediaTracker = new MediaTracker(
//							new Container());
//					mediaTracker.addImage(src, 0);
//					mediaTracker.waitForID(0);
//					src.getWidth(null);
//					src.getHeight(null);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//			// ,String ext 保留字段 缩略图拼接字段
//			// String img_midname=newFile;
//			int old_w = src.getWidth(null) == -1 ? width : src.getWidth(null); // 得到源图宽
//			int old_h = src.getHeight(null) == -1 ? height : src
//					.getHeight(null);
//			int new_w = 0;
//			int new_h = 0; // 得到源图长
//			double w2 = (old_w * 1.00) / (width * 1.00);
//			double h2 = (old_h * 1.00) / (height * 1.00);
//			// 图片调整为方形结束
//			if (old_w > width)
//				new_w = (int) Math.round(old_w / w2);
//			else
//				new_w = old_w;
//			if (old_h > height)
//				new_h = (int) Math.round(old_h / h2);// 计算新图长宽
//			else
//				new_h = old_h;
//			tag = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
//			// tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图
//			tag.getGraphics().drawImage(
//					src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0,
//					0, null);
//			newimage = new FileOutputStream(newFile); // 输出到文件流
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//			/* 压缩质量 */
//			jep.setQuality(quality, true);
//			encoder.encode(tag, jep);
//			// encoder.encode(tag); //近JPEG编码
//			newimage.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			logger.info(oldFile.getName() + "图片压缩问题;" + ex);
//			// Logger.getLogger(File:mpress.class.getName()).log(Level.SEVERE,
//			// null, ex);
//		} finally {
//			if (newimage != null) {
//				try {
//					newimage.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (tag != null) {
//				tag.flush();
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		String oldpath = "D:\\20120918052939765.jpg";
//		String newpath = "D:\\000.jpg";
//		FileCompress ddd = new FileCompress();
//		ddd.Tosmallerpic(new File(oldpath), new File(newpath), 300, 200, 1f);
//	}
//}
