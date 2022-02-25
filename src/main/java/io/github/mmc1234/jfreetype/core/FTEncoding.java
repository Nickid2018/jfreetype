package io.github.mmc1234.jfreetype.core;

/**
 * An enumeration to specify character sets supported by charmaps.
 * Used in the {@link FreeType#FTSelectCharmap} API function.
 *
 * @apiNote Despite the name, this enumeration lists specific character repertories (i.e., charsets),
 * and not text encoding methods (e.g., UTF-8, UTF-16, etc.).<br/>
 * Other encodings might be defined in the future.<br/><br/>
 * <p>
 * When loading a font, FreeType makes a Unicode charmap active if possible (either if the font provides such a charmap,
 * or if FreeType can synthesize one from PostScript glyph name dictionaries; in either case, the charmap is tagged with
 * {@link #UNICODE}). If such a charmap is synthesized, it is placed at the first position of the charmap array.<br/>
 * All other encodings are considered legacy and tagged only if explicitly defined in the font file.
 * Otherwise, {@link #NONE} is used.<br/>
 * {@link #NONE} is set by the BDF and PCF drivers if the charmap is neither Unicode nor ISO-8859-1
 * (otherwise it is set to {@link #UNICODE}). Use FT_Get_BDF_Charset_ID to find out which encoding is really present.
 * If, for example, the cs_registry field is ‘KOI8’ and the cs_encoding field is ‘R’, the font is encoded in KOI8-R.
 * {@link #NONE} is always set (with a single exception) by the winfonts driver. Use {@code FT_Get_WinFNT_Header}
 * and examine the charset field of the {@code FT_WinFNT_HeaderRec} structure to find out which encoding is really present.
 * For example, {@code FT_WinFNT_ID_CP1251} (204) means Windows code page 1251 (for Russian).<br/>
 * {@link #NONE} is set if platform_id is {@code TT_PLATFORM_MACINTOSH} and encoding_id is not
 * {@code TT_MAC_ID_ROMAN} (otherwise it is set to {@link #APPLE_ROMAN}).<br/>
 * If platform_id is {@code TT_PLATFORM_MACINTOSH}, use the function {@code FT_Get_CMap_Language_ID} to
 * query the Mac language ID that may be needed to be able to distinguish Apple encoding variants. See
 * <a href="https://www.unicode.org/Public/MAPPINGS/VENDORS/APPLE/Readme.txt">this</a>
 * to get an idea how to do that. Basically, if the language ID is 0, don't use it, otherwise subtract 1
 * from the language ID. Then examine encoding_id. If, for example, encoding_id is {@code TT_MAC_ID_ROMAN} and
 * the language ID (minus 1) is {@code TT_MAC_LANGID_GREEK}, it is the Greek encoding, not Roman.
 * TT_MAC_ID_ARABIC with {@code TT_MAC_LANGID_FARSI} means the Farsi variant the Arabic encoding.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef enum  FT_Encoding_
 *   {
 *     FT_ENC_TAG( FT_ENCODING_NONE, 0, 0, 0, 0 ),
 *
 *     FT_ENC_TAG( FT_ENCODING_MS_SYMBOL, 's', 'y', 'm', 'b' ),
 *     FT_ENC_TAG( FT_ENCODING_UNICODE,   'u', 'n', 'i', 'c' ),
 *
 *     FT_ENC_TAG( FT_ENCODING_SJIS,    's', 'j', 'i', 's' ),
 *     FT_ENC_TAG( FT_ENCODING_PRC,     'g', 'b', ' ', ' ' ),
 *     FT_ENC_TAG( FT_ENCODING_BIG5,    'b', 'i', 'g', '5' ),
 *     FT_ENC_TAG( FT_ENCODING_WANSUNG, 'w', 'a', 'n', 's' ),
 *     FT_ENC_TAG( FT_ENCODING_JOHAB,   'j', 'o', 'h', 'a' ),
 *
 *     // for backward compatibility
 *     FT_ENCODING_GB2312=FT_ENCODING_PRC,
 *     FT_ENCODING_MS_SJIS=FT_ENCODING_SJIS,
 *     FT_ENCODING_MS_GB2312=FT_ENCODING_PRC,
 *     FT_ENCODING_MS_BIG5=FT_ENCODING_BIG5,
 *     FT_ENCODING_MS_WANSUNG=FT_ENCODING_WANSUNG,
 *     FT_ENCODING_MS_JOHAB=FT_ENCODING_JOHAB,
 *
 *     FT_ENC_TAG(FT_ENCODING_ADOBE_STANDARD,'A','D','O','B'),
 *     FT_ENC_TAG(FT_ENCODING_ADOBE_EXPERT,'A','D','B','E'),
 *     FT_ENC_TAG(FT_ENCODING_ADOBE_CUSTOM,'A','D','B','C'),
 *     FT_ENC_TAG(FT_ENCODING_ADOBE_LATIN_1,'l','a','t','1'),
 *
 *     FT_ENC_TAG(FT_ENCODING_OLD_LATIN_2,'l','a','t','2'),
 *
 *     FT_ENC_TAG(FT_ENCODING_APPLE_ROMAN,'a','r','m','n')
 *
 *   }FT_Encoding;
 * }</pre>
 */
public enum FTEncoding {
    /**
     * The encoding value 0 is reserved for all formats except BDF, PCF, and Windows FNT; see below for more information.
     */
    NONE(0, 0, 0, 0),
    /**
     * Microsoft Symbol encoding, used to encode mathematical symbols and wingdings. For more information,
     * see ‘https://www.microsoft.com/typography/otspec/recom.htm#non-standard-symbol-fonts’,
     * ‘http://www.kostis.net/charsets/symbol.htm’, and ‘http://www.kostis.net/charsets/wingding.htm’.<br/>
     * This encoding uses character codes from the PUA (Private Unicode Area) in the range U+F020-U+F0FF.
     */
    MS_SYMBOL('s', 'y', 'm', 'b'),
    /**
     * The Unicode character set. This value covers all versions of the Unicode repertoire, including ASCII and Latin-1.
     * Most fonts include a Unicode charmap, but not all of them.<br/>
     * For example, if you want to access Unicode value U+1F028 (and the font contains it),
     * use value 0x1F028 as the input value for {@link FreeType#FTGetCharIndex}.
     */
    UNICODE('u', 'n', 'i', 'c'),
    /**
     * Shift JIS encoding for Japanese. More info at ‘https://en.wikipedia.org/wiki/Shift_JIS’.
     * See note on multi-byte encodings below.
     */
    SJIS('s', 'j', 'i', 's'),
    /**
     * Corresponds to encoding systems mainly for Simplified Chinese as used in People's Republic of China (PRC).
     * The encoding layout is based on GB 2312 and its supersets GBK and GB 18030.
     */
    PRC('g', 'b', ' ', ' '),
    /**
     * Corresponds to an encoding system for Traditional Chinese as used in Taiwan and Hong Kong.
     */
    BIG5('b', 'i', 'g', '5'),
    /**
     * Corresponds to the Korean encoding system known as Extended Wansung (MS Windows code page 949).
     * For more information see ‘https://www.unicode.org/Public/MAPPINGS/VENDORS/MICSFT/WindowsBestFit/bestfit949.txt’.
     */
    WANSUNG('w', 'a', 'n', 's'),
    /**
     * The Korean standard character set (KS C 5601-1992), which corresponds to MS Windows code page 1361.
     * This character set includes all possible Hangul character combinations.
     */
    JOHAB('j', 'o', 'h', 'a'),
    /**
     * Adobe Standard encoding, as found in Type 1, CFF, and OpenType/CFF fonts. It is limited to 256 character codes.
     */
    ADOBE_STANDARD('A', 'D', 'O', 'B'),

    /**
     * Adobe Expert encoding, as found in Type 1, CFF, and OpenType/CFF fonts. It is limited to 256 character codes.
     */
    ADOBE_EXPERT('A', 'D', 'B', 'E'),
    /**
     * Corresponds to a custom encoding, as found in Type 1, CFF, and OpenType/CFF fonts.
     * It is limited to 256 character codes.
     */
    ADOBE_CUSTOM('A', 'D', 'B', 'C'),
    /**
     * Corresponds to a Latin-1 encoding as defined in a Type 1 PostScript font. It is limited to 256 character codes.
     */
    ADOBE_LATIN_1('l', 'a', 't', '1'),
    /**
     * This value is deprecated and was neither used nor reported by FreeType. Don't use or test for it.
     */
    OLD_LATIN_2('l', 'a', 't', '2'),
    /**
     * Apple roman encoding. Many TrueType and OpenType fonts contain a charmap for this 8-bit encoding,
     * since older versions of Mac OS are able to use it.
     */
    APPLE_ROMAN('a', 'r', 'm', 'n');

    @Deprecated
    public static final FTEncoding GB2312 = PRC;
    @Deprecated
    public static final FTEncoding MS_SJIS = SJIS;
    @Deprecated
    public static final FTEncoding MS_GB2312 = PRC;
    @Deprecated
    public static final FTEncoding MS_BIG5 = BIG5;
    @Deprecated
    public static final FTEncoding MS_WANSUNG = WANSUNG;
    @Deprecated
    public static final FTEncoding MS_JOHAB = JOHAB;
    private int value;

    FTEncoding(int a, int b, int c, int d) {
        // TODO FT_ENC_TAG
    }

    public int value() {
        return value;
    }
}
