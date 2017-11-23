package com.even.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserMonitorTicket<M extends BaseUserMonitorTicket<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setUserId(java.lang.Integer userId) {
		set("user_id", userId);
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public void setState(java.lang.Boolean state) {
		set("state", state);
	}

	public java.lang.Boolean getState() {
		return get("state");
	}

	public void setTicketCount(java.lang.Integer ticketCount) {
		set("ticket_count", ticketCount);
	}

	public java.lang.Integer getTicketCount() {
		return getInt("ticket_count");
	}

	public void setDptStationName(java.lang.String dptStationName) {
		set("dpt_station_name", dptStationName);
	}

	public java.lang.String getDptStationName() {
		return getStr("dpt_station_name");
	}

	public void setArrStationName(java.lang.String arrStationName) {
		set("arr_station_name", arrStationName);
	}

	public java.lang.String getArrStationName() {
		return getStr("arr_station_name");
	}

	public void setTrainNum(java.lang.String trainNum) {
		set("train_num", trainNum);
	}

	public java.lang.String getTrainNum() {
		return getStr("train_num");
	}

	public void setStartDate(java.lang.Integer startDate) {
		set("start_date", startDate);
	}

	public java.lang.Integer getStartDate() {
		return getInt("start_date");
	}

	public void setSeats(java.lang.String seats) {
		set("seats", seats);
	}

	public java.lang.String getSeats() {
		return getStr("seats");
	}

	public void setPrice(java.lang.Float price) {
		set("price", price);
	}

	public java.lang.Float getPrice() {
		return getFloat("price");
	}

}