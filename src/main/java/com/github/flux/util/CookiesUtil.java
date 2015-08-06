package com.github.flux.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * cookies操作
 */
public class CookiesUtil {
  private final static Logger logger = LoggerFactory.getLogger(CookiesUtil.class);
  private static CookiesUtil instance = null;
  /**
   * 用户登录标记
   */
  public final static String loginFlag = "imp.info";

  private CookiesUtil() {

  }

  public static CookiesUtil getInstance() {
    if (instance == null) {
      synchronized (CookiesUtil.class) {
        if (instance == null) {
          try {
            instance = new CookiesUtil();
          }
          catch (Exception e) {
            logger.error("", e);
          }
        }
      }

    }
    return instance;
  }

  /**
   * 获取当前用户cookies值
   * 
   * @param cookies
   * @return
   */
  public String getCookieValue(HttpServletRequest request) {
    String result = null;
    String uri = request.getRequestURI();
    if (StringUtils.contains(uri, "/rest/")) {
      String auth = request.getHeader("Authorization");
      if (StringUtils.isNotBlank(auth))
        result = ThreeDes.decrypt(auth);
    } else {
      Cookie[] cookies = request.getCookies();
      if (cookies != null && cookies.length > 0)
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals(loginFlag)) {
            try {
              result = ThreeDes.decrypt(cookie.getValue());
            }
            catch (Exception e) {
              logger.error("getCookieValue", e);
            }
            break;
          }
        }
    }
    // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
    if (StringUtils.isNotBlank(result)) {
      String[] cookieValue = result.split("[|]", 7);
      if (cookieValue.length != 7)
        result = null;
    }
    return result;
  }

  public boolean isCookieValid(String encryptCookie) {
    String result = null;
    if (StringUtils.isNotEmpty(StringUtils.trimToEmpty(encryptCookie))) {
      try {
        result = ThreeDes.decrypt(encryptCookie);
      }
      catch (Exception e) {
        logger.error("isCookieValid", e);
      }
    }
    if (StringUtils.isNotEmpty(result)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      return result.split("[|]").length == 7;
    }
    return false;
  }

  /**
   * 获取当前用户cookies值，并已拆分为数组
   * 
   * @param cookies
   * @return
   */
  public String[] getCookieArrayValue(HttpServletRequest request) {
    String result = null;
    String uri = request.getRequestURI();
    if (StringUtils.contains(uri, "/web/")) {
      Cookie[] cookies = request.getCookies();
      if (cookies != null && cookies.length > 0)
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals(loginFlag)) {
            try {
              result = ThreeDes.decrypt(cookie.getValue());
            }
            catch (Exception e) {
              logger.error("getCookieValue", e);
            }
            break;
          }
        }
    } else {
      String auth = request.getHeader("Authorization");
      if (StringUtils.isNotBlank(auth))
        result = ThreeDes.decrypt(auth);
    }
    // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
    if (StringUtils.isNotBlank(result)) {
      String[] cookieValue = result.split("[|]", 7);
      if (cookieValue.length == 7)
        return cookieValue;
    }
    return null;
  }

  /**
   * 添加登录用户email作为cookies
   * 
   * @param email
   * @param response
   * @return
   */
  public Cookie addCookie(int expireSeconds, String code, HttpServletResponse response) {
    Cookie cookie = null;
    try {
      cookie = new Cookie(loginFlag, ThreeDes.encrypt(code));
      cookie.setPath("/");
      cookie.setMaxAge(expireSeconds);// cookie保存7天
      response.addCookie(cookie);
      logger.info("add cookies code=" + code);
    }
    catch (Exception e) {
      logger.error("", e);
    }
    return cookie;
  }

  /**
   * 退出 移除cookies
   * 
   * @param request
   * @param response
   * @return
   */
  public boolean removeCookie(HttpServletRequest request, HttpServletResponse response) {
    boolean b = false;
    Cookie[] cookies = request.getCookies();
    // cookies不为空，则清除
    if (cookies != null) {
      try {
        for (int i = 0; i < cookies.length; i++) {
          if (StringUtils.equals(loginFlag, cookies[i].getName())) {
            Cookie cookie = new Cookie(cookies[i].getName(), null);
            cookie.setMaxAge(0);
            cookie.setPath("/");// 根据你创建cookie的路径进行填写
            response.addCookie(cookie);
            break;
          }
        }
      }
      catch (Exception e) {
        logger.error("removeCookie", e);
      }
      b = true;
    }
    return b;
  }

  public long getUserId(HttpServletRequest request) {
    long userId = 0L;
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return Long.parseLong(decryptValue[0]);
      }
    }
    return userId;
  }

  public String getToken(HttpServletRequest request) {
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return decryptValue[3];
      }
    }
    return null;
  }

  public String getUserDeviceType(HttpServletRequest request) {
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return decryptValue[1];
      }
    }
    return null;
  }

  public String getUserDeviceId(HttpServletRequest request) {
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return decryptValue[2];
      }
    }
    return null;
  }

  public String getAppVer(HttpServletRequest request) {
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return decryptValue[5];
      }
    }
    return null;
  }

  public String getAppId(HttpServletRequest request) {
    String cookieValue = getCookieValue(request);
    if (StringUtils.isNotEmpty(cookieValue)) {
      // userId|deviceType|deviceId|token|lastLoginTime|appVer|appId
      String[] decryptValue = cookieValue.split("[|]", 7);
      if (decryptValue.length == 7) {
        return decryptValue[6];
      }
    }
    return null;
  }

}
