/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        if (asciis.length == 0) return new String[0];
        int maxLength = asciis[0].length();
        for (String str: asciis)
            maxLength = maxLength > str.length() ? maxLength : str.length();
        String[] res = new String[asciis.length];
        for (int i = 0; i < asciis.length; ++i)
            res[i] = asciis[i];
        for (int i = maxLength - 1; i >= 0; --i)
            sortHelperLSD(res, i);
        return res;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] count = new int[257];
        for (int i = 0; i < asciis.length; ++i) {
            int num = 0;
            if (index < asciis[i].length()) num = asciis[i].charAt(index);
            count[num + 1]++;
        }
        for (int i = 1; i < count.length; ++i)
            count[i] += count[i - 1];
        String[] temp = new String[asciis.length];
        for (int i = 0; i < asciis.length; ++i) {
            int item = 0;
            if (index < asciis[i].length()) item = asciis[i].charAt(index);
            int place = count[item];
            temp[place] = asciis[i];
            count[item] += 1;
        }
        for (int i = 0; i < asciis.length; ++i)
            asciis[i] = temp[i];
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
