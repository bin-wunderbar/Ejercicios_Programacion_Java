CLASSES=classes
SRC=src
RELEASE_DIRECTORY=release
RELEASE_PROJECT=ccomponents.jar

if [ ! -d $CLASSES ]
then
    mkdir $CLASSES
fi

if [ ! -d $RELEASE_DIRECTORY ]
then
    mkdir $RELEASE_DIRECTORY
fi


javac -sourcepath $SRC -d $CLASSES $SRC/ccomponents/*

cd $CLASSES

cat<<EOF > manifest.mf
Manifest-Version: 1.0
Class-Path: .
EOF
jar -cmf manifest.mf ../$RELEASE_DIRECTORY/$RELEASE_PROJECT ccomponents/*.class
cd ..
