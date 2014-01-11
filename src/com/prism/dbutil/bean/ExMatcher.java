package com.prism.dbutil.bean;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class ExMatcher {

	public String regex(String param,String regex){
		String val = null;
		try{
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
			Matcher m = p.matcher(param);
			while(m.find()){
				val = m.group(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	public String regex(String param,String regex,int index){
		String val = null;
		try{
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
			Matcher m = p.matcher(param);
			while(m.find()){
				val = m.group(index);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	public List<String> regexs(String param,String regex){
		List<String> val = new ArrayList<String>();
		try{
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
			Matcher m = p.matcher(param);
			
			while(m.find()){
				val.add(m.group(1));
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	public List<String> regexs(String param,String regex,int index){
		List<String> val = new ArrayList<String>();
		try{
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
			Matcher m = p.matcher(param);
			
			while(m.find()){
				val.add(m.group(index));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	
	public List<String> perlRegex(String param,String regex){
		List<String> val = new ArrayList<String>();
		try{
			PatternCompiler pc = new Perl5Compiler();
			Pattern p = pc.compile(regex,Perl5Compiler.CASE_INSENSITIVE_MASK);
			PatternMatcher matcher = new Perl5Matcher();
			if(matcher.contains(param,p)){
				MatchResult result = matcher.getMatch();
				for(int i=1;i<result.groups();i++){
					String t = result.group(i);	
					val.add(t);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}
	public String perl(String param,String regex){
		try{
			Perl5Util perl = new Perl5Util();
			param = perl.substitute(regex, param);
			MatchResult mr = perl.getMatch(); 
			while(mr != null){ 
				param = perl.substitute(regex, param); 
				mr = perl.getMatch(); 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return param;
	}

//	public static void main(String[] args) throws Exception{
//		ExMatcher matcher = new ExMatcher();
//		List list = null;
//		//matcher.regexs("123456789","2(\\d.*)5");
//		/*
//		String[] strs = matcher.regexs("123456789","2(\\d.*)56(\\d.*)9");
//		for(int i=0;i<strs.length;i++){
//			System.out.println(strs[i]);
//		}
//		*/
//		//matcher.regex("123${a}5 6${b}9","\\$\\{([\\s(\\w,.,\\(,\\))]+)\\}");
//		//List list = matcher.regexs("123${a}56${b}9","\\$\\{([\\s(\\w,.,\\(,\\))]+)\\}");
//		//List list = matcher.regexs("123456789","12(.*)56(.*)9");
//		String tmp = "123${a}56${b}9".replaceAll("\\$\\{([\\s(\\w,.,\\(,\\))]+)\\}","(.*)");
//		//list = matcher.perlRegex("123456789",tmp);
//		list = matcher.regexs("123${a}56${b}9","\\$\\{([\\s(\\w,.,\\(,\\))]+)\\}");
//		//System.out.println("=="+tmp);
//		for(int i=0;i<list.size();i++){
//		//	System.out.println(list.get(i));
//		}
//		
//		System.out.println("======"+matcher.regex("abcd${e}f${g}${h}","[a-d]\\$\\{*\\}[a-z]\\$\\{*\\}\\$\\{*\\}"));
//		//matcher.regex("<font face='aa' size='1' color='red'>","'(\\w*)'");
//	}
}
