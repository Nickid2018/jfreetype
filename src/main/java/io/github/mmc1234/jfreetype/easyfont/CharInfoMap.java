package io.github.mmc1234.jfreetype.easyfont;

import java.util.Objects;

/**
 * A hash map to save char infos.
 */
public class CharInfoMap {

    private CharInfo[] charInfos;
    private int recordChars;

    /**
     * Package-private
     */
    CharInfoMap() {
        charInfos = new CharInfo[65536];
        recordChars = 0;
    }

    /**
     * Get record chars count.
     * @return count of the chars
     */
    public int getRecordChars() {
        return recordChars;
    }

    /**
     * Put char information into this.
     * @param info a char info
     */
    public void putCharInfo(CharInfo info) {
        if (recordChars == charInfos.length)
            rehash();
        charInfos[hashFunction(info)] = info;
        recordChars++;
    }

    /**
     * Get a char info.
     * @param codepoint a char
     * @param size a size
     * @return a char info, or null
     */
    public CharInfo getCharInfo(int codepoint, int size) {
        int hash = Objects.hash(codepoint, size) % charInfos.length;
        int origin = hash;
        CharInfo source;
        while ((source = charInfos[hash]) != null) {
            if (source.codepoint() == codepoint && source.size() == size)
                return source;
            hash = (hash + 1) % charInfos.length;
            if (hash == origin)
                break;
        }
        return null;
    }

    /**
     * Remove a char info.
     * @param info info to remove
     */
    public void removeCharInfo(CharInfo info) {
        int position = hashFunction(info);
        if (position == -1)
            return;
        if (charInfos[position] != null) {
            charInfos[position] = null;
            recordChars--;
        }
    }

    private void rehash() {
        CharInfo[] sourceCharInfo = charInfos;
        charInfos = new CharInfo[sourceCharInfo.length * 2];
        for (CharInfo info : sourceCharInfo)
            putCharInfo(info);
    }

    private int hashFunction(CharInfo info) {
        int hash = info.hashCode() % charInfos.length;
        int origin = hash;
        CharInfo source;
        while ((source = charInfos[hash]) != null) {
            if (source.equals(info))
                break;
            hash = (hash + 1) % charInfos.length;
            if (hash == origin)
                return -1;
        }
        return hash;
    }
}
