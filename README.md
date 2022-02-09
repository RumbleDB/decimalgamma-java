# decimalgamma-java

Implementation in Java of the DecimalGamma encoding

![Description of the encoding](https://www.dropbox.com/s/xqj8ra6fu97c9kx/DecimalGamma.png?dl=1)

## Reference

This implementation follows the encoding and decoding as specified in the
[decimalInfinite paper](https://arxiv.org/abs/1506.01598). There is an
[implementation in C++](https://github.com/ghislainfourny/decimalgamma-cpp).

## Publish jars

The jars will be published to the package registry of this project on Github. One way to inform maven on how to
authenticate with Github is to configure Github as a server in your `~/.m2/settings.yml` file using your
[personal token](https://github.com/settings/tokens) (make sure to give it `write:packages` access):

```shell
cat ~/.m2/settings.xml
<settings><servers><server><id>github</id><username>elwin</username><password>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</password></server></servers></settings>
```

Once this is done, a package can be published by running `make publish`.

## Consume jars

Unfortunately, even though the published jars are public, one still needs to authenticate to be able to access them
(see [here](https://github.community/t/download-from-github-package-registry-without-authentication/14407)). This can be
done in the same way as above. Likely, the `read:packages` permission is necessary (not tested). Alternatively when
using GitHub Actions, the environment variable `${{ secrets.GITHUB_TOKEN }}` can be injected and used to properly
authenticate.
