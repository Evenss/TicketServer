package com.even.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseIp<M extends BaseIp<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setIp(java.lang.String ip) {
		set("ip", ip);
	}

	public java.lang.String getIp() {
		return getStr("ip");
	}

	public void setUsable(java.lang.Boolean usable) {
		set("usable", usable);
	}

	public java.lang.Boolean getUsable() {
		return get("usable");
	}

}