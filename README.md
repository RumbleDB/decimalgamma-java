# decimalgamma-java

Implementation in Java of the DecimalGamma encoding

## Reference
This implementation follows the encoding and decoding as specified in the
[decimalInfinite paper](https://arxiv.org/abs/1506.01598). There is an
[implementation in C++](https://github.com/ghislainfourny/decimalgamma-cpp), however, they differ slightly
insofar as the C++ version inverts digits of the significant in case of a negative sign in sets of three
while the paper in this implementation invert the entire number (i.e. 10 - x).

## Publish jars
The jars will be published to the package registry of this project on Github. One way to inform maven on how to
authenticate with Github is to configure Github as a server in your `~/.m2/settings.yml` file using your
[personal token](https://github.com/settings/tokens) (make sure to give it `write:packages` access):
```shell
cat ~/.m2/settings.xml
<settings>
    <servers>
        
        <server>
            <id>github</id>
            <username><!-- Your Github username --></username>
            <password><!-- Your personal Github token --></password>
        </server>

    </servers>
</settings>
```

Once this is done, a package can be published by running `make publish`.