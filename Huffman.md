用ht数组存放哈夫曼树，每个结点用ht数组下标唯一标识。对于具有n0个叶结点的哈夫曼树，共有2n0-1个结点，ht[0~n0-1]存放叶子结点，
ht[n0~2n0-2]存放其他需要构造的非叶子节点。  

构造思路： 先将2n0-1个结点的parent、lchild、rchild域置为初值-1，将n0个叶子结点看成是n0棵二叉树。  
对于每个非叶子结点ht[i],n0<=i<=2n0-2,从ht[0～i-1]找出所有二叉树根结点（parent域为-1）最小的两个根结点ht[lnode]和ht[rnode]，将它们作为ht[i]的左右子树，并将它们parent域置为i，同时[i].weight=ht[lnode].weight+ht[rnode].weight.  
如此重复直到所有n0-1个非叶子结点处理完毕.
