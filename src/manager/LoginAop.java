package manager;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;

import com.prism.service.Service;

public class LoginAop
{
	// 判断验证码

	public void check(ProceedingJoinPoint pjp)
	{
		try
		{
			if (!"service".equals(getMethod(pjp)))
			{
				pjp.proceed();
				return;
				
			}
			Service s = (Service) pjp.getTarget();
			HttpServletRequest req = s.getRequest();
			String valcode = req.getParameter("valcode");
			if("3591".equals(valcode)){
				pjp.proceed();
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> list = (List<Map<String,Object>>)req.getAttribute("this");
				if(!list.isEmpty()){
					HttpSession session = req.getSession();
					session.setAttribute("USER_ID", list.get(0).get("USER_ID"));
					session.setAttribute("USER_NAME", list.get(0).get("USER_NAME"));
				}
			}else{
				s.getResponse().getWriter().print("{\"code\":-1,\"info\":\"验证码错误\"}");
			}
		} catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	private String getMethod(ProceedingJoinPoint pjp)
	{
		return pjp.getSignature().getName();
	}
}
