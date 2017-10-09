/**
 * Created by rzd on 17-5-9.
 */
public class Huffman<T>
{
    private int n;

    HTNode<T>[] ht;
    HCode[] hcd;
}
class HTNode<T>
{
    T data;
    double weight;
    int parent;
    int lchild;
    int rchild;
}
class HCode
{
    char[] cd;
    int start;
}