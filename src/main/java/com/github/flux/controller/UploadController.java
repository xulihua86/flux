package com.github.flux.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.flux.entity.User;
import com.github.flux.service.UserService;
import com.github.flux.util.CookiesUtil;
import com.github.flux.util.result.BaseResult;

@Controller
@RequestMapping("/rest/user")
public class UploadController {

	@Resource
	private UserService userService;
	
	/** 日志对象*/
	private Log logger = LogFactory.getLog(this.getClass());

	/** 上传目录名*/
	private static final String uploadFolderName = "uploadFiles";

	/** 允许上传的扩展名*/
	private static final String [] extensionPermit = {"jpg", "png", "jpeg"};

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> fileUpload(@RequestParam("file") CommonsMultipartFile file, 
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		logger.info("UploadController#fileUpload() start");
		
		// 获取userId
		Long userId = CookiesUtil.getInstance().getUserId(request);
		
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
					// throw new NoSupportExtensionException("No Support extension.");
					map.put("code", BaseResult.FAILED.getCode());
					map.put("msg", "请选择正确的文件类型");
					return map;
				}

				String srcname = "src-"+System.currentTimeMillis()+ "." + fileExtension;
				// String smallname = "small-"+System.currentTimeMillis()+ "." + fileExtension;
				file.transferTo(new File(saveDirectory, srcname)); // 保存
				// ImageUtils.scale2(saveDirectory+"/"+srcname, saveDirectory+"/" + smallname, 50, 50, true);
				
				String logo = "/" + uploadFolderName + "/" + srcname;
				
				User user = new User();
				user.setUserid(userId);
				user.setLogo(logo);;
				userService.update(user);
				
				map.put("code", BaseResult.SUCCESS.getCode());
				map.put("url", logo);
				// map.put("picture", "/" + uploadFolderName + "/" + srcname + "|" + "/" + uploadFolderName + "/" + smallname);
			} else {
				map.put("code", BaseResult.FAILED.getCode());
				map.put("msg", "请选择文件");
			}
			logger.info("UploadController#fileUpload() end");
		}catch(Exception ex) {
			map.put("code", BaseResult.FAILED.getCode());
			map.put("msg", "error:" + ex.getMessage());
		}
		logger.info(map);
		return map;
	}

	public static String getUploadfoldername() {
		return uploadFolderName;
	}

}
