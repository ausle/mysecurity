/**
 * 
 */
package com.asule.security.validate.processor;

import java.util.Map;

import com.asule.security.entity.ValidateCode;
import com.asule.security.validate.ValidateCodeType;
import com.asule.security.validate.exception.ValidateCodeException;
import com.asule.security.validate.generator.ValidateCodeGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 */
	private C generate(ServletWebRequest request) {
		//选择使用什么生成器，取决于当前的processor。
		String type = getValidateCodeType().toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		//根据生成器name，获取生成器来生成验证码。
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存校验码到session中
	 */
	private void save(ServletWebRequest request, C validateCode) {
		sessionStrategy.setAttribute(request, getSessionKey(), validateCode);
	}

	/**
	 * 获取session_Key
	 * session_key=前缀+codeType
	 */
	private String getSessionKey() {
		return SESSION_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


	/**
	 *	根据具体实现的processor的类名，获取对应的validatecodetype
	 */
	private ValidateCodeType getValidateCodeType() {
		String type = StringUtils.substringBefore(getClass().getSimpleName(),"ValidateCodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType processorType = getValidateCodeType();
		String sessionKey = getSessionKey();

		C codeInSession = (C) sessionStrategy.getAttribute(request, sessionKey);

		String codeInRequest="";
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getFormValidateParam());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpired()) {
			sessionStrategy.removeAttribute(request, sessionKey);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}

		sessionStrategy.removeAttribute(request, sessionKey);
	}

}
