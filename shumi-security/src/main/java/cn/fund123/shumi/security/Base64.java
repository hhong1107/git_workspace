package cn.fund123.shumi.security;

/**
 * @ClassName Base64
 * @author lijh@fund123.cn
 * @date 2013年12月3日
 */
public class Base64 {
	
	private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;

    private static final int SixBitMask = 0x3f;

    private static final byte PAD = '=';

    private static final byte[] EncodeTable = {'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '+', '/'};

    private static final int[] DecodeTable = new int[128];

    static {
        for (int i = 0; i < EncodeTable.length; i++) {
            DecodeTable[EncodeTable[i]] = i;
        }
    }

     static byte[] decode(String s) {
        int delta = s.endsWith("==") ? 2 : s.endsWith("=") ? 1 : 0;
        byte[] buffer = new byte[s.length() * BYTES_PER_UNENCODED_BLOCK / BYTES_PER_ENCODED_BLOCK - delta];
        int mask = 0xFF;
        int pos = 0;
        for (int i = 0; i < s.length(); i += BYTES_PER_ENCODED_BLOCK) {
            int c0 = DecodeTable[s.charAt(i)];
            int c1 = DecodeTable[s.charAt(i + 1)];
            buffer[pos++] = (byte) (((c0 << 2) | (c1 >> 4)) & mask);
            if (pos >= buffer.length) {
                return buffer;
            }
            int c2 = DecodeTable[s.charAt(i + 2)];
            buffer[pos++] = (byte) (((c1 << 4) | (c2 >> 2)) & mask);
            if (pos >= buffer.length) {
                return buffer;
            }
            int c3 = DecodeTable[s.charAt(i + 3)];
            buffer[pos++] = (byte) (((c2 << 6) | c3) & mask);
        }
        return buffer;
    }

    static byte[] encode(byte[] in) {

        int modulus = 0;
        int bitWorkArea = 0;
        int numEncodedBytes = (in.length / BYTES_PER_UNENCODED_BLOCK) * BYTES_PER_ENCODED_BLOCK
                + ((in.length % BYTES_PER_UNENCODED_BLOCK == 0) ? 0 : 4);

        byte[] buffer = new byte[numEncodedBytes];
        int pos = 0;

        for (int b : in) {
            modulus = (modulus + 1) % BYTES_PER_UNENCODED_BLOCK;

            if (b < 0)
                b += 256;

            bitWorkArea = (bitWorkArea << 8) + b;
            if (0 == modulus) { 
                buffer[pos++] = EncodeTable[(bitWorkArea >> 18) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea >> 12) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea >> 6) & SixBitMask];
                buffer[pos++] = EncodeTable[bitWorkArea & SixBitMask];
            }
        }

        switch (modulus) { 
            case 1:
                buffer[pos++] = EncodeTable[(bitWorkArea >> 2) & SixBitMask]; 
                buffer[pos++] = EncodeTable[(bitWorkArea << 4) & SixBitMask]; 
                buffer[pos++] = PAD;
                buffer[pos] = PAD;
                break;

            case 2: 
                buffer[pos++] = EncodeTable[(bitWorkArea >> 10) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea >> 4) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea << 2) & SixBitMask];
                buffer[pos] = PAD; 
                break;
        }

        return buffer;
    }
}
