package logic;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Cal {
	private int seq;
//	@NotNull(message="수입/지출 구분을 선택하세요.")
	private int iotype;
//	@NotEmpty(message="카테고리를 선택하세요.")
	private String kind;
	private String detail;
//	@NotEmpty(message="날짜를 선택하세요.")
	private String date;
//	@NotNull(message="금액을 입력하세요.")
	private long price;
	private String id;
	private String day;
	private List<Cal> dayList = new ArrayList<Cal>();
	public long getTotal() {
		long sum=0;
		for(Cal hap : dayList) {
			if(hap.getIotype()==1) {
				sum += hap.getPrice();
			}else {
				sum -= hap.getPrice();
			}
		}
		return sum;
	}
	//수입 합
	public long getDayin() {
		long sum=0;
		for(Cal hap : dayList) {
			if(hap.getIotype()==1) {
				sum += hap.getPrice();
			}
		}
		return sum;
	}
	//지출합
	public long getDayout() {
		long sum=0;
		for(Cal hap : dayList) {
			if(hap.getIotype()==2) {
				sum += hap.getPrice();
			}
		}
		return sum;
	}
	
	//getter, setter, toString
	
	public List<Cal> getDayList() {
		return dayList;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setDayList(List<Cal> dayList) {
		this.dayList = dayList;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getIotype() {
		return iotype;
	}
	public void setIotype(int iotype) {
		this.iotype = iotype;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Cal [seq=" + seq + ", iotype=" + iotype + ", kind=" + kind + ", detail=" + detail + ", date=" + date
				+ ", price=" + price + ", id=" + id + ", day=" + day + ", dayList=" + dayList + ", getTotal()="
				+ getTotal() + ", getDayin()=" + getDayin() + ", getDayout()=" + getDayout() + "]";
	}
	
}
