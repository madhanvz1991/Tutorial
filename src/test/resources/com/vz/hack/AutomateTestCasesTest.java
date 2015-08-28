package com.vz.hack;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class AutomateTestCasesTest {
	
		
	public AutomateTestCasesTest() {
	    }
	    
	    @BeforeClass
	    public static void setUpClass() {
	    }
	    
	    @AfterClass
	    public static void tearDownClass() {
	    }
	    
	    @Before
	    public void setUp() {
	    }
	    
	    @After
	    public void tearDown() {
	    }

	    
	    @Test
	    public void successTestOpr() {
	        System.out.println("testOpr");
	        int a = 5;
	        int b = 5;
	        AutomateTestCases instance = new AutomateTestCases();
	        int expResult = 10;
	        int result = instance.testOpr(a, b);
	        assertEquals(expResult, result);
	    }
	    
	    @Test
	    public void failureTestOpr() {
	        System.out.println("testOpr");
	        int a = 10;
	        int b = 5;
	        AutomateTestCases instance = new AutomateTestCases();
	        int expResult = 15;
	        int result = instance.testOpr(a, b);
	        assertEquals(expResult, result);
	    }
	    
	}
