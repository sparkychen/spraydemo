logLevel := Level.Warn

resolvers ++= Seq(
  "Maven center repository" at "http://search.maven.org/",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Spray repository" at "http://repo.spray.io/",
  "Sbt-plugin-updates repository" at "http://dl.bintray.com/sbt/sbt-plugin-releases/",
  Resolver.mavenLocal
)

//addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.8")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")
