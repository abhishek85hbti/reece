package hackerrank.string;

public class StringOperations {
	
	static int MAX_CHAR = 256;
	
	
	static boolean isStringsPermutationOfEachOther(String string1, String string2) {
		
		if (string1.length() != string2.length()) {
			return false;
		}
		String str1 = string1.toLowerCase();
		String str2 = string2.toLowerCase();
		
		int[] codes1 = new int[MAX_CHAR];
		int[] codes2 = new int[MAX_CHAR];
		
		for (int i=0;i<str1.length();i++) {
			codes1[str1.charAt(i)]++;
			codes2[str2.charAt(i)]++;
		}
		
		for (int i=0;i<MAX_CHAR;i++) {
			if (codes1[i] != codes2[i]) {
				return false;
			}
		}
		return true;
	}
	
	static boolean isUniqueCharactersFav(String string) {
		
		String str = string.toLowerCase();
		if (str.length() > MAX_CHAR) {
			return false;
		}
		int codes[] = new int[MAX_CHAR];
		for (int i=0;i<str.length();i++) {
			if (codes[str.charAt(i)] != 0) {
				return false;
			}
			codes[str.charAt(i)]++;
		}
		return true;
	}

	public static void main(String arg[]) {
		
		System.out.println("Is usingExtraDatStructure:"
				+ isUniqueCharactersFav("Str12!"));
		
		System.out.println("Is isStringsPermutationOfEachOther:"
				+ isStringsPermutationOfEachOther("acaa", "aaca"));
	}
}
