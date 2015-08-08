package com.github.flux.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.flux.util.ImageUtils;
import com.github.flux.util.StringUtils;
import com.github.flux.util.result.BaseResult;
import com.github.flux.util.result.MapResult;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("/web")
public class CommonController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	/** 上传目录名*/
	private static final String uploadFolderName = "uploadFiles";

	/** 允许上传的扩展名*/
	private static final String [] extensionPermit = {"jpg", "png", "jpeg"};

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fileUpload(@RequestParam("file") CommonsMultipartFile file, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		logger.info("UploadController#fileUpload() start");
		
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			//清除上次上传进度信息
			String curProjectPath = session.getServletContext().getRealPath("/");
			String saveDirectoryPath = curProjectPath + "/" + uploadFolderName;
			File saveDirectory = new File(saveDirectoryPath);
			logger.debug("Project real path [" + saveDirectory.getAbsolutePath() + "]");
	
			// 判断文件是否存在
			if (!file.isEmpty()) {
				String fileName = file.getOriginalFilename();
				String fileExtension = FilenameUtils.getExtension(fileName);
				if(!ArrayUtils.contains(extensionPermit, fileExtension)) {
					return MapResult.initMap(BaseResult.INVALID_PARAMETER.getCode(), BaseResult.INVALID_PARAMETER.getMsg());
				}
				/***图片文件名称 以我司编号 工厂编号和分色文件号命名***/
				String srcname = "src-"+System.currentTimeMillis()+ "." + fileExtension;
				String smallname = "small-"+System.currentTimeMillis()+ "." + fileExtension;
				file.transferTo(new File(saveDirectory, srcname)); // 保存
				ImageUtils.scale2(saveDirectory+"/"+srcname, saveDirectory+"/" + smallname, 50, 50, true);
				map = MapResult.successMap();
				map.put("url", "/" + uploadFolderName + "/" + smallname);
				map.put("picture", "/" + uploadFolderName + "/" + srcname + "|" + "/" + uploadFolderName + "/" + smallname);
			} else {
				map.put("code", "0");
				map.put("msg", "请选择文件");
			}
			logger.info("UploadController#fileUpload() end");
		}catch(Exception ex) {
			logger.error("", ex);
			return MapResult.failMap();
		}
		logger.info("result:{}", map);
		return map;
	}

	public static String getUploadfoldername() {
		return uploadFolderName;
	}

	
	@Resource
	private Producer captchaProducer;

	/**
	 * 获取验证码
	 */
	@RequestMapping(value = "/getKaptchaImage", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getKaptchaImage(//
			HttpServletRequest request, //
			@RequestParam(value = "uuid", required = true) String uuid, //
			HttpServletResponse response//
	) throws Exception {
		if (StringUtils.isEmpty(uuid)) {
			return MapResult.failMap();
		}
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		// @TODO 保存，以便下次对比
		// accountResourceClient.saveVerifyCode(uuid, capText);
		
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

}
