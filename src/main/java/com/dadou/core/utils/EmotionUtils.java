package com.dadou.core.utils;

import java.util.HashMap;
import java.util.Map;

public class EmotionUtils {

	public static Map<String, String> emotions = new HashMap<String, String>();
	static{
		emotions.put("(谄笑)", "http://a.xnimg.cn/imgpro/emotions/tie/2.gif?ver=1");
		emotions.put("(吃饭)", "http://a.xnimg.cn/imgpro/emotions/tie/3.gif?ver=1");
		emotions.put("(调皮)", "http://a.xnimg.cn/imgpro/emotions/tie/4.gif?ver=1");
		emotions.put("(尴尬)", "http://a.xnimg.cn/imgpro/emotions/tie/5.gif?ver=1");
		emotions.put("(汗)", "http://a.xnimg.cn/imgpro/emotions/tie/6.gif?ver=1");
		emotions.put("(惊恐)", "http://a.xnimg.cn/imgpro/emotions/tie/7.gif?ver=1");
		emotions.put("(囧)", "http://a.xnimg.cn/imgpro/emotions/tie/8.gif?ver=1");
		emotions.put("(可爱)", "http://a.xnimg.cn/imgpro/emotions/tie/9.gif?ver:1");
		emotions.put("(酷)", "http://a.xnimg.cn/imgpro/emotions/tie/10.gif?ver=1");
		emotions.put("(流口水)", "http://a.xnimg.cn/imgpro/emotions/tie/11.gif?ver=1");
		emotions.put("(生病)", "http://a.xnimg.cn/imgpro/emotions/tie/14.gif?ver=1");
		emotions.put("(叹气)", "http://a.xnimg.cn/imgpro/emotions/tie/15.gif");
		emotions.put("(淘气)", "http://a.xnimg.cn/imgpro/emotions/tie/16.gif");
		emotions.put("(舔)", "http://a.xnimg.cn/imgpro/emotions/tie/17.gif");
		emotions.put("(偷笑)", "http://a.xnimg.cn/imgpro/emotions/tie/18.gif");
		emotions.put("(吻)", "http://a.xnimg.cn/imgpro/emotions/tie/20.gif");
		emotions.put("(晕)", "http://a.xnimg.cn/imgpro/emotions/tie/21.gif?ver=1");
		emotions.put("(住嘴)", "http://a.xnimg.cn/imgpro/emotions/tie/23.gif");
		emotions.put("(大笑)", "http://a.xnimg.cn/imgpro/icons/statusface/16.gif?ver=1");
		emotions.put("(害羞)", "http://a.xnimg.cn/imgpro/icons/statusface/shy.gif");
		emotions.put("(口罩)", "http://a.xnimg.cn/imgpro/icons/statusface/17.gif");
		emotions.put("(哭)", "http://a.xnimg.cn/imgpro/icons/statusface/cry.gif");
		emotions.put("(困)", "http://a.xnimg.cn/imgpro/icons/statusface/sleepy.gif");
		emotions.put("(难过)", "http://a.xnimg.cn/imgpro/icons/statusface/sad.gif");
		emotions.put("(生气)", "http://a.xnimg.cn/imgpro/icons/statusface/5.gif?ver=1");
		emotions.put("(书呆子)", "http://a.xnimg.cn/imgpro/icons/statusface/13.gif?ver=1");
		emotions.put("(微笑)", "http://a.xnimg.cn/imgpro/icons/statusface/1.gif?ver=1");
		emotions.put("(不)", "http://a.xnimg.cn/imgpro/emotions/tie/1.gif");
		emotions.put("(惊讶)", "http://a.xnimg.cn/imgpro/icons/statusface/surprise.gif");
		emotions.put("(kb)", "http://a.xnimg.cn/imgpro/icons/statusface/kbz2.gif");
		emotions.put("(sx)", "http://a.xnimg.cn/imgpro/icons/statusface/shaoxiang.gif");
		emotions.put("(ju)", "http://a.xnimg.cn/imgpro/icons/statusface/jj.gif");
		emotions.put("(gl)", "http://a.xnimg.cn/imgpro/icons/statusface/geili.gif");
		emotions.put("(yl)", "http://a.xnimg.cn/imgpro/icons/statusface/yali.gif");
		emotions.put("(hold1)", "http://a.xnimg.cn/imgpro/icons/statusface/holdzhu.gif");
		emotions.put("(cold)", "http://a.xnimg.cn/imgpro/icons/statusface/cold.gif");
		emotions.put("(nuomi)", "http://a.xnimg.cn/imgpro/icons/statusface/nuomi2.gif");
		emotions.put("(feng)", "http://a.xnimg.cn/imgpro/icons/statusface/fan.gif");
		emotions.put("(hot)", "http://a.xnimg.cn/imgpro/icons/statusface/hot.gif");
		emotions.put("(rs)", "http://a.xnimg.cn/imgpro/icons/statusface/rose0314.gif");
		emotions.put("(sbq)", "http://a.xnimg.cn/imgpro/icons/statusface/shangbuqi.gif");
		emotions.put("(th)", "http://a.xnimg.cn/imgpro/icons/statusface/exclamation.gif");
		emotions.put("(mb)", "http://a.xnimg.cn/imgpro/icons/statusface/guibai.gif");
		emotions.put("(tucao)", "http://a.xnimg.cn/imgpro/icons/statusface/tuc.gif");
		emotions.put("(禅师)", "http://a.xnimg.cn/imgpro/icons/statusface/chsh.gif");
		emotions.put("(冰棒)", "http://a.xnimg.cn/imgpro/icons/statusface/dbb.gif");
		emotions.put("(see)", "http://a.xnimg.cn/imgpro/icons/statusface/seesea.gif");
		emotions.put("(zy)", "http://a.xnimg.cn/imgpro/icons/statusface/zy.gif");
		emotions.put("(by)", "http://a.xnimg.cn/imgpro/icons/statusface/rain.gif");
		emotions.put("(bs)", "http://a.xnimg.cn/imgpro/icons/statusface/bluesky.gif");
		emotions.put("(good)", "http://a.xnimg.cn/img/ems/good.gif");
		emotions.put("(twg)", "http://a.xnimg.cn/imgpro/icons/statusface/style.gif");
		emotions.put("(guoqing)", "http://a.xnimg.cn/imgpro/icons/statusface/guoqing.gif");
		emotions.put("(ly)", "http://a.xnimg.cn/imgpro/icons/statusface/autumn-leaves.gif");
	}
	
	
	/**
     * 替换图片
     * @param temp
     * @return
     */
    public static String getHtml(String temp){
    	if(temp == null || temp.length() == 0 || temp.indexOf("(") == -1) return temp;
		Integer len = temp.length();
		// 替身
		String resultStr = temp;
		Integer begin = 0, end = 1;
		for (Integer i = len - 1; i >= 0; i--) {
			if(temp.charAt(i) == '('){
				begin = i;
			}
			if(temp.charAt(i) == ')'){
				end = i;
			}		
			if(end - begin > 1 && end - begin < 9 && temp.charAt(end) == ')'){
				String oldstr = temp.substring(begin + 1, end)
						,newstr = emotions.get("(" + oldstr + ")");
				if(newstr != null){
					// 会出现重复替换
					// newstr = "<img title=\"" + oldstr + "\" src=\"" + newstr + "\" alt=\"" + oldstr + "\" emotion=\"(" + oldstr + ")\" />";
					
					newstr = "<img title=\"" + oldstr + "\" src=\"" + newstr + "\" alt=\"" + oldstr + "\" />";
					resultStr = resultStr.replace("(" + oldstr + ")", newstr);
				}
				/* 已经输出过一次，将end的值设置为begin */
				end = begin;
			}
		}
		return resultStr;
	}
    
    /**
     * 回车换行替换为<br />
     * @param q
     * @return
     */
    public static String getEnter1(String q){
    	q = q.replaceAll(System.getProperty("line.separator"), "<br>");
    	return q;
    }
    
    
    public static void main(String[] args) {
		String temp = "(汗)(tucao)(tucao)(tucao)(tucao)(tucao)(tucao)(tucao)(汗)";
		Integer len = temp.length();
		
		String resultStr = temp;
		
		Integer begin = len - 1, end = len - 1;
		for (Integer i = len - 1; i >= 0; i--) {
			if(temp.charAt(i) == '('){
				begin = i;
			}
			if(temp.charAt(i) == ')'){
				end = i;
			}		
			if(end - begin >= 1 && end - begin < 9 && temp.charAt(end) == ')'){
				String oldstr = temp.substring(begin + 1, end)
						,newstr = emotions.get("(" + oldstr + ")");
				if(newstr != null){
					newstr = "<img src=\"" + newstr + "\" alt=\"" + oldstr + "\" />";
					// newstr = "<img src=\"" + newstr + "\" alt=\"" + oldstr + "\" emotion=\"(" + oldstr + ")\" />";
					resultStr = resultStr.replace("(" + oldstr + ")", newstr);
				}
				/* 已经输出过一次，将end的值设置为begin */
				end = begin;
			}
		}
	}
    
}
