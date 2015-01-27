/**
 * 
 */
package com.mcm.mobileservices.pizzaorder.entities;

/**
 * @author Mobile Services Team Jan 25, 2015
 * 
 */
public class PizzaDetails {

	private String pizzaName;

	private String pizzaDescription;

	private String pizzaContent;

	private Boolean active;

	private String message;

	/**
	 * @return the pizzaName
	 */
	public String getPizzaName() {
		return pizzaName;
	}

	/**
	 * @param pizzaName
	 *            the pizzaName to set
	 */
	public void setPizzaName(String pizzaName) {
		this.pizzaName = pizzaName;
	}

	/**
	 * @return the pizzaDescription
	 */
	public String getPizzaDescription() {
		return pizzaDescription;
	}

	/**
	 * @param pizzaDescription
	 *            the pizzaDescription to set
	 */
	public void setPizzaDescription(String pizzaDescription) {
		this.pizzaDescription = pizzaDescription;
	}

	/**
	 * @return the pizzaContent
	 */
	public String getPizzaContent() {
		return pizzaContent;
	}

	/**
	 * @param pizzaContent
	 *            the pizzaContent to set
	 */
	public void setPizzaContent(String pizzaContent) {
		this.pizzaContent = pizzaContent;
	}

	/**
	 * @return the active
	 */
	public Boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
