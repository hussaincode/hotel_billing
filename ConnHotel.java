package com.capgemini.hotel_billing;


	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.Statement;
	import java.util.Scanner;

	public class ConnHotel {
		@SuppressWarnings({ "unused", "null" })
		public static void main(String[] args) throws Exception {
			ResultSet rs = null;

			// Loading the Driver
			Class.forName("com.mysql.jdbc.Driver");

			// Getting the connection
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db?user=root&password=root");

			// processing the statement
			Statement stmt = conn.createStatement();

			Scanner sc = new Scanner(System.in);

			System.out.println("Enter 1 to show all item");
			System.out.println("Enter 2 to take order  from customer ");
			System.out.println("Enter 3 to Operate on order");
			System.out.println("Enter 4 for total bill ");

			int rt = sc.nextInt();
			if (rt == 1) {
				rs = stmt.executeQuery("Select * from hotel_bill");
				while (rs.next()) {
					System.out.println("Item_code is: " + rs.getByte(1));
					System.out.println("Food_name is: " + rs.getString(2));
					System.out.println("Price is : " + rs.getDouble(3));

				}
			}

			else if (rt == 2) {
				int i=1;
				do {
				String query = "Insert into order_bill values(?)";
				PreparedStatement pstmt = conn.prepareStatement(query);
				System.out.println("Enter item_code: ");

				pstmt.setInt(1, Integer.parseInt(sc.next()));
				System.out.println("Enter price: ");

				pstmt.setDouble(2, Double.parseDouble(sc.next()));

				int res = pstmt.executeUpdate();
				if (res > 0) {
					System.out.println("Order Recieved");
				}
				System.out.println("Teko Dusra parcel bhi chiye kya ");
				String st = sc.next();
				if(st.equals("Y")) {
					i++;
				}else {
				  i=0;	
				}
				} while(i!=0); 
			}
			
			
			else if (rt == 3) {
				System.out.println("select A for add the item ");
				System.out.println("select B for modify the item");
				System.out.println("select C for delete item ");
				String s = sc.next();

				if (s.equals("A")) {
					String query = "Insert into order_bill values(?,?)";
					PreparedStatement pstmt = conn.prepareStatement(query);
					System.out.println("Enter the item_code");

					pstmt.setInt(1, Integer.parseInt(sc.next()));
					System.out.println("Enter the price of product ");
					pstmt.setDouble(2, Double.parseDouble(sc.next()));

					int res = pstmt.executeUpdate();
					if (res > 0) {
						System.out.println("Order Added ");
					}

				}

				else if (s.equals("B")) {
					String query = "Update into order_bill into" + " set item_code=? where price=? ";
					PreparedStatement pstmt = conn.prepareStatement(query);
					System.out.println("Enter the item_code");
					pstmt.setInt(1, Integer.parseInt(sc.next()));
					System.out.println("Enter the price");
					pstmt.setDouble(2, Double.parseDouble(sc.next()));

					int res = pstmt.executeUpdate();
					if (res > 0) {
						System.out.println("Order Modified ");
					}

				}

				else if (s.equals("C")) {
					String query = "delete from order_bill where item_code=? ";
					PreparedStatement pstmt = conn.prepareStatement(query);
					System.out.println("Enter the item_code");
					pstmt.setInt(1, Integer.parseInt(sc.next()));

					int res = pstmt.executeUpdate();
					if (res > 0) {
						System.out.println("Item deleted  ");
					}

				}

			}
			
			else if(rt==4) {
				Statement st = conn.createStatement();
				rs =st.executeQuery("Select item_code, price ,SUM(price) from hotel_bill");
				while(rs.next()) {
					System.out.println("Total bill is: "+rs.getDouble(3));
				}
				
			}

			conn.close();
			stmt.close();
			sc.close();

		}
	}


