package lzwhuffman;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

public class Huffman {
    private String SourceFileName = null;
    private String DestinationFileName = null;

    private int[] FrequencyArray = new int[256];
    private HuffmanNode root = new HuffmanNode();
    private Vector[] HuffmanCodeArray = new Vector[256];

    public Huffman() {
        for (int i = 0; i < 256; i++) FrequencyArray[i] = 0;
    }

    public void Compress(String sourceFileName, String destinationFileName) {
        setSourceFileName(sourceFileName);
        setDestinationFileName(destinationFileName);

        ComputeFrequency();
        MakeHuffmanTree();

        MakeHuffmanCode(root);
        SaveToFile();
    }

    private void ComputeFrequency() {
        try {
            File f = new File(SourceFileName);
            RandomAccessFile in = new RandomAccessFile(f, "r");

            byte b;
            while (in.getFilePointer() < in.length()) {
                FrequencyArray[in.readUnsignedByte()]++;
            }

            in.close();

        }
        catch (IOException exp) {
            System.out.println(exp.getMessage() + "*******");
        }
    }

    private void MakeHuffmanTree() {

        Vector v = new Vector(10, 1);
        for (int i = 0; i < 256; i++)
            if (FrequencyArray[i] > 0)
                v.addElement(new HuffmanNode(null, null, null, FrequencyArray[i], (byte) i));

        Sort(v);

        if (v.size() == 1) {
            root.right = (HuffmanNode) v.elementAt(0);
            return;
        }

        HuffmanNode p = new HuffmanNode(), x, y;
        p = null;
        while (v.size() > 1) {

            int size = v.size();
            x = (HuffmanNode) v.elementAt(size - 1);
            y = (HuffmanNode) v.elementAt(size - 2);

            p = new HuffmanNode();
            p.parent = null;
            p.left = y;
            p.right = x;
            p.ByteFrequency = x.ByteFrequency + y.ByteFrequency;

            y.parent = p;
            x.parent = p;

            v.removeElementAt(size - 1);
            v.removeElementAt(size - 2);

            int i;
            for (i = 0; i < v.size(); i++)
                if (p.ByteFrequency > ((HuffmanNode) v.elementAt(i)).ByteFrequency) {
                    v.add(i, p);
                    break;
                }
            if (i == v.size()) v.add(i, p);

        }
        if (p != null) {
            root = new HuffmanNode();
            root = p;
        }
    }

    private Vector tempV = new Vector(10, 1);

    private void MakeHuffmanCode(HuffmanNode p) {

        if (p.left != null) {
            tempV.addElement(new Character('0'));
            MakeHuffmanCode(p.left);
        }
        if (p.right != null) {
            tempV.addElement(new Character('1'));
            MakeHuffmanCode(p.right);
        }
        if (p.left == null && p.right == null) {
            int index = (p.ByteName >= 0) ? p.ByteName : 256 + p.ByteName;

            HuffmanCodeArray[index] = new Vector(10, 1);
            for (int i = 0; i < tempV.size(); i++)
                HuffmanCodeArray[index].addElement(tempV.elementAt(i));

        }
        if (tempV.size() > 0) tempV.removeElementAt(tempV.size() - 1);
    }

    private void SaveToFile() {
        try {
            File f1 = new File(SourceFileName);
            RandomAccessFile in = new RandomAccessFile(f1, "r");

            File f2 = new File(DestinationFileName);
            if (f2.exists()) f2.delete();
            RandomAccessFile out = new RandomAccessFile(f2, "rw");

            int n = 0;
            for (int i = 0; i < 256; i++)
                if (FrequencyArray[i] > 0) n++;

            out.writeInt(n);
            for (int i = 0; i < 256; i++)
                if (FrequencyArray[i] > 0) {
                    out.writeByte(i);
                    out.writeInt(FrequencyArray[i]);
                }

            byte buf;
            Vector v1 = new Vector(10, 1);
            short mask = 1;
            byte maskIndex = 8;
            char ch;
            byte b = 0;

            while (in.getFilePointer() < in.length()) {

                v1 = HuffmanCodeArray[in.readUnsignedByte()];

                for (int i = 0; i < v1.size(); i++) {
                    ch = ((Character) v1.elementAt(i)).charValue();
                    maskIndex--;
                    if (ch == '1') mask = 1;
                    else mask = 0;
                    mask <<= maskIndex;
                    b |= mask;

                    if (maskIndex == 0) {
                        out.writeByte(b);

                        b = 0;
                        mask = 1;
                        maskIndex = 8;
                    }

                }

            }
            if (maskIndex != 0) out.writeByte(b);
            out.writeByte(maskIndex);

            out.close();
            in.close();

        }
        catch (IOException exp) {
            System.out.println(exp.getMessage() + "*****");
        }
    }

    private void Sort(Vector v) {
        Object temp;
        for (int i = 1; i <= v.size() - 1; i++)
            for (int j = v.size() - 1; j >= i; j--) {
                if (((HuffmanNode) v.elementAt(j)).ByteFrequency > ((HuffmanNode) v.elementAt(j - 1)).ByteFrequency) {
                    temp = v.elementAt(j);
                    v.setElementAt(v.elementAt(j - 1), j);
                    v.setElementAt(temp, j - 1);

                }
            }

    }

    public void setSourceFileName(String s) {
        SourceFileName = s;
    }

    public void setDestinationFileName(String s) {
        DestinationFileName = s;
    }

    public void DeCompress(String sourceFileName, String destinationFileName) {
        try {
            setSourceFileName(sourceFileName);
            setDestinationFileName(destinationFileName);

            File f1 = new File(SourceFileName);
            RandomAccessFile in = new RandomAccessFile(f1, "r");

            File f2 = new File(DestinationFileName);
            if (f2.exists()) f2.delete();
            RandomAccessFile out = new RandomAccessFile(f2, "rw");

            int n = in.readInt();
            for (int i = 0; i < n; i++)
                FrequencyArray[in.readUnsignedByte()] = in.readInt();

            MakeHuffmanTree();

            byte b;
            byte mask = 1;//1000 0000 b
            byte maskIndex = 8;
            byte maskLimit = 8;
            byte[] buf = new byte[1];

            HuffmanNode p = new HuffmanNode();
            p = root;
            b = in.readByte();

            while (in.getFilePointer() < in.length() - 1 || maskIndex >= maskLimit) {
                if (p.left == null && p.right == null) {
                    buf[0] = p.ByteName;
                    out.write(buf, 0, 1);
                    p = new HuffmanNode();
                    p = root;

                }//if

                mask = 1;//1000 0000 b
                maskIndex--;
                mask <<= maskIndex;

                mask &= b;
                mask >>>= maskIndex;

                if (mask == 0 && p.left != null) p = p.left;
                else if (mask != 0 && p.right != null) p = p.right;

                if (maskIndex == 0 && in.getFilePointer() < in.length() - 1) {
                    b = in.readByte();
                    if (in.getFilePointer() == in.length() - 1) maskLimit = in.readByte();

                    maskIndex = 8;
                    mask = 1;
                }//if

            }//while

            in.close();
            out.close();

        }//try
        catch (IOException exp) {
            System.out.println(exp.getMessage() + "******");
        }//catch;

    }//DeCompress();

}//Huffman

class HuffmanNode {
    protected HuffmanNode() {
        right = null;
        left = null;
        parent = null;
        ByteName = 0;
    }

    protected HuffmanNode(HuffmanNode parentValue, HuffmanNode leftValue, HuffmanNode rightValue, int ByteFrequencyValue, byte ByteNameValue) {
        parent = parentValue;
        left = leftValue;
        right = rightValue;

        ByteFrequency = ByteFrequencyValue;
        ByteName = ByteNameValue;
    }

    protected HuffmanNode right;
    protected HuffmanNode left;
    protected HuffmanNode parent;

    protected int ByteFrequency;
    protected byte ByteName;
}
