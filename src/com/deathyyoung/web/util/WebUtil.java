package com.deathyyoung.web.util;

/**
 * 
 * @author <a href="http://clog.deathyyoung.com" target="_blank">Deathy
 *         Young</a> (<a href="mailto:mapleyeh@qq.com" >mapleyeh@qq.com</a>)
 */
public class WebUtil {
	/**
	 * 传入http请求的UserAgent 根据它判断是手机还是电脑发送过来的请求
	 * 
	 */
	public static boolean choose(String userAgent) {
		if (userAgent.indexOf("Noki") > -1 || // Nokia phones and emulators
				userAgent.indexOf("Eric") > -1 || // Ericsson WAP phones and
													// emulators
				userAgent.indexOf("WapI") > -1 || // Ericsson WapIDE 2.0
				userAgent.indexOf("MC21") > -1 || // Ericsson MC218
				userAgent.indexOf("AUR") > -1 || // Ericsson R320
				userAgent.indexOf("R380") > -1 || // Ericsson R380
				userAgent.indexOf("UP.B") > -1 || // UP.Browser
				userAgent.indexOf("WinW") > -1 || // WinWAP browser
				userAgent.indexOf("UPG1") > -1 || // UP.SDK 4.0
				userAgent.indexOf("upsi") > -1 || // another kind of UP.Browser
				userAgent.indexOf("QWAP") > -1 || // unknown QWAPPER browser
				userAgent.indexOf("Jigs") > -1 || // unknown JigSaw browser
				userAgent.indexOf("Java") > -1 || // unknown Java based browser
				userAgent.indexOf("Alca") > -1 || // unknown Alcatel-BE3 browser
													// (UP based)
				userAgent.indexOf("MITS") > -1 || // unknown Mitsubishi browser
				userAgent.indexOf("MOT-") > -1 || // unknown browser (UP based)
				userAgent.indexOf("My S") > -1 || // unknown Ericsson devkit
													// browser
				userAgent.indexOf("WAPJ") > -1 || // Virtual WAPJAG
													// www.wapjag.de
				userAgent.indexOf("fetc") > -1 || // fetchpage.cgi Perl script
													// from www.wapcab.de
				userAgent.indexOf("ALAV") > -1 || // yet another unknown UP
													// based browser
				userAgent.indexOf("Wapa") > -1 || // another unknown browser
													// (Web based "Wapalyzer")
				userAgent.indexOf("Oper") > -1) {
			return true;
		} else {
			return false;
		}
	}

}
