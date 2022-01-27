ThisBuild / version := "1.0.0"

ThisBuild / scalaVersion := "3.1.1"

ThisBuild / libraryDependencies +=  ("org.scalaj" %% "scalaj-http" % "2.4.2").cross(CrossVersion.for3Use2_13)
ThisBuild / libraryDependencies += "org.jsoup" % "jsoup" % "1.14.3"
ThisBuild / libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % Test
ThisBuild / libraryDependencies += "com.github.daddykotex" %% "courier" % "3.0.1"

lazy val root = (project in file("."))
  .settings(
    name := "QisScala3"
  )
