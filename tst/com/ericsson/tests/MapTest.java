//package com.ericsson.tests;
//
//import static org.junit.Assert.*;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.game.enities.Map;
//
//public class MapTest {
//
//	private Map map;
//	private int[][] mazeArray = new int[12][16];
//	
//	@Before
//	public void setUp() throws Exception {
//		map = new Map(mazeArray, 32);
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		map = null;
//	}
//
//	@Test
//	public void getColumnTest() {
//		int numberOfColumns = map.getNumberOfColumns();
//		assertEquals(16, numberOfColumns);
//	}
//	
//	@Test
//	public void getRowsTest() {
//		int numberOfRows = map.getNumberOfRows();
//		assertEquals(12, numberOfRows);
//	}
//
//}
