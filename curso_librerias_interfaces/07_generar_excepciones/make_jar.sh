CLASSES=classes
SRC=src
RELEASE_DIRECTORY=release
RELEASE_PROJECT=project.jar

if [ ! -d $CLASSES ]
then
    mkdir $CLASSES
fi

if [ ! -d $RELEASE_DIRECTORY ]
then
    mkdir $RELEASE_DIRECTORY
fi


javac -d $CLASSES $SRC/*.java
cd $CLASSES

cat<<EOF > manifest.mf
Manifest-Version: 1.0
Main-Class: MainFrame
Class-Path: .
EOF
jar -cmf manifest.mf ../$RELEASE_DIRECTORY/$RELEASE_PROJECT *.class
cd ..

