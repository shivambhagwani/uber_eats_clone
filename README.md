<h1> Uber Eats Backend Clone </h1>

![](ER_Diagram_UberEats.jpg) <br /> Entity Relationship Diagram

<h3>Customer Entity</h3>
<table>
<tbody>
<tr>
<td>
<p><strong>Column</strong></p>
</td>
<td>
<p><strong>Explanation</strong></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">id (Primary Key)</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer ID. Unique. Not Null.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">cart (Foreign Key)</span></p>
</td>
<td>
<p><span style="font-weight: 400;">ID of the cart which belongs to this customer. The food items added by customers are stored in this cart. When the order is submitted by the customer, the cart's value is calculated, and is put into the customer's order history. Cart is cleared for a fresh order to be built.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">address (Foreign Key)</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer&rsquo;s address id stored in address table.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">full_name&nbsp;</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer&rsquo;s full name.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">email</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer&rsquo;s email. Also used to authenticate customers.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">password</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Authentication password which the customer set&rsquo;s on registration.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">contact_number</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer&rsquo;s contact details.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">fav_cuisine</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Customer&rsquo;s favorite cuisine. Restaurant&rsquo;s can be displayed to the customer based on the cuisines for the betterment of user experience.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">fav_restaurants</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Based on past orders, customers can add restaurants to their favorite list.</span></p>
</td>
</tr>
</tbody>
</table>

<h3>Cart Entity</h3>
<table>
<tbody>
<tr>
<td>
<p><strong>Column</strong></p>
</td>
<td>
<p><strong>Explanation</strong></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">id (Primary Key)</span></p>
</td>
<td>
<p><span style="font-weight: 400;">Cart ID. Not Null. Unique.</span></p>
</td>
</tr>
<tr>
<td>
<p><span style="font-weight: 400;">food_ids_in_cart</span></p>
</td>
<td>
<p><span style="font-weight: 400;">When a customer adds food, the ids are stored in the respective cart.</span></p>
</td>
</tr>
</tbody>
</table>

<h3>Address Entity</h3>
<table>
	<tbody>
		<tr>
			<td>
				<p>
					<strong>
						Column
					</strong>
				</p>
			</td>
			<td>
				<p>
					<strong>
						Explanation
					</strong>
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					id (Primary Key)
				</p>
			</td>
			<td>
				<p>
					Address ID. Unique. Not Null.
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					street
				</p>
			</td>
			<td>
				<p>
					Street address
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					city
				</p>
			</td>
			<td>
				<p>
					City field of address
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					state
				</p>
			</td>
			<td>
				<p>
					State field of address
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					pincode
				</p>
			</td>
			<td>
				<p>
					Pincode field of address. City and Pincode fields can be used to show customer restaurants in the area to order from.
				</p>
			</td>
		</tr>
	</tbody>
</table>

<h3>Restaurant Entity</h3>

<table>
	<tbody>
		<tr>
			<td>
				<p><strong>Column</strong></p>
			</td>
			<td>
				<p><strong>Explanation</strong></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">id (Primary Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Restaurant ID. Unique. Not Null.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">restaurant_name</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Name of the restaurant. Restaurant can be searched based on the name.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">cuisine</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Cuisine that restaurant serves.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">pincode</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Pincode where the restaurant is located. Restaurants can be searched based on the pincode.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">rating</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Customer rating of the restaurant. Can be averaged out and updated with each customer input.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">operation_status</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">If the restaurant is open to delivery at the moment or closed.</span></p>
			</td>
		</tr>
	</tbody>
</table>

<h3>Food Entity </h3>
<table>
	<tbody>
		<tr>
			<td>
				<p><strong>Column</strong></p>
			</td>
			<td>
				<p><strong>Explanation</strong></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">id (Primary Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Food ID. Unique. Not Null.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">restaurant_id (Foreign Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Food items belong to a restaurant. This field stores the restaurant ID to which this food item belongs.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">item_name</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Name of the food item.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">item_cost</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Cost of the food item.</span></p>
			</td>
		</tr>
	</tbody>
</table>

<h3>Restaurant Employee </h3>
<table>
	<tbody>
		<tr>
			<td>
				<p><strong>Column</strong></p>
			</td>
			<td>
				<p><strong>Explanation</strong></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">id (Primary Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">ID of the employee. Unique. Not Null.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">restauranr_id(Foreign Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">ID of the restaurant with which the employee works.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">email</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Email of the employee.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">phone</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Phone number of the employee.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">name</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Name of the employee</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">age</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Age of the employee</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">job_role</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Job role (Authorization) of the employee. (ADMIN/CHEF/OWNER)</span></p>
			</td>
		</tr>
	</tbody>
</table>
<h3>Order Entity</h3>
<table>
	<tbody>
		<tr>
			<td>
				<p><strong>Column</strong></p>
			</td>
			<td>
				<p><strong>Explanation</strong></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">id (Primary Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Order ID. Unique. Not Null.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">customer_id (Foreign Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">ID of the customer who has submitted the order request. This helps to fetch the order history of the customer.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">restaurant_id (Foreign Key)</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">ID of the restaurant to which the order was submitted.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">food_ids_in_order</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">IDs of the food items which are part of order.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">item_count</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Total number of food items in this order.</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">total_price</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Total cost of this order. (Tax calculated using the tax table.)</span></p>
			</td>
		</tr>
		<tr>
			<td>
				<p><span style="font-weight: 400;">order_status</span></p>
			</td>
			<td>
				<p><span style="font-weight: 400;">Status of the order. Can only be changed by restaurant admin. Authorization involved.</span></p>
			</td>
		</tr>
	</tbody>
</table>


<h2>Context Diagram</h2>

![](Context.jpg)

<h2>Container Diagram</h2>

![](ContainerDiagram.jpg) <br /> Container Diagram