mvn clean install -Dmaven.test.skip=true
docker build -t abhishek85hbti/reece/repoaddressbook:1.0 .
docker run -p 8080:8080 addressbook:1.0