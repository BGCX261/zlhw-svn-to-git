package com.ZLHW.common.JBPM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FlowStateImage {
	private Set<ImgLocate> imageLocates;
	private String url;
	public Set<ImgLocate> getImageLocates() {
		return imageLocates;
	}
	public void setImageLocates(Set<ImgLocate> imageLocates) {
		this.imageLocates = imageLocates;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map toHashMap(){
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("url", url);
		int a=0;
		for(ImgLocate i:imageLocates)
			list.add(i.toHashMap());
		map.put("imageLocates", list);
		return map;
	}
}
