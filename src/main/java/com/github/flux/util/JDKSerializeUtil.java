package com.github.flux.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDKSerializeUtil {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(JDKSerializeUtil.class);  
    protected static String charset = "UTF-8"; 
    
	/**
    public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {

		}
		return null;
	}
	**/
	
	/**
     * 对象序列化为字符串
     */
    public static String serialize2Str(Object obj) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);  
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");//必须是ISO-8859-1
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");//编码后字符串不是乱码（不编也不影响功能）
         logger.info("对象obj：【" + obj + "】序列化serStr：【" + serStr + "】");
        
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }
    
    /**
     * 字符串 反序列化为 对象
     */
    public static Object deserializeByStr(String serStr) throws Exception {
        String redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream); 
        Object obj = objectInputStream.readObject();
        logger.info("对象obj：【" + obj + "】反序列化serStr：【" + serStr + "】");
        
        objectInputStream.close();
        byteArrayInputStream.close();
        return obj;
    }

    
    public static <T> T decodeObject(byte[] b) {  
        return (T) deserialize(decompress(b));  
    }  
  
    public static byte[] encodeObject(Object o) {  
        byte[] b = serialize(o);  
        return compress(b);  
    }  
      
    /** 
     * Decode the string with the current character set. 
     */  
    public static String decodeString(byte[] data) {  
        String rv = null;  
        try {  
            if (data != null) {  
                rv = new String(data, charset);  
            }  
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException(e);  
        }  
        return rv;  
    }  
  
    /** 
     * Encode a string into the current character set. 
     */  
    public static byte[] encodeString(String in) {  
        byte[] rv = null;  
        try {  
            rv = in.getBytes(charset);  
        } catch (UnsupportedEncodingException e) {  
            throw new RuntimeException(e);  
        }  
        return rv;  
    }  
  
    /** 
     * Get the bytes representing the given serialized object. 
     */  
    public static byte[] serialize(Object o) {  
        if (o == null) {  
            throw new NullPointerException("Can't serialize null");  
        }  
        byte[] rv = null;  
        ByteArrayOutputStream bos = null;  
        ObjectOutputStream os = null;  
        try {  
            bos = new ByteArrayOutputStream();  
            os = new ObjectOutputStream(bos);  
            os.writeObject(o);  
            os.close();  
            bos.close();  
            rv = bos.toByteArray();  
        } catch (IOException e) {  
            throw new IllegalArgumentException("Non-serializable object", e);  
        } finally {  
            CloseUtil.close(os);  
            CloseUtil.close(bos);  
        }  
        return rv;  
    }  
  
    /** 
     * Get the object represented by the given serialized bytes. 
     */  
    public static Object deserialize(byte[] in) {  
        Object rv = null;  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if (in != null) {  
                bis = new ByteArrayInputStream(in);  
                is = new ObjectInputStream(bis);  
                rv = is.readObject();  
                is.close();  
                bis.close();  
            }  
        } catch (IOException e) {  
        	logger.warn("Caught IOException decoding "  
                    + (in == null ? 0 : in.length) + " bytes of data", e);  
        } catch (ClassNotFoundException e) {  
        	logger.warn("Caught CNFE decoding " + (in == null ? 0 : in.length)  
                    + " bytes of data", e);  
        } finally {  
            CloseUtil.close(is);  
            CloseUtil.close(bis);  
        }  
        return rv;  
    }  
  
    /** 
     * Compress the given array of bytes. 
     */  
    private static byte[] compress(byte[] in) {  
        if (in == null) {  
            throw new NullPointerException("Can't compress null");  
        }  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        GZIPOutputStream gz = null;  
        try {  
            gz = new GZIPOutputStream(bos);  
            gz.write(in);  
        } catch (IOException e) {  
            throw new RuntimeException("IO exception compressing data", e);  
        } finally {  
            CloseUtil.close(gz);  
            CloseUtil.close(bos);  
        }  
        byte[] rv = bos.toByteArray();  
        logger.info("Compressed " + in.length + " bytes to " + rv.length);  
        return rv;  
    }  
  
    /** 
     * Decompress the given array of bytes. 
     *  
     * @return null if the bytes cannot be decompressed 
     */  
    private static byte[] decompress(byte[] in) {  
        ByteArrayOutputStream bos = null;  
        if (in != null) {  
            ByteArrayInputStream bis = new ByteArrayInputStream(in);  
            bos = new ByteArrayOutputStream();  
            GZIPInputStream gis = null;  
            try {  
                gis = new GZIPInputStream(bis);  
  
                byte[] buf = new byte[8192];  
                int r = -1;  
                while ((r = gis.read(buf)) > 0) {  
                    bos.write(buf, 0, r);  
                }  
            } catch (IOException e) {  
            	logger.warn("Failed to decompress data", e);  
                bos = null;  
            } finally {  
                CloseUtil.close(gis);  
                CloseUtil.close(bis);  
                CloseUtil.close(bos);  
            }  
        }  
        return bos == null ? null : bos.toByteArray();  
    }  
  
    private static final class CloseUtil {  
        private CloseUtil() { }  
        public static void close(Closeable closeable) {  
            if (closeable != null) {  
                try {  
                    closeable.close();  
                } catch (Exception e) {  
                    logger.warn("Unable to close " + e.getMessage(), e);  
                }  
            }  
        }  
    }  

}
