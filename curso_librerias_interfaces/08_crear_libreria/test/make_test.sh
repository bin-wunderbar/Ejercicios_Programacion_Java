CLASSES=classes
SRC=src
LIB=lib/ccomponents.jar
RELEASE_DIRECTORY=release
RELEASE_PROJECT=testlibrary.jar

if [ ! -d $CLASSES ]
then
    mkdir $CLASSES
fi

if [ ! -d $RELEASE_DIRECTORY ]
then
    mkdir $RELEASE_DIRECTORY
fi


javac -cp $LIB -sourcepath $SRC -d $CLASSES $SRC/gui/MainFrame.java
cd $CLASSES

cat<<EOF > manifest.mf
Manifest-Version: 1.0
Main-Class: gui.MainFrame
Class-Path: .
EOF
jar -xf ../$LIB
jar -cmf manifest.mf ../$RELEASE_DIRECTORY/$RELEASE_PROJECT gui/*.class ccomponents/*.class
cd ..

