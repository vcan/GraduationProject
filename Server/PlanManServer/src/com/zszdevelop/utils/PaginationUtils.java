package com.zszdevelop.utils;

public class PaginationUtils {

	public static String pageLimits(int currentPage){

		String limits =String.format("%d,%d", (currentPage -1) *10,currentPage*10);
		
		return limits;
	}
}
