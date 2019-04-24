package com.ideacrest.hireup.helper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class EurekaInstaceBeanHelperTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetEurekaInstanceBean() {
		EurekaInstaceBeanHelper.getEurekaInstanceBean();
		Assert.assertTrue(true);
	}
}
