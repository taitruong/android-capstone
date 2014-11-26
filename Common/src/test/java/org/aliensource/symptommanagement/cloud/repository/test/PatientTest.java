package org.aliensource.symptommanagement.cloud.repository.test;

import static org.junit.Assert.*;

import org.aliensource.symptommanagement.cloud.repository.Patient;
import org.junit.Test;

public class PatientTest {

	private final Patient patient1 = new Patient();
	private final Patient patient2 = new Patient();
	
	@Test
	public void testEquals() {
		assertEquals(patient1,patient2);
	}
	
	@Test
	public void testHashcode() {
		assertEquals(patient1.hashCode(),patient2.hashCode());
	}
	
}
