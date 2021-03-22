# decimalgamma-java

Implementation in Java of the DecimalGamma encoding

## Reference
This implementation follows the encoding and decoding as specified in the
[decimalInfinite paper](https://arxiv.org/abs/1506.01598). There is an
[implementation in C++](https://github.com/ghislainfourny/decimalgamma-cpp), however, they differ slightly
insofar as the C++ version inverts digits of the significant in case of a negative sign in sets of three
while the paper in this implementation invert the entire number (i.e. 10 - x).

## Publish jars
The jars will be published to the package registry of this project on gitlab.inf.ethz.ch. Make sure to follow the
instructions to authenticate Maven with the Gitlab instance as described
[here](https://docs.gitlab.com/ee/user/packages/maven_repository/index.html#authenticate-to-the-package-registry-with-maven).

Once this is done, a package can be published by running `make publish`.