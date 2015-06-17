http://www.scala-sbt.org/sbt-native-packager/gettingstarted.html
http://www.scala-sbt.org/0.13/docs/Publishing.html
http://www.scala-sbt.org/sbt-native-packager/
zip:
sbt universal:packageBin
deban:
sbt debian:packageBin

git add .
git commit --message "commit msg!"
git push #输入帐号信息然后提交
