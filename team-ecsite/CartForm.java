package jp.co.internous.kabuki.model.form;

import java.io.Serializable;


public class CartForm implements Serializable{
	private static final long serialVersionUID = 1L;
	private long userId;
	private long productId;
	private long productCount;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getProductCount() {
		return productCount;
	}
	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}
}