package org.study.spring;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.quincy.rock.core.util.IOUtil;
import org.quincy.rock.core.util.JsonUtil;
import org.quincy.rock.core.util.StringUtil;
import org.quincy.rock.core.vo.PageSet;
import org.quincy.rock.core.vo.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.github.pagehelper.Page;


/**
 * <b>AppUtils。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author Nymphet
 * @since 1.0
 */
public abstract class AppUtils {

	public static final String CURRENT_LOGIN_USER_KEY = "_app_loginUser";

	public static final String PUBLIC_LAST_CAPTCHA_KEY = "_captcha_code";

	/**
	 * 启用验证码登录。
	 */
	public static boolean useCaptcha;

	/**
	 * <b>获得当前认证用户信息。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 当前认证用户信息
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * <b>返回是否已经登录了。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @return 是否已经登录了
	 */
	public static boolean isLogin() {
		return getLoginUser() != null;
	}

	/**
	 * <b>获得当前登录用户。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。 
	 * @return 当前登录用户
	 */
	public static User getLoginUser() {
		Object user = (User) RequestContextHolder.currentRequestAttributes().getAttribute(CURRENT_LOGIN_USER_KEY,
				RequestAttributes.SCOPE_SESSION);
		if (user == null) {
			Authentication auth = getAuthentication();
			if (auth != null)
				user = auth.getPrincipal();
		}
		return user instanceof User ? (User) user : null;
	}

	/**
	 * <b>将Mybatis的Page转换成PageSet。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param <E>
	 * @param page Page
	 * @return PageSet
	 */
	public static <E> PageSet<E> toPageSet(Page<E> page) {
		return PageSet.of(page.getResult(), page.getPageNum(), page.getPageSize(), page.getTotal());
	}

	public static void writeJson(ServletResponse response, Result<?> result) throws IOException {
		HttpServletResponse resp = ((HttpServletResponse) response);
		resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
		resp.setCharacterEncoding(StringUtil.UTF_8.name());
		PrintWriter out = resp.getWriter();
		try {
			out.write(JsonUtil.toJson(result));
		} finally {
			IOUtil.flushAndClose(out);
		}
	}
}
