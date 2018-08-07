package stringutil;

import org.junit.jupiter.api.Test;

public class ReplaceUtilTest {
	@Test
	void testReplacewhiteSpace() throws Exception{
		System.out.println(ReplaceUtil.replacewhiteSpace("ab c d !@ triple 4649 @! ad f0", "!", "@"));
		System.out.println(ReplaceUtil.replacewhiteSpace("ab c d !@ triple 4649 @! ad f0 !@triple 4649@! dd d", "!", "@"));
	}

}
