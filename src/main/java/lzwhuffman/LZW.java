package lzwhuffman;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class LZW {
    private String SourceFileName = null;
    private String DestinationFileName = null;

    public LZW() {
    }

    public void setSourceFileName(String s) {
        SourceFileName = s;
    }//setSourceFileName();

    public void setDestinationFileName(String s) {
        DestinationFileName = s;
    }//setDestinationFileName();

    public void Compress(String sourceFileName, String destinationFileNmae) {
        setSourceFileName(sourceFileName);
        setDestinationFileName(destinationFileNmae);

        Vector StringTable = new Vector(10, 1);

        String str = new String();
        byte[] ch = new byte[1];

        try {
            File f1 = new File(SourceFileName);
            RandomAccessFile in = new RandomAccessFile(f1, "r");

            File f2 = new File(DestinationFileName);
            if (f2.exists()) f2.delete();
            RandomAccessFile out = new RandomAccessFile(f2, "rw");

            ch[0] = in.readByte();
            str += new String(ch);

            short n = 256;
            while (in.getFilePointer() < in.length()) {
                ch[0] = in.readByte();
                if (Search(StringTable, str + new String(ch)) != -1) {
                    str += new String(ch);
                } else {
                    if (str.length() == 1) out.writeShort(str.getBytes()[0]);
                    else {
                        short code = Search(StringTable, str);
                        out.writeShort(code);
                    }//else

                    String s = str + new String(ch);
                    int i;
                    for (i = 0; i < StringTable.size(); i++)
                        if (s.compareTo(((TableCell) StringTable.elementAt(i)).string) < 0) {
                            StringTable.add(i, new TableCell(n, s));
                            break;
                        }

                    if (i == StringTable.size())
                        StringTable.add(i, new TableCell(n, s));

                    n++;
                    str = new String(ch);
                }//else
            }//while
            if (str.length() == 1) out.writeShort(str.getBytes()[0]);
            else {
                int code = Search(StringTable, str);
                out.writeShort(code);
            }

            in.close();
            out.close();

        }//try
        catch (IOException exp) {
            System.out.println(exp.getMessage());
        }//catch

    }//Compress();

    //-----------------------------------------------------------------------------
    public void DeCompress(String sourceFileName, String destinationFileName) {
        setSourceFileName(sourceFileName);
        setDestinationFileName(destinationFileName);

        try {
            File f1 = new File(SourceFileName);
            RandomAccessFile in = new RandomAccessFile(f1, "r");

            File f2 = new File(DestinationFileName);
            if (f2.exists()) f2.delete();
            RandomAccessFile out = new RandomAccessFile(f2, "rw");

            Vector TranslationTable = new Vector(10, 1);

            short old_code;
            short new_code;
            byte[] ch = new byte[1];

            short n = 256;

            old_code = in.readShort();
            out.writeByte((byte) old_code);
            ch[0] = (byte) old_code;

            while (in.getFilePointer() < in.length()) {
                new_code = in.readShort();
                String str = new String();
                if (Search(TranslationTable, new_code) == null) {
                    str = Search(TranslationTable, old_code);
                    str += new String(ch);
                } else
                    str = Search(TranslationTable, new_code);

                for (int i = 0; i < str.length(); i++)
                    out.writeByte((byte) str.charAt(i));

                ch[0] = (byte) str.charAt(0);
                String s = Search(TranslationTable, old_code) + new String(ch);

                int i;
                for (i = 0; i < TranslationTable.size(); i++)
                    if (s.compareTo(((TableCell) TranslationTable.elementAt(i)).string) < 0) {
                        TranslationTable.add(i, new TableCell(n, s));
                        break;
                    }

                if (i == TranslationTable.size())
                    TranslationTable.add(i, new TableCell(n, s));

                n++;

                old_code = new_code;

            }//while

            in.close();
            out.close();

        }//try
        catch (IOException exp) {
            System.out.println(exp.getMessage());
        }//catch
    }//DeCompress();

    //-----------------------------------------------------------------------------
    private short Search(Vector v, String s) {
        return BinarySearch(v, s, 0, v.size() - 1);
    }//Search();

    //-----------------------------------------------------------------------------
    private String Search(Vector v, short code) {
        if (code < 256) {
            byte[] b = new byte[1];
            b[0] = (byte) code;
            return new String(b);
        } else
            return BinarySearch(v, code, 0, v.size() - 1);
    }//Search();

    //-----------------------------------------------------------------------------
    private short BinarySearch(Vector v, String s, int beginIndex, int endIndex) {
        if (endIndex <= beginIndex) return -1;

        int middleIndex = (beginIndex + endIndex) / 2;
        String str = ((TableCell) v.elementAt(middleIndex)).string;
        if (s.equals(str)) return ((TableCell) v.elementAt(middleIndex)).code;
        else if (s.compareTo(str) > 0) BinarySearch(v, s, middleIndex + 1, endIndex);
        else if (s.compareTo(str) < 0) BinarySearch(v, s, beginIndex, middleIndex - 1);
        return -1;
    }//BinarySearch()

    //-----------------------------------------------------------------------------
    private String BinarySearch(Vector v, short code, int beginIndex, int endIndex) {
        if (endIndex <= beginIndex) return null;

        int middleIndex = (beginIndex + endIndex) / 2;
        short c = ((TableCell) v.elementAt(middleIndex)).code;
        if (code == c) return ((TableCell) v.elementAt(middleIndex)).string;
        else if (code > c) BinarySearch(v, code, middleIndex + 1, endIndex);
        else if (code < c) BinarySearch(v, code, beginIndex, middleIndex - 1);
        return null;
    }//BinarySearch();

}//LZW

//------------------------------------------------------------------------------
class TableCell {
    public TableCell() {
        code = 0;
        string = null;
    }

    public TableCell(short codeValue, String stringValue) {
        code = codeValue;
        string = stringValue;
    }

    protected short code;
    protected String string = new String();
}//TableCell


