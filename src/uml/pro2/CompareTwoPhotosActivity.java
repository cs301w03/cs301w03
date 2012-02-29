package uml.pro2;


/**
 * @uml.dependency   supplier="uml.pro2.ListAdapter"
 */
public class CompareTwoPhotosActivity implements FView {

	/**
	 * @uml.property  name="order"
	 */
	private String order;

	/**
	 * Getter of the property <tt>order</tt>
	 * @return  Returns the order.
	 * @uml.property  name="order"
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * Setter of the property <tt>order</tt>
	 * @param order  The order to set.
	 * @uml.property  name="order"
	 */
	public void setOrder(String order) {
		this.order = order;
	}

		
		/**
		 */
		public void Orderby(){
		}

		@Override
		public void update(M model) {
		}

}
