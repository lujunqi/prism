package test.advisor;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class ProxyBean extends ProxyFactoryBean
{
	private static final long serialVersionUID = -4723335666022379237L;
	private NameMatchMethodPointcutAdvisor nm = new NameMatchMethodPointcutAdvisor();
	
	public String getMappedName()
	{
		return mappedName;
	}
	public void setMappedName(String mappedName)
	{
		nm.setMappedName(mappedName);
		this.mappedName = mappedName;
	}
	private String mappedName = "";
	
}
