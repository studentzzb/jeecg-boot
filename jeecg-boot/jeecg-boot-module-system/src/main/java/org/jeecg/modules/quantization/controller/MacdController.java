package org.jeecg.modules.quantization.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

@RestController
@RequestMapping("/macd")
@Slf4j
public class MacdController {

	private final String JSON_PATH = "classpath:org/jeecg/modules/demo/mock/json";
	
	/**
	  * 测试报表数据
	 */
	public String getMacd() {
		return readJson("classpath:org/jeecg/modules/demo/mock/json/macd.json");
	}

//	@GetMapping(value = "/stock")
	@PostMapping(value = "/stock")
	public String getStock(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getHeaderNames();
		System.out.println("========================Header Start========================");
		while(paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			System.out.println(name + "---" + request.getHeader(name));
		}
		System.out.println("========================Header End========================");

		paramNames = request.getAttributeNames();
		System.out.println("========================Attr Start========================");
		while(paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			System.out.println(name + "---" + request.getAttribute(name));
		}
		System.out.println("========================Attr End========================");

		paramNames = request.getParameterNames();
		System.out.println("========================Param Start========================");
		while(paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			System.out.println(name + "---" + request.getParameter(name));
		}
		System.out.println("========================Param End========================");
		return readJson("classpath:org/jeecg/modules/demo/mock/json/stock.json");
	}

	/**
	 * 读取json格式文件
	 * @param jsonSrc
	 * @return
	 */
	private String readJson(String jsonSrc) {
		String json = "";
		try {
			//File jsonFile = ResourceUtils.getFile(jsonSrc);
			//json = FileUtils.re.readFileToString(jsonFile);
			//换个写法，解决springboot读取jar包中文件的问题
			InputStream stream = getClass().getClassLoader().getResourceAsStream(jsonSrc.replace("classpath:", ""));
			json = IOUtils.toString(stream);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
