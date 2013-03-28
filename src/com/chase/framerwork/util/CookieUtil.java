package com.chase.framerwork.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 操作Cookie工具类
 * @author Chase
 *
 */
public final class CookieUtil {

	/**
	 * 不可实例化
	 */
	private CookieUtil() {
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param path
	 *            路径
	 * @param maxAge
	 *            有效期(单位: 秒)
	 * @param domain
	 *            域
	 * @param secure
	 *            是否启用加密
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, String path, Integer maxAge, String domain, Boolean secure) {
		
		try {
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		addCookie(request, response, name, value, null, null, null, null);
	}

	/**
	 * 添加cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param path
	 *            路径
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, String path) {
		addCookie(request, response, name, value, path, null, null, null);
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param name
	 *            cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 * @param domain
	 *            域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
		
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		if (StringUtils.isNotEmpty(path)) {
			cookie.setPath(path);
		}
		if (StringUtils.isNotEmpty(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		removeCookie(request, response, name, null, null);
	}

	/**
	 * 移除cookie
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param name
	 *            cookie名称
	 * @param path
	 *            路径
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path) {
		removeCookie(request, response, name, path, null);
	}

}