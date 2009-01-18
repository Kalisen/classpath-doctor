package org.kalisen.test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestEqualHelper {

	private Object o1 = null;
	private Object o2 = null;
	private Object o3 = null;

	public TestEqualHelper(Object o1, Object o2, Object o3) {
		if (o1 == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (o2 == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		if (o3 == null) {
			throw new IllegalArgumentException("null is not a valid argument");
		}
		this.o1 = o1;
		this.o2 = o2;
		this.o3 = o3;
	}

	@Test
	public void equalIsReflexive() {
		Assert.assertTrue(this.o1.equals(this.o1));
	}

	@Test
	public void equalIsSymetric() {
		Assert.assertTrue(this.o1.equals(this.o2));
		Assert.assertTrue(this.o2.equals(this.o1));
	}

	@Test
	public void equalIsTransitive() {
		Assert.assertTrue(this.o1.equals(this.o2));
		Assert.assertTrue(this.o2.equals(this.o3));
		Assert.assertTrue(this.o1.equals(this.o3));
	}

	@Test
	public void equalToNullShouldReturnFalse() {
		Assert.assertFalse(this.o1.equals(null));
	}

	@Test
	public void equalShouldBeConsistentFromCallToCall() {
		Assert.assertTrue(this.o1.equals(this.o2));
		Assert.assertTrue(this.o1.equals(this.o2));
		Assert.assertTrue(this.o1.equals(this.o2));
	}

	@Test
	public void hashcodeShouldBeConsistentFromCallToCall() {
		int hashCode = this.o1.hashCode();
		Assert.assertEquals(hashCode, this.o1.hashCode());
		Assert.assertEquals(hashCode, this.o1.hashCode());
	}

	@Test
	public void hashcodesMustBeEqualIfObjectsAreEqual() {
		Assert.assertTrue(this.o1.equals(this.o2));
		Assert.assertEquals(this.o1.hashCode(), this.o2.hashCode());
	}

}
