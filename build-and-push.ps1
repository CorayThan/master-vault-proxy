
docker --version

./gradlew copyVersion
./gradlew build
./gradlew dockerCopyJar

$VERSION = Get-Content version.txt

cd ./docker

docker build -t "mvproxy:$VERSION" .

aws ecr get-login-password --region us-west-2 | docker login --username AWS --password-stdin 811687120814.dkr.ecr.us-west-2.amazonaws.com/mvproxy

docker tag "mvproxy:$VERSION" 811687120814.dkr.ecr.us-west-2.amazonaws.com/mvproxy:$VERSION

docker push 811687120814.dkr.ecr.us-west-2.amazonaws.com/mvproxy:$VERSION

"All done now!"
