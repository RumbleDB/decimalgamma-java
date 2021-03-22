# decimalgamma-java

Implementation in Java of the DecimalGamma encoding

## Reference
This implementation follows the encoding and decoding as specified in the
[decimalInfinite paper](https://arxiv.org/abs/1506.01598). There is an
[implementation in C++](https://github.com/ghislainfourny/decimalgamma-cpp), however, they differ slightly
insofar as the C++ version inverts digits of the significant in case of a negative sign in sets of three
while the paper in this implementation invert the entire number (i.e. 10 - x).