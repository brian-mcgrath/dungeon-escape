//package com.ericsson.tests;
//
//import static org.junit.Assert.*;
//
//import javax.vecmath.Vector2d;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.game.enities.Ray;
//
//public class RayTest {
//
//private Vector2d p1;
//private Vector2d p2;
//private Ray ray;
//
//@Before
//public void setUp() throws Exception {
//	p1 = new Vector2d(15, 20);
//	p2 = new Vector2d(35, 5);
//	ray = new Ray(p1, p2, true);
//}
//
//@After
//public void tearDown() throws Exception {
//	p1 = null;
//	p2 = null;
//	ray = null;
//}
//
//@Test
//public void distanceTest() {
//	p1 = new Vector2d(15, 20);
//	p2 = new Vector2d(35, 5);
//	ray = new Ray(p1, p2);
//	double result = ray.getLength();
//	assertEquals(25, result, 0);
//
//}
//
//}
//
