package au.com.reece.addressbookapplication.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 
}
