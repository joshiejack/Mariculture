package mariculture.core.helpers;

public class AverageHelper {
	public static int getMode(int[] array) {
		int mode = -1;
		int modefreq = -1;

		for (int i = 0; i < array.length; i++) {
			int current = array[i];
			int freq = getNum(array, current);
			if (freq > modefreq) {
				mode = current;
				modefreq = freq;
			}
		}
		return (mode);
	}

	private static int getNum(int[] array, int current) {
		int num = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == current) {
				num++;
			}
		}
		return (num);
	}
}
