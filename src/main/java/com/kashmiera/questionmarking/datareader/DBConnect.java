package com.kashmiera.questionmarking.datareader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnect {

	public static Connection connect(){

		Connection con = null;
		Properties props = new Properties();
		props.setProperty("user", "root");
		props.setProperty("password", "123456");

		try {

			con = (Connection) DriverManager.getConnection("jdbc:mysql://142.93.244.96:3306/kas", props);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Success");

		return con;

	}
}

